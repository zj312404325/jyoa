/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.User;

/**
 * 培训计划Entity
 * @author cqj
 * @version 2018-05-25
 */
public class Trainplan extends DataEntity<Trainplan> {
	
	private static final long serialVersionUID = 1L;
	private String userid;		// 培训人id
	private String username;		// 培训人
	private String officeid;		// 部门id
	private String officename;		// 部门
	private Date traindate;		// 培训日期
	private String title;		// 培训标题
	private String status;		// 是否完成
	private String completesituation;		// 完成情况
	private List<Trainee> traineeList = Lists.newArrayList();		// 子表列表
	private String isadmin;//查询用
	private String mview;//查询用
	private String isnotify;//是否已通知0未通知1已通知
    private String trainpeople;//培训对象

	public Trainplan() {
		super();
	}

	public Trainplan(String id){
		super(id);
	}

	@ExcelField(title="培训人id", align=2, sort=7)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@ExcelField(title="培训人", fieldType=String.class, value="", align=2, sort=8)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title="部门id", align=2, sort=9)
	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
	
	@ExcelField(title="部门", fieldType=String.class, value="", align=2, sort=10)
	public String getOfficename() {
		return officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="培训日期不能为空")
	@ExcelField(title="培训日期", align=2, sort=11)
	public Date getTraindate() {
		return traindate;
	}

	public void setTraindate(Date traindate) {
		this.traindate = traindate;
	}
	
	@ExcelField(title="培训标题", align=2, sort=12)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="是否完成", dictType="", align=2, sort=13)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="完成情况", align=2, sort=14)
	public String getCompletesituation() {
		return completesituation;
	}

	public void setCompletesituation(String completesituation) {
		this.completesituation = completesituation;
	}
	
	public List<Trainee> getTraineeList() {
		return traineeList;
	}

	public void setTraineeList(List<Trainee> traineeList) {
		this.traineeList = traineeList;
	}

	public String getTraineeIds() {
		return Collections3.extractToString(traineeList, "userid", ",") ;
	}

	public void setTraineeIds(String trainee) {
		this.traineeList = Lists.newArrayList();
		for (String id : StringUtils.split(trainee, ",")){
			Trainee entity = new Trainee();
			entity.setId(IdGen.uuid());
			entity.setTrainplan(this);
			entity.setUserid(id);
			this.traineeList.add(entity);
		}
	}

	/**
	 * 获取通知发送记录用户Name
	 * @return
	 */
	public String getTraineeNames() {
		return Collections3.extractToString(traineeList, "username", ",") ;
	}

	/**
	 * 设置通知发送记录用户Name
	 * @return
	 */
	public void setTraineeNames(String trainee) {
		// 什么也不做
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public String getMview() {
		return mview;
	}

	public void setMview(String mview) {
		this.mview = mview;
	}

	public String getIsnotify() {
		return isnotify;
	}

	public void setIsnotify(String isnotify) {
		this.isnotify = isnotify;
	}

    public String getTrainpeople() {
        return trainpeople;
    }

    public void setTrainpeople(String trainpeople) {
        this.trainpeople = trainpeople;
    }
}