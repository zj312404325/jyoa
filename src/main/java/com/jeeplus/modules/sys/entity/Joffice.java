/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.TreeEntity;
import com.jeeplus.common.utils.Collections3;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 机构Entity
 * @author jeeplus
 * @version 2013-05-15
 */
public class Joffice{

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String pid;
	private List<Joffice> childrens = Lists.newArrayList();
	

	
	public Joffice(){

	}
	public Joffice(Office office){
		this.id=office.getId();
		this.name=office.getName();
		this.pid=office.getParentId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<Joffice> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Joffice> childrens) {
		this.childrens = childrens;
	}
}