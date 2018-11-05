/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.dwr.DwrUtil;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.sms.SmsUtil;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.JPushUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.websocket.onchat.ChatServerPool;
import com.jeeplus.common.websocket.utils.Constant;
import com.jeeplus.modules.flow.entity.*;
import com.jeeplus.modules.flow.service.FlowagentService;
import com.jeeplus.modules.flow.service.FlowapplyService;
import com.jeeplus.modules.flow.service.FlowtemplateService;
import com.jeeplus.modules.iim.service.MailBoxService;
import com.jeeplus.modules.iim.service.MailComposeService;
import com.jeeplus.modules.iim.service.MailService;
import com.jeeplus.modules.oa.dao.OaNotifyFileDao;
import com.jeeplus.modules.oa.dao.OaNotifyRecordDao;
import com.jeeplus.modules.oa.entity.*;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.oa.service.OagroupService;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.engine.TaskService;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * 通知通告Controller
 * @author jeeplus
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNotify")
public class OaNotifyController extends BaseController {

	@Autowired
	private OagroupService oagroupService;
	@Autowired
	private OaNotifyService oaNotifyService;
	
	@Autowired
	private MailBoxService mailBoxService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OaNotifyRecordDao oaNotifyRecordDao;
    @Autowired
    private OaNotifyFileDao oaNotifyFileDao;
	
	@Autowired
	private MailService mailService;
	@Autowired
	private MailComposeService mailComposeService;
	@Autowired
	protected FlowapplyService flowapplyService;
	@Autowired
	protected FlowtemplateService flowtemplateService;
	
	@Autowired
	private FlowagentService flowagentService;
	
