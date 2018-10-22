package com.yibi.core.dao;

import com.yibi.core.entity.OperLog;
import java.util.List;
import java.util.Map;

public interface OperLogMapper {
    int insert(OperLog record);

    int insertSelective(OperLog record);

    int updateByPrimaryKey(OperLog record);

    int updateByPrimaryKeySelective(OperLog record);

    int deleteByPrimaryKey(Integer id);

    OperLog selectByPrimaryKey(Integer id);

    List<OperLog> selectAll(Map<Object, Object> param);

    List<OperLog> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}