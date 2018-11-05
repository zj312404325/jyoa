/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.service;

import java.util.List;

import com.jeeplus.modules.sutoroa.entity.Leavemsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sutoroa.entity.Conference;
import com.jeeplus.modules.sutoroa.dao.ConferenceDao;

/**
 * 每日团队风采Service
 * @author cqj
 * @version 2018-02-23
 */
@Service
@Transactional(readOnly = true)
public class ConferenceService extends CrudService<ConferenceDao, Conference> {
	@Autowired
	private LeavemsgService leavemsgService;
	public Conference get(String id) {
		Conference cf=super.get(id);
		//添加Leavemsg列表
		Leavemsg ls=new Leavemsg();
		ls.setConferenceid(id);
		List<Leavemsg> leavemsgList=leavemsgService.findList(ls);
		cf.setLeavemsgList(leavemsgList);
		return cf;
	}
	
	public List<Conference> findList(Conference conference) {
		return super.findList(conference);
	}
	
	public Page<Conference> findPage(Page<Conference> page, Conference conference) {
		return super.findPage(page, conference);
	}
	
	@Transactional(readOnly = false)
	public void save(Conference conference) {
		super.save(conference);
	}
	
	@Transactional(readOnly = false)
	public void delete(Conference conference) {
		super.delete(conference);
	}
	
	
	
	
}