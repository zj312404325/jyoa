/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.entity;


import java.util.ArrayList;
import java.util.List;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 我的申请Entity
 * @author cqj
 * @version 2016-12-08
 */
public class Templatecontent extends DataEntity<Templatecontent> {
	
	private static final long serialVersionUID = 1L;
	private String controlid;		// 控件id
	private String columnname;		// 字段名称
	private String columnvalue;		// 字段值
	private String columntype;
	private Integer columnsort;		// 排序行
	private Flowapply flowapply;		// 外键 父类
	private List<Tmplatefile> tmplatefile=new ArrayList<Tmplatefile>();
	private Integer columnlocate;		//排序列
	
	public Templatecontent() {
		super();
	}

	public Templatecontent(String id){
		super(id);
	}

	public Templatecontent(Flowapply flowapply){
		this.flowapply = flowapply;
	}

	@ExcelField(title="控件id", align=2, sort=7)
	public String getControlid() {
		return controlid;
	}

	public void setControlid(String controlid) {
		this.controlid = controlid;
	}
	
	@ExcelField(title="字段名称", align=2, sort=8)
	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	
	@ExcelField(title="字段值", align=2, sort=9)
	public String getColumnvalue() {
		return columnvalue;
	}

	public void setColumnvalue(String columnvalue) {
		this.columnvalue = columnvalue;
	}
	
	@ExcelField(title="排序", align=2, sort=10)
	public Integer getColumnsort() {
		return columnsort;
	}

	public void setColumnsort(Integer columnsort) {
		this.columnsort = columnsort;
	}
	
	public Flowapply getFlowapply() {
		return flowapply;
	}

	public void setFlowapply(Flowapply flowapply) {
		this.flowapply = flowapply;
	}

	public String getColumntype() {
		return columntype;
	}

	public void setColumntype(String columntype) {
		this.columntype = columntype;
	}

	public List<Tmplatefile> getTmplatefile() {
		return tmplatefile;
	}

	public void setTmplatefile(List<Tmplatefile> tmplatefile) {
		this.tmplatefile = tmplatefile;
	}

	public Integer getColumnlocate() {
		return columnlocate;
	}

	public void setColumnlocate(Integer columnlocate) {
		this.columnlocate = columnlocate;
	}
	
	
}