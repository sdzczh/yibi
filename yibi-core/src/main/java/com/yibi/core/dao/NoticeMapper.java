package com.yibi.core.dao;

import com.yibi.core.entity.Notice;
import java.util.List;
import java.util.Map;

public interface NoticeMapper {
    int insert(Notice record);

    int insertSelective(Notice record);

    int updateByPrimaryKey(Notice record);

    int updateByPrimaryKeySelective(Notice record);

    int deleteByPrimaryKey(Integer id);

    Notice selectByPrimaryKey(Integer id);

    List<Notice> selectAll(Map<Object, Object> param);
    List<Notice> selectByIdAndState(Map<Object, Object> param);

    List<Notice> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Notice> selectInfoByIndex();
}