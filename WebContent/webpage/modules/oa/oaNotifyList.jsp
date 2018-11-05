<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>传阅管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			/*if("${oaNotify.readFlag}"=='0' && "${oaNotify.self}"=='true'){
				$("#searchForm").attr("action","${ctx}/oa/oaNotify/selfnoread");
			}*/
			
		});
		function add(){
			openDialog("新增传阅","${ctx}/oa/oaNotify/form","60%", "80%","");
		}
		
		
     </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox">
<div class="ibox-title">
		<h5>传阅列表 </h5>
		<!-- <div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="form_basic.html#">
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
		</div> -->
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
		<form:form id="searchForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/${oaNotify.self?'self':''}" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>标题：</span>
				<form:input path="title" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
			<span>发起人：</span>
				<form:input path="createBy.name" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
			<span>类型：</span>
				<form:select path="type"  class="form-control m-b">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			<span>查阅状态：</span>
			<form:select path="readFlag"  class="form-control m-b">
				<form:option value="" label="全部"/>
				<form:options items="${fns:getDictList('oa_notify_read')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>

			<c:if test="${!requestScope.oaNotify.self}"><span>状态：</span>
				<form:radiobuttons path="status" class="i-checks" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</c:if>
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
			<c:if test="${!requestScope.oaNotify.self}">
			<shiro:hasPermission name="oa:oaNotify:add">
			    <button class="btn btn-success btn-sm" data-toggle="tooltip" data-placement="left" onclick="add()" title="新增传阅"><i class="fa fa-plus"></i> 新增</button>
				<%-- <table:addRow url="${ctx}/oa/oaNotify/form" title="传阅"></table:addRow> --%><!-- 增加按钮 -->
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="oa:oaNotify:edit">
			    <table:editRow url="${ctx}/oa/oaNotify/form" id="contentTable"  title="传阅" width="60%" height="80%"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>--%>
			<shiro:hasPermission name="oa:oaNotify:del">
				<table:delRow url="${ctx}/oa/oaNotify/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oa:oaNotify:import">
				<table:importExcel url="${ctx}/oa/oaNotify/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oa:oaNotify:export">
	       		<table:exportExcel url="${ctx}/oa/oaNotify/export"></table:exportExcel><!-- 导出按钮 -->
	       </shiro:hasPermission>
	      
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		 </c:if>
			</div>
		
	</div>
	</div>
	
	
	
	
	<table id="contentTable" class="table table-bordered table-condensed  dataTables-example dataTable no-footer">
		<thead>
			<tr>
				<c:if test="${!requestScope.oaNotify.self}">
				<th> <input type="checkbox" class="i-checks"></th>
				</c:if>
				<th>标题</th>
				<th>类型</th>
				<c:if test="${!requestScope.oaNotify.self}"><th>状态</th></c:if>
				<th>查阅状态</th>
				<c:if test="${!requestScope.oaNotify.self}"><th>确认状态</th></c:if>
				<c:if test="${requestScope.oaNotify.self}"><th>发件人</th></c:if>
				<th>传阅时间</th>
				<c:if test="${!requestScope.oaNotify.self}">
				<th>操作</th>
				</c:if>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaNotify">
			<tr class="<c:if test="${oaNotify.readFlag=='1'}">bg-warning</c:if><c:if test="${oaNotify.readFlag=='2'}">bg-success</c:if>">
			    <c:if test="${!requestScope.oaNotify.self}">
				<td> <input type="checkbox" id="${oaNotify.id}" class="i-checks"></td>
				</c:if>
				<td><%-- <a  href="#" onclick="openDialogView('查看传阅', '${ctx}/oa/oaNotify/${requestScope.oaNotify.self?'view':'form'}?id=${oaNotify.id}','1000px', '800px')">
					${fns:abbr(oaNotify.title,50)}
				</a> --%>
				<%-- <a class="frameItem" href="javascript:" vl="${ctx}/oa/oaNotify/${requestScope.oaNotify.self?'view':'form'}?id=${oaNotify.id}" vl2="查看传阅-${fns:abbr(oaNotify.title,16)}" data-index="${oaNotify.id}" onclick="parent.changeOaNotifyCount('${oaNotify.id}',0)"> --%>
				<a class="frameItem" href="javascript:" vl="${ctx}/oa/oaNotify/${requestScope.oaNotify.self?'view':'form'}?id=${oaNotify.id}" vl2="查看传阅-${fns:abbr(oaNotify.title,16)}" data-index="${oaNotify.id}" >
					${fns:abbr(oaNotify.title,50)}
				</a></td>
				<td>
					${fns:getDictLabel(oaNotify.type, 'oa_notify_type', '')}
				</td>
				<c:if test="${!requestScope.oaNotify.self}">
				<td>
					${fns:getDictLabel(oaNotify.status, 'oa_notify_status', '')}
				</td>
				</c:if>
				
				<c:if test="${!requestScope.oaNotify.self}">
				<td>
						${oaNotify.readNum} / ${oaNotify.readNum + oaNotify.unReadNum}
				</td>
				</c:if>
				<td>
					<c:if test="${requestScope.oaNotify.self}">
						${fns:getDictLabel(oaNotify.readFlag, 'oa_notify_read', '')}
					</c:if>
					<c:if test="${!requestScope.oaNotify.self}">
						${oaNotify.commentNum} / ${oaNotify.readNum + oaNotify.unReadNum}
					</c:if>
				</td>
				<c:if test="${requestScope.oaNotify.self}"><td>${fns:getUserById(oaNotify.createBy).name}</td></c:if>
				<td>
					<fmt:formatDate value="${oaNotify.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<c:if test="${!requestScope.oaNotify.self}">
				<td>
					<%-- <shiro:hasPermission name="oa:oaNotify:view">
						<a href="#" title="查看传阅" onclick="openDialogView('查看传阅', '${ctx}/oa/oaNotify/selfview?id=${oaNotify.id}','1000px', '800px')" class="btn btn-info btn-xs btn-circle" ><i class="fa fa-search-plus"></i></a>
						<a class="frameItem btn btn-info btn-xs btn-circle" href="javascript:" title="查看传阅" vl="${ctx}/oa/oaNotify/${requestScope.oaNotify.self?'view':'form'}?id=${oaNotify.id}" vl2="查看传阅-${fns:abbr(oaNotify.title,16)}" data-index="${oaNotify.id}" onclick="parent.changeOaNotifyCount('${oaNotify.id}',0)"><i class="fa fa-search-plus"></i></a>
					</shiro:hasPermission> --%>
					<c:if test="${oaNotify.status ne '1'}">
					<shiro:hasPermission name="oa:oaNotify:edit">
    					<a href="#" title="修改传阅" onclick="openDialog('修改传阅', '${ctx}/oa/oaNotify/form?id=${oaNotify.id}','60%', '80%')" class="btn btn-success btn-xs btn-circle" ><i class="fa fa-edit"></i></a>
    					<%-- <a class="frameItem btn btn-success btn-xs btn-circle" href="javascript:" title="修改传阅" vl="${ctx}/oa/oaNotify/form?id=${oaNotify.id}" vl2="修改传阅-${fns:abbr(oaNotify.title,16)}" data-index="${oaNotify.id}" ><i class="fa fa-edit"></i></a> --%>
    				</shiro:hasPermission>
    				</c:if>

						<c:if test="${oaNotify.status eq '1'}">
							<shiro:hasPermission name="oa:oaNotify:view">
								<a class="frameItem btn btn-info btn-xs btn-circle" href="javascript:" vl="${ctx}/oa/oaNotify/${requestScope.oaNotify.self?'view':'form'}?id=${oaNotify.id}" vl2="查看传阅-${fns:abbr(oaNotify.title,16)}" data-index="${oaNotify.id}" ><i class="fa fa-search-plus"></i></a>
								<%-- <a class="frameItem btn btn-success btn-xs btn-circle" href="javascript:" title="修改传阅" vl="${ctx}/oa/oaNotify/form?id=${oaNotify.id}" vl2="修改传阅-${fns:abbr(oaNotify.title,16)}" data-index="${oaNotify.id}" ><i class="fa fa-edit"></i></a> --%>
							</shiro:hasPermission>
						</c:if>

    				<%--<c:if test="${oaNotify.status eq '1'}">
					<shiro:hasPermission name="oa:oaNotify:edit">
    					<a class="frameItem btn btn-success btn-xs btn-circle" href="javascript:" title="修改传阅" vl="${ctx}/oa/oaNotify/${requestScope.oaNotify.self?'view':'form'}?id=${oaNotify.id}" vl2="OA传阅-${fns:abbr(oaNotify.title,16)}" data-index="${oaNotify.id}" onclick="parent.changeOaNotifyCount('${oaNotify.id}',0)"><i class="fa fa-edit"></i></a>
    					&lt;%&ndash; <a class="frameItem btn btn-success btn-xs btn-circle" href="javascript:" title="修改传阅" vl="${ctx}/oa/oaNotify/form?id=${oaNotify.id}" vl2="修改传阅-${fns:abbr(oaNotify.title,16)}" data-index="${oaNotify.id}" ><i class="fa fa-edit"></i></a> &ndash;%&gt;
    				</shiro:hasPermission>
    				</c:if>--%>
    				<shiro:hasPermission name="oa:oaNotify:del">
						<a href="${ctx}/oa/oaNotify/delete?id=${oaNotify.id}" title="删除传阅" onclick="return confirmx('确认要删除该传阅吗？', this.href)"   class="btn btn-danger btn-xs btn-circle"><i class="fa fa-trash"></i></a>
					</shiro:hasPermission>
				</td>
				</c:if>
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