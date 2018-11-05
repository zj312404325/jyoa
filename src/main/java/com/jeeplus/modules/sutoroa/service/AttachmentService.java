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
import com.jeeplus.modules.sutoroa.entity.Attachment;
import com.jeeplus.modules.sutoroa.dao.AttachmentDao;

/**
 * 8s检查表附件Service
 * @author cqj
 * @version 2018-02-26
 */
@Service
@Transactional(readOnly = true)
public class AttachmentService extends CrudService<AttachmentDao, Attachment> {

	public Attachment get(String id) {
		return super.get(id);
	}
	
	public List<Attachment> findList(Attachment attachment) {
		return super.findList(attachment);
	}
	
	public Page<Attachment> findPage(Page<Attachment> page, Attachment attachment) {
		return super.findPage(page, attachment);
	}
	
	@Transactional(readOnly = false)
	public void save(Attachment attachment) {
		super.save(attachment);
	}
	
	@Transactional(readOnly = false)
	public void delete(Attachment attachment) {
		super.delete(attachment);
	}

	@Transactional(readOnly = false)
	public void delAttachment(Map map) {
		dao.delAttachment(map);
	}
	
}