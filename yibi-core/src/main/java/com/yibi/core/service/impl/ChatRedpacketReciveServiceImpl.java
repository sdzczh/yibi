package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.core.dao.ChatRedpacketReciveMapper;
import com.yibi.core.entity.ChatRedpacketRecive;
import com.yibi.core.service.ChatRedpacketReciveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:红包收取记录 chat_redpacket_recive
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("chatRedpacketReciveService")
public class ChatRedpacketReciveServiceImpl implements ChatRedpacketReciveService {
    @Resource
    private ChatRedpacketReciveMapper chatRedpacketReciveMapper;

    private static final Logger logger = LoggerFactory.getLogger(ChatRedpacketReciveServiceImpl.class);

    @Override
    public int insert(ChatRedpacketRecive record) {
        return this.chatRedpacketReciveMapper.insert(record);
    }

    @Override
    public int insertSelective(ChatRedpacketRecive record) {
        return this.chatRedpacketReciveMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatRedpacketRecive record) {
        return this.chatRedpacketReciveMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatRedpacketRecive record) {
        return this.chatRedpacketReciveMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.chatRedpacketReciveMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ChatRedpacketRecive selectByPrimaryKey(Integer id) {
        return this.chatRedpacketReciveMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ChatRedpacketRecive> selectAll(Map<Object, Object> param) {
        return this.chatRedpacketReciveMapper.selectAll(param);
    }

    @Override
    public List<ChatRedpacketRecive> selectPaging(Map<Object, Object> param) {
        return this.chatRedpacketReciveMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.chatRedpacketReciveMapper.selectCount(param);
    }

    @Override
    public List<?> queryRedPacketRecives(Integer userId, Integer packetId) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("packetId",packetId);
        return this.chatRedpacketReciveMapper.queryRedPacketRecives(map);
    }

    @Override
    public ChatRedpacketRecive getReciveByPacketId(Integer packetId, Integer receiveUserId) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("redpacid",packetId);
        map.put("userid",receiveUserId);
        List<ChatRedpacketRecive> list = selectAll(map);

        return list==null||list.isEmpty()?null : list.get(0);
    }

    @Override
    public List<?> queryReciveList(Integer userId, Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.chatRedpacketReciveMapper.queryReciveList(map);
    }


    @Override
    public BigDecimal querySumRecive(Integer userId) {
        return this.chatRedpacketReciveMapper.querySumRecive(userId);
    }
}