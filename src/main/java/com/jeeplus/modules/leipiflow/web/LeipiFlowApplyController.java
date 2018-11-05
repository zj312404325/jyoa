/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.web;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.BaseConst;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.JPushUtil;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.websocket.onchat.ChatServerPool;
import com.jeeplus.common.websocket.utils.Constant;
import com.jeeplus.modules.flow.dao.TemplatecontentDao;
import com.jeeplus.modules.flow.entity.*;
import com.jeeplus.modules.flow.service.FlowagentService;
import com.jeeplus.modules.flow.service.FlowapplyService;
import com.jeeplus.modules.flow.service.FlowtemplateService;
import com.jeeplus.modules.leipiflow.entity.*;
import com.jeeplus.modules.leipiflow.service.LeipiFlowProcessService;
import com.jeeplus.modules.leipiflow.service.LeipiFlowService;
import com.jeeplus.modules.leipiflow.service.LeipiRunProcessService;
import com.jeeplus.modules.leipiflow.service.LeipiRunService;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import scala.xml.Null;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;

/**
 * 流程申请模块Controller
 * @author 陈钱江
 * @version 2017-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/leipiflow/leipiFlowApply")
public class LeipiFlowApplyController extends BaseController {

	@Autowired
	private FlowtemplateService flowtemplateService;
	
	@Autowired
	private LeipiFlowService leipiFlowService;
	
	@Autowired
	private LeipiFlowProcessService leipiFlowProcessService;
	
	@Autowired
	private FlowapplyService flowapplyService;
	
	@Autowired
	private LeipiRunProcessService leipiRunProcessService;
	
	@Autowired
	private LeipiRunService leipiRunService;
	
	@Autowired
	private FlowagentService flowagentService;

	@Autowired
	private TemplatecontentDao templatecontentDao;
	
	/**
	 * 发起流程
	 * @param model
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = {"form"})
	public String form(Flowapply flowapply,Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String view = "flowapplyForm";
		//表单id
		String templateid=request.getParameter("formid");
		String flowid=request.getParameter("flowid");
		
		if(!FormatUtil.isNoEmpty(flowid)){
			addMessage(redirectAttributes, "缺少流程id！");
			return "redirect:" + adminPath + "leipiflow/leipiFlow/goLeipiFlow/";
		}
		if(!flowid.equals(Global.SALARY_FLOW_ID) && !flowid.equals(Global.REWARD_FLOW_ID)){
            if(!FormatUtil.isNoEmpty(templateid)){
                addMessage(redirectAttributes, "缺少表单id！");
                return "redirect:" + adminPath + "leipiflow/leipiFlow/goLeipiFlow/";
            }
        }

		
		
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
				tempmap.put("valuerequire", con.getValuerequire());
        		lst.add(tempmap);
        		if(con.getValuerequire()==1){
					validfield.append("'"+con.getColumnid()+"':"+"{validators: {notEmpty: {message: '请填写"+con.getColumnname()+"！'}}},");
				}
    		}
			validfield.append("'title':"+"{validators: {notEmpty: {message: '请填写标题！'}}},");
        	String validfieldstr=validfield.toString();
        	if(FormatUtil.isNoEmpty(validfieldstr)){
        		validfieldstr=validfieldstr.substring(0,validfieldstr.length()-1);
        	}
        	request.setAttribute("validfield", validfieldstr);
        	map.put("lst", lst);
        	JSONObject jsonstr =  JSONObject.fromObject(map);
			request.setAttribute("controls", jsonstr.toString());
		}
//      else{
//			addMessage(redirectAttributes, "缺少模板id！");
//			return "redirect:" + adminPath + "/act/task/process/";
//		}
		LeipiFlow leipiFlow=leipiFlowService.get(flowid);
		model.addAttribute("leipiFlow", leipiFlow);
		model.addAttribute("flowid", flowid);
		model.addAttribute("flowapply", flowapply);

        model.addAttribute("SALARY_FLOW_ID", Global.SALARY_FLOW_ID);
        model.addAttribute("REWARD_FLOW_ID", Global.REWARD_FLOW_ID);
		return "modules/leipiflow/"+view;
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
		Map map = new HashMap();
		PrintWriter out =  response.getWriter();
		try {
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			Flowapply flowapply=new Flowapply();
			User loginUser = UserUtils.getUser();
			flowapply.setCompany(loginUser.getCompany());
			flowapply.setOffice(new Office(loginUser.getOffice().getId()));
			flowapply.setDept(loginUser.getOffice());
			flowapply.setFlowid(request.getParameter("flowid"));
			flowapply.setTemplateid(request.getParameter("flowtemplateid"));
			flowapply.setTemplatehtml(request.getParameter("templatehtmlArea"));
			Flowtemplate ft=flowtemplateService.get(request.getParameter("flowtemplateid"));
			flowapply.setShowcolumn(ft.getShowcolumn());
			String userIds=request.getParameter("userIds");

			LeipiFlow lf = leipiFlowService.get(flowapply.getFlowid());
			flowapply.setFlowtype(lf.getLeipiflowtype());

			flowapply.setVar2(request.getParameter("title"));
			
			String detailJsonArray = request.getParameter("detailJsonArray").trim();
			if(FormatUtil.isNoEmpty(detailJsonArray)){
				detailJsonArray=detailJsonArray.replace("\n", "<br/>");
				JSONArray jsonArray = JSONArray.fromObject(detailJsonArray);
				List<Templatecontent> templatecontentList = JSONArray.toList(jsonArray, Templatecontent.class);
				flowapply.setTemplatecontentList(templatecontentList);

				if(flowapply.getFlowtype() == 1){
					//转正申请
					if(Global.ZHUANZHENG_FLOW_ID.equals(flowapply.getFlowid())){
						for (Templatecontent t:templatecontentList) {
							if(Global.ZHUANZHENG_FLOW_PERSON_ID.equals(t.getControlid())){
								flowapply.setVar1(t.getColumnvalue());
								break;
							}
						}
					}else if(Global.SALARY_FLOW_ID.equals(flowapply.getFlowid())){
						for (Templatecontent t:templatecontentList) {
							if(Global.SALARY_FLOW_PERSON_ID.equals(t.getControlid())){
								flowapply.setVar1(t.getColumnvalue());
								break;
							}
						}
					}else if(Global.REWARD_FLOW_ID.equals(flowapply.getFlowid())){
						for (Templatecontent t:templatecontentList) {
							if(Global.REWARD_FLOW_PERSON_ID.equals(t.getControlid())){
								flowapply.setVar1(t.getColumnvalue());
								break;
							}
						}
					}
				}
				
				List<String> sendUidList = leipiFlowService.save(flowapply,request.getParameter("flowid"),userIds);
				if(sendUidList.size() > 0){
					final int flowtype = FormatUtil.toInt(flowapply.getFlowtype());
					final String flowid = flowapply.getFlowid();
					final String ctx = request.getContextPath();
					for (String uid : sendUidList) {
						final String sendUid = uid;
						final User sendU = UserUtils.getWithJpushId(sendUid);
						if(FormatUtil.isNoEmpty(sendU.getRegisterid()) && flowtype == 0){
							System.out.println("JPUSH推送:"+sendU.getName()+"**************************JPUSH推送ID:"+sendU.getRegisterid());
                            Map extraMap = new HashMap();
                            extraMap.put("id",flowapply.getId());
                            extraMap.put("notifytype","1");
							JPushUtil.SendMsg("你收到了一份新的待办任务,请及时处理！",sendU.getRegisterid(),flowapply.getVar2(),extraMap);
						}
						final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(sendU.getLoginName());
	    		    	if(toUserConn != null){
	    		    		Thread t = new Thread(new Runnable(){  
	    			            public void run(){  
	    			            	try {
	    		            			Map map = new HashMap();
	    		            			LeipiRunProcess leipiRunProcess=new LeipiRunProcess();
	    		                		leipiRunProcess.setUpid(sendUid);
	    		                		leipiRunProcess.setStatus(0);//状态0初始 ,1通过,2打回
										leipiRunProcess.setIsDel(0);

										leipiRunProcess.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
										if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
											if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
												leipiRunProcess.setSearchdays(4);
											}
										}
										if(FormatUtil.isNoEmpty(Global.SEARCH_DATE_FROM)){
											leipiRunProcess.setSearchdatefrom(FormatUtil.StringToDate(Global.SEARCH_DATE_FROM,"yyyy-MM-dd"));
										}
	    		                		map.put("cnt2", leipiRunProcessService.findFirstList(leipiRunProcess).size());
					            		map.put("cnt1", "-1");
					            		map.put("cnt3", "-1");
										if(flowtype == 1){

											//获取EHR申请总数
											LeipiRunProcess leipiRunProcessEhr=new LeipiRunProcess();
											leipiRunProcessEhr.setUpid(UserUtils.getUser().getId());
											leipiRunProcessEhr.setIsDel(0);
											leipiRunProcessEhr.setStatus(0);//状态0初始 ,1通过,2打回
											leipiRunProcessEhr.setFlowtype("1");
											map.put("ehrtodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessEhr).size());
											map.put("ehrtodotasktotalmenu", Global.EHRTODOTASKTOTALMENU);
//											//获取申请待办数量
											LeipiRunProcess leipiRunProcessTotal=new LeipiRunProcess();
											leipiRunProcessTotal.setUpid(UserUtils.getUser().getId());
											leipiRunProcessTotal.setIsDel(0);
											leipiRunProcessTotal.setStatus(0);//状态0初始 ,1通过,2打回
											leipiRunProcessTotal.setFlowtype("1");
											leipiRunProcessTotal.setFlowids("'"+Global.ZHUANZHENG_FLOW_ID+"','"+Global.SALARY_FLOW_ID
													+"','"+Global.REWARD_FLOW_ID+"','"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
											map.put("todotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessTotal).size());
											map.put("todotasktotalmenu", Global.TODOTASKTOTALMENU);
											//转正申请
											if(Global.ZHUANZHENG_FLOW_ID.equals(flowid)){
												//获取转正申请待办数量
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("zhuanzhengtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("zhuanzhengtaskmenu", Global.ZHUANZHENGTODOTASKMENU);
											}else if(Global.SALARY_FLOW_ID.equals(flowid)){//薪资调整申请
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("xinzitaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("xinzitaskmenu", Global.SALARYTODOTASKMENU);
											}else if(Global.REWARD_FLOW_ID.equals(flowid)){//奖惩考核申请
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("jiangchengtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("jiangchengtaskmenu", Global.REWARDTODOTASKMENU);
											}else if(Global.POSTSHENQING_FLOW_ID.equals(flowid)){//岗位变动申请
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("postshenqingtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("postshenqingtaskmenu", Global.POSTSHENQINGTODOTASKMENU);

												//获取岗位变动总数
												LeipiRunProcess leipiRunProcesspost=new LeipiRunProcess();
												leipiRunProcesspost.setUpid(UserUtils.getUser().getId());
												leipiRunProcesspost.setIsDel(0);
												leipiRunProcesspost.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcesspost.setFlowtype("1");
												leipiRunProcesspost.setFlowids("'"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
												map.put("posttodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcesspost).size());
												map.put("posttodotasktotalmenu", Global.POSTTODOTASKMENU);
											}else if(Global.POSTJIAOJIE_FLOW_ID.equals(flowid)){//岗位交接申请
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("postjiaojietaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("postjiaojietaskmenu", Global.POSTJIAOJIETODOTASKMENU);

												//获取岗位变动总数
												LeipiRunProcess leipiRunProcesspost=new LeipiRunProcess();
												leipiRunProcesspost.setUpid(UserUtils.getUser().getId());
												leipiRunProcesspost.setIsDel(0);
												leipiRunProcesspost.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcesspost.setFlowtype("1");
												leipiRunProcesspost.setFlowids("'"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
												map.put("posttodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcesspost).size());
												map.put("posttodotasktotalmenu", Global.POSTTODOTASKMENU);
											}else if(Global.LEAVEJIAOJIE_FLOW_ID.equals(flowid)){//离职交接申请
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("leavejiaojietodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("leavejiaojietodotasktotalmenu", Global.LEAVEJIAOJIETODOTASKMENU);
												map.put("leavetodotasktotalmenu", Global.LEAVETODOTASKMENU);

											}

										}
					            		
	    			            		List<Map> lst = new ArrayList<Map>();
	    			        			Map maptemp = new HashMap();
	    			        			if(flowtype == 1){
//											maptemp.put("url", ctx+adminPath+"/leipiflow/leipiFlowApply/myLeipiTask/?type=0");
											maptemp.put("url", "");
											maptemp.put("title", "你收到了新的HR申请任务,请及时处理！");
										}else{
											maptemp.put("url", ctx+adminPath+"/leipiflow/leipiFlowApply/myLeipiTask/?type=0");
											maptemp.put("title", "你收到了新的待办任务,请及时处理！");
										}
	    			        			lst.add(maptemp);
	    			            		map.put("msg", JSONArray.fromObject(lst).toString());
	    			            		
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
				}
				
				
				map.put("status", "y");
				map.put("info", "申请提交成功");
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

	@RequestMapping(value = "saveSpecial")
	public void saveSpecial(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
		Map map = new HashMap();
		PrintWriter out =  response.getWriter();
		try {
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			Flowapply flowapply=new Flowapply();
			User loginUser = UserUtils.getUser();
			flowapply.setCompany(loginUser.getCompany());
			flowapply.setOffice(new Office(loginUser.getOffice().getId()));
			flowapply.setDept(loginUser.getOffice());
			flowapply.setFlowid(request.getParameter("flowid"));
			flowapply.setTemplateid(request.getParameter("flowtemplateid"));
			flowapply.setTemplatehtml(request.getParameter("templatehtmlArea"));
			flowapply.setShowcolumn(1);
			String userIds=request.getParameter("userIds");

			LeipiFlow lf = leipiFlowService.get(flowapply.getFlowid());
			flowapply.setFlowtype(lf.getLeipiflowtype());

//			flowapply.setVar2(request.getParameter("title"));

			String detailJsonArray = request.getParameter("detailJsonArray").trim();
			if(FormatUtil.isNoEmpty(detailJsonArray)){
				detailJsonArray=detailJsonArray.replace("\n", "<br/>");
				JSONArray jsonArray = JSONArray.fromObject(detailJsonArray);
				List<TemplateDetail> templateDetailList = JSONArray.toList(jsonArray, TemplateDetail.class);
				flowapply.setTemplatedetailList(templateDetailList);

				List<String> sendUidList = leipiFlowService.save(flowapply,request.getParameter("flowid"),userIds);
				if(sendUidList.size() > 0){
					final int flowtype = FormatUtil.toInt(flowapply.getFlowtype());
					final String flowid = flowapply.getFlowid();
					final String ctx = request.getContextPath();
					for (String uid : sendUidList) {
						final String sendUid = uid;
						final User sendU = UserUtils.get(sendUid);
						final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(sendU.getLoginName());
						if(toUserConn != null){
							Thread t = new Thread(new Runnable(){
								public void run(){
									try {
										Map map = new HashMap();
										LeipiRunProcess leipiRunProcess=new LeipiRunProcess();
										leipiRunProcess.setUpid(sendUid);
										leipiRunProcess.setStatus(0);//状态0初始 ,1通过,2打回
										leipiRunProcess.setIsDel(0);

										map.put("cnt2", leipiRunProcessService.findFirstList(leipiRunProcess).size());
										map.put("cnt1", "-1");
										map.put("cnt3", "-1");
										if(flowtype == 1){

											//获取EHR申请总数
											LeipiRunProcess leipiRunProcessEhr=new LeipiRunProcess();
											leipiRunProcessEhr.setUpid(UserUtils.getUser().getId());
											leipiRunProcessEhr.setIsDel(0);
											leipiRunProcessEhr.setStatus(0);//状态0初始 ,1通过,2打回
											leipiRunProcessEhr.setFlowtype("1");
											map.put("ehrtodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessEhr).size());
											map.put("ehrtodotasktotalmenu", Global.EHRTODOTASKTOTALMENU);
//											//获取申请待办数量
											LeipiRunProcess leipiRunProcessTotal=new LeipiRunProcess();
											leipiRunProcessTotal.setUpid(UserUtils.getUser().getId());
											leipiRunProcessTotal.setIsDel(0);
											leipiRunProcessTotal.setStatus(0);//状态0初始 ,1通过,2打回
											leipiRunProcessTotal.setFlowtype("1");
											leipiRunProcessTotal.setFlowids("'"+Global.ZHUANZHENG_FLOW_ID+"','"+Global.SALARY_FLOW_ID
													+"','"+Global.REWARD_FLOW_ID+"','"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
											map.put("todotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessTotal).size());
											map.put("todotasktotalmenu", Global.TODOTASKTOTALMENU);
											//转正申请
											if(Global.ZHUANZHENG_FLOW_ID.equals(flowid)){
												//获取转正申请待办数量
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("zhuanzhengtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("zhuanzhengtaskmenu", Global.ZHUANZHENGTODOTASKMENU);
											}else if(Global.SALARY_FLOW_ID.equals(flowid)){//薪资调整申请
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("xinzitaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("xinzitaskmenu", Global.SALARYTODOTASKMENU);
											}else if(Global.REWARD_FLOW_ID.equals(flowid)){//奖惩考核申请
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("jiangchengtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("jiangchengtaskmenu", Global.REWARDTODOTASKMENU);
											}else if(Global.POSTSHENQING_FLOW_ID.equals(flowid)){//岗位变动申请
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("postshenqingtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("postshenqingtaskmenu", Global.POSTSHENQINGTODOTASKMENU);

												//获取岗位变动总数
												LeipiRunProcess leipiRunProcesspost=new LeipiRunProcess();
												leipiRunProcesspost.setUpid(UserUtils.getUser().getId());
												leipiRunProcesspost.setIsDel(0);
												leipiRunProcesspost.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcesspost.setFlowtype("1");
												leipiRunProcesspost.setFlowids("'"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
												map.put("posttodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcesspost).size());
												map.put("posttodotasktotalmenu", Global.POSTTODOTASKMENU);
											}else if(Global.POSTJIAOJIE_FLOW_ID.equals(flowid)){//岗位交接申请
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("postjiaojietaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("postjiaojietaskmenu", Global.POSTJIAOJIETODOTASKMENU);

												//获取岗位变动总数
												LeipiRunProcess leipiRunProcesspost=new LeipiRunProcess();
												leipiRunProcesspost.setUpid(UserUtils.getUser().getId());
												leipiRunProcesspost.setIsDel(0);
												leipiRunProcesspost.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcesspost.setFlowtype("1");
												leipiRunProcesspost.setFlowids("'"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
												map.put("posttodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcesspost).size());
												map.put("posttodotasktotalmenu", Global.POSTTODOTASKMENU);
											}else if(Global.LEAVEJIAOJIE_FLOW_ID.equals(flowid)){//离职交接申请
												LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
												leipiRunProcess2.setUpid(sendUid);
												leipiRunProcess2.setIsDel(0);
												leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
												leipiRunProcess2.setFlowtype("1");
												leipiRunProcess2.setFlowid(flowid);
												map.put("leavejiaojietodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
												map.put("leavejiaojietodotasktotalmenu", Global.LEAVEJIAOJIETODOTASKMENU);
												map.put("leavetodotasktotalmenu", Global.LEAVETODOTASKMENU);

											}

										}

										List<Map> lst = new ArrayList<Map>();
										Map maptemp = new HashMap();
										if(flowtype == 1){
//											maptemp.put("url", ctx+adminPath+"/leipiflow/leipiFlowApply/myLeipiTask/?type=0");
											maptemp.put("url", "");
											maptemp.put("title", "你收到了新的HR申请任务,请及时处理！");
										}else{
											maptemp.put("url", ctx+adminPath+"/leipiflow/leipiFlowApply/myLeipiTask/?type=0");
											maptemp.put("title", "你收到了新的待办任务,请及时处理！");
										}
										lst.add(maptemp);
										map.put("msg", JSONArray.fromObject(lst).toString());

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
				}


				map.put("status", "y");
				map.put("info", "申请提交成功");
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
	
	@RequestMapping(value = {"myLeipiFlow"})
	public String myLeipiFlow(Flowapply flowapply,Model model,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws ParseException {
		flowapply.setCreateBy(UserUtils.getUser());
		flowapply.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
		//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				flowapply.setSearchdays(4);
			}
		}
		if(FormatUtil.isNoEmpty(Global.SEARCH_DATE_FROM)){
			flowapply.setSearchdatefrom(FormatUtil.StringToDate(Global.SEARCH_DATE_FROM,"yyyy-MM-dd"));
		}
		Page<Flowapply> page = flowapplyService.findPage(new Page<Flowapply>(request, response), flowapply); 
		for (Flowapply apply : page.getList()) {
			
			LeipiRun leipiRun = leipiRunService.get(apply.getProcessInstanceId());//从数据库取出记录的值
			LeipiFlow leipiFlow = leipiFlowService.get(leipiRun.getFlowId());
			
			LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
			String runflowid=leipiRun.getId();
			leipiRunProcess.setRunFlow(runflowid);
			leipiRunProcess.setIsDel(0);
			leipiRunProcess.getPage().setOrderBy("a.dateline desc");
			List<LeipiRunProcess>  leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
			if(FormatUtil.isNoEmpty(leipiRunProcessList)&&leipiRunProcessList.size() > 0){
				LeipiFlowProcess leipiFlowProcess = leipiFlowProcessService.get(leipiRunProcessList.get(0).getRunFlowProcess());
				leipiRunProcess.setLeipiFlowProcess(leipiFlowProcess);
				apply.setLeipiFlowProcess(leipiFlowProcess);
				apply.setLeipiRunProcess(leipiRunProcessList.get(0));
			}
			apply.setLeipiFlow(leipiFlow);
			apply.setLeipiRun(leipiRun);
		}
		model.addAttribute("page", page);
		model.addAttribute("flowapply",flowapply);
		
		return "modules/leipiflow/myLeipiFlow";
	}
	
	@RequestMapping(value = {"myLeipiFlowIndex"})
	public String myLeipiFlowIndex(Flowapply flowapply, HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("flowid", request.getParameter("flowid"));

		if(Global.SALARY_FLOW_ID.equals(request.getParameter("flowid")) || Global.REWARD_FLOW_ID.equals(request.getParameter("flowid"))){
			return "modules/leipiflow/mySpecialLeipiFlowIndex";
		}
		return "modules/leipiflow/myLeipiFlowIndex";
	}
	
	@RequestMapping(value = "myLeipiFlowByIdManager")
	public String myLeipiFlowByIdManager(Flowapply flowapply, Model model, HttpServletRequest request, HttpServletResponse response) {
		int isadmin = FormatUtil.toInt(request.getParameter("isadmin"));
		if(isadmin != 1){
			User loginUser = UserUtils.getUser();
			model.addAttribute("isadmin", "0");
			model.addAttribute("flowid", BaseConst.POST_CHANGE_FLOW_ID);
			
			//if(BaseConst.DEPT_HEADER_ID.equals(loginUser.getUserType()) || BaseConst.DEPT_MANAGER_ID.equals(loginUser.getUserType())){
				return "modules/leipiflow/myLeipiFlowByIdManager";
			//}
		}
		
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowApply/myLeipiFlowById?isadmin="+isadmin+"&flowid="+BaseConst.POST_CHANGE_FLOW_ID;
	}
	
	@RequestMapping(value = {"myLeipiFlowById"})
	public String myLeipiFlowById(Flowapply flowapply,Model model,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		//ehr管理：isadmin：1
		if(FormatUtil.isNoEmpty(request.getParameter("isadmin")) && "1".equals(request.getParameter("isadmin"))){
			model.addAttribute("isadmin", "1");
		}else{
			User loginUser = UserUtils.getUser();
			model.addAttribute("isadmin", "0");
			flowapply.setCreateBy(loginUser);
//			if("true".equals(request.getParameter("isself"))){
//				flowapply.setCreateBy(loginUser);
//				model.addAttribute("isself", true);
//			}else if("false".equals(request.getParameter("isself"))){
//				if(BaseConst.DEPT_HEADER_ID.equals(loginUser.getUserType())){
//					flowapply.setDept(new Office(loginUser.getOfficeTrueId()));
//				}else if(BaseConst.DEPT_MANAGER_ID.equals(loginUser.getUserType())){
//					flowapply.setOffice(loginUser.getOffice());
//				}
//			}
		}
		//流程申请id
		if(FormatUtil.isNoEmpty(request.getParameter("flowid"))){
			flowapply.setFlowid(request.getParameter("flowid"));
			model.addAttribute("flowid", request.getParameter("flowid"));
			LeipiFlow myLeipiFlow = leipiFlowService.get(flowapply.getFlowid());
			model.addAttribute("myLeipiFlow", myLeipiFlow);

			flowapply.setFlowtype(myLeipiFlow.getLeipiflowtype());
		}
		
		Page<Flowapply> page = flowapplyService.findPage(new Page<Flowapply>(request, response), flowapply); 
		for (Flowapply apply : page.getList()) {
			
			LeipiRun leipiRun = leipiRunService.get(apply.getProcessInstanceId());//从数据库取出记录的值
			LeipiFlow  leipiFlow =leipiFlowService.get(leipiRun.getFlowId());
			
			LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
			String runflowid=leipiRun.getId();
			leipiRunProcess.setRunFlow(runflowid);
			leipiRunProcess.setIsDel(0);
			leipiRunProcess.getPage().setOrderBy("a.dateline desc");
			List<LeipiRunProcess>  leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
			if(FormatUtil.isNoEmpty(leipiRunProcessList)&&leipiRunProcessList.size()>0){
				LeipiFlowProcess leipiFlowProcess = leipiFlowProcessService.get(leipiRunProcessList.get(0).getRunFlowProcess());
				leipiRunProcess.setLeipiFlowProcess(leipiFlowProcess);
				apply.setLeipiFlowProcess(leipiFlowProcess);
				apply.setLeipiRunProcess(leipiRunProcessList.get(0));
			}
			apply.setLeipiFlow(leipiFlow);
			apply.setLeipiRun(leipiRun);

		}
		model.addAttribute("page", page);

		Boolean isVar1Show = false;
		if(FormatUtil.isNoEmpty(request.getParameter("flowid"))){
			isVar1Show = IsVar1Show(request.getParameter("flowid"));
		}
		model.addAttribute("isVar1Show", isVar1Show);

		
		return "modules/leipiflow/myLeipiFlowById";
	}

	public Boolean IsVar1Show(String flowid){
		if(Global.ZHUANZHENG_FLOW_ID.equals(flowid) || Global.SALARY_FLOW_ID.equals(flowid) ||
				Global.REWARD_FLOW_ID.equals(flowid)){
			return true;
		}
		return false;
	}

    //申请列表
	@RequestMapping(value = {"myLeipiFlowByIds"})
	public String myLeipiFlowByIds(Flowapply flowapply,Model model,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		//ehr管理：isadmin：1
		if(FormatUtil.isNoEmpty(request.getParameter("isadmin")) && "1".equals(request.getParameter("isadmin"))){
			model.addAttribute("isadmin", "1");
		}else{
			User loginUser = UserUtils.getUser();
			model.addAttribute("isadmin", "0");
			flowapply.setCreateBy(loginUser);
//			if("true".equals(request.getParameter("isself"))){
//				flowapply.setCreateBy(loginUser);
//				model.addAttribute("isself", true);
//			}else if("false".equals(request.getParameter("isself"))){
//				if(BaseConst.DEPT_HEADER_ID.equals(loginUser.getUserType())){
//					flowapply.setDept(new Office(loginUser.getOfficeTrueId()));
//				}else if(BaseConst.DEPT_MANAGER_ID.equals(loginUser.getUserType())){
//					flowapply.setOffice(loginUser.getOffice());
//				}
//			}
		}
		//流程申请id
		if(FormatUtil.isNoEmpty(request.getParameter("flowid"))){
			flowapply.setFlowid(request.getParameter("flowid"));
			model.addAttribute("flowid", request.getParameter("flowid"));
		}
		flowapply.setFlowtype(1);//申请列表
		Page<Flowapply> page = flowapplyService.findPage(new Page<Flowapply>(request, response), flowapply);
		for (Flowapply apply : page.getList()) {

			LeipiRun leipiRun = leipiRunService.get(apply.getProcessInstanceId());//从数据库取出记录的值
			LeipiFlow  leipiFlow =leipiFlowService.get(leipiRun.getFlowId());

			LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
			String runflowid=leipiRun.getId();
			leipiRunProcess.setRunFlow(runflowid);
			leipiRunProcess.setIsDel(0);
			leipiRunProcess.getPage().setOrderBy("a.dateline desc");
			List<LeipiRunProcess>  leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
			if(FormatUtil.isNoEmpty(leipiRunProcessList)&&leipiRunProcessList.size()>0){
				LeipiFlowProcess leipiFlowProcess = leipiFlowProcessService.get(leipiRunProcessList.get(0).getRunFlowProcess());
				leipiRunProcess.setLeipiFlowProcess(leipiFlowProcess);
				apply.setLeipiFlowProcess(leipiFlowProcess);
				apply.setLeipiRunProcess(leipiRunProcessList.get(0));
			}
			apply.setLeipiFlow(leipiFlow);
			apply.setLeipiRun(leipiRun);


		}
		model.addAttribute("page", page);

		return "modules/leipiflow/myLeipiFlowByIds";
	}
	
	@RequestMapping(value = {"myLeipiView"})
	public String myLeipiView(Flowapply flowapply,Model model,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(flowapply.getId())){
			flowapply = flowapplyService.get(flowapply.getId());

			List<LeipiFlowProcess> newLeipiFlowProcessList = new ArrayList<LeipiFlowProcess>();
			LeipiFlow leipiflow = leipiFlowService.get(flowapply.getFlowid());
			List<LeipiFlowProcess> leipiFlowProcessList = leipiFlowProcessService.findList(leipiflow);
			LeipiFlowProcess processFirst = null;
			for (LeipiFlowProcess leipiFlowProcess2 : leipiFlowProcessList) {
				if("is_one".equals(leipiFlowProcess2.getProcessType())){
					processFirst = leipiFlowProcess2;
					break;
				}
			}
			getNextProcess(leipiFlowProcessList,processFirst,newLeipiFlowProcessList);
			model.addAttribute("leipiFlowProcessNameList", newLeipiFlowProcessList);
			
			for (Templatecontent templatecontent : flowapply.getTemplatecontentList()) {
				if(FormatUtil.toInteger(templatecontent.getColumntype())==4&&FormatUtil.isNoEmpty(templatecontent.getColumnvalue())){//上传空间
					String cloumnvalue=templatecontent.getColumnvalue();
//					String[] fileurls=cloumnvalue.split("\\|");
					String[] fileurls=cloumnvalue.split(",");
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
			
			LeipiRun leipiRun = leipiRunService.get(flowapply.getProcessInstanceId());//从数据库取出记录的值
            String runflowid=leipiRun.getId();

            LeipiRunProcess openProcess = new LeipiRunProcess();
            openProcess.setIsOpen(1);
            openProcess.setRunFlow(runflowid);
            openProcess.setUpid(UserUtils.getUser().getId());
            leipiRunProcessService.updateIsOpen(openProcess);

			LeipiFlow  leipiFlow =leipiFlowService.get(leipiRun.getFlowId());
			flowapply.setLeipiFlow(leipiFlow);
			flowapply.setLeipiRun(leipiRun);
			LeipiRunProcess leipiRunProcess = new LeipiRunProcess();

			leipiRunProcess.setRunFlow(runflowid);
			leipiRunProcess.setIsDel(0);
			List<Map>  leipiRunProcessMap = new ArrayList<Map>();
			Map mapTemp = new HashMap();
			List<LeipiRunProcess>  leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
			List<LeipiRunProcess>  leipiRunProcessListTemp = new ArrayList<LeipiRunProcess>();
            List<Map>  leipiRunProcessMapTemp = new ArrayList<Map>();
			Map mapsTemp = new HashMap();
            String processName="";
			for (LeipiRunProcess leipiRunProcess2 : leipiRunProcessList) {
				String runPid = leipiRunProcess2.getRunFlowProcess();
				LeipiFlowProcess lp = leipiFlowProcessService.get(runPid);
				leipiRunProcess2.setLeipiFlowProcess(lp);
				leipiRunProcess2.setRunUser(UserUtils.get(leipiRunProcess2.getUpid()));
				if(FormatUtil.isNoEmpty(leipiRunProcess2.getAgentedid())){
					//被代理人
					leipiRunProcess2.setAgentUser(UserUtils.get(leipiRunProcess2.getAgentedid()));
				}
				if("".equals(processName)){
					processName = lp.getProcessName();
					mapsTemp = new HashMap();
					mapsTemp.put("processName",processName);
					leipiRunProcessListTemp = new ArrayList<LeipiRunProcess>();
					leipiRunProcessListTemp.add(leipiRunProcess2);
				}else if(!processName.equals(lp.getProcessName())){
					mapsTemp.put("processList",leipiRunProcessListTemp);
					leipiRunProcessMapTemp.add(mapsTemp);
					processName = lp.getProcessName();
					mapsTemp = new HashMap();
					leipiRunProcessListTemp = new ArrayList<LeipiRunProcess>();
					leipiRunProcessListTemp.add(leipiRunProcess2);
					mapsTemp.put("processName",processName);

				}else if(processName.equals(lp.getProcessName())){
					leipiRunProcessListTemp.add(leipiRunProcess2);
				}

			}
			mapsTemp.put("processList",leipiRunProcessListTemp);
			leipiRunProcessMapTemp.add(mapsTemp);
			
			flowapply.setCreateBy(UserUtils.get(flowapply.getCreateBy().getId()));
			model.addAttribute("flowapply", flowapply);
			model.addAttribute("leipiRunProcessList", leipiRunProcessList);
			model.addAttribute("leipiRunProcessMap", leipiRunProcessMapTemp);

            model.addAttribute("SALARY_FLOW_ID", Global.SALARY_FLOW_ID);
            model.addAttribute("REWARD_FLOW_ID", Global.REWARD_FLOW_ID);

		}
//		Page<Flowapply> page = flowapplyService.findPage(new Page<Flowapply>(request, response), flowapply); 
//		for (Flowapply apply : page.getList()) {
//			
//			LeipiRun leipiRun = leipiRunService.get(apply.getProcessInstanceId());//从数据库取出记录的值
//			LeipiFlow  leipiFlow =leipiFlowService.get(leipiRun.getFlowId());
//			
//			LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
//			String runflowid=leipiRun.getId();
//			leipiRunProcess.setRunFlow(runflowid);
//			leipiRunProcess.setStatus(0);
//			List<LeipiRunProcess>  leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
//			LeipiFlowProcess leipiFlowProcess = leipiFlowProcessService.get(leipiRunProcessList.get(0).getRunFlowProcess());
//			leipiRunProcess.setLeipiFlowProcess(leipiFlowProcess);
//			
//			
//			apply.setLeipiFlow(leipiFlow);
//			apply.setLeipiFlowProcess(leipiFlowProcess);
//			apply.setLeipiRun(leipiRun);
//			apply.setLeipiRunProcess(leipiRunProcessList.get(0));
//			
//		}
//		model.addAttribute("page", page);


		
		return "modules/leipiflow/flowapplyView";
	}
	
	@RequestMapping(value = {"myLeipiTask"})
	public String myLeipiTask(Flowapply flowapply,Model model,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws ParseException {
		String type=request.getParameter("type");
		//获取待办步骤
		LeipiRunProcess leipiRunProcess=new LeipiRunProcess();
		leipiRunProcess.setUpid(UserUtils.getUser().getId());
		leipiRunProcess.setIsDel(0);
		if("0".equals(type)){
			leipiRunProcess.setStatus(0);//状态0初始 ,1通过,2打回
			request.setAttribute("taskStatus", "0");//未处理
		}else if("1".equals(type)){//在办任务 已处理未结束的流程
			leipiRunProcess.setStatus(-1);//已处理
			request.setAttribute("taskStatus", "1");//已处理未结束
		}else if("2".equals(type)){//已办任务 已处理已结束的流程
			leipiRunProcess.setStatus(-2);//已处理
			request.setAttribute("taskStatus", "2");//已处理已结束
		}
		//获取待办流程环节
		Page<LeipiRunProcess> pageTemp=new Page<LeipiRunProcess>(request, response);
		pageTemp.setOrderBy("a.dateline desc");
		leipiRunProcess.setKeyword(flowapply.getKeyword());
		leipiRunProcess.setKeyword1(flowapply.getKeyword1());
		leipiRunProcess.setVar2(flowapply.getVar2());
		if(FormatUtil.isNoEmpty(flowapply.getOffice())){
			//获得此部门及其子部门的集合
			leipiRunProcess.setOffice(flowapply.getOffice());
		}
		leipiRunProcess.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				leipiRunProcess.setSearchdays(4);
			}
		}
		if(FormatUtil.isNoEmpty(Global.SEARCH_DATE_FROM)){
			leipiRunProcess.setSearchdatefrom(FormatUtil.StringToDate(Global.SEARCH_DATE_FROM,"yyyy-MM-dd"));
		}
		Page<LeipiRunProcess> page = leipiRunProcessService.findFirstPage(pageTemp, leipiRunProcess);
		List<Flowapply> flowapplyList=new ArrayList<Flowapply>();
		if(FormatUtil.isNoEmpty(page.getList())&&page.getList().size()>0){
			for (LeipiRunProcess leipiRunProcess2 : page.getList()) {
				String runFlow=leipiRunProcess2.getRunFlow();
				String runFlowProcess=leipiRunProcess2.getRunFlowProcess();
				
				LeipiRun leipiRun=leipiRunService.get(runFlow);
				Flowapply applyTemp=new Flowapply();
				if(FormatUtil.isNoEmpty(request.getParameter("flowtype"))){
					applyTemp.setFlowtype(FormatUtil.toInt(request.getParameter("flowtype")));
					model.addAttribute("flowtype", request.getParameter("flowtype"));
				}else{
					applyTemp.setFlowtype(null);
				}

				applyTemp.setProcessInstanceId(runFlow);
				applyTemp.setSearchdays(0);
				List<Flowapply> applyList=flowapplyService.findList(applyTemp);
				if(applyList.size() > 0){
					Flowapply apply=applyList.get(0);

					LeipiFlowProcess leipiFlowProcess = leipiFlowProcessService.get(runFlowProcess);

					LeipiFlow leipiFlow=leipiFlowService.get(leipiFlowProcess.getFlowId());

					apply.setLeipiFlow(leipiFlow);
					apply.setLeipiFlowProcess(leipiFlowProcess);
					apply.setLeipiRun(leipiRun);
					apply.setLeipiRunProcess(leipiRunProcess2);
					apply.setCreateBy(UserUtils.get(apply.getCreateBy().getId()));
					flowapplyList.add(apply);
				}

			}
			Page<Flowapply> page2 = new Page<Flowapply>();
			page2.setCount(page.getCount());
			page2.setFuncName(page.getFuncName());
			page2.setFuncParam(page.getFuncParam());
			page2.setOrderBy(page.getOrderBy());
			page2.setPageNo(page.getPageNo());
			page2.setPageSize(page.getPageSize());
			page2.setList(flowapplyList);
			model.addAttribute("flowapply", flowapply);
			model.addAttribute("page", page2);
		}
		
		return "modules/leipiflow/myLeipiTask";
	}
	
	@RequestMapping(value = {"myLeipiTaskById"})
	public String myLeipiTaskById(Flowapply flowapply,Model model,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		//ehr管理：isadmin：1
		if(FormatUtil.isNoEmpty(request.getParameter("isadmin")) && "1".equals(request.getParameter("isadmin"))){
			model.addAttribute("isadmin", "1");
		}else{
			model.addAttribute("isadmin", "0");
		}
		//流程申请id
		if(FormatUtil.isNoEmpty(request.getParameter("flowid"))){
			model.addAttribute("flowid", request.getParameter("flowid"));
		}
		
		//获取待办步骤
		LeipiRunProcess leipiRunProcess=new LeipiRunProcess();
		leipiRunProcess.setUpid(UserUtils.getUser().getId());
		leipiRunProcess.setIsDel(0);
		leipiRunProcess.setStatus(0);//状态0初始 ,1通过,2打回
		leipiRunProcess.setFlowid(request.getParameter("flowid"));
		if(FormatUtil.isNoEmpty(request.getParameter("flowid"))){
			LeipiFlow fl = leipiFlowService.get(request.getParameter("flowid"));
			leipiRunProcess.setFlowtype(FormatUtil.toString(fl.getLeipiflowtype()));
		}
		request.setAttribute("taskStatus", "0");//未处理
		//获取待办流程环节
		Page<LeipiRunProcess> pageTemp=new Page<LeipiRunProcess>(request, response);
		pageTemp.setOrderBy("a.dateline desc");

		leipiRunProcess.setKeyword1(flowapply.getKeyword1());
		leipiRunProcess.setVar1(flowapply.getVar1());
		if(FormatUtil.isNoEmpty(flowapply.getStartdate())){
			leipiRunProcess.setStartdate(flowapply.getStartdate());
		}
        if(FormatUtil.isNoEmpty(flowapply.getEnddate())){
            leipiRunProcess.setEnddate(flowapply.getEnddate());
        }

		Page<LeipiRunProcess> page = leipiRunProcessService.findFirstPage(pageTemp, leipiRunProcess);
//		Page<LeipiRunProcess> page = leipiRunProcessService.findPage(pageTemp, leipiRunProcess);
		model.addAttribute("page1", page);
		List<Flowapply> flowapplyList=new ArrayList<Flowapply>();
		if(FormatUtil.isNoEmpty(page.getList())&&page.getList().size()>0){
			for (LeipiRunProcess leipiRunProcess2 : page.getList()) {
				String runFlow=leipiRunProcess2.getRunFlow();
				String runFlowProcess=leipiRunProcess2.getRunFlowProcess();

				LeipiFlow lf = leipiFlowService.get(request.getParameter("flowid"));
				LeipiRun leipiRun=leipiRunService.get(runFlow);
				Flowapply applyTemp=new Flowapply();
				applyTemp.setFlowtype(lf.getLeipiflowtype());
				applyTemp.setProcessInstanceId(runFlow);
				List<Flowapply> applyList=flowapplyService.findList(applyTemp);
				if(applyList.size() > 0){
					Flowapply apply=applyList.get(0);

					LeipiFlowProcess leipiFlowProcess = leipiFlowProcessService.get(runFlowProcess);

					LeipiFlow leipiFlow=leipiFlowService.get(leipiFlowProcess.getFlowId());

					apply.setLeipiFlow(leipiFlow);
					apply.setLeipiFlowProcess(leipiFlowProcess);
					apply.setLeipiRun(leipiRun);
					apply.setLeipiRunProcess(leipiRunProcess2);
					apply.setCreateBy(UserUtils.get(apply.getCreateBy().getId()));


					flowapplyList.add(apply);
				}

			}
			Page<Flowapply> page2 = new Page<Flowapply>();
			page2.setList(flowapplyList);
			page2.setCount(page.getCount());
			page2.setFuncName(page.getFuncName());
			page2.setFuncParam(page.getFuncParam());
			page2.setOrderBy(page.getOrderBy());
			page2.setPageNo(page.getPageNo());
			page2.setPageSize(page.getPageSize());
			model.addAttribute("page", page2);
		}

		Boolean isVar1Show = false;
		if(FormatUtil.isNoEmpty(request.getParameter("flowid"))){
			isVar1Show = IsVar1Show(request.getParameter("flowid"));
		}
		model.addAttribute("isVar1Show", isVar1Show);

		return "modules/leipiflow/myLeipiTaskById";
	}

	public static Boolean IsFlowShow(String flowid){
		if(Global.ZHUANZHENG_FLOW_ID.equals(flowid)){
			return true;
		}
		return false;
	}

	public static void getNextProcess(List<LeipiFlowProcess> leipiFlowProcessList,LeipiFlowProcess process,List<LeipiFlowProcess> newLeipiFlowProcessList){

		if(FormatUtil.isNoEmpty(process)){
			newLeipiFlowProcessList.add(process);
			if(FormatUtil.isNoEmpty(process.getProcessTo())){
				for (LeipiFlowProcess leipiFlowProcess2 : leipiFlowProcessList) {
					if(process.getProcessTo().equals(leipiFlowProcess2.getId())){
//						newLeipiFlowProcessList.add(leipiFlowProcess2);
						getNextProcess(leipiFlowProcessList,leipiFlowProcess2,newLeipiFlowProcessList);
						break;
					}
				}
			}
		}
		//return newLeipiFlowProcessList;
	}
	
	@RequestMapping(value = {"myLeipiEdit"})
	public String myLeipiEdit(Flowapply flowapply,Model model,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		//ehr提交过来的
		request.setAttribute("mtd", request.getParameter("mtd"));
		if (StringUtils.isNotBlank(flowapply.getId())){
			flowapply = flowapplyService.get(flowapply.getId());

			List<LeipiFlowProcess> newLeipiFlowProcessList = new ArrayList<LeipiFlowProcess>();
			LeipiFlow leipiflow = leipiFlowService.get(flowapply.getFlowid());
			List<LeipiFlowProcess> leipiFlowProcessList = leipiFlowProcessService.findList(leipiflow);
			LeipiFlowProcess processFirst = null;
			for (LeipiFlowProcess leipiFlowProcess2 : leipiFlowProcessList) {
				if("is_one".equals(leipiFlowProcess2.getProcessType())){
					processFirst = leipiFlowProcess2;
					break;
				}
			}
			getNextProcess(leipiFlowProcessList,processFirst,newLeipiFlowProcessList);
			model.addAttribute("leipiFlowProcessNameList", newLeipiFlowProcessList);
			
			for (Templatecontent templatecontent : flowapply.getTemplatecontentList()) {
				if(FormatUtil.toInteger(templatecontent.getColumntype())==4&&FormatUtil.isNoEmpty(templatecontent.getColumnvalue())){//上传空间
					String cloumnvalue=templatecontent.getColumnvalue();
//					String[] fileurls=cloumnvalue.split("\\|");
					String[] fileurls=cloumnvalue.split(",");
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
			
			LeipiRun leipiRun = leipiRunService.get(flowapply.getProcessInstanceId());//从数据库取出记录的值
            String runflowid=leipiRun.getId();

            LeipiRunProcess openProcess = new LeipiRunProcess();
            openProcess.setIsOpen(1);
            openProcess.setRunFlow(runflowid);
            openProcess.setUpid(UserUtils.getUser().getId());
            leipiRunProcessService.updateIsOpen(openProcess);

			LeipiFlow  leipiFlow =leipiFlowService.get(leipiRun.getFlowId());
			flowapply.setLeipiFlow(leipiFlow);
			flowapply.setLeipiRun(leipiRun);
			
			
			LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
			leipiRunProcess.setRunFlow(runflowid);
			flowapply.setProcessInstanceId(runflowid);
			leipiRunProcess.setIsDel(0);
			List<LeipiRunProcess>  leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
			List<LeipiRunProcess>  leipiRunProcessListTemp = null;
			List<Map>  leipiRunProcessMapTemp = new ArrayList<Map>();
			Map mapsTemp = null;
			String processName="";
			for (LeipiRunProcess leipiRunProcess2 : leipiRunProcessList) {
				String runPid = leipiRunProcess2.getRunFlowProcess();
				LeipiFlowProcess lp = leipiFlowProcessService.get(runPid);
				leipiRunProcess2.setLeipiFlowProcess(lp);
				leipiRunProcess2.setRunUser(UserUtils.get(leipiRunProcess2.getUpid()));
				if(FormatUtil.isNoEmpty(leipiRunProcess2.getAgentedid())){
					//被代理人
					leipiRunProcess2.setAgentUser(UserUtils.get(leipiRunProcess2.getAgentedid()));
				}
				if("".equals(processName)){
					processName = lp.getProcessName();
					mapsTemp = new HashMap();
					mapsTemp.put("processName",processName);
					leipiRunProcessListTemp = new ArrayList<LeipiRunProcess>();
					leipiRunProcessListTemp.add(leipiRunProcess2);
				}else if(!processName.equals(lp.getProcessName())){
					mapsTemp.put("processList",leipiRunProcessListTemp);
					leipiRunProcessMapTemp.add(mapsTemp);
					processName = lp.getProcessName();
					mapsTemp = new HashMap();
					leipiRunProcessListTemp = new ArrayList<LeipiRunProcess>();
					leipiRunProcessListTemp.add(leipiRunProcess2);
					mapsTemp.put("processName",processName);

				}else if(processName.equals(lp.getProcessName())){
					leipiRunProcessListTemp.add(leipiRunProcess2);
				}

			}
			mapsTemp.put("processList",leipiRunProcessListTemp);
			mapsTemp.put("islast","true");
			leipiRunProcessMapTemp.add(mapsTemp);
			
			flowapply.setCreateBy(UserUtils.get(flowapply.getCreateBy().getId()));
			model.addAttribute("flowapply", flowapply);
			model.addAttribute("leipiRunProcessList", leipiRunProcessList);
			model.addAttribute("leipiRunProcessMap", leipiRunProcessMapTemp);

            model.addAttribute("SALARY_FLOW_ID", Global.SALARY_FLOW_ID);
            model.addAttribute("REWARD_FLOW_ID", Global.REWARD_FLOW_ID);
		}
		return "modules/leipiflow/flowapplyEdit";
	}
	
	/**
	 * 任务处理
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveAudit")
	public String saveAudit(Flowapply flowapply,Map<String, Object> vars, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(flowapply.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(flowapply, model,request,redirectAttributes);
		}
		
		String flag=request.getParameter("flag");
		String processInstanceId=request.getParameter("processInstanceId");
		String userIds=request.getParameter("userIds");
		flowapply.setProcessInstanceId(processInstanceId);
		//userList待办任务提示用户列表
		List<String> sendUidList=leipiFlowService.saveAudit(flowapply, flag,userIds);
		//待办任务用户推送
		if(sendUidList.size() > 0){
			final String ctx = request.getContextPath();
			final int flowtype = FormatUtil.toInt(flowapply.getFlowtype());
			final String flowid = flowapply.getFlowid();
			for (String uid : sendUidList) {
				final String sendUid = uid;
				final User sendU = UserUtils.getWithJpushId(sendUid);
				if(FormatUtil.isNoEmpty(sendU.getRegisterid()) && flowtype == 0){
					System.out.println("JPUSH推送:"+sendU.getName()+"**************************JPUSH推送ID:"+sendU.getRegisterid());
                    Map extraMap = new HashMap();
                    extraMap.put("id",flowapply.getId());
                    extraMap.put("notifytype","1");
                    JPushUtil.SendMsg("你收到了一份新的待办任务,请及时处理！",sendU.getRegisterid(),flowapply.getVar2(),extraMap);
				}
				final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(sendU.getLoginName());
		    	if(toUserConn != null){
		    		Thread t = new Thread(new Runnable(){  
			            public void run(){  
			            	try {
		            			Map map = new HashMap();
		            			LeipiRunProcess leipiRunProcess=new LeipiRunProcess();
		                		leipiRunProcess.setUpid(sendUid);
		                		leipiRunProcess.setStatus(0);//状态0初始 ,1通过,2打回
								leipiRunProcess.setIsDel(0);

								leipiRunProcess.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
								if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
									if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
										leipiRunProcess.setSearchdays(4);
									}
								}
								if(FormatUtil.isNoEmpty(Global.SEARCH_DATE_FROM)){
									leipiRunProcess.setSearchdatefrom(FormatUtil.StringToDate(Global.SEARCH_DATE_FROM,"yyyy-MM-dd"));
								}
		                		map.put("cnt2", leipiRunProcessService.findFirstList(leipiRunProcess).size());
			            		map.put("cnt1", "-1");
			            		map.put("cnt3", "-1");

								if(flowtype == 1){
									//获取EHR申请总数
									LeipiRunProcess leipiRunProcessEhr=new LeipiRunProcess();
									leipiRunProcessEhr.setUpid(sendUid);
									leipiRunProcessEhr.setIsDel(0);
									leipiRunProcessEhr.setStatus(0);//状态0初始 ,1通过,2打回
									leipiRunProcessEhr.setFlowtype("1");
									map.put("ehrtodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessEhr).size());
									map.put("ehrtodotasktotalmenu", Global.EHRTODOTASKTOTALMENU);
									//获取申请待办数量
									LeipiRunProcess leipiRunProcessTotal=new LeipiRunProcess();
									leipiRunProcessTotal.setUpid(sendUid);
									leipiRunProcessTotal.setIsDel(0);
									leipiRunProcessTotal.setStatus(0);//状态0初始 ,1通过,2打回
									leipiRunProcessTotal.setFlowtype("1");
									leipiRunProcessTotal.setFlowids("'"+Global.ZHUANZHENG_FLOW_ID+"','"+Global.SALARY_FLOW_ID
											+"','"+Global.REWARD_FLOW_ID+"','"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
									map.put("todotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessTotal).size());
									map.put("todotasktotalmenu", Global.TODOTASKTOTALMENU);
									//转正申请
									if(Global.ZHUANZHENG_FLOW_ID.equals(flowid)){
										//获取转正申请待办数量
										LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
										leipiRunProcess2.setUpid(sendUid);
										leipiRunProcess2.setIsDel(0);
										leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
										leipiRunProcess2.setFlowtype("1");
										leipiRunProcess2.setFlowid(flowid);
										map.put("zhuanzhengtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
										map.put("zhuanzhengtaskmenu", Global.ZHUANZHENGTODOTASKMENU);
									}else if(Global.SALARY_FLOW_ID.equals(flowid)){//薪资调整申请
										//获取薪资调整申请待办数量
										LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
										leipiRunProcess2.setUpid(sendUid);
										leipiRunProcess2.setIsDel(0);
										leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
										leipiRunProcess2.setFlowtype("1");
										leipiRunProcess2.setFlowid(flowid);
										map.put("xinzitaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
										map.put("xinzitaskmenu", Global.SALARYTODOTASKMENU);
									}else if(Global.REWARD_FLOW_ID.equals(flowid)){//奖惩考核申请
										LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
										leipiRunProcess2.setUpid(sendUid);
										leipiRunProcess2.setIsDel(0);
										leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
										leipiRunProcess2.setFlowtype("1");
										leipiRunProcess2.setFlowid(flowid);
										map.put("jiangchengtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
										map.put("jiangchengtaskmenu", Global.REWARDTODOTASKMENU);
									}else if(Global.POSTSHENQING_FLOW_ID.equals(flowid)){//岗位变动申请
										LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
										leipiRunProcess2.setUpid(sendUid);
										leipiRunProcess2.setIsDel(0);
										leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
										leipiRunProcess2.setFlowtype("1");
										leipiRunProcess2.setFlowid(flowid);
										map.put("postshenqingtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
										map.put("postshenqingtaskmenu", Global.POSTSHENQINGTODOTASKMENU);

										//获取岗位变动总数
										LeipiRunProcess leipiRunProcesspost=new LeipiRunProcess();
										leipiRunProcesspost.setUpid(UserUtils.getUser().getId());
										leipiRunProcesspost.setIsDel(0);
										leipiRunProcesspost.setStatus(0);//状态0初始 ,1通过,2打回
										leipiRunProcesspost.setFlowtype("1");
										leipiRunProcesspost.setFlowids("'"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
										map.put("posttodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcesspost).size());
										map.put("posttodotasktotalmenu", Global.POSTTODOTASKMENU);
									}else if(Global.POSTJIAOJIE_FLOW_ID.equals(flowid)){//岗位交接申请
										LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
										leipiRunProcess2.setUpid(sendUid);
										leipiRunProcess2.setIsDel(0);
										leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
										leipiRunProcess2.setFlowtype("1");
										leipiRunProcess2.setFlowid(flowid);
										map.put("postjiaojietaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
										map.put("postjiaojietaskmenu", Global.POSTJIAOJIETODOTASKMENU);

										//获取岗位变动总数
										LeipiRunProcess leipiRunProcesspost=new LeipiRunProcess();
										leipiRunProcesspost.setUpid(UserUtils.getUser().getId());
										leipiRunProcesspost.setIsDel(0);
										leipiRunProcesspost.setStatus(0);//状态0初始 ,1通过,2打回
										leipiRunProcesspost.setFlowtype("1");
										leipiRunProcesspost.setFlowids("'"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
										map.put("posttodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcesspost).size());
										map.put("posttodotasktotalmenu", Global.POSTTODOTASKMENU);
									}else if(Global.LEAVEJIAOJIE_FLOW_ID.equals(flowid)){//离职交接申请
										LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
										leipiRunProcess2.setUpid(sendUid);
										leipiRunProcess2.setIsDel(0);
										leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
										leipiRunProcess2.setFlowtype("1");
										leipiRunProcess2.setFlowid(flowid);
										map.put("leavejiaojietodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
										map.put("leavejiaojietodotasktotalmenu", Global.LEAVEJIAOJIETODOTASKMENU);
										map.put("leavetodotasktotalmenu", Global.LEAVETODOTASKMENU);

									}

								}
			            		
			            		List<Map> lst = new ArrayList<Map>();
			        			Map maptemp = new HashMap();
								if(flowtype == 1){
									//maptemp.put("url", ctx+adminPath+"/leipiflow/leipiFlowApply/myLeipiTask/?type=0");
									maptemp.put("url", "");
									maptemp.put("title", "你收到了新的HR申请任务,请及时处理！");
								}else{
									maptemp.put("url", ctx+adminPath+"/leipiflow/leipiFlowApply/myLeipiTask/?type=0");
									maptemp.put("title", "你收到了新的待办任务,请及时处理！");
								}
			        			lst.add(maptemp);
			            		map.put("msg", JSONArray.fromObject(lst).toString());
			            		
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
		}
		
		//更新本用户待办任务数量
		final String ctx = request.getContextPath();
		final String sendUid = UserUtils.getUser().getId();
		final User sendU = UserUtils.getUser();
		final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(sendU.getLoginName());
    	if(toUserConn != null){
			final int flowtype = FormatUtil.toInt(flowapply.getFlowtype());
			final String flowid = flowapply.getFlowid();
    		Thread t = new Thread(new Runnable(){  
	            public void run(){  
	            	try {
            			Map map = new HashMap();
            			LeipiRunProcess leipiRunProcess=new LeipiRunProcess();
                		leipiRunProcess.setUpid(sendUid);
                		leipiRunProcess.setStatus(0);//状态0初始 ,1通过,2打回
						leipiRunProcess.setIsDel(0);
						leipiRunProcess.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
						if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
							if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
								leipiRunProcess.setSearchdays(4);
							}
						}
						if(FormatUtil.isNoEmpty(Global.SEARCH_DATE_FROM)){
							leipiRunProcess.setSearchdatefrom(FormatUtil.StringToDate(Global.SEARCH_DATE_FROM,"yyyy-MM-dd"));
						}
                		map.put("cnt2", leipiRunProcessService.findFirstList(leipiRunProcess).size());
	            		map.put("cnt1", "-1");
	            		map.put("cnt3", "-1");

						if(flowtype == 1){
							//获取EHR申请总数
							LeipiRunProcess leipiRunProcessEhr=new LeipiRunProcess();
							leipiRunProcessEhr.setUpid(UserUtils.getUser().getId());
							leipiRunProcessEhr.setIsDel(0);
							leipiRunProcessEhr.setStatus(0);//状态0初始 ,1通过,2打回
							leipiRunProcessEhr.setFlowtype("1");
							map.put("ehrtodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessEhr).size());
							map.put("ehrtodotasktotalmenu", Global.EHRTODOTASKTOTALMENU);
							//获取申请待办数量
							LeipiRunProcess leipiRunProcessTotal=new LeipiRunProcess();
							leipiRunProcessTotal.setUpid(UserUtils.getUser().getId());
							leipiRunProcessTotal.setIsDel(0);
							leipiRunProcessTotal.setStatus(0);//状态0初始 ,1通过,2打回
							leipiRunProcessTotal.setFlowtype("1");
							leipiRunProcessTotal.setFlowids("'"+Global.ZHUANZHENG_FLOW_ID+"','"+Global.SALARY_FLOW_ID
									+"','"+Global.REWARD_FLOW_ID+"','"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
							map.put("todotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcessTotal).size());
							map.put("todotasktotalmenu", Global.TODOTASKTOTALMENU);
							//转正申请
							if(Global.ZHUANZHENG_FLOW_ID.equals(flowid)){
								//获取转正申请待办数量
								LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
								leipiRunProcess2.setUpid(sendUid);
								leipiRunProcess2.setIsDel(0);
								leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
								leipiRunProcess2.setFlowtype("1");
								leipiRunProcess2.setFlowid(flowid);
								map.put("zhuanzhengtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
								map.put("zhuanzhengtaskmenu", Global.ZHUANZHENGTODOTASKMENU);
							}else if(Global.SALARY_FLOW_ID.equals(flowid)){//薪资调整申请
								//获取薪资调整申请待办数量
								LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
								leipiRunProcess2.setUpid(sendUid);
								leipiRunProcess2.setIsDel(0);
								leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
								leipiRunProcess2.setFlowtype("1");
								leipiRunProcess2.setFlowid(flowid);
								map.put("xinzitaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
								map.put("xinzitaskmenu", Global.SALARYTODOTASKMENU);
							}else if(Global.REWARD_FLOW_ID.equals(flowid)){//奖惩考核申请
								LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
								leipiRunProcess2.setUpid(sendUid);
								leipiRunProcess2.setIsDel(0);
								leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
								leipiRunProcess2.setFlowtype("1");
								leipiRunProcess2.setFlowid(flowid);
								map.put("jiangchengtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
								map.put("jiangchengtaskmenu", Global.REWARDTODOTASKMENU);
							}else if(Global.POSTSHENQING_FLOW_ID.equals(flowid)){//岗位变动申请
								LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
								leipiRunProcess2.setUpid(sendUid);
								leipiRunProcess2.setIsDel(0);
								leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
								leipiRunProcess2.setFlowtype("1");
								leipiRunProcess2.setFlowid(flowid);
								map.put("postshenqingtaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
								map.put("postshenqingtaskmenu", Global.POSTSHENQINGTODOTASKMENU);

								//获取岗位变动总数
								LeipiRunProcess leipiRunProcesspost=new LeipiRunProcess();
								leipiRunProcesspost.setUpid(UserUtils.getUser().getId());
								leipiRunProcesspost.setIsDel(0);
								leipiRunProcesspost.setStatus(0);//状态0初始 ,1通过,2打回
								leipiRunProcesspost.setFlowtype("1");
								leipiRunProcesspost.setFlowids("'"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
								map.put("posttodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcesspost).size());
								map.put("posttodotasktotalmenu", Global.POSTTODOTASKMENU);
							}else if(Global.POSTJIAOJIE_FLOW_ID.equals(flowid)){//岗位交接申请
								LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
								leipiRunProcess2.setUpid(sendUid);
								leipiRunProcess2.setIsDel(0);
								leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
								leipiRunProcess2.setFlowtype("1");
								leipiRunProcess2.setFlowid(flowid);
								map.put("postjiaojietaskcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
								map.put("postjiaojietaskmenu", Global.POSTJIAOJIETODOTASKMENU);

								//获取岗位变动总数
								LeipiRunProcess leipiRunProcesspost=new LeipiRunProcess();
								leipiRunProcesspost.setUpid(UserUtils.getUser().getId());
								leipiRunProcesspost.setIsDel(0);
								leipiRunProcesspost.setStatus(0);//状态0初始 ,1通过,2打回
								leipiRunProcesspost.setFlowtype("1");
								leipiRunProcesspost.setFlowids("'"+Global.POSTSHENQING_FLOW_ID+"','"+Global.POSTJIAOJIE_FLOW_ID+"'");
								map.put("posttodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcesspost).size());
								map.put("posttodotasktotalmenu", Global.POSTTODOTASKMENU);
							}else if(Global.LEAVEJIAOJIE_FLOW_ID.equals(flowid)){//离职交接申请
								LeipiRunProcess leipiRunProcess2=new LeipiRunProcess();
								leipiRunProcess2.setUpid(sendUid);
								leipiRunProcess2.setIsDel(0);
								leipiRunProcess2.setStatus(0);//状态0初始 ,1通过,2打回
								leipiRunProcess2.setFlowtype("1");
								leipiRunProcess2.setFlowid(flowid);
								map.put("leavejiaojietodotasktotalcount", leipiRunProcessService.findFirstList(leipiRunProcess2).size());
								map.put("leavejiaojietodotasktotalmenu", Global.LEAVEJIAOJIETODOTASKMENU);
								map.put("leavetodotasktotalmenu", Global.LEAVETODOTASKMENU);

							}

						}

	            		map.put("msg", "");
	            		
	            		String msg = JSONObject.fromObject(map).toString();
	            		String message = Constant._remind_window_+msg;
	            		ChatServerPool.sendMessageToUser(toUserConn,message);
		             } catch (Exception e) {
		                   e.printStackTrace();
		             }
	            }});  
	            t.start();
    	}

    	if(Global.SALARY_FLOW_ID.equals(flowapply.getFlowid())||Global.REWARD_FLOW_ID.equals(flowapply.getFlowid())){
			//return "redirect:" + adminPath + "/leipiflow/leipiFlowApply/myLeipiFlowIndex?type=1&flowid="+flowapply.getFlowid();
			return "redirect:" + adminPath + "/leipiflow/templateDetail/list?flowid="+flowapply.getFlowid()+"&mtd=recieve";
		}
		
    	if(!FormatUtil.isNoEmpty(request.getParameter("mtd"))){//ehr提交过来的
    		return "redirect:" + adminPath + "/leipiflow/leipiFlowApply/myLeipiTask/?repage&type=0";
    	}else{
            flowapply = flowapplyService.get(flowapply.getId());
    		return "redirect:" + adminPath + "/leipiflow/leipiFlowApply/myLeipiTaskById?repage&flowid="+flowapply.getFlowid();
    	}
	}
	
	/**
	 * 验证步骤是否是执行时替换
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @throws IOException
	 */
	@RequestMapping(value = "checkAutoPerson")
	public void checkAutoPerson(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
		Map map = new HashMap();
		PrintWriter out =  response.getWriter();
		try {
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			String flowId=request.getParameter("flowId");
			String processRunId=request.getParameter("processRunId");
			if(FormatUtil.isNoEmpty(processRunId)){//非第一步
				LeipiRunProcess tempLeipiRunProcess=new LeipiRunProcess();
				tempLeipiRunProcess.setRunFlow(processRunId);
				tempLeipiRunProcess.setIsDel(0);
				tempLeipiRunProcess.setStatus(0);
				tempLeipiRunProcess.setUpid(UserUtils.getUser().getId());
				List<LeipiRunProcess> leipiRunProcessList=leipiRunProcessService.findList(tempLeipiRunProcess);
				LeipiRunProcess runProcess=leipiRunProcessList.get(0);
				LeipiFlowProcess process=leipiFlowProcessService.get(runProcess.getRunFlowProcess());
				if(FormatUtil.isNoEmpty(process.getProcessTo())){
					//此步骤是否所有用户都处理了
					LeipiRunProcess tempLeipiRunProcess2=new LeipiRunProcess();
					tempLeipiRunProcess2.setRunFlow(processRunId);
					tempLeipiRunProcess2.setStatus(0);
					tempLeipiRunProcess2.setIsDel(0);
					tempLeipiRunProcess2.setAddprocessFlag("1");//将中间步骤添加的未处理的排除出去
					tempLeipiRunProcess2.setAgentedidFlag("1");//将代理的步骤排除出去
					List<LeipiRunProcess> leipiRunProcessList2=leipiRunProcessService.findList(tempLeipiRunProcess2);
					if(process.getParallel()==0){//并行
						map.put("gonext", true);
					}else if(process.getParallel()!=0 && leipiRunProcessList2.size() == 1){
						map.put("gonext", true);
					}else{
						map.put("gonext", false);
					}

					LeipiFlowProcess nextprocess = leipiFlowProcessService.get(process.getProcessTo());//下一环节
					if(nextprocess.getAutoPerson()==7){//是执行时替换
						map.put("status", "y");
						map.put("info", "是执行时替换");
						map.put("ids", "");
					}else if(nextprocess.getAutoPerson()==1){//发起人

						Flowapply fa = new Flowapply();
						fa.setProcessInstanceId(runProcess.getRunFlow());
						List<Flowapply> flowapplys = flowapplyService.findList(fa);

						if(flowapplys.size()>0){
							map.put("ids", flowapplys.get(0).getCreateBy().getId());
						}else{
							map.put("ids", "");
						}

						map.put("status", "n");
						map.put("info", "非执行时替换");
					}else{
						map.put("status", "n");
						map.put("info", "非执行时替换");

						//查询下一步执行人userids
						String userids = leipiFlowService.getNextProcessUserids(process.getProcessTo());
						map.put("ids", userids);
					}
					map.put("processname", nextprocess.getProcessName());
				}else{
					map.put("status", "end");
					map.put("info", "无下一个步骤无需验证");
					map.put("ids", "");
				}

//				if(existNextProcess(process)){
//					if(process.getAutoPerson()==7){//是执行时替换
//						map.put("status", "y");
//						map.put("info", "是执行时替换");
//					}else{
//						map.put("status", "n");
//						map.put("info", "非执行时替换");
//					}
//				}else{
//					map.put("status", "n");
//					map.put("info", "无下一个步骤无需验证");
//				}
			}else{//第一步
				LeipiFlow leipiFlow=leipiFlowService.get(flowId);
				List<LeipiFlowProcess> leipiFlowProcess = leipiFlowProcessService.findList(new LeipiFlow(flowId));
				LeipiFlowProcess processFirst = null;
				for (LeipiFlowProcess leipiFlowProcess2 : leipiFlowProcess) {
					if("is_one".equals(leipiFlowProcess2.getProcessType())){
						processFirst = leipiFlowProcess2;
						break;
					}
				}
				if(processFirst.getAutoPerson()==7){//是执行时替换
					map.put("status", "y");
					map.put("info", "是执行时替换");
					map.put("ids", "");
				}else if(processFirst.getAutoPerson()==1){//发起人
					map.put("status", "n");
					map.put("info", "非执行时替换");
					map.put("ids", UserUtils.getUser().getId());
				}else{
					map.put("status", "n");
					map.put("info", "非执行时替换");
					//查询下一步执行人userids
					String userids = leipiFlowService.getNextProcessUserids(processFirst.getId());
					map.put("ids", userids);
				}
				map.put("processname", processFirst.getProcessName());
			}
		} catch (Exception e) {
			logger.error("验证步骤是否执行时替换失败：", e);
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "系统内部错误");map.put("ids", "");

		}
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	/**
	 * 是否存在下一步步骤
	 * @return
	 */
	private boolean existNextProcess(LeipiFlowProcess leipiFlowProcess) {
		//查询是否有下一步步骤
		String ProcessTo=leipiFlowProcess.getProcessTo();
		if(FormatUtil.isNoEmpty(ProcessTo)){//存在下一步步骤
			return true;
		}
		return false;
	}

    /**
     * 任务处理
     * @param
     * @return
     */
    @RequestMapping(value = "leipiApplyAddProcess")
    public void leipiApplyAddProcess(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
        Map map = new HashMap();
        PrintWriter out =  response.getWriter();

        String addids=request.getParameter("ids");
        String processInstanceId=request.getParameter("processInstanceId");
        LeipiRun leipiRun=leipiRunService.get(processInstanceId);

        //查找当前流程步骤
        LeipiRunProcess tempLeipiRunProcess=new LeipiRunProcess();
        tempLeipiRunProcess.setRunFlow(leipiRun.getId());
        tempLeipiRunProcess.setIsDel(0);
//        tempLeipiRunProcess.setUpid(UserUtils.getUser().getId());
        tempLeipiRunProcess.setRunFlowProcess(leipiRun.getRunFlowProcess());
        List<LeipiRunProcess> leipiRunProcessList=leipiRunProcessService.findList(tempLeipiRunProcess);

        LeipiRunProcess leipiRunProcessTemp=leipiRunProcessList.get(0);

        User loginUser = UserUtils.getUser();
        for(String id:addids.split(",")){
            Boolean flag = true;
            for(LeipiRunProcess lp : leipiRunProcessList){
                if(lp.getUpid().equals(id)){
                    flag = false;
                }
            }
            if(flag){
                LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
                leipiRunProcess.setUpid(id);
                leipiRunProcess.setRunFlow(leipiRun.getId());
                leipiRunProcess.setRunFlowProcess(leipiRunProcessTemp.getRunFlowProcess());
                leipiRunProcess.setRunId(leipiRunProcessTemp.getRunId());
                leipiRunProcess.setIsReceiveType(0);
                leipiRunProcess.setIsSponsor(0);
                leipiRunProcess.setIsSingpost(0);
                leipiRunProcess.setIsBack(0);
                leipiRunProcess.setStatus(0);
                leipiRunProcess.setJsTime(new Date());
                leipiRunProcess.setUpdatetime(new Date());
                leipiRunProcess.setIsDel(0);
                leipiRunProcess.setDateline(new Date());
                leipiRunProcess.setIsOpen(0);
                leipiRunProcess.setAddprocessid(loginUser.getId());
                leipiRunProcessService.save(leipiRunProcess);
            }
        }
		//查询代理人
		for(String id:addids.split(",")){
			Boolean flag = false;
			Flowagent flowagent = flowagentService.getFlowAgent(id);
			if(FormatUtil.isNoEmpty(flowagent)){
				flag=true;
				for(LeipiRunProcess lp : leipiRunProcessList){
					if(lp.getUpid().equals(flowagent.getAgentuserid())){
						flag = false;
					}
				}
			}
			if(flag){
				LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
				leipiRunProcess.setUpid(flowagent.getAgentuserid());//代理iD
				leipiRunProcess.setRunFlow(leipiRun.getId());
				leipiRunProcess.setRunFlowProcess(leipiRunProcessTemp.getRunFlowProcess());
				leipiRunProcess.setRunId(leipiRunProcessTemp.getRunId());
				leipiRunProcess.setIsReceiveType(0);
				leipiRunProcess.setIsSponsor(0);
				leipiRunProcess.setIsSingpost(0);
				leipiRunProcess.setIsBack(0);
				leipiRunProcess.setStatus(0);
				leipiRunProcess.setJsTime(new Date());
				leipiRunProcess.setUpdatetime(new Date());
				leipiRunProcess.setIsDel(0);
				leipiRunProcess.setDateline(new Date());
				leipiRunProcess.setAgentedid(id);//被代理的用户iD
				leipiRunProcess.setIsOpen(0);
				leipiRunProcess.setAddprocessid(loginUser.getId());
				leipiRunProcessService.save(leipiRunProcess);
			}
		}


		map.put("status", "y");
		map.put("info", "操作成功！");

        out.write(JSONObject.fromObject(map).toString());
        out.close();
    }

	@RequestMapping(value = "delete")
	public String delete(RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String id=request.getParameter("id");
        Flowapply apply=flowapplyService.get(id);
		flowapplyService.delete(id);
		addMessage(redirectAttributes, "删除成功！");
        if(apply.getFlowtype() == 1){
            return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowApply/myLeipiFlowById?flowid="+apply.getFlowid()+"&repage";
        }else{
            return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowApply/myLeipiFlow/?repage";
        }

	}

	@RequestMapping(value = "unDoFlowApply")
	public String unDoFlowApply(RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String id=request.getParameter("id");
        Flowapply apply=flowapplyService.get(id);
		flowapplyService.unDoFlowApply(id);
		addMessage(redirectAttributes, "撤销成功！");

        if(apply.getFlowtype() == 1){
            return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowApply/myLeipiView?id="+apply.getId()+"&flag=1";
        }else{
            return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowApply/myLeipiFlow/?repage";
        }
	}

	/**
	 * 任务处理
	 * @param
	 * @return
	 */
	@RequestMapping(value = "leipiApplyAddProcessNoLast")
	public void leipiApplyAddProcessNoLast(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
		Map map = new HashMap();
		PrintWriter out =  response.getWriter();

		String addids=request.getParameter("ids");
		String processInstanceId=request.getParameter("processInstanceId");
		//插入环节的前面的环节
		String processId=request.getParameter("processId");
		LeipiRunProcess lrp=leipiRunProcessService.get(processId);

		LeipiRun leipiRun=leipiRunService.get(processInstanceId);

		//查找当前流程步骤
		LeipiRunProcess tempLeipiRunProcess=new LeipiRunProcess();
		tempLeipiRunProcess.setRunFlow(leipiRun.getId());
		tempLeipiRunProcess.setIsDel(0);
//        tempLeipiRunProcess.setUpid(UserUtils.getUser().getId());
		tempLeipiRunProcess.setRunFlowProcess(lrp.getRunFlowProcess());
		List<LeipiRunProcess> leipiRunProcessList=leipiRunProcessService.findList(tempLeipiRunProcess);

		LeipiRunProcess leipiRunProcessTemp=lrp;

		User loginUser = UserUtils.getUser();
		for(String id:addids.split(",")){
			Boolean flag = true;
			for(LeipiRunProcess lp : leipiRunProcessList){
				if(lp.getUpid().equals(id)){
					flag = false;
				}
			}
			if(flag){
				LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
				leipiRunProcess.setUpid(id);
				leipiRunProcess.setRunFlow(leipiRun.getId());
				leipiRunProcess.setRunFlowProcess(leipiRunProcessTemp.getRunFlowProcess());
				leipiRunProcess.setRunId(leipiRunProcessTemp.getRunId());
				leipiRunProcess.setIsReceiveType(0);
				leipiRunProcess.setIsSponsor(0);
				leipiRunProcess.setIsSingpost(0);
				leipiRunProcess.setIsBack(0);
				leipiRunProcess.setStatus(0);
				leipiRunProcess.setJsTime(new Date());
				leipiRunProcess.setUpdatetime(new Date());
				leipiRunProcess.setIsDel(0);
				leipiRunProcess.setDateline(FormatUtil.todaySecondOperate(lrp.getDateline(),1,0));
				leipiRunProcess.setAddprocessDate(new Date());
				leipiRunProcess.setIsOpen(0);
				leipiRunProcess.setAddprocessid(loginUser.getId());
				leipiRunProcessService.save(leipiRunProcess);
			}
		}

		map.put("status", "y");
		map.put("info", "操作成功！");

		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
}