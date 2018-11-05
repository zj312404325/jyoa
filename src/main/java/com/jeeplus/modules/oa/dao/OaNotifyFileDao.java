/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.oa.entity.OaNotifyFile;

import java.util.List;

/**
 * 通知通告记录DAO接口
 * @author jeeplus
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyFileDao extends CrudDao<OaNotifyFile> {

	/**
	 * 插入通知记录
	 * @param oaNotifyFileList
	 * @return
	 */
	public int insertAll(List<OaNotifyFile> oaNotifyFileList);

	/**
	 * 根据通知ID删除通知记录
	 * @param oaNotifyId 通知ID
	 * @return
	 */
	public int deleteByOaNotifyId(String oaNotifyId);

//
//	public int updateComment(OaNotifyRecord oaNotifyRecord);
//
//	public OaNotifyRecord findRecordByuid(Map m);
	
}