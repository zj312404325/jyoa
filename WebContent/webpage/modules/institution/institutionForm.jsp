<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>oa制度管理</title>
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
            var a = $("input[name='itype']:checked").val();
            if(a == 1){
                $("#fileUploadTr").show();
			}
            $("input[name='itype']").on('ifChecked',function(){
                var a = $("input[name='itype']:checked").val();
			   if(a == 1){
                   $("#fileUploadTr").show();
			   }else{
                   $("#fileUploadTr").hide();
			   }
			});

            $("#uploadF").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id,"pdf");
            });

            $("#filecontent").delegate("a[name=removeFile]","click",function () {
                $("#filecontent").html("");
                $("#fileurl").val("");
            })


		});
        function commonFileUploadCallBack(id,url,fname){
            $("#"+id).val(url);
            var htmlstr="";
            if(url!=''){
                htmlstr+='<li><a href="javascript:;" target="_blank" vl="'+url+'" onclick="commonFileDownLoad(this)"><span>'+fname+'</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>';
            }
            $("#filecontent").html(htmlstr);
            if($("#title").val() == ''){
                $("#title").val(fname);
			}
        }
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="institution" action="${ctx}/sys/institution/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   
				<tr>
					<td class="width-15 active"><label class="pull-right">上级：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="parent.id" value="${institution.parent.id}" labelName="parent.title" labelValue="${institution.parent.title}"
					title="" url="/sys/institution/treeData?type=0" extId="${institution.id}"  cssClass="form-control" allowClear="${institution.currentUser.admin}"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>类型：</label></td>
					<td class="width-35">
					    <form:radiobuttons path="itype" items="${fns:getDictList('institution_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required i-checks "/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>排序：</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control required"/>
					</td>
		  		</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">权限：</label></td>
					<td class="width-35">
						<span id="deptarea"><sys:treeselect id="receivedept" name="receivedeptids" value="${institution.receivedeptids}" labelName="receivedeptnames" labelValue="${institution.receivedeptnames}"
							title="部门" url="/sys/office/treeData?type=2" isAll="true" cssClass="form-control required" notAllowSelectParent="false" checked="true"/></span>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>标题：</label></td>
					<td class="width-35">
						<form:input path="title" htmlEscape="false"  maxlength="50"  class="form-control required "/>
					</td>
				</tr>
				<tr id="fileUploadTr" style="display: none">
					<td class="width-15 active"><label class="pull-right">文件路径：</label></td>
					<td class="width-35">
						<button type="button" class="btn btn-primary" id="uploadF" vl="fileurl"><i class="glyphicon glyphicon-open"></i>&nbsp;上传pdf文件</button>
						<input type="hidden" id="fileurl" name="fileurl" value="${institution.fileurl}" />
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontent">
							<c:if test="${institution.fileurl!=null&&institution.fileurl!=''}">
								<li><a href="javascript:;" target="_blank" vl="${institution.fileurl}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(institution.fileurl)}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							</c:if>
						</ol>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>