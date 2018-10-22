package com.yibi.extern.api.rongcloud.request;

import io.rong.RongCloud;

import javax.annotation.Resource;

/**
 * 融云用户管理
* @Description: TODO
* @author lina 
* @date 2018-5-17
* @version V1.0
 */
public class RongCloudRequest {
	
	@Resource
    RongCloud rongCloud;
	
	public RongCloud getRongCloud(){
		return this.rongCloud;
	}
	
	public void setRongCloud(RongCloud rongCloud){
		this.rongCloud = rongCloud;
	}
}
