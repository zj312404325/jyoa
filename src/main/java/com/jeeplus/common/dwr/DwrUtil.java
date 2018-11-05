package com.jeeplus.common.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;











import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.iim.entity.MailBox;
import com.jeeplus.modules.iim.entity.MailPage;
import com.jeeplus.modules.iim.service.MailBoxService;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;



/**
 * DWR 工具类
 * @author Administrator
 *
 */
public class DwrUtil extends BaseController{
	
	private  static Logger log = Logger.getLogger(DwrUtil.class.getName());
	/**保存sessionId对应的ScriptSession*/
	/** 保存当前的ScriptSession key为cookieId  value为ScriptSession*/
	public static Map<String , ScriptSession> scriptSessionMap = new HashMap<String , ScriptSession>();
	/** 保存当前的user和session对应*/
//	public static Map<String , ScriptSession> userScriptMap = new HashMap<String , ScriptSession>();
	
	/**
	 * 绑定用户id和ScriptSession,在要推送的页面加载时调用
	 * @param username 用户名
	 */
	public void onLoad(String userId){
		//在线用户ID不为空
 		if(StringUtils.isBlank(userId)){
			return;
		}
		ScriptSession ss = WebContextFactory.get().getScriptSession();
		//HttpSession hs = WebContextFactory.get().getSession();
		//this.scriptSessionMap.put(hs.getId(), ss);
		this.scriptSessionMap.put(userId, ss);
	}
	
	/**
	 * 发送消息
	 * @param sender 发送者
	 * @param receiverid 接收
	 * @param count 未读信息数
	 */
	public void sendNoReadCount(String senderId,String receiverid,String cnts){
		
//		List<String> cookieIdList = Global.sessionMap.get(receiverid);
		Collection<ScriptSession> collection = new HashSet<ScriptSession>();
//		if(cookieIdList == null || cookieIdList.size() == 0){
//			log.debug("无法找到"+receiverid+"所对应的cookieId");
//			count = 0;
//		}else{
//			for(String cookieId : cookieIdList){
				ScriptSession scriptSession = this.scriptSessionMap.get(receiverid);
				if(scriptSession != null){
					collection.add(scriptSession);
				}
//			}
//		}
		//发送消息
		if(collection == null || collection.size() == 0){
			log.debug("id :+"+receiverid+" 无法找到对应的scriptSession");
		}else{
			Util util = new Util(collection);
			util.addFunctionCall("showMsgCount",cnts ,receiverid);
			System.out.println("DWR推送成功==》receiverid："+receiverid);
		}
	}
	
	public Boolean isOnline(String receiverid){
		
//		List<String> cookieIdList = Global.sessionMap.get(receiverid);
		Collection<ScriptSession> collection = new HashSet<ScriptSession>();
//		if(cookieIdList == null || cookieIdList.size() == 0){
//			log.debug("无法找到"+receiverid+"所对应的cookieId");
//			count = 0;
//		}else{
//			for(String cookieId : cookieIdList){
				ScriptSession scriptSession = this.scriptSessionMap.get(receiverid);
				if(scriptSession != null){
					collection.add(scriptSession);
				}
//			}
//		}
		//发送消息
		if(collection == null || collection.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 发送消息
	 * @param sender 发送者
	 * @param receiverid 接收者id
	 * @param msg 消息内容
	 * @param request
	 */
	public void send(String sender,String receiverid,String msg,HttpServletRequest request){
//		ScriptSession session = this.getScriptSession(receiverid, request);
//		ScriptSession session = this.userScriptMap.get(receiverid);
//		ScriptBuffer script = new ScriptBuffer();
//		script.appendScript("tempfuc(").appendData("nihao!").appendScript(");");
//		session.addScript(script);
//		Util util = new Util(session);
//		util.setStyle("showMessage", "display", "");
//		util.setValue("sender", sender);
//		util.setValue("msg", msg);
//		util.addFunctionCall("showMsg", msg);
	}


}
