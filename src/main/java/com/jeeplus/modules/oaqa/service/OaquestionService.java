/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oaqa.service;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.oaqa.dao.OaanswerDao;
import com.jeeplus.modules.oaqa.dao.OaquestionDao;
import com.jeeplus.modules.oaqa.entity.Oaanswer;
import com.jeeplus.modules.oaqa.entity.Oaquestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 问答Service
 * @author yc
 * @version 2018-03-26
 */
@Service
@Transactional(readOnly = true)
public class OaquestionService extends CrudService<OaquestionDao, Oaquestion> {

	@Autowired
	private OaanswerDao oaanswerDao;
	
	public Oaquestion get(String id) {
		Oaquestion oaquestion = super.get(id);
		oaquestion.setOaanswerList(oaanswerDao.findList(new Oaanswer(oaquestion)));
		return oaquestion;
	}
	
	public List<Oaquestion> findList(Oaquestion oaquestion) {
		return super.findList(oaquestion);
	}
	
	public Page<Oaquestion> findPage(Page<Oaquestion> page, Oaquestion oaquestion) {
		return super.findPage(page, oaquestion);
	}
	
	@Transactional(readOnly = false)
	public void save(Oaquestion oaquestion) {
		super.save(oaquestion);
		for (Oaanswer oaanswer : oaquestion.getOaanswerList()){
//			if (oaanswer.getId() == null){
//				continue;
//			}
			if (Oaanswer.DEL_FLAG_NORMAL.equals(oaanswer.getDelFlag())){
				if (StringUtils.isBlank(oaanswer.getId())){
					oaanswer.setQuertionid(oaquestion.getId());
					oaanswer.preInsert();
					oaanswerDao.insert(oaanswer);
				}else{
					oaanswer.preUpdate();
					oaanswerDao.update(oaanswer);
				}
			}else{
				oaanswerDao.delete(oaanswer);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Oaquestion oaquestion) {
		super.delete(oaquestion);
		oaanswerDao.delete(new Oaanswer(oaquestion));
	}

}