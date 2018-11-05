/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.ehr.entity.LeaveSheet;

/**
 * 离职申请单管理DAO接口
 * @author yc
 * @version 2017-11-02
 */
@MyBatisDao
public interface LeaveSheetDao extends CrudDao<LeaveSheet> {

	
}