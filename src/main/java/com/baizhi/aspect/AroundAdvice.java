package com.baizhi.aspect;

import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import com.baizhi.util.id;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;

@Aspect
@Configuration
public class AroundAdvice {

    @Resource
    HttpSession session;

    @Resource
    private LogMapper logMapper;

    @Around("@annotation(com.baizhi.aspect.MyLog)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) {
        //ProceedingJoinPoint  翻译为程序切入点
        // id 谁  什么时间  操作了什么  是否成功

        Admin adminLogin = null;
        String MethodName = proceedingJoinPoint.getSignature().getName();
        adminLogin = (Admin) session.getAttribute("adminLogin");

        String message = null;

        // String MethodValue = annotation.value();

        Log log = new Log();
        //退出时还能获取登录名
        if (adminLogin != null) {
            //System.out.println(adminLogin.getUsername() + "1");
            log.setAdminUsername(adminLogin.getUsername());
        }
        //                                     获取特征        获取到正在执行的类    类中所有方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String value = signature.getMethod().getAnnotation(MyLog.class).value();
        Object result = null;
        //启动核心功能 类似与拦截器、过滤器，放行之前的都是检查 回来之后看是否有异常
        try {
            log.setCreateDate(new Date());
            result = proceedingJoinPoint.proceed();

            message = "success";
        } catch (Throwable throwable) {
            throwable.printStackTrace();

            message = "error";
        }

        adminLogin = (Admin) session.getAttribute("adminLogin");
        log.setId(id.getId());

        if (adminLogin != null) {
            // System.out.println(adminLogin.getUsername() + "2");
            log.setAdminUsername(adminLogin.getUsername());
        }
        log.setOperate("执行了：" + MethodName + "(" + value + ")");
        if (value.equals("登录账户")) {
            HashMap<String, String> map = (HashMap<String, String>) result;
            if (map.get("status").equals("200")) {
                log.setStatus("success");
            } else {
                //得到形参列表的 实例化对象数组
                Object[] args = proceedingJoinPoint.getArgs();
                for (Object arg : args) {

                    System.out.println(arg.getClass().getSimpleName());
                    //利用反射得到Class类对象--->得到简单名字即类名--->判断
                    if (arg.getClass().getSimpleName().equals("Admin")) {

                        //强转
                        Admin admin = (Admin) arg;
                        //得到实例化对象中的数据
                        log.setAdminUsername(admin.getUsername());
                    }
                }

                log.setStatus("error");
            }
        } else {
            log.setStatus(message);
        }

        logMapper.insert(log);
        return result;
    }
}
