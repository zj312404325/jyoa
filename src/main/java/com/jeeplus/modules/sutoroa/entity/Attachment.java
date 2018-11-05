/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 8s检查表附件Entity
 * @author cqj
 * @version 2018-02-26
 */
public class Attachment extends DataEntity<Attachment> {
	
	private static final long serialVersionUID = 1L;
	private String hygieneplatformid;		// 主表id
	private String ip;		// ip
	private String url;		// 附件
	private Integer isdel;		// isdel
	private String sort;		// 排序
	private String filename;
	
	public Attachment() {
		super();
	}

	public Attachment(String id){
		super(id);
	}

	@ExcelField(title="主表id", align=2, sort=7)
	public String getHygieneplatformid() {
		return hygieneplatformid;
	}

	public void setHygieneplatformid(String hygieneplatformid) {
		this.hygieneplatformid = hygieneplatformid;
	}
	
	@ExcelField(title="ip", align=2, sort=8)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@ExcelField(title="附件", align=2, sort=9)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="isdel", align=2, sort=10)
	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
	@ExcelField(title="排序", align=2, sort=11)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}