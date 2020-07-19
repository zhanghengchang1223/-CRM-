package com.zhang.crm.utils;

import java.util.UUID;

/**
 * 生成32位的一个字符串做为主键
 */
public class UUIDUtil {
	
	public static String getUUID(){
		
		return UUID.randomUUID().toString().replaceAll("-","");
		
	}
	
}
