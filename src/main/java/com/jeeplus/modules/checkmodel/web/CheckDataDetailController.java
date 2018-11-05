/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.web;

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
import com.jeeplus.modules.checkmodel.entity.CheckDataDetail;
import com.jeeplus.modules.checkmodel.service.CheckDataDetailService;

/**
 * 绩效数据设定Controller
 * @author cqj
 * @version 2017-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/checkmodel/checkDataDetail")
public class CheckDataDetailController extends BaseController {

	@Autowired
	private CheckDataDetailService checkDataDetailService;
	
	@ModelAttribute
	public CheckDataDetail get(@RequestParam(required=false) String id) {
		CheckDataDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkDataDetailService.get(id);
		}
		if (entity == null){
			entity = new CheckDataDetail();
		}
		return entity;
	}
	
	/**
	 * 绩效数据设定列表页面
	 */
	@RequiresPermissions("checkmodel:checkDataDetail:list")
	@RequestMapping(value = {"list", ""})
	public String list(CheckDataDetail checkDataDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckDataDetail> page = checkDataDetailService.findPage(new Page<CheckDataDetail>(request, response), checkDataDetail); 
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/checkDataDetailList";
	}

	/**
	 * 查看，增加，编辑绩效数据设定表单页面
	 */
	@RequiresPermissions(value={"checkmodel:checkDataDetail:view","checkmodel:checkDataDetail:add","checkmodel:checkDataDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CheckDataDetail checkDataDetail, Model model) {
		model.addAttribute("checkDataDetail", checkDataDetail);
		return "modules/ehr/checkmodel/checkDataDetailForm";
	}

	/**
	 * 保存绩效数据设定
	 */
	@RequiresPermissions(value={"checkmodel:checkDataDetail:add","checkmodel:checkDataDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CheckDataDetail checkDataDetail, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, checkDataDetail)){
			return form(checkDataDetail, model);
		}
		if(!checkDataDetail.getIsNewRecord()){//编辑表单保存
			CheckDataDetail t = checkDataDetailService.get(checkDataDetail.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(checkDataDetail, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			checkDataDetailService.save(t);//保存
		}else{//新增表单保存
			checkDataDetailService.save(checkDataDetail);//保存
		}
		addMessage(redirectAttributes, "保存绩效数据设定成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkDataDetail/?repage";
	}
	
	/**
	 * 删除绩效数据设定
	 */
	@RequiresPermissions("checkmodel:checkDataDetail:del")
	@RequestMapping(value = "delete")
	public String delete(CheckDataDetail checkDataDetail, RedirectAttributes redirectAttributes) {
		checkDataDetailService.delete(checkDataDetail);
		addMessage(redirectAttributes, "删除绩效数据设定成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkDataDetail/?repage";
	}
	
	/**
	 * 批量删除绩效数据设定
	 */
	@RequiresPermissions("checkmodel:checkDataDetail:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			checkDataDetailService.delete(checkDataDetailService.get(id));
		}
		addMessage(redirectAttributes, "删除绩效数据设定成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkDataDetail/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("checkmodel:checkDataDetail:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CheckDataDetail checkDataDetail, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "绩效数据设定"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CheckDataDetail> page = checkDataDetailService.findPage(new Page<CheckDataDetail>(request, response, -1), checkDataDetail);
    		new ExportExcel("绩效数据设定", CheckDataDetail.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出绩效数据设定记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkDataDetail/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("checkmodel:checkDataDetail:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CheckDataDetail> list = ei.getDataList(CheckDataDetail.class);
			for (CheckDataDetail checkDataDetail : list){
				try{
					checkDataDetailService.save(checkDataDetail);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条绩效数据设定记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条绩效数据设定记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入绩效数据设定失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkDataDetail/?repage";
    }
	
	/**
	 * 下载导入绩效数据设定数据模板
	 */
	@RequiresPermissions("checkmodel:checkDataDetail:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "绩效数据设定数据导入模板.xlsx";
    		List<CheckDataDetail> list = Lists.newArrayList(); 
    		new ExportExcel("绩效数据设定数据", CheckDataDetail.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkDataDetail/?repage";
    }
	
	
	

}