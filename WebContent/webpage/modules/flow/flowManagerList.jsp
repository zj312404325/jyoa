<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>模板创建管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function addit(){
			location.href="${ctx}/flow/flowtemplate/form2";
		}
		function editit(){
			var size = $("#contentTable tbody tr td input.i-checks:checked").size();
			  if(size == 0 ){
					layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
					return;
			  }

			  if(size > 1 ){
					layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
					return;
			  }
			  var id =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("id");
			  location.href="${ctx}/flow/flowtemplate/form?id="+id;
		}
		
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>模板创建列表 </h5>
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
	<form:form id="searchForm" modelAttribute="flowtemplate" action="${ctx}/flow/flowtemplate/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
				<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="addit()" title="模板创建"><i class="fa fa-plus"></i> 添加</button>
			<shiro:hasPermission name="flow:flowtemplate:edit">
			    <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="editit()" title="模板创建"><i class="fa fa-plus"></i> 修改</button><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="flow:flowtemplate:del">
				<table:delRow url="${ctx}/flow/flowtemplate/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<!-- <div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div> -->
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column id">模板序号</th>
				<th  class="sort-column templatename">模板名称</th>
				<th  class="sort-column remarks">备注信息</th>
				<!-- <th  class="sort-column templatehtml">html代码</th> -->
				<th  class="sort-column showcolumn">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="flowtemplate">
			<tr>
				<td> <input type="checkbox" id="${flowtemplate.id}" class="i-checks"></td>
				<td>
					${flowtemplate.id}
				</td>
				<td>
					${flowtemplate.templatename}
				</td>
				<td>
					${flowtemplate.flowremarks}
				</td>
				<%-- <td>
					${flowtemplate.templatehtml}
				</td> --%>
				<td>
					<fmt:formatDate value="${flowtemplate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<%-- <shiro:hasPermission name="flow:flowtemplate:view">
						<a href="#" onclick="openDialogView('查看模板创建', '${ctx}/flow/flowtemplate/form?id=${flowtemplate.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission> --%>
					<shiro:hasPermission name="flow:flowtemplate:edit">
    					<a href="${ctx}/flow/flowtemplate/form?id=${flowtemplate.id}" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="flow:flowtemplate:del">
						<a href="${ctx}/flow/flowtemplate/delete?id=${flowtemplate.id}" onclick="return confirmx('确认要删除该模板创建吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
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