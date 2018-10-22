package com.yibi.globalmarket.netty.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.WebsocketClientUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.service.BaseService;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.CoinScaleService;
import com.yibi.globalmarket.enums.CoinType;
import com.yibi.globalmarket.netty.WebSocketService;
import com.yibi.globalmarket.variables.WebsocketConstants;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订阅信息处理类需要实现WebSocketService接口
 *
 * @author okcoin
 */
@Service
@Transactional
@Log4j2
public class OKCoinServiceImpl implements WebSocketService {
    @Resource(name = "baseService")
    private BaseService baseService;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Resource
    private RedisTemplate<String, String> redis;

    static {
        //ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //redis = (RedisTemplate<String, Object>) appCtx.getBean("redis");
    }

    @Override
    public void onReceive(String msg) {
        try {
            Object json = JSON.parse(msg);
            if (json instanceof JSONObject) {
                log.info("收到okcoin服务器数据jsonObject：" + ((JSONObject)json).toJSONString());
            }
            if (json instanceof JSONArray) {
                JSONArray result = (JSONArray) json;
                JSONObject resultObj = result.getJSONObject(0);
                String channel = resultObj.getString("channel");
                if (channel.contains("ok_sub_spot")) {
                    log.info("收到okcoin服务器数据最新行情变化：" + resultObj.toJSONString());
                    String[] strArr = channel.split("_");
                    String orderCoinName = strArr[3].toUpperCase();
                    Integer orderCoinType = CoinType.getCode(orderCoinName);
                    Integer unitCoinType = CoinType.USDT.code();
                    CoinScale coinScale = coinScaleService.queryByCoin(orderCoinType, unitCoinType);
                    CoinManage orderCoinDetail = coinManageService.queryByCoinType(orderCoinType);
                    CoinManage unitCoinDetail = coinManageService.queryByCoinType(unitCoinType);
                    JSONObject data = resultObj.getJSONObject("data");
                    BigDecimal amount = data.getBigDecimal("vol");
                    BigDecimal price = data.getBigDecimal("last");
                    Long timestamp = data.getLong("timestamp");
                    BigDecimal high = data.getBigDecimal("high");
                    BigDecimal low = data.getBigDecimal("low");
                    BigDecimal open = data.getBigDecimal("open");
                    if (price == null) price = new BigDecimal(0);
                    //获取当前计价币对人民币的汇率
                    BigDecimal cnyRate = baseService.getPriceOfCNY(unitCoinType);
                    //计算交易币换算成人民币最新价格
                    BigDecimal newPriceCNY = price.multiply(cnyRate);
                    //计算涨跌幅
                    BigDecimal chgPrice = price.subtract(open).divide(open, 5, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("orderCoinType", orderCoinType);
                    params.put("unitCoinType", unitCoinType);
                    params.put("orderCoinCnName", orderCoinDetail.getCnname());
                    params.put("orderCoinName", orderCoinDetail.getCoinname());
                    params.put("unitCoinName", unitCoinDetail.getCoinname());
                    //大小超过10000，sumAmount转换为以“万”为单位，保留小数点后两位
                    params.put("sumAmount", BigDecimalUtils.toString(amount, coinScale.getMarkettradenumscale()));
                    params.put("newPrice", BigDecimalUtils.toString(price, coinScale.getOrderamtpricescale()));
                    params.put("newPriceCNY", BigDecimalUtils.toString(newPriceCNY, coinScale.getMarketpriceofcnyscale()));
                    params.put("chgPrice", chgPrice.setScale(2, RoundingMode.HALF_UP).doubleValue() + "");
                    params.put("high", BigDecimalUtils.toString(high, coinScale.getKlinepricescale()));
                    params.put("low", BigDecimalUtils.toString(low, coinScale.getKlinepricescale()));
                    String redisKey = String.format(RedisKey.MARKET, 2, unitCoinType, orderCoinType);
                    RedisUtil.addStringObj(redis, redisKey, params);
                    /*----------------------------------------发送主流行情广播-----------------------------------------------------------*/
                    JSONObject broadcast = new JSONObject();
                    broadcast.put("action", "broadcast");
                    JSONObject broadcastData = new JSONObject();
                    broadcastData.put("scene", 3512);
                    broadcastData.put("info", params);
                    broadcast.put("data", broadcastData);
                    WebsocketClientUtils.sendTextMessage(broadcast.toJSONString());
                }
                if (channel.equals("addChannel")) {
                    log.info("okcoin行情数据订阅成功:" + resultObj.toJSONString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
