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
            elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' , //响应事件。如果没有传入event，则按照默认的click
            format: 'YYYY/MM/DD hh:mm:ss',
            istime: true,
        });
        laydate({
            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' , //响应事件。如果没有传入event，则按照默认的click
            format: 'YYYY/MM/DD hh:mm:ss',
            istime: true,
        });
				
	
	
	});
	
	function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	//location = '${ctx}/leipiflow/leipiFlowApply/myLeipiFlow/?pageNo='+n+'&pageSize='+s;
        }
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>我的申请 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="flowapply" action="${ctx}/leipiflow/leipiFlowApply/myLeipiFlow/" method="get" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<%-- <label>流程类型：&nbsp;</label>
			<form:select path="procDefKey" class="form-control">
				<form:option value="" label="全部流程"/>
				<form:options items="${fns:getDictList('act_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select> --%>
			<label>流程状态：&nbsp;</label>
			<select id="applystatetype" name="applystatetype" class="form-control">
				<option value=""  >全部</option>
				<option value="0" <c:if test="${flowapply.applystatetype=='0'}">selected</c:if> >运行中</option>
				<option value="2" <c:if test="${flowapply.applystatetype=='2'}">selected</c:if> >已撤销</option>
				<option value="1" <c:if test="${flowapply.applystatetype=='1'}">selected</c:if> >已完成</option><%-- <c:if test="${applystatetype=='2'}">selected</c:if> --%>
			</select>
			<label>流程编号：</label>
			<form:input path="keyword" htmlEscape="false"  class="form-control" maxlength="20"/>
				<label>标题：</label>
				<form:input path="var2" htmlEscape="false"  class="form-control" maxlength="20"/>
			<!-- <label>时间：</label> -->
			<%-- <input id="beginDate"  name="beginDate"  type="text" readonly="readonly" maxlength="20"  class="laydate-icon form-control layer-date input-sm"
				value="<fmt:formatDate value="" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
				　--　
			<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20"  class="laydate-icon form-control layer-date input-sm"
				value="<fmt:formatDate value="" pattern="yyyy-MM-dd HH:mm:ss"/>"/> --%>
				<div class="pull-right" style="padding-left: 20px;">
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
				</div>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<!-- <th>标题</th> -->
				<th>流程编号</th>
				<th>标题</th>
				<th>流程名称</th>
				<th>当前环节</th>
				<!-- <th>任务内容</th>-->
				<!-- <th>流程版本</th> -->
				<!-- <th>流程发起人</th> -->
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
					<td>${map.pid}</td>
						<td>${map.var2}</td>
					<td>${map.leipiFlow.flowname}</td>
					<td>
					${map.leipiFlowProcess.processName}
						<%-- <a  href="javascript:openDialogView('跟踪','${ctx}/act/task/trace/photo/${map.PROCESSDEFINITIONID}/${map.EXECUTIONID}','1000px', '600px')">${map.TASKNAME}</a> --%>
						<%--<a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}"></a>
						<a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">${task.name}</a>
						<a target="_blank" href="${ctx}/act/task/trace/info/${task.processInstanceId}">${task.name}</a> --%>
					</td>
					<!-- <td>${task.description}</td>-->
					<%-- <td><b title='流程版本号'>V: ${map.version}</b></td> --%>
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
						<a class="frameItem" href="javascript:" vl="${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${map.id}&flag=1" vl2="我的申请-${fns:abbr(map.leipiFlow.flowname,16)}" data-index="${map.id}" >
							详情
						</a>
						&nbsp;&nbsp;&nbsp;
						<c:if test="${map.leipiRun.status == 0}"><%--<a href="${ctx}/leipiflow/leipiFlowApply/unDoFlowApply?id=${map.id}" onclick="return confirmx('确认要撤销吗？', this.href)">撤销</a>--%>
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
</div>
</body>
</html>