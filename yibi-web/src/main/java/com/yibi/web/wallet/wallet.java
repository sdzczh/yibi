package com.yibi.web.wallet;

import com.yibi.common.encrypt.MD5;
import com.yibi.common.utils.HTTP;

import java.math.BigDecimal;

public class wallet {

	/**
	 * 转账btc ltc doge oio
	 * @param key 转账的url
	 * @param address 目标地址
	 * @param number 转账数量
	 * @return
	 */
	public static String transferCommon(String key, String address, String number){
		try {
			String url = key + address + "&amount=" + number;
			return HTTP.get(url, null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "false";
		}
	}

	/**
	 * ETH转账
	 * @param address
	 * @param remain
	 * @param type
     * @return
     */
	public static String transferETH(String address, String remain, Integer type){
		String key = null;
		try {
			key = MD5.getMd5(address + remain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "http://47.94.213.50:8080/api/index/transferMain?id=" + type + "&amount=" + remain + "&address=" + address + "&key=" + key;
		try {
			return HTTP.get(url, null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "false";
		}
	}

	/**
	 * YT转账
	 */
	public void transferYT(String address, String remain) {
		EthTokenAPI tokenAPI = new EthTokenAPI("47.93.8.48", "9876",  "0xc1b68eebcd2842e475f47ca115967227e3c66f98", "0xa9059cbb", "0x70a08231",new BigDecimal(100000000));
		String from = "0xeef0fdd7fe2c572479f0012f72a49c2675dfec99";
		BigDecimal value = new BigDecimal(remain); //这里请使用字符串
		String from_pwd = "123456789";
		if (tokenAPI.unlockAccount(from, from_pwd)) {
			String txid = tokenAPI.sendTransaction(from, address, API.unit10To16(value.multiply(tokenAPI.getWei())));
			if (!txid.equals("")) {
				System.out.println("转账成功...");
			} else {
				System.out.println("转账失败...");
			}
		} else {
			System.out.println("账户解锁失败，无法转账....");
		}
	}
}
