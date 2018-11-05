/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.web;

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
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.leipiflow.entity.LeipiRunLog;
import com.jeeplus.modules.leipiflow.service.LeipiRunLogService;

/**
 * 运行流程缓存Controller
 * @author 陈钱江
 * @version 2017-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/leipiflow/leipiRunLog")
public class LeipiRunLogController extends BaseController {

	@Autowired
	private LeipiRunLogService leipiRunLogService;
	
	@ModelAttribute
	public LeipiRunLog get(@RequestParam(required=false) String id) {
		LeipiRunLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leipiRunLogService.get(id);
		}
		if (entity == null){
			entity = new LeipiRunLog();
		}
		return entity;
	}
	
	/**
	 * 运行流程缓存列表页面
	 */
	@RequiresPermissions("leipiflow:leipiRunLog:list")
	@RequestMapping(value = {"list", ""})
	public String list(LeipiRunLog leipiRunLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LeipiRunLog> page = leipiRunLogService.findPage(new Page<LeipiRunLog>(request, response), leipiRunLog); 
		model.addAttribute("page", page);
		return "modules/leipiflow/leipiRunLogList";
	}

	/**
	 * 查看，增加，编辑运行流程缓存表单页面
	 */
	@RequiresPermissions(value={"leipiflow:leipiRunLog:view","leipiflow:leipiRunLog:add","leipiflow:leipiRunLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LeipiRunLog leipiRunLog, Model model) {
		model.addAttribute("leipiRunLog", leipiRunLog);
		return "modules/leipiflow/leipiRunLogForm";
	}

	/**
	 * 保存运行流程缓存
	 */
	@RequiresPermissions(value={"leipiflow:leipiRunLog:add","leipiflow:leipiRunLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LeipiRunLog leipiRunLog, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, leipiRunLog)){
			return form(leipiRunLog, model);
		}
		if(!leipiRunLog.getIsNewRecord()){//编辑表单保存
			LeipiRunLog t = leipiRunLogService.get(leipiRunLog.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(leipiRunLog, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			leipiRunLogService.save(t);//保存
		}else{//新增表单保存
			leipiRunLogService.save(leipiRunLog);//保存
		}
		addMessage(redirectAttributes, "保存运行流程缓存成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunLog/?repage";
	}
	
	/**
	 * 删除运行流程缓存
	 */
	@RequiresPermissions("leipiflow:leipiRunLog:del")
	@RequestMapping(value = "delete")
	public String delete(LeipiRunLog leipiRunLog, RedirectAttributes redirectAttributes) {
		leipiRunLogService.delete(leipiRunLog);
		addMessage(redirectAttributes, "删除运行流程缓存成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunLog/?repage";
	}
	
	/**
	 * 批量删除运行流程缓存
	 */
	@RequiresPermissions("leipiflow:leipiRunLog:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leipiRunLogService.delete(leipiRunLogService.get(id));
		}
		addMessage(redirectAttributes, "删除运行流程缓存成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunLog/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("leipiflow:leipiRunLog:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LeipiRunLog leipiRunLog, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运行流程缓存"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeipiRunLog> page = leipiRunLogService.findPage(new Page<LeipiRunLog>(request, response, -1), leipiRunLog);
    		new ExportExcel("运行流程缓存", LeipiRunLog.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出运行流程缓存记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunLog/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("leipiflow:leipiRunLog:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeipiRunLog> list = ei.getDataList(LeipiRunLog.class);
			for (LeipiRunLog leipiRunLog : list){
				try{
					leipiRunLogService.save(leipiRunLog);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条运行流程缓存记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条运行流程缓存记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入运行流程缓存失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunLog/?repage";
    }
	
	/**
	 * 下载导入运行流程缓存数据模板
	 */
	@RequiresPermissions("leipiflow:leipiRunLog:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运行流程缓存数据导入模板.xlsx";
    		List<LeipiRunLog> list = Lists.newArrayList(); 
    		new ExportExcel("运行流程缓存数据", LeipiRunLog.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunLog/?repage";
    }
	
	
	

}