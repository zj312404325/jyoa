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
import com.jeeplus.modules.flow.entity.Flowtemplate;
import com.jeeplus.modules.flow.service.FlowtemplateService;
import com.jeeplus.modules.leipiflow.entity.LeipiFlow;
import com.jeeplus.modules.leipiflow.entity.LeipiFlowProcess;
import com.jeeplus.modules.leipiflow.entity.LeipiRun;
import com.jeeplus.modules.leipiflow.service.LeipiFlowProcessService;
import com.jeeplus.modules.leipiflow.service.LeipiFlowService;
import com.jeeplus.modules.leipiflow.service.LeipiRunService;
import com.jeeplus.modules.oa.entity.Oagroup;
import com.jeeplus.modules.oa.entity.Oagroupdtl;
import com.jeeplus.modules.oa.service.OagroupService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 雷劈流程模块Controller
 * @author 陈钱江
 * @version 2017-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/leipiflow/leipiFlow")
public class LeipiFlowController extends BaseController {

	@Autowired
	private FlowtemplateService flowtemplateService;
	
	@Autowired
	private LeipiFlowService leipiFlowService;
	
	@Autowired
	private LeipiFlowProcessService leipiFlowProcessService;

	@Autowired
	private LeipiRunService leipiRunService;

	@Autowired
	private OagroupService oagroupService;
	
