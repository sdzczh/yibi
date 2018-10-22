package com.yibi.core.service;

import com.yibi.core.entity.ChatTransfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:聊天转账记录 chat_transfer
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface ChatTransferService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(ChatTransfer record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(ChatTransfer record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(ChatTransfer record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(ChatTransfer record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    ChatTransfer selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<ChatTransfer> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<ChatTransfer> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 查询一天的转出总量
     * @param userId
     * @param dateString
     * @return
     */
    BigDecimal querySumSendDay(Integer userId, String dateString);

    /**
     * 根据转账id和当前登陆人id查询转账详情
     * @param transferId
     * @param userId
     * @return
     */
    Map queryByIdAndUserId(Integer transferId, Integer userId);

    /**
     * 查询转出记录
     * @param userId
     * @return
     * @return List<?>
     * @date 2018-5-22
     * @author lina
     */
    List<?> querySendList(Integer userId, Integer page, Integer rows);

    /**
     * 查询转入记录
     * @param userId
     * @return
     * @return List<?>
     * @date 2018-5-22
     * @author lina
     */
    List<?> queryReciveList(Integer userId, Integer page, Integer rows);

    /**
     * 查询转出记录的总额
     * @param userId
     * @return BigDecimal
     * @date 2018-5-22
     * @author lina
     */
    BigDecimal querySumSend(Integer userId);

    /**
     * 查询转入记录的总额
     * @param userId
     * @return BigDecimal
     * @date 2018-5-22
     * @author lina
     */
    BigDecimal querySumRecive(Integer userId);
}