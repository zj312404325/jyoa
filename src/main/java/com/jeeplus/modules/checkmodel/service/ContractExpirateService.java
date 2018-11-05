/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.service;

import java.util.List;

import org.directwebremoting.annotations.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.checkmodel.entity.ContractExpirate;
import com.jeeplus.modules.checkmodel.dao.ContractExpirateDao;
import com.jeeplus.modules.sys.dao.UserDao;

/**
 * 合同到期人员考核申请Service
 * @author cqj
 * @version 2017-10-31
 */
@Service
@Transactional(readOnly = true)
public class ContractExpirateService extends CrudService<ContractExpirateDao, ContractExpirate> {

	public ContractExpirate get(String id) {
		return super.get(id);
	}
	
	public List<ContractExpirate> findList(ContractExpirate contractExpirate) {
		return super.findList(contractExpirate);
	}
	
	public Page<ContractExpirate> findPage(Page<ContractExpirate> page, ContractExpirate contractExpirate) {
		return super.findPage(page, contractExpirate);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractExpirate contractExpirate) {
		super.save(contractExpirate);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractExpirate contractExpirate) {
		super.delete(contractExpirate);
	}
	
	public Page<ContractExpirate> searchContractExpirateList(Page<ContractExpirate> page,ContractExpirate contractExpirate) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		contractExpirate.getSqlMap().put("dsf", dataScopeFilter(contractExpirate.getCurrentUser(), "o", "a"));
		// 设置分页参数
		contractExpirate.setPage(page);
		// 执行分页查询
		page.setList(dao.searchContractExpirateList(contractExpirate));
		return page;
	}
}