/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.TreeEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 档案管理Entity
 * @author yc
 * @version 2017-11-24
 */
public class ArchiveManager extends TreeEntity<ArchiveManager> {
	
	private static final long serialVersionUID = 1L;
	private String topparentid;		// 顶父级编号
//	private ArchiveManager parent;		// 父级编号
	private String parentIds;		// 所有父级编号
//	private String name;		// 名称
//	private Integer sort;		// 排序
	
	public ArchiveManager() {
		super();
	}

	public ArchiveManager(String id){
		super(id);
	}

	@ExcelField(title="顶父级编号", align=2, sort=7)
	public String getTopparentid() {
		return topparentid;
	}

	public void setTopparentid(String topparentid) {
		this.topparentid = topparentid;
	}
	
	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	@ExcelField(title="父级编号", align=2, sort=8)
	public ArchiveManager getParent() {
		return parent;
	}

	public void setParent(ArchiveManager parent) {
		this.parent = parent;
	}
	
	@ExcelField(title="所有父级编号", align=2, sort=9)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
//	@ExcelField(title="名称", align=2, sort=10)
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//	@NotNull(message="排序不能为空")
//	@ExcelField(title="排序", align=2, sort=11)
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}
	
	private Integer total;
	private Integer monthTotal;	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getMonthTotal() {
		return monthTotal;
	}
	public void setMonthTotal(Integer monthTotal) {
		this.monthTotal = monthTotal;
	}

	private String isCurrentMonth;		// 临时存放
	public String getIsCurrentMonth() {
		return isCurrentMonth;
	}
	public void setIsCurrentMonth(String isCurrentMonth) {
		this.isCurrentMonth = isCurrentMonth;
	}
	
}