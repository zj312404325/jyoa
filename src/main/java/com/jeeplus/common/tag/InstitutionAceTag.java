package com.jeeplus.common.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.institution.entity.Institution;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.utils.UserUtils;


/**
 * 
 * 类描述：菜单标签
 * 
 * 刘高峰
 * @date： 日期：2015-1-23 时间：上午10:17:45
 * @version 1.0
 */
public class InstitutionAceTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected Institution institution;//菜单Map
	
	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			ServletContext context = SpringContextHolder
					.getBean(ServletContext.class);
			JspWriter out = this.pageContext.getOut();	
			String menu = (String) this.pageContext.getSession().getAttribute("menuace");
			if(menu!=null){
				out.print(menu);
			}else{
//				menu=end().toString();
				String iid="";
				StringBuffer menuString = new StringBuffer();
				for (Institution in1 : institution.getList()) {
					//menuString.append("<ul class=\"nav nav-list\"><li>");
					menuString.append("<li>");
					iid="";if("1".equals(in1.getItype())){iid = in1.getId();}
					menuString
					.append("<a class=\"dropdown-toggle\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
							+ "\"><i class=\"menu-icon fa fa-graduation-cap\"></i> <span class=\"menu-text\">"
							+ in1.getTitle()
							+ "</span><b class=\"arrow fa fa-angle-down\"></b>");
					if(in1.getList().size() > 0){menuString.append("<b class=\"arrow fa fa-angle-down\"></b>");}
					menuString.append("</a><b class=\"arrow\"></b>");
					if(in1.getList().size() > 0){menuString.append("<ul class=\"submenu\">");}
					for (Institution in2 : in1.getList()) {
						menuString.append("<li>");
						iid="";if("1".equals(in2.getItype())){iid = in2.getId();
						    menuString.append("<a class=\"dropdown-toggle J_menuItem2\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
								+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
								+ in2.getTitle()
								+ "</span>");
						}else{
							menuString.append("<a class=\"dropdown-toggle\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
									+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
									+ in2.getTitle()
									+ "</span>");
						}
						
						if(in2.getList().size() > 0){menuString.append("<b class=\"arrow fa fa-angle-down\"></b>");}
						menuString.append("</a><b class=\"arrow\"></b>");
						if(in2.getList().size() > 0){menuString.append("<ul class=\"submenu\">");}
						for (Institution in3 : in2.getList()) {
							menuString.append("<li>");
							iid="";if("1".equals(in3.getItype())){iid = in3.getId();
								menuString.append("<a class=\"dropdown-toggle J_menuItem2\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
									+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
									+ in3.getTitle()
									+ "</span>");
							}else{
								menuString.append("<a class=\"dropdown-toggle\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
										+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
										+ in3.getTitle()
										+ "</span>");
							}
							
							if(in3.getList().size() > 0){menuString.append("<b class=\"arrow fa fa-angle-down\"></b>");}
							menuString.append("</a><b class=\"arrow\"></b>");
							if(in3.getList().size() > 0){menuString.append("<ul class=\"submenu\">");}
							for (Institution in4 : in3.getList()) {
								menuString.append("<li>");
								iid="";if("1".equals(in4.getItype())){iid = in4.getId();
									menuString.append("<a class=\"dropdown-toggle J_menuItem2\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
										+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
										+ in4.getTitle()
										+ "</span>");
								}else{
									menuString.append("<a class=\"dropdown-toggle\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
											+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
											+ in4.getTitle()
											+ "</span>");
								}
								
								if(in4.getList().size() > 0){menuString.append("<b class=\"arrow fa fa-angle-down\"></b>");}
								menuString.append("</a><b class=\"arrow\"></b>");
								if(in4.getList().size() > 0){menuString.append("<ul class=\"submenu\">");}
								for (Institution in5 : in4.getList()) {
									menuString.append("<li>");
									iid="";if("1".equals(in5.getItype())){iid = in5.getId();
										menuString.append("<a class=\"dropdown-toggle J_menuItem2\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
											+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
											+ in5.getTitle()
											+ "</span>");
									}else{
										menuString.append("<a class=\"dropdown-toggle\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
												+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
												+ in5.getTitle()
												+ "</span>");
									}
									
									if(in5.getList().size() > 0){menuString.append("<b class=\"arrow fa fa-angle-down\"></b>");}
									menuString.append("</a><b class=\"arrow\"></b>");
									if(in5.getList().size() > 0){menuString.append("<ul class=\"submenu\">");}
									for (Institution in6 : in5.getList()) {
										menuString.append("<li>");
										iid="";if("1".equals(in6.getItype())){iid = in6.getId();
											menuString.append("<a class=\"dropdown-toggle J_menuItem2\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
												+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
												+ in6.getTitle()
												+ "</span>");
										}else{
											menuString.append("<a class=\"dropdown-toggle\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
													+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
													+ in6.getTitle()
													+ "</span>");
										}
										
										if(in6.getList().size() > 0){menuString.append("<b class=\"arrow fa fa-angle-down\"></b>");}
										menuString.append("</a><b class=\"arrow\"></b>");
										if(in6.getList().size() > 0){menuString.append("<ul class=\"submenu\">");}
										for (Institution in7 : in6.getList()) {
											menuString.append("<li>");
											iid="";if("1".equals(in7.getItype())){iid = in7.getId();
												menuString.append("<a class=\"dropdown-toggle J_menuItem2\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
													+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
													+ in7.getTitle()
													+ "</span>");
											}else{
												menuString.append("<a class=\"dropdown-toggle\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
														+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
														+ in7.getTitle()
														+ "</span>");
											}
											
											if(in7.getList().size() > 0){menuString.append("<b class=\"arrow fa fa-angle-down\"></b>");}
											menuString.append("</a><b class=\"arrow\"></b>");
											if(in7.getList().size() > 0){menuString.append("<ul class=\"submenu\">");}
											for (Institution in8 : in7.getList()) {
												menuString.append("<li>");
												iid="";if("1".equals(in8.getItype())){iid = in8.getId();
													menuString.append("<a class=\"dropdown-toggle J_menuItem2\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
														+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
														+ in8.getTitle()
														+ "</span>");
												}else{
													menuString.append("<a class=\"dropdown-toggle\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
															+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
															+ in8.getTitle()
															+ "</span>");
												}
												
												if(in8.getList().size() > 0){menuString.append("<b class=\"arrow fa fa-angle-down\"></b>");}
												menuString.append("</a><b class=\"arrow\"></b>");
												if(in8.getList().size() > 0){menuString.append("<ul class=\"submenu\">");}
												for (Institution in9 : in8.getList()) {
													menuString.append("<li>");
													iid="";if("1".equals(in9.getItype())){iid = in9.getId();}
													menuString.append("<a class=\"dropdown-toggle J_menuItem2\" vl=\""+iid+"\" href=\""+context.getContextPath()+Global.getAdminPath()+"/sys/institution/contentView?id="+iid
															+ "\"><i class=\"menu-icon fa null\"></i> <span class=\"menu-text\">"
															+ in9.getTitle()
															+ "</span></a><b class=\"arrow\"></b>");
													menuString.append("</a>");
													menuString.append("</li>");
												}
												if(in8.getList().size() > 0){menuString.append("</ul>");}
												menuString.append("</li>");
											}
											if(in7.getList().size() > 0){menuString.append("</ul>");}
											menuString.append("</li>");
										}
										if(in6.getList().size() > 0){menuString.append("</ul>");}
										menuString.append("</li>");
									}
									if(in5.getList().size() > 0){menuString.append("</ul>");}
									menuString.append("</li>");
								}
								if(in4.getList().size() > 0){menuString.append("</ul>");}
								menuString.append("</li>");
							}
							if(in3.getList().size() > 0){menuString.append("</ul>");}
							menuString.append("</li>");
						}
						if(in2.getList().size() > 0){menuString.append("</ul>");}
						menuString.append("</li>");
					}
					if(in1.getList().size() > 0){menuString.append("</ul>");}
					menuString.append("</li>");
				}
				menu = menuString.toString();
				out.print(menu);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

//	public StringBuffer end() {
//		StringBuffer sb = new StringBuffer();
//		sb.append(getChildOfTree(menu,0, UserUtils.getMenuList()));
//		
//		System.out.println(sb);
//		return sb;
//		
//	}
	
//	private static String getChildOfTree(Menu parent, int level, List<Menu> menuList) {
//		StringBuffer menuString = new StringBuffer();
//		String href = "";
//		if (!parent.hasPermisson())
//			return "";
//		if (level > 0) {//level 为0是功能菜单
//
//			menuString.append("<li>");
//
//			ServletContext context = SpringContextHolder
//					.getBean(ServletContext.class);
//			if (parent.getHref() != null && parent.getHref().length() > 0) {
//				
//				
//				if (parent.getHref().startsWith("http://")) {// 如果是互联网资源
//					href =  parent.getHref();
//				} else if(parent.getHref().endsWith(".html")&&!parent.getHref().endsWith("ckfinder.html")){//如果是静态资源并且不是ckfinder.html，直接访问不加adminPath
//					href = context.getContextPath() + parent.getHref();
//				}else{
//					href = context.getContextPath() + Global.getAdminPath()
//					+ parent.getHref();
//				}
//			}
//		}
//
//
//		if ((parent.getHref() == null || parent.getHref().trim().equals("")) && parent.getIsShow().equals("1")) {//如果是父节点且显示
//			if (level > 0) {
//			menuString
//					.append("<a href=\""
//							+ href
//							+ "\"><i class=\"fa "+parent.getIcon()+"\"></i> <span class=\"menu-text\">"
//							+ parent.getName()
//							+ "</span><span class=\"fa arrow\"></span></a>");
//			}
//			if (level == 1) {
//				menuString.append("<ul class=\"nav nav-second-level\">");
//			} else if (level == 2) {
//				menuString.append("<ul class=\"nav nav-third-level\">");
//			} else if (level == 3) {
//				menuString.append("<ul class=\"nav nav-forth-level\">");
//			} else if (level == 4) {
//				menuString.append("<ul class=\"nav nav-fifth-level\">");
//			}
//			for (Menu child : menuList) {
//				if (child.getParentId().equals(parent.getId())&&child.getIsShow().equals("1")) {
//					menuString.append(getChildOfTree(child, level + 1, menuList));
//				}
//			}
//			if (level > 0) {
//			menuString.append("</ul>");
//			}
//		} else {
//			menuString.append("<a class=\"J_menuItem\"  href=\"" + href
//					+ "\" ><i class=\"fa "+parent.getIcon()+"\"></i> <span class=\"menu-text\">"+parent.getName()+"</span></a>");
//		}
//		if (level > 0) {
//			menuString.append("</li>");
//		}
//
//		return menuString.toString();
//	}
//	
	

}
