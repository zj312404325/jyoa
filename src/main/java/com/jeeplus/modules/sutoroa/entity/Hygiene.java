/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sutoroa.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 8s检查Entity
 * @author cqj
 * @version 2018-03-02
 */
public class Hygiene extends DataEntity<Hygiene> {
	
	private static final long serialVersionUID = 1L;
	private String cqaqsituation;		// 产区安全检查情况
	private String cqaqcontent;		// 厂区安全整改内容
	private Date cqaqdate;		// 厂区安全整改期限
	private String cqaqresult;		// 产区安全复查结果
	private String cqaqcomment;		// 厂区安全备注
	private String cqaqfile;		// 厂区安全图文附件
	private String cqbysituation;		// 厂区标语检查情况
	private String cqbycontent;		// 厂区标语整改内容
	private Date cqbydate;		// 厂区标语整改期限
	private String cqbyresult;		// 厂区标语复查结果
	private String cqbycomment;		// 厂区标语备注
	private String cqbyfile;		// 厂区标语图文附件
	private String sbglsituation;		// 设备管理检查情况
	private String sbglcontent;		// 设备管理整改内容
	private Date sbgldate;		// 设备管理整改期限
	private String sbglresult;		// 设备管理复查结果
	private String sbglcomment;		// 设备管理备注
	private String sbglfile;		// 设备管理图文附件
	private String spglsituation;		// 食品管理检查情况
	private String spglcontent;		// 食品管理整改内容
	private Date spgldate;		// 食品管理整改期限
	private String spglresult;		// 食品管理复查结果
	private String spglcomment;		// 食品管理备注
	private String spglfile;		// 食品管理图文附件
	private String ygzzsituation;		// 员工着装检查情况
	private String ygzzcontent;		// 员工着装整改内容
	private Date ygzzdate;		// 员工着装整改期限
	private String ygzzresult;		// 员工着装复查结果
	private String ygzzcomment;		// 员工着装备注
	private String ygzzfile;		// 员工着装图文附件
	private String itsituation;		// it信息管理检查情况
	private String itcontent;		// it信息管理整改内容
	private Date itdate;		// it信息管理整改期限
	private String itresult;		// it信息管理复查结果
	private String itcomment;		// it信息管理备注
	private String itfile;		// it信息管理图文附件
	private String cpyssituation;		// 产品验收检查情况
	private String cpyscontent;		// 产品验收整改内容
	private Date cpysdate;		// 产品验收整改期限
	private String cpysresult;		// 产品验收复查结果
	private String cpyscomment;		// 产品验收备注
	private String cpysfile;		// 产品验收图文附件
	private String jjbsituation;		// 交接班检查情况
	private String jjbcontent;		// 交接班整改内容
	private Date jjbdate;		// 交接班整改期限
	private String jjbresult;		// 交接班复查结果
	private String jjbcomment;		// 交接班备注
	private String jjbfile;		// 交接班图文附件
	private String sczysituation;		// 生产作业检查情况
	private String sczycontent;		// 生产作业整改内容
	private Date sczydate;		// 生产作业整改期限
	private String sczyresult;		// 生产作业复查结果
	private String sczycomment;		// 生产作业备注
	private String sczyfile;		// 生产作业图文附件
	private String sfywsituation;		// 收发业务检查情况
	private String sfywcontent;		// 收发业务整改内容
	private Date sfywdate;		// 收发业务整改期限
	private String sfywresult;		// 收发业务复查结果
	private String sfywcomment;		// 收发业务备注
	private String sfywfile;		// 收发业务图文附件
	private String clglsituation;		// 车辆管理检查情况
	private String clglcontent;		// 车辆管理整改内容
	private Date clgldate;		// 车辆管理整改期限
	private String clglresult;		// 车辆管理复查结果
	private String clglcomment;		// 车辆管理备注
	private String clglfile;		// 车辆管理图文附件
	private String wlrysituation;		// 外来人员检查情况
	private String wlrycontent;		// 外来人员整改内容
	private Date wlrydate;		// 外来人员整改期限
	private String wlryresult;		// 外来人员复查结果
	private String wlrycomment;		// 外来人员备注
	private String wlryfile;		// 外来人员图文附件
	private String rwjssituation;		// 人文精神检查情况
	private String rwjscontent;		// 人文精神整改内容
	private Date rwjsdate;		// 人文精神整改期限
	private String rwjsresult;		// 人文精神复查结果
	private String rwjscomment;		// 人文精神备注
	private String rwjsfile;		// 人文精神图文附件
	private Integer bbdxqualified;		// 薄板镀锌1表示合格0表示不合格
	private String bbdxsituation;		// 薄板镀锌检查情况
	private String bbdxcontent;		// 薄板镀锌整改内容
	private Date bbdxdate;		// 薄板镀锌整改期限
	private String bbdxresult;		// 薄板镀锌复查结果
	private String bbdxcomment;		// 薄板镀锌备注
	private String bbdxfile;		// 薄板镀锌图文附件
	private Integer bbctqualified;		// 薄板彩涂1表示合格0表示不合格
	private String bbctsituation;		// 薄板彩涂检查情况
	private String bbctcontent;		// 薄板彩涂整改内容
	private Date bbctdate;		// 薄板彩涂整改期限
	private String bbctresult;		// 薄板彩涂复查结果
	private String bbctcomment;		// 薄板彩涂备注
	private String bbctfile;		// 薄板彩涂图文附件
	private Integer bbgpqualified;		// 薄板公铺1表示合格0表示不合格
	private String bbgpsituation;		// 薄板公铺检查情况
	private String bbgpcontent;		// 薄板公铺整改内容
	private Date bbgpdate;		// 薄板公铺整改期限
	private String bbgpresult;		// 薄板公铺复查结果
	private String bbgpcomment;		// 薄板公铺备注
	private String bbgpfile;		// 薄板公铺图文附件
	private Integer bbdckqualified;		// 薄板大仓库1表示合格0表示不合格
	private String bbdcksituation;		// 薄板大仓库检查情况
	private String bbdckcontent;		// 薄板大仓库整改内容
	private Date bbdckdate;		// 薄板大仓库整改期限
	private String bbdckresult;		// 薄板大仓库复查结果
	private String bbdckcomment;		// 薄板大仓库备注
	private String bbdckfile;		// 薄板大仓库图文附件
	private Integer bbflqualified;		// 薄板辅料1表示合格0表示不合格
	private String bbflsituation;		// 薄板辅料检查情况
	private String bbflcontent;		// 薄板辅料整改内容
	private Date bbfldate;		// 薄板辅料整改期限
	private String bbflresult;		// 薄板辅料复查结果
	private String bbflcomment;		// 薄板辅料备注
	private String bbflfile;		// 薄板辅料图文附件
	private Integer bbfeilqualified;		// 薄板废料1表示合格0表示不合格
	private String bbfeilsituation;		// 薄板废料检查情况
	private String bbfeilcontent;		// 薄板废料整改内容
	private Date bbfeildate;		// 薄板废料整改期限
	private String bbfeilresult;		// 薄板废料复查结果
	private String bbfeilcomment;		// 薄板废料备注
	private String bbfeilfile;		// 薄板废料图文附件
	private Integer bbbglqualified;		// 薄板办公楼1表示合格0表示不合格
	private String bbbglsituation;		// 薄板办公楼检查情况
	private String bbbglcontent;		// 薄板办公楼整改内容
	private Date bbbgldate;		// 薄板办公楼整改期限
	private String bbbglresult;		// 薄板办公楼复查结果
	private String bbbglcomment;		// 薄板办公楼备注
	private String bbbglfile;		// 薄板办公楼图文附件
	private Integer bbldqualified;		// 薄板楼道1表示合格0表示不合格
	private String bbldsituation;		// 薄板楼道检查情况
	private String bbldcontent;		// 薄板楼道整改内容
	private Date bblddate;		// 薄板楼道整改期限
	private String bbldresult;		// 薄板楼道复查结果
	private String bbldcomment;		// 薄板楼道备注
	private String bbldfile;		// 薄板楼道图文附件
	private Integer bbsxqualified;		// 薄板酸洗1表示合格0表示不合格
	private String bbsxsituation;		// 薄板酸洗检查情况
	private String bbsxcontent;		// 薄板酸洗整改内容
	private Date bbsxdate;		// 薄板酸洗整改期限
	private String bbsxresult;		// 薄板酸洗复查结果
	private String bbsxcomment;		// 薄板酸洗备注
	private String bbsxfile;		// 薄板酸洗图文附件
	private Integer bbyhzjqualified;		// 薄板1号轧机1表示合格0表示不合格
	private String bbyhzjsituation;		// 薄板1号轧机检查情况
	private String bbyhzjcontent;		// 薄板1号轧机整改内容
	private Date bbyhzjdate;		// 薄板1号轧机整改期限
	private String bbyhzjresult;		// 薄板1号轧机复查结果
	private String bbyhzjcomment;		// 薄板1号轧机备注
	private String bbyhzjfile;		// 薄板1号轧机图文附件
	private Integer bbehzjqualified;		// 薄板2号轧机1表示合格0表示不合格
	private String bbehzjsituation;		// 薄板2号轧机检查情况
	private String bbehzjcontent;		// 薄板2号轧机整改内容
	private Date bbehzjdate;		// 薄板2号轧机整改期限
	private String bbehzjresult;		// 薄板2号轧机复查结果
	private String bbehzjcomment;		// 薄板2号轧机备注
	private String bbehzjfile;		// 薄板2号轧机图文附件
	private Integer bbywdxqualified;		// 薄板15万吨镀锌
	private String bbywdxsituation;		// 薄板15万吨镀锌检查情况
	private String bbywdxcontent;		// 薄板15万吨镀锌整改内容
	private Date bbywdxdate;		// 薄板15万吨镀锌整改期限
	private String bbywdxresult;		// 薄板15万吨镀锌复查结果
	private String bbywdxcomment;		// 薄板15万吨镀锌备注
	private String bbywdxfile;		// 薄板15万吨镀锌图文附件
	private Integer bbewdxqualified;		// 薄板25万吨镀锌
	private String bbewdxsituation;		// 薄板25万吨镀锌检查情况
	private String bbewdxcontent;		// 薄板25万吨镀锌整改内容
	private Date bbewdxdate;		// 薄板25万吨镀锌整改期限
	private String bbewdxresult;		// 薄板25万吨镀锌复查结果
	private String bbewdxcomment;		// 薄板25万吨镀锌备注
	private String bbewdxfile;		// 薄板25万吨镀锌图文附件
	private Integer kldgpqualified;		// 克罗德公铺1表示合格0表示不合格
	private String kldgpsituation;		// 克罗德公铺检查情况
	private String kldgpcontent;		// 克罗德公铺整改内容
	private Date kldgpdate;		// 克罗德公铺整改期限
	private String kldgpresult;		// 克罗德公铺复查结果
	private String kldgpcomment;		// 克罗德公铺备注
	private String kldgpfile;		// 克罗德公铺图文附件
	private Integer klddckqualified;		// 克罗德大仓库
	private String klddcksituation;		// 克罗德大仓库检查情况
	private String klddckcontent;		// 克罗德大仓库整改内容
	private Date klddckdate;		// 克罗德大仓库整改期限
	private String klddckresult;		// 克罗德大仓库复查结果
	private String klddckcomment;		// 克罗德大仓库备注
	private String klddckfile;		// 克罗德大仓库图文附件
	private Integer kldflqualified;		// 克罗德辅料1表示合格0表示不合格
	private String kldflsituation;		// 克罗德辅料检查情况
	private String kldflcontent;		// 克罗德辅料整改内容
	private Date kldfldate;		// 克罗德辅料整改期限
	private String kldflresult;		// 克罗德辅料复查结果
	private String kldflcomment;		// 克罗德辅料库备注
	private String kldflfile;		// 克罗德辅料图文附件
	private Integer kldfeilqualified;		// 克罗德废料1表示合格0表示不合格
	private String kldfeilsituation;		// 克罗德废料检查情况
	private String kldfeilcontent;		// 克罗德废料整改内容
	private Date kldfeildate;		// 克罗德废料整改期限
	private String kldfeilresult;		// 克罗德废料复查结果
	private String kldfeilcomment;		// 克罗德废料库备注
	private String kldfeilfile;		// 克罗德废料图文附件
	private Integer kldbglqualified;		// 克罗德办公楼1表示合格0表示不合格
	private String kldbglsituation;		// 克罗德办公楼检查情况
	private String kldbglcontent;		// 克罗德办公楼整改内容
	private Date kldbgldate;		// 克罗德办公楼整改期限
	private String kldbglresult;		// 克罗德办公楼复查结果
	private String kldbglcomment;		// 克罗德办公楼备注
	private String kldbglfile;		// 克罗德办公楼图文附件
	private Integer klddlqualified;		// 克罗德道路1表示合格0表示不合格
	private String klddlsituation;		// 克罗德道路检查情况
	private String klddlcontent;		// 克罗德道路整改内容
	private Date klddldate;		// 克罗德道路整改期限
	private String klddlresult;		// 克罗德道路复查结果
	private String klddlcomment;		// 克罗德道路备注
	private String klddlfile;		// 克罗德道路图文附件
	private Integer dormitoryqualified;		// 宿舍1表示合格0表示不合格
	private String dormitorysituation;		// 宿舍检查情况
	private String dormitorycontent;		// 宿舍整改内容
	private Date dormitorydate;		// 宿舍整改期限
	private String dormitoryresult;		// 宿舍复查结果
	private String dormitorycomment;		// 宿舍备注
	private String dormitoryfile;		// 宿舍图文附件
	private Integer canteenqualified;		// 食堂1表示合格0表示不合格
	private String canteensituation;		// 食堂检查情况
	private String canteencontent;		// 食堂整改内容
	private Date canteendate;		// 食堂整改期限
	private String canteenresult;		// 食堂复查结果
	private String canteencomment;		// 食堂备注
	private String canteenfile;		// 食堂图文附件
	private Integer guardqualified;		// 门卫1表示合格0表示不合格
	private String guardsituation;		// 门卫检查情况
	private String guardcontent;		// 门卫整改内容
	private Date guarddate;		// 门卫整改期限
	private String guardresult;		// 门卫复查结果
	private String guardcomment;		// 门卫备注
	private String guardfile;		// 门卫图文附件
	private Integer otherqualified;		// 其他1表示合格0表示不合格
	private String othersituation;		// 其他检查情况
	private String othercontent;		// 其他整改内容
	private Date otherdate;		// 其他整改期限
	private String otherresult;		// 其他复查结果
	private String othercomment;		// 其他备注
	private String otherfile;		// 其他图文附件
	private Integer zhbglqualified;		// 浙华办公楼1表示合格0表示不合格
	private String zhbglsituation;		// 浙华办公楼检查情况
	private String zhbglcontent;		// 浙华办公楼整改内容
	private Date zhbgldate;		// 浙华办公楼整改期限
	private String zhbglresult;		// 浙华办公楼复查结果
	private String zhbglcomment;		// 浙华办公楼备注
	private String zhbglfile;		// 浙华办公楼图文附件
	private Integer scbgsqualified;		// 生产办公室1表示合格0表示不合格
	private String scbgssituation;		// 生产办公室检查情况
	private String scbgscontent;		// 生产办公室整改内容
	private Date scbgsdate;		// 生产办公室整改期限
	private String scbgsresult;		// 生产办公室复查结果
	private String scbgscomment;		// 生产办公室备注
	private String scbgsfile;		// 生产办公室图文附件
	private Integer zfcjqualified;		// 直缝车间1表示合格0表示不合格
	private String zfcjsituation;		// 直缝车间检查情况
	private String zfcjcontent;		// 直缝车间整改内容
	private Date zfcjdate;		// 直缝车间整改期限
	private String zfcjresult;		// 直缝车间复查结果
	private String zfcjcomment;		// 直缝车间备注
	private String zfcjfile;		// 直缝车间图文附件
	private Integer lxcjqualified;		// 螺旋车间1表示合格0表示不合格
	private String lxcjsituation;		// 螺旋车间检查情况
	private String lxcjcontent;		// 螺旋车间整改内容
	private Date lxcjdate;		// 螺旋车间整改期限
	private String lxcjresult;		// 螺旋车间复查结果
	private String lxcjcomment;		// 螺旋车间备注
	private String lxcjfile;		// 螺旋车间图文附件
	private Integer xhqualified;		// 现货仓库1表示合格0表示不合格
	private String xhsituation;		// 现货仓库检查情况
	private String xhcontent;		// 现货仓库整改内容
	private Date xhdate;		// 现货仓库整改期限
	private String xhresult;		// 现货仓库复查结果
	private String xhcomment;		// 现货仓库备注
	private String xhfile;		// 现货仓库图文附件
	private Integer flqualified;		// 辅料仓库1表示合格0表示不合格
	private String flsituation;		// 辅料仓库检查情况
	private String flcontent;		// 辅料仓库整改内容
	private Date fldate;		// 辅料仓库整改期限
	private String flresult;		// 辅料仓库复查结果
	private String flcomment;		// 辅料仓库备注
	private String flfile;		// 辅料仓库图文附件
	private Integer roadqualified;		// 宁波道路1表示合格0表示不合格
	private String roadsituation;		// 宁波道路检查情况
	private String roadcontent;		// 宁波道路整改内容
	private Date roaddate;		// 宁波道路整改期限
	private String roadresult;		// 宁波道路复查结果
	private String roadcomment;		// 宁波道路备注
	private String roadfile;		// 宁波道路图文附件
	private String summary;		// 总结
	private String leader;		// 组长
	private Date checkdate;		// 检查日期
	private Date createdate;		// 创建日期
	private String checkmember;		// 检查组成员
	private Integer type;		// 0表示常熟1表示宁波
	private String ip;		// ip
	private String cstoiletsituation;		// 常熟厕所检查情况
	private String cstoiletcontent;		// 常熟厕所整改内容
	private Date cstoiletdate;		// 常熟厕所整改期限
	private String cstoiletresult;		// 常熟厕所复查结果
	private String cstoiletcomment;		// 常熟厕所备注
	private String cstoiletfile;		// 常熟厕所图文附件
	private String nbtoiletsituation;		// 宁波厕所检查情况
	private String nbtoiletcontent;		// 宁波厕所整改内容
	private Date nbtoiletdate;		// 宁波厕所整改期限
	private String nbtoiletresult;		// 宁波厕所复查结果
	private String nbtoiletcomment;		// 宁波厕所备注
	private String nbtoiletfile;		// 宁波厕所图文附件
	private Integer cstoiletqualified;		// 常熟厕所1表示合格0表示不合格
	private Integer nbtoiletqualified;		// 宁波厕所1表示合格0表示不合格
	
