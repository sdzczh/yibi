package com.yibi.core.dao;

import com.yibi.core.entity.ChatGroupUser;
import java.util.List;
import java.util.Map;

public interface ChatGroupUserMapper {
    int insert(ChatGroupUser record);

    int insertSelective(ChatGroupUser record);

    int updateByPrimaryKey(ChatGroupUser record);

    int updateByPrimaryKeySelective(ChatGroupUser record);

    int deleteByPrimaryKey(Integer id);

    ChatGroupUser selectByPrimaryKey(Integer id);

    List<ChatGroupUser> selectAll(Map<Object, Object> param);

    List<ChatGroupUser> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<?>queryUsers(Map<String, Object> map);

    List<Map<String,Object>>selectConditionPaging(Map<Object,Object> map);

    int selectConditionCount(Map<Object,Object> map);

    int deleteAllByTalkGroupId(Integer talkGroupId);
}