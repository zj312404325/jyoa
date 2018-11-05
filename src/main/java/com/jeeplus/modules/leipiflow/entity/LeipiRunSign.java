/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 运行流程signEntity
 * @author cqj
 * @version 2017-09-06
 */
public class LeipiRunSign extends DataEntity<LeipiRunSign> {
	
	private static final long serialVersionUID = 1L;
	private String upid;		// DEFAULT 0
	private String runId;		// DEFAULT 0
	private String runFlow;		// 流程ID,子流程时区分run step
	private Integer runFlowProcess;		// 当前步骤编号
	private String content;		// 会签内容
	private Integer isAgree;		// 审核意见：1同意；2不同意
	private Integer signAttId;		// DEFAULT 0
	private Integer signLook;		// 步骤设置的会签可见性,0总是可见（默认）,1本步骤经办人之间不可见2针对其他步骤不可见
	private String dateline;		// 添加时间
	
	public LeipiRunSign() {
		super();
	}

	public LeipiRunSign(String id){
		super(id);
	}

	@ExcelField(title="DEFAULT 0", align=2, sort=1)
	public String getUpid() {
		return upid;
	}

	public void setUpid(String upid) {
		this.upid = upid;
	}
	
	@ExcelField(title="DEFAULT 0", align=2, sort=2)
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
	
	@NotNull(message="当前步骤编号不能为空")
	@ExcelField(title="当前步骤编号", align=2, sort=4)
	public Integer getRunFlowProcess() {
		return runFlowProcess;
	}

	public void setRunFlowProcess(Integer runFlowProcess) {
		this.runFlowProcess = runFlowProcess;
	}
	
	@ExcelField(title="会签内容", align=2, sort=5)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@NotNull(message="审核意见：1同意；2不同意不能为空")
	@ExcelField(title="审核意见：1同意；2不同意", dictType="", align=2, sort=6)
	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}
	
	@ExcelField(title="DEFAULT 0", align=2, sort=7)
	public Integer getSignAttId() {
		return signAttId;
	}

	public void setSignAttId(Integer signAttId) {
		this.signAttId = signAttId;
	}
	
	@ExcelField(title="步骤设置的会签可见性,0总是可见（默认）,1本步骤经办人之间不可见2针对其他步骤不可见", align=2, sort=8)
	public Integer getSignLook() {
		return signLook;
	}

	public void setSignLook(Integer signLook) {
		this.signLook = signLook;
	}
	
	@ExcelField(title="添加时间", align=2, sort=9)
	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}
	
}