package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.common.utils.RedisUtil;
import com.yibi.core.dao.CoinScaleMapper;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.service.CoinScaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:币种小数位数及最小值管理 coin_scale
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("coinScaleService")
public class CoinScaleServiceImpl implements CoinScaleService {
    @Resource
    private CoinScaleMapper coinScaleMapper;

    @Resource
    private RedisTemplate<String, String> redis;

    private static final Logger logger = LoggerFactory.getLogger(CoinScaleServiceImpl.class);

    @Override
    public int insert(CoinScale record) {
        RedisUtil.addHashObject(redis,"CoinScale",record.getUnitcointype()+":"+record.getOrdercointype(),record);
        return this.coinScaleMapper.insert(record);
    }

    @Override
    public int insertSelective(CoinScale record) {
        return this.coinScaleMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(CoinScale record) {
        RedisUtil.addHashObject(redis,"CoinScale",record.getUnitcointype()+":"+record.getOrdercointype(),record);
        return this.coinScaleMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(CoinScale record) {
        return this.coinScaleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.coinScaleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CoinScale selectByPrimaryKey(Integer id) {
        return this.coinScaleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CoinScale> selectAll(Map<Object, Object> param) {
        return this.coinScaleMapper.selectAll(param);
    }

    @Override
    public List<CoinScale> selectPaging(Map<Object, Object> param) {
        return this.coinScaleMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.coinScaleMapper.selectCount(param);
    }

    @Override
    public CoinScale queryByCoin( Integer orderCoinType,Integer unitCoinType) {
        CoinScale coinScale = RedisUtil.searchHashObject(redis,"CoinScale",unitCoinType+":"+orderCoinType,CoinScale.class);
        if(coinScale ==null){
            Map<Object,Object> map = Maps.newHashMap();
            map.put("ordercointype",orderCoinType);
            map.put("unitcointype",unitCoinType);
            List<CoinScale> coinScales = selectAll(map);
            CoinScale coinScaleData =  coinScales==null||coinScales.isEmpty()?null:coinScales.get(0);
            if(coinScaleData!=null){
                RedisUtil.addHashObject(redis,"CoinScale",unitCoinType+":"+orderCoinType,coinScaleData);
            }
            return coinScaleData;
        }
        return coinScale;
    }

    @Override
    public List<Map<String, Object>> selectConditionPaging(Map<Object, Object> param) {
        return this.coinScaleMapper.selectConditionPaging(param);
    }

    @Override
    public int selectConditionCount(Map<Object, Object> param) {
        return this.coinScaleMapper.selectConditionCount(param);
    }
}