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
public class ProjectAttachment extends DataEntity<ProjectAttachment> {
	
	private static final long serialVersionUID = 1L;
	private ProjectManage project;		// 项目主键 父类
	private String attachmentname;		// 附件名称
	private String attachmentcategory;		// 附件类别
	private String fileurl;		// 文件路径
	private String attachmentmemo;		// 附件说明
	private String createusername;		// 创建者姓名
	
	public ProjectAttachment() {
		super();
	}

	public ProjectAttachment(String id){
		super(id);
	}

	public ProjectAttachment(ProjectManage project){
		this.project = project;
	}

	public ProjectManage getProject() {
		return project;
	}

	public void setProject(ProjectManage project) {
		this.project = project;
	}
	
	@ExcelField(title="附件名称", align=2, sort=8)
	public String getAttachmentname() {
		return attachmentname;
	}

	public void setAttachmentname(String attachmentname) {
		this.attachmentname = attachmentname;
	}
	
	@ExcelField(title="附件类别", align=2, sort=9)
	public String getAttachmentcategory() {
		return attachmentcategory;
	}

	public void setAttachmentcategory(String attachmentcategory) {
		this.attachmentcategory = attachmentcategory;
	}
	
	@ExcelField(title="文件路径", align=2, sort=10)
	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	
	@ExcelField(title="附件说明", align=2, sort=11)
	public String getAttachmentmemo() {
		return attachmentmemo;
	}

	public void setAttachmentmemo(String attachmentmemo) {
		this.attachmentmemo = attachmentmemo;
	}
	
	@ExcelField(title="创建者姓名", align=2, sort=12)
	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	
}