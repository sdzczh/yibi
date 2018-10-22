package com.yibi.core.dao;

import com.yibi.core.entity.ChatRedpacketRecive;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ChatRedpacketReciveMapper {
    int insert(ChatRedpacketRecive record);

    int insertSelective(ChatRedpacketRecive record);

    int updateByPrimaryKey(ChatRedpacketRecive record);

    int updateByPrimaryKeySelective(ChatRedpacketRecive record);

    int deleteByPrimaryKey(Integer id);

    ChatRedpacketRecive selectByPrimaryKey(Integer id);

    List<ChatRedpacketRecive> selectAll(Map<Object, Object> param);

    List<ChatRedpacketRecive> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<?> queryRedPacketRecives(Map<Object, Object> param);

    List<?> queryReciveList(Map<Object, Object> param);

    BigDecimal querySumRecive(Integer userId);
}