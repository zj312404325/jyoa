/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.entity.User;
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

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.ehr.entity.Leaveaudit;
import com.jeeplus.modules.ehr.service.LeaveauditService;

/**
 * 离职审计Controller
 * @author cqj
 * @version 2018-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/ehr/leaveaudit")
public class LeaveauditController extends BaseController {

	@Autowired
	private LeaveauditService leaveauditService;
	
	@ModelAttribute
	public Leaveaudit get(@RequestParam(required=false) String id) {
		Leaveaudit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leaveauditService.get(id);
		}
		if (entity == null){
			entity = new Leaveaudit();
		}
		return entity;
	}
	
	/**
	 * 离职审计列表页面
	 */
	@RequiresPermissions("ehr:leaveaudit:list")
	@RequestMapping(value = {"list", ""})
	public String list(Leaveaudit leaveaudit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Leaveaudit> page = leaveauditService.findPage(new Page<Leaveaudit>(request, response), leaveaudit); 
		model.addAttribute("page", page);
		return "modules/ehr/leaveauditList";
	}

	/**
	 * 查看，增加，编辑离职审计表单页面
	 */
	@RequiresPermissions(value={"ehr:leaveaudit:view","ehr:leaveaudit:add","ehr:leaveaudit:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Leaveaudit leaveaudit, Model model) {
		model.addAttribute("leaveaudit", leaveaudit);
		return "modules/ehr/leaveauditForm";
	}

	/**
	 * 保存离职审计
	 */
	@RequiresPermissions(value={"ehr:leaveaudit:add","ehr:leaveaudit:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Leaveaudit leaveaudit, Model model, RedirectAttributes redirectAttributes) throws Exception{
		User user= UserUtils.getUser();
		leaveaudit.setCreateusername(user.getName());
		if (!beanValidator(model, leaveaudit)){
			return form(leaveaudit, model);
		}
		if(!leaveaudit.getIsNewRecord()){//编辑表单保存
			Leaveaudit t = leaveauditService.get(leaveaudit.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(leaveaudit, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			leaveauditService.save(t);//保存
		}else{//新增表单保存
			leaveauditService.save(leaveaudit);//保存
		}
		addMessage(redirectAttributes, "保存离职审计成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveaudit/?repage";
	}
	
	/**
	 * 删除离职审计
	 */
	@RequiresPermissions("ehr:leaveaudit:del")
	@RequestMapping(value = "delete")
	public String delete(Leaveaudit leaveaudit, RedirectAttributes redirectAttributes) {
		leaveauditService.delete(leaveaudit);
		addMessage(redirectAttributes, "删除离职审计成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveaudit/?repage";
	}
	
	/**
	 * 批量删除离职审计
	 */
	@RequiresPermissions("ehr:leaveaudit:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leaveauditService.delete(leaveauditService.get(id));
		}
		addMessage(redirectAttributes, "删除离职审计成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveaudit/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ehr:leaveaudit:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Leaveaudit leaveaudit, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "离职审计"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Leaveaudit> page = leaveauditService.findPage(new Page<Leaveaudit>(request, response, -1), leaveaudit);
    		new ExportExcel("离职审计", Leaveaudit.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出离职审计记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveaudit/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ehr:leaveaudit:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Leaveaudit> list = ei.getDataList(Leaveaudit.class);
			for (Leaveaudit leaveaudit : list){
				try{
					leaveauditService.save(leaveaudit);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条离职审计记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条离职审计记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入离职审计失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveaudit/?repage";
    }
	
	/**
	 * 下载导入离职审计数据模板
	 */
	@RequiresPermissions("ehr:leaveaudit:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "离职审计数据导入模板.xlsx";
    		List<Leaveaudit> list = Lists.newArrayList(); 
    		new ExportExcel("离职审计数据", Leaveaudit.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveaudit/?repage";
    }
	
	
	

}