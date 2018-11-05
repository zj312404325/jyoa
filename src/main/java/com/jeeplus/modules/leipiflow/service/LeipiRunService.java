/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.leipiflow.entity.LeipiRun;
import com.jeeplus.modules.leipiflow.dao.LeipiRunDao;

/**
 * 流程运行表Service
 * @author 陈钱江
 * @version 2017-09-06
 */
@Service
@Transactional(readOnly = true)
public class LeipiRunService extends CrudService<LeipiRunDao, LeipiRun> {

	public LeipiRun get(String id) {
		return super.get(id);
	}
	
	public List<LeipiRun> findList(LeipiRun leipiRun) {
		return super.findList(leipiRun);
	}
	
	public Page<LeipiRun> findPage(Page<LeipiRun> page, LeipiRun leipiRun) {
		return super.findPage(page, leipiRun);
	}
	
	@Transactional(readOnly = false)
	public void save(LeipiRun leipiRun) {
		super.save(leipiRun);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeipiRun leipiRun) {
		super.delete(leipiRun);
	}
	
	
	
	
}