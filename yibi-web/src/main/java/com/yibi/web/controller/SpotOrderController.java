package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.model.SessionInfo;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.Account;
import com.yibi.core.entity.OrderSpot;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.OrderSpotService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("order")
public class SpotOrderController extends BaseController {
    @Autowired
    private OrderSpotService orderSpotService;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private AccountService accountService;


    @RequestMapping("spotListPage")
    public String spotListPage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "/order/spot/spotListPage";
    }

    @RequestMapping("getSpotList")
    @ResponseBody
    public Object getSpotList(Integer page,Integer rows,Integer orderCoinType,Integer unitCoinType,Integer type,
                              Integer orderType,String phone,Integer state,Integer levFlag){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        if (orderCoinType != null){
            map.put("orderCoinType",orderCoinType);
        }
        if (unitCoinType != null){
            map.put("unitCoinType",unitCoinType);
        }
        if (type != null){
            map.put("type",type);
        }
        if (orderType != null){
            map.put("orderType",orderType);
        }
        if (state != null){
            map.put("state",state);
        }
        if (levFlag != null){
            map.put("levFlag",levFlag);
        }
        if (!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        Grid grid = new Grid();
        grid.setRows(this.orderSpotService.selectConditionPaging(map));
        grid.setTotal(this.orderSpotService.selectConditionCount(map));
        return  grid;
    }

    @RequestMapping("updateState")
    @ResponseBody
    public Object updateState(Integer id, HttpSession session){
        Integer operId = ((SessionInfo)session.getAttribute("loginUser")).getUserid();
        Json json = new Json();
        json.setSuccess(true);
        OrderSpot orderSpot = this.orderSpotService.selectByPrimaryKey(id);
        if (orderSpot.getState() != 0){
            json.setMsg("此订单状态不能撤销");
            return json;
        }
        BigDecimal amount = orderSpot.getRemain();//默认是卖出
        Integer coinType = orderSpot.getOrdercointype();
        Map<Object,Object> map = new HashMap<>();
        map.put("userid",orderSpot.getUserid());
        map.put("cointype",orderSpot.getOrdercointype());
        String operType = "现货卖出撤销";
        if (orderSpot.getType() == 0){//买入
            map.put("cointype",orderSpot.getUnitcointype());
            amount = orderSpot.getRemain().multiply(orderSpot.getPrice());
            operType = "现货买入撤销";
            coinType = orderSpot.getUnitcointype();
        }
        //更新订单状态
        orderSpot.setState(2);
        this.orderSpotService.updateByPrimaryKeySelective(orderSpot);
        Account account = this.accountService.selectAll(map).get(0);
        //更新钱包余额,添加流水
        this.accountService.updateAccountAndInsertFlow(orderSpot.getUserid(),1,coinType,amount,BigDecimal.ZERO,operId,operType,orderSpot.getId());
        json.setMsg("撤销成功");
        return json;

    }


}
