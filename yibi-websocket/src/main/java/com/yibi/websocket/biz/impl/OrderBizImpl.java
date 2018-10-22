package com.yibi.websocket.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.variables.RedisKey;
import com.yibi.websocket.biz.OrderBiz;
import com.yibi.websocket.enums.EnumScene;
import com.yibi.websocket.model.ResultObj;
import com.yibi.websocket.model.WebSocketClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Component
public class OrderBizImpl extends BaseBizImpl implements OrderBiz {
    @Resource
    private RedisTemplate<String, String> redis;

    @Override
    public void orderBroadcast(JSONObject data, Map<String, WebSocketClient> allSocketClients) {
        Integer c1 = data.getIntValue("c1");
        Integer c2 = data.getIntValue("c2");
        /*--------------------------------------现货交易场景数据---------------------------------------------------------*/
        String rediskey = String.format(RedisKey.BUY_ORDER_LIST, c1, c2);
        List buyslist1 = RedisUtil.searchList(redis, rediskey, 0, 9);
        List buyslist2 = RedisUtil.searchList(redis, rediskey, 0, 49);
        rediskey = String.format(RedisKey.SALE_ORDER_LIST, c1, c2);
        int size = (int)RedisUtil.searchListSize(redis, rediskey);
        List saleslist1 = RedisUtil.searchList(redis, rediskey, size - 9 > 0 ? size - 9 : 0, size);
        List saleslist2 = RedisUtil.searchList(redis, rediskey, size - 49 > 0 ? size - 49 : 0, size);
        rediskey = String.format(RedisKey.LATEST_TRANS_PRICE, c1, c2);
        String price = RedisUtil.searchString(redis, rediskey);
        rediskey = String.format(RedisKey.ORDER_RECORD_LIST, c1, c2);
        List orderRecordList = RedisUtil.searchList(redis, rediskey, 0, 19);
        JSONArray buyjson1 = JSONObject.parseArray(buyslist1.toString());
        JSONArray salejson1 = JSONObject.parseArray(saleslist1.toString());
        JSONArray buyjson2 = JSONObject.parseArray(buyslist2.toString());
        JSONArray salejson2 = JSONObject.parseArray(saleslist2.toString());
        JSONArray recordjson = JSONObject.parseArray(orderRecordList.toString());
        JSONObject json1 = new JSONObject();
        json1.put("buys", buyjson1);
        json1.put("sales", salejson1);
        json1.put("price", price);
        json1.put("records", recordjson);
        JSONObject json2 = new JSONObject();
        json2.put("buys", buyjson2);
        json2.put("sales", salejson2);
        json2.put("price", price);
        json2.put("records", recordjson);
        ResultObj order1 = new ResultObj();
        order1.setInfo(json1.toJSONString());
        ResultObj order2 = new ResultObj();
        order2.setInfo(json2.toJSONString());
        /*--------------------------------------一币行情场景数据---------------------------------------------------------*/
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        rediskey = String.format(RedisKey.MARKET, 1, c1, c2);
        String marketVal = RedisUtil.searchString(redis, rediskey);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (marketVal != null) {
            jsonMap = JSON.parseObject(marketVal, Map.class);
        }
        list.add(jsonMap);
        ResultObj market = new ResultObj();
        market.setInfo(JSONArray.toJSONString(list));
        /*--------------------------------------一币K线场景数据---------------------------------------------------------*/
        JSONObject json = new JSONObject();
        json.put("records", recordjson);
        json.put("market", jsonMap);
        ResultObj kline = new ResultObj();
        kline.setInfo(JSONArray.toJSONString(json));
        /*--------------------------------------遍历所有长链接---------------------------------------------------------*/
        for (WebSocketClient client : allSocketClients.values()) {
            if (client.getC1() == c1 && client.getC2() == c2) {
                //客户端在现货交易场景
                if (client.getScene() == EnumScene.SCENE_ORDER.getScene()) {
                    if (client.getGear() == 10) {
                        order1.setScene(client.getScene());
                        sendMessage(client.getChannel(), order1);
                    }
                    if (client.getGear() == 50) {
                        order2.setScene(client.getScene());
                        sendMessage(client.getChannel(), order2);
                    }
                }
                //客户端在一币行情场景
                if (client.getScene() == EnumScene.SCENE_MARKET_YIBI.getScene()) {
                    market.setScene(client.getScene());
                    sendMessage(client.getChannel(), market);
                }
                //客户端在一币k线场景
                if (client.getScene() == EnumScene.SCENE_KLINE_YIBI.getScene()) {
                    kline.setScene(client.getScene());
                    sendMessage(client.getChannel(), kline);
                }
            }
        }
    }
}
