/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.service;

import com.google.common.collect.Maps;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.act.dao.ActDao;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.act.utils.ActUtils;
import com.jeeplus.modules.flow.dao.FlowapplyDao;
import com.jeeplus.modules.flow.dao.TemplatecontentDao;
import com.jeeplus.modules.flow.entity.Flowapply;
import com.jeeplus.modules.flow.entity.Flowtemplate;
import com.jeeplus.modules.flow.entity.Templatecontent;
import com.jeeplus.modules.leipiflow.dao.TemplateDetailDao;
import com.jeeplus.modules.leipiflow.entity.LeipiRun;
import com.jeeplus.modules.leipiflow.entity.LeipiRunProcess;
import com.jeeplus.modules.leipiflow.entity.TemplateDetail;
import com.jeeplus.modules.leipiflow.service.LeipiRunProcessService;
import com.jeeplus.modules.leipiflow.service.LeipiRunService;
import com.jeeplus.modules.oa.dao.OaNotifyFileDao;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyFile;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的申请Service
 * @author cqj
 * @version 2016-12-08
 */
@Service
@Transactional(readOnly = true)
public class FlowapplyService extends CrudService<FlowapplyDao, Flowapply> {
	@Autowired
	private FlowapplyDao flowapplyDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private FlowtemplateService flowtemplateService;
	@Autowired
	private FlowagentService flowagentService;
	@Autowired
	private ActDao actDao;
	@Autowired
	private LeipiRunService leipiRunService;
	@Autowired
	private LeipiRunProcessService leipiRunProcessService;
	
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TemplatecontentDao templatecontentDao;
	@Autowired
	private TemplateDetailDao templateDetailDao;
	@Autowired
	private OaNotifyFileDao oaNotifyFileDao;
	
