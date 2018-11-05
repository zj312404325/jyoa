/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.entity;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;

import java.util.Date;
import java.util.List;

/**
 * 入职员工信息登记Entity
 * @author yc
 * @version 2017-10-18
 */
public class UserInfo extends DataEntity<UserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String fullname;		// 姓名
	private String usedfullname;		// 曾用名
	private String sex;		// 性别
	private String bodyheight;		// 身高
	private String bodyweight;		// 体重
	private String blood;		// 血型
	private String political;		// 政治面貌
	private String birthday;		// 出生年月
	private String idcardno;		// 身份证号码
	private String origin;		// 籍贯
	private String nation;		// 民族
	private String religion;		// 宗教信仰
	private String phone;		// 固定电话
	private String mobile;		// 移动电话
	private String email;		// 邮箱
	private String degree;		// 学历
	private String profession;		// 专业
	private String college;		// 毕业院校
	private String languages;		// 外语语种
	private String marriage;		// 婚姻状况
	private String disease;		// 有无病史
	private String workstatus;		// 目前劳动关系
	private String isfamily;		// 是否有亲属在本公司工作
	private String iscompetition;		// 是否有签署竞业禁止协议
	private String iscardurl;		// 身份证
	private String certificateurl;		// 毕业证
	private String degreecertificateurl;		// 学位证
	private String driverlicenseurl;		// 驾驶证
	private String qualificationcertificateurl;		// 从业资格证
	private String leavingcertificate;		// 离职证明
	private String resumeurl;		// 个人简历
	private String address;		// 现居住地
	private String position;		// 岗位
	private String probationperiod;		// 试用期
	private String probationperiodsalary;		// 试用期薪资
	private String disclaimername;		// 免责声明姓名
	private String disclaimerno;		// 免责声明身份证号码
	private String disclaimeryear;		// 免责声明年
	private String disclaimermonth;		// 免责声明月
	private String disclaimerday;		// 免责声明日
	private String disclaimerdisease;		// 免责声明职业病
	private String photo;		// 照片
	private Integer status=-1;		// 合同审核状态
	private String contract;		// 合同
	private Integer ispermit=-1;		// 信息审核状态
	private Date startdate;// 合同开始时间
	private Date enddate;// 合同结束时间
	private Integer entrytrain=0;		// 入职考试状态
	private Date entrytraindate;
	private Integer entrytrain1=0;
	private Integer entrytrain2=0;
	private Integer entrytrain3=0;
	private Integer entrytrain4=0;
	private Integer entrytrain5=0;
	private Integer entrytrain6=0;
	private Integer entrytrain7=0;
	private Integer entrytrain8=0;
	private Integer entrytrain9=0;
	private Integer entrytrain10=0;
	private Integer entrytrain11=0;
	private Integer entrytrain12=0;
	
	private Integer leavestatus=0;
	private Date leavedate;
	private String leaveurl;
	private String resignation;
	
	private List<UserMember> userMemberList = Lists.newArrayList();		// 子表列表
	
	private String officeid;		//临时存放
	private String deptid;		//临时存放
	private Integer ispermittemp;	// 临时存放信息审核状态
	private Integer statustemp;	// 临时存放合同审核状态
