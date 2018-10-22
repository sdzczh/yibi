package com.yibi.orderapi.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yibi.orderapi.enums.ResultCode;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Result implements Serializable{

	private static final long serialVersionUID = 1L;
	/*返回状态码*/
	public Integer code;
	/*状态描述*/
	public String msg;
	/*数据*/
	private Object data;
	
	public Result(Integer code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public Result(){
		
	}
	
	public static String toResult(ResultCode resultCode) {
		return toResult(resultCode,null);
	}
	public static String toResultFormat(ResultCode resultCode,Object... arg) {
		Result result = new Result();
		result.setCode(resultCode.code());
		result.setMsg(String.format(resultCode.message(), arg));

		return toJsonString(result);
	}
	
	public static String toResult(ResultCode resultCode,Object obj) {
		Result result = new Result();
		result.setCode(resultCode.code());
		result.setMsg(resultCode.message());
		result.setData(obj);
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
		log.info("返回值---->{}",str.toString());
		return str.toString();
	}


}
