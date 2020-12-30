package com.baizhi.service;

import com.baizhi.aspect.MyLog;
import com.baizhi.aspect.goEasy;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.User;
import com.baizhi.entity.UserExample;
import com.baizhi.entity.Users;
import com.baizhi.util.PageBegin;
import com.baizhi.util.id;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    //用户分页
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> queryUserBypage(Integer pageCurrent, Integer pageNum) {


        Integer begin = PageBegin.getBegin(pageCurrent, pageNum);


        UserExample userExample = new UserExample();

        RowBounds rowBounds = new RowBounds(begin - 1, pageNum);

        List<User> users = userMapper.selectByExampleAndRowBounds(userExample, rowBounds);


        return users;
    }

    //更改用户状态
    @MyLog("修改用户状态")
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserStatus(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    //添加用户
    @goEasy
    @MyLog("添加用户")
    @Override
    public String addUser(User user) {
        user.setId(id.getId());
        user.setCreateDate(new Date());
        user.setCredit(0.0);
        user.setStatus("normal");
        userMapper.insertSelective(user);
        return user.getId();
    }

    //修改用户信息
    @MyLog("修改用户信息")
    @Override
    public void update(User user) {

        userMapper.updateByPrimaryKeySelective(user);
    }

    //根据用户id用户信息
    @Override
    public User queryOne(User user) {

        return userMapper.selectByPrimaryKey(user);

    }

    @goEasy
    @MyLog("删除用户")
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delUser(User user) {
        userMapper.deleteByPrimaryKey(user);
    }

    //根据注册时间排序查用户
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Users> queryCountForMonth(String year) {

        return userMapper.queryCountForMonth("2020");
    }
}
