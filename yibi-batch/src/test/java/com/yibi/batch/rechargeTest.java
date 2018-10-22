package com.yibi.batch;

import com.yibi.batch.biz.WalletBiz;
import com.yibi.core.entity.AccountChain;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.service.CoinManageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/24 0024.
 */
public class rechargeTest extends BaseTest{
    @Autowired
    private WalletBiz walletBiz;
    @Autowired
    private CoinManageService coinManageService;


    @Test
    public void test(){
        Map<Object, Object> map = new HashMap<>();
        map.put("rechspotonoff", 1);
        List<CoinManage> list = coinManageService.selectAll(map);
        for (CoinManage cm : list){
            List<AccountChain>AccountChainList = walletBiz.getChainList(cm.getCointype(), 0, 100);
            walletBiz.startCheckAccount(cm.getCointype(), AccountChainList);
        }
    }
    @Test
    public void test1(){
        Map<Object, Object> map = new HashMap<>();
        map.put("rechspotonoff", 1);
        map.put("cointype", 8);
        List<CoinManage> list = coinManageService.selectAll(map);
        for (CoinManage cm : list){
            List<AccountChain>AccountChainList = walletBiz.getChainList(cm.getCointype(), 0, 100);
            walletBiz.recargeERC(cm.getCointype(), AccountChainList);
        }
    }
}
