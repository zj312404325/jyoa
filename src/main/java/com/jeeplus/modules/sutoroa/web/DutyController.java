/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.FormatUtil;
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
import com.jeeplus.modules.sutoroa.entity.Duty;
import com.jeeplus.modules.sutoroa.service.DutyService;

/**
 * 值班表Controller
 * @author cqj
 * @version 2018-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/duty")
public class DutyController extends BaseController {

	@Autowired
	private DutyService dutyService;
	
	@ModelAttribute
	public Duty get(@RequestParam(required=false) String id) {
		Duty entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dutyService.get(id);
		}
		if (entity == null){
			entity = new Duty();
		}
		return entity;
	}
	
	/**
	 * 值班表列表页面
	 */
	@RequiresPermissions("oa:duty:list")
	@RequestMapping(value = {"list", ""})
	public String list(Duty duty, HttpServletRequest request, HttpServletResponse response, Model model) {
		duty.setTbType(request.getParameter("tbType"));
		Page<Duty> page = dutyService.findPage(new Page<Duty>(request, response), duty);
		model.addAttribute("page", page);
		request.setAttribute("tbType",request.getParameter("tbType"));
		return "modules/sutoroa/dutyList";
	}

	/**
	 * 查看，增加，编辑值班表表单页面
	 */
	@RequiresPermissions(value={"oa:duty:view","oa:duty:add","oa:duty:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Duty duty, Model model, HttpServletRequest request) {
		model.addAttribute("duty", duty);
		request.setAttribute("tbType",request.getParameter("tbType"));
		request.setAttribute("type",request.getParameter("type"));
		if(!FormatUtil.isNoEmpty(duty.getId())){//新增
			if(request.getParameter("tbType").equals("1")){//值班表
				duty.setCompany("1");
			}else{//倒班表
				duty.setWorktime("0");
				duty.setCompany("5");
			}
		}
		return "modules/sutoroa/dutyForm";
	}

	/**
	 * 保存值班表
	 */
	@RequiresPermissions(value={"oa:duty:add","oa:duty:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Duty duty, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception{
		if (!beanValidator(model, duty)){
			return form(duty, model,request);
		}
		if(!duty.getIsNewRecord()){//编辑表单保存
			Duty t = dutyService.get(duty.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(duty, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dutyService.save(t);//保存
		}else{//新增表单保存
			dutyService.save(duty);//保存
		}
		addMessage(redirectAttributes, "保存值班表成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/duty/?repage&tbType="+request.getParameter("tbType");
	}
	
	/**
	 * 删除值班表
	 */
	@RequiresPermissions("oa:duty:del")
	@RequestMapping(value = "delete")
	public String delete(Duty duty, RedirectAttributes redirectAttributes) {
		dutyService.delete(duty);
		addMessage(redirectAttributes, "删除值班表成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/duty/?repage";
	}
	
	/**
	 * 批量删除值班表
	 */
	@RequiresPermissions("oa:duty:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dutyService.delete(dutyService.get(id));
		}
		addMessage(redirectAttributes, "删除值班表成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/duty/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("oa:duty:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Duty duty, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "值班表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Duty> page = dutyService.findPage(new Page<Duty>(request, response, -1), duty);
    		new ExportExcel("值班表", Duty.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出值班表记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/duty/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("oa:duty:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Duty> list = ei.getDataList(Duty.class);
			for (Duty duty : list){
				try{
					dutyService.save(duty);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条值班表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条值班表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入值班表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/duty/?repage";
    }
	
	/**
	 * 下载导入值班表数据模板
	 */
	@RequiresPermissions("oa:duty:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "值班表数据导入模板.xlsx";
    		List<Duty> list = Lists.newArrayList(); 
    		new ExportExcel("值班表数据", Duty.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/duty/?repage";
    }
	
	
	

}