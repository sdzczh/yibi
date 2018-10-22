package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.core.entity.AppVersion;
import com.yibi.core.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("versionManage")
@Controller
public class VersionController {
    @Autowired
    private AppVersionService appVersionService;

    @RequestMapping("versionManagePage")
    public String versionManage(){
        return "system/version/versionManage";
    }
    @RequestMapping("versionManageList")
    @ResponseBody
    public Object versionManageList(Integer page,Integer rows){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        Grid grid = new Grid();
        grid.setTotal(this.appVersionService.selectCount(map));
        grid.setRows(this.appVersionService.selectPaging(map));
        return grid;
    }
    @RequestMapping("addVersionManagePage")
    public String addVersionManagePage(){
        return "system/version/addVersionManage";
    }

    @RequestMapping("addVersionPage")
    @ResponseBody
    public Object addVersionPage(AppVersion appVersion){//暂时不放入缓存中
        Json json = new Json();
        json.setSuccess(true);
        int result = this.appVersionService.insert(appVersion);
        if (result != 0){
            if (appVersion.getPhonetype() ==1){
                json.setMsg("新增Android成功");
            }else {
                json.setMsg("新增IOS成功");
            }
        }
        return json;
    }

    @RequestMapping("updateVersionManagePage")
    public String updateVersionManagePage(Integer id, HttpServletRequest request){
        request.setAttribute("versionId",id);
        return "system/version/updateVersionManagePage";
    }

    @RequestMapping("loadVersionForm")
    @ResponseBody
    public Object loadVersionForm(Integer id){
        return this.appVersionService.selectByPrimaryKey(id);
    }
    @RequestMapping("updateVersion")
    @ResponseBody
    public Object updateVersion(AppVersion appVersion){
        Json json = new Json();
        json.setSuccess(true);
        int result = this.appVersionService.updateByPrimaryKeySelective(appVersion);
        if (result == 0){
            json.setMsg("更新失败");
            return json;
        }
        json.setMsg("更新成功");
        return json;
    }
    @RequestMapping("deleteVersion")
    @ResponseBody
    public Object deleteVersion(Integer id){
        Json json = new Json();
        json.setSuccess(true);
        int result = this.appVersionService.deleteByPrimaryKey(id);
        if (result == 0){
            json.setMsg("删除失败");
            return json;
        }
        json.setMsg("删除成功");
        return json;
    }

}