	@ModelAttribute
	public OaNotify get(@RequestParam(required=false) String id) {
		OaNotify entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaNotifyService.get(id);
		}
		if (entity == null){
			entity = new OaNotify();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaNotify:list")
	@RequestMapping(value = {"list", ""})
	public String list(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		oaNotify.setSelf(false);
		oaNotify.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				oaNotify.setSearchdays(4);
			}
		}
		if(FormatUtil.isNoEmpty(Global.SEARCH_DATE_FROM)){
			oaNotify.setSearchdatefrom(FormatUtil.StringToDate(Global.SEARCH_DATE_FROM,"yyyy-MM-dd"));
		}
		oaNotify.setCurrentUser(UserUtils.getUser());
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyList";
	}

	/**
	 * 查看，增加，编辑报告表单页面
	 */
	@RequiresPermissions(value={"oa:oaNotify:view","oa:oaNotify:add","oa:oaNotify:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotify = oaNotifyService.getRecordList(oaNotify);
			oaNotify.setCreateBy(userDao.get(oaNotify.getCreateBy().getId()));
		}
		model.addAttribute("oaNotify", oaNotify);
		
		Page<OaNotifyRecord> page = oaNotifyService.find(new Page<OaNotifyRecord>(request, response),new OaNotifyRecord(oaNotify));
		model.addAttribute("page", page);

		List<Oagroup> groups = oagroupService.findList(new Oagroup());
		model.addAttribute("groups", groups);
		if("1".equals(oaNotify.getStatus())){
			return "modules/oa/oaNotifyView";
		}else{
			
			return "modules/oa/oaNotifyForm";
		}
		
	}
	
	/**
	 * 转发
	 */
	@RequiresPermissions(value={"oa:oaNotify:view","oa:oaNotify:add","oa:oaNotify:edit"},logical=Logical.OR)
	@RequestMapping(value = "reform")
	public String reform(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		OaNotify oaNotifynew = new OaNotify();
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotify = oaNotifyService.getRecordList(oaNotify);
			//oaNotify = oaNotifyService.g(oaNotify);
			
			oaNotifynew.setType(oaNotify.getType());
			oaNotifynew.setTitle(oaNotify.getTitle());
			oaNotifynew.setContent(oaNotify.getContent());
			oaNotifynew.setOafiles(oaNotify.getOafiles());
			oaNotifynew.setOaNotifyFileList(oaNotify.getOaNotifyFileList());
		}
		model.addAttribute("oaNotify", oaNotifynew);
		List<Oagroup> groups = oagroupService.findList(new Oagroup());
		model.addAttribute("groups", groups);
		return "modules/oa/oaNotifyForm";
	}
	
	@RequiresPermissions(value={"oa:oaNotify:view","oa:oaNotify:add","oa:oaNotify:edit"},logical=Logical.OR)
	@RequestMapping(value = "flowreform")
	public String flowreform(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		OaNotify oaNotifynew = new OaNotify();
		if (StringUtils.isNotBlank(request.getParameter("id"))){
			Flowapply flowapply = flowapplyService.get(request.getParameter("id"));
			oaNotifynew.setContent(flowapply.getTemplateviewhtml());
			for (Templatecontent templatecontent : flowapply.getTemplatecontentList()) {
				if(FormatUtil.toInteger(templatecontent.getColumntype())==4&&FormatUtil.isNoEmpty(templatecontent.getColumnvalue())){//上传空间
					String cloumnvalue=templatecontent.getColumnvalue();
					String[] fileurls=cloumnvalue.split("\\|");
					List<Tmplatefile> lstTmplatefile=new ArrayList<Tmplatefile>();
					for (String string : fileurls) {
						if(FormatUtil.isNoEmpty(string)){
							Tmplatefile tf=new Tmplatefile();
							tf.setUrl(string);
							String filename=FormatUtil.getfilename(string);
							try {
								filename=java.net.URLDecoder.decode(filename,"UTF-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							tf.setFilename(FormatUtil.getfilename(filename));
							lstTmplatefile.add(tf);
						}
					}
					templatecontent.setTmplatefile(lstTmplatefile);
				}
			}
			model.addAttribute("flowapply", flowapply);
			Flowtemplate template = flowtemplateService.get(flowapply.getTemplateid());
			model.addAttribute("flowtemplate", template);
		}
		model.addAttribute("oaNotify", oaNotifynew);
		List<Oagroup> groups = oagroupService.findList(new Oagroup());
		model.addAttribute("groups", groups);
		return "modules/oa/oaNotifyFlowForm";
	}

	@RequiresPermissions(value={"oa:oaNotify:add","oa:oaNotify:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OaNotify oaNotify, Model model, RedirectAttributes redirectAttributes,final HttpServletRequest request, final HttpServletResponse response) throws HttpException, IOException {
		if (!beanValidator(model, oaNotify)){
			return form(oaNotify,request,response, model);
		}
		addMessage(redirectAttributes, "发布传阅'" + oaNotify.getTitle() + "'成功");
		if("0".equals(oaNotify.getStatus())){
			addMessage(redirectAttributes, "保存传阅'" + oaNotify.getTitle() + "'成功");
		}
		// 如果是修改，则状态为已发布，则不能再进行操作
		if (StringUtils.isNotBlank(oaNotify.getId())){
			OaNotify e = oaNotifyService.get(oaNotify.getId());
			if ("1".equals(e.getStatus())){
				//addMessage(redirectAttributes, "已发布，不能操作！");
				return "redirect:" + adminPath + "/oa/oaNotify/?repage";
			}
		}
		oaNotifyService.save(oaNotify);

		if("1".equals(oaNotify.getStatus())){
			 final String oaNotifyTitle = StringEscapeUtils.unescapeHtml4(oaNotify.getTitle());
			 final String oaNotifyId = oaNotify.getId();
			 final String ctx = request.getContextPath();
			 final User loginUser = UserUtils.getUser();
			 
			 for (final OaNotifyRecord record : oaNotify.getOaNotifyRecordList()) {
					final User user = UserUtils.getWithJpushId(record.getUser().getId());
					if(FormatUtil.isNoEmpty(user.getRegisterid())){
                        System.out.println("JPUSH推送:"+user.getName()+"**************************JPUSH推送ID:"+user.getRegisterid());
						Map extraMap = new HashMap();
						extraMap.put("id",oaNotifyId);
						extraMap.put("notifytype","0");
                        JPushUtil.SendMsg("你收到了一封来自"+loginUser.getName()+"的新的传阅,请注意查收！",user.getRegisterid(),oaNotifyTitle,extraMap);
                    }
					final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(user.getLoginName());
					if(toUserConn != null){
						Thread t = new Thread(new Runnable(){;
				            public void run(){  
				            	
				            	try {
					            	Map map = new HashMap();
					        		OaNotify oaNotify2 = new OaNotify();
					        		oaNotify2.setSelf(true);
					        		oaNotify2.setReadFlag("0");
					        		oaNotify2.setCurrentUser(user);
					        		List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
					        		map.put("cnt1", list1.size());
					        		map.put("cnt2", "-1");
					        		map.put("cnt3", "-1");
					        		
									List<Map> lst = new ArrayList<Map>();
					    			Map maptemp = new HashMap();
					    			maptemp.put("url", ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId);
					    			maptemp.put("title", "你收到了新的传阅【"+oaNotifyTitle+"】,请注意查收！");
//									maptemp.put("title", "你收到了一封新的传阅,请注意查收！");
					    			lst.add(maptemp);
					    			map.put("msg", JSONArray.fromObject(lst).toString());
					        		String msg = JSONObject.fromObject(map).toString();
					        		
									String message = Constant._remind_window_+msg;
									ChatServerPool.sendMessageToUser(toUserConn,message);
				            	} catch (Exception e) {
					                   e.printStackTrace();
					            }
				            	
				            }
						});
						t.start();
					}
			  }
			 
			 final String mobileRemind = FormatUtil.toString(oaNotify.getMobileremind());
			    if("1".equals(mobileRemind)){
			    	String userids = oaNotify.getOaNotifyRecordIds();
				    List<String> mobileList = userDao.findMobileByIds(userids.split(","));
				    final String mobiles = StringUtils.join(mobileList.toArray(),",");
				    if(FormatUtil.isNoEmpty(mobiles)){
				    	Thread tMobile = new Thread(new Runnable(){  
				            public void run(){  
				            	try {
									SmsUtil.groupsendSms(mobiles, Global.OANOTIFY_SMS_MSG);
								} catch (HttpException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				            }
					    });
					    tMobile.start();
				    }
			    	
				}
		}
		
		
//		if("1".equals(oaNotify.getStatus())){
//			//推送给前端页面 Start
//		    final DwrUtil dwrUtil = new DwrUtil();
//		    final String oaNotifyTitle = oaNotify.getTitle();
//		    final String oaNotifyId = oaNotify.getId();
//		    final String ctx = request.getContextPath();
//		    for (final OaNotifyRecord record : oaNotify.getOaNotifyRecordList()) {
//		    	if(dwrUtil.isOnline(record.getUser().getId())){
//		    		Thread t = new Thread(new Runnable(){  
//			            public void run(){  
//			            	try {
//			            		User user = UserUtils.get(record.getUser().getId());
//		            			Map map = new HashMap();
//			            		OaNotify oaNotify2 = new OaNotify();
//			            		oaNotify2.setSelf(true);
//			            		oaNotify2.setReadFlag("0");
//			            		oaNotify2.setCurrentUser(user);
//			            		List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
//			            		map.put("cnt1", list1.size());
//			            		map.put("cnt2", "-1");
//			            		map.put("cnt3", "-1");
////				            		String userId = user.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
////				            		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
////				            				.includeProcessVariables().orderByTaskCreateTime().desc();
////				            		map.put("cnt2", todoTaskQuery.list().size());
////				            		
////				            		MailBox mailBox = new MailBox();
////				            		mailBox.setReceiver(user);
////				            		mailBox.setReadstatus("0");//筛选未读
////				            		List<MailBox> list3 = mailBoxService.findList(mailBox);
////				            		map.put("cnt3", list3.size());
//			            		List<Map> lst = new ArrayList<Map>();
//			        			Map maptemp = new HashMap();
//			        			maptemp.put("url", ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId);
//			        			maptemp.put("title", "你收到了新的传阅【"+oaNotifyTitle+"】,请注意查收！");
//			        			lst.add(maptemp);
//			            		map.put("msg", JSONArray.fromObject(lst).toString());
//			            		
//			            		Thread.sleep(Global.THREAD_SLEEP_TIME);
//				    			dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), record.getUser().getId(),JSONObject.fromObject(map).toString());
//				             } catch (Exception e) {
//				                   e.printStackTrace();
//				             }
//			            }});  
//			            t.start();
//		    	}
//		    }
//		    //推送给前端页面 end
//		    
//		    final String mobileRemind = FormatUtil.toString(oaNotify.getMobileremind());
//		    if("1".equals(mobileRemind)){
//		    	String userids = oaNotify.getOaNotifyRecordIds();
//			    List<String> mobileList = userDao.findMobileByIds(userids.split(","));
//			    final String mobiles = StringUtils.join(mobileList.toArray(),",");
//			    if(FormatUtil.isNoEmpty(mobiles)){
//			    	Thread tMobile = new Thread(new Runnable(){  
//			            public void run(){  
//			            	try {
//								SmsUtil.groupsendSms(mobiles, Global.OANOTIFY_SMS_MSG);
//							} catch (HttpException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//			            }
//				    });
//				    tMobile.start();
//			    }
//		    	
//			}
//		}
	    
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}

	@RequestMapping(value = "addOaNotifyFile", method=RequestMethod.POST)
	public void addOaNotifyFile(OaNotify oaNotify, Model model, RedirectAttributes redirectAttributes,final HttpServletRequest request, final HttpServletResponse response) throws HttpException, IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();

        if (StringUtils.isNotBlank(oaNotify.getId())){
            oaNotify = oaNotifyService.getRecordList(oaNotify);
        }

		String fileurl = request.getParameter("url");
		String filename = request.getParameter("filename");

		if(FormatUtil.isNoEmpty(filename)){//单文件
			OaNotifyFile file = new OaNotifyFile();
			file.setFilename(filename);
			file.setFileurl(fileurl);
			file.setUser(UserUtils.getUser());
			file.setOaNotify(oaNotify);
			file.setUploadDate(new Date());

			try {
				file.preInsert();
				oaNotifyFileDao.insert(file);
				map.put("status", "y");
				map.put("info", "附件添加成功！");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("status", "n");
				map.put("info", "附件添加失败！");
			}
		}else{//多文件
			if(FormatUtil.isNoEmpty(fileurl)){//多文件上传文件列表
				JSONArray ary = JSONArray.fromObject(fileurl);
				if(ary != null && ary.size() > 0){
					List<Map> fileList = (List<Map>)JSONArray.toCollection(ary, Map.class);
					for(Map fileMap : fileList){
						OaNotifyFile file = new OaNotifyFile();
						file.setFilename(FormatUtil.toString(fileMap.get("fileName")));
						file.setFileurl(FormatUtil.toString(fileMap.get("fileUrl")));
						file.setUser(UserUtils.getUser());
						file.setOaNotify(oaNotify);
						file.setUploadDate(new Date());

						try {
							file.preInsert();
							oaNotifyFileDao.insert(file);
							map.put("status", "y");
							map.put("info", "附件添加成功！");
						} catch (Exception e) {
							e.printStackTrace();
							map.put("status", "n");
							map.put("info", "附件添加失败！");
						}
					}
				}
			}
		}

		if("1".equals(oaNotify.getStatus())){
			final String oaNotifyTitle = StringEscapeUtils.unescapeHtml4(oaNotify.getTitle());
			final String oaNotifyId = oaNotify.getId();
			final String ctx = request.getContextPath();

			for (final OaNotifyRecord record : oaNotify.getOaNotifyRecordList()) {
				final User user = UserUtils.get(record.getUser().getId());
				final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(user.getLoginName());
				if(toUserConn != null){
					Thread t = new Thread(new Runnable(){
						public void run(){

							try {
								Map map = new HashMap();
								OaNotify oaNotify2 = new OaNotify();
								oaNotify2.setSelf(true);
								oaNotify2.setReadFlag("0");
								oaNotify2.setCurrentUser(user);
								List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
								map.put("cnt1", list1.size());
								map.put("cnt2", "-1");
								map.put("cnt3", "-1");

								List<Map> lst = new ArrayList<Map>();
								Map maptemp = new HashMap();
								maptemp.put("url", ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId);
								maptemp.put("title", "你的传阅【"+oaNotifyTitle+"】上传了新的附件,请注意查收！");
								lst.add(maptemp);
								map.put("msg", JSONArray.fromObject(lst).toString());
								String msg = JSONObject.fromObject(map).toString();

								String message = Constant._remind_window_+msg;
								ChatServerPool.sendMessageToUser(toUserConn,message);
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});
					t.start();
				}
			}

//			final String mobileRemind = FormatUtil.toString(oaNotify.getMobileremind());
//			if("1".equals(mobileRemind)){
//				String userids = oaNotify.getOaNotifyRecordIds();
//				List<String> mobileList = userDao.findMobileByIds(userids.split(","));
//				final String mobiles = StringUtils.join(mobileList.toArray(),",");
//				if(FormatUtil.isNoEmpty(mobiles)){
//					Thread tMobile = new Thread(new Runnable(){
//						public void run(){
//							try {
//								SmsUtil.groupsendSms(mobiles, Global.OANOTIFY_SMS_MSG);
//							} catch (HttpException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//					});
//					tMobile.start();
//				}
//
//			}
		}

		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

    @RequestMapping(value = "delOaNotifyFile", method=RequestMethod.POST)
    public void delOaNotifyFile(OaNotify oaNotify, Model model, RedirectAttributes redirectAttributes,final HttpServletRequest request, final HttpServletResponse response) throws HttpException, IOException {
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        PrintWriter out =  response.getWriter();
        Map map = new HashMap();

        String fileid = request.getParameter("fileid");

        try {
            oaNotifyFileDao.delete(new OaNotifyFile(fileid));
            map.put("status", "y");
            map.put("info", "附件删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "n");
            map.put("info", "附件删除失败！");
        }

        out.write(JSONObject.fromObject(map).toString());
        out.close();
    }
	
	@RequiresPermissions("oa:oaNotify:del")
	@RequestMapping(value = "delete")
	public String delete(OaNotify oaNotify, RedirectAttributes redirectAttributes) {
		oaNotifyService.delete(oaNotify);
		addMessage(redirectAttributes, "删除通知成功");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	@RequiresPermissions("oa:oaNotify:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oaNotifyService.delete(oaNotifyService.get(id));
		}
		addMessage(redirectAttributes, "删除通知成功");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	/**
	 * 我的通知列表
	 */
	@RequestMapping(value = "self")
	public String selfList(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		
		oaNotify.setSelf(true);
		oaNotify.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
		//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				oaNotify.setSearchdays(4);
			}
		}
		if(FormatUtil.isNoEmpty(Global.SEARCH_DATE_FROM)){
			oaNotify.setSearchdatefrom(FormatUtil.StringToDate(Global.SEARCH_DATE_FROM,"yyyy-MM-dd"));
		}

		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyList";
	}
	
	@RequestMapping(value = "selfnoread")
	public String selfnoreadList(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaNotify.setSelf(true);
		oaNotify.setReadFlag("0");
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify); 
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyList";
	}

	/**
	 * 我的通知列表-数据
	 */
	@RequiresPermissions("oa:oaNotify:view")
	@RequestMapping(value = "selfData")
	@ResponseBody
	public Page<OaNotify> listData(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaNotify.setSelf(true);
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		return page;
	}
	
	/**
	 * 查看我的通知,重定向在当前页面打开
	 */
	@RequestMapping(value = "view")
	public String view(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			String oanotifyReadFlag = oaNotify.getReadFlag();
			User loginUser = UserUtils.getUser();
			oaNotify.setCurrentUser(loginUser);
			oaNotifyService.updateReadFlag(oaNotify);
			oaNotify = oaNotifyService.get(oaNotify.getId());
			oaNotify.setCreateBy(UserUtils.get(oaNotify.getCreateBy().getId()));
			oaNotify = oaNotifyService.getRecordList(oaNotify);
			model.addAttribute("oaNotify", oaNotify);
			
			Page<OaNotifyRecord> page = oaNotifyService.find(new Page<OaNotifyRecord>(request, response),new OaNotifyRecord(oaNotify));
//					oaNotifyService.find(new Page<OaNotifyRecord>(request, response), oaNotify);
			model.addAttribute("page", page);
			
			for (OaNotifyRecord record : oaNotifyRecordDao.findList(new OaNotifyRecord(oaNotify))) {
				if(record.getUser().getId().equals(record.getCurrentUser().getId())){
					model.addAttribute("myoaNotifyRecord", record);
				}
			}
			
			List<Oagroup> groups = oagroupService.findList(new Oagroup());
			model.addAttribute("groups", groups);
			
			if ("1".equals(oaNotify.getStatus()) && ("0".equals(oanotifyReadFlag) || oanotifyReadFlag == null)) {
		    	//推送给前端页面 Start
//			    final DwrUtil dwrUtil = new DwrUtil();
		    	final String loginuserid = UserUtils.getUser().getId();
		    	final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(UserUtils.getUser().getLoginName());
		    	if(toUserConn != null){
		    		Thread t = new Thread(new Runnable(){  
			            public void run(){  
			            	try {
		            			Map map = new HashMap();
			            		OaNotify oaNotify2 = new OaNotify();
			            		oaNotify2.setSelf(true);
			            		oaNotify2.setReadFlag("0");
			            		oaNotify2.setCurrentUser(UserUtils.get(loginuserid));
			            		List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
			            		map.put("cnt1", list1.size());
			            		map.put("cnt2", "-1");
			            		map.put("cnt3", "-1");
			            		List<Map> lst = new ArrayList<Map>();
			            		map.put("msg", JSONArray.fromObject(lst).toString());
			            		
				    			//dwrUtil.sendNoReadCount(loginuserid, loginuserid,JSONObject.fromObject(map).toString());
			            		String msg = JSONObject.fromObject(map).toString();
			            		String message = Constant._remind_window_+msg;
			            		ChatServerPool.sendMessageToUser(toUserConn,message);
				             } catch (Exception e) {
				                   e.printStackTrace();
				             }
			            }});  
			            t.start();
		    	}
		    }
			
			return "modules/oa/oaNotifyView";
		}
		return "redirect:" + adminPath + "/oa/oaNotify/self?repage";
	}
	
	@RequestMapping(value = "selfview")
	public String selfview(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotifyService.updateReadFlag(oaNotify);
			oaNotify = oaNotifyService.get(oaNotify.getId());
			oaNotify.setCreateBy(UserUtils.get(oaNotify.getCreateBy().getId()));
			oaNotify = oaNotifyService.getRecordList(oaNotify);
			model.addAttribute("oaNotify", oaNotify);
			
			Page<OaNotifyRecord> page = oaNotifyService.find(new Page<OaNotifyRecord>(request, response),new OaNotifyRecord(oaNotify));
//					oaNotifyService.find(new Page<OaNotifyRecord>(request, response), oaNotify);
			model.addAttribute("page", page);
			
			return "modules/oa/oaNotifyView";
		}
		return "redirect:" + adminPath + "/oa/oaNotify/self?repage";
	}
	
	@RequestMapping(value = "upOaNotifyRecordComment", method=RequestMethod.POST)
	public void upOaNotifyRecordComment(OaNotify oaNotify,final HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
    		final String recordid = (String) request.getParameter("recordid");
    		final String message = (String) request.getParameter("message");

			final OaNotifyRecord oaNotifyRecordTemp = oaNotifyRecordDao.get(recordid);
			oaNotifyRecordTemp.setId(recordid);
			if(FormatUtil.isNoEmpty(oaNotifyRecordTemp.getOacomment())){
                String msg = "<font style='color:#969696; line-height:24px;'>";
                msg+=oaNotifyRecordTemp.getOacomment()+"("+FormatUtil.dateToString(oaNotifyRecordTemp.getCommentDate(),"yyyy-MM-dd HH:mm:ss")+")<br/>";
                msg+="</font>";
                msg+=message;
                oaNotifyRecordTemp.setOacomment(msg);
				//app相关
				List<OaRecord> oacommentList=new ArrayList<OaRecord>();
				if(FormatUtil.isNoEmpty(oaNotifyRecordTemp.getOacommentbyapp())){
					JSONObject jsonObject=JSONObject.fromObject(oaNotifyRecordTemp.getOacommentbyapp());
					JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("oacommentbyapp"));
					oacommentList = JSONArray.toList(jsonArray, OaRecord.class);
				}
				OaRecord ord=new OaRecord();
				ord.setCreatedate(FormatUtil.dateToString(oaNotifyRecordTemp.getCommentDate(),"yyyy-MM-dd HH:mm:ss"));
				ord.setContent(message);
				oacommentList.add(ord);
				String oacommentbyapp= null;
				Map mapTemp=new HashMap();
				try {
					mapTemp.put("oacommentbyapp",oacommentList);
					JSONObject jsonObject=JSONObject.fromObject(mapTemp);
					oacommentbyapp = jsonObject.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				oaNotifyRecordTemp.setOacommentbyapp(oacommentbyapp);
			}else{
                oaNotifyRecordTemp.setOacomment(message);
				oaNotifyRecordTemp.setCommentDate(new Date());
				//app相关
				List<OaRecord> oacommentList=new ArrayList<OaRecord>();
				if(FormatUtil.isNoEmpty(oaNotifyRecordTemp.getOacommentbyapp())){
					JSONObject jsonObject=JSONObject.fromObject(oaNotifyRecordTemp.getOacommentbyapp());
					JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("oacommentbyapp"));
					oacommentList = JSONArray.toList(jsonArray, OaRecord.class);
				}
				OaRecord ord=new OaRecord();
				ord.setCreatedate(FormatUtil.dateToString(oaNotifyRecordTemp.getCommentDate(),"yyyy-MM-dd HH:mm:ss"));
				ord.setContent(message);
				oacommentList.add(ord);
				String oacommentbyapp= null;
				Map mapTemp=new HashMap();
				try {
					mapTemp.put("oacommentbyapp",oacommentList);
					JSONObject jsonObject=JSONObject.fromObject(mapTemp);
					oacommentbyapp = jsonObject.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				oaNotifyRecordTemp.setOacommentbyapp(oacommentbyapp);
            }
    		oaNotifyService.updateComment(oaNotifyRecordTemp);

//    		Thread toa = new Thread(new Runnable(){
//	            public void run(){
//	            	Flowagent fa=flowagentService.findUniqueByProperty("agentusername", UserUtils.getUser().getLoginName());
//	        		if(FormatUtil.isNoEmpty(fa)){//设置了代理
//	        			String agentUsername = fa.getAgentedusername();
//	        			User agentUser = UserUtils.getByLoginName(agentUsername);
//	        			Map m = new HashMap();
//	        			m.put("userid", agentUser.getId());
//	        			m.put("oanotifyid", oaNotifyRecordTemp.getOaNotify().getId());
//	        			OaNotifyRecord oaNotifyRecordAgent = oaNotifyRecordDao.findRecordByuid(m);
//	        			if(FormatUtil.isNoEmpty(oaNotifyRecordAgent) && "0".equals(oaNotifyRecordAgent.getReadFlag())){
//	        				oaNotifyRecordAgent.setOacomment(message);
//	        				oaNotifyService.updateComment(oaNotifyRecordAgent);
//	        			}
//	        		}
//	            }
//    		});
//    		toa.start();
    		
			//推送给前端页面 Start
		    final DwrUtil dwrUtil = new DwrUtil();
		    final String ctx = request.getContextPath();
		    final OaNotify theoaNotify = oaNotifyService.get(oaNotifyRecordTemp.getOaNotify().getId());
			User sendUser = UserUtils.getWithJpushId(theoaNotify.getCreateBy().getId());
			if(FormatUtil.isNoEmpty(sendUser.getRegisterid())){
				System.out.println("JPUSH推送:"+sendUser.getName()+"**************************JPUSH推送ID:"+sendUser.getRegisterid());
				Map extraMap = new HashMap();
				extraMap.put("id",theoaNotify.getId());
				extraMap.put("notifytype","0");
				JPushUtil.SendMsg(UserUtils.getUser().getName()+"确认了你的传阅,请注意查看！",sendUser.getRegisterid(),theoaNotify.getTitle(),extraMap);
			}
		    final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(sendUser.getLoginName());
		    if(toUserConn != null && "1".equals(theoaNotify.getRecordremind())){
		    	Thread t = new Thread(new Runnable(){  
		            public void run(){  
		            	try {
		        			//确认时提醒
	        				Map map = new HashMap();
		            		map.put("cnt1", "-1");
		            		map.put("cnt2", "-1");
		            		map.put("cnt3", "-1");
//			            		map.put("msg", "<a target=\"_blank\" href=\""+ctx+adminPath+"/oa/oaNotify/form?id="+oaNotify.getId()+"\" >"+UserUtils.getUser().getName()+"确认了你的传阅【"+oaNotify.getTitle() +"】,请注意查看！</a>");
//			            		MailBox mailBox2 = new MailBox();
//			            		mailBox2.setReceiver(oaNotify.getCreateBy());
//			            		mailBox2.setReadstatus("0");//筛选未读
//			            		List<MailBox> list3 = mailBoxService.findList(mailBox2);
//			            		map.put("cnt3", list3.size());
		            		
		            		List<Map> lst = new ArrayList<Map>();
		        			Map maptemp = new HashMap();
		        			maptemp.put("url", ctx+adminPath+"/oa/oaNotify/form?id="+theoaNotify.getId());
							String oaNotifyTitle = StringEscapeUtils.unescapeHtml4(theoaNotify.getTitle());
		        			maptemp.put("title", UserUtils.getUser().getName()+"确认了你的传阅【"+oaNotifyTitle+"】,请注意查看！");
//							maptemp.put("title", UserUtils.getUser().getName()+"确认了你的传阅,请注意查看！");
		        			lst.add(maptemp);
		            		map.put("msg", JSONArray.fromObject(lst).toString());
		            		
//		            		Thread.sleep(Global.THREAD_SLEEP_TIME);
//			    			dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), theoaNotify.getCreateBy().getId(),JSONObject.fromObject(map).toString());
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
        	
        	map.put("status", "y");
			map.put("info", "确认成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("确认失败：", e);
			map.put("status", "n");
			map.put("info", "确认失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	@RequestMapping(value = "upOaNotifyfiles", method=RequestMethod.POST)
	public void upOaNotifyfiles(OaNotify oaNotify,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
    		String oanotifyid = (String) request.getParameter("oanotifyid");
    		String oafiles = (String) request.getParameter("oafiles");
    		oaNotify = oaNotifyService.get(oanotifyid);
    		oaNotify = oaNotifyService.getRecordList(oaNotify);
    		oaNotify.setOafiles(oafiles);
    		
    		oaNotifyService.updateOaNotify(oaNotify);
    		
    		if("1".equals(oaNotify.getStatus())){
    			//推送给前端页面 Start
//    		    final DwrUtil dwrUtil = new DwrUtil();
    		    final String oaNotifyTitle = oaNotify.getTitle();
    		    final String oaNotifyId = oaNotify.getId();
    		    final String ctx = request.getContextPath();
    		    final String loginuserid = UserUtils.getUser().getId();
    		    for (final OaNotifyRecord record : oaNotify.getOaNotifyRecordList()) {
    		    	final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(UserUtils.get(record.getUser().getId()).getLoginName());
    		    	if(!loginuserid.equals(record.getUser().getId()) && toUserConn != null){
    		    		Thread t = new Thread(new Runnable(){  
    			            public void run(){  
    			            	try {
    			            		User user = UserUtils.get(record.getUser().getId());
    		            			Map map = new HashMap();
    			            		map.put("cnt1", "-1");
    			            		map.put("cnt2", "-1");
    			            		map.put("cnt3", "-1");
    			            		List<Map> lst = new ArrayList<Map>();
    			        			Map maptemp = new HashMap();
    			        			maptemp.put("url", ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId);
    			        			maptemp.put("title", "你的传阅【"+oaNotifyTitle+"】上传了新的附件或者附件已被修改,请注意查收！");
    			        			lst.add(maptemp);
    			            		map.put("msg", JSONArray.fromObject(lst).toString());
    			            		
    			            		String msg = JSONObject.fromObject(map).toString();
    			            		String message = Constant._remind_window_+msg;
    			            		ChatServerPool.sendMessageToUser(toUserConn,message);
//    			            		Thread.sleep(Global.THREAD_SLEEP_TIME);
//    				    			dwrUtil.sendNoReadCount(loginuserid, record.getUser().getId(),JSONObject.fromObject(map).toString());
    				             } catch (Exception e) {
    				                   e.printStackTrace();
    				             }
    			            }});  
    			            t.start();
    		    	}
    		    }
    		    //推送给前端页面 end
    		    
//    		    final String mobileRemind = FormatUtil.toString(oaNotify.getMobileremind());
//    		    if("1".equals(mobileRemind)){
//    		    	String userids = oaNotify.getOaNotifyRecordIds();
//    			    List<String> mobileList = userDao.findMobileByIds(userids.split(","));
//    			    final String mobiles = StringUtils.join(mobileList.toArray(),",");
//    			    if(FormatUtil.isNoEmpty(mobiles)){
//    			    	Thread tMobile = new Thread(new Runnable(){  
//    			            public void run(){  
//    			            	try {
//    								SmsUtil.groupsendSms(mobiles, Global.OANOTIFY_SMS_MSG);
//    							} catch (HttpException e) {
//    								// TODO Auto-generated catch block
//    								e.printStackTrace();
//    							} catch (IOException e) {
//    								// TODO Auto-generated catch block
//    								e.printStackTrace();
//    							}
//    			            }
//    				    });
//    				    tMobile.start();
//    			    }
//    		    	
//    			}
    		}
        	
        	map.put("status", "y");
			map.put("info", "提交成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("提交失败：", e);
			map.put("status", "n");
			map.put("info", "提交失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	@RequestMapping(value = "addOaNotifyRecord", method=RequestMethod.POST)
	public void addOaNotifyRecord(OaNotify oaNotify,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();
    	List<OaNotifyRecord> addoaNotifyRecordList = new ArrayList<OaNotifyRecord>();
    	try {
    		List<String> listids = new ArrayList<String>();
			Boolean flag = true;
    		String hasnames = "";
    		String oanotifyid = (String) request.getParameter("oanotifyid");
    		String ids = (String) request.getParameter("ids");
    		oaNotify = oaNotifyService.get(oanotifyid);
    		oaNotify = oaNotifyService.getRecordList(oaNotify);
    		if(FormatUtil.isNoEmpty(ids)){
    			String[] userids = ids.split(",");
    			for (String id : userids) {
					flag = true;
					for (OaNotifyRecord oaNotifyRecord : oaNotify.getOaNotifyRecordList()) {
						if(id.equals(oaNotifyRecord.getUser().getId())){
							hasnames+=oaNotifyRecord.getUser().getName()+",";
							flag=false;
						}
					}
					if(flag){
						listids.add(id);
					}
				}
    			
//    			if(flag){
//        			map.put("status", "n");
//        			map.put("info", hasnames.substring(0, hasnames.length()-1)+"已添加");
//        			out.write(JSONObject.fromObject(map).toString());
//        			out.close();
//        			return;
//        		}else{
        			User loginUser = UserUtils.getUser();
        			String loginname = loginUser.getName();
        			for (String uid : listids){
        				OaNotifyRecord entity = new OaNotifyRecord();
        				entity.setId(IdGen.uuid());
        				entity.setOaNotify(oaNotify);
        				entity.setUser(new User(uid));
        				entity.setReadFlag("0");
        				if(!loginUser.getId().equals(oaNotify.getCreateBy().getId())){
                            entity.setAddname(loginname);
							entity.setAddoffice(loginUser.getOffice().getName());
                        }
						User usr=UserUtils.get(uid);
						entity.setUserName(usr.getName());
						entity.setOfficeName(usr.getOfficeName());
						entity.setStationName(usr.getStationName());
						entity.setCompanyName(usr.getCompanyName());
        				addoaNotifyRecordList.add(entity);
        			}
        			if(addoaNotifyRecordList.size() > 0){
						oaNotifyRecordDao.insertAll(addoaNotifyRecordList);
					}
//        		}
    		}
    		
    		//推送给前端页面 Start
    	    final DwrUtil dwrUtil = new DwrUtil();
    	    final String oaNotifyTitle = oaNotify.getTitle();
		    final String oaNotifyId = oaNotify.getId();
		    final String ctx = request.getContextPath();
		    final User loginUser = UserUtils.getUser();
    	    for (final OaNotifyRecord record : addoaNotifyRecordList) {
    	        User sendUser = UserUtils.getWithJpushId(record.getUser().getId());
                if(FormatUtil.isNoEmpty(sendUser.getRegisterid())){
                    System.out.println("JPUSH推送:"+sendUser.getName()+"**************************JPUSH推送ID:"+sendUser.getRegisterid());
					Map extraMap = new HashMap();
					extraMap.put("id",oaNotifyId);
					extraMap.put("notifytype","0");
                    JPushUtil.SendMsg("你收到了一封来自"+loginUser.getName()+"的新的传阅,请注意查收！",sendUser.getRegisterid(),oaNotifyTitle,extraMap);
                }
    	    	final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(sendUser.getLoginName());
    	    	if(toUserConn != null){
    	    		Thread t = new Thread(new Runnable(){  
        	            public void run(){  
        	            	try {
        	            		User user = UserUtils.get(record.getUser().getId());
        	            		Map map = new HashMap();
        	            		OaNotify oaNotify2 = new OaNotify();
        	            		oaNotify2.setSelf(true);
        	            		oaNotify2.setReadFlag("0");
        	            		oaNotify2.setCurrentUser(user);
        	            		List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
        	            		map.put("cnt1", list1.size());
//        	            		map.put("msg", "<a target=\"_blank\" href=\""+ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId+"\" >你收到了新的传阅【"+oaNotifyTitle+"】,请注意查收！</a>");
        	            		map.put("cnt2", "-1");
        	            		map.put("cnt3", "-1");
        	            		
        	            		List<Map> lst = new ArrayList<Map>();
    		        			Map maptemp = new HashMap();
    		        			maptemp.put("url", ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId);
    		        			maptemp.put("title", "你收到了新的传阅【"+oaNotifyTitle+"】,请注意查收！");
    		        			lst.add(maptemp);
    		            		map.put("msg", JSONArray.fromObject(lst).toString());
//        	            		String userId = user.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
//        	            		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
//        	            				.includeProcessVariables().orderByTaskCreateTime().desc();
//        	            		map.put("cnt2", todoTaskQuery.list().size());
//        	            		
//        	            		MailBox mailBox = new MailBox();
//        	            		mailBox.setReceiver(user);
//        	            		mailBox.setReadstatus("0");//筛选未读
//        	            		List<MailBox> list3 = mailBoxService.findList(mailBox);
//        	            		map.put("cnt3", list3.size());
//    		            		Thread.sleep(Global.THREAD_SLEEP_TIME);
//        		    			dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), record.getUser().getId(),JSONObject.fromObject(map).toString());
        		    			
        		    			String msg = JSONObject.fromObject(map).toString();
        		    			String message = Constant._remind_window_+msg;
        		    			ChatServerPool.sendMessageToUser(toUserConn,message);
        		             } catch (Exception e) {
        		                   e.printStackTrace();
        		             }
        	            }});  
        	            t.start();
    	    	}
    	    }
    	    //推送给前端页面 end
		    final String mobileRemind = FormatUtil.toString(oaNotify.getMobileremind());
		    if("1".equals(mobileRemind)){
		    	OaNotify tempOaNotify = new OaNotify();
	    	    tempOaNotify.setOaNotifyRecordList(addoaNotifyRecordList);
		    	String userids = tempOaNotify.getOaNotifyRecordIds();
			    List<String> mobileList = userDao.findMobileByIds(userids.split(","));
			    final String mobiles = StringUtils.join(mobileList.toArray(),",");
			    if(FormatUtil.isNoEmpty(mobiles)){
			    	Thread tMobile = new Thread(new Runnable(){  
			            public void run(){  
			            	try {
								SmsUtil.groupsendSms(mobiles, Global.OANOTIFY_SMS_MSG);
							} catch (HttpException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            }
				    });
				    tMobile.start();
			    }
		    	
			}
        	
        	map.put("status", "y");
			map.put("info", "添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("添加失败：", e);
			map.put("status", "n");
			map.put("info", "添加失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	/**
	 * 查看我的通知-数据
	 */
	@RequestMapping(value = "viewData")
	@ResponseBody
	public OaNotify viewData(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotifyService.updateReadFlag(oaNotify);
			return oaNotify;
		}
		return null;
	}
	
	/**
	 * 查看我的通知-发送记录
	 */
	@RequestMapping(value = "viewRecordData")
	@ResponseBody
	public OaNotify viewRecordData(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotify = oaNotifyService.getRecordList(oaNotify);
			return oaNotify;
		}
		return null;
	}
	
	/**
	 * 获取我的通知数目
	 */
	@RequestMapping(value = "self/count")
	@ResponseBody
	public String selfCount(OaNotify oaNotify, Model model) {
		oaNotify.setSelf(true);
		oaNotify.setReadFlag("0");
		return String.valueOf(oaNotifyService.findCount(oaNotify));
	}
	
	@RequestMapping(value = "oaGroupform")
	public String oaGroupform(Oagroup oagroup,HttpServletRequest request, HttpServletResponse response,  Model model) {
		String id = request.getParameter("id");
		if(FormatUtil.isNoEmpty(id)){
			OaNotify oaNotify = oaNotifyService.get(id);
			oaNotify = oaNotifyService.getRecordList(oaNotify);
//			oagroup.setIds(oaNotify.getOaNotifyRecordIds());
//			oagroup.setNames(oaNotify.getOaNotifyRecordNames());
//			model.addAttribute("oagroup", oagroup);
			request.setAttribute("ids", oaNotify.getOaNotifyRecordIds());
			request.setAttribute("names", oaNotify.getOaNotifyRecordNames());
		}else{
//			oagroup.setIds(request.getParameter("ids"));
//			oagroup.setNames(request.getParameter("names"));
			request.setAttribute("ids", request.getParameter("ids"));
			request.setAttribute("names", request.getParameter("names"));
		}
		return "modules/oa/oagroupForm";
	}
	
	@RequestMapping(value = "saveOaGroup", method=RequestMethod.POST)
	public void saveOaGroup(Oagroup oagroup,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();
    	try {
    		String groupname = request.getParameter("groupname");
    		String oagroupId = request.getParameter("oagroupId");
    		String oagroupName = request.getParameter("oagroupName");
    		oagroup = new Oagroup();
    		oagroup.setGroupname(groupname);
    		
    		List<Oagroupdtl> oagroupdtlList = Lists.newArrayList();
    		if(FormatUtil.isNoEmpty(oagroupId)){
    			String ids[] = StringUtils.split(oagroupId, ",");
    			String names[] = StringUtils.split(oagroupName, ",");
    			
    			Oagroupdtl oagroupdtl = null;
    			User groupuser = null;
        		for (int i = 0; i < ids.length; i++) {
        			oagroupdtl = new Oagroupdtl();
        			groupuser = new User();
        			groupuser.setId(ids[i]);
        			groupuser.setName(names[i]);
        			oagroupdtl.setGroupuser(groupuser);
        			oagroupdtlList.add(oagroupdtl);
    			}
        		oagroup.setOagroupdtlList(oagroupdtlList);
    		}
    		
//    		oagroup.setIds(oagroupId);
//    		oagroup.setNames(oagroupName);
    		
    		oagroupService.save(oagroup);
    		
    		map.put("id", oagroup.getId());
        	map.put("status", "y");
			map.put("info", "添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("添加失败：", e);
			map.put("status", "n");
			map.put("info", "添加失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	@RequestMapping(value = "addOaGroupToOanotify", method=RequestMethod.POST)
	public void addOaGroupToOanotify(Oagroup oagroup,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();
    	try {
    		String oanotifyid = request.getParameter("oanotifyid");
    		String ids = request.getParameter("ids");
    		String names = request.getParameter("names");
    		
    		String[] idsArr = ids.split(",");
    		String[] namesArr = names.split(",");
    		
    		List<OaNotifyRecord> addoaNotifyRecordList = new ArrayList<OaNotifyRecord>();
    		
    		OaNotify oaNotify = oaNotifyService.get(oanotifyid);
    		oaNotify.setOaNotifyRecordList(oaNotifyRecordDao.findList(new OaNotifyRecord(oaNotify)));
			String oanotifyIds = oaNotify.getOaNotifyRecordIds();
			String loginname = UserUtils.getUser().getName();
			
    		for(int i = 0 ; i < idsArr.length ; i++){
    			if(!oanotifyIds.contains(idsArr[i])){
    				OaNotifyRecord entity = new OaNotifyRecord();
    				entity.setId(IdGen.uuid());
    				entity.setOaNotify(oaNotify);
    				entity.setUser(new User(idsArr[i]));
    				entity.setReadFlag("0");
    				entity.setAddname(loginname);
					User usr=UserUtils.get(idsArr[i]);
					entity.setUserName(usr.getName());
					entity.setOfficeName(usr.getOfficeName());
					entity.setStationName(usr.getStationName());
					entity.setCompanyName(usr.getCompanyName());
    				addoaNotifyRecordList.add(entity);
    			}
    		}
    		if (addoaNotifyRecordList.size() > 0){
    			oaNotifyRecordDao.insertAll(addoaNotifyRecordList);
    			
				//推送给前端页面 Start
//			    final DwrUtil dwrUtil = new DwrUtil();
			    final String oaNotifyTitle = oaNotify.getTitle();
			    final String oaNotifyId = oaNotify.getId();
			    final String ctx = request.getContextPath();
			    for (final OaNotifyRecord record : addoaNotifyRecordList) {
			    	final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(UserUtils.get(record.getUser().getId()).getLoginName());
			    	if(toUserConn != null){
			    		Thread t = new Thread(new Runnable(){  
				            public void run(){  
				            	try {
				            		User user = UserUtils.get(record.getUser().getId());
				            		Map map = new HashMap();
				            		OaNotify oaNotify2 = new OaNotify();
				            		oaNotify2.setSelf(true);
				            		oaNotify2.setReadFlag("0");
				            		oaNotify2.setCurrentUser(user);
				            		List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
				            		map.put("cnt1", list1.size());
				            		map.put("cnt2", "-1");
				            		map.put("cnt3", "-1");
				            		
				            		List<Map> lst = new ArrayList<Map>();
	    		        			Map maptemp = new HashMap();
	    		        			maptemp.put("url", ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId);
	    		        			maptemp.put("title", "你收到了新的传阅【"+oaNotifyTitle+"】,请注意查收！");
	    		        			lst.add(maptemp);
	    		            		map.put("msg", JSONArray.fromObject(lst).toString());
//	    			            		String userId = user.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
//	    			            		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
//	    			            				.includeProcessVariables().orderByTaskCreateTime().desc();
//	    			            		map.put("cnt2", todoTaskQuery.list().size());
//	    			            		
//	    			            		MailBox mailBox = new MailBox();
//	    			            		mailBox.setReceiver(user);
//	    			            		mailBox.setReadstatus("0");//筛选未读
//	    			            		List<MailBox> list3 = mailBoxService.findList(mailBox);
//	    			            		map.put("cnt3", list3.size());
//	    		            		Thread.sleep(Global.THREAD_SLEEP_TIME);
//					    			dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), record.getUser().getId(),JSONObject.fromObject(map).toString());
	    		            		
	    		            		String msg = JSONObject.fromObject(map).toString();
	    		            		String message = Constant._remind_window_+msg;
	    		            		ChatServerPool.sendMessageToUser(toUserConn,message);
					    			
					             } catch (Exception e) {
					                   e.printStackTrace();
					             }
				            }});  
				        t.start();
			    	}
			    	
			    }
			  //推送给前端页面 end
    			
			    final String mobileRemind = FormatUtil.toString(oaNotify.getMobileremind());
			    if("1".equals(mobileRemind)){
			    	OaNotify tempOaNotify = new OaNotify();
		    	    tempOaNotify.setOaNotifyRecordList(addoaNotifyRecordList);
			    	String userids = tempOaNotify.getOaNotifyRecordIds();
				    List<String> mobileList = userDao.findMobileByIds(userids.split(","));
				    final String mobiles = StringUtils.join(mobileList.toArray(),",");
				    if(FormatUtil.isNoEmpty(mobiles)){
				    	Thread tMobile = new Thread(new Runnable(){  
				            public void run(){  
				            	try {
									SmsUtil.groupsendSms(mobiles, Global.OANOTIFY_SMS_MSG);
								} catch (HttpException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				            }
					    });
					    tMobile.start();
				    }
			    	
				}
			    
    		}

    		map.put("status", "y");
			map.put("info", "添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("添加失败：", e);
			map.put("status", "n");
			map.put("info", "添加失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	@RequestMapping(value = "delOaGroup", method=RequestMethod.POST)
	public void delOaGroup(Oagroup oagroup,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();
    	try {
    		String groupid = request.getParameter("groupid");
    		oagroupService.delete(new Oagroup(groupid));

    		map.put("status", "y");
			map.put("info", "删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("添加失败：", e);
			map.put("status", "n");
			map.put("info", "删除失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	/**
	 * 解决ie浏览器直接打开文件产生乱码问题
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("fileDownload")
	public void fileDownload( HttpServletRequest request,
            HttpServletResponse response)
			throws IOException {
	    //获取项目根目录
	    String ctxPath = request.getSession().getServletContext().getRealPath("");
	    String fileurl=java.net.URLDecoder.decode(request.getParameter("fileUrl"),"UTF-8");
//	    int idx=fileurl.indexOf('/', 2);
//	    if(idx!=-1){
//	    	fileurl=fileurl.substring(idx,fileurl.length());
//	    }
	    
	    //获取下载文件露肩
	    String downLoadPath = ctxPath+"/"+fileurl;  
	  
        String filename = this.getExtensionName(fileurl);  
          
        //设置文件MIME类型  
        //response.setContentType(getServletContext().getMimeType(filename));  
        //设置Content-Disposition
        response.setContentType("application/x-msdownload");
        filename=new String(filename.getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=\""+filename+"\"");
        //downLoadPath="F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp8/wtpwebapps/jy_oa/userfiles/49b58b2b95d740e9adb6eefdbce4448c/files/oa/notify/2017/01/%E5%B9%B4%E4%BC%9A%E5%BA%A7%E4%BD%8D%E5%AE%89%E6%8E%922.xls";
        //读取文件  
        InputStream in = new FileInputStream(downLoadPath);  
        OutputStream out = response.getOutputStream();  
        
        //写文件  
        int b;  
        while((b=in.read())!= -1)  
        {  
            out.write(b);  
        }  
          
        in.close();  
        out.close();  
	}

	@RequestMapping(value = "delOaNotifyRecord", method=RequestMethod.POST)
	public void delOaNotifyRecord(OaNotify oaNotify, Model model, RedirectAttributes redirectAttributes,final HttpServletRequest request, final HttpServletResponse response) throws HttpException, IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();

		String oaNotifyRecordId = request.getParameter("oaNotifyRecordId");

		try {
			oaNotifyRecordDao.delete(new OaNotifyRecord(oaNotifyRecordId));
			map.put("status", "y");
			map.put("info", "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "删除失败！");
		}

		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	/**
	 * 获取文件扩展名
	 * @param filename
	 * @return
	 */
	private static String getExtensionName(String filename) { 
        if ((filename != null) && (filename.length() > 0)) { 
            int dot = filename.lastIndexOf('/'); 
            if ((dot >-1) && (dot < (filename.length() - 1))) { 
                return filename.substring(dot + 1); 
            } 
        } 
        return filename; 
    }
}