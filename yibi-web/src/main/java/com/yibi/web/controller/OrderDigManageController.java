package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.core.entity.DealDigConfig;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.DealDigConfigService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("orderDig")
public class OrderDigManageController extends BaseController {
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private DealDigConfigService dealDigConfigService;

    @RequestMapping("orderDigManage")
    public String orderDigMaange(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "system/orderDigManage/orderDigManage";
    }

    @RequestMapping("orderDigList")
    @ResponseBody
    public Object orderDigList(Integer ordercoinType,Integer page,Integer rows){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        if (ordercoinType != null){
            map.put("ordercoinType",ordercoinType);
        }
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        Grid grid = new Grid();
        grid.setRows(this.dealDigConfigService.selectConditionAll(map));
        grid.setTotal(this.dealDigConfigService.selectConditionCount(map));
        return grid;
    }

    @RequestMapping("addOrderDigManage")
    public String addOrderDigManage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "/system/orderDigManage/addOrderDigManage";
    }

    @RequestMapping("addOrderDig")
    @ResponseBody
    public  Object addOrderDig(DealDigConfig dealDigConfig){
        Json json = new Json();
        json.setSuccess(true);
        int result =  this.dealDigConfigService.insertSelective(dealDigConfig);
        if (result == 0){
            json.setMsg("新增失败");
            return json;
        }
        json.setMsg("新增成功");
        return json ;
    }



    @RequestMapping("updateOrderDigManage")
    public String updateOrderDigManage(Integer id, HttpServletRequest request){
        request.setAttribute("orderDigId",id);
        return "/system/orderDigManage/updateOrderDigManage";
    }
    @RequestMapping("loadOrderDigForm")
    @ResponseBody
    public Object loadOrderDigForm(Integer id){
        return this.dealDigConfigService.selectByPrimaryKey(id);
    }

    @RequestMapping("updateOrderDig")
    @ResponseBody
    public Object updateOrderDig(DealDigConfig dealDigConfig){
        Json json = new Json();
        json.setSuccess(true);
        int result =  this.dealDigConfigService.updateByPrimaryKeySelective(dealDigConfig);
        if (result == 0){
            json.setMsg("更新失败");
            return json;
        }
        json.setMsg("更新成功");
        return json ;
    }


}
