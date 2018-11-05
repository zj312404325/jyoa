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
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.OfficePostDesc;
import com.jeeplus.modules.sys.entity.Post;
import com.jeeplus.modules.sys.service.OfficePostDescService;
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
 * 职务说明Controller
 * @author yc
 * @version 2018-03-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/officePostDesc")
public class OfficePostDescController extends BaseController {

	@Autowired
	private OfficePostDescService officePostDescService;
	
	@ModelAttribute
	public OfficePostDesc get(@RequestParam(required=false) String id) {
		OfficePostDesc entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = officePostDescService.get(id);
		}
		if (entity == null){
			entity = new OfficePostDesc();
		}
		return entity;
	}
	
	/**
	 * 信息列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(OfficePostDesc officePostDesc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OfficePostDesc> page = officePostDescService.findPage(new Page<OfficePostDesc>(request, response), officePostDesc); 
		model.addAttribute("page", page);
		return "modules/sys/officePostDescList";
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
	@RequestMapping(value = "form")
	public String form(OfficePostDesc officePostDesc, Model model) {
		model.addAttribute("officePostDesc", officePostDesc);
		return "modules/sys/officePostDescForm";
	}

	@RequestMapping(value = "form2")
	public String form2(OfficePostDesc officePostDesc, Model model, HttpServletRequest request, HttpServletResponse response) {
		String officeid = request.getParameter("officeid");
		String postid = request.getParameter("postid");
		officePostDesc.setOffice(new Office(officeid));
		officePostDesc.setPost(new Post(postid));
		List<OfficePostDesc> l = officePostDescService.findList(officePostDesc);
		if(l.size() > 0){
			model.addAttribute("officePostDesc", l.get(0));
		}else{
			model.addAttribute("officePostDesc", officePostDesc);
		}


		return "modules/sys/officePostDescForm";
	}

	/**
	 * 保存信息
	 */
	@RequestMapping(value = "save")
	public String save(OfficePostDesc officePostDesc, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, officePostDesc)){
			return form(officePostDesc, model);
		}
		if(!officePostDesc.getIsNewRecord()){//编辑表单保存
			OfficePostDesc t = officePostDescService.get(officePostDesc.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(officePostDesc, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			officePostDescService.save(t);//保存
		}else{//新增表单保存
			officePostDescService.save(officePostDesc);//保存
		}
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/office/jobDescIndex";
	}
	
	/**
	 * 删除信息
	 */
	@RequestMapping(value = "delete")
	public String delete(OfficePostDesc officePostDesc, RedirectAttributes redirectAttributes) {
		officePostDescService.delete(officePostDesc);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/officePostDesc/?repage";
	}
	
	/**
	 * 批量删除信息
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			officePostDescService.delete(officePostDescService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/officePostDesc/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(OfficePostDesc officePostDesc, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OfficePostDesc> page = officePostDescService.findPage(new Page<OfficePostDesc>(request, response, -1), officePostDesc);
    		new ExportExcel("信息", OfficePostDesc.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/officePostDesc/?repage";
    }

	/**
	 * 导入Excel数据

	 */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OfficePostDesc> list = ei.getDataList(OfficePostDesc.class);
			for (OfficePostDesc officePostDesc : list){
				try{
					officePostDescService.save(officePostDesc);
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
		return "redirect:"+Global.getAdminPath()+"/sys/officePostDesc/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<OfficePostDesc> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", OfficePostDesc.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/officePostDesc/?repage";
    }
	
	
	

}