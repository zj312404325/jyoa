/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.contractmanager.entity.InvoiceSearch;
import com.jeeplus.modules.contractmanager.service.InvoiceSearchService;

/**
 * 合同管理-发票查询Controller
 * @author cqj
 * @version 2017-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/contractmanager/invoiceSearch")
public class InvoiceSearchController extends BaseController {

	@Autowired
	private InvoiceSearchService invoiceSearchService;
	
	@ModelAttribute
	public InvoiceSearch get(@RequestParam(required=false) String id) {
		InvoiceSearch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = invoiceSearchService.get(id);
		}
		if (entity == null){
			entity = new InvoiceSearch();
		}
		return entity;
	}
	
	/**
	 * 发票查询列表页面
	 */
	@RequiresPermissions("contractmanager:invoiceSearch:list")
	@RequestMapping(value = {"list", ""})
	public String list(InvoiceSearch invoiceSearch, HttpServletRequest request, HttpServletResponse response, Model model) {
		invoiceSearch.setCreateBy(UserUtils.getUser());
		Page<InvoiceSearch> page = invoiceSearchService.findPage(new Page<InvoiceSearch>(request, response), invoiceSearch); 
		model.addAttribute("page", page);
		return "modules/filemanager/contractmanager/invoiceSearchList";
	}

	/**
	 * 查看，增加，编辑发票查询表单页面
	 */
	@RequiresPermissions(value={"contractmanager:invoiceSearch:view","contractmanager:invoiceSearch:add","contractmanager:invoiceSearch:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(InvoiceSearch invoiceSearch, Model model) {
		model.addAttribute("invoiceSearch", invoiceSearch);
		return "modules/filemanager/contractmanager/invoiceSearchForm";
	}

	/**
	 * 保存发票查询
	 */
	@RequiresPermissions(value={"contractmanager:invoiceSearch:add","contractmanager:invoiceSearch:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(InvoiceSearch invoiceSearch, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, invoiceSearch)){
			return form(invoiceSearch, model);
		}
		if(!invoiceSearch.getIsNewRecord()){//编辑表单保存
			InvoiceSearch t = invoiceSearchService.get(invoiceSearch.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(invoiceSearch, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			invoiceSearchService.save(t);//保存
		}else{//新增表单保存
			invoiceSearchService.save(invoiceSearch);//保存
		}
		addMessage(redirectAttributes, "保存发票查询成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/invoiceSearch/?repage";
	}
	
	/**
	 * 删除发票查询
	 */
	@RequiresPermissions("contractmanager:invoiceSearch:del")
	@RequestMapping(value = "delete")
	public String delete(InvoiceSearch invoiceSearch, RedirectAttributes redirectAttributes) {
		invoiceSearchService.delete(invoiceSearch);
		addMessage(redirectAttributes, "删除发票查询成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/invoiceSearch/?repage";
	}
	
	/**
	 * 批量删除发票查询
	 */
	@RequiresPermissions("contractmanager:invoiceSearch:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			invoiceSearchService.delete(invoiceSearchService.get(id));
		}
		addMessage(redirectAttributes, "删除发票查询成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/invoiceSearch/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("contractmanager:invoiceSearch:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(InvoiceSearch invoiceSearch, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "发票查询"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<InvoiceSearch> page = invoiceSearchService.findPage(new Page<InvoiceSearch>(request, response, -1), invoiceSearch);
    		new ExportExcel("发票查询", InvoiceSearch.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出发票查询记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/invoiceSearch/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("contractmanager:invoiceSearch:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<InvoiceSearch> list = ei.getDataList(InvoiceSearch.class);
			for (InvoiceSearch invoiceSearch : list){
				try{
					invoiceSearchService.save(invoiceSearch);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条发票查询记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条发票查询记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入发票查询失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/invoiceSearch/?repage";
    }
	
	/**
	 * 下载导入发票查询数据模板
	 */
	@RequiresPermissions("contractmanager:invoiceSearch:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "发票查询数据导入模板.xlsx";
    		List<InvoiceSearch> list = Lists.newArrayList(); 
    		new ExportExcel("发票查询数据", InvoiceSearch.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/invoiceSearch/?repage";
    }
	
	
	

}