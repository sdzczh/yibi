package com.yibi.turnapi.commons.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * @描述 DES(对称)加密解密<br>
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-6-7
 */
public class DES {
	
	private DES(){}
	
	private static final String DES="DES";
	
	/**
	 * @描述 DES加密<br>
	 * @param data 明文
	 * @param key 向量
	 * @return 密文
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-5
	 */
    public static String encrypt(String data,String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }
 
    /**
     * @描述 DES解密<br>
     * @param data 密文
     * @param key 向量
     * @return 解密明文s
     * @throws IOException
     * @throws Exception
     * @author administrator
     * @版本 v1.0.0
     * @日期 2017-8-5
     */
    public static String decrypt(String data,String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes());
        return new String(bt);
    }
 
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
     
     
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
    
	/**
	 * @描述 工具类测试方法<br>
	 * @param data 明文 可接收NULl值，默认:这是密文 This is test function
	 * @param key 向量 可接收NULl值，默认:Cr!ys#tal@
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-5
	 */
    public static void test(String data,String key) throws Exception{
    	if(data==null || "".equals(data)){
    		data="这是密文 This is test function";
    	}
    	if(key==null || "".equals(key)){
    		key="Cr!ys#tal@";
    	}
    	System.out.println("【明文】:"+data);
    	System.out.println("【向量】:"+key);
    	
    	String enStr = encrypt(data, key);
    	System.out.println("【密文】:"+enStr);
    	
    	String deStr = decrypt(enStr, key);
    	System.out.println("【解密明文】:"+deStr);
		System.out.println("*******************************************************");
    }
    
    public static void main(String[] args) throws Exception {
		test(null,null);
	}
    	
}
