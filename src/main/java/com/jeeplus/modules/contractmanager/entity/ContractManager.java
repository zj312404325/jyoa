/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 合同管理-合同Entity
 * @author yc
 * @version 2017-12-07
 */
public class ContractManager extends DataEntity<ContractManager> {
	
	private static final long serialVersionUID = 1L;
	private String contractname;		// 合同名称
	private String contractno;		// 合同编号
	private Date signdate;		// 签订日期
	private String contractclassid;		// 合同类别id
	private String contractclass;		// 合同类别
	private String fundnatureid;		// 资金性质id
	private String fundnature;		// 资金性质
	private Date effectivedate;		// 生效日期
	private String contractamount;		// 合同金额
	private String currencyid;		// 币种id
	private String currency;		// 币种
	private Date plancompletiondate;		// 计划完成日期
	private String stamptax;		// 印花税额
	private String affiliationid;		// 所属机构id
	private String affiliation;		// 所属机构
	private String remark1;		// 备注1
	private String remark2;		// 备注2
	private String contractpartyid;		// 合同对方id
	private String contractparty;		// 合同对方
	private String projectid;		// 所属项目id
	private String project;		// 所属项目
	private String responsiblepersonid;		// 负责人id
	private String responsibleperson;		// 负责人
	private Integer status;		// 状态
	private String fundclause;		// 资金条款
	private String inquirerid;		// 查阅人id
	private String inquirer;		// 查阅人
	private Date completedate;// 完成日期
	private List<ContractActualFunds> contractActualFundsList = Lists.newArrayList();		// 子表列表
	private List<ContractFile> contractFileList = Lists.newArrayList();		// 子表列表
	private List<ContractInvoice> contractInvoiceList = Lists.newArrayList();		// 子表列表
	private List<ContractNote> contractNoteList = Lists.newArrayList();		// 子表列表
	private List<ContractPlanfund> contractPlanfundList = Lists.newArrayList();		// 子表列表
	private List<ContractSubject> contractSubjectList = Lists.newArrayList();		// 子表列表
	private List<ContractText> contractTextList = Lists.newArrayList();		// 子表列表

	private String receiveuserids;		// 授权人员ids
	private String receiveusernames;

	private Date startdate;
	private Date enddate;
	private String completed;		// 已完成支付金额
	private String completerate;		// 完成比例
	private String overdue;		// 逾期


	public ContractManager() {
		super();
	}

	public ContractManager(String id){
		super(id);
	}

	@ExcelField(title="合同名称", align=2, sort=7)
	public String getContractname() {
		return contractname;
	}

	public void setContractname(String contractname) {
		this.contractname = contractname;
	}
	
