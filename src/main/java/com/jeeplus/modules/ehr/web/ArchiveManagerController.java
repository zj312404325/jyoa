/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.ehr.entity.ArchiveManager;
import com.jeeplus.modules.ehr.service.ArchiveManagerService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 档案管理Controller
 * @author yc
 * @version 2017-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ehr/archiveManager")
public class ArchiveManagerController extends BaseController {

	@Autowired
	private ArchiveManagerService archiveManagerService;
	
	@ModelAttribute
	public ArchiveManager get(@RequestParam(required=false) String id) {
		ArchiveManager entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = archiveManagerService.get(id);
		}
		if (entity == null){
			entity = new ArchiveManager();
		}
		return entity;
	}
	
	/**
	 * 信息列表页面
	 */
	@RequiresPermissions("ehr:archiveManager:list")
	@RequestMapping(value = {""})
	public String list(ArchiveManager archiveManager, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<ArchiveManager> page = archiveManagerService.findPage(new Page<ArchiveManager>(request, response), archiveManager); 
//		model.addAttribute("page", page);
		return "modules/filemanager/archive/archiveManagerIndex";
	}
	
	@RequiresPermissions("ehr:archiveManager:list")
	@RequestMapping(value = {"list"})
	public String getlist(ArchiveManager archiveManager, HttpServletRequest request, HttpServletResponse response, Model model) {
//		User loginUser = UserUtils.getUser();
		if(archiveManager==null || archiveManager.getParentIds() == null){
			 ArchiveManager ar =new ArchiveManager();
//			 ar.setCreateBy(loginUser);
			 model.addAttribute("list", archiveManagerService.findList(new ArchiveManager()));
		}else{

			List<ArchiveManager> list = new ArrayList<ArchiveManager>();
			if(FormatUtil.isNoEmpty(archiveManager.getId())){
				archiveManager = archiveManagerService.get(archiveManager.getId());
//				archiveManager.setCreateBy(loginUser);
				list = archiveManagerService.findList(archiveManager);
				list.add(archiveManager);
			}else{
//				archiveManager.setCreateBy(loginUser);
				list = archiveManagerService.findList(archiveManager);
			}
			
			 model.addAttribute("list", list);
		}
		model.addAttribute("archiveManager", archiveManager);
		return "modules/filemanager/archive/archiveManagerList";
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
	@RequiresPermissions(value={"ehr:archiveManager:view","ehr:archiveManager:add","ehr:archiveManager:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ArchiveManager archiveManager, Model model) {
		if (archiveManager.getParent()!=null && archiveManager.getParent().getId()!=null){
			archiveManager.setParent(archiveManagerService.get(archiveManager.getParent().getId()));
		}
		model.addAttribute("archiveManager", archiveManager);
		return "modules/filemanager/archive/archiveManagerForm";
	}

	/**
	 * 保存信息
	 */
	@RequiresPermissions(value={"ehr:archiveManager:add","ehr:archiveManager:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ArchiveManager archiveManager, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, archiveManager)){
			return form(archiveManager, model);
		}
		ArchiveManager parentArchiveManager = archiveManagerService.get(archiveManager.getParentId());
		if(FormatUtil.isNoEmpty(parentArchiveManager)){
			if(FormatUtil.isNoEmpty(parentArchiveManager.getTopparentid())){
				archiveManager.setTopparentid(parentArchiveManager.getTopparentid());
			}else{
				archiveManager.setTopparentid(parentArchiveManager.getId());
			}
		}
		if(!archiveManager.getIsNewRecord()){//编辑表单保存
			ArchiveManager t = archiveManagerService.get(archiveManager.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(archiveManager, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			archiveManagerService.save(t);//保存
		}else{//新增表单保存
			archiveManagerService.save(archiveManager);//保存
		}
		addMessage(redirectAttributes, "保存信息成功");
		String id = "0".equals(archiveManager.getParentId()) ? "" : archiveManager.getParentId();
//		if(FormatUtil.isNoEmpty(parentArchiveManager)){
//			return "redirect:" + adminPath + "/ehr/archiveManager/list?id="+id+"&parentIds="+archiveManager.getParentIds();
//		}else{
		return "redirect:" + adminPath + "/ehr/archiveManager?repage";
//		}
	}
	
	/**
	 * 删除信息
	 */
	@RequiresPermissions("ehr:archiveManager:del")
	@RequestMapping(value = "delete")
	public String delete(ArchiveManager archiveManager, RedirectAttributes redirectAttributes) {
		archiveManagerService.delete(archiveManager);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:" + adminPath + "/ehr/archiveManager/list?repage";
	}
	
	/**
	 * 批量删除信息
	 */
	@RequiresPermissions("ehr:archiveManager:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			archiveManagerService.delete(archiveManagerService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/ehr/archiveManager/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ehr:archiveManager:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ArchiveManager archiveManager, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ArchiveManager> page = archiveManagerService.findPage(new Page<ArchiveManager>(request, response, -1), archiveManager);
    		new ExportExcel("信息", ArchiveManager.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/archiveManager/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ehr:archiveManager:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ArchiveManager> list = ei.getDataList(ArchiveManager.class);
			for (ArchiveManager archiveManager : list){
				try{
					archiveManagerService.save(archiveManager);
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
		return "redirect:"+Global.getAdminPath()+"/ehr/archiveManager/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
	@RequiresPermissions("ehr:archiveManager:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<ArchiveManager> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", ArchiveManager.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ehr/archiveManager/?repage";
    }
	
	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ArchiveManager> list = archiveManagerService.findList(new ArchiveManager());
		for (int i=0; i<list.size(); i++){
			ArchiveManager e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					//&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					//&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					//&& Global.YES.equals(e.getUseable())
					){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	

}