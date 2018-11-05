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
			  if($("#leaveurl").val() == ""){
				  layer.alert('请上传离职申请!', {icon: 0, title:'警告'});
				  return false;
			  }
              if($("#resignation").val() == ""){
                  layer.alert('请上传辞职报告!', {icon: 0, title:'警告'});
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

            $("#uploadresignationF").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id);
            });

            $("#filecontent").delegate("a[name=removeFile]","click",function () {
                $("#filecontent").html("");
                $("#leaveurl").val("");
            })

            $("#resignationcontent").delegate("a[name=removeFile]","click",function () {
                $("#resignationcontent").html("");
                $("#resignation").val("");
            })
		});
        //图片上传回调函数
        function commonFileUploadCallBack(id,url,fname){
            $("#"+id).val(url);
            var htmlstr="";
            if(url!=''){
                htmlstr+='<li><a href="javascript:;" target="_blank" vl="'+url+'" onclick="commonFileDownLoad(this)"><span>'+fname+'</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>';
            }
            if(id=='resignation'){
                $("#resignationcontent").html(htmlstr);
            }else{
                $("#filecontent").html(htmlstr);
            }
        }
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/saveLeaveSheet?isadmin=1" method="post" class="form-horizontal">
            <input type="hidden" id="leavestatus" name="leavestatus" value="1"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
               <tr>
                   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名：</label></td>
                   <td class="width-35">
                       <sys:treeselectall id="userid" name="userid" value="${userInfo.userid}" labelName="fullname" labelValue="${userInfo.fullname}"
                                                  title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="true" udata="1" />
                   </td>
                   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>申请单：</label></td>
                   <td class="width-35" colspan="3">
                       <form:hidden id="leaveurl" path="leaveurl" htmlEscape="false" maxlength="255" class="form-control required"/>
                       <button type="button" class="btn btn-primary" id="uploadF" vl="leaveurl"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                       <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontent">
                           <c:if test="${userInfo.leaveurl!=null&&userInfo.leaveurl!=''}">
                               <li><a href="javascript:;" target="_blank" vl="${userInfo.leaveurl}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(userInfo.leaveurl)}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                           </c:if>
                       </ol>
                   </td>
               </tr>
               <tr>
                   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>辞职报告：</label></td>
                   <td class="width-35" colspan="5">
                       <form:hidden id="resignation" path="resignation" htmlEscape="false" maxlength="255" class="form-control required"/>
                       <button type="button" class="btn btn-primary" id="uploadresignationF" vl="resignation"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                       <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="resignationcontent">
                           <c:if test="${userInfo.resignation!=null&&userInfo.resignation!=''}">
                               <li><a href="javascript:;" target="_blank" vl="${userInfo.resignation}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(userInfo.resignation)}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                           </c:if>
                       </ol>
                   </td>
               </tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>