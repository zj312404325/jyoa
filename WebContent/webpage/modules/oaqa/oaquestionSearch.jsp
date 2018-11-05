<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">

		$(document).ready(function() {

			$("#search").click(function(){
			    if($.trim($("#var1").val()) == ''){
			        layer.alert("请输入搜索内容",{icon:0});
			        return false;
				}
			    location.href="${ctx}/oaqa/oaquestion/list?var1="+encodeURI(encodeURI($.trim($("#var1").val())));
			});
		});

        function add(){
            openDialog("创建问答","${ctx}/oaqa/oaquestion/form","60%", "30%","");
        }

	</script>
</head>
<body class="hideScroll">
	<form:form modelAttribute="oaquestion" action="${ctx}/oaqa/oaquestion/list" method="post" class="form-inline">
		<div class="qa_search_box">
			<h2 class="text-center" style="margin-left: -100px; line-height: 40px;"><i style="top:4px; color:#1ab394;" class="glyphicon glyphicon-question-sign"></i> 我要问</h2>
			<form:input path="var1" htmlEscape="false"    class="form-control required"/>
			<a id="search" href="javascript:" class="btn btn-primary"><i class="glyphicon glyphicon-search"></i> 搜索</a>
			<p class="row" style="padding-top: 24px;">
				<span class="col-sm-4 text-right"><a class="frameItem text-info" href="javascript:" vl="${ctx}/oaqa/oaquestion/list?hasanswer=no" vl2="等待回答的问题-办公问答" data-index="566" >等待回答的问题</a></span>
				<span class="col-sm-4 text-center"><a class="frameItem text-info" href="javascript:" vl="${ctx}/oaqa/oaquestion/list?hasanswer=yes" vl2="已有答案的问题-办公问答" data-index="566" >已有答案的问题</a></span>
				<shiro:hasPermission name="oaqa:oaquestion:add"><span class="col-sm-4 text-left"><a class="text-info" onclick="add()" href="javascript:"  >创建问答</a></span></shiro:hasPermission>
			</p>
		</div>
	</form:form>

</body>
</html>