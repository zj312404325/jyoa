/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.flow.entity.Flowapply;

import java.util.List;

/**
 * 我的申请DAO接口
 * @author cqj
 * @version 2016-12-08
 */
@MyBatisDao
public interface FlowapplyDao extends CrudDao<Flowapply> {

	/**
	 * 更新流程实例ID
	 * @param leave
	 * @return
	 */
	public int updateProcessInstanceId(Flowapply flowapply);

	public List<Flowapply> findListSpecial(Flowapply flowapply);
}