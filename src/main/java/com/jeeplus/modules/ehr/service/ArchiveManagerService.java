/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.service.TreeService;
import com.jeeplus.modules.ehr.entity.ArchiveManager;
import com.jeeplus.modules.ehr.dao.ArchiveManagerDao;
import com.jeeplus.modules.oa.dao.LeaveDao;

/**
 * 档案管理Service
 * @author yc
 * @version 2017-11-24
 */
@Service
@Transactional(readOnly = true)
public class ArchiveManagerService extends TreeService<ArchiveManagerDao, ArchiveManager> {
	
	@Autowired
	private ArchiveManagerDao archiveManagerDao;

	public ArchiveManager get(String id) {
		return super.get(id);
	}
	
	public List<ArchiveManager> findList(ArchiveManager archiveManager) {
		return super.findList(archiveManager);
	}
	
	public Page<ArchiveManager> findPage(Page<ArchiveManager> page, ArchiveManager archiveManager) {
		return super.findPage(page, archiveManager);
	}
	
	@Transactional(readOnly = false)
	public void save(ArchiveManager archiveManager) {
		super.save(archiveManager);
	}
	
	@Transactional(readOnly = false)
	public void delete(ArchiveManager archiveManager) {
		super.delete(archiveManager);
	}
	
	public List<ArchiveManager> findListByArchiveManager(ArchiveManager archiveManager) {
		return archiveManagerDao.findListByArchiveManager(archiveManager);
	}
	
	public List<ArchiveManager> findCurrentAndChildCategory(ArchiveManager archiveManager) {
		return archiveManagerDao.findCurrentAndChildCategory(archiveManager);
	}
}