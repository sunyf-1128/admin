package com.baizhi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/text")
public class TextAjax {
    @RequestMapping("/aja")
    public String aja(String id) {
        if (id.equals("1")) {
            return "这是1的请求返回值";
        } else if (id.equals("2")) {
            return "这是2的请求返回值";
        }
        return "这既不是1也不是2的请求返回值";
    }
}
