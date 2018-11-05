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
public class ContractText extends DataEntity<ContractText> {
	
	private static final long serialVersionUID = 1L;
	private ContractManager contractmanager;		// 主表id 父类
	private String textname;		// 文本名称
	private String textclassid;		// 文本类别id
	private String textclass;		// 文本类别
	private String texturl;		// 文本文件
	private String textremark;		// 文本说明
	
	public ContractText() {
		super();
	}

	public ContractText(String id){
		super(id);
	}

	public ContractText(ContractManager contractmanager){
		this.contractmanager = contractmanager;
	}

	public ContractManager getContractmanager() {
		return contractmanager;
	}

	public void setContractmanager(ContractManager contractmanager) {
		this.contractmanager = contractmanager;
	}
	
	@ExcelField(title="文本名称", align=2, sort=8)
	public String getTextname() {
		return textname;
	}

	public void setTextname(String textname) {
		this.textname = textname;
	}
	
	@ExcelField(title="文本类别id", dictType="", align=2, sort=9)
	public String getTextclassid() {
		return textclassid;
	}

	public void setTextclassid(String textclassid) {
		this.textclassid = textclassid;
	}
	
	@ExcelField(title="文本类别", align=2, sort=10)
	public String getTextclass() {
		return textclass;
	}

	public void setTextclass(String textclass) {
		this.textclass = textclass;
	}
	
	@ExcelField(title="文本文件", align=2, sort=11)
	public String getTexturl() {
		return texturl;
	}

	public void setTexturl(String texturl) {
		this.texturl = texturl;
	}
	
	@ExcelField(title="文本说明", align=2, sort=12)
	public String getTextremark() {
		return textremark;
	}

	public void setTextremark(String textremark) {
		this.textremark = textremark;
	}
	
}