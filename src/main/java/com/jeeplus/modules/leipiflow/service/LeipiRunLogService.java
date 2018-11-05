/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.leipiflow.entity.LeipiRunLog;
import com.jeeplus.modules.leipiflow.dao.LeipiRunLogDao;

/**
 * 运行流程缓存Service
 * @author 陈钱江
 * @version 2017-09-06
 */
@Service
@Transactional(readOnly = true)
public class LeipiRunLogService extends CrudService<LeipiRunLogDao, LeipiRunLog> {

	public LeipiRunLog get(String id) {
		return super.get(id);
	}
	
	public List<LeipiRunLog> findList(LeipiRunLog leipiRunLog) {
		return super.findList(leipiRunLog);
	}
	
	public Page<LeipiRunLog> findPage(Page<LeipiRunLog> page, LeipiRunLog leipiRunLog) {
		return super.findPage(page, leipiRunLog);
	}
	
	@Transactional(readOnly = false)
	public void save(LeipiRunLog leipiRunLog) {
		super.save(leipiRunLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeipiRunLog leipiRunLog) {
		super.delete(leipiRunLog);
	}
	
	
	
	
}