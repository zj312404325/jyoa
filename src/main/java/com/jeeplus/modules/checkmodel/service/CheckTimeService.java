/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.checkmodel.entity.CheckTime;
import com.jeeplus.modules.checkmodel.dao.CheckTimeDao;

/**
 * 考核时间Service
 * @author cqj
 * @version 2017-10-23
 */
@Service
@Transactional(readOnly = true)
public class CheckTimeService extends CrudService<CheckTimeDao, CheckTime> {

	public CheckTime get(String id) {
		return super.get(id);
	}
	
	public List<CheckTime> findList(CheckTime checkTime) {
		return super.findList(checkTime);
	}
	
	public Page<CheckTime> findPage(Page<CheckTime> page, CheckTime checkTime) {
		return super.findPage(page, checkTime);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckTime checkTime) {
		super.save(checkTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckTime checkTime) {
		super.delete(checkTime);
	}
	
	
	
	
}