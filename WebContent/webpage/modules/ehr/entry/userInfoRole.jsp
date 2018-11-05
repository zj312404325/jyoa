<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>角色设置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#subRole").click(function () {
                $.post("${ctx}/ehr/entry/saveRole",$("#roleForm").serialize(),function(data){
                    var jsonData = jQuery.parseJSON(data);
                    if(jsonData.status == 'y'){
                        layer.alert(jsonData.info,{icon: 1}, function(){
                            parent.location.reload();
                        });
                    }else{
                        layer.alert(jsonData.info,{icon: 2});
                    }
                });
            });
        });
	</script>
</head>
<body class="gray-bg">
	<div id="roleDiv">
		<div class="container">
			<c:if test="${message==null}">
				<form:form id="roleForm" modelAttribute="user" action="${ctx}/ehr/entry/saveRole" method="post" class="form-horizontal">
					<form:hidden path="id"/>
					<div class="row">
						<c:forEach items="${allRoles }" var="r">
							<div class="checkbox">
								<label>
									<input type="checkbox" class="i-checks" value="${r.id}" name="roleList"
									<c:forEach items="${user.roleList }" var="rl">
										   <c:if test="${rl.id==r.id}">checked="checked"</c:if>
									</c:forEach>
									> ${r.name}

								</label>
							</div>
						</c:forEach>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group text-center">
								<input type="button" id="subRole" value="提交" class="btn btn-info " />
							</div>
						</div>
					</div>
				</form:form>
			</c:if>
			<c:if test="${message!=null}">
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group text-center">
								${message}
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>