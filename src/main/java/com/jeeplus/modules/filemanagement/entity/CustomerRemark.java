/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 客户管理Entity
 * @author yc
 * @version 2017-11-30
 */
public class CustomerRemark extends DataEntity<CustomerRemark> {
	
	private static final long serialVersionUID = 1L;
	private String remarktitle;		// 说明事项
	private String remark;		// 内容
	private String sortno;		// 序号
	private Customer customer;		// 主表id 父类
	
	public CustomerRemark() {
		super();
	}

	public CustomerRemark(String id){
		super(id);
	}

	public CustomerRemark(Customer customer){
		this.customer = customer;
	}

	@ExcelField(title="说明事项", align=2, sort=7)
	public String getRemarktitle() {
		return remarktitle;
	}

	public void setRemarktitle(String remarktitle) {
		this.remarktitle = remarktitle;
	}
	
	@ExcelField(title="内容", align=2, sort=8)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="序号", align=2, sort=9)
	public String getSortno() {
		return sortno;
	}

	public void setSortno(String sortno) {
		this.sortno = sortno;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}