/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.PostDao;
import com.jeeplus.modules.sys.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 岗位Service
 * @author yc
 * @version 2018-03-05
 */
@Service
@Transactional(readOnly = true)
public class PostService extends CrudService<PostDao, Post> {

	public Post get(String id) {
		return super.get(id);
	}
	
	public List<Post> findList(Post post) {
		return super.findList(post);
	}
	
	public Page<Post> findPage(Page<Post> page, Post post) {
		return super.findPage(page, post);
	}
	
	@Transactional(readOnly = false)
	public void save(Post post) {
		super.save(post);
	}
	
	@Transactional(readOnly = false)
	public void delete(Post post) {
		super.delete(post);
	}
	
	
	
	
}