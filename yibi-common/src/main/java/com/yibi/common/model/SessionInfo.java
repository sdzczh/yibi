package com.yibi.common.model;

import lombok.Data;

import java.util.List;
@Data
public class SessionInfo implements java.io.Serializable {

	private Integer userid;// 用户ID
	private String userAccount;// 登录名
	private String userName;// 姓名
	private String token;// 用户IPname
	private Integer type;
	private List<String> resourceList;// 用户可以访问的资源地址列表
	private List<String> resourceAllList;
	private List<Integer> roleList;

	public SessionInfo(Integer userid, String userAccount, String userName, String token, Integer type, List<String> resourceList, List<String> resourceAllList, List<Integer> roleList) {
		this.userid = userid;
		this.userAccount = userAccount;
		this.userName = userName;
		this.token = token;
		this.type = type;
		this.resourceList = resourceList;
		this.resourceAllList = resourceAllList;
		this.roleList = roleList;
	}
}
