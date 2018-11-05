/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.ehr.entity.UserInfo;
import com.jeeplus.modules.ehr.service.UserInfoService;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.service.OfficeService;
import net.sf.json.JSONObject;
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
import com.jeeplus.modules.checkmodel.entity.CheckData;
import com.jeeplus.modules.checkmodel.entity.CheckDataDetail;
import com.jeeplus.modules.checkmodel.entity.CheckTime;
import com.jeeplus.modules.checkmodel.entity.CheckUser;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheck;
import com.jeeplus.modules.checkmodel.entity.PerformanceCheckDetail;
import com.jeeplus.modules.checkmodel.service.CheckDataDetailService;
import com.jeeplus.modules.checkmodel.service.CheckDataService;
import com.jeeplus.modules.checkmodel.service.CheckTimeService;
import com.jeeplus.modules.checkmodel.service.CheckUserService;
import com.jeeplus.modules.checkmodel.service.PerformanceCheckService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 绩效考核明细Controller
 * @author cqj
 * @version 2017-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/checkmodel/performanceCheck")
public class PerformanceCheckController extends BaseController {

	@Autowired
	private PerformanceCheckService performanceCheckService;
	
	@Autowired
	private CheckUserService checkUserService;
	
	@Autowired
	private CheckTimeService checkTimeService;
	
	@Autowired
	private CheckDataService checkDataService;
	
