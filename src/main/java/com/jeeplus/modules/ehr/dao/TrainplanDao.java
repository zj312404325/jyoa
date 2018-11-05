/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.ehr.entity.Trainplan;

/**
 * 培训计划DAO接口
 * @author cqj
 * @version 2018-05-25
 */
@MyBatisDao
public interface TrainplanDao extends CrudDao<Trainplan> {

	
}