/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.contractmanager.entity.PlanfundSearch;
import com.jeeplus.modules.contractmanager.dao.PlanfundSearchDao;

/**
 * 合同管理-计划资金查询Service
 * @author cqj
 * @version 2017-12-13
 */
@Service
@Transactional(readOnly = true)
public class PlanfundSearchService extends CrudService<PlanfundSearchDao, PlanfundSearch> {

	public PlanfundSearch get(String id) {
		return super.get(id);
	}
	
	public List<PlanfundSearch> findList(PlanfundSearch planfundSearch) {
		return super.findList(planfundSearch);
	}
	
	public Page<PlanfundSearch> findPage(Page<PlanfundSearch> page, PlanfundSearch planfundSearch) {
		return super.findPage(page, planfundSearch);
	}
	
	@Transactional(readOnly = false)
	public void save(PlanfundSearch planfundSearch) {
		super.save(planfundSearch);
	}
	
	@Transactional(readOnly = false)
	public void delete(PlanfundSearch planfundSearch) {
		super.delete(planfundSearch);
	}
	
	
	
	
}