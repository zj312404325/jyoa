/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sutoroa.entity.Conference;

/**
 * 每日团队风采DAO接口
 * @author cqj
 * @version 2018-02-23
 */
@MyBatisDao
public interface ConferenceDao extends CrudDao<Conference> {

	
}