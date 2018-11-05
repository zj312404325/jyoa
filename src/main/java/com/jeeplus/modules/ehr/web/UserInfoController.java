/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.ehr.entity.UserInfo;
import com.jeeplus.modules.ehr.entity.UserMember;
import com.jeeplus.modules.ehr.service.UserInfoService;
import com.jeeplus.modules.sys.dao.RoleDao;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入职员工信息登记Controller
 * @author yc
 * @version 2017-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/ehr/entry")
public class UserInfoController extends BaseController {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private OfficeService officeService;
//
	@ModelAttribute
	public UserInfo get(@RequestParam(required=false) String id) {
		UserInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userInfoService.get(id);
		}
		if (entity == null){
			entity = new UserInfo();
		}
		return entity;
	}
	
	/**
	 * 信息列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		userInfo.setIspermittemp(1);
//		userInfo.setIsdel(null);

		userInfo.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
		//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				userInfo.setSearchdays(4);
			}
		}
		Page<UserInfo> page = userInfoService.findPage(new Page<UserInfo>(request, response), userInfo); 
		model.addAttribute("page", page);
		return "modules/ehr/entry/userInfoList";
	}
	
	@RequestMapping(value = {"permitlist"})
	public String permitlist(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		userInfo.setContract("1");
//        userInfo.setIsdel(null);
		Page<UserInfo> page = userInfoService.findPage(new Page<UserInfo>(request, response), userInfo); 
		for (UserInfo o : page.getList()) {
			if(FormatUtil.isNoEmpty(o.getContract())){
				o.setContract(o.getContract().substring(1));
			}
		}
		model.addAttribute("page", page);
		return "modules/ehr/entry/entryContractPermitList";
	}
	
	@RequestMapping(value = {"leavePermitlist"})
	public String leavePermitlist(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		userInfo.setCreateBy(UserUtils.getUser());
//		userInfo.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
		//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
//		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
//			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
//				userInfo.setSearchdays(4);
//			}
//		}
		if(FormatUtil.isNoEmpty(request.getParameter("isadmin")) && "0".equals(request.getParameter("isadmin"))){
			request.setAttribute("isadmin", request.getParameter("isadmin"));
			User loginUser = UserUtils.getUser();
			Office myOffice = officeService.get(loginUser.getOffice().getId());
			if(FormatUtil.isNoEmpty(myOffice)){
				if(FormatUtil.isNoEmpty(myOffice.getPrimaryPerson()) && myOffice.getPrimaryPerson().getId().equals(loginUser.getId())){
					userInfo.setOfficeid(loginUser.getOffice().getId());

					userInfo.setLeaveurl("1");
					userInfo.setUser_del_flag("1");
					Page<UserInfo> page = userInfoService.findPage(new Page<UserInfo>(request, response), userInfo);
					for (UserInfo o : page.getList()) {
						o.setCreateBy(UserUtils.get(o.getCreateBy().getId()));
						if(FormatUtil.isNoEmpty(o.getLeaveurl())){
							o.setLeaveurl(o.getLeaveurl().substring(1));
						}
					}
					model.addAttribute("page", page);

				}
			}
//			if(BaseConst.DEPT_MANAGER_ID.equals(loginUser.getUserType())){
//				userInfo.setDeptid(loginUser.getOfficeTrueId());
//			}else if(BaseConst.DEPT_HEADER_ID.equals(loginUser.getUserType())){
//				userInfo.setOfficeid(loginUser.getOffice().getId());
//			}
		}else{
			userInfo.setLeaveurl("1");
			userInfo.setUser_del_flag("1");
			Page<UserInfo> pg=new Page<UserInfo>(request, response);
			pg.setOrderBy("a.leavedate desc");
			Page<UserInfo> page = userInfoService.findPage(pg, userInfo);
			for (UserInfo o : page.getList()) {
				o.setCreateBy(UserUtils.get(o.getCreateBy().getId()));
				if(FormatUtil.isNoEmpty(o.getLeaveurl())){
					o.setLeaveurl(o.getLeaveurl().substring(1));
				}
			}
			model.addAttribute("page", page);

		}

		return "modules/ehr/leave/leavePermitList";
	}
	
	@RequestMapping(value = "permitContract")
	public String permitContract(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception{
		
		if("no".equals(request.getParameter("pt"))){
			userInfo.setStatus(2);
			addMessage(redirectAttributes, "合同审核不通过");
		}else{
			userInfo.setStatus(1);
			addMessage(redirectAttributes, "合同审核已通过");
		}
		userInfoService.saveOnly(userInfo);
		
		return "redirect:"+Global.getAdminPath()+"/ehr/entry/permitlist";
	}
	
	@RequestMapping(value = "permitLeave")
	public String permitLeave(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception{
		
		if("no".equals(request.getParameter("pt"))){
			userInfo.setLeavestatus(2);
			addMessage(redirectAttributes, "离职申请不通过");
		}else{
			userInfo.setLeavestatus(1);
			addMessage(redirectAttributes, "离职申请已通过");
		}
		userInfoService.saveOnly(userInfo);
		
		return "redirect:"+Global.getAdminPath()+"/ehr/entry/leavePermitlist";
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
	@RequestMapping(value = "form")
	public String form(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		UserInfo myuserInfo = userInfoService.getByCreateBy(UserUtils.getUser().getId());
		if(FormatUtil.isNoEmpty(myuserInfo)){
			model.addAttribute("userInfo", myuserInfo);
		}else{
			model.addAttribute("userInfo", userInfo);
		}
		//查找是否上传了岗位说明书及考核指标书
		User user=UserUtils.getUser();
		if(FormatUtil.isNoEmpty(user.getOffice().getId())){
			Office office=officeService.get(user.getOffice().getId());
			if(FormatUtil.isNoEmpty(office) && FormatUtil.isNoEmpty(office.getFile1())){
				request.setAttribute("departFile",Global.PDF_VIEW_PATH+office.getFile1());
			}
			if(FormatUtil.isNoEmpty(office) && FormatUtil.isNoEmpty(office.getFile2())){
				request.setAttribute("performFile",Global.PDF_VIEW_PATH+office.getFile2());
			}
		}
		return "modules/ehr/entry/entryRegister";
	}
	@RequestMapping(value = "ehrform")
	public String ehrform(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		UserInfo myuserInfo = userInfoService.get(request.getParameter("userinfoid"));
		model.addAttribute("userInfo", myuserInfo);
		model.addAttribute("isehr", true);
//		//查找是否上传了岗位说明书及考核指标书
//		User user=UserUtils.getUser();
//		if(FormatUtil.isNoEmpty(user.getOffice().getId())){
//			Office office=officeService.get(user.getOffice().getId());
//			if(FormatUtil.isNoEmpty(office.getFile1())){
//				request.setAttribute("departFile",Global.PDF_VIEW_PATH+office.getFile1());
//			}
//			if(FormatUtil.isNoEmpty(office.getFile2())){
//				request.setAttribute("performFile",Global.PDF_VIEW_PATH+office.getFile2());
//			}
//		}
		return "modules/ehr/entry/entryRegister";
	}
	@RequestMapping(value = "view")
	public String view(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("userInfo", userInfo);
		return "modules/ehr/entry/entryRegisterView";
	}
	@RequestMapping(value = "disclaimerView")
	public String disclaimerView(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("userInfo", userInfo);
		return "modules/ehr/entry/entryDisclaimerDtl";
	}
	@RequestMapping(value = "gotrain")
	public String gotrain( HttpServletRequest request, HttpServletResponse response, Model model) {
		UserInfo myuserInfo = userInfoService.getByCreateBy(UserUtils.getUser().getId());
		model.addAttribute("userInfo", myuserInfo);
		return "modules/ehr/train/entryTrain";
	}
	@RequestMapping(value = "train")
	public String train( HttpServletRequest request, HttpServletResponse response, Model model) {
		
		UserInfo myuserInfo = userInfoService.getByCreateBy(UserUtils.getUser().getId());
		model.addAttribute("userInfo", myuserInfo);
		
		return "modules/ehr/train/entryExam";
	}
	
	@RequestMapping(value = "permitEntry")
	public String permitEntry(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String status = request.getParameter("status");
		String position = request.getParameter("position");
		String probationperiod = request.getParameter("probationperiod");
		String probationperiodsalary = request.getParameter("probationperiodsalary");
		userInfo.setPosition(position);
		userInfo.setProbationperiod(probationperiod);
		userInfo.setProbationperiodsalary(probationperiodsalary);
//		if("2".equals(status)){
			userInfo.setIspermit(1);
			userInfoService.saveOnly(userInfo);
			addMessage(redirectAttributes, "新员工【"+userInfo.getFullname()+"】登记信息审核已通过");
//		}else{
//			userInfo.setStatus(-1);
//			userInfoService.save(userInfo);
//			addMessage(redirectAttributes, "审核不通过");
//		}
		return "redirect:" + adminPath + "/ehr/entry/list";
	}

	/**
	 * 保存信息
	 */
	@RequestMapping(value = "save")
	public String save(UserInfo userInfo, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userInfo)){
			return form(userInfo, request, response, model);
		}
		
		String detailJsonArray = request.getParameter("detailJsonArray").trim();
		if(FormatUtil.isNoEmpty(detailJsonArray)){
			JSONArray jsonArray = JSONArray.fromObject(detailJsonArray);
			List<UserMember> memberlist = JSONArray.toList(jsonArray,UserMember.class);
			if(memberlist.size() > 0){
				userInfo.setUserMemberList(memberlist);
			}
		}
		
		if(!userInfo.getIsNewRecord()){//编辑表单保存
			userInfo.setIspermit(0);
			UserInfo t = userInfoService.get(userInfo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userInfoService.save(t);//保存
		}else{//新增表单保存
			userInfo.setStatus(0);
			userInfo.setIspermit(0);
			userInfoService.save(userInfo);//保存
		}
		//addMessage(redirectAttributes, "保存信息成功");
		request.setAttribute("isehr",request.getParameter("isehr"));
		return "modules/ehr/entry/entryPromise";
	}
	
	@RequestMapping(value = "saveContract")
	public String saveContract(UserInfo userInfo, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		userInfo.setStatus(0);
		userInfoService.saveOnly(userInfo);//保存 
		addMessage(redirectAttributes, "合同上传成功"); 
		return "redirect:"+Global.getAdminPath()+"/ehr/entryContract/contract";
	}
	@RequestMapping(value = "saveLeaveSheet")
	public String saveLeaveSheet(UserInfo userInfo, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		if(FormatUtil.isNoEmpty(request.getParameter("isadmin"))&&request.getParameter("isadmin").equals("1")){//管理员后台添加离职申请单
			UserInfo tempUserInfo=new UserInfo();
			tempUserInfo.setUserid(userInfo.getUserid());
			tempUserInfo.setUser_del_flag("1");
			List<UserInfo> userInfoList= userInfoService.findList(tempUserInfo);
			if(FormatUtil.isNoEmpty(userInfoList)&&userInfoList.size()>0){
				UserInfo t = userInfoList.get(0);
				t.setFullname(userInfo.getFullname());
				t.setLeavestatus(1);
				t.setLeavedate(new Date());
				t.setLeaveurl(userInfo.getLeaveurl());
				t.setResignation(userInfo.getResignation());
				userInfoService.saveOnly(t);//保存
				addMessage(redirectAttributes, "离职申请上传成功");
			}else{
				addMessage(redirectAttributes, "离职申请上传失败");
			}
			return "redirect:"+Global.getAdminPath()+"/ehr/entry/leavePermitlist";
		}else{//用户自己上传
			userInfo.setLeavestatus(0);
			userInfo.setLeavedate(new Date());
			userInfoService.saveOnly(userInfo);//保存
			addMessage(redirectAttributes, "离职申请上传成功");
			return "redirect:"+Global.getAdminPath()+"/ehr/leaveSheet/toleaveSheet";
		}
	}
	@RequestMapping(value = "passEntryTrain")
	public String passEntryTrain(UserInfo userInfo, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		userInfo.setEntrytraindate(new Date());
		userInfoService.saveOnly(userInfo);//保存 
		addMessage(redirectAttributes, "入职考试通过"); 
		return "redirect:"+Global.getAdminPath()+"/ehr/entry/train";
	}
	
	/**
	 * 删除信息
	 */
	@RequiresPermissions("ehr:userInfo:del")
	@RequestMapping(value = "delete")
	public String delete(UserInfo userInfo, RedirectAttributes redirectAttributes) {
		userInfoService.delete(userInfo);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/userInfo/?repage";
	}
	
	/**
	 * 批量删除信息
	 */
	@RequiresPermissions("ehr:userInfo:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			userInfoService.delete(userInfoService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/userInfo/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ehr:userInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserInfo> page = userInfoService.findPage(new Page<UserInfo>(request, response, -1), userInfo);
    		new ExportExcel("信息", UserInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/userInfo/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ehr:userInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserInfo> list = ei.getDataList(UserInfo.class);
			for (UserInfo userInfo : list){
				try{
					userInfoService.save(userInfo);
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
		return "redirect:"+Global.getAdminPath()+"/ehr/userInfo/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
	@RequiresPermissions("ehr:userInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<UserInfo> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", UserInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/userInfo/?repage";
    }
	
	@RequestMapping(value = {"trainManager"})
	public String trainManager(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Role r = new Role(BaseConst.NEW_EMPLOYEE_ROLE_ID);
//		User u = new User();
//		u.setRole(r);
//		userInfo.setCreateBy(u);

//		userInfo.setIspermit(-2);

		Page<UserInfo> page = userInfoService.findPageTrainList(new Page<UserInfo>(request, response), userInfo); 
		model.addAttribute("page", page);
		return "modules/ehr/train/entryTrainManagerList";
	}

	@RequestMapping(value = "permitEntryAjax")
	public void permitEntryAjax(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();

		try {
			String status = request.getParameter("status");
			String position = request.getParameter("position");
			String probationperiod = request.getParameter("probationperiod");
			String probationperiodsalary = request.getParameter("probationperiodsalary");
			userInfo.setPosition(position);
			userInfo.setProbationperiod(probationperiod);
			userInfo.setProbationperiodsalary(probationperiodsalary);
			userInfo.setIspermit(1);
			userInfoService.saveOnly(userInfo);

			map.put("status", "y");
			map.put("info", "保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "保存失败！");
		}

		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	@RequestMapping(value = {"setRole"})
	public String setRole(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		String id=request.getParameter("id");
		UserInfo ui= userInfoService.get(id);
		User user = userDao.get(ui.getCreateBy().getId());
		if(!FormatUtil.isNoEmpty(user)){
			request.setAttribute("message","未找到相关用户！");
			return "modules/ehr/entry/userInfoRole";
        }
		user.setRoleList(roleDao.findList(new Role(user)));
		model.addAttribute("user", user);
		List<Role> roleList = roleDao.findAllList(new Role());
		model.addAttribute("allRoles", roleList);
		return "modules/ehr/entry/userInfoRole";
	}

	@RequestMapping(value = {"saveRole"})
	public void saveRole(User user,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();
		try {
			User t = UserUtils.get(user.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(user, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			t.setRoleList(user.getRoleList());
			// 保存用户信息
			systemService.saveUser(t);
			map.put("status", "y");
			map.put("info", "保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "保存失败！");
		}
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	@RequestMapping(value = "rosterlist")
	public String rosterlist(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		userInfo.setIspermittemp(1);
		userInfo.setIspermit(1);
		userInfo.setSearchdays(0);
//		userInfo.setIsdel(null);
		Page<UserInfo> page = userInfoService.findPage(new Page<UserInfo>(request, response), userInfo);
		model.addAttribute("page", page);
		return "modules/ehr/entry/userRosterList";
	}

	/**
	 * 人员结构列表
	 */
	@RequestMapping(value = {"peopleStructList", ""})
	public String peopleStructList(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		String peopleStructType=userInfo.getPeopleStructType();
		List<Map> list = userInfoService.findPeopleStruct(userInfo);
		model.addAttribute("list", list);
		model.addAttribute("userInfo",userInfo);
		return "modules/ehr/entry/peopleStructList";
	}
}