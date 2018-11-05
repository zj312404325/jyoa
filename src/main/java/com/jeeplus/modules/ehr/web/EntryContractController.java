/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.ehr.entity.EntryContract;
import com.jeeplus.modules.ehr.entity.UserInfo;
import com.jeeplus.modules.ehr.service.EntryContractService;
import com.jeeplus.modules.ehr.service.UserInfoService;
import com.jeeplus.modules.sys.service.OfficeService;
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
import java.util.List;

/**
 * 入职合同Controller
 * @author yc
 * @version 2017-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/ehr/entryContract")
public class EntryContractController extends BaseController {

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private EntryContractService entryContractService;
	
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public EntryContract get(@RequestParam(required=false) String id) {
		EntryContract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = entryContractService.get(id);
		}
		if (entity == null){
			entity = new EntryContract();
		}
		return entity;
	}
	
	@RequestMapping(value = "contract")
	public String contract(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		//EntryContract myEntryContract = entryContractService.findUniqueByProperty("companyid", UserUtils.getUser().getCompany().getId());
		EntryContract entryContract = new EntryContract();
		entryContract.setCompanyid(UserUtils.getUser().getOffice().getId());
		List<EntryContract> list = entryContractService.findList(entryContract);
		if(list.size()>0){
			model.addAttribute("myEntryContract", list.get(0));
		}else{
			model.addAttribute("myEntryContract", null);
		}

		userInfo = userInfoService.getByCreateBy(UserUtils.getUser().getId());
		model.addAttribute("userInfo", userInfo);
		return "modules/ehr/entry/contractUpload";
	}
	
	/**
	 * 合同列表页面
	 */
	@RequiresPermissions("ehr:entryContract:list")
	@RequestMapping(value = {"list", ""})
	public String list(EntryContract entryContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EntryContract> page = entryContractService.findPage(new Page<EntryContract>(request, response), entryContract); 
		for (EntryContract e : page.getList()) {
			e.setCompany(officeService.get(e.getCompanyid()));
		}
		model.addAttribute("page", page);
		return "modules/ehr/entry/entryContractList";
	}

	/**
	 * 查看，增加，编辑合同表单页面
	 */
	@RequiresPermissions(value={"ehr:entryContract:view","ehr:entryContract:add","ehr:entryContract:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(EntryContract entryContract, Model model) {
		if(FormatUtil.isNoEmpty(entryContract) && FormatUtil.isNoEmpty(entryContract.getCompanyid())){
			entryContract.setCompany(officeService.get(entryContract.getCompanyid()));
		}
		model.addAttribute("entryContract", entryContract);
		return "modules/ehr/entry/entryContractForm";
	}

	/**
	 * 保存合同
	 */
	@RequiresPermissions(value={"ehr:entryContract:add","ehr:entryContract:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(EntryContract entryContract, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, entryContract)){
			return form(entryContract, model);
		}
		if(!entryContract.getIsNewRecord()){//编辑表单保存
			EntryContract t = entryContractService.get(entryContract.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(entryContract, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			entryContractService.save(t);//保存
		}else{//新增表单保存
			EntryContract ec = entryContractService.findUniqueByProperty("companyid", entryContract.getCompanyid());
			if(FormatUtil.isNoEmpty(ec)){
				ec.setContract(entryContract.getContract());
				entryContractService.save(ec);//保存
//				addMessage(redirectAttributes, "该公司已添加合同，无法重复添加！");
				addMessage(redirectAttributes, "保存合同成功");
			}else{
				entryContractService.save(entryContract);//保存
				addMessage(redirectAttributes, "保存合同成功");
			}
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/entryContract/?repage";
	}
	
	/**
	 * 删除合同
	 */
	@RequiresPermissions("ehr:entryContract:del")
	@RequestMapping(value = "delete")
	public String delete(EntryContract entryContract, RedirectAttributes redirectAttributes) {
		entryContractService.delete(entryContract);
		addMessage(redirectAttributes, "删除合同成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/entryContract/?repage";
	}
	
	/**
	 * 批量删除合同
	 */
	@RequiresPermissions("ehr:entryContract:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			entryContractService.delete(entryContractService.get(id));
		}
		addMessage(redirectAttributes, "删除合同成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/entryContract/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ehr:entryContract:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(EntryContract entryContract, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<EntryContract> page = entryContractService.findPage(new Page<EntryContract>(request, response, -1), entryContract);
    		new ExportExcel("合同", EntryContract.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出合同记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/entryContract/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ehr:entryContract:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<EntryContract> list = ei.getDataList(EntryContract.class);
			for (EntryContract entryContract : list){
				try{
					entryContractService.save(entryContract);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条合同记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条合同记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入合同失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/entryContract/?repage";
    }
	
	/**
	 * 下载导入合同数据模板
	 */
	@RequiresPermissions("ehr:entryContract:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同数据导入模板.xlsx";
    		List<EntryContract> list = Lists.newArrayList(); 
    		new ExportExcel("合同数据", EntryContract.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/entryContract/?repage";
    }
	
	
	

}