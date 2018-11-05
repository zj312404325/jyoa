/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.web;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.entity.Post;
import com.jeeplus.modules.sys.service.PostService;
import com.jeeplus.modules.sys.utils.DictUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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
import com.jeeplus.modules.checkmodel.service.CheckUserService;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;

/**
 * 绩效考核考核人Controller
 * @author cqj
 * @version 2017-10-20
 */
@Controller
@RequestMapping(value = "${adminPath}/checkmodel/checkUser")
public class CheckUserController extends BaseController {

	@Autowired
	private CheckUserService checkUserService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OfficeService officeService;

	@Autowired
	private PostService postService;
	
	@ModelAttribute
	public CheckUser get(@RequestParam(required=false) String id) {
		CheckUser entity = null;
		if (StringUtils.isNotBlank(id)){
			CheckUser cu=checkUserService.get(id);
			if(FormatUtil.isNoEmpty(cu.getCheckofficeid())){
				Post tempPost=new Post();
				Office tempOffice=new Office(cu.getCheckofficeid());
				tempPost.setOffice(tempOffice);
				List<Post> postList= postService.findList(tempPost);
				cu.setPostList(postList);
			}
			entity = cu;
		}
		if (entity == null){
			entity = new CheckUser();
		}
		return entity;
	}
	
	/**
	 * 绩效考核考核人列表页面
	 */
	@RequiresPermissions("checkmodel:checkUser:list")
	@RequestMapping(value = {"checkUserIndex"})
	public String checkUserIndex(CheckUser checkUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*if(FormatUtil.isNoEmpty(request.getParameter("checkUser.checkofficeid"))){
			checkUser.setCheckofficeid(request.getParameter("checkUser.checkofficeid"));
			checkUser.setCheckofficename(request.getParameter("checkUser.checkofficename"));
		}*/
		request.getParameter("checkofficename");
		request.setAttribute("category",request.getParameter("category"));
		return "modules/ehr/checkmodel/checkUserIndex";
	}
	
	/**
	 * 绩效考核考核人列表页面
	 */
	@RequiresPermissions("checkmodel:checkUser:list")
	@RequestMapping(value = {"list", ""})
	public String list(CheckUser checkUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		//null,0指的是绩效考核考核人的设定。1表示的是转正考核的考核人设定
		String category=request.getParameter("category");
		request.setAttribute("category",category);
		//相应考核部门下的考核人
		String officeId=request.getParameter("checkofficeid");
		request.setAttribute("checkofficeid", officeId);
		if(FormatUtil.isNoEmpty(officeId)){
			Office office=officeService.get(officeId);
			checkUser.setCheckofficename(office.getName());
			Page<CheckUser> page = checkUserService.findPage(new Page<CheckUser>(request, response), checkUser);
			model.addAttribute("page", page);
			model.addAttribute("checkUser", checkUser);
			return "modules/ehr/checkmodel/checkUserList";
		}
		return null;
	}

