package com.baizhi.goEasy;

import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;


public class Easy {

    public static void send(Object o) {
        String message = JSON.toJSONString(o);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-e037ab4e64d64ff6acb72bcf66a743ca");
        //发布消息 参数：通道名称，要发送消息
        goEasy.publish("my2006_channel", message);
    }
}
