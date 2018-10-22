package com.yibi.core.dao;

import com.yibi.core.entity.DigRecord;

import java.util.List;
import java.util.Map;

public interface DigRecord20185Mapper {
    int insert(DigRecord record);

    int insertSelective(DigRecord record);

    int updateByPrimaryKey(DigRecord record);

    int updateByPrimaryKeySelective(DigRecord record);

    int deleteByPrimaryKey(Integer id);

    DigRecord selectByPrimaryKey(Integer id);

    List<DigRecord> selectAll(Map<Object, Object> param);

    List<DigRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<?> queryByUser(Map<Object, Object> param);

}