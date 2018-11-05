/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sutoroa.entity.Attachment;

import java.util.Map;

/**
 * 8s检查表附件DAO接口
 * @author cqj
 * @version 2018-02-26
 */
@MyBatisDao
public interface AttachmentDao extends CrudDao<Attachment> {

    public int delAttachment(Map map);
}