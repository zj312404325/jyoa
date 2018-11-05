package com.jeeplus.common.sms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {

	public static String get(String url, Map<String, Object> params){
		StringBuilder sb = new StringBuilder();
		StringBuilder jsonMsg= new StringBuilder();
		if (params != null) {
			url = url + "?";
			for (Entry<String, Object> e : params.entrySet()) {
				sb.append(e.getKey() + "=" + e.getValue().toString().trim() + "&");
			}
			url = url + sb.substring(0, sb.length() - 1);
		}
		HttpURLConnection http=null;
		try {
			URL u = new URL(url);
		    http =   (HttpURLConnection) u.openConnection();  
		    http.setRequestMethod("GET");        
		    http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
		    http.setDoOutput(true);        
		    http.setDoInput(true);
		    System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
		    System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
		    http.connect();
		    OutputStream os= http.getOutputStream();    
		    os.flush();
		    os.close();
		    
		    InputStream in = http.getInputStream();
		    //将流转换为字符串  
		    jsonMsg = new StringBuilder();  
		    byte[] b = new byte[4096];  
		    for (int n; (n = in.read(b)) != -1;) {  
		    	jsonMsg.append(new String(b, 0, n, "UTF-8"));  
		    }
		    if(in!=null){
		    	in.close();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonMsg.toString();
	}
	
	public static String post(String url, Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		String p = "";
		if (params != null) {
			for (Entry<String, Object> e : params.entrySet()) {
				if(e.getKey().equals("signature")){
					sb.append(e.getKey() + "=" + URLEncoder.encode(e.getValue().toString().trim()) + "&");
				}else{
					sb.append(e.getKey() + "=" + e.getValue().toString().trim() + "&");
				}
			}
			if(sb!=null&&sb.length()!=0){
				p = sb.substring(0, sb.length() - 1);
			}
		}
		
		HttpURLConnection con = null;
		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); 
			con.setRequestProperty("charset", "UTF-8");
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			
			OutputStream osw = con.getOutputStream() ;
			osw.write(p.getBytes("UTF-8"));
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 

		StringBuilder buffer = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}
}
