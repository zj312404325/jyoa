<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>聊天管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#chatClear").click(function(){

                layer.confirm('你确认要彻底删除所有聊天记录？', {
                    btn: ['确定','关闭'] //按钮
                }, function(){
                    $("#searchForm").submit();
                });
			});
		});

	</script>
</head>
<body>
<sys:message content="${message}"/>
<div class="form-group" style="padding:20px;">

	<button class="btn btn-danger" id="chatClear" >一键清除聊天记录</button>
</div>
	<form:form id="searchForm" modelAttribute="" action="${ctx}/iim/chatHistory/deleteAllChatHistory" method="post" class="form-inline">


	</form:form>
</body>
</html>