/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.ehr.entity.EntryContract;
import com.jeeplus.modules.ehr.entity.UserInfo;

/**
 * 入职合同DAO接口
 * @author yc
 * @version 2017-10-23
 */
@MyBatisDao
public interface EntryContractDao extends CrudDao<EntryContract> {

	
	public List<EntryContract> findContractPermitList(EntryContract entryContract);
}