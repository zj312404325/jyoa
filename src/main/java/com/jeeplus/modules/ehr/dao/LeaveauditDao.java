/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.ehr.entity.Leaveaudit;

/**
 * 离职审计DAO接口
 * @author cqj
 * @version 2018-03-12
 */
@MyBatisDao
public interface LeaveauditDao extends CrudDao<Leaveaudit> {

	
}