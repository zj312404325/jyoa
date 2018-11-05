<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>已办任务</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
        laydate({
            elem: '#startdate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' , //响应事件。如果没有传入event，则按照默认的click
            format: 'YYYY/MM/DD',
            istime: false,
        });
        laydate({
            elem: '#enddate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' , //响应事件。如果没有传入event，则按照默认的click
            format: 'YYYY/MM/DD',
            istime: false,
        });
	});
	function add(flowid,formid){
		window.location.href="${ctx}/leipiflow/leipiFlowApply/form?formid="+formid+"&flowid="+flowid;
		//openDialog("发起流程","${ctx}/oa/oaNotify/form","60%", "80%","");
	}
	function page(n,s){
        	location = '${ctx}/leipiflow/leipiFlowApply/myLeipiFlowById?isadmin=${isadmin}&flowid=${flowid}&pageNo='+n+'&pageSize='+s;
        }
	</script>
</head>
<body>
	<div class="ibox">
	<%--<div class="ibox-title">
		<h5>我的申请 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>

		</div>
	</div>--%>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<form:form id="searchForm" modelAttribute="flowapply" action="${ctx}/leipiflow/leipiFlowApply/myLeipiFlowById?isadmin=${isadmin}&flowid=${flowid}" method="get" class="form-inline">
		<input id="isadmin" name="isadmin" type="hidden" value="${isadmin}"/>
		<input id="flowid" name="flowid" type="hidden" value="${flowid}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<%--<c:if test="${isadmin=='1' }">--%>
				<%--<span>发起人/被申请人：</span>
				<form:input path="keyword" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>--%>
				<span>创建日期：</span>
				<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
					   value="<fmt:formatDate value="${flowapply.startdate}" pattern="yyyy-MM-dd"/>"  />
				至
				<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
					   value="<fmt:formatDate value="${flowapply.enddate}" pattern="yyyy-MM-dd"/>"  />
			<%--</c:if>--%>

				<div class="pull-right" style="padding-left: 20px;">
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
				</div>
		 </div>

	</form:form>
<br/>
	
	<!-- 工具栏 -->
	<c:if test="${isadmin=='1' }">
	<br/>
	</c:if>
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<%--<c:if test="${isself}">
			    <button class="btn btn-success btn-sm" data-toggle="tooltip" data-placement="left" onclick="add('8b1c315d4ac4464abc4388e659ad9eb3','bc03c751ed6746dbb5eeb78a26648186')" title="新增"><i class="fa fa-plus"></i> 新增</button>
			</c:if>--%>
				<button class="btn btn-success btn-sm" data-toggle="tooltip" data-placement="left" onclick="add('${myLeipiFlow.id}','${myLeipiFlow.formid}')" title="新增"><i class="fa fa-plus"></i> 新增</button>
				<%--<table:addRow url="${ctx}/leipiflow/leipiFlowApply/form?formid=${myLeipiFlow.formid}&flowid=${myLeipiFlow.id}" title=""></table:addRow>--%>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		</div>
		<c:if test="${isadmin=='1' }">

		</c:if>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<!-- <th>标题</th> -->
				<th>名称</th>
				<th>标题</th>
				<th>当前环节</th>
				<!-- <th>任务内容</th>-->
				<!-- <th>流程版本</th> -->
				<th>发起人</th>
				<c:if test="${isVar1Show}"><th>被申请人</th></c:if>
				<th>发起时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="map">
				<%-- <c:set var="task" value="${act.histTask}" />
				<c:set var="vars" value="${act.vars}" />
				<c:set var="procDef" value="${act.procDef}" /> --%><%--
				<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
				<%-- <c:set var="status" value="${act.status}" /> --%>
				<tr>
					<%-- <td>
						<a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}</a>
					</td> --%>
					<td>${map.leipiFlow.flowname}</td>
						<td>${map.var2}</td>
					<td>
					${map.leipiFlowProcess.processName}
						<%-- <a  href="javascript:openDialogView('跟踪','${ctx}/act/task/trace/photo/${map.PROCESSDEFINITIONID}/${map.EXECUTIONID}','1000px', '600px')">${map.TASKNAME}</a> --%>
						<%--<a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}"></a>
						<a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">${task.name}</a>
						<a target="_blank" href="${ctx}/act/task/trace/info/${task.processInstanceId}">${task.name}</a> --%>
					</td>
					<!-- <td>${task.description}</td>-->
					<td>${fns:getUserById(map.createBy.id).name}</td>
						<c:if test="${isVar1Show}"><td>${map.var1}</td></c:if>
					<td><fmt:formatDate value="${map.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<%-- <td>${map.createBy.name}</td> --%>
					<td>
						<c:choose>
						<c:when test="${map.leipiRun.status == 0}">运行中</c:when>
						<c:when test="${map.leipiRun.status == 1}">已完成</c:when>
						<c:when test="${map.leipiRun.status == 2}">已撤回</c:when>
						<%-- <c:otherwise>
							已完成
						</c:otherwise> --%>
						</c:choose>
					</td>
					<td>
						<a href="#" onclick="openDialogView('查看我的申请-${fns:abbr(map.leipiFlow.flowname,16)}', '${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${map.id}&flag=1','1000px', '80%')"  >详情</a>
						&nbsp;&nbsp;<c:if test="${map.leipiRun.status == 0}"><%--<a href="${ctx}/leipiflow/leipiFlowApply/unDoFlowApply?id=${map.id}" onclick="return confirmx('确认要撤销吗？', this.href)">撤销</a>--%>
							<a href="${ctx}/leipiflow/leipiFlowApply/delete?id=${map.id}" onclick="return confirmx('确认要删除吗？', this.href)">删除</a></c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</body>
</html>