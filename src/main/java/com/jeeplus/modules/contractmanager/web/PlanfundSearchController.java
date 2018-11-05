/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.contractmanager.dao.ContractActualFundsDao;
import com.jeeplus.modules.contractmanager.entity.PlanfundSearch;
import com.jeeplus.modules.contractmanager.service.PlanfundSearchService;

/**
 * 合同管理-计划资金查询Controller
 * @author cqj
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/contractmanager/planfundSearch")
public class PlanfundSearchController extends BaseController {

	@Autowired
	private PlanfundSearchService planfundSearchService;
	@Autowired
	private ContractActualFundsDao contractActualFundsDao;
	
	@ModelAttribute
	public PlanfundSearch get(@RequestParam(required=false) String id) {
		PlanfundSearch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = planfundSearchService.get(id);
		}
		if (entity == null){
			entity = new PlanfundSearch();
		}
		return entity;
	}
	
	/**
	 * 计划资金查询列表页面
	 */
	@RequiresPermissions("contractmanager:planfundSearch:list")
	@RequestMapping(value = {"list", ""})
	public String list(PlanfundSearch planfundSearch, HttpServletRequest request, HttpServletResponse response, Model model) {
		String fundnature=request.getParameter("fundnature");
		planfundSearch.setFundnatureid(fundnature);//收款/付款标记
		planfundSearch.setCreateBy(UserUtils.getUser());
		Page<PlanfundSearch> page = planfundSearchService.findPage(new Page<PlanfundSearch>(request, response), planfundSearch); 
		//数据处理
		Map map=null;
		for (PlanfundSearch ps : page.getList()) {
			map=new HashMap<String, Object>();
			map.put("id", ps.getContractid());
			map.put("DEL_FLAG_NORMAL", "0");
			Map resultMap=contractActualFundsDao.findSumMoneyByContractId(map);
			if(FormatUtil.isNoEmpty(resultMap)&&FormatUtil.isNoEmpty(resultMap.get("summoney"))){
				//余额
				Double summoney=FormatUtil.toDouble(resultMap.get("summoney"));//实际资金总额
				Double money=FormatUtil.toDouble(ps.getMoney());
				String balance=(money-summoney)+"";
				ps.setBalance(balance);
				//完成比例	
				Double completerate=summoney/money;
				ps.setCompleterate(FormatUtil.formatDouble(completerate*100, 2)+"%");
				//完成金额
				ps.setCompletemoney(summoney+"");
			}else{
				ps.setBalance(ps.getMoney());
				ps.setCompleterate("0%");
				ps.setCompletemoney("0");
			}
			//逾期
			if(FormatUtil.isNoEmpty(ps.getCompletedate())){
				if(FormatUtil.isNoEmpty(ps.getPlancompletiondate())){
					int days= FormatUtil.daysBetween(ps.getPlancompletiondate(), ps.getCompletedate());
					ps.setOverdue(days+"");
				}
			}else{
				if(FormatUtil.isNoEmpty(ps.getPlancompletiondate())){
					if(FormatUtil.daysBetween(new Date(),ps.getPlancompletiondate()) < 0){
						int days= FormatUtil.daysBetween(ps.getPlancompletiondate(), new Date());
						ps.setOverdue(days+"");
					}else{
						ps.setOverdue("0");
					}

				}
			}
		}
		model.addAttribute("page", page);
		if(fundnature.equals("2")){//计划付款
			return "modules/filemanager/contractmanager/planfundPaySearchList";
		}else{//计划收款
			return "modules/filemanager/contractmanager/planfundSearchList";
		}
	}

	/**
	 * 查看，增加，编辑计划资金查询表单页面
	 */
	@RequiresPermissions(value={"contractmanager:planfundSearch:view","contractmanager:planfundSearch:add","contractmanager:planfundSearch:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PlanfundSearch planfundSearch, Model model) {
		model.addAttribute("planfundSearch", planfundSearch);
		return "modules/filemanager/contractmanager/planfundSearchForm";
	}

	/**
	 * 保存计划资金查询
	 */
	@RequiresPermissions(value={"contractmanager:planfundSearch:add","contractmanager:planfundSearch:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PlanfundSearch planfundSearch, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, planfundSearch)){
			return form(planfundSearch, model);
		}
		if(!planfundSearch.getIsNewRecord()){//编辑表单保存
			PlanfundSearch t = planfundSearchService.get(planfundSearch.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(planfundSearch, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			planfundSearchService.save(t);//保存
		}else{//新增表单保存
			planfundSearchService.save(planfundSearch);//保存
		}
		addMessage(redirectAttributes, "保存计划资金查询成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/planfundSearch/?repage";
	}
	
	/**
	 * 删除计划资金查询
	 */
	@RequiresPermissions("contractmanager:planfundSearch:del")
	@RequestMapping(value = "delete")
	public String delete(PlanfundSearch planfundSearch, RedirectAttributes redirectAttributes) {
		planfundSearchService.delete(planfundSearch);
		addMessage(redirectAttributes, "删除计划资金查询成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/planfundSearch/?repage";
	}
	
	/**
	 * 批量删除计划资金查询
	 */
	@RequiresPermissions("contractmanager:planfundSearch:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			planfundSearchService.delete(planfundSearchService.get(id));
		}
		addMessage(redirectAttributes, "删除计划资金查询成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/planfundSearch/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("contractmanager:planfundSearch:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(PlanfundSearch planfundSearch, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "计划资金查询"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PlanfundSearch> page = planfundSearchService.findPage(new Page<PlanfundSearch>(request, response, -1), planfundSearch);
    		new ExportExcel("计划资金查询", PlanfundSearch.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出计划资金查询记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/planfundSearch/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("contractmanager:planfundSearch:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PlanfundSearch> list = ei.getDataList(PlanfundSearch.class);
			for (PlanfundSearch planfundSearch : list){
				try{
					planfundSearchService.save(planfundSearch);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条计划资金查询记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条计划资金查询记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入计划资金查询失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/planfundSearch/?repage";
    }
	
	/**
	 * 下载导入计划资金查询数据模板
	 */
	@RequiresPermissions("contractmanager:planfundSearch:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "计划资金查询数据导入模板.xlsx";
    		List<PlanfundSearch> list = Lists.newArrayList(); 
    		new ExportExcel("计划资金查询数据", PlanfundSearch.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/planfundSearch/?repage";
    }
	
	
	

}