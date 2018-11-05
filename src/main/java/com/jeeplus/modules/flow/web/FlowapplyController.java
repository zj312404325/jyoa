/**
 * There are <a href="http://www.jeeplus.org/">jeeplus</a> code generation
 */
package com.jeeplus.modules.flow.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.dwr.DwrUtil;
import com.jeeplus.common.mapper.JsonMapper;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.sms.SmsUtil;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.JsonUtil;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.act.dao.ActDao;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.flow.dao.FlowapplyDao;
import com.jeeplus.modules.flow.entity.Flowagent;
import com.jeeplus.modules.flow.entity.Flowapply;
import com.jeeplus.modules.flow.entity.Flowtemplate;
import com.jeeplus.modules.flow.entity.Templatecontent;
import com.jeeplus.modules.flow.entity.Templatecontrol;
import com.jeeplus.modules.flow.entity.Tmplatefile;
import com.jeeplus.modules.flow.service.FlowagentService;
import com.jeeplus.modules.flow.service.FlowapplyService;
import com.jeeplus.modules.flow.service.FlowtemplateService;
import com.jeeplus.modules.iim.entity.MailBox;
import com.jeeplus.modules.iim.service.MailBoxService;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 请假Controller
 * @author liuj
 * @version 2013-04-05
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/flowapply")
public class FlowapplyController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected FlowapplyService flowapplyService;
	
	@Autowired
	protected FlowapplyDao flowapplyDao;
	
	@Autowired
	protected FlowtemplateService flowtemplateService;

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected TaskService taskService;
	@Autowired
	private FlowagentService flowagentService;
	@Autowired
	private ActDao actDao;

	@Autowired
	private OaNotifyService oaNotifyService;
	
	@Autowired
	private MailBoxService mailBoxService;
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private UserDao userDao;
	
	@ModelAttribute
	public Flowapply get(@RequestParam(required=false) String id){//, 
//			@RequestParam(value="act.procInsId", required=false) String procInsId) {
		Flowapply flowapply = null;
		if (StringUtils.isNotBlank(id)){
			flowapply = flowapplyService.get(id);
//		}else if (StringUtils.isNotBlank(procInsId)){
//			testAudit = testAuditService.getByProcInsId(procInsId);
		}
		if (flowapply == null){
			flowapply = new Flowapply();
		}
		return flowapply;
	}

	@RequestMapping(value = {"form"})
	public String form(Flowapply flowapply, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		String view = "flowapplyForm";
		// 查看审批申请单
		if (StringUtils.isNotBlank(flowapply.getId())){//.getAct().getProcInsId())){

			/*// 环节编号
			String taskDefKey = flowapply.getAct().getTaskDefKey();*/
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
			
			if(FormatUtil.isNoEmpty(request.getParameter("flag"))){
				//我的申请详情页
				String procid=flowapply.getProcessInstanceId();
				Map map=new HashMap<String, Object>();
				map.put("procid", procid);
				Map procmap=actDao.findProcInstMap(map);
				if(FormatUtil.isNoEmpty(procmap.get("END_TIME_"))){
					request.setAttribute("canUndo", "0");//不可撤销
				}else{
					request.setAttribute("canUndo", "1");//可撤销
				}
				view = "flowapplyView";
			}else{
				if(FormatUtil.isNoEmpty(request.getParameter("adjust"))){
					List<Map> lst = new ArrayList<Map>();
			    	Map tempmap = null;
		        	for (Templatecontent con : flowapply.getTemplatecontentList()) {
		        		tempmap = new HashMap();
		        		tempmap.put("controlid", con.getControlid());
		        		tempmap.put("columnname", con.getColumnname());
		        		tempmap.put("columntype", con.getColumntype());
		        		tempmap.put("columnsort", con.getColumnsort());
		        		tempmap.put("columnvalue", con.getColumnvalue());
		        		tempmap.put("tmplatefile", con.getTmplatefile());
		        		tempmap.put("columnlocate", con.getColumnlocate());
		        		lst.add(tempmap);
		    		}
					String a =  JSONArray.fromObject(lst).toString();
					request.setAttribute("flowapply1", a);
					
					//获取模板
					Flowtemplate flowtemplate=flowtemplateService.get(flowapply.getTemplateid());
					request.setAttribute("flowtemplate", flowtemplate);
					request.setAttribute("processkey", request.getParameter("processkey"));
					Map<String, Object> map = new HashMap<String,Object>();
					
					StringBuilder validfield=new StringBuilder();
			    	List<Map> lst1 = new ArrayList<Map>();
			    	Map tempmap1 = null;
		        	for (Templatecontent con : flowapply.getTemplatecontentList()) {
		        		tempmap1 = new HashMap();
		        		tempmap1.put("id", con.getControlid());
		        		tempmap1.put("columnid", con.getControlid());
		        		tempmap1.put("columnname", con.getColumnname());
		        		tempmap1.put("columntype", con.getColumntype());
		        		tempmap1.put("columnsort", con.getColumnsort());
		        		tempmap1.put("columnlocate", con.getColumnlocate());
		        		lst1.add(tempmap1);
		        		validfield.append("'"+con.getControlid()+"':"+"{validators: {notEmpty: {message: '请填写"+con.getColumnname()+"！'}}},");
		    		}
		        	String validfieldstr=validfield.toString();
		        	if(FormatUtil.isNoEmpty(validfieldstr)){
		        		validfieldstr=validfieldstr.substring(0,validfieldstr.length()-1);
		        	}
		        	request.setAttribute("validfield", validfieldstr);
		        	map.put("lst1", lst1);
		        	JSONObject jsonstr =  JSONObject.fromObject(map);
					request.setAttribute("controls", jsonstr.toString());
					
					view = "flowapplyEdit";
				}else{
					// 查看工单
					if(flowapply.getAct().isFinishTask()){
						view = "flowapplyView";
					}else{
						view = "flowapplyAudit";
					}
				}
			}
		}else{
			//用户填写流程申请内容
			String templateid=request.getParameter("templateid");
			//String templateid="e21a235c57ad4750b5ce2216b1bda7c0";
			if(FormatUtil.isNoEmpty(templateid)){
				//获取模板
				Flowtemplate flowtemplate=flowtemplateService.get(templateid);
				//模板不存在
				if(!FormatUtil.isNoEmpty(flowtemplate)){
					addMessage(redirectAttributes, "模板不存在！");
					return "redirect:" + adminPath + "/act/task/process/";
				}
				request.setAttribute("flowtemplate", flowtemplate);
				request.setAttribute("processkey", request.getParameter("processkey"));
				Map<String, Object> map = new HashMap<String,Object>();
				
				StringBuilder validfield=new StringBuilder();
		    	List<Map> lst = new ArrayList<Map>();
		    	Map tempmap = null;
	        	for (Templatecontrol con : flowtemplate.getTemplatecontrolList()) {
	        		tempmap = new HashMap();
	        		tempmap.put("id", con.getId());
	        		tempmap.put("columnid", con.getColumnid());
	        		tempmap.put("columnname", con.getColumnname());
	        		tempmap.put("columntype", con.getColumntype());
	        		tempmap.put("columnsort", con.getColumnsort());
	        		tempmap.put("columnlocate", con.getColumnlocate());
	        		lst.add(tempmap);
	        		validfield.append("'"+con.getColumnid()+"':"+"{validators: {notEmpty: {message: '请填写"+con.getColumnname()+"！'}}},");
	    		}
	        	String validfieldstr=validfield.toString();
	        	if(FormatUtil.isNoEmpty(validfieldstr)){
	        		validfieldstr=validfieldstr.substring(0,validfieldstr.length()-1);
	        	}
	        	request.setAttribute("validfield", validfieldstr);
	        	map.put("lst", lst);
	        	JSONObject jsonstr =  JSONObject.fromObject(map);
				request.setAttribute("controls", jsonstr.toString());
			}else{
				addMessage(redirectAttributes, "缺少模板id！");
				return "redirect:" + adminPath + "/act/task/process/";
			}
		}

		model.addAttribute("flowapply", flowapply);
		return "modules/flow/"+view;
	}

	/**
	 * 启动请假流程
	 * @param flowapply	
	 * @throws IOException 
	 */
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
		Map map = new HashMap();
		PrintWriter out =  response.getWriter();
		try {
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			Map<String, Object> variables = Maps.newHashMap();
			Flowapply flowapply=new Flowapply();
			flowapply.setTemplateid(request.getParameter("flowtemplateid"));
			flowapply.setTemplatehtml(request.getParameter("templatehtmlArea"));
			Flowtemplate ft=flowtemplateService.get(request.getParameter("flowtemplateid"));
			flowapply.setShowcolumn(ft.getShowcolumn());
			String processkey=request.getParameter("processkey");
			
			String detailJsonArray = request.getParameter("detailJsonArray").trim();
			if(FormatUtil.isNoEmpty(detailJsonArray)){
				detailJsonArray=detailJsonArray.replace("\n", "<br/>");
				JSONArray jsonArray = JSONArray.fromObject(detailJsonArray);
				List<Templatecontent> templatecontentList = JSONArray.toList(jsonArray, Templatecontent.class);
				flowapply.setTemplatecontentList(templatecontentList);
				
				String procid=flowapplyService.save(flowapply, variables,processkey);
				
				//是否是并行事件
				Map<String,Object> maptemp=new HashMap<String, Object>();
				maptemp.put("procid", procid);
				List<Map> lstmap=actDao.findCandidate(maptemp);
				if(FormatUtil.isNoEmpty(lstmap)&&lstmap.size()>0){
					for (Map map2 : lstmap) {
						String candidate=getCandidate(map2);
						if(FormatUtil.isNoEmpty(candidate)){
							System.out.println("候选人:"+candidate);
							final List<String> nextalst = checkFlowAgent(candidate);
							//推送给前端页面 Start
						    final DwrUtil dwrUtil = new DwrUtil();
						    final String ctx = request.getContextPath();
						    for (final String nexta : nextalst) {
						    	Thread t = new Thread(new Runnable(){  
						            public void run(){  
						            	try {
						            		User user = UserUtils.getByLoginName(nexta);
						            		if(dwrUtil.isOnline(user.getId())){
						            			Map map = new HashMap();
//							            		OaNotify oaNotify2 = new OaNotify();
//							            		oaNotify2.setSelf(true);
//							            		oaNotify2.setReadFlag("0");
//							            		oaNotify2.setCurrentUser(user);
//							            		List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
//							            		map.put("cnt1", list1.size());
							            		
							            		String userId = user.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
							            		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
							            				.includeProcessVariables().orderByTaskCreateTime().desc();
							            		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
							            				.includeProcessVariables().active().orderByTaskCreateTime().desc();
							            		map.put("cnt2", todoTaskQuery.list().size()+toClaimQuery.list().size());
							            		map.put("cnt1", "-1");
							            		map.put("cnt3", "-1");
//							            		MailBox mailBox = new MailBox();
//							            		mailBox.setReceiver(user);
//							            		mailBox.setReadstatus("0");//筛选未读
//							            		List<MailBox> list3 = mailBoxService.findList(mailBox);
//							            		map.put("cnt3", list3.size());
							            		List<Map> lst = new ArrayList<Map>();
							        			Map maptemp = new HashMap();
							        			maptemp.put("url", "");
							        			maptemp.put("title", "你收到了新的待办任务,请及时处理！");
							        			lst.add(maptemp);
							            		map.put("msg", JSONArray.fromObject(lst).toString());
							            		
							            		Thread.sleep(Global.THREAD_SLEEP_TIME);
								    			dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), user.getId(),JSONObject.fromObject(map).toString());
						            		}
							             } catch (Exception e) {
							                   e.printStackTrace();
							             }
						            }}); 
						        t.start();
						      //推送给前端页面 end
							}
						}
					}
				}
				
				/*if(FormatUtil.isNoEmpty(nextAssignee)){
					final String nexta = checkFlowAgent(nextAssignee);
					final User user = UserUtils.getByLoginName(nexta);
					//推送给前端页面 Start
				    final DwrUtil dwrUtil = new DwrUtil();
				    final String ctx = request.getContextPath();
				    if(dwrUtil.isOnline(user.getId())){
				    	Thread t = new Thread(new Runnable(){  
				            public void run(){  
				            	try {
				            		Map map = new HashMap();
//				            		OaNotify oaNotify2 = new OaNotify();
//				            		oaNotify2.setSelf(true);
//				            		oaNotify2.setReadFlag("0");
//				            		oaNotify2.setCurrentUser(user);
//				            		List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
//				            		map.put("cnt1", list1.size());
				            		
				            		String userId = user.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
				            		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
				            				.includeProcessVariables().orderByTaskCreateTime().desc();
				            		map.put("cnt2", todoTaskQuery.list().size());
				            		map.put("msg", "<a target=\"_blank\" href=\""+ctx+adminPath+"/oa/oaNotify/view\" >你收到了新的待办任务,请及时处理！</a>");
				            		map.put("cnt1", 0);
				            		map.put("cnt3", 0);
//				            		MailBox mailBox = new MailBox();
//				            		mailBox.setReceiver(user);
//				            		mailBox.setReadstatus("0");//筛选未读
//				            		List<MailBox> list3 = mailBoxService.findList(mailBox);
//				            		map.put("cnt3", list3.size());
					    			 dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), user.getId(),JSONObject.fromObject(map).toString());
					             } catch (Exception e) {
					                   e.printStackTrace();
					             }
				            }}); 
				        t.start();
				    }
				  //推送给前端页面 end
				}*/
				//addMessage(redirectAttributes, "请假申请已经提交");
				map.put("status", "y");
				map.put("info", "请假申请已经提交");
			}else{
				map.put("status", "n");
				map.put("info", "您提交内容为空");
			}
			
		} catch (Exception e) {
			logger.error("启动请假流程失败：", e);
			//addMessage(redirectAttributes, "系统内部错误！");
			map.put("status", "n");
			map.put("info", "系统内部错误");
		}
		out.write(JSONObject.fromObject(map).toString());
		out.close();
		//return "redirect:" + adminPath + "/act/task/process/";
	}

	private String getCandidate(Map map) {
		// TODO Auto-generated method stub
		if(FormatUtil.isNoEmpty(map.get("USER_ID_"))){
			return map.get("USER_ID_").toString();
		}
		if(FormatUtil.isNoEmpty(map.get("ASSIGNEE_"))){
			return map.get("ASSIGNEE_").toString();
		}
		return null;
	}

	/**
	 * 工单执行（完成任务）
	 * @param testAudit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveAudit")
	public String saveAudit(Flowapply flowapply,Map<String, Object> vars, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(FormatUtil.isNoEmpty(request.getParameter("detailJsonArray"))){//申请调整
			String id=request.getParameter("id");
			flowapply=flowapplyService.get(id);
			List<Templatecontent> lstcontent=flowapply.getTemplatecontentList();
			flowapply.getAct().setFlag(request.getParameter("flag"));
			flowapply.getAct().setComment(request.getParameter("comment"));
			flowapply.getAct().setTaskId(request.getParameter("taskId"));
			flowapply.getAct().setTaskName(request.getParameter("taskName"));
			flowapply.getAct().setTaskDefKey(request.getParameter("taskDefKey"));
			flowapply.getAct().setProcInsId(request.getParameter("procInsId"));
			flowapply.getAct().setProcDefId(request.getParameter("procDefId"));
			JSONArray jsonArray = JSONArray.fromObject(request.getParameter("detailJsonArray"));
			List<Templatecontent> templatecontentList = JSONArray.toList(jsonArray, Templatecontent.class);
			flowapply.setTemplatecontentList(templatecontentList);
			flowapplyService.save(flowapply, lstcontent);
			Map agented=flowapplyService.auditSave(flowapply);
			if(FormatUtil.isNoEmpty(agented)){
				setFlowAgent(agented,flowapply);
			}
		}else{
			if (StringUtils.isBlank(flowapply.getAct().getComment())){
				addMessage(model, "请填写审核意见。");
				return form(flowapply, model,request,redirectAttributes);
			}
			Map agented=flowapplyService.auditSave(flowapply);
			if(FormatUtil.isNoEmpty(agented)){
				setFlowAgent(agented,flowapply);
			}
		}
		
		//是否是并行事件
		Map<String,Object> maptemp=new HashMap<String, Object>();
		maptemp.put("procid", flowapply.getProcessInstanceId());
		//获取候选人
		List<Map> lstmap=actDao.findCandidate(maptemp);
		if(FormatUtil.isNoEmpty(lstmap)&&lstmap.size()>0){
			final String ctx = request.getContextPath();
			final DwrUtil dwrUtil = new DwrUtil();
			for (Map map2 : lstmap) {
				String candidate=getCandidate(map2);
				System.out.println("候选人:"+candidate);
				if(FormatUtil.isNoEmpty(candidate)){
					//获取需要发送信息的用户（候选人包括其代理人）
					final List<String> nextalst = checkFlowAgent(candidate);
					//推送给前端页面 Start
				    for (final String nexta : nextalst) {
				    	Thread t = new Thread(new Runnable(){  
				            public void run(){  
				            	try {
				            		User user = UserUtils.getByLoginName(nexta);
				            		if(dwrUtil.isOnline(user.getId())){
				            			Map map = new HashMap();
					            		String userId = user.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
					            		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
					            				.includeProcessVariables().orderByTaskCreateTime().desc();
					            		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
					            				.includeProcessVariables().active().orderByTaskCreateTime().desc();
					            		map.put("cnt2", todoTaskQuery.list().size()+toClaimQuery.list().size());
					            		map.put("cnt1", "-1");
					            		map.put("cnt3", "-1");
					            		
					            		List<Map> lst = new ArrayList<Map>();
					        			Map maptemp = new HashMap();
					        			maptemp.put("url", "");
					        			maptemp.put("title", "你收到了新的待办任务,请及时处理！");
					        			lst.add(maptemp);
					            		map.put("msg", JSONArray.fromObject(lst).toString());
					            		
					            		Thread.sleep(Global.THREAD_SLEEP_TIME);
						    			dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), user.getId(),JSONObject.fromObject(map).toString());
				            		}
				            		
					             } catch (Exception e) {
					                   e.printStackTrace();
					             }
				            }}); 
				        t.start();
				      //推送给前端页面 end
					}
				}
			}
		}
		
		//更新当前通知栏代办任务数量
		final String ctx = request.getContextPath();
		final DwrUtil dwrUtil = new DwrUtil();
		Thread t = new Thread(new Runnable(){  
            public void run(){  
            	try {
            		User me = UserUtils.getUser();
            		if(dwrUtil.isOnline(me.getId())){
            			Map map = new HashMap();
                		String userId = me.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
                		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
                				.includeProcessVariables().orderByTaskCreateTime().desc();
                		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
                				.includeProcessVariables().active().orderByTaskCreateTime().desc();
                		map.put("cnt2", todoTaskQuery.list().size()+toClaimQuery.list().size());
                		map.put("cnt1", "-1");
                		map.put("cnt3", "-1");
                		
                		List<Map> lst = new ArrayList<Map>();
                		map.put("msg", JSONArray.fromObject(lst).toString());
                		
                		Thread.sleep(Global.THREAD_SLEEP_TIME);
            			dwrUtil.sendNoReadCount(me.getId(), me.getId(),JSONObject.fromObject(map).toString());
            		}
            		
	             } catch (Exception e) {
	                   e.printStackTrace();
	             }
            }}); 
        t.start();
		
		/*String nextAssignee="";
		try {
			String taskdefkey=actTaskService.getNextTask(flowapply.getAct().getProcInsId());
			Map<String,Object> mapTemp=new HashMap<String, Object>();
			mapTemp.put("procid", flowapply.getAct().getProcInsId());
			mapTemp.put("taskdefkey", taskdefkey);
			Map result=actDao.findNextAssignee(mapTemp);
			nextAssignee=(String) result.get("ASSIGNEE_");
			System.out.println("nextAssignee==========="+nextAssignee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(FormatUtil.isNoEmpty(nextAssignee)){
			final String nexta = checkFlowAgent(nextAssignee);
			
			//推送给前端页面 Start
		    final DwrUtil dwrUtil = new DwrUtil();
		    final User user = UserUtils.getByLoginName(nexta);
		    if(dwrUtil.isOnline(user.getId())){
		    	final String ctx = request.getContextPath();
		    	Thread t = new Thread(new Runnable(){  
		            public void run(){  
		            	try {
		            		Map map = new HashMap();
//		            		OaNotify oaNotify2 = new OaNotify();
//		            		oaNotify2.setSelf(true);
//		            		oaNotify2.setReadFlag("0");
//		            		oaNotify2.setCurrentUser(user);
//		            		List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
//		            		map.put("cnt1", list1.size());
		            		
		            		String userId = user.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
		            		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
		            				.includeProcessVariables().orderByTaskCreateTime().desc();
		            		map.put("cnt2", todoTaskQuery.list().size());
		            		map.put("msg", "<a target=\"_blank\" href=\""+ctx+adminPath+"/oa/oaNotify/view\" >你收到了新的待办任务,请及时处理！</a>");
		            		map.put("cnt1", 0);
		            		map.put("cnt3", 0);
//		            		MailBox mailBox = new MailBox();
//		            		mailBox.setReceiver(user);
//		            		mailBox.setReadstatus("0");//筛选未读
//		            		List<MailBox> list3 = mailBoxService.findList(mailBox);
//		            		map.put("cnt3", list3.size());
			    			 dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), user.getId(),JSONObject.fromObject(map).toString());
			             } catch (Exception e) {
			                   e.printStackTrace();
			             }
		            }}); 
		        t.start();
		    }
	    	
		  //推送给前端页面 end
		}*/
		return "redirect:" + adminPath + "/act/task";
	}

	//更新act_hi_act的end_time_
	private void setFlowAgent(Map agented, Flowapply flowapply) {
		// TODO Auto-generated method stub
		Map maptemp=new HashMap<String, Object>();
		maptemp.put("procid", flowapply.getProcInsId());
		maptemp.put("taskid", agented.get("TASK_ID_"));
		actDao.updateHiAct(maptemp);
	}

	/**
	 * 任务列表
	 * @param flowapply	
	 */
	@RequestMapping(value = {"list/task",""})
	public String taskList(HttpSession session, Model model) {
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
		List<Flowapply> results = flowapplyService.findTodoTasks(userId);
		model.addAttribute("flowapplys", results);
		return "modules/flow/flowapplyTask";
	}

	/**
	 * 读取所有流程
	 * @return
	 */
	@RequestMapping(value = {"list"})
	public String list(Flowapply flowapply, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Flowapply> page = flowapplyService.find(new Page<Flowapply>(request, response), flowapply); 
        model.addAttribute("page", page);
		return "modules/flow/flowapplyList";
	}
	
	/**
	 * 读取详细数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "detail/{id}")
	@ResponseBody
	public String getFlowapply(@PathVariable("id") String id) {
		Flowapply flowapply = flowapplyService.get(id);
		return JsonMapper.getInstance().toJson(flowapply);
	}

	/**
	 * 读取详细数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "detail-with-vars/{id}/{taskId}")
	@ResponseBody
	public String getFlowapplyWithVars(@PathVariable("id") String id, @PathVariable("taskId") String taskId) {
		Flowapply flowapply = flowapplyService.get(id);
		Map<String, Object> variables = taskService.getVariables(taskId);
		flowapply.setVariables(variables);
		return JsonMapper.getInstance().toJson(flowapply);
	}
	
	/**
	 * 获得模板对应的控件列表
	 * @param request
	 * @param response
	 * @throws Exception	
	 */
	@RequestMapping("/getControlListJson.htm")
	public void getControlListJson(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> map = new HashMap<String,Object>();
		String id=request.getParameter("templateid");
		Flowtemplate ft= flowtemplateService.get(id);
		map.put("lst", ft.getTemplatecontrolList());
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter writer =response.getWriter();
		JSONObject jsonstr =  JSONObject.fromObject(map);
		String a =  jsonstr.toString();
		writer.write(a);
		writer.close();
	}

	/**
	 * 设置了代理将下一步设为代理
	 */
	public List<String> checkFlowAgent(String username){
		List<String> dwrUserlist=new ArrayList<String>();
		dwrUserlist.add(username);
		Flowagent fa=getFlowAgent(username);
		if(FormatUtil.isNoEmpty(fa)){
			Map<String,Object> mapAgent=new HashMap<String, Object>();
			mapAgent.put("agented", username);
			mapAgent.put("agent", fa.getAgentusername());
			actDao.saveIdentitylink(mapAgent);
			actDao.updateIdentitylink(mapAgent);
			dwrUserlist.add(fa.getAgentusername());
		}
		
		/*Flowagent fa=getFlowAgent(username);
		if(FormatUtil.isNoEmpty(fa)){
			//查找当前运行的流程
			List<Map> listmap=actDao.findByPropertyMap("assignee_", username);
			String ids="";
			for (Map map : listmap) {
				ids+="'"+map.get("ID_")+"'"+",";
			}
			if(FormatUtil.isNoEmpty(ids)){
				ids=ids.substring(0, ids.length()-1);
				Map map=new HashMap<String, Object>();
				map.put("assignee", username);
				map.put("agent", fa.getAgentusername());
				//修改run
				actDao.updateRunTask(map);
				//修改hi
				Map mapTemp=new HashMap<String, Object>();
				mapTemp.put("ids", ids);
				mapTemp.put("agent", fa.getAgentusername());
				actDao.updateHiTask(mapTemp);
				//修改act
				Map mapTemp1=new HashMap<String, Object>();
				mapTemp1.put("ids", ids);
				mapTemp1.put("agent", fa.getAgentusername());
				actDao.updateActTask(mapTemp1);
				
				return fa.getAgentusername();
			}
		}*/
		return dwrUserlist;
	}
	
	/**
	 * 查找用户是否设置了代理
	 * @param 登录名
	 * @return
	 */
	public Flowagent getFlowAgent(String username){
		//查找下一步用户是否设置代理
		Flowagent fa=flowagentService.findUniqueByProperty("agentedusername", username);
		if(FormatUtil.isNoEmpty(fa)){//设置了代理
			return fa;
		}
		return null;
	}

	@RequestMapping(value = "flowHandle")
	public String flowHandle(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		List<User> nextUserList = new ArrayList<User>();
		
		String procInsId = request.getParameter("procInsId");
		String flowapplyid = request.getParameter("flowapplyid");
		
		Map<String,Object> maptemp=new HashMap<String, Object>();
		maptemp.put("procid", procInsId);
		List<Map> lstmap=actDao.findCandidate(maptemp);
		if(FormatUtil.isNoEmpty(lstmap)&&lstmap.size()>0){
			for (Map map2 : lstmap) {
				if(FormatUtil.isNoEmpty(map2.get("ASSIGNEE_"))){//已签收任务
					String candidate=map2.get("ASSIGNEE_").toString();
					if(FormatUtil.isNoEmpty(candidate)){
						System.out.println("候选人:"+candidate);
						final List<String> nextalst = checkFlowAgent(candidate);
					    for (final String nexta : nextalst) {
					    	User nextUser = userDao.getByLoginName(new User("",nexta));
					    	nextUserList.add(nextUser);
						}
					}
					break;
				}else{//未签收任务
					String candidate=getCandidate(map2);
					if(FormatUtil.isNoEmpty(candidate)){
						System.out.println("候选人:"+candidate);
						final List<String> nextalst = checkFlowAgent(candidate);
					    for (final String nexta : nextalst) {
					    	User nextUser = userDao.getByLoginName(new User("",nexta));
					    	nextUserList.add(nextUser);
						}
					}
				}
			}
		}
		request.setAttribute("nextUserList", nextUserList);
		
		Flowapply flowapply = flowapplyService.get(flowapplyid);
		request.setAttribute("flowapply", flowapply);
		Flowtemplate template = flowtemplateService.get(flowapply.getTemplateid());
		request.setAttribute("flowtemplate", template);
		
		return "modules/flow/flowHandler";
	}
	
	@RequestMapping(value = "goFlowHandle", method=RequestMethod.POST)
	public void goFlowHandle(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
    		String ids = (String) request.getParameter("ids");
    		final String mobiles = (String) request.getParameter("mobiles");
    		Boolean isWinRemind = Boolean.valueOf(request.getParameter("isWinRemind"));
    		Boolean isMobileRemind = Boolean.valueOf(request.getParameter("isMobileRemind"));
    		Boolean isOanotifyRemind = Boolean.valueOf(request.getParameter("isOanotifyRemind"));
    		final String content = (String) request.getParameter("content");
    		
    		if(isWinRemind){
    			final DwrUtil dwrUtil = new DwrUtil();
    		    final String ctx = request.getContextPath();
    		    for (final String id : ids.split(",")) {
    		    	if(dwrUtil.isOnline(id)){
    		    		Thread t = new Thread(new Runnable(){  
    			            public void run(){  
    			            	try {
    		            			Map map = new HashMap();
    			            		map.put("cnt1", "-1");
    			            		map.put("cnt2", "-1");
    			            		map.put("cnt3", "-1");
//    			            		map.put("msg", content);
    			            		
    			            		List<Map> lst = new ArrayList<Map>();
				        			Map maptemp = new HashMap();
				        			maptemp.put("url", "");
				        			maptemp.put("title", content);
				        			lst.add(maptemp);
				            		map.put("msg", JSONArray.fromObject(lst).toString());
				            		
				            		Thread.sleep(Global.THREAD_SLEEP_TIME);
    				    			dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), id,JSONObject.fromObject(map).toString());
    				             } catch (Exception e) {
    				                   e.printStackTrace();
    				             }
    			            }});  
    			            t.start();
    		    	}
    		    }
    		}
    		
    		if(isMobileRemind){
			    if(FormatUtil.isNoEmpty(mobiles)){
			    	Thread tMobile = new Thread(new Runnable(){  
			            public void run(){  
			            	try {
								SmsUtil.groupsendSms(mobiles, Global.SUPERVISE_MSG);
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
    		
    		if(isOanotifyRemind){
    			OaNotify newoaNotify = new OaNotify();
    			newoaNotify.setType("4");
    			newoaNotify.setTitle("督办信息提醒");
    			newoaNotify.setContent(content);
    			newoaNotify.setStatus("1");
    			newoaNotify.setSecretsend("1");
    			
    			List<OaNotifyRecord> oaNotifyRecordList = Lists.newArrayList();
    			for (final String id : ids.split(",")) {
    				OaNotifyRecord oar = new OaNotifyRecord(); 
    				oar.setId(FormatUtil.getUUID());
        			oar.setUser(UserUtils.get(id));
        			oar.setReadFlag("0");
        			oar.setOaNotify(newoaNotify);
        			oaNotifyRecordList.add(oar);
    			}	
    			newoaNotify.setOaNotifyRecordList(oaNotifyRecordList);
    			
    			oaNotifyService.save(newoaNotify);
    			
    			//推送给前端页面 Start
    		    final DwrUtil dwrUtil = new DwrUtil();
    		    final String oaNotifyTitle = newoaNotify.getTitle();
    		    final String oaNotifyId = newoaNotify.getId();
    		    final String ctx = request.getContextPath();
    		    for (final OaNotifyRecord record : newoaNotify.getOaNotifyRecordList()) {
    		    	if(dwrUtil.isOnline(record.getUser().getId())){
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
//    				            		String userId = user.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
//    				            		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
//    				            				.includeProcessVariables().orderByTaskCreateTime().desc();
//    				            		map.put("cnt2", todoTaskQuery.list().size());
//    				            		
//    				            		MailBox mailBox = new MailBox();
//    				            		mailBox.setReceiver(user);
//    				            		mailBox.setReadstatus("0");//筛选未读
//    				            		List<MailBox> list3 = mailBoxService.findList(mailBox);
//    				            		map.put("cnt3", list3.size());
    			            		List<Map> lst = new ArrayList<Map>();
    			        			Map maptemp = new HashMap();
    			        			maptemp.put("url", ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId);
    			        			maptemp.put("title", "你收到了新的传阅【"+oaNotifyTitle+"】,请注意查收！");
    			        			lst.add(maptemp);
    			            		map.put("msg", JSONArray.fromObject(lst).toString());
    			            		
    			            		Thread.sleep(Global.THREAD_SLEEP_TIME);
    				    			dwrUtil.sendNoReadCount(UserUtils.getUser().getId(), record.getUser().getId(),JSONObject.fromObject(map).toString());
    				             } catch (Exception e) {
    				                   e.printStackTrace();
    				             }
    			            }});  
    			            t.start();
    		    	}
    		    }
    		    //推送给前端页面 end
    		}
        	
        	map.put("status", "y");
			map.put("info", "督办成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("督办失败：", e);
			map.put("status", "n");
			map.put("info", "督办失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	@RequestMapping(value = "updateFlowViewHtml", method=RequestMethod.POST)
	public void updateFlowViewHtml(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
    		String flowapplyid = (String) request.getParameter("flowapplyid");
        	String htmlstr = (String) request.getParameter("htmlstr");
    		
        	Flowapply flowapply = flowapplyService.get(flowapplyid);
        	flowapply.setTemplateviewhtml(htmlstr);
        	flowapplyDao.update(flowapply);
        	
        	map.put("status", "y");
			map.put("info", "成功");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("系统内部错误：", e);
			map.put("status", "n");
			map.put("info", "系统内部错误。");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
}
