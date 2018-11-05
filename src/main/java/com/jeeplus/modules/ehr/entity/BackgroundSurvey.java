/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 背景调查Entity
 * @author yc
 * @version 2017-10-25
 */
public class BackgroundSurvey extends DataEntity<BackgroundSurvey> {
	
	private static final long serialVersionUID = 1L;
	private String surveyname;		// 被调查人
	private String age;		// 年龄
	private String sex;		// 性别
	private String depart;		// 部门
	private String position;		// 岗位
	private String entrydate;		// 入职时间
	private String surveydate;		// 调查时间
	private String operater;		// 调查人
	private String surveyresult;		// 调查结果
	private String hradvice;		// hr建议
	private String surveyfiles;		// 附件
	
	public BackgroundSurvey() {
		super();
	}

	public BackgroundSurvey(String id){
		super(id);
	}

	@ExcelField(title="被调查人", align=2, sort=7)
	public String getSurveyname() {
		return surveyname;
	}

	public void setSurveyname(String surveyname) {
		this.surveyname = surveyname;
	}
	
	@ExcelField(title="年龄", align=2, sort=8)
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	@ExcelField(title="性别", align=2, sort=9)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@ExcelField(title="部门", align=2, sort=10)
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}
	
	@ExcelField(title="岗位", align=2, sort=11)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@ExcelField(title="入职时间", align=2, sort=12)
	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}
	
	@ExcelField(title="调查时间", align=2, sort=13)
	public String getSurveydate() {
		return surveydate;
	}

	public void setSurveydate(String surveydate) {
		this.surveydate = surveydate;
	}
	
	@ExcelField(title="调查人", align=2, sort=14)
	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}
	
	@ExcelField(title="调查结果", align=2, sort=15)
	public String getSurveyresult() {
		return surveyresult;
	}

	public void setSurveyresult(String surveyresult) {
		this.surveyresult = surveyresult;
	}
	
	@ExcelField(title="hr建议", align=2, sort=16)
	public String getHradvice() {
		return hradvice;
	}

	public void setHradvice(String hradvice) {
		this.hradvice = hradvice;
	}
	
	@ExcelField(title="附件", align=2, sort=17)
	public String getSurveyfiles() {
		return surveyfiles;
	}

	public void setSurveyfiles(String surveyfiles) {
		this.surveyfiles = surveyfiles;
	}
	
}