	@ModelAttribute
	public LeipiFlow get(@RequestParam(required=false) String id) {
		LeipiFlow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leipiFlowService.get(id);
		}
		if (entity == null){
			entity = new LeipiFlow();
		}
		return entity;
	}
	
	/**
	 * 流程操作列表页面
	 */
	//@RequiresPermissions("leipiflow:leipiFlow:list")
	@RequestMapping(value = {"list", ""})
	public String list(LeipiFlow leipiFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(FormatUtil.isNoEmpty(request.getParameter("leipiflowtype"))){
			leipiFlow.setLeipiflowtype(FormatUtil.toInteger(request.getParameter("leipiflowtype")));
		}
		Page<LeipiFlow> page = leipiFlowService.findPage(new Page<LeipiFlow>(request, response), leipiFlow);
		model.addAttribute("page", page);
		model.addAttribute("isadmin","1");
		model.addAttribute("leipiflowtype", FormatUtil.toInteger(request.getParameter("leipiflowtype")));
		return "modules/leipiflow/leipiFlowList";
	}
	
	@RequestMapping(value = {"goLeipiFlow"})
	public String goLeipiFlow(LeipiFlow leipiFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		leipiFlow.setStatus(1);
		leipiFlow.setCreateBy(UserUtils.getUser());
		Page<LeipiFlow> page = leipiFlowService.findUsablePage(new Page<LeipiFlow>(request, response), leipiFlow); 
		model.addAttribute("page", page);
		return "modules/leipiflow/leipiFlowList";
	}

	/**
	 * 查看，增加，编辑流程操作表单页面
	 */
	@RequiresPermissions(value={"leipiflow:leipiFlow:view","leipiflow:leipiFlow:add","leipiflow:leipiFlow:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LeipiFlow leipiFlow, Model model, HttpServletRequest request, HttpServletResponse response) {
		leipiFlow.setLeipiflowtype(FormatUtil.toInteger(request.getParameter("leipiflowtype")));
		model.addAttribute("leipiFlow", leipiFlow);
		//查询表单列表
		Flowtemplate ft = new Flowtemplate();
		ft.setDelFlag("0");
		ft.setTemplatetype(FormatUtil.toInteger(request.getParameter("leipiflowtype")));
		List<Flowtemplate> list = flowtemplateService.findList(ft);
		model.addAttribute("formList", list);
		return "modules/leipiflow/leipiFlowForm";
	}

	/**
	 * 保存流程操作
	 */
	@RequiresPermissions(value={"leipiflow:leipiFlow:add","leipiflow:leipiFlow:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LeipiFlow leipiFlow, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, leipiFlow)){
			return form(leipiFlow, model,request,response);
		}
		if(!leipiFlow.getIsNewRecord()){//编辑表单保存
			LeipiFlow t = leipiFlowService.get(leipiFlow.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(leipiFlow, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			
			if(FormatUtil.toInt(request.getParameter("flowarea")) == 0){
				t.setReceiveuserids(null);
				t.setReceiveusernames(null);
				t.setReceivedeptids(request.getParameter("receivedeptids"));
				t.setReceivedeptnames(request.getParameter("receivedeptnames"));
			}else{
				t.setReceiveuserids(request.getParameter("receiveuserids"));
				t.setReceiveusernames(request.getParameter("receiveusernames"));
				t.setReceivedeptids(null);
				t.setReceivedeptnames(null);
			}
			
			leipiFlowService.save(t);//保存
		}else{//新增表单保存
			if(FormatUtil.toInt(request.getParameter("flowarea")) == 0){
				leipiFlow.setReceiveuserids(null);
				leipiFlow.setReceiveusernames(null);
				leipiFlow.setReceivedeptids(request.getParameter("receivedeptids"));
				leipiFlow.setReceivedeptnames(request.getParameter("receivedeptnames"));
			}else{
				leipiFlow.setReceiveuserids(request.getParameter("receiveuserids"));
				leipiFlow.setReceiveusernames(request.getParameter("receiveusernames"));
				leipiFlow.setReceivedeptids(null);
				leipiFlow.setReceivedeptnames(null);
			}
			leipiFlowService.save(leipiFlow);//保存
		}
		addMessage(redirectAttributes, "保存流程操作成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlow/?repage&leipiflowtype="+leipiFlow.getLeipiflowtype();
	}
	
	/**
	 * 删除流程操作
	 */
	@RequiresPermissions("leipiflow:leipiFlow:del")
	@RequestMapping(value = "delete")
	public String delete(LeipiFlow leipiFlow, RedirectAttributes redirectAttributes) {
		if(checkRunFlowExist(leipiFlow.getId())){
			addMessage(redirectAttributes, "此流程存在正在执行的实例！");
			if(leipiFlow.getLeipiflowtype() == 1){
				return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlow?leipiflowtype=1&repage";
			}else{
				return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlow/?repage";
			}
		}
		leipiFlowService.delete(leipiFlow);
		addMessage(redirectAttributes, "删除流程操作成功");
		if(leipiFlow.getLeipiflowtype() == 1){
			return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlow?leipiflowtype=1&repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlow/?repage";
		}

	}
	
	/**
	 * 批量删除流程操作
	 */
	@RequiresPermissions("leipiflow:leipiFlow:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			if(checkRunFlowExist(id)){
				addMessage(redirectAttributes, "此流程存在正在执行的实例！");
				return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlow/?repage";
			}
			leipiFlowService.delete(leipiFlowService.get(id));
		}
		addMessage(redirectAttributes, "删除流程操作成功");
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlow/?repage";
	}

	/**
	 * 判断是否存在正在运行的实例
	 * @param
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
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("leipiflow:leipiFlow:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LeipiFlow leipiFlow, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流程操作"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeipiFlow> page = leipiFlowService.findPage(new Page<LeipiFlow>(request, response, -1), leipiFlow);
    		new ExportExcel("流程操作", LeipiFlow.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出流程操作记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlow/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("leipiflow:leipiFlow:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeipiFlow> list = ei.getDataList(LeipiFlow.class);
			for (LeipiFlow leipiFlow : list){
				try{
					leipiFlowService.save(leipiFlow);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条流程操作记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条流程操作记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入流程操作失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlow/?repage";
    }
	
	/**
	 * 下载导入流程操作数据模板
	 */
	@RequiresPermissions("leipiflow:leipiFlow:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流程操作数据导入模板.xlsx";
    		List<LeipiFlow> list = Lists.newArrayList(); 
    		new ExportExcel("流程操作数据", LeipiFlow.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/leipiflow/leipiFlow/?repage";
    }
	
	
	@RequiresPermissions(value={"leipiflow:leipiFlow:add"})
	@RequestMapping(value = "leipiflowInit")
	public String leipiflowInit(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		String flowid=request.getParameter("flowid");
		request.setAttribute("flowid",flowid);
		LeipiFlow leipiFlow=leipiFlowService.findUniqueByProperty("id", flowid);
		if(!FormatUtil.isNoEmpty(leipiFlow)||leipiFlow.getIsdel()!=0){
			addMessage(redirectAttributes, "流程不存在！");
		}else{
			Map map = new HashMap();
			List<LeipiFlowProcess> lstProcess=leipiFlowProcessService.findList(new LeipiFlow(flowid));
//			List<LeipiFlowProcess> lstProcess=leipiFlowProcessService.findByProperty("flow_id", flowid);
			map.put("total", lstProcess.size());
			List<String> lst=new ArrayList<String>();
			for (LeipiFlowProcess lfp : lstProcess) {
				String info="{\"id\":\""+lfp.getId()+"\",\"flow_id\":\""+flowid+"\",\"process_name\":\""+FormatUtil.toString(lfp.getProcessName())+"\",\"process_to\":\""+FormatUtil.toString(lfp.getProcessTo())+"\",\"icon\":\""+lfp.getStyleIcon()+"\",\"style\":\""+lfp.getStyle()+"\"}";
				lst.add(info);
			}
			map.put("list", lst);
			request.setAttribute("process_data", JSONObject.fromObject(map));
			request.setAttribute("one", leipiFlow);
		}
		return "modules/leipiflow/leipiFlowInit";
	}

	@RequestMapping(value = "flowGroup")
	public String flowGroup( Model model, HttpServletRequest request, HttpServletResponse response) {
		Oagroup og = new Oagroup();
		og.setCurrentUser(UserUtils.getUser());
		og.setGrouptype(1);
		List<Oagroup> groups = oagroupService.findList(og);
		model.addAttribute("groups", groups);
		return "modules/sys/groupSelectHold";
	}

	@RequestMapping(value = "groupform")
	public String groupform(Oagroup oagroup,HttpServletRequest request, HttpServletResponse response,  Model model) {
//		String id = request.getParameter("id");
//		if(FormatUtil.isNoEmpty(id)){
//			OaNotify oaNotify = oaNotifyService.get(id);
//			oaNotify = oaNotifyService.getRecordList(oaNotify);
////			oagroup.setIds(oaNotify.getOaNotifyRecordIds());
////			oagroup.setNames(oaNotify.getOaNotifyRecordNames());
////			model.addAttribute("oagroup", oagroup);
//			request.setAttribute("ids", oaNotify.getOaNotifyRecordIds());
//			request.setAttribute("names", oaNotify.getOaNotifyRecordNames());
//		}else{
////			oagroup.setIds(request.getParameter("ids"));
////			oagroup.setNames(request.getParameter("names"));
//			request.setAttribute("ids", request.getParameter("ids"));
//			request.setAttribute("names", request.getParameter("names"));
//		}

		return "modules/leipiflow/flowGroupForm";
	}

	@RequestMapping(value = "saveGroup", method=RequestMethod.POST)
	public void saveGroup(Oagroup oagroup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();
		try {
			String groupname = request.getParameter("groupname");
			String oagroupId = request.getParameter("groupuIds");
			String oagroupName = request.getParameter("groupuNames");
			oagroup = new Oagroup();
			oagroup.setGrouptype(1);
			oagroup.setGroupname(groupname);

			List<Oagroupdtl> oagroupdtlList = Lists.newArrayList();
			if(FormatUtil.isNoEmpty(oagroupId)){
				String ids[] = StringUtils.split(oagroupId, ",");
				String names[] = StringUtils.split(oagroupName, ",");

				Oagroupdtl oagroupdtl = null;
				User groupuser = null;
				for (int i = 0; i < ids.length; i++) {
					oagroupdtl = new Oagroupdtl();
					groupuser = new User();
					groupuser.setId(ids[i]);
					groupuser.setName(names[i]);
					oagroupdtl.setGroupuser(groupuser);
					oagroupdtlList.add(oagroupdtl);
				}
				oagroup.setOagroupdtlList(oagroupdtlList);
			}

//    		oagroup.setIds(oagroupId);
//    		oagroup.setNames(oagroupName);

			oagroupService.save(oagroup);

			map.put("id", oagroup.getId());
			map.put("status", "y");
			map.put("info", "分组成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("添加失败：", e);
			map.put("status", "n");
			map.put("info", "分组失败");
		}

		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

}