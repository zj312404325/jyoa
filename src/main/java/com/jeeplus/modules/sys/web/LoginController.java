/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.dwr.DwrUtil;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.security.shiro.session.SessionDAO;
import com.jeeplus.common.servlet.ValidateCodeServlet;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.websocket.onchat.ChatServerPool;
import com.jeeplus.common.websocket.utils.Constant;
import com.jeeplus.modules.ehr.entity.Trainplan;
import com.jeeplus.modules.ehr.service.TrainplanService;
import com.jeeplus.modules.ehr.service.UserInfoService;
import com.jeeplus.modules.iim.entity.MailBox;
import com.jeeplus.modules.iim.entity.MailPage;
import com.jeeplus.modules.iim.service.MailBoxService;
import com.jeeplus.modules.leipiflow.entity.LeipiRunProcess;
import com.jeeplus.modules.leipiflow.service.LeipiRunProcessService;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.entity.SystemNotification;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.security.FormAuthenticationFilter;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.jeeplus.modules.sys.service.SystemNotificationService;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.engine.TaskService;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.*;

/**
 * 登录Controller
 * @author jeeplus
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private OaNotifyService oaNotifyService;
	
	@Autowired
	private MailBoxService mailBoxService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private LeipiRunProcessService leipiRunProcessService;
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SystemNotificationService systemNotificationService;

	@Autowired
	private TrainplanService trainplanService;
	/**
	 * 管理登录
	 * @throws IOException 
	 */
	@RequestMapping(value = "${adminPath}/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}
		
		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath;
		}
		
		
		 SavedRequest savedRequest = WebUtils.getSavedRequest(request);//获取跳转到login之前的URL
		// 如果是手机没有登录跳转到到login，则返回JSON字符串
		 if(savedRequest != null){
			 String queryStr = savedRequest.getQueryString();
			if(	queryStr!=null &&( queryStr.contains("__ajax") || queryStr.contains("mobileLogin"))){
				AjaxJson j = new AjaxJson();
				j.setSuccess(false);
				j.setErrorCode("0");
				j.setMsg("没有登录!");
				return renderString(response, j);
			}
		 }
		 
		//查看是否记住我
		 if(FormatUtil.isNoEmpty(CookieUtils.getCookie(request, "oajyuname"))){
			 request.setAttribute("oajyuname", CookieUtils.getCookie(request, "oajyuname"));
			 request.setAttribute("oajypwd", URLDecoder.decode(CookieUtils.getCookie(request, "oajypwd"), "UTF-8"));
			 request.setAttribute("rememberMe", true);
		 }
		 
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		// 如果是手机登录，则返回JSON字符串
		if (mobile){
			AjaxJson j = new AjaxJson();
			j.setSuccess(false);
			j.setMsg(message);
			j.put("username", username);
			j.put("name","");
			j.put("mobileLogin", mobile);
			j.put("JSESSIONID", "");
	        return renderString(response, j.getJsonStr());
		}
		
		return "modules/sys/sysLogin";
	}

	/**
	 * 管理登录
	 * @throws IOException 
	 */
	@RequestMapping(value = "${adminPath}/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		Principal principal = UserUtils.getPrincipal();
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			//清除dwr中用户信息
			//DwrUtil.scriptSessionMap.remove(UserUtils.getUser().getId());
			//清除登录用户session
			Global.userSessionMap.remove(UserUtils.getUser().getId());
			UserUtils.clearCache(UserUtils.getUser());
			UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
			UserUtils.getSubject().logout();
		}
	   // 如果是手机客户端退出跳转到login，则返回JSON字符串
			String ajax = request.getParameter("__ajax");
			if(	ajax!=null){
				model.addAttribute("success", "1");
				model.addAttribute("msg", "退出成功");
				return renderString(response, model);
			}
		 return "redirect:" + adminPath+"/login";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Principal principal = UserUtils.getPrincipal();
		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);

		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		System.out.println(Global.SEARCH_DAYS_LIMIT+"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}

		HttpSession session = request.getSession(true);
		if(FormatUtil.isNoEmpty(session)){
			if(!FormatUtil.isNoEmpty(session.getAttribute("jyoalogintime"))){
				session.setAttribute("jyoalogintime",FormatUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}
		}

//		// 登录成功后，获取上次登录的当前站点ID
//		UserUtils.putCache("siteId", StringUtils.toLong(CookieUtils.getCookie(request, "siteId")));

//		System.out.println("==========================a");
//		try {
//			byte[] bytes = com.jeeplus.common.utils.FileUtils.readFileToByteArray(
//					com.jeeplus.common.utils.FileUtils.getFile("c:\\sxt.dmp"));
//			UserUtils.getSession().setAttribute("kkk", bytes);
//			UserUtils.getSession().setAttribute("kkk2", bytes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		for (int i=0; i<1000000; i++){
////			//UserUtils.getSession().setAttribute("a", "a");
////			request.getSession().setAttribute("aaa", "aa");
////		}
//		System.out.println("==========================b");
		//
		OaNotify oaNotify = new OaNotify();
		oaNotify.setSelf(true);
		oaNotify.setReadFlag("0");
		request.setAttribute("count", oaNotifyService.findList(oaNotify).size());//未读通知条数
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		request.setAttribute("page", page);

//		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
//		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
//				.includeProcessVariables().orderByTaskCreateTime().desc();
//		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
//				.includeProcessVariables().active().orderByTaskCreateTime().desc();
//		request.setAttribute("taskcount", todoTaskQuery.list().size()+toClaimQuery.list().size());//未处理任务数量

		//获取待办任务数量
		LeipiRunProcess leipiRunProcess=new LeipiRunProcess();
		leipiRunProcess.setUpid(UserUtils.getUser().getId());
		leipiRunProcess.setIsDel(0);
		leipiRunProcess.setStatus(0);//状态0初始 ,1通过,2打回
		leipiRunProcess.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				leipiRunProcess.setSearchdays(4);
			}
		}
		if(FormatUtil.isNoEmpty(Global.SEARCH_DATE_FROM)){
			leipiRunProcess.setSearchdatefrom(FormatUtil.StringToDate(Global.SEARCH_DATE_FROM,"yyyy-MM-dd"));
		}
		request.setAttribute("taskcount", leipiRunProcessService.findFirstList(leipiRunProcess).size());//未处理任务数量
		request.setAttribute("todotaskmenu", Global.TODOTASKMENU);//未处理任务数量

		//获取EHR申请总数
		LeipiRunProcess leipiRunProcessEhr=new LeipiRunProcess();
		leipiRunProcessEhr.setUpid(UserUtils.getUser().getId());
		leipiRunProcessEhr.setIsDel(0);
		leipiRunProcessEhr.setStatus(0);//状态0初始 ,1通过,2打回
		leipiRunProcessEhr.setFlowtype("1");
		request.setAttribute("ehrtodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessEhr).size());
		request.setAttribute("ehrtodotasktotalmenu", Global.EHRTODOTASKTOTALMENU);
		//获取在职申请总数
		LeipiRunProcess leipiRunProcessTotal=new LeipiRunProcess();
		leipiRunProcessTotal.setUpid(UserUtils.getUser().getId());
		leipiRunProcessTotal.setIsDel(0);
		leipiRunProcessTotal.setStatus(0);//状态0初始 ,1通过,2打回
		leipiRunProcessTotal.setFlowtype("1");
		leipiRunProcessTotal.setFlowids("'"+Global.ZHUANZHENG_FLOW_ID+"','"+Global.SALARY_FLOW_ID
				+"','"+Global.REWARD_FLOW_ID+"','"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
		request.setAttribute("todotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessTotal).size());
		request.setAttribute("todotasktotalmenu", Global.TODOTASKTOTALMENU);
		//获取转正申请待办数量
		LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
		leipiRunProcess2.setUpid(UserUtils.getUser().getId());
		leipiRunProcess2.setIsDel(0);
		leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
        leipiRunProcess2.setFlowtype("1");
		leipiRunProcess2.setFlowid(Global.ZHUANZHENG_FLOW_ID);
		request.setAttribute("zhuanzhengtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
		request.setAttribute("zhuanzhengtaskmenu", Global.ZHUANZHENGTODOTASKMENU);
        //获取薪资调整申请待办数量
        LeipiRunProcess leipiRunProcess3=new LeipiRunProcess();
        leipiRunProcess3.setUpid(UserUtils.getUser().getId());
        leipiRunProcess3.setIsDel(0);
        leipiRunProcess3.setStatus(0);//状态0初始 ,1通过,2打回
        leipiRunProcess3.setFlowtype("1");
        leipiRunProcess3.setFlowid(Global.SALARY_FLOW_ID);
        request.setAttribute("xinzitaskcount", leipiRunProcessService.findFirstList(leipiRunProcess3).size());
        request.setAttribute("xinzitaskmenu", Global.SALARYTODOTASKMENU);
        //获取奖惩考核申请待办数量
        LeipiRunProcess leipiRunProcess4=new LeipiRunProcess();
        leipiRunProcess4.setUpid(UserUtils.getUser().getId());
        leipiRunProcess4.setIsDel(0);
        leipiRunProcess4.setStatus(0);//状态0初始 ,1通过,2打回
        leipiRunProcess4.setFlowtype("1");
        leipiRunProcess4.setFlowid(Global.REWARD_FLOW_ID);
        request.setAttribute("jiangchengtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess4).size());
        request.setAttribute("jiangchengtaskmenu", Global.REWARDTODOTASKMENU);
		//获取岗位变动申请待办数量
		LeipiRunProcess leipiRunProcess5=new LeipiRunProcess();
		leipiRunProcess5.setUpid(UserUtils.getUser().getId());
		leipiRunProcess5.setIsDel(0);
		leipiRunProcess5.setStatus(0);//状态0初始 ,1通过,2打回
		leipiRunProcess5.setFlowtype("1");
		leipiRunProcess5.setFlowid(Global.POSTSHENQING_FLOW_ID);
		request.setAttribute("postshenqingtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess5).size());
		request.setAttribute("postshenqingtaskmenu", Global.POSTSHENQINGTODOTASKMENU);
		//获取岗位变动交接待办数量
		LeipiRunProcess leipiRunProcess6=new LeipiRunProcess();
		leipiRunProcess6.setUpid(UserUtils.getUser().getId());
		leipiRunProcess6.setIsDel(0);
		leipiRunProcess6.setStatus(0);//状态0初始 ,1通过,2打回
		leipiRunProcess6.setFlowtype("1");
		leipiRunProcess6.setFlowid(Global.POSTJIAOJIE_FLOW_ID);
		request.setAttribute("postjiaojietaskcount", leipiRunProcessService.findFirstList(leipiRunProcess6).size());
		request.setAttribute("postjiaojietaskmenu", Global.POSTJIAOJIETODOTASKMENU);
		//获取岗位变动总数
		LeipiRunProcess leipiRunProcesspost=new LeipiRunProcess();
		leipiRunProcesspost.setUpid(UserUtils.getUser().getId());
		leipiRunProcesspost.setIsDel(0);
		leipiRunProcesspost.setStatus(0);//状态0初始 ,1通过,2打回
		leipiRunProcesspost.setFlowtype("1");
		leipiRunProcesspost.setFlowids("'"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
		request.setAttribute("posttodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcesspost).size());
		request.setAttribute("posttodotasktotalmenu", Global.POSTTODOTASKMENU);
		//获取离职交接申请总数
		LeipiRunProcess leipiRunProcessleave=new LeipiRunProcess();
		leipiRunProcessleave.setUpid(UserUtils.getUser().getId());
		leipiRunProcessleave.setIsDel(0);
		leipiRunProcessleave.setStatus(0);//状态0初始 ,1通过,2打回
		leipiRunProcessleave.setFlowtype("1");
		leipiRunProcessleave.setFlowid(Global.LEAVEJIAOJIE_FLOW_ID);
		request.setAttribute("leavejiaojietodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessleave).size());
		request.setAttribute("leavejiaojietodotasktotalmenu", Global.LEAVEJIAOJIETODOTASKMENU);
		request.setAttribute("leavetodotasktotalmenu", Global.LEAVETODOTASKMENU);



		MailBox mailBox = new MailBox();
		mailBox.setReceiver(UserUtils.getUser());
		mailBox.setReadstatus("0");//筛选未读
		MailPage<MailBox> page1= new MailPage<MailBox>(request, response);
		page1.setOrderBy("a.sendtime desc");
		Page<MailBox> mailPage = mailBoxService.findPage(page1, mailBox);
		request.setAttribute("noReadCount", mailBoxService.getCount(mailBox));
		request.setAttribute("mailPage", mailPage);
		// 默认风格
		String indexStyle = "default";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
				continue;
			}
			if (cookie.getName().equalsIgnoreCase("theme")) {
				indexStyle = cookie.getValue();
			}
		}
		Constant.User_Status_Map.put(UserUtils.getUser().getLoginName(),Constant._online_user_);
		Global.userSessionMap.put(UserUtils.getUser().getId(),CookieUtils.getCookie(request,Global.JPSESSION));

		List<SystemNotification> list = systemNotificationService.findList(new SystemNotification());
		if(list.size()>0){
			SystemNotification systemNotification = list.get(0);
			request.setAttribute("systemNotification",systemNotification.getContent());
		}else{
			request.setAttribute("systemNotification","");
		}

		if(FormatUtil.isNoEmpty(UserUtils.getUser().getPwddate())){
			if(FormatUtil.daysBetween(UserUtils.getUser().getPwddate(),new Date()) >= 30){
				request.setAttribute("pwdchange",true);
			}else{
				request.setAttribute("pwdchange",false);
			}
		}else{
			request.setAttribute("pwdchange",true);
		}

		// 要添加自己的风格，复制下面三行即可
		if (StringUtils.isNotEmpty(indexStyle)
				&& indexStyle.equalsIgnoreCase("ace")) {
			return "modules/sys/sysIndex-ace";
		}

		return "modules/sys/sysIndex";
	}

	//培训计划提醒
	@RequestMapping(value = "${adminPath}/trainPlanTip")
	public void trainPlanTip(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		//判断当天是否有没提示的培训机会
		Trainplan trainplan=new Trainplan();
		trainplan.setUserid(UserUtils.getUser().getId());
		trainplan.setIsnotify("0");
		trainplan.setTraindate(new Date());
		List<Trainplan> trainplanList=trainplanService.findList(trainplan);
		if(FormatUtil.isNoEmpty(trainplanList)&&trainplanList.size()>0){
			for (Trainplan tp:trainplanList) {
				//推送
				//推送给前端页面 Start
				final DwrUtil dwrUtil = new DwrUtil();
				final String title = tp.getTitle();
				final String trainplanId = tp.getId();
				final String ctx = request.getContextPath();
				final User loginUser = UserUtils.getUser();
				final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(loginUser.getLoginName());
				if(toUserConn != null){
					Thread t = new Thread(new Runnable(){
						public void run(){
							try {
								Map map = new HashMap();
								map.put("cnt1", "-1");
								map.put("cnt2", "-1");
								map.put("cnt3", "-1");
								List<Map> lst = new ArrayList<Map>();
								Map maptemp = new HashMap();
								maptemp.put("url", ctx+adminPath+"/ehr/trainplan/view?id="+trainplanId);
								maptemp.put("title", "你今天有一个主题为【"+title+"】的培训计划,请尽快落实！");
								lst.add(maptemp);
								map.put("msg", JSONArray.fromObject(lst).toString());
								String msg = JSONObject.fromObject(map).toString();
								String message = Constant._remind_window_+msg;
								ChatServerPool.sendMessageToUser(toUserConn,message);

								//将通知类型修改为已通
								Trainplan tpl=trainplanService.get(trainplanId);
								tpl.setIsnotify("1");
								trainplanService.save(tpl);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}});
					t.start();
				}
			}
		}
	}

	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	
	
	/**
	 * 首页
	 * @throws IOException 
	 */
	@RequestMapping(value = "${adminPath}/home")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		Boolean isNew = false;
		isNew = UserUtils.isNewEmployee();
		request.setAttribute("isNew", isNew);
		request.setAttribute("isOnline", Global.getIsOnline());
		return "modules/sys/sysHome";
		
	}
	
	/**
	 * 获取通知条数记录
	 * @throws IOException 
	 */
	@RequestMapping(value = "/oanotifycount")
	public void oanotifycount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map result=new HashMap<String, Object>();
		String id=request.getParameter("id");
		OaNotify oaNotify = new OaNotify();
		oaNotify.setId(id);
		oaNotify.setSelf(true);
		oaNotify.setReadFlag("0");
		oaNotify.setExclude(true);
		result.put("notifycount", oaNotifyService.findList(oaNotify).size());//未读通知条数
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify); 
		String indextype=request.getParameter("indextype");
		List<Map> maplst=new ArrayList<Map>();
		for (OaNotify oanotify : page.getList()) {
			Map map=new HashMap<String, Object>();
			map.put("id", oanotify.getId());
			if(FormatUtil.isNoEmpty(indextype)){
				map.put("title", StringUtils.abbr(oanotify.getTitle(),15));
			}else{
				map.put("title", StringUtils.abbr(oanotify.getTitle(),20));
			}
			map.put("millsecond", UserUtils.getTime(oanotify.getUpdateDate()));
			maplst.add(map);
		}
		result.put("lst", maplst);
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter writer=response.getWriter();
		JSONObject jsonObject=JSONObject.fromObject(result);
		String a=jsonObject.toString();
		writer.write(a);
		writer.close();
	}
	
	/**
	 * 获取信箱条数记录
	 * @throws IOException 
	 */
	@RequestMapping(value = "/mailcount")
	public void mailcount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map result=new HashMap<String, Object>();
		String id=request.getParameter("id");
		MailBox mailBox = new MailBox();
		mailBox.setId(id);
		mailBox.setReceiver(UserUtils.getUser());
		mailBox.setReadstatus("0");//筛选未读
		mailBox.setExclude(true);
		MailPage<MailBox> page1= new MailPage<MailBox>(request, response);
		page1.setOrderBy("a.sendtime desc");
		Page<MailBox> mailPage = mailBoxService.findPage(page1, mailBox); 
		result.put("mailcount", mailBoxService.getCount(mailBox));//未读邮件数量
		String indextype=request.getParameter("indextype");
		List<Map> maplst=new ArrayList<Map>();
		for (MailBox mailbox : mailPage.getList()) {
			Map map=new HashMap<String, Object>();
			map.put("id", mailbox.getId());
			map.put("name", mailbox.getSender().getName());
			map.put("photo", mailbox.getSender().getPhoto());
			map.put("sendtime", UserUtils.getTime(mailbox.getSendtime()));
			if(FormatUtil.isNoEmpty(indextype)){
				map.put("title", StringUtils.abbr(mailbox.getMail().getTitle(),20));
			}else{
				map.put("title", StringUtils.abbr(mailbox.getMail().getTitle(),10));
			}
			map.put("overview", mailbox.getMail().getOverview());
			map.put("sendtime1", FormatUtil.DateToString(mailbox.getSendtime(),"yyyy-MM-dd HH:mm:ss"));
			maplst.add(map);
		}
		result.put("lst", maplst);
		
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter writer=response.getWriter();
		JSONObject jsonObject=JSONObject.fromObject(result);
		String a=jsonObject.toString();
		writer.write(a);
		writer.close();
	}
}
