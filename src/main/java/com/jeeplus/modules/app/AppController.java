/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.app;

import com.Util.PropertiesUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.jpush.entity.Jpushregister;
import com.jeeplus.modules.jpush.service.JpushregisterService;
import com.jeeplus.modules.oa.dao.OaNotifyFileDao;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyFile;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@Controller
@RequestMapping(value = "app")
public class AppController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private OaNotifyService oaNotifyService;

	@Autowired
	private OaNotifyFileDao oaNotifyFileDao;

	@Autowired
	private JpushregisterService jpushregisterService;

	@Autowired
	private OfficeService officeService;

    @RequestMapping(value = "download")
    public String download( HttpServletRequest request, HttpServletResponse response, Model model) {
    	request.setAttribute("isOnline",Global.getIsOnline());
        return "modules/app/appDownload";
    }

	@RequestMapping(value = {"versionCheck"})
	public void versionCheck(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		Map<String, Object> map1 = new HashMap<String,Object>();

//		String ver = Global.Version;
//		String verName = Global.VersionName;
//		String appPath = Global.appPath;
//		String versionInfo = Global.VersionInfo;
        if(Global.getIsOnline()){
            String ver = PropertiesUtil.getPropertyValueFromProperties("Version");
            String verName = PropertiesUtil.getPropertyValueFromProperties("VersionName");
            String appPath = PropertiesUtil.getPropertyValueFromProperties("appPath");
            String versionInfo = PropertiesUtil.getPropertyValueFromProperties("VersionInfo");

            Map<String, Object> map = new HashMap<String,Object>();
            map.put("verCode", ver);
            map.put("verName", verName);
            map.put("versionInfo", versionInfo);
            map.put("apkPath", appPath);
            map1.put("data", map);
            PrintWriter writer =response.getWriter();
            JSONObject jsonstr =  JSONObject.fromObject(map);
            writer.write("["+jsonstr.toString()+"]");
            writer.close();
        }else{
            String ver = PropertiesUtil.getPropertyValueFromProperties("Version_test");
            String verName = PropertiesUtil.getPropertyValueFromProperties("VersionName_test");
            String appPath = PropertiesUtil.getPropertyValueFromProperties("appPath_test");
            String versionInfo = PropertiesUtil.getPropertyValueFromProperties("VersionInfo_test");

            Map<String, Object> map = new HashMap<String,Object>();
            map.put("verCode", ver);
            map.put("verName", verName);
            map.put("versionInfo", versionInfo);
            map.put("apkPath", appPath);
            map1.put("data", map);
            PrintWriter writer =response.getWriter();
            JSONObject jsonstr =  JSONObject.fromObject(map);
            writer.write("["+jsonstr.toString()+"]");
            writer.close();
        }

	}

	@RequestMapping(value = {"login"})
	public void login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		String login_name = request.getParameter("login_name");
		String password = request.getParameter( "password");
		String deviceId=request.getParameter("deviceId");
		String registerId=request.getParameter("registerId");
		String userId="";

        System.out.println("device:"+deviceId);
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey","");
		infoMap.put("data", "");
		if(login_name != null && !login_name.equals(""))
		{
			if(password != null && !password.equals(""))
			{
				User myuser = systemService.getUserByLoginName(login_name);
				if(myuser != null && myuser.getLoginName() != null){
					if (SystemService.validatePassword(password, myuser.getPassword())){
						if (Global.NO.equals(myuser.getLoginFlag())){
							infoMap.put("d", "n");
							infoMap.put("msg", "该已帐号禁止登录");
						}else{
							userId=myuser.getId();
							infoMap.put("d", "y");
							infoMap.put("msg", "登录成功");
							infoMap.put("data", myuser.getId());
							if("/images/nohead.jpg".equals(myuser.getPhoto())){
								infoMap.put("headimg", "");
							}else{
								infoMap.put("headimg", myuser.getPhoto());
							}
							infoMap.put("username", myuser.getName());

							infoMap.put("serverKey", TokenUtil.setServerkey(myuser.getId(),deviceId==null?"":deviceId));

							//极光推送相关
							registerJPush(registerId,userId);

//							infoMap.put("data",myuser);
//							infoMap.put("msg", "登录成功！");
//							JsonConfig jsonConfig = new JsonConfig();
//							jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//							JSONObject json = JSONObject.fromObject(infoMap,jsonConfig);
//							out.print(json.toString());
//							return;
						}
					}
					else{
						infoMap.put("d", "n");
						infoMap.put("msg", "密码错误");
					}
				}
				else{
					infoMap.put("d", "n");
					infoMap.put("msg", "用户名不存在");
				}

			}
			else{
				infoMap.put("d", "n");
				infoMap.put("msg", "密码不能为空");
			}
		}
		else{
			infoMap.put("d", "n");
			infoMap.put("msg", "用户名不能为空");
		}
		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		System.out.println(json.toString());
		return ;
	}

	@RequestMapping(value = {"loginoutJpushregister"})
	public void loginoutJpushregister(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        response.setCharacterEncoding("utf-8");
        PrintWriter out =  response.getWriter();
        //返回的信息Map
        Map<String,Object> infoMap = new HashMap<String,Object>();

		String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
		String registerId = request.getParameter("registerId");

		if(!FormatUtil.isNoEmpty(registerId)){
			System.out.println("registerId空啊。。。。");

            infoMap.put("d", "y");
            infoMap.put("msg", "退出成功");
			return;
		}
		try {
            Jpushregister jpushregisterTemp=new Jpushregister();
            jpushregisterTemp.setRegisterid(registerId);
            jpushregisterTemp.setUserid(userId);

            jpushregisterService.deleteJpushregister(jpushregisterTemp);

            infoMap.put("d", "y");
            infoMap.put("msg", "退出成功");
        }catch (Exception e){
		    e.printStackTrace();
            infoMap.put("d", "n");
            infoMap.put("msg", "退出失败");
        }
        JSONObject json = JSONObject.fromObject(infoMap);
        out.print(json.toString());
        System.out.println(json.toString());

		return ;
	}

