package com.zhang.crm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 进行事件获得的一个封装
 * 这里采用的是时分秒毫秒的形式，占有19个字符；
 */
public class DateTimeUtil {
	
	public static String getSysTime(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = new Date();
		String dateStr = sdf.format(date);
		
		return dateStr;
		
	}
	
}
