/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sutoroa.entity.Duty;

/**
 * 值班表DAO接口
 * @author cqj
 * @version 2018-02-24
 */
@MyBatisDao
public interface DutyDao extends CrudDao<Duty> {

	
}