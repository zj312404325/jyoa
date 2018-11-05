/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.oa.entity.Oagroup;
import com.jeeplus.modules.oa.dao.OagroupDao;
import com.jeeplus.modules.oa.entity.Oagroupdtl;
import com.jeeplus.modules.oa.dao.OagroupdtlDao;

/**
 * oa传阅人员分组Service
 * @author 殷
 * @version 2017-01-20
 */
@Service
@Transactional(readOnly = true)
public class OagroupService extends CrudService<OagroupDao, Oagroup> {

	@Autowired
	private OagroupdtlDao oagroupdtlDao;
	
	public Oagroup get(String id) {
		Oagroup oagroup = super.get(id);
		oagroup.setOagroupdtlList(oagroupdtlDao.findList(new Oagroupdtl(oagroup)));
		return oagroup;
	}
	
	public List<Oagroup> findList(Oagroup oagroup) {
		List<Oagroup> list = super.findList(oagroup);
		for (Oagroup gp : list) {
			gp.setOagroupdtlList(oagroupdtlDao.findList(new Oagroupdtl(gp)));
		}
		return list;
	}
	
	public Page<Oagroup> findPage(Page<Oagroup> page, Oagroup oagroup) {
		return super.findPage(page, oagroup);
	}
	
	@Transactional(readOnly = false)
	public void save(Oagroup oagroup) {
		super.save(oagroup);
		for (Oagroupdtl oagroupdtl : oagroup.getOagroupdtlList()){
//			if (oagroupdtl.getId() == null){
//				continue;
//			}
			if (Oagroupdtl.DEL_FLAG_NORMAL.equals(oagroupdtl.getDelFlag())){
				if (StringUtils.isBlank(oagroupdtl.getId())){
					oagroupdtl.setOagroup(oagroup);
					oagroupdtl.preInsert();
					oagroupdtlDao.insert(oagroupdtl);
				}else{
					oagroupdtl.preUpdate();
					oagroupdtlDao.update(oagroupdtl);
				}
			}else{
				oagroupdtlDao.delete(oagroupdtl);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Oagroup oagroup) {
		super.delete(oagroup);
		oagroupdtlDao.delete(new Oagroupdtl(oagroup));
	}
	
}