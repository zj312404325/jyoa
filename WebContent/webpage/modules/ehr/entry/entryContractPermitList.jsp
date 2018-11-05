<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
				width='auto';
				height='auto';
			}else{//如果是PC端，根据用户设置的width和height显示。
				width='700px';
				height='200px';
			}
			
			$("a[name=permitYes]").on("click",function(){
				var infoid = $(this).attr("vl");
				$("#id").val(infoid);
				layer.open({
				    type: 1,  
				    area: [width, height],
				    title:"合同日期设置",
				    content: $("#setDate") ,
				    btn: ['确定', '关闭'],
				    yes: function(index, layero){
				    	if($("#startdate").val()==''||$("#enddate").val()==''){
				    		layer.alert("合同日期不能为空", {icon: 0, title:'警告'});
				    		return false;
				    	}
				    	loading('正在提交，请稍等...');
						$("#permitForm").submit();
					}
				}); 
			});
			laydate({
	            elem: '#startdate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
			laydate({
	            elem: '#enddate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>合同列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<!-- <a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a> -->
			<!-- <ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a> -->
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<%-- <div class="row">
	<div class="col-sm-12"> --%>
	<form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/permitlist" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		    <span>姓名：</span>
				<form:input path="fullname" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
			<span>审核状态：</span>
				<form:select path="statustemp"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('newemployee_contract_permit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>	
			<div class="pull-right" style="padding-left: 20px;">
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		 </div>	
	</form:form>
	<%-- <br/>
	</div>
	</div> --%>
	
	<!-- 工具栏 -->
	<!-- <div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
	</div>
	</div> -->
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<!-- <th> <input type="checkbox" class="i-checks"></th> -->
				<th  class="sort-column">序号</th>
				<th  class="sort-column status">审核状态</th>
				<th  class="sort-column fullname">姓名</th>
				<th  class="sort-column mobile">合同日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userInfo" varStatus="s">
			<tr>
				<%-- <td> <input type="checkbox" id="${userInfo.id}" class="i-checks"></td> --%>
				<td>
					${s.count}
				</td>
				<td>
				   <%-- <a  href="#" onclick="openDialogView('查看合同', '${ctx}/ehr/entryContract/form?id=${entryContract.id}','800px', '500px')">
					${entryContract.remarks}
				</a> --%>
					${fns:getDictLabel(userInfo.status, 'newemployee_contract_permit', '')}
				</td>
				<td>
				    ${userInfo.fullname}
				</td>
				<td>
					<c:if test="${userInfo.startdate== null}">尚未设定</c:if>
					<c:if test="${userInfo.startdate!= null}">
					<fmt:formatDate pattern="yyyy年MM月dd日" value="${userInfo.startdate}" />
                    		至
                    <fmt:formatDate pattern="yyyy年MM月dd日" value="${userInfo.enddate}" />
					</c:if>
				</td>
				<td>
						<a href="javascript:" vl="${userInfo.contract}" onclick="commonFileDownLoad(this)" class="btn btn-info btn-xs" ><i class="glyphicon glyphicon-save"></i>下载合同</a>
    					<a href="javascript:" name="permitYes" vl="${userInfo.id}" class="btn btn-success btn-xs" ><i class="glyphicon glyphicon-ok"></i>审核通过</a>
						<a href="${ctx}/ehr/entry/permitContract?id=${userInfo.id}&pt=no" onclick="return confirmx('确认审核不通过？', this.href)"   class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i>审核不通过</a>
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

<form:form id="permitForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/permitContract" method="post" class="form-inline">
<input id="id" name="id" type="hidden"  value="" >
<div class="row" id="setDate" style="width:660px; display:none;">
	<div class="col-md-12">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tr>
				<td class="width-15 active text-right">合同开始日期设置：</td>
				<td class="width-35"><input id="startdate" name="startdate" readonly="readonly" type="text" maxlength="20" class="laydate-icon form-control layer-date required" value="" aria-required="true"></td>
				<td class="width-15 active text-right">合同结束日期设置：</td>
				<td class="width-35"><input id="enddate" name="enddate" readonly="readonly" type="text" maxlength="20" class="laydate-icon form-control layer-date required" value="" aria-required="true"></td>
			</tr>
		</table>
	</div>
</div>
</form:form>

</body>
</html>