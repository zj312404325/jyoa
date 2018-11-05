/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.service;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.leipiflow.dao.LeipiRunProcessDao;
import com.jeeplus.modules.leipiflow.entity.LeipiRunProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 运行流程步骤Service
 * @author 陈钱江
 * @version 2017-09-06
 */
@Service
@Transactional(readOnly = true)
public class LeipiRunProcessService extends CrudService<LeipiRunProcessDao, LeipiRunProcess> {

	@Autowired
	private LeipiRunProcessDao leipiRunProcessDao;

	public LeipiRunProcess get(String id) {
		return super.get(id);
	}
	
	public List<LeipiRunProcess> findList(LeipiRunProcess leipiRunProcess) {
		return super.findList(leipiRunProcess);
	}
	
	public Page<LeipiRunProcess> findPage(Page<LeipiRunProcess> page, LeipiRunProcess leipiRunProcess) {
		return super.findPage(page, leipiRunProcess);
	}
	
	@Transactional(readOnly = false)
	public void save(LeipiRunProcess leipiRunProcess) {
		super.save(leipiRunProcess);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeipiRunProcess leipiRunProcess) {
		super.delete(leipiRunProcess);
	}

	public void updateIsOpen(LeipiRunProcess openProcess) {
		leipiRunProcessDao.updateIsOpen(openProcess);
	}

	public List<LeipiRunProcess> findFirstList(LeipiRunProcess leipiRunProcess) {
		return leipiRunProcessDao.findFirstList(leipiRunProcess);
	}

	public Page<LeipiRunProcess> findFirstPage(Page<LeipiRunProcess> page, LeipiRunProcess leipiRunProcess) {
		leipiRunProcess.setPage(page);
		page.setList(dao.findFirstList(leipiRunProcess));
		return page;
	}
}