/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 档案管理Entity
 * @author cqj
 * @version 2017-11-24
 */
public class FileManagement extends DataEntity<FileManagement> {
	
	private static final long serialVersionUID = 1L;
	private String categoryid;		// 分类id
	private String categoryname;		// 分类名称
	private String title;		// 标题
	private String fileno;		// 档号
	private String rollno;		// 案卷号
	private String categoryno;		// 分类号
	private String fondno;		// 全宗号
	private String pieceno;		// 件号
	private String wirteno;		// 文号
	private String organization;		// 编制机构
	private String fileyear;		// 年度
	private String filemonth;
	private String secret;		// 密级
	private String position;		// 存放位置
	private Integer num;		// 份数
	private Integer pages;		// 页数
	private String savetime;		// 保存期限
	private String destory;		// 是否销毁
	private Date effectivedate;		// 生效时间
	private Date expirydate;		// 失效时间
	private String fileurl;		// 文件路径
	private List<Annotate> annotateList = Lists.newArrayList();		// 子表列表
	private List<EditRecord> editRecordList = Lists.newArrayList();		// 子表列表
	private String filepdf;	//预览文件
	private String createusername; //创建者姓名
	private String memo; //备注
	private String isManager;//是否是档案管理员
	private String categoryIds;
	private Date startdate;
	private Date enddate;
	private String ignoreEditRecord;//是否或略记录文件版本
	private String complete;//0进行中1结束（转pdf）
	private String isCurrentMonth;
	private String filename;
	private String filefrom;
	private String fileid;
	private String receiveuserids;
	private String receiveusernames;
	
	public FileManagement() {
		super();
	}

	public FileManagement(String id){
		super(id);
	}

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
	@ExcelField(title="分类名称", align=2, sort=8)
	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	@ExcelField(title="标题", align=2, sort=9)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="档号", align=2, sort=10)
	public String getFileno() {
		return fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}
	
	@ExcelField(title="案卷号", align=2, sort=11)
	public String getRollno() {
		return rollno;
	}

	public void setRollno(String rollno) {
		this.rollno = rollno;
	}
	
	@ExcelField(title="分类号", align=2, sort=12)
	public String getCategoryno() {
		return categoryno;
	}

	public void setCategoryno(String categoryno) {
		this.categoryno = categoryno;
	}
	
	@ExcelField(title="全宗号", align=2, sort=13)
	public String getFondno() {
		return fondno;
	}

	public void setFondno(String fondno) {
		this.fondno = fondno;
	}
	
	@ExcelField(title="件号", align=2, sort=14)
	public String getPieceno() {
		return pieceno;
	}

	public void setPieceno(String pieceno) {
		this.pieceno = pieceno;
	}
	
	@ExcelField(title="文号", align=2, sort=15)
	public String getWirteno() {
		return wirteno;
	}

	public void setWirteno(String wirteno) {
		this.wirteno = wirteno;
	}
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	@ExcelField(title="年度", align=2, sort=17)
	public String getFileyear() {
		return fileyear;
	}

	public void setFileyear(String fileyear) {
		this.fileyear = fileyear;
	}
	
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	@ExcelField(title="存放位置", align=2, sort=19)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}
	
	public String getSavetime() {
		return savetime;
	}

	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	
	public String getDestory() {
		return destory;
	}

	public void setDestory(String destory) {
		this.destory = destory;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="生效时间", align=2, sort=24)
	public Date getEffectivedate() {
		return effectivedate;
	}

	public void setEffectivedate(Date effectivedate) {
		this.effectivedate = effectivedate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="失效时间", align=2, sort=25)
	public Date getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}
	
	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	
	public List<Annotate> getAnnotateList() {
		return annotateList;
	}

	public void setAnnotateList(List<Annotate> annotateList) {
		this.annotateList = annotateList;
	}
	public List<EditRecord> getEditRecordList() {
		return editRecordList;
	}

	public void setEditRecordList(List<EditRecord> editRecordList) {
		this.editRecordList = editRecordList;
	}

	public String getFilepdf() {
		return filepdf;
	}

	public void setFilepdf(String filepdf) {
		this.filepdf = filepdf;
	}

	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIsManager() {
		return isManager;
	}

	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
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

	public String getIgnoreEditRecord() {
		return ignoreEditRecord;
	}

	public void setIgnoreEditRecord(String ignoreEditRecord) {
		this.ignoreEditRecord = ignoreEditRecord;
	}

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public String getIsCurrentMonth() {
		return isCurrentMonth;
	}

	public void setIsCurrentMonth(String isCurrentMonth) {
		this.isCurrentMonth = isCurrentMonth;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilefrom() {
		return filefrom;
	}

	public void setFilefrom(String filefrom) {
		this.filefrom = filefrom;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
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

	public String getFilemonth() {
		return filemonth;
	}

	public void setFilemonth(String filemonth) {
		this.filemonth = filemonth;
	}
}