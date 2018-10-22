package com.yibi.web.controller;

import com.yibi.common.encrypt.MD5;
import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.User;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import com.yibi.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private SysparamsService sysparamsService;


    @RequestMapping("/userListPage")
    public String userListPage(){return  "user/userManage/userListPage";}

    @RequestMapping("/getUserList")
    @ResponseBody
    public Object getUserList(Integer page ,Integer rows,String phone,String username){
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("firstResult",pageModel.getFirstResult());
        map.put("maxResult",pageModel.getMaxResult());
        if(!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        if(!StrUtils.isBlank(username)){
            map.put("username",username);
        }
        List<User> list = this.userService.selectPaging(map);
        Integer total = this.userService.selectCount(map);
        Grid grid = new Grid();
        grid.setRows(list);
        grid.setTotal(total);
        return  grid;
    }

    @RequestMapping("freezingUser")
    @ResponseBody
    public Object freezingUser(Integer id){
        Json json = new Json();
       User  user = this.userService.selectByPrimaryKey(id);
       if(user.getState() == GlobalParams.USER_STATE_CANCEL || user.getState() == GlobalParams.USER_STATE_FREEZING){
           json.setMsg("操作的用户不是有效状态，不能冻结");
           json.setSuccess(true);
           return json;
       }
       user.setState(GlobalParams.USER_STATE_FREEZING);
       this.userService.updateByPrimaryKeySelective(user);
       json.setMsg("冻结用户成功");
       json.setSuccess(true);
       return json;
    }
    @RequestMapping("thawUser")
    @ResponseBody
    public Object thawUser(Integer id){
        Json json = new Json();
       User  user = this.userService.selectByPrimaryKey(id);
       if(user.getState() == GlobalParams.USER_STATE_CANCEL || user.getState() == GlobalParams.USER_STATE_VALID){
           json.setMsg("解冻的用户不是冻结状态，不能解冻");
           json.setSuccess(true);
           return json;
       }
       user.setState(GlobalParams.USER_STATE_VALID);
       this.userService.updateByPrimaryKeySelective(user);
       json.setMsg("解冻用户成功");
       json.setSuccess(true);
       return json;
    }

    /**
     * 重置登录密码
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("ResetLoginPwd")
    @ResponseBody
    public Object ResetLoginPwd(Integer id) throws Exception {
        Json json = new Json();
        json.setSuccess(true);
        User user = this.userService.selectByPrimaryKey(id);
        user.setUserpassword(MD5.getMd5(sysparamsService.getValStringByKey(SystemParams.USER_DEFAULT_PASSWORD)));
        int result = this.userService.updateByPrimaryKeySelective(user);
        if (result == 0){
            json.setMsg("更新登录密码失败");
        }
        json.setMsg("更新登录密码成功");
        return json;
    }

}
