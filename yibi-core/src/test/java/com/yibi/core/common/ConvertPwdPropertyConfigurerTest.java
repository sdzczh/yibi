package com.yibi.core.common;

import org.junit.Test;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/11 version: 1.0
 */
public class ConvertPwdPropertyConfigurerTest {

  @Test
  public void testEncryptAndDecrypt() throws Exception {
    CustomPropertyPlaceHoderConfigurer
        convertPropertyConfigurer = new CustomPropertyPlaceHoderConfigurer();
    //String key = "jdbc.password";
    String key = "jdbc.username";
    String value = "Newyibi";
    String encryptVal = convertPropertyConfigurer.encrypt(key, value);
    System.out.println("密码："+encryptVal);
    System.out.println("解密后："+convertPropertyConfigurer.decrypt(key, encryptVal));

  }

}
