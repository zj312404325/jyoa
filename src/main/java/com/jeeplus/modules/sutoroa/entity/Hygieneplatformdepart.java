/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 8s检查表内容Entity
 * @author cqj
 * @version 2018-02-26
 */
public class Hygieneplatformdepart extends DataEntity<Hygieneplatformdepart> {
	
	private static final long serialVersionUID = 1L;
	private String hyqieneid;		// 主表id
	private String depart;		// 部门标识
	private String departname;		// 部门名称
	private String morningrecord;		// 晨会记录
	private String weekrecord;		// 每周例会会议记录
	private String whzrr;		// 相关物品应标识，有维护责任人
	private String wfbxp;		// 无非必需品
	private String keepclear;		// 各办公用品目视化放置（定位、定量），必须定期进行整理，并保持干净
	private String msb;		// 应有人员去向目视板
	private String msh;		// 考核表应及时更新，并目视化
	private String kq;		// 每周考勤是否有迟到早退旷工现象
	private String wjzl;		// 文件、资料整齐放置不得凌乱，超过一周的资料存档
	private String mrbx;		// 非每日必需品不得存放在办公台上
	private String srwp;		// 私人物品应分开、整得摆放一处
	private String qjp;		// 除清洁用具外不得放置任何物品
	private String dmgj;		// 地面保持干净，无垃圾、无污迹及纸屑等
	private String tzzx;		// 人离开办公桌后，办公椅应推至桌下，且应紧挨办公桌平行放置
	private String yb;		// 椅背上不允许摆放衣服和其它物品
	private String wjggj;		// 应保持柜面干净、无灰尘
	private String wjgbs;		// 柜外应有标识，且标识应统一
	private String wjgbf;		// 柜内文件（或物品）摆放整齐，并分类摆放
	private String wjjbs;		// 文件夹上要标识，同一部门的文件夹外侧的标识应统一
	private String wjjml;		// 文件夹内必须有文件目录
	private String mcgj;		// 保持门、窗干净、无灰尘、无蛛网
	private String ctgj;		// 窗台上无杂物
	private String dngj;		// 应保持干净，无灰尘、无污迹
	private String dnx;		// 电脑线应束起来，避免凌乱
	private String dnzl;		// 电脑内不得保存与工作和岗位学习无关的软件、资料
	private String hhxx;		// 花卉应新鲜，花盆内不得有烟蒂、茶渣等杂物
	private String hys;		// 会议室、茶水间等公共区域的整洁
	private String gztd;		// 工作态度是否良好（有无谈天、离岗、怠工、看小说、呆坐、打磕睡、吃零食、玩游戏、听音乐、上娱乐或聊天网站现象）
	private String yzzj;		// 是否保持衣着整洁、仪表端庄（领带、胸针、着装、发饰等，参考行为规范）
	private String ttwy;		// 谈吐文雅、举止规范有礼貌。
	private String gzsj;		// 工作时间不做与工作无关的事。
	private String lts;		// 不在工作场所争吵、打闹、抽烟、聊天
	private String xblk;		// 下班时将工作现场整理后离开
	private String wrgm;		// 办公室无人时应关好门
	private String xxsd;		// 信息系统，人走后（或无人时）应关闭或自动锁定
	private String dwsb;		// 对外申报系统是否下载存档申报资料并打印归档
	private String gsyj;		// 是否正常使用公司邮箱公司电话公司传真
	private String xbgm;		// 下班关好门窗、空调电脑等电源
	private String wjgs;		// 文件柜有锁具及专人保管;商业资料不得随意乱放，超过一周应归档案室
	private String wrdy;		// 无人时须关闭电源
	private String ktsd;		// 空调温度设定不符节能要求
	private String wpsh;		// 物品坏了及时维修（或申报维修）
	private String jdpx;		// 有季度培训计划且按计划执行
	private String bmpx;		// 门组织培训每月2次以上
	private String score;		// 部门得分
	private Integer isdel;		// 是否删除
	private String sort;		// 总分
	
	public Hygieneplatformdepart() {
		super();
	}

	public Hygieneplatformdepart(String id){
		super(id);
	}

	@ExcelField(title="主表id", align=2, sort=7)
	public String getHyqieneid() {
		return hyqieneid;
	}

	public void setHyqieneid(String hyqieneid) {
		this.hyqieneid = hyqieneid;
	}
	
