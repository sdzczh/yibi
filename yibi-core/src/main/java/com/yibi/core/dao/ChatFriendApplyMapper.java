package com.yibi.core.dao;

import com.yibi.core.entity.ChatFriendApply;
import java.util.List;
import java.util.Map;

public interface ChatFriendApplyMapper {
    int insert(ChatFriendApply record);

    int insertSelective(ChatFriendApply record);

    int updateByPrimaryKey(ChatFriendApply record);

    int updateByPrimaryKeySelective(ChatFriendApply record);

    int deleteByPrimaryKey(Integer id);

    ChatFriendApply selectByPrimaryKey(Integer id);

    List<ChatFriendApply> selectAll(Map<Object, Object> param);

    List<ChatFriendApply> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List queryApplyByUserId(Integer userId);
}