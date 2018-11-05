/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.filemanagement.entity.ProjectAttachment;

/**
 * 项目管理DAO接口
 * @author cqj
 * @version 2017-12-05
 */
@MyBatisDao
public interface ProjectAttachmentDao extends CrudDao<ProjectAttachment> {

	
}