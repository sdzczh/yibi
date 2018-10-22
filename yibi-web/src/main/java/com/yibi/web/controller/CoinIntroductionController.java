package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.core.entity.CoinIntroduction;
import com.yibi.core.service.CoinIntroductionService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("coinIntroduction")
public class CoinIntroductionController extends BaseController {
    @Autowired
    private CoinIntroductionService coinIntroductionService;

    @RequestMapping("coinIntroductionListPage")
    public String coinIntroductionPage(){
        return "/document/coinIntroduction/coinIntroductionManage";
    }
    @RequestMapping("getCoinIntroductionList")
    @ResponseBody
    public Object getCoinIntroductionList(){
        return this.coinIntroductionService.selectAll(null);
    }
    @RequestMapping("doCoinIntroductionPage")
    public String doCoinIntroductionPage (Integer id, HttpServletRequest request){
        if (id != null){
            request.setAttribute("coinIntroductionId",id);
        }
        return "/document/coinIntroduction/doCoinIntroduction";
    }
    @RequestMapping("loadForm")
    @ResponseBody
    public Object loadForm(Integer id){
        return this.coinIntroductionService.selectByPrimaryKey(id);
    }
    @RequestMapping("operCoinIntroduction")
    @ResponseBody
    public Object operCoinIntroduction(CoinIntroduction coinIntroduction){
        Json json = new Json();
        json.setSuccess(true);
        int result = 0;
        if (coinIntroduction.getId()!= null){//更新
            result = this.coinIntroductionService.updateByPrimaryKeySelective(coinIntroduction);
            if (result == 0){
                json.setMsg("更新失败");
            }
            json.setMsg("更新成功");
        }else {//新增
            result = this.coinIntroductionService.insertSelective(coinIntroduction);
            if (result == 0){
                json.setMsg("新增失败");
            }
            json.setMsg("新增成功");
        }
        return json;
    }
    @RequestMapping("deleteCoinIntroduction")
    @ResponseBody
    public Object deleteCoinIntroduction(Integer id){
        Json json = new Json();
        int result = this.coinIntroductionService.deleteByPrimaryKey(id);
        if (result == 0 ){
            json.setMsg("删除失败");
            return json;
        }
        json.setMsg("删除成功");
        return json;
    }
}
