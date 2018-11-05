/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.service;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.BaseConst;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.flow.dao.FlowapplyDao;
import com.jeeplus.modules.flow.dao.TemplatecontentDao;
import com.jeeplus.modules.flow.entity.Flowagent;
import com.jeeplus.modules.flow.entity.Flowapply;
import com.jeeplus.modules.flow.entity.Templatecontent;
import com.jeeplus.modules.flow.service.FlowagentService;
import com.jeeplus.modules.leipiflow.dao.LeipiFlowDao;
import com.jeeplus.modules.leipiflow.dao.TemplateDetailDao;
import com.jeeplus.modules.leipiflow.entity.*;
import com.jeeplus.modules.sys.dao.OfficeDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 雷劈流程模块Service
 * @author 陈钱江
 * @version 2017-09-05
 */
@Service
@Transactional(readOnly = true)
public class LeipiFlowService extends CrudService<LeipiFlowDao, LeipiFlow> {

	@Autowired
	private FlowapplyDao flowapplyDao;
	
	@Autowired
	private LeipiFlowDao leipiFlowDao;
	
	@Autowired
	private TemplatecontentDao templatecontentDao;

	@Autowired
	private TemplateDetailDao templateDetailDao;
	
	@Autowired
	private LeipiFlowProcessService leipiFlowProcessService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private LeipiRunService leipiRunService;
	
	@Autowired
	private LeipiRunProcessService leipiRunProcessService;
	
	@Autowired
	private FlowagentService flowagentService;

	@Autowired
	private OfficeDao officeDao;
	
	public LeipiFlow get(String id) {
		return super.get(id);
	}
	
	public List<LeipiFlow> findList(LeipiFlow leipiFlow) {
		return super.findList(leipiFlow);
	}
	
	public Page<LeipiFlow> findPage(Page<LeipiFlow> page, LeipiFlow leipiFlow) {
		return super.findPage(page, leipiFlow);
	}
	
