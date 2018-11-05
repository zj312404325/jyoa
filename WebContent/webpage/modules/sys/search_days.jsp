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

            $("#saveit").click(function(){

                var index = layer.load(0, {shade: false});
                $.post("${ctx}/sys/user/savesearchdays",{
                    'searchdays':$("#searchdays").val()
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
</head>
<body>
	<div class="control-group">
	</div>
	<div class="control-group">
		<label class="col-sm-3 control-label">查询几天:</label>
		<div class="controls">
			<%--<form:input path="name" htmlEscape="false" maxlength="50" class="form-control  max-width-250 required userfmt" />--%>
				<input id="searchdays" name="searchdays" class="form-control  max-width-250 required valid" type="text" value="${SEARCH_DAYS_LIMIT}"  maxlength="50" >
				<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" id="saveit" title="确认"><i class="fa fa-file-text-o"></i> 确认</button>
		</div>
	</div>
</body>
</html>