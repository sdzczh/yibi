package com.yibi.core.dao;

import com.yibi.core.entity.DealDigConfig;
import java.util.List;
import java.util.Map;

public interface DealDigConfigMapper {
    int insert(DealDigConfig record);

    int insertSelective(DealDigConfig record);

    int updateByPrimaryKey(DealDigConfig record);

    int updateByPrimaryKeySelective(DealDigConfig record);

    int deleteByPrimaryKey(Integer id);

    DealDigConfig selectByPrimaryKey(Integer id);

    List<DealDigConfig> selectAll(Map<Object, Object> param);

    List<DealDigConfig> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String ,Object>> selectConditionAll(Map<Object, Object> param);

    int selectConditionCount(Map<Object, Object> param);
}