package com.yibi.batch.biz;

import com.yibi.core.entity.User;
import com.yibi.core.entity.UserDiginfo;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25 0025.
 */
public interface CalculateForceBiz {

    /**
     * 查询昨日未签到的用户
     * @param page
     * @param rows
     * @return
     * @return List<UserDiginfo>
     * @date 2018-4-23
     * @author lina
     */
    List<UserDiginfo> queryListPage(int page, int rows);

    /**
     * 签到中断减算力
     * @param users
     * @param calDay
     */
    void calculateForceCheck(List<UserDiginfo> users, int calDay);

    /**
     *
     * @param users 用户中断交易，减少算力
     * @date 2018-4-20
     * @author lina
     */
    void orderCalculForceCheck(List<User> users, int forceMinus);
}
