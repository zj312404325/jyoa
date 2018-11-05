/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 运行流程缓存Entity
 * @author 陈钱江
 * @version 2017-09-06
 */
public class LeipiRunCache extends DataEntity<LeipiRunCache> {
	
	private static final long serialVersionUID = 1L;
	private String runId;		// 缓存run工作的全部流程模板步骤等信息,确保修改流程后工作依然不变
	private String formId;		// 表单id
	private String flowId;		// 流程ID
	private String runFlowProcess;		// 流程步骤信息
	private Integer isDel;		// 是否删除
	private Date updatetime;		// 更新时间
	private Date dateline;		// 结束时间
	
	public LeipiRunCache() {
		super();
	}

	public LeipiRunCache(String id){
		super(id);
	}

	@ExcelField(title="缓存run工作的全部流程模板步骤等信息,确保修改流程后工作依然不变", align=2, sort=1)
	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}
	
	@ExcelField(title="表单id", align=2, sort=2)
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@ExcelField(title="流程ID", align=2, sort=3)
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	@ExcelField(title="流程步骤信息", align=2, sort=4)
	public String getRunFlowProcess() {
		return runFlowProcess;
	}

	public void setRunFlowProcess(String runFlowProcess) {
		this.runFlowProcess = runFlowProcess;
	}
	
	@ExcelField(title="是否删除", align=2, sort=5)
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	@ExcelField(title="更新时间", dictType="", align=2, sort=6)
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=7)
	public Date getDateline() {
		return dateline;
	}

	public void setDateline(Date dateline) {
		this.dateline = dateline;
	}
	
}