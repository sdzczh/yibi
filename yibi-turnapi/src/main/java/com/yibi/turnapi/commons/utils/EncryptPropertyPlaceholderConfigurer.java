package com.yibi.turnapi.commons.utils;

import com.yibi.common.encrypt.DES;
import com.yibi.turnapi.commons.variables.GlobalParams;
import com.yibi.turnapi.commons.variables.PropertieKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.List;
import java.util.Properties;


/**
 * @描述 properties文件加密解密<br>
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-6-7
 */
public class EncryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	private static final  Logger logger = LoggerFactory.getLogger(EncryptPropertyPlaceholderConfigurer.class);
	
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
			try {
				List<String> keys = PropertieKey.keys();
				for (String key : keys) {
					String keyName =  props.getProperty(key);  
					keyName = DES.decrypt(keyName,GlobalParams.PWD_KEY);
					props.setProperty(key,keyName);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
					e.printStackTrace();
			}
		super.processProperties(beanFactoryToProcess, props);
	}
	public static void main(String[] args) throws Exception {
		System.out.println(DES.decrypt("fQxamtdktUGqLMAEq9m3FoghtXD9WKX2",GlobalParams.PWD_KEY));
		System.out.println(DES.decrypt("M0VaaO81eYflO9mFQNVLSIghtXD9WKX2",GlobalParams.PWD_KEY));
		System.out.println(DES.encrypt("root",GlobalParams.PWD_KEY));
		System.out.println(DES.encrypt("123456",GlobalParams.PWD_KEY));
	}
}
