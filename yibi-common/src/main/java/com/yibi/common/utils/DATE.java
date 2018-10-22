package com.yibi.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @描述 日期时间工具<br>
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-6-8
 */
public class DATE{
	
	private DATE(){}
	
	private static final String CURRENT_DATE_STR = "yyyy-MM-dd";
	private static final String CURRENT_TIME_STR = "yyyy-MM-dd HH:mm:ss";

	public static Date getCurrentDate() {
		Date date = new Date();
		return date;
	}

	/**
	 * @描述 获取字符串格式的当前
	 * @return XXXX-XX-XX XX:XX:XX
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	public static String getCurrentTimeStr() {
		SimpleDateFormat format = new SimpleDateFormat(CURRENT_TIME_STR);
		return format.format(new Date());
	}

	/**
	 * @描述 获取字符串格式的当前
	 * @return XXXX-XX-XX
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-6-17
	 */
	public static String getCurrentDateStr() {
		SimpleDateFormat format = new SimpleDateFormat(CURRENT_DATE_STR);
		return format.format(new Date());
	}
	
	/**
	 * @描述 当前日期日前戳无符号<br>
	 * @return
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-13
	 */
	public static String getCurrentMillTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(new Date());
	}
	
	/**
	 * @描述 当前小时分钟时间戳<br>
	 * @return
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-13
	 */
	public static String getHourMinute(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		return format.format(new Date()).substring(0,15)+"x";
	}

	/**
	 * @描述 字符串转时间 
	 * @param str 例:XXXX-XX-XX XX:XX:XX
	 * @return 日期对象
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-6-17
	 */
	public static Date formatStrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @描述 获取当前月份第一天
	 * @return 
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-7-5
	 */
	public static String getCurrentMonthStartDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		 String startDay = format.format(cal_1.getTime());
		return startDay;
	}
	
	/**
	 * @描述 获取指定时间的前/后某一时间<br>
	 * @param str 指定日期时间
	 * @param order 前/后 0：前 1后
	 * @param type 时间类型 0:分 1:时 2:天 3:周
	 * @param target 相距时长
	 * @return 
	 * @author administrator
	 * @版本 v1.0.0
	 * @日期 2017-10-24
	 */
	public static String getTargetTime(String str,Integer order,Integer type,Integer target){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = formatStrToDate(str);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(date);
		if(order==0){
			target = 0-target;
		}else{
			target = target-0;
		}
		if(type==0){//分
			calendar.add(Calendar.MINUTE, target);
		}
		if(type==1){//时
			calendar.add(Calendar.HOUR, target);
		}
		if(type==2){//天
			calendar.add(Calendar.DATE, target);
		}
		if(type==3){//周
			calendar.add(Calendar.DATE, target*7);
		}
		String result = format.format(calendar.getTime());
		return result;
	}

}
