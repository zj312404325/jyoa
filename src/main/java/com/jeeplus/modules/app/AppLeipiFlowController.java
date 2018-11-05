/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.app;

import com.Util.PropertiesUtil;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.websocket.onchat.ChatServerPool;
import com.jeeplus.common.websocket.utils.Constant;
import com.jeeplus.modules.flow.dao.TemplatecontentDao;
import com.jeeplus.modules.flow.entity.Flowagent;
import com.jeeplus.modules.flow.entity.Flowapply;
import com.jeeplus.modules.flow.entity.Templatecontent;
import com.jeeplus.modules.flow.entity.Tmplatefile;
import com.jeeplus.modules.flow.service.FlowagentService;
import com.jeeplus.modules.flow.service.FlowapplyService;
import com.jeeplus.modules.leipiflow.entity.LeipiFlow;
import com.jeeplus.modules.leipiflow.entity.LeipiFlowProcess;
import com.jeeplus.modules.leipiflow.entity.LeipiRun;
import com.jeeplus.modules.leipiflow.entity.LeipiRunProcess;
import com.jeeplus.modules.leipiflow.service.LeipiFlowProcessService;
import com.jeeplus.modules.leipiflow.service.LeipiFlowService;
import com.jeeplus.modules.leipiflow.service.LeipiRunProcessService;
import com.jeeplus.modules.leipiflow.service.LeipiRunService;
import com.jeeplus.modules.oa.dao.OaNotifyFileDao;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyFile;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import freemarker.ext.beans.HashAdapter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;


@Controller
@RequestMapping(value = "app/leipiflow")
public class AppLeipiFlowController extends BaseController {

	@Autowired
	private LeipiRunProcessService leipiRunProcessService;

	@Autowired
	private FlowapplyService flowapplyService;

	@Autowired
	private LeipiRunService leipiRunService;

	@Autowired
	private LeipiFlowService leipiFlowService;

	@Autowired
	private LeipiFlowProcessService leipiFlowProcessService;

    @Autowired
    private FlowagentService flowagentService;
	@Autowired
	private TemplatecontentDao templatecontentDao;
	@Autowired
	private OaNotifyFileDao oaNotifyFileDao;

