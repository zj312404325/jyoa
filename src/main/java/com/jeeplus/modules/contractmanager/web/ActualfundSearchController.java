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
import com.jeeplus.modules.contractmanager.entity.ActualfundSearch;
import com.jeeplus.modules.contractmanager.service.ActualfundSearchService;

/**
 * 合同管理-实际资金Controller
 * @author cqj
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/contractmanager/actualfundSearch")
public class ActualfundSearchController extends BaseController {

	@Autowired
	private ActualfundSearchService actualfundSearchService;
	
	@ModelAttribute
	public ActualfundSearch get(@RequestParam(required=false) String id) {
		ActualfundSearch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = actualfundSearchService.get(id);
		}
		if (entity == null){
			entity = new ActualfundSearch();
		}
		return entity;
	}
	
	/**
	 * 实际资金列表页面
	 */
	@RequiresPermissions("contractmanager:actualfundSearch:list")
	@RequestMapping(value = {"list", ""})
	public String list(ActualfundSearch actualfundSearch, HttpServletRequest request, HttpServletResponse response, Model model) {
		String fundnature=request.getParameter("fundnature");
		actualfundSearch.setFundnatureid(fundnature);//收款/付款标记
		actualfundSearch.setCreateBy(UserUtils.getUser());
		Page<ActualfundSearch> page = actualfundSearchService.findPage(new Page<ActualfundSearch>(request, response), actualfundSearch); 
		model.addAttribute("page", page);
		if(fundnature.equals("2")){//计划付款
			return "modules/filemanager/contractmanager/actualfundPaySearchList";
		}else{//计划收款
			return "modules/filemanager/contractmanager/actualfundSearchList";
		}
	}

	/**
	 * 查看，增加，编辑实际资金表单页面
	 */
	@RequiresPermissions(value={"contractmanager:actualfundSearch:view","contractmanager:actualfundSearch:add","contractmanager:actualfundSearch:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ActualfundSearch actualfundSearch, Model model) {
		model.addAttribute("actualfundSearch", actualfundSearch);
		return "modules/filemanager/contractmanager/actualfundSearchForm";
	}

	/**
	 * 保存实际资金
	 */
	@RequiresPermissions(value={"contractmanager:actualfundSearch:add","contractmanager:actualfundSearch:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ActualfundSearch actualfundSearch, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, actualfundSearch)){
			return form(actualfundSearch, model);
		}
		if(!actualfundSearch.getIsNewRecord()){//编辑表单保存
			ActualfundSearch t = actualfundSearchService.get(actualfundSearch.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(actualfundSearch, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			actualfundSearchService.save(t);//保存
		}else{//新增表单保存
			actualfundSearchService.save(actualfundSearch);//保存
		}
		addMessage(redirectAttributes, "保存实际资金成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/actualfundSearch/?repage";
	}
	
	/**
	 * 删除实际资金
	 */
	@RequiresPermissions("contractmanager:actualfundSearch:del")
	@RequestMapping(value = "delete")
	public String delete(ActualfundSearch actualfundSearch, RedirectAttributes redirectAttributes) {
		actualfundSearchService.delete(actualfundSearch);
		addMessage(redirectAttributes, "删除实际资金成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/actualfundSearch/?repage";
	}
	
	/**
	 * 批量删除实际资金
	 */
	@RequiresPermissions("contractmanager:actualfundSearch:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			actualfundSearchService.delete(actualfundSearchService.get(id));
		}
		addMessage(redirectAttributes, "删除实际资金成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/actualfundSearch/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("contractmanager:actualfundSearch:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ActualfundSearch actualfundSearch, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "实际资金"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ActualfundSearch> page = actualfundSearchService.findPage(new Page<ActualfundSearch>(request, response, -1), actualfundSearch);
    		new ExportExcel("实际资金", ActualfundSearch.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出实际资金记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/actualfundSearch/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("contractmanager:actualfundSearch:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ActualfundSearch> list = ei.getDataList(ActualfundSearch.class);
			for (ActualfundSearch actualfundSearch : list){
				try{
					actualfundSearchService.save(actualfundSearch);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条实际资金记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条实际资金记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入实际资金失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/actualfundSearch/?repage";
    }
	
	/**
	 * 下载导入实际资金数据模板
	 */
	@RequiresPermissions("contractmanager:actualfundSearch:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "实际资金数据导入模板.xlsx";
    		List<ActualfundSearch> list = Lists.newArrayList(); 
    		new ExportExcel("实际资金数据", ActualfundSearch.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/actualfundSearch/?repage";
    }
	
	
	

}