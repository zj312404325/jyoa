/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同到期人员考核申请Entity
 * @author cqj
 * @version 2017-10-31
 */
public class ContractExpirate extends DataEntity<ContractExpirate> {
	
	private static final long serialVersionUID = 1L;
	private String userid;		// 被考核人id
	private String userno;		// 被考核人编号
	private String username;		// 被考核人名字
	private String officeid;		// 被考核人部门id
	private String officename;		// 被考核人部门
	private String stationid;		// 被考核人岗位id
	private String stationname;		// 被考核人岗位
	private String major;		// 专业
	private String education;		// 学历
	private Date birth;		// 出生年月
	private Date entrytime;		// 入职时间
	private String address;		// 家庭住址
	private Date contractdatestart;		// 考核合同期开始
	private Date contractdateend;		// 考核合同期结束
	private String checkuserid;		// 考核人id
	private String checkuserno;		// 考核人编号
	private String checkusername;		// 考核人姓名
	private String checkofficeid;		// 考核人部门id
	private String checkofficename;		// 考核人部门
	private String checkstationid;		// 考核人岗位id
	private String checkstationname;		// 考核人岗位
	private String knowledgeskill;		// 知识技能
	private String execute;		// 执行力
	private String organization;		// 组织协调
	private String formulate;		// 制度
	private String learn;		// 学习
	private String innovate;		// 创新
	private Integer score;		// 得分
	private String evaluate;		// 评价
	private String recommend;		// 建议
	private String status="-1";		// 状态
	private String recommendother;		// 考核人其他建议
	private String leaderrecommend;		// 分管领导建议
	private String hrrecommend;		// 人事建议
	private String ceorecommend;		// 总经理建议
	private Integer hasaudit=-1;
	private String contract;//合同下载
	private Date startdate;//合同开始时间
	private Date enddate;//合同结束时间
	
	public ContractExpirate() {
		super();
	}

	public ContractExpirate(String id){
		super(id);
	}

	@ExcelField(title="被考核人id", align=2, sort=7)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@ExcelField(title="被考核人编号", align=2, sort=8)
	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}
	
	@ExcelField(title="被考核人名字", align=2, sort=9)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title="被考核人部门id", align=2, sort=10)
	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
	
	@ExcelField(title="被考核人部门", align=2, sort=11)
	public String getOfficename() {
		return officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}
	
	@ExcelField(title="被考核人岗位id", align=2, sort=12)
	public String getStationid() {
		return stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}
	
	@ExcelField(title="被考核人岗位", align=2, sort=13)
	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}
	
	@ExcelField(title="专业", align=2, sort=14)
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	
	@ExcelField(title="学历", align=2, sort=15)
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="出生年月", align=2, sort=16)
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="入职时间", align=2, sort=17)
	public Date getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}
	
	@ExcelField(title="家庭住址", align=2, sort=18)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="考核合同期开始", align=2, sort=19)
	public Date getContractdatestart() {
		return contractdatestart;
	}

	public void setContractdatestart(Date contractdatestart) {
		this.contractdatestart = contractdatestart;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="考核合同期结束", align=2, sort=20)
	public Date getContractdateend() {
		return contractdateend;
	}

	public void setContractdateend(Date contractdateend) {
		this.contractdateend = contractdateend;
	}
	
	@ExcelField(title="考核人id", align=2, sort=21)
	public String getCheckuserid() {
		return checkuserid;
	}

	public void setCheckuserid(String checkuserid) {
		this.checkuserid = checkuserid;
	}
	
	@ExcelField(title="考核人编号", align=2, sort=22)
	public String getCheckuserno() {
		return checkuserno;
	}

	public void setCheckuserno(String checkuserno) {
		this.checkuserno = checkuserno;
	}
	
	@ExcelField(title="考核人姓名", align=2, sort=23)
	public String getCheckusername() {
		return checkusername;
	}

	public void setCheckusername(String checkusername) {
		this.checkusername = checkusername;
	}
	
	@ExcelField(title="考核人部门id", align=2, sort=24)
	public String getCheckofficeid() {
		return checkofficeid;
	}

	public void setCheckofficeid(String checkofficeid) {
		this.checkofficeid = checkofficeid;
	}
	
	@ExcelField(title="考核人部门", align=2, sort=25)
	public String getCheckofficename() {
		return checkofficename;
	}

	public void setCheckofficename(String checkofficename) {
		this.checkofficename = checkofficename;
	}
	
	@ExcelField(title="考核人岗位id", align=2, sort=26)
	public String getCheckstationid() {
		return checkstationid;
	}

	public void setCheckstationid(String checkstationid) {
		this.checkstationid = checkstationid;
	}
	
	@ExcelField(title="考核人岗位", align=2, sort=27)
	public String getCheckstationname() {
		return checkstationname;
	}

	public void setCheckstationname(String checkstationname) {
		this.checkstationname = checkstationname;
	}
	
	@ExcelField(title="知识技能", dictType="", align=2, sort=28)
	public String getKnowledgeskill() {
		return knowledgeskill;
	}

	public void setKnowledgeskill(String knowledgeskill) {
		this.knowledgeskill = knowledgeskill;
	}
	
	@ExcelField(title="执行力", dictType="", align=2, sort=29)
	public String getExecute() {
		return execute;
	}

	public void setExecute(String execute) {
		this.execute = execute;
	}
	
	@ExcelField(title="组织协调", dictType="", align=2, sort=30)
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	@ExcelField(title="制度", dictType="", align=2, sort=31)
	public String getFormulate() {
		return formulate;
	}

	public void setFormulate(String formulate) {
		this.formulate = formulate;
	}
	
	@ExcelField(title="学习", dictType="", align=2, sort=32)
	public String getLearn() {
		return learn;
	}

	public void setLearn(String learn) {
		this.learn = learn;
	}
	
	@ExcelField(title="创新", dictType="", align=2, sort=33)
	public String getInnovate() {
		return innovate;
	}

	public void setInnovate(String innovate) {
		this.innovate = innovate;
	}
	
	@NotNull(message="得分不能为空")
	@ExcelField(title="得分", align=2, sort=34)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	@ExcelField(title="评价", align=2, sort=35)
	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	
	@ExcelField(title="建议", align=2, sort=36)
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
	@ExcelField(title="状态", align=2, sort=37)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecommendother() {
		return recommendother;
	}

	public void setRecommendother(String recommendother) {
		this.recommendother = recommendother;
	}

	public String getLeaderrecommend() {
		return leaderrecommend;
	}

	public void setLeaderrecommend(String leaderrecommend) {
		this.leaderrecommend = leaderrecommend;
	}

	public String getHrrecommend() {
		return hrrecommend;
	}

	public void setHrrecommend(String hrrecommend) {
		this.hrrecommend = hrrecommend;
	}

	public String getCeorecommend() {
		return ceorecommend;
	}

	public void setCeorecommend(String ceorecommend) {
		this.ceorecommend = ceorecommend;
	}

	public Integer getHasaudit() {
		return hasaudit;
	}

	public void setHasaudit(Integer hasaudit) {
		this.hasaudit = hasaudit;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
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
	
}