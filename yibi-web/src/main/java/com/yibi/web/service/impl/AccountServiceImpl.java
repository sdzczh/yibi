package com.yibi.web.service.impl;


import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.model.SessionInfo;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Account;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.FlowService;
import com.yibi.core.service.SysparamsService;
import com.yibi.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private com.yibi.core.service.AccountService accountService;
    @Autowired
    private SysparamsService sysparamsService;

    @Autowired
    private FlowService flowService;

    @Override
    public void walletListPage(HttpServletRequest request) {
        List<CoinManage> lists = this.coinManageService.selectAll(null);
        request.setAttribute("coins",lists);
    }

    @Override
    public Object getWalletList(Integer page, Integer rows, Integer coinType, Integer accountType, String phone, String userName) {
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("firstResult",pageModel.getFirstResult());
        map.put("maxResult",pageModel.getMaxResult());
        if (coinType != null){
            map.put("coinType",coinType);
        }
        if (accountType != null){
            map.put("accountType",accountType);
        }
        if (!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        if (!StrUtils.isBlank(userName)){
            map.put("username",userName);
        }
        List<Map<String,Object>> list = this.accountService.selectAccountOrPhone(map);
        int total = this.accountService.selectAccountCount(map);
        Grid grid = new Grid();
        grid.setRows(list);
        grid.setTotal(total);
        return grid;
    }

    @Override
    public Object updateAccount(Integer id, BigDecimal amount, HttpSession session) {
        Json json = new Json();
        //验证开关是否开启
        int rechargeOnoff =  sysparamsService.getValIntByKey(SystemParams.RECHARGE_ONOFF_TOTAL);
        if (rechargeOnoff != 1){
            json.setMsg("钱包充值功能被关闭，请联系罗杰！");
            return json;
        }

        //更新钱包
        Account account = this.accountService.selectByPrimaryKey(id);
        //只有现货钱包能充值
        if (account.getAccounttype() != 1){
            json.setMsg("只有现货能充值");
            return json;
        }
        //验证币种类型
        String coinTypes = sysparamsService.getValStringByKey(SystemParams.RECHARGE_COINTYPES);
        if (StrUtils.isBlank(coinTypes)){
            json.setMsg("没有可以充值的币种，前往配置");
            return json;
        }
        String [] coins = coinTypes.split(",");
        boolean result = false;
        for (String coin :coins){
            int coinType = Integer.valueOf(coin);
            if (account.getCointype() == coinType){
                result = true;
                break;
            }
        }
        if (!result){
            json.setMsg("你要充值的币种不在可以充值的范围内，请去配置");
            return json;
        }
        //更新前包添加流水
        accountService.updateAccountAndInsertFlow(account.getUserid(),GlobalParams.ACCOUNT_TYPE_SPOT,account.getCointype(),amount,BigDecimal.ZERO,
                ((SessionInfo)session.getAttribute("loginUser")).getUserid(), "管理员充值",0);
        json.setSuccess(true);
        json.setMsg("更新钱包成功");

        return  json;
    }
}
