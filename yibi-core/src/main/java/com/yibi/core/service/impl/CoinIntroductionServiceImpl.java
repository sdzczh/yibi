package com.yibi.core.service.impl;

import com.yibi.core.dao.CoinIntroductionMapper;
import com.yibi.core.entity.CoinIntroduction;
import com.yibi.core.service.CoinIntroductionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:币种介绍 coin_introduction
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("coinIntroductionService")
public class CoinIntroductionServiceImpl implements CoinIntroductionService {
    @Resource
    private CoinIntroductionMapper coinIntroductionMapper;

    private static final Logger logger = LoggerFactory.getLogger(CoinIntroductionServiceImpl.class);

    @Override
    public int insert(CoinIntroduction record) {
        return this.coinIntroductionMapper.insert(record);
    }

    @Override
    public int insertSelective(CoinIntroduction record) {
        return this.coinIntroductionMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(CoinIntroduction record) {
        return this.coinIntroductionMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(CoinIntroduction record) {
        return this.coinIntroductionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.coinIntroductionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CoinIntroduction selectByPrimaryKey(Integer id) {
        return this.coinIntroductionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CoinIntroduction> selectAll(Map<Object, Object> param) {
        return this.coinIntroductionMapper.selectAll(param);
    }

    @Override
    public List<CoinIntroduction> selectPaging(Map<Object, Object> param) {
        return this.coinIntroductionMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.coinIntroductionMapper.selectCount(param);
    }

    @Override
    public Map<String, Object> queryCoinInroduction(Map<String, Object> map, Integer coinType) {
        Map param = new HashMap();
        param.put("cointype", coinType);
        List<CoinIntroduction> list = coinIntroductionMapper.selectAll(param);
        if(list != null && !list.isEmpty()){
            map.put("coin", list.get(0));
            return map;
        }
        return null;
    }
}