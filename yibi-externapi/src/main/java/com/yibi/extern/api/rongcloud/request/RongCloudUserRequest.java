package com.yibi.extern.api.rongcloud.request;

import io.rong.methods.user.User;
import io.rong.methods.user.onlinestatus.OnlineStatus;
import io.rong.models.Result;
import io.rong.models.response.CheckOnlineResult;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RongCloudUserRequest extends RongCloudRequest{
	
	/**
	 * 获取token
	 * @param phone 用户 Id，支持大小写英文字母、数字、部分特殊符号 + | = - _ 的组合方式，最大长度 64 字节。是用户在 App 中的唯一标识码，必须保证在同一个 App 内不重复，重复的用户 Id 将被当作是同一用户。（必传）
	 * @param name 用户名称，最大长度 128 字节。用来在 Push 推送时显示用户的名称。（必传）
	 * @param headImgUrl 用户头像 URI，最大长度 1024 字节。（必传）
	 * @throws Exception
	 * @return String
	 * @date 2018-5-18
	 * @author lina
	 */
	public String getToken(String phone, String name, String headImgUrl)
			throws Exception {
		User rongUser = rongCloud.user;
		UserModel model = new UserModel();
		model.setId(phone);
		model.setName(name);
		model.setPortrait(headImgUrl);
		TokenResult result = rongUser.register(model);
		log.info("【融云】获取用户token-code:{},mas:{}",result.getCode(),result.getMsg());
		
		if(result.getCode()==200){
			return result.getToken();
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	/**
	 * 更新用户信息
	 * @param phone 用户 Id，支持大小写英文字母、数字、部分特殊符号 + | = - _ 的组合方式，最大长度 64 字节。是用户在 App 中的唯一标识码，必须保证在同一个 App 内不重复，重复的用户 Id 将被当作是同一用户。（必传）
	 * @param name 用户名称，最大长度 128 字节。用来在 Push 推送时，显示用户的名称，刷新用户名称后 5 分钟内生效。（可选，提供即刷新，不提供忽略）
	 * @param headImgUrl 用户头像 URI，最大长度 1024 字节。用来在 Push 推送时显示。（可选，提供即刷新，不提供忽略）
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-18
	 * @author lina
	 */
	public boolean update(String phone, String name, String headImgUrl) throws Exception{
		User rongUser = rongCloud.user;
		UserModel model = new UserModel();
		model.setId(phone);
		model.setName(name);
		model.setPortrait(headImgUrl);
		Result result = rongUser.update(model);
		log.info("【融云】获取更新用户信息-code:{},mas:{}",result.getCode(),result.getMsg());
		
		if(result.getCode()==200){
			return true;
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}
	
	/**
	 * 检查用户在线状态
	 * @param phone 用户 Id。（必传）
	 * @return true:在线  false：不在线
	 * @throws Exception
	 * @return boolean
	 * @date 2018-5-18
	 * @author lina
	 */
	public boolean checkOnline(String phone) throws Exception{
		OnlineStatus onlineStatus = rongCloud.user.onlineStatus;
		UserModel model = new UserModel();
		model.setId(phone);
		CheckOnlineResult result = onlineStatus.check(model);
		
		log.info("【融云】获取更新用户信息失败:code:{},mas:{}",result.getCode(),result.getMsg());
		if(result.getCode()==200){
			return "1".equals(result.getStatus());
		}else{
			throw new RuntimeException("融云接口调用异常");
		}
	}

}
