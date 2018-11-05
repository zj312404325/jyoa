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
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.ehr.entity.QuestionSurvey;
import com.jeeplus.modules.ehr.service.QuestionSurveyService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 离职调查问卷Controller
 * @author cqj
 * @version 2017-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/ehr/questionSurvey")
public class QuestionSurveyController extends BaseController {

	@Autowired
	private QuestionSurveyService questionSurveyService;
	
	@ModelAttribute
	public QuestionSurvey get(@RequestParam(required=false) String id) {
		QuestionSurvey entity = null;
		if(FormatUtil.isNoEmpty(id)){
			entity = questionSurveyService.get(id);
			entity.setManagerview("1");
		}else{
			/*QuestionSurvey temp = new QuestionSurvey();
			temp.setCreateBy(UserUtils.getUser());
			List<QuestionSurvey> questionSurveyList=questionSurveyService.findList(temp);
			if (FormatUtil.isNoEmpty(questionSurveyList)&&questionSurveyList.size()>0){
				entity = questionSurveyList.get(0);
			}
			if (entity == null){
				entity = new QuestionSurvey();
			}*/
			entity = new QuestionSurvey();
		}
		return entity;
	}
	
	/**
	 * 离职调查问卷列表页面
	 */
	@RequiresPermissions("ehr:questionSurvey:list")
	@RequestMapping(value = {"list", ""})
	public String list(QuestionSurvey questionSurvey, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(FormatUtil.isNoEmpty(request.getParameter("isadmin"))&&request.getParameter("isadmin").equals("1")){
			questionSurvey.setCreateBy(null);
		}
		Page<QuestionSurvey> page = questionSurveyService.findPage(new Page<QuestionSurvey>(request, response), questionSurvey);
		model.addAttribute("page", page);
		return "modules/ehr/questionsurvey/questionSurveyList";
	}

	/**
	 * 查看，增加，编辑离职调查问卷表单页面
	 */
	@RequiresPermissions(value={"ehr:questionSurvey:view","ehr:questionSurvey:add","ehr:questionSurvey:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(QuestionSurvey questionSurvey, Model model,HttpServletRequest request) {
		request.setAttribute("isadmin",request.getParameter("isadmin"));
		if(!FormatUtil.isNoEmpty(request.getParameter("isadmin"))){
			QuestionSurvey temp = new QuestionSurvey();
			temp.setCreateBy(UserUtils.getUser());
			List<QuestionSurvey> questionSurveyList=questionSurveyService.findList(temp);
			if (FormatUtil.isNoEmpty(questionSurveyList)&&questionSurveyList.size()>0){
				questionSurvey = questionSurveyList.get(0);
			}
		}
		model.addAttribute("questionSurvey", questionSurvey);
		return "modules/ehr/questionsurvey/questionSurveyForm";
	}

	/**
	 * 保存离职调查问卷
	 */
	@RequestMapping(value = "save")
	public String save(QuestionSurvey questionSurvey, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, questionSurvey)){
			return form(questionSurvey, model,request);
		}
		if(!questionSurvey.getIsNewRecord()){//编辑表单保存
			QuestionSurvey t = questionSurveyService.get(questionSurvey.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(questionSurvey, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			questionSurveyService.save(t);//保存
		}else{//新增表单保存
			questionSurveyService.save(questionSurvey);//保存
		}
		addMessage(redirectAttributes, "保存离职调查问卷成功");
		if(FormatUtil.isNoEmpty(request.getParameter("isadmin"))&&request.getParameter("isadmin").equals("1")){
			return "redirect:"+Global.getAdminPath()+"/ehr/questionSurvey/?repage";
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/questionSurvey/form";
	}
	
	/**
	 * 删除离职调查问卷
	 */
	@RequiresPermissions("ehr:questionSurvey:del")
	@RequestMapping(value = "delete")
	public String delete(QuestionSurvey questionSurvey, RedirectAttributes redirectAttributes) {
		questionSurveyService.delete(questionSurvey);
		addMessage(redirectAttributes, "删除离职调查问卷成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/questionSurvey/?repage";
	}
	
	/**
	 * 批量删除离职调查问卷
	 */
	@RequiresPermissions("ehr:questionSurvey:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			questionSurveyService.delete(questionSurveyService.get(id));
		}
		addMessage(redirectAttributes, "删除离职调查问卷成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/questionSurvey/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ehr:questionSurvey:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(QuestionSurvey questionSurvey, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "离职调查问卷"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QuestionSurvey> page = questionSurveyService.findPage(new Page<QuestionSurvey>(request, response, -1), questionSurvey);
    		new ExportExcel("离职调查问卷", QuestionSurvey.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出离职调查问卷记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/questionSurvey/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ehr:questionSurvey:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QuestionSurvey> list = ei.getDataList(QuestionSurvey.class);
			for (QuestionSurvey questionSurvey : list){
				try{
					questionSurveyService.save(questionSurvey);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条离职调查问卷记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条离职调查问卷记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入离职调查问卷失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/questionSurvey/?repage";
    }
	
	/**
	 * 下载导入离职调查问卷数据模板
	 */
	@RequiresPermissions("ehr:questionSurvey:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "离职调查问卷数据导入模板.xlsx";
    		List<QuestionSurvey> list = Lists.newArrayList(); 
    		new ExportExcel("离职调查问卷数据", QuestionSurvey.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/questionSurvey/?repage";
    }
	
	
	

}