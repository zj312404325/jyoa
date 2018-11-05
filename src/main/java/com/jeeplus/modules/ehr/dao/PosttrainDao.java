/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.ehr.entity.Posttrain;

/**
 * 岗位培训DAO接口
 * @author yc
 * @version 2017-10-26
 */
@MyBatisDao
public interface PosttrainDao extends CrudDao<Posttrain> {

	
}