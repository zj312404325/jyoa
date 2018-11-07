package com.jeeplus.modules.reports.service;

import java.util.List;

import com.jeeplus.modules.checkmodel.dao.PerformanceCheckDetailDao;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheck;
import com.jeeplus.modules.reports.dao.WeeklyReportDao;
import com.jeeplus.modules.reports.entity.WeeklyReport;
import com.jeeplus.modules.sys.entity.Post;
import com.jeeplus.modules.sys.service.PostService;
import com.jeeplus.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.reports.entity.WeeklyReport;
import com.jeeplus.modules.reports.entity.WeeklyReportDetail;
import com.jeeplus.modules.reports.dao.WeeklyReportDao;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheckDetail;
import com.jeeplus.modules.reports.dao.WeeklyReportDetailDao;
import com.jeeplus.modules.sys.dao.OfficeDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 周报Service
 * @author zj
 * @version 2018-10-29
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class WeeklyReportService extends CrudService<WeeklyReportDao, WeeklyReport> {

    @Autowired
    private WeeklyReportDetailDao weeklyReportDetailDao;

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private PostService postService;

    @Override
    public WeeklyReport get(String id) {
        WeeklyReport weeklyReport = super.get(id);
        weeklyReport.getPage().setOrderBy("a.sort asc");
        weeklyReport.setWeeklyReportDetailList(weeklyReportDetailDao.findLastList(new WeeklyReportDetail(weeklyReport)));
        weeklyReport.setWeeklyReportDetailListKey(weeklyReportDetailDao.findThisList(new WeeklyReportDetail(weeklyReport)));
        return weeklyReport;
    }

    @Override
    public List<WeeklyReport> findList(WeeklyReport weeklyReport) {
        return super.findList(weeklyReport);
    }

    @Override
    public Page<WeeklyReport> findPage(Page<WeeklyReport> page, WeeklyReport weeklyReport) {
        return super.findPage(page, weeklyReport);
    }

    public List<WeeklyReportDetail> findSumList(WeeklyReport weeklyReport) {
        return weeklyReportDetailDao.findSumList(new WeeklyReportDetail(weeklyReport));
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void save(WeeklyReport weeklyReport) {
        if(!FormatUtil.isNoEmpty(weeklyReport.getId())){
            User user= UserUtils.getUser();
            weeklyReport.setOfficeid(user.getOffice().getId());
            weeklyReport.setOfficename(user.getOffice().getName());
            weeklyReport.setStationid(user.getStationType());
            if(FormatUtil.isNoEmpty(user.getStationType())){
                Post post=postService.get(user.getStationType());
                weeklyReport.setStationname(post.getPostname());
            }
            weeklyReport.setUserid(user.getId());
            weeklyReport.setUserno(user.getNo());
            weeklyReport.setUsername(user.getName());
            weeklyReport.setLockflag(0);
        }
        super.save(weeklyReport);
        int i=0;
        for (WeeklyReportDetail weeklyReportDetail : weeklyReport.getWeeklyReportDetailList()){
            if (weeklyReportDetail.getId() == null){
                continue;
            }
            if (WeeklyReportDetail.DEL_FLAG_NORMAL.equals(weeklyReportDetail.getDelFlag())){
                if (StringUtils.isBlank(weeklyReportDetail.getId())){
                    weeklyReportDetail.setWeekflag(0);
                    weeklyReportDetail.setSort(i);
                    weeklyReportDetail.setWeeklyreport(weeklyReport);
                    weeklyReportDetail.preInsert();
                    weeklyReportDetailDao.insert(weeklyReportDetail);
                    i++;
                }else{
                    weeklyReportDetail.setWeekflag(0);
                    weeklyReportDetail.setSort(i);
                    weeklyReportDetail.setWeeklyreport(weeklyReport);
                    weeklyReportDetail.preUpdate();
                    weeklyReportDetailDao.update(weeklyReportDetail);
                    i++;
                }
            }else{
                weeklyReportDetailDao.delete(weeklyReportDetail);
            }
        }

        for (WeeklyReportDetail weeklyReportDetail : weeklyReport.getWeeklyReportDetailListKey()){
            if (weeklyReportDetail.getId() == null){
                continue;
            }
            if (WeeklyReportDetail.DEL_FLAG_NORMAL.equals(weeklyReportDetail.getDelFlag())){
                if (StringUtils.isBlank(weeklyReportDetail.getId())){
                    weeklyReportDetail.setWeekflag(1);
                    weeklyReportDetail.setSort(i);
                    weeklyReportDetail.setWeeklyreport(weeklyReport);
                    weeklyReportDetail.preInsert();
                    weeklyReportDetailDao.insert(weeklyReportDetail);
                    i++;
                }else{
                    weeklyReportDetail.setWeekflag(1);
                    weeklyReportDetail.setSort(i);
                    weeklyReportDetail.setWeeklyreport(weeklyReport);
                    weeklyReportDetail.preUpdate();
                    weeklyReportDetailDao.update(weeklyReportDetail);
                    i++;
                }
            }else{
                weeklyReportDetailDao.delete(weeklyReportDetail);
            }
        }
    }

    private boolean validIsStation(String id) {
        // TODO Auto-generated method stub
        Office office= officeDao.get(id);
        //岗位
        if("3".equals(office.getType())){
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void delete(WeeklyReport weeklyReport) {
        super.delete(weeklyReport);
        weeklyReportDetailDao.delete(new WeeklyReportDetail(weeklyReport));
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void updateLockState(WeeklyReport weeklyReport){
        super.save(weeklyReport);
    }
}