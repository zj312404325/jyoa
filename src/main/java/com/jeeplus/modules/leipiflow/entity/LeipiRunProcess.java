/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 运行流程步骤Entity
 * @author 陈钱江
 * @version 2017-09-06
 */
public class LeipiRunProcess extends DataEntity<LeipiRunProcess> {
	
	private static final long serialVersionUID = 1L;
	private String upid;		// 用户id
	private String runId;		// 当前流转id
	private String runFlow;		// 属于那个流程的id
	private String runFlowProcess;		// 当前步骤编号
	private String parentFlow;		// 上一步流程
	private Integer parentFlowProcess;		// 上一步骤号
	private String runChild;		// 开始转入子流程run_id 如果转入子流程，则在这里也记录
	private String remark;		// 备注
	private Integer isReceiveType;		// 是否先接收人为主办人
	private Integer isSponsor;		// 是否步骤主办人 0否(默认) 1是
	private Integer isSingpost;		// 是否已会签过
	private Integer isBack;		// 被退回的 0否(默认) 1是
	private Integer status;		// //状态0初始 ,1通过,2打回
	private Date jsTime;		// 接收时间
	private Date blTime;		// 办理时间
	private Date jjTime;		// 转交时间,最后一步等同办结时间
	private Integer isDel;		// 是否删除
	private Date updatetime;		// 更新时间
	private Date dateline;		// 日期
	private String agentedid;	// 代理人
	private Integer isOpen;		// 是否被查阅
	private String addprocessid;	// 步骤添加人id
	
	private LeipiFlowProcess leipiFlowProcess;
	private User runUser;
	private User agentUser;
	private String flowid;//流程id
	private Date addprocessDate;//步骤添加日期
	private String addprocessFlag;//查询标记
	private String keyword;//编号
	private String keyword1;//发起人
	private Office office;	// 归属部门
	private String agentedidFlag;

	private Date startdate;//查询用
	private Date enddate;//查询用
	private String var1;
	private String flowtype="0";
	private String flowids;//查询流程ids
	private String var2;//标题
	
	public LeipiRunProcess() {
		super();
	}

	public LeipiRunProcess(String id){
		super(id);
	}

	@ExcelField(title="用户id", align=2, sort=1)
	public String getUpid() {
		return upid;
	}

	public void setUpid(String upid) {
		this.upid = upid;
	}
	
	@ExcelField(title="当前流转id", align=2, sort=2)
	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}
	
	@ExcelField(title="属于那个流程的id", align=2, sort=3)
	public String getRunFlow() {
		return runFlow;
	}

	public void setRunFlow(String runFlow) {
		this.runFlow = runFlow;
	}
	
	public String getRunFlowProcess() {
		return runFlowProcess;
	}

	public void setRunFlowProcess(String runFlowProcess) {
		this.runFlowProcess = runFlowProcess;
	}

	@ExcelField(title="上一步流程", align=2, sort=5)
	public String getParentFlow() {
		return parentFlow;
	}

	public void setParentFlow(String parentFlow) {
		this.parentFlow = parentFlow;
	}
	
	@NotNull(message="上一步骤号不能为空")
	@ExcelField(title="上一步骤号", dictType="del_flag", align=2, sort=6)
	public Integer getParentFlowProcess() {
		return parentFlowProcess;
	}

	public void setParentFlowProcess(Integer parentFlowProcess) {
		this.parentFlowProcess = parentFlowProcess;
	}
	
	@ExcelField(title="开始转入子流程run_id 如果转入子流程，则在这里也记录", align=2, sort=7)
	public String getRunChild() {
		return runChild;
	}

	public void setRunChild(String runChild) {
		this.runChild = runChild;
	}
	
	@ExcelField(title="备注", align=2, sort=8)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="是否先接收人为主办人", align=2, sort=9)
	public Integer getIsReceiveType() {
		return isReceiveType;
	}

	public void setIsReceiveType(Integer isReceiveType) {
		this.isReceiveType = isReceiveType;
	}
	
	@ExcelField(title="是否步骤主办人 0否(默认) 1是", align=2, sort=10)
	public Integer getIsSponsor() {
		return isSponsor;
	}

	public void setIsSponsor(Integer isSponsor) {
		this.isSponsor = isSponsor;
	}
	
	@ExcelField(title="是否已会签过", align=2, sort=11)
	public Integer getIsSingpost() {
		return isSingpost;
	}

	public void setIsSingpost(Integer isSingpost) {
		this.isSingpost = isSingpost;
	}
	
	@ExcelField(title="被退回的 0否(默认) 1是", align=2, sort=12)
	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}
	
	@ExcelField(title="状态 0为未接收（默认），1为办理中 ,2为已转交,3为已结束4为已打回", align=2, sort=13)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="接收时间", align=2, sort=14)
	public Date getJsTime() {
		return jsTime;
	}

	public void setJsTime(Date jsTime) {
		this.jsTime = jsTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="办理时间", align=2, sort=15)
	public Date getBlTime() {
		return blTime;
	}

	public void setBlTime(Date blTime) {
		this.blTime = blTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="转交时间,最后一步等同办结时间", align=2, sort=16)
	public Date getJjTime() {
		return jjTime;
	}

	public void setJjTime(Date jjTime) {
		this.jjTime = jjTime;
	}
	
	@ExcelField(title="是否删除", align=2, sort=17)
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=18)
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="日期", align=2, sort=19)
	public Date getDateline() {
		return dateline;
	}

	public void setDateline(Date dateline) {
		this.dateline = dateline;
	}

	public LeipiFlowProcess getLeipiFlowProcess() {
		return leipiFlowProcess;
	}

	public void setLeipiFlowProcess(LeipiFlowProcess leipiFlowProcess) {
		this.leipiFlowProcess = leipiFlowProcess;
	}

	public User getRunUser() {
		return runUser;
	}

	public void setRunUser(User runUser) {
		this.runUser = runUser;
	}

	public User getAgentUser() {
		return agentUser;
	}

	public void setAgentUser(User agentUser) {
		this.agentUser = agentUser;
	}

	public String getAgentedid() {
		return agentedid;
	}

	public void setAgentedid(String agentedid) {
		this.agentedid = agentedid;
	}

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public String getAddprocessid() {
		return addprocessid;
	}

	public void setAddprocessid(String addprocessid) {
		this.addprocessid = addprocessid;
	}

	public Date getAddprocessDate() {
		return addprocessDate;
	}

	public void setAddprocessDate(Date addprocessDate) {
		this.addprocessDate = addprocessDate;
	}

	public String getAddprocessFlag() {
		return addprocessFlag;
	}

	public void setAddprocessFlag(String addprocessFlag) {
		this.addprocessFlag = addprocessFlag;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getAgentedidFlag() {
		return agentedidFlag;
	}

	public void setAgentedidFlag(String agentedidFlag) {
		this.agentedidFlag = agentedidFlag;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}

	public String getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}

	public String getFlowids() {
		return flowids;
	}

	public void setFlowids(String flowids) {
		this.flowids = flowids;
	}

	public String getVar2() {
		return var2;
	}

	public void setVar2(String var2) {
		this.var2 = var2;
	}
}