	public Hygiene() {
		super();
	}

	public Hygiene(String id){
		super(id);
	}

	@ExcelField(title="产区安全检查情况", align=2, sort=6)
	public String getCqaqsituation() {
		return cqaqsituation;
	}

	public void setCqaqsituation(String cqaqsituation) {
		this.cqaqsituation = cqaqsituation;
	}
	
	@ExcelField(title="厂区安全整改内容", align=2, sort=7)
	public String getCqaqcontent() {
		return cqaqcontent;
	}

	public void setCqaqcontent(String cqaqcontent) {
		this.cqaqcontent = cqaqcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="厂区安全整改期限", align=2, sort=8)
	public Date getCqaqdate() {
		return cqaqdate;
	}

	public void setCqaqdate(Date cqaqdate) {
		this.cqaqdate = cqaqdate;
	}
	
	@ExcelField(title="产区安全复查结果", align=2, sort=9)
	public String getCqaqresult() {
		return cqaqresult;
	}

	public void setCqaqresult(String cqaqresult) {
		this.cqaqresult = cqaqresult;
	}
	
	@ExcelField(title="厂区安全备注", align=2, sort=10)
	public String getCqaqcomment() {
		return cqaqcomment;
	}

	public void setCqaqcomment(String cqaqcomment) {
		this.cqaqcomment = cqaqcomment;
	}
	
	@ExcelField(title="厂区安全图文附件", align=2, sort=11)
	public String getCqaqfile() {
		return cqaqfile;
	}

	public void setCqaqfile(String cqaqfile) {
		this.cqaqfile = cqaqfile;
	}
	
	@ExcelField(title="厂区标语检查情况", align=2, sort=12)
	public String getCqbysituation() {
		return cqbysituation;
	}

	public void setCqbysituation(String cqbysituation) {
		this.cqbysituation = cqbysituation;
	}
	
	@ExcelField(title="厂区标语整改内容", align=2, sort=13)
	public String getCqbycontent() {
		return cqbycontent;
	}

	public void setCqbycontent(String cqbycontent) {
		this.cqbycontent = cqbycontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="厂区标语整改期限", align=2, sort=14)
	public Date getCqbydate() {
		return cqbydate;
	}

	public void setCqbydate(Date cqbydate) {
		this.cqbydate = cqbydate;
	}
	
	@ExcelField(title="厂区标语复查结果", align=2, sort=15)
	public String getCqbyresult() {
		return cqbyresult;
	}

	public void setCqbyresult(String cqbyresult) {
		this.cqbyresult = cqbyresult;
	}
	
	@ExcelField(title="厂区标语备注", align=2, sort=16)
	public String getCqbycomment() {
		return cqbycomment;
	}

	public void setCqbycomment(String cqbycomment) {
		this.cqbycomment = cqbycomment;
	}
	
	@ExcelField(title="厂区标语图文附件", align=2, sort=17)
	public String getCqbyfile() {
		return cqbyfile;
	}

	public void setCqbyfile(String cqbyfile) {
		this.cqbyfile = cqbyfile;
	}
	
	@ExcelField(title="设备管理检查情况", align=2, sort=18)
	public String getSbglsituation() {
		return sbglsituation;
	}

	public void setSbglsituation(String sbglsituation) {
		this.sbglsituation = sbglsituation;
	}
	
	@ExcelField(title="设备管理整改内容", align=2, sort=19)
	public String getSbglcontent() {
		return sbglcontent;
	}

	public void setSbglcontent(String sbglcontent) {
		this.sbglcontent = sbglcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="设备管理整改期限", align=2, sort=20)
	public Date getSbgldate() {
		return sbgldate;
	}

	public void setSbgldate(Date sbgldate) {
		this.sbgldate = sbgldate;
	}
	
	@ExcelField(title="设备管理复查结果", align=2, sort=21)
	public String getSbglresult() {
		return sbglresult;
	}

	public void setSbglresult(String sbglresult) {
		this.sbglresult = sbglresult;
	}
	
	@ExcelField(title="设备管理备注", align=2, sort=22)
	public String getSbglcomment() {
		return sbglcomment;
	}

	public void setSbglcomment(String sbglcomment) {
		this.sbglcomment = sbglcomment;
	}
	
	@ExcelField(title="设备管理图文附件", align=2, sort=23)
	public String getSbglfile() {
		return sbglfile;
	}

	public void setSbglfile(String sbglfile) {
		this.sbglfile = sbglfile;
	}
	
	@ExcelField(title="食品管理检查情况", align=2, sort=24)
	public String getSpglsituation() {
		return spglsituation;
	}

	public void setSpglsituation(String spglsituation) {
		this.spglsituation = spglsituation;
	}
	