	public Page<LeipiFlow> findUsablePage(Page<LeipiFlow> page, LeipiFlow leipiFlow) {
		leipiFlow.setPage(page);
		page.setList(leipiFlowDao.findUseableList(leipiFlow));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(LeipiFlow leipiFlow) {
		super.save(leipiFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeipiFlow leipiFlow) {
		super.delete(leipiFlow);
	}
	
	/**
	 * 启动流程
	 * @param userIds2 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public List<String> save(Flowapply flowapply,String flowid, String userIds2) {
		
		List<String> sendUidList = new ArrayList<String>();
		
		LeipiFlow leipiFlow=this.get(flowid);
		List<LeipiFlowProcess> leipiFlowProcess = leipiFlowProcessService.findList(new LeipiFlow(flowid));
		LeipiFlowProcess processFirst = null;
		for (LeipiFlowProcess leipiFlowProcess2 : leipiFlowProcess) {
			if("is_one".equals(leipiFlowProcess2.getProcessType())){
				processFirst = leipiFlowProcess2;
				break;
			}
		}
		
		String userIds=userIds2;
//		switch (processFirst.getAutoPerson()) {
//		case 0:
//
//			break;
//		case 1:
//
//			break;
//		case 2:
//			 User loginUser = UserUtils.getUser();
//			List<User> userlist = systemService.findUserByDeptOfficeRole(loginUser.getOfficeTrueId(),null,null, BaseConst.DEPT_MANAGER_ID);
//			if(userlist.size() > 0){
//				for (User user : userlist) {
//					userIds+=user.getId()+",";
//				}
//				if(FormatUtil.isNoEmpty(userIds)){
//					userIds = userIds.substring(0, userIds.length()-1);
//				}
//			}
//			break;
//		case 3:
//			User loginUser2 = UserUtils.getUser();
//			List<User> userlist2 = systemService.findUserByOfficeRole(loginUser2.getOffice().getId(),null, BaseConst.DEPT_HEADER_ID);
//			if(userlist2.size() > 0){
//				for (User user : userlist2) {
//					userIds+=user.getId()+",";
//				}
//				if(FormatUtil.isNoEmpty(userIds)){
//					userIds = userIds.substring(0, userIds.length()-1);
//				}
//			}
//			break;
//		case 4:
//			userIds = processFirst.getAutoSponsorIds();
//			break;
//		case 5:
//			String roids = processFirst.getAutoRoleIds();
//			List<User> listuser = systemService.findUserByOfficeRole(null,null, roids);
//			if(listuser.size() > 0){
//				for (User user : listuser) {
//					userIds+=user.getId()+",";
//				}
//				if(FormatUtil.isNoEmpty(userIds)){
//					userIds = userIds.substring(0, userIds.length()-1);
//				}
//			}
//			break;
//		case 7://执行时替换
//			userIds=userIds2;
//			break;
//		default:
//			break;
//		}
		
		LeipiRun leipiRun = new LeipiRun();
		LeipiRunProcess leipiRunProcess = null;
		
		leipiRun.setFlowId(leipiFlow.getId());
		leipiRun.setCatId(leipiFlow.getFlowtype());
		leipiRun.setRunName(leipiFlow.getFlowname());
		leipiRun.setIsdel(0);
		leipiRun.setStatus(0);
		leipiRun.setUpdatetime(new Date());
		leipiRun.setDateline(new Date());
		leipiRun.setRunFlowProcess(processFirst.getId());
		leipiRun.setRunFlowId(leipiFlow.getId());
		leipiRunService.save(leipiRun);
		
		if(FormatUtil.isNoEmpty(userIds)){
			for (String uid : userIds.split(",")) {
				leipiRunProcess = new LeipiRunProcess();
				leipiRunProcess.setUpid(uid);
				leipiRunProcess.setRunFlow(leipiRun.getId());
				leipiRunProcess.setRunFlowProcess(processFirst.getId());
				leipiRunProcess.setRunId(leipiRun.getFlowId());
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
				leipiRunProcessService.save(leipiRunProcess);
				sendUidList.add(uid);
				
				Flowagent flowagent = flowagentService.getFlowAgent(uid);
				if(FormatUtil.isNoEmpty(flowagent)){
					leipiRunProcess = new LeipiRunProcess();
					leipiRunProcess.setUpid(flowagent.getAgentuserid());//代理iD
					leipiRunProcess.setRunFlow(leipiRun.getId());
					leipiRunProcess.setRunFlowProcess(processFirst.getId());
					leipiRunProcess.setRunId(leipiRun.getFlowId());
					leipiRunProcess.setIsReceiveType(0);
					leipiRunProcess.setIsSponsor(0);
					leipiRunProcess.setIsSingpost(0);
					leipiRunProcess.setIsBack(0);
					leipiRunProcess.setStatus(0);
					leipiRunProcess.setJsTime(new Date());
					leipiRunProcess.setUpdatetime(new Date());
					leipiRunProcess.setIsDel(0);
					leipiRunProcess.setDateline(new Date());
					leipiRunProcess.setAgentedid(uid);//被代理的用户iD
					leipiRunProcess.setIsOpen(0);
					leipiRunProcessService.save(leipiRunProcess);
					sendUidList.add(flowagent.getAgentuserid());
				}
				
			}
		}
		
		// 保存业务数据
		if (StringUtils.isBlank(flowapply.getId())){
			flowapply.preInsert();
			flowapply.setProcessInstanceId(leipiRun.getId());
			flowapplyDao.insert(flowapply);
			if(FormatUtil.isNoEmpty(flowapply.getTemplatecontentList()) && flowapply.getTemplatecontentList().size() > 0){
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
			if(FormatUtil.isNoEmpty(flowapply.getTemplatedetailList()) && flowapply.getTemplatedetailList().size() > 0){
				for(TemplateDetail templateDetail : flowapply.getTemplatedetailList()){
					if (Templatecontent.DEL_FLAG_NORMAL.equals(templateDetail.getDelFlag())){
						if (StringUtils.isBlank(templateDetail.getId())){
							templateDetail.setFlowapplyid(flowapply.getId());
							templateDetail.setLeipiflowid(flowapply.getFlowid());
							templateDetail.preInsert();
							templateDetailDao.insert(templateDetail);
						}else{
							templateDetail.preUpdate();
							templateDetailDao.update(templateDetail);
						}
					}else{
						templateDetailDao.delete(templateDetail);
					}
				}
			}

		}else{
			flowapply.preUpdate();
			flowapplyDao.update(flowapply);
			if(FormatUtil.isNoEmpty(flowapply.getTemplatecontentList()) && flowapply.getTemplatecontentList().size() > 0){
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

		}
		
		
		return sendUidList;
	}
	
	/**
	 * 任务处理
	 * @param userIds 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public List<String> saveAudit(Flowapply flowapply,String flag, String userIds) {
		List<String> sendUidList = new ArrayList<String>();
		if("yes".equals(flag)){//同意
			//查找流程实例
			LeipiRun leipiRun=leipiRunService.get(flowapply.getProcessInstanceId());
			//查找当前流程步骤
			LeipiRunProcess tempLeipiRunProcess=new LeipiRunProcess();
			tempLeipiRunProcess.setRunFlow(leipiRun.getId());
			tempLeipiRunProcess.setStatus(0);
			tempLeipiRunProcess.setIsDel(0);
			tempLeipiRunProcess.setUpid(UserUtils.getUser().getId());
			List<LeipiRunProcess> leipiRunProcessList=leipiRunProcessService.findList(tempLeipiRunProcess);
			LeipiRunProcess leipiRunProcess=leipiRunProcessList.get(0);
			
			//设置意见
			leipiRunProcess.setRemark(flowapply.getAct().getComment());
			LeipiFlowProcess leipiFlowProcess=leipiFlowProcessService.get(leipiRunProcess.getRunFlowProcess());
			if(leipiFlowProcess.getParallel()==0){//非并行步骤
				leipiRunProcess.setStatus(1);
				leipiRunProcess.setBlTime(new Date());
				//保存
				leipiRunProcessService.save(leipiRunProcess);
				//判断当前用户处理的环节是否是被添加的
				if(!FormatUtil.isNoEmpty(leipiRunProcess.getAddprocessDate())){
					//删除此步骤相关人员的任务状态
					LeipiRunProcess tempLeipiRunProcess1=new LeipiRunProcess();
					tempLeipiRunProcess1.setRunFlow(leipiRun.getId());
					tempLeipiRunProcess1.setStatus(0);
					tempLeipiRunProcess1.setIsDel(0);
					List<LeipiRunProcess> leipiRunProcessList1=leipiRunProcessService.findList(tempLeipiRunProcess1);
					for (LeipiRunProcess leipiRunProcess2 : leipiRunProcessList1) {
						if(FormatUtil.isNoEmpty(leipiRunProcess.getAgentedid())&&leipiRunProcess2.getUpid()==leipiRunProcess.getAgentedid()){//当前用户是代理人，需处理被代理人
							//删除添加的代理人的任务
							if(UserUtils.getUser().getId().equals(leipiRunProcess2.getAgentedid())){
								leipiRunProcess2.setStatus(1);
							}
						}else{
							if(!FormatUtil.isNoEmpty(leipiRunProcess2.getAddprocessDate())){
								leipiRunProcess2.setIsDel(1);
							}
						}
						leipiRunProcessService.save(leipiRunProcess2);
						//发送信息
					}
					//查询是否有下一步步骤
					String ProcessTo=leipiFlowProcess.getProcessTo();
					if(FormatUtil.isNoEmpty(ProcessTo)){//存在下一步步骤
						sendUidList=this.saveUserProcess(sendUidList, ProcessTo, leipiRun,userIds);
					}else{//流程结束
						leipiRun.setStatus(1);
						leipiRunService.save(leipiRun);
					}
				}
			}else{//并行步骤
				leipiRunProcess.setStatus(1);
				leipiRunProcess.setBlTime(new Date());
				//保存
				leipiRunProcessService.save(leipiRunProcess);
				if(FormatUtil.isNoEmpty(leipiRunProcess.getAgentedid())){//当前用户是代理人，需处理被代理人
					//查找被代理人将其状态设为同意
					LeipiRunProcess tempLeipiRunProcess1=new LeipiRunProcess();
					tempLeipiRunProcess1.setRunFlow(leipiRun.getId());
					tempLeipiRunProcess1.setStatus(0);
					tempLeipiRunProcess1.setIsDel(0);
					tempLeipiRunProcess1.setUpid(leipiRunProcess.getAgentedid());
					List<LeipiRunProcess> leipiRunProcessList1=leipiRunProcessService.findList(tempLeipiRunProcess1);
					LeipiRunProcess leipiRunProcess1=leipiRunProcessList1.get(0);
					leipiRunProcess1.setStatus(1);
					leipiRunProcessService.save(leipiRunProcess1);
				}else{//被代理人
					//查找被代理人将其任务删除
					LeipiRunProcess tempLeipiRunProcess1=new LeipiRunProcess();
					tempLeipiRunProcess1.setRunFlow(leipiRun.getId());
					tempLeipiRunProcess1.setStatus(0);
					tempLeipiRunProcess1.setIsDel(0);
					tempLeipiRunProcess1.setAgentedid(UserUtils.getUser().getId());
					List<LeipiRunProcess> leipiRunProcessList1=leipiRunProcessService.findList(tempLeipiRunProcess1);
					if(FormatUtil.isNoEmpty(leipiRunProcessList1)&&leipiRunProcessList1.size()>0){
						LeipiRunProcess leipiRunProcess1=leipiRunProcessList1.get(0);
						leipiRunProcess1.setIsDel(1);
						leipiRunProcessService.save(leipiRunProcess1);
					}
				}
				
				//此步骤是否所有用户都处理了
				LeipiRunProcess tempLeipiRunProcess2=new LeipiRunProcess();
				tempLeipiRunProcess2.setRunFlow(leipiRun.getId());
				tempLeipiRunProcess2.setStatus(0);
				tempLeipiRunProcess2.setIsDel(0);
				tempLeipiRunProcess2.setAddprocessFlag("1");//将中间步骤添加的未处理的排除出去
				tempLeipiRunProcess2.setAgentedidFlag("1");//将代理的步骤排除出去
				List<LeipiRunProcess> leipiRunProcessList2=leipiRunProcessService.findList(tempLeipiRunProcess2);
				if(!FormatUtil.isNoEmpty(leipiRunProcessList2)||leipiRunProcessList2.size()==0){//所有用户都处理了
					//查询是否有下一步步骤
					String ProcessTo=leipiFlowProcess.getProcessTo();
					if(FormatUtil.isNoEmpty(ProcessTo)){//存在下一步步骤
						sendUidList=this.saveUserProcess(sendUidList, ProcessTo, leipiRun,userIds);
					}else{//流程结束
						leipiRun.setStatus(1);
						leipiRunService.save(leipiRun);
					}
				}
			}
			
		}else{//不同意
			//查找流程实例
			LeipiRun leipiRun=leipiRunService.get(flowapply.getProcessInstanceId());
			//查找当前流程步骤
			LeipiRunProcess tempLeipiRunProcess=new LeipiRunProcess();
			tempLeipiRunProcess.setRunFlow(leipiRun.getId());
			tempLeipiRunProcess.setStatus(0);
			tempLeipiRunProcess.setIsDel(0);
			tempLeipiRunProcess.setUpid(UserUtils.getUser().getId());
			List<LeipiRunProcess> leipiRunProcessList=leipiRunProcessService.findList(tempLeipiRunProcess);
			LeipiRunProcess leipiRunProcess=leipiRunProcessList.get(0);
			
			//设置意见
			leipiRunProcess.setRemark(flowapply.getAct().getComment());
			LeipiFlowProcess leipiFlowProcess=leipiFlowProcessService.get(leipiRunProcess.getRunFlowProcess());
			
			leipiRunProcess.setStatus(2);
			leipiRunProcess.setBlTime(new Date());
			//保存
			leipiRunProcessService.save(leipiRunProcess);
			//判断当前用户处理的环节是否是被添加的
			if(!FormatUtil.isNoEmpty(leipiRunProcess.getAddprocessDate())){
				//删除此步骤相关人员的任务状态
				LeipiRunProcess tempLeipiRunProcess1=new LeipiRunProcess();
				tempLeipiRunProcess1.setRunFlow(leipiRun.getId());
				tempLeipiRunProcess1.setStatus(0);
				tempLeipiRunProcess1.setIsDel(0);
				List<LeipiRunProcess> leipiRunProcessList1=leipiRunProcessService.findList(tempLeipiRunProcess1);
				for (LeipiRunProcess leipiRunProcess2 : leipiRunProcessList1) {
					if(FormatUtil.isNoEmpty(leipiRunProcess.getAgentedid())&&leipiRunProcess2.getUpid()==leipiRunProcess.getAgentedid()){//当前用户是代理人，需处理被代理人
						//被代理人的任务不删
						leipiRunProcess.setStatus(2);
					}else{
						if(!FormatUtil.isNoEmpty(leipiRunProcess2.getAddprocessDate())){
							leipiRunProcess2.setIsDel(1);
						}
					}
					leipiRunProcessService.save(leipiRunProcess2);
					//发送信息
				}

				//结束流程
				leipiRun.setStatus(2);
				leipiRunService.save(leipiRun);
			}
		}
		return sendUidList;
	}

	@Transactional(readOnly = false)
	public List<String> saveAuditByApp(Flowapply flowapply,String flag, String userIds, User loginUser) {
		List<String> sendUidList = new ArrayList<String>();
		if("yes".equals(flag)){//同意
			//查找流程实例
			LeipiRun leipiRun=leipiRunService.get(flowapply.getProcessInstanceId());
			//查找当前流程步骤
			LeipiRunProcess tempLeipiRunProcess=new LeipiRunProcess();
			tempLeipiRunProcess.setRunFlow(leipiRun.getId());
			tempLeipiRunProcess.setStatus(0);
			tempLeipiRunProcess.setIsDel(0);
			tempLeipiRunProcess.setUpid(loginUser.getId());
			List<LeipiRunProcess> leipiRunProcessList=leipiRunProcessService.findList(tempLeipiRunProcess);
			LeipiRunProcess leipiRunProcess=leipiRunProcessList.get(0);

			//设置意见
			leipiRunProcess.setRemark(flowapply.getAct().getComment());
			LeipiFlowProcess leipiFlowProcess=leipiFlowProcessService.get(leipiRunProcess.getRunFlowProcess());
			if(leipiFlowProcess.getParallel()==0){//非并行步骤
				leipiRunProcess.setStatus(1);
				leipiRunProcess.setBlTime(new Date());
				//保存
				leipiRunProcessService.save(leipiRunProcess);
				//判断当前用户处理的环节是否是被添加的
				if(!FormatUtil.isNoEmpty(leipiRunProcess.getAddprocessDate())){
					//删除此步骤相关人员的任务状态
					LeipiRunProcess tempLeipiRunProcess1=new LeipiRunProcess();
					tempLeipiRunProcess1.setRunFlow(leipiRun.getId());
					tempLeipiRunProcess1.setStatus(0);
					tempLeipiRunProcess1.setIsDel(0);
					List<LeipiRunProcess> leipiRunProcessList1=leipiRunProcessService.findList(tempLeipiRunProcess1);
					for (LeipiRunProcess leipiRunProcess2 : leipiRunProcessList1) {
						if(FormatUtil.isNoEmpty(leipiRunProcess.getAgentedid())&&leipiRunProcess2.getUpid()==leipiRunProcess.getAgentedid()){//当前用户是代理人，需处理被代理人
							//删除添加的代理人的任务
							if(loginUser.getId().equals(leipiRunProcess2.getAgentedid())){
								leipiRunProcess2.setStatus(1);
							}
						}else{
							if(!FormatUtil.isNoEmpty(leipiRunProcess2.getAddprocessDate())){
								leipiRunProcess2.setIsDel(1);
							}
						}
						leipiRunProcessService.save(leipiRunProcess2);
						//发送信息
					}
					//查询是否有下一步步骤
					String ProcessTo=leipiFlowProcess.getProcessTo();
					if(FormatUtil.isNoEmpty(ProcessTo)){//存在下一步步骤
						sendUidList=this.saveUserProcess(sendUidList, ProcessTo, leipiRun,userIds);
					}else{//流程结束
						leipiRun.setStatus(1);
						leipiRunService.save(leipiRun);
					}
				}
			}else{//并行步骤
				leipiRunProcess.setStatus(1);
				leipiRunProcess.setBlTime(new Date());
				//保存
				leipiRunProcessService.save(leipiRunProcess);
				if(FormatUtil.isNoEmpty(leipiRunProcess.getAgentedid())){//当前用户是代理人，需处理被代理人
					//查找被代理人将其状态设为同意
					LeipiRunProcess tempLeipiRunProcess1=new LeipiRunProcess();
					tempLeipiRunProcess1.setRunFlow(leipiRun.getId());
					tempLeipiRunProcess1.setStatus(0);
					tempLeipiRunProcess1.setIsDel(0);
					tempLeipiRunProcess1.setUpid(leipiRunProcess.getAgentedid());
					List<LeipiRunProcess> leipiRunProcessList1=leipiRunProcessService.findList(tempLeipiRunProcess1);
					LeipiRunProcess leipiRunProcess1=leipiRunProcessList1.get(0);
					leipiRunProcess1.setStatus(1);
					leipiRunProcessService.save(leipiRunProcess1);
				}else{//被代理人
					//查找被代理人将其任务删除
					LeipiRunProcess tempLeipiRunProcess1=new LeipiRunProcess();
					tempLeipiRunProcess1.setRunFlow(leipiRun.getId());
					tempLeipiRunProcess1.setStatus(0);
					tempLeipiRunProcess1.setIsDel(0);
					tempLeipiRunProcess1.setAgentedid(loginUser.getId());
					List<LeipiRunProcess> leipiRunProcessList1=leipiRunProcessService.findList(tempLeipiRunProcess1);
					if(FormatUtil.isNoEmpty(leipiRunProcessList1)&&leipiRunProcessList1.size()>0){
						LeipiRunProcess leipiRunProcess1=leipiRunProcessList1.get(0);
						leipiRunProcess1.setIsDel(1);
						leipiRunProcessService.save(leipiRunProcess1);
					}
				}

				//此步骤是否所有用户都处理了
				LeipiRunProcess tempLeipiRunProcess2=new LeipiRunProcess();
				tempLeipiRunProcess2.setRunFlow(leipiRun.getId());
				tempLeipiRunProcess2.setStatus(0);
				tempLeipiRunProcess2.setIsDel(0);
				tempLeipiRunProcess2.setAddprocessFlag("1");//将中间步骤添加的未处理的排除出去
				tempLeipiRunProcess2.setAgentedidFlag("1");//将代理的步骤排除出去
				List<LeipiRunProcess> leipiRunProcessList2=leipiRunProcessService.findList(tempLeipiRunProcess2);
				if(!FormatUtil.isNoEmpty(leipiRunProcessList2)||leipiRunProcessList2.size()==0){//所有用户都处理了
					//查询是否有下一步步骤
					String ProcessTo=leipiFlowProcess.getProcessTo();
					if(FormatUtil.isNoEmpty(ProcessTo)){//存在下一步步骤
						sendUidList=this.saveUserProcess(sendUidList, ProcessTo, leipiRun,userIds);
					}else{//流程结束
						leipiRun.setStatus(1);
						leipiRunService.save(leipiRun);
					}
				}
			}

		}else{//不同意
			//查找流程实例
			LeipiRun leipiRun=leipiRunService.get(flowapply.getProcessInstanceId());
			//查找当前流程步骤
			LeipiRunProcess tempLeipiRunProcess=new LeipiRunProcess();
			tempLeipiRunProcess.setRunFlow(leipiRun.getId());
			tempLeipiRunProcess.setStatus(0);
			tempLeipiRunProcess.setIsDel(0);
			tempLeipiRunProcess.setUpid(loginUser.getId());
			List<LeipiRunProcess> leipiRunProcessList=leipiRunProcessService.findList(tempLeipiRunProcess);
			LeipiRunProcess leipiRunProcess=leipiRunProcessList.get(0);

			//设置意见
			leipiRunProcess.setRemark(flowapply.getAct().getComment());
			LeipiFlowProcess leipiFlowProcess=leipiFlowProcessService.get(leipiRunProcess.getRunFlowProcess());

			leipiRunProcess.setStatus(2);
			leipiRunProcess.setBlTime(new Date());
			//保存
			leipiRunProcessService.save(leipiRunProcess);
			//判断当前用户处理的环节是否是被添加的
			if(!FormatUtil.isNoEmpty(leipiRunProcess.getAddprocessDate())){
				//删除此步骤相关人员的任务状态
				LeipiRunProcess tempLeipiRunProcess1=new LeipiRunProcess();
				tempLeipiRunProcess1.setRunFlow(leipiRun.getId());
				tempLeipiRunProcess1.setStatus(0);
				tempLeipiRunProcess1.setIsDel(0);
				List<LeipiRunProcess> leipiRunProcessList1=leipiRunProcessService.findList(tempLeipiRunProcess1);
				for (LeipiRunProcess leipiRunProcess2 : leipiRunProcessList1) {
					if(FormatUtil.isNoEmpty(leipiRunProcess.getAgentedid())&&leipiRunProcess2.getUpid()==leipiRunProcess.getAgentedid()){//当前用户是代理人，需处理被代理人
						//被代理人的任务不删
						leipiRunProcess.setStatus(2);
					}else{
						if(!FormatUtil.isNoEmpty(leipiRunProcess2.getAddprocessDate())){
							leipiRunProcess2.setIsDel(1);
						}
					}
					leipiRunProcessService.save(leipiRunProcess2);
					//发送信息
				}

				//结束流程
				leipiRun.setStatus(2);
				leipiRunService.save(leipiRun);
			}
		}
		return sendUidList;
	}
	
	/**
	 * 
	 * @param sendUidList
	 * @param userIds2 
	 * @param 为此步骤的下一步骤用户添加任务
	 * @return
	 */
	public List<String> saveUserProcess(List<String> sendUidList,String ProcessTo,LeipiRun leipiRun, String userIds2){
		//获得下一步步骤
		LeipiFlowProcess nextLeipiFlowProcess=leipiFlowProcessService.get(ProcessTo);
		leipiRun.setRunFlowProcess(nextLeipiFlowProcess.getId());
		leipiRun.setRunFlowId(nextLeipiFlowProcess.getId());
		leipiRunService.save(leipiRun);

		String userIds = userIds2;
//		//创建对应用户的步骤实例
//		String userIds="";
//		switch (nextLeipiFlowProcess.getAutoPerson()) {
//		case 0:
//
//			break;
//		case 1:
//
//			break;
//		case 2:
//			 User loginUser = UserUtils.getUser();
//			List<User> userlist = systemService.findUserByDeptOfficeRole(loginUser.getOfficeTrueId(),null,null, BaseConst.DEPT_MANAGER_ID);
//			if(userlist.size() > 0){
//				for (User user : userlist) {
//					userIds+=user.getId()+",";
//				}
//				if(FormatUtil.isNoEmpty(userIds)){
//					userIds = userIds.substring(0, userIds.length()-1);
//				}
//			}
//			break;
//		case 3:
//			User loginUser2 = UserUtils.getUser();
//			List<User> userlist2 = systemService.findUserByOfficeRole(loginUser2.getOffice().getId(),null, BaseConst.DEPT_HEADER_ID);
//			if(userlist2.size() > 0){
//				for (User user : userlist2) {
//					userIds+=user.getId()+",";
//				}
//				if(FormatUtil.isNoEmpty(userIds)){
//					userIds = userIds.substring(0, userIds.length()-1);
//				}
//			}
//			break;
//		case 4:
//			userIds = nextLeipiFlowProcess.getAutoSponsorIds();
//			break;
//		case 5:
//			String roids = nextLeipiFlowProcess.getAutoRoleIds();
//			List<User> listuser = systemService.findUserByOfficeRole(null,null, roids);
//			if(listuser.size() > 0){
//				for (User user : listuser) {
//					userIds+=user.getId()+",";
//				}
//				if(FormatUtil.isNoEmpty(userIds)){
//					userIds = userIds.substring(0, userIds.length()-1);
//				}
//			}
//			break;
//		case 7:
//			userIds=userIds2;
//			break;
//		default:
//			break;
//		}
		
		if(FormatUtil.isNoEmpty(userIds)){
			LeipiRunProcess leipiRunProcess=null;
			for (String uid : userIds.split(",")) {
				leipiRunProcess = new LeipiRunProcess();
				leipiRunProcess.setUpid(uid);
				leipiRunProcess.setRunFlow(leipiRun.getId());
				leipiRunProcess.setRunFlowProcess(nextLeipiFlowProcess.getId());
				leipiRunProcess.setRunId(leipiRun.getFlowId());
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
				leipiRunProcessService.save(leipiRunProcess);
				sendUidList.add(uid);
				
				Flowagent flowagent = flowagentService.getFlowAgent(uid);
				if(FormatUtil.isNoEmpty(flowagent)){
					leipiRunProcess = new LeipiRunProcess();
					leipiRunProcess.setUpid(flowagent.getAgentuserid());//代理iD
					leipiRunProcess.setRunFlow(leipiRun.getId());
					leipiRunProcess.setRunFlowProcess(nextLeipiFlowProcess.getId());
					leipiRunProcess.setRunId(leipiRun.getFlowId());
					leipiRunProcess.setIsReceiveType(0);
					leipiRunProcess.setIsSponsor(0);
					leipiRunProcess.setIsSingpost(0);
					leipiRunProcess.setIsBack(0);
					leipiRunProcess.setStatus(0);
					leipiRunProcess.setJsTime(new Date());
					leipiRunProcess.setUpdatetime(new Date());
					leipiRunProcess.setIsDel(0);
					leipiRunProcess.setDateline(new Date());
					leipiRunProcess.setAgentedid(uid);//被代理的用户iD
					leipiRunProcess.setIsOpen(0);
					leipiRunProcessService.save(leipiRunProcess);
					sendUidList.add(flowagent.getAgentuserid());
				}
			}
		}
		return sendUidList;
	}

	public String getNextProcessUserids(String ProcessTo){
		//获得下一步步骤
		LeipiFlowProcess nextLeipiFlowProcess=leipiFlowProcessService.get(ProcessTo);
		//创建对应用户的步骤实例
		String userIds="";
		switch (nextLeipiFlowProcess.getAutoPerson()) {
			case 0:

				break;
			case 1:
				//外面已处理
				break;
			case 2:
//				User loginUser = UserUtils.getUser();
//				List<User> userlist = systemService.findUserByDeptOfficeRole(loginUser.getOfficeTrueId(),null,null, BaseConst.DEPT_MANAGER_ID);
//				if(userlist.size() > 0){
//					for (User user : userlist) {
//						userIds+=user.getId()+",";
//					}
//					if(FormatUtil.isNoEmpty(userIds)){
//						userIds = userIds.substring(0, userIds.length()-1);
//					}
//				}
				User loginUser = UserUtils.getUser();
				Office  o = officeDao.get(loginUser.getOffice().getId());
				if(FormatUtil.isNoEmpty(o)){
					userIds = FormatUtil.toString(o.getPrimaryPerson());
				}
				break;
			case 3:
//				User loginUser2 = UserUtils.getUser();
//				List<User> userlist2 = systemService.findUserByOfficeRole(loginUser2.getOffice().getId(),null, BaseConst.DEPT_HEADER_ID);
//				if(userlist2.size() > 0){
//					for (User user : userlist2) {
//						userIds+=user.getId()+",";
//					}
//					if(FormatUtil.isNoEmpty(userIds)){
//						userIds = userIds.substring(0, userIds.length()-1);
//					}
//				}
				loginUser = UserUtils.getUser();
				o = officeDao.get(loginUser.getOffice().getId());
				userIds = FormatUtil.toString(o.getPrimaryPerson());
				break;
			case 4:
				userIds = nextLeipiFlowProcess.getAutoSponsorIds();
				break;
			case 5:
				String roids = nextLeipiFlowProcess.getAutoRoleIds();
				List<User> listuser = systemService.findUserByOfficeRole(null,null, roids);
				if(listuser.size() > 0){
					for (User user : listuser) {
						userIds+=user.getId()+",";
					}
					if(FormatUtil.isNoEmpty(userIds)){
						userIds = userIds.substring(0, userIds.length()-1);
					}
				}
				break;
			case 6:
				loginUser = UserUtils.getUser();
				o = officeDao.get(loginUser.getOffice().getId());
				if(FormatUtil.isNoEmpty(o)){
					userIds = FormatUtil.toString(o.getPrimaryPerson());
				}
				break;
			case 7:
				//外面已处理
				//userIds=userIds2;
				break;
			default:
				break;
		}

		return userIds;
	}

	public String getNextProcessUseridsByApp(String ProcessTo,User loginUser){
		//获得下一步步骤
		LeipiFlowProcess nextLeipiFlowProcess=leipiFlowProcessService.get(ProcessTo);
		//创建对应用户的步骤实例
		String userIds="";
		switch (nextLeipiFlowProcess.getAutoPerson()) {
			case 0:

				break;
			case 1:
				//外面已处理
				break;
			case 2:
//				User loginUser = UserUtils.getUser();
//				List<User> userlist = systemService.findUserByDeptOfficeRole(loginUser.getOfficeTrueId(),null,null, BaseConst.DEPT_MANAGER_ID);
//				if(userlist.size() > 0){
//					for (User user : userlist) {
//						userIds+=user.getId()+",";
//					}
//					if(FormatUtil.isNoEmpty(userIds)){
//						userIds = userIds.substring(0, userIds.length()-1);
//					}
//				}
//				User loginUser = UserUtils.getUser();
				Office  o = officeDao.get(loginUser.getOffice().getId());
				if(FormatUtil.isNoEmpty(o)){
					userIds = FormatUtil.toString(o.getPrimaryPerson());
				}
				break;
			case 3:
//				User loginUser2 = UserUtils.getUser();
//				List<User> userlist2 = systemService.findUserByOfficeRole(loginUser2.getOffice().getId(),null, BaseConst.DEPT_HEADER_ID);
//				if(userlist2.size() > 0){
//					for (User user : userlist2) {
//						userIds+=user.getId()+",";
//					}
//					if(FormatUtil.isNoEmpty(userIds)){
//						userIds = userIds.substring(0, userIds.length()-1);
//					}
//				}
//				loginUser = UserUtils.getUser();
				o = officeDao.get(loginUser.getOffice().getId());
				userIds = FormatUtil.toString(o.getPrimaryPerson());
				break;
			case 4:
				userIds = nextLeipiFlowProcess.getAutoSponsorIds();
				break;
			case 5:
				String roids = nextLeipiFlowProcess.getAutoRoleIds();
				List<User> listuser = systemService.findUserByOfficeRole(null,null, roids);
				if(listuser.size() > 0){
					for (User user : listuser) {
						userIds+=user.getId()+",";
					}
					if(FormatUtil.isNoEmpty(userIds)){
						userIds = userIds.substring(0, userIds.length()-1);
					}
				}
				break;
			case 6:
//				loginUser = UserUtils.getUser();
				o = officeDao.get(loginUser.getOffice().getId());
				if(FormatUtil.isNoEmpty(o)){
					userIds = FormatUtil.toString(o.getPrimaryPerson());
				}
				break;
			case 7:
				//外面已处理
				//userIds=userIds2;
				break;
			default:
				break;
		}

		return userIds;
	}
}