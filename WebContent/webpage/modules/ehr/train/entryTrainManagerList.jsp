<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("a[name=gopermit]").live("click",function(){
					var url = $(this).attr("vl");
					top.layer.open({
					    type: 2, 
					    area: ['900px', '700px'],
					    title:"入职登记信息审核",
					    //ajaxData:{selectIds: $("#addoaNotifyRecordId").val()},
					    content: url ,
					    btn: ['审核并确定', '关闭']
			    	       ,yes: function(index,layero){ //或者使用btn1
			    	    	     var body = top.layer.getChildFrame('body', index);
			    	    	     var id = body.find('input[name=id]').val();
				  		         var position = body.find('input[name=position]').val();
				  		      	 var probationperiod = body.find('input[name=probationperiod]').val();
				  		    	 var probationperiodsalary = body.find('input[name=probationperiodsalary]').val();
				  		         
				  		    	$("#id").val(id);
				  		    	 $("#position").val(position);
				  		    	 $("#probationperiod").val(probationperiod);
				  		    	 $("#probationperiodsalary").val(probationperiodsalary);
				  		    	loading('正在提交，请稍等...');
				  		    	if($("#pForm").submit() ){
				  		        	// top.layer.close(index);//关闭对话框。
				  		        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
				  		         }
							},
			    	cancel: function(index){ //或者使用btn2
			    	           //按钮【按钮二】的回调
			    	       }
					}); 
			});
			
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>信息列表 </h5>
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
	<form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/trainManager" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		 
		 <div class="form-group">
			<span>姓名：</span>
				<form:input path="fullname" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
				<span>状态：</span>
				<form:select path="entrytrain"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_pass')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
	<%--<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="ehr:userInfo:add">
				<table:addRow url="${ctx}/ehr/userInfo/form" title="信息"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ehr:userInfo:edit">
			    <table:editRow url="${ctx}/ehr/userInfo/form" title="信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ehr:userInfo:del">
				<table:delRow url="${ctx}/ehr/userInfo/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ehr:userInfo:import">
				<table:importExcel url="${ctx}/ehr/userInfo/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ehr:userInfo:export">
	       		<table:exportExcel url="${ctx}/ehr/userInfo/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
		
			</div>
	</div>
	</div>--%>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<!-- <th> <input type="checkbox" class="i-checks"></th> -->
				<th  class="sort-column fullname">姓名</th>
				<th  class="sort-column status">审核状态</th>
				<th  class="">提交时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userInfo" varStatus="s">
			<tr>
				<%-- <td> <input type="checkbox" id="${userInfo.id}" class="i-checks"></td> --%>
				<td>
					${userInfo.fullname}
				</td>
				<td>
					<c:if test="${userInfo.entrytrain == 1}">已通过</c:if>
					<c:if test="${userInfo.entrytrain != 1}">未通过</c:if>
				</td>
				<td>
					<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${userInfo.entrytraindate}" />
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

<form action="${ctx}/ehr/entry/permitEntry" id="pForm" method="post">
<input type="hidden" name="id" id="id" value="" />
<input type="hidden" name="position" id="position" value="" />
<input type="hidden" name="probationperiod" id="probationperiod" value="" />
<input type="hidden" name="probationperiodsalary" id="probationperiodsalary" value="" />
</form>

</body>
</html>