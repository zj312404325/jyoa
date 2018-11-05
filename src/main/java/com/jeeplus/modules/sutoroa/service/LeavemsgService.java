/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sutoroa.entity.Leavemsg;
import com.jeeplus.modules.sutoroa.dao.LeavemsgDao;

/**
 * 晨会留言Service
 * @author cqj
 * @version 2018-02-23
 */
@Service
@Transactional(readOnly = true)
public class LeavemsgService extends CrudService<LeavemsgDao, Leavemsg> {

	public Leavemsg get(String id) {
		return super.get(id);
	}
	
	public List<Leavemsg> findList(Leavemsg leavemsg) {
		return super.findList(leavemsg);
	}
	
	public Page<Leavemsg> findPage(Page<Leavemsg> page, Leavemsg leavemsg) {
		return super.findPage(page, leavemsg);
	}
	
	@Transactional(readOnly = false)
	public void save(Leavemsg leavemsg) {
		super.save(leavemsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(Leavemsg leavemsg) {
		super.delete(leavemsg);
	}
	
	
	
	
}