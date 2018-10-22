package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/7/21 0021.
 */
public class DigBizTest extends BaseTest {
    @Autowired
    private DigBiz digBiz;

    @Test
    public void getDigPageInfoTest(){
        User user = new User();
        user.setId(3);
        String res = digBiz.getDigPageInfo(user,1);
        System.out.println(res);
    }

    @Test
    public void updateDigStateTest(){
        User user = new User();
        user.setId(3);
        user.setIdstatus(1);
        String res = digBiz.updateDigState(user);
        System.out.println(res);
    }

    @Test
    public void collectDigTest(){
        User user = new User();
        user.setId(3);
        user.setIdstatus(1);
        String res = digBiz.collectDig(user,0);
        System.out.println(res);
    }

    @Test
    public void queryDigListTest(){
        User user = new User();
        user.setId(3);
        String res = digBiz.queryDigList(user,0,10);
        System.out.println(res);
    }

    @Test
    public void queryUserSoulInfoTest(){
        User user = new User();
        user.setId(3);
        String res = digBiz.queryUserSoulInfo(user);
        System.out.println(res);
    }

    @Test
    public void querySoulRankTest(){
        User user = new User();
        user.setId(3);
        String res = digBiz.querySoulRank(user,0,10);
        System.out.println(res);
    }

    @Test
    public void querySoulPageInfoTest(){
        User user = new User();
        user.setId(3);
        user.setIdstatus(1);
        String res = digBiz.querySoulPageInfo(user);
        System.out.println(res);
    }

    @Test
    public void addSignSoulTest(){
        User user = new User();
        user.setId(3);
        user.setIdstatus(1);
        String res = digBiz.addSignSoul(user,0);
        System.out.println(res);
    }

    @Test
    public void addShareSoulTest(){
        User user = new User();
        user.setId(3);
        user.setIdstatus(1);
        String res = digBiz.addShareSoul(user,8);
        System.out.println(res);
    }

    @Test
    public void querySoulFlowTest(){
        User user = new User();
        user.setId(3);
        user.setIdstatus(1);
        String res = digBiz.querySoulFlow(user,0,10);
        System.out.println(res);
    }

    @Test
    public void addDigWithdrawTest(){
        User user = new User();
        user.setId(3);
        user.setIdstatus(1);
        user.setOrderpwd("e10adc3949ba59abbe56e057f20f883e");

        String res = digBiz.addDigWithdraw(user,"123456","312fdswew334",1,0);
        System.out.println(res);
    }
}
