package com.yibi.turnapi.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.encrypt.AES;
import com.yibi.turnapi.commons.enums.ResultCode;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Log4j
@Data
public class Result implements Serializable{

	private static final long serialVersionUID = 1L;
	/*返回状态码*/
	public Integer code;
	/*状态描述*/
	public String msg;
	/*data是否需要加密*/
	public boolean encrypt;
	/*数据*/
	private String data;
	
	public Result(Integer code,String msg,boolean encrypt){
		this.code = code;
		this.msg = msg;
		this.encrypt = encrypt;
	}
	
	public Result(){
		
	}
	
	public static String toResultEncrypt(ResultCode resultCode, Map<String, Object> map, String secretKey) {
		return toResult(resultCode,map,true,secretKey);
	}
	
	public static String toResultNotEncrypt(ResultCode resultCode,Object obj) {
		return toResult(resultCode,obj,false,null);
	}
	
	public static String toResultNotEncrypt(ResultCode resultCode) {
		return toResultNotEncrypt(resultCode,null);
	}
	public static String toResultFormatNotEncrypt(ResultCode resultCode,Object... arg) {
		Result result = new Result();
		result.setCode(resultCode.code());
		result.setMsg(String.format(resultCode.message(), arg));
		result.setEncrypt(false);
		
		return toJsonString(result);
	}
	
	public static String toResult(ResultCode resultCode,Object obj,boolean isEncrypt,String secretKey) {
		Result result = new Result();
		result.setCode(resultCode.code());
		result.setMsg(resultCode.message());
		result.setEncrypt(isEncrypt);
		if(obj!=null){
			try {
			if(obj instanceof List){
				JSONArray json = (JSONArray) JSON.toJSON(obj);
				result.setData(isEncrypt?AES.encrypt(json.toJSONString(), secretKey):json.toJSONString());
			}else {
				JSONObject json = (JSONObject) JSON.toJSON(obj);
				result.setData(isEncrypt?AES.encrypt(json.toJSONString(), secretKey):json.toJSONString());
			}	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return toJsonString(result);
	}

	/**
	 * 返回Result的jsonString
	 * @param result
	 * @return String
	 * @date 2017-12-25
	 * @author lina
	 */
	public static String toJsonString(Result result){
		/*StringBuffer ResultStr = new StringBuffer();
		ResultStr.append("{")
		.append("\"code\":"+result.getCode()+",")
		.append("\"encrypt\":"+result.isEncrypt()+",")
		.append("\"data\":"+result.getData()+",")
		.append("\"msg\":\""+result.getMsg()+"\"")
		.append("}");*/
		
		JSONObject json = (JSONObject) JSON.toJSON(result);
		String str = json.toJSONString();
		log.info("返回值---->"+str.toString());
		return str.toString();
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(Result.toResultNotEncrypt(ResultCode.USER_NOT_LOGGED_IN));
	}
}
