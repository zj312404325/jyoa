<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>入职登记</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		
	});
	</script>
</head>
<body class="hideScroll"><sys:message content="${message}"/>
<sys:message content="${message}"/>
	<div class="wrapper wrapper-content">
		<div class="row  border-bottom white-bg dashboard-header">
			<div class="col-sm-12 ui-sortable text-center">
				<c:if test="${isehr == 'true'}">
				<h3><i class="glyphicon glyphicon-ok text-info"></i>  修改成功！</h3>
				</c:if>
				<c:if test="${isehr != 'true'}">
					<h3><i class="glyphicon glyphicon-ok text-info"></i>  恭喜，您已完成入职登记，请下载填写劳动合同后上传，<a class="frameItem" href="javascript:" vl="${ctx}/ehr/entryContract/contract" vl2="劳动合同" data-index="-52" >去下载</a></h3>
				</c:if>
			</div>
		</div>
		
	</div>
	
</body>
</html>