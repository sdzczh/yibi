package com.yibi.core.service.impl;

import com.yibi.core.dao.ChatGroupMapper;
import com.yibi.core.entity.ChatGroup;
import com.yibi.core.service.ChatGroupService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("chatGroupService")
public class ChatGroupServiceImpl implements ChatGroupService {
    @Resource
    private ChatGroupMapper chatGroupMapper;

    private static final Logger logger = LoggerFactory.getLogger(ChatGroupServiceImpl.class);

    @Override
    public int insert(ChatGroup record) {
        return this.chatGroupMapper.insert(record);
    }

    @Override
    public int insertSelective(ChatGroup record) {
        return this.chatGroupMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatGroup record) {
        return this.chatGroupMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatGroup record) {
        return this.chatGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.chatGroupMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ChatGroup selectByPrimaryKey(Integer id) {
        return this.chatGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ChatGroup> selectAll(Map<Object, Object> param) {
        return this.chatGroupMapper.selectAll(param);
    }

    @Override
    public List<ChatGroup> selectPaging(Map<Object, Object> param) {
        return this.chatGroupMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.chatGroupMapper.selectCount(param);
    }

    @Override
    public List<?> queryByType(Integer type) {
        return this.chatGroupMapper.queryByType(type);
    }

    @Override
    public ChatGroup queryByGroupId(String groupId) {
        Map<Object, Object> map = new HashMap();
        map.put("groupid", groupId);
        List<ChatGroup> groups = selectAll(map);

        return groups==null||groups.isEmpty()?null:groups.get(0);
    }

    @Override
    public List<ChatGroup> queryByTypeAndName(Integer type, String name) {
        Map<Object, Object> map = new HashMap();
        map.put("type", type);
        map.put("name",name);
        return this.chatGroupMapper.queryByTypeAndName(map);
    }
}