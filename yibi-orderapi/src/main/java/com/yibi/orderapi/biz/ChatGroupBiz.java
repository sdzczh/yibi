package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public interface ChatGroupBiz {
    /**
     * 查询群组列表
     * @param type
     * @return
     * @return List<?>
     * @date 2018-5-19
     * @author lina
     */
    String queryList(Integer type);

    /**
     * 查询群组详情
     * @param groupId
     * @return String
     * @date 2018-5-20
     * @author lina
     */
    String queryGroupInfo(User user, String groupId);

    /**
     * 查询群组成员列表
     * @param groupId
     * @return
     * @return String
     * @date 2018-5-19
     * @author lina
     */
    String queryUsers(User user, String groupId, Integer page, Integer rows);

    /**
     * 加入群组
     * @param user
     * @param groupId
     * @param type
     * @return
     * @return String
     * @date 2018-5-19
     * @author lina
     */
    String joinGroup(User user, String groupId, Integer type);

    /**
     * 离开群组
     * @param user
     * @param groupId
     * @return String
     * @date 2018-5-19
     * @author lina
     */
    String leaveGroup(User user, String groupId);

    /**
     * 自动加入群
     * @param user
     * @return void
     * @date 2018-6-7
     * @author lina
     */
    void joinChatRoomAuto(User user) throws Exception;

    /**
     * 设置消息免打扰，解除解除免打扰
     * @param user
     * @param groupId
     * @return String
     * @date 2018-6-8
     * @author lina
     */
    String mute(User user, String groupId);

}
