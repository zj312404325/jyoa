/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.ehr.entity.UserInfo;
import com.jeeplus.modules.ehr.entity.UserMember;

/**
 * 入职员工信息登记DAO接口
 * @author yc
 * @version 2017-10-18
 */
@MyBatisDao
public interface UserMemberDao extends CrudDao<UserMember> {

	public void deleteByUserinfoid(UserInfo userInfo);
}