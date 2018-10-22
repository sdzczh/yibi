package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.FlowService;
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
@RequestMapping("order")
public class FlowController extends BaseController {
    @Autowired
    private FlowService flowService;
    @Autowired
    private CoinManageService coinManageService;

    @RequestMapping("flowListPage")
    public String flowListPage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "order/flow/flowListPage";
    }
    @RequestMapping("getFlowList")
    @ResponseBody
    public Object getFlowList(Integer page,Integer rows,Integer coinType,Integer accountType,String phone,String userName){
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
        List<Map<String,Object>> list = this.flowService.selectFlowOrPhone(map);
        int total = this.flowService.selectFlowCount(map);
        Grid grid = new Grid();
        grid.setRows(list);
        grid.setTotal(total);
        return grid;
    }




}
