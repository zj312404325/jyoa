/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.service;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.modules.checkmodel.entity.CheckData;
import com.jeeplus.modules.checkmodel.entity.CheckDataDetail;
import com.jeeplus.modules.checkmodel.dao.CheckDataDao;

/**
 * 绩效数据设定Service
 * @author cqj
 * @version 2017-10-23
 */
@Service
@Transactional(readOnly = true)
public class CheckDataService extends CrudService<CheckDataDao, CheckData> {
	@Autowired
	CheckDataDetailService checkDataDetailService;
	
	public CheckData get(String id) {
		return super.get(id);
	}
	
	public List<CheckData> findList(CheckData checkData) {
		return super.findList(checkData);
	}
	
	public Page<CheckData> findPage(Page<CheckData> page, CheckData checkData) {
		return super.findPage(page, checkData);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckData checkData) {
		if(FormatUtil.isNoEmpty(checkData.getId())){//修改数据
			//删除明细表
			CheckDataDetail tempCheckDataDetail=new CheckDataDetail();
			tempCheckDataDetail.setCheckdataid(checkData.getId());
			checkDataDetailService.deleteByCheckDataId(tempCheckDataDetail);
		}
		super.save(checkData);
		//插入明细
		if(FormatUtil.isNoEmpty(checkData.getDetailJson())){
			checkData.setDetailJson(checkData.getDetailJson().replace("&quot;", "\""));
			List<CheckDataDetail> checkDataDetailList=(List<CheckDataDetail>) JSONArray.toCollection(JSONArray.fromObject(checkData.getDetailJson()),CheckDataDetail.class);
			for (int i = 0; i < checkDataDetailList.size(); i++) {
				checkDataDetailList.get(i).setSort(i);
				checkDataDetailList.get(i).setCheckdataid(checkData.getId());
				checkDataDetailService.save(checkDataDetailList.get(i));
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckData checkData) {
		if(FormatUtil.isNoEmpty(checkData.getId())){//修改数据
			//删除明细表
			CheckDataDetail tempCheckDataDetail=new CheckDataDetail();
			tempCheckDataDetail.setCheckdataid(checkData.getId());
			checkDataDetailService.deleteByCheckDataId(tempCheckDataDetail);
		}
		super.delete(checkData);
	}
	
	
	
	
}