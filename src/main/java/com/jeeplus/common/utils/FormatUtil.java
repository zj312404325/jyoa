/*
 * @(#)FormatUtil.java
 *       
 * 系统名称：
 * 版本号：1.0
 * 
 * Copyright (c)  
 * All Rights Reserved.
 * 
 * 作者：
 * 创建日期：
 * 
 * 功能描述：数据格式化工具类
 * 公用方法描述：
 * 
 * 修改人：
 * 修改日期：
 * 修改原因：
 * 
 * 
 */ 
package com.jeeplus.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

 
public class FormatUtil extends MultiActionController {
	//------------------------------   格式化数值   ---------------------------------
    /** 把Double类型的数值转化成"###,###.00"的字符串   */
    public static String toStringWithDecimal(Double price) {
        try {return  new DecimalFormat("###,###.00").format(price);} catch (Exception pe) { return null;}
    }
    /** 把String类型的数值转化成"###,###.00"的字符串   */
    public static String toStringWithDecimal(String decimal) {
        try {return  new DecimalFormat("###,###.00").format(new Double(decimal));} catch (Exception pe) { return null;}
    } 
    /** 把String类型的货币数值转化成"###,###"的字符串   */
    public static String toStringWithoutDecimal(String nodecimal) {
        try {return  new DecimalFormat("###,###").format(new Double(nodecimal));} catch (Exception pe) { return null;}
    } 
    /** 把类型为"###,###.00"的字符串转化成Double类型的货币数值 */
    public static Double toDoubleCurrency(String price) {
        try {return new Double(new DecimalFormat("###,###.00").parse(String.valueOf(price)).doubleValue());} catch (Exception pe) { return null;}
    }    
    
    /** 把String类型的数值转化成"###.00"的字符串   */
    public static String toStrWithDecimal(String decimal) {
        try {return  new DecimalFormat("###.00").format(new Double(decimal));} catch (Exception pe) { return null;}
    } 
    
    /** 把类型为"###.00"的字符串转化成Double类型的货币数值 */
    public static Double toDoubleData(String price) {
        try {return new Double(new DecimalFormat("###.00").parse(String.valueOf(price)).doubleValue());} catch (Exception pe) { return null;}
    }  
	//------------------------------   char型数值与整型数据间的转换   ---------------------
	public static int toInt(char c){try{return c;}catch(Exception e){return 0;}}
	public static int toChar(int c){try{return c;}catch(Exception e){return 0;}}

	//------------------------------   Boolean型数值与   ---------------------
	/** 通用转换：true--> 1 false-->0  */
	public static int toIntYes2One(boolean status){if(status) return 1; return 0;}
	public static boolean toBooleanOne2Yes(int status){if(status == 1) return true; return false;}
	
	//------------------------------  Boolean型数值与数据库字典中定义的值相互转换  --------------------- 
	public static boolean toBoolean(String status){	
		if("1".equals(status.trim())) return true;
		if("TRUE".equals(status.toUpperCase().trim())) return true;
		return false;
	} 

	//-------------------------   通用类型转换：先转成Double在根据需求转换   ------------- 
	//简单类型
	public static int     toInt(Object obj) {       try{return toDouble(obj).intValue();}catch(Exception e){return 0;} } 
	public static long    toLongSmp(Object obj) {   try{return toDouble(obj).longValue();}catch(Exception e){return 0;}}  
	public static float   toFloatSmp(Object obj) {  try{return toDouble(obj).floatValue();}catch(Exception e){return 0;}} 
	public static double  toDoubleSmp(Object obj) { try{return toDouble(obj).doubleValue();}catch(Exception e){return 0;}} 	
	
	//引用类型
	public static Integer toInteger(Object obj) {   try{return toDouble(obj).intValue();}catch(Exception e){return 0;}} 
	public static Long    toLong(Object obj) {      try{return toDouble(obj).longValue();}catch(Exception e){return 0L;}} 	
	public static Float   toFloat(Object obj) {     try{return toDouble(obj).floatValue();}catch(Exception e){return 0f;}  } 	
	public static Double  toDouble(Object obj){     try{return new Double(obj.toString());}catch(Exception e){return 0d;} } 
	
