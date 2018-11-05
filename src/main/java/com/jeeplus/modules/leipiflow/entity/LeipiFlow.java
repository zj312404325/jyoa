/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 雷劈流程模块Entity
 * @author 陈钱江
 * @version 2017-09-05
 */
public class LeipiFlow extends DataEntity<LeipiFlow> {
	
	private static final long serialVersionUID = 1L;
	private Integer catid=0;		// 分类
	private String formid;		// 表单id
	private Integer flowtype=0;		// 流程类型
	private String flowname;		// 流程名称
	private String flowdesc;		// 描述
	private String sortorder;		// 排序
	private Integer status=0;		// 状态
	private Integer isdel=0;		// 是否删除
	private Date updatetime;		// 更新时间
	private Date dateline=new Date();		// 创建时间
	private Integer leipiflowtype=0;
	
	private String receivedeptids;		// 授权部门ids
	private String receiveuserids;		// 授权人员ids
	private String receivedeptnames;
	private String receiveusernames;

	public LeipiFlow() {
		super();
	}

	public LeipiFlow(String id){
		super(id);
	}

	@ExcelField(title="分类", align=2, sort=1)
	public Integer getCatid() {
		return catid;
	}

	public void setCatid(Integer catid) {
		this.catid = catid;
	}
	
	@ExcelField(title="表单id", align=2, sort=2)
	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}
	
	@ExcelField(title="流程类型", dictType="flowtype", align=2, sort=3)
	public Integer getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(Integer flowtype) {
		this.flowtype = flowtype;
	}
	
	@ExcelField(title="流程名称", align=2, sort=4)
	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}
	
	@ExcelField(title="描述", align=2, sort=5)
	public String getFlowdesc() {
		return flowdesc;
	}

	public void setFlowdesc(String flowdesc) {
		this.flowdesc = flowdesc;
	}
	
	@ExcelField(title="排序", align=2, sort=6)
	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	
	@ExcelField(title="状态", dictType="status", align=2, sort=7)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="是否删除", align=2, sort=8)
	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=9)
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=10)
	public Date getDateline() {
		return dateline;
	}

	public void setDateline(Date dateline) {
		this.dateline = dateline;
	}

	public String getReceivedeptids() {
		return receivedeptids;
	}

	public void setReceivedeptids(String receivedeptids) {
		this.receivedeptids = receivedeptids;
	}

	public String getReceiveuserids() {
		return receiveuserids;
	}

	public void setReceiveuserids(String receiveuserids) {
		this.receiveuserids = receiveuserids;
	}

	public String getReceivedeptnames() {
		return receivedeptnames;
	}

	public void setReceivedeptnames(String receivedeptnames) {
		this.receivedeptnames = receivedeptnames;
	}

	public String getReceiveusernames() {
		return receiveusernames;
	}

	public void setReceiveusernames(String receiveusernames) {
		this.receiveusernames = receiveusernames;
	}

	public Integer getLeipiflowtype() {
		return leipiflowtype;
	}

	public void setLeipiflowtype(Integer leipiflowtype) {
		this.leipiflowtype = leipiflowtype;
	}
}