package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.core.dao.ChatFriendApplyMapper;
import com.yibi.core.entity.ChatFriendApply;
import com.yibi.core.service.ChatFriendApplyService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:好友关系申请表 chat_friend_apply
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
@Service("chatFriendApplyService")
public class ChatFriendApplyServiceImpl implements ChatFriendApplyService {
    @Resource
    private ChatFriendApplyMapper chatFriendApplyMapper;

    private static final Logger logger = LoggerFactory.getLogger(ChatFriendApplyServiceImpl.class);

    @Override
    public int insert(ChatFriendApply record) {
        return this.chatFriendApplyMapper.insert(record);
    }

    @Override
    public int insertSelective(ChatFriendApply record) {
        return this.chatFriendApplyMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatFriendApply record) {
        return this.chatFriendApplyMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatFriendApply record) {
        return this.chatFriendApplyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.chatFriendApplyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ChatFriendApply selectByPrimaryKey(Integer id) {
        return this.chatFriendApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ChatFriendApply> selectAll(Map<Object, Object> param) {
        return this.chatFriendApplyMapper.selectAll(param);
    }

    @Override
    public List<ChatFriendApply> selectPaging(Map<Object, Object> param) {
        return this.chatFriendApplyMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.chatFriendApplyMapper.selectCount(param);
    }

    @Override
    public ChatFriendApply queryByUserAndFri(Integer userId, Integer friendId, int state) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userid",userId);
        map.put("friendid",friendId);
        map.put("state",state);
        List<ChatFriendApply> friends = selectAll(map);
        return friends==null||friends.isEmpty()?null:friends.get(0);
    }

    @Override
    public List queryApplyByUserId(Integer userId) {
        return this.chatFriendApplyMapper.queryApplyByUserId(userId);
    }
}