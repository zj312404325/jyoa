/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 绩效数据设定Entity
 * @author cqj
 * @version 2017-10-23
 */
public class CheckDataDetail extends DataEntity<CheckDataDetail> {
	
	private static final long serialVersionUID = 1L;
	private String checkdataid;		// 绩效数据主表id
	private String kpi;		// 关键指标
	private String referencepoint;		// 衡量标准
	private String weight;		// 权重
	private String type;		// 类型
	private int sort=0;
	
	public CheckDataDetail() {
		super();
	}

	public CheckDataDetail(String id){
		super(id);
	}

	@ExcelField(title="绩效数据主表id", align=2, sort=7)
	public String getCheckdataid() {
		return checkdataid;
	}

	public void setCheckdataid(String checkdataid) {
		this.checkdataid = checkdataid;
	}
	
	@ExcelField(title="关键指标", align=2, sort=8)
	public String getKpi() {
		return kpi;
	}

	public void setKpi(String kpi) {
		this.kpi = kpi;
	}
	
	@ExcelField(title="衡量标准", align=2, sort=9)
	public String getReferencepoint() {
		return referencepoint;
	}

	public void setReferencepoint(String referencepoint) {
		this.referencepoint = referencepoint;
	}
	
	@ExcelField(title="权重", align=2, sort=10)
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	@ExcelField(title="类型", align=2, sort=11)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	
}