	@ExcelField(title="食品管理整改内容", align=2, sort=25)
	public String getSpglcontent() {
		return spglcontent;
	}

	public void setSpglcontent(String spglcontent) {
		this.spglcontent = spglcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="食品管理整改期限", align=2, sort=26)
	public Date getSpgldate() {
		return spgldate;
	}

	public void setSpgldate(Date spgldate) {
		this.spgldate = spgldate;
	}
	
	@ExcelField(title="食品管理复查结果", align=2, sort=27)
	public String getSpglresult() {
		return spglresult;
	}

	public void setSpglresult(String spglresult) {
		this.spglresult = spglresult;
	}
	
	@ExcelField(title="食品管理备注", align=2, sort=28)
	public String getSpglcomment() {
		return spglcomment;
	}

	public void setSpglcomment(String spglcomment) {
		this.spglcomment = spglcomment;
	}
	
	@ExcelField(title="食品管理图文附件", align=2, sort=29)
	public String getSpglfile() {
		return spglfile;
	}

	public void setSpglfile(String spglfile) {
		this.spglfile = spglfile;
	}
	
	@ExcelField(title="员工着装检查情况", align=2, sort=30)
	public String getYgzzsituation() {
		return ygzzsituation;
	}

	public void setYgzzsituation(String ygzzsituation) {
		this.ygzzsituation = ygzzsituation;
	}
	
	@ExcelField(title="员工着装整改内容", align=2, sort=31)
	public String getYgzzcontent() {
		return ygzzcontent;
	}

	public void setYgzzcontent(String ygzzcontent) {
		this.ygzzcontent = ygzzcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="员工着装整改期限", align=2, sort=32)
	public Date getYgzzdate() {
		return ygzzdate;
	}

	public void setYgzzdate(Date ygzzdate) {
		this.ygzzdate = ygzzdate;
	}
	
	@ExcelField(title="员工着装复查结果", align=2, sort=33)
	public String getYgzzresult() {
		return ygzzresult;
	}

	public void setYgzzresult(String ygzzresult) {
		this.ygzzresult = ygzzresult;
	}
	
	@ExcelField(title="员工着装备注", align=2, sort=34)
	public String getYgzzcomment() {
		return ygzzcomment;
	}

	public void setYgzzcomment(String ygzzcomment) {
		this.ygzzcomment = ygzzcomment;
	}
	
	@ExcelField(title="员工着装图文附件", align=2, sort=35)
	public String getYgzzfile() {
		return ygzzfile;
	}

	public void setYgzzfile(String ygzzfile) {
		this.ygzzfile = ygzzfile;
	}
	
	@ExcelField(title="it信息管理检查情况", align=2, sort=36)
	public String getItsituation() {
		return itsituation;
	}

	public void setItsituation(String itsituation) {
		this.itsituation = itsituation;
	}
	
	@ExcelField(title="it信息管理整改内容", align=2, sort=37)
	public String getItcontent() {
		return itcontent;
	}

	public void setItcontent(String itcontent) {
		this.itcontent = itcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="it信息管理整改期限", align=2, sort=38)
	public Date getItdate() {
		return itdate;
	}

	public void setItdate(Date itdate) {
		this.itdate = itdate;
	}
	
	@ExcelField(title="it信息管理复查结果", align=2, sort=39)
	public String getItresult() {
		return itresult;
	}

	public void setItresult(String itresult) {
		this.itresult = itresult;
	}
	
	@ExcelField(title="it信息管理备注", align=2, sort=40)
	public String getItcomment() {
		return itcomment;
	}

	public void setItcomment(String itcomment) {
		this.itcomment = itcomment;
	}
	
	@ExcelField(title="it信息管理图文附件", align=2, sort=41)
	public String getItfile() {
		return itfile;
	}

	public void setItfile(String itfile) {
		this.itfile = itfile;
	}
	
	@ExcelField(title="产品验收检查情况", align=2, sort=42)
	public String getCpyssituation() {
		return cpyssituation;
	}

	public void setCpyssituation(String cpyssituation) {
		this.cpyssituation = cpyssituation;
	}
	
	@ExcelField(title="产品验收整改内容", align=2, sort=43)
	public String getCpyscontent() {
		return cpyscontent;
	}

	public void setCpyscontent(String cpyscontent) {
		this.cpyscontent = cpyscontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="产品验收整改期限", align=2, sort=44)
	public Date getCpysdate() {
		return cpysdate;
	}

	public void setCpysdate(Date cpysdate) {
		this.cpysdate = cpysdate;
	}
	
	@ExcelField(title="产品验收复查结果", align=2, sort=45)
	public String getCpysresult() {
		return cpysresult;
	}

	public void setCpysresult(String cpysresult) {
		this.cpysresult = cpysresult;
	}
	
	@ExcelField(title="产品验收备注", align=2, sort=46)
	public String getCpyscomment() {
		return cpyscomment;
	}

	public void setCpyscomment(String cpyscomment) {
		this.cpyscomment = cpyscomment;
	}
	
	@ExcelField(title="产品验收图文附件", align=2, sort=47)
	public String getCpysfile() {
		return cpysfile;
	}

	public void setCpysfile(String cpysfile) {
		this.cpysfile = cpysfile;
	}
	
	@ExcelField(title="交接班检查情况", align=2, sort=48)
	public String getJjbsituation() {
		return jjbsituation;
	}

	public void setJjbsituation(String jjbsituation) {
		this.jjbsituation = jjbsituation;
	}
	
	@ExcelField(title="交接班整改内容", align=2, sort=49)
	public String getJjbcontent() {
		return jjbcontent;
	}

	public void setJjbcontent(String jjbcontent) {
		this.jjbcontent = jjbcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="交接班整改期限", align=2, sort=50)
	public Date getJjbdate() {
		return jjbdate;
	}

	public void setJjbdate(Date jjbdate) {
		this.jjbdate = jjbdate;
	}
	
	@ExcelField(title="交接班复查结果", align=2, sort=51)
	public String getJjbresult() {
		return jjbresult;
	}

	public void setJjbresult(String jjbresult) {
		this.jjbresult = jjbresult;
	}
	
	@ExcelField(title="交接班备注", align=2, sort=52)
	public String getJjbcomment() {
		return jjbcomment;
	}

	public void setJjbcomment(String jjbcomment) {
		this.jjbcomment = jjbcomment;
	}
	
	@ExcelField(title="交接班图文附件", align=2, sort=53)
	public String getJjbfile() {
		return jjbfile;
	}

	public void setJjbfile(String jjbfile) {
		this.jjbfile = jjbfile;
	}
	
	@ExcelField(title="生产作业检查情况", align=2, sort=54)
	public String getSczysituation() {
		return sczysituation;
	}

	public void setSczysituation(String sczysituation) {
		this.sczysituation = sczysituation;
	}
	
	@ExcelField(title="生产作业整改内容", align=2, sort=55)
	public String getSczycontent() {
		return sczycontent;
	}

	public void setSczycontent(String sczycontent) {
		this.sczycontent = sczycontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="生产作业整改期限", align=2, sort=56)
	public Date getSczydate() {
		return sczydate;
	}

	public void setSczydate(Date sczydate) {
		this.sczydate = sczydate;
	}
	
	@ExcelField(title="生产作业复查结果", align=2, sort=57)
	public String getSczyresult() {
		return sczyresult;
	}

	public void setSczyresult(String sczyresult) {
		this.sczyresult = sczyresult;
	}
	
	@ExcelField(title="生产作业备注", align=2, sort=58)
	public String getSczycomment() {
		return sczycomment;
	}

	public void setSczycomment(String sczycomment) {
		this.sczycomment = sczycomment;
	}
	
	@ExcelField(title="生产作业图文附件", align=2, sort=59)
	public String getSczyfile() {
		return sczyfile;
	}

	public void setSczyfile(String sczyfile) {
		this.sczyfile = sczyfile;
	}
	
	@ExcelField(title="收发业务检查情况", align=2, sort=60)
	public String getSfywsituation() {
		return sfywsituation;
	}

	public void setSfywsituation(String sfywsituation) {
		this.sfywsituation = sfywsituation;
	}
	
	@ExcelField(title="收发业务整改内容", align=2, sort=61)
	public String getSfywcontent() {
		return sfywcontent;
	}

	public void setSfywcontent(String sfywcontent) {
		this.sfywcontent = sfywcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="收发业务整改期限", align=2, sort=62)
	public Date getSfywdate() {
		return sfywdate;
	}

	public void setSfywdate(Date sfywdate) {
		this.sfywdate = sfywdate;
	}
	
	@ExcelField(title="收发业务复查结果", align=2, sort=63)
	public String getSfywresult() {
		return sfywresult;
	}

	public void setSfywresult(String sfywresult) {
		this.sfywresult = sfywresult;
	}
	
	@ExcelField(title="收发业务备注", align=2, sort=64)
	public String getSfywcomment() {
		return sfywcomment;
	}

	public void setSfywcomment(String sfywcomment) {
		this.sfywcomment = sfywcomment;
	}
	
	@ExcelField(title="收发业务图文附件", align=2, sort=65)
	public String getSfywfile() {
		return sfywfile;
	}

	public void setSfywfile(String sfywfile) {
		this.sfywfile = sfywfile;
	}
	
	@ExcelField(title="车辆管理检查情况", align=2, sort=66)
	public String getClglsituation() {
		return clglsituation;
	}

	public void setClglsituation(String clglsituation) {
		this.clglsituation = clglsituation;
	}
	
	@ExcelField(title="车辆管理整改内容", align=2, sort=67)
	public String getClglcontent() {
		return clglcontent;
	}

	public void setClglcontent(String clglcontent) {
		this.clglcontent = clglcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="车辆管理整改期限", align=2, sort=68)
	public Date getClgldate() {
		return clgldate;
	}

	public void setClgldate(Date clgldate) {
		this.clgldate = clgldate;
	}
	
	@ExcelField(title="车辆管理复查结果", align=2, sort=69)
	public String getClglresult() {
		return clglresult;
	}

	public void setClglresult(String clglresult) {
		this.clglresult = clglresult;
	}
	
	@ExcelField(title="车辆管理备注", align=2, sort=70)
	public String getClglcomment() {
		return clglcomment;
	}

	public void setClglcomment(String clglcomment) {
		this.clglcomment = clglcomment;
	}
	
	@ExcelField(title="车辆管理图文附件", align=2, sort=71)
	public String getClglfile() {
		return clglfile;
	}

	public void setClglfile(String clglfile) {
		this.clglfile = clglfile;
	}
	
	@ExcelField(title="外来人员检查情况", align=2, sort=72)
	public String getWlrysituation() {
		return wlrysituation;
	}

	public void setWlrysituation(String wlrysituation) {
		this.wlrysituation = wlrysituation;
	}
	
	@ExcelField(title="外来人员整改内容", align=2, sort=73)
	public String getWlrycontent() {
		return wlrycontent;
	}

	public void setWlrycontent(String wlrycontent) {
		this.wlrycontent = wlrycontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="外来人员整改期限", align=2, sort=74)
	public Date getWlrydate() {
		return wlrydate;
	}

	public void setWlrydate(Date wlrydate) {
		this.wlrydate = wlrydate;
	}
	
	@ExcelField(title="外来人员复查结果", align=2, sort=75)
	public String getWlryresult() {
		return wlryresult;
	}

	public void setWlryresult(String wlryresult) {
		this.wlryresult = wlryresult;
	}
	
	@ExcelField(title="外来人员备注", align=2, sort=76)
	public String getWlrycomment() {
		return wlrycomment;
	}

	public void setWlrycomment(String wlrycomment) {
		this.wlrycomment = wlrycomment;
	}
	
	@ExcelField(title="外来人员图文附件", align=2, sort=77)
	public String getWlryfile() {
		return wlryfile;
	}

