/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.modules.flow.entity.Flowagent;
import com.jeeplus.modules.flow.dao.FlowagentDao;

/**
 * 流程代理Service
 * @author cqj
 * @version 2016-12-16
 */
@Service
@Transactional(readOnly = true)
public class FlowagentService extends CrudService<FlowagentDao, Flowagent> {

	public Flowagent get(String id) {
		return super.get(id);
	}
	
	public List<Flowagent> findList(Flowagent flowagent) {
		return super.findList(flowagent);
	}
	
	public Page<Flowagent> findPage(Page<Flowagent> page, Flowagent flowagent) {
		return super.findPage(page, flowagent);
	}
	
	@Transactional(readOnly = false)
	public void save(Flowagent flowagent) {
		super.save(flowagent);
	}
	
	@Transactional(readOnly = false)
	public void delete(Flowagent flowagent) {
		super.delete(flowagent);
	}
	
	/**
	 * 查找用户是否设置了代理
	 * @param userid
	 * @return
	 */
	public Flowagent getFlowAgent(String userid){
		//查找下一步用户是否设置代理
		Flowagent flowagent=super.findUniqueByProperty("create_by", userid);
		if(FormatUtil.isNoEmpty(flowagent)){//设置了代理
			return flowagent;
		}
		return null;
	}
	
}