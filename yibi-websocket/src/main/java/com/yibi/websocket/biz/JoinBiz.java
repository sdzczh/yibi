package com.yibi.websocket.biz;

import com.alibaba.fastjson.JSONObject;
import com.yibi.websocket.model.ResultObj;
import com.yibi.websocket.model.WebSocketClient;
import io.netty.channel.Channel;

import java.util.Map;

public interface JoinBiz {
    void join(Channel channel, JSONObject data, ResultObj resultObj, Map<String, WebSocketClient> allSocketClients);
}
