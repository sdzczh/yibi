package com.yibi.common.encrypt;

import java.security.MessageDigest;

/**
 * @描述 MD5(散列)加密
 * @author administrator
 * @版本 V1.0.0
 * @日期 2017-6-6
 */
public class MD5{
	
	private MD5(){}
	
	/**
	 * @描述 MD5加密<br>
	 * @param data 明文
	 * @return 密文
	 * @throws Exception 
	 * @版本 v1.0.0
	 * @日期 2017-6-6
	 */
	public static String getMd5(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data.getBytes("utf8"));
		byte[] b = md.digest();

		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			int i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}
	
	/**
	 * @描述 测试方法<br>
	 * @param data 明文  可接收NULl值，默认:这是密文 This is test function
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-5
	 */
	public static void test(String data) throws Exception{
		if(data==null || "".equals(data)){
			data="123456";
		}
		System.out.println("【明文】:"+data);
		String enStr = getMd5(data);
		System.out.println("【密文】:"+enStr);
		System.out.println("*******************************************************");
	}

	public static void main(String[] args) throws Exception {
		test(null);
	}
}
