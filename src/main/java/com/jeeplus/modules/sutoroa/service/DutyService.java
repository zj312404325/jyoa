/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sutoroa.entity.Duty;
import com.jeeplus.modules.sutoroa.dao.DutyDao;

/**
 * 值班表Service
 * @author cqj
 * @version 2018-02-24
 */
@Service
@Transactional(readOnly = true)
public class DutyService extends CrudService<DutyDao, Duty> {

	public Duty get(String id) {
		return super.get(id);
	}
	
	public List<Duty> findList(Duty duty) {
		return super.findList(duty);
	}
	
	public Page<Duty> findPage(Page<Duty> page, Duty duty) {
		return super.findPage(page, duty);
	}
	
	@Transactional(readOnly = false)
	public void save(Duty duty) {
		super.save(duty);
	}
	
	@Transactional(readOnly = false)
	public void delete(Duty duty) {
		super.delete(duty);
	}
	
	
	
	
}