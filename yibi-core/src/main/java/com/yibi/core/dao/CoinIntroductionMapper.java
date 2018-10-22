package com.yibi.core.dao;

import com.yibi.core.entity.CoinIntroduction;
import java.util.List;
import java.util.Map;

public interface CoinIntroductionMapper {
    int insert(CoinIntroduction record);

    int insertSelective(CoinIntroduction record);

    int updateByPrimaryKey(CoinIntroduction record);

    int updateByPrimaryKeySelective(CoinIntroduction record);

    int deleteByPrimaryKey(Integer id);

    CoinIntroduction selectByPrimaryKey(Integer id);

    List<CoinIntroduction> selectAll(Map<Object, Object> param);

    List<CoinIntroduction> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}