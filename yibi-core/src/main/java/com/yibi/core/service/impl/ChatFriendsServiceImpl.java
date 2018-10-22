package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.core.dao.ChatFriendsMapper;
import com.yibi.core.entity.ChatFriends;
import com.yibi.core.service.ChatFriendsService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:聊天好友关系表 chat_friends
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("chatFriendsService")
public class ChatFriendsServiceImpl implements ChatFriendsService {
    @Resource
    private ChatFriendsMapper chatFriendsMapper;

    private static final Logger logger = LoggerFactory.getLogger(ChatFriendsServiceImpl.class);

    @Override
    public int insert(ChatFriends record) {
        return this.chatFriendsMapper.insert(record);
    }

    @Override
    public int insertSelective(ChatFriends record) {
        return this.chatFriendsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatFriends record) {
        return this.chatFriendsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatFriends record) {
        return this.chatFriendsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.chatFriendsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ChatFriends selectByPrimaryKey(Integer id) {
        return this.chatFriendsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ChatFriends> selectAll(Map<Object, Object> param) {
        return this.chatFriendsMapper.selectAll(param);
    }

    @Override
    public List<ChatFriends> selectPaging(Map<Object, Object> param) {
        return this.chatFriendsMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.chatFriendsMapper.selectCount(param);
    }

    @Override
    public Map queryUserModelByPhone(String phone,Integer userId) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("phone",phone);
        map.put("userId",userId);
        return this.chatFriendsMapper.queryUserModelByPhone(map);
    }

    @Override
    public List<?> queryFriends(Integer userId) {
        return this.chatFriendsMapper.queryFriends(userId);
    }

    @Override
    public ChatFriends getByUser(Integer userId, Integer friendId) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userid",userId);
        map.put("friendid",friendId);
        List<ChatFriends> friends = selectAll(map);
        return friends==null||friends.isEmpty()?null:friends.get(0);
    }

    @Override
    public int deleteFriend(Integer userId, Integer friendId) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userid",userId);
        map.put("friendid",friendId);
        return this.chatFriendsMapper.deleteFriend(map);
    }
}