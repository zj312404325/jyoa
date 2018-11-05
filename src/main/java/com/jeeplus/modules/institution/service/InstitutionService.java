/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.institution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.service.TreeService;
import com.jeeplus.modules.institution.entity.Institution;
import com.jeeplus.modules.institution.dao.InstitutionDao;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * oa制度Service
 * @author 陈
 * @version 2016-12-27
 */
@Service
@Transactional(readOnly = true)
public class InstitutionService extends TreeService<InstitutionDao, Institution> {

	@Autowired
	private InstitutionDao institutionDao;
	
	public Institution get(String id) {
		return super.get(id);
	}
	
	public List<Institution> findList(Institution institution) {
		return super.findList(institution);
	}
	
	public Page<Institution> findPage(Page<Institution> page, Institution institution) {
		return super.findPage(page, institution);
	}
	
	@Transactional(readOnly = false)
	public void save(Institution institution) {
		super.save(institution);
	}
	
	@Transactional(readOnly = false)
	public void delete(Institution institution) {
		super.delete(institution);
	}
	
	public void updateSort(Institution institution) {
		institutionDao.updateSort(institution);
		UserUtils.removeCache(UserUtils.CACHE_INSTITUTION_LIST);
	}
	
	
}