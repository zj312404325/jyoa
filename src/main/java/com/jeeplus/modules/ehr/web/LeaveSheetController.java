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
import com.jeeplus.modules.ehr.entity.LeaveSheet;
import com.jeeplus.modules.ehr.entity.UserInfo;
import com.jeeplus.modules.ehr.service.LeaveSheetService;
import com.jeeplus.modules.ehr.service.UserInfoService;
import com.jeeplus.modules.sys.entity.User;
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
 * 离职申请单管理Controller
 * @author yc
 * @version 2017-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/ehr/leaveSheet")
public class LeaveSheetController extends BaseController {

	@Autowired
	private LeaveSheetService leaveSheetService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public LeaveSheet get(@RequestParam(required=false) String id) {
		LeaveSheet entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leaveSheetService.get(id);
		}
		if (entity == null){
			entity = new LeaveSheet();
		}
		return entity;
	}
	
	@RequestMapping(value = "leaveSheet")
	public String leaveSheet(LeaveSheet leaveSheet, Model model) {
		User loginUser = UserUtils.getUser();
		
//		if(BaseConst.DEPT_HEADER_ID.equals(loginUser.getUserType()) || BaseConst.DEPT_MANAGER_ID.equals(loginUser.getUserType())){
			return "modules/ehr/leave/leaveManager";
//		}
		
		//return "redirect:"+Global.getAdminPath()+"/ehr/leaveSheet/toleaveSheet";
	}
	
	@RequestMapping(value = "toleaveSheet")
	public String toleaveSheet(LeaveSheet leaveSheet, Model model) {
		//User loginUser = UserUtils.getUser();

		LeaveSheet ls=new LeaveSheet();
		ls.setCompanyid(UserUtils.getUser().getOffice().getId());
		List<LeaveSheet> list=leaveSheetService.findList(ls);
		if(list.size()>0){
			model.addAttribute("leaveSheet", list.get(0));
		}else{
			model.addAttribute("leaveSheet", null);
		}

//		LeaveSheet myLeaveSheet = leaveSheetService.findUniqueByProperty("companyid", loginUser.getCompany().getId());
//		model.addAttribute("leaveSheet", myLeaveSheet);
		
		UserInfo userInfo = userInfoService.getByCreateBy(UserUtils.getUser().getId());
		model.addAttribute("userInfo", userInfo);
		return "modules/ehr/leave/leaveUpload";
	}
	
	
	
	/**
	 * 信息列表页面
	 */
	@RequiresPermissions("ehr:leaveSheet:list")
	@RequestMapping(value = {"list", ""})
	public String list(LeaveSheet leaveSheet, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LeaveSheet> page = leaveSheetService.findPage(new Page<LeaveSheet>(request, response), leaveSheet); 
		for (LeaveSheet e : page.getList()) {
			e.setCompany(officeService.get(e.getCompanyid()));
		}
		model.addAttribute("page", page);
		return "modules/ehr/leave/leaveSheetList";
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
	@RequiresPermissions(value={"ehr:leaveSheet:view","ehr:leaveSheet:add","ehr:leaveSheet:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LeaveSheet leaveSheet, Model model,HttpServletRequest request) {
		if(FormatUtil.isNoEmpty(leaveSheet) && FormatUtil.isNoEmpty(leaveSheet.getCompanyid())){
			leaveSheet.setCompany(officeService.get(leaveSheet.getCompanyid()));
		}
		model.addAttribute("leaveSheet", leaveSheet);
		String isadmin=request.getParameter("isadmin");
		if(FormatUtil.isNoEmpty(isadmin)&&isadmin.equals("1")){
		    model.addAttribute("userInfo",new UserInfo());
			return "modules/ehr/leave/leaveSheetAdminForm";
		}
		return "modules/ehr/leave/leaveSheetForm";
	}

	/**
	 * 保存信息
	 */
	@RequiresPermissions(value={"ehr:leaveSheet:add","ehr:leaveSheet:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LeaveSheet leaveSheet, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, leaveSheet)){
			return form(leaveSheet, model,request);
		}
		if(!leaveSheet.getIsNewRecord()){//编辑表单保存
			LeaveSheet t = leaveSheetService.get(leaveSheet.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(leaveSheet, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			leaveSheetService.save(t);//保存
		}else{
			LeaveSheet tempLeaveSheet = leaveSheetService.findUniqueByProperty("companyid", leaveSheet.getCompanyid());
			if(FormatUtil.isNoEmpty(tempLeaveSheet)){
				tempLeaveSheet.setCompanyid(leaveSheet.getCompanyid());
				tempLeaveSheet.setSheeturl(leaveSheet.getSheeturl());
				leaveSheetService.save(tempLeaveSheet);//保存
//				addMessage(redirectAttributes, "该公司已添加申请单，无法重复添加！");
				addMessage(redirectAttributes, "保存信息成功");
			}else{
				//新增表单保存
				leaveSheetService.save(leaveSheet);//保存
				addMessage(redirectAttributes, "保存信息成功");
			}
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveSheet/?repage";
	}
	
	/**
	 * 删除信息
	 */
	@RequiresPermissions("ehr:leaveSheet:del")
	@RequestMapping(value = "delete")
	public String delete(LeaveSheet leaveSheet, RedirectAttributes redirectAttributes) {
		leaveSheetService.delete(leaveSheet);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveSheet/?repage";
	}
	
	/**
	 * 批量删除信息
	 */
	@RequiresPermissions("ehr:leaveSheet:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leaveSheetService.delete(leaveSheetService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveSheet/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ehr:leaveSheet:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LeaveSheet leaveSheet, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeaveSheet> page = leaveSheetService.findPage(new Page<LeaveSheet>(request, response, -1), leaveSheet);
    		new ExportExcel("信息", LeaveSheet.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveSheet/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ehr:leaveSheet:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeaveSheet> list = ei.getDataList(LeaveSheet.class);
			for (LeaveSheet leaveSheet : list){
				try{
					leaveSheetService.save(leaveSheet);
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
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveSheet/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
	@RequiresPermissions("ehr:leaveSheet:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<LeaveSheet> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", LeaveSheet.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/leaveSheet/?repage";
    }
	
	
	

}