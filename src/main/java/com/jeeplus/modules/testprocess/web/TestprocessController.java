/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.testprocess.web;

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
import com.google.common.collect.Maps;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.oa.entity.Leave;
import com.jeeplus.modules.testprocess.entity.Testprocess;
import com.jeeplus.modules.testprocess.service.TestprocessService;

/**
 * 流程测试Controller
 * @author cqj
 * @version 2016-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/testprocess/testprocess")
public class TestprocessController extends BaseController {

	@Autowired
	private TestprocessService testprocessService;
	
	@ModelAttribute
	public Testprocess get(@RequestParam(required=false) String id) {
		Testprocess entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testprocessService.get(id);
		}
		if (entity == null){
			entity = new Testprocess();
		}
		return entity;
	}
	
	/**
	 * 流程测试列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(Testprocess testprocess, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Testprocess> page = testprocessService.findPage(new Page<Testprocess>(request, response), testprocess); 
		model.addAttribute("page", page);
		return "modules/testprocess/testprocessList";
	}

	/**
	 * 查看，增加，编辑流程测试表单页面
	 */
	@RequestMapping(value = "form")
	public String form(Testprocess testprocess, Model model) {
		model.addAttribute("testprocess", testprocess);
		
		String view = "testprocessForm";
		// 查看审批申请单
		if (StringUtils.isNotBlank(testprocess.getId())){//.getAct().getProcInsId())){

			// 环节编号
			String taskDefKey = testprocess.getAct().getTaskDefKey();
			
			// 查看工单
			if(testprocess.getAct().isFinishTask()){
				view = "testprocessView";
			}
			// 修改环节
			else if ("leader".equals(taskDefKey)){
				view = "testprocessAudit";
			}
//			// 审核环节
//			else if ("hrAudit".equals(taskDefKey)){
//				view = "leaveAudit";
//			}
//			// 审核环节2
//			else if ("reportBack".equals(taskDefKey)){
//				view = "leaveAudit";
//			}
//			// 审核环节3
//			else if ("modifyApply".equals(taskDefKey)){
//				view = "leaveAudit";
//			}
		}

		model.addAttribute("testprocess", testprocess);
		return "modules/testprocess/"+view;
	}

	/**
	 * 保存流程测试
	 */
	@RequestMapping(value = "save")
	public String save(Testprocess testprocess, Model model, RedirectAttributes redirectAttributes) throws Exception{
//		if (!beanValidator(model, testprocess)){
//			return form(testprocess, model);
//		}
//		if(!testprocess.getIsNewRecord()){//编辑表单保存
//			Testprocess t = testprocessService.get(testprocess.getId());//从数据库取出记录的值
//			MyBeanUtils.copyBeanNotNull2Bean(testprocess, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
//			testprocessService.save(t);//保存
//		}else{//新增表单保存
//			testprocessService.save(testprocess);//保存
//		}
//		addMessage(redirectAttributes, "保存流程测试成功");
//		return "redirect:"+Global.getAdminPath()+"/testprocess/testprocess/?repage";
		try {
			Map<String, Object> variables = Maps.newHashMap();
			testprocessService.save(testprocess, variables);
			addMessage(redirectAttributes, "请假申请已经提交");
		} catch (Exception e) {
			logger.error("启动请假流程失败：", e);
			addMessage(redirectAttributes, "系统内部错误！");
		}
		return "redirect:" + adminPath + "/act/task/process/";
	}
	
	/**
	 * 删除流程测试
	 */
	@RequestMapping(value = "delete")
	public String delete(Testprocess testprocess, RedirectAttributes redirectAttributes) {
		testprocessService.delete(testprocess);
		addMessage(redirectAttributes, "删除流程测试成功");
		return "redirect:"+Global.getAdminPath()+"/testprocess/testprocess/?repage";
	}
	
	/**
	 * 批量删除流程测试
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testprocessService.delete(testprocessService.get(id));
		}
		addMessage(redirectAttributes, "删除流程测试成功");
		return "redirect:"+Global.getAdminPath()+"/testprocess/testprocess/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Testprocess testprocess, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流程测试"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Testprocess> page = testprocessService.findPage(new Page<Testprocess>(request, response, -1), testprocess);
    		new ExportExcel("流程测试", Testprocess.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出流程测试记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/testprocess/testprocess/?repage";
    }

	/**
	 * 导入Excel数据

	 */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Testprocess> list = ei.getDataList(Testprocess.class);
			for (Testprocess testprocess : list){
				try{
					testprocessService.save(testprocess);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条流程测试记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条流程测试记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入流程测试失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/testprocess/testprocess/?repage";
    }
	
	/**
	 * 下载导入流程测试数据模板
	 */
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流程测试数据导入模板.xlsx";
    		List<Testprocess> list = Lists.newArrayList(); 
    		new ExportExcel("流程测试数据", Testprocess.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/testprocess/testprocess/?repage";
    }
	
	
    /**
	 * 工单执行（完成任务）
	 * @param testAudit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveAudit")
	public String saveAudit(Testprocess testprocess,Map<String, Object> vars, Model model) {
		if (StringUtils.isBlank(testprocess.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(testprocess, model);
		}
		testprocessService.auditSave(testprocess);
		return "redirect:" + adminPath + "/act/task";
	}

}