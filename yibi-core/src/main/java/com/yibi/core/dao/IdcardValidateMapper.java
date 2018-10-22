package com.yibi.core.dao;

import com.yibi.core.entity.IdcardValidate;
import java.util.List;
import java.util.Map;

public interface IdcardValidateMapper {
    int insert(IdcardValidate record);

    int insertSelective(IdcardValidate record);

    int updateByPrimaryKey(IdcardValidate record);

    int updateByPrimaryKeySelective(IdcardValidate record);

    int deleteByPrimaryKey(Integer id);

    IdcardValidate selectByPrimaryKey(Integer id);

    List<IdcardValidate> selectAll(Map<Object, Object> param);

    List<IdcardValidate> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<?> queryValidateTimes(Map<String, Object> map);

    List<Map<String,Object>> selectConditionPaging(Map<Object, Object> param);

    int selectConditionCount(Map<Object, Object> param);
}