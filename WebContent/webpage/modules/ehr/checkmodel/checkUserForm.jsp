<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<c:if test="${category==null||category==''||category=='0'}"><c:set var="titlestr" value="绩效" /></c:if>
	<c:if test="${category=='1'}"><c:set var="titlestr" value="转正" /></c:if>
	<title>${titlestr}考核考核人管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
		      var weight=$("#weight").val();
		      if(weight>100){
                  layer.alert("权重不能超过100",{icon: 2});
                  return;
			  }
              if(weight<0){
                  layer.alert("权重不能小于0",{icon: 2});
                  return;
              }
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}

        function setReadOnly() {
            $(".tbdisable input").attr("disabled","true");
            $(".tbdisable textarea").attr("disabled","true");
            $(".tbdisable button").attr("disabled","true");
            $(".tbdisable select").attr("disabled","true");
            $(".tbdisable a[name=removeFile]").remove();
        }

		$(document).ready(function() {
            if('${method}'=='1'){
                setReadOnly();
            }

		    //岗位选择
		    $("#stationId").change(function () {
				var stationName=$("#stationId").find("option:selected").text();
				if(stationName=='请选择岗位'){
                    $("#stationName").val("");
				}else{
                    $("#stationName").val(stationName);
				}
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
			
			$("#searchUserList").click(function(){
				searchUserList();
			});
		});
		
		function searchUserList(){
			$("#searchResult").hide();
			$("#userListContent").empty();
			var key=$("#searchKey").val();
			if(key!=null&&key!=''){
				//ajax提交
				$.post("${ctx}/checkmodel/checkUser/getUserList",{"keyword":key},function(data){
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						var userList=jsonData.userList;
						var htmlstr='';
						if(userList.length > 0){
							$.each(userList,function(i,n){
								htmlstr+="<tr>";
								htmlstr+="<td>"+n.name+"</td>";
								htmlstr+="<td>"+n.no+"</td>";
								htmlstr+="<td>"+n.officename+"</td>";
								htmlstr+='<td><input type="button" class="btn btn-primary btn-xs" value="选择" onclick="selectUser(this)" vl="'+n.id+'" vl1="'+n.name+'" vl2="'+n.no+'" vl3="'+n.officeid+'" vl4="'+n.officename+'" vl5="'+n.stationid+'" vl6="'+n.stationname+'"  /></td>';
								htmlstr+="</tr>";
							});
						}else{
							htmlstr+='<tr><td colspan="4" class="text-center">无数据！</td></tr>';
						}
						$("#userListContent").html(htmlstr);
						$("#searchResult").show();
					}else{
						layer.alert(jsonData.info,{icon: 2});
					}
				});
			}else{
				layer.alert("请输入关键字！",{icon:2});
			}
		}
		
		function selectUser(obj){
			var $data=$(obj);
			var userid=$data.attr("vl");
			$("#userId").val(userid);
			var username=$data.attr("vl1");
			$("#userName").val(username);
			var userno=$data.attr("vl2");
			$("#userNo").val(userno);
			var officeid=$data.attr("vl3");
			$("#officeId").val(officeid);
			var officename=$data.attr("vl4");
			$("#officeName").val(officename);
		}
	</script>
</head>
<body class="hideScroll">
<div class="wrapper wrapper-content">
		<form:form id="inputForm" modelAttribute="checkUser" action="${ctx}/checkmodel/checkUser/save" method="post" class="form-inline">
		<form:hidden path="id"/>
		<form:hidden path="category"/>
		<sys:message content="${message}"/>
		<c:if test="${method==null||method==''}">
			<div class="row">
				<div class="col-sm-12">
					<div class="form-group">
						<span>员工姓名/编码：</span>
						<input type="text" id="searchKey" htmlEscape="false"  class="form-control input-sm" maxlength="200" />
						<div class="pull-right" style="padding-left: 20px;">
							<button type="button" class="btn btn-primary btn-rounded btn-outline btn-sm " id="searchUserList"><i class="fa fa-search"></i> 查询</button>
						</div>
					</div>
				</div>
			</div>
		</c:if>

		<div class="row" id="searchResult" style="display:none;">
			<div class="col-sm-12">
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>姓名</th>
							<th>工号</th>
							<th>部门</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="userListContent">
					</tbody>
				</table>
			</div>
		</div>
		<form:hidden path="userId" htmlEscape="false" value="${userId}"/>
		<form:hidden path="stationName" htmlEscape="false" value="${checkUser.stationName}"/>
		<form:hidden path="checkofficeid" htmlEscape="false" value="${checkUser.checkofficeid}"/>
		<form:hidden path="checkofficename" htmlEscape="false" value="${checkUser.checkofficename}"/>
		<form:hidden path="officeId" htmlEscape="false" value="${officeId}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>员工编号：</label></td>
					<td class="width-35">
						<form:input path="userNo" htmlEscape="false"    class="form-control required" readonly="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>员工姓名：</label></td>
					<td class="width-35">
						<form:input path="userName" htmlEscape="false"    class="form-control required" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门名称：</label></td>
					<td class="width-35">
						<form:input path="officeName" htmlEscape="false"  value=""  class="form-control required" readonly="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>考核岗位：</label></td>
					<td class="width-35">
						<select class="form-control required" id="stationId" name="stationId" <c:if test="${checkUser.id!=null}">disabled="disabled"</c:if> >
							<option value="">请选择岗位</option>
							<c:forEach items="${checkUser.postList}" var="post">
								<option value="${post.id}" <c:if test="${post.id==checkUser.stationId}">selected="selected"</c:if> >${post.postname}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:input path="memo" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>权重：</label></td>
					<td class="width-35">
						<form:input path="weight" htmlEscape="false"    class="form-control required digits"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</div>
</body>
</html>