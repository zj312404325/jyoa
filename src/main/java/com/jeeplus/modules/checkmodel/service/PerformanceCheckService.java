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
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheck;
import com.jeeplus.modules.checkmodel.dao.PerformanceCheckDao;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheckDetail;
import com.jeeplus.modules.checkmodel.dao.PerformanceCheckDetailDao;
import com.jeeplus.modules.sys.dao.OfficeDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 绩效考核填写Service
 * @author cqj
 * @version 2017-10-25
 */
@Service
@Transactional(readOnly = true)
public class PerformanceCheckService extends CrudService<PerformanceCheckDao, PerformanceCheck> {

	@Autowired
	private PerformanceCheckDetailDao performanceCheckDetailDao;
	
	@Autowired
	private OfficeDao officeDao;

	@Autowired
	private PostService postService;
	
	public PerformanceCheck get(String id) {
		PerformanceCheck performanceCheck = super.get(id);
		performanceCheck.getPage().setOrderBy("a.sort asc");
		performanceCheck.setType("0");
		performanceCheck.setPerformanceCheckDetailList(performanceCheckDetailDao.findList(new PerformanceCheckDetail(performanceCheck)));
		performanceCheck.setType("1");
		performanceCheck.setPerformanceCheckDetailListKey(performanceCheckDetailDao.findList(new PerformanceCheckDetail(performanceCheck)));
		return performanceCheck;
	}
	
	public List<PerformanceCheck> findList(PerformanceCheck performanceCheck) {
		return super.findList(performanceCheck);
	}
	
	public Page<PerformanceCheck> findPage(Page<PerformanceCheck> page, PerformanceCheck performanceCheck) {
		return super.findPage(page, performanceCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(PerformanceCheck performanceCheck) {
		if(!FormatUtil.isNoEmpty(performanceCheck.getId())){
			User user= UserUtils.getUser();
			performanceCheck.setOfficeid(user.getOffice().getId());
			performanceCheck.setOfficename(user.getOffice().getName());
			performanceCheck.setStationid(user.getStationType());
			if(FormatUtil.isNoEmpty(user.getStationType())){
				Post post=postService.get(user.getStationType());
				performanceCheck.setStationname(post.getPostname());
			}
			performanceCheck.setUserid(user.getId());
			performanceCheck.setUserno(user.getNo());
			performanceCheck.setUsername(user.getName());
		}
		super.save(performanceCheck);
		int i=0;
		for (PerformanceCheckDetail performanceCheckDetail : performanceCheck.getPerformanceCheckDetailList()){
			if (performanceCheckDetail.getId() == null){
				continue;
			}
			if (PerformanceCheckDetail.DEL_FLAG_NORMAL.equals(performanceCheckDetail.getDelFlag())){
				if (StringUtils.isBlank(performanceCheckDetail.getId())){
					performanceCheckDetail.setSort(i);
					performanceCheckDetail.setPerformancecheckid(performanceCheck);
					performanceCheckDetail.preInsert();
					performanceCheckDetailDao.insert(performanceCheckDetail);
					i++;
				}else{
					performanceCheckDetail.setSort(i);
					performanceCheckDetail.setPerformancecheckid(performanceCheck);
					performanceCheckDetail.preUpdate();
					performanceCheckDetailDao.update(performanceCheckDetail);
					i++;
				}
			}else{
				performanceCheckDetailDao.delete(performanceCheckDetail);
			}
		}
		for (PerformanceCheckDetail performanceCheckDetail : performanceCheck.getPerformanceCheckDetailListKey()){
			if (performanceCheckDetail.getId() == null){
				continue;
			}
			if (PerformanceCheckDetail.DEL_FLAG_NORMAL.equals(performanceCheckDetail.getDelFlag())){
				if (StringUtils.isBlank(performanceCheckDetail.getId())){
					performanceCheckDetail.setSort(i);
					performanceCheckDetail.setPerformancecheckid(performanceCheck);
					performanceCheckDetail.preInsert();
					performanceCheckDetailDao.insert(performanceCheckDetail);
					i++;
				}else{
					performanceCheckDetail.setSort(i);
					performanceCheckDetail.setPerformancecheckid(performanceCheck);
					performanceCheckDetail.preUpdate();
					performanceCheckDetailDao.update(performanceCheckDetail);
					i++;
				}
			}else{
				performanceCheckDetailDao.delete(performanceCheckDetail);
			}
		}
	}
	
	private boolean validIsStation(String id) {
		// TODO Auto-generated method stub
		Office office= officeDao.get(id);
		if(office.getType().equals("3")){//岗位
			return true;
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public void delete(PerformanceCheck performanceCheck) {
		super.delete(performanceCheck);
		performanceCheckDetailDao.delete(new PerformanceCheckDetail(performanceCheck));
	}
	
}