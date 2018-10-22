package com.yibi.extern.api.aliyun.smscode;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * @描述 短信验证码获取工具<br>
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-6-23
 */
@Slf4j
public class SMSCodeUtil {
	@Setter
	private String accessKeyId;
	@Setter
	private String accessKeySecret;
	@Setter
	private String signName;
	/**
	 * @描述 短信发送<br>
	 * @param phone 接收短信手机号
	 * @return true:成功  false:失败
	 * @throws ClientException
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-9
	 */
	public JSONObject getValidateCode(String phone, String templateCode){
		JSONObject json = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		String code = getCode(6);
		params.put("code", code);
		json.put("codes", code);
		SendSmsResponse response = sendSms(phone,templateCode,params);
		if(response == null||!response.getCode().equals("OK")){
			json.put("code", 416);
			json.put("obj", code);
			
			return json;
		}
		json.put("code", 200);
		json.put("obj", code);
		
		return json;
    }

	/**
	 * 发送短信
	 * @param phone 手机号
	 * @param templateCode 短信模板id
	 * @param params 参数
	 * @return SendSmsResponse
	 * @date 2018-3-8
	 * @author lina
	 */
	public   SendSmsResponse sendSms(String phone,String templateCode,Map<String, String> params){
	
		try {
			String templateStr = JSONObject.fromObject(params).toString();
			//产品名称:云通信短信API产品,开发者无需替换
			String product = "Dysmsapi";
			//产品域名,开发者无需替换
			String domain = "dysmsapi.aliyuncs.com";
			// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)

			//可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			//初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			//组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			//必填:待发送手机号
			request.setPhoneNumbers(phone);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			//必填:短信模板-可在短信控制台中找到
	//    		request.setTemplateCode("SMS_101175020");
			request.setTemplateCode(templateCode);
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam(templateStr);
		
			//hint 此处可能会抛出异常，注意catch
			SendSmsResponse response = acsClient.getAcsResponse(request);
			log.info("【短信接口返回的数据】:ResultCode=" + response.getCode()+",Message=" + response.getMessage()+",RequestId=" + response.getRequestId()+",BizId=" + response.getBizId());
			return response;
		} catch (Exception e) {
			log.info("短信发送失败");
			e.printStackTrace();
		}
		
		return null;
	}
	
    private static String getCode(Integer length){
    	String[] num = new String[]{"0","1","2","3","4","5","6","7","8","9"};
    	Random random = new Random();
    	String code = "";
    	for(int i=0;i<length;i++){
    		int index = random.nextInt(num.length);
    		code = code+num[index];
    	}
    	return code;
    }
   
}