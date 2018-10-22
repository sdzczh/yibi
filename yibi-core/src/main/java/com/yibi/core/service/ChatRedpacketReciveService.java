package com.yibi.core.service;

import com.yibi.core.entity.ChatRedpacket;
import com.yibi.core.entity.ChatRedpacketRecive;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:红包收取记录 chat_redpacket_recive
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface ChatRedpacketReciveService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(ChatRedpacketRecive record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(ChatRedpacketRecive record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(ChatRedpacketRecive record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(ChatRedpacketRecive record);

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
    ChatRedpacketRecive selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<ChatRedpacketRecive> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<ChatRedpacketRecive> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 查询红包接收人
     * @param userId
     * @param packetId
     * @return
     */
    List<?> queryRedPacketRecives(Integer userId,Integer packetId);

    /**
     * 根据红包Id和接收用户获取接收信息
     * @param packetId
     * @param receiveUserId
     * @return TalkRedpacketRecive
     * @date 2018-5-21
     * @author lina
     */
    ChatRedpacketRecive getReciveByPacketId(Integer packetId, Integer receiveUserId);



    /**
     * 查询接收到的红包
     * @param userId
     * @return
     * @return List<?>
     * @date 2018-5-22
     * @author lina
     */
    List<?> queryReciveList(Integer userId, Integer page, Integer rows);



    /**
     * 查询接收红包的总额
     * @param userId
     * @return BigDecimal
     * @date 2018-5-22
     * @author lina
     */
    BigDecimal querySumRecive(Integer userId);
}