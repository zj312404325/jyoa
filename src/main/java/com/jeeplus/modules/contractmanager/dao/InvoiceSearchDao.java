/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.contractmanager.entity.InvoiceSearch;

/**
 * 合同管理-发票查询DAO接口
 * @author cqj
 * @version 2017-12-14
 */
@MyBatisDao
public interface InvoiceSearchDao extends CrudDao<InvoiceSearch> {

	
}