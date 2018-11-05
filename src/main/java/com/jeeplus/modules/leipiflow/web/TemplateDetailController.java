/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.web;

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
import com.jeeplus.modules.flow.entity.Flowapply;
import com.jeeplus.modules.flow.service.FlowapplyService;
import com.jeeplus.modules.leipiflow.entity.*;
import com.jeeplus.modules.leipiflow.service.*;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 指定流程申请明细表Controller
 * @author yc
 * @version 2018-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/leipiflow/templateDetail")
public class TemplateDetailController extends BaseController {

	@Autowired
	private TemplateDetailService templateDetailService;
	@Autowired
	private FlowapplyService flowapplyService;
	@Autowired
	private LeipiRunProcessService leipiRunProcessService;
	@Autowired
	private LeipiRunService leipiRunService;
	@Autowired
	private LeipiFlowService leipiFlowService;
	@Autowired
	private LeipiFlowProcessService leipiFlowProcessService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private UserDao userDao;

	
	@ModelAttribute
	public TemplateDetail get(@RequestParam(required=false) String id) {
		TemplateDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = templateDetailService.get(id);
		}
		if (entity == null){
			entity = new TemplateDetail();
		}
		return entity;
	}
	
	/**
	 * 信息列表页面
	 */
//	@RequiresPermissions("leipiflow:templateDetail:list")
	@RequestMapping(value = {"list", ""})
	public String list(TemplateDetail templateDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
//		if(FormatUtil.isNoEmpty(request.getParameter("mtd"))){
//			templateDetail.setMtd(request.getParameter("mtd"));//我接收到的
//			templateDetail.setCreateuserid(UserUtils.getUser().getId());
//		}else{
//			templateDetail.setCreateBy(UserUtils.getUser());//我发出的
//		}
//		templateDetail.setLeipiflowid(request.getParameter("flowid"));
//		Page<TemplateDetail> page = templateDetailService.findPage(new Page<TemplateDetail>(request, response), templateDetail);
//
//		Map result = new HashMap();
//		for (TemplateDetail detail : page.getList()) {
//			Flowapply apply = flowapplyService.get(detail.getFlowapplyid());
//
//			LeipiRun leipiRun = leipiRunService.get(apply.getProcessInstanceId());//从数据库取出记录的值
//			LeipiFlow leipiFlow = leipiFlowService.get(leipiRun.getFlowId());
//
//			LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
//			String runflowid=leipiRun.getId();
//			leipiRunProcess.setRunFlow(runflowid);
//			leipiRunProcess.setIsDel(0);
//			leipiRunProcess.getPage().setOrderBy("a.dateline desc");
//			List<LeipiRunProcess>  leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
//			if(FormatUtil.isNoEmpty(leipiRunProcessList)&&leipiRunProcessList.size() > 0){
//				LeipiFlowProcess leipiFlowProcess = leipiFlowProcessService.get(leipiRunProcessList.get(0).getRunFlowProcess());
//				leipiRunProcess.setLeipiFlowProcess(leipiFlowProcess);
//				apply.setLeipiFlowProcess(leipiFlowProcess);
//				apply.setLeipiRunProcess(leipiRunProcessList.get(0));
//			}
//			apply.setLeipiFlow(leipiFlow);
//			apply.setLeipiRun(leipiRun);
//
//			//detail.setFlowapply(apply);
//
//
//			result.put(apply.getId(),apply);
//
//		}
//		model.addAttribute("result", result);
//		model.addAttribute("page", page);
//        model.addAttribute("flowid", request.getParameter("flowid"));
//        model.addAttribute("SALARY_FLOW_ID", Global.SALARY_FLOW_ID);
//		model.addAttribute("REWARD_FLOW_ID", Global.REWARD_FLOW_ID);

		Flowapply flowapply = new Flowapply();
		flowapply.setFlowtype(1);
		flowapply.setFlowid(request.getParameter("flowid"));
		flowapply.setTemplateDetailTemp(templateDetail);
		if(FormatUtil.isNoEmpty(request.getParameter("mtd"))){
			model.addAttribute("mtd", request.getParameter("mtd"));
			flowapply.setRunprocess_upid(UserUtils.getUser().getId());
//			flowapply.setRunflow_status("0");
			flowapply.setRunflow_status_useful("1");
		}else{
			if(!"1".equals(request.getParameter("isadmin"))){
				flowapply.setCreateBy(UserUtils.getUser());//我发出的
			}else{
				flowapply.setRunflow_status_useful("1");
				request.setAttribute("isadmin",request.getParameter("isadmin"));
			}
		}
		Page<Flowapply> page = flowapplyService.findPageSpecial(new Page<Flowapply>(request, response), flowapply);

		for (Flowapply apply : page.getList()) {

			templateDetail = new TemplateDetail();
			templateDetail.setFlowapplyid(apply.getId());
			List<TemplateDetail> detailList = templateDetailService.findList(templateDetail);
			apply.setTemplatedetailList(detailList);

			LeipiRun leipiRun = leipiRunService.get(apply.getProcessInstanceId());//从数据库取出记录的值
			LeipiFlow leipiFlow = leipiFlowService.get(leipiRun.getFlowId());

			LeipiRunProcess leipiRunProcess = new LeipiRunProcess();
			String runflowid=leipiRun.getId();
			leipiRunProcess.setRunFlow(runflowid);
			leipiRunProcess.setIsDel(0);
			leipiRunProcess.getPage().setOrderBy("a.dateline desc");
			List<LeipiRunProcess>  leipiRunProcessList = leipiRunProcessService.findList(leipiRunProcess);
			if(FormatUtil.isNoEmpty(leipiRunProcessList)&&leipiRunProcessList.size() > 0){
				LeipiFlowProcess leipiFlowProcess = leipiFlowProcessService.get(leipiRunProcessList.get(0).getRunFlowProcess());
				leipiRunProcess.setLeipiFlowProcess(leipiFlowProcess);
				apply.setLeipiFlowProcess(leipiFlowProcess);
				apply.setLeipiRunProcess(leipiRunProcessList.get(0));
			}
			for(LeipiRunProcess l : leipiRunProcessList){
				if(l.getUpid().equals(UserUtils.getUser().getId()) &&l.getStatus()==0){
					apply.setHasmyprocesstodo("1");
					break;
				}
			}
			apply.setLeipiFlow(leipiFlow);
			apply.setLeipiRun(leipiRun);

			//detail.setFlowapply(apply);
		}

		model.addAttribute("page", page);
		model.addAttribute("flowid", request.getParameter("flowid"));
		model.addAttribute("SALARY_FLOW_ID", Global.SALARY_FLOW_ID);
		model.addAttribute("REWARD_FLOW_ID", Global.REWARD_FLOW_ID);
		return "modules/leipiflow/templateDetailList";
	}

	@RequestMapping(value = "salarydetaillist")
	public String salarydetaillist(TemplateDetail templateDetail, HttpServletRequest request, HttpServletResponse response, Model model) {

		templateDetail = new TemplateDetail();
        templateDetail.setFlowstatus("1");
		templateDetail.setLeipiflowid(Global.SALARY_FLOW_ID);
		if(FormatUtil.isNoEmpty(request.getParameter("startdate"))){
			templateDetail.setStartdate(FormatUtil.StringToDate(request.getParameter("startdate"),"yyyy-MM-dd"));
		}
		if(FormatUtil.isNoEmpty(request.getParameter("enddate"))){
			templateDetail.setEnddate(FormatUtil.StringToDate(request.getParameter("enddate"),"yyyy-MM-dd"));
		}

        templateDetail.getPage().setOrderBy("a.create_date desc,a.sortno ASC");
		List<TemplateDetail> totallist = templateDetailService.findList(templateDetail);
        Page<TemplateDetail> pg = new Page<TemplateDetail>(request, response);
        pg.setOrderBy("a.create_date desc,a.sortno ASC");
		Page<TemplateDetail> detailList = templateDetailService.findPage(pg, templateDetail);

		Map m = new LinkedHashMap();
		int temptotal = 0;
		int temptwice = 0;
		for (TemplateDetail td : totallist) {
			if(!FormatUtil.isNoEmpty(m.get(td.getVar1()))){
				m.put(td.getVar1(),1);
				temptotal++;
			}else{
				int i = FormatUtil.toInt(m.get(td.getVar1()))+1;
				m.put(td.getVar1(),i);
				if(i == 2){
					temptwice++;
				}
			}
		}

//		List<User> listuser = systemService.findUser(new User());
		List<User> listuser = userDao.findList(new User());
		int totaluser = listuser.size();

		model.addAttribute("salarylist", m);
		model.addAttribute("temptotal", temptotal);
		model.addAttribute("temptotalPercentage", FormatUtil.getPercentage(temptotal,totaluser));

		model.addAttribute("temptwice", temptwice);
		model.addAttribute("temptwicePercentage", FormatUtil.getPercentage(temptwice,totaluser));


		model.addAttribute("detailList", detailList);
		return "modules/leipiflow/salaryTemplateDetailList";
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
//	@RequiresPermissions(value={"leipiflow:templateDetail:view","leipiflow:templateDetail:add","leipiflow:templateDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TemplateDetail templateDetail, Model model) {
		model.addAttribute("templateDetail", templateDetail);
		return "modules/leipiflow/templateDetailForm";
	}

	/**
	 * 保存信息
	 */