	@ExcelField(title="部门标识", align=2, sort=8)
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}
	
	@ExcelField(title="部门名称", align=2, sort=9)
	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}
	
	@ExcelField(title="晨会记录", align=2, sort=10)
	public String getMorningrecord() {
		return morningrecord;
	}

	public void setMorningrecord(String morningrecord) {
		this.morningrecord = morningrecord;
	}
	
	@ExcelField(title="每周例会会议记录", align=2, sort=11)
	public String getWeekrecord() {
		return weekrecord;
	}

	public void setWeekrecord(String weekrecord) {
		this.weekrecord = weekrecord;
	}
	
	@ExcelField(title="相关物品应标识，有维护责任人", align=2, sort=12)
	public String getWhzrr() {
		return whzrr;
	}

	public void setWhzrr(String whzrr) {
		this.whzrr = whzrr;
	}
	
	@ExcelField(title="无非必需品", align=2, sort=13)
	public String getWfbxp() {
		return wfbxp;
	}

	public void setWfbxp(String wfbxp) {
		this.wfbxp = wfbxp;
	}
	
	@ExcelField(title="各办公用品目视化放置（定位、定量），必须定期进行整理，并保持干净", align=2, sort=14)
	public String getKeepclear() {
		return keepclear;
	}

	public void setKeepclear(String keepclear) {
		this.keepclear = keepclear;
	}
	
	@ExcelField(title="应有人员去向目视板", align=2, sort=15)
	public String getMsb() {
		return msb;
	}

	public void setMsb(String msb) {
		this.msb = msb;
	}
	
	@ExcelField(title="考核表应及时更新，并目视化", align=2, sort=16)
	public String getMsh() {
		return msh;
	}

	public void setMsh(String msh) {
		this.msh = msh;
	}
	
	@ExcelField(title="每周考勤是否有迟到早退旷工现象", align=2, sort=17)
	public String getKq() {
		return kq;
	}

	public void setKq(String kq) {
		this.kq = kq;
	}
	
	@ExcelField(title="文件、资料整齐放置不得凌乱，超过一周的资料存档", align=2, sort=18)
	public String getWjzl() {
		return wjzl;
	}

	public void setWjzl(String wjzl) {
		this.wjzl = wjzl;
	}
	
	@ExcelField(title="非每日必需品不得存放在办公台上", align=2, sort=19)
	public String getMrbx() {
		return mrbx;
	}

	public void setMrbx(String mrbx) {
		this.mrbx = mrbx;
	}
	
	@ExcelField(title="私人物品应分开、整得摆放一处", align=2, sort=20)
	public String getSrwp() {
		return srwp;
	}

	public void setSrwp(String srwp) {
		this.srwp = srwp;
	}
	
	@ExcelField(title="除清洁用具外不得放置任何物品", align=2, sort=21)
	public String getQjp() {
		return qjp;
	}

	public void setQjp(String qjp) {
		this.qjp = qjp;
	}
	
	@ExcelField(title="地面保持干净，无垃圾、无污迹及纸屑等", align=2, sort=22)
	public String getDmgj() {
		return dmgj;
	}

	public void setDmgj(String dmgj) {
		this.dmgj = dmgj;
	}
	
	@ExcelField(title="人离开办公桌后，办公椅应推至桌下，且应紧挨办公桌平行放置", align=2, sort=23)
	public String getTzzx() {
		return tzzx;
	}

	public void setTzzx(String tzzx) {
		this.tzzx = tzzx;
	}
	
	@ExcelField(title="椅背上不允许摆放衣服和其它物品", align=2, sort=24)
	public String getYb() {
		return yb;
	}

	public void setYb(String yb) {
		this.yb = yb;
	}
	
	@ExcelField(title="应保持柜面干净、无灰尘", align=2, sort=25)
	public String getWjggj() {
		return wjggj;
	}

	public void setWjggj(String wjggj) {
		this.wjggj = wjggj;
	}
	
	@ExcelField(title="柜外应有标识，且标识应统一", align=2, sort=26)
	public String getWjgbs() {
		return wjgbs;
	}

	public void setWjgbs(String wjgbs) {
		this.wjgbs = wjgbs;
	}
	
	@ExcelField(title="柜内文件（或物品）摆放整齐，并分类摆放", align=2, sort=27)
	public String getWjgbf() {
		return wjgbf;
	}

	public void setWjgbf(String wjgbf) {
		this.wjgbf = wjgbf;
	}
	
	@ExcelField(title="文件夹上要标识，同一部门的文件夹外侧的标识应统一", align=2, sort=28)
	public String getWjjbs() {
		return wjjbs;
	}

	public void setWjjbs(String wjjbs) {
		this.wjjbs = wjjbs;
	}
	
	@ExcelField(title="文件夹内必须有文件目录", align=2, sort=29)
	public String getWjjml() {
		return wjjml;
	}

	public void setWjjml(String wjjml) {
		this.wjjml = wjjml;
	}
	
	@ExcelField(title="保持门、窗干净、无灰尘、无蛛网", align=2, sort=30)
	public String getMcgj() {
		return mcgj;
	}

	public void setMcgj(String mcgj) {
		this.mcgj = mcgj;
	}
	
	@ExcelField(title="窗台上无杂物", align=2, sort=31)
	public String getCtgj() {
		return ctgj;
	}

	public void setCtgj(String ctgj) {
		this.ctgj = ctgj;
	}
	
	@ExcelField(title="应保持干净，无灰尘、无污迹", align=2, sort=32)
	public String getDngj() {
		return dngj;
	}

	public void setDngj(String dngj) {
		this.dngj = dngj;
	}
	
	@ExcelField(title="电脑线应束起来，避免凌乱", align=2, sort=33)
	public String getDnx() {
		return dnx;
	}

	public void setDnx(String dnx) {
		this.dnx = dnx;
	}
	
	@ExcelField(title="电脑内不得保存与工作和岗位学习无关的软件、资料", align=2, sort=34)
	public String getDnzl() {
		return dnzl;
	}

	public void setDnzl(String dnzl) {
		this.dnzl = dnzl;
	}
	
	@ExcelField(title="花卉应新鲜，花盆内不得有烟蒂、茶渣等杂物", align=2, sort=35)
	public String getHhxx() {
		return hhxx;
	}

	public void setHhxx(String hhxx) {
		this.hhxx = hhxx;
	}
	
	@ExcelField(title="会议室、茶水间等公共区域的整洁", align=2, sort=36)
	public String getHys() {
		return hys;
	}

	public void setHys(String hys) {
		this.hys = hys;
	}
	
	@ExcelField(title="工作态度是否良好（有无谈天、离岗、怠工、看小说、呆坐、打磕睡、吃零食、玩游戏、听音乐、上娱乐或聊天网站现象）", align=2, sort=37)
	public String getGztd() {
		return gztd;
	}

	public void setGztd(String gztd) {
		this.gztd = gztd;
	}
	
	@ExcelField(title="是否保持衣着整洁、仪表端庄（领带、胸针、着装、发饰等，参考行为规范）", align=2, sort=38)
	public String getYzzj() {
		return yzzj;
	}

	public void setYzzj(String yzzj) {
		this.yzzj = yzzj;
	}
	
	@ExcelField(title="谈吐文雅、举止规范有礼貌。", align=2, sort=39)
	public String getTtwy() {
		return ttwy;
	}

	public void setTtwy(String ttwy) {
		this.ttwy = ttwy;
	}
	
	@ExcelField(title="工作时间不做与工作无关的事。", align=2, sort=40)
	public String getGzsj() {
		return gzsj;
	}

	public void setGzsj(String gzsj) {
		this.gzsj = gzsj;
	}
	
	@ExcelField(title="不在工作场所争吵、打闹、抽烟、聊天", align=2, sort=41)
	public String getLts() {
		return lts;
	}

	public void setLts(String lts) {
		this.lts = lts;
	}
	
	@ExcelField(title="下班时将工作现场整理后离开", align=2, sort=42)
	public String getXblk() {
		return xblk;
	}

	public void setXblk(String xblk) {
		this.xblk = xblk;
	}
	
	@ExcelField(title="办公室无人时应关好门", align=2, sort=43)
	public String getWrgm() {
		return wrgm;
	}

	public void setWrgm(String wrgm) {
		this.wrgm = wrgm;
	}
	
	@ExcelField(title="信息系统，人走后（或无人时）应关闭或自动锁定", align=2, sort=44)
	public String getXxsd() {
		return xxsd;
	}

	public void setXxsd(String xxsd) {
		this.xxsd = xxsd;
	}
	
	@ExcelField(title="对外申报系统是否下载存档申报资料并打印归档", align=2, sort=45)
	public String getDwsb() {
		return dwsb;
	}

	public void setDwsb(String dwsb) {
		this.dwsb = dwsb;
	}
	
	@ExcelField(title="是否正常使用公司邮箱公司电话公司传真", align=2, sort=46)
	public String getGsyj() {
		return gsyj;
	}

	public void setGsyj(String gsyj) {
		this.gsyj = gsyj;
	}
	
	@ExcelField(title="下班关好门窗、空调电脑等电源", align=2, sort=47)
	public String getXbgm() {
		return xbgm;
	}

	public void setXbgm(String xbgm) {
		this.xbgm = xbgm;
	}
	
	@ExcelField(title="文件柜有锁具及专人保管;商业资料不得随意乱放，超过一周应归档案室", align=2, sort=48)
	public String getWjgs() {
		return wjgs;
	}

	public void setWjgs(String wjgs) {
		this.wjgs = wjgs;
	}
	
	@ExcelField(title="无人时须关闭电源", align=2, sort=49)
	public String getWrdy() {
		return wrdy;
	}

	public void setWrdy(String wrdy) {
		this.wrdy = wrdy;
	}
	
	@ExcelField(title="空调温度设定不符节能要求", align=2, sort=50)
	public String getKtsd() {
		return ktsd;
	}

	public void setKtsd(String ktsd) {
		this.ktsd = ktsd;
	}
	
	@ExcelField(title="物品坏了及时维修（或申报维修）", align=2, sort=51)
	public String getWpsh() {
		return wpsh;
	}

	public void setWpsh(String wpsh) {
		this.wpsh = wpsh;
	}
	
	@ExcelField(title="有季度培训计划且按计划执行", align=2, sort=52)
	public String getJdpx() {
		return jdpx;
	}

	public void setJdpx(String jdpx) {
		this.jdpx = jdpx;
	}
	
	@ExcelField(title="门组织培训每月2次以上", align=2, sort=53)
	public String getBmpx() {
		return bmpx;
	}

	public void setBmpx(String bmpx) {
		this.bmpx = bmpx;
	}
	
	@ExcelField(title="部门得分", align=2, sort=54)
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@ExcelField(title="是否删除", align=2, sort=55)
	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
	@ExcelField(title="总分", align=2, sort=56)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}