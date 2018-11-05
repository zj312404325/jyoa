/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.leipiflow.entity.LeipiRunLog;

/**
 * 运行流程缓存DAO接口
 * @author 陈钱江
 * @version 2017-09-06
 */
@MyBatisDao
public interface LeipiRunLogDao extends CrudDao<LeipiRunLog> {

	
}