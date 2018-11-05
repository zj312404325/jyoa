/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jpush.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 极光推送注册用户信息表Entity
 * @author cqj
 * @version 2018-04-10
 */
public class Jpushregister extends DataEntity<Jpushregister> {
	
	private static final long serialVersionUID = 1L;
	private String registerid;		// 极光推送注册id
	private String userid;		// 对应的用户id
	
	public Jpushregister() {
		super();
	}

	public Jpushregister(String id){
		super(id);
	}

	@ExcelField(title="极光推送注册id", align=2, sort=1)
	public String getRegisterid() {
		return registerid;
	}

	public void setRegisterid(String registerid) {
		this.registerid = registerid;
	}
	
	@ExcelField(title="对应的用户id", align=2, sort=2)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}