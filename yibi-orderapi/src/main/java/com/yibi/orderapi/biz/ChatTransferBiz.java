package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/12 0012.
 */
public interface ChatTransferBiz {

    /**
     * 聊天发送转账
     * @param user
     * @param phone
     * @param accountType
     * @param coinType
     * @param amount
     * @param note
     * @param password
     * @return String
     * @date 2018-5-22
     * @author lina
     */
    String sendFransfer(User user, String phone, Integer accountType, Integer coinType, BigDecimal amount, String note, String password) ;

    /**
     * 查看转账详情
     * @param user
     * @param id
     * @return
     * @return String
     * @date 2018-5-22
     * @author lina
     */
    String queryTransferDetail(User user, Integer id);

    /**
     * 查询转账记录
     * @param user
     * @param type 0转入记录 1转出记录
     * @return
     * @return String
     * @date 2018-5-22
     * @author lina
     */
    String queryTransferList(User user, Integer type, Integer page, Integer rows);
}