	/*
	我的申请
	 */
	@RequestMapping(value = {"myLeipiFlow"})
	public void myLeipiFlow(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));

		String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
		User loginUser = UserUtils.get(userId);

		Flowapply flowapply = new Flowapply();
		flowapply.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
		//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				flowapply.setSearchdays(4);
			}
		}

		flowapply.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
		//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				flowapply.setSearchdays(4);
			}
		}
		flowapply.setCreateBy(loginUser);

		String currentPage= request.getParameter("currentPage");
		Page<Flowapply> pg=new Page<Flowapply>(FormatUtil.toInteger(currentPage),Global.pageSize);
		Page<Flowapply> page = flowapplyService.findPage(pg, flowapply);

		List<Map> dataList=new ArrayList<Map>();
		Map mapTemp = null;
		for (Flowapply apply : page.getList()) {

			LeipiRun leipiRun = leipiRunService.get(apply.getProcessInstanceId());//从数据库取出记录的值
			LeipiFlow leipiFlow = leipiFlowService.get(leipiRun.getFlowId());

			LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
			String runflowid=leipiRun.getId();
			leipiRunProcess.setRunFlow(runflowid);
			leipiRunProcess.setIsDel(0);
			leipiRunProcess.getPage().setOrderBy("a.dateline desc");
			List<LeipiRunProcess> leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
			LeipiFlowProcess leipiFlowProcess=null;
			if(FormatUtil.isNoEmpty(leipiRunProcessList)&&leipiRunProcessList.size() > 0){
				leipiFlowProcess = leipiFlowProcessService.get(leipiRunProcessList.get(0).getRunFlowProcess());
				leipiRunProcess.setLeipiFlowProcess(leipiFlowProcess);
				apply.setLeipiFlowProcess(leipiFlowProcess);
				apply.setLeipiRunProcess(leipiRunProcessList.get(0));
			}
			mapTemp = new HashMap();
			mapTemp.put("id",apply.getId());
			mapTemp.put("title",StringEscapeUtils.unescapeHtml(apply.getVar2()));
			mapTemp.put("flowname",StringEscapeUtils.unescapeHtml(leipiFlow.getFlowname()));
			mapTemp.put("flowno",apply.getPid());
			mapTemp.put("processname","");
			if(FormatUtil.isNoEmpty(leipiFlowProcess)){
				mapTemp.put("processname",leipiFlowProcess.getProcessName());
			}
			mapTemp.put("applytime",FormatUtil.dateToString(apply.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
			mapTemp.put("status",leipiRun.getStatus());
			//查询附件
			Templatecontent templatecontent=new Templatecontent();
			templatecontent.setFlowapply(apply);
			templatecontent.setColumntype("4");//上传控件
			List<Templatecontent> templatecontentList=templatecontentDao.findList(templatecontent);
			String hasfile="0";
			for (int i = 0; i <templatecontentList.size() ; i++) {
				if(FormatUtil.isNoEmpty(templatecontentList.get(i).getColumnvalue())){
					hasfile="1";
					break;
				}
			}
			if(hasfile.equals("0")){
				List<OaNotifyFile> oaNotifyFileList=oaNotifyFileDao.findList(new OaNotifyFile(new OaNotify(apply.getId())));
				if(FormatUtil.isNoEmpty(oaNotifyFileList)&&oaNotifyFileList.size()>0){
					hasfile="1";
				}
			}
			mapTemp.put("hasfile",hasfile);
			dataList.add(mapTemp);
		}
//		model.addAttribute("page", page);
//		model.addAttribute("flowapply",flowapply);

		infoMap.put("data", dataList);

		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		return ;
	}

	/*
	待办任务
	 */
	@RequestMapping(value = {"myLeipiTask"})
	public void myLeipiTask(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));

		String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
		User loginUser = UserUtils.get(userId);

		String type=request.getParameter("type");
		//获取待办步骤
		LeipiRunProcess leipiRunProcess=new LeipiRunProcess();
		leipiRunProcess.setUpid(loginUser.getId());
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
		String currentPage= request.getParameter("currentPage");
		Page<LeipiRunProcess> pageTemp = new Page<LeipiRunProcess>(FormatUtil.toInteger(currentPage),Global.pageSize);
		pageTemp.setOrderBy("a.dateline desc");
