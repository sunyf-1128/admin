package com.baizhi.controller;

import com.baizhi.VO.VideoVO;
import com.baizhi.service.VideoVOService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/app")
public class InterfaceController {
    @Resource
    private VideoVOService videoVOService;

    @RequestMapping("/queryByReleaseTime")
    public HashMap<String, Object> queryByReleaseTime(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        List<VideoVO> videoVOS = videoVOService.queryAll();

        map.put("data", videoVOS);
        map.put("message", "查询成功");
        map.put("status", "100");
        return map;
    }
}
