/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.web;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONObject;
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
import com.jeeplus.modules.sutoroa.entity.Leavemsg;
import com.jeeplus.modules.sutoroa.service.LeavemsgService;

/**
 * 晨会留言Controller
 * @author cqj
 * @version 2018-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sutoroa/leavemsg")
public class LeavemsgController extends BaseController {

	@Autowired
	private LeavemsgService leavemsgService;
	
	@ModelAttribute
	public Leavemsg get(@RequestParam(required=false) String id) {
		Leavemsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leavemsgService.get(id);
		}
		if (entity == null){
			entity = new Leavemsg();
		}
		return entity;
	}
	
	/**
	 * 晨会留言列表页面
	 */
	@RequiresPermissions("sutoroa:leavemsg:list")
	@RequestMapping(value = {"list", ""})
	public String list(Leavemsg leavemsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Leavemsg> page = leavemsgService.findPage(new Page<Leavemsg>(request, response), leavemsg); 
		model.addAttribute("page", page);
		return "modules/sutoroa/leavemsgList";
	}

	/**
	 * 查看，增加，编辑晨会留言表单页面
	 */
	@RequiresPermissions(value={"sutoroa:leavemsg:view","sutoroa:leavemsg:add","sutoroa:leavemsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Leavemsg leavemsg, Model model) {
		model.addAttribute("leavemsg", leavemsg);
		return "modules/sutoroa/leavemsgForm";
	}

	/**
	 * 保存晨会留言
	 */
	@RequiresPermissions(value={"sutoroa:leavemsg:add","sutoroa:leavemsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Leavemsg leavemsg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, leavemsg)){
			return form(leavemsg, model);
		}
		if(!leavemsg.getIsNewRecord()){//编辑表单保存
			Leavemsg t = leavemsgService.get(leavemsg.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(leavemsg, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			leavemsgService.save(t);//保存
		}else{//新增表单保存
			leavemsgService.save(leavemsg);//保存
		}
		addMessage(redirectAttributes, "保存晨会留言成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/leavemsg/?repage";
	}
	
	/**
	 * 删除晨会留言
	 */
	@RequiresPermissions("sutoroa:leavemsg:del")
	@RequestMapping(value = "delete")
	public String delete(Leavemsg leavemsg, RedirectAttributes redirectAttributes) {
		leavemsgService.delete(leavemsg);
		addMessage(redirectAttributes, "删除晨会留言成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/leavemsg/?repage";
	}
	
	/**
	 * 批量删除晨会留言
	 */
	@RequiresPermissions("sutoroa:leavemsg:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leavemsgService.delete(leavemsgService.get(id));
		}
		addMessage(redirectAttributes, "删除晨会留言成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/leavemsg/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sutoroa:leavemsg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Leavemsg leavemsg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "晨会留言"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Leavemsg> page = leavemsgService.findPage(new Page<Leavemsg>(request, response, -1), leavemsg);
    		new ExportExcel("晨会留言", Leavemsg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出晨会留言记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/leavemsg/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sutoroa:leavemsg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Leavemsg> list = ei.getDataList(Leavemsg.class);
			for (Leavemsg leavemsg : list){
				try{
					leavemsgService.save(leavemsg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条晨会留言记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条晨会留言记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入晨会留言失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/leavemsg/?repage";
    }
	
	/**
	 * 下载导入晨会留言数据模板
	 */
	@RequiresPermissions("sutoroa:leavemsg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "晨会留言数据导入模板.xlsx";
    		List<Leavemsg> list = Lists.newArrayList(); 
    		new ExportExcel("晨会留言数据", Leavemsg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/leavemsg/?repage";
    }

	@RequestMapping(value = "saveAjax")
	public void saveAjax(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter writer =response.getWriter();
		Map mapInfo=new HashMap<String, String>();
		String conferenceid=request.getParameter("conferenceid");
		if(!FormatUtil.isNoEmpty(conferenceid)){
			mapInfo.put("status", "n");
			mapInfo.put("info", "晨会ID不能为空！");
			JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
			String a =  jsonstr.toString();
			writer.write(a);
			writer.close();
			return;
		}
		String content=request.getParameter("content");
		if(!FormatUtil.isNoEmpty(content)){
			mapInfo.put("status", "n");
			mapInfo.put("info", "内容不能为空！");
			JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
			String a =  jsonstr.toString();
			writer.write(a);
			writer.close();
			return;
		}
		Leavemsg leavemsg=new Leavemsg();
		leavemsg.setConferenceid(conferenceid);
		leavemsg.setContent(content);
		leavemsg.setCreateBy(UserUtils.getUser());
		leavemsg.setCreateusername(UserUtils.getUser().getName());
		leavemsg.setCreateDate(new Date());
		try {
			leavemsgService.save(leavemsg);//保存
			mapInfo.put("status", "y");
			mapInfo.put("info", "提交成功！");
			JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
			String a =  jsonstr.toString();
			writer.write(a);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			mapInfo.put("status", "n");
			mapInfo.put("info", "提交失败！");
			JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
			String a =  jsonstr.toString();
			writer.write(a);
			writer.close();
			return;
		}
	}
}