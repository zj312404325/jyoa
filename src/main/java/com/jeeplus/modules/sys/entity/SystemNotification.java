/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 系统通知Entity
 * @author yc
 * @version 2018-03-01
 */
public class SystemNotification extends DataEntity<SystemNotification> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 内容
	
	public SystemNotification() {
		super();
	}

	public SystemNotification(String id){
		super(id);
	}

	@ExcelField(title="内容", align=2, sort=7)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}