package com.yibi.core.service;

import com.yibi.core.BaseTest;
import com.yibi.core.exception.BanlanceNotEnoughException;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/12 0012.
 */
public class AccountServiceTest extends BaseTest {
    @Resource
    private AccountService accountService;

    @Test
    public void updateAccountAndInsertFlowTest(){
        try {
            accountService.updateAccountAndInsertFlow(3,0,1, BigDecimal.ONE,BigDecimal.ZERO,3,"测试啦",1);
        } catch (BanlanceNotEnoughException e) {
            e.printStackTrace();
        }
    }
}
