/**
 * Copyright (c) 2013-2015 www.javahih.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.Util;

import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p> Title:利用本地化对象来取资源文件的值</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2013 </p>
 * <p> Company:hihsoft.co.,ltd </p>
 *  @author hihsoft.co.,ltd
 * @version 1.0
 */
public class PropertiesUtil {
//	Locale locale = Locale.getDefault();
//
//	static ResourceBundle rb = ResourceBundle
//			.getBundle("resources.propertiescfg.SystemGlobals");
//	static ResourceBundle app = ResourceBundle
//			.getBundle("resources.propertiescfg.ApplicationResources");
//
//	public static String getValue(String key) {
//		return rb.containsKey(key) ? rb.getString(key) : null;
//	}
//	
//	public static String getValue(String key, String defaultValue) {
//		String val = getValue(key);
//		if (val == null) val = defaultValue;
//		return val;
//	}
//	public static Integer getIntValue(String key) {
//		try {
//			String s = getValue(key);
//			return StringHelpers.isNull(s) ? null : Integer.valueOf(s);
//		} catch (final Exception ex) {
//			return null;
//		}
//	}
//	
//	public static String getValueByFile(String key, String fileName) {
//		try {
//			return ResourceBundle.getBundle(fileName).getString(key);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	
//	public static String getAppValue(String key, String defaultValue) {
//		try {
//			String val = app.getString(key);
//			return StringHelpers.isNull(val) ? defaultValue : val;
//		} catch (Exception e) {
//			return defaultValue;
//		}
//	}
	
	
	  public static Properties props;
	  
	  //public static PropertiesUtil propertiesUtilInstance;
	  
	   static {
		  props = new Properties();
		  InputStream in = null;
		try {
			in = new ClassPathResource("config.properties").getInputStream();
			props.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	  }
	  
//	  public static PropertiesUtil getInstance(){
//		  if(propertiesUtilInstance == null){
//			  propertiesUtilInstance = new PropertiesUtil();
//		  }
//		  return propertiesUtilInstance;
//	  }
//	  
	  public static String getPropertyValueFromProperties(String key){
		  return (String) props.get(key);
	  }
}
