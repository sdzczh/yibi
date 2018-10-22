package com.yibi.common.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
	Properties props = new Properties();
	private String path;
	
	public PropertyUtils(String path){
		this.path = path;
	}
	
	public String getProperty(String key){
		try {
			props.load(PropertyUtils.class.getClassLoader().getResourceAsStream(this.path));
			return props.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		
	}
}