	public static String  toString(Object obj){  
		if(obj == null || obj.equals("null"))
			return "";
		try{
			if(obj instanceof Double)	return new DecimalFormat("0.00").format(obj);
			if(obj instanceof Float)	return new DecimalFormat("0.00").format(obj);			
			return obj.toString().replaceAll("\"", "\\\"");
		}catch(Exception e){return "";} 	
	} 
	
	public static String trim(Object obj)
	{
		String str = toString(obj).trim();
		str = str.replaceAll("null", "");
		return str.replaceAll("undefined", "");
	}
	
	public static String  toString6(Object obj){    
		try{
			if(obj instanceof Double)	return new DecimalFormat("0.000000").format(obj);
			if(obj instanceof Float)	return new DecimalFormat("0.000000").format(obj);			
			return obj.toString();
		}catch(Exception e){return null;} 	
	} 
	public static Set toSet(Object obj){
		Set set = new HashSet();
		try{
			String[] cities = obj.toString().split("/");
			for(int i=0;i<cities.length;i++)
				set.add(cities[i]);
			return set;
		}catch(Exception e){return set;} 	
	}
	
	/**
	 * BigDecimal转换为String
	 * 
	 * @param number BigDecimal数字
	 * @param length 字符串长度
	 * @return
	 */
	public static String format(BigDecimal number, int length) {
		String str = "";
		
		if (length == 0) {
			return "";
		}
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(length);
		
		//去除结果中的","
		try {
			str = formatter.format(number).replace(",", "");
		} catch (Exception e) {
			return "";
		}
		
		return str;
	}
	
	public static String toPercentage(double number) {
		DecimalFormat df = new DecimalFormat("0%");  
		if (number == 0) {
			return "0%";
		}
		
		return df.format(number);
	}
	
