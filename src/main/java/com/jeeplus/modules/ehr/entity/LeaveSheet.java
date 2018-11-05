/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;

/**
 * 离职申请单管理Entity
 * @author yc
 * @version 2017-11-02
 */
public class LeaveSheet extends DataEntity<LeaveSheet> {
	
	private static final long serialVersionUID = 1L;
	private String companyid;		// 公司id
	private String sheeturl;		// 路径
	
	private Office company;

	private String receivedeptids;		// 授权部门ids
	private String receivedeptnames;
	
	public LeaveSheet() {
		super();
	}

	public LeaveSheet(String id){
		super(id);
	}

	@ExcelField(title="公司id", align=2, sort=7)
	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	
	@ExcelField(title="路径", align=2, sort=8)
	public String getSheeturl() {
		return sheeturl;
	}

	public void setSheeturl(String sheeturl) {
		this.sheeturl = sheeturl;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getReceivedeptids() {
		return receivedeptids;
	}

	public void setReceivedeptids(String receivedeptids) {
		this.receivedeptids = receivedeptids;
	}

	public String getReceivedeptnames() {
		return receivedeptnames;
	}

	public void setReceivedeptnames(String receivedeptnames) {
		this.receivedeptnames = receivedeptnames;
	}
}