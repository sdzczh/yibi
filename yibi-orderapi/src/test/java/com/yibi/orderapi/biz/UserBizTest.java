package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class UserBizTest extends BaseTest {

    @Resource
    private UserBiz userBiz;
    @Test
    public void queryUserTest(){
        User user = userBiz.queryUser();
        System.out.println(user.toString());
    }
    @Test
    public void register() throws Exception {
        String result = userBiz.register("15621552205","000000",149,"qwer123","","1",1);
        System.out.println(result);
    }
    @Test
    public void login() throws Exception {
        String result = userBiz.login("15621552298","qq940916","1",1,"61j2XdNIRTsnBRyi");
        System.out.println(result);
    }
    @Test
    public void loginByPhone() throws Exception {
        String result = userBiz.loginByPhone("15865711062","000000",1,"1",1,"");
        System.out.println(result);
    }
    @Test
    public void bindInfo() throws Exception {
        User user = userBiz.queryUser();
        String result = userBiz.bindInfo(user,"123456","34113","斯蒂芬森的","http://img.huolicoin.com/fai/eTr/pictureTr/201805221229351445.jpg","","",1);
        System.out.println(result);
    }
    @Test
    public void bindInfo1() throws Exception {
        User user = userBiz.queryUser();
        String result = userBiz.getCalcul(user);
        System.out.println(result);
    }


}
