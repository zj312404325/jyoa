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
public class CustomerContact extends DataEntity<CustomerContact> {
	
	private static final long serialVersionUID = 1L;
	private String realname;		// 姓名
	private String dept;		// 部门及职位
	private String mobile;		// 移动电话
	private String phone;		// 办公电话
	private String fax;		// 传真
	private String email;		// E-Mail
	private String remark;		// 备注
	private Customer customer;		// 主表id 父类
	private Integer sortno;		// 序号
	
	public CustomerContact() {
		super();
	}

	public CustomerContact(String id){
		super(id);
	}

	public CustomerContact(Customer customer){
		this.customer = customer;
	}

	@ExcelField(title="姓名", align=2, sort=7)
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	@ExcelField(title="部门及职位", align=2, sort=8)
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	@ExcelField(title="移动电话", align=2, sort=9)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="办公电话", align=2, sort=10)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="传真", align=2, sort=11)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@ExcelField(title="E-Mail", align=2, sort=12)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="备注", align=2, sort=13)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@ExcelField(title="序号", align=2, sort=15)
	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}
	
}