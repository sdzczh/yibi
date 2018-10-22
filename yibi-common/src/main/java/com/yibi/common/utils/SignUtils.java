package com.yibi.common.utils;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.yibi.common.encrypt.MD5;
import lombok.extern.log4j.Log4j;

public class SignUtils {

	/**
	 * 生成签名：按照params的key值排序
	 * @param params
	 * @param key
	 * @throws Exception
	 * @return String
	 * @date 2017-12-25
	 * @author lina
	 */
	public static String createSign(Map<String, Object> params,String key) throws Exception{
		Set<String> keySet = params.keySet();
		keySet.remove("secretKey");
		Object[] names = keySet.toArray();
		Arrays.sort(names);
		StringBuffer signBuf = new StringBuffer();
		boolean first = true;
		for(Object name:names){
			if(first){
				first=false;
			}else{
				signBuf.append("&");
			}
			signBuf.append(name).append("=");
			Object value = params.get(name);
			if(null!=value){
				signBuf.append(URLEncoder.encode(String.valueOf(value), "UTF-8"));
			}
		}
		if(!StrUtils.isBlank(key)){
			signBuf.append("&key=").append(key);
		}
		//log.info("签名前字符串："+signBuf.toString());
		return  MD5.getMd5(signBuf.toString()).toUpperCase();
	}
}
