<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同到期人员考核申请管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
	    $('#contentTable thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
	    	  $('#contentTable tbody tr td input.i-checks').iCheck('check');
	    	});
	
	    $('#contentTable thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
	    	  $('#contentTable tbody tr td input.i-checks').iCheck('uncheck');
	    	});
	    
	});

	function changeState(obj){
		// var url = $(this).attr('data-url');
		  var str="";
		  var ids="";
		  $("#contentTable tbody tr td input.i-checks:checkbox").each(function(){
		    if(true == $(this).is(':checked')){
		      str+=$(this).attr("id")+",";
		    }
		  });
		  if(str.substr(str.length-1)== ','){
		    ids = str.substr(0,str.length-1);
		  }
		  if(ids == ""){
			top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
			return;
		  }
			top.layer.confirm('确认要审核数据吗?', {icon: 3, title:'系统提示'}, function(index){
			window.location = "/a/checkmodel/contractExpirate/changeState?ids="+ids+"&status="+obj;
		    top.layer.close(index);
		});
		 

	}
</script>
</head>
<body class="">
	<div class="wrapper wrapper-content">
	<!-- <div class="ibox"> -->
	<!-- <div class="ibox-title">
		<h5>合同到期人员考核申请列表 </h5>
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
	</div>-->
    
    <!-- <div class="ibox-content"> -->
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="contractExpirate" action="${ctx}/checkmodel/contractExpirate/managerlist?type=1" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>员工编号：</span>
			<form:input path="userno" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
			<span>员工姓名：</span>
			<form:input path="username" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
		 </div>
	<br/>
	</form:form>
	</div>
	</div>
	<br/>
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<%-- <shiro:hasPermission name="checkmodel:contractExpirate:add">
				<table:addRow url="${ctx}/checkmodel/contractExpirate/form" title="合同到期人员考核申请"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="checkmodel:contractExpirate:del">
				<table:delRow url="${ctx}/checkmodel/contractExpirate/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission> --%>
			
             <button class="btn btn-white btn-sm" onclick="changeState('0')" data-toggle="tooltip" data-placement="left"><i class="fa fa-edit"> 续订合同</i></button>
             <button class="btn btn-white btn-sm" onclick="changeState('1')" data-toggle="tooltip" data-placement="left"><i class="fa fa-edit"> 终止合同</i></button> 
             <button class="btn btn-white btn-sm" onclick="changeState('2')" data-toggle="tooltip" data-placement="left"><i class="fa fa-edit"> 其他</i></button>          
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column username">被考核人名字</th>
				<th  class="sort-column officename">被考核人部门</th>
				<th  class="sort-column score">得分</th>
				<th  class="sort-column checkusername">考核人姓名</th>
				<th  class="sort-column recommend">考核人建议</th>
				<th  class="sort-column create_date">创建时间</th>
				<th  class="sort-column status">状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contractExpirate">
			<tr>
				<td> <input type="checkbox" id="${contractExpirate.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看合同到期人员考核申请', '${ctx}/checkmodel/contractExpirate/form?id=${contractExpirate.id}','900px', '80%')">
					${contractExpirate.username}</a>
				</td>
				<td>
					${contractExpirate.officename}
				</td>
				<td>
					${contractExpirate.score}
				</td>
				<td>
					${contractExpirate.checkusername}
				</td>
				<td>
					${fns:getDictLabel(contractExpirate.recommend, 'contractExpirateRecommend', '')}
				</td>
				<td>
					<fmt:formatDate value="${contractExpirate.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(contractExpirate.status, 'contractExpirateRecommend', '')}
				</td>
				<td>
					<shiro:hasPermission name="checkmodel:contractExpirate:view">
						<a href="#" onclick="openDialogView('查看合同到期人员考核申请', '${ctx}/checkmodel/contractExpirate/form?id=${contractExpirate.id}&type=1','900px', '80%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="checkmodel:contractExpirate:edit">
						<a href="#" onclick="openDialog('修改合同到期人员考核申请', '${ctx}/checkmodel/contractExpirate/form?id=${contractExpirate.id}&type=1&ismanage=1','900px', '80%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
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
</body>
</html>