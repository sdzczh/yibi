                   package com.yibi.extern.api.rongcloud.request;

import io.rong.methods.group.Group;
import io.rong.methods.group.gag.Gag;
import io.rong.models.Result;
import io.rong.models.group.GroupMember;
import io.rong.models.group.GroupModel;
import io.rong.models.group.UserGroup;
import io.rong.models.response.GagGroupUser;
import io.rong.models.response.GroupUser;
import io.rong.models.response.GroupUserQueryResult;
import io.rong.models.response.ListGagGroupUserResult;

import java.util.List;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RongCloudGroupRequest extends RongCloudRequest{
	
	/**
	 * 创建群组
	 * @param groupId 创建群组 Id。（必传）
	 * @param groupName 群组 Id 对应的名称。（必传）
	 * @param phones 要加入群的用户 ,可多个 （必传）
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-18
	 * @author lina
	 */
	public boolean  create(String groupId,String groupName,String... phones)
			throws Exception {
		Group Group = rongCloud.group;

		/**
		 * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/group/group.html#create
		 */
		GroupMember[] members = new GroupMember[phones.length];
		for(int i = 0;i<phones.length;i++){
			members[i] = new GroupMember().setId(phones[i]);
		}

		GroupModel group = new GroupModel()
				.setId(groupId)
				.setMembers(members)
				.setName(groupName);
		Result groupCreateResult = Group.create(group);
		log.info("【融云】创建群组-code:"+groupCreateResult.getCode()+",msg:"+groupCreateResult.getMsg());
		
		if(groupCreateResult.getCode()==200){
			return true;
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	/**
	 * 刷新群组
	 * @param groupId 群组 Id。（必传）
	 * @param groupName 群组名称。（必传）
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-18
	 * @author lina
	 */
	public boolean  update(String groupId,String groupName)
			throws Exception {
		Group Group = rongCloud.group;

		/**
		 * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/group/group.html#update
		 */

		GroupModel group = new GroupModel()
				.setId(groupId)
				.setName(groupName);
		Result groupCreateResult = Group.update(group);
		log.info("【融云】刷新群组信息-code:"+groupCreateResult.getCode()+",msg:"+groupCreateResult.getMsg());
		
		if(groupCreateResult.getCode()==200){
			return true;
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	/**
	 *  同步用户群组信息 当不提交group[id]=name参数时，表示解除userId对应群的绑定关系；
	 * @param phone 被同步群信息的用户 Id。（必传）
	 * @param groups 该用户所属的群信息，如群 Id 已经存在，则不会刷新对应群组名称，如果想刷新群组名称请调用刷新群组信息方法。此参数可传多个
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-18
	 * @author lina
	 */
	public boolean  sync(String phone,GroupModel... groups)
			throws Exception {
		Group Group = rongCloud.group;

		/**
		 * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/group/group.html#sync
		 */

		UserGroup user = new UserGroup();
		user.setId(phone);
		user.setGroups(groups);
		Result groupCreateResult = Group.sync(user);
		log.info("【融云】同步用户群组信息-code:"+groupCreateResult.getCode()+",msg:"+groupCreateResult.getMsg());
		
		if(groupCreateResult.getCode()==200){
			return true;
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	/**
	 * 邀请用户加入群组
	 * @param groupId 要加入的群 Id。（必传）
	 * @param groupName 要加入的群 Id 对应的名称。（必传）
	 * @param phones 要加入群的用户 Id，可提交多个，最多不超过 1000 个。（必传）
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-18
	 * @author lina
	 */
	public boolean  join( String groupId,String groupName,String... phones)
			throws Exception {
		Group Group = rongCloud.group;

		/**
		 * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/group/group.html#join
		 */
		GroupMember[] members = new GroupMember[phones.length];
		for(int i = 0;i<phones.length;i++){
			members[i] = new GroupMember().setId(phones[i]);
		}

		GroupModel group = new GroupModel()
				.setId(groupId)
				.setMembers(members)
				.setName(groupName);
		Result groupCreateResult = Group.join(group);
		log.info("【融云】加入群组-code:"+groupCreateResult.getCode()+",msg:"+groupCreateResult.getMsg());
		
		if(groupCreateResult.getCode()==200){
			return true;
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	/**
	 * 用户退出群组
	 * @param groupId 要退出的群 Id。（必传）
	 * @param phones  要退出群的用户 Id。可多传（必传）
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-18
	 * @author lina
	 */
	public boolean  quit(String groupId,String... phones)
			throws Exception {
		Group Group = rongCloud.group;

		/**
		 * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/group/group.html#quit
		 */
		GroupMember[] members = new GroupMember[phones.length];
		for(int i = 0;i<phones.length;i++){
			members[i] = new GroupMember().setId(phones[i]);
		}

		GroupModel group = new GroupModel()
				.setId(groupId)
				.setMembers(members);
		Result groupCreateResult = Group.quit(group);
		log.info("【融云】退出群组-code:"+groupCreateResult.getCode()+",msg:"+groupCreateResult.getMsg());
		
		if(groupCreateResult.getCode()==200){
			return true;
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	/**
	 * 解散群组
	 * @param phone 操作解散群的用户手机号，可以为任何用户 手机号 ，非群组创建者也可以解散群组
	 * @param groupId 要解散的群 Id
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-18
	 * @author lina
	 */
	public boolean  dismiss(String phone,String groupId)
			throws Exception {
		Group Group = rongCloud.group;

		/**
		 * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/group/group.html#dismiss
		 */
		GroupMember[] members = {new GroupMember().setId(phone)};

		GroupModel group = new GroupModel()
				.setId(groupId)
				.setMembers(members);
		Result groupCreateResult = Group.dismiss(group);
		log.info("【融云】集散群组-code:"+groupCreateResult.getCode()+",msg:"+groupCreateResult.getMsg());
		
		if(groupCreateResult.getCode()==200){
			return true;
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	/**
	 * 查看群成员
	 * @param groupId 群 Id。（必传）
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-18
	 * @author lina
	 */
	public List<GroupUser>  query(String groupId)
			throws Exception {
		Group Group = rongCloud.group;

		/**
		 * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/group/group.html#query
		 */

		GroupModel group = new GroupModel()
				.setId(groupId);
		GroupUserQueryResult groupCreateResult = Group.get(group);
		log.info("【融云】查看群成员-code:"+groupCreateResult.getCode()+",msg:"+groupCreateResult.getMsg());
		
		if(groupCreateResult.getCode()==200){
			return groupCreateResult.getMembers();
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	
	/**
	 * 查询被禁言群成员
	 * @param groupId 群组 Id。（必传）
	 * @throws Exception
	 * @return List<GagGroupUser>
	 * @date 2018-5-18
	 * @author lina
	 */
	public List<GagGroupUser>  GagGetList(String groupId)
			throws Exception {
		 Gag Gag = rongCloud.group.gag;

		/**
		 * API 文档:  http://rongcloud.github.io/server-sdk-nodejs/docs/v1/group/gag.html#getList
		 */
		 
		ListGagGroupUserResult result = Gag.getList(groupId);
		
		log.info("【融云】查询被禁言群成员-code:"+result.getCode()+",msg:"+result.getMsg());
		
		if(result.getCode()==200){
			return result.getMembers();
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	/**
	 * 添加禁言
	 * @param groupId 群组 Id。（必传）
	 * @param minute 禁言时长，以分钟为单位，最大值为43200分钟，为 0 表示永久禁言。（必传）
	 * @param phones 用户 Id。（必传） 可传多个
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-20
	 * @author lina
	 */
	public boolean  GagAdd(String groupId, int minute,String... phones)
			throws Exception {
		 Gag Gag = rongCloud.group.gag;

		/**
		 * API 文档:  http://rongcloud.github.io/server-sdk-nodejs/docs/v1/group/gag.html#add
		 */
		GroupMember[] members = new GroupMember[phones.length];
		for(int i = 0;i<phones.length;i++){
			members[i] = new GroupMember().setId(phones[i]);
		}

		GroupModel group = new GroupModel()
				.setId(groupId)
				.setMembers(members)
				.setMinute(minute);
		Result groupCreateResult = Gag.add(group);
		log.info("【融云】添加禁言群成员-code:"+groupCreateResult.getCode()+",msg:"+groupCreateResult.getMsg());
		
		if(groupCreateResult.getCode()==200){
			return true;
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	/**
	 * 移除禁言群成员
	 * @param groupId 群组 Id。（必传）
	 * @param phones 用户 Id，支持同时移除多个群成员。（必传）
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-18
	 * @author lina
	 */
	public boolean  GagRemove(String groupId,String... phones)
			throws Exception {
		 Gag Gag = rongCloud.group.gag;

		/**
		 * API 文档:  http://rongcloud.github.io/server-sdk-nodejs/docs/v1/group/gag.html#remove
		 */
		GroupMember[] members = new GroupMember[phones.length];
		for(int i = 0;i<phones.length;i++){
			members[i] = new GroupMember().setId(phones[i]);
		}

		GroupModel group = new GroupModel()
				.setId(groupId)
				.setMembers(members);
		Result groupCreateResult = Gag.remove(group);
		log.info("【融云】移除禁言群成员-code:"+groupCreateResult.getCode()+",msg:"+groupCreateResult.getMsg());
		
		if(groupCreateResult.getCode()==200){
			return true;
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}

}
