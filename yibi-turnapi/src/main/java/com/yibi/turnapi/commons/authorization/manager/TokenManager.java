package com.yibi.turnapi.commons.authorization.manager;


import com.yibi.core.entity.User;


public interface TokenManager {

	/**
	 * 创建redis
	 * @param userId
	 * @return String
	 * @date 2017-12-23
	 * @author lina
	 */
	public String createToken(Integer userId);
	
	/**
	 * 校验token
	 * @param token
	 * @return Integer 用户Id
	 * @date 2017-12-23
	 * @author lina
	 */
	public User checkToken(String token);
	
}
