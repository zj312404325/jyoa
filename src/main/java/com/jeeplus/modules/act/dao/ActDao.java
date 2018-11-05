/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.act.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.act.entity.Act;

/**
 * 审批DAO接口
 * @author jeeplus
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
	public List<Map> findProcessInstList(Map map);
	
	public Integer findProcessInstCount(Map map);
	
	public Integer updateHiTask(Map map);
	
	public Integer updateRunTask(Map map);
	
	public Map findNextAssignee(Map map);
	
	public Integer updateActTask(Map map);
	
	public List<Map> findActByMap(Map map);
	
	public List<Map> findCandidate(Map map);
	
	public Integer saveIdentitylink(Map map);
	
	public Integer updateIdentitylink(Map map);
	
	public Integer updateIdentitylinkForAgent(Map map);
	
	public Integer deleteIdentitylinkForAgent(Map map);
	
	public Integer updateIdentitylinkForDel(Map map);
	
	public List<Map> findAgented(Map map);
	
	public Integer saveHiAct(Map map);
	
	public Integer updateHiAct(Map map);
	
	public List<Map> findHistoryFlow(Map map);
	
	public Integer updateActHitory(Map map);
	
	public Map findProcInstMap(Map map);
	
	public Integer updateActTaskForDel(Map map);
	
	public Integer updateHiTaskForDel(Map map);
	
	public List<Map> findActTaskMap(Map map);
	
	public List<Map> findActMap(Map map);
}
