/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;


/**
 * 发件箱Entity
 * @author jeeplus
 * @version 2015-11-15
 */
public class MailBox extends DataEntity<MailBox> {
	
	private static final long serialVersionUID = 1L;
	private String readstatus;		// 状态 0 未读 1 已读
	private User sender;		// 发件人
	private User receiver;		// 收件人
	private Date sendtime;		// 发送时间
	private Mail mail;		// 邮件外键 父类
	private boolean exclude=false;//通知数目查询的时候排除当前条数
	
	public MailBox() {
		super();
	}

	public MailBox(String id){
		super(id);
	}

	public MailBox(Mail mail){
		this.mail = mail;
	}

	@Length(min=0, max=45, message="状态 0 未读 1 已读长度必须介于 0 和 45 之间")
	public String getReadstatus() {
		return readstatus;
	}

	public void setReadstatus(String readstatus) {
		this.readstatus = readstatus;
	}
	
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}
	
	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	
	@Length(min=0, max=64, message="邮件外键长度必须介于 0 和 64 之间")
	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public boolean isExclude() {
		return exclude;
	}

	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}
	
}