/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.ehr.entity.Leaveaudit;
import com.jeeplus.modules.ehr.dao.LeaveauditDao;

/**
 * 离职审计Service
 * @author cqj
 * @version 2018-03-12
 */
@Service
@Transactional(readOnly = true)
public class LeaveauditService extends CrudService<LeaveauditDao, Leaveaudit> {

	public Leaveaudit get(String id) {
		return super.get(id);
	}
	
	public List<Leaveaudit> findList(Leaveaudit leaveaudit) {
		return super.findList(leaveaudit);
	}
	
	public Page<Leaveaudit> findPage(Page<Leaveaudit> page, Leaveaudit leaveaudit) {
		return super.findPage(page, leaveaudit);
	}
	
	@Transactional(readOnly = false)
	public void save(Leaveaudit leaveaudit) {
		super.save(leaveaudit);
	}
	
	@Transactional(readOnly = false)
	public void delete(Leaveaudit leaveaudit) {
		super.delete(leaveaudit);
	}
	
	
	
	
}