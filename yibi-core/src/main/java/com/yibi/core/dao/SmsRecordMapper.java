package com.yibi.core.dao;

import com.yibi.core.entity.SmsRecord;
import java.util.List;
import java.util.Map;

public interface SmsRecordMapper {
    int insert(SmsRecord record);

    int insertSelective(SmsRecord record);

    int updateByPrimaryKey(SmsRecord record);

    int updateByPrimaryKeySelective(SmsRecord record);

    int deleteByPrimaryKey(Integer id);

    SmsRecord selectByPrimaryKey(Integer id);

    List<SmsRecord> selectAll(Map<Object, Object> param);

    List<SmsRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<SmsRecord> queryListByTimeLimit(Map map);
}