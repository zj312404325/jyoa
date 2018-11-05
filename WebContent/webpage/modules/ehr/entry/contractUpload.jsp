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

            $("a[name=furl]").live("click",function(){
                $(this).parent().remove();
                $("#contract").val("");
            });
		});
        function commonFileUploadCallBack(id,url,fname){
            $("#contract").val(url);
            if(url!=""&&fname!=""){
                $("#filearea").html('<li><a class="" href="javascript:" vl="'+url+'" onclick="commonFileDownLoad(this)">'+fname+'</a> &nbsp; <a href="javascript:" name="furl" vl="'+url+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
            }
            //totalUrl();
        }
		
		
		
     </script>
     

</head>
<body class="gray-bg" >
<div class="wrapper wrapper-content">
<div class="ibox">
<div class="ibox-title">
		<h5>劳动合同</h5>
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
<c:if test="${userInfo.status != 1}">
    <div class="ibox-content clearfix">
		<form:form id="inputForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/saveContract" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <c:if test="${myEntryContract != null && myEntryContract.contract != null && myEntryContract.contract != ''}">
		       <tr>
		         <td  class="width-15 active">	<label class="pull-right">下载劳动合同：</label></td>
		         <td >
		         	<a href="javascript:" vl="${myEntryContract.contract}" onclick="commonFileDownLoad(this)" class="btn btn-primary">下载合同</a>
		         </td>
		         
		      </tr>
		      </c:if>
		      <tr>
		      	<td  class="width-15 active">	<label class="pull-right">上传劳动合同：</label></td>
		         <td>
		         	<form:hidden id="contract" path="contract" htmlEscape="false" maxlength="255" class="form-control"/>
					<%--<sys:ckfinderhead input="contract" type="files" uploadPath="/ehr/contractUpload" selectMultiple="false"/>--%>
					 <button type="button" class="btn btn-primary" onclick="commonFileUpload('contract')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>
					 <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filearea">
						 <c:if test="${userInfo.contract!=null}">
							 <li><a class="" href="javascript:" vl="${userInfo.contract}" onclick="commonFileDownLoad(this)">${fns:getFileName(userInfo.contract)}</a> &nbsp;
								 <a href="javascript:" name="furl" vl="${userInfo.contract}"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
						 </c:if>
					 </ol>
		         </td>
		      </tr>	
		      <tr>
		         <td  class="width-15 active">	<label class="pull-right">状态：</label></td>
		         <td >
		         	<c:choose>
		         	  <c:when test="${userInfo != null && userInfo.contract != null && userInfo.contract != ''}">
		         	    <c:if test="${userInfo.status == 0}">待审核</c:if>
		         	    <c:if test="${userInfo.status == 1}">审核通过</c:if>
		         	    <c:if test="${userInfo.status == 2}">审核不通过，请重新上传合同</c:if>
		         	  </c:when>
		         	  <c:otherwise>
		         	    未上传合同
		         	  </c:otherwise>
		         	</c:choose>
		         </td>
		         
		      </tr>
			</tbody>
		</table>
		
		<c:choose>
        	  <c:when test="${userInfo != null && userInfo.contract != null && userInfo.contract != ''}">
        	    <c:if test="${userInfo.status == 0}"><button type="submit" class="btn btn-primary pull-right">提&ensp;交</button></c:if>
        	    <c:if test="${userInfo.status == 1}"></c:if>
        	    <c:if test="${userInfo.status == 2}"><button type="submit" class="btn btn-primary pull-right">提&ensp;交</button></c:if>
        	  </c:when>
        	  <c:otherwise>
        	    <button type="submit" class="btn btn-primary pull-right">提&ensp;交</button>
        	  </c:otherwise>
        	</c:choose>
        	
	</form:form>
	

	</div>
</c:if>   
<c:if test="${userInfo.status == 1}">
	<div class="ibox-content clearfix">
		<h1 class="text-success lead">您的合同已成功上传并审核成功！</h1>
		<p>合同日期为：<fmt:formatDate pattern="yyyy年MM月dd日" value="${userInfo.startdate}" />
                    		~
                    <fmt:formatDate pattern="yyyy年MM月dd日" value="${userInfo.enddate}" /></p>
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