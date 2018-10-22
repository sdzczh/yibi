package com.yibi.core.dao;

import com.yibi.core.entity.AboutInfo;
import java.util.List;
import java.util.Map;

public interface AboutInfoMapper {
    int insert(AboutInfo record);

    int insertSelective(AboutInfo record);

    int updateByPrimaryKey(AboutInfo record);

    int updateByPrimaryKeySelective(AboutInfo record);

    int deleteByPrimaryKey(Integer id);

    AboutInfo selectByPrimaryKey(Integer id);

    List<AboutInfo> selectAll(Map<Object, Object> param);

    List<AboutInfo> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}