/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.filemanagement.entity.Annotate;

/**
 * 档案管理DAO接口
 * @author cqj
 * @version 2017-11-24
 */
@MyBatisDao
public interface AnnotateDao extends CrudDao<Annotate> {

	
}