/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.entity;

import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 绩效考核填写Entity
 * @author cqj
 * @version 2017-10-25
 */
public class PerformanceCheck extends DataEntity<PerformanceCheck> {
	
	private static final long serialVersionUID = 1L;
	private String officeid;		// 部门id
	private String officename;		// 部门名称
	private String stationid;		// 岗位id
	private String stationname;		// 岗位名称
	private Integer selfscore;		// 自评得分
	private String checkyear;		// 年份
	private String checkquarter;		// 季度
	private String centralplan;		// 计划改进
	private String userid;		// 用户id
	private String userno;		// 用户编号
	private String username;		// 用户姓名
	private Integer score;		// 考核得分
	private List<PerformanceCheckDetail> performanceCheckDetailList = Lists.newArrayList();		// 子表列表
	private List<PerformanceCheckDetail> performanceCheckDetailListKey = Lists.newArrayList();		// 子表列表
	private String type;
	private String stationIds; //考核岗位
	private Date startdate;//开始时间（查询用）
	private Date enddate;//结束时间
	private String category;//null或0表示绩效考核，1表转正考核
	private String fileurl;//转正报告url
	private String sqlstr;
	
	public PerformanceCheck() {
		super();
	}

	public PerformanceCheck(String id){
		super(id);
	}

	@ExcelField(title="部门id", align=2, sort=7)
	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
	
	@ExcelField(title="部门名称", align=2, sort=8)
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
	
	@ExcelField(title="岗位名称", align=2, sort=10)
	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}
	
	@NotNull(message="自评得分不能为空")
	@ExcelField(title="自评得分", align=2, sort=11)
	public Integer getSelfscore() {
		return selfscore;
	}

	public void setSelfscore(Integer selfscore) {
		this.selfscore = selfscore;
	}
	
	@ExcelField(title="年份", align=2, sort=12)
	public String getCheckyear() {
		return checkyear;
	}

	public void setCheckyear(String checkyear) {
		this.checkyear = checkyear;
	}
	
	@ExcelField(title="季度", dictType="", align=2, sort=13)
	public String getCheckquarter() {
		return checkquarter;
	}

	public void setCheckquarter(String checkquarter) {
		this.checkquarter = checkquarter;
	}
	
	@ExcelField(title="计划改进", align=2, sort=14)
	public String getCentralplan() {
		return centralplan;
	}

	public void setCentralplan(String centralplan) {
		this.centralplan = centralplan;
	}
	
	@ExcelField(title="用户id", align=2, sort=15)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@ExcelField(title="用户编号", align=2, sort=16)
	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}
	
	@ExcelField(title="用户姓名", align=2, sort=17)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title="考核得分", align=2, sort=18)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public List<PerformanceCheckDetail> getPerformanceCheckDetailList() {
		return performanceCheckDetailList;
	}

	public void setPerformanceCheckDetailList(List<PerformanceCheckDetail> performanceCheckDetailList) {
		this.performanceCheckDetailList = performanceCheckDetailList;
	}

	public List<PerformanceCheckDetail> getPerformanceCheckDetailListKey() {
		return performanceCheckDetailListKey;
	}

	public void setPerformanceCheckDetailListKey(
			List<PerformanceCheckDetail> performanceCheckDetailListKey) {
		this.performanceCheckDetailListKey = performanceCheckDetailListKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public String getSqlstr() {
		return sqlstr;
	}

	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}
}