	/**
	 * 日期转字符
	 * @param String 日期类型
	 * @param pattern 格式如yyyy-MM-dd
	 * @return
	 */
	public static String dateToString(Date date,String pattern){
		if(date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 日期字符格式化成字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String StringToString(String date,String pattern){
		return dateToString(StringToDate(date, pattern), pattern);
	}
	
	/**
	 * 判断参数是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isNoEmpty(Object obj){
		if(obj==null)
			return false;
		String s = trim(obj);
		if(s.equals("") )
			return false;
		return true;		
	}
	
	public static boolean isNoNull(Object obj){
		if(obj==null)
			return false;
		return true;		
	}
	
	 /* 字符串转换到时间格式
	 * @param dateStr 需要转换的字符串
	 * @param formatStr 需要格式的目标字符串  举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException 转换异常
	 */
	public static Date StringToDate(String dateStr,String formatStr){
		if(dateStr==null || dateStr.equals(""))
			return null;
		DateFormat sdf=new SimpleDateFormat(formatStr);
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	} 
	
	public static String DateTimeToString(long dateTime,String formatStr){
		Date date = new Date(dateTime);
		return DateToString(date,formatStr);
	}
	
	public static String DateToString(Date date,String formatStr){
		return new SimpleDateFormat(formatStr).format(date);
	}
	
	/**
	 * 搜索sql语句查询出来的map值
	 * @param obj
	 * @param key
	 * @return
	 */
	public static Object MapKeyToValue(Map obj,String key){
		if(obj == null)
			return "";
		if(obj.get(key.toUpperCase()) == null)
			return "";
		return obj.get(key.toUpperCase());
	}
	
	public static boolean IsHasString(String[] strs,String str){
		if(strs==null || strs.length==0){
			return false;
		}
		for(String s : strs){
			if(s.equals(str)){
				return true;
			}
		}
		return false;
	}
	
	public static String IdsToIds(String ids){
		String str = "";
		if(isNoEmpty(ids)){
			String[] idss = ids.split(",");
			String f = "";
			for(String v : idss){
				str += f+"'"+v+"'";
				f = ",";
			}			
		}
		return str;
	}
	
	public static String ClobToString(Object obj) {
		if(obj == null)
			return "";
		Clob clob=(Clob) obj;
		StringBuffer sb = null;
		try {
			Reader rd=clob.getCharacterStream();
			BufferedReader br=new BufferedReader(rd);
			String s="";
			sb=new StringBuffer();
			while((s=br.readLine())!=null){
				sb.append(s);
			}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

		return sb.toString();
	}
	
	/**
	 * 去除html标签
	 * @param htmlStr
	 * @return
	 */
	 public static String delHTMLTag(String htmlStr){ 
//         String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>";
//         String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; 
//         String regEx_html="<[^>]+>";
//         Pattern p_script=Pattern.compile(regEx_script, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
//         Matcher m_script=p_script.matcher(htmlStr);
//         htmlStr=m_script.replaceAll("");    
//         Pattern p_style=Pattern.compile(regEx_style, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
//         Matcher m_style=p_style.matcher(htmlStr);
//         htmlStr=m_style.replaceAll("");     
//         Pattern p_html=Pattern.compile(regEx_html, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
//         Matcher m_html=p_html.matcher(htmlStr);
//         htmlStr=m_html.replaceAll("");
//        return htmlStr.trim(); 
		  String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
          String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
          String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
          Pattern  p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
          Matcher m_script = p_script.matcher(htmlStr);
          htmlStr = m_script.replaceAll(""); //过滤script标签

          Pattern p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
          Matcher m_style = p_style.matcher(htmlStr);
          htmlStr = m_style.replaceAll(""); //过滤style标签

          Pattern p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
          Matcher m_html = p_html.matcher(htmlStr);
          htmlStr = m_html.replaceAll(""); //过滤html标签
          htmlStr=htmlStr.replace("&nbsp;","");
          return htmlStr;
     }
	 
	
	 /**
	  * 得到ip
	  * @param request
	  * @return
	  */
	 public static String getRemortIP(HttpServletRequest request) {  
			String ip = request.getHeader("x-forwarded-for");  
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("Proxy-Client-IP");  
			}  
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("WL-Proxy-Client-IP");  
			}  
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getRemoteAddr();  
			}  
			return ip;  
		 } 

	 /**
	  * 获得uuid
	  * @return
	  */
	 public static String getUUID(){
		 String uuID=UUID.randomUUID().toString();
		 String id = "";
			String oid[] = uuID.split("-");
			for(int i = 0 ; i < oid.length ; i++){
				id +=  oid[i];   
			}
			return id;
	 }
	 
