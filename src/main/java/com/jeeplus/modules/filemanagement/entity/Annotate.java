/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 档案管理Entity
 * @author cqj
 * @version 2017-11-24
 */
public class Annotate extends DataEntity<Annotate> {
	
	private static final long serialVersionUID = 1L;
	private String createusername;		// 创建者姓名
	private String content;		// 内容
	private FileManagement filemanagement;		// 档案id 父类
	
	public Annotate() {
		super();
	}

	public Annotate(String id){
		super(id);
	}

	public Annotate(FileManagement filemanagement){
		this.filemanagement = filemanagement;
	}

	@ExcelField(title="创建者姓名", align=2, sort=7)
	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	
	@ExcelField(title="内容", align=2, sort=8)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public FileManagement getFilemanagement() {
		return filemanagement;
	}

	public void setFilemanagement(FileManagement filemanagement) {
		this.filemanagement = filemanagement;
	}
	
}