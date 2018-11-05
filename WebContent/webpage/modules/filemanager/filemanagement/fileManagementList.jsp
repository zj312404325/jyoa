<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>档案管理管理</title>
	<meta name="decorator" content="default"/>
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
		function add(){
			openDialog("新增档案","${ctx}/filemanagement/fileManagement/form?categoryid=${fileManagement.categoryid}","500px", "400px","");
		}
		//打开对话框(添加修改)
		function openDialog(title,url,width,height,target){
			
			if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
				width='auto';
				height='auto';
			}else{//如果是PC端，根据用户设置的width和height显示。
			
			}
			
			top.layer.open({
			    type: 2,  
			    area: [width, height],
			    title: title,
		        maxmin: true, //开启最大化最小化按钮
			    content: url ,
			    btn: ['确定', '关闭'],
			    yes: function(index, layero){
			    	 var body = top.layer.getChildFrame('body', index);
			         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			         var inputForm = body.find('#inputForm');
			         var top_iframe;
			         if(target){
			        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
			         }else{
			        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
			         }
			         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
			         if(body.find("input[name=categoryid]").val() == ''){
			        	 top.layer.alert("请选择档案归属", {icon: 0, title:'警告'});
				    	 return false;
			         }
			         if(body.find("input[name=fileurl]").val() == ''){
			        	 top.layer.alert("请上传文件", {icon: 0, title:'警告'});
				    	 return false;
			         }
			         if(iframeWin.contentWindow.doSubmit() ){
			        	 top.layer.close(index);//关闭对话框。
			        	  //setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
			         }
					
				  },
				  cancel: function(index){ 
			       }
			}); 	
			
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>档案管理列表 </h5>
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
	<form:form id="searchForm" modelAttribute="fileManagement" action="${ctx}/filemanagement/fileManagement/list?categoryid=${fileManagement.categoryid}" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		
		<%-- <div class="form-group">
			<span>档案归属：</span>
				         <sys:treeselect id="fileManagement" name="categoryid" value="${fileManagement.categoryid}" labelName="categoryname" labelValue="${fileManagement.categoryname}"
		
							title="目录" url="/ehr/archiveManager/treeData" extId="${fileManagement.id}"  cssClass="form-control" /> --%>
		<div class="form-group">					
		<span>&nbsp;标题：</span>
			<form:input path="title" htmlEscape="false" maxlength="30"    class="form-control "/>
		<label>&nbsp;密级：</label>
			<select id="secret" name="secret" class="form-control">
				<option value="" <c:if test="${fileManagement.secret==null||fileManagement.secret=='' }">selected</c:if> >全部</option>
				<option value="0" <c:if test="${fileManagement.secret=='0' }">selected</c:if> >无密级</option>
				<option value="1" <c:if test="${fileManagement.secret=='1' }">selected</c:if> >秘密</option>
				<option value="2" <c:if test="${fileManagement.secret=='2' }">selected</c:if> >内部</option>
				<option value="3" <c:if test="${fileManagement.secret=='3' }">selected</c:if> >公开</option>
			</select>
		<span>&nbsp;档号：</span>
			<form:input path="fileno" htmlEscape="false" maxlength="30"    class="form-control " />
		<span>&nbsp;案卷号：</span>
			<form:input path="rollno" htmlEscape="false" maxlength="30"    class="form-control "/>
			</br></br>
		<span>&nbsp;开始时间：</span>
			<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${fileManagement.startdate}" pattern="yyyy-MM-dd"/>"/>
		<span>&nbsp;结束时间：</span>
		<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
						value="<fmt:formatDate value="${fileManagement.enddate}" pattern="yyyy-MM-dd"/>"/>

			<span>&nbsp;档案时间（年）：</span>
			<input id="fileyear" name="fileyear" type="text" maxlength="20" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" class="laydate-icon form-control layer-date required"
				   value="${fileManagement.fileyear}"/>
			<span>&nbsp;档案时间（月）：</span>
			<input id="filemonth" name="filemonth" type="text" maxlength="20" onclick="WdatePicker({skin:'whyGreen',dateFmt:'MM'})" class="laydate-icon form-control layer-date required"
				   value="${fileManagement.filemonth}"/>
		</div>	
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
			<shiro:hasPermission name="filemanagement:fileManagement:add">
				<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="add()" title="档案管理添加"><i class="fa fa-plus"></i> 添加</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="filemanagement:fileManagement:edit">
			    <table:editRow url="${ctx}/filemanagement/fileManagement/form" title="档案管理" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="filemanagement:fileManagement:del">
				<table:delRow url="${ctx}/filemanagement/fileManagement/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="filemanagement:fileManagement:import">
				<table:importExcel url="${ctx}/filemanagement/fileManagement/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="filemanagement:fileManagement:export">
	       		<table:exportExcel url="${ctx}/filemanagement/fileManagement/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				
				<th  class="">标题</th>
				<th  class="">档号</th>
				<th  class="">案卷号</th>
				<th  class="">档案时间</th>
				<th  class="sort-column createDate">创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fileManagement">
			<tr>
				<td> <input type="checkbox" id="${fileManagement.id}" class="i-checks"></td>
				<td>
					<a  href="#" onclick="openDialogView('查看档案管理', '${ctx}/filemanagement/fileManagement/form?id=${fileManagement.id}&mtd=1','800px', '500px')">
					  ${fileManagement.title}
					</a>
				</td>
				<td>
					${fileManagement.fileno}
				</td>
				<td>
					${fileManagement.rollno}
				</td>
				<td>
					<c:if test="${fileManagement.fileyear != null}">${fileManagement.fileyear}年</c:if><c:if test="${fileManagement.filemonth != null}">${fileManagement.filemonth}月</c:if>
				</td>
				<td>
					<fmt:formatDate value="${fileManagement.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="filemanagement:fileManagement:view">
						<a href="#" onclick="openDialogView('查看档案管理', '${ctx}/filemanagement/fileManagement/form?id=${fileManagement.id}&mtd=1','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="filemanagement:fileManagement:edit">
    					<a href="#" onclick="openDialog('修改档案管理', '${ctx}/filemanagement/fileManagement/form?id=${fileManagement.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="filemanagement:fileManagement:del">
						<a href="${ctx}/filemanagement/fileManagement/delete?id=${fileManagement.id}" onclick="return confirmx('确认要删除该档案管理吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="filemanagement:fileManagement:download">
						<a href="javascript:" vl="${fileManagement.fileurl}" onclick="commonFileDownLoad(this)"   class="btn btn-primary btn-xs"><i class="fa fa-file-text-o"></i> 源文件下载</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="filemanagement:fileManagement:download">
						<a href="javascript:" vl="${fileManagement.filepdf}" onclick="commonFileDownLoad(this)"   class="btn btn-warning btn-xs"><i class="fa fa-file-text-o"></i> pdf下载</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="filemanagement:fileManagement:edit">
						<a href="#" onclick="openDialog('授权档案', '${ctx}/filemanagement/fileManagement/author?id=${fileManagement.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 授权</a>
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