	 /**  
	     * 计算两个日期之间相差的天数  
	     * @param smdate 较小的时间 
	     * @param bdate  较大的时间 
	     * @return 相差天数 
	     * @throws ParseException  
	     */    
	    public static int daysBetween(Date smdate,Date bdate)    
	    {    
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        try {
				smdate=sdf.parse(sdf.format(smdate));
				bdate=sdf.parse(sdf.format(bdate));  
			} catch (ParseException e) {
				e.printStackTrace();
			}  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));           
	    }  
	    
	    public static int getDaysBetween(Date smdate,Date bdate)    
	    {   
	    	if(!FormatUtil.isNoEmpty(smdate)){
	    		smdate = new Date();
	    	}
	    	if(!FormatUtil.isNoEmpty(bdate)){
	    		bdate = new Date();
	    	}
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        try {
				smdate=sdf.parse(sdf.format(smdate));
				bdate=sdf.parse(sdf.format(bdate));  
			} catch (ParseException e) {
				e.printStackTrace();
			}  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));           
	    } 
	    
	    /**
	     * 比较2个时间的前后
	     * @param minDate 较小的时间 
	     * @param maxDate 较大的时间 
	     * @return 相差的时间毫秒数
	     */
	    public static long dayTimeBetween(Date minDate,Date maxDate)    
	    {    
	    	return maxDate.getTime()-minDate.getTime();
	    }
	    
	    /**                                                                        
	     * 使用参数Format将字符串转为Date                                      
	     */                                                                    
	    public static Date parseDate(String strDate, String pattern)               
	            throws ParseException                                          
	    {                                                                      
	        return isNoEmpty(strDate) ? new SimpleDateFormat( 
	                pattern).parse(strDate):null;                                   
	    }                                                                      

	    /**
	     * 返回城市id
	     * @param request
	     * @return
	     */
	    public static String getCityid(HttpServletRequest request){
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
			   for (Cookie cookie : cookies) {  
			      if (cookie != null) {
				      if ("iEdu_LocalCityId".equals(cookie.getName())) {  
					      String cityId= cookie.getValue(); // 得到cookie的用户名  
					      return cityId;
				      }
			      }
			   }
			}
			return null;
	    }
	    
	    /**
	     * 
	     * @param 传入文本
	     * @param 取开头的位数
	     * @param 去结尾的未数
	     * @param 隐藏文本符号
	     * @return
	     */
	    public static String hideStr(String str,int beginPos,int endPos,String replaceStr)
	    {
	    	try {
				String result=""; 
				if(str!=null&&str!="")
				{
					if(str.length()<beginPos){
						
					}
					result+=str.substring(0,beginPos);
					result+=replaceStr;
					result+=str.substring(str.length()-endPos,str.length());
				}
				return result;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "";
			}
	    }
	    
	    public static String escapeHtml1(String s) {    
			if (s == null || s.isEmpty()) {    
			   return "";    
			}    
			StringBuilder sb = new StringBuilder("");    
			for (int i = 0; i < s.length(); i++) {    
			char c = s.charAt(i);    
			switch (c) {    
			case '>':    
			sb.append("&gt;");    
			break;    
			case '<':    
			sb.append("&lt;");   
			break;     
			case '"':    
			sb.append("&quot;");  
			break;    
			case '\'':    
			sb.append("&apos;");  
			break;
			/*case 10:
			sb.append("<br/>");
			break;*/
			case 13:    
			break;   
			default:    
			sb.append(c);    
			break;    
			}    
			}    
			return sb.toString();
		}

	    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	    private static String formatIso8601Date(Date date) {
	        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
	        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
	        return df.format(date);
	    }
	    
	    public static String getfilename(String fName)  {
			String fileName = null;
			try {
				fileName = java.net.URLDecoder.decode(fName.substring(fName.lastIndexOf("/")+1), "UTF-8");
				return fileName;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			}

	    }

	public static String getfileHouzhui(String fName)  {
		String fileHouzhui = null;
		try {
			fileHouzhui = java.net.URLDecoder.decode(fName.substring(fName.lastIndexOf(".")+1), "UTF-8");
			return fileHouzhui;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}

	}
	    
	    public static String timestampToString(Object value) {
	    	Timestamp timestamp = null; 
	    	try { 
	    	timestamp = (Timestamp) value; 
	    	} catch (Exception e) { 
	    	timestamp = getOracleTimestamp(value); 
	    	} 
	    	if(timestamp!=null) 
	    	return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(timestamp); 
	    	else return null;
	    }
	    
	    public static Timestamp getOracleTimestamp(Object value) {
	    	  try {
	    	    Class clz = value.getClass();
	    	    Method method = clz.getMethod("timestampValue", null); 
	    	         //method = clz.getMethod("timeValue", null); 时间类型 
	    	         //method = clz.getMethod("dateValue", null); 日期类型
	    	    return (Timestamp) method.invoke(value, null);
	    	  } catch (Exception e) {
	    	    return null;
	    	  }
	    	}
	    
	    /* 
	    * 毫秒转化 
	    */  
	    public static String formatMillSecondToTime(long ms) {  
	                  
	                 int ss = 1000;  
	                 int mi = ss * 60;  
	                 int hh = mi * 60;  
	                 int dd = hh * 24;  
	      
	                 long day = ms / dd;  
	                 long hour = (ms - day * dd) / hh;  
	                 long minute = (ms - day * dd - hour * hh) / mi;  
	                 long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
	                 long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  
	      
	                 String strDay = day < 10 ? "0" + day : "" + day; //天  
	                 String strHour = hour < 10 ? "0" + hour : "" + hour;//小时  
	                 String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟  
	                 String strSecond = second < 10 ? "0" + second : "" + second;//秒  
	                 String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒  
	                 strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;  
	                 
	                 String result="";
	                 if(!strDay.equals("00")){
	                	 result+=strDay + " 天 ";
	                 }
	                 if(!strHour.equals("00")){
	                	 result+=strHour + " 小时 ";
	                 }
	                 if(!strMinute.equals("00")){
	                	 result+=strMinute + " 分钟 ";
	                 }
	                 result+=strSecond + " 秒";
	                 return  result;  
	       }
	    
	    /**
         * 获取指定时间的那天 23:59:59.999 的时间
         * 
         * @param date
         * @return
         */
        public static Date dayEnd(final Date date) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.set(Calendar.HOUR_OF_DAY, 23);
                c.set(Calendar.MINUTE, 59);
                c.set(Calendar.SECOND, 59);
                c.set(Calendar.MILLISECOND, 999);
                return c.getTime();
        }
	    
        /**
         * 日期加几天
         * 
         * @param date
         * @return
         * @throws ParseException 
         */
        public static Date todayOperate(Date today,int num,int fun) {
        	try {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");  
				Calendar c = Calendar.getInstance();  
				if(fun==0){
					c.setTime(today);
					c.add(Calendar.DAY_OF_MONTH, num);// 
					 
				}else{
					c.set(Calendar.DATE, c.get(Calendar.DATE) - num);
				}
				return c.getTime();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return today;
			} 
        }
        
        /**
         * 格式化double保留length位小数
         * @return
         */
        public static Double formatDouble(Double num,int length){
        	BigDecimal   b   =   new   BigDecimal(num);  
        	double   f1   =   b.setScale(length,   BigDecimal.ROUND_HALF_UP).doubleValue();  
			return f1;
        }

	/**
	 * 日期加几秒
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date todaySecondOperate(Date today,int num,int fun) {
		try {
			//SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			if(fun==0){
				c.setTime(today);
				c.add(Calendar.SECOND, num);//

			}else{
				c.set(Calendar.SECOND, c.get(Calendar.SECOND) - num);
			}
			return c.getTime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return today;
		}
	}

	//格式化上传文件名
    public static String formatFileName(String oldName) {
		if(FormatUtil.isNoEmpty(oldName)){
			oldName=oldName.replace("'","");//去除单引号
		}
		return oldName;
    }

	public static Boolean isImg(String filename) {
		String rgx = "(bmp|jpg|gif|jpeg|png)";
		String houzhui = getExtensionName(filename).toLowerCase();
		//判断文件类型
		boolean isImage = houzhui.toLowerCase().matches(rgx);
		return isImage;
	}
	/**
	 * 获取文件扩展名
	 * @param filename
	 * @return
	 */
	private static  String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot >-1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	//将list转换为带有 ， 的字符串
	public static String listToString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append(list.get(i) + ",");
				} else {
					sb.append(list.get(i));
				}
			}
		}
		return sb.toString();
	}

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	public static String dayForWeek(String pTime) throws ParseException{
		Calendar c = Calendar.getInstance();
		c.setTime(sdfDay.parse(pTime));
		Date date = c.getTime();
		//中文周几
		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		//数字周几
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
         dayForWeek = 7;
        }else{
         dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
		return toString(dayForWeek);
	}

	public static String getPercentage(int num1,int num2){
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(1);
		String result = numberFormat.format((float) num1 / (float) num2 * 100);
		return result + "%";
	}


}