/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.entity;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.contractmanager.entity.ContractManager;

import java.util.Date;
import java.util.List;

/**
 * 客户管理Entity
 * @author yc
 * @version 2017-11-30
 */
public class Customer extends DataEntity<Customer> {
	
	private static final long serialVersionUID = 1L;
	private String customername;		// 客户名称
	private String customerno;		// 客户编号
	private String industry;		// 行业
	private String customerclassid;		// 客户类别id
	private String customerclass;		// 客户类别
	private String country;		// 国家
	private String province;		// 省
	private String city;		// 市
	private String customerlevelid;		// 客户级别id
	private String customerlevel;		// 客户级别
	private String creditlevelid;		// 信用级别id
	private String creditlevel;		// 信用级别
	private String address;		// 联系地址
	private String zipcode;		// 邮编
	private String companyurl;		// 公司网址
	private String contact;		// 首要联系人
	private String phone;		// 办公电话
	private String position;		// 职位
	private String mobile;		// 移动电话
	private String fax;		// 传真
	private String email;		// E-Mail
	private String qqmsn;		// (QQ/MSN)
	private String legal;		// 法定代表人
	private String registeredcapital;		// 注册资本
	private String currencyid;		// 币种id
	private String currency;		// 币种
	private String paidincapital;		// 实收资本
	private String companyclassid;		// 公司类型id
	private String companyclass;		// 公司类型
	private String residence;		// 住所
	private Date setuptime;		// 成立时间
	private String businessscope;		// 经营范围
	private Date businesssdeadline;		// 经营截止日期
	private String remark1;		// 备用1
	private String remark2;		// 备用2
	private String responsibleperson;		// 负责人
	private String responsiblepersonid;		// 负责人id
	private String invoicename;		// 名称
	private String invoicetaxno;		// 纳税人识别号
	private String invoiceaddress;		// 地址
	private String invoicemobile;		// 电话
	private String bank;		// 开户银行
	private String bankno;		// 银行账号
	private Integer usertype;		// 用户类型
	private Integer status;		// 审核状态
	private List<CustomerContact> customerContactList = Lists.newArrayList();		// 子表列表
	private List<CustomerFile> customerFileList = Lists.newArrayList();		// 子表列表
	private List<CustomerRemark> customerRemarkList = Lists.newArrayList();		// 子表列表
	
	private List<ContractManager> contractManagerList = Lists.newArrayList();		// 关联合同列表
	private List<ProjectManage> projectManageList = Lists.newArrayList();		// 关联项目列表
	
	
	private Date startdate;//查询用
	private Date enddate;//查询用
	private String receiveuserids;		// 授权人员ids
	private String receiveusernames;
	
	public Customer() {
		super();
	}

	public Customer(String id){
		super(id);
	}

	@ExcelField(title="客户名称", align=2, sort=7)
	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
	
	@ExcelField(title="客户编号", align=2, sort=8)
	public String getCustomerno() {
		return customerno;
	}

	public void setCustomerno(String customerno) {
		this.customerno = customerno;
	}
	
	@ExcelField(title="行业", align=2, sort=9)
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@ExcelField(title="客户类别id", align=2, sort=10)
	public String getCustomerclassid() {
		return customerclassid;
	}

	public void setCustomerclassid(String customerclassid) {
		this.customerclassid = customerclassid;
	}
	
	@ExcelField(title="客户类别", align=2, sort=11)
	public String getCustomerclass() {
		return customerclass;
	}

	public void setCustomerclass(String customerclass) {
		this.customerclass = customerclass;
	}
	
	@ExcelField(title="国家", align=2, sort=12)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@ExcelField(title="省", align=2, sort=13)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@ExcelField(title="市", align=2, sort=14)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@ExcelField(title="客户级别id", align=2, sort=15)
	public String getCustomerlevelid() {
		return customerlevelid;
	}

	public void setCustomerlevelid(String customerlevelid) {
		this.customerlevelid = customerlevelid;
	}
	
	@ExcelField(title="客户级别", align=2, sort=16)
	public String getCustomerlevel() {
		return customerlevel;
	}

	public void setCustomerlevel(String customerlevel) {
		this.customerlevel = customerlevel;
	}
	
	@ExcelField(title="信用级别id", align=2, sort=17)
	public String getCreditlevelid() {
		return creditlevelid;
	}

	public void setCreditlevelid(String creditlevelid) {
		this.creditlevelid = creditlevelid;
	}
	
	@ExcelField(title="信用级别", align=2, sort=18)
	public String getCreditlevel() {
		return creditlevel;
	}

	public void setCreditlevel(String creditlevel) {
		this.creditlevel = creditlevel;
	}
	
