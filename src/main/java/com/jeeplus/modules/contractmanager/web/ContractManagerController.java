/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.contractmanager.dao.ContractActualFundsDao;
import com.jeeplus.modules.contractmanager.entity.ContractActualFunds;
import com.jeeplus.modules.contractmanager.entity.ContractManager;
import com.jeeplus.modules.contractmanager.entity.ContractPlanfund;
import com.jeeplus.modules.contractmanager.service.ContractManagerService;
import com.jeeplus.modules.sys.utils.DictUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同管理-合同Controller
 * @author yc
 * @version 2017-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/contractmanager/contractManager")
public class ContractManagerController extends BaseController {

	@Autowired
	private ContractManagerService contractManagerService;
	@Autowired
	private ContractActualFundsDao contractActualFundsDao;
	
	@ModelAttribute
	public ContractManager get(@RequestParam(required=false) String id) {
		ContractManager entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractManagerService.get(id);
		}
		if (entity == null){
			entity = new ContractManager();
		}
		return entity;
	}
	
	/**
	 * 信息列表页面
	 */
	@RequiresPermissions("contractmanager:contractManager:list")
	@RequestMapping(value = {"list", ""})
	public String list(ContractManager contractManager, HttpServletRequest request, HttpServletResponse response, Model model) {
		contractManager.setCreateBy(UserUtils.getUser());
		Page<ContractManager> page = contractManagerService.findPage(new Page<ContractManager>(request, response), contractManager);

		Map map=null;
		for(ContractManager cm : page.getList()){
			map=new HashMap<String, Object>();
			map.put("id", cm.getId());
			map.put("DEL_FLAG_NORMAL", "0");
			Map resultMap=contractActualFundsDao.findSumMoneyByContractId(map);

			if(FormatUtil.isNoEmpty(resultMap)&&FormatUtil.isNoEmpty(resultMap.get("summoney"))){
				//余额
				Double summoney=FormatUtil.toDouble(resultMap.get("summoney"));//实际资金总额
				Double money=FormatUtil.toDouble(cm.getContractamount());

				cm.setCompleted(FormatUtil.toString(summoney));
				//完成比例
				Double completerate=summoney/money;
				cm.setCompleterate(FormatUtil.formatDouble(completerate*100, 2)+"%");
				//完成金额
			}else{
				cm.setCompleted("0");
				cm.setCompleterate("0%");
			}
			//逾期
			if(FormatUtil.isNoEmpty(cm.getCompletedate())){
				if(FormatUtil.isNoEmpty(cm.getPlancompletiondate())){
					int days= FormatUtil.daysBetween(cm.getPlancompletiondate(), cm.getCompletedate());
					cm.setOverdue(days+"");
				}
			}else{
				if(FormatUtil.isNoEmpty(cm.getPlancompletiondate())){
					if(FormatUtil.daysBetween(new Date(),cm.getPlancompletiondate()) < 0){
						int days= FormatUtil.daysBetween(cm.getPlancompletiondate(), new Date());
						cm.setOverdue(days+"");
					}else{
						cm.setOverdue("0");
					}

				}
			}
		}

		model.addAttribute("page", page);
		return "modules/filemanager/contractmanager/contractManagerList";
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
	@RequiresPermissions(value={"contractmanager:contractManager:view","contractmanager:contractManager:add","contractmanager:contractManager:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ContractManager contractManager, Model model) {
		model.addAttribute("contractManager", contractManager);
		return "modules/filemanager/contractmanager/contractManagerForm";
	}
	@RequiresPermissions(value={"contractmanager:contractManager:view","contractmanager:contractManager:add","contractmanager:contractManager:edit"},logical=Logical.OR)
	@RequestMapping(value = "authorform")
	public String authorform(ContractManager contractManager, Model model) {
		model.addAttribute("contractManager", contractManager);
		return "modules/filemanager/contractmanager/contractManagerAuthorForm";
	}

	@RequiresPermissions(value={"contractmanager:contractManager:view","contractmanager:contractManager:add","contractmanager:contractManager:edit"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(ContractManager contractManager, Model model) {
		model.addAttribute("contractManager", contractManager);
		
		double contractActualfund = this.getContractActualfund(contractManager.getContractActualFundsList());
		double contractPlanfund = this.getContractPlanfund(contractManager.getContractPlanfundList());
		double contractActualfundleft = BigDecimalUtil.sub(contractPlanfund, contractActualfund);
		model.addAttribute("contractActualfund", contractActualfund);
		model.addAttribute("contractActualfundleft", contractActualfundleft);
		if(contractPlanfund == 0){
			model.addAttribute("contractFundPercentage", FormatUtil.toPercentage(0));
		}else{
			model.addAttribute("contractFundPercentage", FormatUtil.toPercentage(BigDecimalUtil.div(contractActualfund, contractPlanfund, 2)));
		}
		return "modules/filemanager/contractmanager/contractManagerView";
	}

	/**
	 * 保存信息
	 */
	@RequiresPermissions(value={"contractmanager:contractManager:add","contractmanager:contractManager:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ContractManager contractManager, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, contractManager)){
			return form(contractManager, model);
		}
		
		if(FormatUtil.isNoEmpty(contractManager.getContractclassid())){
			contractManager.setContractclass(DictUtils.getDictLabel(contractManager.getContractclassid(), "contractmanager_contractclass", ""));
		}else{contractManager.setContractclass("");}
		if(FormatUtil.isNoEmpty(contractManager.getFundnatureid())){
			contractManager.setFundnature(DictUtils.getDictLabel(contractManager.getFundnatureid(), "fundnature", ""));
		}else{contractManager.setFundnature("");}
		if(FormatUtil.isNoEmpty(contractManager.getCurrencyid())){
			contractManager.setCurrency(DictUtils.getDictLabel(contractManager.getCurrencyid(), "currency", ""));
		}else{contractManager.setCurrency("");}
		if(FormatUtil.isNoEmpty(contractManager.getAffiliationid())){
			contractManager.setAffiliation(DictUtils.getDictLabel(contractManager.getAffiliationid(), "contractmanager_affiliation", ""));
		}else{contractManager.setAffiliation("");}
		
		if(!contractManager.getIsNewRecord()){//编辑表单保存
			ContractManager t = contractManagerService.get(contractManager.getId());//从数据库取出记录的值
			if(t.getStatus() != 2 && contractManager.getStatus() == 2){//更新完成状态是添加完成时间
				contractManager.setCompletedate(new Date());
			}
			MyBeanUtils.copyBeanNotNull2Bean(contractManager, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			contractManagerService.save(t);//保存
		}else{//新增表单保存
//			contractManager.setStatus(0);
			contractManagerService.save(contractManager);//保存
		}
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/contractManager/?repage";
	}
	
	/**
	 * 删除信息
	 */
	@RequiresPermissions("contractmanager:contractManager:del")
	@RequestMapping(value = "delete")
	public String delete(ContractManager contractManager, RedirectAttributes redirectAttributes) {
		contractManagerService.delete(contractManager);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/contractManager/?repage";
	}
	
	/**
	 * 批量删除信息
	 */
	@RequiresPermissions("contractmanager:contractManager:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			contractManagerService.delete(contractManagerService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/contractmanager/contractManager/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("contractmanager:contractManager:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ContractManager contractManager, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ContractManager> page = contractManagerService.findPage(new Page<ContractManager>(request, response, -1), contractManager);
    		new ExportExcel("信息", ContractManager.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/contractManager/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("contractmanager:contractManager:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ContractManager> list = ei.getDataList(ContractManager.class);
			for (ContractManager contractManager : list){
				try{
					contractManagerService.save(contractManager);
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
		return "redirect:"+Global.getAdminPath()+"/contractmanager/contractManager/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
	@RequiresPermissions("contractmanager:contractManager:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<ContractManager> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", ContractManager.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contractmanager/contractManager/?repage";
    }
	
	
	public static double getContractActualfund(List<ContractActualFunds> contractActualFundsList){
		double total = 0d;
		for (ContractActualFunds contractActualFunds : contractActualFundsList) {
			total = BigDecimalUtil.add(total, FormatUtil.toDouble(contractActualFunds.getMoney()));
		}
		return total;
	}
	public static double getContractPlanfund(List<ContractPlanfund> contractPlanFundsList){
		double total = 0d;
		for (ContractPlanfund contractPlanfund : contractPlanFundsList) {
			total = BigDecimalUtil.add(total, FormatUtil.toDouble(contractPlanfund.getMoney()));
		}
		return total;
	}

}