package com.baizhi.aspect;

import com.baizhi.entity.Users;
import com.baizhi.goEasy.Easy;
import com.baizhi.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Aspect
@Configuration
public class goEasyAroundAdvice {
    @Resource
    UserService userService;


    @Around("@annotation(com.baizhi.aspect.goEasy)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) {
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        List<Users> usersList = userService.queryCountForMonth("2020");
        List<String> month = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (int i = 0; i < usersList.size(); i++) {
            month.add(usersList.get(i).getMonth());
            count.add(usersList.get(i).getCount());
        }
        map.put("month", month);
        map.put("count", count);
        System.out.println(map.size());
        Easy.send(map);
        return proceed;
    }
}
