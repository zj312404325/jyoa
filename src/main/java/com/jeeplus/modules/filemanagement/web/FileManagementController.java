/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.filemanagement.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

import scala.util.matching.Regex;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.BaseConst;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.watermark.OfficeToSwf;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.ehr.entity.ArchiveManager;
import com.jeeplus.modules.ehr.service.ArchiveManagerService;
import com.jeeplus.modules.filemanagement.dao.AnnotateDao;
import com.jeeplus.modules.filemanagement.dao.EditRecordDao;
import com.jeeplus.modules.filemanagement.entity.Annotate;
import com.jeeplus.modules.filemanagement.entity.FileManagement;
import com.jeeplus.modules.filemanagement.service.FileManagementService;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 档案管理Controller
 * @author cqj
 * @version 2017-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/filemanagement/fileManagement")
public class FileManagementController extends BaseController {

	@Autowired
	private FileManagementService fileManagementService;
	
	@Autowired
	private ArchiveManagerService archiveManagerService;
	
	@Autowired
	private AnnotateDao annotateDao;
	
	@Autowired
	private EditRecordDao editRecordDao;
	
	@ModelAttribute
	public FileManagement get(@RequestParam(required=false) String id) {
		FileManagement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fileManagementService.get(id);
		}
		if (entity == null){
			entity = new FileManagement();
		}
		return entity;
	}
	
	/**
	 * 档案管理列表页面
	 */
	@RequiresPermissions("filemanagement:fileManagement:list")
	@RequestMapping(value = {"","index"})
	public String goindex(FileManagement fileManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
	    request.setAttribute("categoryid",request.getParameter("categoryid"));
		return "modules/filemanager/filemanagement/fileManagementIndex";
	}
	
	@RequiresPermissions("filemanagement:fileManagement:list")
	@RequestMapping(value = {"list"})
	public String list(FileManagement fileManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		fileManagement.setCreateBy(UserUtils.getUser());
		/*if(checkFileManager(fileManagement)){
			fileManagement.setIsManager("1");
		}else{
			fileManagement.setIsManager("0");
		}*/
		fileManagement.setIsManager("1");
		if(FormatUtil.isNoEmpty(request.getParameter("categoryid"))){
			setCategoryIds(request.getParameter("categoryid"),fileManagement);
		}
		Page<FileManagement> page = fileManagementService.findPage(new Page<FileManagement>(request, response), fileManagement);
		model.addAttribute("page", page);
		
		if(FormatUtil.isNoEmpty(fileManagement.getCategoryid())){
			fileManagement.setCategoryname(archiveManagerService.get(fileManagement.getCategoryid()).getName());
		}
		model.addAttribute("fileManagement", fileManagement);
		return "modules/filemanager/filemanagement/fileManagementList";
	}

	/**
	 * 设置需要查找的类目
	 * @param parameter
	 * @param fileManagement
	 */
	private void setCategoryIds(String categoryId, FileManagement fileManagement) {
		ArchiveManager archiveManager=new ArchiveManager();
		archiveManager.setId(categoryId);
		List<ArchiveManager> archiveManagerList=archiveManagerService.findCurrentAndChildCategory(archiveManager);
		String ids="";
		for (ArchiveManager archiveManager2 : archiveManagerList) {
			ids+="'"+archiveManager2.getId()+"',";
		}
		if(FormatUtil.isNoEmpty(ids)){
			ids=ids.substring(0,ids.length()-1);
			fileManagement.setCategoryIds(ids);
		}
	}

	/**
	 * 验证是否是档案管理员
	 * @param fileManagement
	 */
	private boolean checkFileManager(FileManagement fileManagement) {
		// TODO Auto-generated method stub
		User user=fileManagement.getCreateBy();
		List<Role> roleList=user.getRoleList();
		boolean result=false;
		for (Role role : roleList) {
			if(role.getId().equals(BaseConst.FILEMANAGEMENT_ROLE_ID)){
				result=true;
				break;
			}
		}
		return result;
	}

	/**
	 * 查看，增加，编辑档案管理表单页面
	 */
	@RequiresPermissions(value={"filemanagement:fileManagement:view","filemanagement:fileManagement:add","filemanagement:fileManagement:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FileManagement fileManagement, Model model,HttpServletRequest request) {
		if(FormatUtil.isNoEmpty(fileManagement.getCategoryid())){
			ArchiveManager am=archiveManagerService.get(fileManagement.getCategoryid());
			if(FormatUtil.isNoEmpty(am)){
				fileManagement.setCategoryname(archiveManagerService.get(fileManagement.getCategoryid()).getName());
			}
		}
		model.addAttribute("fileManagement", fileManagement);
		if(FormatUtil.isNoEmpty(request.getParameter("mtd"))){
			String fileurl="/webpage/modules/ehr/viewPDF/web/viewer.html?file="+fileManagement.getFilepdf();
			
			if(fileManagement.getComplete().equals("0")){
				request.setAttribute("filesuccess", true);
				request.setAttribute("fileexist", false);
			}else{
				if(isExistPdf(fileManagement.getFilepdf())){
					request.setAttribute("fileexist", true);
					request.setAttribute("filesuccess", true);
					request.setAttribute("fileurl", fileurl);
				}else{
					request.setAttribute("filesuccess", false);
				}
			}
			return "modules/filemanager/filemanagement/fileManagementForm";
		}else{
			if(FormatUtil.isNoEmpty(fileManagement)&&FormatUtil.isNoEmpty(fileManagement.getFileurl())){
				fileManagement.setFilename(FormatUtil.getfilename(fileManagement.getFileurl()));
			}
			return "modules/filemanager/filemanagement/fileManagementAddForm";
		}
	}
	@RequiresPermissions(value={"filemanagement:fileManagement:view","filemanagement:fileManagement:add","filemanagement:fileManagement:edit"},logical=Logical.OR)
	@RequestMapping(value = "author")
	public String author(FileManagement fileManagement, Model model,HttpServletRequest request) {
		if(FormatUtil.isNoEmpty(fileManagement.getCategoryid())){
			ArchiveManager am=archiveManagerService.get(fileManagement.getCategoryid());
			if(FormatUtil.isNoEmpty(am)){
				fileManagement.setCategoryname(archiveManagerService.get(fileManagement.getCategoryid()).getName());
			}
		}
		model.addAttribute("fileManagement", fileManagement);

		return "modules/filemanager/filemanagement/fileManagementAuthorForm";

	}

	private boolean isExistPdfUrl(String filepdf) {
		if(FormatUtil.isNoEmpty(filepdf)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 文件是否存在
	 * @param filepdf
	 * @return
	 */
	private boolean isExistPdf(String filepdf) {
		String root=Global.getWebRoot()+filepdf;
		File file=new File(root);
		return file.exists();
	}

	/**
	 * 保存档案管理
	 */
	@RequiresPermissions(value={"filemanagement:fileManagement:add","filemanagement:fileManagement:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(FileManagement fileManagement, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, fileManagement)){
			return form(fileManagement, model,request);
		}
		
		if(!FormatUtil.isNoEmpty(fileManagement.getCategoryid()) || !FormatUtil.isNoEmpty(fileManagement.getCategoryname())){
			addMessage(redirectAttributes, "保存档案管理失败");
			return "redirect:"+Global.getAdminPath()+"/filemanagement/fileManagement/?repage";
		}
		FileManagement fm=null;
		if(!fileManagement.getIsNewRecord()){//编辑表单保存
			FileManagement t = fileManagementService.get(fileManagement.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(fileManagement, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			t.setComplete("0");
			fileManagementService.save(t);//保存
			fm=t;
		}else{//新增表单保存
			fileManagement.setComplete("0");
			fileManagementService.save(fileManagement);//保存
			fm=fileManagement;
		}
		
		//生成预览pdf
		try{
			if(FormatUtil.isNoEmpty(fm.getFileurl() )){
				String waterMarkString=Global.WATERMARK+" "+UserUtils.getUser().getName()+" "+FormatUtil.dateToString(new Date(), "yyyy-MM-dd");
				if(OfficeToSwf.isWinOs()){
					createPdf(fm,waterMarkString,"","");
				}else{
					createPdfByLinux(fm,waterMarkString,"","");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		addMessage(redirectAttributes, "保存档案管理成功");
		return "redirect:"+Global.getAdminPath()+"/filemanagement/fileManagement/index?categoryid="+fileManagement.getCategoryid();
	}

	@RequiresPermissions(value={"filemanagement:fileManagement:add","filemanagement:fileManagement:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveAuthor")
	public String saveAuthor(FileManagement fileManagement, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, fileManagement)){
			return form(fileManagement, model,request);
		}
		fileManagementService.saveAuthor(fileManagement);//保存

		addMessage(redirectAttributes, "档案授权成功");
		return "redirect:"+Global.getAdminPath()+"/filemanagement/fileManagement/index?categoryid="+fileManagement.getCategoryid();
	}
	
	/**
	 * 生成预览pdf
	 */
	private void createPdf(final FileManagement fm,final String watermarkString,final String preHeader,final String footer) {
		// TODO Auto-generated method stub
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String inputPath=fm.getFileurl();
				try {
					String webroot=Global.getWebRoot();
					String outputFileName=webroot+"\\watermark"+getOutputFilename(inputPath);
					String tempInputPath=inputPath.replace("/", "\\");
					tempInputPath=java.net.URLDecoder.decode(tempInputPath, "utf-8");
					String tempOutputFileName=outputFileName.replace("/", "\\");
					tempOutputFileName=java.net.URLDecoder.decode(tempOutputFileName, "utf-8");
					String outputwithwatermark=tempOutputFileName.substring(0, tempOutputFileName.lastIndexOf("\\"))+"\\"+FormatUtil.getUUID()+".pdf";
					System.out.println("转pdf源文件："+webroot+tempInputPath);
					System.out.println("转pdf目标文件："+tempOutputFileName);
					boolean result=false;
					try {
						result=OfficeToSwf.officeToPdf(webroot+tempInputPath, tempOutputFileName,0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("水印源文件："+tempOutputFileName);
					System.out.println("水印目标文件："+outputwithwatermark);
					OfficeToSwf.addFooterAndWater(tempOutputFileName, outputwithwatermark, watermarkString, preHeader, footer);
					if(!OfficeToSwf.isExistFile((outputwithwatermark.replace(webroot,"")).replace("\\", "/"))){
						result=false;
					}else{
						result=true;
					}
					if(!result){
						fm.setFilepdf("");
					}else{
						fm.setFilepdf((outputwithwatermark.replace(webroot,"")).replace("\\", "/"));
					}
					fm.setIgnoreEditRecord("1");
					fm.setComplete("1");
					fileManagementService.save(fm);
					editRecordDao.updateEditRecord(fm);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	private void createPdfByLinux(final FileManagement fm,final String watermarkString,final String preHeader,final String footer) {
		// TODO Auto-generated method stub
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String inputPath=fm.getFileurl();
				try {
					String webroot=Global.getWebRoot();
					String outputFileName=webroot+"/watermark"+getOutputFilename(inputPath);
					String tempInputPath=inputPath;
					tempInputPath=java.net.URLDecoder.decode(tempInputPath, "utf-8");
					String tempOutputFileName=outputFileName;
					tempOutputFileName=java.net.URLDecoder.decode(tempOutputFileName, "utf-8");
					String outputwithwatermark=tempOutputFileName.substring(0, tempOutputFileName.lastIndexOf("/"))+"/"+FormatUtil.getUUID()+".pdf";
					System.out.println("转pdf源文件："+webroot+tempInputPath);
					System.out.println("转pdf目标文件："+tempOutputFileName);
					boolean result=false;
					try {
						result=OfficeToSwf.officeToPdf(webroot+tempInputPath, tempOutputFileName,0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("水印源文件："+tempOutputFileName);
					System.out.println("水印目标文件："+outputwithwatermark);
					OfficeToSwf.addFooterAndWater(tempOutputFileName, outputwithwatermark, watermarkString, preHeader, footer);
					if(!OfficeToSwf.isExistFile(outputwithwatermark.replace(webroot,""))){
						result=false;
					}else{
						result=true;
					}
					if(!result){
						fm.setFilepdf("");
					}else{
						fm.setFilepdf(outputwithwatermark.replace(webroot,""));
					}
					fm.setComplete("1");
					fm.setIgnoreEditRecord("1");
					fileManagementService.save(fm);
					editRecordDao.updateEditRecord(fm);
				} catch (IOException e) {
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
	 * 删除档案管理
	 */
	@RequiresPermissions("filemanagement:fileManagement:del")
	@RequestMapping(value = "delete")
	public String delete(FileManagement fileManagement, RedirectAttributes redirectAttributes) {
		fileManagementService.delete(fileManagement);
		addMessage(redirectAttributes, "删除档案管理成功");
		return "redirect:"+Global.getAdminPath()+"/filemanagement/fileManagement/list/?repage";
	}
	
	/**
	 * 批量删除档案管理
	 */
	@RequiresPermissions("filemanagement:fileManagement:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fileManagementService.delete(fileManagementService.get(id));
		}
		addMessage(redirectAttributes, "删除档案管理成功");
		return "redirect:"+Global.getAdminPath()+"/filemanagement/fileManagement/list/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("filemanagement:fileManagement:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(FileManagement fileManagement, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "档案管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            fileManagement.setCreateBy(UserUtils.getUser());
    		if(checkFileManager(fileManagement)){
    			fileManagement.setIsManager("1");
    		}else{
    			fileManagement.setIsManager("0");
    		}
    		if(FormatUtil.isNoEmpty(request.getParameter("categoryid"))){
    			setCategoryIds(request.getParameter("categoryid"),fileManagement);
    		}
            Page<FileManagement> page = fileManagementService.findPage(new Page<FileManagement>(request, response, -1), fileManagement);
    		new ExportExcel("档案管理", FileManagement.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出档案管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/filemanagement/fileManagement/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("filemanagement:fileManagement:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FileManagement> list = ei.getDataList(FileManagement.class);
			for (FileManagement fileManagement : list){
				try{
					fileManagementService.save(fileManagement);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条档案管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条档案管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入档案管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/filemanagement/fileManagement/?repage";
    }
	
	/**
	 * 下载导入档案管理数据模板
	 */
	@RequiresPermissions("filemanagement:fileManagement:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "档案管理数据导入模板.xlsx";
    		List<FileManagement> list = Lists.newArrayList(); 
    		new ExportExcel("档案管理数据", FileManagement.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/filemanagement/fileManagement/?repage";
    }
	
	@RequestMapping(value = "testPdf")
	public void testPdf(HttpServletResponse response,HttpServletRequest request) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer=response.getWriter();
		String webroot=Global.getWebRoot();
		try {
			OfficeToSwf.officeToPdf(webroot+"/1.doc", webroot+"/2.pdf",0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		OfficeToSwf.addFooterAndWater(webroot+"/2.pdf", webroot+"/2watermark.pdf", "WordToPdf水印严禁复制", "WordToPdf页眉", "WordToPdf页脚");
		writer.write("OK");
		writer.close();
    }

	@RequestMapping(value = {"statistics"})
	public String statistics(FileManagement fileManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		User loginUser = UserUtils.getUser();
		ArchiveManager am = new ArchiveManager();
		am.setParent(new ArchiveManager("0"));
//		am.setCreateBy(loginUser);
		List<ArchiveManager> list = archiveManagerService.findListByArchiveManager(am);
		for (ArchiveManager archiveManager : list) {
			am = new ArchiveManager();
			am.setParentIds(archiveManager.getId());
//			am.setCreateBy(loginUser);
			List<ArchiveManager> l = archiveManagerService.findListByArchiveManager(am);
			String ids="'"+archiveManager.getId()+"'";
			for (ArchiveManager archiveManager2 : l) {
				ids+=",'"+archiveManager2.getId()+"'";
			}
			FileManagement fm=new FileManagement();
//			fm.setCreateBy(loginUser);
			fm.setCategoryIds(ids);
			List<FileManagement> fileManagementList=fileManagementService.findList(fm);
			archiveManager.setTotal(fileManagementList.size());
			fm.setIsCurrentMonth("true");
			fileManagementList=fileManagementService.findList(fm);
			archiveManager.setMonthTotal(fileManagementList.size());
		}
		model.addAttribute("list", list);
		
		return "modules/filemanager/filemanagement/fileManagementStatistics";
	}

	/**
	 * 异步保存数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "saveAjax")
	public void saveAjax(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();
    	try {
    		String id=request.getParameter("id");
    		if(FormatUtil.isNoEmpty(id)){
    			FileManagement fm= fileManagementService.get(id);
    			fm.setFileno(request.getParameter("fileno"));
    			fm.setCategoryno(request.getParameter("categoryno"));
    			fm.setRollno(request.getParameter("rollno"));
    			fm.setFondno(request.getParameter("fondno"));
    			fm.setPieceno(request.getParameter("pieceno"));
    			fm.setWirteno(request.getParameter("wirteno"));
    			fm.setOrganization(request.getParameter("organization"));
    			fm.setFileyear(request.getParameter("fileyear"));
				fm.setFilemonth(request.getParameter("filemonth"));
    			fm.setSecret(request.getParameter("secret"));
    			fm.setPosition(request.getParameter("position"));
    			if(FormatUtil.isNoEmpty(request.getParameter("num"))){
    				fm.setNum(FormatUtil.toInteger(request.getParameter("num")));
    			}
    			if(FormatUtil.isNoEmpty(request.getParameter("pages"))){
    				fm.setPages(FormatUtil.toInteger(request.getParameter("pages")));
    			}
    			fm.setSavetime(request.getParameter("savetime"));
    			fm.setMemo(request.getParameter("memo"));
    			if(FormatUtil.isNoEmpty(request.getParameter("effectivedate"))){
    				fm.setEffectivedate(FormatUtil.StringToDate(request.getParameter("effectivedate"), "yyyy-MM-dd"));
    			}
    			if(FormatUtil.isNoEmpty(request.getParameter("expirydate"))){
    				fm.setExpirydate(FormatUtil.StringToDate(request.getParameter("expirydate"), "yyyy-MM-dd"));
    			}
    			fm.setDestory(request.getParameter("destory"));
    			//不需要添加文件版本
    			fm.setIgnoreEditRecord("1");
    			fileManagementService.save(fm);
    			map.put("status", "y");
    			map.put("info", "保存成功！");
    		}else{
    			map.put("status", "n");
    			map.put("info", "id不能为空！");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "保存失败！");
		}
    	out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	/**
	 * 异步保存评注
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "saveAnnotate")
	public void saveAnnotate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();
    	try {
    		String id=request.getParameter("id");
    		if(FormatUtil.isNoEmpty(id)){
    			Annotate annotate=new Annotate();
    			annotate.setId(FormatUtil.getUUID());
    			annotate.setContent(request.getParameter("content"));
    			annotate.setCreateBy(UserUtils.getUser());
    			Date dt=new Date();
    			annotate.setCreateDate(dt);
    			annotate.setCreateusername(UserUtils.getUser().getName());
    			FileManagement fm=new FileManagement();
    			fm.setId(id);
    			annotate.setFilemanagement(fm);
    			annotateDao.insert(annotate);
    			map.put("status", "y");
    			map.put("dt", FormatUtil.dateToString(dt, "yyyy年MM月dd日"));
    			map.put("info", "保存成功！");
    		}else{
    			map.put("status", "n");
    			map.put("info", "id不能为空！");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "保存失败！");
		}
    	out.write(JSONObject.fromObject(map).toString());
		out.close();
	}
}