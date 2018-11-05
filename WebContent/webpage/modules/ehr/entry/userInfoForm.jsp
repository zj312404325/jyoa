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
			
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="userInfo" action="${ctx}/ehr/userInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">姓名：</label></td>
					<td class="width-35">
						<form:input path="fullname" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">曾用名：</label></td>
					<td class="width-35">
						<form:input path="usedfullname" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">性别：</label></td>
					<td class="width-35">
						<form:input path="sex" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">身高：</label></td>
					<td class="width-35">
						<form:input path="bodyheight" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">体重：</label></td>
					<td class="width-35">
						<form:input path="bodyweight" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">血型：</label></td>
					<td class="width-35">
						<form:input path="blood" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">政治面貌：</label></td>
					<td class="width-35">
						<form:input path="political" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">出生年月：</label></td>
					<td class="width-35">
						<form:input path="birthday" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">身份证号码：</label></td>
					<td class="width-35">
						<form:input path="idcardno" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">籍贯：</label></td>
					<td class="width-35">
						<form:input path="origin" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">民族：</label></td>
					<td class="width-35">
						<form:input path="nation" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">宗教信仰：</label></td>
					<td class="width-35">
						<form:input path="religion" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">固定电话：</label></td>
					<td class="width-35">
						<form:input path="phone" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">移动电话：</label></td>
					<td class="width-35">
						<form:input path="mobile" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">邮箱：</label></td>
					<td class="width-35">
						<form:input path="email" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">学历：</label></td>
					<td class="width-35">
						<%--<form:input path="degree" htmlEscape="false"    class="form-control "/>--%>
						<form:select path="degree" class="form-control" style="padding:0;">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('degree')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">专业：</label></td>
					<td class="width-35">
						<form:input path="profession" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">毕业院校：</label></td>
					<td class="width-35">
						<form:input path="college" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">外语语种：</label></td>
					<td class="width-35">
						<form:input path="languages" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">婚姻状况：</label></td>
					<td class="width-35">
						<form:input path="marriage" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">有无病史：</label></td>
					<td class="width-35">
						<form:input path="disease" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">目前劳动关系：</label></td>
					<td class="width-35">
						<form:input path="workstatus" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否有亲属在本公司工作：</label></td>
					<td class="width-35">
						<form:input path="isfamily" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">是否有签署竞业禁止协议：</label></td>
					<td class="width-35">
						<form:input path="iscompetition" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">身份证：</label></td>
					<td class="width-35">
						<form:input path="iscardurl" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">毕业证：</label></td>
					<td class="width-35">
						<form:input path="certificateurl" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">学位证：</label></td>
					<td class="width-35">
						<form:input path="degreecertificateurl" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">驾驶证：</label></td>
					<td class="width-35">
						<form:input path="driverlicenseurl" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">从业资格证：</label></td>
					<td class="width-35">
						<form:input path="qualificationcertificateurl" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">离职证明：</label></td>
					<td class="width-35">
						<form:input path="leavingcertificate" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">个人简历：</label></td>
					<td class="width-35">
						<form:input path="resumeurl" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">现居住地：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">岗位：</label></td>
					<td class="width-35">
						<form:input path="position" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">试用期：</label></td>
					<td class="width-35">
						<form:input path="probationperiod" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">试用期薪资：</label></td>
					<td class="width-35">
						<form:input path="probationperiodsalary" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">免责声明姓名：</label></td>
					<td class="width-35">
						<form:input path="disclaimername" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">免责声明身份证号码：</label></td>
					<td class="width-35">
						<form:input path="disclaimerno" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">免责声明年：</label></td>
					<td class="width-35">
						<form:input path="disclaimeryear" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">免责声明月：</label></td>
					<td class="width-35">
						<form:input path="disclaimermonth" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">免责声明日：</label></td>
					<td class="width-35">
						<form:input path="disclaimerday" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">免责声明职业病：</label></td>
					<td class="width-35">
						<form:input path="disclaimerdisease" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">是否同意：</label></td>
					<td class="width-35">
						<form:input path="isagree" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">照片：</label></td>
					<td class="width-35">
						<form:input path="photo" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
		
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">入职登记家庭成员表：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="addRow('#userMemberList', userMemberRowIdx, userMemberTpl);userMemberRowIdx = userMemberRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>备注信息</th>
						<th>关系</th>
						<th>姓名</th>
						<th>年龄</th>
						<th>工作单位及职务</th>
						<th>联系电话</th>
						<th>详细住址</th>
						<th>序号</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="userMemberList">
				</tbody>
			</table>
			<script type="text/template" id="userMemberTpl">//<!--
				<tr id="userMemberList{{idx}}">
					<td class="hide">
						<input id="userMemberList{{idx}}_id" name="userMemberList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="userMemberList{{idx}}_delFlag" name="userMemberList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<textarea id="userMemberList{{idx}}_remarks" name="userMemberList[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					
					<td>
						<input id="userMemberList{{idx}}_relationship" name="userMemberList[{{idx}}].relationship" type="text" value="{{row.relationship}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="userMemberList{{idx}}_memberfullname" name="userMemberList[{{idx}}].memberfullname" type="text" value="{{row.memberfullname}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="userMemberList{{idx}}_memberage" name="userMemberList[{{idx}}].memberage" type="text" value="{{row.memberage}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="userMemberList{{idx}}_memberworkunit" name="userMemberList[{{idx}}].memberworkunit" type="text" value="{{row.memberworkunit}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="userMemberList{{idx}}_membermobile" name="userMemberList[{{idx}}].membermobile" type="text" value="{{row.membermobile}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="userMemberList{{idx}}_memberaddress" name="userMemberList[{{idx}}].memberaddress" type="text" value="{{row.memberaddress}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="userMemberList{{idx}}_sortno" name="userMemberList[{{idx}}].sortno" type="text" value="{{row.sortno}}"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#userMemberList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var userMemberRowIdx = 0, userMemberTpl = $("#userMemberTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(userInfo.userMemberList)};
					for (var i=0; i<data.length; i++){
						addRow('#userMemberList', userMemberRowIdx, userMemberTpl, data[i]);
						userMemberRowIdx = userMemberRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>