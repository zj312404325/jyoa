/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 考核时间Entity
 * @author cqj
 * @version 2017-10-23
 */
public class CheckTime extends DataEntity<CheckTime> {
	
	private static final long serialVersionUID = 1L;
	private String checkYear;		// 年份
	private String checkQuarter;		// 季度
	private Date startDate;		// 考核开始时间
	private Date enddate;		// 考核结束时间
	private String createusername;		// 创建人
	private String updateusername;		// 修改人
	
	public CheckTime() {
		super();
	}

	public CheckTime(String id){
		super(id);
	}

	@ExcelField(title="年份", align=2, sort=7)
	public String getCheckYear() {
		return checkYear;
	}

	public void setCheckYear(String checkYear) {
		this.checkYear = checkYear;
	}
	
	@ExcelField(title="季度", align=2, sort=8)
	public String getCheckQuarter() {
		return checkQuarter;
	}

	public void setCheckQuarter(String checkQuarter) {
		this.checkQuarter = checkQuarter;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="考核开始时间", align=2, sort=9)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="考核结束时间", align=2, sort=10)
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	@ExcelField(title="创建人", align=2, sort=11)
	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	
	@ExcelField(title="修改人", align=2, sort=12)
	public String getUpdateusername() {
		return updateusername;
	}

	public void setUpdateusername(String updateusername) {
		this.updateusername = updateusername;
	}
	
}