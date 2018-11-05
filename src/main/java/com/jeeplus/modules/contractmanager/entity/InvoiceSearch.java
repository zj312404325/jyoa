/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同管理-发票查询Entity
 * @author cqj
 * @version 2017-12-14
 */
public class InvoiceSearch extends DataEntity<InvoiceSearch> {
	
	private static final long serialVersionUID = 1L;
	private String invoicetypeid;		// 发票类型id
	private String invoicetype;		// 类型
	private String contractname;		// 合同名称
	private String contractno;		// 合同编号
	private String invoiceno;		// 发票号
	private String fundnatureid;		// 资金性质
	private String fundnature;		// 资金性质
	private String customername;		// 合同对方
	private String money;		// 发票金额
	private String status;		// 发票状态
	private String remark1;		// 备用1
	private String remark2;		// 备用2
	private String contractid;		// 备用2
	
	public InvoiceSearch() {
		super();
	}

	public InvoiceSearch(String id){
		super(id);
	}

	@ExcelField(title="发票类型id", align=2, sort=7)
	public String getInvoicetypeid() {
		return invoicetypeid;
	}

	public void setInvoicetypeid(String invoicetypeid) {
		this.invoicetypeid = invoicetypeid;
	}
	
	@ExcelField(title="类型", align=2, sort=8)
	public String getInvoicetype() {
		return invoicetype;
	}

	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}
	
	@ExcelField(title="合同名称", align=2, sort=9)
	public String getContractname() {
		return contractname;
	}

	public void setContractname(String contractname) {
		this.contractname = contractname;
	}
	
	@ExcelField(title="合同编号", align=2, sort=10)
	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}
	
	@ExcelField(title="发票号", align=2, sort=11)
	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	
	@ExcelField(title="资金性质", align=2, sort=12)
	public String getFundnatureid() {
		return fundnatureid;
	}

	public void setFundnatureid(String fundnatureid) {
		this.fundnatureid = fundnatureid;
	}
	
	@ExcelField(title="资金性质", align=2, sort=13)
	public String getFundnature() {
		return fundnature;
	}

	public void setFundnature(String fundnature) {
		this.fundnature = fundnature;
	}
	
	@ExcelField(title="合同对方", align=2, sort=14)
	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
	
	@ExcelField(title="发票金额", align=2, sort=15)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@ExcelField(title="发票状态", align=2, sort=16)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="备用1", align=2, sort=17)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="备用2", align=2, sort=18)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getContractid() {
		return contractid;
	}

	public void setContractid(String contractid) {
		this.contractid = contractid;
	}
	
}