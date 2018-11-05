/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.common.config;

import com.Util.PropertiesUtil;
import com.ckfinder.connector.ServletContextFactory;
import com.google.common.collect.Maps;
import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.common.utils.PropertiesLoader;
import com.jeeplus.common.utils.StringUtils;
import org.apache.ibatis.io.Resources;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 全局配置类
 * @author jeeplus
 * @version 2014-06-25
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("jeeplus.properties");

	//app 分页大小
	public static final int pageSize=10;
	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";

	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}

	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}

	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}

	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}

	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}

	/**
	 * 在修改系统用户和角色时是否同步到Activiti
	 */
	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}

	/**
	 * 页面获取常量
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getConfig("userfiles.basedir");
		if (StringUtils.isBlank(dir)){
			try {
				dir = ServletContextFactory.getServletContext().getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
//		System.out.println("userfiles.basedir: " + dir);
		return dir;
	}

	/**
	 * 获取网站根目录
	 * @return
	 */
	public static String getWebRoot() {
		String dir = "";
		try {
			dir = ServletContextFactory.getServletContext().getRealPath("/");
		} catch (Exception e) {
			return "";
		}
		if(dir.endsWith("\\")) {
			dir =dir.substring(0,dir.length()-1);
		}
		if(dir.endsWith("/")) {
			dir =dir.substring(0,dir.length()-1);
		}
		System.out.println("root: " + dir);
		return dir;
	}

    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }

    /**
	 * 写入properties信息
	 *
	 * @param key
	 *            名称
	 * @param value
	 *            值
	 */
	public static void modifyConfig(String key, String value) {
		try {
			// 从输入流中读取属性列表（键和元素对）
			Properties prop = getProperties();
			prop.setProperty(key, value);
			String path = Global.class.getResource("/jeeplus.properties").getPath();
			FileOutputStream outputFile = new FileOutputStream(path);
			prop.store(outputFile, "modify");
			outputFile.close();
			outputFile.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回　Properties
	 * @param fileName 文件名　(注意：加载的是src下的文件,如果在某个包下．请把包名加上)
	 * @param
	 * @return
	 */
	public static Properties getProperties(){
		Properties prop = new Properties();
		try {
			Reader reader = Resources.getResourceAsReader("/jeeplus.properties");
			prop.load(reader);
		} catch (Exception e) {
			return null;
		}
		return prop;
	}

	public static final  Map  columnMap = new HashMap(){{
		put("0", "文本控件");
		put("1", "密码控件");
		put("2", "时间控件(年月日时分秒)");
		put("3", "文本域控件");
		put("4", "上传控件");
		put("5", "下拉控件");
		put("6", "单选控件");
		put("7", "多选控件");
		put("8", "时间控件(年月日)");
		put("10", "纯文本控件");
	}};

	public static final Map<String,List<String>> sessionMap = new HashMap<String,List<String>>();

	/**
	 * 获取登录过期时间
	 */
	public static Long getLoginoutTime() {
		return FormatUtil.toLong(getConfig("loginouttime"));
	}

	public static Boolean getIsOnline() {
		return FormatUtil.toBoolean(getConfig("isOnline"));
	}

	/**
	 * 发送OA传阅的短信提醒
	 */
	public static final String OANOTIFY_SMS_MSG = "您的OA收到新的传阅，请注意查收。";
	/**
	 * 发送督办的短信提醒
	 */
	public static final String SUPERVISE_MSG = "您的OA收到新的待办任务，请注意查收。";

	/**
	 * 通用启用流程URL
	 */
	public static final String ACT_formKey = "/flow/flowapply/form?templateid=";

	/**
	 * 通用启用流程URL
	 */
	public static final String NO_HEAD = "/images/nohead.jpg";

	/**极光推送相关start**/
	// isOnline是否生产环境，测试环境不推送
	public static final String IS_ONLINE = PropertiesUtil.getPropertyValueFromProperties("isOnline");
	// appkey车队版
	public static final String appKey = PropertiesUtil.getPropertyValueFromProperties("appKey");
	// mastersecret
	public static final String masterSecret = PropertiesUtil.getPropertyValueFromProperties("masterSecret");
	/**极光推送相关end**/

	/**
	 * 线程启动sleep时间
	 */
	public static final long THREAD_SLEEP_TIME = 30000;

	public static final String WIN = "D:/Program Files (x86)/OpenOffice 4/";
	public static final String LINUX = "/opt/openoffice4/";

	public static final String WATERMARK="互联网+工业生态圈服务平台";

	//用户session关联--解决登录
	public static Map  userSessionMap = new HashMap(){};
	public static String JPSESSION="jeeplus.session.id";

	public static final String PDF_VIEW_PATH = "/webpage/modules/ehr/viewPDF/web/viewer.html?file=";

	//转正申请
	public static final String ZHUANZHENG_FLOW_ID = "5e2c5330f78841929e9575e39e179453";
	public static final String ZHUANZHENG_FLOW_PERSON_ID = "8097e18dd520453aa5f60306e2872982";

	//薪资申请
	public static final String SALARY_FLOW_ID = "3766ffa41cb94c0a8b5dd6386667eeca";
	public static final String SALARY_FLOW_PERSON_ID = "78af49181f904f95b455556cc66b27f4";

	//奖惩申报
	public static final String REWARD_FLOW_ID = "8fc331991c9544a4850271bfa07c5704";
	public static final String REWARD_FLOW_PERSON_ID = "1877ca30edc648e8a81b6018f4efbd39";

	//岗位变动申请
	public static final String POSTSHENQING_FLOW_ID = "8b1c315d4ac4464abc4388e659ad9eb3";

	//岗位变动交接单
	public static final String POSTJIAOJIE_FLOW_ID = "1d4da9946c1e443d8d9a738d350400ac";

	//岗位变动交接单
	public static final String LEAVEJIAOJIE_FLOW_ID = "f72904e8dc0848a5a3643ff94219c2ab";

	//待办任务菜单IDS
	//待办任务
	public static final String TODOTASKMENU="214e0e84a96f45b1b1f4f7d9a2a6be84";
	//EHR
	public static final String EHRTODOTASKTOTALMENU="97697d7a1ef6489baf9df09529bc83b7";
	//EHR管理
	public static final String EHRMTODOTASKTOTALMENU="8fc683623b114da89f13a384c2911ffb";
	//在职
	public static final String TODOTASKTOTALMENU="5beba247b2f34d898df08af55f60ba2b";
	//转正申请
	public static final String ZHUANZHENGTODOTASKMENU="94c25be6e97c406aadbcf58f62679c1e";
	//薪资调整申请
	public static final String SALARYTODOTASKMENU="f938a5a84b2143fbbe6a5d4005a35217";
	//奖惩申报
	public static final String REWARDTODOTASKMENU="d44ff22f894947ae84bb32472fa77a58";
	//岗位变动
	public static final String POSTTODOTASKMENU = "9756985e847e4b70be899a293021a7ce";
	//岗位变动申请
	public static final String POSTSHENQINGTODOTASKMENU = "f59fe8475ffa4c428507126d4dfb691a";
	//岗位变动交接单
	public static final String POSTJIAOJIETODOTASKMENU = "e01125e43b6d48cfa5e3315439a90b1c";
	//离职
	public static final String LEAVETODOTASKMENU = "d62af4e847b84aeb8eab181a8eeb89c9";
	//离职交接单
	public static final    String LEAVEJIAOJIETODOTASKMENU = "5f31a8677c3e4a12914c38d61775ada1";

	//信息搜索天数
	public static String SEARCH_DAYS_LIMIT = "0";
	//信息搜索开始日期
	public static String SEARCH_DATE_FROM = "2018-04-26";

}
