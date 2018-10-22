package com.yibi.openapi.biz;

import java.math.BigDecimal;

public interface WalletBiz {
    /**
     * 提现
     *
     * @param account     用户名
     * @param password    交易密码
     * @param type        提现到的账户
     * @param accountType 资金来源
     * @param coinType    提现币种
     * @param money       提现金额
     * @return
     */
    Object withdrawMoney(String account, String password, int type, int accountType, Integer coinType, BigDecimal money);

    /**
     * 使用一币钱包充值游戏账户
     * @param account
     * @param password
     * @param type
     * @param accountType
     * @param coinType
     * @param money
     * @return
     */
    Object rechargeGame(String account, String password, int type, int accountType, Integer coinType, BigDecimal money);

    /**
     * 查询某用户现货钱包某币种可用数量
     * @param phone
     * @param coinType
     * @return
     */
    Object queryAvailBalance(String phone, Integer coinType);
}
