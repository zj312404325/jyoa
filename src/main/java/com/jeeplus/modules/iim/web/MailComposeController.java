/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.dwr.DwrUtil;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.websocket.onchat.ChatServerPool;
import com.jeeplus.common.websocket.utils.Constant;
import com.jeeplus.modules.iim.entity.MailBox;
import com.jeeplus.modules.iim.entity.MailCompose;
import com.jeeplus.modules.iim.entity.MailPage;
import com.jeeplus.modules.iim.service.MailBoxService;
import com.jeeplus.modules.iim.service.MailComposeService;
import com.jeeplus.modules.iim.service.MailService;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 发件箱Controller
 * @author jeeplus
 * @version 2015-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/iim/mailCompose")
public class MailComposeController extends BaseController {

	@Autowired
	private MailComposeService mailComposeService;
	
	@Autowired
	private MailBoxService mailBoxService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private OaNotifyService oaNotifyService;
	
	@Autowired
	private TaskService taskService;
	
	@ModelAttribute
	public MailCompose get(@RequestParam(required=false) String id) {
		MailCompose entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mailComposeService.get(id);
		}
		if (entity == null){
			entity = new MailCompose();
		}
		return entity;
	}
	
	
	
	/*
	 * 写站内信
	 */
	@RequestMapping(value = {"sendLetter"})
	public String sendLetter(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user = systemService.getUser(user.getId());
		model.addAttribute("receiver", user);
		
		//查询未读的条数
		MailBox serachBox = new MailBox();
		serachBox.setReadstatus("0");
		serachBox.setReceiver(UserUtils.getUser());
		model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));
		
		//查询总条数
		MailBox serachBox2 = new MailBox();
		serachBox2.setReceiver(UserUtils.getUser());
		model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));
		
		//查询已发送条数
		MailCompose serachBox3 = new MailCompose();
		serachBox3.setSender(UserUtils.getUser());
		serachBox3.setStatus("1");//已发送
		model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));
		
		//查询草稿箱条数
		MailCompose serachBox4 = new MailCompose();
		serachBox4.setSender(UserUtils.getUser());
		serachBox4.setStatus("0");//草稿
		model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
		
		return "modules/iim/mail_send";
	}
	
	/*
	 * 回复站内信
	 */
	@RequestMapping(value = {"replyLetter"})
	public String replyLetter(MailBox mailBox, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("mailBox",  mailBoxService.get(mailBox.getId()));
		
		//查询未读的条数
		MailBox serachBox = new MailBox();
		serachBox.setReadstatus("0");
		serachBox.setReceiver(UserUtils.getUser());
		model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));
		
		//查询总条数
		MailBox serachBox2 = new MailBox();
		serachBox2.setReceiver(UserUtils.getUser());
		model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));
		
		//查询已发送条数
		MailCompose serachBox3 = new MailCompose();
		serachBox3.setSender(UserUtils.getUser());
		serachBox3.setStatus("1");//已发送
		model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));
		
		//查询草稿箱条数
		MailCompose serachBox4 = new MailCompose();
		serachBox4.setSender(UserUtils.getUser());
		serachBox4.setStatus("0");//草稿
		model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
		
		return "modules/iim/mail_reply";
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(MailCompose mailCompose, HttpServletRequest request, HttpServletResponse response, Model model) {
		mailCompose.setSender(UserUtils.getUser());
		Page<MailCompose> page = mailComposeService.findPage(new MailPage<MailCompose>(request, response), mailCompose); 
		model.addAttribute("page", page);
		//查询未读的条数
		MailBox serachBox = new MailBox();
		serachBox.setReadstatus("0");
		serachBox.setReceiver(UserUtils.getUser());
		model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));
		
		//查询总条数
		MailBox serachBox2 = new MailBox();
		serachBox2.setReceiver(UserUtils.getUser());
		model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));
		
		//查询已发送条数
		MailCompose serachBox3 = new MailCompose();
		serachBox3.setMail(mailCompose.getMail());
		serachBox3.setSender(UserUtils.getUser());
		serachBox3.setStatus("1");//已发送
		model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));
		
		//查询草稿箱条数
		MailCompose serachBox4 = new MailCompose();
		serachBox4.setMail(mailCompose.getMail());
		serachBox4.setSender(UserUtils.getUser());
		serachBox4.setStatus("0");//草稿
		model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
		
		
		if(mailCompose.getStatus()== null || mailCompose.getStatus().equals("0")){
			return "modules/iim/mailDraftList";//草稿箱
		}
		return "modules/iim/mailComposeList";//已发送
	}

	@RequestMapping(value = "detail")//打开已发送信件
	public String detail(MailCompose mailCompose, Model model) {
		model.addAttribute("mailCompose", mailCompose);
		
		
		//查询未读的条数
		MailBox serachBox = new MailBox();
		serachBox.setReadstatus("0");
		serachBox.setReceiver(UserUtils.getUser());
		model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));
		
		//查询总条数
		MailBox serachBox2 = new MailBox();
		serachBox2.setReceiver(UserUtils.getUser());
		model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));
		
		//查询已发送条数
		MailCompose serachBox3 = new MailCompose();
		serachBox3.setSender(UserUtils.getUser());
		serachBox3.setStatus("1");//已发送
		model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));
		
		//查询草稿箱条数
		MailCompose serachBox4 = new MailCompose();
		serachBox4.setSender(UserUtils.getUser());
		serachBox4.setStatus("0");//草稿
		model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
		
		return "modules/iim/mailComposeDetail";
	}
	
	@RequestMapping(value = "draftDetail")//打开草稿
	public String draftDetail(MailCompose mailCompose, Model model) {
		//查询未读的条数
		MailBox serachBox = new MailBox();
		serachBox.setReadstatus("0");
		serachBox.setReceiver(UserUtils.getUser());
		model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));
		
		//查询总条数
		MailBox serachBox2 = new MailBox();
		serachBox2.setReceiver(UserUtils.getUser());
		model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));
		
		//查询已发送条数
		MailCompose serachBox3 = new MailCompose();
		serachBox3.setSender(UserUtils.getUser());
		serachBox3.setStatus("1");//已发送
		model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));
		
		//查询草稿箱条数
		MailCompose serachBox4 = new MailCompose();
		serachBox4.setSender(UserUtils.getUser());
		serachBox4.setStatus("0");//草稿
		model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
		
		mailCompose = mailComposeService.get(mailCompose.getId());
		model.addAttribute("mailCompose", mailCompose);
		model.addAttribute("receiverIds",mailCompose.getReceiver().getId());
		model.addAttribute("receiverNames",mailCompose.getReceiver().getName());
		return "modules/iim/mailDraftDetail";
	}

	@RequestMapping(value = "save")
	public String save(MailCompose mailCompose, Model model, HttpServletRequest request, HttpServletResponse response) {
		/*if (!beanValidator(model, mailCompose.getMail())){
			return detail(mailCompose, model);
		}*/
		mailService.saveOnlyMain(mailCompose.getMail());
		Date date = new Date(System.currentTimeMillis());
		mailCompose.setSender(UserUtils.getUser());
		mailCompose.setSendtime(date);
		for(final User receiver : mailCompose.getReceiverList()){
			mailCompose.setReceiver(receiver);
			mailCompose.setId(null);//标记为新纪录，每次往发件箱插入一条记录
			mailComposeService.save(mailCompose);//0 显示在草稿箱，1 显示在已发送需同时保存到收信人的收件箱。
		
		
			if(mailCompose.getStatus().equals("1"))//已发送，同时保存到收信人的收件箱
			{
				MailBox mailBox = new MailBox();
				mailBox.setReadstatus("0");
				mailBox.setReceiver(receiver);
				mailBox.setSender(UserUtils.getUser());
				mailBox.setMail(mailCompose.getMail());
				mailBox.setSendtime(date);
				mailBoxService.save(mailBox);
				
				//推送给前端页面 Start
//			    final DwrUtil dwrUtil = new DwrUtil();
			    final User user = UserUtils.get(receiver.getId());
			    final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(user.getLoginName());
				if(toUserConn != null){
			    	final String ctx = request.getContextPath();
				    final String mailboxId = mailBox.getId();
			    	Thread t = new Thread(new Runnable(){  
			            public void run(){  
			            	try {
			            		
			            		Map map = new HashMap();
//			            		OaNotify oaNotify2 = new OaNotify();
//			            		oaNotify2.setSelf(true);
//			            		oaNotify2.setReadFlag("0");
//			            		oaNotify2.setCurrentUser(user);
//			            		List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
//			            		map.put("cnt1", list1.size());
//			            		
//			            		String userId = user.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
//			            		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
//			            				.includeProcessVariables().orderByTaskCreateTime().desc();
//			            		map.put("cnt2", todoTaskQuery.list().size());
			            		map.put("cnt1", "-1");
			            		map.put("cnt2", "-1");
			            		MailBox mailBox = new MailBox();
			            		mailBox.setReceiver(user);
			            		mailBox.setReadstatus("0");//筛选未读
			            		List<MailBox> list3 = mailBoxService.findList(mailBox);
			            		map.put("cnt3", list3.size());
				    			
			            		List<Map> lst = new ArrayList<Map>();
			        			Map maptemp = new HashMap();
			        			maptemp.put("url", ctx+adminPath+"/iim/mailBox/detail?id="+mailboxId);
			        			maptemp.put("title", "你收到了新的站内信,请注意查看！");
			        			lst.add(maptemp);
			            		map.put("msg", JSONArray.fromObject(lst).toString());
			            		
//			            		Thread.sleep(Global.THREAD_SLEEP_TIME);
//			            		dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), user.getId(),JSONObject.fromObject(map).toString());
			            		String msg = JSONObject.fromObject(map).toString();
			            		String message = Constant._remind_window_+msg;
								ChatServerPool.sendMessageToUser(toUserConn,message);
				             } catch (Exception e) {
				                   e.printStackTrace();
				             }
			            }});  
			        t.start();
			    }
			    //推送给前端页面 end
			}
		}
		
		request.setAttribute("mailCompose", mailCompose);
		return "modules/iim/mail_compose_success";
	}
	
	@RequestMapping(value = "delete")
	public String delete(MailCompose mailCompose, RedirectAttributes redirectAttributes) {
		mailComposeService.delete(mailCompose);
		addMessage(redirectAttributes, "删除站内信成功");
		return "redirect:"+Global.getAdminPath()+"/iim/mailCompose/?repage&orderBy=sendtime desc&status="+mailCompose.getStatus();
	}
	
	/**
	 * 批量删除已发送
	 */
	@RequestMapping(value = "deleteAllCompose")
	public String deleteAllCompose(String ids, Model model, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			mailComposeService.delete(mailComposeService.get(id));
		}
		
		
		
		
		addMessage(redirectAttributes, "删除邮件成功");
		return "redirect:"+Global.getAdminPath()+"/iim/mailCompose/?repage&status=1&orderBy=sendtime desc";
	}
	
	/**
	 * 批量删除草稿箱
	 */
	@RequestMapping(value = "deleteAllDraft")
	public String deleteAllDraft(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			mailComposeService.delete(mailComposeService.get(id));
		}
		addMessage(redirectAttributes, "删除邮件成功");
		return "redirect:"+Global.getAdminPath()+"/iim/mailCompose/?repage&status=0&orderBy=sendtime desc";
	}

}