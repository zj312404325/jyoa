<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>入职</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			/* if("${oaNotify.readFlag}"=='0' && "${oaNotify.self}"=='true'){
				$("#searchForm").attr("action","${ctx}/oa/oaNotify/selfnoread");
			} */
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

        function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
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
     </script>
     

</head>
<body class="gray-bg" >
<div class="wrapper wrapper-content">
<div class="ibox">
<div class="ibox-title">
		<h5>离职申请</h5>
		<!-- <div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="form_basic.html#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div> -->
	</div>
<c:if test="${userInfo.leavestatus != 1}">
    <div class="ibox-content clearfix">
		<form:form id="inputForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/saveLeaveSheet" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <c:if test="${leaveSheet != null && leaveSheet.sheeturl != null && leaveSheet.sheeturl != ''}">
		       <tr>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>下载离职申请：</label></td>
		         <td >
		         	<a href="javascript:" vl="${leaveSheet.sheeturl}" onclick="commonFileDownLoad(this)" class="btn btn-primary">下载离职申请单</a>
		         </td>
		         
		      </tr>
		      </c:if>
		      <tr>
		      	<td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>上传离职申请：</label></td>
		         <td>
		         	<%--<form:hidden id="leaveurl" path="leaveurl" htmlEscape="false" maxlength="255" class="form-control"/>
					<sys:ckfinderhead input="leaveurl" type="files" uploadPath="/ehr/leaveSheetUpload" selectMultiple="false"/>--%>
						<button type="button" class="btn btn-primary" id="uploadF" vl="leaveurl"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
						<input type="hidden" id="leaveurl" name="leaveurl" value="${userInfo.leaveurl}" />
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontent">
							<c:if test="${userInfo.leaveurl!=null&&userInfo.leaveurl!=''}">
								<li><a href="javascript:;" target="_blank" vl="${userInfo.leaveurl}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(userInfo.leaveurl)}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							</c:if>
						</ol>
		         </td>
		      </tr>

			  <tr>
				  <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>上传辞职报告：</label></td>
				  <td>
						<input type="hidden" id="resignation" name="resignation" value="${userInfo.resignation}" />
					  <button type="button" class="btn btn-primary" id="uploadresignationF" vl="resignation"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
					  <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="resignationcontent">
						  <c:if test="${userInfo.resignation!=null&&userInfo.resignation!=''}">
							  <li><a href="javascript:;" target="_blank" vl="${userInfo.resignation}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(userInfo.resignation)}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
						  </c:if>
					  </ol>
				  </td>
			  </tr>

		      <tr>
		         <td  class="width-15 active">	<label class="pull-right">状态：</label></td>
		         <td >
		         	<c:choose>
		         	  <c:when test="${(userInfo != null && userInfo.leaveurl != null && userInfo.leaveurl != '')&&(userInfo != null && userInfo.resignation != null && userInfo.resignation != '')}">
		         	    <c:if test="${userInfo.leavestatus == 0}">待审核</c:if>
		         	    <c:if test="${userInfo.leavestatus == 1}">审核通过</c:if>
		         	    <c:if test="${userInfo.leavestatus == 2}">审核不通过，请重新上传离职申请及离职报告</c:if>
		         	  </c:when>
		         	  <c:otherwise>
		         	    未上传离职申请或离职报告
		         	  </c:otherwise>
		         	</c:choose>
		         </td>
		         
		      </tr>
			</tbody>
		</table>
		
		<c:choose>
        	  <c:when test="${userInfo != null && userInfo.leaveurl != null && userInfo.leaveurl != ''}">
        	    <c:if test="${userInfo.leavestatus == 0}"><button type="button" class="btn btn-primary pull-right" onclick="doSubmit()">提&ensp;交</button></c:if>
        	    <c:if test="${userInfo.leavestatus == 1}"></c:if>
        	    <c:if test="${userInfo.leavestatus == 2}"><button type="button" class="btn btn-primary pull-right" onclick="doSubmit()">提&ensp;交</button></c:if>
        	  </c:when>
        	  <c:otherwise>
        	    <button type="button" class="btn btn-primary pull-right" onclick="doSubmit()">提&ensp;交</button>
        	  </c:otherwise>
        	</c:choose>
        	
	</form:form>
	

	</div>
</c:if>   
<c:if test="${userInfo.leavestatus == 1}">
	<div class="ibox-content clearfix">
		<h1 class="text-success lead">您的离职申请单已上传成功！</h1>
		
	</div>
</c:if> 
	
	</div>
</div>
<script>
$(function(){
	
});
</script>


</body>
</html>