package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class UserRealNameBizTest extends BaseTest {

    @Resource
    private UserBiz userBiz;
    @Test
    public void queryUserTest(){
        User user = userBiz.queryUser();
        System.out.println(user.toString());
    }

    @Test
    public void login() throws Exception {
        User user = userBiz.queryUser();
        String result = userBiz.getToken(user);
        System.out.println(result);
    }
    @Test
    public void init() throws Exception {
        User user = userBiz.queryUser();
        String result = userBiz.getStatus(user, "499620d14d59418b93c1738784211815");
        System.out.println(result);
    }
    @Test
    public void init1() throws Exception {
        User user = userBiz.queryUser();
        String result = userBiz.getToken(user);
        System.out.println(result);
    }
}
