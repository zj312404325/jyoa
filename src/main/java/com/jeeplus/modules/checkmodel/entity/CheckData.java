/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.entity;


import java.util.List;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 绩效数据设定Entity
 * @author cqj
 * @version 2017-10-23
 */
public class CheckData extends DataEntity<CheckData> {
	
	private static final long serialVersionUID = 1L;
	private String userid;		// 用户id
	private String userno;		// 员工编码
	private String username;		// 员工姓名
	private String officeid;		// 部门ID
	private String officename;		// 部门
	private String stationid;		// 岗位ID
	private String stationname;		// 岗位
	private String detailJson;	//明细jsonArray
	private List<CheckDataDetail> checkDataDetailList;
	
	public CheckData() {
		super();
	}

	public CheckData(String id){
		super(id);
	}

	@ExcelField(title="用户id", align=2, sort=7)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@ExcelField(title="员工编码", align=2, sort=8)
	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}
	
	@ExcelField(title="员工姓名", align=2, sort=9)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title="部门ID", align=2, sort=10)
	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
	
	@ExcelField(title="部门", align=2, sort=11)
	public String getOfficename() {
		return officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}
	
	@ExcelField(title="岗位ID", align=2, sort=12)
	public String getStationid() {
		return stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}
	
	@ExcelField(title="岗位", align=2, sort=13)
	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public List<CheckDataDetail> getCheckDataDetailList() {
		return checkDataDetailList;
	}

	public void setCheckDataDetailList(List<CheckDataDetail> checkDataDetailList) {
		this.checkDataDetailList = checkDataDetailList;
	}
	
}