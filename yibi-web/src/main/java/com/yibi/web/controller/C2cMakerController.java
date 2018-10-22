package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.OrderMakerService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("order")
@Controller
public class C2cMakerController extends BaseController {
    @Autowired
    private OrderMakerService orderMakerService;
    @Autowired
    private CoinManageService coinManageService;

    @RequestMapping("makerListPage")
    public String makerListPage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "order/makerListPage";
    }
    @RequestMapping("getMakerList")
    @ResponseBody
    public Object getMakerList(Integer page,Integer rows,Integer coinType,Integer type,Integer state,String phone,String orderNum,Integer orderFlag ){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        if (coinType != null){
            map.put("coinType",coinType);
        }
        if (orderFlag != null){
            map.put("orderFlag",orderFlag);
        }
        if (type != null){
            map.put("type",type);
        }
        if (state != null){
            map.put("state",state);
        }
        if (!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        if (!StrUtils.isBlank(orderNum)){
            map.put("orderNum",orderNum);
        }
        Grid grid = new Grid();
        grid.setTotal(this.orderMakerService.selectConditionCount(map));
        grid.setRows(this.orderMakerService.selectConditionPaging(map));
        return grid;

    }



}
