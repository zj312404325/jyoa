/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 项目管理Entity
 * @author cqj
 * @version 2017-12-05
 */
public class ProjectTime extends DataEntity<ProjectTime> {
	
	private static final long serialVersionUID = 1L;
	private ProjectManage project;		// 项目主键 父类
	private String matter;		// 事项
	private Date planstarttime;		// 计划开始日期
	private Date planendtime;		// 计划结束时间
	private String memo;		// 备注
	private String createusername;		// 创建者姓名
	
	public ProjectTime() {
		super();
	}

	public ProjectTime(String id){
		super(id);
	}

	public ProjectTime(ProjectManage project){
		this.project = project;
	}

	public ProjectManage getProject() {
		return project;
	}

	public void setProject(ProjectManage project) {
		this.project = project;
	}
	
	@ExcelField(title="事项", align=2, sort=8)
	public String getMatter() {
		return matter;
	}

	public void setMatter(String matter) {
		this.matter = matter;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	@NotNull(message="计划开始日期不能为空")
	@ExcelField(title="计划开始日期", align=2, sort=9)
	public Date getPlanstarttime() {
		return planstarttime;
	}

	public void setPlanstarttime(Date planstarttime) {
		this.planstarttime = planstarttime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	@NotNull(message="计划结束时间不能为空")
	@ExcelField(title="计划结束时间", align=2, sort=10)
	public Date getPlanendtime() {
		return planendtime;
	}

	public void setPlanendtime(Date planendtime) {
		this.planendtime = planendtime;
	}
	
	@ExcelField(title="备注", align=2, sort=11)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@ExcelField(title="创建者姓名", align=2, sort=12)
	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	
}