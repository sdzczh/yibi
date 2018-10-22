package com.yibi.orderapi.biz;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
public interface SmsCodeBiz {
    /**
     * 获取短信验证码
     * @param phone 手机号
     * @param type type 1：注册   2：其他
     * @return
     */
    String getValidateCode(String phone, Integer type);
}
