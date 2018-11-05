/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oaqa.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 问答Entity
 * @author yc
 * @version 2018-03-26
 */
public class Oaanswer extends DataEntity<Oaanswer> {
	
	private static final long serialVersionUID = 1L;
	private String answer;		// 答
	private String quertionid;		// 问id 父类
	private Integer praise = 0;		// 赞
	
	public Oaanswer() {
		super();
	}

	public Oaanswer(Oaquestion q){
		this.quertionid = q.getId();
	}
	public Oaanswer(String id){
		super(id);
	}

	@ExcelField(title="答", align=2, sort=7)
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getQuertionid() {
		return quertionid;
	}

	public void setQuertionid(String quertionid) {
		this.quertionid = quertionid;
	}
	
	@ExcelField(title="赞", align=2, sort=9)
	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}
	
}