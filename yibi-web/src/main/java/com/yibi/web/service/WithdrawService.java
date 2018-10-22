package com.yibi.web.service;

import javax.servlet.http.HttpSession;

public interface WithdrawService {
    /**
     * 获取提现列表
     * @param accountType
     * @param rows
     * @param page
     * @param coinType
     * @param state
     * @param phone
     * @param username
     * @param orderNum
     * @return
     */
    Object getWithdrawList(Integer accountType, Integer rows, Integer page, Integer coinType, Integer state, String phone, String username, String orderNum);

    /**
     * 更新提现订单状态
     * @param id
     * @param type
     * @param session
     * @return
     */
    Object updateWithdrawState(Integer id, Integer type, HttpSession session);
}