//	@RequiresPermissions(value={"leipiflow:templateDetail:add","leipiflow:templateDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TemplateDetail templateDetail, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, templateDetail)){
			return form(templateDetail, model);
		}
		if(!templateDetail.getIsNewRecord()){//编辑表单保存
			TemplateDetail t = templateDetailService.get(templateDetail.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(templateDetail, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			templateDetailService.save(t);//保存
		}else{//新增表单保存
			templateDetailService.save(templateDetail);//保存
		}
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/templateDetail/?repage";
	}
	
	/**
	 * 删除信息
	 */
//	@RequiresPermissions("leipiflow:templateDetail:del")
	@RequestMapping(value = "delete")
	public String delete(TemplateDetail templateDetail, RedirectAttributes redirectAttributes) {
		templateDetailService.delete(templateDetail);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/templateDetail/?repage";
	}
	
	/**
	 * 批量删除信息
	 */
//	@RequiresPermissions("leipiflow:templateDetail:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			templateDetailService.delete(templateDetailService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/templateDetail/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
//	@RequiresPermissions("leipiflow:templateDetail:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(TemplateDetail templateDetail, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TemplateDetail> page = templateDetailService.findPage(new Page<TemplateDetail>(request, response, -1), templateDetail);
    		new ExportExcel("信息", TemplateDetail.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/templateDetail/?repage";
    }

	/**
	 * 导入Excel数据

	 */
//	@RequiresPermissions("leipiflow:templateDetail:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TemplateDetail> list = ei.getDataList(TemplateDetail.class);
			for (TemplateDetail templateDetail : list){
				try{
					templateDetailService.save(templateDetail);
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
		return "redirect:"+Global.getAdminPath()+"/leipiflow/templateDetail/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
//	@RequiresPermissions("leipiflow:templateDetail:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<TemplateDetail> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", TemplateDetail.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/templateDetail/?repage";
    }
	
	
	

}