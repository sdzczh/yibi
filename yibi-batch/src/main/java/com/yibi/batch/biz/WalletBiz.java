package com.yibi.batch.biz;

import com.yibi.core.entity.AccountChain;

import java.util.List;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
public interface WalletBiz {




    /**
     * 得到用户地址表 分页
     * @param valueOf
     * @param page
     * @param rows
     * @return
     */
    List<AccountChain> getChainList(Integer type, int page, int rows);

    /**
     * 定时检查账户余额
     * @return void
     * @date 2018-3-6
     * @author lina
     * @param list
     */
    void startCheckAccount(Integer type, List<AccountChain> accountChainList);

    /**
     * 币种转移
     * @param type
     * @param accountChainList
     */
    void transToMainAccount(Integer type, List<AccountChain> accountChainList);

    /**
     * ERC20代币充值
     * @param type
     * @param accountChainList
     */
    void recargeERC(Integer type, List<AccountChain> accountChainList);
}
