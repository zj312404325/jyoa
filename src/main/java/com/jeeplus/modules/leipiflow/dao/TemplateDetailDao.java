/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.leipiflow.entity.TemplateDetail;

/**
 * 指定流程申请明细表DAO接口
 * @author yc
 * @version 2018-03-21
 */
@MyBatisDao
public interface TemplateDetailDao extends CrudDao<TemplateDetail> {

	
}