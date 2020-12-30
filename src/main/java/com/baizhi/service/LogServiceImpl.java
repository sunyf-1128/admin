package com.baizhi.service;

import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import com.baizhi.entity.LogExample;
import com.baizhi.util.PageBegin;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;

    //分页查看日志
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Log> logPage(Integer page, Integer rows) {

        Integer begin = PageBegin.getBegin(page, rows) - 1;

        LogExample logExample = new LogExample();

        RowBounds rowBounds = new RowBounds(begin, rows);


        return logMapper.selectByExampleAndRowBounds(logExample, rowBounds);
    }
}
