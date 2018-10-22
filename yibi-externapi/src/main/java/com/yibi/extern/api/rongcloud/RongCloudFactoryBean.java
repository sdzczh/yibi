package com.yibi.extern.api.rongcloud;

import io.rong.RongCloud;

import java.util.Objects;

import lombok.Setter;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class RongCloudFactoryBean implements FactoryBean<RongCloud>,InitializingBean {
	
	@Setter
	private String	appKey;//融云APPKey
	@Setter
	private String	appSecret;//融云APPSecret
	
	private RongCloud rongCloud;
	
	public void afterPropertiesSet() throws Exception {
		Objects.requireNonNull(appKey);
		Objects.requireNonNull(appSecret);
		rongCloud = RongCloud.getInstance(appKey, appSecret);
		
	}
	public RongCloud getObject() throws Exception {
		return rongCloud;
	}
	public Class<?> getObjectType() {
		return RongCloud.class;
	}
	public boolean isSingleton() {
		return true;
	}
	
	

}
