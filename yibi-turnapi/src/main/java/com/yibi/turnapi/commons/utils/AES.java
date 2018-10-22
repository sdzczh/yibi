package com.yibi.turnapi.commons.utils;

import com.alibaba.fastjson.JSON;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;

public class AES {
	/** 填充模式 */
    private static final String transformation = "AES/CBC/NoPadding";
    private static final String IV_STRING = "xpLbp7JdqU49LJuz";
    /** 
     * 加密 
     *  
     * @param content 需要加密的内容 
     * @param password 加密密码 
     * @return 
     */
    public static String encrypt(String content, String password) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(IV_STRING.getBytes());
            SecretKeySpec key1 = new SecretKeySpec(password.getBytes(),"AES");
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, key1, zeroIv);
            byte[] encryptedData = cipher.doFinal(content.getBytes("utf8"));
            return BASE64.encoder(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String decrypt(String content, String password) {
        try {
            byte[] decryptFrom = BASE64.decoderByte(content);
            IvParameterSpec zeroIv = new IvParameterSpec(IV_STRING.getBytes());
            SecretKeySpec key1 = new SecretKeySpec(password.getBytes(),"AES");
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, key1, zeroIv);
            byte decryptedData[] = cipher.doFinal(decryptFrom);
            return new String(decryptedData,"utf8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        String enStr  = AES.encrypt("lvmeihe","75570b81b65a471f");
//        System.out.println(enStr);
		String deStr = AES.decrypt("Am1gLZTtFzC0hFMmPoESPqGJpB3zo7t59huy+a7Q31g=", "385f5ad890624f38");
        System.out.printf("--"+deStr+"--");
        Map<String,Object> map = (Map<String, Object>)JSON.parse(deStr.trim());
		System.out.println(map);
	}
      
   
}