//	private Integer isdel=0;// 临时存放删除状态
	private Office office;
	private String bankcardurl;//银行卡地址
	private String healthurl;//体检证明
	private String userid;//对应的用户id 保存离职申请用
	private String peopleStructType="0";//人员结构类型：0学历1性别
	private String user_del_flag="0";
	
	public UserInfo() {
		super();
	}

	public UserInfo(String id){
		super(id);
	}
	
	public UserInfo(User user){
		super(user);
	}
	
	@ExcelField(title="姓名", align=2, sort=7)
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	@ExcelField(title="曾用名", align=2, sort=8)
	public String getUsedfullname() {
		return usedfullname;
	}

	public void setUsedfullname(String usedfullname) {
		this.usedfullname = usedfullname;
	}
	
	@ExcelField(title="性别", align=2, sort=9)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@ExcelField(title="身高", align=2, sort=10)
	public String getBodyheight() {
		return bodyheight;
	}

	public void setBodyheight(String bodyheight) {
		this.bodyheight = bodyheight;
	}
	
	@ExcelField(title="体重", align=2, sort=11)
	public String getBodyweight() {
		return bodyweight;
	}

	public void setBodyweight(String bodyweight) {
		this.bodyweight = bodyweight;
	}
	
	@ExcelField(title="血型", align=2, sort=12)
	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}
	
	@ExcelField(title="政治面貌", align=2, sort=13)
	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}
	
	@ExcelField(title="出生年月", align=2, sort=14)
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@ExcelField(title="身份证号码", align=2, sort=15)
	public String getIdcardno() {
		return idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}
	
	@ExcelField(title="籍贯", align=2, sort=16)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@ExcelField(title="民族", align=2, sort=17)
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	@ExcelField(title="宗教信仰", align=2, sort=18)
	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}
	
	@ExcelField(title="固定电话", align=2, sort=19)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="移动电话", align=2, sort=20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="邮箱", align=2, sort=21)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="学历", align=2, sort=22)
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	@ExcelField(title="专业", align=2, sort=23)
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	@ExcelField(title="毕业院校", align=2, sort=24)
	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}
	
	@ExcelField(title="外语语种", align=2, sort=25)
	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}
	
	@ExcelField(title="婚姻状况", align=2, sort=26)
	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	
	@ExcelField(title="有无病史", align=2, sort=27)
	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}
	
	@ExcelField(title="目前劳动关系", align=2, sort=28)
	public String getWorkstatus() {
		return workstatus;
	}

	public void setWorkstatus(String workstatus) {
		this.workstatus = workstatus;
	}
	
	@ExcelField(title="是否有亲属在本公司工作", align=2, sort=29)
	public String getIsfamily() {
		return isfamily;
	}

	public void setIsfamily(String isfamily) {
		this.isfamily = isfamily;
	}
	
	@ExcelField(title="是否有签署竞业禁止协议", align=2, sort=30)
	public String getIscompetition() {
		return iscompetition;
	}

	public void setIscompetition(String iscompetition) {
		this.iscompetition = iscompetition;
	}
	
	@ExcelField(title="身份证", align=2, sort=31)
	public String getIscardurl() {
		return iscardurl;
	}

	public void setIscardurl(String iscardurl) {
		this.iscardurl = iscardurl;
	}
	
	@ExcelField(title="毕业证", align=2, sort=32)
	public String getCertificateurl() {
		return certificateurl;
	}

	public void setCertificateurl(String certificateurl) {
		this.certificateurl = certificateurl;
	}
	
	@ExcelField(title="学位证", align=2, sort=33)
	public String getDegreecertificateurl() {
		return degreecertificateurl;
	}

	public void setDegreecertificateurl(String degreecertificateurl) {
		this.degreecertificateurl = degreecertificateurl;
	}
	
	@ExcelField(title="驾驶证", align=2, sort=34)
	public String getDriverlicenseurl() {
		return driverlicenseurl;
	}

	public void setDriverlicenseurl(String driverlicenseurl) {
		this.driverlicenseurl = driverlicenseurl;
	}
	
	@ExcelField(title="从业资格证", align=2, sort=35)
	public String getQualificationcertificateurl() {
		return qualificationcertificateurl;
	}

	public void setQualificationcertificateurl(String qualificationcertificateurl) {
		this.qualificationcertificateurl = qualificationcertificateurl;
	}
	
	@ExcelField(title="离职证明", align=2, sort=36)
	public String getLeavingcertificate() {
		return leavingcertificate;
	}

	public void setLeavingcertificate(String leavingcertificate) {
		this.leavingcertificate = leavingcertificate;
	}
	
	@ExcelField(title="个人简历", align=2, sort=37)
	public String getResumeurl() {
		return resumeurl;
	}

	public void setResumeurl(String resumeurl) {
		this.resumeurl = resumeurl;
	}
	
	@ExcelField(title="现居住地", align=2, sort=38)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="岗位", align=2, sort=39)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@ExcelField(title="试用期", align=2, sort=40)
	public String getProbationperiod() {
		return probationperiod;
	}

	public void setProbationperiod(String probationperiod) {
		this.probationperiod = probationperiod;
	}
	
	@ExcelField(title="试用期薪资", align=2, sort=41)
	public String getProbationperiodsalary() {
		return probationperiodsalary;
	}

	public void setProbationperiodsalary(String probationperiodsalary) {
		this.probationperiodsalary = probationperiodsalary;
	}
	
	@ExcelField(title="免责声明姓名", align=2, sort=42)
	public String getDisclaimername() {
		return disclaimername;
	}

	public void setDisclaimername(String disclaimername) {
		this.disclaimername = disclaimername;
	}
	
	@ExcelField(title="免责声明身份证号码", align=2, sort=43)
	public String getDisclaimerno() {
		return disclaimerno;
	}

	public void setDisclaimerno(String disclaimerno) {
		this.disclaimerno = disclaimerno;
	}
	
	@ExcelField(title="免责声明年", align=2, sort=44)
	public String getDisclaimeryear() {
		return disclaimeryear;
	}

	public void setDisclaimeryear(String disclaimeryear) {
		this.disclaimeryear = disclaimeryear;
	}
	
	@ExcelField(title="免责声明月", align=2, sort=45)
	public String getDisclaimermonth() {
		return disclaimermonth;
	}

	public void setDisclaimermonth(String disclaimermonth) {
		this.disclaimermonth = disclaimermonth;
	}
	
	@ExcelField(title="免责声明日", align=2, sort=46)
	public String getDisclaimerday() {
		return disclaimerday;
	}

	public void setDisclaimerday(String disclaimerday) {
		this.disclaimerday = disclaimerday;
	}
	
	@ExcelField(title="免责声明职业病", align=2, sort=47)
	public String getDisclaimerdisease() {
		return disclaimerdisease;
	}

	public void setDisclaimerdisease(String disclaimerdisease) {
		this.disclaimerdisease = disclaimerdisease;
	}
	
	@ExcelField(title="照片", align=2, sort=48)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@ExcelField(title="审核状态", align=2, sort=49)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public List<UserMember> getUserMemberList() {
		return userMemberList;
	}

	public void setUserMemberList(List<UserMember> userMemberList) {
		this.userMemberList = userMemberList;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Integer getIspermit() {
		return ispermit;
	}

	public void setIspermit(Integer ispermit) {
		this.ispermit = ispermit;
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

	public Integer getEntrytrain() {
		return entrytrain;
	}

	public void setEntrytrain(Integer entrytrain) {
		this.entrytrain = entrytrain;
	}

	public Integer getEntrytrain1() {
		return entrytrain1;
	}

	public void setEntrytrain1(Integer entrytrain1) {
		this.entrytrain1 = entrytrain1;
	}

	public Integer getEntrytrain2() {
		return entrytrain2;
	}

	public void setEntrytrain2(Integer entrytrain2) {
		this.entrytrain2 = entrytrain2;
	}

	public Integer getEntrytrain3() {
		return entrytrain3;
	}

	public void setEntrytrain3(Integer entrytrain3) {
		this.entrytrain3 = entrytrain3;
	}

	public Integer getEntrytrain4() {
		return entrytrain4;
	}

	public void setEntrytrain4(Integer entrytrain4) {
		this.entrytrain4 = entrytrain4;
	}

	public Integer getEntrytrain5() {
		return entrytrain5;
	}

	public void setEntrytrain5(Integer entrytrain5) {
		this.entrytrain5 = entrytrain5;
	}

	public Integer getEntrytrain6() {
		return entrytrain6;
	}

	public void setEntrytrain6(Integer entrytrain6) {
		this.entrytrain6 = entrytrain6;
	}

	public Integer getEntrytrain7() {
		return entrytrain7;
	}

	public void setEntrytrain7(Integer entrytrain7) {
		this.entrytrain7 = entrytrain7;
	}

	public Integer getEntrytrain8() {
		return entrytrain8;
	}

	public void setEntrytrain8(Integer entrytrain8) {
		this.entrytrain8 = entrytrain8;
	}

	public Integer getEntrytrain9() {
		return entrytrain9;
	}

	public void setEntrytrain9(Integer entrytrain9) {
		this.entrytrain9 = entrytrain9;
	}

	public Integer getEntrytrain10() {
		return entrytrain10;
	}

	public void setEntrytrain10(Integer entrytrain10) {
		this.entrytrain10 = entrytrain10;
	}

	public Integer getEntrytrain11() {
		return entrytrain11;
	}

	public void setEntrytrain11(Integer entrytrain11) {
		this.entrytrain11 = entrytrain11;
	}

	public Integer getEntrytrain12() {
		return entrytrain12;
	}

	public void setEntrytrain12(Integer entrytrain12) {
		this.entrytrain12 = entrytrain12;
	}

	public Date getEntrytraindate() {
		return entrytraindate;
	}

	public void setEntrytraindate(Date entrytraindate) {
		this.entrytraindate = entrytraindate;
	}

	public Integer getLeavestatus() {
		return leavestatus;
	}

	public void setLeavestatus(Integer leavestatus) {
		this.leavestatus = leavestatus;
	}

	public Date getLeavedate() {
		return leavedate;
	}

	public void setLeavedate(Date leavedate) {
		this.leavedate = leavedate;
	}

	public String getLeaveurl() {
		return leaveurl;
	}

	public void setLeaveurl(String leaveurl) {
		this.leaveurl = leaveurl;
	}

	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public Integer getIspermittemp() {
		return ispermittemp;
	}

	public void setIspermittemp(Integer ispermittemp) {
		this.ispermittemp = ispermittemp;
	}

	public Integer getStatustemp() {
		return statustemp;
	}

	public void setStatustemp(Integer statustemp) {
		this.statustemp = statustemp;
	}

//	public Integer getIsdel() {
//		return isdel;
//	}
//
//	public void setIsdel(Integer isdel) {
//		this.isdel = isdel;
//	}


	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getBankcardurl() {
		return bankcardurl;
	}

	public void setBankcardurl(String bankcardurl) {
		this.bankcardurl = bankcardurl;
	}

	public String getHealthurl() {
		return healthurl;
	}

	public void setHealthurl(String healthurl) {
		this.healthurl = healthurl;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPeopleStructType() {
		return peopleStructType;
	}

	public void setPeopleStructType(String peopleStructType) {
		this.peopleStructType = peopleStructType;
	}

	public String getUser_del_flag() {
		return user_del_flag;
	}

	public void setUser_del_flag(String user_del_flag) {
		this.user_del_flag = user_del_flag;
	}

	public String getResignation() {
		return resignation;
	}

	public void setResignation(String resignation) {
		this.resignation = resignation;
	}

}