/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同管理-合同Entity
 * @author yc
 * @version 2017-12-12
 */
public class ContractInvoice extends DataEntity<ContractInvoice> {
	
	private static final long serialVersionUID = 1L;
	private String invoicetypeid;		// 发票类型id
	private String invoicetype;		// 发票类型
	private String money;		// 金额
	private Date invoicedate;		// 开票日期
	private String invoiceno;		// 开票号
	private String remark1;		// 备注1
	private String remark2;		// 备注2
	private ContractManager contractmanager;		// 主表ID 父类
	
	public ContractInvoice() {
		super();
	}

	public ContractInvoice(String id){
		super(id);
	}

	public ContractInvoice(ContractManager contractmanager){
		this.contractmanager = contractmanager;
	}

	@ExcelField(title="发票类型id", dictType="", align=2, sort=7)
	public String getInvoicetypeid() {
		return invoicetypeid;
	}

	public void setInvoicetypeid(String invoicetypeid) {
		this.invoicetypeid = invoicetypeid;
	}
	
	@ExcelField(title="发票类型", align=2, sort=8)
	public String getInvoicetype() {
		return invoicetype;
	}

	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}
	
	@ExcelField(title="金额", align=2, sort=9)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	@ExcelField(title="开票日期", align=2, sort=10)
	public Date getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}
	
	@ExcelField(title="开票号", align=2, sort=11)
	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	
	@ExcelField(title="备注1", align=2, sort=12)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="备注2", align=2, sort=13)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	public ContractManager getContractmanager() {
		return contractmanager;
	}

	public void setContractmanager(ContractManager contractmanager) {
		this.contractmanager = contractmanager;
	}
	
}