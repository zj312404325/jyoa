/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 离职调查问卷Entity
 * @author cqj
 * @version 2017-11-02
 */
public class QuestionSurvey extends DataEntity<QuestionSurvey> {
	
	private static final long serialVersionUID = 1L;
	private String username;		// 离职人姓名
	private String officename;		// 所在部门
	private String post;		// 职务
	private Date entrytime;		// 入职时间
	private String internalreason;		// 内部原因
	private String externalreason;		// 外部原因
	private String improve;		// 公司改善
	private String statisfaction;		// 满意度
	private String price;		// 部门评价
	private String improvestay;		// 改善留下
	private String managerview="0";
	
	public QuestionSurvey() {
		super();
	}

	public QuestionSurvey(String id){
		super(id);
	}

	@ExcelField(title="离职人姓名", align=2, sort=7)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title="所在部门", align=2, sort=8)
	public String getOfficename() {
		return officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}
	
	@ExcelField(title="职务", align=2, sort=9)
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="入职时间", align=2, sort=10)
	public Date getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}
	
	@ExcelField(title="内部原因", align=2, sort=11)
	public String getInternalreason() {
		return internalreason;
	}

	public void setInternalreason(String internalreason) {
		this.internalreason = internalreason;
	}
	
	@ExcelField(title="外部原因", align=2, sort=12)
	public String getExternalreason() {
		return externalreason;
	}

	public void setExternalreason(String externalreason) {
		this.externalreason = externalreason;
	}
	
	@ExcelField(title="公司改善", align=2, sort=13)
	public String getImprove() {
		return improve;
	}

	public void setImprove(String improve) {
		this.improve = improve;
	}
	
	@ExcelField(title="满意度", align=2, sort=14)
	public String getStatisfaction() {
		return statisfaction;
	}

	public void setStatisfaction(String statisfaction) {
		this.statisfaction = statisfaction;
	}
	
	@ExcelField(title="部门评价", align=2, sort=15)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@ExcelField(title="改善留下", align=2, sort=16)
	public String getImprovestay() {
		return improvestay;
	}

	public void setImprovestay(String improvestay) {
		this.improvestay = improvestay;
	}

	public String getManagerview() {
		return managerview;
	}

	public void setManagerview(String managerview) {
		this.managerview = managerview;
	}
	
}