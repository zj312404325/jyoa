/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sutoroa.entity.Hygieneplatformdepart;
import com.jeeplus.modules.sutoroa.dao.HygieneplatformdepartDao;

/**
 * 8s检查表内容Service
 * @author cqj
 * @version 2018-02-26
 */
@Service
@Transactional(readOnly = true)
public class HygieneplatformdepartService extends CrudService<HygieneplatformdepartDao, Hygieneplatformdepart> {

	public Hygieneplatformdepart get(String id) {
		return super.get(id);
	}
	
	public List<Hygieneplatformdepart> findList(Hygieneplatformdepart hygieneplatformdepart) {
		return super.findList(hygieneplatformdepart);
	}
	
	public Page<Hygieneplatformdepart> findPage(Page<Hygieneplatformdepart> page, Hygieneplatformdepart hygieneplatformdepart) {
		return super.findPage(page, hygieneplatformdepart);
	}
	
	@Transactional(readOnly = false)
	public void save(Hygieneplatformdepart hygieneplatformdepart) {
		super.save(hygieneplatformdepart);
	}
	
	@Transactional(readOnly = false)
	public void delete(Hygieneplatformdepart hygieneplatformdepart) {
		super.delete(hygieneplatformdepart);
	}

	@Transactional(readOnly = false)
	public void delHygieneplatformdepart(Map map) {
		dao.delHygieneplatformdepart(map);
	}
	
	
}