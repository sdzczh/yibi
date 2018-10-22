package com.yibi.core.dao;

import com.yibi.core.entity.CommissionRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CommissionRecordMapper {
    int insert(CommissionRecord record);

    int insertSelective(CommissionRecord record);

    int updateByPrimaryKey(CommissionRecord record);

    int updateByPrimaryKeySelective(CommissionRecord record);

    int deleteByPrimaryKey(Integer id);

    CommissionRecord selectByPrimaryKey(Integer id);

    List<CommissionRecord> selectAll(Map<Object, Object> param);

    List<CommissionRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    BigDecimal selectSumAmountByOrderid(Integer orderid);
}