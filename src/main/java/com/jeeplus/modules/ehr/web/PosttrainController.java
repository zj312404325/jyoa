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
import com.jeeplus.common.websocket.onchat.ChatServerPool;
import com.jeeplus.common.websocket.utils.Constant;
import com.jeeplus.modules.ehr.entity.Posttrain;
import com.jeeplus.modules.ehr.service.PosttrainService;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.java_websocket.WebSocket;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 岗位培训Controller
 * @author yc
 * @version 2017-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/ehr/posttrain")
public class PosttrainController extends BaseController {

	@Autowired
	private PosttrainService posttrainService;
	
	@Autowired
	private SystemService systemService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public Posttrain get(@RequestParam(required=false) String id) {
		Posttrain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = posttrainService.get(id);
		}
		if (entity == null){
			entity = new Posttrain();
		}
		return entity;
	}
	
	/**
	 * 信息列表页面
	 */
	@RequiresPermissions("ehr:posttrain:list")
	@RequestMapping(value = {"mylist"})
	public String mylist(Posttrain posttrain, HttpServletRequest request, HttpServletResponse response, Model model) {
		posttrain.setCreateBy(UserUtils.getUser());
		Page<Posttrain> page = posttrainService.findPage(new Page<Posttrain>(request, response), posttrain); 
		model.addAttribute("page", page);
		return "modules/ehr/posttrain/myposttrainList";
	}
	@RequiresPermissions("ehr:posttrain:list")
	@RequestMapping(value = {"thelist"})
	public String thelist(Posttrain posttrain, HttpServletRequest request, HttpServletResponse response, Model model) {
		User loginUser = UserUtils.getUser();
		Office myOffice = officeService.get(loginUser.getOffice().getId());
		if(FormatUtil.isNoEmpty(myOffice)){
			if(FormatUtil.isNoEmpty(myOffice.getPrimaryPerson()) && myOffice.getPrimaryPerson().getId().equals(loginUser.getId())){
				posttrain.setOffice(loginUser.getOffice().getId());
				Page<Posttrain> page = posttrainService.findPage(new Page<Posttrain>(request, response), posttrain);
				model.addAttribute("page", page);
			}
		}

//		if(BaseConst.DEPT_MANAGER_ID.equals(loginUser.getUserType())){
//			posttrain.setDepart(loginUser.getOfficeTrueId());
//			Page<Posttrain> page = posttrainService.findPage(new Page<Posttrain>(request, response), posttrain);
//			model.addAttribute("page", page);
//		}else if(BaseConst.DEPT_HEADER_ID.equals(loginUser.getUserType())){
//			posttrain.setOffice(loginUser.getOffice().getId());
//			Page<Posttrain> page = posttrainService.findPage(new Page<Posttrain>(request, response), posttrain);
//			model.addAttribute("page", page);
//		}
		return "modules/ehr/posttrain/theposttrainList";
	}
	@RequestMapping(value = {"managerlist"})
	public String managerlist(Posttrain posttrain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Posttrain> page = posttrainService.findPage(new Page<Posttrain>(request, response), posttrain); 
		model.addAttribute("page", page);
		return "modules/ehr/posttrain/posttrainManagerList";
	}
	@RequiresPermissions("ehr:posttrain:list")
	@RequestMapping(value = {"list", ""})
	public String list(Posttrain posttrain, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/ehr/posttrain/posttrainList";
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
	@RequiresPermissions(value={"ehr:posttrain:view","ehr:posttrain:add","ehr:posttrain:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Posttrain posttrain, Model model) {
		model.addAttribute("posttrain", posttrain);
		return "modules/ehr/posttrain/posttrainForm";
	}
	@RequiresPermissions(value={"ehr:posttrain:view"})
	@RequestMapping(value = "view")
	public String view(Posttrain posttrain, Model model) {
		model.addAttribute("posttrain", posttrain);
		return "modules/ehr/posttrain/posttrainView";
	}

	/**
	 * 保存信息
	 */
	@RequiresPermissions(value={"ehr:posttrain:add","ehr:posttrain:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Posttrain posttrain, Model model, RedirectAttributes redirectAttributes,final HttpServletRequest request, final HttpServletResponse response) throws Exception{
		if (!beanValidator(model, posttrain)){
			return form(posttrain, model);
		}
		if(!posttrain.getIsNewRecord()){//编辑表单保存
			Posttrain t = posttrainService.get(posttrain.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(posttrain, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			posttrainService.save(t);//保存
		}else{//新增表单保存
			User loginUser = UserUtils.getUser();
			posttrain.setCompany(loginUser.getCompany().getId());
			posttrain.setDepart(loginUser.getOfficeTrueId());
			posttrain.setOffice(loginUser.getOffice().getId());
			posttrain.setTrainer(loginUser.getName());
			posttrainService.save(posttrain);//保存
			
//			List<User> deptManagerlist = systemService.findUserByDeptOfficeRole(loginUser.getOfficeTrueId(),null,null, BaseConst.DEPT_MANAGER_ID);
//			List<User> deptHeaderlist = systemService.findUserByDeptOfficeRole(null,loginUser.getOffice().getId(),null, BaseConst.DEPT_HEADER_ID);
//			deptManagerlist.addAll(deptHeaderlist);

			List<User> sendUserlist = new ArrayList<User>();
			Office myOffice = officeService.get(loginUser.getOffice().getId());
			if(FormatUtil.isNoEmpty(myOffice)){
				if(FormatUtil.isNoEmpty(myOffice.getPrimaryPerson())){
					sendUserlist.add(UserUtils.get(myOffice.getPrimaryPerson().getId()));
				}
			}
			final String ctx = request.getContextPath();
			final String posttrainid = posttrain.getId();
			final String loginUsername = loginUser.getName();
			for (User manager : sendUserlist) {
				if(!loginUser.getId().equals(manager.getId())){//自己是主管、经理时不用推送给自己
					final User user = manager;
					final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(user.getLoginName());
					if(toUserConn != null){
						Thread t = new Thread(new Runnable(){
				            public void run(){  
				            	
				            	try {
					            	Map map = new HashMap();
					        		map.put("cnt1", "-1");
					        		map.put("cnt2", "-1");
					        		map.put("cnt3", "-1");
					        		
									List<Map> lst = new ArrayList<Map>();
					    			Map maptemp = new HashMap();
					    			maptemp.put("url", ctx+adminPath+"/ehr/posttrain/view?id="+posttrainid);
					    			maptemp.put("title", loginUsername+"提交了新的岗位培训信息,请注意查收！");
					    			lst.add(maptemp);
					    			map.put("msg", JSONArray.fromObject(lst).toString());
					        		String msg = JSONObject.fromObject(map).toString();
					        		
									String message = Constant._remind_window_+msg;
									ChatServerPool.sendMessageToUser(toUserConn,message);
				            	} catch (Exception e) {
					                   e.printStackTrace();
					            }
				            	
				            }
						});
						t.start();
					}
				}
			}
			
		}
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/posttrain/?repage";
	}
	
	/**
	 * 删除信息
	 */
	@RequiresPermissions("ehr:posttrain:del")
	@RequestMapping(value = "delete")
	public String delete(Posttrain posttrain, RedirectAttributes redirectAttributes) {
		posttrainService.delete(posttrain);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/posttrain/?repage";
	}
	
	/**
	 * 批量删除信息
	 */
	@RequiresPermissions("ehr:posttrain:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			posttrainService.delete(posttrainService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/posttrain/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ehr:posttrain:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Posttrain posttrain, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Posttrain> page = posttrainService.findPage(new Page<Posttrain>(request, response, -1), posttrain);
    		new ExportExcel("信息", Posttrain.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/posttrain/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ehr:posttrain:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Posttrain> list = ei.getDataList(Posttrain.class);
			for (Posttrain posttrain : list){
				try{
					posttrainService.save(posttrain);
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
		return "redirect:"+Global.getAdminPath()+"/ehr/posttrain/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
	@RequiresPermissions("ehr:posttrain:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<Posttrain> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", Posttrain.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/posttrain/?repage";
    }
	
	
	

}