	public void setWlryfile(String wlryfile) {
		this.wlryfile = wlryfile;
	}
	
	@ExcelField(title="人文精神检查情况", align=2, sort=78)
	public String getRwjssituation() {
		return rwjssituation;
	}

	public void setRwjssituation(String rwjssituation) {
		this.rwjssituation = rwjssituation;
	}
	
	@ExcelField(title="人文精神整改内容", align=2, sort=79)
	public String getRwjscontent() {
		return rwjscontent;
	}

	public void setRwjscontent(String rwjscontent) {
		this.rwjscontent = rwjscontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="人文精神整改期限", align=2, sort=80)
	public Date getRwjsdate() {
		return rwjsdate;
	}

	public void setRwjsdate(Date rwjsdate) {
		this.rwjsdate = rwjsdate;
	}
	
	@ExcelField(title="人文精神复查结果", align=2, sort=81)
	public String getRwjsresult() {
		return rwjsresult;
	}

	public void setRwjsresult(String rwjsresult) {
		this.rwjsresult = rwjsresult;
	}
	
	@ExcelField(title="人文精神备注", align=2, sort=82)
	public String getRwjscomment() {
		return rwjscomment;
	}

	public void setRwjscomment(String rwjscomment) {
		this.rwjscomment = rwjscomment;
	}
	
	@ExcelField(title="人文精神图文附件", align=2, sort=83)
	public String getRwjsfile() {
		return rwjsfile;
	}

	public void setRwjsfile(String rwjsfile) {
		this.rwjsfile = rwjsfile;
	}
	
	@ExcelField(title="薄板镀锌1表示合格0表示不合格", align=2, sort=84)
	public Integer getBbdxqualified() {
		return bbdxqualified;
	}

	public void setBbdxqualified(Integer bbdxqualified) {
		this.bbdxqualified = bbdxqualified;
	}
	
	@ExcelField(title="薄板镀锌检查情况", align=2, sort=85)
	public String getBbdxsituation() {
		return bbdxsituation;
	}

	public void setBbdxsituation(String bbdxsituation) {
		this.bbdxsituation = bbdxsituation;
	}
	
	@ExcelField(title="薄板镀锌整改内容", align=2, sort=86)
	public String getBbdxcontent() {
		return bbdxcontent;
	}

	public void setBbdxcontent(String bbdxcontent) {
		this.bbdxcontent = bbdxcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="薄板镀锌整改期限", align=2, sort=87)
	public Date getBbdxdate() {
		return bbdxdate;
	}

	public void setBbdxdate(Date bbdxdate) {
		this.bbdxdate = bbdxdate;
	}
	
	@ExcelField(title="薄板镀锌复查结果", align=2, sort=88)
	public String getBbdxresult() {
		return bbdxresult;
	}

	public void setBbdxresult(String bbdxresult) {
		this.bbdxresult = bbdxresult;
	}
	
	@ExcelField(title="薄板镀锌备注", align=2, sort=89)
	public String getBbdxcomment() {
		return bbdxcomment;
	}

	public void setBbdxcomment(String bbdxcomment) {
		this.bbdxcomment = bbdxcomment;
	}
	
	@ExcelField(title="薄板镀锌图文附件", align=2, sort=90)
	public String getBbdxfile() {
		return bbdxfile;
	}

	public void setBbdxfile(String bbdxfile) {
		this.bbdxfile = bbdxfile;
	}
	
	@ExcelField(title="薄板彩涂1表示合格0表示不合格", align=2, sort=91)
	public Integer getBbctqualified() {
		return bbctqualified;
	}

	public void setBbctqualified(Integer bbctqualified) {
		this.bbctqualified = bbctqualified;
	}
	
	@ExcelField(title="薄板彩涂检查情况", align=2, sort=92)
	public String getBbctsituation() {
		return bbctsituation;
	}

	public void setBbctsituation(String bbctsituation) {
		this.bbctsituation = bbctsituation;
	}
	
	@ExcelField(title="薄板彩涂整改内容", align=2, sort=93)
	public String getBbctcontent() {
		return bbctcontent;
	}

	public void setBbctcontent(String bbctcontent) {
		this.bbctcontent = bbctcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="薄板彩涂整改期限", align=2, sort=94)
	public Date getBbctdate() {
		return bbctdate;
	}

	public void setBbctdate(Date bbctdate) {
		this.bbctdate = bbctdate;
	}
	
	@ExcelField(title="薄板彩涂复查结果", align=2, sort=95)
	public String getBbctresult() {
		return bbctresult;
	}

	public void setBbctresult(String bbctresult) {
		this.bbctresult = bbctresult;
	}
	
	@ExcelField(title="薄板彩涂备注", align=2, sort=96)
	public String getBbctcomment() {
		return bbctcomment;
	}

	public void setBbctcomment(String bbctcomment) {
		this.bbctcomment = bbctcomment;
	}
	
	@ExcelField(title="薄板彩涂图文附件", align=2, sort=97)
	public String getBbctfile() {
		return bbctfile;
	}

	public void setBbctfile(String bbctfile) {
		this.bbctfile = bbctfile;
	}
	
	
	@ExcelField(title="薄板公铺1表示合格0表示不合格", align=2, sort=98)
	public Integer getBbgpqualified() {
		return bbgpqualified;
	}

	public void setBbgpqualified(Integer bbgpqualified) {
		this.bbgpqualified = bbgpqualified;
	}
	
	@ExcelField(title="薄板公铺检查情况", align=2, sort=99)
	public String getBbgpsituation() {
		return bbgpsituation;
	}

	public void setBbgpsituation(String bbgpsituation) {
		this.bbgpsituation = bbgpsituation;
	}
	
	@ExcelField(title="薄板公铺整改内容", align=2, sort=100)
	public String getBbgpcontent() {
		return bbgpcontent;
	}

