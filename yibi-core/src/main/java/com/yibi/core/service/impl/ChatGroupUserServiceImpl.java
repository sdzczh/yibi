package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.core.dao.ChatGroupUserMapper;
import com.yibi.core.entity.ChatGroupUser;
import com.yibi.core.service.ChatGroupUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("chatGroupUserService")
public class ChatGroupUserServiceImpl implements ChatGroupUserService {
    @Resource
    private ChatGroupUserMapper chatGroupUserMapper;

    private static final Logger logger = LoggerFactory.getLogger(ChatGroupUserServiceImpl.class);

    @Override
    public int insert(ChatGroupUser record) {
        return this.chatGroupUserMapper.insert(record);
    }

    @Override
    public int insertSelective(ChatGroupUser record) {
        return this.chatGroupUserMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatGroupUser record) {
        return this.chatGroupUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatGroupUser record) {
        return this.chatGroupUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.chatGroupUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ChatGroupUser selectByPrimaryKey(Integer id) {
        return this.chatGroupUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ChatGroupUser> selectAll(Map<Object, Object> param) {
        return this.chatGroupUserMapper.selectAll(param);
    }

    @Override
    public List<ChatGroupUser> selectPaging(Map<Object, Object> param) {
        return this.chatGroupUserMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.chatGroupUserMapper.selectCount(param);
    }

    @Override
    public List<?> queryUsers(String groupId, Integer userId, Integer page, Integer rows) {

        Map<String,Object> map = Maps.newHashMap();
        map.put("groupId",groupId);
        map.put("userId",userId);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.chatGroupUserMapper.queryUsers(map);
    }

    @Override
    public ChatGroupUser getByGroupIdAndUserId(Integer talkGroupId, Integer userId) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("talkgroupid",talkGroupId);
        map.put("userid",userId);
        List<ChatGroupUser> list = selectAll(map);
        return list ==null||list.isEmpty()? null:list.get(0);
    }

    @Override
    public List<Map<String, Object>> selectConditionPaging(Map<Object, Object> map) {
        return this.chatGroupUserMapper.selectConditionPaging(map);
    }

    @Override
    public int selectConditionCount(Map<Object, Object> map) {
        return this.chatGroupUserMapper.selectConditionCount(map);
    }

    @Override
    public int deleteAllByTalkGroupId(Integer talkGroupId) {
        return this.chatGroupUserMapper.deleteAllByTalkGroupId(talkGroupId);
    }


}