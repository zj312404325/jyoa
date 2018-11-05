/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 项目管理Entity
 * @author cqj
 * @version 2017-12-05
 */
public class ProjectMemo extends DataEntity<ProjectMemo> {
	
	private static final long serialVersionUID = 1L;
	private ProjectManage project;		// 项目主键 父类
	private String matter;		// 说明事项
	private String content;		// 内容
	private String createusername;		// 创建者姓名
	
	public ProjectMemo() {
		super();
	}

	public ProjectMemo(String id){
		super(id);
	}

	public ProjectMemo(ProjectManage project){
		this.project = project;
	}

	public ProjectManage getProject() {
		return project;
	}

	public void setProject(ProjectManage project) {
		this.project = project;
	}
	
	@ExcelField(title="说明事项", align=2, sort=8)
	public String getMatter() {
		return matter;
	}

	public void setMatter(String matter) {
		this.matter = matter;
	}
	
	@ExcelField(title="内容", align=2, sort=9)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="创建者姓名", align=2, sort=10)
	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	
}