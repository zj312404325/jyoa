/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 运行流程缓存Entity
 * @author 陈钱江
 * @version 2017-09-06
 */
public class LeipiRunLog extends DataEntity<LeipiRunLog> {
	
	private static final long serialVersionUID = 1L;
	private String upid;		// upid
	private String runId;		// 流转id
	private String runFlow;		// 流程ID,子流程时区分run step
	private String content;		// 日志内容
	private String ip;		// ip2long最后登陆ip
	private Double dateline;		// 添加时间
	
	public LeipiRunLog() {
		super();
	}

	public LeipiRunLog(String id){
		super(id);
	}

	@ExcelField(title="upid", align=2, sort=1)
	public String getUpid() {
		return upid;
	}

	public void setUpid(String upid) {
		this.upid = upid;
	}
	
	@ExcelField(title="流转id", align=2, sort=2)
	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}
	
	@ExcelField(title="流程ID,子流程时区分run step", align=2, sort=3)
	public String getRunFlow() {
		return runFlow;
	}

	public void setRunFlow(String runFlow) {
		this.runFlow = runFlow;
	}
	
	@ExcelField(title="日志内容", align=2, sort=4)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="ip2long最后登陆ip", align=2, sort=5)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@NotNull(message="添加时间不能为空")
	@ExcelField(title="添加时间", dictType="", align=2, sort=6)
	public Double getDateline() {
		return dateline;
	}

	public void setDateline(Double dateline) {
		this.dateline = dateline;
	}
	
}