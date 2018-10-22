package com.yibi.core.dao;

import com.yibi.core.entity.YubiProfit;
import java.util.List;
import java.util.Map;

public interface YubiProfitMapper {
    int insert(YubiProfit record);

    int insertSelective(YubiProfit record);

    int updateByPrimaryKey(YubiProfit record);

    int updateByPrimaryKeySelective(YubiProfit record);

    int deleteByPrimaryKey(Integer id);

    YubiProfit selectByPrimaryKey(Integer id);

    List<YubiProfit> selectAll(Map<Object, Object> param);

    List<YubiProfit> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String,Object>> selectYbProfitByCondition(Map<Object,Object> map);

    int selectYbProfitByConditionCount(Map<Object,Object> map);
}