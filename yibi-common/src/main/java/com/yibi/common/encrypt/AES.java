package com.yibi.common.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	/** 填充模式 */  
    private static final String transformation = "AES/CBC/PKCS5Padding";   
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
		String str = "{\"deviceNum\":\"ipone11.5\",\"amount\":10,\"price\":100,\"timeStamp\":\"1516783771128\",\"unitCoin\":\"0\",\"syetemType\":2,\"orderCoin\":\"6\",\"password\":\"ss123\"}";
		String key = "5274e323bb474c36";
		String enStr = AES.encrypt(str, key);
		String deStr = AES.decrypt(enStr, key);
		System.out.println("明文-->"+str);
		System.out.println("密文-->"+enStr);
		System.out.println("解密后-->"+deStr);
	}
      
   
}
