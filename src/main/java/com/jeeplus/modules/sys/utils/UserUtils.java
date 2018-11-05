/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.utils;

import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.sms.SMSUtils;
import com.jeeplus.common.utils.BaseConst;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.ehr.dao.UserInfoDao;
import com.jeeplus.modules.ehr.entity.UserInfo;
import com.jeeplus.modules.institution.dao.InstitutionDao;
import com.jeeplus.modules.institution.entity.Institution;
import com.jeeplus.modules.sys.dao.*;
import com.jeeplus.modules.sys.entity.*;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm.Principal;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.*;

/**
 * 用户工具类
 * @author jeeplus
 * @version 2013-12-05
 */
public class UserUtils {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	private static InstitutionDao institutionDao = SpringContextHolder.getBean(InstitutionDao.class);
	private static UserInfoDao userInfoDao = SpringContextHolder.getBean(UserInfoDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_ALL_CACHE = "userAllCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";
	public static final String USER_ALL_CACHE_LIST_BY_OFFICE_ID_ = "all_id_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_ROLE_MYLIST = "myroleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_MENU_ALL_LIST = "menuAllList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
	public static final String CACHE_INSTITUTION_LIST = "institutionList";
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user ==  null){
			user = userDao.get(id);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}

	public static User getWithJpushId(String id){

		User user = userDao.getWithJpushId(id);
		if (user == null){
			return null;
		}
//		user.setRoleList(roleDao.findList(new Role(user)));
		return user;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null){
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}

	public static User getByLoginNameOL(String loginName){
		User user = userDao.getByLoginName(new User(null, loginName));
		if (user == null){
			return null;
		}
		user.setRoleList(roleDao.findList(new Role(user)));
		CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
		CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		return user;
	}

