package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.RechargeService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("finance")
public class RechargeController extends BaseController {
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private CoinManageService coinManageService;

    @RequestMapping("rechargeListPage")
    public String rechargeListPage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "finance/recharge/rechargeListPage";
    }

    @RequestMapping("getRechargeList")
    @ResponseBody
    public Object  getRechargeList(Integer rows,Integer page,Integer coinType,Integer state,String phone,String username,String orderNum){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        if(coinType != null){
            map.put("coinType",coinType);
        }
        if(state != null){
            map.put("state",state);
        }
        if(!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        if(!StrUtils.isBlank(username)){
            map.put("username",username);
        }
        if(!StrUtils.isBlank(orderNum)){
            map.put("orderNum",orderNum);
        }
        List<Map<String,Object>> list = this.rechargeService.selectRechargeOrPhone(map);
        int total = this.rechargeService.selectCountRechargeOrPhone(map);
        Grid grid = new Grid();
        grid.setRows(list);
        grid.setTotal(total);
        return  grid;
    }



}
