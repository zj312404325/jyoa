/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.ehr.entity.LeaveSheet;
import com.jeeplus.modules.ehr.dao.LeaveSheetDao;

/**
 * 离职申请单管理Service
 * @author yc
 * @version 2017-11-02
 */
@Service
@Transactional(readOnly = true)
public class LeaveSheetService extends CrudService<LeaveSheetDao, LeaveSheet> {

	public LeaveSheet get(String id) {
		return super.get(id);
	}
	
	public List<LeaveSheet> findList(LeaveSheet leaveSheet) {
		return super.findList(leaveSheet);
	}
	
	public Page<LeaveSheet> findPage(Page<LeaveSheet> page, LeaveSheet leaveSheet) {
		return super.findPage(page, leaveSheet);
	}
	
	@Transactional(readOnly = false)
	public void save(LeaveSheet leaveSheet) {
		super.save(leaveSheet);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeaveSheet leaveSheet) {
		super.delete(leaveSheet);
	}
	
	
	
	
}