/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sutoroa.entity.Hygiene;
import com.jeeplus.modules.sutoroa.dao.HygieneDao;

/**
 * 8s检查Service
 * @author cqj
 * @version 2018-03-02
 */
@Service
@Transactional(readOnly = true)
public class HygieneService extends CrudService<HygieneDao, Hygiene> {

	public Hygiene get(String id) {
		return super.get(id);
	}
	
	public List<Hygiene> findList(Hygiene hygiene) {
		return super.findList(hygiene);
	}
	
	public Page<Hygiene> findPage(Page<Hygiene> page, Hygiene hygiene) {
		return super.findPage(page, hygiene);
	}
	
	@Transactional(readOnly = false)
	public void save(Hygiene hygiene) {
		super.save(hygiene);
	}
	
	@Transactional(readOnly = false)
	public void delete(Hygiene hygiene) {
		super.delete(hygiene);
	}
	
	
	
	
}