/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.*;
import com.jeeplus.modules.sys.service.OfficePostDescService;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.PostService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构Controller
 * @author jeeplus
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private SystemService systemService;

	@Autowired
	private PostService postService;

	@Autowired
	private OfficePostDescService officePostDescService;

	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.getOffice(id);
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:index")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:index")
	@RequestMapping(value = {"list"})
	public String list(Office office, Model model) {
		if(office==null || office.getParentIds() == null){
			 model.addAttribute("list", officeService.findList(false));
		}else{
			List<Office> list = new ArrayList<Office>();
			if(FormatUtil.isNoEmpty(office.getId())){
				office = officeService.get(office.getId());
				list = officeService.findList(office);
				list.add(officeService.get(office.getId()));
			}else{
				list = officeService.findList(office);
			}
			
			 model.addAttribute("list", list);
		}
		model.addAttribute("office", office);
		return "modules/sys/officeList";
	}
	
	@RequiresPermissions(value={"sys:office:view","sys:office:add","sys:office:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			if(FormatUtil.isNoEmpty(office.getParent().getCode())){
				office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
			}else{
				office.setCode(StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
			}
		}
		model.addAttribute("office", office);
		//model.addAttribute("allPosts", postService.findList(new Post()));
		return "modules/sys/officeForm";
	}
	
	@RequiresPermissions(value={"sys:office:add","sys:office:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return form(office, model);
		}
		Office parentoffice = officeService.get(office.getParentId());
		if(FormatUtil.isNoEmpty(parentoffice)){
			if(FormatUtil.isNoEmpty(parentoffice.getTopparentid())){
				office.setTopparentid(parentoffice.getTopparentid());
			}else{
				office.setTopparentid(parentoffice.getId());
			}
		}
		if(FormatUtil.isNoEmpty(office.getIsshowarea()) && "1".equals(office.getIsshowarea())){
			office.setName(office.getOname()+"("+office.getArea().getName()+")");
		}else{
			office.setName(office.getOname());
		}
		officeService.saveOfficePost(office);

//		Office o = new Office();
//		o.setParentIds(office.getId());
//		List<Office> childOfficeList = officeService.findByParentIdsLike(o);
//		for(Office of : childOfficeList){
//			of.setPostList(office.getPostList());
//			officeService.updateOfficePosts(of);
//		}
		
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		if(FormatUtil.isNoEmpty(parentoffice)){
			return "redirect:" + adminPath + "/sys/office/list?repage&id="+id+"&parentIds="+office.getParentIds();
		}else{
			return "redirect:" + adminPath + "/sys/office/list?repage";
		}
		
	}
	
	@RequiresPermissions("sys:office:del")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list?repage";
		}
//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
			officeService.delete(office);
			addMessage(redirectAttributes, "删除机构成功");
			UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
			UserUtils.removeCache(UserUtils.CACHE_OFFICE_ALL_LIST);
