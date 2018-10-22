package com.yibi.core.dao;

import com.yibi.core.entity.Recharge;
import java.util.List;
import java.util.Map;

public interface RechargeMapper {
    int insert(Recharge record);

    int insertSelective(Recharge record);

    int updateByPrimaryKey(Recharge record);

    int updateByPrimaryKeySelective(Recharge record);

    int deleteByPrimaryKey(Integer id);

    Recharge selectByPrimaryKey(Integer id);

    List<Recharge> selectAll(Map<Object, Object> param);

    List<Recharge> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String,Object>> selectRechargeOrPhone(Map<Object,Object> map );

    int selectCountRechargeOrPhone (Map<Object,Object> map);
}