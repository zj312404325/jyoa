/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.SystemNotification;

/**
 * 系统通知DAO接口
 * @author yc
 * @version 2018-03-01
 */
@MyBatisDao
public interface SystemNotificationDao extends CrudDao<SystemNotification> {

	
}