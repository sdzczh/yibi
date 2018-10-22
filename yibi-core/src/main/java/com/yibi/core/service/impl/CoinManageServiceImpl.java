package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.common.utils.RedisUtil;
import com.yibi.core.dao.CoinManageMapper;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.CoinManageModel;
import com.yibi.core.service.CoinManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:币种管理 coin_manage
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("coinManageService")
public class CoinManageServiceImpl implements CoinManageService {
    @Resource
    private CoinManageMapper coinManageMapper;
    @Resource
    private RedisTemplate<String, String> redis;
    private static final Logger logger = LoggerFactory.getLogger(CoinManageServiceImpl.class);

    @Override
    public int insert(CoinManage record) {
        RedisUtil.addHashObject(redis,"CoinManage",record.getCointype().toString(),record);
        return this.coinManageMapper.insert(record);
    }

    @Override
    public int insertSelective(CoinManage record) {
        return this.coinManageMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(CoinManage record) {
        RedisUtil.addHashObject(redis,"CoinManage",record.getCointype().toString(),record);
        return this.coinManageMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(CoinManage record) {
        return this.coinManageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.coinManageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CoinManage selectByPrimaryKey(Integer id) {
        return this.coinManageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CoinManage> selectAll(Map<Object, Object> param) {
        return this.coinManageMapper.selectAll(param);
    }

    @Override
    public List<CoinManage> selectPaging(Map<Object, Object> param) {
        return this.coinManageMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.coinManageMapper.selectCount(param);
    }

    @Override
    public CoinManage queryByCoinType(Integer coinType) {
        CoinManage coinManage = RedisUtil.searchHashObject(redis,"CoinManage",coinType.toString(),CoinManage.class);
        if(coinManage ==null){
            Map<Object,Object> map = Maps.newHashMap();
            map.put("cointype",coinType);
            List<CoinManage> coinManages = selectAll(map);
            if(coinManages==null||coinManages.isEmpty()){
                return null;
            }else{
                RedisUtil.addHashObject(redis,"CoinManage",coinType.toString(),coinManages.get(0));
                return coinManages.get(0);
            }
        }
        return coinManage;
    }

    @Override
    public List<CoinManageModel> queryAllByConfig(Map map) {
        return this.coinManageMapper.queryAllByConfig(map);
    }
}