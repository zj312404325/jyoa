/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 培训计划Entity
 * @author cqj
 * @version 2018-05-25
 */
public class Trainee extends DataEntity<Trainee> {
	
	private static final long serialVersionUID = 1L;
	private Trainplan trainplan;		// 培训计划id 父类
	private String userid;		// 培训对象id
	private String username;		// 培训对象
	private String officeid;		// 部门id
	private String officename;		// 部门名称
	
	public Trainee() {
		super();
	}

	public Trainee(String id){
		super(id);
	}

	public Trainee(Trainplan trainplan){
		this.trainplan = trainplan;
	}

	public Trainplan getTrainplan() {
		return trainplan;
	}

	public void setTrainplan(Trainplan trainplan) {
		this.trainplan = trainplan;
	}
	
	@ExcelField(title="培训对象id", align=2, sort=8)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@ExcelField(title="培训对象", fieldType=String.class, value="", align=2, sort=9)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title="部门id", align=2, sort=10)
	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
	
	@ExcelField(title="部门名称", fieldType=String.class, value="", align=2, sort=11)
	public String getOfficename() {
		return officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}
	
}