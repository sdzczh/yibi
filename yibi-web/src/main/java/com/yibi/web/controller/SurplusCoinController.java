package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.YubiProfitService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("order")
@Controller
public class SurplusCoinController extends BaseController {
    @Autowired
    private YubiProfitService yubiProfitService;
    @Autowired
    private CoinManageService coinManageService;

    @RequestMapping("surplusCoinRecord")
    public String surplusCoinRecordPage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "/order/surplusCoin/surplusCoinRecordPage";
    }
    @RequestMapping("getSurplusCoinList")
    @ResponseBody
    public Object getSurplusCoinList(Integer page,Integer rows,String phone,String userName,Integer coinType){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("firstResult",pageModel.getFirstResult());
        map.put("maxResult",pageModel.getMaxResult());
        if(coinType != null){
            map.put("coinType",coinType);
        }
        if (!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        if (!StrUtils.isBlank(userName)){
            map.put("userName",userName);
        }
        List<Map<String,Object>> list = this.yubiProfitService.selectYbProfitByCondition(map);
        int total = this.yubiProfitService.selectYbProfitByConditionCount(map);
        Grid grid = new Grid();
        grid.setTotal(total);
        grid.setRows(list);
        return  grid;

    }
}
