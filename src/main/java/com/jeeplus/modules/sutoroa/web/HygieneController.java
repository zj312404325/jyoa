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
import org.nutz.http.Http;
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
import com.jeeplus.modules.sutoroa.entity.Hygiene;
import com.jeeplus.modules.sutoroa.service.HygieneService;

/**
 * 8s检查Controller
 * @author cqj
 * @version 2018-03-02
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/hygiene")
public class HygieneController extends BaseController {

	@Autowired
	private HygieneService hygieneService;
	
	@ModelAttribute
	public Hygiene get(@RequestParam(required=false) String id) {
		Hygiene entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hygieneService.get(id);
		}
		if (entity == null){
			entity = new Hygiene();
		}
		return entity;
	}
	
	/**
	 * 8s检查列表页面
	 */
	@RequiresPermissions("oa:hygiene:list")
	@RequestMapping(value = {"list", ""})
	public String list(Hygiene hygiene, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Hygiene> page = hygieneService.findPage(new Page<Hygiene>(request, response), hygiene); 
		model.addAttribute("page", page);
		return "modules/sutoroa/hygieneList";
	}

	/**
	 * 查看，增加，编辑8s检查表单页面
	 */
	@RequiresPermissions(value={"oa:hygiene:view","oa:hygiene:add","oa:hygiene:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Hygiene hygiene, Model model,HttpServletRequest request) {
		if(!FormatUtil.isNoEmpty(hygiene.getId())){
			hygiene.setType(0);
		}
		request.setAttribute("tp",request.getParameter("tp"));
		model.addAttribute("hygiene", hygiene);
		return "modules/sutoroa/hygieneForm";
	}

	/**
	 * 保存8s检查
	 */
	@RequiresPermissions(value={"oa:hygiene:add","oa:hygiene:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Hygiene hygiene, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception{
		if (!beanValidator(model, hygiene)){
			return form(hygiene, model,request);
		}
		if(!hygiene.getIsNewRecord()){//编辑表单保存
			Hygiene t = hygieneService.get(hygiene.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(hygiene, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			hygieneService.save(t);//保存
		}else{//新增表单保存
			hygieneService.save(hygiene);//保存
		}
		addMessage(redirectAttributes, "保存8s检查成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygiene/?repage";
	}
	
	/**
	 * 删除8s检查
	 */
	@RequiresPermissions("oa:hygiene:del")
	@RequestMapping(value = "delete")
	public String delete(Hygiene hygiene, RedirectAttributes redirectAttributes) {
		hygieneService.delete(hygiene);
		addMessage(redirectAttributes, "删除8s检查成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygiene/?repage";
	}
	
	/**
	 * 批量删除8s检查
	 */
	@RequiresPermissions("oa:hygiene:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hygieneService.delete(hygieneService.get(id));
		}
		addMessage(redirectAttributes, "删除8s检查成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygiene/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("oa:hygiene:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Hygiene hygiene, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "8s检查"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Hygiene> page = hygieneService.findPage(new Page<Hygiene>(request, response, -1), hygiene);
    		new ExportExcel("8s检查", Hygiene.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出8s检查记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygiene/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("oa:hygiene:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Hygiene> list = ei.getDataList(Hygiene.class);
			for (Hygiene hygiene : list){
				try{
					hygieneService.save(hygiene);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条8s检查记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条8s检查记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入8s检查失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygiene/?repage";
    }
	
	/**
	 * 下载导入8s检查数据模板
	 */
	@RequiresPermissions("oa:hygiene:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "8s检查数据导入模板.xlsx";
    		List<Hygiene> list = Lists.newArrayList(); 
    		new ExportExcel("8s检查数据", Hygiene.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygiene/?repage";
    }
	
	
	

}