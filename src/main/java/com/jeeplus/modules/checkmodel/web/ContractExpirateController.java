/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.utils.DictUtils;
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
import com.jeeplus.modules.checkmodel.entity.CheckUser;
import com.jeeplus.modules.checkmodel.entity.ContractExpirate;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheck;
import com.jeeplus.modules.checkmodel.service.ContractExpirateService;
import com.jeeplus.modules.ehr.service.UserInfoService;
import com.jeeplus.modules.sys.dao.OfficeDao;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 合同到期人员考核申请Controller
 * @author cqj
 * @version 2017-10-31
 */
@Controller
@RequestMapping(value = "${adminPath}/checkmodel/contractExpirate")
public class ContractExpirateController extends BaseController {

	@Autowired
	private ContractExpirateService contractExpirateService;
	
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public ContractExpirate get(@RequestParam(required=false) String id) {
		ContractExpirate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractExpirateService.get(id);
		}
		if (entity == null){
			entity = new ContractExpirate();
		}
		return entity;
	}
	
	/**
	 * 合同到期人员考核列表页面
	 */
	@RequiresPermissions("checkmodel:contractExpirate:list")
	@RequestMapping(value = {"contractExpirateIndex"})
	public String contractExpirateIndex(CheckUser checkUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("repage", request.getParameter("repage"));
		return "modules/ehr/checkmodel/contractExpirateIndex";
	}
	
	/**
	 * 合同到期人员考核申请列表页面
	 */
	@RequiresPermissions("checkmodel:contractExpirate:list")
	@RequestMapping(value = {"list", ""})
	public String list(ContractExpirate contractExpirate, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user= UserUtils.getUser();
		if(FormatUtil.isNoEmpty(request.getParameter("category"))&&request.getParameter("category").equals("recieve")){
			Page<ContractExpirate> page = getListPage(user,request,response,contractExpirate);  
			model.addAttribute("page", page);
			return "modules/ehr/checkmodel/contractExpirateRecieveList";
		}
		contractExpirate.setCreateBy(user);
		Page<ContractExpirate> page = contractExpirateService.findPage(new Page<ContractExpirate>(request, response), contractExpirate); 
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/contractExpirateSendList";
	}

	private Page<ContractExpirate> getListPage(User user,
			HttpServletRequest request, HttpServletResponse response,
			ContractExpirate contractExpirate) {
		if(user.getUserType().equals("2")){//部门经理
			//String officeid=user.getOfficeTrueId();//获取部门id
			contractExpirate.setOfficeid(user.getOffice().getId());
			return contractExpirateService.findPage(new Page<ContractExpirate>(request, response), contractExpirate);
		}else if(user.getUserType().equals("4")){//人事
			return contractExpirateService.findPage(new Page<ContractExpirate>(request, response), contractExpirate);
		}else if(user.getUserType().equals("3")){//普通用户
			request.setAttribute("commonuser", "1");
			contractExpirate.setUserid(user.getId());
			return contractExpirateService.findPage(new Page<ContractExpirate>(request, response), contractExpirate);
		}
		contractExpirate.setOfficeid("''");
		return contractExpirateService.findPage(new Page<ContractExpirate>(request, response), contractExpirate);
	}

	/**
	 * 查看，增加，编辑合同到期人员考核申请表单页面
	 */
	@RequiresPermissions(value={"checkmodel:contractExpirate:view","checkmodel:contractExpirate:add","checkmodel:contractExpirate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ContractExpirate contractExpirate, Model model,HttpServletRequest request) {
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("ismanage", request.getParameter("ismanage"));
		request.setAttribute("mtd", request.getParameter("mtd"));
		model.addAttribute("contractExpirate", contractExpirate);
		User user=UserUtils.getUser();
		request.setAttribute("user", user);
		if(FormatUtil.isNoEmpty(request.getParameter("type"))&&request.getParameter("type").equals("1")){
			return "modules/ehr/checkmodel/contractExpirateRecieveForm";
		}
		if(FormatUtil.isNoEmpty(contractExpirate)&&!FormatUtil.isNoEmpty(contractExpirate.getId())){//新增
			//设定考核人
			ContractExpirate ce=setCheckUser(contractExpirate,user);
			model.addAttribute("contractExpirate", contractExpirate);
		}
		return "modules/ehr/checkmodel/contractExpirateForm";
	}
	
	//设定考核人
	private ContractExpirate setCheckUser(ContractExpirate contractExpirate,User user) {
		contractExpirate.setCheckuserid(user.getId());
		contractExpirate.setCheckusername(user.getName());
		contractExpirate.setCheckuserno(user.getNo());
		Office office= officeService.get(user.getOffice().getId());
		contractExpirate.setCheckofficeid(user.getOffice().getId());
		contractExpirate.setCheckofficename(user.getOffice().getName());
		contractExpirate.setCheckstationid(user.getStationType());
		contractExpirate.setCheckstationname(DictUtils.getDictLabel(user.getStationType(), "sys_station_type", ""));
		return contractExpirate;
	}

	/**
	 * 保存合同到期人员考核申请
	 */
	@RequiresPermissions(value={"checkmodel:contractExpirate:add","checkmodel:contractExpirate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ContractExpirate contractExpirate, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, contractExpirate)){
			return form(contractExpirate, model,request);
		}
		if(!contractExpirate.getIsNewRecord()){//编辑表单保存
			ContractExpirate t = contractExpirateService.get(contractExpirate.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(contractExpirate, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			contractExpirateService.save(t);//保存
		}else{//新增表单保存
			contractExpirateService.save(contractExpirate);//保存
		}
		addMessage(redirectAttributes, "保存合同到期人员考核申请成功");
		if(!FormatUtil.isNoEmpty(request.getParameter("ismanage"))){
			return "redirect:"+Global.getAdminPath()+"/checkmodel/contractExpirate/contractExpirateIndex?repage=repage&type="+request.getParameter("type");
		}else{
			return "redirect:"+Global.getAdminPath()+"/checkmodel/contractExpirate/managerIndex?repage=repage&type="+request.getParameter("type");
		}
	}

	/**
	 * 删除合同到期人员考核申请
	 */
	@RequiresPermissions("checkmodel:contractExpirate:del")
	@RequestMapping(value = "delete")
	public String delete(ContractExpirate contractExpirate, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		contractExpirateService.delete(contractExpirate);
		addMessage(redirectAttributes, "删除合同到期人员考核申请成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/contractExpirate/contractExpirateIndex?repage=repage&type="+request.getParameter("type");
	}
	
	/**
	 * 批量删除合同到期人员考核申请
	 */
	@RequiresPermissions("checkmodel:contractExpirate:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			contractExpirateService.delete(contractExpirateService.get(id));
		}
		addMessage(redirectAttributes, "删除合同到期人员考核申请成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/contractExpirate/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("checkmodel:contractExpirate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ContractExpirate contractExpirate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同到期人员考核申请"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ContractExpirate> page = contractExpirateService.findPage(new Page<ContractExpirate>(request, response, -1), contractExpirate);
    		new ExportExcel("合同到期人员考核申请", ContractExpirate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出合同到期人员考核申请记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/contractExpirate/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("checkmodel:contractExpirate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ContractExpirate> list = ei.getDataList(ContractExpirate.class);
			for (ContractExpirate contractExpirate : list){
				try{
					contractExpirateService.save(contractExpirate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条合同到期人员考核申请记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条合同到期人员考核申请记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入合同到期人员考核申请失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/contractExpirate/?repage";
    }
	
	/**
	 * 下载导入合同到期人员考核申请数据模板
	 */
	@RequiresPermissions("checkmodel:contractExpirate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同到期人员考核申请数据导入模板.xlsx";
    		List<ContractExpirate> list = Lists.newArrayList(); 
    		new ExportExcel("合同到期人员考核申请数据", ContractExpirate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/contractExpirate/?repage";
    }
	
	
	/**
	 *合同到期列索引页(后台)
	 */
	@RequiresPermissions("checkmodel:contractExpirate:managerlist")
	@RequestMapping(value = {"managerIndex"})
	public String managerIndex(ContractExpirate contractExpirate, HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("type", request.getParameter("type"));
		return "modules/ehr/checkmodel/contractExpirateManageIndex";
	}
	
	/**
	 *合同到期列表页面(后台)
	 */
	@RequiresPermissions("checkmodel:contractExpirate:managerlist")
	@RequestMapping(value = {"managerlist"})
	public String managerlist(ContractExpirate contractExpirate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractExpirate> page = contractExpirateService.findPage(new Page<ContractExpirate>(request, response), contractExpirate); 
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/contractExpirateManageList";
	}
	
	/**
	 *合同到期提醒列表页面(后台)
	 */
	@RequiresPermissions("checkmodel:contractExpirate:managerlist")
	@RequestMapping(value = {"contractWarnninglist"})
	public String contractWarnninglist(ContractExpirate contractExpirate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractExpirate> page = contractExpirateService.searchContractExpirateList(new Page<ContractExpirate>(request, response), contractExpirate);
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/warnninglist";
	}

	/**
	 * 批量删除合同到期人员考核申请
	 */
	@RequiresPermissions("checkmodel:contractExpirate:managerlist")
	@RequestMapping(value = "changeState")
	public String changeState(String ids, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String status= request.getParameter("status");
		String idArray[] =ids.split(",");
		for(String id : idArray){
			ContractExpirate contractExpirate=contractExpirateService.get(id);
			contractExpirate.setStatus(status);
			contractExpirateService.save(contractExpirate);
		}
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/contractExpirate/managerlist?type=1";
	}
}