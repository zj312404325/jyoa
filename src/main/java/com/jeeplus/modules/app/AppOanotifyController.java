/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.app;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.dwr.DwrUtil;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.sms.SmsUtil;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.websocket.onchat.ChatServerPool;
import com.jeeplus.common.websocket.utils.Constant;
import com.jeeplus.modules.oa.dao.OaNotifyFileDao;
import com.jeeplus.modules.oa.dao.OaNotifyRecordDao;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyFile;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.oa.entity.OaRecord;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringEscapeUtils;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import scala.util.parsing.combinator.testing.Str;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@Controller
@RequestMapping(value = "app/oanotify")
public class AppOanotifyController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private OaNotifyService oaNotifyService;
	@Autowired
	private OaNotifyRecordDao oaNotifyRecordDao;
	@Autowired
	private OaNotifyFileDao oaNotifyFileDao;
	@Autowired
	private UserDao userDao;


	@RequestMapping(value = {"getOaNotifyList"})
	public void getOaNotifyList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
		OaNotify oaNotify=new OaNotify();
		oaNotify.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
		//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				oaNotify.setSearchdays(4);
			}
		}

		oaNotify.setSelf(true);
		String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
		oaNotify.setCurrentUser(UserUtils.get(userId));
		oaNotify.setTitle(request.getParameter("keyword"));
		oaNotify.setType(request.getParameter("oanotifytype"));
		oaNotify.setReadFlag(request.getParameter("oanotifystatus"));
		String currentPage= request.getParameter("currentPage");
		Page<OaNotify> pg=new Page<OaNotify>(FormatUtil.toInteger(currentPage),Global.pageSize);
		Page<OaNotify> page = oaNotifyService.find(pg, oaNotify);
		List<OaNotify> oaNotifyList=page.getList();
		List<Map> dataList=new ArrayList<Map>();
		Map tempMap=null;
		if(FormatUtil.isNoEmpty(oaNotifyList)&&oaNotifyList.size()>0){
			for (OaNotify oa:oaNotifyList) {
				tempMap=new HashMap();
				tempMap.put("id",oa.getId());
				tempMap.put("title", "");
				if(FormatUtil.isNoEmpty(oa.getTitle())){
					tempMap.put("title", StringEscapeUtils.unescapeHtml(oa.getTitle()));
				}
				if(!FormatUtil.isNoEmpty(oa.getContent())){
					tempMap.put("content","未填写");
				}else{
					tempMap.put("content",FormatUtil.delHTMLTag(StringEscapeUtils.unescapeHtml(oa.getContent())));
				}
				User usr= systemService.getUser(oa.getCreateBy().getId());
				tempMap.put("createusername","");
				if(FormatUtil.isNoEmpty(usr)){
					tempMap.put("createusername",usr.getName());
				}
				tempMap.put("createdate",FormatUtil.dateToString(oa.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
				tempMap.put("readflag",oa.getReadFlag());
				tempMap.put("type",oa.getType());
				oaNotifyService.getRecordList(oa);
				tempMap.put("hasfile","0");
				if(FormatUtil.isNoEmpty(oa.getOaNotifyFileList())&&oa.getOaNotifyFileList().size()>0){
					tempMap.put("hasfile","1");
				}
				dataList.add(tempMap);
			}
		}
		infoMap.put("data", dataList);

		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		System.out.println(json.toString());
		return ;
	}

	@RequestMapping(value = {"getMyOaNotifyList"})
	public void getMyOaNotifyList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
		OaNotify oaNotify=new OaNotify();
		oaNotify.setSearchdays(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT));
		//		System.out.println(FormatUtil.dayForWeek("2018-04-23"));
		if("1".equals(FormatUtil.dayForWeek(FormatUtil.dateToString(new Date(),"yyyy-MM-dd")))){
			if(FormatUtil.toInt(Global.SEARCH_DAYS_LIMIT) == 2){
				oaNotify.setSearchdays(4);
			}
		}

		oaNotify.setSelf(false);
		String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
		oaNotify.setCurrentUser(UserUtils.get(userId));
		oaNotify.setTitle(request.getParameter("keyword"));
		oaNotify.setType(request.getParameter("oanotifytype"));
		oaNotify.setReadFlag(request.getParameter("oanotifystatus"));
		oaNotify.setStatus(request.getParameter("status"));
		String currentPage= request.getParameter("currentPage");
		Page<OaNotify> pg=new Page<OaNotify>(FormatUtil.toInteger(currentPage),Global.pageSize);
		Page<OaNotify> page = oaNotifyService.find(pg, oaNotify);
		List<OaNotify> oaNotifyList=page.getList();
		List<Map> dataList=new ArrayList<Map>();
		Map tempMap=null;
		if(FormatUtil.isNoEmpty(oaNotifyList)&&oaNotifyList.size()>0){
			for (OaNotify oa:oaNotifyList) {
				tempMap=new HashMap();
				tempMap.put("id",oa.getId());
				tempMap.put("title", "");
				if(FormatUtil.isNoEmpty(oa.getTitle())){
					tempMap.put("title", StringEscapeUtils.unescapeHtml(oa.getTitle()));
				}
				if(!FormatUtil.isNoEmpty(oa.getContent())){
					tempMap.put("content","未填写");
				}else{
					tempMap.put("content",FormatUtil.delHTMLTag(StringEscapeUtils.unescapeHtml(oa.getContent())));
				}
				User usr= systemService.getUser(oa.getCreateBy().getId());
				tempMap.put("createusername","");
				if(FormatUtil.isNoEmpty(usr)){
					tempMap.put("createusername",usr.getName());
				}
				tempMap.put("createdate",FormatUtil.dateToString(oa.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
				tempMap.put("readflag",oa.getReadFlag());
				tempMap.put("type",oa.getType());
				oaNotifyService.getRecordList(oa);
				tempMap.put("hasfile","0");
				if(FormatUtil.isNoEmpty(oa.getOaNotifyFileList())&&oa.getOaNotifyFileList().size()>0){
					tempMap.put("hasfile","1");
				}
				tempMap.put("oastatus",oa.getStatus());
				tempMap.put("readnum",oa.getReadNum());
				tempMap.put("totalnum",FormatUtil.toInteger(oa.getReadNum())+FormatUtil.toInteger(oa.getUnReadNum()));
				tempMap.put("commentnum",oa.getCommentNum());
				dataList.add(tempMap);
			}
		}
		infoMap.put("data", dataList);

		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		System.out.println(json.toString());
		return ;
	}

	@RequestMapping(value = "view")
	public void view(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
		Map<String,Object> infoMapData = new HashMap<String,Object>();

		String oanotifyId = request.getParameter("oanotifyId");
		if (StringUtils.isNotBlank(oanotifyId)){

			String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
			User loginUser = UserUtils.get(userId);

			OaNotify oaNotify = oaNotifyService.get(oanotifyId);
			String oanotifyReadFlag = oaNotify.getReadFlag();
			oaNotify.setCurrentUser(loginUser);
			oaNotifyService.updateReadFlag(oaNotify);

//			oaNotify.setCreateBy(UserUtils.get(oaNotify.getCreateBy().getId()));
			oaNotify = oaNotifyService.getRecordList(oaNotify);

//			JsonConfig jsonConfig1 = new JsonConfig();
//			jsonConfig1.setExcludes(new String[]{"oaNotifyRecordList","oaNotifyFileList","oaNotifyRecordNames","oaNotifyFileUrls","oaNotifyFileUrls2","oaNotifyRecordIds"});
//			infoMapData.put("oaNotify", JSONArray.fromObject(oaNotify,jsonConfig1));
			Map oanotifyMap = new HashMap();
			oanotifyMap.put("id",oaNotify.getId());
			oanotifyMap.put("readNum",oaNotify.getReadNum());
			oanotifyMap.put("unReadNum",oaNotify.getUnReadNum());
			oanotifyMap.put("commentNum",oaNotify.getCommentNum());
			oanotifyMap.put("title",StringEscapeUtils.unescapeHtml(oaNotify.getTitle()));
			oanotifyMap.put("oanotifytype",DictUtils.getDictLabel(oaNotify.getType(),"oa_notify_type", ""));
			oanotifyMap.put("otype",oaNotify.getType());
			oanotifyMap.put("ostatus",oaNotify.getStatus());
			oanotifyMap.put("createname",UserUtils.get(oaNotify.getCreateBy().getId()).getName());
			oanotifyMap.put("createdate",FormatUtil.dateToString(oaNotify.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
			oanotifyMap.put("content","");
//            oanotifyMap.put("content",oaNotify.getReadFlag());
			if(FormatUtil.isNoEmpty(oaNotify.getContent())){
				//oanotifyMap.put("content",FormatUtil.delHTMLTag(StringEscapeUtils.unescapeHtml(oaNotify.getContent())));
				String content=oaNotify.getContent();
				content = content.replace("\n",StringEscapeUtils.escapeHtml("<br>"));
				oanotifyMap.put("content",content);
			}
			oanotifyMap.put("mobileremind",oaNotify.getMobileremind());
			oanotifyMap.put("recordremind",oaNotify.getRecordremind());
			oanotifyMap.put("secretsend",oaNotify.getSecretsend());
			oanotifyMap.put("isallow",oaNotify.getIsallow());
			List<Map> fileList=new ArrayList<Map>();
			if(FormatUtil.isNoEmpty(oaNotify.getOaNotifyFileList())&&oaNotify.getOaNotifyFileList().size()>0) {
				Map tempMap=null;
				for (OaNotifyFile file : oaNotify.getOaNotifyFileList()) {
					tempMap = new HashMap();
					tempMap.put("id",file.getId());
					tempMap.put("fileurl",file.getFileurl());
					tempMap.put("filename",file.getFilename());
					User uploadUser = UserUtils.get(file.getUser().getId());
					tempMap.put("uploadname",FormatUtil.isNoEmpty(uploadUser)?uploadUser.getName():"");
					tempMap.put("uploaddate",FormatUtil.dateToString(file.getUploadDate(),"yyyy-MM-dd HH:mm:ss"));
					if(loginUser.getId().equals(file.getUser().getId())){
						tempMap.put("isuploadme","1");
					}else{
						tempMap.put("isuploadme","0");
					}
					fileList.add(tempMap);
				}
			}
			infoMapData.put("fileList", fileList);

			//接受人start 草稿编辑时处理
			String oanotifydraft=request.getParameter("oanotifydraft");
			if(FormatUtil.isNoEmpty(oanotifydraft)&&oanotifydraft.equals("1")){//草稿编辑时处理
				List<Map> recordList=new ArrayList<Map>();
				List<String> userIds=new ArrayList<>();
				List<String> userNames=new ArrayList<>();
				if(FormatUtil.isNoEmpty(oaNotify.getOaNotifyRecordList())&&oaNotify.getOaNotifyRecordList().size()>0) {
					Map tempMap=null;
					for (OaNotifyRecord oaRecord : oaNotify.getOaNotifyRecordList()) {
						tempMap=new HashMap();
						tempMap.put("id",oaRecord.getId());
						userIds.add(oaRecord.getUser().getId());
						tempMap.put("userName", "");
						if(FormatUtil.isNoEmpty(oaRecord.getUserName())){
							tempMap.put("userName", oaRecord.getUserName());
						}
						userNames.add(oaRecord.getUserName());
						recordList.add(tempMap);
					}
				}
				infoMapData.put("userIds", FormatUtil.listToString(userIds));
				infoMapData.put("userNames", FormatUtil.listToString(userNames));
				infoMapData.put("recordList", recordList);
			}
			//接受人end

			oanotifyMap.put("hasfile","0");
			if(fileList.size()> 0 ){
				oanotifyMap.put("hasfile","1");
			}
			infoMapData.put("oaNotify", oanotifyMap);
//			Page<OaNotifyRecord> page = oaNotifyService.find(new Page<OaNotifyRecord>(request, response),new OaNotifyRecord(oaNotify));
//			model.addAttribute("page", page);
			infoMapData.put("myoaNotifyRecordId", "");
			for (OaNotifyRecord record : oaNotify.getOaNotifyRecordList()) {
				if(record.getUser().getId().equals(loginUser.getId())){
					infoMapData.put("myoaNotifyRecordId", record.getId());
				}
			}

//			List<Oagroup> groups = oagroupService.findList(new Oagroup());
//			model.addAttribute("groups", groups);


		}
		infoMap.put("data", infoMapData);
		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		System.out.println(json.toString());
	}

	@RequestMapping(value = "delOaNotifyFile")
	public void delOaNotifyFile(HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
		Map<String,Object> infoMapData = new HashMap<String,Object>();

		String fileid = request.getParameter("fileid");

		try {
			OaNotifyFile onf=oaNotifyFileDao.get(fileid);
			String url=onf.getFileurl();
			oaNotifyFileDao.delete(new OaNotifyFile(fileid));
			infoMapData.put("d", "y");
			infoMapData.put("msg", "附件删除成功！");
			infoMapData.put("url", url);
		} catch (Exception e) {
			e.printStackTrace();
			infoMapData.put("d", "n");
			infoMapData.put("msg", "附件删除失败！");
		}

		infoMap.put("data", infoMapData);
		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		System.out.println(json.toString());
	}

	@RequestMapping(value = {"getOaNotifyRecordList"})
	public void getOaNotifyRecordList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
        String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
        User loginUser = UserUtils.get(userId);
		OaNotify oaNotify=new OaNotify();
		String oanotifyId= request.getParameter("oanotifyId");
		List<Map> dataList=new ArrayList<Map>();
		if(FormatUtil.isNoEmpty(oanotifyId)){
			oaNotify.setId(oanotifyId);
			String currentPage= request.getParameter("currentPage");
			Page<OaNotifyRecord> pg=new Page<OaNotifyRecord>(FormatUtil.toInteger(currentPage),Global.pageSize);
			Page<OaNotifyRecord> page = oaNotifyService.find(pg, new OaNotifyRecord(oaNotify));
			infoMap.put("isFirstPage",page.isFirstPage());
			infoMap.put("isLastPage",page.isLastPage());
			infoMap.put("pageString",page.getAppPageString(Global.pageSize));
            infoMap.put("isoanotify","0");
            OaNotify oatemp=oaNotifyService.get(oanotifyId);
            if(loginUser.getId().equals(oatemp.getCreateBy().getId())){
                infoMap.put("isoanotify","1");
            }
			List<OaNotifyRecord> oaNotifyRecordList=page.getList();
			Map tempMap=null;
			if(FormatUtil.isNoEmpty(oaNotifyRecordList)&&oaNotifyRecordList.size()>0){
				for (OaNotifyRecord oaRecord:oaNotifyRecordList) {
					tempMap=new HashMap();
					tempMap.put("id",oaRecord.getId());
					tempMap.put("userName", "");
					if(FormatUtil.isNoEmpty(oaRecord.getUserName())){
						tempMap.put("userName", oaRecord.getUserName());
					}
					tempMap.put("officeName", "");
					if(FormatUtil.isNoEmpty(oaRecord.getOfficeName())){
						tempMap.put("officeName", "【"+oaRecord.getOfficeName()+"】");
					}
					tempMap.put("oacomment","");
					if(FormatUtil.isNoEmpty(oaRecord.getOacomment())){
						tempMap.put("oacomment",FormatUtil.delHTMLTag(StringEscapeUtils.unescapeHtml(oaRecord.getOacomment())));
					}
					tempMap.put("commentdate",FormatUtil.dateToString(oaRecord.getCommentDate(),"yyyy-MM-dd HH:mm:ss"));
					tempMap.put("readFlag","");
					if(FormatUtil.isNoEmpty(oaRecord.getReadFlag())){
						//String readFlag=DictUtils.getDictLabel(oaRecord.getReadFlag(),"oa_notify_read", "");
						tempMap.put("readFlag",oaRecord.getReadFlag());
					}
					tempMap.put("oacommentbyapp",oaRecord.getOacommentbyapp());
					dataList.add(tempMap);
				}
			}
		}
		infoMap.put("data", dataList);

		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		System.out.println(json.toString());
		return ;
	}

	@RequestMapping(value = "saveOanotifyComment")
	public void saveOanotifyComment(HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
		String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
		final User loginUser = UserUtils.get(userId);
		Map<String,Object> infoMapData = new HashMap<String,Object>();

		try {
			final String recordid = (String) request.getParameter("recordid");
			final String message = (String) request.getParameter("message");
			final OaNotifyRecord oaNotifyRecordTemp = oaNotifyRecordDao.get(recordid);
			oaNotifyRecordTemp.setId(recordid);
			if(FormatUtil.isNoEmpty(oaNotifyRecordTemp.getOacomment())){
				String msg = "<font style='color:#969696; line-height:24px;'>";
				msg+=oaNotifyRecordTemp.getOacomment()+"("+FormatUtil.dateToString(oaNotifyRecordTemp.getCommentDate(),"yyyy-MM-dd HH:mm:ss")+")<br/>";
				msg+="</font>";
				msg+=message;
				oaNotifyRecordTemp.setOacomment(msg);
				//app相关
				List<OaRecord> oacommentList=new ArrayList<OaRecord>();
				if(FormatUtil.isNoEmpty(oaNotifyRecordTemp.getOacommentbyapp())){
					JSONObject jsonObject=JSONObject.fromObject(oaNotifyRecordTemp.getOacommentbyapp());
					JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("oacommentbyapp"));
					oacommentList = JSONArray.toList(jsonArray, OaRecord.class);
				}
				OaRecord ord=new OaRecord();
				ord.setCreatedate(FormatUtil.dateToString(oaNotifyRecordTemp.getCommentDate(),"yyyy-MM-dd HH:mm:ss"));
				ord.setContent(message);
				oacommentList.add(ord);
				String oacommentbyapp= null;
				Map map=new HashMap();
				try {
					map.put("oacommentbyapp",oacommentList);
					JSONObject jsonObject=JSONObject.fromObject(map);
					oacommentbyapp = jsonObject.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				oaNotifyRecordTemp.setOacommentbyapp(oacommentbyapp);
			}else{
				oaNotifyRecordTemp.setOacomment(message);
				oaNotifyRecordTemp.setCommentDate(new Date());
				//app相关
				List<OaRecord> oacommentList=new ArrayList<OaRecord>();
				if(FormatUtil.isNoEmpty(oaNotifyRecordTemp.getOacommentbyapp())){
					JSONObject jsonObject=JSONObject.fromObject(oaNotifyRecordTemp.getOacommentbyapp());
					JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("oacommentbyapp"));
					oacommentList = JSONArray.toList(jsonArray, OaRecord.class);
				}
				OaRecord ord=new OaRecord();
				ord.setCreatedate(FormatUtil.dateToString(oaNotifyRecordTemp.getCommentDate(),"yyyy-MM-dd HH:mm:ss"));
				ord.setContent(message);
				oacommentList.add(ord);
				String oacommentbyapp= null;
				Map map=new HashMap();
				try {
					map.put("oacommentbyapp",oacommentList);
					JSONObject jsonObject=JSONObject.fromObject(map);
					oacommentbyapp = jsonObject.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				oaNotifyRecordTemp.setOacommentbyapp(oacommentbyapp);
			}
			oaNotifyService.updateComment(oaNotifyRecordTemp);

			//推送给前端页面 Start
			final DwrUtil dwrUtil = new DwrUtil();
			final String ctx = request.getContextPath();
			final OaNotify theoaNotify = oaNotifyService.get(oaNotifyRecordTemp.getOaNotify().getId());
			User sendUser = UserUtils.getWithJpushId(theoaNotify.getCreateBy().getId());
			if(FormatUtil.isNoEmpty(sendUser.getRegisterid())){
				System.out.println("JPUSH推送:"+sendUser.getName()+"**************************JPUSH推送ID:"+sendUser.getRegisterid());
				Map extraMap = new HashMap();
				extraMap.put("id",theoaNotify.getId());
				extraMap.put("notifytype","0");
				JPushUtil.SendMsg(loginUser.getName()+"确认了你的传阅,请注意查看！",sendUser.getRegisterid(),theoaNotify.getTitle(),extraMap);
			}
			final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(sendUser.getLoginName());
			if(toUserConn != null && "1".equals(theoaNotify.getRecordremind())){
				Thread t = new Thread(new Runnable(){
					public void run(){
						try {
							//确认时提醒
							Map map = new HashMap();
							map.put("cnt1", "-1");
							map.put("cnt2", "-1");
							map.put("cnt3", "-1");
							List<Map> lst = new ArrayList<Map>();
							Map maptemp = new HashMap();
							maptemp.put("url", ctx+adminPath+"/oa/oaNotify/form?id="+theoaNotify.getId());
							maptemp.put("title", loginUser.getName()+"确认了你的传阅,请注意查看！");
							lst.add(maptemp);
							map.put("msg", JSONArray.fromObject(lst).toString());
							String msg = JSONObject.fromObject(map).toString();
							String message = Constant._remind_window_+msg;
							ChatServerPool.sendMessageToUser(toUserConn,message);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}});
				t.start();
			}
			//推送给前端页面 end

			infoMapData.put("d", "y");
			infoMapData.put("msg", "确认成功！");
			JSONObject jsonObject=JSONObject.fromObject(oaNotifyRecordTemp.getOacommentbyapp());
			infoMapData.put("record",jsonObject.toString());
			infoMapData.put("commentdate",FormatUtil.dateToString(oaNotifyRecordTemp.getCommentDate(),"yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("确认失败：", e);
			infoMapData.put("d", "n");
			infoMapData.put("msg", "确认失败");
		}
		infoMap.put("data", infoMapData);
		JSONObject json = JSONObject.fromObject(infoMap);
		out.print(json.toString());
		System.out.println(json.toString());
	}

    @RequestMapping(value = "delOaNotifyRecord")
    public void delOaNotifyRecord(HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out =  response.getWriter();
        //返回的信息Map
        Map<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
        Map<String,Object> infoMapData = new HashMap<String,Object>();
        String oaNotifyRecordId = request.getParameter("oaNotifyRecordId");
        try {
            oaNotifyRecordDao.delete(new OaNotifyRecord(oaNotifyRecordId));
            infoMapData.put("d", "y");
            infoMapData.put("msg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            infoMapData.put("d", "n");
            infoMapData.put("msg", "删除失败");
        }
        out.write(JSONObject.fromObject(infoMapData).toString());
        out.close();
    }

	@RequestMapping(value = "delOaNotify")
	public void delOaNotify(HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));
		Map<String,Object> infoMapData = new HashMap<String,Object>();
		String id = request.getParameter("id");
		try {
			oaNotifyService.delete(new OaNotify(id));
			infoMapData.put("d", "y");
			infoMapData.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			infoMapData.put("d", "n");
			infoMapData.put("msg", "删除失败");
		}
		out.write(JSONObject.fromObject(infoMapData).toString());
		out.close();
	}

	@RequestMapping(value = "addOaNotifyRecord")
	public void addOaNotifyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		//返回的信息Map
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));

		String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
		User loginUser = UserUtils.get(userId);

		Map<String,Object> infoMapData = new HashMap<String,Object>();

		List<OaNotifyRecord> addoaNotifyRecordList = new ArrayList<OaNotifyRecord>();
		try {
			List<String> listids = new ArrayList<String>();
			Boolean flag = true;
			String hasnames = "";
			String oanotifyid = (String) request.getParameter("oanotifyid");
			String ids = (String) request.getParameter("ids");
			OaNotify oaNotify = oaNotifyService.get(oanotifyid);
			oaNotify = oaNotifyService.getRecordList(oaNotify);
			if(FormatUtil.isNoEmpty(ids)){
				String[] userids = ids.split(",");
				for (String id : userids) {
					flag = true;
					for (OaNotifyRecord oaNotifyRecord : oaNotify.getOaNotifyRecordList()) {
						if(id.equals(oaNotifyRecord.getUser().getId())){
							hasnames+=oaNotifyRecord.getUser().getName()+",";
							flag=false;
						}
					}
					if(flag){
						listids.add(id);
					}
				}

				String loginname = loginUser.getName();
				for (String uid : listids){
					OaNotifyRecord entity = new OaNotifyRecord();
					entity.setId(IdGen.uuid());
					entity.setOaNotify(oaNotify);
					entity.setUser(new User(uid));
					entity.setReadFlag("0");
					if(!loginUser.getId().equals(oaNotify.getCreateBy().getId())){
						entity.setAddname(loginname);
						entity.setAddoffice(loginUser.getOffice().getName());
					}
					User usr=UserUtils.get(uid);
					entity.setUserName(usr.getName());
					entity.setOfficeName(usr.getOfficeName());
					entity.setStationName(usr.getStationName());
					entity.setCompanyName(usr.getCompanyName());
					addoaNotifyRecordList.add(entity);
				}
				if(addoaNotifyRecordList.size() > 0){
					oaNotifyRecordDao.insertAll(addoaNotifyRecordList);
				}
//        		}
			}

			//推送给前端页面 Start
//			final DwrUtil dwrUtil = new DwrUtil();
			final String oaNotifyTitle = oaNotify.getTitle();
			final String oaNotifyId = oaNotify.getId();
			final String ctx = request.getContextPath();
			for (final OaNotifyRecord record : addoaNotifyRecordList) {
				User sendUser = UserUtils.get(record.getUser().getId());
				if(FormatUtil.isNoEmpty(sendUser.getRegisterid())){
					System.out.println("JPUSH推送:"+sendUser.getName()+"**************************JPUSH推送ID:"+sendUser.getRegisterid());
					Map extraMap = new HashMap();
					extraMap.put("id",oaNotifyId);
					extraMap.put("notifytype","0");
					JPushUtil.SendMsg("你收到了一封来自"+loginUser.getName()+"的新的传阅,请注意查收！",sendUser.getRegisterid(),oaNotifyTitle,extraMap);
				}
				final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(sendUser.getLoginName());
				if(toUserConn != null){
					Thread t = new Thread(new Runnable(){
						public void run(){
							try {
								User user = UserUtils.get(record.getUser().getId());
								Map map = new HashMap();
								OaNotify oaNotify2 = new OaNotify();
								oaNotify2.setSelf(true);
								oaNotify2.setReadFlag("0");
								oaNotify2.setCurrentUser(user);
								List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
								map.put("cnt1", list1.size());
								map.put("cnt2", "-1");
								map.put("cnt3", "-1");

								List<Map> lst = new ArrayList<Map>();
								Map maptemp = new HashMap();
								maptemp.put("url", ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId);
								maptemp.put("title", "你收到了新的传阅【"+oaNotifyTitle+"】,请注意查收！");
								lst.add(maptemp);
								map.put("msg", JSONArray.fromObject(lst).toString());

								String msg = JSONObject.fromObject(map).toString();
								String message = Constant._remind_window_+msg;
								ChatServerPool.sendMessageToUser(toUserConn,message);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}});
					t.start();
				}
			}
			//推送给前端页面 end
			final String mobileRemind = FormatUtil.toString(oaNotify.getMobileremind());
			if("1".equals(mobileRemind)){
				OaNotify tempOaNotify = new OaNotify();
				tempOaNotify.setOaNotifyRecordList(addoaNotifyRecordList);
				String userids = tempOaNotify.getOaNotifyRecordIds();
				List<String> mobileList = userDao.findMobileByIds(userids.split(","));
				final String mobiles = StringUtils.join(mobileList.toArray(),",");
				if(FormatUtil.isNoEmpty(mobiles)){
					Thread tMobile = new Thread(new Runnable(){
						public void run(){
							try {
//                                    SmsUtil.groupsendSms(mobiles, Global.OANOTIFY_SMS_MSG);
								SmsUtil.sendSmsByApi(mobiles,Global.OANOTIFY_SMS_MSG);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					tMobile.start();
				}

			}

			infoMap.put("d", "y");
			infoMap.put("msg", "添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("添加失败：", e);
			infoMap.put("d", "n");
			infoMap.put("msg", "添加失败");
		}

		out.write(JSONObject.fromObject(infoMap).toString());
		out.close();
	}

    @RequestMapping(value = "addOaNotify")
    public void addOaNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("utf-8");
        PrintWriter out =  response.getWriter();
        //返回的信息Map
        Map<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("serverKey",TokenUtil.updateServerkey(request.getParameter("serverKey")));

        String userId=TokenUtil.getUserid(request.getParameter("serverKey"));
        User loginUser = UserUtils.get(userId);

        Map<String,Object> infoMapData = new HashMap<String,Object>();

        String oanotifyid = (String) request.getParameter("oanotifyid");
        if(!FormatUtil.isNoEmpty(request.getParameter("title"))){
            infoMap.put("d", "n");
            infoMap.put("msg", "请输入标题");
            out.write(JSONObject.fromObject(infoMap).toString());
            out.close();
            return;
        }
        String title = (String) request.getParameter("title");
        if(!FormatUtil.isNoEmpty(request.getParameter("oanotifytype"))){
            infoMap.put("d", "n");
            infoMap.put("msg", "请选择类型");
            out.write(JSONObject.fromObject(infoMap).toString());
            out.close();
            return;
        }
        String oanotifytype = (String) request.getParameter("oanotifytype");
        if(!FormatUtil.isNoEmpty(request.getParameter("oanotifystatus"))){
            infoMap.put("d", "n");
            infoMap.put("msg", "请选择状态");
            out.write(JSONObject.fromObject(infoMap).toString());
            out.close();
            return;
        }
        String oanotifystatus = (String) request.getParameter("oanotifystatus");
        String mobileremind = (String) request.getParameter("mobileremind");
        String recordremind = (String) request.getParameter("recordremind");
        String secretsend = (String) request.getParameter("secretsend");
        String isallow = (String) request.getParameter("isallow");
        String content=(String) request.getParameter("content");
        if(!FormatUtil.isNoEmpty(request.getParameter("oaNotifyRecordIds"))){
            infoMap.put("d", "n");
            infoMap.put("msg", "请选择人员");
            out.write(JSONObject.fromObject(infoMap).toString());
            out.close();
            return;
        }
        String oaNotifyRecordIds = (String) request.getParameter("oaNotifyRecordIds");
        String oaNotifyRecordNames = (String) request.getParameter("oaNotifyRecordNames");
        String oaNotifyFileUrls = (String) request.getParameter("oaNotifyFileUrls");

        String oanotifydraft=request.getParameter("oanotifydraft");
        //新增数据
        if(FormatUtil.isNoEmpty(oanotifydraft)){//修改
			OaNotify oaNotify=oaNotifyService.get(oanotifyid);
			oaNotify.setTitle(title);
			oaNotify.setStatus(oanotifystatus);
			oaNotify.setOaNotifyRecordIds(oaNotifyRecordIds);
			oaNotify.setOaNotifyRecordNames(oaNotifyRecordNames);
			oaNotify.setOaNotifyFileUrls(oaNotifyFileUrls);
			oaNotify.setType(oanotifytype);
			oaNotify.setContent(content);
			oaNotify.setCreateBy(loginUser);
			oaNotify.setUpdateBy(loginUser);
			oaNotify.setRecordremind(recordremind);
			oaNotify.setMobileremind(mobileremind);
			oaNotify.setSecretsend(secretsend);
			oaNotify.setIsallow(isallow);
			oaNotifyService.save(oaNotify);
			infoMap.put("d", "y");
			infoMap.put("msg", "添加成功");

			if("1".equals(oaNotify.getStatus())){
				final String oaNotifyTitle = oaNotify.getTitle();
				final String oaNotifyId = oaNotify.getId();
				final String ctx = request.getContextPath();

				for (final OaNotifyRecord record : oaNotify.getOaNotifyRecordList()) {
					final User user = UserUtils.getWithJpushId(record.getUser().getId());
					if(FormatUtil.isNoEmpty(user.getRegisterid())){
						System.out.println("JPUSH推送:"+user.getName()+"**************************JPUSH推送ID:"+user.getRegisterid());
						Map extraMap = new HashMap();
						extraMap.put("id",oaNotifyId);
						extraMap.put("notifytype","0");
						JPushUtil.SendMsg("你收到了一封来自"+loginUser.getName()+"的新的传阅,请注意查收！",user.getRegisterid(),oaNotifyTitle,extraMap);
					}
					final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(user.getLoginName());
					if(toUserConn != null){
						Thread t = new Thread(new Runnable(){;
							public void run(){

								try {
									Map map = new HashMap();
									OaNotify oaNotify2 = new OaNotify();
									oaNotify2.setSelf(true);
									oaNotify2.setReadFlag("0");
									oaNotify2.setCurrentUser(user);
									List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
									map.put("cnt1", list1.size());
									map.put("cnt2", "-1");
									map.put("cnt3", "-1");

									List<Map> lst = new ArrayList<Map>();
									Map maptemp = new HashMap();
									maptemp.put("url", ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId);
//					    			maptemp.put("title", "你收到了新的传阅【"+oaNotifyTitle+"】,请注意查收！");
									maptemp.put("title", "你收到了一封新的传阅,请注意查收！");
									lst.add(maptemp);
									map.put("msg", JSONArray.fromObject(lst).toString());
									String msg = JSONObject.fromObject(map).toString();

									String message = Constant._remind_window_+msg;
									ChatServerPool.sendMessageToUser(toUserConn,message);
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						});
						t.start();
					}
				}

				final String mobileRemind = FormatUtil.toString(oaNotify.getMobileremind());
				if("1".equals(mobileRemind)){
					String userids = oaNotify.getOaNotifyRecordIds();
					List<String> mobileList = userDao.findMobileByIds(userids.split(","));
					final String mobiles = StringUtils.join(mobileList.toArray(),",");
					if(FormatUtil.isNoEmpty(mobiles)){
						Thread tMobile = new Thread(new Runnable(){
							public void run(){
								try {
//                                    SmsUtil.groupsendSms(mobiles, Global.OANOTIFY_SMS_MSG);
									SmsUtil.sendSmsByApi(mobiles,Global.OANOTIFY_SMS_MSG);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						tMobile.start();
					}

				}
			}
        }else{//新增
            OaNotify oaNotify=new OaNotify();
            oaNotify.setTitle(title);
            oaNotify.setStatus(oanotifystatus);
            oaNotify.setOaNotifyRecordIds(oaNotifyRecordIds);
            oaNotify.setOaNotifyRecordNames(oaNotifyRecordNames);
            oaNotify.setOaNotifyFileUrls(oaNotifyFileUrls);
            oaNotify.setType(oanotifytype);
            oaNotify.setContent(content);
            oaNotify.setCreateBy(loginUser);
            oaNotify.setUpdateBy(loginUser);
            oaNotify.setRecordremind(recordremind);
            oaNotify.setMobileremind(mobileremind);
            oaNotify.setSecretsend(secretsend);
            oaNotify.setIsallow(isallow);
            oaNotifyService.save(oaNotify);
            infoMap.put("d", "y");
            infoMap.put("msg", "添加成功");

            if("1".equals(oaNotify.getStatus())){
                final String oaNotifyTitle = oaNotify.getTitle();
                final String oaNotifyId = oaNotify.getId();
                final String ctx = request.getContextPath();

                for (final OaNotifyRecord record : oaNotify.getOaNotifyRecordList()) {
                    final User user = UserUtils.getWithJpushId(record.getUser().getId());
                    if(FormatUtil.isNoEmpty(user.getRegisterid())){
                        System.out.println("JPUSH推送:"+user.getName()+"**************************JPUSH推送ID:"+user.getRegisterid());
						Map extraMap = new HashMap();
						extraMap.put("id",oaNotifyId);
						extraMap.put("notifytype","0");
                        JPushUtil.SendMsg("你收到了一封来自"+loginUser.getName()+"的新的传阅,请注意查收！",user.getRegisterid(),oaNotifyTitle,extraMap);
                    }
                    final WebSocket toUserConn = ChatServerPool.getWebSocketByUser(user.getLoginName());
                    if(toUserConn != null){
                        Thread t = new Thread(new Runnable(){;
                            public void run(){

                                try {
                                    Map map = new HashMap();
                                    OaNotify oaNotify2 = new OaNotify();
                                    oaNotify2.setSelf(true);
                                    oaNotify2.setReadFlag("0");
                                    oaNotify2.setCurrentUser(user);
                                    List<OaNotify> list1 = oaNotifyService.findList(oaNotify2);
                                    map.put("cnt1", list1.size());
                                    map.put("cnt2", "-1");
                                    map.put("cnt3", "-1");

                                    List<Map> lst = new ArrayList<Map>();
                                    Map maptemp = new HashMap();
                                    maptemp.put("url", ctx+adminPath+"/oa/oaNotify/view?id="+oaNotifyId);
//					    			maptemp.put("title", "你收到了新的传阅【"+oaNotifyTitle+"】,请注意查收！");
                                    maptemp.put("title", "你收到了一封新的传阅,请注意查收！");
                                    lst.add(maptemp);
                                    map.put("msg", JSONArray.fromObject(lst).toString());
                                    String msg = JSONObject.fromObject(map).toString();

                                    String message = Constant._remind_window_+msg;
                                    ChatServerPool.sendMessageToUser(toUserConn,message);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        t.start();
                    }
                }

                final String mobileRemind = FormatUtil.toString(oaNotify.getMobileremind());
                if("1".equals(mobileRemind)){
                    String userids = oaNotify.getOaNotifyRecordIds();
                    List<String> mobileList = userDao.findMobileByIds(userids.split(","));
                    final String mobiles = StringUtils.join(mobileList.toArray(),",");
                    if(FormatUtil.isNoEmpty(mobiles)){
                        Thread tMobile = new Thread(new Runnable(){
                            public void run(){
                                try {
//                                    SmsUtil.groupsendSms(mobiles, Global.OANOTIFY_SMS_MSG);
									SmsUtil.sendSmsByApi(mobiles,Global.OANOTIFY_SMS_MSG);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        });
                        tMobile.start();
                    }

                }
            }
        }
        out.write(JSONObject.fromObject(infoMap).toString());
        out.close();
    }
}
