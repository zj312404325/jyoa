/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.leipiflow.entity.LeipiRunSign;
import com.jeeplus.modules.leipiflow.dao.LeipiRunSignDao;

/**
 * 运行流程signService
 * @author cqj
 * @version 2017-09-06
 */
@Service
@Transactional(readOnly = true)
public class LeipiRunSignService extends CrudService<LeipiRunSignDao, LeipiRunSign> {

	public LeipiRunSign get(String id) {
		return super.get(id);
	}
	
	public List<LeipiRunSign> findList(LeipiRunSign leipiRunSign) {
		return super.findList(leipiRunSign);
	}
	
	public Page<LeipiRunSign> findPage(Page<LeipiRunSign> page, LeipiRunSign leipiRunSign) {
		return super.findPage(page, leipiRunSign);
	}
	
	@Transactional(readOnly = false)
	public void save(LeipiRunSign leipiRunSign) {
		super.save(leipiRunSign);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeipiRunSign leipiRunSign) {
		super.delete(leipiRunSign);
	}
	
	
	
	
}