package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.core.service.UserService;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/24 0024.
 */
public class YubiBizTest extends BaseTest {
    @Resource
    private YubiBiz yubiBiz;
    @Resource
    private UserService userService;

    @Test
    public void transferTest(){
        User user = userService.selectByPhone("15621552297");
        String res = yubiBiz.transfer(user,"123456",new BigDecimal(50),0,0);
        System.out.println(res);
    }

    @Test
    public void queryFlowsTest(){
        User user = userService.selectByPhone("15621552297");
        String res = yubiBiz.queryFlows(user,0,0,10);
        System.out.println(res);
    }

    @Test
    public void queryBalanceTest(){
        User user = userService.selectByPhone("15621552297");
        String res = yubiBiz.queryBalance(user,0,4);
        System.out.println(res);
    }
}
