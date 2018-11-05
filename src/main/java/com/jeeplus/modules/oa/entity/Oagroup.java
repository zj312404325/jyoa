/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.entity;


import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * oa传阅人员分组Entity
 * @author 殷
 * @version 2017-01-06
 */
public class Oagroup extends DataEntity<Oagroup> {
	
	private static final long serialVersionUID = 1L;
	private String groupname;		// 组名
	private List<Oagroupdtl> oagroupdtlList = Lists.newArrayList();
	private int grouptype = 0;
	
	public Oagroup() {
		super();
	}

	public Oagroup(String id){
		super(id);
	}

	@ExcelField(title="组名", align=2, sort=9)
	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public List<Oagroupdtl> getOagroupdtlList() {
		return oagroupdtlList;
	}

	public void setOagroupdtlList(List<Oagroupdtl> oagroupdtlList) {
		this.oagroupdtlList = oagroupdtlList;
	}
	
	
	public String getIds() {
		return Collections3.extractToString(oagroupdtlList, "groupuser.id", ",");
	}
    
	public String getNames() {
		return Collections3.extractToString(oagroupdtlList, "groupuser.name", ",");
	}

	public int getGrouptype() {
		return grouptype;
	}

	public void setGrouptype(int grouptype) {
		this.grouptype = grouptype;
	}
}