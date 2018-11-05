/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.ehr.dao.UserInfoDao;
import com.jeeplus.modules.ehr.dao.UserMemberDao;
import com.jeeplus.modules.ehr.entity.UserInfo;
import com.jeeplus.modules.ehr.entity.UserMember;
import com.jeeplus.modules.sys.entity.User;

/**
 * 入职员工信息登记Service
 * @author yc
 * @version 2017-10-18
 */
@Service
@Transactional(readOnly = true)
public class UserInfoService extends CrudService<UserInfoDao, UserInfo> {

	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private UserMemberDao userMemberDao;
	
	public UserInfo get(String id) {
		UserInfo userInfo = super.get(id);
		if(FormatUtil.isNoEmpty(userInfo)){
			userInfo.setUserMemberList(userMemberDao.findList(new UserMember(userInfo)));
		}
		return userInfo;
	}
	
	public UserInfo getByCreateBy(String id) {
		UserInfo userInfo = userInfoDao.getByCreateBy(new UserInfo(new User(id)));
		if(FormatUtil.isNoEmpty(userInfo)){
			userInfo.setUserMemberList(userMemberDao.findList(new UserMember(userInfo)));
		}
		return userInfo;
	}
	
	public List<UserInfo> findList(UserInfo userInfo) {
		return super.findList(userInfo);
	}
	
	public Page<UserInfo> findPage(Page<UserInfo> page, UserInfo userInfo) {
		return super.findPage(page, userInfo);
	}

	public List<Map> findPeopleStruct(UserInfo userInfo) {
		return userInfoDao.findPeopleStructList(userInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(UserInfo userInfo) {
		super.save(userInfo);
		if(FormatUtil.isNoEmpty(userInfo.getUserMemberList())){
			userMemberDao.deleteByUserinfoid(userInfo);
			for (UserMember userMember : userInfo.getUserMemberList()){
//				if (UserMember.DEL_FLAG_NORMAL.equals(userMember.getDelFlag())){
					if (StringUtils.isBlank(userMember.getId())){
						userMember.setUserinfo(userInfo);
						userMember.preInsert();
						userMemberDao.insert(userMember);
					}else{
						userMember.preUpdate();
						userMemberDao.update(userMember);
					}
//				}else{
//					userMemberDao.delete(userMember);
//				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void saveOnly(UserInfo userInfo) {
		super.save(userInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserInfo userInfo) {
		super.delete(userInfo);
		userMemberDao.delete(new UserMember(userInfo));
	}
	
	public List<UserInfo> findTrainList(UserInfo userInfo) {
		return userInfoDao.findTrainList(userInfo);
	}
	public Page<UserInfo> findPageTrainList(Page<UserInfo> page, UserInfo userInfo) {
		
		userInfo.setPage(page);
		page.setList(userInfoDao.findTrainList(userInfo));
		return page;
	}
}