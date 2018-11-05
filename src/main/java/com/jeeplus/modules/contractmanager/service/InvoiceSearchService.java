/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.contractmanager.entity.InvoiceSearch;
import com.jeeplus.modules.contractmanager.dao.InvoiceSearchDao;

/**
 * 合同管理-发票查询Service
 * @author cqj
 * @version 2017-12-14
 */
@Service
@Transactional(readOnly = true)
public class InvoiceSearchService extends CrudService<InvoiceSearchDao, InvoiceSearch> {

	public InvoiceSearch get(String id) {
		return super.get(id);
	}
	
	public List<InvoiceSearch> findList(InvoiceSearch invoiceSearch) {
		return super.findList(invoiceSearch);
	}
	
	public Page<InvoiceSearch> findPage(Page<InvoiceSearch> page, InvoiceSearch invoiceSearch) {
		return super.findPage(page, invoiceSearch);
	}
	
	@Transactional(readOnly = false)
	public void save(InvoiceSearch invoiceSearch) {
		super.save(invoiceSearch);
	}
	
	@Transactional(readOnly = false)
	public void delete(InvoiceSearch invoiceSearch) {
		super.delete(invoiceSearch);
	}
	
	
	
	
}