//	public void indexAdData() throws Exception{
//		AdService adservice = (AdService) BaseTxService.getBeanStatic("AdService");
//		String aid = request.getParameter("id");
//		request.setAttribute("aid",aid);
//		List<BAddetail> list = adservice.findDetailAll(" from BAddetail where isdel = 0  and isshow = 1 and aid = '"+aid+"' order by adSortno ");
//		Map map = new HashMap();
//		map.put("serverKey",super.serverKey);
//		map.put("data", list);
//		response.setHeader("Content-Type", "text/html;charset=utf-8");
//		PrintWriter writer = response.getWriter();
//		JSONObject jsonstr =  JSONObject.fromObject(map);
//		writer.write(jsonstr.toString());
//	}


//	@RequestMapping(value = {"upload"})
//	public void upload(User user, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
//		response.setHeader("Content-Type", "text/html;charset=utf-8");
//		PrintWriter writer =response.getWriter();
//		Map mapInfo=new HashMap<String, String>();
//
//		ServletContext application = request.getSession().getServletContext();
//		String savePath = "/userfiles/"+UserUtils.getUser().getId()+"/files/"+DateUtils.DateToString(new Date(), DateStyle.YYYY_MM_DD.getValue())+"/"+new Date().getTime()+"/";
//		String url = application.getRealPath("/") ;
//		File dir = new File(url+savePath);
//		if (!dir.exists()) {
//			dir.mkdirs();
//			dir = null;
//		}
//
//        MultiValueMap<String,MultipartFile> map = ((MultipartHttpServletRequest) request).getMultiFileMap();// 为了获取文件，这个类是必须的
//        List<MultipartFile> list = map.get("fileUploade");// 获取到文件的列表
//        String fileName = "";
//        List<Map> fileList=new ArrayList<Map>();
//        // 将图片进行存储
//        for (MultipartFile mFile : list) {
//            String oldName= mFile.getOriginalFilename();//获取文件名称
//			oldName=FormatUtil.formatFileName(oldName);
//            fileName = oldName;
//            File f = new File(url + File.separator +savePath+ fileName);
//            try {
//                FileCopyUtils.copy(mFile.getBytes(), f);
//                Map fileMap=new HashMap();
//                fileMap.put("fileName",fileName);
//                fileMap.put("fileUrl",savePath+ fileName);
//                fileList.add(fileMap);
//            } catch (IOException e) {
//                e.printStackTrace();
//                mapInfo.put("status", "n");
//                mapInfo.put("info", "文件上传失败");
//                JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
//                String a =  jsonstr.toString();
//                writer.write(a);
//                writer.close();
//                return;
//            }
//        }
//		mapInfo.put("error", 0);
//		mapInfo.put("url", savePath+ fileName);
//		mapInfo.put("filename", fileName);
//		mapInfo.put("status", "y");
//		if(fileList.size()>0){
//            mapInfo.put("fileList", fileList);
//        }
//		JSONObject jsonstr =  JSONObject.fromObject(mapInfo);
//		String a =  jsonstr.toString();
//		writer.write(a);
//		writer.close();
//	}

	@RequestMapping(value = {"upload"})
	public void upload(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		Map infoMap=new HashMap<String, String>();


		Map<String,Object> infoMapData = new HashMap<String,Object>();
		infoMapData.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));

		String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
		User loginUser = UserUtils.get(userId);

		ServletContext application = request.getSession().getServletContext();
		String savePath = "/userfiles/"+ loginUser.getId()+"/appfiles/"+ DateUtils.DateToString(new Date(), DateStyle.YYYY_MM_DD.getValue())+"/"+new Date().getTime()+"/";
		String url = application.getRealPath("/") ;
		File dir = new File(url+savePath);
		if (!dir.exists()) {
			dir.mkdirs();
			dir = null;
		}

		DefaultMultipartHttpServletRequest multiRequest = (DefaultMultipartHttpServletRequest) request;
		Iterator<String> it= multiRequest.getFileNames();
		String fileName = "";
		while (it.hasNext()) {
			CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile(it.next());
			String oldName = file.getOriginalFilename();

			fileName = oldName;
			File f = new File(url + File.separator +savePath+ fileName);
			try {
				FileCopyUtils.copy(file.getBytes(), f);
			} catch (IOException e) {
				e.printStackTrace();
				infoMapData.put("d", "n");
				infoMapData.put("msg", "文件上传失败");
				infoMap.put("data", infoMapData);
				JSONObject json = JSONObject.fromObject(infoMap);
				out.print(json.toString());
				System.out.println(json.toString());
				return;
			}
		}

		//oa传阅详情页添加附件
		if("oanotify_file".equals(request.getParameter("filetype"))){
			String oanotifyid = request.getParameter("oanotifyid");
			OaNotify oaNotify = oaNotifyService.get(oanotifyid);
			if(FormatUtil.isNoEmpty(fileName)){//单文件
				OaNotifyFile file = new OaNotifyFile();
				file.setFilename(fileName);
				file.setFileurl(savePath+ fileName);
				file.setUser(loginUser);
				file.setOaNotify(oaNotify);
				Date uploaddate = new Date();
				file.setUploadDate(uploaddate);
				try {
					file.preInsert();
					oaNotifyFileDao.insert(file);

                    infoMapData.put("fileid", file.getId());
					infoMapData.put("uploadname", loginUser.getName());
					infoMapData.put("uploaddate",FormatUtil.DateToString(uploaddate,"yyyy-MM-dd HH:mm:ss"));
				} catch (Exception e) {
					e.printStackTrace();
					infoMapData.put("d", "n");
					infoMapData.put("msg", "文件上传失败");
					infoMap.put("data", infoMapData);
					JSONObject json = JSONObject.fromObject(infoMap);
					out.print(json.toString());
					System.out.println(json.toString());
					return;
				}
			}
		}else if("flow_file".equals(request.getParameter("filetype"))){
			String applyflowid = request.getParameter("applyflowid");
			if(FormatUtil.isNoEmpty(fileName)){//单文件
				OaNotifyFile file = new OaNotifyFile();
				file.setFilename(fileName);
				file.setFileurl(savePath+ fileName);
				file.setUser(loginUser);
				file.setOaNotify(new OaNotify(applyflowid));
				Date uploaddate = new Date();
				file.setUploadDate(uploaddate);
				try {
					file.preInsert();
					oaNotifyFileDao.insert(file);

					infoMapData.put("fileid", file.getId());
					infoMapData.put("uploadname", loginUser.getName());
					infoMapData.put("uploaddate",FormatUtil.DateToString(uploaddate,"yyyy-MM-dd HH:mm:ss"));
				} catch (Exception e) {
					e.printStackTrace();
					infoMapData.put("d", "n");
					infoMapData.put("msg", "文件上传失败");
					infoMap.put("data", infoMapData);
					JSONObject json = JSONObject.fromObject(infoMap);
					out.print(json.toString());
					System.out.println(json.toString());
					return;
				}
			}
		}

		infoMapData.put("url", savePath+ fileName);
		infoMapData.put("filename", fileName);
		infoMapData.put("msg", "附件上传成功");
		infoMapData.put("d", "y");
		infoMap.put("data", infoMapData);
		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		System.out.println(json.toString());
	}


	//极光推送数据
	public void registerJPush(String registerId, String userId) throws Exception {
		if(!FormatUtil.isNoEmpty(registerId)){
			System.out.println("registerId空啊。。。。");
			return;
		}
		Jpushregister jpushregisterTemp=new Jpushregister();
		jpushregisterTemp.setRegisterid(registerId);
		jpushregisterTemp.setUserid(userId);

		jpushregisterService.saveJpushregister(jpushregisterTemp);

		return ;
	}

	@RequestMapping(value = {"getOfficeList"})
	public void getOfficeList(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
							  @RequestParam(required=false) Long grade, @RequestParam(required=false) String isAll,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
		String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
		User loginUser = UserUtils.get(userId);

		//部门
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list=new ArrayList<>();
		if(isAll.equals("1")){
			list = officeService.findList(true,loginUser);
		}else {
			list = officeService.findList(false,loginUser);
		}

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
				map.put("isuser", "0");//表部门
				map.put("isParent", "0");
				if (type != null && "3".equals(type)){
					map.put("isParent", "1");
				}
				mapList.add(map);

				//查找部门下面的用户
				List<User> userlist = systemService.findUserByOfficeId(e.getId());
				for (int j=0; j<userlist.size(); j++){
					User eu = userlist.get(j);
					if(!eu.getId().equals("1")){//排除admin
						Map<String, Object> mapTemp = Maps.newHashMap();
						mapTemp.put("id", eu.getId());
						mapTemp.put("pId", e.getId());
						mapTemp.put("pIds", e.getParentIds()+e.getId()+",");
						mapTemp.put("name", StringUtils.replace(eu.getName(), " ", ""));
						mapTemp.put("isuser", "1");//表用户
						mapTemp.put("isParent", "0");
						mapList.add(mapTemp);
					}
				}
			}
		}
		infoMap.put("data", mapList);
		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		System.out.println(json.toString());
		return ;
	}
}