	/**
	 * 获取流程详细及工作流参数
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public Flowapply get(String id) {
		Flowapply flowapply = flowapplyDao.get(id);
		flowapply.setTemplatecontentList(templatecontentDao.findList(new Templatecontent(flowapply)));
		flowapply.setTemplatedetailList(templateDetailDao.findList(new TemplateDetail(flowapply)));
//		for (TemplateDetail templatedetail : flowapply.getTemplatedetailList()){
//			if(FormatUtil.isNoEmpty(templatedetail.getVar10())){
//				templatedetail.setVars(templatedetail.getVar10().split(","));
//			}
//		}
		flowapply.setOaNotifyFileList(oaNotifyFileDao.findList(new OaNotifyFile(new OaNotify(id))));
		String userId=UserUtils.getUser().getId();
		for (OaNotifyFile oaNotifyFile:flowapply.getOaNotifyFileList()) {
			if(oaNotifyFile.getUser().getId().equals(userId)){
				oaNotifyFile.setCanEdit("1");
			}
		}
//		Map<String,Object> variables=null;
//		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(flowapply.getProcessInstanceId()).singleResult();
//		if(historicProcessInstance!=null) {
//			variables = Collections3.extractToMap(historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).list(), "variableName", "value");
//		} else {
//			variables = runtimeService.getVariables(runtimeService.createProcessInstanceQuery().processInstanceId(flowapply.getProcessInstanceId()).active().singleResult().getId());
//		}
//		flowapply.setVariables(variables);
		return flowapply;
	}
	
	/**
	 * 启动流程
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public String save(Flowapply flowapply, Map<String, Object> variables,String processkey) {
		
		// 保存业务数据
		if (StringUtils.isBlank(flowapply.getId())){
			flowapply.preInsert();
			flowapplyDao.insert(flowapply);
			for (Templatecontent templatecontent : flowapply.getTemplatecontentList()){
				/*if (templatecontent.getId() == null){
					continue;
				}*/
				if (Templatecontent.DEL_FLAG_NORMAL.equals(templatecontent.getDelFlag())){
					if (StringUtils.isBlank(templatecontent.getId())){
						templatecontent.setFlowapply(flowapply);
						templatecontent.preInsert();
						templatecontentDao.insert(templatecontent);
					}else{
						templatecontent.preUpdate();
						templatecontentDao.update(templatecontent);
					}
				}else{
					templatecontentDao.delete(templatecontent);
				}
			}
		}else{
			flowapply.preUpdate();
			flowapplyDao.update(flowapply);
			for (Templatecontent templatecontent : flowapply.getTemplatecontentList()){
				/*if (templatecontent.getId() == null){
					continue;
				}*/
				if (Templatecontent.DEL_FLAG_NORMAL.equals(templatecontent.getDelFlag())){
					if (StringUtils.isBlank(templatecontent.getId())){
						templatecontent.setFlowapply(flowapply);
						templatecontent.preInsert();
						templatecontentDao.insert(templatecontent);
					}else{
						templatecontent.preUpdate();
						templatecontentDao.update(templatecontent);
					}
				}else{
					templatecontentDao.delete(templatecontent);
				}
			}
		}
		logger.debug("save entity: {}", flowapply);
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(flowapply.getCurrentUser().getLoginName());
		
		// 启动流程
		String businessKey = flowapply.getId().toString();
		variables.put("type", "flowapply");
		variables.put("busId", businessKey);
		Flowtemplate flowtemplate=flowtemplateService.get(flowapply.getTemplateid());
		variables.put("title", flowtemplate.getTemplatename());//设置标题；
		variables.put("applyUserId", flowapply.getCurrentUser().getLoginName());//设置标题；
		variables.put("applyUser", UserUtils.getUser().getName());//设置名字
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processkey, businessKey, variables);
		flowapply.setProcessInstance(processInstance);
		
		// 更新流程实例ID
		flowapply.setProcessInstanceId(processInstance.getId());
		flowapplyDao.updateProcessInstanceId(flowapply);
		
		logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[] { 
				processkey, businessKey, processInstance.getId(), variables });
		
		//指定了代理人的处理流程
		String procid=processInstance.getId();
		/*String taskdefkey="";
		try {
			taskdefkey=actTaskService.getNextTask(processInstance.getId());
			Map<String,Object> mapTemp=new HashMap<String, Object>();
			mapTemp.put("procid", processInstance.getId());
			mapTemp.put("taskdefkey", taskdefkey);
			Map result=actDao.findNextAssignee(mapTemp);
			nextAssignee=(String) result.get("ASSIGNEE_");
			System.out.println("nextAssignee==========="+nextAssignee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return procid;
//		
//		if(FormatUtil.isNoEmpty(nextAssignee)){
//			checkFlowAgent(nextAssignee);
//		}
	}
	
	@Transactional(readOnly = false)
	public void save(Flowapply flowapply,List<Templatecontent> lstcontent) {
		
		if(FormatUtil.isNoEmpty(lstcontent)){
			//删除原有数据
			for (Templatecontent templatecontent : lstcontent) {
				templatecontentDao.delete(templatecontent);
			}
			for (Templatecontent templatecontent : flowapply.getTemplatecontentList()){
				if (Templatecontent.DEL_FLAG_NORMAL.equals(templatecontent.getDelFlag())){
					if (StringUtils.isBlank(templatecontent.getId())){
						templatecontent.setFlowapply(flowapply);
						templatecontent.preInsert();
						templatecontentDao.insert(templatecontent);
					}else{
						templatecontent.preUpdate();
						templatecontentDao.update(templatecontent);
					}
				}else{
					templatecontentDao.delete(templatecontent);
				}
			}
		}
		
		/*
		if (StringUtils.isBlank(flowapply.getId())){
			
		}else{
			flowapply.preUpdate();
			flowapplyDao.update(flowapply);
			for (Templatecontent templatecontent : flowapply.getTemplatecontentList()){
				if (Templatecontent.DEL_FLAG_NORMAL.equals(templatecontent.getDelFlag())){
					if (StringUtils.isBlank(templatecontent.getId())){
						templatecontent.setFlowapply(flowapply);
						templatecontent.preInsert();
						templatecontentDao.insert(templatecontent);
					}else{
						templatecontent.preUpdate();
						templatecontentDao.update(templatecontent);
					}
				}else{
					templatecontentDao.delete(templatecontent);
				}
			}
		}*/
	}

	/**
	 * 查询待办任务
	 * @param userId 用户ID
	 * @return
	 */
	public List<Flowapply> findTodoTasks(String userId) {
		
		List<Flowapply> results = new ArrayList<Flowapply>();
		List<Task> tasks = new ArrayList<Task>();
		// 根据当前人的ID查询
		List<Task> todoList = taskService.createTaskQuery().processDefinitionKey(ActUtils.PD_FLOWAPPLY[0]).taskAssignee(userId).active().orderByTaskPriority().desc().orderByTaskCreateTime().desc().list();
		// 根据当前人未签收的任务
		List<Task> unsignedTasks = taskService.createTaskQuery().processDefinitionKey(ActUtils.PD_FLOWAPPLY[0]).taskCandidateUser(userId).active().orderByTaskPriority().desc().orderByTaskCreateTime().desc().list();
		// 合并
		tasks.addAll(todoList);
		tasks.addAll(unsignedTasks);
		// 根据流程的业务ID查询实体并关联
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
			String businessKey = processInstance.getBusinessKey();
			Flowapply flowapply = flowapplyDao.get(businessKey);
			flowapply.setTask(task);
			flowapply.setProcessInstance(processInstance);
			flowapply.setProcessDefinition(repositoryService.createProcessDefinitionQuery().processDefinitionId((processInstance.getProcessDefinitionId())).singleResult());
			results.add(flowapply);
		}
		return results;
	}

	public Page<Flowapply> find(Page<Flowapply> page, Flowapply flowapply) {

		flowapply.getSqlMap().put("dsf", dataScopeFilter(flowapply.getCurrentUser(), "o", "u"));
		
		flowapply.setPage(page);
		page.setList(flowapplyDao.findList(flowapply));
		
		for(Flowapply item : page.getList()) {
			String processInstanceId = item.getProcessInstanceId();
			Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
			item.setTask(task);
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if(historicProcessInstance!=null) {
				item.setHistoricProcessInstance(historicProcessInstance);
				item.setProcessDefinition(repositoryService.createProcessDefinitionQuery().processDefinitionId(historicProcessInstance.getProcessDefinitionId()).singleResult());
			} else {
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
				if (processInstance != null){
					item.setProcessInstance(processInstance);
					item.setProcessDefinition(repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult());
				}
			}
		}
		return page;
	}
	
	/* * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public Map auditSave(Flowapply flowapply) {
		
		// 设置意见
		flowapply.getAct().setComment(("yes".equals(flowapply.getAct().getFlag())?"[同意] ":"[驳回] ")+flowapply.getAct().getComment());
		
		flowapply.preUpdate();
		
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = flowapply.getAct().getTaskDefKey();
       
		//业务逻辑对应的条件表达式
//		String exp = "";
//		// 审核环节
//		if ("deptLeaderAudit".equals(taskDefKey)){
//			
//			exp = "deptLeaderPass";
//			
//		}
//		else if ("hrAudit".equals(taskDefKey)){
//			exp = "hrPass";
//		}
//		else if ("modifyApply".equals(taskDefKey)){
//			exp = "reApply";
//		}
//		else if ("apply_end".equals(taskDefKey)){
//			
//		}
		
		/*// 未知环节，直接返回
		else{
			return;
		}*/
