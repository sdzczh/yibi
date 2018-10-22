package com.yibi.core.dao;

import com.yibi.core.entity.ChatRedpacket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ChatRedpacketMapper {
    int insert(ChatRedpacket record);

    int insertSelective(ChatRedpacket record);

    int updateByPrimaryKey(ChatRedpacket record);

    int updateByPrimaryKeySelective(ChatRedpacket record);

    int deleteByPrimaryKey(Integer id);

    ChatRedpacket selectByPrimaryKey(Integer id);

    List<ChatRedpacket> selectAll(Map<Object, Object> param);

    List<ChatRedpacket> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<?> querySendList(Map<Object, Object> param);

    BigDecimal querySumSend(Integer userId);
}