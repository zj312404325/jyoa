/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.filemanagement.entity.Customer;
import com.jeeplus.modules.filemanagement.dao.CustomerDao;
import com.jeeplus.modules.filemanagement.entity.CustomerContact;
import com.jeeplus.modules.filemanagement.dao.CustomerContactDao;
import com.jeeplus.modules.filemanagement.entity.CustomerFile;
import com.jeeplus.modules.filemanagement.dao.CustomerFileDao;
import com.jeeplus.modules.filemanagement.entity.CustomerRemark;
import com.jeeplus.modules.filemanagement.dao.CustomerRemarkDao;

/**
 * 客户管理Service
 * @author yc
 * @version 2017-11-30
 */
@Service
@Transactional(readOnly = true)
public class CustomerService extends CrudService<CustomerDao, Customer> {

	@Autowired
	private CustomerContactDao customerContactDao;
	@Autowired
	private CustomerFileDao customerFileDao;
	@Autowired
	private CustomerRemarkDao customerRemarkDao;
	
	public Customer get(String id) {
		Customer customer = super.get(id);
		customer.setCustomerContactList(customerContactDao.findList(new CustomerContact(customer)));
		customer.setCustomerFileList(customerFileDao.findList(new CustomerFile(customer)));
		customer.setCustomerRemarkList(customerRemarkDao.findList(new CustomerRemark(customer)));
		return customer;
	}
	
	public List<Customer> findList(Customer customer) {
		return super.findList(customer);
	}
	
	public Page<Customer> findPage(Page<Customer> page, Customer customer) {
		return super.findPage(page, customer);
	}
	
	@Transactional(readOnly = false)
	public void save(Customer customer) {
		super.save(customer);
		for (CustomerContact customerContact : customer.getCustomerContactList()){
			if (customerContact.getId() == null){
				continue;
			}
			if (CustomerContact.DEL_FLAG_NORMAL.equals(customerContact.getDelFlag())){
				if (StringUtils.isBlank(customerContact.getId())){
					customerContact.setCustomer(customer);
					customerContact.preInsert();
					customerContactDao.insert(customerContact);
				}else{
					customerContact.preUpdate();
					customerContactDao.update(customerContact);
				}
			}else{
				customerContactDao.delete(customerContact);
			}
		}
		for (CustomerFile customerFile : customer.getCustomerFileList()){
			if (customerFile.getId() == null){
				continue;
			}
			if (CustomerFile.DEL_FLAG_NORMAL.equals(customerFile.getDelFlag())){
				if (StringUtils.isBlank(customerFile.getId())){
					customerFile.setCustomer(customer);
					customerFile.preInsert();
					customerFileDao.insert(customerFile);
				}else{
					customerFile.preUpdate();
					customerFileDao.update(customerFile);
				}
			}else{
				customerFileDao.delete(customerFile);
			}
		}
		for (CustomerRemark customerRemark : customer.getCustomerRemarkList()){
			if (customerRemark.getId() == null){
				continue;
			}
			if (CustomerRemark.DEL_FLAG_NORMAL.equals(customerRemark.getDelFlag())){
				if (StringUtils.isBlank(customerRemark.getId())){
					customerRemark.setCustomer(customer);
					customerRemark.preInsert();
					customerRemarkDao.insert(customerRemark);
				}else{
					customerRemark.preUpdate();
					customerRemarkDao.update(customerRemark);
				}
			}else{
				customerRemarkDao.delete(customerRemark);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Customer customer) {
		super.delete(customer);
		customerContactDao.delete(new CustomerContact(customer));
		customerFileDao.delete(new CustomerFile(customer));
		customerRemarkDao.delete(new CustomerRemark(customer));
	}
	
}