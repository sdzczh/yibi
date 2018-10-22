package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class BankBizTest extends BaseTest {

    @Resource
    private BankBiz bankBiz;
    @Resource
    private UserBiz userBiz;
    @Test
    public void queryUserTest(){
        User user = userBiz.queryUser();
        System.out.println(user.toString());
    }
    @Test
    public void systemBiz(){
        String user = bankBiz.queryList(0,100);
        System.out.println(user);
    }

}
