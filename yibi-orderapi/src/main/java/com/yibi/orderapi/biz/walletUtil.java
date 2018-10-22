package com.yibi.orderapi.biz;

import com.google.gson.Gson;
import com.yibi.common.utils.HTTP;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class walletUtil {

	/**
	 * 创建钱包
	 * @param key 创建钱包的url
	 * @return
	 */
	public static String createAddress(String key){
		try {
			return HTTP.get(key,null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	/**
	 * 创建/获取钱包地址
	 * @param coinType 币种编码
	 * @param userId 用户id
	 * @return 绑定的地址
	 */ 
	public static String getAddress(Integer coinType, Integer userId){
		StringBuffer s = new StringBuffer();
		s.append("http://47.93.217.195:81/index.php?s=/index/dk/bindUser?id=");
		try {
			s.append(coinType).append("&uid").append(userId);
			return HTTP.get(s.toString(),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.info("钱包创建失败");
			return "钱包创建失败";
		}
	}
	/**
	 * 创建ETH钱包
	 * @param key 创建钱包的url
	 * @param coinType 
	 * @return
	 */
	public static String createETHAddress(String key, Integer coinType){
		try {
			return HTTP.get(key + coinType,null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.info("【ETH】钱包创建失败");
			return "钱包创建失败";
		}
	}
	

}
