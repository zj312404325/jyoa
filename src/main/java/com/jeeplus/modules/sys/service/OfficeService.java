/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.service.TreeService;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.modules.sys.dao.OfficeDao;
import com.jeeplus.modules.sys.dao.PostDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.Post;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构Service
 * @author jeeplus
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	@Autowired
	private PostDao postDao;
	@Autowired
	private OfficeDao officeDao;

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}

	public List<Office> findPerformanceCheckList(Office office){
		User user = UserUtils.getUser();
		List<Office> officeList=null;
		officeList = officeDao.findPerformCheckList(office);
		return officeList;
	}

	public List<Office> findList(Boolean isAll, User user){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList(user);
		}
	}

	public Office getOffice(String id) {
		Office o = dao.get(id);
		o.setPostList(postDao.findList(new Post(o)));
		return dao.get(id);
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
//		office.setParentIds(office.getParentIds()+"%");
		return dao.findByParentIdsLike(office);
	}

	@Transactional(readOnly = true)
	public List<Office> findByParentIdsLike(Office office){
//		office.setParentIds(office.getParentIds()+"%");
		return dao.findByParentIdsLike(office);
	}
	
	@Transactional(readOnly = true)
	public Office getByCode(String code){
		return dao.getByCode(code);
	}
	
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	@Transactional(readOnly = false)
	public void saveOfficePost(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);

//		// 更新用户与角色关联
//		postDao.deleteOfficePost(office);
//		if (office.getPostList() != null && office.getPostList().size() > 0){
//			postDao.insertOfficePost(office);
//		}
	}

	public void updateOfficePosts(Office office) {
		// 更新用户与角色关联
		postDao.deleteOfficePost(office);
		if (office.getPostList() != null && office.getPostList().size() > 0){
			postDao.insertOfficePost(office);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		postDao.deleteOfficePost(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	public List<Office> findParentOffice(Map officeMap) {
		return dao.findParentOffice(officeMap);
	}

	public List<Office> findListByType(Office office) {
		return dao.findListByType(office);
	}

	public List<Office> findListByParentId(Office office) {
		return dao.findListByParentId(office);
	}
	
	/**
	 * 获取此岗位的上级部门
	 * @param officeId 岗位id
	 * @return
	 */
	public Office getParentOffice(String officeId) {
		if(FormatUtil.isNoEmpty(officeId)){
			Office officeTemp=this.get(officeId);
			String parentIds=officeTemp.getParentIds();
			if(FormatUtil.isNoEmpty(parentIds)){
				String ids=getIds(parentIds);
				Map officeMap=new HashMap<String, Object>();
				officeMap.put("useable", "1");
				officeMap.put("type", "3");
				officeMap.put("ids", ids);
				List<Office> officelst=this.findParentOffice(officeMap);
				if(FormatUtil.isNoEmpty(officelst)&&officelst.size()>0){
					return officelst.get(0);
				}
			}
		}
		return null;
	}
	
	private String getIds(String ids) {
		if(FormatUtil.isNoEmpty(ids)){
			ids="'"+ids.replace(",", "','")+"'";
			return ids;
		}
		return null;
	}
}
