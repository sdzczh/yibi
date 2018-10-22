package com.yibi.core.dao;

import com.yibi.core.entity.Resource;
import java.util.List;
import java.util.Map;

public interface ResourceMapper {
    int insert(Resource record);

    int insertSelective(Resource record);

    int updateByPrimaryKey(Resource record);

    int updateByPrimaryKeySelective(Resource record);

    int deleteByPrimaryKey(Integer id);

    Resource selectByPrimaryKey(Integer id);

    List<Resource> selectAll(Map<Object, Object> param);

    List<Resource> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Resource> selectRescourcesByManager(Map<String, Object> params);

    Map<String,Object> getIndexInfo();
}