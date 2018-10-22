package com.yibi.orderapi.event;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.WebsocketClientUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.dao.OrderSpotRecordMapper;
import com.yibi.core.dao.OrderTakerMapper;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 行情、k线、交易深度加入缓存
 */
@Slf4j
public class AfterOrderListener {
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private OrderSpotRecordService orderSpotRecordService;
    @Resource
    private RedisTemplate<String, String> redis;
    @Resource
    private OrderSpotRecordMapper orderSpotRecordMapper;
    @Resource
    private OrderTakerMapper orderTakerMapper;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private OrderSpotService orderSpotService;


    @Subscribe
    public void afterOrder(AfterOrderListenerBean event) {
        try {
            Integer orderCoinType = event.getOrderCoin();
            Integer unitCoinType = event.getUnitCoin();
            List<OrderSpotRecord> recordList = event.getRecordList();
            CoinScale coinScale = coinScaleService.queryByCoin(orderCoinType, unitCoinType);

            /*-------------------------------------------redis记录最新成交记录------------------------------------------------*/
            log.info("更新最新成交记录");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            putOrderRecordsToRedis(recordList, orderCoinType, unitCoinType, sdf, coinScale);

            /*-------------------------------------------redis记录交易深度----------------------------------------------------*/
            int maxSize = 100;
            Sysparams param = sysparamsService.getValByKey(SystemParams.MARKET_ORDER_SIZE_MAX);
            if (param != null) {
                maxSize = Integer.parseInt(param.getKeyval());
            }
            log.info("更新交易深度");
            //查询买单挂单总笔数
            Map<Object, Object> orderParams = new HashMap<Object, Object>();
            orderParams.put("ordercointype", orderCoinType);
            orderParams.put("unitcointype", unitCoinType);
            orderParams.put("type", GlobalParams.ORDER_TYPE_BUY);
            orderParams.put("state", GlobalParams.ORDER_STATE_UNTREATED);
            //int countBuy = orderSpotService.selectCount(orderParams);
            //查询卖单挂单总笔数
            orderParams.put("type", GlobalParams.ORDER_TYPE_SALE);
            //int countSale = orderSpotService.selectCount(orderParams);
            final List<?> objects = orderSpotService.queryBuyOrderList(orderCoinType, unitCoinType, maxSize, coinScale);
            List<?> buys = objects;
            orderParams.put("type", GlobalParams.ORDER_TYPE_BUY);
            for (int i = 0; i < buys.size(); i++) {
                Map<String, Object> data = (Map<String, Object>) buys.get(i);
                data.put("num", (i + 1));

                BigDecimal price = new BigDecimal(data.get("price").toString());
                orderParams.put("price", price);
                //int countBuyPrice = orderSpotService.selectCount(orderParams);
                data.put("price", BigDecimalUtils.toStringInZERO(price, coinScale.getOrderamtpricescale()));

                BigDecimal remain = new BigDecimal(data.get("remain").toString());
                data.put("remain", BigDecimalUtils.toStringInZERO(remain, coinScale.getOrderamtamountscale()));
                //data.put("rate", BigDecimalUtils.toString(new BigDecimal((double) countBuyPrice / countBuy * 100), 2));
                data.put("rate", "0");
            }
            List<?> sales = orderSpotService.querySaleOrderList(orderCoinType, unitCoinType, maxSize, coinScale);
            orderParams.put("type", GlobalParams.ORDER_TYPE_SALE);
            for (int i = 0; i < sales.size(); i++) {
                Map<String, Object> data = (Map<String, Object>) sales.get(i);
                data.put("num", (i + 1));

                BigDecimal price = new BigDecimal(data.get("price").toString());
                orderParams.put("price", price);
                //int countSalePrice = orderSpotService.selectCount(orderParams);
                data.put("price", BigDecimalUtils.toStringInZERO(price, coinScale.getOrderamtpricescale()));

                BigDecimal remain = new BigDecimal(data.get("remain").toString());
                data.put("remain", BigDecimalUtils.toStringInZERO(remain, coinScale.getOrderamtamountscale()));
                //data.put("rate", BigDecimalUtils.toString(new BigDecimal((double) countSalePrice / countSale * 100), 2));
                data.put("rate", "0");
            }
            String listKey = String.format(RedisKey.BUY_ORDER_LIST, unitCoinType, orderCoinType);
            RedisUtil.deleteKey(redis, listKey);
            RedisUtil.addListRight(redis, listKey, buys);

            listKey = String.format(RedisKey.SALE_ORDER_LIST, unitCoinType, orderCoinType);
            RedisUtil.deleteKey(redis, listKey);
            RedisUtil.addList(redis, listKey, sales);


            /*-------------------------------------------redis记录最新行情----------------------------------------------------*/

            //获取交易币和计价币详情
            CoinManage orderCoinDetail = coinManageService.queryByCoinType(orderCoinType);
            CoinManage unitCoinDetail = coinManageService.queryByCoinType(unitCoinType);
            //获取当前最新价格和某段时间内的成交量
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date endTime = new Date();
            Date startTime = sdf1.parse(sdf1.format(new Date()));
            BigDecimal sumAmount = orderSpotRecordService.getSumAmount(orderCoinType, unitCoinType, startTime, endTime);
            OrderSpotRecord newRecord = orderSpotRecordService.getNewRecord(unitCoinType, orderCoinType, endTime);
            BigDecimal newPrice = newRecord == null ? BigDecimal.ZERO : newRecord.getPrice();
            //获取当前计价币对人民币的汇率
            BigDecimal cnyRate = getPriceOfCNY(unitCoinType);
            //计算交易币换算成人民币最新价格
            BigDecimal newPriceCNY = newPrice.multiply(cnyRate);
            //获取现货交易最新价格和次新价格，计算涨幅
            BigDecimal chgPrice = new BigDecimal(0);
            if (newRecord != null) {
                //获取零点交易记录
                OrderSpotRecord newerRecord = orderSpotRecordService.getFirstRecord(unitCoinType,orderCoinType,startTime);
                if(newerRecord==null){
                    orderSpotRecordService.getNewRecord(unitCoinType,orderCoinType , startTime);
                }
                if (newerRecord != null)
                    chgPrice = newPrice.subtract(newerRecord.getPrice()).divide(newerRecord.getPrice(), 5, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            }
            Map<String, Object> result = orderSpotRecordService.getHighLowAmount(orderCoinType, unitCoinType, startTime, endTime);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderCoinType", orderCoinType);
            params.put("unitCoinType", unitCoinType);
            params.put("orderCoinCnName", orderCoinDetail.getCnname());
            params.put("orderCoinName", orderCoinDetail.getCoinname());
            params.put("unitCoinName", unitCoinDetail.getCoinname());
            //大小超过10000，sumAmount转换为以“万”为单位，保留小数点后两位
            params.put("sumAmount", BigDecimalUtils.toString(sumAmount, coinScale.getMarkettradenumscale()));
            params.put("newPrice", BigDecimalUtils.toStringInZERO(newPrice, coinScale.getOrderamtpricescale()));
            params.put("newPriceCNY", BigDecimalUtils.toStringInZERO(newPriceCNY, coinScale.getMarketpriceofcnyscale()));
            params.put("chgPrice", chgPrice.setScale(2, RoundingMode.HALF_UP).doubleValue() + "");
            BigDecimal maxPrice = (BigDecimal) result.get("high");
            BigDecimal minPrice = (BigDecimal) result.get("low");
            params.put("high", BigDecimalUtils.toStringInZERO(maxPrice, coinScale.getKlinepricescale()));
            params.put("low", BigDecimalUtils.toStringInZERO(minPrice, coinScale.getKlinepricescale()));
            String redisKey = String.format(RedisKey.MARKET, 1, unitCoinType, orderCoinType);
            RedisUtil.addStringObj(redis, redisKey, params);
            log.info("更新行情信息");
            /*-------------------------------------------redis记录最新价格----------------------------------------------------*/
            redisKey = String.format(RedisKey.LATEST_TRANS_PRICE, unitCoinType, orderCoinType);
            RedisUtil.addString(redis, redisKey, BigDecimalUtils.toString(newPrice, coinScale.getOrderamtpricescale()));
            log.info("更新最新成交价");
            /*-------------------------------------------发送websocket广播----------------------------------------------------*/
            JSONObject json = new JSONObject();
            json.put("action", "order");
            JSONObject data = new JSONObject();
            data.put("c1", unitCoinType);
            data.put("c2", orderCoinType);
            json.put("data", data);
            WebsocketClientUtils.sendTextMessage(json.toJSONString());
        } catch (Exception e) {
            log.error("交易完成保存行情缓存--->失败," + e.getMessage());
            e.printStackTrace();
        }
    }

    public BigDecimal getSpotLatestPrice(Integer orderCoinType, Integer unitCoinType) {
        String key = String.format(RedisKey.LATEST_TRANS_PRICE, unitCoinType, orderCoinType);
        String price = RedisUtil.searchString(redis, key);
        if (StrUtils.isBlank(price)) {
            Map<Object, Object> map = Maps.newHashMap();
            map.put("ordercointype", orderCoinType);
            map.put("unitcointype", unitCoinType);
            map.put("firstResult", 0);
            map.put("maxResult", 1);

            List<OrderSpotRecord> records = orderSpotRecordMapper.selectPaging(map);
            return records == null || records.isEmpty() ? BigDecimal.ZERO : records.get(0).getPrice();
        }
        return new BigDecimal(price);
    }


    public BigDecimal getC2CLatestPrice(Integer coinType) {
        String key = String.format(RedisKey.C2C_PRICE, coinType);
        String price = RedisUtil.searchString(redis, key);

        if (StrUtils.isBlank(price)) {
            Map<Object, Object> map = Maps.newHashMap();
            map.put("cointype", coinType);
            map.put("firstResult", 0);
            map.put("maxResult", 1);
            map.put("state", GlobalParams.C2C_ORDER_STATE_FINISHED);

            List<OrderTaker> records = orderTakerMapper.selectPaging(map);
            return records == null || records.isEmpty() ? BigDecimal.ZERO : records.get(0).getPrice();
        }
        return StrUtils.isBlank(price) ? new BigDecimal(0) : new BigDecimal(price);
    }


    public BigDecimal getPriceOfCNY(Integer coinType) {
        if (coinType == CoinType.KN) {
            return BigDecimal.ONE;
        }
        BigDecimal c2cPrice = getC2CLatestPrice(coinType);
        if (c2cPrice.compareTo(BigDecimal.ZERO) == 1) {
            return c2cPrice;
        }
        return getSpotLatestPrice(coinType, CoinType.KN);
    }

    public void putOrderRecordsToRedis(List<OrderSpotRecord> list, Integer orderCoinType, Integer unitCoinType, SimpleDateFormat sdf, CoinScale coinScale) {
        if (!list.isEmpty()) {
            List<Map<String, Object>> listRecord = new ArrayList<Map<String, Object>>();
            for (OrderSpotRecord record : list) {
                if(record!=null){
                    Map<String, Object> map = new HashMap<String, Object>();
                    //record = orderSpotRecordService.selectByPrimaryKey(record.getId());
                    //log.info("coinScale:{}",coinScale);
                    map.put("amount", BigDecimalUtils.toStringInZERO(record.getAmount(), coinScale.getOrderamtamountscale()));
                        map.put("createTime", sdf.format(record.getCreatetime()));
                    map.put("price", BigDecimalUtils.toStringInZERO(record.getPrice(), coinScale.getOrderamtpricescale()));
                    if (record.getBuyid() > record.getSaleid()) {
                        map.put("orderType", 0);
                    } else {
                        map.put("orderType", 1);
                    }
                    listRecord.add(map);
                }else{
                    log.info("record is null ");
                }

            }
            String redisKey = String.format(RedisKey.ORDER_RECORD_LIST, unitCoinType, orderCoinType);

            RedisUtil.addList(redis, redisKey, listRecord);
            redis.opsForList().trim(redisKey, 0, 20);
        }

    }
}

