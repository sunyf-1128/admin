package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

public interface AdminService {

    //后台登录
    public HashMap<String, String> login(Admin admin, String code, HttpSession session);

    //管理员分页
    public List<Admin> adminPage(Integer page, Integer rows);

    //添加管理员
    public void addAdmin(Admin admin);

    //修改管理员
    public void updateAdmin(Admin admin);

    //删除管理员
    public void delAdmin(Admin admin);
}
