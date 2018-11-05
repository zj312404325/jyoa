/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.dwr.DwrUtil;
import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.utils.CookieUtils;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.UserAgentUtils;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 手机端视图拦截器
 * @author jeeplus
 * @version 2014-9-1
 */
public class LoginInterceptor extends BaseService implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		/*HttpSession session = request.getSession(true);
		if(FormatUtil.isNoEmpty(session)){
			String jyoalogintime=(String) session.getAttribute("jyoalogintime");
			if(FormatUtil.isNoEmpty(jyoalogintime)){
				String uri = request.getServletPath();
				if(!uri.equals("/dwr")){
					Date dt=FormatUtil.StringToDate(jyoalogintime, "yyyy-MM-dd HH:mm:ss");
					Long millsecond=FormatUtil.dayTimeBetween(dt, new Date());
					if(millsecond>Global.getLoginoutTime()){
						//跳转到登录页面
						session.invalidate();
					}else{
						session.setAttribute("jyoalogintime",FormatUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					}
				}
			}
		}*/
		
		//判断用户是否是登录
		Principal principal=UserUtils.getPrincipal();
		if(FormatUtil.isNoEmpty(principal)){//用户已登录
			//验证此用户登录状态是否过期
			HttpSession session = request.getSession(true);
			if(FormatUtil.isNoEmpty(session)){
				String jyoalogintime=(String) session.getAttribute("jyoalogintime");
				if(FormatUtil.isNoEmpty(jyoalogintime)){
					Date dt=FormatUtil.StringToDate(jyoalogintime, "yyyy-MM-dd HH:mm:ss");
					Long millsecond=FormatUtil.dayTimeBetween(dt, new Date());
					if(millsecond>Global.getLoginoutTime()){
						//清除dwr中用户信息
						DwrUtil.scriptSessionMap.remove(UserUtils.getUser().getId());
						//退出登录状态
						UserUtils.getSubject().logout();
						//跳转到登录页面
						session.invalidate();
						//跳转到登录页面
						response.sendRedirect(request.getContextPath()+"/a/login");
						return false;
					}else{
						session.setAttribute("jyoalogintime",FormatUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					}
				}
			}
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
