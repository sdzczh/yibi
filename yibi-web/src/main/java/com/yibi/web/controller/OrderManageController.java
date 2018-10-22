package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.RedisUtil;
import com.yibi.core.entity.OrderManage;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.OrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("orderManage")
public class OrderManageController {
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private CoinManageService coinManageService;
    @Resource
    private RedisTemplate<String, String> redis;

    @RequestMapping("orderManageListPage")
    public String orderManageList(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "system/orderManage/orderManage";
    }
    @RequestMapping("orderManageList")
    @ResponseBody
    public  Object orderManageList(Integer page,Integer rows,Integer orderCoinType,Integer unitCoinType){
        PageModel pageModel  = new PageModel(page,rows);
        Map<Object,Object> map  = new HashMap<>();
        map.put("firstResult",pageModel.getFirstResult());
        map.put("maxResult",pageModel.getMaxResult());
        map.put("orderCoinType",orderCoinType);
        map.put("unitCoinType",unitCoinType);
        Grid grid = new Grid();
        grid.setRows(this.orderManageService.selectConditionPaging(map));
        grid.setTotal(this.orderManageService.selectConditionCount(map));
        return grid;
    }

    @RequestMapping("saveOrderManagePage")
    public  String saveOrderManagePage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "system/orderManage/saveOrderManagePage";
    }
    @RequestMapping("saveOrderManage")
    @ResponseBody
    public  Object saveOrderManage(OrderManage orderManage){
        Json json = new Json();
        json.setSuccess(true);
        int result = this.orderManageService.insertSelective(orderManage);
        if (result == 0){
            json.setMsg("添加失败");
            return json;
        }
        //加入缓存中
        RedisUtil.addHashObject(redis,"OrderManage",orderManage.getOrdercointype().toString()+":"+orderManage.getUnitcointype().toString(),orderManage);
        json.setMsg("新增交易管理配置成功");
        return json;
    }

    @RequestMapping("updateOrderManagePage")
    public  String saveOrUpdateOrderManagePage(Integer id, HttpServletRequest request){
        if (id != null){
            request.setAttribute("orderManageId",id);
        }
        return "system/orderManage/updateOrderManage";
    }
    @RequestMapping("updateOrderManage")
    @ResponseBody
    public Object updateOrderManage(OrderManage orderManage){
        Json json = new Json();
        json.setSuccess(true);
        this.orderManageService.updateByPrimaryKeySelective(orderManage);
        RedisUtil.addHashObject(redis,"OrderManage",orderManage.getOrdercointype().toString()+":"+orderManage.getUnitcointype().toString(),orderManage);
        json.setMsg("更新交易管理配置成功");
        return json;
    }

    @RequestMapping("loadOrderManageForm")
    @ResponseBody
    public  Object loadOrderManageForm(Integer id){
        return  this.orderManageService.selectByPrimaryKey(id);
    }

}
