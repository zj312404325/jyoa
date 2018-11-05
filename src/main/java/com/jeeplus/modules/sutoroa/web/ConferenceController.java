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
import com.jeeplus.modules.sutoroa.entity.Conference;
import com.jeeplus.modules.sutoroa.service.ConferenceService;

/**
 * 每日团队风采Controller
 * @author cqj
 * @version 2018-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/conference")
public class ConferenceController extends BaseController {

	@Autowired
	private ConferenceService conferenceService;
	
	@ModelAttribute
	public Conference get(@RequestParam(required=false) String id) {
		Conference entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = conferenceService.get(id);
		}
		if (entity == null){
			entity = new Conference();
		}
		return entity;
	}
	
	/**
	 * 每日团队风采列表页面
	 */
	@RequiresPermissions("oa:conference:list")
	@RequestMapping(value = {"list", ""})
	public String list(Conference conference, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(FormatUtil.isNoEmpty(request.getParameter("category"))){
			conference.setCategory(FormatUtil.toInteger(request.getParameter("category")));
		}
		Page<Conference> page = conferenceService.findPage(new Page<Conference>(request, response), conference);
		model.addAttribute("page", page);
		model.addAttribute("conference", conference);
		return "modules/sutoroa/conferenceList";
	}

	/**
	 * 查看，增加，编辑每日团队风采表单页面
	 */
	@RequiresPermissions(value={"oa:conference:view","oa:conference:add","oa:conference:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Conference conference, Model model, HttpServletRequest request) {
		if(!FormatUtil.isNoEmpty(conference.getId())){
			if(FormatUtil.isNoEmpty(request.getParameter("category"))){
				conference.setCategory(FormatUtil.toInteger(request.getParameter("category")));
			}else{
				conference.setCategory(0);
			}
		}
		if(FormatUtil.isNoEmpty(conference)&&FormatUtil.isNoEmpty(conference.getTeampic())){
			conference.setFilename(FormatUtil.getfilename(conference.getTeampic()));
		}
		model.addAttribute("conference", conference);
		if(FormatUtil.isNoEmpty(request.getParameter("type"))){//查看
			return "modules/sutoroa/conferenceView";
		}
		return "modules/sutoroa/conferenceForm";
	}

	/**
	 * 保存每日团队风采
	 */
	@RequiresPermissions(value={"oa:conference:add","oa:conference:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Conference conference, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception{
		if (!beanValidator(model, conference)){
			return form(conference, model,request);
		}
		if(!conference.getIsNewRecord()){//编辑表单保存
			Conference t = conferenceService.get(conference.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(conference, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			conferenceService.save(t);//保存
		}else{//新增表单保存
			conferenceService.save(conference);//保存
		}
		addMessage(redirectAttributes, "保存每日团队风采成功");
		return "redirect:"+Global.getAdminPath()+"/oa/conference/?repage&category="+conference.getCategory();
	}
	
	/**
	 * 删除每日团队风采
	 */
	@RequiresPermissions("oa:conference:del")
	@RequestMapping(value = "delete")
	public String delete(Conference conference, RedirectAttributes redirectAttributes) {
		conferenceService.delete(conference);
		addMessage(redirectAttributes, "删除每日团队风采成功");
		return "redirect:"+Global.getAdminPath()+"/oa/conference/?repage";
	}
	
	/**
	 * 批量删除每日团队风采
	 */
	@RequiresPermissions("oa:conference:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			conferenceService.delete(conferenceService.get(id));
		}
		addMessage(redirectAttributes, "删除每日团队风采成功");
		return "redirect:"+Global.getAdminPath()+"/oa/conference/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("oa:conference:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Conference conference, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "每日团队风采"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Conference> page = conferenceService.findPage(new Page<Conference>(request, response, -1), conference);
    		new ExportExcel("每日团队风采", Conference.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出每日团队风采记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/oa/conference/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("oa:conference:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Conference> list = ei.getDataList(Conference.class);
			for (Conference conference : list){
				try{
					conferenceService.save(conference);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条每日团队风采记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条每日团队风采记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入每日团队风采失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/oa/conference/?repage";
    }
	
	/**
	 * 下载导入每日团队风采数据模板
	 */
	@RequiresPermissions("oa:conference:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "每日团队风采数据导入模板.xlsx";
    		List<Conference> list = Lists.newArrayList(); 
    		new ExportExcel("每日团队风采数据", Conference.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/oa/conference/?repage";
    }
	
	
	

}