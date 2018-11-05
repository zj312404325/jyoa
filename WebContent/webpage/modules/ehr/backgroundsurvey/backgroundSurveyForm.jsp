<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<!-- SUMMERNOTE -->
	 <link href="${ctxStatic}/summernote/summernote.css" rel="stylesheet">
	 <link href="${ctxStatic}/summernote/summernote-bs3.css" rel="stylesheet">
	 <script src="${ctxStatic}/summernote/summernote.min.js"></script>
	 <script src="${ctxStatic}/summernote/summernote-zh-CN.js"></script>
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
			
			$('#surveyresult,#hradvice').summernote({
                lang: 'zh-CN',
                toolbar: [["style", ["style"]], ["font", ["bold", "italic", "underline", "clear"]], ["color", ["color"]], ["para", ["ul", "ol", "paragraph"]], ["height", ["height"]], ["table", ["table"]]]
            });
			
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

            laydate({
                elem: '#entrydate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });

            laydate({
                elem: '#surveydate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });

            $("#uploadF").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id);
            });

            $("#filecontent").delegate("a[name=removeFile]","click",function () {
                $("#filecontent").html("");
                $("#surveyfiles").val("");
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
		<form:form id="inputForm" modelAttribute="backgroundSurvey" action="${ctx}/ehr/backgroundSurvey/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-10 active"><label class="pull-right">被调查人：</label></td>
					<td class="width-30">
						<form:input path="surveyname" htmlEscape="false"    class="form-control required" maxlength="20"/>
					</td>
					<td class="width-10 active"><label class="pull-right">年龄：</label></td>
					<td class="width-30">
						<form:input path="age" htmlEscape="false"    class="form-control digits" maxlength="20"/>
					</td>
					<td class="width-10 active"><label class="pull-right">性别：</label></td>
					<td class="width-30">
						<%--<form:input path="sex" htmlEscape="false"    class="form-control "/>--%>
						<form:select path="sex" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-10 active"><label class="pull-right">部门：</label></td>
					<td class="width-30">
						<form:input path="depart" htmlEscape="false"    class="form-control required" maxlength="20"/>
					</td>
					<td class="width-10 active"><label class="pull-right">岗位：</label></td>
					<td class="width-30">
						<form:input path="position" htmlEscape="false"    class="form-control required" maxlength="20"/>
					</td>
					<td class="width-10 active"><label class="pull-right">入职时间：</label></td>
					<td class="width-30">
						<input id="entrydate" name="entrydate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							   value="${backgroundSurvey.entrydate}"/>
					</td>
				</tr>
				<tr>
					<td class="width-10 active"><label class="pull-right">调查时间：</label></td>
					<td class="width-70" colspan="3">
						<input id="surveydate" name="surveydate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							   value="${backgroundSurvey.surveydate}"/>
					</td>
					<td class="width-10 active"><label class="pull-right">调查人：</label></td>
					<td class="width-30">
						<form:input path="operater" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
				</tr>
				<tr>
		         <td  class="width-10 active">	<label class="pull-right"><!-- <font color="red">*</font> -->调查结果：</label></td>
		         <td colspan="5" ><form:textarea path="surveyresult" htmlEscape="false" rows="6" maxlength="2000" class="form-control required"/></td>
		         
		      </tr>
		      <tr>
		         <td  class="width-10 active">	<label class="pull-right"><!-- <font color="red">*</font> -->hr建议：</label></td>
		         <td colspan="5" ><form:textarea path="hradvice" htmlEscape="false" rows="6" maxlength="2000" class="form-control required"/></td>
		         
		      </tr>
		      <tr>
		      	<td  class="width-10 active">	<label class="pull-right">附件：</label></td>
		         <td colspan="5">
					<%--<form:hidden id="surveyfiles" path="surveyfiles" htmlEscape="false" maxlength="255" class="form-control"/>
					<sys:ckfinder input="surveyfiles" type="files" uploadPath="/ehr/surveyfiles" selectMultiple="true"/>--%>
					<%-- <form:hidden id="files" path="files" htmlEscape="false" maxlength="255" class="form-control"/>
					<sys:ckfinder input="files" type="files" uploadPath="/oa/notify" selectMultiple="true" readonly="true" /> --%>

						<button type="button" class="btn btn-primary" id="uploadF" vl="surveyfiles"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
						<input type="hidden" id="surveyfiles" name="surveyfiles" value="${backgroundSurvey.surveyfiles}" />
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontent">
							<c:if test="${backgroundSurvey.surveyfiles!=null&&backgroundSurvey.surveyfiles!=''}">
								<li><a href="javascript:;" target="_blank" vl="${backgroundSurvey.surveyfiles}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(backgroundSurvey.surveyfiles)}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							</c:if>
						</ol>
		         </td>
		      </tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>