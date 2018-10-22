package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.CoinScaleService;
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
@RequestMapping("/coin")
public class CoinScaleController extends BaseController {
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private CoinManageService coinManageService;

    @RequestMapping("/coinScale")
    public String mainpage() {
        return "/system/coinConfigure/mainpage";
    }

    @RequestMapping("/getCoinScaleList")
    @ResponseBody
    public Object getCoinScaleList(Integer rows,Integer page){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("firstResult",pageModel.getFirstResult());
        map.put("maxResult",pageModel.getMaxResult());
        List<Map<String,Object>> list = this.coinScaleService.selectConditionPaging(map);
        Integer  total = this.coinScaleService.selectConditionCount(map);
        Grid grid = new Grid();
        grid.setTotal(total);
        grid.setRows(list);
        return grid ;
    }

    @RequestMapping("/addCoinScalePage")
    public String addCoinScalePage(HttpServletRequest request ) {
        List<CoinManage>  list  = this.coinManageService.selectAll(null);
        request.setAttribute("coins",list);
        return "/system/coinConfigure/addCoinScale";
    }

    @RequestMapping("addCoinScale")
    @ResponseBody
    public  Object addCoinScale(CoinScale coinScale){
        this.coinScaleService.insert(coinScale);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("添加币种小数位数成功");
        return json;
    }

    @RequestMapping("/updateCoinScalePage")
    public String updateCoinScalePage(Integer id, HttpServletRequest request) {
        CoinScale  coinScale = this.coinScaleService.selectByPrimaryKey(id);
        request.setAttribute("coinScale",coinScale);
        return "/system/coinConfigure/updateCoinScale";
    }

    @RequestMapping("updateCoinScale")
    @ResponseBody
    public  Object updateCoinScale(CoinScale coinScale){
        this.coinScaleService.updateByPrimaryKey(coinScale);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("修改币种小数位数成功");
        return json;
    }

    @RequestMapping("/loadForm")
    @ResponseBody
    public Object loadForm(Integer id){
        CoinScale coinScale = coinScaleService.selectByPrimaryKey(id);
        return coinScale;
    }

    @RequestMapping("/showCoinName")
    @ResponseBody
    public Object showCoinName(Integer coinType){
        CoinManage  coinManage = this.coinManageService.queryByCoinType(coinType);
        return coinManage.getCoinname();
    }
    @RequestMapping("deleteCoinScale")
    @ResponseBody
    public Object deleteCoinScale(Integer id){
        Json json = new Json();
        json.setSuccess(true);
        int result = this.coinScaleService.deleteByPrimaryKey(id);
        if (result != 1){
            json.setMsg("删除失败！");
        }
        json.setMsg("删除成功！");
        return json;
    }

}
