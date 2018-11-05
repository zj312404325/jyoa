<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var basePath="${basePath}";
	</script>
</head>
<body class="hideScroll" style="position:relative;">
		<form id="inputForm"  action="" method="post" class="form-horizontal">
		<input type="hidden" id="iid" value="${institution.id}" />
		<textarea id="content" name="content" style=" width:870px; height:400px;" >${institution.content}</textarea>
	</form>
	<br><br><br>
	<div class="layui-layer-btn" style="margin-top: 15px; position:fixed; right:0; bottom:0; z-index:99999">
	  <a class="layui-layer-btn0" id="sub">确定</a>
	  <a class="layui-layer-btn1" id="cls">关闭</a>
	</div>
	
	<script type="text/javascript" charset="utf-8" src="${basePath}/uediter/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${basePath}/uediter/ueditor.all.js"> </script>
	<script type="text/javascript" charset="utf-8" src="${basePath}/uediter/lang/zh-cn/zh-cn.js"> </script>
	<script type="text/javascript">
	var basePath="${basePath}";
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			var loginUsername = "jyoa";
			UE.getEditor('content');
			UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
			UE.Editor.prototype.getActionUrl = function(action) {
			    	return '${basePath}/uediter/jsp/controller.jsp?username='+loginUsername+'&action='+action;
			};
			
			
			$("#cls").click(function(){
				parent.layer.closeAll();
			});
			
			$("#sub").click(function(){
				UE.getEditor('content').sync();
			    if(UE.getEditor('content').getContent() == ''){
			    	layer.msg("请填写详细内容", {icon: 2});
			    	return;
			    }
			    var index = layer.load(0, {shade: false});
			    $.post("${ctx}/sys/institution/saveContent",{
					'content':UE.getEditor('content').getContent(),
					'iid':$("#iid").val()
				   },function(data){
					   layer.close(index);
						var jsonData = jQuery.parseJSON(data);
						if(jsonData.status == 'y'){
							parent.layer.closeAll();
							parent.layer.msg(jsonData.info, {icon: 1});
					    }else{
					    	layer.alert(jsonData.info,0);
					    }
				});
			});
		});
		
	</script>
</body>
</html>