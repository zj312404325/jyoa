/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.modules.leipiflow.entity.LeipiFlow;
import com.jeeplus.modules.leipiflow.entity.LeipiFlowProcess;
import com.jeeplus.modules.leipiflow.dao.LeipiFlowProcessDao;

/**
 * 流程步骤Service
 * @author 陈钱江
 * @version 2017-09-05
 */
@Service
@Transactional(readOnly = true)
public class LeipiFlowProcessService extends CrudService<LeipiFlowProcessDao, LeipiFlowProcess> {

	public LeipiFlowProcess get(String id) {
		return super.get(id);
	}
	
	public List<LeipiFlowProcess> findList(LeipiFlowProcess leipiFlowProcess) {
		return super.findList(leipiFlowProcess);
	}
	
	public List<LeipiFlowProcess> findList(LeipiFlow leipiFlow) {
		return super.findList(new LeipiFlowProcess(leipiFlow));
	}
	
	public Page<LeipiFlowProcess> findPage(Page<LeipiFlowProcess> page, LeipiFlowProcess leipiFlowProcess) {
		return super.findPage(page, leipiFlowProcess);
	}
	
	@Transactional(readOnly = false)
	public void save(LeipiFlowProcess leipiFlowProcess) {
		super.save(leipiFlowProcess);
	}
	
	@Transactional(readOnly = false)
	public void saveAttr(LeipiFlowProcess leipiFlowProcess) {
		if(leipiFlowProcess.getProcessType().equals("is_one")){
			List<LeipiFlowProcess> lstProcess=this.findList(new LeipiFlow(leipiFlowProcess.getFlowId()));
			for (LeipiFlowProcess leipiFlowProcess2 : lstProcess) {
				leipiFlowProcess2.setProcessType("is_step");
				super.save(leipiFlowProcess2);
			}
		}
		super.save(leipiFlowProcess);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeipiFlowProcess leipiFlowProcess){
		//修改其他步骤指向
		List<LeipiFlowProcess> lstProcess=this.findList(new LeipiFlow(leipiFlowProcess.getFlowId()));
		super.delete(leipiFlowProcess);
		for (LeipiFlowProcess proc : lstProcess) {
			String progress=proc.getProcessTo();
			if(FormatUtil.isNoEmpty(progress)){
				String[] progressArray=progress.split(",");
				List list = Arrays.asList(progressArray);
				List arrayList = new ArrayList(list);
				for (int i = 0; i < arrayList.size(); i++) {
					if(arrayList.get(i).equals(leipiFlowProcess.getId())){
						arrayList.remove(i);
					}
				}
				if(arrayList.size()>0){
					String progressto= StringUtils.join(arrayList.toArray(),",");
					proc.setProcessTo(progressto);
					super.save(proc);
				}
			}
		}
	}
	
	
	
	
}