/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.web;

import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.*;
import com.jeeplus.modules.ehr.dao.TraineeDao;
import com.jeeplus.modules.ehr.entity.Posttrain;
import com.jeeplus.modules.ehr.entity.Trainee;
import com.jeeplus.modules.ehr.service.PosttrainService;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.sys.entity.Office;
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

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.ehr.entity.Trainplan;
import com.jeeplus.modules.ehr.service.TrainplanService;

/**
 * 培训计划Controller
 * @author cqj
 * @version 2018-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/ehr/trainplan")
public class TrainplanController extends BaseController {

	@Autowired
	private TrainplanService trainplanService;
	@Autowired
	private TraineeDao traineeDao;
	@Autowired
	private PosttrainService posttrainService;
	
	@ModelAttribute
	public Trainplan get(@RequestParam(required=false) String id) {
		Trainplan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = trainplanService.get(id);
		}
		if (entity == null){
			entity = new Trainplan();
		}
		return entity;
	}
	
	/**
	 * 培训计划列表页面
	 */
	@RequiresPermissions("ehr:trainplan:list")
	@RequestMapping(value = {"list", ""})
	public String list(Trainplan trainplan, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(!FormatUtil.isNoEmpty(trainplan.getIsadmin())){
			trainplan.setUserid(UserUtils.getUser().getId());
		}
		Page<Trainplan> page = trainplanService.findPage(new Page<Trainplan>(request, response), trainplan); 
		model.addAttribute("page", page);
		model.addAttribute("trainplan",trainplan);
		return "modules/ehr/trainplanList";
	}

	/**
	 * 查看，增加，编辑培训计划表单页面
	 */
	@RequiresPermissions(value={"ehr:trainplan:view","ehr:trainplan:add","ehr:trainplan:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Trainplan trainplan, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("trainplan", trainplan);
		Page<Trainee> page = trainplanService.find(new Page<Trainee>(request, response),new Trainee(trainplan));
		model.addAttribute("page", page);
		return "modules/ehr/trainplanForm";
	}

	/**
	 * 保存培训计划
	 */
	@RequiresPermissions(value={"ehr:trainplan:add","ehr:trainplan:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Trainplan trainplan, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, trainplan)){
			return form(trainplan,request,response, model);
		}
		if(!trainplan.getIsNewRecord()){//编辑表单保存
			Trainplan t = trainplanService.get(trainplan.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(trainplan, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			trainplanService.save(t);//保存
//			if(FormatUtil.isNoEmpty(trainplan.getIsadmin())){
//				return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/form?id="+t.getId()+"&isadmin="+trainplan.getIsadmin();
//			}
//			return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/form?id="+t.getId();

		}else{//新增表单保存
			trainplan.setStatus("0");
			trainplan.setIsnotify("0");
			trainplanService.save(trainplan);//保存
		}
		addMessage(redirectAttributes, "保存培训计划成功");
		if(FormatUtil.isNoEmpty(trainplan.getIsadmin())){
			return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/?repage&isadmin="+trainplan.getIsadmin();
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/?repage";
	}
	
	/**
	 * 删除培训计划
	 */
	@RequiresPermissions("ehr:trainplan:del")
	@RequestMapping(value = "delete")
	public String delete(Trainplan trainplan, RedirectAttributes redirectAttributes) {
		trainplanService.delete(trainplan);
		addMessage(redirectAttributes, "删除培训计划成功");

		if(FormatUtil.isNoEmpty(trainplan.getIsadmin())){
			return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/?repage&isadmin="+trainplan.getIsadmin();
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/?repage";
	}
	
	/**
	 * 批量删除培训计划
	 */
	@RequiresPermissions("ehr:trainplan:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			trainplanService.delete(trainplanService.get(id));
		}
		addMessage(redirectAttributes, "删除培训计划成功");
		if(FormatUtil.isNoEmpty(request.getParameter("isadmin"))){
			return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/?repage&isadmin="+request.getParameter("isadmin");
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ehr:trainplan:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Trainplan trainplan, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "培训计划"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Trainplan> page = trainplanService.findPage(new Page<Trainplan>(request, response, -1), trainplan);
    		new ExportExcel("培训计划", Trainplan.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出培训计划记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ehr:trainplan:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Trainplan> list = ei.getDataList(Trainplan.class);
			for (Trainplan trainplan : list){
				try{
					trainplanService.save(trainplan);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条培训计划记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条培训计划记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入培训计划失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/?repage";
    }
	
	/**
	 * 下载导入培训计划数据模板
	 */
	@RequiresPermissions("ehr:trainplan:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "培训计划数据导入模板.xlsx";
    		List<Trainplan> list = Lists.newArrayList(); 
    		new ExportExcel("培训计划数据", Trainplan.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/trainplan/?repage";
    }

	@RequestMapping(value = "addTrainee", method=RequestMethod.POST)
	public void addTrainee(Trainplan trainplan,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();
		List<Trainee> addTraineeList = new ArrayList<Trainee>();
		try {
			List<String> listids = new ArrayList<String>();
			Boolean flag = true;
			String hasnames = "";
			String trainplanid = (String) request.getParameter("trainplanid");
			String ids = (String) request.getParameter("ids");
			trainplan = trainplanService.get(trainplanid);
			if(FormatUtil.isNoEmpty(ids)){
				String[] userids = ids.split(",");
				for (String id : userids) {
					flag = true;
					for (Trainee trainee : trainplan.getTraineeList()) {
						if(id.equals(trainee.getUserid())){
							hasnames+=trainee.getUsername()+",";
							flag=false;
						}
					}
					if(flag){
						listids.add(id);
					}
				}

				for (String uid : listids){
					Trainee trainee = new Trainee();
					trainee.setId(IdGen.uuid());
					trainee.setTrainplan(trainplan);
					trainee.setUserid(uid);
					User usr=UserUtils.get(uid);
					trainee.setUsername(usr.getName());

					trainee.setOfficeid(usr.getOffice().getId());
					trainee.setOfficename(usr.getOfficeName());
					addTraineeList.add(trainee);
				}
				if(addTraineeList.size() > 0){
					traineeDao.insertAll(addTraineeList);
				}
			}

			map.put("status", "y");
			map.put("info", "添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("添加失败：", e);
			map.put("status", "n");
			map.put("info", "添加失败");
		}

		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	@RequestMapping(value = {"mylist"})
	public String mylist(Posttrain posttrain, HttpServletRequest request, HttpServletResponse response, Model model) {
		posttrain.setCreateBy(UserUtils.getUser());
		Page<Posttrain> page = posttrainService.findPage(new Page<Posttrain>(request, response), posttrain);
		model.addAttribute("page", page);
		return "modules/ehr/posttrain/theposttrainList";
	}

	@RequestMapping(value = "view")
	public String view(Trainplan trainplan, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("trainplan", trainplan);
		trainplan.setMview("1");
		Page<Trainee> page = trainplanService.find(new Page<Trainee>(request, response),new Trainee(trainplan));
		model.addAttribute("page", page);
		return "modules/ehr/trainplanForm";
	}
}