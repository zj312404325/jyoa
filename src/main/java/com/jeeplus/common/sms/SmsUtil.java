package com.jeeplus.common.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jeeplus.common.utils.FormatUtil;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.linkage.netmsg.NetMsgclient;
import com.linkage.netmsg.server.ReceiveMsg;

import static com.jeeplus.common.utils.FormatUtil.dateToString;

/**
 * 短信发送接口
 * @author Administrator
 *
 */
public class SmsUtil {
	public static String hostPath="http://api.jinying365.com";
	
	/**
	 * 发送短信
	 * @param mobiles 手机号码 多个“,”号间隔
	 * @param content
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static int sendSms(String mobiles,String content) throws HttpException, IOException{
//		HttpClient httpClient = new HttpClient();
//        //发送即时消息
//		String url = "http://210.5.158.31/hy/";
//		PostMethod postMethod = new PostMethod(url);
//		NameValuePair[] data = {
//				new NameValuePair("uid","50106"),
//				new NameValuePair("auth", "d7de7b260295cad96812fe9698a1d676"),//jinyingjinying222
//				new NameValuePair("expid","0"),
//				new NameValuePair("mobile",mobiles),
//				new NameValuePair("msg", java.net.URLEncoder.encode(content,"GBK")), 
//				};
//		postMethod.setRequestBody(data);
//		int statusCode = httpClient.executeMethod(postMethod);
		int statusCode = 0;
		NetMsgclient client   = new NetMsgclient();
        
        /*ReceiveMsgImpl为ReceiveMsg类的子类，构造时，构造自己继承的子类就行*/
        ReceiveMsg receiveMsg = new ReceiveImpl();
        
        /*初始化参数*/
        client = client.initParameters("202.102.41.101", 9005, "0512C00160339", "0339qwer",receiveMsg);

        try {
            
            /*登录认证*/
            boolean isLogin = client.anthenMsg(client);
            if(isLogin){
            	System.out.println("login sucess");
            	String rtnFlag = client.sendMsg(client, 0, mobiles, content, 1);
            	if(!"16".equals(rtnFlag)){
            		statusCode = 200;
            	}
            }
            
           //关闭发送短信与接收短信连接
           client.closeConn();
            
        } catch (Exception e1) {
            e1.printStackTrace();
        }
		return statusCode;
	}
	
	public static void groupsendSms(String mobiles,String content) throws HttpException, IOException{
//		HttpClient httpClient = new HttpClient();
//        //发送即时消息
//		String url = "http://210.5.158.31/hy/";
//		PostMethod postMethod = new PostMethod(url);
//		NameValuePair[] data = {
//				new NameValuePair("uid","50106"),
//				new NameValuePair("auth", "d7de7b260295cad96812fe9698a1d676"),//jinyingjinying222
//				new NameValuePair("expid","0"),
//				new NameValuePair("mobile",mobiles),
//				new NameValuePair("msg", java.net.URLEncoder.encode(content,"GBK")), 
//				};
//		postMethod.setRequestBody(data);
//		int statusCode = httpClient.executeMethod(postMethod);
		int statusCode = 0;
		NetMsgclient client   = new NetMsgclient();
        
        /*ReceiveMsgImpl为ReceiveMsg类的子类，构造时，构造自己继承的子类就行*/
        ReceiveMsg receiveMsg = new ReceiveImpl();
        
        /*初始化参数*/
        client = client.initParameters("202.102.41.101", 9005, "0512C00160339", "0339qwer",receiveMsg);

        try {
            
            /*登录认证*/
            boolean isLogin = client.anthenMsg(client);
            if(isLogin){
            	System.out.println("login sucess");
            	String[] mbs = mobiles.split(",");
            	for (String mobile : mbs) {
            		String rtnFlag = client.sendMsg(client, 0, mobile, content, 1);
                	if(!"16".equals(rtnFlag)){
                		statusCode = 200;
	            		System.out.println(mobile+":群发短信发送成功！");
	            	}
				}
//            	String rtnFlag = client.sendMsg(client, 0, mobiles, content, 1);
//            	if(!"16".equals(rtnFlag)){
//            		statusCode = 200;
//            	}
            }
            
           //关闭发送短信与接收短信连接
           client.closeConn();
            
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//		return statusCode;
	}
	
	public static int sendSmsOld(String mobiles,String content) throws HttpException, IOException{
		HttpClient httpClient = new HttpClient();
        //发送即时消息
		String url = "http://210.5.158.31/hy/";
		PostMethod postMethod = new PostMethod(url);
		NameValuePair[] data = {
				new NameValuePair("uid","50106"),
				new NameValuePair("auth", "d7de7b260295cad96812fe9698a1d676"),//jinyingjinying222
				new NameValuePair("expid","0"),
				new NameValuePair("mobile",mobiles),
				new NameValuePair("msg", java.net.URLEncoder.encode(content,"GBK")), 
				};
		postMethod.setRequestBody(data);
		int statusCode = httpClient.executeMethod(postMethod);
		return statusCode;
	}

	public static String sendSmsByApi(String mobile,String content){
		//发送短信
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("mobile", mobile);
		//map.put("mobile", "15370194038");
		map.put("content", content);
		//map.put("platform", "1");
		/**通用字段start**/
		String dateStr=dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String signatureStr= SignatureApiUtil.createSignature("", "", "jinying", dateStr);
		map.put("signature", signatureStr);
		//map.put("signature", "136%75%75%76%122%122%77%75%76%82%72%75%83%72%77%78%59%76%80%85%75%82%85%77%77%122%133%132%137%148%132%137%130%");
		map.put("timeStamp", dateStr);
		/**通用字段end**/
		String result=HttpUtil.post(hostPath+"/tool/sendSms.htm", map);
		System.out.println("result:"+result);
		return result;
		//String result=HttpUtil.post("http://172.19.10.121:8099/tool/sendSms.htm", map);
		//System.out.println("result:"+result);
	}
	
	public static void main(String[] args) throws HttpException, IOException {
		//sendSms("13776219258","测试发短信哦哦哦O(∩_∩)O~");
		//groupsendSms("18662623254,15370194038","测试群发短信哦哦哦O(∩_∩)O~");
		SmsUtil.sendSmsByApi("15370194038,17625746240","你好api");
	}

	public static String dateToString(Date date,String formatStr){
		return new SimpleDateFormat(formatStr).format(date);
	}
}