	@ExcelField(title="合同编号", align=2, sort=8)
	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="签订日期", align=2, sort=9)
	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}
	
	@ExcelField(title="合同类别id", dictType="", align=2, sort=10)
	public String getContractclassid() {
		return contractclassid;
	}

	public void setContractclassid(String contractclassid) {
		this.contractclassid = contractclassid;
	}
	
	@ExcelField(title="合同类别", align=2, sort=11)
	public String getContractclass() {
		return contractclass;
	}

	public void setContractclass(String contractclass) {
		this.contractclass = contractclass;
	}
	
	@ExcelField(title="资金性质id", dictType="", align=2, sort=12)
	public String getFundnatureid() {
		return fundnatureid;
	}

	public void setFundnatureid(String fundnatureid) {
		this.fundnatureid = fundnatureid;
	}
	
	@ExcelField(title="资金性质", align=2, sort=13)
	public String getFundnature() {
		return fundnature;
	}

	public void setFundnature(String fundnature) {
		this.fundnature = fundnature;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="生效日期", align=2, sort=14)
	public Date getEffectivedate() {
		return effectivedate;
	}

	public void setEffectivedate(Date effectivedate) {
		this.effectivedate = effectivedate;
	}
	
	@ExcelField(title="合同金额", align=2, sort=15)
	public String getContractamount() {
		return contractamount;
	}

	public void setContractamount(String contractamount) {
		this.contractamount = contractamount;
	}
	
	@ExcelField(title="币种id", dictType="", align=2, sort=16)
	public String getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(String currencyid) {
		this.currencyid = currencyid;
	}
	
	@ExcelField(title="币种", align=2, sort=17)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="计划完成日期", align=2, sort=18)
	public Date getPlancompletiondate() {
		return plancompletiondate;
	}

	public void setPlancompletiondate(Date plancompletiondate) {
		this.plancompletiondate = plancompletiondate;
	}
	
	@ExcelField(title="印花税额", align=2, sort=19)
	public String getStamptax() {
		return stamptax;
	}

	public void setStamptax(String stamptax) {
		this.stamptax = stamptax;
	}
	
	@ExcelField(title="所属机构id", dictType="", align=2, sort=20)
	public String getAffiliationid() {
		return affiliationid;
	}

	public void setAffiliationid(String affiliationid) {
		this.affiliationid = affiliationid;
	}
	
	@ExcelField(title="所属机构", align=2, sort=21)
	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	
	@ExcelField(title="备注1", align=2, sort=22)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="备注2", align=2, sort=23)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@ExcelField(title="合同对方id", align=2, sort=24)
	public String getContractpartyid() {
		return contractpartyid;
	}

	public void setContractpartyid(String contractpartyid) {
		this.contractpartyid = contractpartyid;
	}
	
	@ExcelField(title="合同对方", align=2, sort=25)
	public String getContractparty() {
		return contractparty;
	}

	public void setContractparty(String contractparty) {
		this.contractparty = contractparty;
	}
	
	@ExcelField(title="所属项目id", align=2, sort=26)
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	@ExcelField(title="所属项目", align=2, sort=27)
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	@ExcelField(title="负责人id", align=2, sort=28)
	public String getResponsiblepersonid() {
		return responsiblepersonid;
	}

	public void setResponsiblepersonid(String responsiblepersonid) {
		this.responsiblepersonid = responsiblepersonid;
	}
	
	@ExcelField(title="负责人", align=2, sort=29)
	public String getResponsibleperson() {
		return responsibleperson;
	}

	public void setResponsibleperson(String responsibleperson) {
		this.responsibleperson = responsibleperson;
	}
	
	@ExcelField(title="状态", align=2, sort=30)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="资金条款", align=2, sort=31)
	public String getFundclause() {
		return fundclause;
	}

	public void setFundclause(String fundclause) {
		this.fundclause = fundclause;
	}
	
	@ExcelField(title="查阅人id", align=2, sort=32)
	public String getInquirerid() {
		return inquirerid;
	}

	public void setInquirerid(String inquirerid) {
		this.inquirerid = inquirerid;
	}
	
	@ExcelField(title="查阅人", align=2, sort=33)
	public String getInquirer() {
		return inquirer;
	}

	public void setInquirer(String inquirer) {
		this.inquirer = inquirer;
	}
	
	public List<ContractFile> getContractFileList() {
		return contractFileList;
	}

	public void setContractFileList(List<ContractFile> contractFileList) {
		this.contractFileList = contractFileList;
	}
	public List<ContractNote> getContractNoteList() {
		return contractNoteList;
	}

	public void setContractNoteList(List<ContractNote> contractNoteList) {
		this.contractNoteList = contractNoteList;
	}
	public List<ContractPlanfund> getContractPlanfundList() {
		return contractPlanfundList;
	}

	public void setContractPlanfundList(List<ContractPlanfund> contractPlanfundList) {
		this.contractPlanfundList = contractPlanfundList;
	}
	public List<ContractSubject> getContractSubjectList() {
		return contractSubjectList;
	}

	public void setContractSubjectList(List<ContractSubject> contractSubjectList) {
		this.contractSubjectList = contractSubjectList;
	}
	public List<ContractText> getContractTextList() {
		return contractTextList;
	}

	public void setContractTextList(List<ContractText> contractTextList) {
		this.contractTextList = contractTextList;
	}

	public List<ContractActualFunds> getContractActualFundsList() {
		return contractActualFundsList;
	}

	public void setContractActualFundsList(
			List<ContractActualFunds> contractActualFundsList) {
		this.contractActualFundsList = contractActualFundsList;
	}

	public List<ContractInvoice> getContractInvoiceList() {
		return contractInvoiceList;
	}

	public void setContractInvoiceList(List<ContractInvoice> contractInvoiceList) {
		this.contractInvoiceList = contractInvoiceList;
	}

	public Date getCompletedate() {
		return completedate;
	}

	public void setCompletedate(Date completedate) {
		this.completedate = completedate;
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

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public String getCompleterate() {
		return completerate;
	}

	public void setCompleterate(String completerate) {
		this.completerate = completerate;
	}

	public String getOverdue() {
		return overdue;
	}

	public void setOverdue(String overdue) {
		this.overdue = overdue;
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