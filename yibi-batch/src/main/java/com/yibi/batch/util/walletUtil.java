package com.yibi.batch.util;

import com.alibaba.fastjson.JSON;
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
	 * 转账
	 * @param key 转账的url
	 * @param address 目标地址
	 * @param number 转账数量
	 * @return
	 */
	public static String sendCoin(String key, String address, String number){
		try {
			String url = key + address + "&amount=" + number;
			return HTTP.get(url, null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.info("转账失败");
			return "转账失败";
		}
	}

	/**
	 * 获取交易信息
	 * @param address
	 * @param type
	 * @param cm
     * @return
     */
	public static List<Map<String,String>> getListTransactions(String address, Integer type, CoinManage cm) {
		String val = cm.getListTransactions();
		if(val == null){
			log.info("【轮询充值】获取交易记录错误--币种编码【"+type+"】");
			return null;
		}
		StringBuffer url = new StringBuffer();
		url.append(val).append(address);
		String res = "";
		try {
			res = HTTP.get(url.toString(), null);
		} catch (Exception e) {
			log.info("【轮询充值】获取交易记录失败，URL错误--币种编码【"+type+"】");
			return null;
		}
		/* 验证地址是否错误 */
		if(StrUtils.isBlank(res) || res.contains("<")){
			log.info("【创建钱包】【" + "】币种编码【" +  type + "】钱包服务器发生异常错误");
			return null;
		}
		Map<String, String> resultMap = (Map<String, String>) JSON.parseObject(res, Map.class);
		Object result = resultMap.get("result");
		List<Map<String, String>> list = JSON.parseObject(result.toString(), List.class);
		if(list == null){
			log.info("【轮询充值】获取交易记录失败，地址有误--币种编码【"+type+"】");
			return null;
		}
		if(list.size() == 0 || list.isEmpty()){
			return null;
		}
		return list;
	}
	public static String getBalance(String fromAddress, Integer type, User user) throws Exception {
		if(type == CoinType.ETH){
			String url = "http://47.94.213.50:8080/api/index/getBalance?id=4&address=" + fromAddress;
			String result = HTTP.get(url, null);
			if(StrUtils.isBlank(result)){
				return null;
			}
			if(result.contains("<")){
				log.info("【币种余额转移】【" + user.getPhone() + "】币种编码【" +  type + "】钱包服务器发生异常错误");
				return null;
			}
			log.info("【币种余额转移】【成功】【" + user.getPhone() + "】【查询余额】币种编码【" +  type + "】===========余额数量：" + result);
			return result;
		}
		log.info("【币种余额转移】币种编码【" +  type + "】 币种编码错误");
		return null;
	}
	public static String getFee(Integer type) throws Exception {
		if(type == CoinType.ETH){
			String url = "http://47.94.213.50:8080/api/index/getFee?id=" + type;
			String result = HTTP.get(url, null);
			if(StrUtils.isBlank(result)){
				return null;
			}
			if(result.contains("<")){
				log.info("【币种余额转移】币种编码【" +  type + "】钱包服务器发生异常错误");
				return null;
			}
			log.info("【币种余额转移】【成功】【查询矿工费】币种编码【" +  type + "】===========实时矿工费：" + result);
			return result;
		}
		log.info("【币种余额转移】币种编码【" +  type + "】 币种编码错误");
		return null;
	}

	public static String transToMainAccount(String address, BigDecimal amount, Integer type, String secretKey, User user) {
		if(type == CoinType.ETH){
			StringBuffer s = new StringBuffer();
			s.append("http://47.94.213.50:8080/api/index/transfer?to=0xa67826bbb8617d03ccef3beba0f6fa52dc705476&from=")
					.append(address)
					.append("&amount=")
					.append(amount)
					.append("&id=").append(type)
					.append("&secretKey=").append(secretKey);
			String result = null;
			try {
				result = HTTP.get(s.toString(), null);
			} catch (Exception e) {
				log.info("【ETH】转移至主账户失败");
				e.printStackTrace();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			Gson gson = new Gson();
			map = gson.fromJson(result, map.getClass());
			if(map.get("result") == "" || map.get("result") == null || "".equals(map.get("result"))){
				return null;
			}
			result = map.get("result").toString();
			if("true".equals(result)){
				log.info("【币种余额转移】【转账成功】【" + user.getPhone() + "】币种编码【" +  type + "】 ");
				return result;
			}else{
				log.info("【币种余额转移】【失败】 【" + user.getPhone() + "】====="+ address +"=====币种编码【" + type + "】======【"+ map.get("result") +"】");
				return null;
			}
		}
		return null;
	}

	/**
	 * ETH交易数据
	 *
	 * @param address
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getEthTrans(String address) throws Exception {
		String responbody = HTTP.get(
				"http://api.etherscan.io/api?module=account&action=txlist&address=" + address + "&sort=asc", null);
		Map<String, String> resultMap = (Map<String, String>) JSON.parseObject(responbody, Map.class);
		Object result = resultMap.get("result");
		if (result == null || result.toString() == "[]" || result.toString().contains("Invalid")) {
			return null;
		}
		List<Map<String, String>> trans = JSON.parseObject(result.toString(), List.class);
		return trans;
	}
}
