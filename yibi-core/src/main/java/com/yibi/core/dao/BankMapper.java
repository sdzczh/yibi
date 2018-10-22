package com.yibi.core.dao;

import com.yibi.core.entity.Bank;
import java.util.List;
import java.util.Map;

public interface BankMapper {
    int insert(Bank record);

    int insertSelective(Bank record);

    int updateByPrimaryKey(Bank record);

    int updateByPrimaryKeySelective(Bank record);

    int deleteByPrimaryKey(Integer id);

    Bank selectByPrimaryKey(Integer id);

    List<Bank> selectAll(Map<Object, Object> param);

    List<Bank> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String,Object>> selectRelationPaging(Map<Object, Object> param);

    int selectRelationCount(Map<Object, Object> param);
}