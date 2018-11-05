/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.entity;


import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;

/**
 * 岗位培训Entity
 * @author yc
 * @version 2017-10-26
 */
public class Posttrain extends DataEntity<Posttrain> {
	
	private static final long serialVersionUID = 1L;
	private String depart;		// 部门
	private String company;		// 公司
	private String office;		// 岗位
	private String trainer;		// 参加培训者
	private String title;		// 培训标题
	private String address;		// 培训地点
	private String organizer;		// 主办机构
	private Date traintime;		// 培训时间
	private String content;		// 培训内容
	private String summary;		// 培训总结
	private String trainplan;		// 培训计划id
	
	public Posttrain() {
		super();
	}

	public Posttrain(String id){
		super(id);
	}

	@ExcelField(title="部门", align=2, sort=7)
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}
	
	@ExcelField(title="参加培训者", align=2, sort=8)
	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
	
	@ExcelField(title="培训标题", align=2, sort=9)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="培训地点", align=2, sort=10)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="主办机构", align=2, sort=11)
	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	
	@ExcelField(title="培训时间", align=2, sort=12)
	public Date getTraintime() {
		return traintime;
	}

	public void setTraintime(Date traintime) {
		this.traintime = traintime;
	}
	
	@ExcelField(title="培训内容", align=2, sort=13)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="培训总结", align=2, sort=14)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getTrainplan() {
		return trainplan;
	}

	public void setTrainplan(String trainplan) {
		this.trainplan = trainplan;
	}
}