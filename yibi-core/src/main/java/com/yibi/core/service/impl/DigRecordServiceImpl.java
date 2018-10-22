package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.core.dao.*;
import com.yibi.core.entity.DigRecord;
import com.yibi.core.service.DigRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/19 0019.
 */
@Service("digRecordService")
public class DigRecordServiceImpl implements DigRecordService {
    @Resource
    private DigRecord20180Mapper digRecord20180Mapper;
    @Resource
    private DigRecord20181Mapper digRecord20181Mapper;
    @Resource
    private DigRecord20182Mapper digRecord20182Mapper;
    @Resource
    private DigRecord20183Mapper digRecord20183Mapper;
    @Resource
    private DigRecord20184Mapper digRecord20184Mapper;
    @Resource
    private DigRecord20185Mapper digRecord20185Mapper;
    @Resource
    private DigRecord20186Mapper digRecord20186Mapper;
    @Resource
    private DigRecord20187Mapper digRecord20187Mapper;
    @Resource
    private DigRecord20188Mapper digRecord20188Mapper;
    @Resource
    private DigRecord20189Mapper digRecord20189Mapper;
    @Override
    public int insert(DigRecord record) {
        if(record.getUserid() ==null){
            throw  new NullPointerException("用户id为空");

        }
        int inx = record.getUserid()%10;
        switch (inx){
            case 0: return digRecord20180Mapper.insert(record);
            case 1: return digRecord20181Mapper.insert(record);
            case 2: return digRecord20182Mapper.insert(record);
            case 3: return digRecord20183Mapper.insert(record);
            case 4: return digRecord20184Mapper.insert(record);
            case 5: return digRecord20185Mapper.insert(record);
            case 6: return digRecord20186Mapper.insert(record);
            case 7: return digRecord20187Mapper.insert(record);
            case 8: return digRecord20188Mapper.insert(record);
            case 9: return digRecord20189Mapper.insert(record);
        }
        return 0;
    }

    @Override
    public int insertSelective(DigRecord record) {
        if(record.getUserid() ==null){
            throw  new NullPointerException("用户id为空");

        }
        int inx = record.getUserid()%10;
        switch (inx){
            case 0: return digRecord20180Mapper.insertSelective(record);
            case 1: return digRecord20181Mapper.insertSelective(record);
            case 2: return digRecord20182Mapper.insertSelective(record);
            case 3: return digRecord20183Mapper.insertSelective(record);
            case 4: return digRecord20184Mapper.insertSelective(record);
            case 5: return digRecord20185Mapper.insertSelective(record);
            case 6: return digRecord20186Mapper.insertSelective(record);
            case 7: return digRecord20187Mapper.insertSelective(record);
            case 8: return digRecord20188Mapper.insertSelective(record);
            case 9: return digRecord20189Mapper.insertSelective(record);
        }
        return 0;
    }

    @Override
    public int updateByPrimaryKey(DigRecord record) {
        if(record.getUserid() ==null){
            throw  new NullPointerException("用户id为空");

        }
        int inx = record.getUserid()%10;
        switch (inx){
            case 0: return digRecord20180Mapper.updateByPrimaryKey(record);
            case 1: return digRecord20181Mapper.updateByPrimaryKey(record);
            case 2: return digRecord20182Mapper.updateByPrimaryKey(record);
            case 3: return digRecord20183Mapper.updateByPrimaryKey(record);
            case 4: return digRecord20184Mapper.updateByPrimaryKey(record);
            case 5: return digRecord20185Mapper.updateByPrimaryKey(record);
            case 6: return digRecord20186Mapper.updateByPrimaryKey(record);
            case 7: return digRecord20187Mapper.updateByPrimaryKey(record);
            case 8: return digRecord20188Mapper.updateByPrimaryKey(record);
            case 9: return digRecord20189Mapper.updateByPrimaryKey(record);
        }
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(DigRecord record) {
        if(record.getUserid() ==null){
            throw  new NullPointerException("用户id为空");

        }
        int inx = record.getUserid()%10;
        switch (inx){
            case 0: return digRecord20180Mapper.updateByPrimaryKeySelective(record);
            case 1: return digRecord20181Mapper.updateByPrimaryKeySelective(record);
            case 2: return digRecord20182Mapper.updateByPrimaryKeySelective(record);
            case 3: return digRecord20183Mapper.updateByPrimaryKeySelective(record);
            case 4: return digRecord20184Mapper.updateByPrimaryKeySelective(record);
            case 5: return digRecord20185Mapper.updateByPrimaryKeySelective(record);
            case 6: return digRecord20186Mapper.updateByPrimaryKeySelective(record);
            case 7: return digRecord20187Mapper.updateByPrimaryKeySelective(record);
            case 8: return digRecord20188Mapper.updateByPrimaryKeySelective(record);
            case 9: return digRecord20189Mapper.updateByPrimaryKeySelective(record);
        }
        return 0;
    }

    @Override
    public List<DigRecord> selectByUserIdAndType(Integer userId, Integer coinType) {
        Map<Object, Object> map = new HashMap();
        map.put("userid", userId);
        map.put("cointype", coinType);
        map.put("state",0);
        int inx = userId%10;
        switch (inx){
            case 0: return digRecord20180Mapper.selectAll(map);
            case 1: return digRecord20181Mapper.selectAll(map);
            case 2: return digRecord20182Mapper.selectAll(map);
            case 3: return digRecord20183Mapper.selectAll(map);
            case 4: return digRecord20184Mapper.selectAll(map);
            case 5: return digRecord20185Mapper.selectAll(map);
            case 6: return digRecord20186Mapper.selectAll(map);
            case 7: return digRecord20187Mapper.selectAll(map);
            case 8: return digRecord20188Mapper.selectAll(map);
            case 9: return digRecord20189Mapper.selectAll(map);
        }
        return null;
    }

    @Override
    public List<?> queryByUser(Integer userId, Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);

        int inx = userId%10;
        switch (inx){
            case 0: return digRecord20180Mapper.queryByUser(map);
            case 1: return digRecord20181Mapper.queryByUser(map);
            case 2: return digRecord20182Mapper.queryByUser(map);
            case 3: return digRecord20183Mapper.queryByUser(map);
            case 4: return digRecord20184Mapper.queryByUser(map);
            case 5: return digRecord20185Mapper.queryByUser(map);
            case 6: return digRecord20186Mapper.queryByUser(map);
            case 7: return digRecord20187Mapper.queryByUser(map);
            case 8: return digRecord20188Mapper.queryByUser(map);
            case 9: return digRecord20189Mapper.queryByUser(map);
        }
        return null;
    }
}
