package com.jeeplus.common.utils;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.jeeplus.common.config.Global;
import org.apache.log4j.Logger;

import java.util.Map;

public class JPushUtil {
	private  static Logger log = Logger.getLogger("JPushUtil");
	
	/**
	 * alert方式发送给所有用户
	 * @param msg
	 */
	public static void SendMsg(String msg){
		try {
			if (Boolean.parseBoolean(Global.IS_ONLINE)) {
				JPushClient jpushClient = new JPushClient(Global.masterSecret, Global.appKey, 3);
				// For push, all you need do is to build PushPayload object.
				PushPayload payload= PushPayload.alertAll(msg);

				try {
					PushResult result = jpushClient.sendPush(payload);
					log.info("通知发送结果:"+result);

				} catch (APIConnectionException e) {
					// Connection error, should retry later
					log.info("连接失败稍后重试", e);

				} catch (APIRequestException e) {
					// Should review the error, and fix the request
					log.error("Should review the error, and fix the request", e);
					log.info("HTTP Status: " + e.getStatus());
					log.info("Error Code: " + e.getErrorCode());
					log.info("Error Message: " + e.getErrorMessage());
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log.info("config.properties缺少isOnline配置");
		}

	}
	
	/**
	 * 向指定注册id用户发送信息
	 * @param msg
	 */
	public static void SendMsg(String msg,String registid){
		JPushClient jpushClient = new JPushClient(Global.masterSecret, Global.appKey, 3);
		//JPushClient jpushClient = new JPushClient("494c32fb4489a3fe5a641607", "6cacdaebcb9a1883718a271d", 3);
		//TagAliasResult tar=getTagAliasResult(jpushClient,registid);
		PushPayload payload=buildPushObject_all_alias_alert(registid,msg);
		
	    try {
	        PushResult result = jpushClient.sendPush(payload);
	        log.info("通知发送结果:"+result);

	    } catch (APIConnectionException e) {
	        // Connection error, should retry later
	    	log.info("连接失败稍后重试", e);

	    } catch (APIRequestException e) {
	        // Should review the error, and fix the request
	    	log.error("Should review the error, and fix the request", e);
	        log.info("HTTP Status: " + e.getStatus());
	        log.info("Error Code: " + e.getErrorCode());
	        log.info("Error Message: " + e.getErrorMessage());
	    }
	}
	
	/**
	 * 向指定注册id用户发送信息带标题的
	 * @param msg
	 */
	public static void SendMsg(String msg,String registid,String title){
		JPushClient jpushClient = new JPushClient(Global.masterSecret, Global.appKey, 3);
		//TagAliasResult tar=getTagAliasResult(jpushClient,registid);
		PushPayload payload=buildPushObject_android_tag_alertWithTitle(registid,msg,title);


	    try {
	        PushResult result = jpushClient.sendPush(payload);
	        log.info("通知发送结果:"+result);

	    } catch (APIConnectionException e) {
	        // Connection error, should retry later
	    	log.info("连接失败稍后重试", e);

	    } catch (APIRequestException e) {
	        // Should review the error, and fix the request
	    	log.error("Should review the error, and fix the request", e);
	        log.info("HTTP Status: " + e.getStatus());
	        log.info("Error Code: " + e.getErrorCode());
	        log.info("Error Message: " + e.getErrorMessage());
	    }
	}

	/**
	 * 向指定注册id用户发送信息带标题带附加字段
	 * @param msg
	 */
	public static void SendMsg(String msg,String registid,String title,Map extra){
		JPushClient jpushClient = new JPushClient(Global.masterSecret, Global.appKey, 3);
		//TagAliasResult tar=getTagAliasResult(jpushClient,registid);
		PushPayload payload=buildPushObject_android_tag_alertWithTitle(registid,msg,title,extra);


		try {
			PushResult result = jpushClient.sendPush(payload);
			log.info("通知发送结果:"+result);

		} catch (APIConnectionException e) {
			// Connection error, should retry later
			log.info("连接失败稍后重试", e);

		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			log.error("Should review the error, and fix the request", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
		}
	}
	
	public static PushPayload buildPushObject_all_alias_alert(String registrationIds,String ALERT) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationIds))
                .setNotification(Notification.alert(ALERT))
                .build();
    }
	
	public static PushPayload buildPushObject_android_tag_alertWithTitle(String registrationIds,String ALERT,String TITLE) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationIds))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }

	public static PushPayload buildPushObject_android_tag_alertWithTitle(String registrationIds,String ALERT,String TITLE,Map extra) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.registrationId(registrationIds))
				.setNotification(Notification.android(ALERT, TITLE, extra))
				.build();
	}
	
	/**
	 * 根据注册id创建推送识别
	 * @param jpushClient
	 * @param REGISTRATION_ID1
	 * @return
	 */
	public static TagAliasResult getTagAliasResult(JPushClient jpushClient,String REGISTRATION_ID1) {
		try {
	        TagAliasResult result = jpushClient.getDeviceTagAlias(REGISTRATION_ID1);
	        log.info(result.alias);
	        log.info(result.tags.toString());
	        return result;
	    } catch (APIConnectionException e) {
	    	log.error("Connection error. Should retry later. ", e);
	    } catch (APIRequestException e) {
	    	log.error("Error response from JPush server. Should review and fix it. ", e);
	    	log.info("HTTP Status: " + e.getStatus());
	    	log.info("Error Code: " + e.getErrorCode());
	    	log.info("Error Message: " + e.getErrorMessage());
	    }
		return null;
	}

	public static void main(String[] arg){
		System.out.println("helloword");
		SendMsg("helloworld!","140fe1da9ea085e99ba");
	}
}