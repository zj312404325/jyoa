<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="posttrain" action="${ctx}/ehr/posttrain/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">培训标题：</label></td>
					<td class="width-35">
						${posttrain.title }
					</td>
					<td class="width-15 active"><label class="pull-right">培训地点：</label></td>
					<td class="width-35">
						${posttrain.address }
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">主办机构：</label></td>
					<td class="width-35">
						${posttrain.organizer }
					</td>
					<td class="width-15 active"><label class="pull-right">培训时间：</label></td>
					<td class="width-35">
					  <fmt:formatDate pattern="yyyy年MM月dd日" value="${posttrain.traintime}" />
					</td>
				</tr>
				<tr>
			         <td  class="width-15 active">	<label class="pull-right"><!-- <font color="red">*</font> -->培训内容：</label></td>
			         <td colspan="3" >
			         ${fns:unescapeHtml(posttrain.content)}
			         </td>
			         
			    </tr>
				<tr>
			         <td  class="width-15 active">	<label class="pull-right"><!-- <font color="red">*</font> -->培训总结：</label></td>
			         <td colspan="3" >
			           ${fns:unescapeHtml(posttrain.summary)}
			         </td>
			         
			    </tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>