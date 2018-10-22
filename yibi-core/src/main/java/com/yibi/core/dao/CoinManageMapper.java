package com.yibi.core.dao;

import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.CoinManageModel;

import java.util.List;
import java.util.Map;

public interface CoinManageMapper {
    int insert(CoinManage record);

    int insertSelective(CoinManage record);

    int updateByPrimaryKey(CoinManage record);

    int updateByPrimaryKeySelective(CoinManage record);

    int deleteByPrimaryKey(Integer id);

    CoinManage selectByPrimaryKey(Integer id);

    List<CoinManage> selectAll(Map<Object, Object> param);

    List<CoinManage> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<CoinManageModel> queryAllByConfig(Map map);
}