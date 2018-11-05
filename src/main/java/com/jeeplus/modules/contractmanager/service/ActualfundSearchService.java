/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.contractmanager.entity.ActualfundSearch;
import com.jeeplus.modules.contractmanager.dao.ActualfundSearchDao;

/**
 * 合同管理-实际资金Service
 * @author cqj
 * @version 2017-12-13
 */
@Service
@Transactional(readOnly = true)
public class ActualfundSearchService extends CrudService<ActualfundSearchDao, ActualfundSearch> {

	public ActualfundSearch get(String id) {
		return super.get(id);
	}
	
	public List<ActualfundSearch> findList(ActualfundSearch actualfundSearch) {
		return super.findList(actualfundSearch);
	}
	
	public Page<ActualfundSearch> findPage(Page<ActualfundSearch> page, ActualfundSearch actualfundSearch) {
		return super.findPage(page, actualfundSearch);
	}
	
	@Transactional(readOnly = false)
	public void save(ActualfundSearch actualfundSearch) {
		super.save(actualfundSearch);
	}
	
	@Transactional(readOnly = false)
	public void delete(ActualfundSearch actualfundSearch) {
		super.delete(actualfundSearch);
	}
	
	
	
	
}