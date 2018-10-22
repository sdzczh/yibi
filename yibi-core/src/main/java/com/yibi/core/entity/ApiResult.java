package com.yibi.core.entity;

import java.io.Serializable;

/**
* @Description:API返回值
* @author zhaohe 
* @date 2018-07-06
* @version V1.0
 */
public class ApiResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private String time;
	private String address;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
