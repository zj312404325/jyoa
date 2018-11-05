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
 * @version 2017-12-07
 */
public class ContractPlanfund extends DataEntity<ContractPlanfund> {
	
	private static final long serialVersionUID = 1L;
	private String planfundname;		// 名称
	private Date plancompletiondate;		// 计划完成日期
	private String  money;		// 金额
	private String settlementid;		// 结算方式id
	private String settlement;		// 结算方式
	private String remark;		// 备注
	private ContractManager contractmanager;		// 主表id 父类
	
	public ContractPlanfund() {
		super();
	}

	public ContractPlanfund(String id){
		super(id);
	}

	public ContractPlanfund(ContractManager contractmanager){
		this.contractmanager = contractmanager;
	}

	@ExcelField(title="名称", align=2, sort=7)
	public String getPlanfundname() {
		return planfundname;
	}

	public void setPlanfundname(String planfundname) {
		this.planfundname = planfundname;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	@ExcelField(title="计划完成日期", align=2, sort=8)
	public Date getPlancompletiondate() {
		return plancompletiondate;
	}

	public void setPlancompletiondate(Date plancompletiondate) {
		this.plancompletiondate = plancompletiondate;
	}
	
	@ExcelField(title="金额", align=2, sort=9)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
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
	
	@ExcelField(title="备注", align=2, sort=12)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public ContractManager getContractmanager() {
		return contractmanager;
	}

	public void setContractmanager(ContractManager contractmanager) {
		this.contractmanager = contractmanager;
	}
	
}