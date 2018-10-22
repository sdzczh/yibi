package com.yibi.core.dao;

import com.yibi.core.entity.LoanRecord;
import java.util.List;
import java.util.Map;

public interface LoanRecordMapper {
    int insert(LoanRecord record);

    int insertSelective(LoanRecord record);

    int updateByPrimaryKey(LoanRecord record);

    int updateByPrimaryKeySelective(LoanRecord record);

    int deleteByPrimaryKey(Integer id);

    LoanRecord selectByPrimaryKey(Integer id);

    List<LoanRecord> selectAll(Map<Object, Object> param);

    List<LoanRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}