package com.yibi.core.dao;

import com.yibi.core.entity.Picture;
import java.util.List;
import java.util.Map;

public interface PictureMapper {
    int insert(Picture record);

    int insertSelective(Picture record);

    int updateByPrimaryKey(Picture record);

    int updateByPrimaryKeySelective(Picture record);

    int deleteByPrimaryKey(Integer id);

    Picture selectByPrimaryKey(Integer id);

    List<Picture> selectAll(Map<Object, Object> param);

    List<Picture> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}