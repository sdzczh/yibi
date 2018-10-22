package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/23 0023.
 */
public interface YubiBiz {

    /**
     *
     * @param user
     * @param password
     * @param amountDec
     * @param type 0转入  1转出
     * @param coinType
     * @return
     */
    String transfer(User user, String password, BigDecimal amountDec, Integer type,Integer coinType);

    /**
     * 查询流水
     * @param user
     * @param coinType
     * @param page
     * @param rows
     * @return
     */
    String queryFlows(User user,Integer coinType,Integer page,Integer rows);

    /**
     * 查询余额
     * @param user
     * @param coinType
     * @param accountType
     * @return
     */
    String queryBalance(User user, Integer coinType, Integer accountType);
}
