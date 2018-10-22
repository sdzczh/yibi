package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/11 0011.
 */
public interface ChatRedPacketBiz {

    /**
     * 发送红包
     * @param user
     * @param accountType
     * @param coinType
     * @param amount
     * @param note
     * @param password
     * @return String
     * @date 2018-5-21
     * @author lina
     */
    String sendRedPacket(User user, Integer accountType, Integer coinType, BigDecimal amount, Integer num, String note, String password, String phone);

    /**
     * 查看红包详情
     * @param user
     * @param id
     * @return String
     * @date 2018-5-21
     * @author lina
     */
    String queryRedPacketDetail(User user, Integer id);

    /**
     * 接收红包
     * @param user
     * @param id
     * @return
     * @return String
     * @date 2018-5-21
     * @author lina
     */
    String reciveRedPacket(User user, Integer id);

    /**
     * 查询红包列表
     * @param user
     * @param type 类型：0：收到的红包 1：发出的红包
     * @return String
     * @date 2018-5-22
     * @author lina
     */
    String queryRedPacketList(User user, Integer type, Integer page, Integer rows);
}
