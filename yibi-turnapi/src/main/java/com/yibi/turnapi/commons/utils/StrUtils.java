package com.yibi.turnapi.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yibi.turnapi.commons.variables.GlobalParams;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @描述 String字符串工具类
 * @author  
 * @版本 V1.0.0
 * @日期 2017-6-6
 */
public class StrUtils {

	/**
	 * @描述 去除换行<br>
	 * @param str
	 * @return
	 * @author  
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	public static String trimEnter(String str){
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		String dest = m.replaceAll("");
		return dest;
	}
	
	/**
	 * @描述 字符串空值判断<br>
	 * @param str 字符串
	 * @return 是否为空
	 * @author  
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	public static boolean isBlank(String str){
		if(str==null || "".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	/**
	 * @描述 获取随机数<br>
	 * @param len 随机数长度
	 * @return 随机数
	 * @author  
	 * @版本 v1.0.0
	 * @日期 2017-6-27
	 */
	public static String getCode(int len){
		Random ran = new Random();
		StringBuffer str = new StringBuffer();
		for(int i=0;i<len;i++){
			str.append(GlobalParams.ROUND_NUM[ran.nextInt(len)]);
		}
		return str.toString();
	}
	
	/**
	 * @描述 对象格式化为JSON<br>
	 * @param obj 返回值对象
	 * @return JSON 字符串
	 * @author  
	 * @版本 v1.0.0
	 * @日期 2017-6-28
	 */	
	public static String response(Object obj){
		JSONObject json = (JSONObject) JSON.toJSON(obj);
		return json.toJSONString();
	}
	
	/**
	 * @描述 判空<br>
	 * @param str
	 * @return
	 * @author  
	 * @版本 v1.0.0
	 * @日期 2017-7-4
	 */
	public static boolean checkNull(String str){
		if(str==null || "".equals(str.trim())|| str.length()==0){
			return true;
		}else{
			return false;
		}
	}
	
	public static String phoneFormat(String phone){
		if(isBlank(phone)){
			return "";
		}
		return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
	}
	
	/**
	 * 替换匹配到的字符串为目标字符串
	 * @param str
	 * @param pattern 正则表达式
	 * @param replace 目标字符串
	 * @return String
	 * @date 2018-2-2
	 * @author lina
	 */
	public static String replaceAll(String str,String pattern,String replace){
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		String dest = m.replaceAll(replace);
		return dest;
	}
	
	public static void main(String[] args) {
		
		String userName = "12都是2121212";
		if(StrUtils.isInteger(userName)){
			userName = StrUtils.getStarString(userName, 3, 4);
		}else if(userName.length()<=3){
			userName = StrUtils.getStarString(userName, 1, 0);
		}else {
			userName = StrUtils.getStarString(userName, 2, 0);
		}
		System.out.println(userName);
	}
	
	/** 
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替 
     *  
     * @param content 
     *            传入的字符串 
     * @param frontNum 
     *            保留前面字符的位数 
     * @param endNum 
     *            保留后面字符的位数 
     * @return 带星号的字符串 
     */  
	public  static  String getStarString(String content, int frontNum, int endNum) {  
		  
	    if (frontNum >= content.length() || frontNum < 0) {  
	        return content;  
	    }  
	    if (endNum >= content.length() || endNum < 0) {  
	        return content;  
	    }  
	    if (frontNum + endNum >= content.length()) {  
	        return content;  
	    }  
	    String starStr = "";  
	    for (int i = 0; i < (content.length() - frontNum - endNum); i++) {  
	        starStr = starStr + "*";  
	    }  
	    return content.substring(0, frontNum) + starStr  
	            + content.substring(content.length() - endNum, content.length());  
	
	}  
	
	/**
	 * 判断是不是数字
	 * @param str
	 * @return boolean
	 * @date 2018-4-13
	 * @author lina
	 */
	public static boolean isInteger(String str) {  
	        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	        return pattern.matcher(str).matches();  
	}
	
}
