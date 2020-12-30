package com.baizhi.controller;

import com.baizhi.entity.Log;
import com.baizhi.service.LogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    private LogService logService;

    @RequestMapping("/logPage")
    public List<Log> logPage(Integer page, Integer rows) {
        return logService.logPage(page, rows);
    }

}
