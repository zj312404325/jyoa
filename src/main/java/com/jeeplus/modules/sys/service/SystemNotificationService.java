/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.entity.SystemNotification;
import com.jeeplus.modules.sys.dao.SystemNotificationDao;

/**
 * 系统通知Service
 * @author yc
 * @version 2018-03-01
 */
@Service
@Transactional(readOnly = true)
public class SystemNotificationService extends CrudService<SystemNotificationDao, SystemNotification> {

	public SystemNotification get(String id) {
		return super.get(id);
	}
	
	public List<SystemNotification> findList(SystemNotification systemNotification) {
		return super.findList(systemNotification);
	}
	
	public Page<SystemNotification> findPage(Page<SystemNotification> page, SystemNotification systemNotification) {
		return super.findPage(page, systemNotification);
	}
	
	@Transactional(readOnly = false)
	public void save(SystemNotification systemNotification) {
		super.save(systemNotification);
	}
	
	@Transactional(readOnly = false)
	public void delete(SystemNotification systemNotification) {
		super.delete(systemNotification);
	}
	
	
	
	
}