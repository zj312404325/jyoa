/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.flow.entity.Flowtemplate;
import com.jeeplus.modules.flow.dao.FlowtemplateDao;
import com.jeeplus.modules.flow.entity.Templatecontrol;
import com.jeeplus.modules.flow.dao.TemplatecontrolDao;

/**
 * 模板主表Service
 * @author cqj
 * @version 2016-12-09
 */
@Service
@Transactional(readOnly = true)
public class FlowtemplateService extends CrudService<FlowtemplateDao, Flowtemplate> {

	@Autowired
	private TemplatecontrolDao templatecontrolDao;
	
	public Flowtemplate get(String id) {
		Flowtemplate flowtemplate = super.get(id);
		if(FormatUtil.isNoEmpty(flowtemplate)){
			List<Templatecontrol> list = templatecontrolDao.findList(new Templatecontrol(flowtemplate));
			if(list.size() > 0){
				flowtemplate.setTemplatecontrolList(list);
			}
		}
		return flowtemplate;
	}
	
	public Templatecontrol getTemplatecontrolByid(String id) {
		Templatecontrol templatecontrol = templatecontrolDao.get(id);
		return templatecontrol;
	}
	
	public List<Flowtemplate> findList(Flowtemplate flowtemplate) {
		return super.findList(flowtemplate);
	}
	
	public Page<Flowtemplate> findPage(Page<Flowtemplate> page, Flowtemplate flowtemplate) {
		return super.findPage(page, flowtemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(Flowtemplate flowtemplate) {
		super.save(flowtemplate);
		for (Templatecontrol templatecontrol : flowtemplate.getTemplatecontrolList()){
			if (templatecontrol.getId() == null){
				continue;
			}
			if (Templatecontrol.DEL_FLAG_NORMAL.equals(templatecontrol.getDelFlag())){
				if (StringUtils.isBlank(templatecontrol.getId())){
					templatecontrol.setFlowtemplate(flowtemplate);
					templatecontrol.preInsert();
					templatecontrolDao.insert(templatecontrol);
				}else{
					templatecontrol.preUpdate();
					templatecontrolDao.update(templatecontrol);
				}
			}else{
				templatecontrolDao.delete(templatecontrol);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void save(Flowtemplate flowtemplate,Templatecontrol templatecontrol) {
		if (StringUtils.isBlank(templatecontrol.getId())){
			templatecontrol.setFlowtemplate(flowtemplate);
			templatecontrol.preInsert();
			templatecontrol.setColumnid(templatecontrol.getId());
			templatecontrolDao.insert(templatecontrol);
		}else{
			templatecontrol.preUpdate();
			templatecontrolDao.update(templatecontrol);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Flowtemplate flowtemplate) {
		super.delete(flowtemplate);
		templatecontrolDao.delete(new Templatecontrol(flowtemplate));
	}
	
}