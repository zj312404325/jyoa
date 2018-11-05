/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.checkmodel.entity.CheckTime;

/**
 * 考核时间DAO接口
 * @author cqj
 * @version 2017-10-23
 */
@MyBatisDao
public interface CheckTimeDao extends CrudDao<CheckTime> {

	
}