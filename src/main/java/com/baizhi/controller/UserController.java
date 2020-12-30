package com.baizhi.controller;

import com.baizhi.aliyun.aliyunUploadDelFragment;
import com.baizhi.entity.User;
import com.baizhi.entity.Users;
import com.baizhi.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //用户分页
    @RequestMapping("/userPage")
    public List<User> userPage(Integer page, Integer rows) {
        // System.out.println("Page：" + page + ";每页：" + rows);
        List<User> pages = userService.queryUserBypage(page, rows);

        return pages;
    }

    //修改用户状态
    @RequestMapping("/updateUserStatus")
    public void updateUserStatus(User user) {
        // System.out.println(user);
        userService.updateUserStatus(user);
    }

    //用户的添加、修改、删除
    @RequestMapping("/edit")
    public String edit(User user, String oper) {

        String id = null;
        // System.out.println(user);
        if (oper.equals("add")) {
            id = userService.addUser(user);
        } else if (oper.equals("edit")) {
            user.setHeadPortrait(null);
            userService.update(user);
            id = user.getId();
        } else {
            userService.delUser(user);
        }
        // System.out.println("add:" + id);
        return id;
    }

    //上传头像
    @RequestMapping("/uploadHead")
    public void uploadHead(MultipartFile headPortrait, String id, HttpServletRequest request) throws IOException {

//        System.out.println(headPortrait);
//        org.springframework.core.io.Resource resource = headPortrait.getResource();
//        System.out.println(resource);
        User user = new User();
        user.setId(id);
        if (headPortrait != null) {
            //获取上传头像的原始名+时间戳
            String fileName = new Date().getTime() + "-" + headPortrait.getOriginalFilename();
            //System.out.println(user.getId());
            user.setHeadPortrait(fileName);
            //更新数据库的头像存放地址
            userService.update(user);
            String path = request.getServletContext().getRealPath("/upload");
            File files = new File(path);
            if (!files.exists()) {
                files.mkdirs();
                // System.out.println(mkdirs);
            }
            headPortrait.transferTo(new File(path, fileName));//上传
        }
    }

    //(aliyun)上传头像
    @RequestMapping("/uploadHeadImg")
    public void uploadHeadImg(MultipartFile headPortrait, String id, HttpServletRequest request) throws IOException {
        // System.out.println(url);
        User user = new User();
        user.setId(id);
        if (headPortrait != null) {

            user.setId(id);
            User one = userService.queryOne(user);
            //删除原有文件
            String fileOldAddress = one.getHeadPortrait();
            // System.out.println(one.getHeadPortrait());
            //返回字符串中以/img/前的下标
            int begin = fileOldAddress.indexOf("/img/") + 5;
            int end = fileOldAddress.length();
            String fileOldName = fileOldAddress.substring(begin, end);//得到文件名
            // System.out.println(fileOldName);
            aliyunUploadDelFragment.del(fileOldName, "sunyf-yx", "img/");

            //获取文件的原始名字
            String name = headPortrait.getOriginalFilename();
            //获取上传头像的原始名+时间戳
            String fileName = new Date().getTime() + "-" + headPortrait.getOriginalFilename();
            //System.out.println(user.getId());
            user.setHeadPortrait("https://sunyf-yx.oss-cn-beijing.aliyuncs.com/img/" + fileName);
            //更新数据库的头像存放地址
            userService.update(user);
            //                         调用时请配置为:  "sunyf-yx"            "img/"
            aliyunUploadDelFragment.upload(fileName, "sunyf-yx", "img/", headPortrait);

        }
    }

    @RequestMapping("/queryCountForMonth")
    public HashMap<String, Object> queryCountForMonth(String year) {

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
        // System.out.println(123);
        return map;
    }
}
