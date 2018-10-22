package com.yibi.web.controller;


import com.yibi.common.model.Json;
import com.yibi.web.service.TaskUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("taskUser")
@Controller
public class TaskUserController {
    @Autowired
    private TaskUserService taskUserService;

    @RequestMapping("addTaskUserPage")
    public String addTaskUserPage(){
        return "robot/addExecuteUser";
    }
    @RequestMapping("addTaskUser")
    @ResponseBody
    public Object addTaskUser(String phone){
        Json json = new Json();
        try {
            return taskUserService.addTaskUser(phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.setMsg("添加执行用户失败");
        return json;

    }

}
