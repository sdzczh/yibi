package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.core.entity.Account;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.CoinScaleService;
import com.yibi.orderapi.biz.AccountBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
@Transactional
public class AccountBizImpl extends BaseBizImpl implements AccountBiz {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CoinScaleService coinScaleService;


    @Override
    public BigDecimal queryByUser(Integer id, int accountType) {
        Map<String, Object> accountMap = new HashMap<String, Object>();
        List<Account> list = accountService.queryByUserIdAndAccountType(id, accountType);
        BigDecimal totalSumOfCny = new BigDecimal(0);
        for(Account account :list){
            Map<String, Object> data = new HashMap<String, Object>();
            Integer coinType = account.getCointype();
            CoinScale coinScale = coinScaleService.queryByCoin(coinType, -1);
            BigDecimal availBalance = account.getAvailbalance();
            BigDecimal frozenBlance = account.getFrozenblance();
            BigDecimal totalBalance = BigDecimalUtils.add(availBalance, frozenBlance);
            BigDecimal totalOfCny = BigDecimalUtils.multiply(totalBalance, getPriceOfCNY(coinType));
            totalSumOfCny = BigDecimalUtils.add(totalOfCny, totalSumOfCny);
        }
        return totalSumOfCny;
    }
}
