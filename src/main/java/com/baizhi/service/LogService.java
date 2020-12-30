package com.baizhi.service;

import com.baizhi.entity.Log;

import java.util.List;

public interface LogService {

    //查看日志，分页
    public List<Log> logPage(Integer page, Integer rows);
}
