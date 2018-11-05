/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 岗位Entity
 * @author yc
 * @version 2018-03-05
 */
public class Post extends DataEntity<Post> {
	
	private static final long serialVersionUID = 1L;
	private String postname;		// 岗位名称

	private Office office;

	private OfficePostDesc officePostDesc;
	
	public Post() {
		super();
	}

	public Post(String id){
		super(id);
	}

	public Post(Office office){
		this.office = office;
	}

	@ExcelField(title="岗位名称", align=2, sort=7)
	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}


	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public OfficePostDesc getOfficePostDesc() {
		return officePostDesc;
	}

	public void setOfficePostDesc(OfficePostDesc officePostDesc) {
		this.officePostDesc = officePostDesc;
	}
}