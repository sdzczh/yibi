package com.yibi.core.dao;

import com.yibi.core.entity.DigcalRecord;
import java.util.List;
import java.util.Map;

public interface DigcalRecordMapper {
    int insert(DigcalRecord record);

    int insertSelective(DigcalRecord record);

    int updateByPrimaryKey(DigcalRecord record);

    int updateByPrimaryKeySelective(DigcalRecord record);

    int deleteByPrimaryKey(Integer id);

    DigcalRecord selectByPrimaryKey(Integer id);

    List<DigcalRecord> selectAll(Map<Object, Object> param);

    List<DigcalRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<DigcalRecord> existCalcalForceDay(Map<Object, Object> param);

    Integer getTotalCalculForceByUserAndType(Map<Object, Object> param);

    List<?> queryListByUser(Map<Object, Object> param);

    int insertBatch(List<DigcalRecord> list);

    int selectTodayCountByUserId(Integer userId);
}