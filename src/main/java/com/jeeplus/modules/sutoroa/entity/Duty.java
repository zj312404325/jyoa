/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 值班表Entity
 * @author cqj
 * @version 2018-02-24
 */
public class Duty extends DataEntity<Duty> {
	
	private static final long serialVersionUID = 1L;
	private String company;		// 公司
	private Date startdate;		// 开始时间
	private Date enddate;		// 结束时间
	private String dutier;		// 值班人
	private String department;		// 部门
	private String post;		// 职务
	private String zhehuaproblem;		// 浙华办公楼存在问题
	private String zhehuaresult;		// 浙华办公楼处理结果
	private String beimenproblem;		// 北门保安室存在问题
	private String beimenresult;		// 北门保安室处理结果
	private String shengchanproblem;		// 生产办公室存在问题
	private String shengchanresult;		// 生产办公室处理结果
	private String zfroomproblem;		// 直缝车间存在问题
	private String zfroomresult;		// 直缝车间处理结果
	private String lxroomresult;		// 螺旋车间处理结果
	private String lxroomproblem;		// 螺旋车间存在问题
	private String ffroomproblem;		// 防腐车间存在问题
	private String ffroomresult;		// 防腐车间处理结果
	private String xhstoreproblem;		// 现货仓库存在问题
	private String xhstoreresult;		// 现货仓库处理结果
	private String dgroomproblem;		// 机修电工房存在问题
	private String dgroomresult;		// 机修电工房处理结果
	private String sfflstoreproblem;		// 收发辅料仓库存在问题
	private String sfflstoreresult;		// 收发辅料仓库处理结果
	private String bobanofficeproblem;		// 薄板办公楼存在问题
	private String bobanofficeresult;		// 薄板办公楼处理结果
	private String monitorroomproblem;		// 监控室存在问题
	private String monitorroomresult;		// 监控室处理结果
	private String kldofficeproblem;		// 克罗德办公楼存在问题
	private String kldofficeresult;		// 克罗德办公楼处理结果
	private String aduxinproblem;		// 薄板镀锌线存在问题
	private String aduxinresult;		// 薄板镀锌线处理结果
	private String atucaiproblem;		// 薄板彩涂线存在问题
	private String atucairesult;		// 薄板彩涂线处理结果
	private String bsuanxiproblem;		// 酸洗产线存在问题
	private String bsuanxiresult;		// 酸洗产线处理结果
	private String bzhaji1problem;		// 1#轧机存在问题
	private String bzhaji1result;		// 1#轧机处理结果
	private String bzhaji2problem;		// 2#轧机存在问题
	private String bzhaji2result;		// 2#轧机处理结果
	private String cduxin15problem;		// 15WT镀锌存在问题
	private String cduxin15result;		// 15WT镀锌处理结果
	private String cduxin25problem;		// 25WT镀锌存在问题
	private String cduxin25result;		// 25WT镀锌处理结果
	private String cdulvxinproblem;		// 镀铝锌存在问题
	private String cdulvxinresult;		// 镀铝锌处理结果
	private String astoreproblem;		// 薄板大仓库存在问题
	private String astoreresult;		// 薄板大仓库处理结果
	private String cstoreproblem;		// 克罗德大仓库存在问题
	private String cstoreresult;		// 克罗德大仓库处理结果
	private String aflstoreproblem;		// 薄板辅料仓库存在问题
	private String aflstoreresult;		// 薄板辅料仓库处理结果
	private String bflstoreproblem;		// 克罗德辅料仓库存在问题
	private String bflstoreresult;		// 克罗德辅料仓库处理结果
	private String cflstoreproblem;		// cflstoreproblem
	private String cflstoreresult;		// cflstoreresult
	private String officeproblem;		// 综合办公楼存在问题
	private String officeresult;		// 综合办公楼处理结果
	private String machineroomproblem;		// 机房存在问题
	private String machineroomresult;		// 机房处理结果
	private String flstoreproblem;		// 辅料仓库存在问题
	private String flstoreresult;		// 辅料仓库处理结果
	private String factoryworkproblem;		// 厂区及车间存在问题
	private String factoryworkresult;		// 厂区及车间处理结果
	private String powerroomproblem;		// 配电间存在问题
	private String powerroomresult;		// 配电间处理结果
	private String bgstoreproblem;		// 余料仓库存在问题
	private String bgstoreresult;		// 余料仓库处理结果
	private String ylstoreproblem;		// ylstoreproblem
	private String ylstoreresult;		// ylstoreresult
	private String dormitoryproblem;		// 宿舍存在问题
	private String dormitoryresult;		// 宿舍处理结果
	private String eatroomproblem;		// 食堂存在问题
	private String eatroomresult;		// 食堂处理结果
	private String guardproblem;		// 门卫存在问题
	private String guardresult;		// 门卫处理结果
	private String summary;		// 补充总结
	private String signature;		// 处理人确认
	private String tdbsproblem;		// 涂镀事业中心存在问题
	private String tdbsresult;		// 涂镀事业中心处理结果
	private String ggbsproblem;		// 钢管事业中心存在问题
	private String ggbsresult;		// 钢管事业中心处理结果
	private String jgbsproblem;		// 加工事业中心存在问题
	private String jgbsresult;		// 加工事业中心处理结果
	private Date addtime;		// 添加时间
	private Date uptime;		// 更新时间
	private String ip;		// ip
	private String sxsc;		// 酸洗生产情况
	private String sxpz;		// 酸洗品质情况
	private String sxsb;		// 酸洗设备
	private String sxaq;		// 酸洗安全
	private String sxry;		// 酸洗人员
	private String sxqt;		// 酸洗其他
	private String sxresult;		// 酸洗处理结果
	private String zjsc;		// 轧机生产情况
	private String zjpz;		// 轧机品质情况
	private String zjsb;		// 轧机设备
	private String zjaq;		// 轧机安全
	private String zjry;		// 轧机人员
	private String zjqt;		// 轧机其他
	private String zjresult;		// 轧机处理结果
	private String xzjsc;		// 新轧机生产情况
	private String xzjpz;		// 新轧机品质情况
	private String xzjsb;		// 新轧机设备
	private String xzjaq;		// 新轧机安全
	private String xzjry;		// 新轧机人员
	private String xzjqt;		// 新轧机其他
	private String xzjresult;		// 新轧机处理结果
	private String wddxsc;		// 25万吨镀锌生产情况
	private String wddxpz;		// 25万吨镀锌品质情况
	private String wddxsb;		// 25万吨镀锌设备
	private String wddxaq;		// 25万吨镀锌安全
	private String wddxry;		// 25万吨镀锌人员
	private String wddxqt;		// 25万吨镀锌其他
	private String wddxresult;		// 25万吨镀锌处理结果
	private String wddlxsc;		// 15万吨镀铝锌生产情况
	private String wddlxpz;		// 15万吨镀铝锌品质情况
	private String wddlxsb;		// 15万吨镀铝锌设备
	private String wddlxaq;		// 15万吨镀铝锌安全
	private String wddlxry;		// 15万吨镀铝锌人员
	private String wddlxqt;		// 15万吨镀铝锌其他
	private String wddlxresult;		// 15万吨镀铝锌处理结果
	private String bbdxsc;		// 薄板镀锌生产情况
	private String bbdxpz;		// 薄板镀锌品质情况
	private String bbdxsb;		// 薄板镀锌设备
	private String bbdxaq;		// 薄板镀锌安全
	private String bbdxry;		// 薄板镀锌人员
	private String bbdxqt;		// 薄板镀锌其他
	private String bbdxresult;		// 薄板镀锌处理结果
	private String ctsc;		// 彩涂生产情况
	private String ctpz;		// 彩涂品质情况
	private String ctsb;		// 彩涂设备
	private String ctaq;		// 彩涂安全
	private String ctry;		// 彩涂人员
	private String ctqt;		// 彩涂其他
	private String ctresult;		// 彩涂处理结果
	private String theothers;		// 其他
	private String bglpro;		// 办公楼
	private String bglres;		// 办公楼处理结果
	private String bzhypro;		// 品质化验
	private String bzhyres;		// 品质化验处理结果
	private String sfflpro;		// 收发辅料
	private String sfflres;		// 收发辅料处理结果
	private String gfpro;		// 公辅
	private String gfres;		// 公辅处理结果
	private String sshpro;		// 宿舍
	private String sshres;		// 宿舍处理结果
	private String shtpro;		// 食堂
	private String shtres;		// 食堂处理结果
	private String mwpro;		// 门卫
	private String mwres;		// 门卫处理结果
	private String wqzbpro;		// 围墙周边
	private String wqzbres;		// 围墙周边处理结果
	private String jxxmpro;		// 检修项目情况
	private String jxxmres;		// 检修项目情况处理结果
	private String qtjj;		// 其他交接
	private String zfcjsc;		// 直缝车间生产情况
	private String zfcjpz;		// 直缝车间品质情况
	private String zfcjsb;		// 直缝车间设备
	private String zfcjaq;		// 直缝车间安全
	private String zfcjry;		// 直缝车间人员
	private String zfcjqt;		// 直缝车间其他
	private String zfcjresult;		// 直缝车间处理结果
	private String lxcjsc;		// 螺旋车间生产情况
	private String lxcjpz;		// 螺旋车间品质情况
	private String lxcjsb;		// 螺旋车间设备
	private String lxcjaq;		// 螺旋车间安全
	private String lxcjry;		// 螺旋车间人员
	private String lxcjqt;		// 螺旋车间其他
	private String lxcjresult;		// 螺旋车间处理结果
	private String ffcjsc;		// 防腐车间生产情况
	private String ffcjpz;		// 防腐车间品质情况
	private String ffcjsb;		// 防腐车间设备
	private String ffcjaq;		// 防腐车间安全
	private String ffcjry;		// 防腐车间人员
	private String ffcjqt;		// 防腐车间其他
	private String ffcjresult;		// 防腐车间处理结果
	private String xhcksc;		// 现货仓库生产情况
	private String xhckpz;		// 现货仓库品质情况
	private String xhcksb;		// 现货仓库设备
	private String xhckaq;		// 现货仓库安全
	private String xhckry;		// 现货仓库人员
	private String xhckqt;		// 现货仓库其他
	private String xhckresult;		// 现货仓库处理结果
	private String jxdgsc;		// 机修电工房生产情况
	private String jxdgpz;		// 机修电工房品质情况
	private String jxdgsb;		// 机修电工房设备
	private String jxdgaq;		// 机修电工房安全
	private String jxdgry;		// 机修电工房人员
	private String jxdgqt;		// 机修电工房其他
	private String jxdgresult;		// 机修电工房处理结果
	private String worktime;		// 白夜班
	private String handover;		// 交接人
	private String memo;		// 备注
	private String tbType;
	
