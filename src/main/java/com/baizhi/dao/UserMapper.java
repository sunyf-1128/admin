package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.Users;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    public List<Users> queryCountForMonth(@Param("year") String year);
}