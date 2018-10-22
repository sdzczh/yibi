package com.yibi.common.encrypt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


/**
 * @描述 RSA(非对称)加密解密   RSA/None/NoPadding <br>
 * 1、明文长度(bytes) <= 密钥长度(bytes)-11；密文长度等于密钥长度.当然这是不分片情况下的.分片后,密文长度=密钥长度*片数； 该类暂不支持分片；
 * 2、android系统的RSA实现是"RSA/None/NoPadding"，而标准JDK实现是"RSA/None/PKCS1Padding" ，
 * 这造成了在android机上加密后无法在服务器上解密的原因（服务器使用的是SUN JDK6.0）。其实只要加载bouncycastle Jar到PC服务端和android客户端即可。
 * 例：KeyPairGenerator.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
 * 3、JDK支持的密钥长度：RSA keys must be at least 512 bits long； RSA keys must be no longer than 16384 bits
 * 4、使用X509EncodedKeySpec生成公钥、使用PKCS8EncodedKeySpec私钥， X509和PKCS8的区别：
 * 
 * 两者同属第一代PKI（Public Key Infrastructure 公钥基础设施）标准，同属第一代还有... 第二代是在2001年，
 * 由微软、VeriSign和webMethods三家公司发布了XML密钥管理规范（XML Key Management Specification，XKMS），被称为第二代PKI标准。
 * 
 * X.509是由国际电信联盟（ITU-T）制定的数字证书标准。在X.500确保用户名称惟一性的基础上，X.509为X.500用户名称提供了通信实体的鉴别机制，
 * 并规定了实体鉴别过程中广泛适用的证书语法和数据接口。 
 * X.509的最初版本公布于1988年。X.509证书由用户公共密钥和用户标识符组成。此外还包括版本号、证书序列号、CA标识符、签名算法标识、签发者名称、证书有效期等信息。
 * 这一标准的最新版本是X.509 v3，它定义了包含扩展信息的数字证书。该版数字证书提供了一个扩展信息字段，用来提供更多的灵活性及特殊应用环境下所需的信息传送。
 * 
 * PKCS是由美国RSA数据安全公司及其合作伙伴制定的一组公钥密码学标准，其中包括证书申请、证书更新、证书作废表发布、扩展证书内容以及数字签名、数字信封的格式等方面的一系列相关协议。
 * PKCS#8：描述私有密钥信息格式，该信息包括公开密钥算法的私有密钥以及可选的属性集等。 
 * 其他常见的标准有：
 * PKCS#7：定义一种通用的消息语法，包括数字签名和加密等用于增强的加密机制，PKCS#7与PEM兼容，所以不需其他密码操作，就可以将加密的消息转换成PEM消息。 
 * PKCS#10：描述证书请求语法。 
 * PKCS#12：描述个人信息交换语法标准。描述了将用户公钥、私钥、证书和其他相关信息打包的语法。 
 * 
 * 此外，
 * CA中心普遍采用的规范是X.509[13]系列和PKCS系列
 * 本质上，X509、PKCS12都是数字文档，需要通过配合一定的二进制编码使用，如DER、PEM（用于Base64编码，如各种X509 v3证书）
 * 文件保存时，DER、PEM也可以作为文件拓展名，也可使用其他的拓展名，如CRT（DER或PEM编码）、CER（CRT的微软版）、KEY（DER或PEM编码的PKCS#8公钥或私钥）
 * 
 * 5、私钥保密，公钥可以公开；利用私钥可以生成公钥
 * 6、由于RSA加密速度慢的问题，在实际应用中，往往配合对成加密算法使用（用RSA加密其密钥）
 * 7、用RSA对信息摘要进行加密来生成“数字签名”；由CA机构统一发布的包含公钥等信息的文件称为“数字证书”
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-8-5
 */
public class RSA {

	private RSA(){}
	
