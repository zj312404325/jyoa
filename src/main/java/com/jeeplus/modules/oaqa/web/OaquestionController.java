/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oaqa.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.oaqa.dao.OaanswerDao;
import com.jeeplus.modules.oaqa.entity.Oaanswer;
import com.jeeplus.modules.oaqa.entity.Oaquestion;
import com.jeeplus.modules.oaqa.service.OaquestionService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpException;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问答Controller
 * @author yc
 * @version 2018-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/oaqa/oaquestion")
public class OaquestionController extends BaseController {

	@Autowired
	private OaquestionService oaquestionService;

	@Autowired
	private OaanswerDao oaanswerDao;
	
	@ModelAttribute
	public Oaquestion get(@RequestParam(required=false) String id) {
		Oaquestion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaquestionService.get(id);
		}
		if (entity == null){
			entity = new Oaquestion();
		}
		return entity;
	}


	@RequiresPermissions("oaqa:oaquestion:list")
	@RequestMapping(value = "searchIndex")
	public String searchIndex(Oaquestion oaquestion, Model model) {
		return "modules/oaqa/oaquestionSearch";
	}
	/**
	 * 信息列表页面
	 */
	@RequiresPermissions("oaqa:oaquestion:list")
	@RequestMapping(value = {"list", ""})
	public String list(Oaquestion oaquestion, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		oaquestion.setHasanswer(request.getParameter("hasanswer"));
		if(FormatUtil.isNoEmpty(request.getParameter("var1"))){
			oaquestion.setVar1(java.net.URLDecoder.decode(request.getParameter("var1"), "utf-8"));
		}
		Page<Oaquestion> page = oaquestionService.findPage(new Page<Oaquestion>(request, response), oaquestion);
		for(Oaquestion q : page.getList()){
			q.setOaanswerList(oaanswerDao.findList(new Oaanswer(q)));
		}
		model.addAttribute("page", page);
		model.addAttribute("var1", request.getParameter("var1"));
		model.addAttribute("oaquestion", oaquestion);
		model.addAttribute("loginUser", UserUtils.getUser());
		return "modules/oaqa/oaquestionList";
	}

	/**
	 * 查看，增加，编辑信息表单页面
	 */
	@RequiresPermissions(value={"oaqa:oaquestion:view","oaqa:oaquestion:add","oaqa:oaquestion:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Oaquestion oaquestion, Model model) {
		model.addAttribute("oaquestion", oaquestion);
		for(Oaanswer an : oaquestion.getOaanswerList()){
			if(an.getCreateBy().getId().equals(oaquestion.getCreateBy().getId())){
				oaquestion.setVar1(an.getAnswer());
				break;
			}
		}
		return "modules/oaqa/oaquestionForm";
	}

	/**
	 * 保存信息
	 */
	@RequiresPermissions(value={"oaqa:oaquestion:add","oaqa:oaquestion:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Oaquestion oaquestion, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, oaquestion)){
			return form(oaquestion, model);
		}
		if(!oaquestion.getIsNewRecord()){//编辑表单保存
			Oaquestion t = oaquestionService.get(oaquestion.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(oaquestion, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			oaquestionService.save(t);//保存
		}else{//新增表单保存
			if(FormatUtil.isNoEmpty(oaquestion.getVar1())){
				List<Oaanswer> oaanswerList = new ArrayList<Oaanswer>();
				Oaanswer answer = new Oaanswer();
				answer.setAnswer(oaquestion.getVar1());
				oaanswerList.add(answer);
				oaquestion.setOaanswerList(oaanswerList);
			}
			oaquestionService.save(oaquestion);//保存
		}
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/oaqa/oaquestion/?repage";
	}
	
	/**
	 * 删除信息
	 */
//	@RequiresPermissions("oaqa:oaquestion:del")
	@RequestMapping(value = "delete")
	public String delete(Oaquestion oaquestion, RedirectAttributes redirectAttributes) {
		oaquestionService.delete(oaquestion);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/oaqa/oaquestion/?repage";
	}
	
	/**
	 * 批量删除信息
	 */
	@RequiresPermissions("oaqa:oaquestion:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oaquestionService.delete(oaquestionService.get(id));
		}
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/oaqa/oaquestion/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("oaqa:oaquestion:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Oaquestion oaquestion, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Oaquestion> page = oaquestionService.findPage(new Page<Oaquestion>(request, response, -1), oaquestion);
    		new ExportExcel("信息", Oaquestion.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/oaqa/oaquestion/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("oaqa:oaquestion:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Oaquestion> list = ei.getDataList(Oaquestion.class);
			for (Oaquestion oaquestion : list){
				try{
					oaquestionService.save(oaquestion);
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
		return "redirect:"+Global.getAdminPath()+"/oaqa/oaquestion/?repage";
    }
	
	/**
	 * 下载导入信息数据模板
	 */
	@RequiresPermissions("oaqa:oaquestion:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "信息数据导入模板.xlsx";
    		List<Oaquestion> list = Lists.newArrayList(); 
    		new ExportExcel("信息数据", Oaquestion.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/oaqa/oaquestion/?repage";
    }

    @RequestMapping(value = "doPraise", method=RequestMethod.POST)
    public void doPraise(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        PrintWriter out =  response.getWriter();
        Map map = new HashMap();

        String answerid = request.getParameter("answerid");
        String jyoa_qa_praise_ids = FormatUtil.toString(CookieUtils.getCookie(request,"jyoa_qa_praise_ids"));
        if(!jyoa_qa_praise_ids.contains(answerid)){
            try {
                Oaanswer oaanswer = oaanswerDao.get(answerid);
                int i = oaanswer.getPraise() +1;
                oaanswer.setPraise(i);
                oaanswerDao.update(oaanswer);
                CookieUtils.setCookie(response,"jyoa_qa_praise_ids",FormatUtil.toString(jyoa_qa_praise_ids)+answerid+",");

                map.put("status", "y");
                map.put("info", "点赞成功！");
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", "n");
                map.put("info", "点赞失败！");
            }

        }else{
            map.put("status", "n");
            map.put("info", "你已赞过，谢谢！");
        }
        out.write(JSONObject.fromObject(map).toString());
        out.close();

    }

	@RequestMapping(value = "subAnswer", method=RequestMethod.POST)
	public void subAnswer(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();

		User loginUser = UserUtils.getUser();

		String answerid = request.getParameter("answerid");
		String answercontent = request.getParameter("answercontent");
		String questionid = request.getParameter("questionid");

		try {
			if(FormatUtil.isNoEmpty(answerid)){
				Oaanswer oaanswer = oaanswerDao.get(answerid);
				oaanswer.setAnswer(answercontent);
				oaanswer.preUpdate();
				oaanswerDao.update(oaanswer);

				map.put("answerid", oaanswer.getId());
				map.put("name", loginUser.getName());
			}else{
				Oaanswer oaanswer = new Oaanswer();
				oaanswer.setAnswer(answercontent);
				oaanswer.setQuertionid(questionid);
				oaanswer.preInsert();
				oaanswerDao.insert(oaanswer);

				map.put("answerid", oaanswer.getId());
				map.put("name", loginUser.getName());
			}

			map.put("status", "y");
			map.put("info", "回答成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "回答失败！");
		}

		out.write(JSONObject.fromObject(map).toString());
		out.close();

	}

	@RequestMapping(value = "logicDelete", method=RequestMethod.POST)
	public void logicDelete(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();

		User loginUser = UserUtils.getUser();

		String type = request.getParameter("type");
		String id = request.getParameter("pid");

		try {
			if("0".equals(type)){
				oaquestionService.delete(new Oaquestion(id));
			}else if("1".equals(type)){
				oaanswerDao.delete(new Oaanswer(id));
			}

			map.put("status", "y");
			map.put("info", "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "删除失败！");
		}

		out.write(JSONObject.fromObject(map).toString());
		out.close();

	}

}