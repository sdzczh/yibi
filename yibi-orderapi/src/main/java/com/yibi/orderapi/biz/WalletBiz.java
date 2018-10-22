package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
public interface WalletBiz {
    /**
     * 查找钱包列表
     * @param user
     * @param accountType
     * @return
     * @return String
     * @date 2018-2-10
     * @author lina
     */
    String queryByUser(User user, Integer accountType);

    String queryCoinAvailBalance(User user,Integer coinType,Integer accountType);

    /**
     * 提现申请
     * @param user
     * @param password
     * @param amountDec
     * @param type
     * @param accountNum
     * @param coinType
     * @return
     */
    String withDrawApply(User user,String password,BigDecimal amount,Integer accountType ,String onlineNum,Integer coinType);

    /**
     * 提现列表
     * @param user
     * @param page
     * @param rows
     * @param type 账户类型 0:C2C 1:现货 2::挖矿账户
     * @param coinType
     * @return
     */
    String withDrawQueryAll(User user, Integer page, Integer rows, Integer accountType, Integer coinType) throws ParseException;

    /**
     * 资金划转
     * @param amountDec 金额
     * @param user
     * @param toAccount 转入账户
     * @param fromAccount 转出账户
     * @param coinType 币种
     * @param password 交易密码    @return
     */
    String transfer(User user, Integer type, Integer coinType, BigDecimal amount, String password);

    /**
     * 获取指定币种信息
     * @param user
     * @param page
     *@param rows
     * @param coinType 币种
     * @param accountType 账户类型   @return
     */
    String accountDetails(User user,Integer coinType,  Integer accountType, Integer page, Integer rows) throws ParseException;

    /**
     * 提现信息
     * @param user
     * @param coinType
     * @param accountType
     * @return
     */
    String withDrawInfo(User user, Integer coinType, Integer accountType);

    /**
     * 获取充值信息
     * @param user
     * @param coinType
     * @return
     */
    String rechargeInfo(User user, Integer coinType);

    /**
     * 给现有用户增加钱包
     */
    void addWallet();

}
