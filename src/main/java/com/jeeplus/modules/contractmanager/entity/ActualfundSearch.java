/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.entity;


import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同管理-实际资金Entity
 * @author cqj
 * @version 2017-12-13
 */
public class ActualfundSearch extends DataEntity<ActualfundSearch> {
	
	private static final long serialVersionUID = 1L;
	private String money;		// 实际金额
	private String contractname;		// 合同名称
	private String contractno;		// 合同编号
	private String contractid;		// 合同id
	private String contractclass;		// 合同类别
	private String contractclassid;		// 合同类别id
	private Date settlementdate;		// 结算日期
	private String customername;		// 客户名称
	private String customerclassid;		// 客户类型
	private String customerclass;		// 客户类型
	private String fundnatureid;
	private String remark1;
	private String remark2;
	
	public ActualfundSearch() {
		super();
	}

	public ActualfundSearch(String id){
		super(id);
	}

	@ExcelField(title="实际金额", align=2, sort=7)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@ExcelField(title="合同名称", align=2, sort=8)
	public String getContractname() {
		return contractname;
	}

	public void setContractname(String contractname) {
		this.contractname = contractname;
	}
	
	@ExcelField(title="合同编号", align=2, sort=9)
	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}
	
	@ExcelField(title="合同id", align=2, sort=10)
	public String getContractid() {
		return contractid;
	}

	public void setContractid(String contractid) {
		this.contractid = contractid;
	}
	
	@ExcelField(title="合同类别", align=2, sort=11)
	public String getContractclass() {
		return contractclass;
	}

	public void setContractclass(String contractclass) {
		this.contractclass = contractclass;
	}
	
	@ExcelField(title="合同类别id", align=2, sort=12)
	public String getContractclassid() {
		return contractclassid;
	}

	public void setContractclassid(String contractclassid) {
		this.contractclassid = contractclassid;
	}
	
	public Date getSettlementdate() {
		return settlementdate;
	}

	public void setSettlementdate(Date settlementdate) {
		this.settlementdate = settlementdate;
	}

	@ExcelField(title="客户名称", align=2, sort=14)
	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
	
	@ExcelField(title="客户类型", align=2, sort=15)
	public String getCustomerclassid() {
		return customerclassid;
	}

	public void setCustomerclassid(String customerclassid) {
		this.customerclassid = customerclassid;
	}

	public String getFundnatureid() {
		return fundnatureid;
	}

	public void setFundnatureid(String fundnatureid) {
		this.fundnatureid = fundnatureid;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getCustomerclass() {
		return customerclass;
	}

	public void setCustomerclass(String customerclass) {
		this.customerclass = customerclass;
	}
	
	
}