/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.User;

/**
 * oa传阅人员分组Entity
 * @author 殷
 * @version 2017-01-20
 */
public class Oagroupdtl extends DataEntity<Oagroupdtl> {
	
	private static final long serialVersionUID = 1L;
	private Oagroup oagroup;		// 主表id 父类
	private User groupuser;		// 组员
	
	public Oagroupdtl() {
		super();
	}

	public Oagroupdtl(String id){
		super(id);
	}
	
	public Oagroupdtl(User groupuser){
		this.groupuser = groupuser;
	}

	public Oagroupdtl(Oagroup oagroup){
		this.oagroup = oagroup;
	}

	public Oagroup getOagroup() {
		return oagroup;
	}

	public void setOagroup(Oagroup oagroup) {
		this.oagroup = oagroup;
	}

	public User getGroupuser() {
		return groupuser;
	}

	public void setGroupuser(User groupuser) {
		this.groupuser = groupuser;
	}
	
	
}