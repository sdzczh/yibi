package com.yibi.web.service.impl;

import com.yibi.common.encrypt.MD5;
import com.yibi.common.model.Json;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.User;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import com.yibi.web.service.TaskUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TaskUserSerivceImpl implements TaskUserService {
    @Autowired
    private UserService userService;
    @Autowired
    private SysparamsService sysparamsService;
    @Override
    public Object addTaskUser(String phone) throws Exception {
        Json json = new Json();

        User oldUser = userService.selectByPhone(phone);
        if(oldUser == null){
            json.setMsg("用户账号不存在");
            return json;
        }
        if (oldUser.getState() != 1){
            json.setMsg("用户账号状态非有效状态");
            return json;
        }
        if (oldUser.getState() != 1){
            json.setMsg("用户账号状态,非有效状态");
            return json;
        }
        if (oldUser.getIdstatus() != 1){
            json.setMsg("用户账号必须实名");
            return json;
        }
        if (StrUtils.isBlank(oldUser.getOrderpwd())){
            json.setMsg("用户必须设置交易密码，而且必须和后台的默认交易密码一致。");
            return json;
        }
        if (!oldUser.getOrderpwd().equals(MD5.getMd5(sysparamsService.getValStringByKey(SystemParams.ORDER_PASSWORD_DEFAULT)))){
            json.setMsg(oldUser.getPhone()+":设置的交易密码，而且必须和后台的默认交易密码一致。");
            return json;
        }
        oldUser.setRole(GlobalParams.ROLE_TYPE_ROBOT);//机器人
        int result = userService.updateByPrimaryKeySelective(oldUser);
        if (result == 0){
            json.setMsg("普通用户更新成机器人执行用户失败了");
            return json;
        }
        json.setMsg("普通用户更新成机器人执行用户成功了");
        return json;
    }
}
