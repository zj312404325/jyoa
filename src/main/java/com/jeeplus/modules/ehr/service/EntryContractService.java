/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.ehr.entity.EntryContract;
import com.jeeplus.modules.ehr.dao.EntryContractDao;

/**
 * 入职合同Service
 * @author yc
 * @version 2017-10-23
 */
@Service
@Transactional(readOnly = true)
public class EntryContractService extends CrudService<EntryContractDao, EntryContract> {

	public EntryContract get(String id) {
		return super.get(id);
	}
	
	public List<EntryContract> findList(EntryContract entryContract) {
		return super.findList(entryContract);
	}
	
	public Page<EntryContract> findPage(Page<EntryContract> page, EntryContract entryContract) {
		return super.findPage(page, entryContract);
	}
	
	@Transactional(readOnly = false)
	public void save(EntryContract entryContract) {
		super.save(entryContract);
	}
	
	@Transactional(readOnly = false)
	public void delete(EntryContract entryContract) {
		super.delete(entryContract);
	}
	
	
	
	
}