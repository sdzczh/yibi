package com.yibi.core.service;

import com.yibi.core.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/7/11 0011.
 */
public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;
    @Test
    public void selectByPhoneTest(){
        System.out.println(userService.selectByPhone("13500010001"));
    }
}
