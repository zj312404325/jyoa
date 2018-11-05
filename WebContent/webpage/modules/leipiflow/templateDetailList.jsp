<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
            laydate({
                elem: '#startdate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#enddate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
		});

        function add(flowid){
            //openDialog("新增传阅","${ctx}/oa/oaNotify/form","60%", "80%","");
            window.location.href="${ctx}/leipiflow/leipiFlowApply/form?flowid="+flowid;
        }
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">

    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
		<c:set var="mtd" value="${templateDetail.mtd}" />
	<form:form id="searchForm" modelAttribute="templateDetail" action="${ctx}/leipiflow/templateDetail/" method="post" class="form-inline">
		<input id="mtd" name="mtd" type="hidden" value="${mtd}"/>
		<input id="isadmin" name="isadmin" type="hidden" value="${isadmin}"/>
		<input id="flowid" name="flowid" type="hidden" value="${flowid}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->

			<div class="form-group">

				<span>姓名：</span>
				<form:input path="var1" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
        <c:if test="${mtd=='recieve'}">
            <span>发起人：</span>
            <form:input path="createusername" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
        </c:if>
				<span>申请时间：</span>
				<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
					   value="<fmt:formatDate value="${templateDetail.startdate}" pattern="yyyy-MM-dd"/>"/>
				<span>至</span>
				<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
					   value="<fmt:formatDate value="${templateDetail.enddate}" pattern="yyyy-MM-dd"/>"/>


				<div class="pull-right" style="padding-left: 20px">
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
			<c:if test="${mtd!='recieve'}">
				<button class="btn btn-success btn-sm" data-toggle="tooltip" data-placement="left" onclick="add('${flowid}')" title="新增申请">
					<i class="fa fa-plus"></i> 新增</button>
			</c:if>
	       <%--<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>--%>
		
			</div>

	</div>
	</div>
	
	<c:if test="${flowid == SALARY_FLOW_ID || flowid == REWARD_FLOW_ID}"><%--薪资调整 奖惩调整--%>
	<table id="contentTable" class="table table-bordered table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<c:if test="${mtd!='recieve'}">
					<th  class="">姓名</th>
				</c:if>
				<c:if test="${mtd=='recieve'}">
					<th  class="">被申请人</th>
				</c:if>
				<th  class="">当前环节</th>
				<c:if test="${mtd=='recieve'}">
					<th  class="">发起人</th>
				</c:if>
				<th  class="">申请时间</th>
				<th  class="">状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="flowapply">
		<c:forEach items="${flowapply.templatedetailList}" var="templateDetail" varStatus="st">
			<tr>
				<td>
						${templateDetail.var1}
				</td>
				<c:if test="${st.count == 1}">
				<td rowspan="${fn:length(flowapply.templatedetailList)}">
						${flowapply.leipiFlowProcess.processName}
				</td>
				</c:if>

				<c:if test="${mtd=='recieve'}">
					<td>
							${fns:getUserById(templateDetail.createBy.id).name}
					</td>
				</c:if>
				<c:if test="${st.count == 1}">
					<td rowspan="${fn:length(flowapply.templatedetailList)}">
						<fmt:formatDate value="${templateDetail.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</c:if>

				<c:if test="${st.count == 1}">
					<td rowspan="${fn:length(flowapply.templatedetailList)}">
						<c:choose>
							<c:when test="${flowapply.leipiRun.status == 0}">运行中</c:when>
							<c:when test="${flowapply.leipiRun.status == 1}">已完成</c:when>
							<c:when test="${flowapply.leipiRun.status == 2}">已撤回</c:when>
						</c:choose>
					</td>
				</c:if>
				<c:if test="${st.count == 1}">
					<td rowspan="${fn:length(flowapply.templatedetailList)}">
						<c:if test="${mtd!='recieve'}">
							<a href="#" onclick="openDialogView('查看详情', '${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${flowapply.id}&flag=1','60%', '80%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>查看详情</a>
						</c:if>
						<c:if test="${mtd=='recieve'}">
						 	<c:if test="${flowapply.leipiRun.status=='0' && flowapply.hasmyprocesstodo == '1' }"><%--${flowapply.leipiRunProcess.upid}--%>
								<a href="${ctx}/leipiflow/leipiFlowApply/myLeipiEdit?id=${flowapply.id}&flag=1">任务办理</a>
							</c:if>
							<c:if test="${flowapply.leipiRun.status=='0' && flowapply.hasmyprocesstodo == '0' }"><%--${flowapply.leipiRunProcess.upid}--%>
								<a href="${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${flowapply.id}&flag=1">查看详情</a>
							</c:if>
							<c:if test="${flowapply.leipiRun.status!='0'}"><%--${flowapply.leipiRunProcess.upid}--%>
								<a href="${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${flowapply.id}&flag=1">查看详情</a>
							</c:if>
						</c:if>
					</td>
				</c:if>

			</tr>
		</c:forEach>
		</c:forEach>
		<%--<c:forEach items="${page.list}" var="templateDetail">
			<tr>
				<td>
					${templateDetail.flowapply.leipiFlowProcess.processName}
				</td>
				<td>
					${templateDetail.var1}
				</td>
				<c:if test="${mtd=='recieve'}">
					<td>
							${fns:getUserById(templateDetail.createBy.id).name}
					</td>
				</c:if>
                <td>
                    <fmt:formatDate value="${templateDetail.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
				<td>
					<c:choose>
						<c:when test="${templateDetail.flowapply.leipiRun.status == 0}">运行中</c:when>
						<c:when test="${templateDetail.flowapply.leipiRun.status == 1}">已完成</c:when>
						<c:when test="${templateDetail.flowapply.leipiRun.status == 2}">已撤回</c:when>
					</c:choose>
				</td>
				<td>
					<c:if test="${mtd!='recieve'}">
						<a href="#" onclick="openDialog('查看详情', '${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${templateDetail.flowapply.id}&flag=1','60%', '80%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>查看详情</a>
					</c:if>
					<c:if test="${mtd=='recieve'}">
						<c:if test="${templateDetail.flowapply.leipiRun.status=='0'}">
							<a href="${ctx}/leipiflow/leipiFlowApply/myLeipiEdit?id=${templateDetail.flowapply.id}&flag=1">任务办理</a>
						</c:if>
					</c:if>
				</td>
			</tr>
		</c:forEach>--%>
		</tbody>
	</table>
	</c:if>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>