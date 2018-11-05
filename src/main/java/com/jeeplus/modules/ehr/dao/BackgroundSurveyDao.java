/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.ehr.entity.BackgroundSurvey;

/**
 * 背景调查DAO接口
 * @author yc
 * @version 2017-10-25
 */
@MyBatisDao
public interface BackgroundSurveyDao extends CrudDao<BackgroundSurvey> {

	
}