/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 绩效考核填写Entity
 * @author cqj
 * @version 2017-10-25
 */
public class PerformanceCheckDetail extends DataEntity<PerformanceCheckDetail> {
	
	private static final long serialVersionUID = 1L;
	private String kpi;		// 本期工作计划及指标
	private String referencepoint;		// 绩效衡量标准
	private Integer weight;		// 权重
	private Integer type;		// 类型
	private Integer sort;		// 排序
	private PerformanceCheck performancecheckid;		// 主表id 父类
	private String execution;		// 完成情况
	private Integer selfscore;		// 自评得分
	private Integer score;		// 考核得分
	
	public PerformanceCheckDetail() {
		super();
	}

	public PerformanceCheckDetail(String id){
		super(id);
	}

	public PerformanceCheckDetail(PerformanceCheck performancecheckid){
		this.performancecheckid = performancecheckid;
	}

	@ExcelField(title="本期工作计划及指标", align=2, sort=7)
	public String getKpi() {
		return kpi;
	}

	public void setKpi(String kpi) {
		this.kpi = kpi;
	}
	
	@ExcelField(title="绩效衡量标准", align=2, sort=8)
	public String getReferencepoint() {
		return referencepoint;
	}

	public void setReferencepoint(String referencepoint) {
		this.referencepoint = referencepoint;
	}
	
	@NotNull(message="权重不能为空")
	@ExcelField(title="权重", align=2, sort=9)
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	@ExcelField(title="类型", align=2, sort=10)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="排序", align=2, sort=11)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public PerformanceCheck getPerformancecheckid() {
		return performancecheckid;
	}

	public void setPerformancecheckid(PerformanceCheck performancecheckid) {
		this.performancecheckid = performancecheckid;
	}
	
	@ExcelField(title="完成情况", align=2, sort=13)
	public String getExecution() {
		return execution;
	}

	public void setExecution(String execution) {
		this.execution = execution;
	}
	
	@ExcelField(title="自评得分", align=2, sort=14)
	public Integer getSelfscore() {
		return selfscore;
	}

	public void setSelfscore(Integer selfscore) {
		this.selfscore = selfscore;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}