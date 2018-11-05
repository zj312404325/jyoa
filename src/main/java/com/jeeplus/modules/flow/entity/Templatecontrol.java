/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 模板主表Entity
 * @author cqj
 * @version 2016-12-09
 */
public class Templatecontrol extends DataEntity<Templatecontrol> {
	
	private static final long serialVersionUID = 1L;
	private Flowtemplate flowtemplate;		// 外键 父类
	private String columnname;		// 字段名称
	private String columnid;		// 唯一id
	private String columntype;		// 字段类型
	private String columnvalue;		// 选择框值
	private Integer columnsort;		// 排序
	private Integer columnlocate;
	private Integer valuerequire;//是否必填1必填2不必填
	
	public Templatecontrol() {
		super();
	}

	public Templatecontrol(String id){
		super(id);
	}

	public Templatecontrol(Flowtemplate flowtemplate){
		this.flowtemplate = flowtemplate;
	}

	public Flowtemplate getFlowtemplate() {
		return flowtemplate;
	}

	public void setFlowtemplate(Flowtemplate flowtemplate) {
		this.flowtemplate = flowtemplate;
	}
	
	@ExcelField(title="字段名称", align=2, sort=8)
	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	
	@ExcelField(title="唯一id", align=2, sort=9)
	public String getColumnid() {
		return columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}
	
	@ExcelField(title="字段类型", align=2, sort=10)
	public String getColumntype() {
		return columntype;
	}

	public void setColumntype(String columntype) {
		this.columntype = columntype;
	}
	
	@ExcelField(title="选择框值", align=2, sort=11)
	public String getColumnvalue() {
		return columnvalue;
	}

	public void setColumnvalue(String columnvalue) {
		this.columnvalue = columnvalue;
	}
	
	@ExcelField(title="排序", align=2, sort=12)
	public Integer getColumnsort() {
		return columnsort;
	}

	public void setColumnsort(Integer columnsort) {
		this.columnsort = columnsort;
	}

	public Integer getColumnlocate() {
		return columnlocate;
	}

	public void setColumnlocate(Integer columnlocate) {
		this.columnlocate = columnlocate;
	}

	public Integer getValuerequire() {
		return valuerequire;
	}

	public void setValuerequire(Integer valuerequire) {
		this.valuerequire = valuerequire;
	}
}