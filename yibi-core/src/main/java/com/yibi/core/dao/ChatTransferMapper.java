package com.yibi.core.dao;

import com.yibi.core.entity.ChatTransfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ChatTransferMapper {
    int insert(ChatTransfer record);

    int insertSelective(ChatTransfer record);

    int updateByPrimaryKey(ChatTransfer record);

    int updateByPrimaryKeySelective(ChatTransfer record);

    int deleteByPrimaryKey(Integer id);

    ChatTransfer selectByPrimaryKey(Integer id);

    List<ChatTransfer> selectAll(Map<Object, Object> param);

    List<ChatTransfer> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    BigDecimal querySumSendDay(Map<Object, Object> param);

    Map queryByIdAndUserId(Map<Object, Object> param);

    List<?> querySendList(Map<Object, Object> param);

    List<?> queryReciveList(Map<Object, Object> param);

    BigDecimal querySumSend(Integer userId);

    BigDecimal querySumRecive(Integer userId);
}