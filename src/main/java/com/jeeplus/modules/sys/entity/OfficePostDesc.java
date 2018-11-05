/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 职务说明Entity
 * @author yc
 * @version 2018-03-05
 */
public class OfficePostDesc extends DataEntity<OfficePostDesc> {
	
	private static final long serialVersionUID = 1L;
	private String file1;		// 职务说明书
	private String file2;		// 绩效考核指标
	private Office office;		// 部门id
	private Post post;		// 岗位id
	
	public OfficePostDesc() {
		super();
	}

	public OfficePostDesc(String id){
		super(id);
	}

	@ExcelField(title="职务说明书", align=2, sort=7)
	public String getFile1() {
		return file1;
	}

	public void setFile1(String file1) {
		this.file1 = file1;
	}
	
	@ExcelField(title="绩效考核指标", align=2, sort=8)
	public String getFile2() {
		return file2;
	}

	public void setFile2(String file2) {
		this.file2 = file2;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}