package com.baizhi.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class aliyunUtil {
    private static Properties pp = new Properties();

    static {
        try {
            InputStream is = aliyunUtil.class.getResourceAsStream("/conf/aliyun.properties");
            pp.load(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getKeyAndSecret() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("endpoint", pp.getProperty("endpoint"));
        map.put("AccessKeyID", pp.getProperty("AccessKeyID"));
        map.put("AccessKeySecret", pp.getProperty("AccessKeySecret"));
        return map;
    }
}
