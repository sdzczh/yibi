package com.yibi.web.controller;

import com.yibi.common.model.Json;
import com.yibi.core.entity.User;
import com.yibi.core.service.UserService;
import com.yibi.extern.api.rongcloud.request.RongCloudMsgRequest;
import com.yibi.web.controller.base.BaseController;
import io.rong.messages.CustomTxtMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("systemPush")
public class SystemPushController extends BaseController {
    @Autowired
    private RongCloudMsgRequest msgRequest;
    @Autowired
    private UserService userService;

    @RequestMapping("systemPushContent")
    public String systemPushContent(){
        return "document/systemContent/systemPushContent";
    }

    @RequestMapping("pushAll")
    @ResponseBody
    public Object pushAll(String content){
        Map<Object, Object> map = new HashMap<>();
        Integer page = 0;
        map.put("firstResult", page);
        map.put("maxResult", 100);
        List<User> list = userService.selectPaging(map);
        while (list != null && !list.isEmpty()){
            push(list, content);
            page++;
            map.put("firstResult", page * 100);
            list = userService.selectPaging(map);
        }
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("操作成功");
        return json;
    }

    public void push(List<User> list, String content){
        for(User user : list) {
            CustomTxtMessage msg = new CustomTxtMessage(content);
            try {
                msgRequest.sendSystemMassage("系统消息", msg, user.getPhone());
            } catch (Exception e) {
                log.info("【系统消息】消息推送失败，帐号：【" + user.getToken() + "】");
                e.printStackTrace();
            }
        }
        log.info("【系统消息】消息推送成功】");
    }



}
