package com.jeeplus.modules.reports.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheckDetail;
import com.jeeplus.modules.oaqa.entity.Oaquestion;
import com.jeeplus.modules.reports.entity.WeeklyReport;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 周报Entity
 * @author zj
 * @version 2018-10-29
 */
public class WeeklyReport extends DataEntity<com.jeeplus.modules.reports.entity.WeeklyReport> {

    private static final long serialVersionUID = 1L;
    /**部门id*/
    private String officeid;
    /**部门名称*/
    private String officename;
    /**岗位id*/
    private String stationid;
    /**岗位名称*/
    private String stationname;
    /**用户id*/
    private String userid;
    /**用户编号*/
    private String userno;
    /**用户姓名*/
    private String username;
    /**上周工作明细列表*/
    private List<WeeklyReportDetail> weeklyReportDetailList = Lists.newArrayList();
    /**本周工作计划列表*/
    private List<WeeklyReportDetail> weeklyReportDetailListKey = Lists.newArrayList();
    /**考核岗位*/
    private String stationIds;
    private String sqlstr;
    private String title;
    /**锁定标志 0：未锁定  1：锁定*/
    private Integer lockflag;
    /**开始时间（查询用）*/
    private Date startdate;
    /**结束时间*/
    private Date enddate;

    public WeeklyReport() {
        super();
    }

    public WeeklyReport(String id){
        super(id);
    }

    public String getOfficeid() {
        return officeid;
    }

    public void setOfficeid(String officeid) {
        this.officeid = officeid;
    }

    @ExcelField(title="部门名称", align=2, sort=4)
    public String getOfficename() {
        return officename;
    }

    public void setOfficename(String officename) {
        this.officename = officename;
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }

    @ExcelField(title="岗位名称", align=2, sort=5)
    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @ExcelField(title="用户编号", align=2, sort=3)
    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    @ExcelField(title="姓名", align=2, sort=2)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStationIds() {
        return stationIds;
    }

    public void setStationIds(String stationIds) {
        this.stationIds = stationIds;
    }

    public List<WeeklyReportDetail> getWeeklyReportDetailList() {
        return weeklyReportDetailList;
    }

    public void setWeeklyReportDetailList(List<WeeklyReportDetail> weeklyReportDetailList) {
        this.weeklyReportDetailList = weeklyReportDetailList;
    }

    public String getSqlstr() {
        return sqlstr;
    }

    public void setSqlstr(String sqlstr) {
        this.sqlstr = sqlstr;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @ExcelField(title="周报标题", align=2, sort=1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WeeklyReportDetail> getWeeklyReportDetailListKey() {
        return weeklyReportDetailListKey;
    }

    public void setWeeklyReportDetailListKey(List<WeeklyReportDetail> weeklyReportDetailListKey) {
        this.weeklyReportDetailListKey = weeklyReportDetailListKey;
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title="提交时间", align=2, sort=6)
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getLockflag() {
        return lockflag;
    }

    public void setLockflag(Integer lockflag) {
        this.lockflag = lockflag;
    }
}
