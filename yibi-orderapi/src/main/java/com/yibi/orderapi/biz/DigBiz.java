package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

/**
 * Created by Administrator on 2018/7/19 0019.
 */
public interface DigBiz {

    String getDigPageInfo(User user, Integer level);

    String updateDigState(User user);

    String collectDig(User user,Integer coinType);

    String queryDigList(User user,Integer page, Integer rows);

    /**
     * 获取提升魂力页面信息
     * @param user
     * @return
     */
    String querySoulPageInfo(User user);

    String addSignSoul(User user,Integer type);

    String addShareSoul(User user,Integer type);

    /**
     * 查询用户魂力信息
     * @param user
     * @return
     */
    String queryUserSoulInfo(User user);

    /**
     * 查询魂力排名
     * @param user
     * @param page
     * @param rows
     * @return
     */
    String querySoulRank(User user,Integer page,Integer rows);

    /**
     * 查询用户魂力流水
     * @param user
     * @param page
     * @param rows
     * @return
     */
    String querySoulFlow(User user,Integer page,Integer rows);

    /**
     * 挖矿账户提现
     * @param user
     * @param password
     * @param accountNum
     * @param type 0提现到其他账户，1提现到现货账户
     * @param coinType
     * @return
     */
    String addDigWithdraw(User user, String password, String accountNum, Integer type, Integer coinType);
}
