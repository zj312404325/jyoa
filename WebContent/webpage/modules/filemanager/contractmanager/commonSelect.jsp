<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>传阅管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#sub").click(function(){
				 var pid = "${pid}";
				 var pvalue = "${pvalue}";
				 if(pid != ''){
					 window.parent.$("#"+pid).val($("#item option:selected").val());
				 }
				 if(pvalue != ''){
					 window.parent.$("#"+pvalue).val($("#item option:selected").text());
				 }
				 //$("span[name=unitweightsuffix1]",window.parent.document).html($(this).text()); 
				 window.parent.layer.closeAll();
			});
		});
		
     </script>
</head>
<body class="gray-bg">
<form class="form-horizontal">
<p>&nbsp;</p>
			<label class="col-xs-3 control-label">类型</label>
			<div class="col-xs-9">
				<select id="item" class="form-control m-b ">
					<option value="" selected="selected"></option>
					<c:forEach items="${list}" var="s">
					   <option value="${s.value}" >${s.label}</option>
					</c:forEach>
				</select>
			</div>
			<p>&nbsp;</p>
<div class="layui-layer-btn"><a id="sub" class="layui-layer-btn0">确定</a></div>
</form>
</body>
</html>