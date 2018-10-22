package com.yibi.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class websocketTest {
    @Test
    public void testWebsocket() throws InterruptedException {
        JSONObject json = new JSONObject();
        json.put("action", "join");
        JSONObject data = new JSONObject();
        data.put("scene", 350);
        data.put("gear", 1);
        data.put("c1", 0);
        data.put("c2", 8);
        json.put("data", data);
        WebsocketClientUtils.sendTextMessage(json.toJSONString());
        Thread.sleep(1000 * 10);

    }

    @Test
    public void ping() throws InterruptedException {
        JSONObject json = new JSONObject();
        json.put("action", "ping");
        WebsocketClientUtils.sendTextMessage(json.toJSONString());
        Thread.sleep(1000 * 10);
    }

    @Test
    public void marketyb() throws InterruptedException {
        JSONObject json = new JSONObject();
        json.put("action", "join");
        json.put("cmsg_code", 3511);
        JSONObject data = new JSONObject();
        data.put("scene", 3511);
        json.put("data", data);
        WebsocketClientUtils.sendTextMessage(json.toJSONString());
        Thread.sleep(1000 * 10);

    }
    @Test
    public void kyb() throws InterruptedException {
        JSONObject json = new JSONObject();
        json.put("action", "join");
        json.put("cmsg_code", 3521);
        JSONObject data = new JSONObject();
        data.put("scene", 3521);
        data.put("gear", 1);
        data.put("c1", 0);
        data.put("c2", 8);
        json.put("data", data);
        WebsocketClientUtils.sendTextMessage(json.toJSONString());
        Thread.sleep(1000 * 10);

    }

}
