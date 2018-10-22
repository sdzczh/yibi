package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
public interface PassWordBiz {
    /**
     * 修改登录密码
     * @param user
     * @param password 新密码
     * @param code
     * @param codeId
     * @return
     */
    String update(User user, String password, String code, Integer codeId);

    /**
     * 设置交易密码
     * @param user
     * @param password
     * @param code
     * @param codeId
     * @return
     */
    String setOrderPwd(User user, String password, String code, Integer codeId);

    /**
     * 修改交易密码
     * @param user
     * @param password
     * @param code
     * @param codeId
     * @return
     */
    String updateOrderPwd(User user, String password, String code, Integer codeId);

    /**
     * 找回密码
     *
     * @param phone
     * @param password
     * @param code
     * @param codeId
     * @return
     */
    String getback(String phone, String password, String code, Integer codeId);
}


