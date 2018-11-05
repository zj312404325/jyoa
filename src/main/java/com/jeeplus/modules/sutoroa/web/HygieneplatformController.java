/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.modules.sutoroa.entity.Attachment;
import com.jeeplus.modules.sutoroa.entity.Hygieneplatformdepart;
import net.sf.json.JSONArray;
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
import com.jeeplus.modules.sutoroa.entity.Hygieneplatform;
import com.jeeplus.modules.sutoroa.service.HygieneplatformService;

/**
 * 8s检查表Controller
 * @author cqj
 * @version 2018-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/hygieneplatform")
public class HygieneplatformController extends BaseController {

	@Autowired
	private HygieneplatformService hygieneplatformService;
	
	@ModelAttribute
	public Hygieneplatform get(@RequestParam(required=false) String id) {
		Hygieneplatform entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hygieneplatformService.get(id);
		}
		if (entity == null){
			entity = new Hygieneplatform();
		}
		return entity;
	}
	
	/**
	 * 8s检查表列表页面
	 */
	@RequiresPermissions("oa:hygieneplatform:list")
	@RequestMapping(value = {"list", ""})
	public String list(Hygieneplatform hygieneplatform, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Hygieneplatform> page = hygieneplatformService.findPage(new Page<Hygieneplatform>(request, response), hygieneplatform); 
		model.addAttribute("page", page);
		return "modules/sutoroa/hygieneplatformList";
	}

	/**
	 * 查看，增加，编辑8s检查表表单页面
	 */
	@RequiresPermissions(value={"oa:hygieneplatform:view","oa:hygieneplatform:add","oa:hygieneplatform:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Hygieneplatform hygieneplatform, Model model,HttpServletRequest request) {
		request.setAttribute("type",request.getParameter("type"));//type=0 查看
		model.addAttribute("hygieneplatform", hygieneplatform);
		return "modules/sutoroa/hygieneplatformForm";
	}

	/**
	 * 保存8s检查表
	 */
	@RequiresPermissions(value={"oa:hygieneplatform:add","oa:hygieneplatform:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Hygieneplatform hygieneplatform, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, hygieneplatform)){
			return form(hygieneplatform, model,request);
		}
		//明细
		//插入明细
		String detailJson= request.getParameter("detailJson");
		if(FormatUtil.isNoEmpty(detailJson)){
			detailJson=detailJson.replace("&quot;", "\"");
			List<Hygieneplatformdepart> hygieneplatformdepartList=(List<Hygieneplatformdepart>) JSONArray.toCollection(JSONArray.fromObject(detailJson),Hygieneplatformdepart.class);
			hygieneplatform.setHygieneplatformdepartList(hygieneplatformdepartList);
		}
		//附件
		String attachJson= request.getParameter("attachJson");
		if(FormatUtil.isNoEmpty(attachJson)){
			attachJson=attachJson.replace("&quot;", "\"");
			List<Attachment> attachmentList=(List<Attachment>) JSONArray.toCollection(JSONArray.fromObject(attachJson),Attachment.class);
			hygieneplatform.setAttachmentList(attachmentList);
		}
		if(!hygieneplatform.getIsNewRecord()){//编辑表单保存
			Hygieneplatform t = hygieneplatformService.get(hygieneplatform.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(hygieneplatform, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			hygieneplatformService.save(t);//保存
		}else{//新增表单保存
			hygieneplatformService.save(hygieneplatform);//保存
		}
		addMessage(redirectAttributes, "保存8s检查表成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatform/?repage";
	}
	
	/**
	 * 删除8s检查表
	 */
	@RequiresPermissions("oa:hygieneplatform:del")
	@RequestMapping(value = "delete")
	public String delete(Hygieneplatform hygieneplatform, RedirectAttributes redirectAttributes) {
		hygieneplatformService.delete(hygieneplatform);
		addMessage(redirectAttributes, "删除8s检查表成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatform/?repage";
	}
	
	/**
	 * 批量删除8s检查表
	 */
	@RequiresPermissions("oa:hygieneplatform:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hygieneplatformService.delete(hygieneplatformService.get(id));
		}
		addMessage(redirectAttributes, "删除8s检查表成功");
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatform/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("oa:hygieneplatform:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Hygieneplatform hygieneplatform, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "8s检查表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Hygieneplatform> page = hygieneplatformService.findPage(new Page<Hygieneplatform>(request, response, -1), hygieneplatform);
    		new ExportExcel("8s检查表", Hygieneplatform.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出8s检查表记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatform/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("oa:hygieneplatform:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Hygieneplatform> list = ei.getDataList(Hygieneplatform.class);
			for (Hygieneplatform hygieneplatform : list){
				try{
					hygieneplatformService.save(hygieneplatform);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条8s检查表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条8s检查表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入8s检查表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatform/?repage";
    }
	
	/**
	 * 下载导入8s检查表数据模板
	 */
	@RequiresPermissions("oa:hygieneplatform:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "8s检查表数据导入模板.xlsx";
    		List<Hygieneplatform> list = Lists.newArrayList(); 
    		new ExportExcel("8s检查表数据", Hygieneplatform.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sutoroa/hygieneplatform/?repage";
    }
	
	
	

}