	public static User getByLoginNameNoCache(String loginName){User user = userDao.getByLoginName(new User(null, loginName));
		if (user == null){
			return null;
		}
		user.setRoleList(roleDao.findList(new Role(user)));
		CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
		CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		return user;
	}
	
	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_AREA_LIST);
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(User user){
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
		if (user.getOffice() != null && user.getOffice().getId() != null){
			CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
		}
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			User user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static Boolean isNewEmployee(){
		Boolean isNew = false;
		User loginUser = getUser();
		if(!loginUser.isAdmin()){
			List<Role> roles = UserUtils.getMyRoleList();
			for (Role role : roles) {
				if(BaseConst.NEW_EMPLOYEE_ROLE_ID.equals(role.getId())){
					UserInfo uinfo = userInfoDao.getByCreateBy(new UserInfo(loginUser));
					if(FormatUtil.isNoEmpty(uinfo)){
						if(!FormatUtil.isNoEmpty(uinfo.getStatus()) || uinfo.getStatus() < 1){
							isNew = true;
						}
			 		}else{
						isNew = true;
					}
					break;
				}
			}
		}
		return isNew;
	}

	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<Role> getRoleList(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
		if (roleList == null){
			User user = getUser();
			if (user.isAdmin()){
				roleList = roleDao.findAllList(new Role());
			}else{
				Role role = new Role();
				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
				roleList = roleDao.findList(role);
			}
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}

	public static List<Role> getMyRoleList(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_MYLIST);
		if (roleList == null){
			User user = getUser();
			if (user.isAdmin()){
				roleList = roleDao.findAllList(new Role());
			}else{
				Role role = new Role();
				role.setUser(user.getCurrentUser());
				roleList = roleDao.findList(role);
			}
			putCache(CACHE_ROLE_MYLIST, roleList);
		}
		return roleList;
	}
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Menu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST);
		if (menuList == null){
			User user = getUser();
			if (user.isAdmin()){
				Menu m = new Menu();
				m.setIsShow("1");
				menuList = menuDao.findAllList(m);
			}else if(isNewEmployee()){//判断是否是新员工
				//获取角色的菜单ids
				Map map=new HashMap<String, Object>();
				String roleids="";
				for (Role role : user.getRoleList()) {
					roleids+="'"+role.getId()+"'"+",";
				}
				if(FormatUtil.isNoEmpty(roleids)){
					roleids=roleids.substring(0,roleids.length()-1);
				}
				map.put("ids", "''");
				if(FormatUtil.isNoEmpty(roleids)){
					map.put("ids", roleids);
				}
				List<String> menuIdList=menuDao.findRoleMenuIds(map);
				String roleMenuIds="";
				if(FormatUtil.isNoEmpty(menuIdList)&&menuIdList.size()>0){
					roleMenuIds=StringUtils.join(menuIdList, ",");
				}

				//获取部门的菜单ids
				String officeIds=getOfficeIdsStr(user);
				map.put("ids", "''");
				if(FormatUtil.isNoEmpty(officeIds)){
					map.put("ids", officeIds);
				}
				//查找当前部门以及其父部门的菜单ID集合
				List<String> officeMenuIdList=menuDao.findOfficeMenuIdsParent(map);
				String officeMenuIds="";
				if(FormatUtil.isNoEmpty(officeMenuIdList)&&officeMenuIdList.size()>0){
					officeMenuIds=StringUtils.join(officeMenuIdList, ",");
				}
				String menus=getMenuIds(roleMenuIds,officeMenuIds);
				Map menuMap=new HashMap<String, Object>();
				menuMap.put("ids", "''");
				if(FormatUtil.isNoEmpty(menus)){
					menuMap.put("ids", menus);
				}
				menuList = menuDao.findByMenuIds(menuMap);
			}else{
				//获取角色的菜单ids
				Map map=new HashMap<String, Object>();
				String roleids="";
				for (Role role : user.getRoleList()) {
					roleids+="'"+role.getId()+"'"+",";
				}
				if(FormatUtil.isNoEmpty(roleids)){
					roleids=roleids.substring(0,roleids.length()-1);
				}
				map.put("ids", "''");
				if(FormatUtil.isNoEmpty(roleids)){
					map.put("ids", roleids);
				}
				List<String> roleMenuIdList=menuDao.findRoleMenuIds(map);
				String roleMenuIds="";
				if(FormatUtil.isNoEmpty(roleMenuIdList)&&roleMenuIdList.size()>0){
					roleMenuIds=StringUtils.join(roleMenuIdList, ",");
				}
				//获取部门的菜单ids
				String officeIds=getOfficeIdsStr(user);
				map.put("ids", "''");
				if(FormatUtil.isNoEmpty(officeIds)){
					map.put("ids", officeIds);
				}
				//查找当前部门以及其父部门的菜单ID集合
				List<String> officeMenuIdList=menuDao.findOfficeMenuIdsParent(map);
				String officeMenuIds="";
				if(FormatUtil.isNoEmpty(officeMenuIdList)&&officeMenuIdList.size()>0){
					officeMenuIds=StringUtils.join(officeMenuIdList, ",");
				}
				
				String menus=getMenuIds(roleMenuIds,officeMenuIds);
				Map menuMap=new HashMap<String, Object>();
				menuMap.put("ids", "''");
				if(FormatUtil.isNoEmpty(menus)){
					menuMap.put("ids", menus);
				}
				menuList = menuDao.findByMenuIds(menuMap);
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}

	public static List<Menu> getAllMenuList(){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_ALL_LIST);
		if (menuList == null){

			Menu m = new Menu();
			m.setIsShow("1");
			menuList = menuDao.findAllList(m);
			putCache(CACHE_MENU_ALL_LIST, menuList);
		}
		return menuList;
	}

	//获取本部门及其父部门的ID的字符串集合
	private static String getOfficeIdsStr(User user) {
		try {
			String ids="";
			if(FormatUtil.isNoEmpty(user.getOffice())){
                String parentIds=user.getOffice().getParentIds();
                if(FormatUtil.isNoEmpty(parentIds)){
                    parentIds=parentIds.substring(0,parentIds.length()-1);
                    parentIds="'"+parentIds.replace(",","','")+"'";
                    ids="'"+user.getOffice().getId()+"',"+parentIds;
                }
            }
			return ids;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取菜单id集合（角色与部门的集合）
	 * @param roleMenuIds
	 * @param officeMenuIds
	 * @return
	 */
	private static String getMenuIds(String roleMenuIds, String officeMenuIds) {
		List<String> menuStringLst=new ArrayList<String>();
		if(FormatUtil.isNoEmpty(roleMenuIds)){
			String[] roleMenuArray=roleMenuIds.split(",");
			for (String mid : roleMenuArray) {
				menuStringLst.add(mid);
			}
		}
		
		if(FormatUtil.isNoEmpty(officeMenuIds)){
			String[] officeMenuArray=officeMenuIds.split(",");
			for (String mid : officeMenuArray) {
				if(!menuStringLst.contains(mid)){
					menuStringLst.add(mid);
				}
			}
		}
		
		String menus="";
		if(menuStringLst.size()>0){
			for (String mid : menuStringLst) {
				menus+="'"+mid+"'"+",";
			}
			if(FormatUtil.isNoEmpty(menus)){
				menus=menus.substring(0,menus.length()-1);
			}
		}
		if(!FormatUtil.isNoEmpty(menus)){
			menus="''";
		}
		return menus;
	}

	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static Menu getTopMenu(){
		Menu topMenu =  null;
		if(getMenuList().size() > 0){
			topMenu =  getMenuList().get(0);
		}
		return topMenu;
	}
	/**
	 * 获取当前用户授权的区域
	 * @return
	 */
	public static List<Area> getAreaList(){
		@SuppressWarnings("unchecked")
		List<Area> areaList = (List<Area>)getCache(CACHE_AREA_LIST);
		if (areaList == null){
			areaList = areaDao.findAllList(new Area());
			putCache(CACHE_AREA_LIST, areaList);
		}
		return areaList;
	}
	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_LIST);
		if (officeList == null){
			User user = getUser();
			if (user.isAdmin()){
				officeList = officeDao.findAllList(new Office("1"));
			}else{
				Office office = officeDao.get(user.getOffice().getId());
				if(FormatUtil.isNoEmpty(office)){
					office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
					officeList = officeDao.findList(office);
				}else{
					officeList = officeDao.findAllList(new Office("1"));
				}
			}
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeList(User user){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_LIST);
		if (officeList == null){
			if (user.isAdmin()){
				officeList = officeDao.findAllList(new Office("1"));
			}else{
				Office office = officeDao.get(user.getOffice().getId());
				if(FormatUtil.isNoEmpty(office)){
					office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
					officeList = officeDao.findList(office);
				}else{
					officeList = officeDao.findAllList(new Office("1"));
				}
			}
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeAllList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_ALL_LIST);
		if (officeList == null){
			officeList = officeDao.findAllList(new Office());
		}
		return officeList;
	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
//		getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}
	
	public static String getTime(Date date){
		StringBuffer time = new StringBuffer();
        Date date2 = new Date();
        long temp = date2.getTime() - date.getTime();    
        long days = temp / 1000 / 3600/24;                //相差小时数
        if(days>0){
        	time.append(days+"天");
        }
        long temp1 = temp % (1000 * 3600*24);
        long hours = temp1 / 1000 / 3600;                //相差小时数
        if(days>0 || hours>0){
        	time.append(hours+"小时");
        }
        long temp2 = temp1 % (1000 * 3600);
        long mins = temp2 / 1000 / 60;                    //相差分钟数
        time.append(mins + "分钟");
        return  time.toString();
	}


	//发送注册码
	public static String sendRandomCode(String uid, String pwd, String tel, String randomCode) throws IOException {
		//发送内容
		String content = "您的验证码是："+randomCode+"，有效期30分钟，请在有效期内使用。"; 
		
		return SMSUtils.send(uid, pwd, tel, content);

	}
	
	//注册用户重置密码
	public static String sendPass(String uid, String pwd, String tel, String password) throws IOException {
		//发送内容
		String content = "您的新密码是："+password+"，请登录系统，重新设置密码。"; 
		return SMSUtils.send(uid, pwd, tel, content);

	}
	
	public static Office getByOfficeId(String id){
		Office office = new Office();
		if(FormatUtil.isNoEmpty(id)){
			office = officeDao.get(id);
		}
		if(FormatUtil.isNoEmpty(office)){
			return office;
		}else{
			return new Office();
		}
	}
	
	/**
	 * 导出Excel调用,根据姓名转换为ID
	 */
	public static User getByUserName(String name){
		User u = new User();
		u.setName(name);
		List<User> list = userDao.findList(u);
		if(list.size()>0){
			return list.get(0);
		}else{
			return new User();
		}
	}
	/**
	 * 导出Excel使用，根据名字转换为id
	 */
	public static Office getByOfficeName(String name){
		Office o = new Office();
		o.setName(name);
		List<Office> list = officeDao.findList(o);
		if(list.size()>0){
			return list.get(0);
		}else{
			return new Office();
		}
	}
	
	/**
	 * 导出Excel使用，根据名字转换为id
	 */
	public static Office getOfficeByOfficeName(String name){
		Office o = new Office();
		o = officeDao.getByName(name);
		if(o !=null){
			return o;
		}else{
			return new Office();
		}
	}
	/**
	 * 导出Excel使用，根据名字转换为id
	 */
	public static Area getByAreaName(String name){
		Area a = new Area();
		a.setName(name);
		List<Area> list = areaDao.findList(a);
		if(list.size()>0){
			return list.get(0);
		}else{
			return new Area();
		}
	}
	
	/**
	 * 获取制度菜单
	 * @return
	 */
	public static List<Institution> getInstitutionList(){
		@SuppressWarnings("unchecked")
		List<Institution> institutionList = (List<Institution>)getCache(CACHE_INSTITUTION_LIST);
		if (institutionList == null){
			User user = getUser();
			if (!user.isAdmin()){
				Map mTemp = new HashMap();
//				List<String> rolelist = user.getRoleIdList();
//				String roleidssql = " and (";
//				for (int i = 0;i<rolelist.size();i++) {
//					if(i == 0){
//						roleidssql+="i.role_id = '"+rolelist.get(i)+"'";
//					}else{
//						roleidssql+=" or i.role_id = '"+rolelist.get(i)+"'";
//					}
//				}
//				roleidssql+=")";
//				mTemp.put("roleidssql", roleidssql);
				mTemp.put("DEL_FLAG_NORMAL", '0');
				mTemp.put("ilevel", '1');
				mTemp.put("officeid", user.getOffice().getId());
				institutionList = institutionDao.findListByLevel(mTemp);
				for (Institution in1 : institutionList) {
					mTemp.put("ilevel", '2');
					mTemp.put("parentid", in1.getId());
					List<Institution> list2 = institutionDao.findListByLevel(mTemp);
					for (Institution in2 : list2) {
						mTemp.put("ilevel", '3');
						mTemp.put("parentid", in2.getId());
						List<Institution> list3 = institutionDao.findListByLevel(mTemp);
						for (Institution in3 : list3) {
							mTemp.put("ilevel", '4');
							mTemp.put("parentid", in3.getId());
							List<Institution> list4 = institutionDao.findListByLevel(mTemp);
							for (Institution in4 : list4) {
								mTemp.put("ilevel", '5');
								mTemp.put("parentid", in4.getId());
								List<Institution> list5 = institutionDao.findListByLevel(mTemp);
								for (Institution in5 : list5) {
									mTemp.put("ilevel", '6');
									mTemp.put("parentid", in5.getId());
									List<Institution> list6 = institutionDao.findListByLevel(mTemp);
									for (Institution in6 : list6) {
										mTemp.put("ilevel", '7');
										mTemp.put("parentid", in6.getId());
										List<Institution> list7 = institutionDao.findListByLevel(mTemp);
										for (Institution in7 : list7) {
											mTemp.put("ilevel", '8');
											mTemp.put("parentid", in7.getId());
											List<Institution> list8 = institutionDao.findListByLevel(mTemp);
											for (Institution in8 : list8) {
												mTemp.put("ilevel", '9');
												mTemp.put("parentid", in8.getId());
												List<Institution> list9 = institutionDao.findListByLevel(mTemp);
												in8.setList(list9);
											}
											in7.setList(list8);
										}
										in6.setList(list7);
									}
									in5.setList(list6);
								}
								in4.setList(list5);
							}
							in3.setList(list4);
						}
						in2.setList(list3);
					}
					in1.setList(list2);
				}
			}else{
				Institution inTemp = new Institution();
				inTemp.setIlevel("1");
				inTemp.setParent(new Institution());
				institutionList = institutionDao.findAllListByLevel(inTemp);
				for (Institution in1 : institutionList) {
					
					inTemp = new Institution();
					inTemp.setIlevel("2");
					inTemp.setParent(new Institution(in1.getId()));
					List<Institution> list2 = institutionDao.findAllListByLevel(inTemp);
					
					for (Institution in2 : list2) {
						inTemp = new Institution();
						inTemp.setIlevel("3");
						inTemp.setParent(new Institution(in2.getId()));
						List<Institution> list3 = institutionDao.findAllListByLevel(inTemp);
						
						for (Institution in3 : list3) {
							inTemp = new Institution();
							inTemp.setIlevel("4");
							inTemp.setParent(new Institution(in3.getId()));
							List<Institution> list4 = institutionDao.findAllListByLevel(inTemp);
							
							for (Institution in4 : list4) {
								inTemp = new Institution();
								inTemp.setIlevel("5");
								inTemp.setParent(new Institution(in4.getId()));
								List<Institution> list5 = institutionDao.findAllListByLevel(inTemp);
								
								for (Institution in5 : list5) {
									inTemp = new Institution();
									inTemp.setIlevel("6");
									inTemp.setParent(new Institution(in5.getId()));
									List<Institution> list6 = institutionDao.findAllListByLevel(inTemp);
									
									for (Institution in6 : list6) {
										inTemp = new Institution();
										inTemp.setIlevel("7");
										inTemp.setParent(new Institution(in6.getId()));
										List<Institution> list7 = institutionDao.findAllListByLevel(inTemp);
										
										for (Institution in7 : list7) {
											inTemp = new Institution();
											inTemp.setIlevel("8");
											inTemp.setParent(new Institution(in7.getId()));
											List<Institution> list8 = institutionDao.findAllListByLevel(inTemp);
											
											for (Institution in8 : list8) {
												inTemp = new Institution();
												inTemp.setIlevel("9");
												inTemp.setParent(new Institution(in8.getId()));
												List<Institution> list9 = institutionDao.findAllListByLevel(inTemp);
												
												in8.setList(list9);
											}
											
											in7.setList(list8);
										}
										
										in6.setList(list7);
									}
									
									in5.setList(list6);
								}
								
								in4.setList(list5);
							}
							
							in3.setList(list4);
						}
						
						in2.setList(list3);
					}
					
					in1.setList(list2);
				}
			}
			putCache(CACHE_INSTITUTION_LIST, institutionList);
		}
		
//		}
		return institutionList;
	}
	
	/**
	 * 获取制度菜单
	 * @return
	 */
	public static Institution getTopInstitutionMenu(){
		Institution in = new Institution();
		in.setList(getInstitutionList());
		return in;
	}
	
}
