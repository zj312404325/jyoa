/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.leipiflow.entity.TemplateDetail;
import com.jeeplus.modules.leipiflow.dao.TemplateDetailDao;

/**
 * 指定流程申请明细表Service
 * @author yc
 * @version 2018-03-21
 */
@Service
@Transactional(readOnly = true)
public class TemplateDetailService extends CrudService<TemplateDetailDao, TemplateDetail> {

	public TemplateDetail get(String id) {
		return super.get(id);
	}
	
	public List<TemplateDetail> findList(TemplateDetail templateDetail) {
		return super.findList(templateDetail);
	}
	
	public Page<TemplateDetail> findPage(Page<TemplateDetail> page, TemplateDetail templateDetail) {
		return super.findPage(page, templateDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(TemplateDetail templateDetail) {
		super.save(templateDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(TemplateDetail templateDetail) {
		super.delete(templateDetail);
	}
	
	
	
	
}