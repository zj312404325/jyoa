/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.flow.entity.Flowtemplate;
import com.jeeplus.modules.flow.entity.Templatecontrol;
import com.jeeplus.modules.flow.service.FlowtemplateService;
import net.sf.json.JSONArray;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板主表Controller
 * @author cqj
 * @version 2016-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/flowtemplate")
public class FlowtemplateController extends BaseController {

	@Autowired
	private FlowtemplateService flowtemplateService;
	
	@ModelAttribute
	public Flowtemplate get(@RequestParam(required=false) String id) {
		Flowtemplate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = flowtemplateService.get(id);
		}
		if (entity == null){
			entity = new Flowtemplate();
		}
		return entity;
	}
	
	/**
	 * 模板创建列表页面
	 */
	@RequiresPermissions("flow:flowtemplate:list")
	@RequestMapping(value = {"list", ""})
	public String list(Flowtemplate flowtemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(FormatUtil.isNoEmpty(request.getParameter("templatetype"))){
			flowtemplate.setTemplatetype(FormatUtil.toInteger(request.getParameter("templatetype")));
		}
		Page<Flowtemplate> page = flowtemplateService.findPage(new Page<Flowtemplate>(request, response), flowtemplate);
		model.addAttribute("page", page);
		model.addAttribute("templatetype", flowtemplate.getTemplatetype());
		return "modules/flow/flowtemplateList";
	}

	/**
	 * 查看，增加，编辑模板创建表单页面
	 */
	@RequiresPermissions(value={"flow:flowtemplate:view","flow:flowtemplate:add","flow:flowtemplate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Flowtemplate flowtemplate,HttpServletRequest request, HttpServletResponse response, Model model) {
		String tab = request.getParameter("tab");
		request.setAttribute("tab", tab);
		flowtemplate.setTemplatetype(FormatUtil.toInteger(request.getParameter("templatetype")));
		model.addAttribute("flowtemplate", flowtemplate);
		return "modules/flow/flowtemplateForm";
	}

	/**
	 * 保存模板创建
	 */
	@RequiresPermissions(value={"flow:flowtemplate:add","flow:flowtemplate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save", method=RequestMethod.POST)
	public void save(Flowtemplate flowtemplate,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		/*if (!beanValidator(model, flowtemplate)){
			return form(flowtemplate, model);
		}
		if(!flowtemplate.getIsNewRecord()){//编辑表单保存
			Flowtemplate t = flowtemplateService.get(flowtemplate.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(flowtemplate, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			flowtemplateService.save(t);//保存
		}else{//新增表单保存
			flowtemplateService.save(flowtemplate);//保存
		}
		addMessage(redirectAttributes, "保存模板创建成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowtemplate/?repage";*/
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
    		String flowtemplateid = (String) request.getParameter("flowtemplateid");
    		if(FormatUtil.isNoEmpty(flowtemplateid)){
    			flowtemplate = flowtemplateService.get(flowtemplateid);
    		}
    		String templatename = (String) request.getParameter("templatename");
        	String remarks = request.getParameter("remarks");
			String templatetype = request.getParameter("templatetype");
        	flowtemplate.setFlowremarks(remarks);
        	flowtemplate.setTemplatename(templatename);
			flowtemplate.setTemplatetype(FormatUtil.toInteger(templatetype));
        	flowtemplateService.save(flowtemplate);
        	
        	map.put("status", "y");
			map.put("flowtemplateid", flowtemplate.getId());
			map.put("info", "模板保存成功，请继续进行字段设置，模板设置！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("模板保存失败：", e);
			map.put("status", "n");
			map.put("info", "模板保存失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	@RequiresPermissions(value={"flow:flowtemplate:add","flow:flowtemplate:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveTemplatecontrol", method=RequestMethod.POST)
	public void saveTemplatecontrol(Flowtemplate flowtemplate,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
    		String flowtemplateid = (String) request.getParameter("flowtemplateid");
    		String controlid = (String) request.getParameter("controlid");
    		String columnname = (String) request.getParameter("columnname");
        	//String columnid = (String) request.getParameter("columnid");
        	String columntype = (String) request.getParameter("columntype");
        	String columnvalue = (String) request.getParameter("columnvalue");
			String valuerequire = (String) request.getParameter("valuerequire");
        	
        	flowtemplate = flowtemplateService.get(flowtemplateid);
        	
        	Templatecontrol con = null;
        	if(FormatUtil.isNoEmpty(controlid)){
        		con = flowtemplateService.getTemplatecontrolByid(controlid);
        		//con.setColumnid(columnid);
            	con.setColumnname(columnname);
            	con.setColumntype(columntype);
            	con.setColumnvalue(columnvalue);
				con.setValuerequire(FormatUtil.toInteger(valuerequire));
        	}else{
        		
//        		if(flowtemplate.getTemplatecontrolList().size() > 0){
//            		Boolean isOnly = true;
//            		for (Templatecontrol conl : flowtemplate.getTemplatecontrolList()) {
//        				if(columnid.equals(conl.getColumnid())){
//        					isOnly = false;
//        				}
//        			}
//            		if(!isOnly){
//            			map.put("status", "n");
//            			map.put("info", "所属字段不能重复！");
//            			out.write(JSONObject.fromObject(map).toString());
//            			out.close();
//            			return;
//            		}
//            	}
        		
        		con = new Templatecontrol();
            	con.setId("");
//            	con.setColumnid(columnid);
            	con.setColumnname(columnname);
            	con.setColumntype(columntype);
            	con.setColumnvalue(columnvalue);
            	con.setColumnlocate(1);
				con.setValuerequire(FormatUtil.toInteger(valuerequire));
            	
            	List<Templatecontrol> lst = flowtemplate.getTemplatecontrolList();
            	lst.add(con);
            	flowtemplate.setTemplatecontrolList(lst);
        	}
        	
        	flowtemplateService.save(flowtemplate,con);
        	
        	map.put("status", "y");
			map.put("info", "字段设置成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("字段设置失败：", e);
			map.put("status", "n");
			map.put("info", "字段设置失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	@RequiresPermissions(value={"flow:flowtemplate:add","flow:flowtemplate:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveTemplatecontrolSort", method=RequestMethod.POST)
	public void saveTemplatecontrolSort(Flowtemplate flowtemplate,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
    		String flowtemplateid = (String) request.getParameter("flowtemplateid");
    		String htmlstr = (String) request.getParameter("htmlstr");
    		String sortstr = (String) request.getParameter("sortstr");
    		int showcolumn = FormatUtil.toInt(request.getParameter("showcolumn"));
        	
        	flowtemplate = flowtemplateService.get(flowtemplateid);
        	List<Templatecontrol> lst = new ArrayList<Templatecontrol>();
        	String[] s = sortstr.split(" ");
        	for (String string : s) {
				for (Templatecontrol con : flowtemplate.getTemplatecontrolList()) {
					if(con.getId().equals(string.split(",")[0])){
						con.setColumnsort(FormatUtil.toInt(string.split(",")[1]));
						con.setColumnlocate(FormatUtil.toInt(string.split(",")[2]));
						lst.add(con);
					}
				}
			}
        	flowtemplate.setTemplatecontrolList(lst);
        	flowtemplate.setTemplatehtml(htmlstr);
        	flowtemplate.setShowcolumn(showcolumn);
        	flowtemplateService.save(flowtemplate);
        	
        	map.put("status", "y");
			map.put("info", "模板设置成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("模板设置失败：", e);
			map.put("status", "n");
			map.put("info", "模板设置失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	@RequiresPermissions(value={"flow:flowtemplate:list"})
	@RequestMapping(value = "getTemplatecontrolList", method=RequestMethod.POST)
	public void getTemplatecontrolList(Flowtemplate flowtemplate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	String id = request.getParameter("id");
    	List<Map> lst = new ArrayList<Map>();
    	if(FormatUtil.isNoEmpty(id)){
    		flowtemplate = flowtemplateService.get(id);
        	
        	Map tempmap = null;
        	for (Templatecontrol con : flowtemplate.getTemplatecontrolList()) {
        		tempmap = new HashMap();
        		tempmap.put("id", con.getId());
        		tempmap.put("columnid", con.getColumnid());
        		tempmap.put("columnname", con.getColumnname());
        		tempmap.put("columntype", con.getColumntype());
        		tempmap.put("columntypename", Global.columnMap.get(con.getColumntype()));
        		tempmap.put("columnvalue", con.getColumnvalue());
				tempmap.put("valuerequire", con.getValuerequire());
        		lst.add(tempmap);
    		}
    		
    	}
    	out.write(JSONArray.fromObject(lst).toString());
    	//out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	@RequiresPermissions("flow:flowtemplate:del")
	@RequestMapping(value = "deleteTemplatecontrol")
	public void deleteTemplatecontrol(Flowtemplate flowtemplate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();
    	String flowtemplateid = request.getParameter("flowtemplateid");
    	String templateidcontrolid = request.getParameter("templateidcontrolid");
    	List<Map> lst = new ArrayList<Map>();
    	
    	
    	try {
    		if(FormatUtil.isNoEmpty(flowtemplateid)){
        		flowtemplate = flowtemplateService.get(flowtemplateid);
            	
            	for (Templatecontrol con : flowtemplate.getTemplatecontrolList()) {
            		if(templateidcontrolid.equals(con.getId())){
            			con.setDelFlag(Templatecontrol.DEL_FLAG_DELETE);
            		}
        		}
            	flowtemplateService.save(flowtemplate);
    		}	
        	map.put("status", "y");
			map.put("info", "删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("字段删除失败：", e);
			map.put("status", "n");
			map.put("info", "删除失败");
		}
    	
    	out.write(JSONObject.fromObject(map).toString());
		out.close();
		
	}
	
	/**
	 * 删除模板创建
	 */
	@RequiresPermissions("flow:flowtemplate:del")
	@RequestMapping(value = "delete")
	public String delete(Flowtemplate flowtemplate, RedirectAttributes redirectAttributes) {
		flowtemplateService.delete(flowtemplate);
		addMessage(redirectAttributes, "删除模板创建成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowtemplate/?repage&templatetype="+flowtemplate.getTemplatetype();
	}
	
	/**
	 * 批量删除模板创建
	 */
	@RequiresPermissions("flow:flowtemplate:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		Flowtemplate flowtemplate = null;
		for(String id : idArray){
			flowtemplate = flowtemplateService.get(id);
			flowtemplateService.delete(flowtemplate);
		}
		addMessage(redirectAttributes, "删除模板创建成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowtemplate/?repage&templatetype="+flowtemplate.getTemplatetype();
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("flow:flowtemplate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Flowtemplate flowtemplate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "模板创建"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Flowtemplate> page = flowtemplateService.findPage(new Page<Flowtemplate>(request, response, -1), flowtemplate);
    		new ExportExcel("模板创建", Flowtemplate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出模板创建记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/flow/flowtemplate/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("flow:flowtemplate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Flowtemplate> list = ei.getDataList(Flowtemplate.class);
			for (Flowtemplate flowtemplate : list){
				try{
					flowtemplateService.save(flowtemplate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条模板创建记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条模板创建记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板创建失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/flow/flowtemplate/?repage";
    }
	
	/**
	 * 下载导入模板创建数据模板
	 */
	@RequiresPermissions("flow:flowtemplate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "模板创建数据导入模板.xlsx";
    		List<Flowtemplate> list = Lists.newArrayList(); 
    		new ExportExcel("模板创建数据", Flowtemplate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/flow/flowtemplate/?repage";
    }
	
	
	@RequestMapping(value = "flowManager")
	public String flowManager(Flowtemplate flowtemplate,HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/flow/flowManagerList";
	}

	@RequestMapping(value = "form2")
	public String form2(Flowtemplate flowtemplate,HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/flow/flowManagerForm";
	}
}