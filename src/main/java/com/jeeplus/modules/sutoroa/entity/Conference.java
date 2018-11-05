/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;

import java.util.ArrayList;
import java.util.List;

/**
 * 每日团队风采Entity
 * @author cqj
 * @version 2018-02-23
 */
public class Conference extends DataEntity<Conference> {
	
	private static final long serialVersionUID = 1L;
	private String department;		// 部门
	private String team;		// 团队
	private String compere;		// 主持人
	private Integer shouldnumber;		// 应到人数
	private Integer realnumber;		// 实到人数
	private String experience;		// 工作经验
	private String workplan;		// 工作计划
	private String recommend;		// 今日建议与意见
	private String teampic;		// 图片地址
	private String ip;		// ip
	private String createusername;		// 上传人
	private Integer category;		// 类别
	private String bigimg;		// 大图
	private String smallimg;		// 小图
	private String filename;		// 文件名
    private List<Leavemsg> leavemsgList=new ArrayList<Leavemsg>();
    private List<Office> officeList=new ArrayList<Office>();//主页菜单生成是使用
	private String officeid;//部门id
	
	public Conference() {
		super();
	}

	public Conference(String id){
		super(id);
	}

	@ExcelField(title="部门", align=2, sort=7)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@ExcelField(title="团队", align=2, sort=8)
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	@ExcelField(title="主持人", align=2, sort=9)
	public String getCompere() {
		return compere;
	}

	public void setCompere(String compere) {
		this.compere = compere;
	}
	
	@NotNull(message="应到人数不能为空")
	@ExcelField(title="应到人数", align=2, sort=10)
	public Integer getShouldnumber() {
		return shouldnumber;
	}

	public void setShouldnumber(Integer shouldnumber) {
		this.shouldnumber = shouldnumber;
	}
	
	@NotNull(message="实到人数不能为空")
	@ExcelField(title="实到人数", align=2, sort=11)
	public Integer getRealnumber() {
		return realnumber;
	}

	public void setRealnumber(Integer realnumber) {
		this.realnumber = realnumber;
	}
	
	@ExcelField(title="工作经验", align=2, sort=12)
	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}
	
	@ExcelField(title="工作计划", align=2, sort=13)
	public String getWorkplan() {
		return workplan;
	}

	public void setWorkplan(String workplan) {
		this.workplan = workplan;
	}
	
	@ExcelField(title="今日建议与意见", align=2, sort=14)
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
	@ExcelField(title="图片地址", align=2, sort=15)
	public String getTeampic() {
		return teampic;
	}

	public void setTeampic(String teampic) {
		this.teampic = teampic;
	}
	
	@ExcelField(title="ip", align=2, sort=16)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@ExcelField(title="上传人", align=2, sort=17)
	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	
	@ExcelField(title="类别", align=2, sort=18)
	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}
	
	@ExcelField(title="大图", align=2, sort=19)
	public String getBigimg() {
		return bigimg;
	}

	public void setBigimg(String bigimg) {
		this.bigimg = bigimg;
	}
	
	@ExcelField(title="小图", align=2, sort=20)
	public String getSmallimg() {
		return smallimg;
	}

	public void setSmallimg(String smallimg) {
		this.smallimg = smallimg;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

    public List<Leavemsg> getLeavemsgList() {
        return leavemsgList;
    }

    public void setLeavemsgList(List<Leavemsg> leavemsgList) {
        this.leavemsgList = leavemsgList;
    }

	public List<Office> getOfficeList() {
		return officeList;
	}

	public void setOfficeList(List<Office> officeList) {
		this.officeList = officeList;
	}

	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
}