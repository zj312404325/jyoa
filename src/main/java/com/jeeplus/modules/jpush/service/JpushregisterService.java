/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jpush.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.jpush.entity.Jpushregister;
import com.jeeplus.modules.jpush.dao.JpushregisterDao;

/**
 * 极光推送注册用户信息表Service
 * @author cqj
 * @version 2018-04-10
 */
@Service
@Transactional(readOnly = true)
public class JpushregisterService extends CrudService<JpushregisterDao, Jpushregister> {

	public Jpushregister get(String id) {
		return super.get(id);
	}
	
	public List<Jpushregister> findList(Jpushregister jpushregister) {
		return super.findList(jpushregister);
	}
	
	public Page<Jpushregister> findPage(Page<Jpushregister> page, Jpushregister jpushregister) {
		return super.findPage(page, jpushregister);
	}
	
	@Transactional(readOnly = false)
	public void save(Jpushregister jpushregister) {
		super.save(jpushregister);
	}
	
	@Transactional(readOnly = false)
	public void delete(Jpushregister jpushregister) {
		super.delete(jpushregister);
	}

	public List<Jpushregister> findJPushRegister(Jpushregister jpushregister) {
		return dao.findJPushRegister(jpushregister);
	}

	@Transactional(readOnly = false)
	public void deleteJpushregister(Jpushregister jpushregister) {
		dao.deleteJpushregister(jpushregister);
	}

	@Transactional(readOnly = false)
	public void saveJpushregister(Jpushregister jpushregister) {
		dao.deleteJpushregister(jpushregister);
		super.save(jpushregister);
	}
}