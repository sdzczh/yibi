package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.core.dao.ChatTransferMapper;
import com.yibi.core.entity.ChatTransfer;
import com.yibi.core.service.ChatTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:聊天转账记录 chat_transfer
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("chatTransferService")
public class ChatTransferServiceImpl implements ChatTransferService {
    @Resource
    private ChatTransferMapper chatTransferMapper;

    private static final Logger logger = LoggerFactory.getLogger(ChatTransferServiceImpl.class);

    @Override
    public int insert(ChatTransfer record) {
        return this.chatTransferMapper.insert(record);
    }

    @Override
    public int insertSelective(ChatTransfer record) {
        return this.chatTransferMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatTransfer record) {
        return this.chatTransferMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatTransfer record) {
        return this.chatTransferMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.chatTransferMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ChatTransfer selectByPrimaryKey(Integer id) {
        return this.chatTransferMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ChatTransfer> selectAll(Map<Object, Object> param) {
        return this.chatTransferMapper.selectAll(param);
    }

    @Override
    public List<ChatTransfer> selectPaging(Map<Object, Object> param) {
        return this.chatTransferMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.chatTransferMapper.selectCount(param);
    }

    @Override
    public BigDecimal querySumSendDay(Integer userId, String dateString) {
        Timestamp start = TimeStampUtils.StrToTimeStamp(dateString+" 00:00:00");
        Timestamp end = TimeStampUtils.StrToTimeStamp(dateString+" 23:59:59");
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("start",start);
        map.put("end",end);
        return this.chatTransferMapper.querySumSendDay(map);
    }

    @Override
    public Map queryByIdAndUserId(Integer transferId, Integer userId) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("transferId",transferId);
        map.put("userId",userId);

        return this.chatTransferMapper.queryByIdAndUserId(map);
    }

    @Override
    public List<?> querySendList(Integer userId, Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.chatTransferMapper.querySendList(map);
    }

    @Override
    public List<?> queryReciveList(Integer userId, Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.chatTransferMapper.queryReciveList(map);
    }

    @Override
    public BigDecimal querySumSend(Integer userId) {
        return this.chatTransferMapper.querySumSend(userId);
    }

    @Override
    public BigDecimal querySumRecive(Integer userId) {
        return this.chatTransferMapper.querySumRecive(userId);
    }
}