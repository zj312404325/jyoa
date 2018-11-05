/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.contractmanager.entity.ContractManager;

import java.util.Date;
import java.util.List;

/**
 * 项目管理Entity
 * @author cqj
 * @version 2017-12-05
 */
public class ProjectManage extends DataEntity<ProjectManage> {
	
	private static final long serialVersionUID = 1L;
	private String projectname;		// 项目名称
	private String projectno;		// 项目编号
	private String projectcategory;		// 项目类别
	private Double projectgather;		// 项目预算收款
	private Double projectpayment;		// 项目预算付款
	private String gathercurrencytype;		// 收款币种
	private String paymentcurrencytype;		// 付款币种
	private Date planstartdate;		// 计划开始时间
	private Date planenddate;		// 计划结束时间
	private String backupone;		// 备用1
	private String backuptwo;		// 备用2
	private String officalid;		// 负责人id
	private String officalname;		// 负责人姓名
	private String createusername;		// 创建人姓名
	private String state;		// 项目状态
	private List<ProjectAttachment> projectAttachmentList = Lists.newArrayList();		// 子表列表
	private List<ProjectMemo> projectMemoList = Lists.newArrayList();		// 子表列表
	private List<ProjectTime> projectTimeList = Lists.newArrayList();		// 子表列表
	private Date startdate;
	private Date enddate;
	
	private ContractManager contractManager;
	private String receiveuserids;		// 授权人员ids
	private String receiveusernames;
	
	public ProjectManage() {
		super();
	}

	public ProjectManage(String id){
		super(id);
	}
	
	public ProjectManage(ContractManager contractManager) {
		super();
		this.contractManager = contractManager;
	}

	@ExcelField(title="项目名称", align=2, sort=7)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="项目编号", align=2, sort=8)
	public String getProjectno() {
		return projectno;
	}

	public void setProjectno(String projectno) {
		this.projectno = projectno;
	}
	
	@ExcelField(title="项目类别", dictType="", align=2, sort=9)
	public String getProjectcategory() {
		return projectcategory;
	}

	public void setProjectcategory(String projectcategory) {
		this.projectcategory = projectcategory;
	}
	
	@ExcelField(title="项目预算收款", align=2, sort=10)
	public Double getProjectgather() {
		return projectgather;
	}

	public void setProjectgather(Double projectgather) {
		this.projectgather = projectgather;
	}
	
	@ExcelField(title="项目预算付款", align=2, sort=11)
	public Double getProjectpayment() {
		return projectpayment;
	}

	public void setProjectpayment(Double projectpayment) {
		this.projectpayment = projectpayment;
	}
	
	@ExcelField(title="收款币种", dictType="", align=2, sort=12)
	public String getGathercurrencytype() {
		return gathercurrencytype;
	}

	public void setGathercurrencytype(String gathercurrencytype) {
		this.gathercurrencytype = gathercurrencytype;
	}
	
	@ExcelField(title="付款币种", dictType="", align=2, sort=13)
	public String getPaymentcurrencytype() {
		return paymentcurrencytype;
	}

	public void setPaymentcurrencytype(String paymentcurrencytype) {
		this.paymentcurrencytype = paymentcurrencytype;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="计划开始时间", align=2, sort=14)
	public Date getPlanstartdate() {
		return planstartdate;
	}

	public void setPlanstartdate(Date planstartdate) {
		this.planstartdate = planstartdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="计划结束时间", align=2, sort=15)
	public Date getPlanenddate() {
		return planenddate;
	}

	public void setPlanenddate(Date planenddate) {
		this.planenddate = planenddate;
	}
	
	@ExcelField(title="备用1", align=2, sort=16)
	public String getBackupone() {
		return backupone;
	}

	public void setBackupone(String backupone) {
		this.backupone = backupone;
	}
	
	@ExcelField(title="备用2", align=2, sort=17)
	public String getBackuptwo() {
		return backuptwo;
	}

	public void setBackuptwo(String backuptwo) {
		this.backuptwo = backuptwo;
	}
	
	@ExcelField(title="负责人id", align=2, sort=18)
	public String getOfficalid() {
		return officalid;
	}

	public void setOfficalid(String officalid) {
		this.officalid = officalid;
	}
	
	@ExcelField(title="负责人姓名", align=2, sort=19)
	public String getOfficalname() {
		return officalname;
	}

	public void setOfficalname(String officalname) {
		this.officalname = officalname;
	}
	
	@ExcelField(title="创建人姓名", align=2, sort=20)
	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	
	@ExcelField(title="项目状态", align=2, sort=21)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public List<ProjectAttachment> getProjectAttachmentList() {
		return projectAttachmentList;
	}

	public void setProjectAttachmentList(List<ProjectAttachment> projectAttachmentList) {
		this.projectAttachmentList = projectAttachmentList;
	}
	public List<ProjectMemo> getProjectMemoList() {
		return projectMemoList;
	}

	public void setProjectMemoList(List<ProjectMemo> projectMemoList) {
		this.projectMemoList = projectMemoList;
	}
	public List<ProjectTime> getProjectTimeList() {
		return projectTimeList;
	}

	public void setProjectTimeList(List<ProjectTime> projectTimeList) {
		this.projectTimeList = projectTimeList;
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

	public ContractManager getContractManager() {
		return contractManager;
	}

	public void setContractManager(ContractManager contractManager) {
		this.contractManager = contractManager;
	}

	public String getReceiveuserids() {
		return receiveuserids;
	}

	public void setReceiveuserids(String receiveuserids) {
		this.receiveuserids = receiveuserids;
	}

	public String getReceiveusernames() {
		return receiveusernames;
	}

	public void setReceiveusernames(String receiveusernames) {
		this.receiveusernames = receiveusernames;
	}
}