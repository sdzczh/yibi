package com.yibi.web.controller;

import com.yibi.common.encrypt.MD5;
import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.entity.User;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("system")
public class SystemParamController extends BaseController {
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private RedisTemplate<String, String> redis;
    @Autowired
    private UserService userService;

    @RequestMapping("/paramListPage")
    public String paramListPage(){
        return "/system/systemParam/paramListPage";
    }

    @RequestMapping("/paramList")
    @ResponseBody
    public  Object paramList(Integer rows,Integer page,String remark){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("firstResult",pageModel.getFirstResult());
        map.put("maxResult",pageModel.getMaxResult());
        if (!StrUtils.isBlank(remark)){
            map.put("remark",remark);
        }
        List<Map<String,Object>> list  = this.sysparamsService.selectSystemParamByCondition(map);
        Integer total = this.sysparamsService.selectSystemParamCountByCondition(map);
        Grid grid = new Grid();
        grid.setTotal(total);
        grid.setRows(list);
        return grid;
    }

    @RequestMapping("/saveOrUpdateSystemParamPage")
    public String saveOrUpdateSystemParamPage(HttpServletRequest request, Integer id){
        if(id != null){
            request.setAttribute("id",id);
        }
        return "/system/systemParam/saveOrUpdateSystemParamPage";
    }

    @RequestMapping("/loadForm")
    @ResponseBody
    public Object loadForm(Integer id){
        return  this.sysparamsService.selectByPrimaryKey(id);
    }
    @RequestMapping("saveOrUpdateSystemParam")
    @ResponseBody
    public Object saveOrUpdateSystemParam(Sysparams sysparams) throws Exception {
        Json json = new Json();
        json.setSuccess(true);
        if(sysparams.getId() != null){//执行更新
            if (sysparams.getKeyname().equals(SystemParams.RECHARGE_ONOFF_TOTAL)){
                json.setMsg("你无权修改充值开关，联系罗杰、杨文磊");
                return json;
            }
            if (sysparams.getKeyname().equals(SystemParams.ORDER_PASSWORD_DEFAULT)){
                Map<Object,Object> map = new HashMap<>();
                map.put("role",GlobalParams.ROLE_TYPE_ROBOT);
                List<User> list = userService.selectAll(map);
                for (User user : list){
                    user.setUserpassword(MD5.getMd5(sysparams.getKeyval()));
                    userService.updateByPrimaryKeySelective(user);
                }
            }
            this.sysparamsService.updateByPrimaryKeySelective(sysparams);
            json.setMsg("更新系统参数成功！");
            //更新的数据更新到缓存中
            RedisUtil.addStringObj(redis,String.format(RedisKey.SYSTEM_PARAM, sysparams.getKeyname()),sysparams.getKeyval());
            return  json;

        } else {//执行插入操作
            this.sysparamsService.insertSelective(sysparams);
            RedisUtil.addStringObj(redis,String.format(RedisKey.SYSTEM_PARAM, sysparams.getKeyname()),sysparams.getKeyval());
            json.setMsg("新增系统参数成功");
            return  json;
        }
    }
    @RequestMapping("/deleteSystemParam")
    @ResponseBody
    public Object deleteSystemParam(Integer id){
        this.sysparamsService.deleteByPrimaryKey(id);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("删除系统参数成功！");
        return  json;
    }
}
