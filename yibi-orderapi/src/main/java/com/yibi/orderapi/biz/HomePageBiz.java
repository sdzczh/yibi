package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
public interface HomePageBiz {
    /**
     * 未登录首页初始化
     * @return
     */
    String initOut();

    /**
     * 登录后首页获取信息
     * @return
     * @param user
     */
    String initIn(User user);
}
