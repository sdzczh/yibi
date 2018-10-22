package com.yibi.openapi.biz;

public interface UserBiz {
    Object validateUser(String phone, String password) throws Exception;

    Object register(String phone,String password,String referPhone) throws Exception;

    /**
     * 修改密码
     * @param account
     * @param oldPassword
     * @param password
     * @return
     */
    Object updatePwd(String account, String oldPassword, String password);
}
