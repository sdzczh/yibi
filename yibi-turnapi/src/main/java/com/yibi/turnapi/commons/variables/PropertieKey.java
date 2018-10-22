package com.yibi.turnapi.commons.variables;

import java.util.ArrayList;
import java.util.List;

public class PropertieKey {

	/**
	 * 获取加密key
	 * @return
	 */
	public static List<String> keys(){
		List<String> keys = new ArrayList<>();
		
		/*Druid数据库账号密码*/
		keys.add("druid.username");
		keys.add("druid.password");
		
		/*Redis密码*/
		keys.add("redis.password");
		return keys;
	}
}
