package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.core.entity.DigBase;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.DigBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("digBase")
@Controller
public class DigBaseController {
    @Autowired
    private DigBaseService digBaseService;
    @Autowired
    private CoinManageService coinManageService;

    @RequestMapping("digBaseListPage")
    public String DigBasePage(){
        return "system/digBase/digBaseList";
    }

    @RequestMapping("digBaseList")
    @ResponseBody
    public Object digBaseList(){
        return this.digBaseService.selectAllByCondtion();
    }

    @RequestMapping("addDigBasePage")
    public String addDigBasePage(HttpServletRequest request){
        request.setAttribute("coins",this.coinManageService.selectAll(null));
        return "system/digBase/addDigBasePage";
    }

    @RequestMapping("addDigBase")
    @ResponseBody
    public Object addDigBase(DigBase digBase){
        Json json = new Json();
        json.setSuccess(true);
        int result = this.digBaseService.insertSelective(digBase);
        if (result == 0){
            json.setMsg("新增失败");
            return  json;
        }
        json.setMsg("新增成功");
        return  json;
    }

    @RequestMapping("updateDigBasePage")
    public String updateDigBasePage(HttpServletRequest request,Integer id){
        request.setAttribute("digBaseId",id);
        return "system/digBase/updateDigBasePage";
    }
    @ResponseBody
    @RequestMapping("loadDigBaseForm")
    public Object loadDigBaseForm(Integer id){
        return this.digBaseService.selectByPrimaryKey(id);
    }
    @RequestMapping("updateDigBase")
    @ResponseBody
    public  Object updateDigBase(DigBase digBase){
        Json json = new Json();
        int result = this.digBaseService.updateByPrimaryKeySelective(digBase);
        if (result == 0){
            json.setMsg("更新失败");
            return  json;
        }
        json.setMsg("更新成功");
        return  json;
    }
}
