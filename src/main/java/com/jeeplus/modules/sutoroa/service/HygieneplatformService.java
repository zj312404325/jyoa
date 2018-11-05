/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.modules.sutoroa.entity.Attachment;
import com.jeeplus.modules.sutoroa.entity.Hygieneplatformdepart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sutoroa.entity.Hygieneplatform;
import com.jeeplus.modules.sutoroa.dao.HygieneplatformDao;
//import sun.org.mozilla.javascript.internal.ast.FunctionNode;

/**
 * 8s检查表Service
 * @author cqj
 * @version 2018-02-26
 */
@Service
@Transactional(readOnly = true)
public class HygieneplatformService extends CrudService<HygieneplatformDao, Hygieneplatform> {
	@Autowired
	private HygieneplatformdepartService hygieneplatformdepartService;

	@Autowired
	private AttachmentService attachmentService;

	public Hygieneplatform get(String id) {
		Hygieneplatform hf=super.get(id);
		Hygieneplatformdepart hygieneplatformdepartTemp=new Hygieneplatformdepart();
		hygieneplatformdepartTemp.setHyqieneid(id);
		List<Hygieneplatformdepart> hygieneplatformdepartList=hygieneplatformdepartService.findList(hygieneplatformdepartTemp);
		if(FormatUtil.isNoEmpty(hygieneplatformdepartList)){
			hf.setHygieneplatformdepartList(hygieneplatformdepartList);
		}
		Attachment attachmentTemp=new Attachment();
		attachmentTemp.setHygieneplatformid(id);
		List<Attachment> attachmentList=attachmentService.findList(attachmentTemp);
		if(FormatUtil.isNoEmpty(attachmentList)){
			for (Attachment attachment:attachmentList) {
				if(FormatUtil.isNoEmpty(attachment.getUrl())){
					attachment.setFilename(FormatUtil.getfilename(attachment.getUrl()));
				}
			}
			hf.setAttachmentList(attachmentList);
		}
		return hf;
	}
	
	public List<Hygieneplatform> findList(Hygieneplatform hygieneplatform) {
		return super.findList(hygieneplatform);
	}
	
	public Page<Hygieneplatform> findPage(Page<Hygieneplatform> page, Hygieneplatform hygieneplatform) {
		return super.findPage(page, hygieneplatform);
	}
	
	@Transactional(readOnly = false)
	public void save(Hygieneplatform hygieneplatform) {
		if(FormatUtil.isNoEmpty(hygieneplatform.getId())){//修改
			Map m=new HashMap<String,Object>();
			m.put("id", hygieneplatform.getId());
			//删除明细
			hygieneplatformdepartService.delHygieneplatformdepart(m);
			//删除附件
			attachmentService.delAttachment(m);
			super.save(hygieneplatform);
			for (int i = 0; i < hygieneplatform.getHygieneplatformdepartList().size(); i++) {
				hygieneplatform.getHygieneplatformdepartList().get(i).setSort(i+1+"");
				hygieneplatform.getHygieneplatformdepartList().get(i).setHyqieneid(hygieneplatform.getId()+"");
				hygieneplatform.getHygieneplatformdepartList().get(i).setIsdel(0);
				hygieneplatformdepartService.save(hygieneplatform.getHygieneplatformdepartList().get(i));
			}
			//附件
			for (int i = 0; i < hygieneplatform.getAttachmentList().size(); i++) {
				hygieneplatform.getAttachmentList().get(i).setSort(i+1+"");
				hygieneplatform.getAttachmentList().get(i).setHygieneplatformid(hygieneplatform.getId());
				hygieneplatform.getAttachmentList().get(i).setIsdel(0);
				attachmentService.save(hygieneplatform.getAttachmentList().get(i));
			}
		}else{//新增
			super.save(hygieneplatform);
			for (int i = 0; i < hygieneplatform.getHygieneplatformdepartList().size(); i++) {
				hygieneplatform.getHygieneplatformdepartList().get(i).setSort(i+1+"");
				hygieneplatform.getHygieneplatformdepartList().get(i).setHyqieneid(hygieneplatform.getId()+"");
				hygieneplatformdepartService.save(hygieneplatform.getHygieneplatformdepartList().get(i));
			}
			//附件
			for (int i = 0; i < hygieneplatform.getAttachmentList().size(); i++) {
				hygieneplatform.getAttachmentList().get(i).setSort(i+1+"");
				hygieneplatform.getAttachmentList().get(i).setHygieneplatformid(hygieneplatform.getId());
				attachmentService.save(hygieneplatform.getAttachmentList().get(i));
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Hygieneplatform hygieneplatform) {
		super.delete(hygieneplatform);
	}
	
	
	
	
}