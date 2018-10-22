package com.yibi.core.service;

import com.yibi.core.entity.ChatGroupUser;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface ChatGroupUserService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(ChatGroupUser record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(ChatGroupUser record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(ChatGroupUser record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(ChatGroupUser record);

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
    ChatGroupUser selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<ChatGroupUser> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<ChatGroupUser> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    List<?> queryUsers(String groupId, Integer userId, Integer page, Integer rows);

    ChatGroupUser getByGroupIdAndUserId(Integer talkGroupId,Integer userId);

    /**
     * 后台条件查询
     * @param map
     * @return
     */
    List<Map<String,Object>>selectConditionPaging(Map<Object,Object> map);
    /**
     * 后台条件查询
     * @param map
     * @return
     */
    int selectConditionCount(Map<Object,Object> map);

    /**
     * 通过群组Id删除所有群组用户
     * @param talkGroupId
     * @return
     */
    int deleteAllByTalkGroupId(Integer talkGroupId);
}