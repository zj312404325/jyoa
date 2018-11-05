/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.contractmanager.entity.ContractInvoice;

/**
 * 合同管理-合同DAO接口
 * @author yc
 * @version 2017-12-12
 */
@MyBatisDao
public interface ContractInvoiceDao extends CrudDao<ContractInvoice> {

	
}