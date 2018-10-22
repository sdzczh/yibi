package com.yibi.core.dao;

import com.yibi.core.entity.ChatFriends;
import java.util.List;
import java.util.Map;

public interface ChatFriendsMapper {
    int insert(ChatFriends record);

    int insertSelective(ChatFriends record);

    int updateByPrimaryKey(ChatFriends record);

    int updateByPrimaryKeySelective(ChatFriends record);

    int deleteByPrimaryKey(Integer id);

    ChatFriends selectByPrimaryKey(Integer id);

    List<ChatFriends> selectAll(Map<Object, Object> param);

    List<ChatFriends> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    Map queryUserModelByPhone(Map<Object, Object> param);

    List<?> queryFriends(Integer userId);
    int deleteFriend(Map<Object, Object> param);

}