	public Duty() {
		super();
	}

	public Duty(String id){
		super(id);
	}

	@ExcelField(title="公司", dictType="", align=2, sort=7)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开始时间", align=2, sort=8)
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=9)
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	@ExcelField(title="值班人", align=2, sort=10)
	public String getDutier() {
		return dutier;
	}

	public void setDutier(String dutier) {
		this.dutier = dutier;
	}
	
	@ExcelField(title="部门", align=2, sort=11)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@ExcelField(title="职务", align=2, sort=12)
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@ExcelField(title="浙华办公楼存在问题", align=2, sort=13)
	public String getZhehuaproblem() {
		return zhehuaproblem;
	}

	public void setZhehuaproblem(String zhehuaproblem) {
		this.zhehuaproblem = zhehuaproblem;
	}
	
	@ExcelField(title="浙华办公楼处理结果", align=2, sort=14)
	public String getZhehuaresult() {
		return zhehuaresult;
	}

	public void setZhehuaresult(String zhehuaresult) {
		this.zhehuaresult = zhehuaresult;
	}
	
	@ExcelField(title="北门保安室存在问题", align=2, sort=15)
	public String getBeimenproblem() {
		return beimenproblem;
	}

	public void setBeimenproblem(String beimenproblem) {
		this.beimenproblem = beimenproblem;
	}
	
	@ExcelField(title="北门保安室处理结果", align=2, sort=16)
	public String getBeimenresult() {
		return beimenresult;
	}

	public void setBeimenresult(String beimenresult) {
		this.beimenresult = beimenresult;
	}
	
	@ExcelField(title="生产办公室存在问题", align=2, sort=17)
	public String getShengchanproblem() {
		return shengchanproblem;
	}

	public void setShengchanproblem(String shengchanproblem) {
		this.shengchanproblem = shengchanproblem;
	}
	
	@ExcelField(title="生产办公室处理结果", align=2, sort=18)
	public String getShengchanresult() {
		return shengchanresult;
	}

	public void setShengchanresult(String shengchanresult) {
		this.shengchanresult = shengchanresult;
	}
	
	@ExcelField(title="直缝车间存在问题", align=2, sort=19)
	public String getZfroomproblem() {
		return zfroomproblem;
	}

	public void setZfroomproblem(String zfroomproblem) {
		this.zfroomproblem = zfroomproblem;
	}
	
	@ExcelField(title="直缝车间处理结果", align=2, sort=20)
	public String getZfroomresult() {
		return zfroomresult;
	}

	public void setZfroomresult(String zfroomresult) {
		this.zfroomresult = zfroomresult;
	}
	
	@ExcelField(title="螺旋车间处理结果", align=2, sort=21)
	public String getLxroomresult() {
		return lxroomresult;
	}

	public void setLxroomresult(String lxroomresult) {
		this.lxroomresult = lxroomresult;
	}
	
	@ExcelField(title="螺旋车间存在问题", align=2, sort=22)
	public String getLxroomproblem() {
		return lxroomproblem;
	}

	public void setLxroomproblem(String lxroomproblem) {
		this.lxroomproblem = lxroomproblem;
	}
	
	@ExcelField(title="防腐车间存在问题", align=2, sort=23)
	public String getFfroomproblem() {
		return ffroomproblem;
	}

	public void setFfroomproblem(String ffroomproblem) {
		this.ffroomproblem = ffroomproblem;
	}
	
	@ExcelField(title="防腐车间处理结果", align=2, sort=24)
	public String getFfroomresult() {
		return ffroomresult;
	}

	public void setFfroomresult(String ffroomresult) {
		this.ffroomresult = ffroomresult;
	}
	
	@ExcelField(title="现货仓库存在问题", align=2, sort=25)
	public String getXhstoreproblem() {
		return xhstoreproblem;
	}

	public void setXhstoreproblem(String xhstoreproblem) {
		this.xhstoreproblem = xhstoreproblem;
	}
	
	@ExcelField(title="现货仓库处理结果", align=2, sort=26)
	public String getXhstoreresult() {
		return xhstoreresult;
	}

	public void setXhstoreresult(String xhstoreresult) {
		this.xhstoreresult = xhstoreresult;
	}
	
	@ExcelField(title="机修电工房存在问题", align=2, sort=27)
	public String getDgroomproblem() {
		return dgroomproblem;
	}

	public void setDgroomproblem(String dgroomproblem) {
		this.dgroomproblem = dgroomproblem;
	}
	
	@ExcelField(title="机修电工房处理结果", align=2, sort=28)
	public String getDgroomresult() {
		return dgroomresult;
	}

	public void setDgroomresult(String dgroomresult) {
		this.dgroomresult = dgroomresult;
	}
	
	@ExcelField(title="收发辅料仓库存在问题", align=2, sort=29)
	public String getSfflstoreproblem() {
		return sfflstoreproblem;
	}

	public void setSfflstoreproblem(String sfflstoreproblem) {
		this.sfflstoreproblem = sfflstoreproblem;
	}
	
	@ExcelField(title="收发辅料仓库处理结果", align=2, sort=30)
	public String getSfflstoreresult() {
		return sfflstoreresult;
	}

	public void setSfflstoreresult(String sfflstoreresult) {
		this.sfflstoreresult = sfflstoreresult;
	}
	
	@ExcelField(title="薄板办公楼存在问题", align=2, sort=31)
	public String getBobanofficeproblem() {
		return bobanofficeproblem;
	}

	public void setBobanofficeproblem(String bobanofficeproblem) {
		this.bobanofficeproblem = bobanofficeproblem;
	}
	
	@ExcelField(title="薄板办公楼处理结果", align=2, sort=32)
	public String getBobanofficeresult() {
		return bobanofficeresult;
	}

	public void setBobanofficeresult(String bobanofficeresult) {
		this.bobanofficeresult = bobanofficeresult;
	}
	
	@ExcelField(title="监控室存在问题", align=2, sort=33)
	public String getMonitorroomproblem() {
		return monitorroomproblem;
	}

	public void setMonitorroomproblem(String monitorroomproblem) {
		this.monitorroomproblem = monitorroomproblem;
	}
	
	@ExcelField(title="监控室处理结果", align=2, sort=34)
	public String getMonitorroomresult() {
		return monitorroomresult;
	}

	public void setMonitorroomresult(String monitorroomresult) {
		this.monitorroomresult = monitorroomresult;
	}
	
	@ExcelField(title="克罗德办公楼存在问题", align=2, sort=35)
	public String getKldofficeproblem() {
		return kldofficeproblem;
	}

	public void setKldofficeproblem(String kldofficeproblem) {
		this.kldofficeproblem = kldofficeproblem;
	}
	
	@ExcelField(title="克罗德办公楼处理结果", align=2, sort=36)
	public String getKldofficeresult() {
		return kldofficeresult;
	}

	public void setKldofficeresult(String kldofficeresult) {
		this.kldofficeresult = kldofficeresult;
	}
	
	@ExcelField(title="薄板镀锌线存在问题", align=2, sort=37)
	public String getAduxinproblem() {
		return aduxinproblem;
	}

	public void setAduxinproblem(String aduxinproblem) {
		this.aduxinproblem = aduxinproblem;
	}
	
	@ExcelField(title="薄板镀锌线处理结果", align=2, sort=38)
	public String getAduxinresult() {
		return aduxinresult;
	}

	public void setAduxinresult(String aduxinresult) {
		this.aduxinresult = aduxinresult;
	}
	
	@ExcelField(title="薄板彩涂线存在问题", align=2, sort=39)
	public String getAtucaiproblem() {
		return atucaiproblem;
	}

	public void setAtucaiproblem(String atucaiproblem) {
		this.atucaiproblem = atucaiproblem;
	}
	
	@ExcelField(title="薄板彩涂线处理结果", align=2, sort=40)
	public String getAtucairesult() {
		return atucairesult;
	}

	public void setAtucairesult(String atucairesult) {
		this.atucairesult = atucairesult;
	}
	
	@ExcelField(title="酸洗产线存在问题", align=2, sort=41)
	public String getBsuanxiproblem() {
		return bsuanxiproblem;
	}

	public void setBsuanxiproblem(String bsuanxiproblem) {
		this.bsuanxiproblem = bsuanxiproblem;
	}
	
	@ExcelField(title="酸洗产线处理结果", align=2, sort=42)
	public String getBsuanxiresult() {
		return bsuanxiresult;
	}

	public void setBsuanxiresult(String bsuanxiresult) {
		this.bsuanxiresult = bsuanxiresult;
	}
	
	@ExcelField(title="1#轧机存在问题", align=2, sort=43)
	public String getBzhaji1problem() {
		return bzhaji1problem;
	}

	public void setBzhaji1problem(String bzhaji1problem) {
		this.bzhaji1problem = bzhaji1problem;
	}
	
	@ExcelField(title="1#轧机处理结果", align=2, sort=44)
	public String getBzhaji1result() {
		return bzhaji1result;
	}

	public void setBzhaji1result(String bzhaji1result) {
		this.bzhaji1result = bzhaji1result;
	}
	
	@ExcelField(title="2#轧机存在问题", align=2, sort=45)
	public String getBzhaji2problem() {
		return bzhaji2problem;
	}

	public void setBzhaji2problem(String bzhaji2problem) {
		this.bzhaji2problem = bzhaji2problem;
	}
	
	@ExcelField(title="2#轧机处理结果", align=2, sort=46)
	public String getBzhaji2result() {
		return bzhaji2result;
	}

	public void setBzhaji2result(String bzhaji2result) {
		this.bzhaji2result = bzhaji2result;
	}
	
	@ExcelField(title="15WT镀锌存在问题", align=2, sort=47)
	public String getCduxin15problem() {
		return cduxin15problem;
	}

	public void setCduxin15problem(String cduxin15problem) {
		this.cduxin15problem = cduxin15problem;
	}
	
	@ExcelField(title="15WT镀锌处理结果", align=2, sort=48)
	public String getCduxin15result() {
		return cduxin15result;
	}

	public void setCduxin15result(String cduxin15result) {
		this.cduxin15result = cduxin15result;
	}
	
	@ExcelField(title="25WT镀锌存在问题", align=2, sort=49)
	public String getCduxin25problem() {
		return cduxin25problem;
	}

	public void setCduxin25problem(String cduxin25problem) {
		this.cduxin25problem = cduxin25problem;
	}
	
	@ExcelField(title="25WT镀锌处理结果", align=2, sort=50)
	public String getCduxin25result() {
		return cduxin25result;
	}

	public void setCduxin25result(String cduxin25result) {
		this.cduxin25result = cduxin25result;
	}
	
	@ExcelField(title="镀铝锌存在问题", align=2, sort=51)
	public String getCdulvxinproblem() {
		return cdulvxinproblem;
	}

	public void setCdulvxinproblem(String cdulvxinproblem) {
		this.cdulvxinproblem = cdulvxinproblem;
	}
	
	@ExcelField(title="镀铝锌处理结果", align=2, sort=52)
	public String getCdulvxinresult() {
		return cdulvxinresult;
	}

	public void setCdulvxinresult(String cdulvxinresult) {
		this.cdulvxinresult = cdulvxinresult;
	}
	
	@ExcelField(title="薄板大仓库存在问题", align=2, sort=53)
	public String getAstoreproblem() {
		return astoreproblem;
	}

	public void setAstoreproblem(String astoreproblem) {
		this.astoreproblem = astoreproblem;
	}
	
	@ExcelField(title="薄板大仓库处理结果", align=2, sort=54)
	public String getAstoreresult() {
		return astoreresult;
	}

	public void setAstoreresult(String astoreresult) {
		this.astoreresult = astoreresult;
	}
	
	@ExcelField(title="克罗德大仓库存在问题", align=2, sort=55)
	public String getCstoreproblem() {
		return cstoreproblem;
	}

	public void setCstoreproblem(String cstoreproblem) {
		this.cstoreproblem = cstoreproblem;
	}
	
	@ExcelField(title="克罗德大仓库处理结果", align=2, sort=56)
	public String getCstoreresult() {
		return cstoreresult;
	}

	public void setCstoreresult(String cstoreresult) {
		this.cstoreresult = cstoreresult;
	}
	
	@ExcelField(title="薄板辅料仓库存在问题", align=2, sort=57)
	public String getAflstoreproblem() {
		return aflstoreproblem;
	}

	public void setAflstoreproblem(String aflstoreproblem) {
		this.aflstoreproblem = aflstoreproblem;
	}
	
	@ExcelField(title="薄板辅料仓库处理结果", align=2, sort=58)
	public String getAflstoreresult() {
		return aflstoreresult;
	}

	public void setAflstoreresult(String aflstoreresult) {
		this.aflstoreresult = aflstoreresult;
	}
	
	@ExcelField(title="克罗德辅料仓库存在问题", align=2, sort=59)
	public String getBflstoreproblem() {
		return bflstoreproblem;
	}

	public void setBflstoreproblem(String bflstoreproblem) {
		this.bflstoreproblem = bflstoreproblem;
	}
	
	@ExcelField(title="克罗德辅料仓库处理结果", align=2, sort=60)
	public String getBflstoreresult() {
		return bflstoreresult;
	}

	public void setBflstoreresult(String bflstoreresult) {
		this.bflstoreresult = bflstoreresult;
	}
	
	@ExcelField(title="cflstoreproblem", align=2, sort=61)
	public String getCflstoreproblem() {
		return cflstoreproblem;
	}

	public void setCflstoreproblem(String cflstoreproblem) {
		this.cflstoreproblem = cflstoreproblem;
	}
	
	@ExcelField(title="cflstoreresult", align=2, sort=62)
	public String getCflstoreresult() {
		return cflstoreresult;
	}

	public void setCflstoreresult(String cflstoreresult) {
		this.cflstoreresult = cflstoreresult;
	}
	
	@ExcelField(title="综合办公楼存在问题", align=2, sort=63)
	public String getOfficeproblem() {
		return officeproblem;
	}

	public void setOfficeproblem(String officeproblem) {
		this.officeproblem = officeproblem;
	}
	
	@ExcelField(title="综合办公楼处理结果", align=2, sort=64)
	public String getOfficeresult() {
		return officeresult;
	}

	public void setOfficeresult(String officeresult) {
		this.officeresult = officeresult;
	}
	
	@ExcelField(title="机房存在问题", align=2, sort=65)
	public String getMachineroomproblem() {
		return machineroomproblem;
	}

	public void setMachineroomproblem(String machineroomproblem) {
		this.machineroomproblem = machineroomproblem;
	}
	
	@ExcelField(title="机房处理结果", align=2, sort=66)
	public String getMachineroomresult() {
		return machineroomresult;
	}

	public void setMachineroomresult(String machineroomresult) {
		this.machineroomresult = machineroomresult;
	}
	
	@ExcelField(title="辅料仓库存在问题", align=2, sort=67)
	public String getFlstoreproblem() {
		return flstoreproblem;
	}

	public void setFlstoreproblem(String flstoreproblem) {
		this.flstoreproblem = flstoreproblem;
	}
	
	@ExcelField(title="辅料仓库处理结果", align=2, sort=68)
	public String getFlstoreresult() {
		return flstoreresult;
	}

	public void setFlstoreresult(String flstoreresult) {
		this.flstoreresult = flstoreresult;
	}
	
	@ExcelField(title="厂区及车间存在问题", align=2, sort=69)
	public String getFactoryworkproblem() {
		return factoryworkproblem;
	}

	public void setFactoryworkproblem(String factoryworkproblem) {
		this.factoryworkproblem = factoryworkproblem;
	}
	
	@ExcelField(title="厂区及车间处理结果", align=2, sort=70)
	public String getFactoryworkresult() {
		return factoryworkresult;
	}

	public void setFactoryworkresult(String factoryworkresult) {
		this.factoryworkresult = factoryworkresult;
	}
	
	@ExcelField(title="配电间存在问题", align=2, sort=71)
	public String getPowerroomproblem() {
		return powerroomproblem;
	}

	public void setPowerroomproblem(String powerroomproblem) {
		this.powerroomproblem = powerroomproblem;
	}
	
	@ExcelField(title="配电间处理结果", align=2, sort=72)
	public String getPowerroomresult() {
		return powerroomresult;
	}

	public void setPowerroomresult(String powerroomresult) {
		this.powerroomresult = powerroomresult;
	}
	
	@ExcelField(title="余料仓库存在问题", align=2, sort=73)
	public String getBgstoreproblem() {
		return bgstoreproblem;
	}

	public void setBgstoreproblem(String bgstoreproblem) {
		this.bgstoreproblem = bgstoreproblem;
	}
	
	@ExcelField(title="余料仓库处理结果", align=2, sort=74)
	public String getBgstoreresult() {
		return bgstoreresult;
	}

	public void setBgstoreresult(String bgstoreresult) {
		this.bgstoreresult = bgstoreresult;
	}
	
	@ExcelField(title="ylstoreproblem", align=2, sort=75)
	public String getYlstoreproblem() {
		return ylstoreproblem;
	}

	public void setYlstoreproblem(String ylstoreproblem) {
		this.ylstoreproblem = ylstoreproblem;
	}
	
	@ExcelField(title="ylstoreresult", align=2, sort=76)
	public String getYlstoreresult() {
		return ylstoreresult;
	}

	public void setYlstoreresult(String ylstoreresult) {
		this.ylstoreresult = ylstoreresult;
	}
	
	@ExcelField(title="宿舍存在问题", align=2, sort=77)
	public String getDormitoryproblem() {
		return dormitoryproblem;
	}

	public void setDormitoryproblem(String dormitoryproblem) {
		this.dormitoryproblem = dormitoryproblem;
	}
	
	@ExcelField(title="宿舍处理结果", align=2, sort=78)
	public String getDormitoryresult() {
		return dormitoryresult;
	}

	public void setDormitoryresult(String dormitoryresult) {
		this.dormitoryresult = dormitoryresult;
	}
	
	@ExcelField(title="食堂存在问题", align=2, sort=79)
	public String getEatroomproblem() {
		return eatroomproblem;
	}

	public void setEatroomproblem(String eatroomproblem) {
		this.eatroomproblem = eatroomproblem;
	}
	
	@ExcelField(title="食堂处理结果", align=2, sort=80)
	public String getEatroomresult() {
		return eatroomresult;
	}

	public void setEatroomresult(String eatroomresult) {
		this.eatroomresult = eatroomresult;
	}
	
	@ExcelField(title="门卫存在问题", align=2, sort=81)
	public String getGuardproblem() {
		return guardproblem;
	}

	public void setGuardproblem(String guardproblem) {
		this.guardproblem = guardproblem;
	}
	
	@ExcelField(title="门卫处理结果", align=2, sort=82)
	public String getGuardresult() {
		return guardresult;
	}

	public void setGuardresult(String guardresult) {
		this.guardresult = guardresult;
	}
	
	@ExcelField(title="补充总结", align=2, sort=83)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@ExcelField(title="处理人确认", align=2, sort=84)
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	@ExcelField(title="涂镀事业中心存在问题", align=2, sort=85)
	public String getTdbsproblem() {
		return tdbsproblem;
	}

	public void setTdbsproblem(String tdbsproblem) {
		this.tdbsproblem = tdbsproblem;
	}
	
	@ExcelField(title="涂镀事业中心处理结果", align=2, sort=86)
	public String getTdbsresult() {
		return tdbsresult;
	}

	public void setTdbsresult(String tdbsresult) {
		this.tdbsresult = tdbsresult;
	}
	
	@ExcelField(title="钢管事业中心存在问题", align=2, sort=87)
	public String getGgbsproblem() {
		return ggbsproblem;
	}

	public void setGgbsproblem(String ggbsproblem) {
		this.ggbsproblem = ggbsproblem;
	}
	
	@ExcelField(title="钢管事业中心处理结果", align=2, sort=88)
	public String getGgbsresult() {
		return ggbsresult;
	}

	public void setGgbsresult(String ggbsresult) {
		this.ggbsresult = ggbsresult;
	}
	
	@ExcelField(title="加工事业中心存在问题", align=2, sort=89)
	public String getJgbsproblem() {
		return jgbsproblem;
	}

	public void setJgbsproblem(String jgbsproblem) {
		this.jgbsproblem = jgbsproblem;
	}
	
	@ExcelField(title="加工事业中心处理结果", align=2, sort=90)
	public String getJgbsresult() {
		return jgbsresult;
	}

	public void setJgbsresult(String jgbsresult) {
		this.jgbsresult = jgbsresult;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=91)
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=92)
	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	
	@ExcelField(title="ip", align=2, sort=93)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@ExcelField(title="酸洗生产情况", align=2, sort=94)
	public String getSxsc() {
		return sxsc;
	}

	public void setSxsc(String sxsc) {
		this.sxsc = sxsc;
	}
	
	@ExcelField(title="酸洗品质情况", align=2, sort=95)
	public String getSxpz() {
		return sxpz;
	}

	public void setSxpz(String sxpz) {
		this.sxpz = sxpz;
	}
	
	@ExcelField(title="酸洗设备", align=2, sort=96)
	public String getSxsb() {
		return sxsb;
	}

	public void setSxsb(String sxsb) {
		this.sxsb = sxsb;
	}
	
	@ExcelField(title="酸洗安全", align=2, sort=97)
	public String getSxaq() {
		return sxaq;
	}

	public void setSxaq(String sxaq) {
		this.sxaq = sxaq;
	}
	
	@ExcelField(title="酸洗人员", align=2, sort=98)
	public String getSxry() {
		return sxry;
	}

	public void setSxry(String sxry) {
		this.sxry = sxry;
	}
	
	@ExcelField(title="酸洗其他", align=2, sort=99)
	public String getSxqt() {
		return sxqt;
	}

	public void setSxqt(String sxqt) {
		this.sxqt = sxqt;
	}
	
	@ExcelField(title="酸洗处理结果", align=2, sort=100)
	public String getSxresult() {
		return sxresult;
	}

	public void setSxresult(String sxresult) {
		this.sxresult = sxresult;
	}
	
	@ExcelField(title="轧机生产情况", align=2, sort=101)
	public String getZjsc() {
		return zjsc;
	}

	public void setZjsc(String zjsc) {
		this.zjsc = zjsc;
	}
	
	@ExcelField(title="轧机品质情况", align=2, sort=102)
	public String getZjpz() {
		return zjpz;
	}

	public void setZjpz(String zjpz) {
		this.zjpz = zjpz;
	}
	
	@ExcelField(title="轧机设备", align=2, sort=103)
	public String getZjsb() {
		return zjsb;
	}

	public void setZjsb(String zjsb) {
		this.zjsb = zjsb;
	}
	
	@ExcelField(title="轧机安全", align=2, sort=104)
	public String getZjaq() {
		return zjaq;
	}

	public void setZjaq(String zjaq) {
		this.zjaq = zjaq;
	}
	
	@ExcelField(title="轧机人员", align=2, sort=105)
	public String getZjry() {
		return zjry;
	}

	public void setZjry(String zjry) {
		this.zjry = zjry;
	}
	
	@ExcelField(title="轧机其他", align=2, sort=106)
	public String getZjqt() {
		return zjqt;
	}

	public void setZjqt(String zjqt) {
		this.zjqt = zjqt;
	}
	
	@ExcelField(title="轧机处理结果", align=2, sort=107)
	public String getZjresult() {
		return zjresult;
	}

	public void setZjresult(String zjresult) {
		this.zjresult = zjresult;
	}
	
	@ExcelField(title="新轧机生产情况", align=2, sort=108)
	public String getXzjsc() {
		return xzjsc;
	}

	public void setXzjsc(String xzjsc) {
		this.xzjsc = xzjsc;
	}
	
	@ExcelField(title="新轧机品质情况", align=2, sort=109)
	public String getXzjpz() {
		return xzjpz;
	}

	public void setXzjpz(String xzjpz) {
		this.xzjpz = xzjpz;
	}
	
	@ExcelField(title="新轧机设备", align=2, sort=110)
	public String getXzjsb() {
		return xzjsb;
	}

	public void setXzjsb(String xzjsb) {
		this.xzjsb = xzjsb;
	}
	
	@ExcelField(title="新轧机安全", align=2, sort=111)
	public String getXzjaq() {
		return xzjaq;
	}

	public void setXzjaq(String xzjaq) {
		this.xzjaq = xzjaq;
	}
	
	@ExcelField(title="新轧机人员", align=2, sort=112)
	public String getXzjry() {
		return xzjry;
	}

	public void setXzjry(String xzjry) {
		this.xzjry = xzjry;
	}
	
	@ExcelField(title="新轧机其他", align=2, sort=113)
	public String getXzjqt() {
		return xzjqt;
	}

	public void setXzjqt(String xzjqt) {
		this.xzjqt = xzjqt;
	}
	
	@ExcelField(title="新轧机处理结果", align=2, sort=114)
	public String getXzjresult() {
		return xzjresult;
	}

	public void setXzjresult(String xzjresult) {
		this.xzjresult = xzjresult;
	}
	
	@ExcelField(title="25万吨镀锌生产情况", align=2, sort=115)
	public String getWddxsc() {
		return wddxsc;
	}

	public void setWddxsc(String wddxsc) {
		this.wddxsc = wddxsc;
	}
	
	@ExcelField(title="25万吨镀锌品质情况", align=2, sort=116)
	public String getWddxpz() {
		return wddxpz;
	}

	public void setWddxpz(String wddxpz) {
		this.wddxpz = wddxpz;
	}
	
	@ExcelField(title="25万吨镀锌设备", align=2, sort=117)
	public String getWddxsb() {
		return wddxsb;
	}

	public void setWddxsb(String wddxsb) {
		this.wddxsb = wddxsb;
	}
	
	@ExcelField(title="25万吨镀锌安全", align=2, sort=118)
	public String getWddxaq() {
		return wddxaq;
	}

	public void setWddxaq(String wddxaq) {
		this.wddxaq = wddxaq;
	}
	
	@ExcelField(title="25万吨镀锌人员", align=2, sort=119)
	public String getWddxry() {
		return wddxry;
	}

	public void setWddxry(String wddxry) {
		this.wddxry = wddxry;
	}
	
	@ExcelField(title="25万吨镀锌其他", align=2, sort=120)
	public String getWddxqt() {
		return wddxqt;
	}

	public void setWddxqt(String wddxqt) {
		this.wddxqt = wddxqt;
	}
	
	@ExcelField(title="25万吨镀锌处理结果", align=2, sort=121)
	public String getWddxresult() {
		return wddxresult;
	}

	public void setWddxresult(String wddxresult) {
		this.wddxresult = wddxresult;
	}
	
	@ExcelField(title="15万吨镀铝锌生产情况", align=2, sort=122)
	public String getWddlxsc() {
		return wddlxsc;
	}

	public void setWddlxsc(String wddlxsc) {
		this.wddlxsc = wddlxsc;
	}
	
	@ExcelField(title="15万吨镀铝锌品质情况", align=2, sort=123)
	public String getWddlxpz() {
		return wddlxpz;
	}

	public void setWddlxpz(String wddlxpz) {
		this.wddlxpz = wddlxpz;
	}
	
	@ExcelField(title="15万吨镀铝锌设备", align=2, sort=124)
	public String getWddlxsb() {
		return wddlxsb;
	}

	public void setWddlxsb(String wddlxsb) {
		this.wddlxsb = wddlxsb;
	}
	
	@ExcelField(title="15万吨镀铝锌安全", align=2, sort=125)
	public String getWddlxaq() {
		return wddlxaq;
	}

	public void setWddlxaq(String wddlxaq) {
		this.wddlxaq = wddlxaq;
	}
	
	@ExcelField(title="15万吨镀铝锌人员", align=2, sort=126)
	public String getWddlxry() {
		return wddlxry;
	}

	public void setWddlxry(String wddlxry) {
		this.wddlxry = wddlxry;
	}
	
	@ExcelField(title="15万吨镀铝锌其他", align=2, sort=127)
	public String getWddlxqt() {
		return wddlxqt;
	}

	public void setWddlxqt(String wddlxqt) {
		this.wddlxqt = wddlxqt;
	}
	
	@ExcelField(title="15万吨镀铝锌处理结果", align=2, sort=128)
	public String getWddlxresult() {
		return wddlxresult;
	}

	public void setWddlxresult(String wddlxresult) {
		this.wddlxresult = wddlxresult;
	}
	
	@ExcelField(title="薄板镀锌生产情况", align=2, sort=129)
	public String getBbdxsc() {
		return bbdxsc;
	}

	public void setBbdxsc(String bbdxsc) {
		this.bbdxsc = bbdxsc;
	}
	
	@ExcelField(title="薄板镀锌品质情况", align=2, sort=130)
	public String getBbdxpz() {
		return bbdxpz;
	}

	public void setBbdxpz(String bbdxpz) {
		this.bbdxpz = bbdxpz;
	}
	
	@ExcelField(title="薄板镀锌设备", align=2, sort=131)
	public String getBbdxsb() {
		return bbdxsb;
	}

	public void setBbdxsb(String bbdxsb) {
		this.bbdxsb = bbdxsb;
	}
	
	@ExcelField(title="薄板镀锌安全", align=2, sort=132)
	public String getBbdxaq() {
		return bbdxaq;
	}

	public void setBbdxaq(String bbdxaq) {
		this.bbdxaq = bbdxaq;
	}
	
	@ExcelField(title="薄板镀锌人员", align=2, sort=133)
	public String getBbdxry() {
		return bbdxry;
	}

	public void setBbdxry(String bbdxry) {
		this.bbdxry = bbdxry;
	}
	
	@ExcelField(title="薄板镀锌其他", align=2, sort=134)
	public String getBbdxqt() {
		return bbdxqt;
	}

	public void setBbdxqt(String bbdxqt) {
		this.bbdxqt = bbdxqt;
	}
	
	@ExcelField(title="薄板镀锌处理结果", align=2, sort=135)
	public String getBbdxresult() {
		return bbdxresult;
	}

	public void setBbdxresult(String bbdxresult) {
		this.bbdxresult = bbdxresult;
	}
	
	@ExcelField(title="彩涂生产情况", align=2, sort=136)
	public String getCtsc() {
		return ctsc;
	}

	public void setCtsc(String ctsc) {
		this.ctsc = ctsc;
	}
	
	@ExcelField(title="彩涂品质情况", align=2, sort=137)
	public String getCtpz() {
		return ctpz;
	}

	public void setCtpz(String ctpz) {
		this.ctpz = ctpz;
	}
	
	@ExcelField(title="彩涂设备", align=2, sort=138)
	public String getCtsb() {
		return ctsb;
	}

	public void setCtsb(String ctsb) {
		this.ctsb = ctsb;
	}
	
	@ExcelField(title="彩涂安全", align=2, sort=139)
	public String getCtaq() {
		return ctaq;
	}

	public void setCtaq(String ctaq) {
		this.ctaq = ctaq;
	}
	
	@ExcelField(title="彩涂人员", align=2, sort=140)
	public String getCtry() {
		return ctry;
	}

	public void setCtry(String ctry) {
		this.ctry = ctry;
	}
	
	@ExcelField(title="彩涂其他", align=2, sort=141)
	public String getCtqt() {
		return ctqt;
	}

	public void setCtqt(String ctqt) {
		this.ctqt = ctqt;
	}
	
	@ExcelField(title="彩涂处理结果", align=2, sort=142)
	public String getCtresult() {
		return ctresult;
	}

	public void setCtresult(String ctresult) {
		this.ctresult = ctresult;
	}
	
	@ExcelField(title="其他", align=2, sort=143)
	public String getTheothers() {
		return theothers;
	}

	public void setTheothers(String theothers) {
		this.theothers = theothers;
	}
	
	@ExcelField(title="办公楼", align=2, sort=144)
	public String getBglpro() {
		return bglpro;
	}

	public void setBglpro(String bglpro) {
		this.bglpro = bglpro;
	}
	
	@ExcelField(title="办公楼处理结果", align=2, sort=145)
	public String getBglres() {
		return bglres;
	}

	public void setBglres(String bglres) {
		this.bglres = bglres;
	}
	
	@ExcelField(title="品质化验", align=2, sort=146)
	public String getBzhypro() {
		return bzhypro;
	}

	public void setBzhypro(String bzhypro) {
		this.bzhypro = bzhypro;
	}
	
	@ExcelField(title="品质化验处理结果", align=2, sort=147)
	public String getBzhyres() {
		return bzhyres;
	}

	public void setBzhyres(String bzhyres) {
		this.bzhyres = bzhyres;
	}
	
	@ExcelField(title="收发辅料", align=2, sort=148)
	public String getSfflpro() {
		return sfflpro;
	}

	public void setSfflpro(String sfflpro) {
		this.sfflpro = sfflpro;
	}
	
	@ExcelField(title="收发辅料处理结果", align=2, sort=149)
	public String getSfflres() {
		return sfflres;
	}

	public void setSfflres(String sfflres) {
		this.sfflres = sfflres;
	}
	
	@ExcelField(title="公辅", align=2, sort=150)
	public String getGfpro() {
		return gfpro;
	}

	public void setGfpro(String gfpro) {
		this.gfpro = gfpro;
	}
	
	@ExcelField(title="公辅处理结果", align=2, sort=151)
	public String getGfres() {
		return gfres;
	}

	public void setGfres(String gfres) {
		this.gfres = gfres;
	}
	
	@ExcelField(title="宿舍", align=2, sort=152)
	public String getSshpro() {
		return sshpro;
	}

	public void setSshpro(String sshpro) {
		this.sshpro = sshpro;
	}
	
	@ExcelField(title="宿舍处理结果", align=2, sort=153)
	public String getSshres() {
		return sshres;
	}

	public void setSshres(String sshres) {
		this.sshres = sshres;
	}
	
	@ExcelField(title="食堂", align=2, sort=154)
	public String getShtpro() {
		return shtpro;
	}

	public void setShtpro(String shtpro) {
		this.shtpro = shtpro;
	}
	
	@ExcelField(title="食堂处理结果", align=2, sort=155)
	public String getShtres() {
		return shtres;
	}

	public void setShtres(String shtres) {
		this.shtres = shtres;
	}
	
	@ExcelField(title="门卫", align=2, sort=156)
	public String getMwpro() {
		return mwpro;
	}

	public void setMwpro(String mwpro) {
		this.mwpro = mwpro;
	}
	
	@ExcelField(title="门卫处理结果", align=2, sort=157)
	public String getMwres() {
		return mwres;
	}

	public void setMwres(String mwres) {
		this.mwres = mwres;
	}
	
	@ExcelField(title="围墙周边", align=2, sort=158)
	public String getWqzbpro() {
		return wqzbpro;
	}

	public void setWqzbpro(String wqzbpro) {
		this.wqzbpro = wqzbpro;
	}
	
	@ExcelField(title="围墙周边处理结果", align=2, sort=159)
	public String getWqzbres() {
		return wqzbres;
	}

	public void setWqzbres(String wqzbres) {
		this.wqzbres = wqzbres;
	}
	
	@ExcelField(title="检修项目情况", align=2, sort=160)
	public String getJxxmpro() {
		return jxxmpro;
	}

	public void setJxxmpro(String jxxmpro) {
		this.jxxmpro = jxxmpro;
	}
	
	@ExcelField(title="检修项目情况处理结果", align=2, sort=161)
	public String getJxxmres() {
		return jxxmres;
	}

	public void setJxxmres(String jxxmres) {
		this.jxxmres = jxxmres;
	}
	
	@ExcelField(title="其他交接", align=2, sort=162)
	public String getQtjj() {
		return qtjj;
	}

	public void setQtjj(String qtjj) {
		this.qtjj = qtjj;
	}
	
	@ExcelField(title="直缝车间生产情况", align=2, sort=163)
	public String getZfcjsc() {
		return zfcjsc;
	}

	public void setZfcjsc(String zfcjsc) {
		this.zfcjsc = zfcjsc;
	}
	
	@ExcelField(title="直缝车间品质情况", align=2, sort=164)
	public String getZfcjpz() {
		return zfcjpz;
	}

	public void setZfcjpz(String zfcjpz) {
		this.zfcjpz = zfcjpz;
	}
	
	@ExcelField(title="直缝车间设备", align=2, sort=165)
	public String getZfcjsb() {
		return zfcjsb;
	}

	public void setZfcjsb(String zfcjsb) {
		this.zfcjsb = zfcjsb;
	}
	
	@ExcelField(title="直缝车间安全", align=2, sort=166)
	public String getZfcjaq() {
		return zfcjaq;
	}

	public void setZfcjaq(String zfcjaq) {
		this.zfcjaq = zfcjaq;
	}
	
	@ExcelField(title="直缝车间人员", align=2, sort=167)
	public String getZfcjry() {
		return zfcjry;
	}

	public void setZfcjry(String zfcjry) {
		this.zfcjry = zfcjry;
	}
	
	@ExcelField(title="直缝车间其他", align=2, sort=168)
	public String getZfcjqt() {
		return zfcjqt;
	}

	public void setZfcjqt(String zfcjqt) {
		this.zfcjqt = zfcjqt;
	}
	
	@ExcelField(title="直缝车间处理结果", align=2, sort=169)
	public String getZfcjresult() {
		return zfcjresult;
	}

	public void setZfcjresult(String zfcjresult) {
		this.zfcjresult = zfcjresult;
	}
	
	@ExcelField(title="螺旋车间生产情况", align=2, sort=170)
	public String getLxcjsc() {
		return lxcjsc;
	}

	public void setLxcjsc(String lxcjsc) {
		this.lxcjsc = lxcjsc;
	}
	
	@ExcelField(title="螺旋车间品质情况", align=2, sort=171)
	public String getLxcjpz() {
		return lxcjpz;
	}

	public void setLxcjpz(String lxcjpz) {
		this.lxcjpz = lxcjpz;
	}
	
	@ExcelField(title="螺旋车间设备", align=2, sort=172)
	public String getLxcjsb() {
		return lxcjsb;
	}

	public void setLxcjsb(String lxcjsb) {
		this.lxcjsb = lxcjsb;
	}
	
	@ExcelField(title="螺旋车间安全", align=2, sort=173)
	public String getLxcjaq() {
		return lxcjaq;
	}

	public void setLxcjaq(String lxcjaq) {
		this.lxcjaq = lxcjaq;
	}
	
	@ExcelField(title="螺旋车间人员", align=2, sort=174)
	public String getLxcjry() {
		return lxcjry;
	}

	public void setLxcjry(String lxcjry) {
		this.lxcjry = lxcjry;
	}
	
	@ExcelField(title="螺旋车间其他", align=2, sort=175)
	public String getLxcjqt() {
		return lxcjqt;
	}

	public void setLxcjqt(String lxcjqt) {
		this.lxcjqt = lxcjqt;
	}
	
	@ExcelField(title="螺旋车间处理结果", align=2, sort=176)
	public String getLxcjresult() {
		return lxcjresult;
	}

	public void setLxcjresult(String lxcjresult) {
		this.lxcjresult = lxcjresult;
	}
	
	@ExcelField(title="防腐车间生产情况", align=2, sort=177)
	public String getFfcjsc() {
		return ffcjsc;
	}

	public void setFfcjsc(String ffcjsc) {
		this.ffcjsc = ffcjsc;
	}
	
	@ExcelField(title="防腐车间品质情况", align=2, sort=178)
	public String getFfcjpz() {
		return ffcjpz;
	}

	public void setFfcjpz(String ffcjpz) {
		this.ffcjpz = ffcjpz;
	}
	
	@ExcelField(title="防腐车间设备", align=2, sort=179)
	public String getFfcjsb() {
		return ffcjsb;
	}

	public void setFfcjsb(String ffcjsb) {
		this.ffcjsb = ffcjsb;
	}
	
	@ExcelField(title="防腐车间安全", align=2, sort=180)
	public String getFfcjaq() {
		return ffcjaq;
	}

	public void setFfcjaq(String ffcjaq) {
		this.ffcjaq = ffcjaq;
	}
	
	@ExcelField(title="防腐车间人员", align=2, sort=181)
	public String getFfcjry() {
		return ffcjry;
	}

	public void setFfcjry(String ffcjry) {
		this.ffcjry = ffcjry;
	}
	
	@ExcelField(title="防腐车间其他", align=2, sort=182)
	public String getFfcjqt() {
		return ffcjqt;
	}

	public void setFfcjqt(String ffcjqt) {
		this.ffcjqt = ffcjqt;
	}
	
	@ExcelField(title="防腐车间处理结果", align=2, sort=183)
	public String getFfcjresult() {
		return ffcjresult;
	}

	public void setFfcjresult(String ffcjresult) {
		this.ffcjresult = ffcjresult;
	}
	
	@ExcelField(title="现货仓库生产情况", align=2, sort=184)
	public String getXhcksc() {
		return xhcksc;
	}

	public void setXhcksc(String xhcksc) {
		this.xhcksc = xhcksc;
	}
	
	@ExcelField(title="现货仓库品质情况", align=2, sort=185)
	public String getXhckpz() {
		return xhckpz;
	}

	public void setXhckpz(String xhckpz) {
		this.xhckpz = xhckpz;
	}
	
	@ExcelField(title="现货仓库设备", align=2, sort=186)
	public String getXhcksb() {
		return xhcksb;
	}

	public void setXhcksb(String xhcksb) {
		this.xhcksb = xhcksb;
	}
	
	@ExcelField(title="现货仓库安全", align=2, sort=187)
	public String getXhckaq() {
		return xhckaq;
	}

	public void setXhckaq(String xhckaq) {
		this.xhckaq = xhckaq;
	}
	
	@ExcelField(title="现货仓库人员", align=2, sort=188)
	public String getXhckry() {
		return xhckry;
	}

	public void setXhckry(String xhckry) {
		this.xhckry = xhckry;
	}
	
	@ExcelField(title="现货仓库其他", align=2, sort=189)
	public String getXhckqt() {
		return xhckqt;
	}

	public void setXhckqt(String xhckqt) {
		this.xhckqt = xhckqt;
	}
	
	@ExcelField(title="现货仓库处理结果", align=2, sort=190)
	public String getXhckresult() {
		return xhckresult;
	}

	public void setXhckresult(String xhckresult) {
		this.xhckresult = xhckresult;
	}
	
	@ExcelField(title="机修电工房生产情况", align=2, sort=191)
	public String getJxdgsc() {
		return jxdgsc;
	}

	public void setJxdgsc(String jxdgsc) {
		this.jxdgsc = jxdgsc;
	}
	
	@ExcelField(title="机修电工房品质情况", align=2, sort=192)
	public String getJxdgpz() {
		return jxdgpz;
	}

	public void setJxdgpz(String jxdgpz) {
		this.jxdgpz = jxdgpz;
	}
	
	@ExcelField(title="机修电工房设备", align=2, sort=193)
	public String getJxdgsb() {
		return jxdgsb;
	}

	public void setJxdgsb(String jxdgsb) {
		this.jxdgsb = jxdgsb;
	}
	
	@ExcelField(title="机修电工房安全", align=2, sort=194)
	public String getJxdgaq() {
		return jxdgaq;
	}

	public void setJxdgaq(String jxdgaq) {
		this.jxdgaq = jxdgaq;
	}
	
	@ExcelField(title="机修电工房人员", align=2, sort=195)
	public String getJxdgry() {
		return jxdgry;
	}

	public void setJxdgry(String jxdgry) {
		this.jxdgry = jxdgry;
	}
	
	@ExcelField(title="机修电工房其他", align=2, sort=196)
	public String getJxdgqt() {
		return jxdgqt;
	}

	public void setJxdgqt(String jxdgqt) {
		this.jxdgqt = jxdgqt;
	}
	
	@ExcelField(title="机修电工房处理结果", align=2, sort=197)
	public String getJxdgresult() {
		return jxdgresult;
	}

	public void setJxdgresult(String jxdgresult) {
		this.jxdgresult = jxdgresult;
	}
	
	@ExcelField(title="白夜班", dictType="", align=2, sort=198)
	public String getWorktime() {
		return worktime;
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}
	
	@ExcelField(title="交接人", align=2, sort=199)
	public String getHandover() {
		return handover;
	}

	public void setHandover(String handover) {
		this.handover = handover;
	}
	
	@ExcelField(title="备注", align=2, sort=200)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTbType() {
		return tbType;
	}

	public void setTbType(String tbType) {
		this.tbType = tbType;
	}
}