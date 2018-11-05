/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.ArrayList;
import java.util.List;

/**
 * 8s检查表Entity
 * @author cqj
 * @version 2018-02-26
 */
public class Hygieneplatform extends DataEntity<Hygieneplatform> {
	
	private static final long serialVersionUID = 1L;
	private String checkuser;		// 检查人
	private String checkdate;		// 日期
	private String address;		// 地点
	private String ip;		// ip
	private String url;		// url
	private String detailJson;
	private String attachJson;
	private List<Attachment> attachmentList=new ArrayList<Attachment>();
	private List<Hygieneplatformdepart> hygieneplatformdepartList=new ArrayList<Hygieneplatformdepart>();
	
	public Hygieneplatform() {
		super();
	}

	public Hygieneplatform(String id){
		super(id);
	}

	@ExcelField(title="检查人", align=2, sort=7)
	public String getCheckuser() {
		return checkuser;
	}

	public void setCheckuser(String checkuser) {
		this.checkuser = checkuser;
	}
	
	@ExcelField(title="日期", align=2, sort=8)
	public String getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	
	@ExcelField(title="地点", align=2, sort=9)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="ip", align=2, sort=10)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@ExcelField(title="url", align=2, sort=11)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getAttachJson() {
		return attachJson;
	}

	public void setAttachJson(String attachJson) {
		this.attachJson = attachJson;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<Hygieneplatformdepart> getHygieneplatformdepartList() {
		return hygieneplatformdepartList;
	}

	public void setHygieneplatformdepartList(List<Hygieneplatformdepart> hygieneplatformdepartList) {
		this.hygieneplatformdepartList = hygieneplatformdepartList;
	}
}