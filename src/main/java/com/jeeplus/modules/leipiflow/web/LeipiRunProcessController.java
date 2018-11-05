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
import com.jeeplus.modules.leipiflow.entity.LeipiRunProcess;
import com.jeeplus.modules.leipiflow.service.LeipiRunProcessService;

/**
 * 运行流程步骤Controller
 * @author 陈钱江
 * @version 2017-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/leipiflow/leipiRunProcess")
public class LeipiRunProcessController extends BaseController {

	@Autowired
	private LeipiRunProcessService leipiRunProcessService;
	
	@ModelAttribute
	public LeipiRunProcess get(@RequestParam(required=false) String id) {
		LeipiRunProcess entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leipiRunProcessService.get(id);
		}
		if (entity == null){
			entity = new LeipiRunProcess();
		}
		return entity;
	}
	
	/**
	 * 运行流程步骤列表页面
	 */
	@RequiresPermissions("leipiflow:leipiRunProcess:list")
	@RequestMapping(value = {"list", ""})
	public String list(LeipiRunProcess leipiRunProcess, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LeipiRunProcess> page = leipiRunProcessService.findPage(new Page<LeipiRunProcess>(request, response), leipiRunProcess); 
		model.addAttribute("page", page);
		return "modules/leipiflow/leipiRunProcessList";
	}

	/**
	 * 查看，增加，编辑运行流程步骤表单页面
	 */
	@RequiresPermissions(value={"leipiflow:leipiRunProcess:view","leipiflow:leipiRunProcess:add","leipiflow:leipiRunProcess:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LeipiRunProcess leipiRunProcess, Model model) {
		model.addAttribute("leipiRunProcess", leipiRunProcess);
		return "modules/leipiflow/leipiRunProcessForm";
	}

	/**
	 * 保存运行流程步骤
	 */
	@RequiresPermissions(value={"leipiflow:leipiRunProcess:add","leipiflow:leipiRunProcess:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LeipiRunProcess leipiRunProcess, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, leipiRunProcess)){
			return form(leipiRunProcess, model);
		}
		if(!leipiRunProcess.getIsNewRecord()){//编辑表单保存
			LeipiRunProcess t = leipiRunProcessService.get(leipiRunProcess.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(leipiRunProcess, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			leipiRunProcessService.save(t);//保存
		}else{//新增表单保存
			leipiRunProcessService.save(leipiRunProcess);//保存
		}
		addMessage(redirectAttributes, "保存运行流程步骤成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunProcess/?repage";
	}
	
	/**
	 * 删除运行流程步骤
	 */
	@RequiresPermissions("leipiflow:leipiRunProcess:del")
	@RequestMapping(value = "delete")
	public String delete(LeipiRunProcess leipiRunProcess, RedirectAttributes redirectAttributes) {
		leipiRunProcessService.delete(leipiRunProcess);
		addMessage(redirectAttributes, "删除运行流程步骤成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunProcess/?repage";
	}
	
	/**
	 * 批量删除运行流程步骤
	 */
	@RequiresPermissions("leipiflow:leipiRunProcess:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leipiRunProcessService.delete(leipiRunProcessService.get(id));
		}
		addMessage(redirectAttributes, "删除运行流程步骤成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunProcess/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("leipiflow:leipiRunProcess:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LeipiRunProcess leipiRunProcess, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运行流程步骤"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeipiRunProcess> page = leipiRunProcessService.findPage(new Page<LeipiRunProcess>(request, response, -1), leipiRunProcess);
    		new ExportExcel("运行流程步骤", LeipiRunProcess.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出运行流程步骤记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunProcess/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("leipiflow:leipiRunProcess:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeipiRunProcess> list = ei.getDataList(LeipiRunProcess.class);
			for (LeipiRunProcess leipiRunProcess : list){
				try{
					leipiRunProcessService.save(leipiRunProcess);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条运行流程步骤记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条运行流程步骤记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入运行流程步骤失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunProcess/?repage";
    }
	
	/**
	 * 下载导入运行流程步骤数据模板
	 */
	@RequiresPermissions("leipiflow:leipiRunProcess:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运行流程步骤数据导入模板.xlsx";
    		List<LeipiRunProcess> list = Lists.newArrayList(); 
    		new ExportExcel("运行流程步骤数据", LeipiRunProcess.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunProcess/?repage";
    }
	
	
	

}