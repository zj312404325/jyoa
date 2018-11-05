/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 流程代理Entity
 * @author cqj
 * @version 2016-12-16
 */
public class Flowagent extends DataEntity<Flowagent> {
	
	private static final long serialVersionUID = 1L;
	private String agentuserid;		// 代理人id
	private String agentusername;		// 代理人登录账号
	private String agentedusername;		// 被代理人账号
	private String agentname;		// 代理人姓名
	
	public Flowagent() {
		super();
	}

	public Flowagent(String id){
		super(id);
	}

	@ExcelField(title="代理人id", align=2, sort=7)
	public String getAgentuserid() {
		return agentuserid;
	}

	public void setAgentuserid(String agentuserid) {
		this.agentuserid = agentuserid;
	}
	
	@ExcelField(title="代理人登录账号", align=2, sort=8)
	public String getAgentusername() {
		return agentusername;
	}

	public void setAgentusername(String agentusername) {
		this.agentusername = agentusername;
	}
	
	@ExcelField(title="被代理人账号", align=2, sort=9)
	public String getAgentedusername() {
		return agentedusername;
	}

	public void setAgentedusername(String agentedusername) {
		this.agentedusername = agentedusername;
	}
	
	@ExcelField(title="代理人姓名", align=2, sort=10)
	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
	
}