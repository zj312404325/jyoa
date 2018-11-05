/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.web;

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
import com.jeeplus.modules.contractmanager.entity.ContractManager;
import com.jeeplus.modules.contractmanager.service.ContractManagerService;
import com.jeeplus.modules.filemanagement.entity.Customer;
import com.jeeplus.modules.filemanagement.entity.ProjectManage;
import com.jeeplus.modules.filemanagement.service.CustomerService;
import com.jeeplus.modules.filemanagement.service.ProjectManageService;
import com.jeeplus.modules.sys.entity.Dict;
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
import java.util.List;

/**
 * 客户管理Controller
 * @author yc
 * @version 2017-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/filemanagement/customer")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ContractManagerService contractManagerService;
	
	@Autowired
	private ProjectManageService projectManageService;
	
	@ModelAttribute
	public Customer get(@RequestParam(required=false) String id) {
		Customer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerService.get(id);
		}
		if (entity == null){
			entity = new Customer();
		}
		return entity;
	}
	
	/**
	 * 信息列表页面
	 */
	@RequiresPermissions("filemanagement:customer:list")
	@RequestMapping(value = {"list", ""})
	public String list(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
		int usertype = FormatUtil.toInt(request.getParameter("usertype"));
		model.addAttribute("usertype", usertype);
		customer.setUsertype(usertype);
		customer.setCreateBy(UserUtils.getUser());
		Page<Customer> page = customerService.findPage(new Page<Customer>(request, response), customer); 
		model.addAttribute("page", page);
		if(usertype == 0){
			return "modules/filemanager/contractmanager/customerList";
		}else{
			return "modules/filemanager/contractmanager/supplierList";
		}
		
	}
	
	@RequestMapping(value = {"selectlist"})
	public String selectlist(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
		int usertype = FormatUtil.toInt(request.getParameter("usertype"));
		model.addAttribute("usertype", usertype);
		customer.setUsertype(usertype);
		customer.setCreateBy(UserUtils.getUser());
		customer.setStatus(1);
		Page<Customer> pageParam = new Page<Customer>(request, response);
		pageParam.setPageSize(5);
		Page<Customer> page = customerService.findPage(pageParam, customer); 
		model.addAttribute("page", page);
		if(usertype == 0){
			return "modules/filemanager/contractmanager/customerSelectList";
		}else{
			return "modules/filemanager/contractmanager/supplierSelectList";
		}
		
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
	@RequiresPermissions(value={"filemanagement:customer:view","filemanagement:customer:add","filemanagement:customer:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
		int usertype = FormatUtil.toInteger(request.getParameter("usertype"));
		customer.setUsertype(usertype);
		model.addAttribute("customer", customer);
		
//		List<Dict> list = DictUtils.getDictList("customerclass");
//		String customerclassOptionStr = "<option value=\"\" selected=\"selected\"></option>";
//		for (Dict dict : list) {
//			customerclassOptionStr+="<option value=\""+dict.getValue()+"\" selected=\"selected\">"+dict.getLabel()+"</option>";
//		}
//		model.addAttribute("customerclassOptionStr", customerclassOptionStr);
		if(usertype == 0){
			return "modules/filemanager/contractmanager/customerForm";
		}else{
			return "modules/filemanager/contractmanager/supplierForm";
		}
	}

	@RequiresPermissions(value={"filemanagement:customer:view","filemanagement:customer:add","filemanagement:customer:edit"},logical=Logical.OR)
	@RequestMapping(value = "authorform")
	public String authorform(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
		int usertype = FormatUtil.toInteger(request.getParameter("usertype"));
		customer.setUsertype(usertype);
		model.addAttribute("customer", customer);

		return "modules/filemanager/contractmanager/customerAuthorForm";
	}
	
	/**
	 * 查看，增加，编辑信息表单页面
	 */
	@RequiresPermissions(value={"filemanagement:customer:view","filemanagement:customer:add","filemanagement:customer:edit"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
//		int usertype = FormatUtil.toInteger(request.getParameter("usertype"));
//		customer.setUsertype(usertype);
		model.addAttribute("customer", customer);
		
		ContractManager contractManager = new ContractManager();
		contractManager.setContractpartyid(customer.getId());
		List<ContractManager> contractManagerList = contractManagerService.findList(contractManager);
		for (ContractManager m : contractManagerList) {
			contractManagerService.get(m);
		}
		customer.setContractManagerList(contractManagerList);
		
		customer.setProjectManageList(projectManageService.findList(new ProjectManage(contractManager)));
		
		if(customer.getUsertype() == 0){
			return "modules/filemanager/contractmanager/customerView";
		}else{
			return "modules/filemanager/contractmanager/supplierView";
		}
	}
	

	/**
	 * 保存信息
	 */
	@RequiresPermissions(value={"filemanagement:customer:add","filemanagement:customer:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, customer)){
			return form(customer, request, response, model);
		}
		
		if(FormatUtil.isNoEmpty(customer.getCustomerclassid())){
			customer.setCustomerclass(DictUtils.getDictLabel(customer.getCustomerclassid(), "customerclass", ""));
		}else{customer.setCustomerclass("");}
		if(FormatUtil.isNoEmpty(customer.getCustomerlevelid())){
			customer.setCustomerlevel(DictUtils.getDictLabel(customer.getCustomerlevelid(), "customerlevel", ""));
		}else{customer.setCustomerlevel("");}
		if(FormatUtil.isNoEmpty(customer.getCreditlevelid())){
			customer.setCreditlevel(DictUtils.getDictLabel(customer.getCreditlevelid(), "creditlevel", ""));
		}else{customer.setCreditlevel("");}
		if(FormatUtil.isNoEmpty(customer.getCurrencyid())){
			customer.setCurrency(DictUtils.getDictLabel(customer.getCurrencyid(), "currency", ""));
		}else{customer.setCurrency("");}
		if(FormatUtil.isNoEmpty(customer.getCompanyclassid())){
			customer.setCompanyclass(DictUtils.getDictLabel(customer.getCompanyclassid(), "companyclass", ""));
		}else{customer.setCompanyclass("");}
		
		if(!customer.getIsNewRecord()){//编辑表单保存
			Customer t = customerService.get(customer.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(customer, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			customerService.save(t);//保存
		}else{//新增表单保存
			customer.setStatus(1);//初始设置为1
			customerService.save(customer);//保存
		}
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/filemanagement/customer/?usertype="+customer.getUsertype()+"&repage";
	}
	
	/**
	 * 删除信息
	 */
	@RequiresPermissions("filemanagement:customer:del")
	@RequestMapping(value = "delete")
	public String delete(Customer customer, RedirectAttributes redirectAttributes) {
		customerService.delete(customer);
		addMessage(redirectAttributes, "删除信息成功");

		if(customer.getUsertype() == 0){
			return "redirect:"+Global.getAdminPath()+"/filemanagement/customer/list?repage&usertype=0";
		}else{
			return "redirect:"+Global.getAdminPath()+"/filemanagement/customer/list?repage&usertype=1";
		}
	}
	
	/**
	 * 批量删除信息
	 */
	@RequiresPermissions("filemanagement:customer:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerService.delete(customerService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/filemanagement/customer/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("filemanagement:customer:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Customer customer, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Customer> page = customerService.findPage(new Page<Customer>(request, response, -1), customer);
    		new ExportExcel("信息", Customer.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/filemanagement/customer/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("filemanagement:customer:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Customer> list = ei.getDataList(Customer.class);
			for (Customer customer : list){
				try{
					customerService.save(customer);
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
		return "redirect:"+Global.getAdminPath()+"/filemanagement/customer/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
	@RequiresPermissions("filemanagement:customer:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<Customer> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", Customer.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/filemanagement/customer/?repage";
    }
	
	
	
	@RequestMapping(value = {"goselect"})
	public String goselect(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("pid", FormatUtil.toString(request.getParameter("pid")));
		model.addAttribute("pvalue", FormatUtil.toString(request.getParameter("pvalue")));
		List<Dict> l = DictUtils.getDictList(request.getParameter("type"));
		model.addAttribute("list", l);
		return "modules/filemanager/contractmanager/commonSelect";
	}
}