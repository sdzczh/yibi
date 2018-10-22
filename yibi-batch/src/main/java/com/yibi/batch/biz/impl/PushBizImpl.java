package com.yibi.batch.biz.impl;

import com.yibi.batch.biz.PushBiz;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.User;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import com.yibi.extern.api.rongcloud.request.RongCloudMsgRequest;
import io.rong.messages.CustomTxtMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
@Slf4j
@Service
@Transactional
public class PushBizImpl implements PushBiz {

    @Autowired
    private UserService userService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private RongCloudMsgRequest msgRequest;
    @Override
    public void start(String str) {
        CustomTxtMessage msg = new CustomTxtMessage(str);
        int page = 0;
        Map map = new HashMap<>();
        map.put("firstResult", page);
        map.put("maxResult", 100);
        List<User> list = userService.selectPaging(map);
        while(list != null && list.size() != 0){
            for (User user : list) {
                if(StrUtils.isBlank(user.getToken())){
                    continue;
                }
                try {
                    msgRequest.sendSystemMassage("系统消息", msg, user.getPhone());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            page++;
            map.put("firstResult", page * 100);
            list = userService.selectPaging(map);
        }
     }
}
