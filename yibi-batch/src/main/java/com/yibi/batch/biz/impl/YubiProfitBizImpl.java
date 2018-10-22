package com.yibi.batch.biz.impl;

import com.alibaba.fastjson.JSON;
import com.yibi.batch.biz.YubiProfitBiz;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.DateUtils;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

@Slf4j
@Service
@Transactional
public class YubiProfitBizImpl implements YubiProfitBiz {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private YubiProfitService yubiProfitService;
    @Autowired
    private AccountTransferService accountTransferService;
    @Autowired
    private UserService userService;
    @Override
    public void start() {
        log.info("----------------余币宝收益计算开始-------------");
        Timestamp start = new Timestamp(System.currentTimeMillis());
        System.out.println("计算开始："+TimeStampUtils.toTimeString(start));
        String yubiCoinStr = sysparamsService.getValStringByKey(SystemParams.APP_CONFIG_YUBIBAO_COIN);
        List<Integer> yubiCoins = JSON.parseArray(yubiCoinStr, Integer.class);
        for(Integer coin:yubiCoins){
            int page = 0;
            CoinManage coinManage = coinManageService.queryByCoinType(coin);
            CoinScale coinScale = coinScaleService.queryByCoin(coin, CoinType.NONE);
            List<Account> accountList = accountService.queryByAvailBalance(GlobalParams.ACCOUNT_TYPE_YUBI,coin,coinManage.getYubiprofitminamt(),page,1000);
            while(accountList!=null && !accountList.isEmpty()){
                calculProfit(accountList,coinManage,coinScale);
                page = page +1;
                accountList = accountService.queryByAvailBalance(GlobalParams.ACCOUNT_TYPE_YUBI,coin,coinManage.getYubiprofitminamt(),page,1000);
            }

        }
        Timestamp end = new Timestamp(System.currentTimeMillis());
        System.out.println("计算结束："+TimeStampUtils.toTimeString(end));
        System.out.println("总计时（毫秒）："+(end.getTime()-start.getTime()+""));
        log.info("----------------余币宝收益计算结束-------------");

    }

    public void calculProfit(List<Account> accounts, CoinManage coinManage, CoinScale coinScale){

        for (Account account:accounts){
            User user = userService.selectByPrimaryKey(account.getUserid());

            if(user == null|| user.getLogintime()==null){
                log.info("用户:{},不存在或未登录，收益不计算",account.getUserid());
                continue;
            }
            YubiProfit lastProfit = yubiProfitService.queryLastProfit(account.getUserid(),account.getCointype());
            BigDecimal totalProfit =  lastProfit == null? BigDecimal.ZERO:lastProfit.getYubitotalprofit();

            String startTime = DateUtils.getSomeDay(-1)+" 15:00:00";
            //转入金额
            BigDecimal inAmt = accountTransferService.querySumByCoinAndTime(account.getUserid(),GlobalParams.ACCOUNT_TYPE_SPOT,GlobalParams.ACCOUNT_TYPE_YUBI,account.getCointype(),startTime);

            BigDecimal remain = BigDecimalUtils.subtract(account.getAvailbalance(),inAmt);
            if(remain.compareTo(coinManage.getYubiprofitminamt())>=0){
                /*账户余额减去转入金额后大于最低收益金额，开始计算收益*/
                BigDecimal profit = BigDecimalUtils.roundDown(BigDecimalUtils.multiply(remain,coinManage.getYubirate()),coinScale.getYubiscale());
                if(profit.compareTo(BigDecimal.ZERO)==1){
                    /*收益大于0 保存收益和流水*/
                    if(lastProfit!=null&&lastProfit.getCreatetime().compareTo(TimeStampUtils.getSomeDayTime(0))>=0){
                        //今日收益已经存在,则更新
                        lastProfit.setInterest(BigDecimalUtils.add(lastProfit.getInterest(),profit));
                        lastProfit.setYubitotalprofit(BigDecimalUtils.add(totalProfit,profit));
                        yubiProfitService.updateByPrimaryKey(lastProfit);

                    }else{
                        YubiProfit newProfit = new YubiProfit(account.getUserid(),account.getCointype(),remain,coinManage.getYubirate(),profit,BigDecimalUtils.add(totalProfit,profit));
                        yubiProfitService.insert(newProfit);
                    }

                    accountService.updateAccountAndInsertFlow(account.getUserid(),GlobalParams.ACCOUNT_TYPE_YUBI,account.getCointype(),profit,BigDecimal.ZERO,0,"收益",0);
                    /*Account account1 = new Account();
                    account1.setId(account.getId());
                    account1.setAvailbalance(profit);
                    accountList.add(account1);

                    Flow flow = new Flow(account.getUserid(),"收益",0,GlobalParams.ACCOUNT_TYPE_YUBI,account.getCointype(),0,profit,BigDecimalUtils.add(account.getAvailbalance(),profit));
                    flowList.add(flow);*/


                    if(user.getReferenceid()!=null && user.getReferenceid()>0){
                        User refUser = userService.selectByPrimaryKey(user.getReferenceid());
                        if(refUser!=null && !StringUtils.isBlank(refUser.getToken())){
                            YubiProfit refLastProfit = yubiProfitService.queryLastProfit(refUser.getId(),account.getCointype());
                            BigDecimal refTotalProfit = refLastProfit ==null ?BigDecimal.ZERO:refLastProfit.getYubitotalprofit();
                            if(refLastProfit!=null && refLastProfit.getCreatetime().compareTo(TimeStampUtils.getSomeDayTime(0))>=0){
                                //推荐人今日收益已经存在,则更新
                                refLastProfit.setInterest(BigDecimalUtils.add(refLastProfit.getInterest(),profit));
                                refLastProfit.setYubitotalprofit(BigDecimalUtils.add(refTotalProfit,profit));
                                yubiProfitService.updateByPrimaryKey(refLastProfit);

                            }else{
                                YubiProfit refNewProfit = new YubiProfit(refUser.getId(),account.getCointype(),remain,coinManage.getYubirate(),profit,BigDecimalUtils.add(refTotalProfit,profit));
                                yubiProfitService.insert(refNewProfit);
                            }
                            accountService.updateAccountAndInsertFlow(refUser.getId(),GlobalParams.ACCOUNT_TYPE_YUBI,account.getCointype(),profit,BigDecimal.ZERO,0,"推荐人收益",0);

                           /*// Account refAcc = accountService.getSpotAcountByUserAndCoinType(refUser.getId(),);
                            *//*推荐人已经登陆过，才返利息*//*
                            Account account2 = new Account();
                            account2.setId(account.getId());
                            account2.setAvailbalance(profit);
                            accountList.add(account2);

                            Flow refflow = new Flow(refUser.getId(),"推荐人收益",0,GlobalParams.ACCOUNT_TYPE_YUBI,account.getCointype(),0,profit,BigDecimalUtils.add(account.getAvailbalance(),profit));
                            flowList.add(flow);*/
                        }
                    }
                }

            }
        }

    }

}
