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
import com.jeeplus.modules.leipiflow.entity.LeipiRun;
import com.jeeplus.modules.leipiflow.service.LeipiRunService;

/**
 * 流程运行表Controller
 * @author 陈钱江
 * @version 2017-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/leipiflow/leipiRun")
public class LeipiRunController extends BaseController {

	@Autowired
	private LeipiRunService leipiRunService;
	
	@ModelAttribute
	public LeipiRun get(@RequestParam(required=false) String id) {
		LeipiRun entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leipiRunService.get(id);
		}
		if (entity == null){
			entity = new LeipiRun();
		}
		return entity;
	}
	
	/**
	 * 流程运行表列表页面
	 */
	@RequiresPermissions("leipiflow:leipiRun:list")
	@RequestMapping(value = {"list", ""})
	public String list(LeipiRun leipiRun, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LeipiRun> page = leipiRunService.findPage(new Page<LeipiRun>(request, response), leipiRun); 
		model.addAttribute("page", page);
		return "modules/leipiflow/leipiRunList";
	}

	/**
	 * 查看，增加，编辑流程运行表表单页面
	 */
	@RequiresPermissions(value={"leipiflow:leipiRun:view","leipiflow:leipiRun:add","leipiflow:leipiRun:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LeipiRun leipiRun, Model model) {
		model.addAttribute("leipiRun", leipiRun);
		return "modules/leipiflow/leipiRunForm";
	}

	/**
	 * 保存流程运行表
	 */
	@RequiresPermissions(value={"leipiflow:leipiRun:add","leipiflow:leipiRun:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LeipiRun leipiRun, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, leipiRun)){
			return form(leipiRun, model);
		}
		if(!leipiRun.getIsNewRecord()){//编辑表单保存
			LeipiRun t = leipiRunService.get(leipiRun.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(leipiRun, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			leipiRunService.save(t);//保存
		}else{//新增表单保存
			leipiRunService.save(leipiRun);//保存
		}
		addMessage(redirectAttributes, "保存流程运行表成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRun/?repage";
	}
	
	/**
	 * 删除流程运行表
	 */
	@RequiresPermissions("leipiflow:leipiRun:del")
	@RequestMapping(value = "delete")
	public String delete(LeipiRun leipiRun, RedirectAttributes redirectAttributes) {
		leipiRunService.delete(leipiRun);
		addMessage(redirectAttributes, "删除流程运行表成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRun/?repage";
	}
	
	/**
	 * 批量删除流程运行表
	 */
	@RequiresPermissions("leipiflow:leipiRun:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leipiRunService.delete(leipiRunService.get(id));
		}
		addMessage(redirectAttributes, "删除流程运行表成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRun/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("leipiflow:leipiRun:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LeipiRun leipiRun, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流程运行表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeipiRun> page = leipiRunService.findPage(new Page<LeipiRun>(request, response, -1), leipiRun);
    		new ExportExcel("流程运行表", LeipiRun.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出流程运行表记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRun/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("leipiflow:leipiRun:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeipiRun> list = ei.getDataList(LeipiRun.class);
			for (LeipiRun leipiRun : list){
				try{
					leipiRunService.save(leipiRun);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条流程运行表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条流程运行表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入流程运行表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRun/?repage";
    }
	
	/**
	 * 下载导入流程运行表数据模板
	 */
	@RequiresPermissions("leipiflow:leipiRun:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流程运行表数据导入模板.xlsx";
    		List<LeipiRun> list = Lists.newArrayList(); 
    		new ExportExcel("流程运行表数据", LeipiRun.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRun/?repage";
    }
	
	
	

}