/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jpush.web;

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
import com.jeeplus.modules.jpush.entity.Jpushregister;
import com.jeeplus.modules.jpush.service.JpushregisterService;

/**
 * 极光推送注册用户信息表Controller
 * @author cqj
 * @version 2018-04-10
 */
@Controller
@RequestMapping(value = "${adminPath}/jpush/jpushregister")
public class JpushregisterController extends BaseController {

	@Autowired
	private JpushregisterService jpushregisterService;
	
	@ModelAttribute
	public Jpushregister get(@RequestParam(required=false) String id) {
		Jpushregister entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jpushregisterService.get(id);
		}
		if (entity == null){
			entity = new Jpushregister();
		}
		return entity;
	}
	
	/**
	 * 极光推送注册用户信息表列表页面
	 */
	@RequiresPermissions("jpush:jpushregister:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jpushregister jpushregister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jpushregister> page = jpushregisterService.findPage(new Page<Jpushregister>(request, response), jpushregister); 
		model.addAttribute("page", page);
		return "modules/jpush/jpushregisterList";
	}

	/**
	 * 查看，增加，编辑极光推送注册用户信息表表单页面
	 */
	@RequiresPermissions(value={"jpush:jpushregister:view","jpush:jpushregister:add","jpush:jpushregister:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Jpushregister jpushregister, Model model) {
		model.addAttribute("jpushregister", jpushregister);
		return "modules/jpush/jpushregisterForm";
	}

	/**
	 * 保存极光推送注册用户信息表
	 */
	@RequiresPermissions(value={"jpush:jpushregister:add","jpush:jpushregister:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Jpushregister jpushregister, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, jpushregister)){
			return form(jpushregister, model);
		}
		if(!jpushregister.getIsNewRecord()){//编辑表单保存
			Jpushregister t = jpushregisterService.get(jpushregister.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(jpushregister, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			jpushregisterService.save(t);//保存
		}else{//新增表单保存
			jpushregisterService.save(jpushregister);//保存
		}
		addMessage(redirectAttributes, "保存极光推送注册用户信息表成功");
		return "redirect:"+Global.getAdminPath()+"/jpush/jpushregister/?repage";
	}
	
	/**
	 * 删除极光推送注册用户信息表
	 */
	@RequiresPermissions("jpush:jpushregister:del")
	@RequestMapping(value = "delete")
	public String delete(Jpushregister jpushregister, RedirectAttributes redirectAttributes) {
		jpushregisterService.delete(jpushregister);
		addMessage(redirectAttributes, "删除极光推送注册用户信息表成功");
		return "redirect:"+Global.getAdminPath()+"/jpush/jpushregister/?repage";
	}
	
	/**
	 * 批量删除极光推送注册用户信息表
	 */
	@RequiresPermissions("jpush:jpushregister:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jpushregisterService.delete(jpushregisterService.get(id));
		}
		addMessage(redirectAttributes, "删除极光推送注册用户信息表成功");
		return "redirect:"+Global.getAdminPath()+"/jpush/jpushregister/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("jpush:jpushregister:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Jpushregister jpushregister, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "极光推送注册用户信息表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jpushregister> page = jpushregisterService.findPage(new Page<Jpushregister>(request, response, -1), jpushregister);
    		new ExportExcel("极光推送注册用户信息表", Jpushregister.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出极光推送注册用户信息表记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/jpush/jpushregister/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("jpush:jpushregister:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jpushregister> list = ei.getDataList(Jpushregister.class);
			for (Jpushregister jpushregister : list){
				try{
					jpushregisterService.save(jpushregister);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条极光推送注册用户信息表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条极光推送注册用户信息表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入极光推送注册用户信息表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/jpush/jpushregister/?repage";
    }
	
	/**
	 * 下载导入极光推送注册用户信息表数据模板
	 */
	@RequiresPermissions("jpush:jpushregister:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "极光推送注册用户信息表数据导入模板.xlsx";
    		List<Jpushregister> list = Lists.newArrayList(); 
    		new ExportExcel("极光推送注册用户信息表数据", Jpushregister.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/jpush/jpushregister/?repage";
    }
	
	
	

}