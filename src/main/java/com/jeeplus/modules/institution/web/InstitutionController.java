/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.institution.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.watermark.OfficeToSwf;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.filemanagement.entity.FileManagement;
import com.jeeplus.modules.institution.entity.Institution;
import com.jeeplus.modules.institution.service.InstitutionService;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONObject;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * oa制度Controller
 * @author 陈
 * @version 2016-12-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/institution")
public class InstitutionController extends BaseController {

	@Autowired
	private InstitutionService institutionService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Institution get(@RequestParam(required=false) String id) {
		Institution entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = institutionService.get(id);
		}
		if (entity == null){
			entity = new Institution();
		}
		return entity;
	}
	
	/**
	 * oa制度列表页面
	 */
	@RequiresPermissions("sys:institution:list")
	@RequestMapping(value = {"list", ""})
	public String list(Institution institution, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Institution> list = institutionService.findList(institution); 
		model.addAttribute("list", list);
		return "modules/institution/institutionList";
	}

	/**
	 * 查看，增加，编辑oa制度表单页面
	 */
	@RequiresPermissions(value={"sys:institution:view","sys:institution:add","sys:institution:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Institution institution, Model model) {
		if(FormatUtil.isNoEmpty(institution.getParent())){
			if(FormatUtil.isNoEmpty(institution.getParent().getId())){
				institution.setParent(institutionService.get(institution.getParent().getId()));
			}
		}
		
		model.addAttribute("institution", institution);
		return "modules/institution/institutionForm";
	}
	@RequiresPermissions(value={"sys:institution:view","sys:institution:add","sys:institution:edit"},logical=Logical.OR)
	@RequestMapping(value = "formMuti")
	public String formMuti(Institution institution, Model model) {
		if(FormatUtil.isNoEmpty(institution.getParent())){
			if(FormatUtil.isNoEmpty(institution.getParent().getId())){
				institution.setParent(institutionService.get(institution.getParent().getId()));
			}
		}

		model.addAttribute("institution", institution);
		return "modules/institution/institutionFormMuti";
	}
	
	@RequiresPermissions(value={"sys:institution:view","sys:institution:add","sys:institution:edit"},logical=Logical.OR)
	@RequestMapping(value = "contentForm")
	public String contentForm(Institution institution, Model model) {
		if(FormatUtil.isNoEmpty(institution.getParent())){
			if(FormatUtil.isNoEmpty(institution.getParent().getId())){
				institution.setParent(institutionService.get(institution.getParent().getId()));
			}
		}
		
		model.addAttribute("institution", institution);
//		return "modules/institution/institutionContentForm";
		return "modules/institution/institutionFormNew";
	}
	
	@RequestMapping(value = "contentView")
	public String contentView(Institution institution, Model model) {
		
		model.addAttribute("institution", institution);
		model.addAttribute("PDF_VIEW_PATH", Global.PDF_VIEW_PATH);
		//return "modules/institution/institutionContentView";
		return "modules/institution/institutionContentViewNew";
	}

	/**
	 * 保存oa制度
	 */
	@RequiresPermissions(value={"sys:institution:add","sys:institution:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Institution institution, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, institution)){
			return form(institution, model);
		}

		if(!"1".equals(institution.getItype())){
			institution.setFileurl(null);
		}
		String ilevel = "1";
		if(FormatUtil.isNoEmpty(institution.getParent().getId())){
			Institution tp = institutionService.get(institution.getParent().getId());
//			institution.setIlevel(FormatUtil.toString(FormatUtil.toInt(tp.getIlevel())+1));
            ilevel = FormatUtil.toString(FormatUtil.toInt(tp.getIlevel())+1);
//		}else{
//			institution.setIlevel("1");
		}
        institution.setIlevel(ilevel);
		if(FormatUtil.toInt(institution.getIlevel()) < 10){
		    if(FormatUtil.isNoEmpty(institution.getFileurl()) && !FormatUtil.isNoEmpty(institution.getId())){
                String[] fileurls = institution.getFileurl().split(",");
                String[] filenames = institution.getTitle().split(",");
                User user = UserUtils.getUser();
                for(int i = 0;i<fileurls.length;i++){
                    Institution ins = new Institution();
                    MyBeanUtils.copyBeanNotNull2Bean(institution, ins);
                    ins.setFileurl(fileurls[i]);
                    if(!FormatUtil.isNoEmpty(ins.getTitle())){
						ins.setTitle(filenames[i]);
					}
                    ins.setId(IdGen.uuid());
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(user.getId())){
                        ins.setUpdateBy(user);
                        ins.setCreateBy(user);
                    }
                    ins.setUpdateDate(new Date());
                    ins.setCreateDate(new Date());

                    if("pdf".equals(FormatUtil.getfileHouzhui(ins.getFileurl()))){
						ins.setSourcefileurl(fileurls[i]);
					}else{
						ins.setSourcefileurl(ins.getFileurl());
						//生成预览pdf
						try{
							if(FormatUtil.isNoEmpty(ins.getSourcefileurl())){

								String webroot=Global.getWebRoot();
								String outputFileName=webroot+getOutputFilename(ins.getSourcefileurl());
								String tempInputPath=ins.getSourcefileurl().replace("/", "\\");
								tempInputPath=java.net.URLDecoder.decode(tempInputPath, "utf-8");
								String tempOutputFileName=outputFileName.replace("/", "\\");
								tempOutputFileName=java.net.URLDecoder.decode(tempOutputFileName, "utf-8");

								if(OfficeToSwf.isWinOs()){
									createPdf(tempInputPath,tempOutputFileName,"","");
								}else{
									createPdfByLinux(tempInputPath,tempOutputFileName,"","");
								}

								ins.setFileurl((outputFileName.replace(webroot,"")).replace("\\", "/"));
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
                    institutionService.save(ins);//保存
                }
            }else{
                institutionService.save(institution);//保存
            }

			addMessage(redirectAttributes, "保存成功");
		}else{
			addMessage(redirectAttributes, "无法添加，最多9级目录");
		}
		
		return "redirect:"+adminPath+"/sys/institution/?repage";
	}

	/**
	 * 生成预览pdf
	 */
	private void createPdf(final String inputPath, final String outputPath, final String preHeader, final String footer) {
		// TODO Auto-generated method stub
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String webroot=Global.getWebRoot();
//					String outputwithwatermark=outputPath.substring(0, outputPath.lastIndexOf("\\"))+"\\"+FormatUtil.getUUID()+".pdf";
					System.out.println("转pdf源文件："+webroot+inputPath);
					System.out.println("转pdf目标文件："+outputPath);
					boolean result=false;
					try {
						result=OfficeToSwf.officeToPdf(webroot+inputPath, outputPath,0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	private void createPdfByLinux(final String inputPath,final String outputPath,final String preHeader,final String footer) {
		// TODO Auto-generated method stub
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String webroot=Global.getWebRoot();
//					String outputwithwatermark=tempOutputFileName.substring(0, tempOutputFileName.lastIndexOf("/"))+"/"+FormatUtil.getUUID()+".pdf";
					System.out.println("转pdf源文件："+webroot+inputPath);
					System.out.println("转pdf目标文件："+outputPath);
					boolean result=false;
					try {
						result=OfficeToSwf.officeToPdf(webroot+inputPath, outputPath,0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	private String getOutputFilename(String inputPath){
		String newName = inputPath.substring(0, inputPath.lastIndexOf("."))+".pdf";
		System.out.println(newName);
		return newName;
	}

	/**
	 * 删除oa制度
	 */
	@RequiresPermissions("sys:institution:del")
	@RequestMapping(value = "delete")
	public String delete(Institution institution, RedirectAttributes redirectAttributes) {
		institutionService.delete(institution);
		addMessage(redirectAttributes, "删除oa制度成功");
		return "redirect:"+Global.getAdminPath()+"/sys/institution/?repage";
	}
	
	/**
	 * 批量删除oa制度
	 */
	@RequiresPermissions("sys:institution:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			institutionService.delete(institutionService.get(id));
		}
		addMessage(redirectAttributes, "删除oa制度成功");
		return "redirect:"+Global.getAdminPath()+"/institution/institution/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:institution:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Institution institution, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "oa制度"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Institution> page = institutionService.findPage(new Page<Institution>(request, response, -1), institution);
    		new ExportExcel("oa制度", Institution.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出oa制度记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/institution/institution/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:institution:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Institution> list = ei.getDataList(Institution.class);
			for (Institution institution : list){
				try{
					institutionService.save(institution);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条oa制度记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条oa制度记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入oa制度失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/institution/institution/?repage";
    }
	
	/**
	 * 下载导入oa制度数据模板
	 */
	@RequiresPermissions("sys:institution:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "oa制度数据导入模板.xlsx";
    		List<Institution> list = Lists.newArrayList(); 
    		new ExportExcel("oa制度数据", Institution.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/institution/institution/?repage";
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
			 @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Institution in = new Institution();
		in.setItype(type);
		List<Institution> list = institutionService.findList(in);
		for (int i=0; i<list.size(); i++){
			Institution e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getTitle());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	@RequiresPermissions(value={"sys:institution:add","sys:institution:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveContent", method=RequestMethod.POST)
	public void saveContent(Institution institution,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();

    	try {
//    		String content = (String) request.getParameter("content");
    		String iid = (String) request.getParameter("iid");
			String fileurl = (String) request.getParameter("fileurl");
    		
    		institution = institutionService.get(iid);
//    		institution.setContent(content);
			institution.setFileurl(fileurl);
    		
    		institutionService.save(institution);
        	
        	map.put("status", "y");
			map.put("info", "保存成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存失败：", e);
			map.put("status", "n");
			map.put("info", "保存失败");
		}
    	
		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	@RequiresPermissions("sys:institution:edit")
	@RequestMapping(value = "auth")
	public String auth( HttpServletRequest request, HttpServletResponse response,Model model) {
		String roleid = request.getParameter("id");
		Role role = systemService.getRole(roleid);
		model.addAttribute("role", role);
		model.addAttribute("institutionList", institutionService.findList(new Institution()));
//		model.addAttribute("officeList", officeService.findAll());
		return "modules/institution/institutionAuth";
	}

	/**
	 * 批量修改排序
	 */
	@RequiresPermissions("sys:institution:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
//		if(Global.isDemoMode()){
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:" + adminPath + "/sys/menu/";
//		}
		Boolean flag = false;
    	for (int i = 0; i < ids.length; i++) {
    		if(FormatUtil.toInt(sorts[i])<=0){
    			flag = true;
    			break;
    		}
    		Institution institution = new Institution(ids[i]);
    		institution.setSort(FormatUtil.toInt(sorts[i]));
    		institutionService.updateSort(institution);
    	}
    	if(flag){
			addMessage(redirectAttributes, "排序数值必须大于零！");
			return "redirect:" + adminPath + "/sys/institution/";
    	}
    	addMessage(redirectAttributes, "保存制度排序成功!");
		return "redirect:" + adminPath + "/sys/institution/";
	}
}