/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.leipiflow.entity.LeipiFlow;

/**
 * 雷劈流程模块DAO接口
 * @author 陈钱江
 * @version 2017-09-05
 */
@MyBatisDao
public interface LeipiFlowDao extends CrudDao<LeipiFlow> {

	public List<LeipiFlow> findUseableList(LeipiFlow leipiFlow);
}