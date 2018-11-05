/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.institution.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.TreeDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.institution.entity.Institution;

/**
 * oa制度DAO接口
 * @author 陈
 * @version 2016-12-27
 */
@MyBatisDao
public interface InstitutionDao extends TreeDao<Institution> {

	public List<Institution> findAllListByLevel(Institution institution);
	
	public List<Institution> findListByLevel(Map m);
	
	public void updateSort(Institution institution);
	
}