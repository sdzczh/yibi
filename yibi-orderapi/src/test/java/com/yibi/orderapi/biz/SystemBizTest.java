package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class SystemBizTest extends BaseTest {

    @Resource
    private SystemBiz systemBiz;
    @Resource
    private UserBiz userBiz;
    @Test
    public void queryUserTest(){
        User user = userBiz.queryUser();
        System.out.println(user.toString());
    }
    @Test
    public void systemBiz(){
        String user = systemBiz.checkUpdate(1,0);
        System.out.println(user);
    }
    @Test
    public void aboutInfo(){
        String user = systemBiz.aboutInfo();
        System.out.println(user);
    }
    @Test
    public void config(){
        String user = systemBiz.getStartupParam(13);
        System.out.println(user);
    }
    @Test
    public void poster(){
        User user = userBiz.queryUser();
        String s = systemBiz.getPoster(user);
        System.out.println(s);
    }

}
