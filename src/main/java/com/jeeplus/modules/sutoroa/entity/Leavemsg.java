/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 晨会留言Entity
 * @author cqj
 * @version 2018-02-23
 */
public class Leavemsg extends DataEntity<Leavemsg> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 内容
	private String conferenceid;		// 晨会ID
	private String ip;		// ip
	private String createusername;		// 创建者
	
	public Leavemsg() {
		super();
	}

	public Leavemsg(String id){
		super(id);
	}

	@ExcelField(title="内容", align=2, sort=7)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="晨会ID", align=2, sort=8)
	public String getConferenceid() {
		return conferenceid;
	}

	public void setConferenceid(String conferenceid) {
		this.conferenceid = conferenceid;
	}
	
	@ExcelField(title="ip", align=2, sort=9)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@ExcelField(title="创建者", align=2, sort=10)
	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	
}