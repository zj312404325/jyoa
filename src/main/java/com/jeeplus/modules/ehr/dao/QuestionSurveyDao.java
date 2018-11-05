/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.ehr.entity.QuestionSurvey;

/**
 * 离职调查问卷DAO接口
 * @author cqj
 * @version 2017-11-02
 */
@MyBatisDao
public interface QuestionSurveyDao extends CrudDao<QuestionSurvey> {

	
}