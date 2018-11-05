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
public class CustomerFile extends DataEntity<CustomerFile> {
	
	private static final long serialVersionUID = 1L;
	private String filename;		// 附件名称
	private String fileclassid;		// 附件类别id
	private String fileclass;		// 附件类别
	private String fileurl;		// 文件路径
	private String remark;		// 附件说明
	private Integer sortno;		// 序号
	private Customer customer;		// 主表id 父类
	
	public CustomerFile() {
		super();
	}

	public CustomerFile(String id){
		super(id);
	}

	public CustomerFile(Customer customer){
		this.customer = customer;
	}

	@ExcelField(title="附件名称", align=2, sort=7)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@ExcelField(title="附件类别id", align=2, sort=8)
	public String getFileclassid() {
		return fileclassid;
	}

	public void setFileclassid(String fileclassid) {
		this.fileclassid = fileclassid;
	}
	
	@ExcelField(title="附件类别", align=2, sort=9)
	public String getFileclass() {
		return fileclass;
	}

	public void setFileclass(String fileclass) {
		this.fileclass = fileclass;
	}
	
	@ExcelField(title="文件路径", align=2, sort=10)
	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	
	@ExcelField(title="附件说明", align=2, sort=11)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="序号", align=2, sort=12)
	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}