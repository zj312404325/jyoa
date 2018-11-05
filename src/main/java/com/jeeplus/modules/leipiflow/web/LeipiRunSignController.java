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
import com.jeeplus.modules.leipiflow.entity.LeipiRunSign;
import com.jeeplus.modules.leipiflow.service.LeipiRunSignService;

/**
 * 运行流程signController
 * @author cqj
 * @version 2017-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/leipiflow/leipiRunSign")
public class LeipiRunSignController extends BaseController {

	@Autowired
	private LeipiRunSignService leipiRunSignService;
	
	@ModelAttribute
	public LeipiRunSign get(@RequestParam(required=false) String id) {
		LeipiRunSign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leipiRunSignService.get(id);
		}
		if (entity == null){
			entity = new LeipiRunSign();
		}
		return entity;
	}
	
	/**
	 * 运行流程sign列表页面
	 */
	@RequiresPermissions("leipiflow:leipiRunSign:list")
	@RequestMapping(value = {"list", ""})
	public String list(LeipiRunSign leipiRunSign, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LeipiRunSign> page = leipiRunSignService.findPage(new Page<LeipiRunSign>(request, response), leipiRunSign); 
		model.addAttribute("page", page);
		return "modules/leipiflow/leipiRunSignList";
	}

	/**
	 * 查看，增加，编辑运行流程sign表单页面
	 */
	@RequiresPermissions(value={"leipiflow:leipiRunSign:view","leipiflow:leipiRunSign:add","leipiflow:leipiRunSign:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LeipiRunSign leipiRunSign, Model model) {
		model.addAttribute("leipiRunSign", leipiRunSign);
		return "modules/leipiflow/leipiRunSignForm";
	}

	/**
	 * 保存运行流程sign
	 */
	@RequiresPermissions(value={"leipiflow:leipiRunSign:add","leipiflow:leipiRunSign:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LeipiRunSign leipiRunSign, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, leipiRunSign)){
			return form(leipiRunSign, model);
		}
		if(!leipiRunSign.getIsNewRecord()){//编辑表单保存
			LeipiRunSign t = leipiRunSignService.get(leipiRunSign.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(leipiRunSign, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			leipiRunSignService.save(t);//保存
		}else{//新增表单保存
			leipiRunSignService.save(leipiRunSign);//保存
		}
		addMessage(redirectAttributes, "保存运行流程sign成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunSign/?repage";
	}
	
	/**
	 * 删除运行流程sign
	 */
	@RequiresPermissions("leipiflow:leipiRunSign:del")
	@RequestMapping(value = "delete")
	public String delete(LeipiRunSign leipiRunSign, RedirectAttributes redirectAttributes) {
		leipiRunSignService.delete(leipiRunSign);
		addMessage(redirectAttributes, "删除运行流程sign成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunSign/?repage";
	}
	
	/**
	 * 批量删除运行流程sign
	 */
	@RequiresPermissions("leipiflow:leipiRunSign:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leipiRunSignService.delete(leipiRunSignService.get(id));
		}
		addMessage(redirectAttributes, "删除运行流程sign成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunSign/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("leipiflow:leipiRunSign:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LeipiRunSign leipiRunSign, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运行流程sign"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeipiRunSign> page = leipiRunSignService.findPage(new Page<LeipiRunSign>(request, response, -1), leipiRunSign);
    		new ExportExcel("运行流程sign", LeipiRunSign.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出运行流程sign记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunSign/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("leipiflow:leipiRunSign:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeipiRunSign> list = ei.getDataList(LeipiRunSign.class);
			for (LeipiRunSign leipiRunSign : list){
				try{
					leipiRunSignService.save(leipiRunSign);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条运行流程sign记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条运行流程sign记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入运行流程sign失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunSign/?repage";
    }
	
	/**
	 * 下载导入运行流程sign数据模板
	 */
	@RequiresPermissions("leipiflow:leipiRunSign:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运行流程sign数据导入模板.xlsx";
    		List<LeipiRunSign> list = Lists.newArrayList(); 
    		new ExportExcel("运行流程sign数据", LeipiRunSign.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunSign/?repage";
    }
	
	
	

}