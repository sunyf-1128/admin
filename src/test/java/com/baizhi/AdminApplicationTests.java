package com.baizhi;

import com.baizhi.dao.AdminMapper;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AdminApplicationTests {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        User user = new User();

        user.setHeadPortrait("https://sunyf-yx.oss-cn-beijing.aliyuncs.com/img/1608856841084-1.jpg");


        user.setArea("山西");
        user.setIntro("123123");
        user.setMobile("1231231");
        user.setUsername("sdfsdfsdf");
        user.setSex("man");
        System.out.println(user);
        userService.addUser(user);

    }

}
