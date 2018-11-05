<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function selectone(id,name){
			 window.parent.$("#contractpartyid").val(id);
			 window.parent.$("#contractparty").val(name);
			 window.parent.layer.closeAll();
			 return false;
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<!-- <div class="ibox-title">
		<h5>供应商信息列表 </h5> 
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
		</div>
	</div> -->
    
    <div class="ibox-content">
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="customer" action="${ctx}/filemanagement/customer/selectlist" method="post" class="form-inline">
		<input id="usertype" name="usertype" type="hidden" value="${usertype}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		   <span>供应商名称：</span>
				<form:input path="customername" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
				
			  <div class="pull-right" style="padding-left: 20px;">
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  class="sort-column customername">供应商名称</th>
				<th  class="sort-column customerno">供应商编号</th>
				<th  class="sort-column customerclass">供应商类别</th>
				<th  class="sort-column contact">首要联系人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customer">
			<tr>
				<td>
					${customer.customername}
				</td>
				<td>
					${customer.customerno}
				</td>
				<td>
					${customer.customerclass}
				</td>
				<td>
					${customer.contact}
				</td>
				<td>
						<a href="javascript:" onclick="javascript:selectone('${customer.id}','${customer.customername}')" class="btn btn-info btn-xs" ><i class="fa fa-check"></i>选择</a>
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