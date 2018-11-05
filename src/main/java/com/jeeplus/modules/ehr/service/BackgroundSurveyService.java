/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.ehr.entity.BackgroundSurvey;
import com.jeeplus.modules.ehr.dao.BackgroundSurveyDao;

/**
 * 背景调查Service
 * @author yc
 * @version 2017-10-25
 */
@Service
@Transactional(readOnly = true)
public class BackgroundSurveyService extends CrudService<BackgroundSurveyDao, BackgroundSurvey> {

	public BackgroundSurvey get(String id) {
		return super.get(id);
	}
	
	public List<BackgroundSurvey> findList(BackgroundSurvey backgroundSurvey) {
		return super.findList(backgroundSurvey);
	}
	
	public Page<BackgroundSurvey> findPage(Page<BackgroundSurvey> page, BackgroundSurvey backgroundSurvey) {
		return super.findPage(page, backgroundSurvey);
	}
	
	@Transactional(readOnly = false)
	public void save(BackgroundSurvey backgroundSurvey) {
		super.save(backgroundSurvey);
	}
	
	@Transactional(readOnly = false)
	public void delete(BackgroundSurvey backgroundSurvey) {
		super.delete(backgroundSurvey);
	}
	
	
	
	
}