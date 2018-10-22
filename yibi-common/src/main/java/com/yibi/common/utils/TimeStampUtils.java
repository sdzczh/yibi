package com.yibi.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
* @Description: TODO
* @author lina 
* @date 2018-1-3
* @version V1.0
 */
public class TimeStampUtils {

	public static final String TIME_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss"; 
	public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd";
	
	public static String toTimeString(Date time){
		if(time==null){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(TIME_PATTERN_DEFAULT);
		return format.format(time);
	}
	
	public static String toTimeString(Date time,String pattern){
		if(time == null){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(time);
	}
	
	public static String toDateString(Date time){
		SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN_DEFAULT);
		return format.format(time);
	}
	
	public static Timestamp StrToTimeStamp(String timeStr){
		try {
			return Timestamp.valueOf(timeStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	public static Timestamp getSomeDayTime(int day){
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, day);
		yesterday.set(Calendar.MILLISECOND, 0);
		yesterday.set(Calendar.SECOND, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		
		return new Timestamp(yesterday.getTimeInMillis());
	}
}
