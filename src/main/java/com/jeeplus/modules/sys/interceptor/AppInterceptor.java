/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.interceptor;

import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.utils.DateUtil;
import com.jeeplus.common.utils.DesUtil;
import com.jeeplus.common.utils.FormatUtil;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 手机端视图拦截器
 * @author jeeplus
 * @version 2014-9-1
 */
public class AppInterceptor extends BaseService implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		System.out.println("app拦截器===================================================");
		System.out.println("url:"+request.getRequestURI()+"===================================================");
		System.out.println("serverKey:"+FormatUtil.toString(request.getParameter("serverKey"))+"===================================================");
		Map<String, Object> map = new HashMap<String,Object>();
		if(FormatUtil.isNoEmpty(request.getParameter("serverKey"))){
			String serverKey=request.getParameter("serverKey");
			String deserverkey=DesUtil.decrypt(serverKey);
			String[] keys=deserverkey.split("_");
			//Date d= DateUtil.StringToDate(keys[2], "yyyy-MM-dd HH:mm:ss");
            Date d=DateUtil.transferLongToDate(keys[2]);
			int i=FormatUtil.daysBetween(d,new Date());
			if(i>=1){
				map.put("serverKey", "");
				map.put("d", "n");
				map.put("islogin", "n");
				map.put("data", "");
				map.put("msg", "您登录状态已过期！");
				PrintWriter writer =response.getWriter();
				JSONObject json = JSONObject.fromObject(map);
				writer.print(json.toString());
				System.out.println(json.toString());
				writer.close();
				return false;
			}
		}
		else{
			map.put("serverKey", "");
			map.put("d", "n");
			map.put("islogin", "n");
			map.put("data", "");
			map.put("msg", "您当前未登陆！");
			PrintWriter writer =response.getWriter();
			JSONObject json = JSONObject.fromObject(map);
			writer.print(json.toString());
			System.out.println(json.toString());
			writer.close();
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		
	}

}