	@ExcelField(title="联系地址", align=2, sort=19)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="邮编", align=2, sort=20)
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@ExcelField(title="公司网址", align=2, sort=21)
	public String getCompanyurl() {
		return companyurl;
	}

	public void setCompanyurl(String companyurl) {
		this.companyurl = companyurl;
	}
	
	@ExcelField(title="首要联系人", align=2, sort=22)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@ExcelField(title="办公电话", align=2, sort=23)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="职位", align=2, sort=24)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@ExcelField(title="移动电话", align=2, sort=25)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="传真", align=2, sort=26)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@ExcelField(title="E-Mail", align=2, sort=27)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="(QQ/MSN)", align=2, sort=28)
	public String getQqmsn() {
		return qqmsn;
	}

	public void setQqmsn(String qqmsn) {
		this.qqmsn = qqmsn;
	}
	
	@ExcelField(title="法定代表人", align=2, sort=29)
	public String getLegal() {
		return legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}
	
	@ExcelField(title="注册资本", align=2, sort=30)
	public String getRegisteredcapital() {
		return registeredcapital;
	}

	public void setRegisteredcapital(String registeredcapital) {
		this.registeredcapital = registeredcapital;
	}
	
	@ExcelField(title="币种id", align=2, sort=31)
	public String getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(String currencyid) {
		this.currencyid = currencyid;
	}
	
	@ExcelField(title="币种", align=2, sort=32)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@ExcelField(title="实收资本", align=2, sort=33)
	public String getPaidincapital() {
		return paidincapital;
	}

	public void setPaidincapital(String paidincapital) {
		this.paidincapital = paidincapital;
	}
	
	@ExcelField(title="公司类型id", align=2, sort=34)
	public String getCompanyclassid() {
		return companyclassid;
	}

	public void setCompanyclassid(String companyclassid) {
		this.companyclassid = companyclassid;
	}
	
	@ExcelField(title="公司类型", align=2, sort=35)
	public String getCompanyclass() {
		return companyclass;
	}

	public void setCompanyclass(String companyclass) {
		this.companyclass = companyclass;
	}
	
	@ExcelField(title="住所", align=2, sort=36)
	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}
	
	@ExcelField(title="成立时间", align=2, sort=37)
	public Date getSetuptime() {
		return setuptime;
	}

	public void setSetuptime(Date setuptime) {
		this.setuptime = setuptime;
	}
	
	@ExcelField(title="经营范围", align=2, sort=38)
	public String getBusinessscope() {
		return businessscope;
	}

	public void setBusinessscope(String businessscope) {
		this.businessscope = businessscope;
	}
	
	@ExcelField(title="经营截止日期", align=2, sort=39)
	public Date getBusinesssdeadline() {
		return businesssdeadline;
	}

	public void setBusinesssdeadline(Date businesssdeadline) {
		this.businesssdeadline = businesssdeadline;
	}
	
	@ExcelField(title="备用1", align=2, sort=40)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="备用2", align=2, sort=41)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@ExcelField(title="负责人", align=2, sort=42)
	public String getResponsibleperson() {
		return responsibleperson;
	}

	public void setResponsibleperson(String responsibleperson) {
		this.responsibleperson = responsibleperson;
	}
	
	@ExcelField(title="名称", align=2, sort=43)
	public String getInvoicename() {
		return invoicename;
	}

	public void setInvoicename(String invoicename) {
		this.invoicename = invoicename;
	}
	
	@ExcelField(title="纳税人识别号", align=2, sort=44)
	public String getInvoicetaxno() {
		return invoicetaxno;
	}

	public void setInvoicetaxno(String invoicetaxno) {
		this.invoicetaxno = invoicetaxno;
	}
	
	@ExcelField(title="地址", align=2, sort=45)
	public String getInvoiceaddress() {
		return invoiceaddress;
	}

	public void setInvoiceaddress(String invoiceaddress) {
		this.invoiceaddress = invoiceaddress;
	}
	
	@ExcelField(title="电话", align=2, sort=46)
	public String getInvoicemobile() {
		return invoicemobile;
	}

	public void setInvoicemobile(String invoicemobile) {
		this.invoicemobile = invoicemobile;
	}
	
	@ExcelField(title="开户银行", align=2, sort=47)
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
	@ExcelField(title="银行账号", align=2, sort=48)
	public String getBankno() {
		return bankno;
	}

	public void setBankno(String bankno) {
		this.bankno = bankno;
	}
	
	@ExcelField(title="用户类型", align=2, sort=49)
	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
	
	@ExcelField(title="审核状态", align=2, sort=50)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public List<CustomerContact> getCustomerContactList() {
		return customerContactList;
	}

	public void setCustomerContactList(List<CustomerContact> customerContactList) {
		this.customerContactList = customerContactList;
	}
	public List<CustomerFile> getCustomerFileList() {
		return customerFileList;
	}

	public void setCustomerFileList(List<CustomerFile> customerFileList) {
		this.customerFileList = customerFileList;
	}
	public List<CustomerRemark> getCustomerRemarkList() {
		return customerRemarkList;
	}

	public void setCustomerRemarkList(List<CustomerRemark> customerRemarkList) {
		this.customerRemarkList = customerRemarkList;
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

	public String getResponsiblepersonid() {
		return responsiblepersonid;
	}

	public void setResponsiblepersonid(String responsiblepersonid) {
		this.responsiblepersonid = responsiblepersonid;
	}

	public List<ContractManager> getContractManagerList() {
		return contractManagerList;
	}

	public void setContractManagerList(List<ContractManager> contractManagerList) {
		this.contractManagerList = contractManagerList;
	}

	public List<ProjectManage> getProjectManageList() {
		return projectManageList;
	}

	public void setProjectManageList(List<ProjectManage> projectManageList) {
		this.projectManageList = projectManageList;
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