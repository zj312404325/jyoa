/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.institution.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jeeplus.common.persistence.TreeEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * oa制度Entity
 * @author 陈
 * @version 2016-12-27
 */
public class Institution extends TreeEntity<Institution> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
//	private Institution parent;		// 父级编号
//	private String parentIds;		// 所有父级编号
//	private String name;		// 名称
//	private Integer sort;		// 排序
	private String ilevel;		// 层级
	private String itype;		// 类型
	private String fileurl;   //文件路径

	private String receivedeptids;		// 授权部门ids
	private String receivedeptnames;

	private String sourcefileurl;   //源文件路径

	private List<Institution> list;
	
	public Institution() {
		super();
	}

	public Institution(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=7)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="内容", align=2, sort=8)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	@ExcelField(title="父级编号", align=2, sort=9)
	public Institution getParent() {
		return parent;
	}

	public void setParent(Institution parent) {
		this.parent = parent;
	}
	
	@ExcelField(title="所有父级编号", align=2, sort=10)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@ExcelField(title="名称", align=2, sort=11)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="排序", align=2, sort=12)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="层级", align=2, sort=13)
	public String getIlevel() {
		return ilevel;
	}

	public void setIlevel(String ilevel) {
		this.ilevel = ilevel;
	}

	public List<Institution> getList() {
		return list;
	}

	public void setList(List<Institution> list) {
		this.list = list;
	}

	public String getItype() {
		return itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public String getReceivedeptids() {
		return receivedeptids;
	}

	public void setReceivedeptids(String receivedeptids) {
		this.receivedeptids = receivedeptids;
	}

	public String getReceivedeptnames() {
		return receivedeptnames;
	}

	public void setReceivedeptnames(String receivedeptnames) {
		this.receivedeptnames = receivedeptnames;
	}

	public String getSourcefileurl() {
		return sourcefileurl;
	}

	public void setSourcefileurl(String sourcefileurl) {
		this.sourcefileurl = sourcefileurl;
	}
}