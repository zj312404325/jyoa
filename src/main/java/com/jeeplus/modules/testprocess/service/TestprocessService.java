/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.testprocess.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.act.utils.ActUtils;
import com.jeeplus.modules.oa.dao.LeaveDao;
import com.jeeplus.modules.oa.entity.Leave;
import com.jeeplus.modules.testprocess.entity.Testprocess;
import com.jeeplus.modules.testprocess.dao.TestprocessDao;

/**
 * 流程测试Service
 * @author cqj
 * @version 2016-12-05
 */
@Service
@Transactional(readOnly = true)
public class TestprocessService extends CrudService<TestprocessDao, Testprocess> {

	@Autowired
	private TestprocessDao testprocessDao;
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
	private ActTaskService actTaskService;
	
	public Testprocess get(String id) {
		return super.get(id);
	}
	
	public List<Testprocess> findList(Testprocess testprocess) {
		return super.findList(testprocess);
	}
	
	public Page<Testprocess> findPage(Page<Testprocess> page, Testprocess testprocess) {
		return super.findPage(page, testprocess);
	}
	
	@Transactional(readOnly = false)
	public void save(Testprocess testprocess, Map<String, Object> variables) {
		// 保存业务数据
		if (StringUtils.isBlank(testprocess.getId())){
			testprocess.preInsert();
			testprocessDao.insert(testprocess);
		}else{
			testprocess.preUpdate();
			testprocessDao.update(testprocess);
		}
		logger.debug("save entity: {}", testprocess);
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(testprocess.getCurrentUser().getLoginName());
		
		// 启动流程
		String businessKey = testprocess.getId().toString();
		variables.put("type", "leave");
		variables.put("busId", businessKey);
		variables.put("title", testprocess.getRemarks());//设置标题；
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ActUtils.PD_TESTPROCESS[0], businessKey, variables);
		testprocess.setProcessInstance(processInstance);
		
		// 更新流程实例ID
		testprocess.setProcessInstanceId(processInstance.getId());
		testprocessDao.updateProcessInstanceId(testprocess);
		
		logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[] { 
				ActUtils.PD_TESTPROCESS[0], businessKey, processInstance.getId(), variables });
				
	}
	
	@Transactional(readOnly = false)
	public void delete(Testprocess testprocess) {
		super.delete(testprocess);
	}
	
	
	/* * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void auditSave(Testprocess testprocess) {
		
		// 设置意见
		testprocess.getAct().setComment(("yes".equals(testprocess.getAct().getFlag())?"[同意] ":"[驳回] ")+testprocess.getAct().getComment());
		
		testprocess.preUpdate();
		
//		// 对不同环节的业务逻辑进行操作
//		String taskDefKey = testprocess.getAct().getTaskDefKey();
//       
//		//业务逻辑对应的条件表达式
		String exp = "";
//		// 审核环节
//		if ("leader".equals(taskDefKey)){
//			
//			exp = "leaderPass";
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
//		
//		// 未知环节，直接返回
//		else{
//			return;
//		}
//		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put(exp, "yes".equals(testprocess.getAct().getFlag())? true : false);
		// 提交流程任务
		actTaskService.complete(testprocess.getAct().getTaskId(), testprocess.getAct().getProcInsId(), testprocess.getAct().getComment(), vars);
		
	}
	
}