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
public class ContractSubject extends DataEntity<ContractSubject> {
	
	private static final long serialVersionUID = 1L;
	private ContractManager contractmanager;		// 主表id 父类
	private String subjectname;		// 名称
	private String model;		// 型号
	private String specification;		// 规格
	private String quantity;		// 数量
	private String unit;		// 单位
	private String unitprice;		// 单价
	private String subtotal;		// 小计
	private String remark;		// 备注
	
	public ContractSubject() {
		super();
	}

	public ContractSubject(String id){
		super(id);
	}

	public ContractSubject(ContractManager contractmanager){
		this.contractmanager = contractmanager;
	}

	public ContractManager getContractmanager() {
		return contractmanager;
	}

	public void setContractmanager(ContractManager contractmanager) {
		this.contractmanager = contractmanager;
	}
	
	@ExcelField(title="名称", align=2, sort=8)
	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}
	
	@ExcelField(title="型号", align=2, sort=9)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	@ExcelField(title="规格", align=2, sort=10)
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	@ExcelField(title="数量", align=2, sort=11)
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	@ExcelField(title="单位", align=2, sort=12)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="单价", align=2, sort=13)
	public String getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}
	
	@ExcelField(title="小计", align=2, sort=14)
	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	
	@ExcelField(title="备注", align=2, sort=15)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}