/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oaqa.entity;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 问答Entity
 * @author yc
 * @version 2018-03-26
 */
public class Oaquestion extends DataEntity<Oaquestion> {
	
	private static final long serialVersionUID = 1L;
	private String question;		// 问
	private String var1;
	private List<Oaanswer> oaanswerList = Lists.newArrayList();		// 子表列表
	private int answercount;// not db
	private String hasanswer;// not db
	
	public Oaquestion() {
		super();
	}

	public Oaquestion(String id){
		super(id);
	}

	@ExcelField(title="问", align=2, sort=7)
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public List<Oaanswer> getOaanswerList() {
		return oaanswerList;
	}

	public void setOaanswerList(List<Oaanswer> oaanswerList) {
		this.oaanswerList = oaanswerList;
	}

	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}

	public int getAnswercount() {
		return answercount;
	}

	public void setAnswercount(int answercount) {
		this.answercount = answercount;
	}

	public String getHasanswer() {
		return hasanswer;
	}

	public void setHasanswer(String hasanswer) {
		this.hasanswer = hasanswer;
	}
}