	@Autowired
	private CheckDataDetailService checkDataDetailService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public PerformanceCheck get(@RequestParam(required=false) String id) {
		PerformanceCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = performanceCheckService.get(id);
		}
		if (entity == null){
			entity = new PerformanceCheck();
		}
		return entity;
	}
	
	//设定考核数据
	private void setCheckData(PerformanceCheck entity) {
		//查找此用户是否设定了考核数据
		CheckData ckdata= checkDataService.findUniqueByProperty("userid", UserUtils.getUser().getId());
		if(FormatUtil.isNoEmpty(ckdata)){
			CheckDataDetail temp=new CheckDataDetail();
			temp.setCheckdataid(ckdata.getId());
			temp.getPage().setOrderBy("a.sort asc");
			List<CheckDataDetail> ls=checkDataDetailService.findList(temp);
			List<PerformanceCheckDetail> basicList=new ArrayList<PerformanceCheckDetail>();
			List<PerformanceCheckDetail> keyList=new ArrayList<PerformanceCheckDetail>();
			for (CheckDataDetail checkDataDetail : ls) {
				if(checkDataDetail.getType().equals("0")){
					PerformanceCheckDetail pcd=new PerformanceCheckDetail();
					pcd.setKpi(checkDataDetail.getKpi());
					pcd.setReferencepoint(checkDataDetail.getReferencepoint());
					pcd.setType(FormatUtil.toInteger(checkDataDetail.getType()));
					pcd.setWeight(FormatUtil.toInteger(checkDataDetail.getWeight()));
					basicList.add(pcd);
				}else{
					PerformanceCheckDetail pcd=new PerformanceCheckDetail();
					pcd.setKpi(checkDataDetail.getKpi());
					pcd.setReferencepoint(checkDataDetail.getReferencepoint());
					pcd.setType(FormatUtil.toInteger(checkDataDetail.getType()));
					pcd.setWeight(FormatUtil.toInteger(checkDataDetail.getWeight()));
					keyList.add(pcd);
				}
				entity.setPerformanceCheckDetailList(basicList);
				entity.setPerformanceCheckDetailListKey(keyList);
			}
		}
	}

	/**
	 * 绩效考核考核人列表页面
	 */
	@RequiresPermissions("checkmodel:performanceCheck:list")
	@RequestMapping(value = {"performanceCheckIndex"})
	public String performanceCheckIndex(PerformanceCheck performanceCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("repage", request.getParameter("repage"));
		model.addAttribute("performanceCheck",performanceCheck);
		if(FormatUtil.isNoEmpty(request.getParameter("tipinfo"))){
			model.addAttribute("tipinfo",request.getParameter("tipinfo"));
		}
		return "modules/ehr/checkmodel/performanceCheckIndex";
	}
	
	/**
	 * 绩效考核明细列表页面
	 */
	@RequiresPermissions("checkmodel:performanceCheck:list")
	@RequestMapping(value = {"list", ""})
	public String list(PerformanceCheck performanceCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user= UserUtils.getUser();
		model.addAttribute("performanceCheck",performanceCheck);
		if(FormatUtil.isNoEmpty(request.getParameter("cat"))&&request.getParameter("cat").equals("recieve")){
			Page<PerformanceCheck> page = getListPage(user,request,response,performanceCheck);  
			model.addAttribute("page", page);
			return "modules/ehr/checkmodel/performanceCheckRecieveList";
		}
		if(FormatUtil.isNoEmpty(performanceCheck.getCategory())&&performanceCheck.getCategory().equals("1")){//转正考核
			request.setAttribute("canCheck", true);
		}else{//绩效考核
			//判断是否有考核时间
			boolean canCheck=validCanCheck();
			request.setAttribute("canCheck", canCheck);
		}
		performanceCheck.setCreateBy(user);
		Page<PerformanceCheck> page = performanceCheckService.findPage(new Page<PerformanceCheck>(request, response), performanceCheck); 
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/performanceCheckSendList";
	}

	/**
	 * 是否有考核时间记录
	 * @return
	 */
	private boolean validCanCheck() {
		// TODO Auto-generated method stub
		CheckTime tempCheckTime=new CheckTime();
		tempCheckTime.setStartDate(new Date());
		List<CheckTime> checkTimeList=checkTimeService.findList(tempCheckTime);
		if(FormatUtil.isNoEmpty(checkTimeList)&&checkTimeList.size()>0){
			return true;
		}
		return false;
	}

	private Page<PerformanceCheck> getListPage(User user,HttpServletRequest request,HttpServletResponse response,PerformanceCheck performanceCheck) {
		//查找考核人记录
		CheckUser tempCheckUser=new CheckUser();
		tempCheckUser.setUserId(user.getId());
		tempCheckUser.setCategory(performanceCheck.getCategory());
		List<CheckUser> checkUserList=checkUserService.findList(tempCheckUser);
		if(FormatUtil.isNoEmpty(checkUserList)&&checkUserList.size()>0){
			StringBuilder sqlstr=new StringBuilder();
			for (int i = 0; i < checkUserList.size(); i++) {
				sqlstr.append(" ((a.officeid='"+checkUserList.get(i).getCheckofficeid()+"' or o.parent_ids like '%"+checkUserList.get(i).getCheckofficeid()+"%') and a.stationid='"+checkUserList.get(i).getStationId()+"') or");
			}
			String ids=sqlstr.toString();
			if(FormatUtil.isNoEmpty(ids)){
				ids=sqlstr.toString().substring(0,ids.length()-2);
			}
			performanceCheck.setSqlstr(ids);
			return performanceCheckService.findPage(new Page<PerformanceCheck>(request, response), performanceCheck);
		}
		performanceCheck.setSqlstr("1!=1");
		return performanceCheckService.findPage(new Page<PerformanceCheck>(request, response), performanceCheck);
	}

	/**
	 * 查看，增加，编辑绩效考核明细表单页面
	 */
	@RequiresPermissions(value={"checkmodel:performanceCheck:view","checkmodel:performanceCheck:add","checkmodel:performanceCheck:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PerformanceCheck performanceCheck, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		request.setAttribute("bhv", request.getParameter("bhv"));
		request.setAttribute("type", request.getParameter("type"));
		model.addAttribute("performanceCheck", performanceCheck);
		if(FormatUtil.isNoEmpty(request.getParameter("type"))&&request.getParameter("type").equals("1")){
			return "modules/ehr/checkmodel/performanceCheckRecieveForm";
		}else if(FormatUtil.isNoEmpty(request.getParameter("type"))&&request.getParameter("type").equals("2")){//后台管理
			request.setAttribute("disableScore", "1");
			return "modules/ehr/checkmodel/performanceCheckRecieveForm";
		}
		if(FormatUtil.isNoEmpty(performanceCheck.getCategory())&&performanceCheck.getCategory().equals("1")){

		}else{
			if(FormatUtil.isNoEmpty(performanceCheck)&&!FormatUtil.isNoEmpty(performanceCheck.getId())){
				//设定考核数据
				setCheckData(performanceCheck);
			}
		}
		return "modules/ehr/checkmodel/performanceCheckForm";
	}

	/**
	 * 保存绩效考核明细
	 */
	@RequiresPermissions(value={"checkmodel:performanceCheck:add","checkmodel:performanceCheck:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PerformanceCheck performanceCheck, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, performanceCheck)){
			return form(performanceCheck, model,request,redirectAttributes);
		}
		if(FormatUtil.isNoEmpty(performanceCheck.getCategory())&&performanceCheck.getCategory().equals("1")){//转正考核
			UserInfo userInfo=userInfoService.getByCreateBy(UserUtils.getUser().getId());
			if(!FormatUtil.isNoEmpty(userInfo.getEntrytraindate())){
				addMessage(redirectAttributes, "你尚未通过入职考试，请先完成入职考试再进行转正考核。");
				return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceCheck/performanceCheckIndex?repage=repage&type="+request.getParameter("type")+"&category="+FormatUtil.toString(performanceCheck.getCategory()+"&tipinfo=danger");
			}
		}
		if(!performanceCheck.getIsNewRecord()){//编辑表单保存
			PerformanceCheck t = performanceCheckService.get(performanceCheck.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(performanceCheck, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			performanceCheckService.save(t);//保存
			if(FormatUtil.isNoEmpty(performanceCheck.getCategory())&&performanceCheck.getCategory().equals("1")){
				addMessage(redirectAttributes, "保存转正考核成功");
			}else{
				addMessage(redirectAttributes, "保存绩效考核明细成功");
			}
			return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceCheck/performanceCheckIndex?repage=repage&type="+request.getParameter("type")+"&category="+FormatUtil.toString(performanceCheck.getCategory());
		}else{//新增表单保存
			performanceCheckService.save(performanceCheck);//保存
			if(FormatUtil.isNoEmpty(performanceCheck.getCategory())&&performanceCheck.getCategory().equals("1")){
				addMessage(redirectAttributes, "保存转正考核成功");
			}else{
				addMessage(redirectAttributes, "保存绩效考核明细成功");
			}
			//跳转到绩效考核面谈列表
			//return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceInterview/performanceInterviewIndex?type="+request.getParameter("type");
			return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceCheck/performanceCheckIndex?repage=repage&type="+request.getParameter("type")+"&category="+FormatUtil.toString(performanceCheck.getCategory());
		}
	}
	
	/**
	 * 删除绩效考核明细
	 */
	@RequiresPermissions("checkmodel:performanceCheck:del")
	@RequestMapping(value = "delete")
	public String delete(PerformanceCheck performanceCheck, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		performanceCheckService.delete(performanceCheck);
		if(FormatUtil.isNoEmpty(performanceCheck.getCategory())&&performanceCheck.getCategory().equals("1")){
			addMessage(redirectAttributes, "删除转正考核成功");
		}else{
			addMessage(redirectAttributes, "删除绩效考核明细成功");
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceCheck/performanceCheckIndex?repage=repage&type="+request.getParameter("type")+"&category="+FormatUtil.toString(performanceCheck.getCategory());
	}
	
	/**
	 * 批量删除绩效考核明细
	 */
	@RequiresPermissions("checkmodel:performanceCheck:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			performanceCheckService.delete(performanceCheckService.get(id));
		}
		addMessage(redirectAttributes, "删除绩效考核明细成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceCheck/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("checkmodel:performanceCheck:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(PerformanceCheck performanceCheck, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "绩效考核明细"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PerformanceCheck> page = performanceCheckService.findPage(new Page<PerformanceCheck>(request, response, -1), performanceCheck);
    		new ExportExcel("绩效考核明细", PerformanceCheck.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出绩效考核明细记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceCheck/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("checkmodel:performanceCheck:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PerformanceCheck> list = ei.getDataList(PerformanceCheck.class);
			for (PerformanceCheck performanceCheck : list){
				try{
					performanceCheckService.save(performanceCheck);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条绩效考核明细记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条绩效考核明细记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入绩效考核明细失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceCheck/?repage";
    }
	
	/**
	 * 下载导入绩效考核明细数据模板
	 */
	@RequiresPermissions("checkmodel:performanceCheck:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "绩效考核明细数据导入模板.xlsx";
    		List<PerformanceCheck> list = Lists.newArrayList(); 
    		new ExportExcel("绩效考核明细数据", PerformanceCheck.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/performanceCheck/?repage";
    }
	
	
	/**
	 * 绩效考核明细列表页面(后台)
	 */
	@RequiresPermissions("checkmodel:performanceCheck:managerlist")
	@RequestMapping(value = {"managerlist"})
	public String managerlist(PerformanceCheck performanceCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PerformanceCheck> page = performanceCheckService.findPage(new Page<PerformanceCheck>(request, response), performanceCheck); 
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/performanceCheckManageList";
	}

	//验证是否已设置岗位
	@RequestMapping(value = {"validHasPost"})
	public void validHasPost(PerformanceCheck performanceCheck, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();
		try {
			User user=UserUtils.getUser();
			if(!FormatUtil.isNoEmpty(user.getStationType())){
				map.put("status", "n");
				map.put("info", "您还未设置岗位！");
			}else{
				map.put("status", "y");
				map.put("info", "存在岗位！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "查询失败！");
		}
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	@RequestMapping(value = {"analysislist"})
	public String analysislist(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Office> officeList=officeService.findPerformanceCheckList(office);
		model.addAttribute("list", officeList);
		model.addAttribute("office", office);
		return "modules/ehr/checkmodel/analysislist";
	}

	@RequestMapping(value = {"managerPerformChecklist"})
	public String managerPerformChecklist(PerformanceCheck performanceCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		performanceCheck.setOfficeid(request.getParameter("officeid"));
		performanceCheck.setCheckyear(request.getParameter("checkyear"));
		performanceCheck.setCheckquarter(request.getParameter("checkquarter"));
		Page<PerformanceCheck> page = performanceCheckService.findPage(new Page<PerformanceCheck>(request, response), performanceCheck);
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/managerPerformChecklist";
	}
}