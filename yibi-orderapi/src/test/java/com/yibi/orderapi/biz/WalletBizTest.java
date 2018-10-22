package com.yibi.orderapi.biz;

import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class WalletBizTest extends BaseTest {

    @Resource
    private WalletBiz walletBiz;
    @Resource
    private UserBiz userBiz;

    @Test
    public void queryUserTest1(){
        User user = userBiz.queryUser();
        System.out.println(user.toString());
    }
    @Test
    public void queryUserTest(){
        User user = new User();
        user.setId(192);
        String result = walletBiz.queryByUser(user, 1);
        System.out.println(result);
    }
    @Test
    public void accountDetails() throws ParseException {
        User user = new User();
        user.setId(12);
        String result = walletBiz.accountDetails(user, 0, 1, 0, 5);
        System.out.println(result);
    }
    @Test
    public void info() throws ParseException {
        User user = userBiz.queryUser();
        String result = walletBiz.rechargeInfo(user, 8);
        System.out.println(result);
    }
    @Test
    public void withDrawApply() throws ParseException {
        User user = userBiz.queryUser();
        String result = walletBiz.withDrawApply(user, "123456", new BigDecimal(1), 1 , "111", 1);
        System.out.println(result);
    }
    @Test
    public void withDrawQueryAll() throws ParseException {
        User user = userBiz.queryUser();
        String result = walletBiz.withDrawQueryAll(user,0,5,1,3);
        System.out.println(result);
    }
    @Test
    public void transfer() throws ParseException {
        User user = userBiz.queryUser();
        String result = walletBiz.transfer(user, GlobalParams.ACCOUNT_TRANSFER_TYPE_SPOTTOC2C,0,new BigDecimal(1),"000000");
        System.out.println(result);
    }
    @Test
    public void withDrawInfo() throws ParseException {
        User user = userBiz.queryUser();
        String result = walletBiz.withDrawInfo(user, 0, 1);
        System.out.println(result);
    }

    @Test
    public void queryCoinAvailBalanceTest(){
        User user = userBiz.queryUser();
        String res = walletBiz.queryCoinAvailBalance(user,0,0);
        System.out.println(res);
    }

}
