/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheck;

/**
 * 绩效考核填写DAO接口
 * @author cqj
 * @version 2017-10-25
 */
@MyBatisDao
public interface PerformanceCheckDao extends CrudDao<PerformanceCheck> {

	
}