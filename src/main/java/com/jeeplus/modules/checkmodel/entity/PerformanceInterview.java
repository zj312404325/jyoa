/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 绩效考核面谈表Entity
 * @author cqj
 * @version 2017-10-27
 */
public class PerformanceInterview extends DataEntity<PerformanceInterview> {
	
	private static final long serialVersionUID = 1L;
	private String officeid;		// 部门id
	private String officename;		// 部门
	private String stationid;		// 岗位id
	private String stationname;		// 岗位
	private String checkyear;		// 年份
	private String checkquarter;		// 季度
	private String place;		// 面谈地点
	private Date writedate;		// 填表日期
	private String work;		// 本月主要工作
	private String achievement;		// 本月主要工作业绩
	private String uncompletereason;		// 本月未达成原因
	private String favorreason;		// 有利于工作完成
	private String plan;		// 下月工作计划
	private String userid;		// 员工id
	private String userno;		// 员工编号
	private String username;		// 员工姓名
	private String stationIds; //考核岗位
	private Date startdate;//开始时间（查询用）
	private Date enddate;//结束时间
	private String sqlstr;
	private String overachievement;		// 本月突出业绩
	private String development;		// 职业发展建议
	private String interviewcomment;		// 面谈改进建议
	
	public PerformanceInterview() {
		super();
	}

	public PerformanceInterview(String id){
		super(id);
	}

	@ExcelField(title="部门id", align=2, sort=7)
	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
	
	@ExcelField(title="部门", align=2, sort=8)
	public String getOfficename() {
		return officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}
	
	@ExcelField(title="岗位id", align=2, sort=9)
	public String getStationid() {
		return stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}
	
	@ExcelField(title="岗位", align=2, sort=10)
	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}
	
	@ExcelField(title="年份", align=2, sort=11)
	public String getCheckyear() {
		return checkyear;
	}

	public void setCheckyear(String checkyear) {
		this.checkyear = checkyear;
	}
	
	@ExcelField(title="季度", dictType="", align=2, sort=12)
	public String getCheckquarter() {
		return checkquarter;
	}

	public void setCheckquarter(String checkquarter) {
		this.checkquarter = checkquarter;
	}
	
	@ExcelField(title="面谈地点", align=2, sort=13)
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="填表日期不能为空")
	@ExcelField(title="填表日期", align=2, sort=14)
	public Date getWritedate() {
		return writedate;
	}

	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
	
	@ExcelField(title="本月主要工作", align=2, sort=15)
	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}
	
	@ExcelField(title="本月主要工作业绩", align=2, sort=16)
	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	
	@ExcelField(title="本月未达成原因", align=2, sort=17)
	public String getUncompletereason() {
		return uncompletereason;
	}

	public void setUncompletereason(String uncompletereason) {
		this.uncompletereason = uncompletereason;
	}
	
	@ExcelField(title="有利于工作完成", align=2, sort=18)
	public String getFavorreason() {
		return favorreason;
	}

	public void setFavorreason(String favorreason) {
		this.favorreason = favorreason;
	}
	
	@ExcelField(title="下月工作计划", align=2, sort=19)
	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}
	
	@ExcelField(title="员工id", align=2, sort=20)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@ExcelField(title="员工编号", align=2, sort=21)
	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}
	
	@ExcelField(title="员工姓名", align=2, sort=22)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStationIds() {
		return stationIds;
	}

	public void setStationIds(String stationIds) {
		this.stationIds = stationIds;
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

	public String getSqlstr() {
		return sqlstr;
	}

	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}

	public String getOverachievement() {
		return overachievement;
	}

	public void setOverachievement(String overachievement) {
		this.overachievement = overachievement;
	}

	public String getDevelopment() {
		return development;
	}

	public void setDevelopment(String development) {
		this.development = development;
	}

	public String getInterviewcomment() {
		return interviewcomment;
	}

	public void setInterviewcomment(String interviewcomment) {
		this.interviewcomment = interviewcomment;
	}
}