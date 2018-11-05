/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.contractmanager.entity.ContractManager;
import com.jeeplus.modules.contractmanager.service.ContractManagerService;
import com.jeeplus.modules.filemanagement.entity.ProjectManage;
import com.jeeplus.modules.filemanagement.service.ProjectManageService;
import com.jeeplus.modules.sys.utils.UserUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 项目管理Controller
 * @author cqj
 * @version 2017-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/filemanagement/projectManage")
public class ProjectManageController extends BaseController {

	@Autowired
	private ProjectManageService projectManageService;
	@Autowired
	private ContractManagerService contractManagerService;
	
	@ModelAttribute
	public ProjectManage get(@RequestParam(required=false) String id) {
		ProjectManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectManageService.get(id);
		}
		if (entity == null){
			entity = new ProjectManage();
		}
		return entity;
	}
	
	/**
	 * 项目管理列表页面
	 */
	@RequiresPermissions("filemanagement:projectManage:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectManage projectManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectManage.setCreateBy(UserUtils.getUser());
		Page<ProjectManage> page = projectManageService.findPage(new Page<ProjectManage>(request, response), projectManage); 
		model.addAttribute("page", page);
		model.addAttribute("projectManage", projectManage);
		return "modules/filemanager/contractmanager/projectManageList";
	}
	
	@RequestMapping(value = {"selectlist"})
	public String selectlist(ProjectManage projectManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectManage.setCreateBy(UserUtils.getUser());
		Page<ProjectManage> pageParam = new Page<ProjectManage>(request, response);
		pageParam.setPageSize(5);
		Page<ProjectManage> page = projectManageService.findPage(pageParam, projectManage); 
		model.addAttribute("page", page);
		model.addAttribute("projectManage", projectManage);
		return "modules/filemanager/contractmanager/projectManageSelectList";
	}

	/**
	 * 查看，增加，编辑项目管理表单页面
	 */
	@RequiresPermissions(value={"filemanagement:projectManage:view","filemanagement:projectManage:add","filemanagement:projectManage:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProjectManage projectManage, Model model) {
		model.addAttribute("projectManage", projectManage);
		return "modules/filemanager/contractmanager/projectManageForm";
	}

	@RequiresPermissions(value={"filemanagement:projectManage:view","filemanagement:projectManage:add","filemanagement:projectManage:edit"},logical=Logical.OR)
	@RequestMapping(value = "authorform")
	public String authorform(ProjectManage projectManage, Model model) {
		model.addAttribute("projectManage", projectManage);
		return "modules/filemanager/contractmanager/projectManageAuthorForm";
	}
	
	@RequiresPermissions(value={"filemanagement:projectManage:view","filemanagement:projectManage:add","filemanagement:projectManage:edit"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(ProjectManage projectManage, Model model) {
		model.addAttribute("projectManage", projectManage);
		
		ContractManager m = new ContractManager();
		m.setProjectid(projectManage.getId());
		List<ContractManager> list = contractManagerService.findList(m);
		model.addAttribute("contractManagerList", list);
		
		return "modules/filemanager/contractmanager/projectManageView";
	}

	/**
	 * 保存项目管理
	 */
	@RequiresPermissions(value={"filemanagement:projectManage:add","filemanagement:projectManage:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProjectManage projectManage, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, projectManage)){
			return form(projectManage, model);
		}
		if(!projectManage.getIsNewRecord()){//编辑表单保存
			ProjectManage t = projectManageService.get(projectManage.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(projectManage, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			projectManageService.save(t);//保存
		}else{//新增表单保存
			//projectManage.setState("1");
			projectManageService.save(projectManage);//保存
		}
		addMessage(redirectAttributes, "保存项目管理成功");
		return "redirect:"+Global.getAdminPath()+"/filemanagement/projectManage/?repage";
	}
	
	/**
	 * 删除项目管理
	 */
	@RequiresPermissions("filemanagement:projectManage:del")
	@RequestMapping(value = "delete")
	public String delete(ProjectManage projectManage, RedirectAttributes redirectAttributes) {
		projectManageService.delete(projectManage);
		addMessage(redirectAttributes, "删除项目管理成功");
		return "redirect:"+Global.getAdminPath()+"/filemanagement/projectManage/?repage";
	}
	
	/**
	 * 批量删除项目管理
	 */
	@RequiresPermissions("filemanagement:projectManage:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			projectManageService.delete(projectManageService.get(id));
		}
		addMessage(redirectAttributes, "删除项目管理成功");
		return "redirect:"+Global.getAdminPath()+"/filemanagement/projectManage/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("filemanagement:projectManage:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ProjectManage projectManage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProjectManage> page = projectManageService.findPage(new Page<ProjectManage>(request, response, -1), projectManage);
    		new ExportExcel("项目管理", ProjectManage.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出项目管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/filemanagement/projectManage/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("filemanagement:projectManage:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProjectManage> list = ei.getDataList(ProjectManage.class);
			for (ProjectManage projectManage : list){
				try{
					projectManageService.save(projectManage);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条项目管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条项目管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入项目管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/filemanagement/projectManage/?repage";
    }
	
	/**
	 * 下载导入项目管理数据模板
	 */
	@RequiresPermissions("filemanagement:projectManage:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目管理数据导入模板.xlsx";
    		List<ProjectManage> list = Lists.newArrayList(); 
    		new ExportExcel("项目管理数据", ProjectManage.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/filemanagement/projectManage/?repage";
    }
	
	
	

}