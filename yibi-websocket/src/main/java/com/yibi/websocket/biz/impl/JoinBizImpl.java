package com.yibi.websocket.biz.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.OrderManage;
import com.yibi.core.service.OrderManageService;
import com.yibi.websocket.biz.JoinBiz;
import com.yibi.websocket.enums.EnumScene;
import com.yibi.websocket.model.ResultCode;
import com.yibi.websocket.model.ResultObj;
import com.yibi.websocket.model.WebSocketClient;
import io.netty.channel.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
@Log4j2
public class JoinBizImpl extends BaseBizImpl implements JoinBiz {
    @Resource
    private RedisTemplate<String, String> redis;
    @Autowired
    private OrderManageService orderManageService;


    @Override
    public void join(Channel channel, JSONObject data, ResultObj resultObj, Map<String, WebSocketClient> allSocketClients) {
        int scene = data.getIntValue("scene");
        int gear = data.getIntValue("gear");
        int c1 = data.getIntValue("c1");
        int c2 = data.getIntValue("c2");
        resultObj.setScene(scene);
        if (scene == 0) {
            resultObj.setMsg(ResultCode.TYPE_ERROR_PARAMS.message());
            resultObj.setCode(ResultCode.TYPE_ERROR_PARAMS.code());
            sendMessage(channel, resultObj);
            return;
        }
        String comingIp = channel.remoteAddress().toString();
        WebSocketClient wsc = allSocketClients.get(comingIp);
        if (wsc == null) {
            wsc = new WebSocketClient(comingIp, scene, gear, c1, c2);
            wsc.setChannel(channel);
            wsc.setScene(scene);
            wsc.setGear(gear);
            wsc.setC1(c1);
            wsc.setC2(c2);
            allSocketClients.put(comingIp, wsc);
        } else {
            wsc.setChannel(channel);
            wsc.setScene(scene);
            wsc.setGear(gear);
            wsc.setC1(c1);
            wsc.setC2(c2);
        }
        resultObj.setCode(ResultCode.TYPE_SUCCESS_JOIN.code());
        resultObj.setMsg(ResultCode.TYPE_SUCCESS_JOIN.message());
        sendMessage(channel, resultObj);
        // 处理返回数据
        if (scene == EnumScene.SCENE_ORDER.getScene()) {// 现货350
            dealSceneOrder(data, channel, resultObj);
        } else if (scene == EnumScene.SCENE_MARKET_YIBI.getScene() || scene == EnumScene.SCENE_MARKET_ZULIU.getScene()) {// 行情
            dealSceneMarket(data, channel, resultObj);
        } else if (scene == EnumScene.SCENE_KLINE_YIBI.getScene() || scene == EnumScene.SCENE_KLINE_ZULIU.getScene()) {// K线图
            dealSceneKline(data, channel, resultObj);
        } else {
            // 什么都不做
            resultObj.setCode(ResultCode.TYPE_ERROR_PARAMS.code());
            resultObj.setMsg(ResultCode.TYPE_ERROR_PARAMS.message());
            sendMessage(channel, resultObj);
            return;
        }
    }

