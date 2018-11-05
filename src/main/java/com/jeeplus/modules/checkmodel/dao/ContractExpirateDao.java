/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.checkmodel.entity.ContractExpirate;
import com.jeeplus.modules.sys.entity.User;

/**
 * 合同到期人员考核申请DAO接口
 * @author cqj
 * @version 2017-10-31
 */
@MyBatisDao
public interface ContractExpirateDao extends CrudDao<ContractExpirate> {
	public List<ContractExpirate> searchContractExpirateList(ContractExpirate contractExpirate);
}