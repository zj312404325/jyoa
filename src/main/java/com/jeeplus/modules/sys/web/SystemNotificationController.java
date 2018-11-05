/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.websocket.onchat.ChatServerPool;
import com.jeeplus.modules.sys.entity.SystemNotification;
import com.jeeplus.modules.sys.service.SystemNotificationService;
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
import java.util.Collection;
import java.util.List;

/**
 * 系统通知Controller
 * @author yc
 * @version 2018-03-01
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/systemNotification")
public class SystemNotificationController extends BaseController {

	@Autowired
	private SystemNotificationService systemNotificationService;
	
	@ModelAttribute
	public SystemNotification get(@RequestParam(required=false) String id) {
		SystemNotification entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = systemNotificationService.get(id);
		}
		if (entity == null){
			entity = new SystemNotification();
		}
		return entity;
	}
	
	/**
	 * 信息列表页面
	 */
//	@RequiresPermissions("sys:systemNotification:list")
	@RequestMapping(value = {"list", ""})
	public String list(SystemNotification systemNotification, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SystemNotification> page = systemNotificationService.findPage(new Page<SystemNotification>(request, response), systemNotification); 
		model.addAttribute("page", page);
		return "modules/sys/systemNotificationList";
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
//	@RequiresPermissions(value={"sys:systemNotification:view","sys:systemNotification:add","sys:systemNotification:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SystemNotification systemNotification, Model model) {
		List<SystemNotification> list = systemNotificationService.findList(new SystemNotification());
		if(list.size()>0){
			systemNotification = list.get(0);
		}
		model.addAttribute("systemNotification", systemNotification);
		return "modules/sys/systemNotificationForm";
	}

	/**
	 * 保存信息
	 */
//	@RequiresPermissions(value={"sys:systemNotification:add","sys:systemNotification:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SystemNotification systemNotification, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, systemNotification)){
			return form(systemNotification, model);
		}
		if(!systemNotification.getIsNewRecord()){//编辑表单保存
			SystemNotification t = systemNotificationService.get(systemNotification.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(systemNotification, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			systemNotificationService.save(t);//保存
		}else{//新增表单保存
			systemNotificationService.save(systemNotification);//保存
		}

		final String systemNotificationMsg = systemNotification.getContent();
		Thread t = new Thread(new Runnable(){
			public void run(){
				try {
					Collection<String> onlineUsers = ChatServerPool.getOnlineUser();
					ChatServerPool.sendMessage("_online_all_systemnotification_"+systemNotificationMsg);//通知所有用户更新在线信息
				} catch (Exception e) {
					e.printStackTrace();
				}
			}});
		t.start();


		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/systemNotification/form";
	}
	
	/**
	 * 删除信息
	 */
//	@RequiresPermissions("sys:systemNotification:del")
	@RequestMapping(value = "delete")
	public String delete(SystemNotification systemNotification, RedirectAttributes redirectAttributes) {
		systemNotificationService.delete(systemNotification);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/systemNotification/?repage";
	}
	
	/**
	 * 批量删除信息
	 */
	@RequiresPermissions("sys:systemNotification:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			systemNotificationService.delete(systemNotificationService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/systemNotification/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:systemNotification:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SystemNotification systemNotification, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SystemNotification> page = systemNotificationService.findPage(new Page<SystemNotification>(request, response, -1), systemNotification);
    		new ExportExcel("信息", SystemNotification.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemNotification/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:systemNotification:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SystemNotification> list = ei.getDataList(SystemNotification.class);
			for (SystemNotification systemNotification : list){
				try{
					systemNotificationService.save(systemNotification);
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
		return "redirect:"+Global.getAdminPath()+"/sys/systemNotification/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
	@RequiresPermissions("sys:systemNotification:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<SystemNotification> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", SystemNotification.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemNotification/?repage";
    }
	
	
	

}