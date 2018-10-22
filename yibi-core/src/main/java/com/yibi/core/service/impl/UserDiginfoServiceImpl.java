package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.core.dao.UserDiginfoMapper;
import com.yibi.core.entity.UserDiginfo;
import com.yibi.core.service.UserDiginfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:用户挖矿信息 user_diginfo
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("userDiginfoService")
public class UserDiginfoServiceImpl implements UserDiginfoService {
    @Resource
    private UserDiginfoMapper userDiginfoMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserDiginfoServiceImpl.class);

    @Override
    public int insert(UserDiginfo record) {
        return this.userDiginfoMapper.insert(record);
    }

    @Override
    public int insertSelective(UserDiginfo record) {
        return this.userDiginfoMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserDiginfo record) {
        return this.userDiginfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(UserDiginfo record) {
        return this.userDiginfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.userDiginfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public UserDiginfo selectByPrimaryKey(Integer id) {
        return this.userDiginfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserDiginfo> selectAll(Map<Object, Object> param) {
        return this.userDiginfoMapper.selectAll(param);
    }

    @Override
    public List<UserDiginfo> selectPaging(Map<Object, Object> param) {
        return this.userDiginfoMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.userDiginfoMapper.selectCount(param);
    }

    @Override
    public UserDiginfo saveOrUpdate(UserDiginfo diginfo) {
        if(diginfo.getId()==null){
            userDiginfoMapper.insert(diginfo);
        }else{
            userDiginfoMapper.updateByPrimaryKey(diginfo);
        }
        return diginfo;
    }

    @Override
    public List<Map<String, Object>> selectDigInfoAndPhone(Map<Object, Object> map) {
        return this.userDiginfoMapper.selectDigInfoAndPhone(map);
    }

    @Override
    public int selectDigInfoCount(Map<Object, Object> map) {
        return this.userDiginfoMapper.selectDigInfoCount(map);
    }

    @Override
    public UserDiginfo queryByUserId(Integer userId) {
        Map<Object, Object> map = new HashMap();
        map.put("userid", userId);
        List<UserDiginfo> list = selectAll(map);

        return list==null||list.isEmpty()?null:list.get(0);
    }

    @Override
    public int queryCountBySoulLevel(Integer soulMin, Integer soulMax) {
        Map<Object, Object> map = new HashMap();
        map.put("soulMin", soulMin);
        map.put("soulMax", soulMax);
        return this.userDiginfoMapper.queryCountBySoulLevel(map);
    }

    @Override
    public int queryRankByUserId(Integer userId) {
        return this.userDiginfoMapper.queryRankByUserId(userId);
    }

    @Override
    public List<?> queryRankList(Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.userDiginfoMapper.queryRankList(map);
    }

    @Override
    public List<UserDiginfo> querySignInterList(Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        map.put("yesterday", TimeStampUtils.getSomeDayTime(-1));
        return this.userDiginfoMapper.querySignInterList(map);
    }

    @Override
    public int updateCalculForceBatch(List<Integer> userIds, Integer calculForce) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userIds",userIds);
        map.put("calculForce",calculForce);
        return this.userDiginfoMapper.updateCalculForceBatch(map);
    }


}