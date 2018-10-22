package com.yibi.orderapi.biz;

import com.yibi.common.model.PageModel;
import com.yibi.core.entity.User;

public interface OrderBiz {
    /**
     * 获取现货交易界面场景信息
     */
    String mainpageInfo(User user, Integer orderCoin, Integer unitCoin, Integer levFlag);

    /**
     * 限价买入
     */
    String limitPriceBuy(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String price, String amount, String password);

    /**
     * 限价卖出
     */
    String limitPriceSale(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String price, String amount, String password);

    /**
     * 市价买入
     */
    String marketPriceBuy(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String total, String password);

    /**
     * 市价卖出
     */
    String marketPriceSale(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String amount, String password);

    /**
     * 查询当前委托或历史记录
     */
    String orderRecord(User user, Integer orderCoin, Integer unitCoin, Integer type, PageModel pageModel);

    /**
     * 撤销订单
     */
    String orderCancel(Integer id);

    /**
     * 订单详情
     */
    String orderDetail(Integer id);

    /**
     * 挖矿交易信息
     * @param user
     * @param page
     *@param rows @return
     */
    String dealDigRecordList(User user, Integer page, Integer rows);

    /**
     * 验证实名状态和交易密码
     * @param user
     * @return
     */
    String checkRealNameAndOrderPassWord(User user);

    /**
     * 小K线图
     * @param orderCoin
     * @param unitCoin
     * @return
     */
    String minKLine(Integer orderCoin, Integer unitCoin);
}
