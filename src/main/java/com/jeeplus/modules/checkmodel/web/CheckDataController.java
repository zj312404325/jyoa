/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.checkmodel.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.checkmodel.entity.CheckData;
import com.jeeplus.modules.checkmodel.entity.CheckDataDetail;
import com.jeeplus.modules.checkmodel.service.CheckDataDetailService;
import com.jeeplus.modules.checkmodel.service.CheckDataService;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.User;

/**
 * 绩效数据设定Controller
 * @author cqj
 * @version 2017-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/checkmodel/checkData")
public class CheckDataController extends BaseController {

	@Autowired
	private CheckDataService checkDataService;
	@Autowired
	private CheckDataDetailService checkDataDetailService;
	
	@Autowired
	private UserDao userDao;
	
	@ModelAttribute
	public CheckData get(@RequestParam(required=false) String id) {
		CheckData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkDataService.get(id);
		}
		if (entity == null){
			entity = new CheckData();
		}
		return entity;
	}
	
	/**
	 * 绩效数据设定列表页面
	 */
	@RequiresPermissions("checkmodel:checkData:list")
	@RequestMapping(value = {"list", ""})
	public String list(CheckData checkData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckData> page = checkDataService.findPage(new Page<CheckData>(request, response), checkData); 
		model.addAttribute("page", page);
		return "modules/ehr/checkmodel/checkDataList";
	}

	/**
	 * 查看，增加，编辑绩效数据设定表单页面
	 */
	@RequiresPermissions(value={"checkmodel:checkData:view","checkmodel:checkData:add","checkmodel:checkData:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CheckData checkData, Model model) {
		//获取明细
		if(FormatUtil.isNoEmpty(checkData.getId())){//修改
			CheckDataDetail checkDataDetail=new CheckDataDetail();
			checkDataDetail.setCheckdataid(checkData.getId());
			checkDataDetail.getPage().setOrderBy("a.sort asc");
			List<CheckDataDetail> checkDataDetailList=checkDataDetailService.findList(checkDataDetail);
			checkData.setCheckDataDetailList(checkDataDetailList);
		}
		model.addAttribute("checkData", checkData);
		return "modules/ehr/checkmodel/checkDataForm";
	}

	/**
	 * 保存绩效数据设定
	 */
	@RequiresPermissions(value={"checkmodel:checkData:add","checkmodel:checkData:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CheckData checkData, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, checkData)){
			return form(checkData, model);
		}
		if(!checkData.getIsNewRecord()){//编辑表单保存
			CheckData t = checkDataService.get(checkData.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(checkData, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			checkDataService.save(t);//保存
		}else{//新增表单保存
			//验证次用户记录是否存在
			if(existUserData(checkData)){
				addMessage(redirectAttributes, "此员工已设置绩效数据！");
				return "redirect:"+Global.getAdminPath()+"/checkmodel/checkData/?repage";
			}
			checkDataService.save(checkData);//保存
		}
		addMessage(redirectAttributes, "保存绩效数据设定成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkData/?repage";
	}
	
	/**
	 * 此数据是否存在
	 * @param checkData
	 * @return
	 */
	private boolean existUserData(CheckData checkData) {
		CheckData temp=new CheckData();
		temp.setUserid(checkData.getUserid());
		List<CheckData> checkDataList=checkDataService.findList(temp);
		if(FormatUtil.isNoEmpty(checkDataList)&&checkDataList.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * 删除绩效数据设定
	 */
	@RequiresPermissions("checkmodel:checkData:del")
	@RequestMapping(value = "delete")
	public String delete(CheckData checkData, RedirectAttributes redirectAttributes) {
		checkDataService.delete(checkData);
		addMessage(redirectAttributes, "删除绩效数据设定成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkData/?repage";
	}
	
	/**
	 * 批量删除绩效数据设定
	 */
	@RequiresPermissions("checkmodel:checkData:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			checkDataService.delete(checkDataService.get(id));
		}
		addMessage(redirectAttributes, "删除绩效数据设定成功");
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkData/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("checkmodel:checkData:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CheckData checkData, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "绩效数据设定"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CheckData> page = checkDataService.findPage(new Page<CheckData>(request, response, -1), checkData);
    		new ExportExcel("绩效数据设定", CheckData.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出绩效数据设定记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkData/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("checkmodel:checkData:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CheckData> list = ei.getDataList(CheckData.class);
			for (CheckData checkData : list){
				try{
					checkDataService.save(checkData);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条绩效数据设定记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条绩效数据设定记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入绩效数据设定失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkData/?repage";
    }
	
	/**
	 * 下载导入绩效数据设定数据模板
	 */
	@RequiresPermissions("checkmodel:checkData:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "绩效数据设定数据导入模板.xlsx";
    		List<CheckData> list = Lists.newArrayList(); 
    		new ExportExcel("绩效数据设定数据", CheckData.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/checkmodel/checkData/?repage";
    }
	

}