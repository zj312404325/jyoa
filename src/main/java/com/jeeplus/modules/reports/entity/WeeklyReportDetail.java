package com.jeeplus.modules.reports.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheck;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 周报明细Entity
 * @author zj
 * @version 2018-10-29
 */
public class WeeklyReportDetail extends DataEntity<com.jeeplus.modules.reports.entity.WeeklyReportDetail> {

    private static final long serialVersionUID = 1L;
    /**本周，上周标识 0：本周   1：上周*/
    private Integer weekflag;
    /**任务描述*/
    private String taskdesc;
    /**内容*/
    private String content;
    /**主表id 父类*/
    private WeeklyReport weeklyreport;
    /**任务状态*/
    private String taskstatus;
    /**花费天数*/
    private Double costdays;
    /**排序*/
    private Integer sort;
    /**项目*/
    private String project;
    /**任务类型*/
    private String tasktype;

    /**导出excel用*/
    private String excelusername;
    private String exceluserno;
    private String excelofficename;
    private String excelstationname;
    private String exceltitle;
    private Date exceldate;
    private String days;

    public WeeklyReportDetail() {
        super();
    }

    public WeeklyReportDetail(String id){
        super(id);
    }

    public WeeklyReportDetail(WeeklyReport weeklyreport){
        this.weeklyreport = weeklyreport;
    }

    public Integer getWeekflag() {
        return weekflag;
    }

    public void setWeekflag(Integer weekflag) {
        this.weekflag = weekflag;
    }

    @ExcelField(title="任务描述", align=2, sort=9)
    public String getTaskdesc() {
        return taskdesc;
    }

    public void setTaskdesc(String taskdesc) {
        this.taskdesc = taskdesc;
    }

    @ExcelField(title="内容", align=2, sort=10)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ExcelField(title="任务状态", align=2, sort=11)
    public String getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(String taskstatus) {
        this.taskstatus = taskstatus;
    }

    public Double getCostdays() {
        return costdays;
    }

    public void setCostdays(Double costdays) {
        this.costdays = costdays;
    }

    public WeeklyReport getWeeklyreport() {
        return weeklyreport;
    }

    public void setWeeklyreport(WeeklyReport weeklyreport) {
        this.weeklyreport = weeklyreport;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @ExcelField(title="姓名", align=2, sort=2)
    public String getExcelusername() {
        return excelusername;
    }

    public void setExcelusername(String excelusername) {
        this.excelusername = excelusername;
    }

    @ExcelField(title="用户编号", align=2, sort=3)
    public String getExceluserno() {
        return exceluserno;
    }

    public void setExceluserno(String exceluserno) {
        this.exceluserno = exceluserno;
    }

    @ExcelField(title="部门名称", align=2, sort=4)
    public String getExcelofficename() {
        return excelofficename;
    }

    public void setExcelofficename(String excelofficename) {
        this.excelofficename = excelofficename;
    }

    @ExcelField(title="岗位名称", align=2, sort=5)
    public String getExcelstationname() {
        return excelstationname;
    }

    public void setExcelstationname(String excelstationname) {
        this.excelstationname = excelstationname;
    }

    @ExcelField(title="周报标题", align=2, sort=1)
    public String getExceltitle() {
        return exceltitle;
    }

    public void setExceltitle(String exceltitle) {
        this.exceltitle = exceltitle;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title="提交时间", align=2, sort=6)
    public Date getExceldate() {
        return exceldate;
    }

    public void setExceldate(Date exceldate) {
        this.exceldate = exceldate;
    }

    @ExcelField(title="花费时间（天）", align=2, sort=12)
    public String getDays() {
        return this.costdays.toString();
    }

    public void setDays(String days) {
        this.days = days;
    }

    @ExcelField(title="项目", align=2, sort=7)
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @ExcelField(title="任务类型", align=2, sort=8)
    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }
}