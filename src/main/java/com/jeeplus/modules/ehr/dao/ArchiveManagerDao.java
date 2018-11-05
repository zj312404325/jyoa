/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.TreeDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.ehr.entity.ArchiveManager;
import com.jeeplus.modules.ehr.entity.EntryContract;

/**
 * 档案管理DAO接口
 * @author yc
 * @version 2017-11-24
 */
@MyBatisDao
public interface ArchiveManagerDao extends TreeDao<ArchiveManager> {

	public List<ArchiveManager> findListByArchiveManager(ArchiveManager am);
	
	public List<ArchiveManager> findCurrentAndChildCategory(ArchiveManager am);
}