//		}
		return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds();
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
		List<Office> list = officeService.findList(isAll);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
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
	
	@RequiresPermissions("sys:office:auth")
	@RequestMapping(value = "auth")
	public String auth(Office office, Model model) {
		if(FormatUtil.isNoEmpty(office)){
			List<String> menuIdList=systemService.getMenuIds(office.getId());
			if(FormatUtil.isNoEmpty(menuIdList)&&menuIdList.size()>0){
				model.addAttribute("menuIds", StringUtils.join(menuIdList, ","));
			}
		}
		model.addAttribute("office", office);
		model.addAttribute("menuList", systemService.findAllMenu());
		model.addAttribute("officeList", officeService.findAll());
		return "modules/sys/officeAuth";
	}
	
	@RequiresPermissions(value={"sys:office:assign","sys:office:auth","sys:office:add","sys:office:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveAuth")
	public String saveAuth(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(!UserUtils.getUser().isAdmin()){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + adminPath + "/sys/office/?repage";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/?repage";
		}
		if (!beanValidator(model, office)){
			return list(office, model);
		}
		systemService.saveOffice(office);
		addMessage(redirectAttributes, "保存部门权限成功");
		return "redirect:" + adminPath + "/sys/office/?repage";
	}

	@RequestMapping(value = "getOfficeByid", method= RequestMethod.POST)
	public void getOfficeByid(Office office, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();
		try {
//			String id = request.getParameter("id");
//			Office o = officeService.get(id);

			map.put("type", office.getType());
			map.put("status", "y");
			map.put("info", "添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("添加失败：", e);
			map.put("status", "n");
			map.put("info", "添加失败");
		}

		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	@RequestMapping(value = {"jobDescIndex"})
	public String jobDescIndex(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeJobDescIndex";
	}

	@RequestMapping(value = {"jobDesc"})
	public String jobDesc(Office office, Model model) {
		for(Post p : office.getPostList()){
			OfficePostDesc officePostDesc = new OfficePostDesc();
			officePostDesc.setOffice(new Office(office.getId()));
			officePostDesc.setPost(new Post(p.getId()));
			List<OfficePostDesc> l = officePostDescService.findList(officePostDesc);
			if(l.size() > 0){
				p.setOfficePostDesc(l.get(0));
			}
		}
        model.addAttribute("office", office);
		return "modules/sys/officeJobDesc";
	}

	@RequestMapping(value = {"jobDescView"})
	public String jobDescView(Office office, Model model) {
	    User loginUser = UserUtils.getUser();
        OfficePostDesc officePostDesc = new OfficePostDesc();
        officePostDesc.setOffice(new Office(loginUser.getOffice().getId()));
        officePostDesc.setPost(new Post(loginUser.getStationType()));
        List<OfficePostDesc> l = officePostDescService.findList(officePostDesc);
        if(l.size() > 0){
            model.addAttribute("officePostDesc", l.get(0));
        }else{
            model.addAttribute("officePostDesc", officePostDesc);
        }

		return "modules/sys/officeJobDescView";
	}

    @RequestMapping(value = {"jobDescUpload"})
    public String jobDescUpload(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
        return "modules/sys/officeJobDescFile";
    }


    @RequestMapping(value = "saveFiles", method=RequestMethod.POST)
    public void saveFiles(Office office, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        PrintWriter out =  response.getWriter();
        Map map = new HashMap();

        try {
            String id = (String) request.getParameter("id");
            String file1 = (String) request.getParameter("file1");
            String file2 = (String) request.getParameter("file2");

            office = officeService.get(id);
//    		institution.setContent(content);
            office.setFile1(file1);
            office.setFile2(file2);

            officeService.save(office);

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

    @RequestMapping(value = "pdfView")
    public String pdfView(Office office, Model model, HttpServletRequest request, HttpServletResponse response) {
	    if("1".equals(request.getParameter("type"))){
            model.addAttribute("fileurl", Global.PDF_VIEW_PATH+office.getFile1());
        }else{
            model.addAttribute("fileurl", Global.PDF_VIEW_PATH+office.getFile2());
        }

        return "modules/sys/pdfView";
    }

	@RequestMapping(value = "viewPdf")
	public String viewPdf(Office office, Model model, HttpServletRequest request, HttpServletResponse response) {

		model.addAttribute("fileurl", request.getParameter("fileurl"));

		return "modules/sys/pdfView";
	}

	@RequestMapping(value = "getPostsByOfficeId", method=RequestMethod.POST)
	public void getPostsByOfficeId(Office office, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();

		try {
			String officeid = (String) request.getParameter("officeid");

			office = officeService.getOffice(officeid);

            List listtemp = new ArrayList();
            Map<String, Object> maptemp = null;
 			if(office.getPostList().size() > 0){
			    for (Post p : office.getPostList()){
                    maptemp = new HashMap();
                    maptemp.put("name",p.getPostname());
                    maptemp.put("id",p.getId());
                    listtemp.add(maptemp);
                }
            }
			map.put("postlist", listtemp);

			map.put("status", "y");
			map.put("info", "成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存失败：", e);
			map.put("status", "n");
			map.put("info", "失败");
		}

		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}

	@RequestMapping(value = "organizationalStructure")
	public String organizationalStructure(Office office, Model model, HttpServletRequest request, HttpServletResponse response) {

		model.addAttribute("fileurl", request.getParameter("fileurl"));

		return "modules/sys/organizationalStructure";
	}

	@RequestMapping(value = "organizationalStructureJson", method=RequestMethod.POST)
	public void organizationalStructureJson(Office office, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =  response.getWriter();
		Map map = new HashMap();

		User loginUser = UserUtils.getUser();

		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> officeList = officeService.findList(true);
		Office myCompany = null;
		for (int i=0; i<officeList.size(); i++){
			Office e = officeList.get(i);
			if(e.getId().equals(loginUser.getCompany().getId())){
				myCompany = e;
			}
		}
		if(FormatUtil.isNoEmpty(myCompany)){
			Joffice jCompany = new Joffice(myCompany);
			for (int i=0; i<officeList.size(); i++){
				Joffice ii = new Joffice(officeList.get(i));
				if(ii.getPid().equals(jCompany.getId())){

					for (int j=0; j<officeList.size(); j++){
						Joffice jj = new Joffice(officeList.get(j));
						if(jj.getPid().equals(ii.getId())){

							for (int k=0; k<officeList.size(); k++){
								Joffice kk = new Joffice(officeList.get(k));
								if(kk.getPid().equals(jj.getId())){

									for (int l=0; l<officeList.size(); l++){
										Joffice ll = new Joffice(officeList.get(l));
										if(ll.getPid().equals(kk.getId())){


											kk.getChildrens().add(ll);
										}
									}

									jj.getChildrens().add(kk);
								}
							}

							ii.getChildrens().add(jj);
						}
					}

					jCompany.getChildrens().add(ii);
				}
			}

			List<Joffice> myjoffices = Lists.newArrayList();
			myjoffices.add(jCompany);
			map.put("data",myjoffices);

		}


		out.write(JSONObject.fromObject(map).toString());
		out.close();
	}



}
