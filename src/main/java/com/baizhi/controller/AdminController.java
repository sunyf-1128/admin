package com.baizhi.controller;

import com.baizhi.aspect.MyLog;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.CreateValidateCode;
import com.baizhi.util.ImageCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.baizhi.util.ImageCodeUtil.createImage;
import static com.baizhi.util.ImageCodeUtil.getSecurityCode;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Resource
    private AdminService adminService;


    @RequestMapping("/code")
    public String code(HttpSession session, HttpServletResponse response) throws IOException {
        CreateValidateCode vCode = new CreateValidateCode(66, 30, 4, 10);
        session.setAttribute("code", vCode.getCode()); //保存在Session作用域
        vCode.write(response.getOutputStream());//注意Struts2中没有response
        //System.out.println("code is request");
        return null;
    }


    @ResponseBody
    @RequestMapping("/login")
    public HashMap<String, String> login(HttpSession session, Admin admin, String code) {
        // System.out.println("admin is login");

        HashMap<String, String> map = adminService.login(admin, code, session);
        // System.out.println(map.size());
        return map;
    }

    @MyLog("退出账户")
    @ResponseBody
    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        session.removeAttribute("adminLogin");
        session.invalidate();
        return "ok";
    }

    @RequestMapping("/code1")
    public String code1(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
        /*
         * 1. 生成图片
         * 2. 保存图片上的文本到session域中
         * 3. 把图片响应给客户端
         */
        ImageCode vc = new ImageCode();
        BufferedImage image = vc.getImage();
        request.getSession().setAttribute("code", vc.getText());//保存图片上的文本到session域
        ImageCode.output(image, response.getOutputStream());
        return null;
    }

    @RequestMapping("code2")
    public void code2(HttpServletResponse response, HttpSession session) throws IOException {
        //获得随机字符
        String securityCode = getSecurityCode();
        //打印随机字符
        session.setAttribute("code", securityCode);
        System.out.println("====" + securityCode);
        //生成图片
        BufferedImage image = createImage(securityCode);
        //将生成的验证码图片以png(1.png)的格式输出到客户端
        ImageIO.write(image, "png", response.getOutputStream());
    }

    @ResponseBody
    @RequestMapping("/adminPage")
    public List<Admin> adminPage(Integer page, Integer rows) {
        return adminService.adminPage(page, rows);
    }

    @ResponseBody
    @RequestMapping("/edit")
    public void edit(Admin admin, String oper) {
        if (oper.equals("add")) {
            adminService.addAdmin(admin);
        } else if (oper.equals("edit")) {
            adminService.updateAdmin(admin);
        } else {
            adminService.delAdmin(admin);
        }
    }

    @ResponseBody
    @RequestMapping("/updateAdminStatus")
    public void updateAdminStatus(Admin admin) {
        adminService.updateAdmin(admin);
    }
}
