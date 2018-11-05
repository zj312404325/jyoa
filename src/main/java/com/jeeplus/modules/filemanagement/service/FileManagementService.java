/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.service;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.filemanagement.dao.AnnotateDao;
import com.jeeplus.modules.filemanagement.dao.EditRecordDao;
import com.jeeplus.modules.filemanagement.dao.FileManagementDao;
import com.jeeplus.modules.filemanagement.entity.Annotate;
import com.jeeplus.modules.filemanagement.entity.EditRecord;
import com.jeeplus.modules.filemanagement.entity.FileManagement;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 档案管理Service
 * @author cqj
 * @version 2017-11-24
 */
@Service
@Transactional(readOnly = true)
public class FileManagementService extends CrudService<FileManagementDao, FileManagement> {

	@Autowired
	private AnnotateDao annotateDao;
	@Autowired
	private EditRecordDao editRecordDao;
	@Autowired
	private FileManagementDao fileManagementDao;
	
	public FileManagement get(String id) {
		FileManagement fileManagement = super.get(id);
		fileManagement.setAnnotateList(annotateDao.findList(new Annotate(fileManagement)));
		fileManagement.setEditRecordList(editRecordDao.findList(new EditRecord(fileManagement)));
		return fileManagement;
	}
	
	public List<FileManagement> findList(FileManagement fileManagement) {
		return super.findList(fileManagement);
	}
	
	public Page<FileManagement> findPage(Page<FileManagement> page, FileManagement fileManagement) {
		return super.findPage(page, fileManagement);
	}
	
	/**
	 * 
	 * @param fileManagement
	 * @param 原来预览文件地址
	 */
	@Transactional(readOnly = false)
	public void save(FileManagement fileManagement) {
		User loginUser = UserUtils.getUser();
		fileManagement.setCreateusername(loginUser.getName());
		super.save(fileManagement);

		FileManagement fm = new FileManagement();
		fm.setFilefrom(loginUser.getId());
		fm.setFileid(fileManagement.getId());
		fileManagementDao.deleteFrom(fm);
		if(FormatUtil.isNoEmpty(fileManagement.getReceiveuserids())){
			String fileid = fileManagement.getId();
			for (String id : fileManagement.getReceiveuserids().split(",")){
				fileManagement.setCreateBy(new User(id));
				fileManagement.setId(IdGen.uuid());
				fileManagement.setIsNewRecord(true);
				fileManagement.setFilefrom(loginUser.getId());
				fileManagement.setFileid(fileid);
				fileManagement.setReceiveuserids(null);
				fileManagement.setReceiveusernames(null);
				super.saveOnly(fileManagement);
			}
		}

		for (Annotate annotate : fileManagement.getAnnotateList()){
			if (annotate.getId() == null){
				continue;
			}
			if (Annotate.DEL_FLAG_NORMAL.equals(annotate.getDelFlag())){
				if (StringUtils.isBlank(annotate.getId())){
					annotate.setFilemanagement(fileManagement);
					annotate.preInsert();
					annotateDao.insert(annotate);
				}else{
					annotate.preUpdate();
					annotateDao.update(annotate);
				}
			}else{
				annotateDao.delete(annotate);
			}
		}
		for (EditRecord editRecord : fileManagement.getEditRecordList()){
			if (editRecord.getId() == null){
				continue;
			}
			if (EditRecord.DEL_FLAG_NORMAL.equals(editRecord.getDelFlag())){
				if (StringUtils.isBlank(editRecord.getId())){
					editRecord.setFilemanagement(fileManagement);
					editRecord.preInsert();
					editRecordDao.insert(editRecord);
				}else{
					editRecord.preUpdate();
					editRecordDao.update(editRecord);
				}
			}else{
				editRecordDao.delete(editRecord);
			}
		}
		if(!FormatUtil.isNoEmpty(fileManagement.getIgnoreEditRecord())||!fileManagement.getIgnoreEditRecord().equals("1")){
			//添加文件版本
			AddEditRecord(fileManagement);
		}
	}

    @Transactional(readOnly = false)
    public void saveAuthor(FileManagement fileManagement) {
        super.save(fileManagement);
        User loginUser = UserUtils.getUser();

        FileManagement fm = new FileManagement();
        if(FormatUtil.isNoEmpty(fileManagement.getFilefrom())){
            fm.setFilefrom(loginUser.getId());
        }
        fm.setFileid(fileManagement.getId());
        fileManagementDao.deleteFrom(fm);
        if(FormatUtil.isNoEmpty(fileManagement.getReceiveuserids())){
            String fileid = fileManagement.getId();
            for (String id : fileManagement.getReceiveuserids().split(",")){
                fileManagement.setCreateBy(new User(id));
                fileManagement.setId(IdGen.uuid());
                fileManagement.setIsNewRecord(true);
                fileManagement.setFilefrom(loginUser.getId());
                fileManagement.setFileid(fileid);
                fileManagement.setReceiveuserids(null);
                fileManagement.setReceiveusernames(null);
                super.saveOnly(fileManagement);
            }
        }
    }

	//添加文件版本
	@Transactional(readOnly = false)
	private void AddEditRecord(FileManagement fileManagement) {
		EditRecord editRecord=new EditRecord();
		editRecord.setId(FormatUtil.getUUID());
		editRecord.setCreateBy(UserUtils.getUser());
		editRecord.setCreateDate(new Date());
		editRecord.setCreateusername(UserUtils.getUser().getName());
		editRecord.setFilemanagement(fileManagement);
		editRecordDao.insert(editRecord);
	}

	@Transactional(readOnly = false)
	public void delete(FileManagement fileManagement) {
		super.delete(fileManagement);
		annotateDao.delete(new Annotate(fileManagement));
		editRecordDao.delete(new EditRecord(fileManagement));
	}


}