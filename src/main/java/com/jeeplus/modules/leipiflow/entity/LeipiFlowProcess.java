/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.oa.entity.OaNotify;

/**
 * 流程步骤Entity
 * @author 陈钱江
 * @version 2017-09-05
 */
public class LeipiFlowProcess extends DataEntity<LeipiFlowProcess> {
	
	private static final long serialVersionUID = 1L;
	private String flowId;		// 流程ID
	private String processName;		// 步骤名称
	private String processType;		// 步骤类型
	private String processTo;		// 转交下一步骤号
	private String childId;		// is_child 子流程id有return_step_to结束后继续父流程下一步
	private String childRelation;		// [保留功能]父子流程字段映射关系
	private Integer childAfter=0;		// 子流程 结束后动作 0结束并更新父流程节点为结束  1结束并返回父流程步骤
	private String childBackProcess;		// 子流程结束返回的步骤id
	private String returnSponsorIds;		// [保留功能]主办人 子流程结束后下一步的主办人
	private String returnResponIds;		// [保留功能]经办人 子流程结束后下一步的经办人
	private String writeFields;		// 这个步骤可写的字段
	private String secretFields;		// 这个步骤隐藏的字段
	private String lockFields;		// 锁定不能更改宏控件的值
	private String checkFields;		// 字段验证规则
	private Integer autoPerson=0;		// 本步骤的自动选主办人规则0:为不自动选择1：流程发起人2：本部门主管3指定默认人4上级主管领导5. 一级部门主管6. 指定步骤主办人7.执行时替换
	private Integer autoUnlock=0;		// 是否允许修改主办人auto_type>0 0不允许 1允许（默认）
	private String autoSponsorIds;		// 3指定步骤主办人ids
	private String autoSponsorText;		// 3指定步骤主办人text
	private String autoResponIds;		// 3指定步骤主办人ids
	private String autoResponText;		// 3指定步骤主办人text
	private String autoRoleIds;		// 制定默认角色ids
	private String autoRoleText;		// 制定默认角色text
	private String autoProcessSponsor;		// [保留功能]指定其中一个步骤的主办人处理
	private String rangeUserIds;		// 本步骤的经办人授权范围ids
	private String rangeUserText;		// 本步骤的经办人授权范围text
	private String rangeDeptIds;		// 本步骤的经办部门授权范围
	private String rangeDeptText;		// 本步骤的经办部门授权范围text
	private String rangeRoleIds;		// 本步骤的经办角色授权范围ids
	private String rangeRoleText;		// 本步骤的经办角色授权范围text
	private Integer receiveType=0;		// 0明确指定主办人1先接收者为主办人
	private Integer isUserEnd=0;		// 允许主办人在非最后步骤也可以办结流程
	private Integer isUseropPass=0;		// 经办人可以转交下一步
	private Integer isSing=0;		// 会签选项0禁止会签1允许会签（默认） 2强制会签
	private Integer signLook=0;		// 会签可见性0总是可见（默认）,1本步骤经办人之间不可见2针对其他步骤不可见
	private String outCondition;		// 转出条件
	private Integer setleft=0;		// 左 坐标
	private Integer settop=0;		// 上 坐标
	private String style;		// 样式 序列化
	private String isdel;		// 是否删除
	private Date updatetime;		// 更新时间
	private Date dateline;		// 结束时间
	private Integer isBack=0;		// 是否允许回退0不允许（默认） 1允许退回上一步2允许退回之前步骤
	private String styleWidth;		// 图标宽度
	private String styleHeight;		// 图标高度
	private String styleColor;		// 图标颜色
	private String styleIcon;		// 图标
	private Integer parallel=0;		// 0非并行步骤1并行步骤
	
	private LeipiFlow leipiFlow;
	
	public LeipiFlowProcess(LeipiFlow leipiFlow){
		this.leipiFlow = leipiFlow;
	}
	
	public LeipiFlowProcess() {
		super();
	}

	public LeipiFlowProcess(String id){
		super(id);
	}

	@ExcelField(title="流程ID", align=2, sort=1)
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	@ExcelField(title="步骤名称", align=2, sort=2)
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	@ExcelField(title="步骤类型", align=2, sort=3)
	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	@ExcelField(title="转交下一步骤号", align=2, sort=4)
	public String getProcessTo() {
		return processTo;
	}

	public void setProcessTo(String processTo) {
		this.processTo = processTo;
	}
	