//		try {
//			String taskdefkey=actTaskService.getNextTask(flowapply.getAct().getProcInsId());
//			Map<String,Object> mapTemp=new HashMap<String, Object>();
//			mapTemp.put("procid", flowapply.getAct().getProcInsId());
//			mapTemp.put("taskdefkey", taskdefkey);
//			Map result=actDao.findNextAssignee(mapTemp);
//			nextAssignee=(String) result.get("ASSIGNEE_");
//			System.out.println("nextAssignee==========="+nextAssignee);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Map map=new HashMap<String, Object>();
		map.put("userid", UserUtils.getUser().getLoginName());
		map.put("taskid", flowapply.getAct().getTaskId());
		Map agented=null;
		List<Map> agentedlist=actDao.findAgented(map);
		for (Map map2 : agentedlist) {
			agented=new HashMap<String, Object>();
			agented=map2;
			break;
		}
		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(flowapply.getAct().getFlag())? true : false);
		if(!"yes".equals(flowapply.getAct().getFlag())){
			vars.put("adjust", "1");
		}else{
			vars.put("adjust", null);
		}
		//vars.put(exp, "yes".equals(flowapply.getAct().getFlag())? true : false);
		// 提交流程任务
		actTaskService.complete(flowapply.getAct().getTaskId(), flowapply.getAct().getProcInsId(), flowapply.getAct().getComment(), vars);
		
//		if(FormatUtil.isNoEmpty(nextAssignee)){
//			checkFlowAgent(nextAssignee);
//		}
		return agented;
	}

	/**删除流程**/
	@Transactional(readOnly = false)
	public void delete(String id) {
		Flowapply apply=this.get(id);
		LeipiRun leipiRun = leipiRunService.get(apply.getProcessInstanceId());
		LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
		leipiRunProcess.setRunFlow(leipiRun.getId());
		leipiRunProcess.setIsDel(0);
		List<LeipiRunProcess>  leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
		//删除leipiRunProcess
		for (LeipiRunProcess lp:leipiRunProcessList) {
			leipiRunProcessService.delete(lp);
		}
		//删除leipiRun
		leipiRunService.delete(leipiRun);
		//删除Flowapply
		this.delete(apply);
	}

	/**撤销流程**/
	@Transactional(readOnly = false)
	public void unDoFlowApply(String id) {
		Flowapply apply=this.get(id);
		LeipiRun leipiRun = leipiRunService.get(apply.getProcessInstanceId());

		//删除此步骤相关人员的任务状态
		LeipiRunProcess tempLeipiRunProcess1=new LeipiRunProcess();
		tempLeipiRunProcess1.setRunFlow(leipiRun.getId());
		tempLeipiRunProcess1.setStatus(0);
		tempLeipiRunProcess1.setIsDel(0);
		List<LeipiRunProcess> leipiRunProcessList1=leipiRunProcessService.findList(tempLeipiRunProcess1);
		for (LeipiRunProcess leipiRunProcess2 : leipiRunProcessList1) {
			leipiRunProcess2.setIsDel(1);
			leipiRunProcessService.save(leipiRunProcess2);
		}
		//结束流程（用户撤销）
		leipiRun.setStatus(2);
		leipiRunService.save(leipiRun);
	}

    public List<Flowapply> findListSpecial(Flowapply flowapply) {
        return flowapplyDao.findListSpecial(flowapply);
    }

    public Page<Flowapply> findPageSpecial(Page<Flowapply> page, Flowapply flowapply) {
        flowapply.setPage(page);
        page.setList(flowapplyDao.findListSpecial(flowapply));
        return page;
    }
}