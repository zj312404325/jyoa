/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.web;

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
import com.jeeplus.modules.ehr.entity.BackgroundSurvey;
import com.jeeplus.modules.ehr.service.BackgroundSurveyService;

/**
 * 背景调查Controller
 * @author yc
 * @version 2017-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/ehr/backgroundSurvey")
public class BackgroundSurveyController extends BaseController {

	@Autowired
	private BackgroundSurveyService backgroundSurveyService;
	
	@ModelAttribute
	public BackgroundSurvey get(@RequestParam(required=false) String id) {
		BackgroundSurvey entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = backgroundSurveyService.get(id);
		}
		if (entity == null){
			entity = new BackgroundSurvey();
		}
		return entity;
	}
	
	/**
	 * 信息列表页面
	 */
	@RequiresPermissions("ehr:backgroundSurvey:list")
	@RequestMapping(value = {"list", ""})
	public String list(BackgroundSurvey backgroundSurvey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BackgroundSurvey> page = backgroundSurveyService.findPage(new Page<BackgroundSurvey>(request, response), backgroundSurvey); 
		model.addAttribute("page", page);
		return "modules/ehr/backgroundsurvey/backgroundSurveyList";
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
	@RequiresPermissions(value={"ehr:backgroundSurvey:view","ehr:backgroundSurvey:add","ehr:backgroundSurvey:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BackgroundSurvey backgroundSurvey, Model model) {
		model.addAttribute("backgroundSurvey", backgroundSurvey);
		return "modules/ehr/backgroundsurvey/backgroundSurveyForm";
	}
	
	@RequiresPermissions(value={"ehr:backgroundSurvey:view","ehr:backgroundSurvey:add","ehr:backgroundSurvey:edit"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(BackgroundSurvey backgroundSurvey, Model model) {
		model.addAttribute("backgroundSurvey", backgroundSurvey);
		return "modules/ehr/backgroundsurvey/backgroundSurveyView";
	}

	/**
	 * 保存信息
	 */
	@RequiresPermissions(value={"ehr:backgroundSurvey:add","ehr:backgroundSurvey:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(BackgroundSurvey backgroundSurvey, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, backgroundSurvey)){
			return form(backgroundSurvey, model);
		}
		if(!backgroundSurvey.getIsNewRecord()){//编辑表单保存
			BackgroundSurvey t = backgroundSurveyService.get(backgroundSurvey.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(backgroundSurvey, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			backgroundSurveyService.save(t);//保存
		}else{//新增表单保存
			backgroundSurveyService.save(backgroundSurvey);//保存
		}
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/backgroundSurvey/?repage";
	}
	
	/**
	 * 删除信息
	 */
	@RequiresPermissions("ehr:backgroundSurvey:del")
	@RequestMapping(value = "delete")
	public String delete(BackgroundSurvey backgroundSurvey, RedirectAttributes redirectAttributes) {
		backgroundSurveyService.delete(backgroundSurvey);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/backgroundSurvey/?repage";
	}
	
	/**
	 * 批量删除信息
	 */
	@RequiresPermissions("ehr:backgroundSurvey:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			backgroundSurveyService.delete(backgroundSurveyService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/backgroundSurvey/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ehr:backgroundSurvey:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(BackgroundSurvey backgroundSurvey, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BackgroundSurvey> page = backgroundSurveyService.findPage(new Page<BackgroundSurvey>(request, response, -1), backgroundSurvey);
    		new ExportExcel("信息", BackgroundSurvey.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/backgroundSurvey/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ehr:backgroundSurvey:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BackgroundSurvey> list = ei.getDataList(BackgroundSurvey.class);
			for (BackgroundSurvey backgroundSurvey : list){
				try{
					backgroundSurveyService.save(backgroundSurvey);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/backgroundSurvey/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
	@RequiresPermissions("ehr:backgroundSurvey:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<BackgroundSurvey> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", BackgroundSurvey.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/backgroundSurvey/?repage";
    }
	
	@RequestMapping(value = "hetongdaoqi")
	public String hetongdaoqi(BackgroundSurvey backgroundSurvey, Model model) {
		return "modules/ehr/checkmodel/hetongdaoqi";
	}
	

}