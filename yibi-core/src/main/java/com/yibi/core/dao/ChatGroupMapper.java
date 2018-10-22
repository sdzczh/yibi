package com.yibi.core.dao;

import com.yibi.core.entity.ChatGroup;
import java.util.List;
import java.util.Map;

public interface ChatGroupMapper {
    int insert(ChatGroup record);

    int insertSelective(ChatGroup record);

    int updateByPrimaryKey(ChatGroup record);

    int updateByPrimaryKeySelective(ChatGroup record);

    int deleteByPrimaryKey(Integer id);

    ChatGroup selectByPrimaryKey(Integer id);

    List<ChatGroup> selectAll(Map<Object, Object> param);

    List<ChatGroup> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<?> queryByType(Integer type);

    List<ChatGroup> queryByTypeAndName(Map<Object, Object> param);
}