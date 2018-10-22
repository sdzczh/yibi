package com.yibi.orderapi.biz.impl;

import com.yibi.core.entity.Recharge;
import com.yibi.core.entity.User;
import com.yibi.core.service.RechargeService;
import com.yibi.orderapi.biz.RechargeBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Slf4j
@Service
@Transactional
public class RechargeBizImpl implements RechargeBiz{
    @Autowired
    private RechargeService rechargeService;



    @Override
    public Recharge rechargeApply(User user, BigDecimal amount, Integer coinType, String txid) {
        return rechargeService.rechargeApply(user, amount, coinType, txid);
    }

}
