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
import com.jeeplus.modules.leipiflow.entity.LeipiRunCache;
import com.jeeplus.modules.leipiflow.service.LeipiRunCacheService;

/**
 * 运行流程缓存Controller
 * @author 陈钱江
 * @version 2017-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/leipiflow/leipiRunCache")
public class LeipiRunCacheController extends BaseController {

	@Autowired
	private LeipiRunCacheService leipiRunCacheService;
	
	@ModelAttribute
	public LeipiRunCache get(@RequestParam(required=false) String id) {
		LeipiRunCache entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leipiRunCacheService.get(id);
		}
		if (entity == null){
			entity = new LeipiRunCache();
		}
		return entity;
	}
	
	/**
	 * 运行流程缓存列表页面
	 */
	@RequiresPermissions("leipiflow:leipiRunCache:list")
	@RequestMapping(value = {"list", ""})
	public String list(LeipiRunCache leipiRunCache, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LeipiRunCache> page = leipiRunCacheService.findPage(new Page<LeipiRunCache>(request, response), leipiRunCache); 
		model.addAttribute("page", page);
		return "modules/leipiflow/leipiRunCacheList";
	}

	/**
	 * 查看，增加，编辑运行流程缓存表单页面
	 */
	@RequiresPermissions(value={"leipiflow:leipiRunCache:view","leipiflow:leipiRunCache:add","leipiflow:leipiRunCache:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LeipiRunCache leipiRunCache, Model model) {
		model.addAttribute("leipiRunCache", leipiRunCache);
		return "modules/leipiflow/leipiRunCacheForm";
	}

	/**
	 * 保存运行流程缓存
	 */
	@RequiresPermissions(value={"leipiflow:leipiRunCache:add","leipiflow:leipiRunCache:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LeipiRunCache leipiRunCache, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, leipiRunCache)){
			return form(leipiRunCache, model);
		}
		if(!leipiRunCache.getIsNewRecord()){//编辑表单保存
			LeipiRunCache t = leipiRunCacheService.get(leipiRunCache.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(leipiRunCache, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			leipiRunCacheService.save(t);//保存
		}else{//新增表单保存
			leipiRunCacheService.save(leipiRunCache);//保存
		}
		addMessage(redirectAttributes, "保存运行流程缓存成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunCache/?repage";
	}
	
	/**
	 * 删除运行流程缓存
	 */
	@RequiresPermissions("leipiflow:leipiRunCache:del")
	@RequestMapping(value = "delete")
	public String delete(LeipiRunCache leipiRunCache, RedirectAttributes redirectAttributes) {
		leipiRunCacheService.delete(leipiRunCache);
		addMessage(redirectAttributes, "删除运行流程缓存成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunCache/?repage";
	}
	
	/**
	 * 批量删除运行流程缓存
	 */
	@RequiresPermissions("leipiflow:leipiRunCache:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leipiRunCacheService.delete(leipiRunCacheService.get(id));
		}
		addMessage(redirectAttributes, "删除运行流程缓存成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunCache/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("leipiflow:leipiRunCache:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LeipiRunCache leipiRunCache, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运行流程缓存"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeipiRunCache> page = leipiRunCacheService.findPage(new Page<LeipiRunCache>(request, response, -1), leipiRunCache);
    		new ExportExcel("运行流程缓存", LeipiRunCache.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出运行流程缓存记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunCache/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("leipiflow:leipiRunCache:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeipiRunCache> list = ei.getDataList(LeipiRunCache.class);
			for (LeipiRunCache leipiRunCache : list){
				try{
					leipiRunCacheService.save(leipiRunCache);
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
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunCache/?repage";
    }
	
	/**
	 * 下载导入运行流程缓存数据模板
	 */
	@RequiresPermissions("leipiflow:leipiRunCache:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运行流程缓存数据导入模板.xlsx";
    		List<LeipiRunCache> list = Lists.newArrayList(); 
    		new ExportExcel("运行流程缓存数据", LeipiRunCache.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiRunCache/?repage";
    }
	
	
	

}