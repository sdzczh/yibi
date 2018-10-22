package com.yibi.websocket.biz;

import com.alibaba.fastjson.JSONObject;
import com.yibi.websocket.model.WebSocketClient;

import java.util.Map;

public interface OrderBiz {
    void orderBroadcast(JSONObject data, Map<String, WebSocketClient> allSocketClients);
}
