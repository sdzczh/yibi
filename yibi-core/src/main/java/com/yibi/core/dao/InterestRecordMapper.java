package com.yibi.core.dao;

import com.yibi.core.entity.InterestRecord;
import java.util.List;
import java.util.Map;

public interface InterestRecordMapper {
    int insert(InterestRecord record);

    int insertSelective(InterestRecord record);

    int updateByPrimaryKey(InterestRecord record);

    int updateByPrimaryKeySelective(InterestRecord record);

    int deleteByPrimaryKey(Integer id);

    InterestRecord selectByPrimaryKey(Integer id);

    List<InterestRecord> selectAll(Map<Object, Object> param);

    List<InterestRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}