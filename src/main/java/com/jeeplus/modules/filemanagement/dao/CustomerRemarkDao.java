/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.filemanagement.entity.CustomerRemark;

/**
 * 客户管理DAO接口
 * @author yc
 * @version 2017-11-30
 */
@MyBatisDao
public interface CustomerRemarkDao extends CrudDao<CustomerRemark> {

	
}