/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同管理-合同Entity
 * @author yc
 * @version 2017-12-07
 */
public class ContractFile extends DataEntity<ContractFile> {
	
	private static final long serialVersionUID = 1L;
	private String filename;		// 附件名称
	private String fileclassid;		// 附件类型id
	private String fileclass;		// 附件类型
	private String fileurl;		// 附件文件
	private String fileremark;		// 附件说明
	private ContractManager contractmanager;		// 主表id 父类
	
	public ContractFile() {
		super();
	}

	public ContractFile(String id){
		super(id);
	}

	public ContractFile(ContractManager contractmanager){
		this.contractmanager = contractmanager;
	}

	@ExcelField(title="附件名称", align=2, sort=7)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@ExcelField(title="附件类型id", dictType="", align=2, sort=8)
	public String getFileclassid() {
		return fileclassid;
	}

	public void setFileclassid(String fileclassid) {
		this.fileclassid = fileclassid;
	}
	
	@ExcelField(title="附件类型", align=2, sort=9)
	public String getFileclass() {
		return fileclass;
	}

	public void setFileclass(String fileclass) {
		this.fileclass = fileclass;
	}
	
	@ExcelField(title="附件文件", align=2, sort=10)
	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	
	@ExcelField(title="附件说明", align=2, sort=11)
	public String getFileremark() {
		return fileremark;
	}

	public void setFileremark(String fileremark) {
		this.fileremark = fileremark;
	}
	
	public ContractManager getContractmanager() {
		return contractmanager;
	}

	public void setContractmanager(ContractManager contractmanager) {
		this.contractmanager = contractmanager;
	}
	
}