	/**
	 * 查看，增加，编辑绩效考核考核人表单页面
	 */
	@RequiresPermissions(value={"checkmodel:checkUser:view","checkmodel:checkUser:add","checkmodel:checkUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CheckUser checkUser, Model model,HttpServletRequest request) {
		request.setAttribute("method",request.getParameter("method"));//method=1表示查看，null或''表示新增编辑
		//考核部门id
		String checkofficeid=request.getParameter("checkofficeid");
		if(FormatUtil.isNoEmpty(checkofficeid)){
			if(!FormatUtil.isNoEmpty(checkUser.getId())){//新增
				Post tempPost=new Post();
				Office tempOffice=new Office(checkofficeid);
				tempPost.setOffice(tempOffice);
				List<Post> postList= postService.findList(tempPost);
				checkUser.setPostList(postList);
			}
			model.addAttribute("checkUser", checkUser);
			return "modules/ehr/checkmodel/checkUserForm";
		}
		return null;
	}

	/**
	 * 保存绩效考核考核人
	 */
	@RequiresPermissions(value={"checkmodel:checkUser:add","checkmodel:checkUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CheckUser checkUser, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, checkUser)){
			return form(checkUser, model,request);
		}
		if(!checkUser.getIsNewRecord()){//编辑表单保存
			CheckUser t = checkUserService.get(checkUser.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(checkUser, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			Office ofc=officeService.get(checkUser.getCheckofficeid());
			if(FormatUtil.isNoEmpty(ofc)){
				t.setCheckofficename(ofc.getName());
			}
			checkUserService.save(t);//保存
		}else{//新增表单保存
			//验证岗位是否已存在考核人
			CheckUser checkUserTemp=new CheckUser();
			checkUserTemp.setCategory(checkUser.getCategory());
			checkUserTemp.setStationId(checkUser.getStationId());
			checkUserTemp.setCheckofficeid(checkUser.getCheckofficeid());
			List<CheckUser> listCheckUser= checkUserService.findList(checkUserTemp);
			if(FormatUtil.isNoEmpty(listCheckUser)&&listCheckUser.size()>0){
				addMessage(redirectAttributes, "此岗位已设定考核人");
				return "redirect:"+Global.getAdminPath()+"/checkmodel/checkUser/checkUserIndex?repage&category="+FormatUtil.toString(checkUser.getCategory())+"&checkofficeid="+checkUser.getCheckofficeid()+"&checkofficename="+java.net.URLEncoder.encode(checkUser.getCheckofficename(),"UTF-8");
			}
			Office ofc=officeService.get(checkUser.getCheckofficeid());
			if(FormatUtil.isNoEmpty(ofc)){
				checkUser.setCheckofficename(ofc.getName());
			}
			checkUserService.save(checkUser);//保存
		}
		if(FormatUtil.isNoEmpty(checkUser.getCategory())&&checkUser.getCategory().equals("1")){
			addMessage(redirectAttributes, "保存转正考核考核人成功");
		}else{
			addMessage(redirectAttributes, "保存绩效考核考核人成功");
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkUser/checkUserIndex?repage&category="+FormatUtil.toString(checkUser.getCategory())+"&checkofficeid="+checkUser.getCheckofficeid()+"&checkofficename="+java.net.URLEncoder.encode(checkUser.getCheckofficename(),"UTF-8");
	}
	
	/**
	 * 删除绩效考核考核人
	 */
	@RequiresPermissions("checkmodel:checkUser:del")
	@RequestMapping(value = "delete")
	public String delete(CheckUser checkUser, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		checkUserService.delete(checkUser);
		addMessage(redirectAttributes, "删除绩效考核考核人成功");
		/*return "redirect:"+Global.getAdminPath()+"/checkmodel/checkUser/checkUserIndex?repage&checkUser.stationId="+checkUser.getStationId()+"&ocheckUser.stationName="+checkUser.getStationName();*/
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkUser/checkUserIndex?repage&category="+FormatUtil.toString(checkUser.getCategory())+"&checkofficeid="+checkUser.getCheckofficeid()+"&checkofficename="+java.net.URLEncoder.encode(checkUser.getCheckofficename(),"UTF-8");
	}
	
	/**
	 * 批量删除绩效考核考核人
	 */
	@RequiresPermissions("checkmodel:checkUser:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			checkUserService.delete(checkUserService.get(id));
		}
		addMessage(redirectAttributes, "删除绩效考核考核人成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkUser/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("checkmodel:checkUser:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CheckUser checkUser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "绩效考核考核人"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CheckUser> page = checkUserService.findPage(new Page<CheckUser>(request, response, -1), checkUser);
    		new ExportExcel("绩效考核考核人", CheckUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出绩效考核考核人记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkUser/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("checkmodel:checkUser:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CheckUser> list = ei.getDataList(CheckUser.class);
			for (CheckUser checkUser : list){
				try{
					checkUserService.save(checkUser);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条绩效考核考核人记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条绩效考核考核人记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入绩效考核考核人失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkUser/?repage";
    }
	
	/**
	 * 下载导入绩效考核考核人数据模板
	 */
	@RequiresPermissions("checkmodel:checkUser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "绩效考核考核人数据导入模板.xlsx";
    		List<CheckUser> list = Lists.newArrayList(); 
    		new ExportExcel("绩效考核考核人数据", CheckUser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkUser/?repage";
    }
	
	@RequestMapping(value = "getUserList")
	public void getUserList(HttpServletResponse response, HttpServletRequest request) {
		try {
			response.setContentType("text/html");
			PrintWriter writer=response.getWriter();
			Map infoMap=new HashMap<String, Object>();
            String keyword=request.getParameter("keyword");
            if(FormatUtil.isNoEmpty(keyword)){
            	Map keyMap=new HashMap<String, Object>();
            	keyMap.put("keyword", keyword);
            	List<User> userList=userDao.searchUserList(keyMap);
            	List<Map> userMapList=new ArrayList<Map>();
            	Map temp=null;
            	for (User user : userList) {
            		temp=new HashMap<String, Object>();
            		temp.put("id", user.getId());
            		temp.put("no", user.getNo());
            		temp.put("name", user.getName());

					temp.put("officename", user.getOffice().getName());
					temp.put("officeid", user.getOffice().getId());
					temp.put("stationname","");
					if(FormatUtil.isNoEmpty(user.getStationType())){
						Post post=postService.get(user.getStationType());
						if(FormatUtil.isNoEmpty(post)){
							temp.put("stationname",post.getPostname());
						}
					}
					temp.put("stationid", user.getStationType());
            		userMapList.add(temp);
				}
            	infoMap.put("status", "y");
            	infoMap.put("userList", userMapList);
            	JSONObject jsonObj=JSONObject.fromObject(infoMap);
            	writer.write(jsonObj.toString());
            	writer.close();
            }else{
            	infoMap.put("status", "n");
            	infoMap.put("info", "关键词不能为空！");
            	JSONObject jsonObj=JSONObject.fromObject(infoMap);
            	writer.write(jsonObj.toString());
            	writer.close();
            	return;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	private boolean validIsStation(String id) {
		// TODO Auto-generated method stub
		Office office= officeService.get(id);
		if(office.getType().equals("3")){//岗位
			return true;
		}
		return false;
	}

	@RequestMapping(value = "getUserById")
	public void getUserById(HttpServletResponse response, HttpServletRequest request) {
		try {
			response.setContentType("text/html");
			PrintWriter writer=response.getWriter();
			Map infoMap=new HashMap<String, Object>();
			String userid=request.getParameter("userid");
			if(FormatUtil.isNoEmpty(userid)){
				User user= userDao.get(userid);
				Map temp=null;
				if(FormatUtil.isNoEmpty(user)){
					temp=new HashMap<String, Object>();
					temp.put("id", user.getId());
					temp.put("no", user.getNo());
					temp.put("name", user.getName());

					temp.put("officename", user.getOffice().getName());
					temp.put("officeid", user.getOffice().getId());
					temp.put("stationname","");
					if(FormatUtil.isNoEmpty(user.getStationType())){
						Post post=postService.get(user.getStationType());
						if(FormatUtil.isNoEmpty(post)){
							temp.put("stationname",post.getPostname());
						}
					}
					temp.put("stationid", user.getStationType());
				}
				infoMap.put("status", "y");
				infoMap.put("user", temp);
				JSONObject jsonObj=JSONObject.fromObject(infoMap);
				writer.write(jsonObj.toString());
				writer.close();
			}else{
				infoMap.put("status", "n");
				infoMap.put("info", "用户id不能为空！");
				JSONObject jsonObj=JSONObject.fromObject(infoMap);
				writer.write(jsonObj.toString());
				writer.close();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}