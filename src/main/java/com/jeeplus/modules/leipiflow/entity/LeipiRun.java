/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 流程运行表Entity
 * @author 陈钱江
 * @version 2017-09-06
 */
public class LeipiRun extends DataEntity<LeipiRun> {
	
	private static final long serialVersionUID = 1L;
	private String pid;		// work_run父流转公文ID 值大于0则这个是子流程，完成后或者要返回父流程
	private String pidFlowStep;		// 父pid的flow_id中的第几步骤进入的,取回这个work_flow_step的child_over决定结束子流程的动作
	private String cacheRunId;		// 多个子流程时pid无法识别cache所以加这个字段pid>0
	private String upid;		// upid
	private String flowId;		// 流程id 正常流程
	private Integer catId;		// 流程分类ID即公文分类ID
	private String runName;		// 公文名称
	private String runFlowId;		// 流转到什么流程 最新流程，查询优化，进入子流程时将简化查询，子流程与父流程同步
	private String runFlowProcess;		// 流转到第几步
	private String attIds;		// 公文附件ids
	private Date endtime;		// 结束时间
	private Integer status;		// 流程状态0运行中1已完成2已撤销（上级不同意，自己撤销）
	private Integer isdel;		// 是否删除
	private Date updatetime;		// 更新时间
	private Date dateline;		// 结束时间
	
	public LeipiRun() {
		super();
	}

	public LeipiRun(String id){
		super(id);
	}

	@ExcelField(title="work_run父流转公文ID 值大于0则这个是子流程，完成后或者要返回父流程", align=2, sort=1)
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@ExcelField(title="父pid的flow_id中的第几步骤进入的,取回这个work_flow_step的child_over决定结束子流程的动作", align=2, sort=2)
	public String getPidFlowStep() {
		return pidFlowStep;
	}

	public void setPidFlowStep(String pidFlowStep) {
		this.pidFlowStep = pidFlowStep;
	}
	
	@ExcelField(title="多个子流程时pid无法识别cache所以加这个字段pid>0", align=2, sort=3)
	public String getCacheRunId() {
		return cacheRunId;
	}

	public void setCacheRunId(String cacheRunId) {
		this.cacheRunId = cacheRunId;
	}
	
	@ExcelField(title="upid", align=2, sort=4)
	public String getUpid() {
		return upid;
	}

	public void setUpid(String upid) {
		this.upid = upid;
	}
	
	@ExcelField(title="流程id 正常流程", align=2, sort=5)
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	@NotNull(message="流程分类ID即公文分类ID不能为空")
	@ExcelField(title="流程分类ID即公文分类ID", dictType="del_flag", align=2, sort=6)
	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	
	@ExcelField(title="公文名称", align=2, sort=7)
	public String getRunName() {
		return runName;
	}

	public void setRunName(String runName) {
		this.runName = runName;
	}
	
	@ExcelField(title="流转到什么流程 最新流程，查询优化，进入子流程时将简化查询，子流程与父流程同步", align=2, sort=8)
	public String getRunFlowId() {
		return runFlowId;
	}

	public void setRunFlowId(String runFlowId) {
		this.runFlowId = runFlowId;
	}
	
	@ExcelField(title="流转到第几步", align=2, sort=9)
	public String getRunFlowProcess() {
		return runFlowProcess;
	}

	public void setRunFlowProcess(String runFlowProcess) {
		this.runFlowProcess = runFlowProcess;
	}
	
	@ExcelField(title="公文附件ids", align=2, sort=10)
	public String getAttIds() {
		return attIds;
	}

	public void setAttIds(String attIds) {
		this.attIds = attIds;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=11)
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	@ExcelField(title="公文导入的状态，0未审核，1通过,2不通过", align=2, sort=12)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="是否删除", align=2, sort=13)
	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=14)
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=15)
	public Date getDateline() {
		return dateline;
	}

	public void setDateline(Date dateline) {
		this.dateline = dateline;
	}
	
	
}