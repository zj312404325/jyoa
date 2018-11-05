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
					    area: ['900px', '80%'],
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
								/*loading('正在提交，请稍等...');
								if($("#pForm").submit() ){
									// top.layer.close(index);//关闭对话框。
									  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
								 }*/
                            $.post("${ctx}/ehr/entry/permitEntryAjax",$("#pForm").serialize(),function(data){
                                top.layer.closeAll();
                                var jsonData = jQuery.parseJSON(data);
                                if(jsonData.status == 'y'){
                                    //设置用户角色
                                    layer.open({
                                        type: 2,
                                        title: '角色设置',
                                        shadeClose: true,
                                        shade: 0.8,
                                        area : ['750px' , '450px'],
                                        offset : ['150px',''],
                                        content: '${ctx}/ehr/entry/setRole?id='+id
                                    });
                                }else{
                                    layer.alert(jsonData.info,{icon: 2});
                                }
                            });
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
	<form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		 
		 <div class="form-group">
			<span>姓名：</span>
				<form:input path="fullname" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
			 <span>部门：</span>
			 <sys:treeselect id="office" name="office.id" value="${userInfo.office.id}" labelName="office.name" labelValue="${userInfo.office.name}"
							 title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" notAllowSelectParent="false"/>
				<span>审核状态：</span>
				<form:select path="ispermit"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('newemployee_permit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<!-- <select id="status" name="status" class="form-control m-b">
					<option value="" selected="selected"></option>
					<option value="1">未审核</option>
					<option value="2">已审核</option>
				</select> -->
			<%-- <span>类型：</span>
				<form:select path="type"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<c:if test="${!requestScope.oaNotify.self}"><span>状态：</span>
				<form:radiobuttons path="status" class="i-checks" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</c:if> --%>
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
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<!-- <th> <input type="checkbox" class="i-checks"></th> -->
				<th  class="sort-column">序号</th>
				<th  class="sort-column status">审核状态</th>
				<th  class="sort-column fullname">姓名</th>
				<th  class="">部门</th>
				<th  class="sort-column mobile">创建时间</th>
				<th  class="">免责声明</th>
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
					${fns:getDictLabel(userInfo.ispermit, 'newemployee_permit', '')}
				</td>
				<td>
					${userInfo.fullname}
				</td>
				<td>
						${userInfo.office.name}
				</td>
				<td>
					<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${userInfo.createDate}" />
				</td>
				<td>
					<c:if test="${userInfo.disclaimername != null || userInfo.disclaimerno != null || userInfo.disclaimeryear != null 
					|| userInfo.disclaimermonth != null || userInfo.disclaimerday != null || userInfo.disclaimerdisease != null}">
					    <%-- <a href="javascript:" name="seedisclaimer" vl="${userInfo.id}"  >查看</a> --%>
					    <a href="#" onclick="openDialogView('查看免责声明', '${ctx}/ehr/entry/disclaimerView?id=${userInfo.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看免责声明</a>
					</c:if>
					<c:if test="${userInfo.disclaimername == null && userInfo.disclaimerno == null && userInfo.disclaimeryear == null 
					&& userInfo.disclaimermonth == null && userInfo.disclaimerday == null && userInfo.disclaimerdisease == null}">
					    无
					</c:if>
				</td>
				<td>
				    <a href="javascript:" name="gopermit" vl="${ctx}/ehr/entry/view?id=${userInfo.id}" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 查看入职信息</a>
					<a href="#" onclick="openDialogView('修改信息', '${ctx}/ehr/entry/ehrform?userinfoid=${userInfo.id}','60%', '80%')" class="btn  btn-warning btn-xs" ><i class="fa fa-edit"></i> 修改入职信息</a>
					<%-- <a href="javascript:" onclick="openDialogView('查看入职登记', '${ctx}/ehr/entry/form?id=${userInfo.id}&isView=true','900px', '600px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 查看</a> --%>
					<%-- <shiro:hasPermission name="ehr:userInfo:view">
						<a href="#" onclick="openDialogView('查看信息', '${ctx}/ehr/userInfo/form?id=${userInfo.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="ehr:userInfo:edit">
    					<a href="#" onclick="openDialog('修改信息', '${ctx}/ehr/userInfo/form?id=${userInfo.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="ehr:userInfo:del">
						<a href="${ctx}/ehr/userInfo/delete?id=${userInfo.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission> --%>
					
					<%-- <a href="${ctx}/ehr/entry/permitEntry?id=${userInfo.id}&status=2" onclick="return confirmx('确认要审核通过吗？', this.href)"   class="btn btn-info btn-xs"><i class="fa fa-check"></i> 审核通过</a>
					<a href="${ctx}/ehr/entry/permitEntry?id=${userInfo.id}&status=-1" onclick="return confirmx('确认要审核通过吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-times"></i> 审核不通过</a> --%>
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