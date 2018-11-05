/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;

import java.util.Date;


/**
 * 通知通告记录Entity
 * @author jeeplus
 * @version 2014-05-16
 */
public class OaNotifyFile extends DataEntity<OaNotifyFile> {

	private static final long serialVersionUID = 1L;
	private OaNotify oaNotify;		// 通知通告ID
	private User user;		// 上传人
	private Date uploadDate;		// 上传时间
	private String fileurl;
	private String filename;
	private String canEdit;//标记使用


	public OaNotifyFile() {
		super();
	}

	public OaNotifyFile(String id){
		super(id);
	}

	public OaNotifyFile(OaNotify oaNotify){
		this.oaNotify = oaNotify;
	}

	public OaNotify getOaNotify() {
		return oaNotify;
	}

	public void setOaNotify(OaNotify oaNotify) {
		this.oaNotify = oaNotify;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(String canEdit) {
		this.canEdit = canEdit;
	}
}