	@ExcelField(title="is_child 子流程id有return_step_to结束后继续父流程下一步", align=2, sort=5)
	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}
	
	@ExcelField(title="[保留功能]父子流程字段映射关系", dictType="del_flag", align=2, sort=6)
	public String getChildRelation() {
		return childRelation;
	}

	public void setChildRelation(String childRelation) {
		this.childRelation = childRelation;
	}
	
	@ExcelField(title="子流程 结束后动作 0结束并更新父流程节点为结束  1结束并返回父流程步骤", align=2, sort=7)
	public Integer getChildAfter() {
		return childAfter;
	}

	public void setChildAfter(Integer childAfter) {
		this.childAfter = childAfter;
	}
	
	@ExcelField(title="子流程结束返回的步骤id", align=2, sort=8)
	public String getChildBackProcess() {
		return childBackProcess;
	}

	public void setChildBackProcess(String childBackProcess) {
		this.childBackProcess = childBackProcess;
	}
	
	@ExcelField(title="[保留功能]主办人 子流程结束后下一步的主办人", align=2, sort=9)
	public String getReturnSponsorIds() {
		return returnSponsorIds;
	}

	public void setReturnSponsorIds(String returnSponsorIds) {
		this.returnSponsorIds = returnSponsorIds;
	}
	
	@ExcelField(title="[保留功能]经办人 子流程结束后下一步的经办人", align=2, sort=10)
	public String getReturnResponIds() {
		return returnResponIds;
	}

	public void setReturnResponIds(String returnResponIds) {
		this.returnResponIds = returnResponIds;
	}
	
	@ExcelField(title="这个步骤可写的字段", align=2, sort=11)
	public String getWriteFields() {
		return writeFields;
	}

	public void setWriteFields(String writeFields) {
		this.writeFields = writeFields;
	}
	
	@ExcelField(title="这个步骤隐藏的字段", align=2, sort=12)
	public String getSecretFields() {
		return secretFields;
	}

	public void setSecretFields(String secretFields) {
		this.secretFields = secretFields;
	}
	
	@ExcelField(title="锁定不能更改宏控件的值", align=2, sort=13)
	public String getLockFields() {
		return lockFields;
	}

	public void setLockFields(String lockFields) {
		this.lockFields = lockFields;
	}
	
	@ExcelField(title="字段验证规则", align=2, sort=14)
	public String getCheckFields() {
		return checkFields;
	}

	public void setCheckFields(String checkFields) {
		this.checkFields = checkFields;
	}
	
	@ExcelField(title="本步骤的自动选主办人规则0:为不自动选择1：流程发起人2：本部门主管3指定默认人4上级主管领导5. 一级部门主管6. 指定步骤主办人", align=2, sort=15)
	public Integer getAutoPerson() {
		return autoPerson;
	}

	public void setAutoPerson(Integer autoPerson) {
		this.autoPerson = autoPerson;
	}
	
	@ExcelField(title="是否允许修改主办人auto_type>0 0不允许 1允许（默认）", align=2, sort=16)
	public Integer getAutoUnlock() {
		return autoUnlock;
	}

	public void setAutoUnlock(Integer autoUnlock) {
		this.autoUnlock = autoUnlock;
	}
	
	@ExcelField(title="3指定步骤主办人ids", align=2, sort=17)
	public String getAutoSponsorIds() {
		return autoSponsorIds;
	}

	public void setAutoSponsorIds(String autoSponsorIds) {
		this.autoSponsorIds = autoSponsorIds;
	}
	
	@ExcelField(title="3指定步骤主办人text", align=2, sort=18)
	public String getAutoSponsorText() {
		return autoSponsorText;
	}

	public void setAutoSponsorText(String autoSponsorText) {
		this.autoSponsorText = autoSponsorText;
	}
	
	@ExcelField(title="3指定步骤主办人ids", align=2, sort=19)
	public String getAutoResponIds() {
		return autoResponIds;
	}

	public void setAutoResponIds(String autoResponIds) {
		this.autoResponIds = autoResponIds;
	}
	
	@ExcelField(title="3指定步骤主办人text", align=2, sort=20)
	public String getAutoResponText() {
		return autoResponText;
	}

	public void setAutoResponText(String autoResponText) {
		this.autoResponText = autoResponText;
	}
	
	@ExcelField(title="制定默认角色ids", align=2, sort=21)
	public String getAutoRoleIds() {
		return autoRoleIds;
	}

	public void setAutoRoleIds(String autoRoleIds) {
		this.autoRoleIds = autoRoleIds;
	}
	
	@ExcelField(title="制定默认角色text", align=2, sort=22)
	public String getAutoRoleText() {
		return autoRoleText;
	}

	public void setAutoRoleText(String autoRoleText) {
		this.autoRoleText = autoRoleText;
	}
	
	@ExcelField(title="[保留功能]指定其中一个步骤的主办人处理", align=2, sort=23)
	public String getAutoProcessSponsor() {
		return autoProcessSponsor;
	}

	public void setAutoProcessSponsor(String autoProcessSponsor) {
		this.autoProcessSponsor = autoProcessSponsor;
	}
	
	@ExcelField(title="本步骤的经办人授权范围ids", align=2, sort=24)
	public String getRangeUserIds() {
		return rangeUserIds;
	}

	public void setRangeUserIds(String rangeUserIds) {
		this.rangeUserIds = rangeUserIds;
	}
	
	@ExcelField(title="本步骤的经办人授权范围text", align=2, sort=25)
	public String getRangeUserText() {
		return rangeUserText;
	}

	public void setRangeUserText(String rangeUserText) {
		this.rangeUserText = rangeUserText;
	}
	
	@ExcelField(title="本步骤的经办部门授权范围", align=2, sort=26)
	public String getRangeDeptIds() {
		return rangeDeptIds;
	}

	public void setRangeDeptIds(String rangeDeptIds) {
		this.rangeDeptIds = rangeDeptIds;
	}
	
	@ExcelField(title="本步骤的经办部门授权范围text", align=2, sort=27)
	public String getRangeDeptText() {
		return rangeDeptText;
	}

	public void setRangeDeptText(String rangeDeptText) {
		this.rangeDeptText = rangeDeptText;
	}
	
	@ExcelField(title="本步骤的经办角色授权范围ids", align=2, sort=28)
	public String getRangeRoleIds() {
		return rangeRoleIds;
	}

	public void setRangeRoleIds(String rangeRoleIds) {
		this.rangeRoleIds = rangeRoleIds;
	}
	
	@ExcelField(title="本步骤的经办角色授权范围text", align=2, sort=29)
	public String getRangeRoleText() {
		return rangeRoleText;
	}

	public void setRangeRoleText(String rangeRoleText) {
		this.rangeRoleText = rangeRoleText;
	}
	
	@ExcelField(title="0明确指定主办人1先接收者为主办人", align=2, sort=30)
	public Integer getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}
	
	@ExcelField(title="允许主办人在非最后步骤也可以办结流程", align=2, sort=31)
	public Integer getIsUserEnd() {
		return isUserEnd;
	}

	public void setIsUserEnd(Integer isUserEnd) {
		this.isUserEnd = isUserEnd;
	}
	
	@ExcelField(title="经办人可以转交下一步", align=2, sort=32)
	public Integer getIsUseropPass() {
		return isUseropPass;
	}

	public void setIsUseropPass(Integer isUseropPass) {
		this.isUseropPass = isUseropPass;
	}
	
	@ExcelField(title="会签选项0禁止会签1允许会签（默认） 2强制会签", align=2, sort=33)
	public Integer getIsSing() {
		return isSing;
	}

	public void setIsSing(Integer isSing) {
		this.isSing = isSing;
	}
	
	@ExcelField(title="会签可见性0总是可见（默认）,1本步骤经办人之间不可见2针对其他步骤不可见", align=2, sort=34)
	public Integer getSignLook() {
		return signLook;
	}

	public void setSignLook(Integer signLook) {
		this.signLook = signLook;
	}
	
	@ExcelField(title="转出条件", align=2, sort=35)
	public String getOutCondition() {
		return outCondition;
	}

	public void setOutCondition(String outCondition) {
		this.outCondition = outCondition;
	}
	
	@ExcelField(title="左 坐标", align=2, sort=36)
	public Integer getSetleft() {
		return setleft;
	}

	public void setSetleft(Integer setleft) {
		this.setleft = setleft;
	}
	
	@ExcelField(title="上 坐标", align=2, sort=37)
	public Integer getSettop() {
		return settop;
	}

	public void setSettop(Integer settop) {
		this.settop = settop;
	}
	
	@ExcelField(title="样式 序列化", align=2, sort=38)
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	@ExcelField(title="是否删除", align=2, sort=39)
	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=40)
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=41)
	public Date getDateline() {
		return dateline;
	}

	public void setDateline(Date dateline) {
		this.dateline = dateline;
	}
	
	@ExcelField(title="是否允许回退0不允许（默认） 1允许退回上一步2允许退回之前步骤", align=2, sort=42)
	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}

	public LeipiFlow getLeipiFlow() {
		return leipiFlow;
	}

	public void setLeipiFlow(LeipiFlow leipiFlow) {
		this.leipiFlow = leipiFlow;
	}
	
	@ExcelField(title="图标宽度", align=2, sort=43)
	public String getStyleWidth() {
		return styleWidth;
	}

	public void setStyleWidth(String styleWidth) {
		this.styleWidth = styleWidth;
	}
	
	@ExcelField(title="图标高度", align=2, sort=44)
	public String getStyleHeight() {
		return styleHeight;
	}

	public void setStyleHeight(String styleHeight) {
		this.styleHeight = styleHeight;
	}
	
	@ExcelField(title="图标颜色", align=2, sort=45)
	public String getStyleColor() {
		return styleColor;
	}

	public void setStyleColor(String styleColor) {
		this.styleColor = styleColor;
	}
	
	@ExcelField(title="图标", align=2, sort=46)
	public String getStyleIcon() {
		return styleIcon;
	}

	public void setStyleIcon(String styleIcon) {
		this.styleIcon = styleIcon;
	}

	public Integer getParallel() {
		return parallel;
	}

	public void setParallel(Integer parallel) {
		this.parallel = parallel;
	}

}