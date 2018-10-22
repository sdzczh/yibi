package com.yibi.core.dao;

import com.yibi.core.entity.UserBindAccount;
import java.util.List;
import java.util.Map;

public interface UserBindAccountMapper {
    int insert(UserBindAccount record);

    int insertSelective(UserBindAccount record);

    int updateByPrimaryKey(UserBindAccount record);

    int updateByPrimaryKeySelective(UserBindAccount record);

    int deleteByPrimaryKey(Integer id);

    UserBindAccount selectByPrimaryKey(Integer id);

    List<UserBindAccount> selectAll(Map<Object, Object> param);

    List<UserBindAccount> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}