	/**
	 * @描述 生成公钥私钥<br>
	 * @return MAP<String,Object>(){"public":公钥对象,"private":私钥对象}
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-5
	 */
	public static Map<String, Object> getKey() throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();  
		 	KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
	        keyPairGen.initialize(1536);  
	        KeyPair keyPair = keyPairGen.generateKeyPair();  
	        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
	        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
	        map.put("public", publicKey);  
	        map.put("private", privateKey);  
	        return map;  
	}
	
	/**
	 * @描述 获取公钥对象<br>
	 * @param publicKey 公钥Base64解码后字节数组
	 * @return 公钥对象
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-5
	 */
	public static RSAPublicKey getPublikKey(byte[] publicKey) throws Exception{
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);  
        KeyFactory factory = KeyFactory.getInstance("RSA");  
        PublicKey p = factory.generatePublic(keySpec);  
        return (RSAPublicKey) p;
	}
	
	/**
	 * @描述 获取私钥对象<br>
	 * @param privateKey 私钥Base64解码后字节数组
	 * @return 私钥对象
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-5
	 */
	public static RSAPrivateKey getPrivateKey(byte[] privateKey) throws Exception{
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec); 
	}

	/**
	 * @描述 公钥加密<br>
	 * @param data 需要加密字符串 (<=60汉字) 字节长度<=180bytes的字符串(编码：UTF-8中，一个汉字=3bytes)
	 * @param publicKey 公钥对象
	 * @return 加密后字符串(Base64编码)
	 * @author administrator
	 * @throws Exception
	 * @版本 v1.0.0
	 * @日期 2017-8-5
	 */
	public static String encode(String data,RSAPublicKey publicKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding",new BouncyCastleProvider());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		String result = BASE64.encoder(cipher.doFinal(data.getBytes()));
		return result;
	}

	/**
	 * @描述 私钥解密<br>
	 * @param data Base64解码后的字节数组
	 * @param privateKey 私钥对象
	 * @return 明文
	 * @author administrator
	 * @throws Exception 
	 * @版本 v1.0.0
	 * @日期 2017-8-5
	 */
	public static String decode(byte[] data,RSAPrivateKey privateKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding",new BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		String result =new String(cipher.doFinal(data));
		return result;
	}
	
	/**
	 * @描述 测试方法<br>
	 * @param data 明文  可接收NULl值，默认:测试中华人民共和国一测试中华人民共和国二测试中华人民共和国三测试中华人民共和国四测试中华人民共和国五测试中华人民共和国六
	 * @throws Exception
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-8-5
	 */
	public static void test(String data) throws Exception {
		
		if(data==null||"".equals(data)){
			data = "689";//60
		}
		
		System.out.println("【明文】:"+data);
		
		Map<String, Object> map = RSA.getKey();
		RSAPublicKey publicKeyObj = (RSAPublicKey) map.get("public");
		RSAPrivateKey privateKeyObj = (RSAPrivateKey) map.get("private");
		System.out.println("【公钥对象】:"+publicKeyObj);
		System.out.println("-----------");
		System.out.println("【私钥对象】:"+privateKeyObj);
		System.out.println("===================================");
		
		String pubKey = BASE64.encoder(publicKeyObj.getEncoded());
		String priKey = BASE64.encoder(privateKeyObj.getEncoded());
		System.out.println("【公钥字符串】:"+pubKey);
		System.out.println("-----------");
		System.out.println("【私钥字符串】:"+priKey);
		System.out.println("===================================");
		
		
		
		
		RSAPublicKey publicKey = RSA.getPublikKey(BASE64.decoderByte(pubKey));
		RSAPrivateKey privateKey = RSA.getPrivateKey(BASE64.decoderByte(priKey));
		System.out.println("【重新获取公钥对象】:"+publicKey);
		System.out.println("-----------");
		System.out.println("【重新获取私钥对象】:"+privateKey);
		System.out.println("===================================");
		
		String enStr = RSA.encode(data, publicKey);
		System.out.println("【密文】:"+enStr);
		System.out.println("===================================");
		
		String deStr = RSA.decode(BASE64.decoderByte(enStr), privateKey);
		System.out.println("【解密明文】:"+deStr);
		System.out.println("===================================");
		System.out.println("*******************************************************");
	}	
	
	public static void main(String[] args) throws Exception {
		
		String pk="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVCvLofPNFRkwOHYMMKs6tHq/coklSNHnG+0JOy65+Azsy7hV7OpjIiLMn7M50Qr47qxjWqGHHyxH27Vx+RrkZ7aLXr3/DnCSqNQOBkBPAuy4y9iUGPTRZNBiTpLHbs4BS8rh/mXchW5d+1X9d0RkL+k0VqYhgO0YjY0oqGbww3wIDAQAB";
		RSAPublicKey publicKey = RSA.getPublikKey(BASE64.decoderByte(pk));
		String data = "123weew";
		String enStr = RSA.encode(data, publicKey);
		System.out.println("密文-->"+enStr);
		System.out.println("----------------------");
		String prk="MIIDlwIBADANBgkqhkiG9w0BAQEFAASCA4EwggN9AgEAAoHBALAP2hFONlnert+Q/Yjhya8UKJnpKw2uSrXJNeLov8BdznIWV6sc8vGtmwtr8wE1NEY473Va+hkElTO5CR4Wd5Va+X/vnHNpQRaYIGiHeSBELLtCfJSEh7Ljpx5FuU+Nd9XhMPUEgMgJxtIiJ8/bjxJOM9UFZKCOjcevDwhVaJUGG6I1IjnHSiWAOlMXFNGeqZUKaEwIk040oPI7oVTTXN1lpCKwLY7yyoVUrsrpUPYQHKXmvto0QjeEwv1BITumCQIDAQABAoHBAJuhp5ZcmNbgbZzawgNZ87p8C1fiMViqlQgUkTm/nKEjSva2oVB1CpxwZfm2ttx+d3MLqUEH5sRutKVAD7s1CQhRnvNkj9vFUu/yEdPUzVGkchYmM5HlhT6w1m2AHMshtSs3G289Z63QxAOVKf0jMwCrZLXdg6BvLzg2qrmMPHDuR4UgB1Xq++UEIaHcKc5y8/tKIxgiVnWWK4vUTzlUYCoufnV0knNzwWqyJxSSjEcUEfpljw9IGOJMFoWprPdh8QJhAP5NPK4agnw/8uFKvxJHGLHouFpcW1SYs6CiAD71VIC/eHWT4bBsR+5cTxvhbrSgDMApSITsPGwZAMF3y4QsYDRu+Qg9dvC6DUCBwipGh78ThA11MoN6or6FALZ8bekFFwJhALE82nW3UkKPQnLuSuAzpqHgAWvopTCD05RrziUZoZZXQDEjpSglP4z99PtMSw2c+lhFy6XtPUVtDbgcJe7BkJ0XXa7geyf+2NCSSxxcJjxgViQRR2GihRJr6gENwtHh3wJgJ2R/idjBbc3aKdwSTRqGUMjkBdtYqnodGTz/HMGUHX2Lg2stAs9DNUFHX+JD6+b/EqVSfoS5t2UIIPAVrpvRK04ldYlP62pmsx9mnHXggVDcm9kTwho1K54exiXwQLBxAmEAhbdHijA4m4bBtzwYuXW+bbo22Fa46hVK91suN8dSpb5F68zcuaEAhTrELSOuLCMWrKpbrl+CLnLMOu7hn3PhyuLqBxnNUKtwoEr+5/KN1ldIJgXzOLIIe5F3Bjx/kyE5AmBnooY+qbVIVx1IvuaxEn4FRAyRf3lkostgzjS1rVBaa9OKuhpFdcQBw6Ciih1IrzufLy5VMpsO4Kvc+W6hlWqihquqG+YSi1RCa+qcOwmeRFHzUsjEBxzBiJCD0CWNYDg=";
		RSAPrivateKey privateKey = RSA.getPrivateKey(BASE64.decoderByte(prk));
		String md="fr6Jml5uNHWipljK9+7ywxrOWPDTDgu/uWXCVE7TE1WJRiSIF5l6RaHuTos9ci3GzdaWMoLRRzfN5Bh3S3hCAHBt4iL7kSpho0HryqQUFBG9Di5mtWEY6duO1rE6+Y7WDBc+JgJkiK/eu1VaK1vXrhVx+nOCvk8n8no+RmoMu5UUM9Lrp4tfokSwlAh2O8SdeLe+OHbZI9wQz01GzlcnL8DKxia80Hop5WzOmjDTR3nYKbTmO9uoqNufRYaqSICy";
		String deStr = RSA.decode(BASE64.decoderByte(md), privateKey);
		System.out.println("【解密明文】:"+deStr);
		
		
		
			
	//	test("123");
		
	}
	
}
