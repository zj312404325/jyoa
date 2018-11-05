/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.ehr.entity.QuestionSurvey;
import com.jeeplus.modules.ehr.dao.QuestionSurveyDao;

/**
 * 离职调查问卷Service
 * @author cqj
 * @version 2017-11-02
 */
@Service
@Transactional(readOnly = true)
public class QuestionSurveyService extends CrudService<QuestionSurveyDao, QuestionSurvey> {

	public QuestionSurvey get(String id) {
		return super.get(id);
	}
	
	public List<QuestionSurvey> findList(QuestionSurvey questionSurvey) {
		return super.findList(questionSurvey);
	}
	
	public Page<QuestionSurvey> findPage(Page<QuestionSurvey> page, QuestionSurvey questionSurvey) {
		return super.findPage(page, questionSurvey);
	}
	
	@Transactional(readOnly = false)
	public void save(QuestionSurvey questionSurvey) {
		super.save(questionSurvey);
	}
	
	@Transactional(readOnly = false)
	public void delete(QuestionSurvey questionSurvey) {
		super.delete(questionSurvey);
	}
	
	
	
	
}