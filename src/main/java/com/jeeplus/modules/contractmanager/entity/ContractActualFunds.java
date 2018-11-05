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
public class ContractActualFunds extends DataEntity<ContractActualFunds> {
	
	private static final long serialVersionUID = 1L;
	private ContractManager contractmanager;		// 主表id 父类
	private String money;		// 资金金额
	private String billno;		// 票据号码
	private String settlementid;		// 结算方式id
	private String settlement;		// 结算方式
	private Date settlementdate;		// 结算日期
	private String remark1;		// 备注1
	private String remark2;		// 备注2
	
	public ContractActualFunds() {
		super();
	}

	public ContractActualFunds(String id){
		super(id);
	}

	public ContractActualFunds(ContractManager contractmanager){
		this.contractmanager = contractmanager;
	}

	public ContractManager getContractmanager() {
		return contractmanager;
	}

	public void setContractmanager(ContractManager contractmanager) {
		this.contractmanager = contractmanager;
	}
	
	@ExcelField(title="资金金额", align=2, sort=8)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@ExcelField(title="票据号码", align=2, sort=9)
	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}
	
	@ExcelField(title="结算方式id", dictType="", align=2, sort=10)
	public String getSettlementid() {
		return settlementid;
	}

	public void setSettlementid(String settlementid) {
		this.settlementid = settlementid;
	}
	
	@ExcelField(title="结算方式", align=2, sort=11)
	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	@ExcelField(title="结算日期", align=2, sort=12)
	public Date getSettlementdate() {
		return settlementdate;
	}

	public void setSettlementdate(Date settlementdate) {
		this.settlementdate = settlementdate;
	}
	
	@ExcelField(title="备注1", align=2, sort=13)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="备注2", align=2, sort=14)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
}