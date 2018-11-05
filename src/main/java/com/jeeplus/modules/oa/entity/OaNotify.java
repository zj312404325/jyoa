/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.entity;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 通知通告Entity
 * @author jeeplus
 * @version 2014-05-16
 */
public class OaNotify extends DataEntity<OaNotify> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型
	private String title;		// 标题
	private String content;		// 类型
	private String oafiles;		// 附件
	private String status;		// 状态

	private String readNum="0";		// 已读
	private String unReadNum="0";	// 未读
	
	private String commentNum="0";		// 已确认
	private String unCommentNum="0";	// 未确认
	
	private boolean isSelf;		// 是否只查询自己的通知
	
	private String readFlag;	// 本人阅读状态  查询用
	
	private String mobileremind;
	private String recordremind;
	private String secretsend;
	private String isallow;
	
	private boolean exclude=false;//通知数目查询的时候排除当前条数
	
	private List<OaNotifyRecord> oaNotifyRecordList = Lists.newArrayList();
	private List<OaNotifyFile> oaNotifyFileList = Lists.newArrayList();
	
	public OaNotify() {
		super();
	}

	public OaNotify(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标题长度必须介于 0 和 200 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=1, message="类型长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=2000, message="附件长度必须介于 0 和 2000 之间")
	public String getOafiles() {
		return oafiles;
	}

	public void setOafiles(String oafiles) {
		this.oafiles = oafiles;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}

	public String getUnReadNum() {
		return unReadNum;
	}

	public void setUnReadNum(String unReadNum) {
		this.unReadNum = unReadNum;
	}
	
	public List<OaNotifyRecord> getOaNotifyRecordList() {
		return oaNotifyRecordList;
	}

	public void setOaNotifyRecordList(List<OaNotifyRecord> oaNotifyRecordList) {
		this.oaNotifyRecordList = oaNotifyRecordList;
	}

	public List<OaNotifyFile> getOaNotifyFileList() {
		return oaNotifyFileList;
	}

	public void setOaNotifyFileList(List<OaNotifyFile> oaNotifyFileList) {
		this.oaNotifyFileList = oaNotifyFileList;
	}

	/**
	 * 获取通知发送记录用户ID
	 * @return
	 */
	public String getOaNotifyRecordIds() {
		return Collections3.extractToString(oaNotifyRecordList, "user.id", ",") ;
	}
	
	public String getOaNotifyRecordIds2() {
		return "'"+Collections3.extractToString(oaNotifyRecordList, "user.id", "','")+"'" ;
	}
	
	/**
	 * 设置通知发送记录用户ID
	 * @return
	 */
	public void setOaNotifyRecordIds(String oaNotifyRecord) {
		this.oaNotifyRecordList = Lists.newArrayList();
		for (String id : StringUtils.split(oaNotifyRecord, ",")){
			OaNotifyRecord entity = new OaNotifyRecord();
			entity.setId(IdGen.uuid());
			entity.setOaNotify(this);
			entity.setUser(new User(id));
			entity.setReadFlag("0");
			this.oaNotifyRecordList.add(entity);
		}
	}

	public String getOaNotifyFileUrls() {
		return Collections3.extractToString(oaNotifyFileList, "fileurl", ",") ;
	}
	public String getOaNotifyFileUrls2() {
		return "'"+Collections3.extractToString(oaNotifyFileList, "fileurl", "','")+"'" ;
	}
	public void setOaNotifyFileUrls(String oaNotifyFile) {
		this.oaNotifyFileList = Lists.newArrayList();
		for (String url : StringUtils.split(oaNotifyFile, ",")){
			OaNotifyFile entity = new OaNotifyFile();
			entity.setId(IdGen.uuid());
			entity.setOaNotify(this);
			entity.setFileurl(url);
			this.oaNotifyFileList.add(entity);
		}
	}

	/**
	 * 获取通知发送记录用户Name
	 * @return
	 */
	public String getOaNotifyRecordNames() {
		return Collections3.extractToString(oaNotifyRecordList, "user.name", ",") ;
	}
	
	/**
	 * 设置通知发送记录用户Name
	 * @return
	 */
	public void setOaNotifyRecordNames(String oaNotifyRecord) {
		// 什么也不做
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	public String getUnCommentNum() {
		return unCommentNum;
	}

	public void setUnCommentNum(String unCommentNum) {
		this.unCommentNum = unCommentNum;
	}

	public String getMobileremind() {
		return mobileremind;
	}

	public void setMobileremind(String mobileremind) {
		this.mobileremind = mobileremind;
	}

	public String getRecordremind() {
		return recordremind;
	}

	public void setRecordremind(String recordremind) {
		this.recordremind = recordremind;
	}

	public String getSecretsend() {
		return secretsend;
	}

	public void setSecretsend(String secretsend) {
		this.secretsend = secretsend;
	}

	public String getIsallow() {
		return isallow;
	}

	public void setIsallow(String isallow) {
		this.isallow = isallow;
	}

	public boolean isExclude() {
		return exclude;
	}

	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}

}