/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.entity;


/**
 * 通知通告记录Entity
 * @author jeeplus
 * @version 2014-05-16
 */
public class OaRecord {
	private String createdate;
	private String content;

	public OaRecord() {
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}