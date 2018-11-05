/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.service;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.oa.dao.OaNotifyDao;
import com.jeeplus.modules.oa.dao.OaNotifyFileDao;
import com.jeeplus.modules.oa.dao.OaNotifyRecordDao;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyFile;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 通知通告Service
 * @author jeeplus
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OaNotifyService extends CrudService<OaNotifyDao, OaNotify> {

	@Autowired
	private OaNotifyRecordDao oaNotifyRecordDao;
	@Autowired
	private OaNotifyFileDao oaNotifyFileDao;

	public OaNotify get(String id) {
		OaNotify entity = dao.get(id);
		return entity;
	}
	
	/**
	 * 获取通知发送记录
	 * @param oaNotify
	 * @return
	 */
	public OaNotify getRecordList(OaNotify oaNotify) {
		oaNotify.setOaNotifyRecordList(oaNotifyRecordDao.findList(new OaNotifyRecord(oaNotify)));
		oaNotify.setOaNotifyFileList(oaNotifyFileDao.findList(new OaNotifyFile(oaNotify)));
		return oaNotify;
	}
	
	public Page<OaNotify> find(Page<OaNotify> page, OaNotify oaNotify) {
		oaNotify.setPage(page);
		page.setList(dao.findList(oaNotify));
		return page;
	}
	
	public Page<OaNotifyRecord> find(Page<OaNotifyRecord> page, OaNotifyRecord oaNotifyrecord) {
		oaNotifyrecord.setPage(page);
		page.setList(oaNotifyRecordDao.findList(oaNotifyrecord));
		return page;
	}
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify) {
		return dao.findCount(oaNotify);
	}
	
	@Transactional(readOnly = false)
	public void save(OaNotify oaNotify) {
		super.save(oaNotify);
		
		// 更新发送接受人记录
		oaNotifyRecordDao.deleteByOaNotifyId(oaNotify.getId());
		if (oaNotify.getOaNotifyRecordList().size() > 0){
			for (OaNotifyRecord oar:oaNotify.getOaNotifyRecordList()) {
				User usr=UserUtils.get(oar.getUser().getId());
                oar.setUserName(usr.getName());
				oar.setOfficeName(usr.getOfficeName());
				oar.setStationName(usr.getStationName());
				oar.setCompanyName(usr.getCompanyName());
			}
			oaNotifyRecordDao.insertAll(oaNotify.getOaNotifyRecordList());
		}
		// 更新文件记录
		oaNotifyFileDao.deleteByOaNotifyId(oaNotify.getId());
		if (oaNotify.getOaNotifyFileList().size() > 0){
			List<OaNotifyFile> list = oaNotify.getOaNotifyFileList();
			User loginUser = UserUtils.get(oaNotify.getCreateBy().getId());
			for (OaNotifyFile f : list){
				f.setUploadDate(new Date());
				f.setUser(loginUser);
				f.setFilename(this.getFileName(f.getFileurl()));
			}
			oaNotifyFileDao.insertAll(oaNotify.getOaNotifyFileList());
		}
	}

	private static String getFileName(String filename) {
		int start = filename.lastIndexOf("/");
		if (start != -1 ) {
			return filename.substring(start + 1);
		} else {
			return "";
		}
	}
	
	/**
	 * 更新阅读状态
	 */
	@Transactional(readOnly = false)
	public void updateReadFlag(OaNotify oaNotify) {
		OaNotifyRecord oaNotifyRecord = new OaNotifyRecord(oaNotify);
		oaNotifyRecord.setUser(oaNotify.getCurrentUser());
		oaNotifyRecord.setReadDate(new Date());
		oaNotifyRecord.setReadFlag("1");
		oaNotifyRecordDao.update(oaNotifyRecord);
	}
	
	/**
	 * 更新阅读状态
	 */
	@Transactional(readOnly = false)
	public void updateComment(OaNotifyRecord oaNotifyRecord) {
		
		oaNotifyRecord.setUser(oaNotifyRecord.getCurrentUser());
		oaNotifyRecord.setCommentDate(new Date());
		oaNotifyRecord.setCommentFlag("1");
		oaNotifyRecord.setReadFlag("2");
		oaNotifyRecordDao.updateComment(oaNotifyRecord);
	}
	
	public void updateOaNotify(OaNotify oaNotify) {
		dao.update(oaNotify);
	}
}