	public void setBbgpcontent(String bbgpcontent) {
		this.bbgpcontent = bbgpcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板公铺整改期限", align=2, sort=101)
	public Date getBbgpdate() {
		return bbgpdate;
	}

	public void setBbgpdate(Date bbgpdate) {
		this.bbgpdate = bbgpdate;
	}
	
	@ExcelField(title="薄板公铺复查结果", align=2, sort=102)
	public String getBbgpresult() {
		return bbgpresult;
	}

	public void setBbgpresult(String bbgpresult) {
		this.bbgpresult = bbgpresult;
	}
	
	@ExcelField(title="薄板公铺备注", align=2, sort=103)
	public String getBbgpcomment() {
		return bbgpcomment;
	}

	public void setBbgpcomment(String bbgpcomment) {
		this.bbgpcomment = bbgpcomment;
	}
	
	@ExcelField(title="薄板公铺图文附件", align=2, sort=104)
	public String getBbgpfile() {
		return bbgpfile;
	}

	public void setBbgpfile(String bbgpfile) {
		this.bbgpfile = bbgpfile;
	}
	
	
	@ExcelField(title="薄板大仓库1表示合格0表示不合格", align=2, sort=105)
	public Integer getBbdckqualified() {
		return bbdckqualified;
	}

	public void setBbdckqualified(Integer bbdckqualified) {
		this.bbdckqualified = bbdckqualified;
	}
	
	@ExcelField(title="薄板大仓库检查情况", align=2, sort=106)
	public String getBbdcksituation() {
		return bbdcksituation;
	}

	public void setBbdcksituation(String bbdcksituation) {
		this.bbdcksituation = bbdcksituation;
	}
	
	@ExcelField(title="薄板大仓库整改内容", align=2, sort=107)
	public String getBbdckcontent() {
		return bbdckcontent;
	}

	public void setBbdckcontent(String bbdckcontent) {
		this.bbdckcontent = bbdckcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板大仓库整改期限", align=2, sort=108)
	public Date getBbdckdate() {
		return bbdckdate;
	}

	public void setBbdckdate(Date bbdckdate) {
		this.bbdckdate = bbdckdate;
	}
	
	@ExcelField(title="薄板大仓库复查结果", align=2, sort=109)
	public String getBbdckresult() {
		return bbdckresult;
	}

	public void setBbdckresult(String bbdckresult) {
		this.bbdckresult = bbdckresult;
	}
	
	@ExcelField(title="薄板大仓库备注", align=2, sort=110)
	public String getBbdckcomment() {
		return bbdckcomment;
	}

	public void setBbdckcomment(String bbdckcomment) {
		this.bbdckcomment = bbdckcomment;
	}
	
	@ExcelField(title="薄板大仓库图文附件", align=2, sort=111)
	public String getBbdckfile() {
		return bbdckfile;
	}

	public void setBbdckfile(String bbdckfile) {
		this.bbdckfile = bbdckfile;
	}
	
	
	@ExcelField(title="薄板辅料1表示合格0表示不合格", align=2, sort=112)
	public Integer getBbflqualified() {
		return bbflqualified;
	}

	public void setBbflqualified(Integer bbflqualified) {
		this.bbflqualified = bbflqualified;
	}
	
	@ExcelField(title="薄板辅料检查情况", align=2, sort=113)
	public String getBbflsituation() {
		return bbflsituation;
	}

	public void setBbflsituation(String bbflsituation) {
		this.bbflsituation = bbflsituation;
	}
	
	@ExcelField(title="薄板辅料整改内容", align=2, sort=114)
	public String getBbflcontent() {
		return bbflcontent;
	}

	public void setBbflcontent(String bbflcontent) {
		this.bbflcontent = bbflcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板辅料整改期限", align=2, sort=115)
	public Date getBbfldate() {
		return bbfldate;
	}

	public void setBbfldate(Date bbfldate) {
		this.bbfldate = bbfldate;
	}
	
	@ExcelField(title="薄板辅料复查结果", align=2, sort=116)
	public String getBbflresult() {
		return bbflresult;
	}

	public void setBbflresult(String bbflresult) {
		this.bbflresult = bbflresult;
	}
	
	@ExcelField(title="薄板辅料备注", align=2, sort=117)
	public String getBbflcomment() {
		return bbflcomment;
	}

	public void setBbflcomment(String bbflcomment) {
		this.bbflcomment = bbflcomment;
	}
	
	@ExcelField(title="薄板辅料图文附件", align=2, sort=118)
	public String getBbflfile() {
		return bbflfile;
	}

	public void setBbflfile(String bbflfile) {
		this.bbflfile = bbflfile;
	}
	
	
	@ExcelField(title="薄板废料1表示合格0表示不合格", align=2, sort=119)
	public Integer getBbfeilqualified() {
		return bbfeilqualified;
	}

	public void setBbfeilqualified(Integer bbfeilqualified) {
		this.bbfeilqualified = bbfeilqualified;
	}
	
	@ExcelField(title="薄板废料检查情况", align=2, sort=120)
	public String getBbfeilsituation() {
		return bbfeilsituation;
	}

	public void setBbfeilsituation(String bbfeilsituation) {
		this.bbfeilsituation = bbfeilsituation;
	}
	
	@ExcelField(title="薄板废料整改内容", align=2, sort=121)
	public String getBbfeilcontent() {
		return bbfeilcontent;
	}

	public void setBbfeilcontent(String bbfeilcontent) {
		this.bbfeilcontent = bbfeilcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板废料整改期限", align=2, sort=122)
	public Date getBbfeildate() {
		return bbfeildate;
	}

	public void setBbfeildate(Date bbfeildate) {
		this.bbfeildate = bbfeildate;
	}
	
	@ExcelField(title="薄板废料复查结果", align=2, sort=123)
	public String getBbfeilresult() {
		return bbfeilresult;
	}

	public void setBbfeilresult(String bbfeilresult) {
		this.bbfeilresult = bbfeilresult;
	}
	
	@ExcelField(title="薄板废料备注", align=2, sort=124)
	public String getBbfeilcomment() {
		return bbfeilcomment;
	}

	public void setBbfeilcomment(String bbfeilcomment) {
		this.bbfeilcomment = bbfeilcomment;
	}
	
	@ExcelField(title="薄板废料图文附件", align=2, sort=125)
	public String getBbfeilfile() {
		return bbfeilfile;
	}

	public void setBbfeilfile(String bbfeilfile) {
		this.bbfeilfile = bbfeilfile;
	}
	
	
	@ExcelField(title="薄板办公楼1表示合格0表示不合格", align=2, sort=126)
	public Integer getBbbglqualified() {
		return bbbglqualified;
	}

	public void setBbbglqualified(Integer bbbglqualified) {
		this.bbbglqualified = bbbglqualified;
	}
	
	@ExcelField(title="薄板办公楼检查情况", align=2, sort=127)
	public String getBbbglsituation() {
		return bbbglsituation;
	}

	public void setBbbglsituation(String bbbglsituation) {
		this.bbbglsituation = bbbglsituation;
	}
	
	@ExcelField(title="薄板办公楼整改内容", align=2, sort=128)
	public String getBbbglcontent() {
		return bbbglcontent;
	}

	public void setBbbglcontent(String bbbglcontent) {
		this.bbbglcontent = bbbglcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板办公楼整改期限", align=2, sort=129)
	public Date getBbbgldate() {
		return bbbgldate;
	}

	public void setBbbgldate(Date bbbgldate) {
		this.bbbgldate = bbbgldate;
	}
	
	@ExcelField(title="薄板办公楼复查结果", align=2, sort=130)
	public String getBbbglresult() {
		return bbbglresult;
	}

	public void setBbbglresult(String bbbglresult) {
		this.bbbglresult = bbbglresult;
	}
	
	@ExcelField(title="薄板办公楼备注", align=2, sort=131)
	public String getBbbglcomment() {
		return bbbglcomment;
	}

	public void setBbbglcomment(String bbbglcomment) {
		this.bbbglcomment = bbbglcomment;
	}
	
	@ExcelField(title="薄板办公楼图文附件", align=2, sort=132)
	public String getBbbglfile() {
		return bbbglfile;
	}

	public void setBbbglfile(String bbbglfile) {
		this.bbbglfile = bbbglfile;
	}
	
	
	@ExcelField(title="薄板楼道1表示合格0表示不合格", align=2, sort=133)
	public Integer getBbldqualified() {
		return bbldqualified;
	}

	public void setBbldqualified(Integer bbldqualified) {
		this.bbldqualified = bbldqualified;
	}
	
	@ExcelField(title="薄板楼道检查情况", align=2, sort=134)
	public String getBbldsituation() {
		return bbldsituation;
	}

	public void setBbldsituation(String bbldsituation) {
		this.bbldsituation = bbldsituation;
	}
	
	@ExcelField(title="薄板楼道整改内容", align=2, sort=135)
	public String getBbldcontent() {
		return bbldcontent;
	}

	public void setBbldcontent(String bbldcontent) {
		this.bbldcontent = bbldcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板楼道整改期限", align=2, sort=136)
	public Date getBblddate() {
		return bblddate;
	}

	public void setBblddate(Date bblddate) {
		this.bblddate = bblddate;
	}
	
	@ExcelField(title="薄板楼道复查结果", align=2, sort=137)
	public String getBbldresult() {
		return bbldresult;
	}

	public void setBbldresult(String bbldresult) {
		this.bbldresult = bbldresult;
	}
	
	@ExcelField(title="薄板楼道备注", align=2, sort=138)
	public String getBbldcomment() {
		return bbldcomment;
	}

	public void setBbldcomment(String bbldcomment) {
		this.bbldcomment = bbldcomment;
	}
	
	@ExcelField(title="薄板楼道图文附件", align=2, sort=139)
	public String getBbldfile() {
		return bbldfile;
	}

	public void setBbldfile(String bbldfile) {
		this.bbldfile = bbldfile;
	}
	
	
	@ExcelField(title="薄板酸洗1表示合格0表示不合格", align=2, sort=140)
	public Integer getBbsxqualified() {
		return bbsxqualified;
	}

	public void setBbsxqualified(Integer bbsxqualified) {
		this.bbsxqualified = bbsxqualified;
	}
	
	@ExcelField(title="薄板酸洗检查情况", align=2, sort=141)
	public String getBbsxsituation() {
		return bbsxsituation;
	}

	public void setBbsxsituation(String bbsxsituation) {
		this.bbsxsituation = bbsxsituation;
	}
	
	@ExcelField(title="薄板酸洗整改内容", align=2, sort=142)
	public String getBbsxcontent() {
		return bbsxcontent;
	}

	public void setBbsxcontent(String bbsxcontent) {
		this.bbsxcontent = bbsxcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板酸洗整改期限", align=2, sort=143)
	public Date getBbsxdate() {
		return bbsxdate;
	}

	public void setBbsxdate(Date bbsxdate) {
		this.bbsxdate = bbsxdate;
	}
	
	@ExcelField(title="薄板酸洗复查结果", align=2, sort=144)
	public String getBbsxresult() {
		return bbsxresult;
	}

	public void setBbsxresult(String bbsxresult) {
		this.bbsxresult = bbsxresult;
	}
	
	@ExcelField(title="薄板酸洗备注", align=2, sort=145)
	public String getBbsxcomment() {
		return bbsxcomment;
	}

	public void setBbsxcomment(String bbsxcomment) {
		this.bbsxcomment = bbsxcomment;
	}
	
	@ExcelField(title="薄板酸洗图文附件", align=2, sort=146)
	public String getBbsxfile() {
		return bbsxfile;
	}

	public void setBbsxfile(String bbsxfile) {
		this.bbsxfile = bbsxfile;
	}
	
	
	@ExcelField(title="薄板1号轧机1表示合格0表示不合格", align=2, sort=147)
	public Integer getBbyhzjqualified() {
		return bbyhzjqualified;
	}

	public void setBbyhzjqualified(Integer bbyhzjqualified) {
		this.bbyhzjqualified = bbyhzjqualified;
	}
	
	@ExcelField(title="薄板1号轧机检查情况", align=2, sort=148)
	public String getBbyhzjsituation() {
		return bbyhzjsituation;
	}

	public void setBbyhzjsituation(String bbyhzjsituation) {
		this.bbyhzjsituation = bbyhzjsituation;
	}
	
	@ExcelField(title="薄板1号轧机整改内容", align=2, sort=149)
	public String getBbyhzjcontent() {
		return bbyhzjcontent;
	}

	public void setBbyhzjcontent(String bbyhzjcontent) {
		this.bbyhzjcontent = bbyhzjcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板1号轧机整改期限", align=2, sort=150)
	public Date getBbyhzjdate() {
		return bbyhzjdate;
	}

	public void setBbyhzjdate(Date bbyhzjdate) {
		this.bbyhzjdate = bbyhzjdate;
	}
	
	@ExcelField(title="薄板1号轧机复查结果", align=2, sort=151)
	public String getBbyhzjresult() {
		return bbyhzjresult;
	}

	public void setBbyhzjresult(String bbyhzjresult) {
		this.bbyhzjresult = bbyhzjresult;
	}
	
	@ExcelField(title="薄板1号轧机备注", align=2, sort=152)
	public String getBbyhzjcomment() {
		return bbyhzjcomment;
	}

	public void setBbyhzjcomment(String bbyhzjcomment) {
		this.bbyhzjcomment = bbyhzjcomment;
	}
	
	@ExcelField(title="薄板1号轧机图文附件", align=2, sort=153)
	public String getBbyhzjfile() {
		return bbyhzjfile;
	}

	public void setBbyhzjfile(String bbyhzjfile) {
		this.bbyhzjfile = bbyhzjfile;
	}
	
	
	@ExcelField(title="薄板2号轧机1表示合格0表示不合格", align=2, sort=154)
	public Integer getBbehzjqualified() {
		return bbehzjqualified;
	}

	public void setBbehzjqualified(Integer bbehzjqualified) {
		this.bbehzjqualified = bbehzjqualified;
	}
	
	@ExcelField(title="薄板2号轧机检查情况", align=2, sort=155)
	public String getBbehzjsituation() {
		return bbehzjsituation;
	}

	public void setBbehzjsituation(String bbehzjsituation) {
		this.bbehzjsituation = bbehzjsituation;
	}
	
	@ExcelField(title="薄板2号轧机整改内容", align=2, sort=156)
	public String getBbehzjcontent() {
		return bbehzjcontent;
	}

	public void setBbehzjcontent(String bbehzjcontent) {
		this.bbehzjcontent = bbehzjcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板2号轧机整改期限", align=2, sort=157)
	public Date getBbehzjdate() {
		return bbehzjdate;
	}

	public void setBbehzjdate(Date bbehzjdate) {
		this.bbehzjdate = bbehzjdate;
	}
	
	@ExcelField(title="薄板2号轧机复查结果", align=2, sort=158)
	public String getBbehzjresult() {
		return bbehzjresult;
	}

	public void setBbehzjresult(String bbehzjresult) {
		this.bbehzjresult = bbehzjresult;
	}
	
	@ExcelField(title="薄板2号轧机备注", align=2, sort=159)
	public String getBbehzjcomment() {
		return bbehzjcomment;
	}

	public void setBbehzjcomment(String bbehzjcomment) {
		this.bbehzjcomment = bbehzjcomment;
	}
	
	@ExcelField(title="薄板2号轧机图文附件", align=2, sort=160)
	public String getBbehzjfile() {
		return bbehzjfile;
	}

	public void setBbehzjfile(String bbehzjfile) {
		this.bbehzjfile = bbehzjfile;
	}
	
	
	@ExcelField(title="薄板15万吨镀锌", align=2, sort=161)
	public Integer getBbywdxqualified() {
		return bbywdxqualified;
	}

	public void setBbywdxqualified(Integer bbywdxqualified) {
		this.bbywdxqualified = bbywdxqualified;
	}
	
	@ExcelField(title="薄板15万吨镀锌检查情况", align=2, sort=162)
	public String getBbywdxsituation() {
		return bbywdxsituation;
	}

	public void setBbywdxsituation(String bbywdxsituation) {
		this.bbywdxsituation = bbywdxsituation;
	}
	
	@ExcelField(title="薄板15万吨镀锌整改内容", align=2, sort=163)
	public String getBbywdxcontent() {
		return bbywdxcontent;
	}

	public void setBbywdxcontent(String bbywdxcontent) {
		this.bbywdxcontent = bbywdxcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板15万吨镀锌整改期限", align=2, sort=164)
	public Date getBbywdxdate() {
		return bbywdxdate;
	}

	public void setBbywdxdate(Date bbywdxdate) {
		this.bbywdxdate = bbywdxdate;
	}
	
	@ExcelField(title="薄板15万吨镀锌复查结果", align=2, sort=165)
	public String getBbywdxresult() {
		return bbywdxresult;
	}

	public void setBbywdxresult(String bbywdxresult) {
		this.bbywdxresult = bbywdxresult;
	}
	
	@ExcelField(title="薄板15万吨镀锌备注", align=2, sort=166)
	public String getBbywdxcomment() {
		return bbywdxcomment;
	}

	public void setBbywdxcomment(String bbywdxcomment) {
		this.bbywdxcomment = bbywdxcomment;
	}
	
	@ExcelField(title="薄板15万吨镀锌图文附件", align=2, sort=167)
	public String getBbywdxfile() {
		return bbywdxfile;
	}

	public void setBbywdxfile(String bbywdxfile) {
		this.bbywdxfile = bbywdxfile;
	}
	
	
	@ExcelField(title="薄板25万吨镀锌", align=2, sort=168)
	public Integer getBbewdxqualified() {
		return bbewdxqualified;
	}

	public void setBbewdxqualified(Integer bbewdxqualified) {
		this.bbewdxqualified = bbewdxqualified;
	}
	
	@ExcelField(title="薄板25万吨镀锌检查情况", align=2, sort=169)
	public String getBbewdxsituation() {
		return bbewdxsituation;
	}

	public void setBbewdxsituation(String bbewdxsituation) {
		this.bbewdxsituation = bbewdxsituation;
	}
	
	@ExcelField(title="薄板25万吨镀锌整改内容", align=2, sort=170)
	public String getBbewdxcontent() {
		return bbewdxcontent;
	}

	public void setBbewdxcontent(String bbewdxcontent) {
		this.bbewdxcontent = bbewdxcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="薄板25万吨镀锌整改期限", align=2, sort=171)
	public Date getBbewdxdate() {
		return bbewdxdate;
	}

	public void setBbewdxdate(Date bbewdxdate) {
		this.bbewdxdate = bbewdxdate;
	}
	
	@ExcelField(title="薄板25万吨镀锌复查结果", align=2, sort=172)
	public String getBbewdxresult() {
		return bbewdxresult;
	}

	public void setBbewdxresult(String bbewdxresult) {
		this.bbewdxresult = bbewdxresult;
	}
	
	@ExcelField(title="薄板25万吨镀锌备注", align=2, sort=173)
	public String getBbewdxcomment() {
		return bbewdxcomment;
	}

	public void setBbewdxcomment(String bbewdxcomment) {
		this.bbewdxcomment = bbewdxcomment;
	}
	
	@ExcelField(title="薄板25万吨镀锌图文附件", align=2, sort=174)
	public String getBbewdxfile() {
		return bbewdxfile;
	}

	public void setBbewdxfile(String bbewdxfile) {
		this.bbewdxfile = bbewdxfile;
	}
	
	
	@ExcelField(title="克罗德公铺1表示合格0表示不合格", align=2, sort=175)
	public Integer getKldgpqualified() {
		return kldgpqualified;
	}

	public void setKldgpqualified(Integer kldgpqualified) {
		this.kldgpqualified = kldgpqualified;
	}
	
	@ExcelField(title="克罗德公铺检查情况", align=2, sort=176)
	public String getKldgpsituation() {
		return kldgpsituation;
	}

	public void setKldgpsituation(String kldgpsituation) {
		this.kldgpsituation = kldgpsituation;
	}
	
	@ExcelField(title="克罗德公铺整改内容", align=2, sort=177)
	public String getKldgpcontent() {
		return kldgpcontent;
	}

	public void setKldgpcontent(String kldgpcontent) {
		this.kldgpcontent = kldgpcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="克罗德公铺整改期限", align=2, sort=178)
	public Date getKldgpdate() {
		return kldgpdate;
	}

	public void setKldgpdate(Date kldgpdate) {
		this.kldgpdate = kldgpdate;
	}
	
	@ExcelField(title="克罗德公铺复查结果", align=2, sort=179)
	public String getKldgpresult() {
		return kldgpresult;
	}

	public void setKldgpresult(String kldgpresult) {
		this.kldgpresult = kldgpresult;
	}
	
	@ExcelField(title="克罗德公铺备注", align=2, sort=180)
	public String getKldgpcomment() {
		return kldgpcomment;
	}

	public void setKldgpcomment(String kldgpcomment) {
		this.kldgpcomment = kldgpcomment;
	}
	
	@ExcelField(title="克罗德公铺图文附件", align=2, sort=181)
	public String getKldgpfile() {
		return kldgpfile;
	}

	public void setKldgpfile(String kldgpfile) {
		this.kldgpfile = kldgpfile;
	}
	
	
	@ExcelField(title="克罗德大仓库", align=2, sort=182)
	public Integer getKlddckqualified() {
		return klddckqualified;
	}

	public void setKlddckqualified(Integer klddckqualified) {
		this.klddckqualified = klddckqualified;
	}
	
	@ExcelField(title="克罗德大仓库检查情况", align=2, sort=183)
	public String getKlddcksituation() {
		return klddcksituation;
	}

	public void setKlddcksituation(String klddcksituation) {
		this.klddcksituation = klddcksituation;
	}
	
	@ExcelField(title="克罗德大仓库整改内容", align=2, sort=184)
	public String getKlddckcontent() {
		return klddckcontent;
	}

	public void setKlddckcontent(String klddckcontent) {
		this.klddckcontent = klddckcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="克罗德大仓库整改期限", align=2, sort=185)
	public Date getKlddckdate() {
		return klddckdate;
	}

	public void setKlddckdate(Date klddckdate) {
		this.klddckdate = klddckdate;
	}
	
	@ExcelField(title="克罗德大仓库复查结果", align=2, sort=186)
	public String getKlddckresult() {
		return klddckresult;
	}

	public void setKlddckresult(String klddckresult) {
		this.klddckresult = klddckresult;
	}
	
	@ExcelField(title="克罗德大仓库备注", align=2, sort=187)
	public String getKlddckcomment() {
		return klddckcomment;
	}

	public void setKlddckcomment(String klddckcomment) {
		this.klddckcomment = klddckcomment;
	}
	
	@ExcelField(title="克罗德大仓库图文附件", align=2, sort=188)
	public String getKlddckfile() {
		return klddckfile;
	}

	public void setKlddckfile(String klddckfile) {
		this.klddckfile = klddckfile;
	}
	
	@ExcelField(title="克罗德辅料1表示合格0表示不合格", align=2, sort=189)
	public Integer getKldflqualified() {
		return kldflqualified;
	}

	public void setKldflqualified(Integer kldflqualified) {
		this.kldflqualified = kldflqualified;
	}
	
	@ExcelField(title="克罗德辅料检查情况", align=2, sort=190)
	public String getKldflsituation() {
		return kldflsituation;
	}

	public void setKldflsituation(String kldflsituation) {
		this.kldflsituation = kldflsituation;
	}
	
	@ExcelField(title="克罗德辅料整改内容", align=2, sort=191)
	public String getKldflcontent() {
		return kldflcontent;
	}

	public void setKldflcontent(String kldflcontent) {
		this.kldflcontent = kldflcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="克罗德辅料整改期限", align=2, sort=192)
	public Date getKldfldate() {
		return kldfldate;
	}

	public void setKldfldate(Date kldfldate) {
		this.kldfldate = kldfldate;
	}
	
	@ExcelField(title="克罗德辅料复查结果", align=2, sort=193)
	public String getKldflresult() {
		return kldflresult;
	}

	public void setKldflresult(String kldflresult) {
		this.kldflresult = kldflresult;
	}
	
	@ExcelField(title="克罗德辅料库备注", align=2, sort=194)
	public String getKldflcomment() {
		return kldflcomment;
	}

	public void setKldflcomment(String kldflcomment) {
		this.kldflcomment = kldflcomment;
	}
	
	@ExcelField(title="克罗德辅料图文附件", align=2, sort=195)
	public String getKldflfile() {
		return kldflfile;
	}

	public void setKldflfile(String kldflfile) {
		this.kldflfile = kldflfile;
	}
	
	@ExcelField(title="克罗德废料1表示合格0表示不合格", align=2, sort=196)
	public Integer getKldfeilqualified() {
		return kldfeilqualified;
	}

	public void setKldfeilqualified(Integer kldfeilqualified) {
		this.kldfeilqualified = kldfeilqualified;
	}
	
	@ExcelField(title="克罗德废料检查情况", align=2, sort=197)
	public String getKldfeilsituation() {
		return kldfeilsituation;
	}

	public void setKldfeilsituation(String kldfeilsituation) {
		this.kldfeilsituation = kldfeilsituation;
	}
	
	@ExcelField(title="克罗德废料整改内容", align=2, sort=198)
	public String getKldfeilcontent() {
		return kldfeilcontent;
	}

	public void setKldfeilcontent(String kldfeilcontent) {
		this.kldfeilcontent = kldfeilcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="克罗德废料整改期限", align=2, sort=199)
	public Date getKldfeildate() {
		return kldfeildate;
	}

	public void setKldfeildate(Date kldfeildate) {
		this.kldfeildate = kldfeildate;
	}
	
	@ExcelField(title="克罗德废料复查结果", align=2, sort=200)
	public String getKldfeilresult() {
		return kldfeilresult;
	}

	public void setKldfeilresult(String kldfeilresult) {
		this.kldfeilresult = kldfeilresult;
	}
	
	@ExcelField(title="克罗德废料库备注", align=2, sort=201)
	public String getKldfeilcomment() {
		return kldfeilcomment;
	}

	public void setKldfeilcomment(String kldfeilcomment) {
		this.kldfeilcomment = kldfeilcomment;
	}
	
	@ExcelField(title="克罗德废料图文附件", align=2, sort=202)
	public String getKldfeilfile() {
		return kldfeilfile;
	}

	public void setKldfeilfile(String kldfeilfile) {
		this.kldfeilfile = kldfeilfile;
	}
	
	@ExcelField(title="克罗德办公楼1表示合格0表示不合格", align=2, sort=203)
	public Integer getKldbglqualified() {
		return kldbglqualified;
	}

	public void setKldbglqualified(Integer kldbglqualified) {
		this.kldbglqualified = kldbglqualified;
	}
	
	@ExcelField(title="克罗德办公楼检查情况", align=2, sort=204)
	public String getKldbglsituation() {
		return kldbglsituation;
	}

	public void setKldbglsituation(String kldbglsituation) {
		this.kldbglsituation = kldbglsituation;
	}
	
	@ExcelField(title="克罗德办公楼整改内容", align=2, sort=205)
	public String getKldbglcontent() {
		return kldbglcontent;
	}

	public void setKldbglcontent(String kldbglcontent) {
		this.kldbglcontent = kldbglcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="克罗德办公楼整改期限", align=2, sort=206)
	public Date getKldbgldate() {
		return kldbgldate;
	}

	public void setKldbgldate(Date kldbgldate) {
		this.kldbgldate = kldbgldate;
	}
	
	@ExcelField(title="克罗德办公楼复查结果", align=2, sort=207)
	public String getKldbglresult() {
		return kldbglresult;
	}

	public void setKldbglresult(String kldbglresult) {
		this.kldbglresult = kldbglresult;
	}
	
	@ExcelField(title="克罗德办公楼备注", align=2, sort=208)
	public String getKldbglcomment() {
		return kldbglcomment;
	}

	public void setKldbglcomment(String kldbglcomment) {
		this.kldbglcomment = kldbglcomment;
	}
	
	@ExcelField(title="克罗德办公楼图文附件", align=2, sort=209)
	public String getKldbglfile() {
		return kldbglfile;
	}

	public void setKldbglfile(String kldbglfile) {
		this.kldbglfile = kldbglfile;
	}
	
	@ExcelField(title="克罗德道路1表示合格0表示不合格", align=2, sort=210)
	public Integer getKlddlqualified() {
		return klddlqualified;
	}

	public void setKlddlqualified(Integer klddlqualified) {
		this.klddlqualified = klddlqualified;
	}
	
	@ExcelField(title="克罗德道路检查情况", align=2, sort=211)
	public String getKlddlsituation() {
		return klddlsituation;
	}

	public void setKlddlsituation(String klddlsituation) {
		this.klddlsituation = klddlsituation;
	}
	
	@ExcelField(title="克罗德道路整改内容", align=2, sort=212)
	public String getKlddlcontent() {
		return klddlcontent;
	}

	public void setKlddlcontent(String klddlcontent) {
		this.klddlcontent = klddlcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="克罗德道路整改期限", align=2, sort=213)
	public Date getKlddldate() {
		return klddldate;
	}

	public void setKlddldate(Date klddldate) {
		this.klddldate = klddldate;
	}
	
	@ExcelField(title="克罗德道路复查结果", align=2, sort=214)
	public String getKlddlresult() {
		return klddlresult;
	}

	public void setKlddlresult(String klddlresult) {
		this.klddlresult = klddlresult;
	}
	
	@ExcelField(title="克罗德道路备注", align=2, sort=215)
	public String getKlddlcomment() {
		return klddlcomment;
	}

	public void setKlddlcomment(String klddlcomment) {
		this.klddlcomment = klddlcomment;
	}
	
	@ExcelField(title="克罗德道路图文附件", align=2, sort=216)
	public String getKlddlfile() {
		return klddlfile;
	}

	public void setKlddlfile(String klddlfile) {
		this.klddlfile = klddlfile;
	}
	
	@ExcelField(title="宿舍1表示合格0表示不合格", align=2, sort=217)
	public Integer getDormitoryqualified() {
		return dormitoryqualified;
	}

	public void setDormitoryqualified(Integer dormitoryqualified) {
		this.dormitoryqualified = dormitoryqualified;
	}
	
	@ExcelField(title="宿舍检查情况", align=2, sort=218)
	public String getDormitorysituation() {
		return dormitorysituation;
	}

	public void setDormitorysituation(String dormitorysituation) {
		this.dormitorysituation = dormitorysituation;
	}
	
	@ExcelField(title="宿舍整改内容", align=2, sort=219)
	public String getDormitorycontent() {
		return dormitorycontent;
	}

	public void setDormitorycontent(String dormitorycontent) {
		this.dormitorycontent = dormitorycontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="宿舍整改期限", align=2, sort=220)
	public Date getDormitorydate() {
		return dormitorydate;
	}

	public void setDormitorydate(Date dormitorydate) {
		this.dormitorydate = dormitorydate;
	}
	
	@ExcelField(title="宿舍复查结果", align=2, sort=221)
	public String getDormitoryresult() {
		return dormitoryresult;
	}

	public void setDormitoryresult(String dormitoryresult) {
		this.dormitoryresult = dormitoryresult;
	}
	
	@ExcelField(title="宿舍备注", align=2, sort=222)
	public String getDormitorycomment() {
		return dormitorycomment;
	}

	public void setDormitorycomment(String dormitorycomment) {
		this.dormitorycomment = dormitorycomment;
	}
	
	@ExcelField(title="宿舍图文附件", align=2, sort=223)
	public String getDormitoryfile() {
		return dormitoryfile;
	}

	public void setDormitoryfile(String dormitoryfile) {
		this.dormitoryfile = dormitoryfile;
	}
	
	@ExcelField(title="食堂1表示合格0表示不合格", align=2, sort=224)
	public Integer getCanteenqualified() {
		return canteenqualified;
	}

	public void setCanteenqualified(Integer canteenqualified) {
		this.canteenqualified = canteenqualified;
	}
	
	@ExcelField(title="食堂检查情况", align=2, sort=225)
	public String getCanteensituation() {
		return canteensituation;
	}

	public void setCanteensituation(String canteensituation) {
		this.canteensituation = canteensituation;
	}
	
	@ExcelField(title="食堂整改内容", align=2, sort=226)
	public String getCanteencontent() {
		return canteencontent;
	}

	public void setCanteencontent(String canteencontent) {
		this.canteencontent = canteencontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="食堂整改期限", align=2, sort=227)
	public Date getCanteendate() {
		return canteendate;
	}

	public void setCanteendate(Date canteendate) {
		this.canteendate = canteendate;
	}
	
	@ExcelField(title="食堂复查结果", align=2, sort=228)
	public String getCanteenresult() {
		return canteenresult;
	}

	public void setCanteenresult(String canteenresult) {
		this.canteenresult = canteenresult;
	}
	
	@ExcelField(title="食堂备注", align=2, sort=229)
	public String getCanteencomment() {
		return canteencomment;
	}

	public void setCanteencomment(String canteencomment) {
		this.canteencomment = canteencomment;
	}
	
	@ExcelField(title="食堂图文附件", align=2, sort=230)
	public String getCanteenfile() {
		return canteenfile;
	}

	public void setCanteenfile(String canteenfile) {
		this.canteenfile = canteenfile;
	}
	
	
	@ExcelField(title="门卫1表示合格0表示不合格", align=2, sort=231)
	public Integer getGuardqualified() {
		return guardqualified;
	}

	public void setGuardqualified(Integer guardqualified) {
		this.guardqualified = guardqualified;
	}
	
	@ExcelField(title="门卫检查情况", align=2, sort=232)
	public String getGuardsituation() {
		return guardsituation;
	}

	public void setGuardsituation(String guardsituation) {
		this.guardsituation = guardsituation;
	}
	
	@ExcelField(title="门卫整改内容", align=2, sort=233)
	public String getGuardcontent() {
		return guardcontent;
	}

	public void setGuardcontent(String guardcontent) {
		this.guardcontent = guardcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="门卫整改期限", align=2, sort=234)
	public Date getGuarddate() {
		return guarddate;
	}

	public void setGuarddate(Date guarddate) {
		this.guarddate = guarddate;
	}
	
	@ExcelField(title="门卫复查结果", align=2, sort=235)
	public String getGuardresult() {
		return guardresult;
	}

	public void setGuardresult(String guardresult) {
		this.guardresult = guardresult;
	}
	
	@ExcelField(title="门卫备注", align=2, sort=236)
	public String getGuardcomment() {
		return guardcomment;
	}

	public void setGuardcomment(String guardcomment) {
		this.guardcomment = guardcomment;
	}
	
	@ExcelField(title="门卫图文附件", align=2, sort=237)
	public String getGuardfile() {
		return guardfile;
	}

	public void setGuardfile(String guardfile) {
		this.guardfile = guardfile;
	}
	
	
	@ExcelField(title="其他1表示合格0表示不合格", align=2, sort=238)
	public Integer getOtherqualified() {
		return otherqualified;
	}

	public void setOtherqualified(Integer otherqualified) {
		this.otherqualified = otherqualified;
	}
	
	@ExcelField(title="其他检查情况", align=2, sort=239)
	public String getOthersituation() {
		return othersituation;
	}

	public void setOthersituation(String othersituation) {
		this.othersituation = othersituation;
	}
	
	@ExcelField(title="其他整改内容", align=2, sort=240)
	public String getOthercontent() {
		return othercontent;
	}

	public void setOthercontent(String othercontent) {
		this.othercontent = othercontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="其他整改期限", align=2, sort=241)
	public Date getOtherdate() {
		return otherdate;
	}

	public void setOtherdate(Date otherdate) {
		this.otherdate = otherdate;
	}
	
	@ExcelField(title="其他复查结果", align=2, sort=242)
	public String getOtherresult() {
		return otherresult;
	}

	public void setOtherresult(String otherresult) {
		this.otherresult = otherresult;
	}
	
	@ExcelField(title="其他备注", align=2, sort=243)
	public String getOthercomment() {
		return othercomment;
	}

	public void setOthercomment(String othercomment) {
		this.othercomment = othercomment;
	}
	
	@ExcelField(title="其他图文附件", align=2, sort=244)
	public String getOtherfile() {
		return otherfile;
	}

	public void setOtherfile(String otherfile) {
		this.otherfile = otherfile;
	}
	
	@ExcelField(title="浙华办公楼1表示合格0表示不合格", align=2, sort=245)
	public Integer getZhbglqualified() {
		return zhbglqualified;
	}

	public void setZhbglqualified(Integer zhbglqualified) {
		this.zhbglqualified = zhbglqualified;
	}
	
	@ExcelField(title="浙华办公楼检查情况", align=2, sort=246)
	public String getZhbglsituation() {
		return zhbglsituation;
	}

	public void setZhbglsituation(String zhbglsituation) {
		this.zhbglsituation = zhbglsituation;
	}
	
	@ExcelField(title="浙华办公楼整改内容", align=2, sort=247)
	public String getZhbglcontent() {
		return zhbglcontent;
	}

	public void setZhbglcontent(String zhbglcontent) {
		this.zhbglcontent = zhbglcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="浙华办公楼整改期限", align=2, sort=248)
	public Date getZhbgldate() {
		return zhbgldate;
	}

	public void setZhbgldate(Date zhbgldate) {
		this.zhbgldate = zhbgldate;
	}
	
	@ExcelField(title="浙华办公楼复查结果", align=2, sort=249)
	public String getZhbglresult() {
		return zhbglresult;
	}

	public void setZhbglresult(String zhbglresult) {
		this.zhbglresult = zhbglresult;
	}
	
	@ExcelField(title="浙华办公楼备注", align=2, sort=250)
	public String getZhbglcomment() {
		return zhbglcomment;
	}

	public void setZhbglcomment(String zhbglcomment) {
		this.zhbglcomment = zhbglcomment;
	}
	
	@ExcelField(title="浙华办公楼图文附件", align=2, sort=251)
	public String getZhbglfile() {
		return zhbglfile;
	}

	public void setZhbglfile(String zhbglfile) {
		this.zhbglfile = zhbglfile;
	}
	
	@ExcelField(title="生产办公室1表示合格0表示不合格", align=2, sort=252)
	public Integer getScbgsqualified() {
		return scbgsqualified;
	}

	public void setScbgsqualified(Integer scbgsqualified) {
		this.scbgsqualified = scbgsqualified;
	}
	
	@ExcelField(title="生产办公室检查情况", align=2, sort=253)
	public String getScbgssituation() {
		return scbgssituation;
	}

	public void setScbgssituation(String scbgssituation) {
		this.scbgssituation = scbgssituation;
	}
	
	@ExcelField(title="生产办公室整改内容", align=2, sort=254)
	public String getScbgscontent() {
		return scbgscontent;
	}

	public void setScbgscontent(String scbgscontent) {
		this.scbgscontent = scbgscontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="生产办公室整改期限", align=2, sort=255)
	public Date getScbgsdate() {
		return scbgsdate;
	}

	public void setScbgsdate(Date scbgsdate) {
		this.scbgsdate = scbgsdate;
	}
	
	@ExcelField(title="生产办公室复查结果", align=2, sort=256)
	public String getScbgsresult() {
		return scbgsresult;
	}

	public void setScbgsresult(String scbgsresult) {
		this.scbgsresult = scbgsresult;
	}
	
	@ExcelField(title="生产办公室备注", align=2, sort=257)
	public String getScbgscomment() {
		return scbgscomment;
	}

	public void setScbgscomment(String scbgscomment) {
		this.scbgscomment = scbgscomment;
	}
	
	@ExcelField(title="生产办公室图文附件", align=2, sort=258)
	public String getScbgsfile() {
		return scbgsfile;
	}

	public void setScbgsfile(String scbgsfile) {
		this.scbgsfile = scbgsfile;
	}
	
	
	@ExcelField(title="直缝车间1表示合格0表示不合格", align=2, sort=259)
	public Integer getZfcjqualified() {
		return zfcjqualified;
	}

	public void setZfcjqualified(Integer zfcjqualified) {
		this.zfcjqualified = zfcjqualified;
	}
	
	@ExcelField(title="直缝车间检查情况", align=2, sort=260)
	public String getZfcjsituation() {
		return zfcjsituation;
	}

	public void setZfcjsituation(String zfcjsituation) {
		this.zfcjsituation = zfcjsituation;
	}
	
	@ExcelField(title="直缝车间整改内容", align=2, sort=261)
	public String getZfcjcontent() {
		return zfcjcontent;
	}

	public void setZfcjcontent(String zfcjcontent) {
		this.zfcjcontent = zfcjcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	@ExcelField(title="直缝车间整改期限", align=2, sort=262)
	public Date getZfcjdate() {
		return zfcjdate;
	}

	public void setZfcjdate(Date zfcjdate) {
		this.zfcjdate = zfcjdate;
	}
	
	@ExcelField(title="直缝车间复查结果", align=2, sort=263)
	public String getZfcjresult() {
		return zfcjresult;
	}

	public void setZfcjresult(String zfcjresult) {
		this.zfcjresult = zfcjresult;
	}
	
	@ExcelField(title="直缝车间备注", align=2, sort=264)
	public String getZfcjcomment() {
		return zfcjcomment;
	}

	public void setZfcjcomment(String zfcjcomment) {
		this.zfcjcomment = zfcjcomment;
	}
	
	@ExcelField(title="直缝车间图文附件", align=2, sort=265)
	public String getZfcjfile() {
		return zfcjfile;
	}

	public void setZfcjfile(String zfcjfile) {
		this.zfcjfile = zfcjfile;
	}
	
	
	@ExcelField(title="螺旋车间1表示合格0表示不合格", align=2, sort=266)
	public Integer getLxcjqualified() {
		return lxcjqualified;
	}

	public void setLxcjqualified(Integer lxcjqualified) {
		this.lxcjqualified = lxcjqualified;
	}
	
	@ExcelField(title="螺旋车间检查情况", align=2, sort=267)
	public String getLxcjsituation() {
		return lxcjsituation;
	}

	public void setLxcjsituation(String lxcjsituation) {
		this.lxcjsituation = lxcjsituation;
	}
	
	@ExcelField(title="螺旋车间整改内容", align=2, sort=268)
	public String getLxcjcontent() {
		return lxcjcontent;
	}

	public void setLxcjcontent(String lxcjcontent) {
		this.lxcjcontent = lxcjcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="螺旋车间整改期限", align=2, sort=269)
	public Date getLxcjdate() {
		return lxcjdate;
	}

	public void setLxcjdate(Date lxcjdate) {
		this.lxcjdate = lxcjdate;
	}
	
	@ExcelField(title="螺旋车间复查结果", align=2, sort=270)
	public String getLxcjresult() {
		return lxcjresult;
	}

	public void setLxcjresult(String lxcjresult) {
		this.lxcjresult = lxcjresult;
	}
	
	@ExcelField(title="螺旋车间备注", align=2, sort=271)
	public String getLxcjcomment() {
		return lxcjcomment;
	}

	public void setLxcjcomment(String lxcjcomment) {
		this.lxcjcomment = lxcjcomment;
	}
	
	@ExcelField(title="螺旋车间图文附件", align=2, sort=272)
	public String getLxcjfile() {
		return lxcjfile;
	}

	public void setLxcjfile(String lxcjfile) {
		this.lxcjfile = lxcjfile;
	}
	
	@ExcelField(title="现货仓库1表示合格0表示不合格", align=2, sort=273)
	public Integer getXhqualified() {
		return xhqualified;
	}

	public void setXhqualified(Integer xhqualified) {
		this.xhqualified = xhqualified;
	}
	
	@ExcelField(title="现货仓库检查情况", align=2, sort=274)
	public String getXhsituation() {
		return xhsituation;
	}

	public void setXhsituation(String xhsituation) {
		this.xhsituation = xhsituation;
	}
	
	@ExcelField(title="现货仓库整改内容", align=2, sort=275)
	public String getXhcontent() {
		return xhcontent;
	}

	public void setXhcontent(String xhcontent) {
		this.xhcontent = xhcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="现货仓库整改期限", align=2, sort=276)
	public Date getXhdate() {
		return xhdate;
	}

	public void setXhdate(Date xhdate) {
		this.xhdate = xhdate;
	}
	
	@ExcelField(title="现货仓库复查结果", align=2, sort=277)
	public String getXhresult() {
		return xhresult;
	}

	public void setXhresult(String xhresult) {
		this.xhresult = xhresult;
	}
	
	@ExcelField(title="现货仓库备注", align=2, sort=278)
	public String getXhcomment() {
		return xhcomment;
	}

	public void setXhcomment(String xhcomment) {
		this.xhcomment = xhcomment;
	}
	
	@ExcelField(title="现货仓库图文附件", align=2, sort=279)
	public String getXhfile() {
		return xhfile;
	}

	public void setXhfile(String xhfile) {
		this.xhfile = xhfile;
	}
	
	@ExcelField(title="辅料仓库1表示合格0表示不合格", align=2, sort=280)
	public Integer getFlqualified() {
		return flqualified;
	}

	public void setFlqualified(Integer flqualified) {
		this.flqualified = flqualified;
	}
	
	@ExcelField(title="辅料仓库检查情况", align=2, sort=281)
	public String getFlsituation() {
		return flsituation;
	}

	public void setFlsituation(String flsituation) {
		this.flsituation = flsituation;
	}
	
	@ExcelField(title="辅料仓库整改内容", align=2, sort=282)
	public String getFlcontent() {
		return flcontent;
	}

	public void setFlcontent(String flcontent) {
		this.flcontent = flcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="辅料仓库整改期限", align=2, sort=283)
	public Date getFldate() {
		return fldate;
	}

	public void setFldate(Date fldate) {
		this.fldate = fldate;
	}
	
	@ExcelField(title="辅料仓库复查结果", align=2, sort=284)
	public String getFlresult() {
		return flresult;
	}

	public void setFlresult(String flresult) {
		this.flresult = flresult;
	}
	
	@ExcelField(title="辅料仓库备注", align=2, sort=285)
	public String getFlcomment() {
		return flcomment;
	}

	public void setFlcomment(String flcomment) {
		this.flcomment = flcomment;
	}
	
	@ExcelField(title="辅料仓库图文附件", align=2, sort=286)
	public String getFlfile() {
		return flfile;
	}

	public void setFlfile(String flfile) {
		this.flfile = flfile;
	}
	
	@ExcelField(title="宁波道路1表示合格0表示不合格", align=2, sort=287)
	public Integer getRoadqualified() {
		return roadqualified;
	}

	public void setRoadqualified(Integer roadqualified) {
		this.roadqualified = roadqualified;
	}
	
	@ExcelField(title="宁波道路检查情况", align=2, sort=288)
	public String getRoadsituation() {
		return roadsituation;
	}

	public void setRoadsituation(String roadsituation) {
		this.roadsituation = roadsituation;
	}
	
	@ExcelField(title="宁波道路整改内容", align=2, sort=289)
	public String getRoadcontent() {
		return roadcontent;
	}

	public void setRoadcontent(String roadcontent) {
		this.roadcontent = roadcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="宁波道路整改期限", align=2, sort=290)
	public Date getRoaddate() {
		return roaddate;
	}

	public void setRoaddate(Date roaddate) {
		this.roaddate = roaddate;
	}
	
	@ExcelField(title="宁波道路复查结果", align=2, sort=291)
	public String getRoadresult() {
		return roadresult;
	}

	public void setRoadresult(String roadresult) {
		this.roadresult = roadresult;
	}
	
	@ExcelField(title="宁波道路备注", align=2, sort=292)
	public String getRoadcomment() {
		return roadcomment;
	}

	public void setRoadcomment(String roadcomment) {
		this.roadcomment = roadcomment;
	}
	
	@ExcelField(title="宁波道路图文附件", align=2, sort=293)
	public String getRoadfile() {
		return roadfile;
	}

	public void setRoadfile(String roadfile) {
		this.roadfile = roadfile;
	}
	
	@ExcelField(title="总结", align=2, sort=294)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@ExcelField(title="组长", align=2, sort=295)
	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="检查日期", align=2, sort=296)
	public Date getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建日期", align=2, sort=297)
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@ExcelField(title="检查组成员", align=2, sort=298)
	public String getCheckmember() {
		return checkmember;
	}

	public void setCheckmember(String checkmember) {
		this.checkmember = checkmember;
	}
	
	@ExcelField(title="0表示常熟1表示宁波", align=2, sort=299)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="ip", align=2, sort=300)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@ExcelField(title="常熟厕所检查情况", align=2, sort=301)
	public String getCstoiletsituation() {
		return cstoiletsituation;
	}

	public void setCstoiletsituation(String cstoiletsituation) {
		this.cstoiletsituation = cstoiletsituation;
	}
	
	@ExcelField(title="常熟厕所整改内容", align=2, sort=302)
	public String getCstoiletcontent() {
		return cstoiletcontent;
	}

	public void setCstoiletcontent(String cstoiletcontent) {
		this.cstoiletcontent = cstoiletcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="常熟厕所整改期限", align=2, sort=303)
	public Date getCstoiletdate() {
		return cstoiletdate;
	}

	public void setCstoiletdate(Date cstoiletdate) {
		this.cstoiletdate = cstoiletdate;
	}
	
	@ExcelField(title="常熟厕所复查结果", align=2, sort=304)
	public String getCstoiletresult() {
		return cstoiletresult;
	}

	public void setCstoiletresult(String cstoiletresult) {
		this.cstoiletresult = cstoiletresult;
	}
	
	@ExcelField(title="常熟厕所备注", align=2, sort=305)
	public String getCstoiletcomment() {
		return cstoiletcomment;
	}

	public void setCstoiletcomment(String cstoiletcomment) {
		this.cstoiletcomment = cstoiletcomment;
	}
	
	@ExcelField(title="常熟厕所图文附件", align=2, sort=306)
	public String getCstoiletfile() {
		return cstoiletfile;
	}

	public void setCstoiletfile(String cstoiletfile) {
		this.cstoiletfile = cstoiletfile;
	}
	
	@ExcelField(title="宁波厕所检查情况", align=2, sort=307)
	public String getNbtoiletsituation() {
		return nbtoiletsituation;
	}

	public void setNbtoiletsituation(String nbtoiletsituation) {
		this.nbtoiletsituation = nbtoiletsituation;
	}
	
	@ExcelField(title="宁波厕所整改内容", align=2, sort=308)
	public String getNbtoiletcontent() {
		return nbtoiletcontent;
	}

	public void setNbtoiletcontent(String nbtoiletcontent) {
		this.nbtoiletcontent = nbtoiletcontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="宁波厕所整改期限", align=2, sort=309)
	public Date getNbtoiletdate() {
		return nbtoiletdate;
	}

	public void setNbtoiletdate(Date nbtoiletdate) {
		this.nbtoiletdate = nbtoiletdate;
	}
	
	@ExcelField(title="宁波厕所复查结果", align=2, sort=310)
	public String getNbtoiletresult() {
		return nbtoiletresult;
	}

	public void setNbtoiletresult(String nbtoiletresult) {
		this.nbtoiletresult = nbtoiletresult;
	}
	
	@ExcelField(title="宁波厕所备注", align=2, sort=311)
	public String getNbtoiletcomment() {
		return nbtoiletcomment;
	}

	public void setNbtoiletcomment(String nbtoiletcomment) {
		this.nbtoiletcomment = nbtoiletcomment;
	}
	
	@ExcelField(title="宁波厕所图文附件", align=2, sort=312)
	public String getNbtoiletfile() {
		return nbtoiletfile;
	}

	public void setNbtoiletfile(String nbtoiletfile) {
		this.nbtoiletfile = nbtoiletfile;
	}
	
	@ExcelField(title="常熟厕所1表示合格0表示不合格", align=2, sort=313)
	public Integer getCstoiletqualified() {
		return cstoiletqualified;
	}

	public void setCstoiletqualified(Integer cstoiletqualified) {
		this.cstoiletqualified = cstoiletqualified;
	}
	
	@ExcelField(title="宁波厕所1表示合格0表示不合格", align=2, sort=314)
	public Integer getNbtoiletqualified() {
		return nbtoiletqualified;
	}

	public void setNbtoiletqualified(Integer nbtoiletqualified) {
		this.nbtoiletqualified = nbtoiletqualified;
	}
	
}