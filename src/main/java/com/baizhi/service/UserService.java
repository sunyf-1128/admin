package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.Users;

import java.util.List;

public interface UserService {

    //分页查询用户
    public List<User> queryUserBypage(Integer pageCurrent, Integer pageNum);

    //修改用户状态
    public void updateUserStatus(User user);

    //添加用户
    public String addUser(User user);

    //修改用户
    public void update(User user);

    //根据用户id信息
    public User queryOne(User user);

    //删除用户
    public void delUser(User user);

    //根据注册时间排序查用户
    public List<Users> queryCountForMonth(String year);
}
