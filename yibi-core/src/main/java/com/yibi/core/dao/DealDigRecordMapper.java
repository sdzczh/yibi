package com.yibi.core.dao;

import com.yibi.core.entity.DealDigRecord;
import com.yibi.core.entity.DealDigRecordModel;

import java.util.List;
import java.util.Map;

public interface DealDigRecordMapper {
    int insert(DealDigRecord record);

    int insertSelective(DealDigRecord record);

    int updateByPrimaryKey(DealDigRecord record);

    int updateByPrimaryKeySelective(DealDigRecord record);

    int deleteByPrimaryKey(Integer id);

    DealDigRecord selectByPrimaryKey(Integer id);

    List<DealDigRecord> selectAll(Map<Object, Object> param);

    List<DealDigRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    DealDigRecordModel queryProfit(Integer userid);
}