/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.filemanagement.entity.ProjectManage;
import com.jeeplus.modules.filemanagement.dao.ProjectManageDao;
import com.jeeplus.modules.filemanagement.entity.ProjectAttachment;
import com.jeeplus.modules.filemanagement.dao.ProjectAttachmentDao;
import com.jeeplus.modules.filemanagement.entity.ProjectMemo;
import com.jeeplus.modules.filemanagement.dao.ProjectMemoDao;
import com.jeeplus.modules.filemanagement.entity.ProjectTime;
import com.jeeplus.modules.filemanagement.dao.ProjectTimeDao;

/**
 * 项目管理Service
 * @author cqj
 * @version 2017-12-05
 */
@Service
@Transactional(readOnly = true)
public class ProjectManageService extends CrudService<ProjectManageDao, ProjectManage> {

	@Autowired
	private ProjectAttachmentDao projectAttachmentDao;
	@Autowired
	private ProjectMemoDao projectMemoDao;
	@Autowired
	private ProjectTimeDao projectTimeDao;
	
	public ProjectManage get(String id) {
		ProjectManage projectManage = super.get(id);
		projectManage.setProjectAttachmentList(projectAttachmentDao.findList(new ProjectAttachment(projectManage)));
		projectManage.setProjectMemoList(projectMemoDao.findList(new ProjectMemo(projectManage)));
		projectManage.setProjectTimeList(projectTimeDao.findList(new ProjectTime(projectManage)));
		return projectManage;
	}
	
	public List<ProjectManage> findList(ProjectManage projectManage) {
		return super.findList(projectManage);
	}
	
	public Page<ProjectManage> findPage(Page<ProjectManage> page, ProjectManage projectManage) {
		return super.findPage(page, projectManage);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectManage projectManage) {
		super.save(projectManage);
		for (ProjectAttachment projectAttachment : projectManage.getProjectAttachmentList()){
			if (projectAttachment.getId() == null){
				continue;
			}
			if (ProjectAttachment.DEL_FLAG_NORMAL.equals(projectAttachment.getDelFlag())){
				if (StringUtils.isBlank(projectAttachment.getId())){
					projectAttachment.setProject(projectManage);
					projectAttachment.preInsert();
					projectAttachmentDao.insert(projectAttachment);
				}else{
					projectAttachment.preUpdate();
					projectAttachmentDao.update(projectAttachment);
				}
			}else{
				projectAttachmentDao.delete(projectAttachment);
			}
		}
		for (ProjectMemo projectMemo : projectManage.getProjectMemoList()){
			if (projectMemo.getId() == null){
				continue;
			}
			if (ProjectMemo.DEL_FLAG_NORMAL.equals(projectMemo.getDelFlag())){
				if (StringUtils.isBlank(projectMemo.getId())){
					projectMemo.setProject(projectManage);
					projectMemo.preInsert();
					projectMemoDao.insert(projectMemo);
				}else{
					projectMemo.preUpdate();
					projectMemoDao.update(projectMemo);
				}
			}else{
				projectMemoDao.delete(projectMemo);
			}
		}
		for (ProjectTime projectTime : projectManage.getProjectTimeList()){
			if (projectTime.getId() == null){
				continue;
			}
			if (ProjectTime.DEL_FLAG_NORMAL.equals(projectTime.getDelFlag())){
				if (StringUtils.isBlank(projectTime.getId())){
					projectTime.setProject(projectManage);
					projectTime.preInsert();
					projectTimeDao.insert(projectTime);
				}else{
					projectTime.preUpdate();
					projectTimeDao.update(projectTime);
				}
			}else{
				projectTimeDao.delete(projectTime);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectManage projectManage) {
		super.delete(projectManage);
		projectAttachmentDao.delete(new ProjectAttachment(projectManage));
		projectMemoDao.delete(new ProjectMemo(projectManage));
		projectTimeDao.delete(new ProjectTime(projectManage));
	}
	
}