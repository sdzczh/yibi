package com.yibi.turnapi.commons.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;


/**
 * @描述 Base64(编码)加密解密
 * @see BASE64Decoder sun.misc.BASE64Encoder
 * @author administrator 
 * @版本 V1.0.0 
 * @日期 2017-8-4
 */
public class BASE64 {
	
	private BASE64(){}
	
	/**
	 * @描述 Base64加密<br>
	 * @param text 加密内容
	 * @return Base64加密值
	 * 版本 v1.0.0
	 * 日期 2017-6-6
	 */
	public static String encoder(byte[] text) {
		return new BASE64Encoder().encode(text);
	}
	
	/**
	 * @描述 Base64加密<br>
	 * @param text 加密内容
	 * @return Base64加密值
	 * 版本 v1.0.0
	 * 日期 2017-6-6
	 * @throws Exception 
	 */
	public static String encoder(String text) throws Exception {
		return new BASE64Encoder().encode(text.getBytes("utf8"));
	}
	
	/**
	 * @描述 Base64解密<br>
	 * @param text 解密内容
	 * @return Base64解密值
	 * @throws IOException 
	 * @版本 v1.0.0
	 * @日期 2017-6-6
	 */
	public static String decoder(String text) throws IOException {
			return new String(new BASE64Decoder().decodeBuffer(text),"utf8");
	}
	
	/**
	 * @描述 Base64解密<br>
	 * @param text 解密内容
	 * @return Base64解密值数组
	 * @throws IOException 
	 * @版本 v1.0.0
	 * @日期 2017-6-6
	 */
	public static byte[] decoderByte(String text) throws IOException {
		return new BASE64Decoder().decodeBuffer(text);
	}

	/**
	 * @描述 测试方法<br>
	 * @param data 明文  可接收NULl值，默认:Crystal项目Jar封装测试
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-5
	 */
	public static void test(String data) throws Exception {
		if(data==null || "".equals(data)){
			data="Crystal项目Jar封装测试";
		}
		System.out.println("【明文】:"+data);
		String enStr = encoder(data.getBytes());
		System.out.println("【密文】:"+enStr);
		String deStr = decoder(enStr);
		System.out.println("【解密明文】:"+deStr);
		
		String deStrByte = new String(decoderByte(enStr));
		System.out.println("【解密明文Byte】:"+deStrByte);
		System.out.println("*******************************************************");
	}
	
	public static void main(String[] args) throws Exception {
		String test = null;
		test="7d431e42dfa6a6d693ac2d04:5e987ac6d2e04d95a9d8f0d1";
		test(test);
	}
}