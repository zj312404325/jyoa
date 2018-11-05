<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
		});
		
	</script>
</head>
<body class="hideScroll">
<div style="margin: 0 auto;width: 1200px">
    <c:if test="${institution.fileurl!=null&&institution.fileurl!='' }"><iframe class="J_iframe" id="iframepage" width="100%" height="850" name="iframe0"  src="${PDF_VIEW_PATH}${institution.fileurl}" frameborder="0" data-id="${ctx}/home" seamless></iframe></c:if>
</div>
</body>
</html>