/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.beanvalidator.BeanValidators;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.dao.RoleDao;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.*;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.PostService;
import com.jeeplus.modules.sys.service.SystemConfigService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.tools.utils.TwoDimensionCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.*;
import java.util.*;

/**
 * 用户Controller
 * @author jeeplus
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private OfficeService officeService;

	@Autowired
	private PostService postService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:index")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:index")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		if(FormatUtil.isNoEmpty(user.getOffice())){
			user.getOffice().setName(java.net.URLDecoder.decode(user.getOffice().getName(),"UTF-8"));
			Office o = officeService.get(user.getOffice().getId());
			user.setCompany(officeService.get(o.getTopparentid()));
		}
		if(UserUtils.getUser().isAdmin() || "1".equals(request.getParameter("isAdmin"))){
			user.setLoginFlag(null);
			request.setAttribute("isAdmin",request.getParameter("isAdmin"));
		}
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}

	
	@RequiresPermissions(value={"sys:user:view","sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null || "".equals(user.getCompany().getId())){
//			user.setCompany(UserUtils.getUser().getCompany());
		}else{
			user.setCompany(officeService.get(user.getCompany().getId()));
		}
		if (user.getOffice()==null || user.getOffice().getId()==null || "".equals(user.getOffice().getId())){
//			user.setOffice(UserUtils.getUser().getOffice());
		}else{
			user.setOffice(officeService.get(user.getOffice().getId()));
		}
		if(FormatUtil.isNoEmpty(user.getOffice())){
			user.getOffice().setPostList(postService.findList(new Post(user.getOffice())));
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userForm";
	}

	@RequiresPermissions(value={"sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		//选择部门是设置
		if(FormatUtil.isNoEmpty(request.getParameter("office.id"))){
			Office of=officeService.get(request.getParameter("office.id"));
			Office ofcompany=officeService.get(request.getParameter("company.id"));
			user.setCompanyName(ofcompany.getName());
			if(!FormatUtil.isNoEmpty(of)){
				addMessage(model, "您选择的部门不存在！");
				return form(user, model);
			}
			user.setOfficeName(of.getName());
//			if(validIsStation(request.getParameter("office.id"))){
//				user.setStationName(of.getName());
//				String parentOfficeId=getParentOfficeId(request.getParameter("office.id"));
//				user.setOfficeTrueId(parentOfficeId);
//				Office of1=officeService.get(parentOfficeId);
//				user.setOfficeName(of1.getName());
//			}else{
//				user.setOfficeTrueId(request.getParameter("office.id"));
//				user.setOfficeName(of.getName());
//			}
		}
		
		
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		//生成用户二维码，使用登录名
		String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
		+ user.getId() + "/qrcode/";
		FileUtils.createDirectory(realPath);
		String name= user.getId()+".png"; //encoderImgId此处二维码的图片名
		String filePath = realPath + name;  //存放路径
		TwoDimensionCode.encoderQRCode(user.getLoginName(), filePath, "png");//执行生成二维码
		user.setQrCode(request.getContextPath()+Global.USERFILES_BASE_URL
			+  user.getId()  + "/qrcode/"+name);
		
		if(!FormatUtil.isNoEmpty(user.getPhoto())){
			user.setPhoto(Global.NO_HEAD);
		}
		if(!FormatUtil.isNoEmpty(user.getUserType())){
			user.setUserType("3");
		}
		if(FormatUtil.isNoEmpty(user.getStationType())){
			Post p = postService.get(user.getStationType());
			if(FormatUtil.isNoEmpty(p)){
				user.setStationName(p.getPostname());
			}
		}

		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 获取此岗位的上级部门
	 * @param officeId 岗位id
	 * @return
	 */
	private String getParentOfficeId(String officeId) {
		if(FormatUtil.isNoEmpty(officeId)){
			Office officeTemp=officeService.get(officeId);
			String parentId=officeTemp.getParentId();
			if(FormatUtil.isNoEmpty(parentId)){
				return parentId;
			}
		}
		return null;
	}

	private String getIds(String ids) {
		if(FormatUtil.isNoEmpty(ids)){
			ids="'"+ids.replace(",", "','")+"'";
			return ids;
		}
		return null;
	}

	/**
	 * 验证是否是岗位
	 * @param officeId
	 * @return
	 */
	private boolean validIsStation(String officeId) {
		if(FormatUtil.isNoEmpty(officeId)){
			Office office= officeService.get(officeId);
			if(office.getType().equals("3")){//是岗位
				return true;
			}
		}
		return false;
	}

	@RequiresPermissions("sys:user:del")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 批量删除用户
	 */
	@RequiresPermissions("sys:user:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			User user = systemService.getUser(id);
			if(Global.isDemoMode()){
				addMessage(redirectAttributes, "演示模式，不允许操作！");
				return "redirect:" + adminPath + "/sys/user/list?repage";
			}
			if (UserUtils.getUser().getId().equals(user.getId())){
				addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
			}else if (User.isAdmin(user.getId())){
				addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
			}else{
				systemService.deleteUser(user);
				addMessage(redirectAttributes, "删除用户成功");
			}
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
            List<User> l = page.getList();
            for (User u : l) {
				u.setRoleList(roleDao.findList(new Role(u)));
			}
            page.setList(l);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						user.setPhoto(Global.NO_HEAD);
						
						//选择岗位是设置start
						if(FormatUtil.isNoEmpty(user.getOffice().getId())){
							if(validIsStation(user.getOffice().getId())){//是岗位
								String parentOfficeId=getParentOfficeId(user.getOffice().getId());
								user.setOfficeTrueId(parentOfficeId);
							}else{
								user.setOfficeTrueId(user.getOffice().getId());
							}
						}
						//选择岗位是设置end
						
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		/*User user1= UserUtils.getUser();
		request.setAttribute("user", user1);*/
		//return "redirect:" + adminPath + "/sys/user/list?repage";
		return "redirect:" + adminPath + "/sys/user/index";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginNameNoCache(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}
	
	/**
	 * 用户信息显示编辑保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "infoEdit")
	public String infoEdit(User user, boolean __ajax, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			if(user.getName() !=null )
				currentUser.setName(user.getName());
			if(user.getEmail() !=null )
				currentUser.setEmail(user.getEmail());
			if(user.getPhone() !=null )
				currentUser.setPhone(user.getPhone());
			if(user.getMobile() !=null )
				currentUser.setMobile(user.getMobile());
			if(user.getRemarks() !=null )
				currentUser.setRemarks(user.getRemarks());
//			if(user.getPhoto() !=null )
//				currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			if(__ajax){//手机访问
				AjaxJson j = new AjaxJson();
				j.setSuccess(true);
				j.setMsg("修改个人资料成功!");
				return renderString(response, j);
			}
			model.addAttribute("user", currentUser);
			model.addAttribute("Global", new Global());
			model.addAttribute("message", "保存用户信息成功");
			return "modules/sys/userInfo";
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfoEdit";
	}

	
	/**
	 * 用户头像显示编辑保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "imageEdit")
	public String imageEdit(User user, boolean __ajax, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			if(user.getPhoto() !=null )
				currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			if(__ajax){//手机访问
				AjaxJson j = new AjaxJson();
				j.setSuccess(true);
				j.setMsg("修改个人头像成功!");
				return renderString(response, j);
			}
			model.addAttribute("message", "保存用户信息成功");
			return "modules/sys/userInfo";
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userImageEdit";
	}
	/**
	 * 用户头像显示编辑保存
	 * @param user
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "imageUpload")
	public String imageUpload( HttpServletRequest request, HttpServletResponse response,MultipartFile file) throws IllegalStateException, IOException {
		User currentUser = UserUtils.getUser();
		
		// 判断文件是否为空  
        if (!file.isEmpty()) {  
                // 文件保存路径  
            	String realPath = Global.USERFILES_BASE_URL
        		+ UserUtils.getPrincipal() + "/images/" ;
                // 转存文件  
            	FileUtils.createDirectory(Global.getUserfilesBaseDir()+realPath);
            	file.transferTo(new File( Global.getUserfilesBaseDir() +realPath +  file.getOriginalFilename()));  
//            	currentUser.setPhoto(request.getContextPath()+realPath + file.getOriginalFilename());
            	currentUser.setPhoto(realPath + file.getOriginalFilename());
    			systemService.updateUserInfo(currentUser);
        }  

		return "modules/sys/userImageEdit";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = "commonImageUpload")
	public void commonImageUpload( HttpServletRequest request, HttpServletResponse response,MultipartFile file) throws IllegalStateException, IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();
		// 判断文件是否为空  
        if (!file.isEmpty()) {  
                // 文件保存路径  
            	String realPath = Global.USERFILES_BASE_URL
        		+ UserUtils.getPrincipal() + "/images/" ;
                // 转存文件  
            	FileUtils.createDirectory(Global.getUserfilesBaseDir()+realPath);
            	file.transferTo(new File( Global.getUserfilesBaseDir() +realPath +  file.getOriginalFilename()));  
            	map.put("url",realPath + file.getOriginalFilename());
        }  
        out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public AjaxJson infoData() {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		j.setErrorCode("-1");
		j.setMsg("获取个人信息成功!");
		j.put("data", UserUtils.getUser());
		return j;
	}
	
	
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) throws UnsupportedEncodingException {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			String pwd= StringEscapeUtils.unescapeHtml4(newPassword);
			oldPassword= StringEscapeUtils.unescapeHtml4(oldPassword);
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), pwd);
				model.addAttribute("message", "修改密码成功");
				//退出登录
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath+"/login";
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
			return "modules/sys/userInfo";
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwdByBtn")
	public String modifyPwdByBtn(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			String pwd= StringEscapeUtils.unescapeHtml4(newPassword);
			oldPassword= StringEscapeUtils.unescapeHtml4(oldPassword);
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
				//退出登录
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath+"/login";
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
			return "modules/sys/userInfo";
		}
		model.addAttribute("user", user);
		model.addAttribute("hasBtn", true);
		return "modules/sys/userModifyPwd";
	}
	
	/**
	 * 保存签名
	 */
	@ResponseBody
	@RequestMapping(value = "saveSign")
	public AjaxJson saveSign(User user, boolean __ajax, HttpServletResponse response, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		User currentUser = UserUtils.getUser();
		currentUser.setSign(user.getSign());
		systemService.updateUserInfo(currentUser);
		j.setMsg("设置签名成功");
		return j;
	}
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			if(!UserUtils.getUser().isAdmin() && e.isAdmin()){
				continue;
			}
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeAllData")
	public List<Map<String, Object>> treeAllData(@RequestParam(required=false) String officeId, HttpServletResponse response,HttpServletRequest request) {
		String udata=request.getParameter("udata");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findAllUserByOfficeId(officeId,udata);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			if(!UserUtils.getUser().isAdmin() && e.isAdmin()){
				continue;
			}
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
    
	/**
	 * web端ajax验证用户名是否可用
	 * @param loginName
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validateLoginName")
	public boolean validateLoginName(String loginName, HttpServletResponse response) {
		
	    User user =  userDao.findUniqueByProperty("login_name", loginName);
	    if(user == null){
	    	return true;
	    }else{
		    return false;
	    }
	    
	}
	
	/**
	 * web端ajax验证手机号是否可以注册（数据库中不存在）
	 */
	@ResponseBody
	@RequestMapping(value = "validateMobile")
	public boolean validateMobile(String mobile, HttpServletResponse response, Model model) {
		  User user =  userDao.findUniqueByProperty("mobile", mobile);
		    if(user == null){
		    	return true;
		    }else{
			    return false;
		    }
	}
	
	/**
	 * web端ajax验证手机号是否已经注册（数据库中已存在）
	 */
	@ResponseBody
	@RequestMapping(value = "validateMobileExist")
	public boolean validateMobileExist(String mobile, HttpServletResponse response, Model model) {
		  User user =  userDao.findUniqueByProperty("mobile", mobile);
		    if(user != null){
		    	return true;
		    }else{
			    return false;
		    }
	}
	
	@ResponseBody
	@RequestMapping(value = "resetPassword")
	public AjaxJson resetPassword(String mobile, HttpServletResponse response, Model model) {
		SystemConfig config = systemConfigService.get("1");//获取短信配置的用户名和密码
		AjaxJson j = new AjaxJson();
		if(userDao.findUniqueByProperty("mobile", mobile) == null){
			j.setSuccess(false);
			j.setMsg("手机号不存在!");
			j.setErrorCode("1");
			return j;
		}
		User user =  userDao.findUniqueByProperty("mobile", mobile);
		String newPassword = String.valueOf((int) (Math.random() * 900000 + 100000));
		try {
			String result = UserUtils.sendPass(config.getSmsName(), config.getSmsPassword(), mobile, newPassword);
			if (!result.equals("100")) {
				j.setSuccess(false);
				j.setErrorCode("2");
				j.setMsg("短信发送失败，密码重置失败，错误代码："+result+"，请联系管理员。");
			}else{
				j.setSuccess(true);
				j.setErrorCode("-1");
				j.setMsg("短信发送成功，密码重置成功!");
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
			}
		} catch (IOException e) {
			j.setSuccess(false);
			j.setErrorCode("3");
			j.setMsg("因未知原因导致短信发送失败，请联系管理员。");
		}
		return j;
	}
	
	@RequestMapping(value = "remindWin")
	public String remindWin(User user,HttpServletRequest request, HttpServletResponse response, Model model) {
//		request.setAttribute("msg", request.getParameter("msg"));
		List<Map> lst = (List<Map>) JSONArray.toCollection(JSONArray.fromObject(request.getParameter("msg")), Map.class);
//		Map m = new HashMap();
//		m.put("url", "/jy_oa/a/oa/oaNotify/form?id=222564f106b449de824839f83a988209");
//		m.put("title", "看见对方");
//		lst.add(m);
		request.setAttribute("lst", lst);
		return "modules/sys/remindWin";
	}
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
	
	/**
	 * 用户头像显示编辑保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "commonImageUploadInit")
	public String commonImageUploadInit(User user, boolean __ajax, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/commonImageUpload";
	}
	
	@RequestMapping("fileDownload")
	public void fileDownload( HttpServletRequest request,
            HttpServletResponse response)
			throws IOException {
	    //获取项目根目录
	    String ctxPath = request.getSession().getServletContext().getRealPath("");
		String fileurl=StringEscapeUtils.unescapeHtml4(request.getParameter("fileUrl"));
//	    String fileurl=java.net.URLDecoder.decode(request.getParameter("fileUrl"),"UTF-8");
//	    int idx=fileurl.indexOf('/', 2);
//	    if(idx!=-1){
//	    	fileurl=fileurl.substring(idx,fileurl.length());
//	    }
	    
	    //获取下载文件露肩
	    String downLoadPath = ctxPath+"/"+fileurl;  
	  
        String filename = this.getExtensionName(fileurl);  
          
        //设置文件MIME类型  
        //response.setContentType(getServletContext().getMimeType(filename));  
        //设置Content-Disposition
        response.setContentType("application/x-msdownload");
        filename=new String(filename.getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=\""+filename+"\"");
        //downLoadPath="F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp8/wtpwebapps/jy_oa/userfiles/49b58b2b95d740e9adb6eefdbce4448c/files/oa/notify/2017/01/%E5%B9%B4%E4%BC%9A%E5%BA%A7%E4%BD%8D%E5%AE%89%E6%8E%922.xls";
        //读取文件  
        InputStream in = new FileInputStream(downLoadPath);  
        OutputStream out = response.getOutputStream();  
        
        //写文件  
        int b;  
        while((b=in.read())!= -1)  
        {  
            out.write(b);  
        }  
          
        in.close();  
        out.close();  
	}
	
	/**
	 * 获取文件扩展名
	 * @param filename
	 * @return
	 */
	private static String getExtensionName(String filename) { 
        if ((filename != null) && (filename.length() > 0)) { 
            int dot = filename.lastIndexOf('/'); 
            if ((dot >-1) && (dot < (filename.length() - 1))) { 
                return filename.substring(dot + 1); 
            } 
        } 
        return filename; 
    }

	@RequestMapping(value = {"upload"})
	public void upload(User user, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter writer =response.getWriter();
		Map mapInfo=new HashMap<String, String>();

		ServletContext application = request.getSession().getServletContext();
		String savePath = "/userfiles/"+UserUtils.getUser().getId()+"/files/"+DateUtils.DateToString(new Date(), DateStyle.YYYY_MM_DD.getValue())+"/"+new Date().getTime()+"/";
		String url = application.getRealPath("/") ;
		File dir = new File(url+savePath);
		if (!dir.exists()) {
			dir.mkdirs();
			dir = null;
		}

        MultiValueMap<String,MultipartFile> map = ((MultipartHttpServletRequest) request).getMultiFileMap();// 为了获取文件，这个类是必须的
        List<MultipartFile> list = map.get("fileUploade");// 获取到文件的列表
        String fileName = "";
        List<Map> fileList=new ArrayList<Map>();
        // 将图片进行存储
        for (MultipartFile mFile : list) {
            String oldName= mFile.getOriginalFilename();//获取文件名称
			oldName=FormatUtil.formatFileName(oldName);
            fileName = oldName;
            File f = new File(url + File.separator +savePath+ fileName);
            try {
                FileCopyUtils.copy(mFile.getBytes(), f);
                Map fileMap=new HashMap();
                fileMap.put("fileName",fileName);
                fileMap.put("fileUrl",savePath+ fileName);
                fileList.add(fileMap);
            } catch (IOException e) {
                e.printStackTrace();
                mapInfo.put("status", "n");
                mapInfo.put("info", "文件上传失败");
                JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
                String a =  jsonstr.toString();
                writer.write(a);
                writer.close();
                return;
            }
        }
		mapInfo.put("error", 0);
		mapInfo.put("url", savePath+ fileName);
		mapInfo.put("filename", fileName);
		mapInfo.put("status", "y");
		if(fileList.size()>0){
            mapInfo.put("fileList", fileList);
        }
		JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
		String a =  jsonstr.toString();
		writer.write(a);
		writer.close();
	}

	@RequestMapping(value = {"uploadInstitution"})
	public void uploadInstitution(User user, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter writer =response.getWriter();
		Map mapInfo=new HashMap<String, String>();

		ServletContext application = request.getSession().getServletContext();
		String savePath = "/userfiles/"+UserUtils.getUser().getId()+"/files/"+DateUtils.DateToString(new Date(), DateStyle.YYYY_MM_DD.getValue())+"/"+new Date().getTime()+"/";
		String url = application.getRealPath("/") ;
		File dir = new File(url+savePath);
		if (!dir.exists()) {
			dir.mkdirs();
			dir = null;
		}

		MultiValueMap<String,MultipartFile> map = ((MultipartHttpServletRequest) request).getMultiFileMap();// 为了获取文件，这个类是必须的
		List<MultipartFile> list = map.get("fileUploade");// 获取到文件的列表
		String fileName = "";
		List<Map> fileList=new ArrayList<Map>();
		// 将图片进行存储
		for (MultipartFile mFile : list) {
			String oldName= mFile.getOriginalFilename();//获取文件名称
			oldName=FormatUtil.formatFileName(oldName);
			fileName = oldName;
			File f = new File(url + File.separator +savePath+ fileName);
			try {
				FileCopyUtils.copy(mFile.getBytes(), f);
				Map fileMap=new HashMap();
				fileMap.put("fileName",fileName);
				fileMap.put("fileUrl",savePath+ fileName);
				fileList.add(fileMap);
			} catch (IOException e) {
				e.printStackTrace();
				mapInfo.put("status", "n");
				mapInfo.put("info", "文件上传失败");
				JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
				String a =  jsonstr.toString();
				writer.write(a);
				writer.close();
				return;
			}
		}
		mapInfo.put("error", 0);
		mapInfo.put("url", savePath+ fileName);
		mapInfo.put("filename", fileName);
		mapInfo.put("status", "y");
		if(fileList.size()>0){
			mapInfo.put("fileList", fileList);
		}
		JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
		String a =  jsonstr.toString();
		writer.write(a);
		writer.close();
	}

	@RequestMapping(value = "uploadInit")
	public String uploadInit(User user, boolean __ajax,HttpServletRequest request,HttpServletResponse response, Model model) {
		request.setAttribute("id",request.getParameter("id"));
		String type=request.getParameter("type");
        String filetype=request.getParameter("filetype");
		if(FormatUtil.isNoEmpty(type)&&type.equals("pdf")){
			return "modules/sys/uploadPdf";
		}else if(FormatUtil.isNoEmpty(type)&&type.equals("muti")){
            if(FormatUtil.isNoEmpty(filetype)){
                request.setAttribute("filetype",filetype);
                return "modules/sys/uploadMultipleFile";
            }
			return "modules/sys/uploadMultiple";
		}
		return "modules/sys/upload";
	}


    @RequestMapping(value = {"uploadOne"})
    public void uploadOne(User user, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        PrintWriter writer =response.getWriter();
        Map mapInfo=new HashMap<String, String>();

        ServletContext application = request.getSession().getServletContext();
        String savePath = "/userfiles/"+UserUtils.getUser().getId()+"/files/"+DateUtils.DateToString(new Date(), DateStyle.YYYY_MM_DD.getValue())+"/"+new Date().getTime()+"/";
        String url = application.getRealPath("/") ;
        File dir = new File(url+savePath);
        if (!dir.exists()) {
            dir.mkdirs();
            dir = null;
        }

        DefaultMultipartHttpServletRequest multiRequest = (DefaultMultipartHttpServletRequest) request;
        Iterator<String> it= multiRequest.getFileNames();
        String fileName = "";
        while (it.hasNext()) {
            CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile(it.next());
            String oldName = file.getOriginalFilename();
            //检查扩展名
	    	/*String fileExt = oldName.substring(oldName.lastIndexOf(".") + 1).toLowerCase();
			if(!Arrays.<String>asList(BaseConst.extMap.get(dirName).split(",")).contains(fileExt)){
				writer.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + BaseConst.extMap.get(dirName) + "格式。"));
				writer.close();

				mapInfo.put("status", "n");
				mapInfo.put("info", "上传文件扩展名是不允许的扩展名。\n只允许" + BaseConst.extMap.get(dirName) + "格式。");
				JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
				String a =  jsonstr.toString();
				writer.write(a);
				writer.close();
				return;
			}*/
            //检查文件大小
	    	/*if(this.isMaxSize(dirName, file.getSize())){
	    		mapInfo.put("status", "n");
				mapInfo.put("info", "上传超过规定,图片不大于1M，文件不能大于10M");
				JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
				String a =  jsonstr.toString();
				writer.write(a);
				writer.close();
				return;
	    	}*/
	    	/*String hz=oldName.substring(oldName.lastIndexOf("."));
	    	fileName = new Date().getTime()+new Random().nextInt(Integer.MAX_VALUE)+hz;*/
            //fileName = new Date().getTime()+"_"+oldName;
            fileName = oldName;
            File f = new File(url + File.separator +savePath+ fileName);
//	    	if (f.exists()) {
//	    		f.delete();
//	    	}
//	    	try {
//				if (!f.createNewFile()) continue;
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
            try {
                FileCopyUtils.copy(file.getBytes(), f);
            } catch (IOException e) {
                e.printStackTrace();
                mapInfo.put("status", "n");
                mapInfo.put("info", "文件上传失败");
                JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
                String a =  jsonstr.toString();
                writer.write(a);
                writer.close();
                return;
            }
        }
	    /*JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", savePath+ fileName);
		obj.put("status", "y");
		writer.println(obj.toString());
		writer.close();*/
        mapInfo.put("error", 0);
        mapInfo.put("url", savePath+ fileName);
        mapInfo.put("filename", fileName);
        mapInfo.put("status", "y");
        JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
        String a =  jsonstr.toString();
        writer.write(a);
        writer.close();
    }

	@RequestMapping(value = "searchdays")
	public String searchdays(HttpServletRequest request, HttpServletResponse response, Model model) {

		request.setAttribute("SEARCH_DAYS_LIMIT", Global.SEARCH_DAYS_LIMIT);
		return "modules/sys/search_days";
	}

	@RequestMapping(value = {"savesearchdays"})
	public void savesearchdays(User user, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();

		try {
//    		String content = (String) request.getParameter("content");
			String searchdays = (String) request.getParameter("searchdays");

			if(FormatUtil.isNoEmpty(searchdays)){
				Global.SEARCH_DAYS_LIMIT = FormatUtil.toString(FormatUtil.toInt(searchdays));
			}else{
				Global.SEARCH_DAYS_LIMIT = "0";
			}

			System.out.println(Global.SEARCH_DAYS_LIMIT);

			map.put("status", "y");
			map.put("info", "保存成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存失败：", e);
			map.put("status", "n");
			map.put("info", "保存失败");
		}

		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	@RequestMapping(value = "fileDownloadInit")
	public String fileDownloadInit(HttpServletRequest request, HttpServletResponse response, Model model) {

		return "modules/sys/filedownload";
	}
}
