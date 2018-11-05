/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.entity;


import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同管理-计划资金查询Entity
 * @author cqj
 * @version 2017-12-13
 */
public class PlanfundSearch extends DataEntity<PlanfundSearch> {
	
	private static final long serialVersionUID = 1L;
	private String contractid;		// 合同id
	private String planfundname;		// 计划收款名称
	private String contractname;		// 合同名称
	private String contractno;		// 合同编号
	private String contractclass;		// 合同类型
	private String money;		// 计划收款金额
	private String customername;		// 客户名称
	private String balance;		// 余额
	private String completerate;		// 完成比例
	private String overdue;		// 逾期
	private String customerclass;		// 客户类别
	private String currency;		// 币种
	private String completemoney;		// 完成金额
	private String settlement;		// 结算方式
	private String remark;		// 备注
	private Date plancompletiondate;
	private Date completedate;// 完成日期
	private String fundnatureid; //资金性质1收款2付款
	private String settlementid;		// 结算方式
	private String contractclassid;		// 合同类型
	
	public PlanfundSearch() {
		super();
	}

	public PlanfundSearch(String id){
		super(id);
	}

	@ExcelField(title="计划收款名称", align=2, sort=7)
	public String getPlanfundname() {
		return planfundname;
	}

	public void setPlanfundname(String planfundname) {
		this.planfundname = planfundname;
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
	
	@ExcelField(title="合同类型", align=2, sort=10)
	public String getContractclass() {
		return contractclass;
	}

	public void setContractclass(String contractclass) {
		this.contractclass = contractclass;
	}
	
	@ExcelField(title="计划收款金额", align=2, sort=11)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@ExcelField(title="客户名称", align=2, sort=12)
	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
	
	@ExcelField(title="余额", align=2, sort=13)
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	@ExcelField(title="完成比例", align=2, sort=14)
	public String getCompleterate() {
		return completerate;
	}

	public void setCompleterate(String completerate) {
		this.completerate = completerate;
	}
	
	@ExcelField(title="逾期", align=2, sort=15)
	public String getOverdue() {
		return overdue;
	}

	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}
	
	@ExcelField(title="客户类别", align=2, sort=16)
	public String getCustomerclass() {
		return customerclass;
	}

	public void setCustomerclass(String customerclass) {
		this.customerclass = customerclass;
	}
	
	@ExcelField(title="币种", align=2, sort=17)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@ExcelField(title="完成金额", align=2, sort=18)
	public String getCompletemoney() {
		return completemoney;
	}

	public void setCompletemoney(String completemoney) {
		this.completemoney = completemoney;
	}
	
	@ExcelField(title="结算方式", align=2, sort=19)
	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	
	@ExcelField(title="备注", align=2, sort=20)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getPlancompletiondate() {
		return plancompletiondate;
	}

	public void setPlancompletiondate(Date plancompletiondate) {
		this.plancompletiondate = plancompletiondate;
	}

	public Date getCompletedate() {
		return completedate;
	}

	public void setCompletedate(Date completedate) {
		this.completedate = completedate;
	}

	public String getFundnatureid() {
		return fundnatureid;
	}

	public void setFundnatureid(String fundnatureid) {
		this.fundnatureid = fundnatureid;
	}

	public String getContractid() {
		return contractid;
	}

	public void setContractid(String contractid) {
		this.contractid = contractid;
	}

	public String getSettlementid() {
		return settlementid;
	}

	public void setSettlementid(String settlementid) {
		this.settlementid = settlementid;
	}

	public String getContractclassid() {
		return contractclassid;
	}

	public void setContractclassid(String contractclassid) {
		this.contractclassid = contractclassid;
	}
	
}