//		leipiRunProcess.setKeyword(flowapply.getKeyword());
//		leipiRunProcess.setKeyword1(flowapply.getKeyword1());
//		leipiRunProcess.setVar2(flowapply.getVar2());
//		if(FormatUtil.isNoEmpty(flowapply.getOffice())){
//			//获得此部门及其子部门的集合
//			leipiRunProcess.setOffice(flowapply.getOffice());
//		}

		leipiRunProcess.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				leipiRunProcess.setSearchdays(4);
			}
		}

		Page<LeipiRunProcess> page = leipiRunProcessService.findFirstPage(pageTemp, leipiRunProcess);
		List<Flowapply> flowapplyList=new ArrayList<Flowapply>();
		List<Map> dataList=new ArrayList<Map>();
		Map mapTemp = null;
		if(FormatUtil.isNoEmpty(page.getList())&&page.getList().size()>0){
			for (LeipiRunProcess leipiRunProcess2 : page.getList()) {
				String runFlow=leipiRunProcess2.getRunFlow();
				String runFlowProcess=leipiRunProcess2.getRunFlowProcess();

				LeipiRun leipiRun=leipiRunService.get(runFlow);
				Flowapply applyTemp=new Flowapply();
				if(FormatUtil.isNoEmpty(request.getParameter("flowtype"))){
					applyTemp.setFlowtype(FormatUtil.toInt(request.getParameter("flowtype")));
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
//					apply.setLeipiFlow(leipiFlow);
//					apply.setLeipiFlowProcess(leipiFlowProcess);
//					apply.setLeipiRun(leipiRun);
//					apply.setLeipiRunProcess(leipiRunProcess2);
//					apply.setCreateBy(UserUtils.get(apply.getCreateBy().getId()));
//					flowapplyList.add(apply);

					mapTemp = new HashMap();
					mapTemp.put("id",apply.getId());
					mapTemp.put("title",StringEscapeUtils.unescapeHtml(apply.getVar2()));
					mapTemp.put("flowname",StringEscapeUtils.unescapeHtml(leipiFlow.getFlowname()));
					mapTemp.put("flowno",apply.getPid());
					mapTemp.put("processname",StringEscapeUtils.unescapeHtml(leipiFlowProcess.getProcessName()));
					User applyUser = UserUtils.get(apply.getCreateBy().getId());
					mapTemp.put("applyusername",applyUser.getName());
					mapTemp.put("applyuseroffice",apply.getOffice().getName());
					mapTemp.put("applytime",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"yyyy-MM-dd HH:mm:ss"));

					//查询附件
					Templatecontent templatecontent=new Templatecontent();
					templatecontent.setFlowapply(apply);
					templatecontent.setColumntype("4");//上传控件
					List<Templatecontent> templatecontentList=templatecontentDao.findList(templatecontent);
					String hasfile="0";
					for (int i = 0; i <templatecontentList.size() ; i++) {
						if(FormatUtil.isNoEmpty(templatecontentList.get(i).getColumnvalue())){
							hasfile="1";
							break;
						}
					}
					if(hasfile.equals("0")){
						List<OaNotifyFile> oaNotifyFileList=oaNotifyFileDao.findList(new OaNotifyFile(new OaNotify(apply.getId())));
						if(FormatUtil.isNoEmpty(oaNotifyFileList)&&oaNotifyFileList.size()>0){
							hasfile="1";
						}
					}

					mapTemp.put("hasfile",hasfile);
					dataList.add(mapTemp);
				}

			}
		}
		infoMap.put("data", dataList);
		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		return ;
	}


	@RequestMapping(value = {"myLeipiEdit"})
	public void myLeipiEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));

		String applyflowid = request.getParameter("applyflowid");

		String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
		User loginUser = UserUtils.get(userId);
		String hasfile="0";

		if (StringUtils.isNotBlank(applyflowid)){
			Flowapply flowapply = flowapplyService.get(applyflowid);

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
//			infoMap.put("leipiFlowProcessNameList", newLeipiFlowProcessList);

			List<Map> listContent = new ArrayList<>();
			Map contentMap = null;
			for (Templatecontent templatecontent : flowapply.getTemplatecontentList()) {
				contentMap = new HashMap();
				contentMap.put("columntype",templatecontent.getColumntype());
				contentMap.put("columnname",StringEscapeUtils.unescapeHtml(templatecontent.getColumnname()));
				if(FormatUtil.toInteger(templatecontent.getColumntype())==4&&FormatUtil.isNoEmpty(templatecontent.getColumnvalue())){//上传空间
					String cloumnvalue=templatecontent.getColumnvalue();
					String[] fileurls=cloumnvalue.split(",");
//					List<Tmplatefile> lstTmplatefile=new ArrayList<Tmplatefile>();
					List<Map> listfile = new ArrayList<>();
					Map fileMap = null;
					for (String string : fileurls) {
						if(FormatUtil.isNoEmpty(string)){
							fileMap = new HashMap();
							String filename=FormatUtil.getfilename(string);
							try {
								filename=java.net.URLDecoder.decode(filename,"UTF-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							fileMap.put("filename",filename);
							fileMap.put("url",string);
							listfile.add(fileMap);
						}
					}
					contentMap.put("columnvalue",listfile);
					//有附件
					if(listfile.size()>0){
						hasfile="1";
					}
				}else{
					contentMap.put("columnvalue",FormatUtil.isNoEmpty(templatecontent.getColumnvalue())?FormatUtil.delHTMLTag(templatecontent.getColumnvalue()):"");
				}
				listContent.add(contentMap);
			}

			List<Map> listFile = new ArrayList<>();
			Map fileMap = null;
			for (OaNotifyFile file : flowapply.getOaNotifyFileList()) {
				fileMap = new HashMap();
				fileMap.put("id",file.getId());
				fileMap.put("fileurl",file.getFileurl());
				fileMap.put("filename",file.getFilename());
				User uploadUser = UserUtils.get(file.getUser().getId());
				fileMap.put("uploadname",FormatUtil.isNoEmpty(uploadUser)?uploadUser.getName():"");
				fileMap.put("uploaddate",FormatUtil.dateToString(file.getUploadDate(),"yyyy-MM-dd HH:mm:ss"));
				if(loginUser.getId().equals(file.getUser().getId())){
					fileMap.put("isuploadme","1");
				}else{
					fileMap.put("isuploadme","0");
				}
				listFile.add(fileMap);
			}
			//有附件
			if(hasfile.equals("0")){
				if(listFile.size()>0){
					hasfile="1";
				}
			}

			LeipiRun leipiRun = leipiRunService.get(flowapply.getProcessInstanceId());//从数据库取出记录的值
			String runflowid=leipiRun.getId();

			LeipiRunProcess openProcess = new LeipiRunProcess();
			openProcess.setIsOpen(1);
			openProcess.setRunFlow(runflowid);
			openProcess.setUpid(loginUser.getId());
			leipiRunProcessService.updateIsOpen(openProcess);

			LeipiFlow  leipiFlow =leipiFlowService.get(leipiRun.getFlowId());
			flowapply.setLeipiFlow(leipiFlow);
			flowapply.setLeipiRun(leipiRun);


			LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
			leipiRunProcess.setRunFlow(runflowid);
			flowapply.setProcessInstanceId(runflowid);
			leipiRunProcess.setIsDel(0);
			List<LeipiRunProcess>  leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
			List<Map>  leipiRunProcessListTemp = null;
			List<Map>  leipiRunProcessMapTemp = new ArrayList<Map>();
			Map mapsTemp = new HashMap();
			Map leipiRunProcessMap = null;
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
					leipiRunProcessListTemp = new ArrayList<Map>();

//					leipiRunProcessListTemp.add(leipiRunProcess2);
					leipiRunProcessMap = new HashMap();
					leipiRunProcessMap.put("agentusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAgentUser())?leipiRunProcess2.getAgentUser().getName():"");
					leipiRunProcessMap.put("addusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAddprocessid())?UserUtils.get(leipiRunProcess2.getAddprocessid()).getName():"");
					leipiRunProcessMap.put("status",leipiRunProcess2.getStatus());
					leipiRunProcessMap.put("isopen",leipiRunProcess2.getIsOpen());
					leipiRunProcessMap.put("jstime_ymd",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"yyyy-MM-dd"));
					leipiRunProcessMap.put("jstime_hms",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"HH:mm:ss"));
					leipiRunProcessMap.put("runusername",leipiRunProcess2.getRunUser().getName());
					leipiRunProcessMap.put("remark",leipiRunProcess2.getRemark());
					leipiRunProcessListTemp.add(leipiRunProcessMap);

				}else if(!processName.equals(lp.getProcessName())){
					mapsTemp.put("processList",leipiRunProcessListTemp);
					leipiRunProcessMapTemp.add(mapsTemp);
					processName = lp.getProcessName();
					mapsTemp = new HashMap();
					leipiRunProcessListTemp = new ArrayList<Map>();
//					leipiRunProcessListTemp.add(leipiRunProcess2);
					leipiRunProcessMap = new HashMap();
					leipiRunProcessMap.put("agentusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAgentUser())?leipiRunProcess2.getAgentUser().getName():"");
					leipiRunProcessMap.put("addusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAddprocessid())?UserUtils.get(leipiRunProcess2.getAddprocessid()).getName():"");
					leipiRunProcessMap.put("status",leipiRunProcess2.getStatus());
					leipiRunProcessMap.put("isopen",leipiRunProcess2.getIsOpen());
					leipiRunProcessMap.put("jstime_ymd",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"yyyy-MM-dd"));
					leipiRunProcessMap.put("jstime_hms",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"HH:mm:ss"));
					leipiRunProcessMap.put("runusername",leipiRunProcess2.getRunUser().getName());
					leipiRunProcessMap.put("remark",leipiRunProcess2.getRemark());
					leipiRunProcessListTemp.add(leipiRunProcessMap);
					mapsTemp.put("processName",processName);

				}else if(processName.equals(lp.getProcessName())){
//					leipiRunProcessListTemp.add(leipiRunProcess2);
					leipiRunProcessMap = new HashMap();
					leipiRunProcessMap.put("agentusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAgentUser())?leipiRunProcess2.getAgentUser().getName():"");
					leipiRunProcessMap.put("addusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAddprocessid())?UserUtils.get(leipiRunProcess2.getAddprocessid()).getName():"");
					leipiRunProcessMap.put("status",leipiRunProcess2.getStatus());
					leipiRunProcessMap.put("isopen",leipiRunProcess2.getIsOpen());
					leipiRunProcessMap.put("jstime_ymd",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"yyyy-MM-dd"));
					leipiRunProcessMap.put("jstime_hms",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"HH:mm:ss"));
					leipiRunProcessMap.put("runusername",leipiRunProcess2.getRunUser().getName());
					leipiRunProcessMap.put("remark",leipiRunProcess2.getRemark());
					leipiRunProcessListTemp.add(leipiRunProcessMap);
					mapsTemp.put("processName",processName);
				}

			}
			if(FormatUtil.isNoEmpty(leipiRunProcessListTemp)){
				mapsTemp.put("processList",leipiRunProcessListTemp);
			}else{//不存在
				leipiRunProcessListTemp = new ArrayList<Map>();
				mapsTemp.put("processList",leipiRunProcessListTemp);
			}

			mapsTemp.put("islast","true");
			leipiRunProcessMapTemp.add(mapsTemp);

			flowapply.setCreateBy(UserUtils.get(flowapply.getCreateBy().getId()));
//			infoMap.put("flowapply", flowapply);
			Map flowapplyData = new HashMap();
			flowapplyData.put("title",StringEscapeUtils.unescapeHtml(flowapply.getVar2()));
			flowapplyData.put("applydate",FormatUtil.dateToString(flowapply.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
			flowapplyData.put("applydate_ymd",FormatUtil.dateToString(flowapply.getCreateDate(),"yyyy-MM-dd"));
			flowapplyData.put("applydate_hms",FormatUtil.dateToString(flowapply.getCreateDate(),"HH:mm:ss"));
			flowapplyData.put("applyname",flowapply.getCreateBy().getName());
			String flowname = "【"+StringEscapeUtils.unescapeHtml(flowapply.getLeipiFlow().getFlowname())+"】";
			if(flowapply.getLeipiRun().getStatus() == 0){
				flowname+="--【运行中】";
			}else if(flowapply.getLeipiRun().getStatus() == 1){
				flowname+="--【已完成】";
			}else if(flowapply.getLeipiRun().getStatus() == 2){
				flowname+="--【已撤回】";
			}
			flowapplyData.put("leipirun_status",flowapply.getLeipiRun().getStatus());
			flowapplyData.put("flowname",flowname);
			infoMap.put("data", flowapplyData);
			infoMap.put("contentdata", listContent);
			infoMap.put("fileistldata", listFile);
			infoMap.put("leipiRunProcessdata", leipiRunProcessMapTemp);
			infoMap.put("hasfile", hasfile);
//			infoMap.put("leipiRunProcessList", leipiRunProcessList);
//			infoMap.put("leipiRunProcessMap", leipiRunProcessMapTemp);

		}
		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		return ;
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

	@RequestMapping(value = "delFlowApply")
	public void delFlowApply(HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
		Map<String,Object> infoMapData = new HashMap<String,Object>();
		String id = request.getParameter("id");
		try {
			flowapplyService.delete(id);
			infoMapData.put("d", "y");
			infoMapData.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			infoMapData.put("d", "n");
			infoMapData.put("msg", "删除失败");
		}
		out.write(JSONObject.fromObject(infoMapData).toString());
		out.close();
	}

	@RequestMapping(value = "saveAudit")
	public void saveAudit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out =  response.getWriter();
        //返回的信息Map
        Map<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));

        String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
        User loginUser = UserUtils.get(userId);

        List<String> sendUidList = new ArrayList<String>();
        String applyflowid = request.getParameter("applyflowid");
        Flowapply flowapply = flowapplyService.get(applyflowid);
        try {
            String flag=request.getParameter("flag");
            String userIds=request.getParameter("userIds");
            String commentvalue=request.getParameter("commentvalue");
            flowapply.getAct().setComment(commentvalue);
            //userList待办任务提示用户列表
            sendUidList=leipiFlowService.saveAuditByApp(flowapply, flag,userIds,loginUser);

            infoMap.put("d", "y");
            infoMap.put("msg", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            infoMap.put("d", "n");
            infoMap.put("msg", "操作失败");
        }



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

								map.put("cnt2", leipiRunProcessService.findFirstList(leipiRunProcess).size());
								map.put("cnt1", "-1");
								map.put("cnt3", "-1");

								List<Map> lst = new ArrayList<Map>();
								Map maptemp = new HashMap();
								maptemp.put("url", ctx+adminPath+"/leipiflow/leipiFlowApply/myLeipiTask/?type=0");
								maptemp.put("title", "你收到了新的待办任务,请及时处理！");
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
		final String sendUid = loginUser.getId();
		final User sendU = loginUser;
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

						map.put("cnt2", leipiRunProcessService.findFirstList(leipiRunProcess).size());
						map.put("cnt1", "-1");
						map.put("cnt3", "-1");
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

		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		return ;

	}

    @RequestMapping(value = "checkAutoPerson")
    public void checkAutoPerson(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter out =  response.getWriter();
        //返回的信息Map
        Map<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
        try {
            String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
            User loginUser = UserUtils.get(userId);
//            response.setHeader("Content-Type", "text/html;charset=utf-8");
//            String flowId=request.getParameter("flowId");
//            String processRunId=request.getParameter("processRunId");
            String applyflowid = request.getParameter("applyflowid");
            Flowapply flowapply = flowapplyService.get(applyflowid);
            String flowId=flowapply.getFlowid();
            String processRunId=flowapply.getProcessInstanceId();

            if(FormatUtil.isNoEmpty(processRunId)){//非第一步
                LeipiRunProcess tempLeipiRunProcess=new LeipiRunProcess();
                tempLeipiRunProcess.setRunFlow(processRunId);
                tempLeipiRunProcess.setIsDel(0);
                tempLeipiRunProcess.setStatus(0);
                tempLeipiRunProcess.setUpid(loginUser.getId());
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
                        infoMap.put("gonext", true);
                    }else if(process.getParallel()!=0 && leipiRunProcessList2.size() == 1){
                        infoMap.put("gonext", true);
                    }else{
                        infoMap.put("gonext", false);
                    }

                    LeipiFlowProcess nextprocess = leipiFlowProcessService.get(process.getProcessTo());//下一环节
                    if(nextprocess.getAutoPerson()==7){//是执行时替换
                        infoMap.put("status", "y");
                        infoMap.put("info", "是执行时替换");
                        infoMap.put("ids", "");
                    }else if(nextprocess.getAutoPerson()==1){//发起人

                        Flowapply fa = new Flowapply();
                        fa.setProcessInstanceId(runProcess.getRunFlow());
                        List<Flowapply> flowapplys = flowapplyService.findList(fa);

                        if(flowapplys.size()>0){
                            infoMap.put("ids", flowapplys.get(0).getCreateBy().getId());
                        }else{
                            infoMap.put("ids", "");
                        }

                        infoMap.put("status", "n");
                        infoMap.put("info", "非执行时替换");
                    }else{
                        infoMap.put("status", "n");
                        infoMap.put("info", "非执行时替换");

                        //查询下一步执行人userids
                        String userids = leipiFlowService.getNextProcessUseridsByApp(process.getProcessTo(),loginUser);
                        infoMap.put("ids", userids);
                    }
                    infoMap.put("processname", nextprocess.getProcessName());
                }else{
                    infoMap.put("status", "end");
                    infoMap.put("info", "无下一个步骤无需验证");
                    infoMap.put("ids", "");
					infoMap.put("gonext", "");
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
                    infoMap.put("status", "y");
                    infoMap.put("info", "是执行时替换");
                    infoMap.put("ids", "");
                }else if(processFirst.getAutoPerson()==1){//发起人
                    infoMap.put("status", "n");
                    infoMap.put("info", "非执行时替换");
                    infoMap.put("ids", loginUser.getId());
                }else{
                    infoMap.put("status", "n");
                    infoMap.put("info", "非执行时替换");
                    //查询下一步执行人userids
                    String userids = leipiFlowService.getNextProcessUseridsByApp(processFirst.getId(),loginUser);
                    infoMap.put("ids", userids);
                }
                infoMap.put("processname", processFirst.getProcessName());
            }
        } catch (Exception e) {
            logger.error("验证步骤是否执行时替换失败：", e);
            e.printStackTrace();
            infoMap.put("status", "n");
            infoMap.put("info", "系统内部错误");
            infoMap.put("ids", "");

        }
        JSONObject json = JSONObject.fromObject(infoMap);
        out.print(json.toString());
        return ;
    }

    @RequestMapping(value = "leipiApplyAddProcess")
    public void leipiApplyAddProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out =  response.getWriter();
        //返回的信息Map
        Map<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));

        try {
            String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
            User loginUser = UserUtils.get(userId);

            String addids=request.getParameter("ids");
//        String processInstanceId=request.getParameter("processInstanceId");

            String applyflowid = request.getParameter("applyflowid");
            Flowapply flowapply = flowapplyService.get(applyflowid);
            String flowId=flowapply.getFlowid();
            String processInstanceId=flowapply.getProcessInstanceId();
            LeipiRun leipiRun=leipiRunService.get(processInstanceId);

            //查找当前流程步骤
            LeipiRunProcess tempLeipiRunProcess=new LeipiRunProcess();
            tempLeipiRunProcess.setRunFlow(leipiRun.getId());
            tempLeipiRunProcess.setIsDel(0);
//        tempLeipiRunProcess.setUpid(UserUtils.getUser().getId());
            tempLeipiRunProcess.setRunFlowProcess(leipiRun.getRunFlowProcess());
            List<LeipiRunProcess> leipiRunProcessList=leipiRunProcessService.findList(tempLeipiRunProcess);

            LeipiRunProcess leipiRunProcessTemp=leipiRunProcessList.get(0);

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
//			infoMap.put("leipiFlowProcessNameList", newLeipiFlowProcessList);


            LeipiRun leipiRun2 = leipiRunService.get(flowapply.getProcessInstanceId());//从数据库取出记录的值
            String runflowid=leipiRun2.getId();

            LeipiRunProcess openProcess = new LeipiRunProcess();
            openProcess.setIsOpen(1);
            openProcess.setRunFlow(runflowid);
            openProcess.setUpid(loginUser.getId());
            leipiRunProcessService.updateIsOpen(openProcess);

            LeipiFlow  leipiFlow =leipiFlowService.get(leipiRun2.getFlowId());
            flowapply.setLeipiFlow(leipiFlow);
            flowapply.setLeipiRun(leipiRun2);


            LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
            leipiRunProcess.setRunFlow(runflowid);
            flowapply.setProcessInstanceId(runflowid);
            leipiRunProcess.setIsDel(0);
            List<LeipiRunProcess>  leipiRunProcessList2 = leipiRunProcessService.findList(leipiRunProcess);
            List<Map>  leipiRunProcessListTemp = null;
            List<Map>  leipiRunProcessMapTemp = new ArrayList<Map>();
            Map mapsTemp = null;
            Map leipiRunProcessMap = null;
            String processName="";
            for (LeipiRunProcess leipiRunProcess2 : leipiRunProcessList2) {
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
                    leipiRunProcessListTemp = new ArrayList<Map>();

//					leipiRunProcessListTemp.add(leipiRunProcess2);
                    leipiRunProcessMap = new HashMap();
                    leipiRunProcessMap.put("agentusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAgentUser())?leipiRunProcess2.getAgentUser().getName():"");
                    leipiRunProcessMap.put("addusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAddprocessid())?UserUtils.get(leipiRunProcess2.getAddprocessid()).getName():"");
                    leipiRunProcessMap.put("status",leipiRunProcess2.getStatus());
                    leipiRunProcessMap.put("isopen",leipiRunProcess2.getIsOpen());
                    leipiRunProcessMap.put("jstime_ymd",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"yyyy-MM-dd"));
                    leipiRunProcessMap.put("jstime_hms",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"HH:mm:ss"));
                    leipiRunProcessMap.put("runusername",leipiRunProcess2.getRunUser().getName());
                    leipiRunProcessMap.put("remark",leipiRunProcess2.getRemark());
                    leipiRunProcessListTemp.add(leipiRunProcessMap);

                }else if(!processName.equals(lp.getProcessName())){
                    mapsTemp.put("processList",leipiRunProcessListTemp);
                    leipiRunProcessMapTemp.add(mapsTemp);
                    processName = lp.getProcessName();
                    mapsTemp = new HashMap();
                    leipiRunProcessListTemp = new ArrayList<Map>();
//					leipiRunProcessListTemp.add(leipiRunProcess2);
                    leipiRunProcessMap = new HashMap();
                    leipiRunProcessMap.put("agentusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAgentUser())?leipiRunProcess2.getAgentUser().getName():"");
                    leipiRunProcessMap.put("addusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAddprocessid())?UserUtils.get(leipiRunProcess2.getAddprocessid()).getName():"");
                    leipiRunProcessMap.put("status",leipiRunProcess2.getStatus());
                    leipiRunProcessMap.put("isopen",leipiRunProcess2.getIsOpen());
                    leipiRunProcessMap.put("jstime_ymd",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"yyyy-MM-dd"));
                    leipiRunProcessMap.put("jstime_hms",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"HH:mm:ss"));
                    leipiRunProcessMap.put("runusername",leipiRunProcess2.getRunUser().getName());
                    leipiRunProcessMap.put("remark",leipiRunProcess2.getRemark());
                    leipiRunProcessListTemp.add(leipiRunProcessMap);
                    mapsTemp.put("processName",processName);

                }else if(processName.equals(lp.getProcessName())){
//					leipiRunProcessListTemp.add(leipiRunProcess2);
                    leipiRunProcessMap = new HashMap();
                    leipiRunProcessMap.put("agentusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAgentUser())?leipiRunProcess2.getAgentUser().getName():"");
                    leipiRunProcessMap.put("addusername",FormatUtil.isNoEmpty(leipiRunProcess2.getAddprocessid())?UserUtils.get(leipiRunProcess2.getAddprocessid()).getName():"");
                    leipiRunProcessMap.put("status",leipiRunProcess2.getStatus());
                    leipiRunProcessMap.put("isopen",leipiRunProcess2.getIsOpen());
                    leipiRunProcessMap.put("jstime_ymd",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"yyyy-MM-dd"));
                    leipiRunProcessMap.put("jstime_hms",FormatUtil.dateToString(leipiRunProcess2.getJsTime(),"HH:mm:ss"));
                    leipiRunProcessMap.put("runusername",leipiRunProcess2.getRunUser().getName());
                    leipiRunProcessMap.put("remark",leipiRunProcess2.getRemark());
                    leipiRunProcessListTemp.add(leipiRunProcessMap);
                    mapsTemp.put("processName",processName);
                }

            }
            mapsTemp.put("processList",leipiRunProcessListTemp);
            mapsTemp.put("islast","true");
            leipiRunProcessMapTemp.add(mapsTemp);

            flowapply.setCreateBy(UserUtils.get(flowapply.getCreateBy().getId()));
