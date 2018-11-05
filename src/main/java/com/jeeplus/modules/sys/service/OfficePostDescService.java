/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.entity.OfficePostDesc;
import com.jeeplus.modules.sys.dao.OfficePostDescDao;

/**
 * 职务说明Service
 * @author yc
 * @version 2018-03-05
 */
@Service
@Transactional(readOnly = true)
public class OfficePostDescService extends CrudService<OfficePostDescDao, OfficePostDesc> {

	public OfficePostDesc get(String id) {
		return super.get(id);
	}
	
	public List<OfficePostDesc> findList(OfficePostDesc officePostDesc) {
		return super.findList(officePostDesc);
	}
	
	public Page<OfficePostDesc> findPage(Page<OfficePostDesc> page, OfficePostDesc officePostDesc) {
		return super.findPage(page, officePostDesc);
	}
	
	@Transactional(readOnly = false)
	public void save(OfficePostDesc officePostDesc) {
		super.save(officePostDesc);
	}
	
	@Transactional(readOnly = false)
	public void delete(OfficePostDesc officePostDesc) {
		super.delete(officePostDesc);
	}
	
	
	
	
}