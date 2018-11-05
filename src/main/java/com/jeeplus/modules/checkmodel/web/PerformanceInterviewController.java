/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.web;

import java.text.Normalizer;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.checkmodel.entity.CheckTime;
import com.jeeplus.modules.checkmodel.entity.CheckUser;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheck;
import com.jeeplus.modules.checkmodel.entity.PerformanceInterview;
import com.jeeplus.modules.checkmodel.service.CheckTimeService;
import com.jeeplus.modules.checkmodel.service.CheckUserService;
import com.jeeplus.modules.checkmodel.service.PerformanceInterviewService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 绩效考核面谈表Controller
 * @author cqj
 * @version 2017-10-27
 */
@Controller
@RequestMapping(value = "${adminPath}/checkmodel/performanceInterview")
public class PerformanceInterviewController extends BaseController {

	@Autowired
	private PerformanceInterviewService performanceInterviewService;
	
	@Autowired
	private CheckUserService checkUserService;
	
	@Autowired
	private CheckTimeService checkTimeService;
	
	@ModelAttribute
	public PerformanceInterview get(@RequestParam(required=false) String id) {
		PerformanceInterview entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = performanceInterviewService.get(id);
		}
		if (entity == null){
			entity = new PerformanceInterview();
		}
		return entity;
	}
	
	@RequiresPermissions("checkmodel:performanceInterview:list")
	@RequestMapping(value = {"performanceInterviewIndex"})
	public String performanceInterviewIndex(CheckUser checkUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("repage", request.getParameter("repage"));
		return "modules/ehr/checkmodel/performanceInterviewIndex";
	}
	
	/**
	 * 绩效考核面谈表列表页面
	 */
	@RequiresPermissions("checkmodel:performanceInterview:list")
	@RequestMapping(value = {"list", ""})
	public String list(PerformanceInterview performanceInterview, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user= UserUtils.getUser();
		if(FormatUtil.isNoEmpty(request.getParameter("category"))&&request.getParameter("category").equals("recieve")){
			Page<PerformanceInterview> page = getListPage(user,request,response,performanceInterview);  
			model.addAttribute("page", page);
			return "modules/ehr/checkmodel/performanceInterviewRecieveList";
		}
		
		//判断是否有考核时间
		boolean canCheck=validCanCheck();
		request.setAttribute("canCheck", canCheck);
		performanceInterview.setCreateBy(user);
		Page<PerformanceInterview> page = performanceInterviewService.findPage(new Page<PerformanceInterview>(request, response), performanceInterview); 
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/performanceInterviewSendList";
	}

	/**
	 * 是否有考核时间记录
	 * @return
	 */
	private boolean validCanCheck() {
		// TODO Auto-generated method stub
		CheckTime tempCheckTime=new CheckTime();
		tempCheckTime.setStartDate(new Date());
		List<CheckTime> checkTimeList=checkTimeService.findList(tempCheckTime);
		if(FormatUtil.isNoEmpty(checkTimeList)&&checkTimeList.size()>0){
			return true;
		}
		return false;
	}

	private Page<PerformanceInterview> getListPage(User user,
			HttpServletRequest request, HttpServletResponse response,
			PerformanceInterview performanceInterview) {
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
			performanceInterview.setSqlstr(ids);
			return performanceInterviewService.findPage(new Page<PerformanceInterview>(request, response), performanceInterview);
		}
		performanceInterview.setSqlstr("1!=1");
		return performanceInterviewService.findPage(new Page<PerformanceInterview>(request, response), performanceInterview);
	}

	/**
	 * 查看，增加，编辑绩效考核面谈表表单页面
	 */
	@RequiresPermissions(value={"checkmodel:performanceInterview:view","checkmodel:performanceInterview:add","checkmodel:performanceInterview:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PerformanceInterview performanceInterview, Model model,HttpServletRequest request) {
		request.setAttribute("bhv",request.getParameter("bhv"));
		request.setAttribute("type", request.getParameter("type"));
		model.addAttribute("performanceInterview", performanceInterview);
		if(FormatUtil.isNoEmpty(request.getParameter("type"))&&request.getParameter("type").equals("1")){
			return "modules/ehr/checkmodel/performanceInterviewRecieveForm";
		}
		return "modules/ehr/checkmodel/performanceInterviewForm";
	}

	/**
	 * 保存绩效考核面谈表
	 */
	@RequiresPermissions(value={"checkmodel:performanceInterview:add","checkmodel:performanceInterview:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PerformanceInterview performanceInterview, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, performanceInterview)){
			return form(performanceInterview, model,request);
		}
		if(!performanceInterview.getIsNewRecord()){//编辑表单保存
			if(FormatUtil.isNoEmpty(request.getParameter("rcv"))&&request.getParameter("rcv").equals("1")){
				PerformanceInterview t = performanceInterviewService.get(performanceInterview.getId());//从数据库取出记录的值
				t.setOverachievement(performanceInterview.getOverachievement());
				t.setInterviewcomment(performanceInterview.getInterviewcomment());
				t.setDevelopment(performanceInterview.getDevelopment());
				performanceInterviewService.savePerformanceInterview(t);//保存
			}else{
				PerformanceInterview t = performanceInterviewService.get(performanceInterview.getId());//从数据库取出记录的值
				MyBeanUtils.copyBeanNotNull2Bean(performanceInterview, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
				performanceInterviewService.save(t);//保存
			}
		}else{//新增表单保存
			performanceInterviewService.save(performanceInterview);//保存
		}
		addMessage(redirectAttributes, "保存绩效考核面谈表成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceInterview/performanceInterviewIndex?repage=repage&type="+request.getParameter("type");
	}
	
	/**
	 * 删除绩效考核面谈表
	 */
	@RequiresPermissions("checkmodel:performanceInterview:del")
	@RequestMapping(value = "delete")
	public String delete(PerformanceInterview performanceInterview, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		performanceInterviewService.delete(performanceInterview);
		addMessage(redirectAttributes, "删除绩效考核面谈表成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceInterview/performanceInterviewIndex?repage=repage&type="+request.getParameter("type");
	}
	
	/**
	 * 批量删除绩效考核面谈表
	 */
	@RequiresPermissions("checkmodel:performanceInterview:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			performanceInterviewService.delete(performanceInterviewService.get(id));
		}
		addMessage(redirectAttributes, "删除绩效考核面谈表成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceInterview/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("checkmodel:performanceInterview:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(PerformanceInterview performanceInterview, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "绩效考核面谈表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PerformanceInterview> page = performanceInterviewService.findPage(new Page<PerformanceInterview>(request, response, -1), performanceInterview);
    		new ExportExcel("绩效考核面谈表", PerformanceInterview.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出绩效考核面谈表记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceInterview/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("checkmodel:performanceInterview:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PerformanceInterview> list = ei.getDataList(PerformanceInterview.class);
			for (PerformanceInterview performanceInterview : list){
				try{
					performanceInterviewService.save(performanceInterview);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条绩效考核面谈表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条绩效考核面谈表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入绩效考核面谈表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceInterview/?repage";
    }
	
	/**
	 * 下载导入绩效考核面谈表数据模板
	 */
	@RequiresPermissions("checkmodel:performanceInterview:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "绩效考核面谈表数据导入模板.xlsx";
    		List<PerformanceInterview> list = Lists.newArrayList(); 
    		new ExportExcel("绩效考核面谈表数据", PerformanceInterview.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceInterview/?repage";
    }
	
	/**
	 * 绩效考核面谈列表页面(后台)
	 */
	@RequiresPermissions("checkmodel:performanceInterview:managerlist")
	@RequestMapping(value = {"managerlist"})
	public String managerlist(PerformanceInterview performanceInterview, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PerformanceInterview> page = performanceInterviewService.findPage(new Page<PerformanceInterview>(request, response), performanceInterview); 
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/performanceInterviewManageList";
	}
	

}