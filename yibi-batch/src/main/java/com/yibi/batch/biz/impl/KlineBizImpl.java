package com.yibi.batch.biz.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.batch.biz.KlineBiz;
import com.yibi.common.utils.*;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.entity.OrderManage;
import com.yibi.core.entity.OrderSpotRecord;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.CoinScaleService;
import com.yibi.core.service.OrderManageService;
import com.yibi.core.service.OrderSpotRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class KlineBizImpl implements KlineBiz {
    @Resource
    private RedisTemplate<String, String> redis;
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private OrderSpotRecordService orderSpotRecordService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private CoinManageService coinManageService;
    @Override
    public Long klineJob(Long timeInteval, Long lastTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("onoff", GlobalParams.ON);
        List<OrderManage> listYBOrder = orderManageService.selectAll(params);
        params.clear();
        params.put("okcoinflag", GlobalParams.ON);
        List<OrderManage> listOKOrder = orderManageService.selectAll(params);
        Long thisTime = DateUtils.getKLineTimestamp(timeInteval);
        log.info("当前更新时间:" + sdf.format(new Date(thisTime)));
        if (lastTime == null) {
            log.info("首次更新");
        }else{
            log.info("上次更新时间:" + sdf.format(new Date(lastTime)));
        }
        try {
        String gear = "";
        switch (Long.toString(timeInteval)) {
            case "60000":
                gear = "1";
                break;
            case "300000":
                gear = "2";
                break;
            case "1800000":
                gear = "3";
                break;
            case "3600000":
                gear = "4";
                break;
            case "86400000":
                gear = "5";
                break;
            default:
                gear = "1";
                break;
        }

            for (OrderManage orderManager : listYBOrder) {
                //log.info("OrderManage的值：{}",orderManager);
                String jedisKey = String.format(RedisKey.KLINEYB, gear, orderManager.getUnitcointype(), orderManager.getOrdercointype());
                List<Map<String, Object>> list = initKline(timeInteval, 1, orderManager.getOrdercointype(), orderManager.getUnitcointype(), thisTime, lastTime);
                RedisUtil.addListRight(redis, jedisKey, list);
                //redis中保留300条数据
                long size = RedisUtil.searchListSize(redis, jedisKey);
                if(size > 300){
                    redis.opsForList().trim(jedisKey, size-300, size-1);
                }
                if(list != null && list.size() < 300){
                    JSONObject broadcast = new JSONObject();
                    broadcast.put("action", "broadcast");
                    JSONObject broadcastData = new JSONObject();
                    broadcastData.put("c1", orderManager.getUnitcointype());
                    broadcastData.put("c2", orderManager.getOrdercointype());
                    broadcastData.put("scene", 3521);
                    broadcastData.put("gear", gear);

                    JSONObject json = new JSONObject();
                    json.put("kline",list);

                    broadcastData.put("info", json);
                    broadcast.put("data", broadcastData);

                    WebsocketClientUtils.sendTextMessage(broadcast.toJSONString());
                }

            }
            for (OrderManage orderManager : listOKOrder) {
                String jedisKey = String.format(RedisKey.KLINEOK, gear, orderManager.getUnitcointype(), orderManager.getOrdercointype());
                List<Map<String, Object>> list = initKline(timeInteval, 2, orderManager.getOrdercointype(), orderManager.getUnitcointype(), thisTime, lastTime);
                RedisUtil.addListRight(redis, jedisKey, list);
                //redis中保留300条数据
                long size = RedisUtil.searchListSize(redis, jedisKey);
                if(size > 300){
                    redis.opsForList().trim(jedisKey, size-300, size-1);
                }
                if(list != null && list.size() < 300){
                    JSONObject broadcast = new JSONObject();
                    broadcast.put("action", "broadcast");
                    JSONObject broadcastData = new JSONObject();
                    broadcastData.put("c1", orderManager.getUnitcointype());
                    broadcastData.put("c2", orderManager.getOrdercointype());
                    broadcastData.put("scene", 3522);
                    broadcastData.put("gear", gear);
                    JSONObject json = new JSONObject();
                    json.put("kline",list);

                    broadcastData.put("info", json);
                    broadcast.put("data", broadcastData);
                    WebsocketClientUtils.sendTextMessage(broadcast.toJSONString());
                }
            }
            if("1".equals(gear)) {
                List zline = null;
                for (OrderManage orderManager : listYBOrder) {
                    String jedisKey = String.format(RedisKey.KLINEYB, 1, orderManager.getUnitcointype(), orderManager.getOrdercointype());
                    int size = (int) RedisUtil.searchListSize(redis, jedisKey);
                    zline = RedisUtil.searchList(redis, jedisKey, size - 59 > 0 ? size - 59 : 0, size);
                    JSONObject broadcast = new JSONObject();
                    broadcast.put("action", "broadcast");
                    JSONObject broadcastData = new JSONObject();
                    broadcastData.put("c1", orderManager.getUnitcointype());
                    broadcastData.put("c2", orderManager.getOrdercointype());
                    broadcastData.put("scene", 350);
                    broadcastData.put("gear", gear);

                    JSONArray zlinejson = JSONObject.parseArray(zline.toString());
                    JSONObject json = new JSONObject();
                    json.put("zline", zlinejson);
                    broadcastData.put("info", json);

                    broadcast.put("data", broadcastData);
                    WebsocketClientUtils.sendTextMessage(broadcast.toJSONString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("K线图定时异常"+e);
        }
        return thisTime;
    }

    private List<Map<String, Object>> initKline(Long timeInteval, Integer marketType, Integer orderCoinType, Integer unitCoinType, Long maxTimestamp, Long lastTime) {
        //log.info("coinScale查询条件，orderCoinType：{},unitCoinType:{}",orderCoinType,unitCoinType);
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoinType, unitCoinType);
       // log.info("coinScale的值：{}",coinScale);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (marketType == 1) {
            if(lastTime == null){
                for (int i = 300; i > 0; i--) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    //获取 本节点取数据的时间上下限
                    Date startTime = new Date(maxTimestamp - i * timeInteval);
                    Date endTime = new Date(maxTimestamp - (i - 1) * timeInteval);
                    OrderSpotRecord startRecord = orderSpotRecordService.getNewRecord(unitCoinType, orderCoinType, startTime);
                    OrderSpotRecord endRecord = orderSpotRecordService.getNewRecord(unitCoinType, orderCoinType, endTime);
                    BigDecimal openPrice = startRecord == null ? BigDecimal.ZERO : startRecord.getPrice();//开盘价
                    BigDecimal closePrice = endRecord == null ? BigDecimal.ZERO : endRecord.getPrice();//收盘价
                    Map<String, Object> queryResultMap = orderSpotRecordService.getHighLowAmount(orderCoinType, unitCoinType, startTime, endTime);
                    BigDecimal maxPrice = null;//最高价
                    BigDecimal minPrice = null;//最低价
                    BigDecimal amount = null;//成交量
                    if (queryResultMap != null) {
                        maxPrice = (BigDecimal) queryResultMap.get("high");
                        minPrice = (BigDecimal) queryResultMap.get("low");
                        amount = (BigDecimal) queryResultMap.get("amount");
                    }
                    //如果这段时间没有交易成交，最高价和最低价都取这段时间之前的最后成交价格
                    if (maxPrice == null||maxPrice.compareTo(BigDecimal.ZERO)<1||minPrice.compareTo(BigDecimal.ZERO)<1) maxPrice = minPrice = openPrice;

                    Long timestamp = maxTimestamp - (i - 1) * timeInteval;
                    params.put("timestamp", timestamp);

                    params.put("openPrice", BigDecimalUtils.toString(openPrice, coinScale.getKlinepricescale()));
                    params.put("closePrice", BigDecimalUtils.toString(closePrice, coinScale.getKlinepricescale()));
                    params.put("maxPrice", BigDecimalUtils.toString(maxPrice, coinScale.getKlinepricescale()));
                    params.put("minPrice", BigDecimalUtils.toString(minPrice, coinScale.getKlinepricescale()));
                    params.put("amount", BigDecimalUtils.toString(amount, coinScale.getOrderamtamountscale()));
                    params.put("marketType", 1);
                    params.put("timeInteval", timeInteval);
                    list.add(params);
                }
            }else{//获取某时间节点之后的数据
                while(lastTime < maxTimestamp){
                    Map<String, Object> params = new HashMap<String, Object>();
                    Date startTime = new Date(lastTime);
                    Date endTime = new Date(lastTime + timeInteval);
                    OrderSpotRecord startRecord = orderSpotRecordService.getNewRecord(unitCoinType, orderCoinType, startTime);
                    OrderSpotRecord endRecord = orderSpotRecordService.getNewRecord(unitCoinType, orderCoinType, endTime);
                    BigDecimal openPrice = startRecord == null ? BigDecimal.ZERO : startRecord.getPrice();//开盘价
                    BigDecimal closePrice = endRecord == null ? BigDecimal.ZERO : endRecord.getPrice();//收盘价
                    Map<String, Object> queryResultMap = orderSpotRecordService.getHighLowAmount(orderCoinType, unitCoinType, startTime, endTime);
                    BigDecimal maxPrice = null;//最高价
                    BigDecimal minPrice = null;//最低价
                    BigDecimal amount = null;//成交量
                    if (queryResultMap != null) {
                        maxPrice = (BigDecimal) queryResultMap.get("high");
                        minPrice = (BigDecimal) queryResultMap.get("low");
                        amount = (BigDecimal) queryResultMap.get("amount");
                    }
                    //如果这段时间没有交易成交，最高价和最低价都取这段时间之前的最后成交价格
                    if (maxPrice == null ||maxPrice.compareTo(BigDecimal.ZERO)<1||minPrice.compareTo(BigDecimal.ZERO)<1) maxPrice = minPrice = openPrice;
                    Long timestamp = lastTime + timeInteval;
                    params.put("timestamp", timestamp);
                    params.put("openPrice", BigDecimalUtils.toString(openPrice, coinScale.getKlinepricescale()));
                    params.put("closePrice", BigDecimalUtils.toString(closePrice, coinScale.getKlinepricescale()));
                    params.put("maxPrice", BigDecimalUtils.toString(maxPrice, coinScale.getKlinepricescale()));
                    params.put("minPrice", BigDecimalUtils.toString(minPrice, coinScale.getKlinepricescale()));
                    params.put("amount", BigDecimalUtils.toString(amount, coinScale.getOrderamtamountscale()));
                    params.put("marketType", 1);
                    params.put("timeInteval", timeInteval);
                    list.add(params);
                    lastTime = lastTime + timeInteval;
                }
            }

        }
        //OKCOIN
        if (marketType == 2) {
            //初始化字符串格式化参数
            CoinManage coinManage = coinManageService.queryByCoinType(orderCoinType);
            String orderCoinName = coinManage.getCoinname();
            String type = "";
            String since = "";
            String size = "";
            switch (Long.toString(timeInteval)) {
                case "60000":
                    type = "1min";
                    break;
                case "300000":
                    type = "5min";
                    break;
                case "1800000":
                    type = "30min";
                    break;
                case "3600000":
                    type = "1hour";
                    break;
                case "86400000":
                    type = "1day";
                    break;
                default:
                    type = "1min";
            }
            if(lastTime == null){//初始化数据
                size = "300";
            }else{
                if(lastTime > maxTimestamp){
                    return list;
                }
                since = Long.toString(lastTime + 1);
            }
            try {//调用okcoin获取kline数据接口
                String result = HTTP.get(String.format("https://www.okcoin.com/api/v1/kline.do?symbol=%s_usd&type=%s&size=%s&since=%s", orderCoinName, type, size, since), null);
                JSONArray kLineArr = JSONArray.parseArray(result);
                for (Object object : kLineArr) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    JSONArray kLineNode = (JSONArray) object;
                    params.put("timestamp", kLineNode.get(0));
                    params.put("openPrice", kLineNode.get(1));
                    params.put("closePrice", kLineNode.get(4));
                    params.put("maxPrice", kLineNode.get(2));
                    params.put("minPrice", kLineNode.get(3));
                    params.put("amount", kLineNode.get(5));
                    params.put("marketType", 2);
                    params.put("timeInteval", timeInteval);
                    list.add(params);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
