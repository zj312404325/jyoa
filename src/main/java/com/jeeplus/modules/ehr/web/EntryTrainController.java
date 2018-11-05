/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.web;

import java.io.PrintWriter;
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
import com.jeeplus.modules.ehr.entity.EntryContract;
import com.jeeplus.modules.ehr.entity.UserInfo;
import com.jeeplus.modules.ehr.service.EntryContractService;
import com.jeeplus.modules.ehr.service.UserInfoService;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.Oagroup;
import com.jeeplus.modules.oa.entity.Oagroupdtl;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 入职合同Controller
 * @author yc
 * @version 2017-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/ehr/train")
public class EntryTrainController extends BaseController {

	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping(value = "detail")
	public String detail( HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("fileurl", request.getParameter("fileurl"));
		request.setAttribute("trainno", request.getParameter("trainno"));
		return "modules/ehr/train/entryTrainDetail";
	}
	

	@RequestMapping(value = "trainOver", method=RequestMethod.POST)
	public void trainOver(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
    	PrintWriter out =  response.getWriter();
    	Map map = new HashMap();
    	try {
    		int trainno = FormatUtil.toInt(request.getParameter("trainno"));
    		UserInfo myuserInfo = userInfoService.getByCreateBy(UserUtils.getUser().getId());
    		switch (trainno) {
				case 1:myuserInfo.setEntrytrain1(1);break;
				case 2:myuserInfo.setEntrytrain2(1);break;
				case 3:myuserInfo.setEntrytrain3(1);break;
				case 4:myuserInfo.setEntrytrain4(1);break;
				case 5:myuserInfo.setEntrytrain5(1);break;
				case 6:myuserInfo.setEntrytrain6(1);break;
				case 7:myuserInfo.setEntrytrain7(1);break;
				case 8:myuserInfo.setEntrytrain8(1);break;
				case 9:myuserInfo.setEntrytrain9(1);break;
				case 10:myuserInfo.setEntrytrain10(1);break;
				case 11:myuserInfo.setEntrytrain11(1);break;
				case 12:myuserInfo.setEntrytrain12(1);break;
				default:
					break;
			}
    		userInfoService.saveOnly(myuserInfo);
    		
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
}