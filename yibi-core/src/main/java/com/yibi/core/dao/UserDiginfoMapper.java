package com.yibi.core.dao;

import com.yibi.core.entity.UserDiginfo;

import java.util.List;
import java.util.Map;

public interface UserDiginfoMapper {
    int insert(UserDiginfo record);

    int insertSelective(UserDiginfo record);

    int updateByPrimaryKey(UserDiginfo record);

    int updateByPrimaryKeySelective(UserDiginfo record);

    int deleteByPrimaryKey(Integer id);

    UserDiginfo selectByPrimaryKey(Integer id);

    List<UserDiginfo> selectAll(Map<Object, Object> param);

    List<UserDiginfo> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String,Object>> selectDigInfoAndPhone(Map<Object,Object> map);

    int selectDigInfoCount(Map<Object,Object> map);

    int queryCountBySoulLevel(Map<Object, Object> param);

    int queryRankByUserId(Integer userId);

    List<?> queryRankList(Map<Object, Object> param);

    List<UserDiginfo> querySignInterList(Map<Object, Object> param);

    int updateCalculForceBatch(Map<Object, Object> param);
}