/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leipiflow.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.flow.entity.Flowapply;

import java.util.Date;

/**
 * 指定流程申请明细表Entity
 * @author yc
 * @version 2018-03-21
 */
public class TemplateDetail extends DataEntity<TemplateDetail> {
	
	private static final long serialVersionUID = 1L;
	private String var1;		// 值1
	private String var2;		// 值2
	private String var3;		// 值3
	private String var4;		// 值4
	private String var5;		// 值5
	private String var6;		// 值6
	private String var7;		// 值7
	private String var8;		// 值8
	private String var9;		// 值9
	private String var10;		// 值10
	private String var11;		// 值11
	private String var12;		// 值12
	private String var13;		// 值13
	private String var14;		// 值14
	private String var15;		// 值15
	private String flowapplyid;		// 提交流程申请id
	private String leipiflowid;		// 流程申请id
	private Flowapply flowapply;
    private Integer sortno;

	private String[] vars;
    public String[] getVars() {
        return vars;
    }
    public void setVars(String[] vars) {
        this.vars = vars;
    }

    private Date startdate;		//查询用
	private Date enddate;		//查询用
	private String mtd;//reciever我接收到的
	private String flowstatus;//查询用
	private String createuserid;//查询用 我接收到的
    private String createusername;//查询用
	
	public TemplateDetail() {
		super();
	}

	public TemplateDetail(String id){
		super(id);
	}
    public TemplateDetail(Flowapply flowapply){
        this.flowapply = flowapply;
    }

	@ExcelField(title="值1", align=2, sort=7)
	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}
	
	@ExcelField(title="值2", align=2, sort=8)
	public String getVar2() {
		return var2;
	}

	public void setVar2(String var2) {
		this.var2 = var2;
	}
	
	@ExcelField(title="值3", align=2, sort=9)
	public String getVar3() {
		return var3;
	}

	public void setVar3(String var3) {
		this.var3 = var3;
	}
	
	@ExcelField(title="值4", align=2, sort=10)
	public String getVar4() {
		return var4;
	}

	public void setVar4(String var4) {
		this.var4 = var4;
	}
	
	@ExcelField(title="值5", align=2, sort=11)
	public String getVar5() {
		return var5;
	}

	public void setVar5(String var5) {
		this.var5 = var5;
	}
	
	@ExcelField(title="值6", align=2, sort=12)
	public String getVar6() {
		return var6;
	}

	public void setVar6(String var6) {
		this.var6 = var6;
	}
	
	@ExcelField(title="值7", align=2, sort=13)
	public String getVar7() {
		return var7;
	}

	public void setVar7(String var7) {
		this.var7 = var7;
	}
	
	@ExcelField(title="值8", align=2, sort=14)
	public String getVar8() {
		return var8;
	}

	public void setVar8(String var8) {
		this.var8 = var8;
	}
	
	@ExcelField(title="值9", align=2, sort=15)
	public String getVar9() {
		return var9;
	}

	public void setVar9(String var9) {
		this.var9 = var9;
	}
	
	@ExcelField(title="值10", align=2, sort=16)
	public String getVar10() {
		return var10;
	}

	public void setVar10(String var10) {
		this.var10 = var10;
	}
	
	@ExcelField(title="值11", align=2, sort=17)
	public String getVar11() {
		return var11;
	}

	public void setVar11(String var11) {
		this.var11 = var11;
	}
	
	@ExcelField(title="值12", align=2, sort=18)
	public String getVar12() {
		return var12;
	}

	public void setVar12(String var12) {
		this.var12 = var12;
	}
	
	@ExcelField(title="值13", align=2, sort=19)
	public String getVar13() {
		return var13;
	}

	public void setVar13(String var13) {
		this.var13 = var13;
	}
	
	@ExcelField(title="值14", align=2, sort=20)
	public String getVar14() {
		return var14;
	}

	public void setVar14(String var14) {
		this.var14 = var14;
	}
	
	@ExcelField(title="值15", align=2, sort=21)
	public String getVar15() {
		return var15;
	}

	public void setVar15(String var15) {
		this.var15 = var15;
	}
	
	@ExcelField(title="提交流程申请id", align=2, sort=22)
	public String getFlowapplyid() {
		return flowapplyid;
	}

	public void setFlowapplyid(String flowapplyid) {
		this.flowapplyid = flowapplyid;
	}
	
	@ExcelField(title="流程申请id", align=2, sort=23)
	public String getLeipiflowid() {
		return leipiflowid;
	}

	public void setLeipiflowid(String leipiflowid) {
		this.leipiflowid = leipiflowid;
	}

	public Flowapply getFlowapply() {
		return flowapply;
	}

	public void setFlowapply(Flowapply flowapply) {
		this.flowapply = flowapply;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getMtd() {
		return mtd;
	}

	public void setMtd(String mtd) {
		this.mtd = mtd;
	}

	public String getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }

	public String getFlowstatus() {
		return flowstatus;
	}

	public void setFlowstatus(String flowstatus) {
		this.flowstatus = flowstatus;
	}
}