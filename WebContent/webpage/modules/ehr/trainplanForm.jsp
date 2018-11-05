<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>培训计划管理</title>
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
			

            <c:choose>
            <c:when test="${trainplan.isadmin==null||trainplan.isadmin==''}">

            </c:when>
            <c:otherwise>
            laydate({
                elem: '#traindate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            </c:otherwise>
            </c:choose>

            $("#addRecord").click(function(){
                ///jy_oa/a/tag/treeselect?url=%2Fsys%2Foffice%2FtreeData%3Ftype%3D3&module=&checked=true&extId=&isAll=&selectIds=
                var ids = [], names = [], nodes = [];
                layer.open({
                    type: 2,
                    area: ['300px', '420px'],
                    title:"新增人员",
                    ajaxData:{selectIds: $("#traineeId").val()},
                    content: "${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3")+"&module=&checked=true&extId=&isAll=&selectIds="+$("#traineeId").val() ,
                    btn: ['确定', '关闭']
                    ,yes: function(index, layero){ //或者使用btn1
                        var tree = layero.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
                        nodes = tree.getCheckedNodes(true);
                        for(var i=0; i<nodes.length; i++) {
                            if (nodes[i].isParent){
                                continue; // 如果为复选框选择，则过滤掉父节点
                            }
                            if (nodes[i].level == 0){
                                //top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
                                top.layer.msg("不能选择根节点（"+nodes[i].name+"）请重新选择。", {icon: 0});
                                return false;
                            }
                            if (nodes[i].isParent){
                                //top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
                                //layer.msg('有表情地提示');
                                top.layer.msg("不能选择父节点（"+nodes[i].name+"）请重新选择。", {icon: 0});
                                return false;
                            }
                            ids.push(nodes[i].id);
                            names.push(nodes[i].name);
                            //break;
                        }
                        var idsStr = ids.join(",").replace(/u_/ig,"");
                        //$("#addoaNotifyRecordId").val(ids.join(",").replace(/u_/ig,""));
                        //$("#addoaNotifyRecordName").val(names.join(","));
                        //$("#addoaNotifyRecordName").focus();
                        $.post("${ctx}/ehr/trainplan/addTrainee",
                            {
                                "trainplanid":"${trainplan.id}",
                                "ids":idsStr
                            },function(data){
                                var jsonData = jQuery.parseJSON(data);
                                if(jsonData.status == 'y'){
                                    layer.close(index);
                                    window.location.href="${ctx}/ehr/trainplan/form?id=${trainplan.id}";
                                }else{
                                    layer.msg(jsonData.info, {icon: 2});
                                }
                            });


                    },
                    cancel: function(index){ //或者使用btn2
                        //按钮【按钮二】的回调
                    }
                });
            });

            $("a[name=delrecord]").live("click",function(){
                $(this).parent().parent().remove();
                resetRecord();
            });
		});

        //点击叉叉后重置人员
        function resetRecord(){
            var ids = [], names = [];
            var trs = $("#recordArea").find("tr[name=recordtr]");
            $.each(trs, function(i,val){
                ids.push($(this).attr("vl1"));
                names.push($(this).attr("vl2"));
            });
            $("#traineeId").val(ids.join(",").replace(/u_/ig,""));
            console.log($("#traineeId").val());
            $("#traineeName").val(names.join(","));
            console.log($("#traineeName").val());
        }

        //新添人员后回调
        function callback(){
            if($("#traineeId").val() != ''){
                var idsArr = $("#traineeId").val().split(",");
                var namesArr = $("#traineeName").val().split(",");
                var htmlStr = '';

                $.each(idsArr, function(i,val){
                    htmlStr+='<tr name="recordtr" vl1="'+idsArr[i]+'" vl2="'+namesArr[i]+'"><td>'+namesArr[i]+'</td><td><a class="remove" name="delrecord" href="javascript:void(0)" title="删除"><i class="glyphicon glyphicon-remove"></i></a></td></tr>';
                });
                $("#recordArea").html(htmlStr);
            }
        }
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="trainplan" action="${ctx}/ehr/trainplan/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="isadmin"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门：</label></td>
					<td class="width-35">
						<c:choose>
							<c:when test="${trainplan.isadmin==null||trainplan.isadmin==''}">
								<sys:treeselect id="officeid" name="officeid" value="${trainplan.officeid}" labelName="officename" labelValue="${trainplan.officename}"
												title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="false" disabled="disabled"/>
							</c:when>
							<c:otherwise>
								<sys:treeselect id="officeid" name="officeid" value="${trainplan.officeid}" labelName="officename" labelValue="${trainplan.officename}"
												title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="false"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>培训人：</label></td>
					<td class="width-35">
						<c:choose>
							<c:when test="${trainplan.isadmin==null||trainplan.isadmin==''}">
								<sys:treeselect id="userid" name="userid" value="${trainplan.userid}" labelName="username" labelValue="${trainplan.username}"
												title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="false" disabled="disabled"/>
							</c:when>
							<c:otherwise>
								<sys:treeselect id="userid" name="userid" value="${trainplan.userid}" labelName="username" labelValue="${trainplan.username}"
												title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="false"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>培训日期：</label></td>
					<td class="width-35">
						<input id="traindate" name="traindate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							   value="<fmt:formatDate value="${trainplan.traindate}" pattern="yyyy-MM-dd"/>" <c:if test="${trainplan.isadmin==null||trainplan.isadmin==''}">readonly="true"</c:if> />
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>培训标题：</label></td>
					<td class="width-35">

						<c:choose>
							<c:when test="${trainplan.isadmin==null||trainplan.isadmin==''}">
								<form:input path="title" htmlEscape="false" maxlength="200"    class="form-control required" disabled="true" />
							</c:when>
							<c:otherwise>
								<form:input path="title" htmlEscape="false" maxlength="200"    class="form-control required" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:choose>
					<c:when test="${trainplan.isadmin==null||trainplan.isadmin==''}">
						<tr>
							<td class="width-15 active"><label class="pull-right">是否完成：</label></td>
							<td class="width-35">
								<form:select path="status" class="form-control ">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</td>
							<td class="width-15 active"><label class="pull-right">完成情况：</label></td>
							<td class="width-35">
								<form:textarea path="completesituation" htmlEscape="false" rows="4"    class="form-control "/>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>培训对象：</label></td>
					<td class="width-35">
						<form:input path="trainpeople" htmlEscape="false" maxlength="200"    class="form-control required" />
					</td>
					<td class="width-15 active"></td>
					<td class="width-35">
					</td>
				</tr>
				<%--<c:if test="${trainplan.id!=null&&trainplan.id!=''}">
					<c:if test="${trainplan.mview==null||trainplan.mview==''}">
						<tr>
							<td class="width-15 active"></td>
							<td class="width-35" colspan="3">
								<input class="btn btn-primary" type="button" value="保&nbsp;&nbsp;&nbsp;存" onclick="doSubmit()"/>&nbsp;
							</td>
						</tr>
					</c:if>
				</c:if>--%>
				<%--<tr>
					<c:choose>
						<c:when test="${trainplan.id!=null&&trainplan.id!=''}"><!--修改-->
							<input id="traineeId" name="traineeIds" type="hidden" value="${trainplan.traineeIds}"/>
							<input id="traineeName" name="traineeNames" type="hidden" value="${trainplan.traineeNames}"/>
							<td  class="width-15 active">	<label class="pull-right">接受人：</label></td>
							<td class="width-35" colspan="3">
								<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
									<c:if test="${trainplan.mview==null||trainplan.mview==''}">
										<c:if test="${trainplan.isadmin!=null&&trainplan.isadmin=='1'}">
											<a href="javascript:" id="addRecord" class="btn btn-success pull-right" style="margin-bottom:10px;"><i class="fa fa-plus"></i> 新添人员</a>
										</c:if>
									</c:if>
									<thead>
									<tr>
										<th>培训对象</th>
										<th>部门</th>
										<th>操作</th>
									</tr>
									</thead>
									<tbody id="recordArea">
									<c:forEach items="${page.list}" var="trainee">
										<tr name="recordtr" vl1="${trainee.userid}" vl2="${trainee.username}">
											<td>
													${trainee.username}
											</td>
											<td>
													${trainee.officename}
											</td>
											<td>
												<a class="remove" name="delrecord" href="javascript:void(0)" title="删除"><i class="glyphicon glyphicon-remove"></i></a>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<!-- 分页代码 -->
								<table:page page="${page}"></table:page>
							</td>
						</c:when>
						<c:otherwise><!--新增-->
							<td  class="width-15 active">	<label class="pull-right">培训对象：</label></td>
							<td class="width-35" colspan="3">
								<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<span class="pull-right" style="padding-bottom: 5px;"><sys:treeselectcallbackHold id="trainee" name="traineeIds" value="${trainplan.traineeIds}" labelName="traineeNames" labelValue="${trainplan.traineeNames}"
																											  title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" isAll="true" notAllowSelectParent="true" checked="true"/></span>
									<thead>
									<tr>
										<th>培训对象</th>
										<th>操作</th>
									</tr>
									</thead>
									<tbody id="recordArea">
									<c:if test="${trainplan.id!=null&&trainplan.id!=''}">
										<c:forEach items="${trainplan.traineeList}" var="trainee">
											<tr name="recordtr" vl1="${trainee.userid}" vl2="${trainee.username}">
												<td>
														${trainee.username}
												</td>
												<td>
													<a class="remove" name="delrecord" href="javascript:void(0)" title="删除"><i class="glyphicon glyphicon-remove"></i></a>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									</tbody>
								</table>
							</td>
						</c:otherwise>
					</c:choose>
				</tr>--%>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>