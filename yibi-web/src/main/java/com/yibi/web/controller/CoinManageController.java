package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.service.CoinManageService;
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
@RequestMapping("/coinManage")
public class CoinManageController extends BaseController {
    @Autowired
    private CoinManageService coinManageService;

    @RequestMapping("/coinListPage")
    public  String coinListPage(){
        return "system/coinManage/coinManage";
    }

    @RequestMapping("/getCoinList")
    @ResponseBody
    public Object getCoinList(Integer rows,Integer page){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("firstResult",pageModel.getFirstResult());
        map.put("maxResult",pageModel.getMaxResult());
        List<CoinManage> list  =  this.coinManageService.selectAll(map);
        Integer  total = this.coinManageService.selectCount(map);
        Grid grid = new Grid();
        grid.setTotal(total);
        grid.setRows(list);
        return grid ;
    }
    @RequestMapping("saveOrUpdateCoinPage")
    public String addCoinPage(HttpServletRequest request, Integer id){
        if(id != null){
            request.setAttribute("id",id);
        }
        return "system/coinManage/saveOrUpdateCoinPage";
    }
    @RequestMapping("saveOrUpdateCoin")
    @ResponseBody
    public Object saveOrUpdateCoin(CoinManage coinManage){
        if(coinManage.getId() != null){//执行更新操作
            this.coinManageService.updateByPrimaryKey(coinManage);
            Json json = new Json();
            json.setSuccess(true);
            json.setMsg("更新币种成功");
            return json;
        } else {//执行插入
            this.coinManageService.insert(coinManage);
            Json json = new Json();
            json.setSuccess(true);
            json.setMsg("添加币种成功");
            return json;
        }
    }
    @RequestMapping("loadForm")
    @ResponseBody
    public Object loadForm(Integer id){
        return this.coinManageService.selectByPrimaryKey(id);
    }
    @RequestMapping("deleteCoin")
    @ResponseBody
    public Object deleteCoin(Integer id){
        this.coinManageService.deleteByPrimaryKey(id);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("删除币种成功");
        return json;
    }

}
