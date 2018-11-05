/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jpush.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.jpush.entity.Jpushregister;

import java.util.List;

/**
 * 极光推送注册用户信息表DAO接口
 * @author cqj
 * @version 2018-04-10
 */
@MyBatisDao
public interface JpushregisterDao extends CrudDao<Jpushregister> {

	public List<Jpushregister> findJPushRegister(Jpushregister jpushregister);

	public void deleteJpushregister(Jpushregister jpushregister);
}