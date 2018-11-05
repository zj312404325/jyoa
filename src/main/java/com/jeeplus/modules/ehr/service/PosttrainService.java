/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.ehr.entity.Posttrain;
import com.jeeplus.modules.ehr.dao.PosttrainDao;

/**
 * 岗位培训Service
 * @author yc
 * @version 2017-10-26
 */
@Service
@Transactional(readOnly = true)
public class PosttrainService extends CrudService<PosttrainDao, Posttrain> {

	public Posttrain get(String id) {
		return super.get(id);
	}
	
	public List<Posttrain> findList(Posttrain posttrain) {
		return super.findList(posttrain);
	}
	
	public Page<Posttrain> findPage(Page<Posttrain> page, Posttrain posttrain) {
		return super.findPage(page, posttrain);
	}
	
	@Transactional(readOnly = false)
	public void save(Posttrain posttrain) {
		super.save(posttrain);
	}
	
	@Transactional(readOnly = false)
	public void delete(Posttrain posttrain) {
		super.delete(posttrain);
	}

	@Transactional(readOnly = false)
	public void savePosttrain(Posttrain entity) {
		if (entity.getIsNewRecord()){
			entity.setId(IdGen.uuid());
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(entity.getCreateBy());
			entity.setCreateDate(new Date());
			dao.insert(entity);
		}else{
			entity.setUpdateDate(new Date());
			dao.update(entity);
		}
	}
}