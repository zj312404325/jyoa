/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * 绩效考核考核人Entity
 * @author cqj
 * @version 2017-10-20
 */
public class CheckUser extends DataEntity<CheckUser> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String userNo;		// 员工编号
	private String userName;		// 员工姓名
	private String officeId;		// 部门id
	private String officeName;		// 部门名称
	private String stationId;		// 岗位id
	private String stationName;		// 岗位名称
	private String memo;		// 备注
	private String weight;		// 权重
	private String checkofficeid; //岗位所属的部门id
	private String checkofficename; //岗位所属的部门名称
	private String category;//null或0表示绩效考核考核人，1表转正考核考核人
	private List<Post> postList=new ArrayList<Post>();//岗位类表
	
	public CheckUser() {
		super();
	}

	public CheckUser(String id){
		super(id);
	}

	@ExcelField(title="用户id", align=2, sort=7)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="员工编号", align=2, sort=8)
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	@ExcelField(title="员工姓名", align=2, sort=9)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ExcelField(title="部门id", align=2, sort=10)
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@ExcelField(title="部门名称", align=2, sort=11)
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	@ExcelField(title="岗位id", align=2, sort=12)
	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
	@ExcelField(title="岗位名称", align=2, sort=13)
	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	@ExcelField(title="备注", align=2, sort=14)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@ExcelField(title="权重", align=2, sort=15)
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getCheckofficeid() {
		return checkofficeid;
	}

	public void setCheckofficeid(String checkofficeid) {
		this.checkofficeid = checkofficeid;
	}

	public String getCheckofficename() {
		return checkofficename;
	}

	public void setCheckofficename(String checkofficename) {
		this.checkofficename = checkofficename;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Post> getPostList() {
		return postList;
	}

	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}
}