/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.checkmodel.entity.CheckDataDetail;

/**
 * 绩效数据设定DAO接口
 * @author cqj
 * @version 2017-10-23
 */
@MyBatisDao
public interface CheckDataDetailDao extends CrudDao<CheckDataDetail> {
	public int deleteByCheckDataId(CheckDataDetail checkDataDetail);
}