/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.service;

import java.util.List;

import com.jeeplus.modules.sys.entity.Post;
import com.jeeplus.modules.sys.service.PostService;
import com.jeeplus.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.modules.checkmodel.entity.PerformanceInterview;
import com.jeeplus.modules.checkmodel.dao.PerformanceInterviewDao;
import com.jeeplus.modules.sys.dao.OfficeDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 绩效考核面谈表Service
 * @author cqj
 * @version 2017-10-27
 */
@Service
@Transactional(readOnly = true)
public class PerformanceInterviewService extends CrudService<PerformanceInterviewDao, PerformanceInterview> {

	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private PostService postService;
	
	public PerformanceInterview get(String id) {
		return super.get(id);
	}
	
	public List<PerformanceInterview> findList(PerformanceInterview performanceInterview) {
		return super.findList(performanceInterview);
	}
	
	public Page<PerformanceInterview> findPage(Page<PerformanceInterview> page, PerformanceInterview performanceInterview) {
		return super.findPage(page, performanceInterview);
	}
	
	@Transactional(readOnly = false)
	public void save(PerformanceInterview performanceInterview) {
		User user= UserUtils.getUser();
		performanceInterview.setOfficeid(user.getOffice().getId());
		performanceInterview.setOfficename(user.getOffice().getName());
		performanceInterview.setStationid(user.getStationType());
		if(FormatUtil.isNoEmpty(user.getStationType())){
			Post post=postService.get(user.getStationType());
			performanceInterview.setStationname(post.getPostname());
		}
		performanceInterview.setUserid(user.getId());
		performanceInterview.setUserno(user.getNo());
		performanceInterview.setUsername(user.getName());
		super.save(performanceInterview);
	}

	@Transactional(readOnly = false)
	public void savePerformanceInterview(PerformanceInterview performanceInterview) {
		super.save(performanceInterview);
	}
	
	@Transactional(readOnly = false)
	public void delete(PerformanceInterview performanceInterview) {
		super.delete(performanceInterview);
	}
	
	private boolean validIsStation(String id) {
		// TODO Auto-generated method stub
		Office office= officeDao.get(id);
		if(office.getType().equals("3")){//岗位
			return true;
		}
		return false;
	}
	
}