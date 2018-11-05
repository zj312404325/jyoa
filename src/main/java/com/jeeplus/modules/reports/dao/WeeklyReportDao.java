package com.jeeplus.modules.reports.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.reports.entity.WeeklyReport;
import com.jeeplus.modules.reports.entity.WeeklyReportDetail;

import java.util.List;

/**
 * 周报DAO接口
 * @author zj
 * @version 2018-10-29
 */
@MyBatisDao
public interface WeeklyReportDao extends CrudDao<WeeklyReport> {

}

