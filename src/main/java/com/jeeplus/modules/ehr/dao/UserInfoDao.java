/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.ehr.entity.UserInfo;
import com.jeeplus.modules.sys.entity.User;

/**
 * 入职员工信息登记DAO接口
 * @author yc
 * @version 2017-10-18
 */
@MyBatisDao
public interface UserInfoDao extends CrudDao<UserInfo> {

	public UserInfo getByCreateBy(UserInfo userInfo);
	
	public List<UserInfo> findTrainList(UserInfo userInfo);
	
	public Page<UserInfo> findPageTrainList(Page<UserInfo> page, UserInfo userInfo);

	public int deleteUserInfoByUser(User user);

    List<Map> findPeopleStructList(UserInfo userInfo);
}