<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
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
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

            $("#uploadF").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id,"pdf");
            });
            $("#uploadF2").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id,"pdf");
            });

            $("#file1area").delegate("a[name=removefile1]","click",function () {
                $("#file1area").html("");
                $("#file1").val("");
            });
            $("#file2area").delegate("a[name=removefile2]","click",function () {
                $("#file2area").html("");
                $("#file2").val("");
            });
			
		});
        //图片上传回调函数
        function commonFileUploadCallBack(id,url,fname){
            $("#"+id).val(url);
            var htmlstr="";
            if(url!=''){
                htmlstr+='<li><a href="javascript:;" target="_blank" vl="'+url+'" onclick="commonFileDownLoad(this)"><span>'+fname+'</span></a> &nbsp; <a href="javascript:;" name="remove'+id+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>';
            }
            $("#"+id+"area").html(htmlstr);
        }

	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="officePostDesc" action="${ctx}/sys/officePostDesc/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
			<form:hidden path="post.id"/>
			<form:hidden path="office.id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   <tr>
			   <td class="width-15 active"><label class="pull-right">职务说明书：</label></td>
			   <td class="width-35">
				   <button type="button" class="btn btn-primary" id="uploadF" vl="file1"><i class="glyphicon glyphicon-open"></i>&nbsp;上传pdf文件</button>
				   <input type="hidden" id="file1" name="file1" value="${officePostDesc.file1}" />
				   <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="file1area">
					   <c:if test="${officePostDesc.file1!=null&&officePostDesc.file1!=''}">
						   <li><a href="javascript:;" target="_blank" vl="${officePostDesc.file1}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(officePostDesc.file1)}</span></a> &nbsp; <a href="javascript:;" name="removefile1"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
					   </c:if>
				   </ol>
			   </td>
		   </tr>
		   <tr>
			   <td class="width-15 active"><label class="pull-right">绩效考核指标：</label></td>
			   <td class="width-35">
				   <button type="button" class="btn btn-primary" id="uploadF2" vl="file2"><i class="glyphicon glyphicon-open"></i>&nbsp;上传pdf文件</button>
				   <input type="hidden" id="file2" name="file2" value="${officePostDesc.file2}" />
				   <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="file2area">
					   <c:if test="${officePostDesc.file2!=null&&officePostDesc.file2!=''}">
						   <li><a href="javascript:;" target="_blank" vl="${officePostDesc.file2}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(officePostDesc.file2)}</span></a> &nbsp; <a href="javascript:;" name="removefile2"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
					   </c:if>
				   </ol>
			   </td>
		   </tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>