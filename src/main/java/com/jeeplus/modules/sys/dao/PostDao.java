/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.Post;

/**
 * 岗位DAO接口
 * @author yc
 * @version 2018-03-05
 */
@MyBatisDao
public interface PostDao extends CrudDao<Post> {

    public int deleteOfficePost(Office office);

    public int insertOfficePost(Office office);
}