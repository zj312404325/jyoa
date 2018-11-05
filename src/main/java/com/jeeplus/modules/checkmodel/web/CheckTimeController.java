/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.web;

import java.util.Date;
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
import com.jeeplus.modules.checkmodel.entity.CheckTime;
import com.jeeplus.modules.checkmodel.service.CheckTimeService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 考核时间Controller
 * @author cqj
 * @version 2017-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/checkmodel/checkTime")
public class CheckTimeController extends BaseController {

	@Autowired
	private CheckTimeService checkTimeService;
	
	@ModelAttribute
	public CheckTime get(@RequestParam(required=false) String id) {
		CheckTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkTimeService.get(id);
		}
		if (entity == null){
			entity = new CheckTime();
		}
		return entity;
	}
	
	/**
	 * 考核时间列表页面
	 */
	@RequiresPermissions("checkmodel:checkTime:list")
	@RequestMapping(value = {"list", ""})
	public String list(CheckTime checkTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckTime> page = checkTimeService.findPage(new Page<CheckTime>(request, response), checkTime); 
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/checkTimeList";
	}

	/**
	 * 查看，增加，编辑考核时间表单页面
	 */
	@RequiresPermissions(value={"checkmodel:checkTime:view","checkmodel:checkTime:add","checkmodel:checkTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CheckTime checkTime, Model model) {
		model.addAttribute("checkTime", checkTime);
		return "modules/ehr/checkmodel/checkTimeForm";
	}

	/**
	 * 保存考核时间
	 */
	@RequiresPermissions(value={"checkmodel:checkTime:add","checkmodel:checkTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CheckTime checkTime, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, checkTime)){
			return form(checkTime, model);
		}
		if(!checkTime.getIsNewRecord()){//编辑表单保存
			CheckTime t = checkTimeService.get(checkTime.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(checkTime, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			User user=UserUtils.getUser();
			t.setUpdateDate(new Date());
			t.setUpdateusername(user.getName());
			checkTimeService.save(t);//保存
		}else{//新增表单保存
			User user=UserUtils.getUser();
			checkTime.setCreateDate(new Date());
			checkTime.setCreateusername(user.getName());
			checkTimeService.save(checkTime);//保存
		}
		addMessage(redirectAttributes, "保存考核时间成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkTime/?repage";
	}
	
	/**
	 * 删除考核时间
	 */
	@RequiresPermissions("checkmodel:checkTime:del")
	@RequestMapping(value = "delete")
	public String delete(CheckTime checkTime, RedirectAttributes redirectAttributes) {
		checkTimeService.delete(checkTime);
		addMessage(redirectAttributes, "删除考核时间成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkTime/?repage";
	}
	
	/**
	 * 批量删除考核时间
	 */
	@RequiresPermissions("checkmodel:checkTime:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			checkTimeService.delete(checkTimeService.get(id));
		}
		addMessage(redirectAttributes, "删除考核时间成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkTime/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("checkmodel:checkTime:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CheckTime checkTime, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "考核时间"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CheckTime> page = checkTimeService.findPage(new Page<CheckTime>(request, response, -1), checkTime);
    		new ExportExcel("考核时间", CheckTime.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出考核时间记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkTime/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("checkmodel:checkTime:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CheckTime> list = ei.getDataList(CheckTime.class);
			for (CheckTime checkTime : list){
				try{
					checkTimeService.save(checkTime);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条考核时间记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条考核时间记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入考核时间失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkTime/?repage";
    }
	
	/**
	 * 下载导入考核时间数据模板
	 */
	@RequiresPermissions("checkmodel:checkTime:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "考核时间数据导入模板.xlsx";
    		List<CheckTime> list = Lists.newArrayList(); 
    		new ExportExcel("考核时间数据", CheckTime.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkTime/?repage";
    }
	
	
	

}