/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.flow.entity;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.ActEntity;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.leipiflow.entity.*;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyFile;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 我的申请Entity
 * @author cqj
 * @version 2016-12-08
 */
public class Flowapply extends ActEntity<Flowapply> {
	
	private static final long serialVersionUID = 1L;
	private String processInstanceId; // 流程实例编号
	private String templatecontentid;		// 模板内容id
	private String templateid;		// 模板id
	private String flowid;		// 流程id
	private Integer showcolumn;		// 显示列数
	private List<Templatecontent> templatecontentList = Lists.newArrayList();		// 子表列表
	private List<TemplateDetail> templatedetailList = Lists.newArrayList();		// 子表列表
	
	private String ids;
	private Date createDateStart;
	private Date createDateEnd;
	
	private String comment;
	private String templatehtml;
	private String templateviewhtml;
	private String detailJsonArray;

	private String var1;
	private String var2;
	
	private Office company;	// 归属公司
	private Office office;	// 归属部门
	private Office dept;	// 归属岗位
	private Integer flowtype=0; //0流程1申请

	private Date startdate;//查询用
	private Date enddate;//查询用
    private String runprocess_upid;//查询用
    private String runflow_status;//查询用
	private String runflow_status_useful;//查询用
	private TemplateDetail templateDetailTemp;
	private String hasmyprocesstodo = "0";// 此申请是否有未处理环节 查询用

	//-- 临时属性 --//
	// 流程任务
	private Task task;
	private Map<String, Object> variables;
	// 运行中的流程实例
	private ProcessInstance processInstance;
	// 历史的流程实例
	private HistoricProcessInstance historicProcessInstance;
	// 流程定义
	private ProcessDefinition processDefinition;
	private LeipiFlow leipiFlow;
	private LeipiFlowProcess leipiFlowProcess;
	private LeipiRun leipiRun;
	private LeipiRunProcess leipiRunProcess;
	private String keyword;//编号
	private String keyword1;//发起人
	private String applystatetype;
	private Integer pid;
	private List<OaNotifyFile> oaNotifyFileList = Lists.newArrayList();

	public Flowapply() {
		super();
	}

