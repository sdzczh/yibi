package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class PassWordBizTest extends BaseTest {

    @Resource
    private PassWordBiz passWordBiz;
    @Resource
    private UserBiz userBiz;
    @Test
    public void update() throws Exception {
        User user = userBiz.queryUser();
        String result = passWordBiz.update(user, "000000","000000",6);
        System.out.println(result);
    }
    @Test
    public void setOrderPwd() throws Exception {
        User user = userBiz.queryUser();
        String result = passWordBiz.setOrderPwd(user,"123123","000000",6);
        System.out.println(result);
    }
    @Test
    public void updateOrderPwd() throws Exception {
        User user = userBiz.queryUser();
        String result = passWordBiz.updateOrderPwd(user,"000000","000000",6);
        System.out.println(result);
    }
    @Test
    public void back() throws Exception {
        User user = userBiz.queryUser();
        String result = passWordBiz.getback("15621552297","000000", "000000", 1);
        System.out.println(result);
    }
}
