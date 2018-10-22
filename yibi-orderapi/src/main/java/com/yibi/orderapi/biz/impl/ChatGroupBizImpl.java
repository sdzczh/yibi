package com.yibi.orderapi.biz.impl;

import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.ChatGroup;
import com.yibi.core.entity.ChatGroupUser;
import com.yibi.core.entity.User;
import com.yibi.core.service.ChatGroupService;
import com.yibi.core.service.ChatGroupUserService;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import com.yibi.extern.api.rongcloud.request.RongCloudConversationRequest;
import com.yibi.extern.api.rongcloud.request.RongCloudGroupRequest;
import com.yibi.orderapi.biz.ChatGroupBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import io.rong.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
@Service
@Transactional
public class ChatGroupBizImpl implements ChatGroupBiz {
    @Autowired
    private ChatGroupService chatGroupService;
    @Autowired
    private ChatGroupUserService chatGroupUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private RongCloudGroupRequest groupRequest;
    @Autowired
    private RongCloudConversationRequest conversationRequest;
    @Autowired
    private SysparamsService sysparamsService;

    @Override
    public String queryList(Integer type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", chatGroupService.queryByType(type));

        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String queryGroupInfo(User user, String groupId) {
        Map<Object, Object> map = new HashMap();
        ChatGroup group = chatGroupService.queryByGroupId(groupId);
        if(group == null){
            return Result.toResult(ResultCode.TAKL_GROUP_NOT_EXIST);
        }
        map.put("talkgroupid",group.getId());
        int count = chatGroupUserService.selectCount(map);
        List<?> list = chatGroupUserService.queryUsers(groupId, user.getId(), 0, 9);

        ChatGroupUser gu = chatGroupUserService.getByGroupIdAndUserId(group.getId(),user.getId());

        map.remove("talkgroupid");
        map.put("name", group.getName());
        map.put("decription", group.getDecription());
        map.put("imgUrl", group.getImgurl());
        map.put("num", count);
        map.put("list", list);
        map.put("isMuted", gu==null?false:gu.getIsmuted());
        return Result.toResult(ResultCode.SUCCESS,map);

    }

    @Override
    public String queryUsers(User user, String groupId, Integer page, Integer rows) {

        Map<String, Object> map = new HashMap();
        page = page ==null?0:page;
        rows = rows ==null?10:rows;
        List<?> list = chatGroupUserService.queryUsers(groupId, user.getId(), page, rows);

        map.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String joinGroup(User user, String groupId, Integer type) {
        ChatGroup group = chatGroupService.queryByGroupId(groupId);
        if(group == null){
            return Result.toResult(ResultCode.TAKL_GROUP_NOT_EXIST);
        }
        if(type == GlobalParams.GROUP_TYPE_ROOM){
            if(group.getType().intValue() != type){
                return Result.toResult(ResultCode.TAKL_GROUP_TYPE_ERROR);
            }
            //加入群组
            joinRongCloudGroup(group,user);

            return Result.toResult(ResultCode.SUCCESS);
        }else{
            return Result.toResult(ResultCode.PERMISSION_NO_OPEN);
        }
    }

    @Override
    public String leaveGroup(User user, String groupId) {
        ChatGroup group = chatGroupService.queryByGroupId(groupId);
        if(group ==null ){
            return Result.toResult(ResultCode.TAKL_GROUP_NOT_EXIST);
        }
        ChatGroupUser gu = chatGroupUserService.getByGroupIdAndUserId(group.getId(), user.getId());
        if(gu.getRole()!=GlobalParams.GROUP_ROLE_OWNER){
            chatGroupUserService.deleteByPrimaryKey(gu.getId());

            try {
                groupRequest.quit(group.getGroupid(), user.getPhone());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }else{
            return Result.toResult(ResultCode.TAKL_GROUP_OWNER_CANNOT_LEAVE);
        }
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public void joinChatRoomAuto(User user) throws Exception {
        ChatGroup lastGroup = null;
        boolean joined = false;
        String groupTemplateName = sysparamsService.getValStringByKey(SystemParams.GROUP_TEMPLATE_NAME);
        List<ChatGroup> groups = chatGroupService.queryByTypeAndName(GlobalParams.GROUP_TYPE_ROOM,groupTemplateName);
        if(groups !=null &&! groups.isEmpty()){
            lastGroup = groups.get(0);
            Map<Object, Object> map = new HashMap();
            for(ChatGroup group :groups){
                map.put("talkgroupid",group.getId());
                int count = chatGroupUserService.selectCount(map);
                if(count<GlobalParams.GROUP_MAX_NUMBER){
                    joinRongCloudGroup(group,user);
                    joined = true;
                    break;
                }
            }
        }
        if(!joined){
            ChatGroup newGroup = createRongCloudGroup(lastGroup,groupTemplateName);
            joinRongCloudGroup(newGroup,user);
        }
    }

    @Override
    public String mute(User user, String groupId) {
        ChatGroup group = chatGroupService.queryByGroupId(groupId);
        if(group ==null ){
            Result.toResult(ResultCode.TAKL_GROUP_NOT_EXIST);
        }
        ChatGroupUser gu = chatGroupUserService.getByGroupIdAndUserId(group.getId(), user.getId());
        if(gu==null){
            return Result.toResult(ResultCode.TAKL_GROUP_USER_NOT_EXIST);
        }
        gu.setIsmuted(!gu.getIsmuted());

		/*设置融云免打扰*/
        try {
            conversationRequest.mute(CodeUtil.ConversationType.GROUP, user.getPhone(), group.getGroupid(), gu.getIsmuted());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        chatGroupUserService.updateByPrimaryKey(gu);
        Map<String, Object> map = new HashMap<>();
        map.put("isMuted", gu.getIsmuted());
        return Result.toResult(ResultCode.SUCCESS,map);
    }


    /**
     * 创建新群
     * @param lastGroup
     * @param templateName
     * @throws Exception
     * @return void
     * @date 2018-6-7
     * @author lina
     */
    public  ChatGroup createRongCloudGroup(ChatGroup lastGroup,String templateName) throws Exception{
        /*生成新的群名称和id*/
        String newGroupName;
        String newGroupId;
        if(lastGroup==null){
            newGroupName = templateName+"1群";
            newGroupId = GlobalParams.GROUP_TEMPLATE_ID+"1";
        }else{
            int index = 0;
            try {
                String suffix = lastGroup.getName().substring(templateName.length(),lastGroup.getName().length()-1);
                index = Integer.parseInt(suffix);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            index++;

            newGroupName = templateName+index+"群";
            newGroupId = GlobalParams.GROUP_TEMPLATE_ID+index;
        }

		/*创建群信息*/
        ChatGroup group = new ChatGroup();
        group.setGroupid(newGroupId);
        group.setName(newGroupName);
        group.setDecription(sysparamsService.getValStringByKey(SystemParams.GROUP_TEMPLATE_DECRIPTION));
        group.setType(GlobalParams.GROUP_TYPE_ROOM);
        group.setImgurl(sysparamsService.getValStringByKey(SystemParams.GROUP_TEMPLATE_IMGURL));
        chatGroupService.insert(group);

        //群主
        User groupOwner = userService.selectByPhone(GlobalParams.GROUP_OWNER_PHONE );

		/*融云创建群*/
        groupRequest.create(newGroupId, newGroupName,GlobalParams.GROUP_OWNER_PHONE);


		/*添加群主信息*/
        if(groupOwner!=null){
            ChatGroupUser groupUser = new ChatGroupUser();
            groupUser.setUserid(groupOwner.getId());
            groupUser.setTalkgroupid(group.getId());
            groupUser.setRole(GlobalParams.GROUP_ROLE_OWNER);
            groupUser.setState(GlobalParams.ACTIVE);
            groupUser.setIsmuted(false);
            chatGroupUserService.insert(groupUser);
        }
        return group;
    }

    /**
     * 加入群组
     * @param group
     * @param user
     * @return void
     * @date 2018-6-7
     * @author lina
     */
    public void joinRongCloudGroup(ChatGroup group,User user){
        ChatGroupUser gu = chatGroupUserService.getByGroupIdAndUserId(group.getId(),user.getId());
        if(gu == null ){
            ChatGroupUser groupUser = new ChatGroupUser();
            groupUser.setUserid(user.getId());
            groupUser.setTalkgroupid(group.getId());
            groupUser.setState(GlobalParams.ACTIVE);
            groupUser.setRole(GlobalParams.GROUP_ROLE_MEMBER);
            groupUser.setIsmuted(false);
            if(groupUser.getId() == null){
                chatGroupUserService.insert(groupUser);
            }else{
                chatGroupUserService.updateByPrimaryKey(groupUser);
            }

            //融云用户添加群组
            try {
                groupRequest.join(group.getGroupid(), group.getName(), user.getPhone());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }
}
