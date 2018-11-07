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
public interface WeeklyReportDetailDao extends CrudDao<WeeklyReportDetail> {

    /**
     * 查找上周工作明细列表
     * @param weeklyReportDetail
     * @return List<WeeklyReportDetail>
     */
    public List<WeeklyReportDetail> findLastList(WeeklyReportDetail weeklyReportDetail);

    /**
     * 查找本周计划明细列表
     * @param weeklyReportDetail
     * @return List<WeeklyReportDetail>
     */
    public List<WeeklyReportDetail> findThisList(WeeklyReportDetail weeklyReportDetail);

    /**
     * 查找统计工时列表
     * @param weeklyReportDetail
     * @return List<WeeklyReportDetail>
     */
    public List<WeeklyReportDetail> findSumList(WeeklyReportDetail weeklyReportDetail);
}
