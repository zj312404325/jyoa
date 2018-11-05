<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
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


            $("#downloadit").click(function(){
                var href=$("#fileurl").val();
                if(href!=''){
                    window.location.href="http://172.19.100.2/a/sys/user/fileDownload?fileUrl="+encodeURIComponent(href);
                }

            });

        });
	</script>
</head>
<body>
	<div class="control-group">
	</div>

	<div class="control-group">
		<label class="col-sm-3 control-label">下载:</label>
		<div class="controls">
			<%--<form:input path="name" htmlEscape="false" maxlength="50" class="form-control  max-width-250 required userfmt" />--%>
			<input id="fileurl" name="fileurl" class="form-control  max-width-250 required valid" type="text" value=""  maxlength="500" >
			<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" id="downloadit" title="下载"><i class="fa fa-file-text-o"></i> 确认</button>
		</div>
	</div>
</body>
</html>