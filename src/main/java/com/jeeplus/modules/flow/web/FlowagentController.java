/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.act.dao.ActDao;
import com.jeeplus.modules.flow.entity.Flowagent;
import com.jeeplus.modules.flow.service.FlowagentService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 流程代理Controller
 * @author cqj
 * @version 2016-12-16
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/flowagent")
public class FlowagentController extends BaseController {

	@Autowired
	private ActDao actDao;
	@Autowired
	private FlowagentService flowagentService;
	@Autowired
	private SystemService systemService;
	@ModelAttribute
	public Flowagent get(@RequestParam(required=false) String id) {
		Flowagent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = flowagentService.get(id);
		}
		if (entity == null){
			entity = new Flowagent();
		}
		return entity;
	}
	
	/**
	 * 流程代理列表页面
	 */
	@RequiresPermissions("flow:flowagent:list")
	@RequestMapping(value = {"list", ""})
	public String list(Flowagent flowagent, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<Flowagent> page = flowagentService.findPage(new Page<Flowagent>(request, response), flowagent); 
		List<Flowagent> lst = flowagentService.findList(flowagent);
		if(lst.size() > 0){
			model.addAttribute("flowagent", lst.get(0));
		}
		
		return "modules/flow/flowagentList";
	}

	/**
	 * 查看，增加，编辑流程代理表单页面
	 */
	@RequiresPermissions(value={"flow:flowagent:view","flow:flowagent:add","flow:flowagent:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Flowagent flowagent, Model model) {
		model.addAttribute("flowagent", flowagent);
		return "modules/flow/flowagentForm";
	}

	/**
	 * 保存流程代理
	 */
	@RequiresPermissions("flow:flowagent:list")
	@RequestMapping(value = "save")
	public String save(Flowagent flowagent, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, flowagent)){
			return form(flowagent, model);
		}
		if(!flowagent.getIsNewRecord()){//编辑表单保存
			//判断是否是互为代理的情况
			if(FormatUtil.isNoEmpty(flowagent.getAgentuserid())){
				Flowagent flow=flowagentService.findUniqueByProperty("create_by", flowagent.getAgentuserid());
				if(FormatUtil.isNoEmpty(flow)){
//					if((flow.getAgentuserid().toString()).equals(flowagent.getCreateBy().toString())){
//						addMessage(redirectAttributes, "不能互为代理！");
//						return "redirect:"+Global.getAdminPath()+"/flow/flowagent/?repage";
//					}
					addMessage(redirectAttributes, "代理失败！你申请的代理人也申请了代理！");
					return "redirect:"+Global.getAdminPath()+"/flow/flowagent/?repage";
				}
			}
			
			Flowagent t = flowagentService.get(flowagent.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(flowagent, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			flowagentService.save(t);//保存
			
			//更新
			//查找当前运行的流程
			//插入act_ru_identitylink
			String username = flowagent.getAgentedusername();
			User u=UserUtils.get(t.getAgentuserid());
			setIdentitylinkForAgent(username,u.getLoginName());

			/*List<Map> listmap=actDao.findByPropertyMap("assignee_", username);
			String ids="";
			for (Map map : listmap) {
				ids+="'"+map.get("ID_")+"'"+",";
			}
			if(FormatUtil.isNoEmpty(ids)){
				ids=ids.substring(0, ids.length()-1);
				Map map=new HashMap<String, Object>();
				map.put("assignee", username);
				map.put("agent", flowagent.getAgentusername());
				map.put("createby", username);
				//修改run
				actDao.updateRunTask(map);
				//修改hi
				Map mapTemp=new HashMap<String, Object>();
				mapTemp.put("ids", ids);
				mapTemp.put("agent", flowagent.getAgentusername());
				actDao.updateHiTask(mapTemp);
				//修改act
				Map mapTemp1=new HashMap<String, Object>();
				mapTemp1.put("ids", ids);
				mapTemp1.put("agent", flowagent.getAgentusername());
				actDao.updateActTask(mapTemp1);
			}*/
		}else{//新增表单保存
			if(!FormatUtil.isNoEmpty(flowagent.getAgentuserid())){
				addMessage(redirectAttributes, "请填写代理人！");
				return "redirect:"+Global.getAdminPath()+"/flow/flowagent";
			}
			
			//判断是否是互为代理的情况
			if(FormatUtil.isNoEmpty(flowagent.getAgentuserid())){
				Flowagent flow=flowagentService.findUniqueByProperty("create_by", flowagent.getAgentuserid());
				if(FormatUtil.isNoEmpty(flow)){
//					if(flow.getAgentuserid().equals(flowagent.getCurrentUser().getId())){
//						addMessage(redirectAttributes, "不能互为代理！");
//						return "redirect:"+Global.getAdminPath()+"/flow/flowagent/?repage";
//					}
					addMessage(redirectAttributes, "代理失败！你申请的代理人也申请了代理！");
					return "redirect:"+Global.getAdminPath()+"/flow/flowagent/?repage";
				}
			}
			
			User u = systemService.getUser(flowagent.getAgentuserid());
			flowagent.setAgentusername(u.getLoginName());
			flowagent.setAgentedusername(flowagent.getCurrentUser().getLoginName());
			flowagentService.save(flowagent);//保存
			
			//更新
			//查找当前运行的流程
			//插入act_ru_identitylink
			String username = flowagent.getAgentedusername();
			setIdentitylink(username,flowagent.getAgentusername());

			/*List<Map> listmap=actDao.findByPropertyMap("assignee_", username);
			String ids="";
			for (Map map : listmap) {
				ids+="'"+map.get("ID_")+"'"+",";
			}
			if(FormatUtil.isNoEmpty(ids)){
				ids=ids.substring(0, ids.length()-1);
				Map map=new HashMap<String, Object>();
				map.put("assignee", username);
				map.put("agent", flowagent.getAgentusername());
				map.put("createby", username);
				//修改run
				actDao.updateRunTask(map);
				//修改hi
				Map mapTemp=new HashMap<String, Object>();
				mapTemp.put("ids", ids);
				mapTemp.put("agent", flowagent.getAgentusername());
				actDao.updateHiTask(mapTemp);
				//修改act
				Map mapTemp1=new HashMap<String, Object>();
				mapTemp1.put("ids", ids);
				mapTemp1.put("agent", flowagent.getAgentusername());
				actDao.updateActTask(mapTemp1);
			}*/
		}
		addMessage(redirectAttributes, "保存流程代理成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowagent/?repage";
	}
	
	//新增
	private void setIdentitylink(String username, String agentusername) {
		// TODO Auto-generated method stub
		Map<String,Object> mapAgent=new HashMap<String, Object>();
		mapAgent.put("agented", username);
		mapAgent.put("agent", agentusername);
		actDao.saveIdentitylink(mapAgent);
		actDao.updateIdentitylink(mapAgent);
	}
	//修改
	private void setIdentitylinkForAgent(String username, String agentusername) {
		// TODO Auto-generated method stub
		Map<String,Object> mapAgent=new HashMap<String, Object>();
		mapAgent.put("agentcreateby", username);
		mapAgent.put("userid", agentusername);
		actDao.updateIdentitylinkForAgent(mapAgent);
	}
	
	//删除
	private void delIdentitylinkForAgent(String username) {
		// TODO Auto-generated method stub
		Map<String,Object> mapAgent=new HashMap<String, Object>();
		mapAgent.put("agentcreateby", username);
		actDao.deleteIdentitylinkForAgent(mapAgent);
		mapAgent.put("isagent", "1");
		mapAgent.put("userid", username);
		actDao.updateIdentitylinkForDel(mapAgent);
	}

	/**
	 * 删除流程代理
	 */
	@RequiresPermissions("flow:flowagent:list")
	@RequestMapping(value = "delete")
	public String delete(Flowagent flowagent,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String id=request.getParameter("id");
		flowagent=flowagentService.get(id);
		String username = flowagent.getAgentusername();
		flowagent.setCreateBy(UserUtils.getUser());
		flowagentService.delete(flowagent);
		addMessage(redirectAttributes, "取消代理成功");
		
		//删除已代理信息
		delIdentitylinkForAgent(UserUtils.getUser().getLoginName());
		
		//更新
		//查找当前运行的流程
		//List<Map> listmap=actDao.findByPropertyMap("assignee_", username);
		/*Map<String,Object> mp=new HashMap<String, Object>();
		mp.put("assignee", username);
		mp.put("createby", UserUtils.getUser().getLoginName());
		List<Map> listmap=actDao.findActByMap(mp);
		String ids="";
		for (Map map : listmap) {
			ids+="'"+map.get("ID_")+"'"+",";
		}
		if(FormatUtil.isNoEmpty(ids)){
			ids=ids.substring(0, ids.length()-1);
			Map map=new HashMap<String, Object>();
			map.put("assignee", username);
			map.put("agent", flowagent.getAgentedusername());
			//修改run
			actDao.updateRunTask(map);
			//修改hi
			Map mapTemp=new HashMap<String, Object>();
			mapTemp.put("ids", ids);
			mapTemp.put("agent", flowagent.getAgentedusername());
			actDao.updateHiTask(mapTemp);
			//修改act
			Map mapTemp1=new HashMap<String, Object>();
			mapTemp1.put("ids", ids);
			mapTemp1.put("agent", flowagent.getAgentedusername());
			actDao.updateActTask(mapTemp1);
		}*/
		
		return "redirect:"+Global.getAdminPath()+"/flow/flowagent";
	}
	
	/**
	 * 批量删除流程代理
	 */
	@RequiresPermissions("flow:flowagent:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			flowagentService.delete(flowagentService.get(id));
		}
		addMessage(redirectAttributes, "删除流程代理成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowagent/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("flow:flowagent:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Flowagent flowagent, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流程代理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Flowagent> page = flowagentService.findPage(new Page<Flowagent>(request, response, -1), flowagent);
    		new ExportExcel("流程代理", Flowagent.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出流程代理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/flow/flowagent/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("flow:flowagent:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Flowagent> list = ei.getDataList(Flowagent.class);
			for (Flowagent flowagent : list){
				try{
					flowagentService.save(flowagent);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条流程代理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条流程代理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入流程代理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/flow/flowagent/?repage";
    }
	
	/**
	 * 下载导入流程代理数据模板
	 */
	@RequiresPermissions("flow:flowagent:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流程代理数据导入模板.xlsx";
    		List<Flowagent> list = Lists.newArrayList(); 
    		new ExportExcel("流程代理数据", Flowagent.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/flow/flowagent/?repage";
    }
	
	
	

}