//			infoMap.put("flowapply", flowapply);
            Map flowapplyData = new HashMap();
            flowapplyData.put("title",flowapply.getVar2());
            flowapplyData.put("applydate",FormatUtil.dateToString(flowapply.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
            flowapplyData.put("applydate_ymd",FormatUtil.dateToString(flowapply.getCreateDate(),"yyyy-MM-dd"));
            flowapplyData.put("applydate_hms",FormatUtil.dateToString(flowapply.getCreateDate(),"HH:mm:ss"));
            flowapplyData.put("applyname",flowapply.getCreateBy().getName());
            String flowname = "【"+flowapply.getLeipiFlow().getFlowname()+"】";
            if(flowapply.getLeipiRun().getStatus() == 0){
                flowname+="--【运行中】";
            }else if(flowapply.getLeipiRun().getStatus() == 1){
                flowname+="--【已完成】";
            }else if(flowapply.getLeipiRun().getStatus() == 2){
                flowname+="--【已撤回】";
            }
            flowapplyData.put("leipirun_status",flowapply.getLeipiRun().getStatus());
            flowapplyData.put("flowname",flowname);
            infoMap.put("data", flowapplyData);
            infoMap.put("leipiRunProcessdata", leipiRunProcessMapTemp);


            infoMap.put("d", "y");
            infoMap.put("msg", "操作成功！");
        }catch (Exception e){
            e.printStackTrace();
            infoMap.put("d", "n");
            infoMap.put("msg", "操作失败！");
        }

        out.write(JSONObject.fromObject(infoMap).toString());
        out.close();
    }

	@RequestMapping(value = "unDoFlowApply")
	public void unDoFlowApply(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
		Map<String,Object> infoMapData = new HashMap<String,Object>();
		try {
			String id=request.getParameter("id");
			Flowapply apply=flowapplyService.get(id);
			flowapplyService.unDoFlowApply(id);
			infoMapData.put("d", "y");
			infoMapData.put("msg", "撤销成功");
		} catch (Exception e) {
			e.printStackTrace();
			infoMapData.put("d", "n");
			infoMapData.put("msg", "撤销失败");
		}
		out.write(JSONObject.fromObject(infoMapData).toString());
		out.close();
	}
}
