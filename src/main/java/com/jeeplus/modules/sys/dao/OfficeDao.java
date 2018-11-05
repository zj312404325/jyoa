/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.TreeDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.Role;

/**
 * 机构DAO接口
 * @author jeeplus
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	public Office getByCode(String code);
	
	public Office getByName(String name);
	
	/**
	 * 维护部门与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteOfficeMenu(Office office);

	public int insertOfficeMenu(Office office);
	
	/**
	 * 菜单字符串
	 * @param id
	 * @return
	 */
	public List<String> getMenuIds(String id);
	
	public List<Office> findParentOffice(Map officeMap);

	public List<Office> findListByType(Office office);

	public List<Office> findListByParentId(Office office);

	public List<Office> findPerformCheckList(Office office);
}
