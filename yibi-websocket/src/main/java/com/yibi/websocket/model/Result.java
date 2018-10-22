package com.yibi.websocket.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Result implements Serializable{

	private static final long serialVersionUID = 1L;
	/*返回状态码*/
	public Integer code;
	/*状态描述*/
	public String msg;
	/*数据*/
	private Object info;
	
	public Result(Integer code,String msg, MessageInfo info){
		this.code = code;
		this.msg = msg;
		this.info = info;
	}
	
	public Result(){
		
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public static String toResultStr(ResultCode resultCode,Object data) {
		return toResult(resultCode,data);
	}
	
	public static String toResultStr(ResultCode resultCode) {
		return toResult(resultCode,null);
	}
	
	public static String toResult(ResultCode resultCode,Object info) {
		Result result = new Result();
		result.setCode(resultCode.code());
		result.setMsg(resultCode.message());
		if(info!=null){
			result.setInfo(info);
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
		JSONObject json = (JSONObject) JSON.toJSON(result);
		String str = json.toJSONString();
		return str;
	}
	
	
}
