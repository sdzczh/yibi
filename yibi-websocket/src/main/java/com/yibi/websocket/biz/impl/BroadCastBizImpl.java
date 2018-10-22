package com.yibi.websocket.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.yibi.websocket.biz.BroadCastBiz;
import com.yibi.websocket.model.ResultObj;
import com.yibi.websocket.model.WebSocketClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Log4j2
@Component
public class BroadCastBizImpl extends BaseBizImpl implements BroadCastBiz {
    @Resource
    private RedisTemplate<String, String> redis;

    @Override
    public void broadCast(JSONObject data, Map<String, WebSocketClient> allSocketClients) {
        Object info = data.get("info");

        int scene = data.getIntValue("scene");
        int gear = data.getIntValue("gear");
        int c1 = data.getIntValue("c1");
        int c2 = data.getIntValue("c2");
        if(gear==1){

        }
        for (WebSocketClient client : allSocketClients.values()) {


            if (client.getC1() == c1 && client.getC2() == c2 &&  scene == client.getScene()) {

                if(gear==1){
                    ResultObj resultObj = new ResultObj();
                    resultObj.setInfo(JSONObject.toJSONString(info));
                    resultObj.setScene(client.getScene());
                    sendMessage(client.getChannel(), resultObj);
                }else if(gear == client.getGear() ) {
                    ResultObj resultObj = new ResultObj();
                    resultObj.setInfo(JSONObject.toJSONString(info));
                    resultObj.setScene(client.getScene());
                    sendMessage(client.getChannel(), resultObj);
                }


            }
        }
    }
}