    //处理初始化现货交易场景
    public void dealSceneOrder(JSONObject data, Channel incoming, ResultObj resultObj) {
        String c1 = data.getString("c1");//计价币
        String c2 = data.getString("c2");//交易币
        int gear = data.getIntValue("gear");
        int scene = data.getIntValue("scene");
        int count = 10;// 默认10挡
        if (gear != 0)
            count = gear;
        List saleslist = null;
        List buyslist = null;
        String price = "";
        List orderRecordList = null;
        List zline = null;

        try {
            String rediskey = String.format(RedisKey.BUY_ORDER_LIST, c1, c2);
            buyslist = RedisUtil.searchList(redis, rediskey, 0, count - 1);
            rediskey = String.format(RedisKey.SALE_ORDER_LIST, c1, c2);
            int size = (int)RedisUtil.searchListSize(redis, rediskey);
            saleslist = RedisUtil.searchList(redis, rediskey, size - count + 1 > 0 ? size - count + 1 : 0, size);
            rediskey = String.format(RedisKey.LATEST_TRANS_PRICE, c1, c2);
            price = RedisUtil.searchString(redis, rediskey);
            rediskey = String.format(RedisKey.ORDER_RECORD_LIST, c1, c2);
            orderRecordList = RedisUtil.searchList(redis, rediskey, 0, 19);
            rediskey = String.format(RedisKey.KLINEYB,1,c1,c2);
            int zlineSize = (int)RedisUtil.searchListSize(redis, rediskey);
            zline = RedisUtil.searchList(redis, rediskey, zlineSize - 59 <= 0 ? 0 :zlineSize - 59, zlineSize);
            JSONArray buyjson = JSONObject.parseArray(buyslist.toString());
            JSONArray zlinejson = JSONObject.parseArray(zline.toString());
            JSONArray salejson = JSONObject.parseArray(saleslist.toString());
            JSONArray recordjson = JSONObject.parseArray(orderRecordList.toString());
            JSONObject json = new JSONObject();
            json.put("buys", buyjson);
            json.put("sales", salejson);
            json.put("price", price);
            json.put("records", recordjson);
            json.put("zline", zlinejson);
            log.info("发送现货数据包:" + json.toString());
            resultObj.setInfo(json.toJSONString());
            sendMessage(incoming, resultObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化行情数据
     */
    public void dealSceneMarket(JSONObject data, Channel incoming, ResultObj resultObj) {
        int scene = data.getIntValue("scene");
        Map<Object, Object> params = new HashMap<Object, Object>();
        int marketType = 1;
        params.put("onoff", GlobalParams.ON);
        if (scene == EnumScene.SCENE_MARKET_ZULIU.getScene()) {
            params.clear();
            params.put("okcoinflag", GlobalParams.ON);
            marketType = 2;
        }
        try {
            List<OrderManage> listOM = orderManageService.selectAllOrderBySeque(params);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (OrderManage orderManage : listOM) {
                Integer unitCoin = orderManage.getUnitcointype();
                Integer orderCoin = orderManage.getOrdercointype();
                String redisKey = String.format(RedisKey.MARKET, marketType, unitCoin, orderCoin);
                String redisVal = RedisUtil.searchString(redis, redisKey);
                if (redisVal != null && !redisVal.equals("")) {
                    Map<String, Object> jsonMap = JSON.parseObject(redisVal, Map.class);
                    list.add(jsonMap);
                }
            }
            resultObj.setInfo(JSONArray.toJSONString(list));
            sendMessage(incoming, resultObj);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dealSceneKline(JSONObject data, Channel incoming, ResultObj resultObj) {
        int scene = data.getIntValue("scene");
        Integer c1 = data.getIntValue("c1");
        Integer c2 = data.getIntValue("c2");
        Integer gear = data.getIntValue("gear");
        int marketType = 0;
        try {
            String redisKey = "";
            if (scene == EnumScene.SCENE_KLINE_YIBI.getScene()) { //一币K图
                marketType = 1;
                redisKey = String.format(RedisKey.KLINEYB, gear, c1, c2);
            }
            if (scene == EnumScene.SCENE_KLINE_ZULIU.getScene()) {//主流k图
                marketType = 2;
                redisKey = String.format(RedisKey.KLINEOK, gear, c1, c2);
            }
            List klineList = RedisUtil.searchList(redis, redisKey, 0, 299);
            JSONArray jsonArray = JSONObject.parseArray(klineList.toString());
            JSONObject json = new JSONObject();
            json.put("kline", jsonArray);
            redisKey = String.format(RedisKey.ORDER_RECORD_LIST, c1, c2);
            List orderRecordList = RedisUtil.searchList(redis, redisKey, 0, 19);
            JSONArray recordJson = JSONObject.parseArray(orderRecordList.toString());
            json.put("records", recordJson);
            redisKey = String.format(RedisKey.MARKET, marketType, c1, c2);
            String market = RedisUtil.searchString(redis, redisKey);
            Map<String, Object> marketMap = JSON.parseObject(market, Map.class);
            json.put("market", marketMap);
            resultObj.setInfo(json.toJSONString());
            log.info("发送K线初始化信息数据包:" + json.toString());
            sendMessage(incoming, resultObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
