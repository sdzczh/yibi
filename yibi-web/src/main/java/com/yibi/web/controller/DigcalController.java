package com.yibi.web.controller;

import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.DigcalRecord;
import com.yibi.core.entity.UserDiginfo;
import com.yibi.core.service.DigcalRecordService;
import com.yibi.core.service.UserDiginfoService;
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
@RequestMapping("user")
public class DigcalController extends BaseController {
    @Autowired
    private UserDiginfoService userDiginfoService;
    @Autowired
    private DigcalRecordService digcalRecordService;

    @RequestMapping("digcalListPage")
    public String digcalListPage(){
        return "user/digcalInfo/digcalInfoListPage";
    }
    @RequestMapping("getDigcalInfoList")
    @ResponseBody
    public Object getDigcalInfoList(Integer rows,Integer page,String phone,String username,Integer minDigCalcul,Integer maxDigCalcul){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        if(!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        if(!StrUtils.isBlank(username)){
            map.put("username",username);
        }
        if(maxDigCalcul != null){
            map.put("maxDigCalcul",maxDigCalcul);
        }
        if(minDigCalcul != null){
            map.put("minDigCalcul",minDigCalcul);
        }
        List<Map<String,Object>> list = this.userDiginfoService.selectDigInfoAndPhone(map);
        Integer total = this.userDiginfoService.selectDigInfoCount(map);
        Grid grid = new Grid();
        grid.setTotal(total);
        grid.setRows(list);
        return grid;
    }
    @RequestMapping("updateDigcalPage")
    public String updateDigcalPage(HttpServletRequest request,Integer id){
        request.setAttribute("id",id);
        return "/user/digcalInfo/updateDigcal";
    }
    @RequestMapping("updateDigcal")
    @ResponseBody
    public Object  updateDigcal(Integer id,Integer amount){
        Json json = new Json();
        if (amount == null){
            json.setSuccess(true);
            json.setMsg("请输入正确的魂力！");
            return json;
        }
        UserDiginfo userDiginfo = this.userDiginfoService.selectByPrimaryKey(id);
        //添加魂力记录
        DigcalRecord digcalRecord = new DigcalRecord();
        digcalRecord.setName("后台充值魂力");
        digcalRecord.setDigcalcul(amount);
        digcalRecord.setType(18);
        digcalRecord.setAllcalculforce(userDiginfo.getDigcalcul()+amount);
        digcalRecord.setUserid(userDiginfo.getUserid());
        this.digcalRecordService.insertSelective(digcalRecord);
        //更新魂力
        userDiginfo.setDigcalcul(userDiginfo.getDigcalcul()+amount);
        this.userDiginfoService.updateByPrimaryKeySelective(userDiginfo);

        json.setSuccess(true);
        json.setMsg("更新魂力成功！");
        return json;
    }

}
