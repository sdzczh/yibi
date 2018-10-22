package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.core.dao.ChatRedpacketMapper;
import com.yibi.core.entity.ChatRedpacket;
import com.yibi.core.service.ChatRedpacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("chatRedpacketService")
public class ChatRedpacketServiceImpl implements ChatRedpacketService {
    @Resource
    private ChatRedpacketMapper chatRedpacketMapper;

    private static final Logger logger = LoggerFactory.getLogger(ChatRedpacketServiceImpl.class);

    @Override
    public int insert(ChatRedpacket record) {
        return this.chatRedpacketMapper.insert(record);
    }

    @Override
    public int insertSelective(ChatRedpacket record) {
        return this.chatRedpacketMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatRedpacket record) {
        return this.chatRedpacketMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatRedpacket record) {
        return this.chatRedpacketMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.chatRedpacketMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ChatRedpacket selectByPrimaryKey(Integer id) {
        return this.chatRedpacketMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ChatRedpacket> selectAll(Map<Object, Object> param) {
        return this.chatRedpacketMapper.selectAll(param);
    }

    @Override
    public List<ChatRedpacket> selectPaging(Map<Object, Object> param) {
        return this.chatRedpacketMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.chatRedpacketMapper.selectCount(param);
    }

    @Override
    public List<?> querySendList(Integer userId, Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.chatRedpacketMapper.querySendList(map);
    }

    @Override
    public BigDecimal querySumSend(Integer userId) {
        return this.chatRedpacketMapper.querySumSend(userId);
    }
}