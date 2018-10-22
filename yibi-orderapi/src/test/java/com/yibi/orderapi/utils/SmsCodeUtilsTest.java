package com.yibi.orderapi.utils;

import com.yibi.extern.api.aliyun.smscode.SMSCodeUtil;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class SmsCodeUtilsTest extends BaseTest {

    @Resource
    private SMSCodeUtil smsCodeUtil;

    @Test
    public void sendCodeTest(){
        smsCodeUtil.getValidateCode("17686186183","SMS_107440031");
    }
}
