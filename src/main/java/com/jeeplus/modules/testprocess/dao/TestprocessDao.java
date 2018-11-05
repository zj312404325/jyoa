/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.testprocess.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.oa.entity.Leave;
import com.jeeplus.modules.testprocess.entity.Testprocess;

/**
 * 流程测试DAO接口
 * @author cqj
 * @version 2016-12-05
 */
@MyBatisDao
public interface TestprocessDao extends CrudDao<Testprocess> {

	/**
	 * 更新流程实例ID
	 * @param leave
	 * @return
	 */
	public int updateProcessInstanceId(Testprocess testprocess);
}