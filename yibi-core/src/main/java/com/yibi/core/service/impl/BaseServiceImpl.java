package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.dao.OrderSpotRecordMapper;
import com.yibi.core.dao.OrderTakerMapper;
import com.yibi.core.entity.OrderSpotRecord;
import com.yibi.core.entity.OrderTaker;
import com.yibi.core.service.BaseService;
import com.yibi.core.service.SysparamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/13 0013.
 */
@Service("baseService")
public class BaseServiceImpl implements BaseService {

    @Resource
    private RedisTemplate<String,String> redis;
    @Resource
    private OrderSpotRecordMapper orderSpotRecordMapper;

    @Resource
    private OrderTakerMapper orderTakerMapper;

    @Autowired
    private SysparamsService sysparamsService;

    @Override
    public BigDecimal getSpotLatestPrice(Integer orderCoinType, Integer unitCoinType) {
        String key = String.format(RedisKey.LATEST_TRANS_PRICE, unitCoinType,orderCoinType);
        String price = RedisUtil.searchString(redis, key);
        if(StrUtils.isBlank(price)){
            Map<Object,Object> map = Maps.newHashMap();
            map.put("ordercointype",orderCoinType);
            map.put("unitcointype",unitCoinType);
            map.put("firstResult",0);
            map.put("maxResult",1);

            List<OrderSpotRecord> records = orderSpotRecordMapper.selectPaging(map);
            return records==null||records.isEmpty()?BigDecimal.ZERO:records.get(0).getPrice();
        }
        return new BigDecimal(price);
    }

    @Override
    public BigDecimal getUsdtPrice() {
        String result = sysparamsService.getValStringByKey(SystemParams.ROBOT_USDT_PRICE);
        if (!StrUtils.isBlank(result)){
            return BigDecimal.valueOf(Double.valueOf(result));
        }
        return null;
    }

    @Override
    public BigDecimal getC2CLatestPrice(Integer coinType) {
        String key = String.format(RedisKey.C2C_PRICE, coinType);
        String price = RedisUtil.searchString(redis, key);

        if(StrUtils.isBlank(price)){
            Map<Object,Object> map = Maps.newHashMap();
            map.put("cointype",coinType);
            map.put("firstResult",0);
            map.put("maxResult",1);
            map.put("state", GlobalParams.C2C_ORDER_STATE_FINISHED);

            List<OrderTaker> records = orderTakerMapper.selectPaging(map);
            return records==null||records.isEmpty()?BigDecimal.ZERO:records.get(0).getPrice();
        }
        return StrUtils.isBlank(price)? new BigDecimal(0):new BigDecimal(price);
    }

    @Override
    public BigDecimal getPriceOfCNY(Integer coinType) {
        if(coinType == CoinType.KN){
            return BigDecimal.ONE;
        }
        BigDecimal c2cPrice = getC2CLatestPrice(coinType);
        if(c2cPrice.compareTo(BigDecimal.ZERO) == 1){
            return c2cPrice;
        }
        return getSpotLatestPrice(coinType,CoinType.KN);
    }
}