	public Flowapply(String id){
		super(id);
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public User getUser() {
		return createBy;
	}
	
	public void setUser(User user) {
		this.createBy = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public String getIds() {
		List<String> idList = Lists.newArrayList();
		if (StringUtils.isNotBlank(ids)){
			String ss = ids.trim().replace("　", ",").replace(" ",",").replace("，", ",").replace("'", "");
			for(String s : ss.split(",")) {
//				if(s.matches("\\d*")) {
					idList.add("'"+s+"'");
//				}
			}
		}
		return StringUtils.join(idList, ",");
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Date getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(Date createDateStart) {
		this.createDateStart = createDateStart;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	@ExcelField(title="模板内容id", align=2, sort=7)
	public String getTemplatecontentid() {
		return templatecontentid;
	}

	public void setTemplatecontentid(String templatecontentid) {
		this.templatecontentid = templatecontentid;
	}
	
	@ExcelField(title="模板id", align=2, sort=8)
	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}
	
	@ExcelField(title="流程id", align=2, sort=9)
	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}
	
	@ExcelField(title="显示列数", align=2, sort=10)
	public Integer getShowcolumn() {
		return showcolumn;
	}

	public void setShowcolumn(Integer showcolumn) {
		this.showcolumn = showcolumn;
	}
	
	public List<Templatecontent> getTemplatecontentList() {
		return templatecontentList;
	}

	public void setTemplatecontentList(List<Templatecontent> templatecontentList) {
		this.templatecontentList = templatecontentList;
	}

	public String getTemplatehtml() {
		return templatehtml;
	}

	public void setTemplatehtml(String templatehtml) {
		this.templatehtml = templatehtml;
	}

	public String getDetailJsonArray() {
		return detailJsonArray;
	}

	public void setDetailJsonArray(String detailJsonArray) {
		this.detailJsonArray = detailJsonArray;
	}

	public String getTemplateviewhtml() {
		return templateviewhtml;
	}

	public void setTemplateviewhtml(String templateviewhtml) {
		this.templateviewhtml = templateviewhtml;
	}

	public LeipiFlow getLeipiFlow() {
		return leipiFlow;
	}

	public void setLeipiFlow(LeipiFlow leipiFlow) {
		this.leipiFlow = leipiFlow;
	}

	public LeipiFlowProcess getLeipiFlowProcess() {
		return leipiFlowProcess;
	}

	public void setLeipiFlowProcess(LeipiFlowProcess leipiFlowProcess) {
		this.leipiFlowProcess = leipiFlowProcess;
	}

	public LeipiRun getLeipiRun() {
		return leipiRun;
	}

	public void setLeipiRun(LeipiRun leipiRun) {
		this.leipiRun = leipiRun;
	}

	public LeipiRunProcess getLeipiRunProcess() {
		return leipiRunProcess;
	}

	public void setLeipiRunProcess(LeipiRunProcess leipiRunProcess) {
		this.leipiRunProcess = leipiRunProcess;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getApplystatetype() {
		return applystatetype;
	}

	public void setApplystatetype(String applystatetype) {
		this.applystatetype = applystatetype;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
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

	public Integer getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(Integer flowtype) {
		this.flowtype = flowtype;
	}

	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getVar2() {
		return var2;
	}

	public void setVar2(String var2) {
		this.var2 = var2;
	}

	public List<OaNotifyFile> getOaNotifyFileList() {
		return oaNotifyFileList;
	}

	public void setOaNotifyFileList(List<OaNotifyFile> oaNotifyFileList) {
		this.oaNotifyFileList = oaNotifyFileList;
	}

	public String getOaNotifyFileUrls() {
		return Collections3.extractToString(oaNotifyFileList, "fileurl", ",") ;
	}
	public String getOaNotifyFileUrls2() {
		return "'"+Collections3.extractToString(oaNotifyFileList, "fileurl", "','")+"'" ;
	}
	public void setOaNotifyFileUrls(String oaNotifyFile) {
		this.oaNotifyFileList = Lists.newArrayList();
		for (String url : StringUtils.split(oaNotifyFile, ",")){
			OaNotifyFile entity = new OaNotifyFile();
			entity.setId(IdGen.uuid());
			OaNotify oaNotify=new OaNotify();
			oaNotify.setId(this.getId());
			entity.setOaNotify(oaNotify);
			entity.setFileurl(url);
			this.oaNotifyFileList.add(entity);
		}
	}

	public List<TemplateDetail> getTemplatedetailList() {
		return templatedetailList;
	}

	public void setTemplatedetailList(List<TemplateDetail> templatedetailList) {
		this.templatedetailList = templatedetailList;
	}

    public String getRunprocess_upid() {
        return runprocess_upid;
    }

    public void setRunprocess_upid(String runprocess_upid) {
        this.runprocess_upid = runprocess_upid;
    }

    public String getRunflow_status() {
        return runflow_status;
    }

    public void setRunflow_status(String runflow_status) {
        this.runflow_status = runflow_status;
    }

	public TemplateDetail getTemplateDetailTemp() {
		return templateDetailTemp;
	}

	public void setTemplateDetailTemp(TemplateDetail templateDetailTemp) {
		this.templateDetailTemp = templateDetailTemp;
	}

	public String getRunflow_status_useful() {
		return runflow_status_useful;
	}

	public void setRunflow_status_useful(String runflow_status_useful) {
		this.runflow_status_useful = runflow_status_useful;
	}

	public String getHasmyprocesstodo() {
		return hasmyprocesstodo;
	}

	public void setHasmyprocesstodo(String hasmyprocesstodo) {
		this.hasmyprocesstodo = hasmyprocesstodo;
	}
}