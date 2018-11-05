/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.entity;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 模板主表Entity
 * @author cqj
 * @version 2016-12-09
 */
public class Flowtemplate extends DataEntity<Flowtemplate> {
	
	private static final long serialVersionUID = 1L;
	private String flowremarks;		// 备注信息
	private String templatename;		// 模板名称
	private String templatehtml;		// html代码
	private Integer showcolumn;		// 显示列数
	private Integer templatetype=0;
	private List<Templatecontrol> templatecontrolList = Lists.newArrayList();		// 子表列表
	
	public Flowtemplate() {
		super();
	}

	public Flowtemplate(String id){
		super(id);
	}

	@ExcelField(title="备注信息", align=2, sort=5)
	public String getFlowremarks() {
		return flowremarks;
	}

	public void setFlowremarks(String flowremarks) {
		this.flowremarks = flowremarks;
	}
	
	@ExcelField(title="模板名称", align=2, sort=7)
	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
	
	@ExcelField(title="html代码", align=2, sort=8)
	public String getTemplatehtml() {
		return templatehtml;
	}

	public void setTemplatehtml(String templatehtml) {
		this.templatehtml = templatehtml;
	}
	
	@ExcelField(title="显示列数", align=2, sort=9)
	public Integer getShowcolumn() {
		return showcolumn;
	}

	public void setShowcolumn(Integer showcolumn) {
		this.showcolumn = showcolumn;
	}
	
	public List<Templatecontrol> getTemplatecontrolList() {
		return templatecontrolList;
	}

	public void setTemplatecontrolList(List<Templatecontrol> templatecontrolList) {
		this.templatecontrolList = templatecontrolList;
	}

	public Integer getTemplatetype() {
		return templatetype;
	}

	public void setTemplatetype(Integer templatetype) {
		this.templatetype = templatetype;
	}
}