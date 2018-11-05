/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.leipiflow.entity.LeipiRunCache;
import com.jeeplus.modules.leipiflow.dao.LeipiRunCacheDao;

/**
 * 运行流程缓存Service
 * @author 陈钱江
 * @version 2017-09-06
 */
@Service
@Transactional(readOnly = true)
public class LeipiRunCacheService extends CrudService<LeipiRunCacheDao, LeipiRunCache> {

	public LeipiRunCache get(String id) {
		return super.get(id);
	}
	
	public List<LeipiRunCache> findList(LeipiRunCache leipiRunCache) {
		return super.findList(leipiRunCache);
	}
	
	public Page<LeipiRunCache> findPage(Page<LeipiRunCache> page, LeipiRunCache leipiRunCache) {
		return super.findPage(page, leipiRunCache);
	}
	
	@Transactional(readOnly = false)
	public void save(LeipiRunCache leipiRunCache) {
		super.save(leipiRunCache);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeipiRunCache leipiRunCache) {
		super.delete(leipiRunCache);
	}
	
	
	
	
}