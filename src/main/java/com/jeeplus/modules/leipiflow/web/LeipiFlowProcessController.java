/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import net.sf.json.JSONArray;
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
import com.jeeplus.modules.flow.entity.Flowtemplate;
import com.jeeplus.modules.leipiflow.entity.LeipiFlow;
import com.jeeplus.modules.leipiflow.entity.LeipiFlowProcess;
import com.jeeplus.modules.leipiflow.entity.LeipiRun;
import com.jeeplus.modules.leipiflow.service.LeipiFlowProcessService;
import com.jeeplus.modules.leipiflow.service.LeipiFlowService;
import com.jeeplus.modules.leipiflow.service.LeipiRunService;
import com.jeeplus.modules.sys.utils.DictUtils;

/**
 * 流程步骤Controller
 * @author 陈钱江
 * @version 2017-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/leipiflow/leipiFlowProcess")
public class LeipiFlowProcessController extends BaseController {

	@Autowired
	private LeipiFlowProcessService leipiFlowProcessService;
	@Autowired
	private LeipiFlowService leipiFlowService;
	@Autowired
	private LeipiRunService leipiRunService;
	
	@ModelAttribute
	public LeipiFlowProcess get(@RequestParam(required=false) String id) {
		LeipiFlowProcess entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leipiFlowProcessService.get(id);
		}
		if (entity == null){
			entity = new LeipiFlowProcess();
		}
		return entity;
	}
	
	/**
	 * 流程步骤列表页面
	 */
	@RequiresPermissions("leipiflow:leipiFlowProcess:list")
	@RequestMapping(value = {"list", ""})
	public String list(LeipiFlowProcess leipiFlowProcess, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LeipiFlowProcess> page = leipiFlowProcessService.findPage(new Page<LeipiFlowProcess>(request, response), leipiFlowProcess); 
		model.addAttribute("page", page);
		return "modules/leipiflow/leipiFlowProcessList";
	}

	/**
	 * 查看，增加，编辑流程步骤表单页面
	 */
	@RequiresPermissions(value={"leipiflow:leipiFlowProcess:view","leipiflow:leipiFlowProcess:add","leipiflow:leipiFlowProcess:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LeipiFlowProcess leipiFlowProcess, Model model) {
		model.addAttribute("leipiFlowProcess", leipiFlowProcess);
		return "modules/leipiflow/leipiFlowProcessForm";
	}

	/**
	 * 保存流程步骤
	 */
	@RequiresPermissions(value={"leipiflow:leipiFlowProcess:add","leipiflow:leipiFlowProcess:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LeipiFlowProcess leipiFlowProcess, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, leipiFlowProcess)){
			return form(leipiFlowProcess, model);
		}
		if(!leipiFlowProcess.getIsNewRecord()){//编辑表单保存
			LeipiFlowProcess t = leipiFlowProcessService.get(leipiFlowProcess.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(leipiFlowProcess, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			leipiFlowProcessService.save(t);//保存
		}else{//新增表单保存
			leipiFlowProcessService.save(leipiFlowProcess);//保存
		}
		addMessage(redirectAttributes, "保存流程步骤成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowProcess/?repage";
	}
	
	/**
	 * 删除流程步骤
	 */
	@RequiresPermissions("leipiflow:leipiFlowProcess:del")
	@RequestMapping(value = "delete")
	public String delete(LeipiFlowProcess leipiFlowProcess, RedirectAttributes redirectAttributes) {
		leipiFlowProcessService.delete(leipiFlowProcess);
		addMessage(redirectAttributes, "删除流程步骤成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowProcess/?repage";
	}
	
	/**
	 * 批量删除流程步骤
	 */
	@RequiresPermissions("leipiflow:leipiFlowProcess:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leipiFlowProcessService.delete(leipiFlowProcessService.get(id));
		}
		addMessage(redirectAttributes, "删除流程步骤成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowProcess/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("leipiflow:leipiFlowProcess:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LeipiFlowProcess leipiFlowProcess, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流程步骤"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeipiFlowProcess> page = leipiFlowProcessService.findPage(new Page<LeipiFlowProcess>(request, response, -1), leipiFlowProcess);
    		new ExportExcel("流程步骤", LeipiFlowProcess.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出流程步骤记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowProcess/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("leipiflow:leipiFlowProcess:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeipiFlowProcess> list = ei.getDataList(LeipiFlowProcess.class);
			for (LeipiFlowProcess leipiFlowProcess : list){
				try{
					leipiFlowProcessService.save(leipiFlowProcess);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条流程步骤记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条流程步骤记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入流程步骤失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowProcess/?repage";
    }
	
	/**
	 * 下载导入流程步骤数据模板
	 */
	@RequiresPermissions("leipiflow:leipiFlowProcess:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流程步骤数据导入模板.xlsx";
    		List<LeipiFlowProcess> list = Lists.newArrayList(); 
    		new ExportExcel("流程步骤数据", LeipiFlowProcess.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlowProcess/?repage";
    }
	
	
	/**
	 * 保存模板创建
	 */
	@RequestMapping(value = "saveProcess", method=RequestMethod.POST)
	public void saveProcess(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
    		String flow_id = (String) request.getParameter("flow_id");
    		String left = (String) request.getParameter("left");
    		String top = (String) request.getParameter("top");
    		
    		List<LeipiFlowProcess> lstProcess=leipiFlowProcessService.findByProperty("flow_id", flow_id);
    		String process_type="is_step";
    		if(lstProcess.size()<=0){
    			process_type="is_one";
    		}
    		
    		LeipiFlowProcess lfp=new LeipiFlowProcess();
    		lfp.setFlowId(flow_id);
    		lfp.setProcessName("新建步骤");
    		lfp.setProcessType(process_type);
    		lfp.setChildAfter(1);
    		lfp.setAutoUnlock(1);
    		left=left.replace("px", "");
    		top=top.replace("px", "");
    		lfp.setStyle("width:121px;color:#0e76a8;left:"+FormatUtil.toInteger(left)+"px;top:"+FormatUtil.toInteger(top)+"px;");
    		lfp.setSetleft(FormatUtil.toInteger(left));
    		lfp.setSettop(FormatUtil.toInteger(top));
    		lfp.setIsdel("0");
			lfp.setParallel(1);
    		leipiFlowProcessService.save(lfp);
    		
        	map.put("status", 1);
			map.put("msg", "success");
			String info="{\"id\":\""+lfp.getId()+"\",\"flow_id\":\""+lfp.getFlowId()+"\",\"process_name\":\""+FormatUtil.toString(lfp.getProcessName())+"\",\"process_to\":\""+FormatUtil.toString(lfp.getProcessTo())+"\",\"icon\":\"glyphicon-ok\",\"style\":\""+lfp.getStyle()+"\"}";
			map.put("info", JSONObject.fromObject(info));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("模板保存失败：", e);
			map.put("status", 0);
			map.put("msg", "添加失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	/**
	 * 保存设计
	 */
	@RequestMapping(value = "saveDesign", method=RequestMethod.POST)
	public void saveDesign(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
    		String flow_id = (String) request.getParameter("flow_id");
    		String process_info = (String) request.getParameter("process_info");
    		
    		LeipiFlow leipiFlow=leipiFlowService.findUniqueByProperty("id", flow_id);
    		if(!FormatUtil.isNoEmpty(leipiFlow)||leipiFlow.getIsdel()!=0){
    			map.put("status", 0);
    			map.put("msg", "未找到流程数据");
    		}else{
    			//验证此流程是否正在执行
        		if(checkRunFlowExist(flow_id)){
        			map.put("status", 0);
        			map.put("msg", "此流程存在正在执行的实例！");
        			out.write(JSONObject.fromObject(map).toString());
        			out.close();
        			return;
        		}
    			
    			Map<String, Object> map2 = new HashMap<String, Object>();
        		map2 = JSONObject.fromObject(process_info);
        		
        		if(checkValidProcessTo(map2)){
        			map.put("status", 0);
        			map.put("msg", "步骤不能有多个指向！");
        			out.write(JSONObject.fromObject(map).toString());
        			out.close();
        			return;
        		}
        		
    			for (String key : map2.keySet()) {
					System.out.println(key+":"+map2.get(key));
					LeipiFlowProcess leipiFlowProcess= leipiFlowProcessService.get(key);
					Map<String, Object> map3 = new HashMap<String, Object>();
					map3 = JSONObject.fromObject(map2.get(key));
					leipiFlowProcess.setSettop(FormatUtil.toInteger(map3.get("top")));
					leipiFlowProcess.setSetleft(FormatUtil.toInteger(map3.get("left")));
					String process=FormatUtil.toString(map3.get("process_to")).replace("[", "").replace("]", "").replaceAll("\"", "");
					leipiFlowProcess.setProcessTo(process);
					leipiFlowProcess.setUpdatetime(new Date());
					leipiFlowProcess.setStyle("width:"+leipiFlowProcess.getStyleWidth()+"px;height:"+leipiFlowProcess.getStyleHeight()+"px;color:#0e76a8;left:"+leipiFlowProcess.getSetleft()+"px;top:"+leipiFlowProcess.getSettop()+"px;");
					leipiFlowProcessService.save(leipiFlowProcess);
				}
    			
            	map.put("status", 1);
    			map.put("msg", "success");
//    			String info="{\"id\":\""+lfp.getId()+"\",\"flow_id\":\""+lfp.getFlow_id()+"\",\"process_name\":\""+FormatUtil.toString(lfp.getProcess_name())+"\",\"process_to\":\""+FormatUtil.toString(lfp.getProcess_to())+"\",\"icon\":\"glyphicon-ok\",\"style\":\""+lfp.getStyle()+"\"}";
//    			map.put("info", JSONObject.fromObject(info));
    		}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("模板保存失败：", e);
			map.put("status", 0);
			map.put("msg", "添加失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	/**
	 * 验证步骤是否指向多个步骤
	 * @param map2
	 * @return boolean true指向多个步骤false指向单个步骤
	 */
	private boolean checkValidProcessTo(Map<String, Object> map2) {
		if(FormatUtil.isNoEmpty(map2)){
			for (String key : map2.keySet()) {
				System.out.println(key+":"+map2.get(key));
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3 = JSONObject.fromObject(map2.get(key));
				String process=FormatUtil.toString(map3.get("process_to")).replace("[", "").replace("]", "").replaceAll("\"", "");
				if(FormatUtil.isNoEmpty(process)){
					if(process.contains(",")){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 删除步骤
	 */
	@RequestMapping(value = "delProcess", method=RequestMethod.POST)
	public void delProcess(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
    		//步骤id
    		String process_id = (String) request.getParameter("process_id");
    		//流程id
    		String flow_id = (String) request.getParameter("flow_id");
    		
    		//验证此流程是否正在执行
    		if(checkRunFlowExist(flow_id)){
    			map.put("status", 0);
    			map.put("msg", "此流程存在正在执行的实例！");
    			out.write(JSONObject.fromObject(map).toString());
    			out.close();
    			return;
    		}
    		
    		LeipiFlowProcess lfp=new LeipiFlowProcess(process_id);
    		lfp.setFlowId(flow_id);
    		//删除步骤
    		leipiFlowProcessService.delete(lfp);
    		map.put("status", 1);
			map.put("msg", "删除成功");
    		
		} catch (Exception e) {
			// TODO: handle exception
			map.put("status", 0);
			map.put("msg", "删除失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	/**
	 * 判断是否存在正在运行的实例
	 * @param flowid
	 * @return
	 */
	private boolean checkRunFlowExist(String flowId) {
		LeipiRun leipiRunTemp=new LeipiRun();
		leipiRunTemp.setFlowId(flowId);
		leipiRunTemp.setStatus(0);
		List<LeipiRun> leipiRunList = leipiRunService.findList(leipiRunTemp);//从数据库取出记录的值
		if(FormatUtil.isNoEmpty(leipiRunList)&&leipiRunList.size()>0){
			return true;
		}
		return false;
	}

	@RequestMapping(value = "goDesignAttribute")
	public String goDesignAttribute(HttpServletRequest request, HttpServletResponse response,  Model model) {
		
		String procid = request.getParameter("id");
		LeipiFlowProcess process = leipiFlowProcessService.get(procid);
		LeipiFlow flow = leipiFlowService.get(process.getFlowId());
		
		List<LeipiFlowProcess> process_to_list = leipiFlowProcessService.findList(new LeipiFlow(process.getFlowId()));
		
		request.setAttribute("one", process);
		request.setAttribute("oneTos", FormatUtil.isNoEmpty(process.getProcessTo())?process.getProcessTo():null);
		request.setAttribute("process_to_list", process_to_list);
		
		List<LeipiFlowProcess> process_to_list_left = new ArrayList<LeipiFlowProcess>();
		List<LeipiFlowProcess> process_to_list_right = new ArrayList<LeipiFlowProcess>();
		if(FormatUtil.isNoEmpty(process.getProcessTo())){
			for (LeipiFlowProcess leipiFlowProcess : process_to_list) {
				if(process.getProcessTo().contains(leipiFlowProcess.getId())){
					process_to_list_left.add(leipiFlowProcess);
				}else{
					process_to_list_right.add(leipiFlowProcess);
				}
			}
		}else{
			process_to_list_right = process_to_list;
		}
		request.setAttribute("process_to_list_left", process_to_list_left);
		request.setAttribute("process_to_list_right", process_to_list_right);
		
		return "modules/leipiflow/attribute";
	}
	
	/**
	 * 保存属性
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "saveAttribute")
	public void saveAttribute(HttpServletRequest request, HttpServletResponse response,  Model model) throws Exception {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();
		try {
			String flow_id = request.getParameter("flow_id");
			//验证此流程是否正在执行
			if(checkRunFlowExist(flow_id)){
				map.put("status", 0);
				map.put("msg", "此流程存在正在执行的实例！");
				out.write(JSONObject.fromObject(map).toString());
				out.close();
				return;
			}
			String parallel=request.getParameter("parallel");
			String process_id = request.getParameter("process_id");
			String process_name = request.getParameter("process_name");
			String process_type = request.getParameter("process_type");
			String[] process_to_arr = request.getParameterValues("process_to");
			String process_to = "";
			if(FormatUtil.isNoEmpty(process_to_arr)&&process_to_arr.length>0){
				process_to = StringUtils.join(process_to_arr, ",");
			}
			String write_fields = request.getParameter("write_fields");
			String secret_fields = request.getParameter("secret_fields");
			String auto_person = request.getParameter("auto_person");//自动选人
			String auto_unlock = request.getParameter("auto_unlock");//预先设置自动选人，更方便转交工作
			String auto_sponsor_ids = request.getParameter("auto_sponsor_ids");//指定主办人
			String auto_sponsor_text = request.getParameter("auto_sponsor_text");
			String auto_respon_ids = request.getParameter("auto_respon_ids");//指定经办人
			String auto_respon_text = request.getParameter("auto_respon_text");
			String auto_role_ids = request.getParameter("auto_role_ids");//指定角色
			String auto_role_text = DictUtils.getDictLabel(auto_role_ids, "sys_user_type", null);
			String range_user_ids = request.getParameter("range_user_ids");//授权人员
			String range_user_text = request.getParameter("range_user_text");
			String range_dept_ids = request.getParameter("range_dept_ids");//授权部门
			String range_dept_text = request.getParameter("range_dept_text");
			String range_role_ids = request.getParameter("range_role_ids");//授权角色
			String range_role_text = DictUtils.getDictLabel(range_role_ids, "sys_user_type", null);
			
			String receive_type = request.getParameter("receive_type");//交接方式
			String is_user_end = request.getParameter("is_user_end");//允许主办人办结流程
			String is_userop_pass = request.getParameter("is_userop_pass");//经办人可以转交下一步
			String is_sing = request.getParameter("is_sing");//会签方式
			String sign_look = request.getParameter("sign_look");//可见性
			String is_back = request.getParameter("is_back");//回退方式
			String process_condition = request.getParameter("process_condition");//转出条件
			String style_width = request.getParameter("style_width");
			String style_height = request.getParameter("style_height");
			String style_color = request.getParameter("style_color");
			String style_icon = request.getParameter("style_icon");
			
			//获取步骤
			LeipiFlowProcess lfp=leipiFlowProcessService.get(process_id);
			if(FormatUtil.isNoEmpty(process_name)){
				lfp.setProcessName(process_name);
			}
			if(FormatUtil.isNoEmpty(parallel)){
				lfp.setParallel(FormatUtil.toInteger(parallel));
			}else{
				lfp.setParallel(0);
			}
			if(FormatUtil.isNoEmpty(process_type)){
				lfp.setProcessType(process_type);
			}
			lfp.setProcessTo(process_to);
			if(FormatUtil.isNoEmpty(write_fields)){
				lfp.setWriteFields(write_fields);
			}
			if(FormatUtil.isNoEmpty(secret_fields)){
				lfp.setSecretFields(secret_fields);
			}
			if(FormatUtil.isNoEmpty(auto_person)){
				lfp.setAutoPerson(FormatUtil.toInteger(auto_person));
			}
			if(FormatUtil.isNoEmpty(auto_unlock)){
				lfp.setAutoUnlock(FormatUtil.toInteger(auto_unlock));
			}
			if(FormatUtil.isNoEmpty(auto_sponsor_ids)){
				lfp.setAutoSponsorIds(auto_sponsor_ids);
			}
			if(FormatUtil.isNoEmpty(auto_sponsor_text)){
				lfp.setAutoSponsorText(auto_sponsor_text);
			}
			if(FormatUtil.isNoEmpty(auto_respon_ids)){
				lfp.setAutoResponIds(auto_respon_ids);
			}
			if(FormatUtil.isNoEmpty(auto_respon_text)){
				lfp.setAutoResponText(auto_respon_text);
			}
			if(FormatUtil.isNoEmpty(auto_role_ids)){
				lfp.setAutoRoleIds(auto_role_ids);
			}
			if(FormatUtil.isNoEmpty(auto_role_text)){
				lfp.setAutoRoleText(auto_role_text);
			}
			if(FormatUtil.isNoEmpty(range_user_ids)){
				lfp.setRangeUserIds(range_user_ids);
			}
			if(FormatUtil.isNoEmpty(range_user_text)){
				lfp.setRangeUserText(range_user_text);
			}
			if(FormatUtil.isNoEmpty(range_dept_ids)){
				lfp.setRangeDeptIds(range_dept_ids);
			}
			if(FormatUtil.isNoEmpty(range_dept_text)){
				lfp.setRangeDeptText(range_dept_text);
			}
			if(FormatUtil.isNoEmpty(range_role_ids)){
				lfp.setRangeRoleIds(range_role_ids);
			}
			if(FormatUtil.isNoEmpty(range_role_text)){
				lfp.setRangeRoleText(range_role_text);
			}
			if(FormatUtil.isNoEmpty(receive_type)){
				lfp.setReceiveType(FormatUtil.toInteger(receive_type));
			}
			if(FormatUtil.isNoEmpty(is_user_end)){
				lfp.setIsUserEnd(FormatUtil.toInteger(is_user_end));
			}
			if(FormatUtil.isNoEmpty(is_userop_pass)){
				lfp.setIsUseropPass(FormatUtil.toInteger(is_userop_pass));
			}
			
			if(FormatUtil.isNoEmpty(is_sing)){
				lfp.setIsSing(FormatUtil.toInteger(is_sing));
			}
			if(FormatUtil.isNoEmpty(sign_look)){
				lfp.setSignLook(FormatUtil.toInteger(sign_look));
			}
			if(FormatUtil.isNoEmpty(is_back)){
				lfp.setIsBack(FormatUtil.toInteger(is_back));
			}
			if(FormatUtil.isNoEmpty(process_condition)){
				lfp.setOutCondition(process_condition);
			}
			
			String style="";
			if(FormatUtil.isNoEmpty(style_width)){
				lfp.setStyleWidth(style_width);
				style+="width:"+style_width+"px;";
			}else{
				style+="width:121px;";
			}
			if(FormatUtil.isNoEmpty(style_height)){
				lfp.setStyleHeight(style_height);
				style+="height:"+style_height+"px;";
			}else{
				style+="height:41px";
			}
			if(FormatUtil.isNoEmpty(style_color)){
				lfp.setStyleColor(style_color);
				style+="color:"+style_color+";";
			}else{
				style+="color:#0e76a8";
			}
			if(FormatUtil.isNoEmpty(style_icon)){
				lfp.setStyleIcon(style_icon);
			}
			style+="left:"+lfp.getSetleft()+"px;"+"top:"+lfp.getSettop()+"px;";
			lfp.setStyle(style);
			
			leipiFlowProcessService.saveAttr(lfp);
			
    		map.put("status", 1);
			map.put("msg", "保存成功");
    		
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			map.put("status", 0);
			map.put("msg", "保存失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
		
	}
}