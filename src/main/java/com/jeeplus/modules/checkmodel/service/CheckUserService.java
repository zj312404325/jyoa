/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.checkmodel.entity.CheckUser;
import com.jeeplus.modules.checkmodel.dao.CheckUserDao;

/**
 * 绩效考核考核人Service
 * @author cqj
 * @version 2017-10-20
 */
@Service
@Transactional(readOnly = true)
public class CheckUserService extends CrudService<CheckUserDao, CheckUser> {

	public CheckUser get(String id) {
		return super.get(id);
	}
	
	public List<CheckUser> findList(CheckUser checkUser) {
		return super.findList(checkUser);
	}
	
	public Page<CheckUser> findPage(Page<CheckUser> page, CheckUser checkUser) {
		return super.findPage(page, checkUser);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckUser checkUser) {
		super.save(checkUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckUser checkUser) {
		super.delete(checkUser);
	}
	
	
	
	
}