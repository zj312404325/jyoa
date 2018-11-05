/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.flow.entity.Flowagent;

/**
 * 流程代理DAO接口
 * @author cqj
 * @version 2016-12-16
 */
@MyBatisDao
public interface FlowagentDao extends CrudDao<Flowagent> {

	
}