package com.yibi.core.dao;

import com.yibi.core.entity.CoinScale;
import java.util.List;
import java.util.Map;

public interface CoinScaleMapper {
    int insert(CoinScale record);

    int insertSelective(CoinScale record);

    int updateByPrimaryKey(CoinScale record);

    int updateByPrimaryKeySelective(CoinScale record);

    int deleteByPrimaryKey(Integer id);

    CoinScale selectByPrimaryKey(Integer id);

    List<CoinScale> selectAll(Map<Object, Object> param);

    List<CoinScale> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String,Object>> selectConditionPaging(Map<Object, Object> param);

    int selectConditionCount(Map<Object, Object> param);
}