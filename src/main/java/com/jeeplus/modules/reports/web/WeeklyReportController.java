package com.jeeplus.modules.reports.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.checkmodel.entity.CheckUser;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheck;
import com.jeeplus.modules.checkmodel.service.*;
import com.jeeplus.modules.ehr.entity.UserInfo;
import com.jeeplus.modules.ehr.service.UserInfoService;
import com.jeeplus.modules.reports.entity.WeeklyReport;
import com.jeeplus.modules.reports.entity.WeeklyReportDetail;
import com.jeeplus.modules.reports.service.WeeklyReportService;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.service.OfficeService;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;

import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 周报Controller
 * @author zj
 * @version 2018-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/checkmodel/reports")
public class WeeklyReportController extends BaseController{
    @Autowired
    private WeeklyReportService weeklyReportService;

    @Autowired
    private CheckUserService checkUserService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OfficeService officeService;

    @ModelAttribute
    public WeeklyReport get(@RequestParam(required=false) String id) {
        WeeklyReport entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = weeklyReportService.get(id);
        }
        if (entity == null){
            entity = new WeeklyReport();
        }
        return entity;
    }

    /**
     * 周报页面
     */
    @RequiresPermissions("checkmodel:reports:list")
    @RequestMapping(value = {"weeklyReportIndex"})
    public String weeklyReportIndex(WeeklyReport weeklyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("type", request.getParameter("type"));
        request.setAttribute("repage", request.getParameter("repage"));
        model.addAttribute("weeklyReport",weeklyReport);
        if(FormatUtil.isNoEmpty(request.getParameter("tipinfo"))){
            model.addAttribute("tipinfo",request.getParameter("tipinfo"));
        }
        return "modules/reports/weeklyReportIndex";
    }

    /**
     * 周报列表页面
     */
    @RequiresPermissions("checkmodel:reports:list")
    @RequestMapping(value = {"list", ""})
    public String list(WeeklyReport weeklyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user= UserUtils.getUser();
        model.addAttribute("weeklyReport",weeklyReport);
        if(FormatUtil.isNoEmpty(request.getParameter("cat"))&& ("receive".equals(request.getParameter("cat")))){
            Page<WeeklyReport> page = getListPage(user,request,response,weeklyReport);
            model.addAttribute("page", page);
            return "modules/reports/weeklyReportRecieveList";
        }
        weeklyReport.setCreateBy(user);
        Page<WeeklyReport> page = weeklyReportService.findPage(new Page<WeeklyReport>(request, response), weeklyReport);
        model.addAttribute("page", page);
        return "modules/reports/weeklyReportSendList";
    }

    private Page<WeeklyReport> getListPage(User user,HttpServletRequest request,HttpServletResponse response,WeeklyReport weeklyReport) {
        //查找考核人记录
        CheckUser tempCheckUser=new CheckUser();
        tempCheckUser.setUserId(user.getId());
        List<CheckUser> checkUserList=checkUserService.findList(tempCheckUser);
        if(FormatUtil.isNoEmpty(checkUserList)&&checkUserList.size()>0){
            StringBuilder sqlstr=new StringBuilder();
            for (int i = 0; i < checkUserList.size(); i++) {
                sqlstr.append(" ((a.officeid='"+checkUserList.get(i).getCheckofficeid()+"' or o.parent_ids like '%"+checkUserList.get(i).getCheckofficeid()+"%') and a.stationid='"+checkUserList.get(i).getStationId()+"') or");
            }
            String ids=sqlstr.toString();
            if(FormatUtil.isNoEmpty(ids)){
                ids=sqlstr.toString().substring(0,ids.length()-2);
            }
            weeklyReport.setSqlstr(ids);
            return weeklyReportService.findPage(new Page<WeeklyReport>(request, response), weeklyReport);
        }
        weeklyReport.setSqlstr("1!=1");
        return weeklyReportService.findPage(new Page<WeeklyReport>(request, response), weeklyReport);
    }

    /**
     * 查看，增加，编辑周报表单页面
     */
    @RequiresPermissions(value={"checkmodel:weeklyReport:view","checkmodel:weeklyReport:add","checkmodel:weeklyReport:edit"},logical=Logical.OR)
    @RequestMapping(value = "form")
    public String form(WeeklyReport weeklyReport, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
        request.setAttribute("bhv", request.getParameter("bhv"));
        request.setAttribute("type", request.getParameter("type"));
        //新增表单保存
        //获取本周第一天和最后一天
        if("add".equals(request.getParameter("opt"))){
            Date tm = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String dateString=format.format(tm);
            weeklyReport.setTitle(dateString);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            c.setTime(tm);

            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                c.add(Calendar.DATE, -1);
            }
            Calendar cf = Calendar.getInstance();
            cf.setTime(c.getTime());
            cf.set(Calendar.DAY_OF_WEEK, cf.getFirstDayOfWeek());
            cf.add(Calendar.DATE, -6);
            cf.set(Calendar.HOUR_OF_DAY, 0);
            cf.set(Calendar.MINUTE, 0);
            cf.set(Calendar.SECOND, 0);

            Calendar ce = Calendar.getInstance();
            ce.setTime(c.getTime());
            ce.set(Calendar.DAY_OF_WEEK, cf.getFirstDayOfWeek() + 6);
            ce.add(Calendar.DATE, -6);
            ce.set(Calendar.HOUR_OF_DAY, 23);
            ce.set(Calendar.MINUTE, 59);
            ce.set(Calendar.SECOND, 59);
            System.out.println(sdf.format(tm));
            System.out.println("第一天:" + sdf.format(cf.getTime()));
            System.out.println("第七天:" + sdf.format(ce.getTime()));
            Date beginDate = cf.getTime();
            Date endDate = ce.getTime();
            User user = UserUtils.getUser();
            WeeklyReport temp = new WeeklyReport();
            temp.setCreateBy(user);
            temp.setStartdate(beginDate);
            temp.setEnddate(endDate);
            List<WeeklyReport> list = weeklyReportService.findList(temp);
            WeeklyReport lastReport = new WeeklyReport();
            if (list.size() > 0) {
                lastReport = list.get(0);
                List<WeeklyReportDetail> detailList = weeklyReportService.get(lastReport.getId()).getWeeklyReportDetailListKey();
                for (WeeklyReportDetail detail : detailList) {
                    detail.setWeekflag(0);
                    detail.setTasktype("计划内");
                    detail.setTaskstatus("进行中");
                    detail.setId(null);
                }
                weeklyReport.setWeeklyReportDetailList(detailList);
            }
        }

        model.addAttribute("weeklyReport", weeklyReport);
        if(FormatUtil.isNoEmpty(request.getParameter("type"))&&("1".equals(request.getParameter("type")))){
            return "modules/reports/weeklyReportRecieveForm";
        }
        //后台管理
        else if(FormatUtil.isNoEmpty(request.getParameter("type"))&&("2".equals(request.getParameter("type")))){
            request.setAttribute("disableScore", "1");
            return "modules/reports/weeklyReportRecieveForm";
        }

        return "modules/reports/weeklyReportForm";
    }

    /**
     * 保存周报明细
     */
    @RequiresPermissions(value={"checkmodel:weeklyReport:add","checkmodel:weeklyReport:edit"},logical=Logical.OR)
    @RequestMapping(value = "save")
    public String save(WeeklyReport weeklyReport, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
        if (!beanValidator(model, weeklyReport)){
            return form(weeklyReport, model,request,redirectAttributes);
        }
        //编辑表单修改
        if(!weeklyReport.getIsNewRecord()){
            //从数据库取出记录的值
            WeeklyReport t = weeklyReportService.get(weeklyReport.getId());
            if(t.getLockflag().equals(1)){
                addMessage(redirectAttributes, "周报已被锁定，无法修改！");
                return "redirect:"+Global.getAdminPath()+"/checkmodel/reports/weeklyReportIndex?repage=repage&type="+request.getParameter("type");
            }
            //将编辑表单中的非NULL值覆盖数据库记录中的值
            MyBeanUtils.copyBeanNotNull2Bean(weeklyReport, t);
            //保存
            weeklyReportService.save(t);

            addMessage(redirectAttributes, "保存周报明细成功");
            return "redirect:"+Global.getAdminPath()+"/checkmodel/reports/weeklyReportIndex?repage=repage&type="+request.getParameter("type");
        }else{
            //新增表单保存
            //获取本周第一天和最后一天
            Date tm = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            c.setTime(tm);

            if(c.get(Calendar.DAY_OF_WEEK)==1){
                c.add(Calendar.DATE, -1);
            }
            Calendar cf = Calendar.getInstance();
            cf.setTime(c.getTime());
            cf.set(Calendar.DAY_OF_WEEK, cf.getFirstDayOfWeek());
            cf.add(Calendar.DATE, 1);
            cf.set(Calendar.HOUR_OF_DAY, 0);
            cf.set(Calendar.MINUTE, 0);
            cf.set(Calendar.SECOND, 0);

            Calendar ce = Calendar.getInstance();
            ce.setTime(c.getTime());
            ce.set(Calendar.DAY_OF_WEEK, cf.getFirstDayOfWeek()+6);
            ce.add(Calendar.DATE, 1);
            ce.set(Calendar.HOUR_OF_DAY, 23);
            ce.set(Calendar.MINUTE, 59);
            ce.set(Calendar.SECOND, 59);
            System.out.println(sdf.format(tm));
            System.out.println("第一天:"+sdf.format(cf.getTime()));
            System.out.println("第七天:"+sdf.format(ce.getTime()));
            Date beginDate = cf.getTime();
            Date endDate = ce.getTime();

            User user= UserUtils.getUser();
            WeeklyReport temp=new WeeklyReport();
            temp.setCreateBy(user);
            temp.setStartdate(beginDate);
            temp.setEnddate(endDate);
            List<WeeklyReport> list=weeklyReportService.findList(temp);
            if(list.size()>0){
                addMessage(redirectAttributes, "本周您已填写过周报，无需重复填写！");
                return "redirect:"+Global.getAdminPath()+"/checkmodel/reports/weeklyReportIndex?repage=repage&type="+request.getParameter("type");
            }
            //保存
            weeklyReportService.save(weeklyReport);
            addMessage(redirectAttributes, "保存周报明细成功");
            //跳转到周报列表
            return "redirect:"+Global.getAdminPath()+"/checkmodel/reports/weeklyReportIndex?repage=repage&type="+request.getParameter("type");
        }
    }

    /**
     * 删除周报明细
     */
    @RequiresPermissions("checkmodel:weeklyReport:del")
    @RequestMapping(value = "delete")
    public String delete(WeeklyReport weeklyReport, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        if(weeklyReport.getLockflag().equals(1)) {
            addMessage(redirectAttributes, "周报已锁定，无法删除");
        }
        else{
            weeklyReportService.delete(weeklyReport);
            addMessage(redirectAttributes, "删除周报明细成功");
        }
        return "redirect:"+Global.getAdminPath()+"/checkmodel/reports/weeklyReportIndex?repage=repage&type="+request.getParameter("type");
    }

    /**
     * 批量删除周报明细
     */
    @RequiresPermissions("checkmodel:weeklyReport:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray =ids.split(",");
        for(String id : idArray){
            weeklyReportService.delete(weeklyReportService.get(id));
        }
        addMessage(redirectAttributes, "删除周报成功");
        return "redirect:"+Global.getAdminPath()+"/checkmodel/reports/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("checkmodel:weeklyReport:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(WeeklyReport weeklyReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName ;
            String type=request.getParameter("type");
            if("0".equalsIgnoreCase(type)){
                fileName = "上周工作周报明细"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            }
            else{
                fileName = "本周计划任务周报明细"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            }

            //查找考核人记录
            User user =UserUtils.getUser();
            CheckUser tempCheckUser=new CheckUser();
            tempCheckUser.setUserId(user.getId());
            List<CheckUser> checkUserList=checkUserService.findList(tempCheckUser);
            if(FormatUtil.isNoEmpty(checkUserList)&&checkUserList.size()>0){
                StringBuilder sqlstr=new StringBuilder();
                for (int i = 0; i < checkUserList.size(); i++) {
                    sqlstr.append(" ((a.officeid='"+checkUserList.get(i).getCheckofficeid()+"' or o.parent_ids like '%"+checkUserList.get(i).getCheckofficeid()+"%') and a.stationid='"+checkUserList.get(i).getStationId()+"') or");
                }
                String ids=sqlstr.toString();
                if(FormatUtil.isNoEmpty(ids)){
                    ids=sqlstr.toString().substring(0,ids.length()-2);
                }
                weeklyReport.setSqlstr(ids);
            }

            Page<WeeklyReport> page = weeklyReportService.findPage(new Page<WeeklyReport>(request, response, -1), weeklyReport);
            List<WeeklyReportDetail> list=Lists.newArrayList();
            for(WeeklyReport w:page.getList()){
                w=weeklyReportService.get(w.getId());
                List<WeeklyReportDetail> tmplist;
                if("0".equalsIgnoreCase(type)){
                    tmplist=w.getWeeklyReportDetailList();
                }
                else{
                    tmplist=w.getWeeklyReportDetailListKey();
                }
                for(WeeklyReportDetail d:tmplist){
                    d.setExceldate(w.getCreateDate());
                    d.setExcelofficename(w.getOfficename());
                    d.setExcelstationname(w.getStationname());
                    d.setExceltitle(w.getTitle());
                    d.setExcelusername(w.getUsername());
                    d.setExceluserno(w.getUserno());
                    list.add(d);
                }
            }
            new ExportExcel("周报明细", WeeklyReportDetail.class).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出周报明细记录失败！失败信息："+e.getMessage());
        }
        return "redirect:"+Global.getAdminPath()+"/checkmodel/reports/?repage";
    }

    /**
     * 锁定周报
     */
    @RequiresPermissions("checkmodel:weeklyReport:lock")
    @RequestMapping(value = "lock")
    public String lockReports(WeeklyReport weeklyReport, RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response) {
        //查找考核人记录
        User user =UserUtils.getUser();
        CheckUser tempCheckUser=new CheckUser();
        tempCheckUser.setUserId(user.getId());
        List<CheckUser> checkUserList=checkUserService.findList(tempCheckUser);
        if(FormatUtil.isNoEmpty(checkUserList)&&checkUserList.size()>0){
            StringBuilder sqlstr=new StringBuilder();
            for (int i = 0; i < checkUserList.size(); i++) {
                sqlstr.append(" ((a.officeid='"+checkUserList.get(i).getCheckofficeid()+"' or o.parent_ids like '%"+checkUserList.get(i).getCheckofficeid()+"%') and a.stationid='"+checkUserList.get(i).getStationId()+"') or");
            }
            String ids=sqlstr.toString();
            if(FormatUtil.isNoEmpty(ids)){
                ids=sqlstr.toString().substring(0,ids.length()-2);
            }
            weeklyReport.setSqlstr(ids);
        }

        Page<WeeklyReport> page = weeklyReportService.findPage(new Page<WeeklyReport>(request, response), weeklyReport);
        for(WeeklyReport w:page.getList()){
            w.setLockflag(1);
            weeklyReportService.updateLockState(w);
        }

        addMessage(redirectAttributes, "锁定周报成功");
        return "redirect:"+Global.getAdminPath()+"/checkmodel/reports/weeklyReportIndex?repage=repage&type="+request.getParameter("type");
    }

    /**
     * 解锁周报
     */
    @RequiresPermissions("checkmodel:weeklyReport:lock")
    @RequestMapping(value = "unlock")
    public String unlockReports(WeeklyReport weeklyReport, RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response) {
        //查找考核人记录
        User user =UserUtils.getUser();
        CheckUser tempCheckUser=new CheckUser();
        tempCheckUser.setUserId(user.getId());
        List<CheckUser> checkUserList=checkUserService.findList(tempCheckUser);
        if(FormatUtil.isNoEmpty(checkUserList)&&checkUserList.size()>0){
            StringBuilder sqlstr=new StringBuilder();
            for (int i = 0; i < checkUserList.size(); i++) {
                sqlstr.append(" ((a.officeid='"+checkUserList.get(i).getCheckofficeid()+"' or o.parent_ids like '%"+checkUserList.get(i).getCheckofficeid()+"%') and a.stationid='"+checkUserList.get(i).getStationId()+"') or");
            }
            String ids=sqlstr.toString();
            if(FormatUtil.isNoEmpty(ids)){
                ids=sqlstr.toString().substring(0,ids.length()-2);
            }
            weeklyReport.setSqlstr(ids);
        }

        Page<WeeklyReport> page = weeklyReportService.findPage(new Page<WeeklyReport>(request, response), weeklyReport);
        for(WeeklyReport w:page.getList()){
            w.setLockflag(0);
            weeklyReportService.updateLockState(w);
        }

        addMessage(redirectAttributes, "解锁周报成功");
        return "redirect:"+Global.getAdminPath()+"/checkmodel/reports/weeklyReportIndex?repage=repage&type="+request.getParameter("type");
    }

    /**
     * 预览周报
     */
    @RequiresPermissions("checkmodel:weeklyReport:viewExcel")
    @RequestMapping(value = "viewExcel")
    public String viewFile(WeeklyReport weeklyReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        String type=request.getParameter("type");
        //查找考核人记录
        User user =UserUtils.getUser();
        CheckUser tempCheckUser=new CheckUser();
        tempCheckUser.setUserId(user.getId());
        List<CheckUser> checkUserList=checkUserService.findList(tempCheckUser);
        if(FormatUtil.isNoEmpty(checkUserList)&&checkUserList.size()>0){
            StringBuilder sqlstr=new StringBuilder();
            for (int i = 0; i < checkUserList.size(); i++) {
                sqlstr.append(" ((a.officeid='"+checkUserList.get(i).getCheckofficeid()+"' or o.parent_ids like '%"+checkUserList.get(i).getCheckofficeid()+"%') and a.stationid='"+checkUserList.get(i).getStationId()+"') or");
            }
            String ids=sqlstr.toString();
            if(FormatUtil.isNoEmpty(ids)){
                ids=sqlstr.toString().substring(0,ids.length()-2);
            }
            weeklyReport.setSqlstr(ids);
        }

        Page<WeeklyReport> page = weeklyReportService.findPage(new Page<WeeklyReport>(request, response, -1), weeklyReport);
        List<WeeklyReportDetail> list=Lists.newArrayList();
        for(WeeklyReport w:page.getList()){
            w=weeklyReportService.get(w.getId());
            List<WeeklyReportDetail> tmplist;
            if("0".equalsIgnoreCase(type)){
                tmplist=w.getWeeklyReportDetailList();
            }
            else{
                tmplist=w.getWeeklyReportDetailListKey();
            }
            for(WeeklyReportDetail d:tmplist){
                d.setExceldate(w.getCreateDate());
                d.setExcelofficename(w.getOfficename());
                d.setExcelstationname(w.getStationname());
                d.setExceltitle(w.getTitle());
                d.setExcelusername(w.getUsername());
                d.setExceluserno(w.getUserno());
                list.add(d);
            }
        }

        //拼音排序
        Collections.sort(list, new Comparator<WeeklyReportDetail>() {
            @Override
            public int compare(WeeklyReportDetail o1, WeeklyReportDetail o2) {
                Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
                return com.compare(o1.getExcelusername(), o2.getExcelusername());
            }
        });

        model.addAttribute("weeklyReportDetail", list);
        model.addAttribute("weeklyReport", weeklyReport);
        return "modules/reports/weeklyReportViewList";
    }

    /**
     * 工时统计
     */
    @RequiresPermissions("checkmodel:weeklyReport:viewExcel")
    @RequestMapping(value = "viewSum")
    public String viewSum(WeeklyReport weeklyReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        String type=request.getParameter("type");
        //查找考核人记录
        User user =UserUtils.getUser();
        CheckUser tempCheckUser=new CheckUser();
        tempCheckUser.setUserId(user.getId());
        List<CheckUser> checkUserList=checkUserService.findList(tempCheckUser);
        if(FormatUtil.isNoEmpty(checkUserList)&&checkUserList.size()>0){
            StringBuilder sqlstr=new StringBuilder();
            for (int i = 0; i < checkUserList.size(); i++) {
                sqlstr.append(" ((a.officeid='"+checkUserList.get(i).getCheckofficeid()+"' or o.parent_ids like '%"+checkUserList.get(i).getCheckofficeid()+"%') and a.stationid='"+checkUserList.get(i).getStationId()+"') or");
            }
            String ids=sqlstr.toString();
            if(FormatUtil.isNoEmpty(ids)){
                ids=sqlstr.toString().substring(0,ids.length()-2);
            }
            weeklyReport.setSqlstr(ids);
        }

        Page<WeeklyReport> page = weeklyReportService.findPage(new Page<WeeklyReport>(request, response, -1), weeklyReport);
        List<WeeklyReportDetail> list=Lists.newArrayList();
        for(WeeklyReport w:page.getList()){
            w=weeklyReportService.get(w.getId());
            List<WeeklyReportDetail> tmplist;
            if("0".equalsIgnoreCase(type)){
                tmplist=w.getWeeklyReportDetailList();
            }
            else{
                tmplist=w.getWeeklyReportDetailListKey();
            }
            for(WeeklyReportDetail d:tmplist){
                d.setExceldate(w.getCreateDate());
                d.setExcelofficename(w.getOfficename());
                d.setExcelstationname(w.getStationname());
                d.setExceltitle(w.getTitle());
                d.setExcelusername(w.getUsername());
                d.setExceluserno(w.getUserno());
                list.add(d);
            }
        }

        //工时合并统计
        List<WeeklyReportDetail> resultList=Lists.newArrayList();
        resultList=weeklyReportService.findSumList(weeklyReport);

        for(WeeklyReportDetail detail:resultList){
            detail.setExcelusername(UserUtils.get(detail.getCreateBy().getId()).getName());
        }
        //拼音排序
        Collections.sort(resultList, new Comparator<WeeklyReportDetail>() {
            @Override
            public int compare(WeeklyReportDetail o1, WeeklyReportDetail o2) {
                Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
                return com.compare(o1.getExcelusername(), o2.getExcelusername());
            }
        });

        model.addAttribute("weeklyReportDetail", resultList);
        model.addAttribute("weeklyReport", weeklyReport);
        return "modules/reports/weeklyReportSumList";
    }
}
