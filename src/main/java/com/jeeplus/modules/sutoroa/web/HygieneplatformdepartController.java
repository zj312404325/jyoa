/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.web;

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
import com.jeeplus.modules.sutoroa.entity.Hygieneplatformdepart;
import com.jeeplus.modules.sutoroa.service.HygieneplatformdepartService;

/**
 * 8s检查表内容Controller
 * @author cqj
 * @version 2018-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sutoroa/hygieneplatformdepart")
public class HygieneplatformdepartController extends BaseController {

	@Autowired
	private HygieneplatformdepartService hygieneplatformdepartService;
	
	@ModelAttribute
	public Hygieneplatformdepart get(@RequestParam(required=false) String id) {
		Hygieneplatformdepart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hygieneplatformdepartService.get(id);
		}
		if (entity == null){
			entity = new Hygieneplatformdepart();
		}
		return entity;
	}
	
	/**
	 * 8s检查表内容列表页面
	 */
	@RequiresPermissions("sutoroa:hygieneplatformdepart:list")
	@RequestMapping(value = {"list", ""})
	public String list(Hygieneplatformdepart hygieneplatformdepart, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Hygieneplatformdepart> page = hygieneplatformdepartService.findPage(new Page<Hygieneplatformdepart>(request, response), hygieneplatformdepart); 
		model.addAttribute("page", page);
		return "modules/sutoroa/hygieneplatformdepartList";
	}

	/**
	 * 查看，增加，编辑8s检查表内容表单页面
	 */
	@RequiresPermissions(value={"sutoroa:hygieneplatformdepart:view","sutoroa:hygieneplatformdepart:add","sutoroa:hygieneplatformdepart:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Hygieneplatformdepart hygieneplatformdepart, Model model) {
		model.addAttribute("hygieneplatformdepart", hygieneplatformdepart);
		return "modules/sutoroa/hygieneplatformdepartForm";
	}

	/**
	 * 保存8s检查表内容
	 */
	@RequiresPermissions(value={"sutoroa:hygieneplatformdepart:add","sutoroa:hygieneplatformdepart:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Hygieneplatformdepart hygieneplatformdepart, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, hygieneplatformdepart)){
			return form(hygieneplatformdepart, model);
		}
		if(!hygieneplatformdepart.getIsNewRecord()){//编辑表单保存
			Hygieneplatformdepart t = hygieneplatformdepartService.get(hygieneplatformdepart.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(hygieneplatformdepart, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			hygieneplatformdepartService.save(t);//保存
		}else{//新增表单保存
			hygieneplatformdepartService.save(hygieneplatformdepart);//保存
		}
		addMessage(redirectAttributes, "保存8s检查表内容成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatformdepart/?repage";
	}
	
	/**
	 * 删除8s检查表内容
	 */
	@RequiresPermissions("sutoroa:hygieneplatformdepart:del")
	@RequestMapping(value = "delete")
	public String delete(Hygieneplatformdepart hygieneplatformdepart, RedirectAttributes redirectAttributes) {
		hygieneplatformdepartService.delete(hygieneplatformdepart);
		addMessage(redirectAttributes, "删除8s检查表内容成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatformdepart/?repage";
	}
	
	/**
	 * 批量删除8s检查表内容
	 */
	@RequiresPermissions("sutoroa:hygieneplatformdepart:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hygieneplatformdepartService.delete(hygieneplatformdepartService.get(id));
		}
		addMessage(redirectAttributes, "删除8s检查表内容成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatformdepart/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sutoroa:hygieneplatformdepart:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Hygieneplatformdepart hygieneplatformdepart, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "8s检查表内容"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Hygieneplatformdepart> page = hygieneplatformdepartService.findPage(new Page<Hygieneplatformdepart>(request, response, -1), hygieneplatformdepart);
    		new ExportExcel("8s检查表内容", Hygieneplatformdepart.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出8s检查表内容记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatformdepart/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sutoroa:hygieneplatformdepart:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Hygieneplatformdepart> list = ei.getDataList(Hygieneplatformdepart.class);
			for (Hygieneplatformdepart hygieneplatformdepart : list){
				try{
					hygieneplatformdepartService.save(hygieneplatformdepart);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条8s检查表内容记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条8s检查表内容记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入8s检查表内容失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatformdepart/?repage";
    }
	
	/**
	 * 下载导入8s检查表内容数据模板
	 */
	@RequiresPermissions("sutoroa:hygieneplatformdepart:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "8s检查表内容数据导入模板.xlsx";
    		List<Hygieneplatformdepart> list = Lists.newArrayList(); 
    		new ExportExcel("8s检查表内容数据", Hygieneplatformdepart.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatformdepart/?repage";
    }
	
	
	

}