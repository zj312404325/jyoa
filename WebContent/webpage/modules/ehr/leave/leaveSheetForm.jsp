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
			  if($("#sheeturl").val() == ""){
				  layer.alert('请上传离职申请!', {icon: 0, title:'警告'});
				  return false;
			  }
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
                commonFileUpload(id);
            });

            $("#filecontent").delegate("a[name=removeFile]","click",function () {
                $("#filecontent").html("");
                $("#sheeturl").val("");
            })
		});
        //图片上传回调函数
        function commonFileUploadCallBack(id,url,fname){
            $("#"+id).val(url);
            var htmlstr="";
            if(url!=''){
                htmlstr+='<li><a href="javascript:;" target="_blank" vl="'+url+'" onclick="commonFileDownLoad(this)"><span>'+fname+'</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>';
            }
            $("#filecontent").html(htmlstr);
        }
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="leaveSheet" action="${ctx}/ehr/leaveSheet/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">机构：</label></td>
					<td class="width-35">
					  <%--<c:if test="${leaveSheet.id != null}">
						<sys:treeselect id="company" name="companyid" value="${leaveSheet.companyid}" labelName="company.name" labelValue="${leaveSheet.company.name}"
						title="公司" url="/sys/office/treeData?type=1" disabled="disabled" cssClass="form-control required"/>
						</c:if>
						<c:if test="${leaveSheet.id == null}">
						<sys:treeselect id="company" name="companyid" value="${leaveSheet.companyid}" labelName="company.name" labelValue="${leaveSheet.company.name}"
						title="公司" url="/sys/office/treeData?type=1" cssClass="form-control required"/>
						</c:if>--%>
						  <sys:treeselect id="receivedept" name="receivedeptids" value="${leaveSheet.receivedeptids}" labelName="receivedeptnames" labelValue="${leaveSheet.receivedeptnames}"
										  title="部门" url="/sys/office/treeData?type=2" isAll="true" cssClass="form-control required" notAllowSelectParent="false" checked="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">申请单：</label></td>
					<td class="width-35">
						<form:hidden id="sheeturl" path="sheeturl" htmlEscape="false" maxlength="255" class="form-control required"/>
					<%--<sys:ckfinderhead input="sheeturl" type="files" uploadPath="/ehr/leavesheet" selectMultiple="false" />--%>
						<button type="button" class="btn btn-primary" id="uploadF" vl="sheeturl"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontent">
							<c:if test="${leaveSheet.sheeturl!=null&&leaveSheet.sheeturl!=''}">
								<li><a href="javascript:;" target="_blank" vl="${leaveSheet.sheeturl}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(leaveSheet.sheeturl)}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							</c:if>
						</ol>
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>