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
public class ContractNote extends DataEntity<ContractNote> {
	
	private static final long serialVersionUID = 1L;
	private ContractManager contractmanager;		// 主表id 父类
	private String note;		// 说明事项
	private String content;		// 内容
	
	public ContractNote() {
		super();
	}

	public ContractNote(String id){
		super(id);
	}

	public ContractNote(ContractManager contractmanager){
		this.contractmanager = contractmanager;
	}

	public ContractManager getContractmanager() {
		return contractmanager;
	}

	public void setContractmanager(ContractManager contractmanager) {
		this.contractmanager = contractmanager;
	}
	
	@ExcelField(title="说明事项", align=2, sort=8)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@ExcelField(title="内容", align=2, sort=9)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}