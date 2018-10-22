package com.yibi.core.service.impl;

import com.yibi.core.dao.YubiProfitMapper;
import com.yibi.core.entity.YubiProfit;
import com.yibi.core.service.YubiProfitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:余币宝收益记录 yubi_profit
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("yubiProfitService")
public class YubiProfitServiceImpl implements YubiProfitService {
    @Resource
    private YubiProfitMapper yubiProfitMapper;

    private static final Logger logger = LoggerFactory.getLogger(YubiProfitServiceImpl.class);

    @Override
    public int insert(YubiProfit record) {
        return this.yubiProfitMapper.insert(record);
    }

    @Override
    public int insertSelective(YubiProfit record) {
        return this.yubiProfitMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(YubiProfit record) {
        return this.yubiProfitMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(YubiProfit record) {
        return this.yubiProfitMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.yubiProfitMapper.deleteByPrimaryKey(id);
    }

    @Override
    public YubiProfit selectByPrimaryKey(Integer id) {
        return this.yubiProfitMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<YubiProfit> selectAll(Map<Object, Object> param) {
        return this.yubiProfitMapper.selectAll(param);
    }

    @Override
    public List<YubiProfit> selectPaging(Map<Object, Object> param) {
        return this.yubiProfitMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.yubiProfitMapper.selectCount(param);
    }

    @Override
    public List<Map<String, Object>> selectYbProfitByCondition(Map<Object, Object> map) {
        return this.yubiProfitMapper.selectYbProfitByCondition(map);
    }

    @Override
    public int selectYbProfitByConditionCount(Map<Object, Object> map) {
        return this.yubiProfitMapper.selectYbProfitByConditionCount(map);
    }

    @Override
    public YubiProfit queryLastProfit(Integer userId, Integer coinType) {

        Map<Object, Object> map = new HashMap<>();
        map.put("userid",userId);
        map.put("cointype",coinType);
        map.put("firstResult",0);
        map.put("maxResult",1);
        List<YubiProfit> list = selectPaging(map);
        return list!=null&&!list.isEmpty()?list.get(0):null;
    }
}