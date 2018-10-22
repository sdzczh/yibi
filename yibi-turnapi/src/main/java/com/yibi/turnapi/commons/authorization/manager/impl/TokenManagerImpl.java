package com.yibi.turnapi.commons.authorization.manager.impl;
import com.yibi.core.entity.User;
import com.yibi.core.service.UserService;
import com.yibi.turnapi.commons.authorization.manager.TokenManager;
import com.yibi.turnapi.commons.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenManagerImpl implements TokenManager {

	
	@Autowired
	private UserService userService;
	
	
	@Override
	public String createToken(Integer userId) {
		String uuid = UUID.randomUUID().toString().replace("-","");
		return uuid;
	}

	@Override
	public User checkToken(String token) {
		if(StrUtils.isBlank(token)){
			return null;
		}
		Map<Object, Object> params = new HashMap<Object, Object>();
		params.put("token", token);
		List<User> list = userService.selectAll(params);
		User user = null;
		if(list != null && !list.isEmpty()){
			user = list.get(0);
		}
		return user==null?null:user;
	}



}
