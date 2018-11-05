/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 离职审计Entity
 * @author cqj
 * @version 2018-03-12
 */
public class Leaveaudit extends DataEntity<Leaveaudit> {
	
	private static final long serialVersionUID = 1L;
	private String leaverid;
	private String leaver;		// 离职人姓名
	private String officeid;
	private String officename;		// 离职人部门
	private String stationid;
	private String stationname;		// 离职人岗位
	private Date entrydate;		// 入职日期
	private Date leavedate;		// 离职日期
	private String leaveurl;		// 离职审计报告
	private String createusername;		// 审计提交人
	private String isadmin;
	private String bhv;
	private Date startdate;//开始时间（查询用）
	private Date enddate;//结束时间
	
	public Leaveaudit() {
		super();
	}

	public Leaveaudit(String id){
		super(id);
	}

	@ExcelField(title="离职人姓名", align=2, sort=7)
	public String getLeaver() {
		return leaver;
	}

	public void setLeaver(String leaver) {
		this.leaver = leaver;
	}
	
	@ExcelField(title="离职人部门", align=2, sort=8)
	public String getOfficename() {
		return officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}
	
	@ExcelField(title="离职人岗位", align=2, sort=9)
	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="入职日期不能为空")
	@ExcelField(title="入职日期", align=2, sort=10)
	public Date getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="离职日期不能为空")
	@ExcelField(title="离职日期", align=2, sort=11)
	public Date getLeavedate() {
		return leavedate;
	}

	public void setLeavedate(Date leavedate) {
		this.leavedate = leavedate;
	}
	
	@ExcelField(title="离职审计报告", align=2, sort=12)
	public String getLeaveurl() {
		return leaveurl;
	}

	public void setLeaveurl(String leaveurl) {
		this.leaveurl = leaveurl;
	}
	
	@ExcelField(title="审计提交人", align=2, sort=13)
	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public String getBhv() {
		return bhv;
	}

	public void setBhv(String bhv) {
		this.bhv = bhv;
	}

	public String getLeaverid() {
		return leaverid;
	}

	public void setLeaverid(String leaverid) {
		this.leaverid = leaverid;
	}

	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}

	public String getStationid() {
		return stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
}