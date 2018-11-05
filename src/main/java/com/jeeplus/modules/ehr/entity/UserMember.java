/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 入职员工信息登记Entity
 * @author yc
 * @version 2017-10-18
 */
public class UserMember extends DataEntity<UserMember> {
	
	private static final long serialVersionUID = 1L;
	private UserInfo userinfo;		// 外键 父类
	private String relationship;		// 关系
	private String memberfullname;		// 姓名
	private String memberage;		// 年龄
	private String memberworkunit;		// 工作单位及职务
	private String membermobile;		// 联系电话
	private String memberaddress;		// 详细住址
	private String sortno;		// 序号
	
	public UserMember() {
		super();
	}

	public UserMember(String id){
		super(id);
	}

	public UserMember(UserInfo userinfo){
		this.userinfo = userinfo;
	}

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	
	@ExcelField(title="关系", align=2, sort=8)
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	@ExcelField(title="姓名", align=2, sort=9)
	public String getMemberfullname() {
		return memberfullname;
	}

	public void setMemberfullname(String memberfullname) {
		this.memberfullname = memberfullname;
	}
	
	@ExcelField(title="年龄", align=2, sort=10)
	public String getMemberage() {
		return memberage;
	}

	public void setMemberage(String memberage) {
		this.memberage = memberage;
	}
	
	@ExcelField(title="工作单位及职务", align=2, sort=11)
	public String getMemberworkunit() {
		return memberworkunit;
	}

	public void setMemberworkunit(String memberworkunit) {
		this.memberworkunit = memberworkunit;
	}
	
	@ExcelField(title="联系电话", align=2, sort=12)
	public String getMembermobile() {
		return membermobile;
	}

	public void setMembermobile(String membermobile) {
		this.membermobile = membermobile;
	}
	
	@ExcelField(title="详细住址", align=2, sort=13)
	public String getMemberaddress() {
		return memberaddress;
	}

	public void setMemberaddress(String memberaddress) {
		this.memberaddress = memberaddress;
	}
	
	@ExcelField(title="序号", align=2, sort=14)
	public String getSortno() {
		return sortno;
	}

	public void setSortno(String sortno) {
		this.sortno = sortno;
	}
}