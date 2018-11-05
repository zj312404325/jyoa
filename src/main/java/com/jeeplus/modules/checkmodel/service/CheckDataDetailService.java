/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.checkmodel.entity.CheckDataDetail;
import com.jeeplus.modules.checkmodel.dao.CheckDataDetailDao;

/**
 * 绩效数据设定Service
 * @author cqj
 * @version 2017-10-23
 */
@Service
@Transactional(readOnly = true)
public class CheckDataDetailService extends CrudService<CheckDataDetailDao, CheckDataDetail> {
	@Autowired
	public CheckDataDetailDao checkDataDetailDao;

	public CheckDataDetail get(String id) {
		return super.get(id);
	}
	
	public List<CheckDataDetail> findList(CheckDataDetail checkDataDetail) {
		return super.findList(checkDataDetail);
	}
	
	public Page<CheckDataDetail> findPage(Page<CheckDataDetail> page, CheckDataDetail checkDataDetail) {
		return super.findPage(page, checkDataDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckDataDetail checkDataDetail) {
		super.save(checkDataDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckDataDetail checkDataDetail) {
		super.delete(checkDataDetail);
	}
	
	@Transactional(readOnly = false)
	public void deleteByCheckDataId(CheckDataDetail checkDataDetail) {
		checkDataDetailDao.deleteByCheckDataId(checkDataDetail);
	}
	
	
}