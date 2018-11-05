<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同到期人员考核申请管理</title>
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

        function callback(){
            if($("#useridId").val() != ''){
                $.post("${ctx}/checkmodel/checkUser/getUserById",
                    {
                        "userid":$("#useridId").val()
                    },function(data){
                        var jsonData = jQuery.parseJSON(data);
                        var htmlStr = '<option value="">请选择</option>';
                        if(jsonData.status == 'y'){
                            var user=jsonData.user;
                            if(user!=null){
                                $("#userid").val(user.id);
                                $("#userno").val(user.no);
                                $("#username").val(user.name);
                                $("#officeid").val(user.officeid);
                                $("#officename").val(user.officename);
                                $("#stationid").val(user.stationid);
                                $("#stationname").val(user.stationname);
							}
                        }
                    });
            }
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
			
					laydate({
			            elem: '#birth', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#entrytime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#contractdatestart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#contractdateend', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					
					$("#searchUserList").click(function(){
						searchUserList();
					});
					
					
					
					$("input.contractExpirate").on("ifChanged",function(){
			        	setTimeout(cv(),100);
			        });
		});
		
		function searchUserList(){
			$("#searchResult").hide();
			$("#userListContent").html("");
			var key=$("#searchKey").val();
			if(key!=null&&key!=''){
				//ajax提交
				$.post("${ctx}/checkmodel/checkUser/getUserList",{"keyword":key},function(data){
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						var userList=jsonData.userList;
						console.log(userList);
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
			}
		}
		
        
		function cv(){
			var valueArr= $("input.contractExpirate:checked");
			var sumValue=0;
			   if(valueArr.size()>0||valueArr != null){  
			       for (var i=0;i<valueArr.size();i++ )  
			        {    
			            if(isNaN($(valueArr[i]).attr("data-sum"))||$(valueArr[i]).attr("data-sum")==null||$(valueArr[i]).attr("data-sum")==""){  
			               sumValue +=0;  
			            }

			            else{  
			               //alert(valueArr[i].value);  
			               sumValue +=parseFloat($(valueArr[i]).attr("data-sum"));
			               
			            }  
			              
			        }  
			   }  
			   if(sumValue>0){  
			        $('#score').val(parseInt(sumValue));  
			   }  
			   else{  
			       $('#score').val(0);  
			   }  
		}
		function selectUser(obj){
			var $data=$(obj);
			var userid=$data.attr("vl");
			$("#userid").val(userid);
			var username=$data.attr("vl1");
			$("#username").val(username);
			var userno=$data.attr("vl2");
			$("#userno").val(userno);
			var officeid=$data.attr("vl3");
			$("#officeid").val(officeid);
			var officename=$data.attr("vl4");
			$("#officename").val(officename);
			var stationid=$data.attr("vl5");
			$("#stationid").val(stationid);
			var stationname=$data.attr("vl6");
			$("#stationname").val(stationname);
		}
	</script>
</head>
<body class="hideScroll">
	<div class="wrapper wrapper-content">
		<form:form id="inputForm" modelAttribute="contractExpirate" action="${ctx}/checkmodel/contractExpirate/save" method="post" class="form-inline">
		<input type="hidden" name="type" value="${type }" />
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<%--<c:if test="${mtd==null||mtd=='' }">
			<div class="row" style="margin-bottom: 12px;">
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
		</c:if>--%>
		<%--<form:hidden path="userid"/>--%>
		<form:hidden path="userno"/>
		<form:hidden path="officeid"/>
		<form:hidden path="stationid"/>
		<form:hidden path="checkuserid"/>
		<form:hidden path="checkuserno"/>
		<form:hidden path="checkofficeid"/>
		<form:hidden path="checkstationid"/>
		<table class="table table-bordered assessment_table" style="text-align:center; width:820px">
					<tr>
						<td width="16.6%"><font color="red">*</font><strong>被考核人姓名</strong></td>
						<td width="16.6%">
							<%--<input type="text" id="username" name="username" readonly="readonly" class="form-control required"  value="${contractExpirate.username }" />--%>
							<sys:treeselectCallBackFun id="userid" name="userid" value="${contractExpirate.userid}" labelName="username" labelValue="${contractExpirate.username}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
						</td>
						<td width="16.6%"><font color="red">*</font><strong>部门</strong></td>
						<td width="16.6%"><input type="text" id="officename" name="officename" readonly="readonly" class="form-control required" value="${contractExpirate.officename }" /></td>
						<td width="16.6%"><strong>岗位</strong></td>
						<td width="16.6%"><input type="text" id="stationname" name="stationname" readonly="readonly" class="form-control" value="${contractExpirate.stationname }" /></td>
					</tr>
					<tr>
						<td width="16.6%"><strong>专业</strong></td>
						<td width="16.6%"><input type="text" id="major" name="major" class="form-control" value="${contractExpirate.major }" /></td>
						<td width="16.6%"><strong>学历</strong></td>
						<td width="16.6%"><input type="text" id="education" name="education" class="form-control" value="${contractExpirate.education }" /></td>
						<td width="16.6%"><strong>出生年月</strong></td>
						<td width="16.6%"><input id="birth" name="birth" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${contractExpirate.birth}" pattern="yyyy-MM-dd"/>"/></td>
					</tr>
					<tr>
						<td width="16.6%"><strong>入职时间</strong></td>
						<td width="16.6%"><input id="entrytime" name="entrytime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${contractExpirate.entrytime}" pattern="yyyy-MM-dd"/>"/></td>
						<td width="16.6%"><strong>家庭地址</strong></td>
						<td colspan="3"><form:input path="address" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					</tr>
					<tr>
						<td width="16.6%"><strong>考核合同期</strong></td>
						<td colspan="5"><input id="contractdatestart" name="contractdatestart" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${contractExpirate.contractdatestart}" pattern="yyyy-MM-dd"/>"/>至
							<input id="contractdateend" name="contractdateend" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${contractExpirate.contractdateend}" pattern="yyyy-MM-dd"/>"/></td>
					</tr>
					<tr>
						<td width="16.6%"><strong>考核人姓名</strong></td>
						<td width="16.6%"><input type="text" id="checkusername" name="checkusername" readonly="readonly" class="form-control required" value="${contractExpirate.checkusername }" /></td>
						<td width="16.6%"><strong>部门</strong></td>
						<td width="16.6%"><input type="text" id="checkofficename" name="checkofficename" readonly="readonly" class="form-control required" value="${contractExpirate.checkofficename }" /></td>
						<td width="16.6%"><strong>岗位</strong></td>
						<td width="16.6%"><input type="text" id="checkstationname" name="checkstationname" readonly="readonly" class="form-control" value="${contractExpirate.checkstationname }" /></td>
					</tr>
				</table>
				<table class="table table-bordered assessment_table" style="text-align:center; width:820px">
					<tr style="background:rgb(255,204,153)">
						<td width="16.6%"><strong>类别</strong></td>
						<td colspan="5"><strong>指标</strong></td>
					</tr>
					<tr>
						<td rowspan="4">知识<br/>技能</td>
						<td colspan="5" class="text-left"><input type="radio"  data-sum="10" name="knowledgeskill" class="i-checks contractExpirate" value="1" <c:if test="${contractExpirate.knowledgeskill=='1' }">checked="checked"</c:if> /><small>A：对本岗位业务知识模糊，理论基础较差，工作无思路，技能欠缺，无法满足职务需求</small></td>
					</tr>
					<tr>
						<td colspan="5" class="text-left"><input type="radio"  data-sum="12" name="knowledgeskill" class="i-checks contractExpirate" value="2" <c:if test="${contractExpirate.knowledgeskill=='2' }">checked="checked"</c:if> /><small>B：对本岗位业务知识及理论基础稍有欠缺，工作技能有待提高，经过努力和指导能满足岗位职务需求</small></td>
					</tr>
					<tr>
						<td colspan="5" class="text-left"><input type="radio"  data-sum="15" name="knowledgeskill" class="i-checks contractExpirate" value="3" <c:if test="${contractExpirate.knowledgeskill=='3' }">checked="checked"</c:if> /><small>C：对本岗位业务知识较为丰富，理论功底扎实，工作技能熟练，专业素养较好，能够满足岗位需求</small></td>
					</tr>
					<tr>
						<td colspan="5" class="text-left"><input type="radio"  data-sum="16" name="knowledgeskill" class="i-checks contractExpirate" value="4" <c:if test="${contractExpirate.knowledgeskill=='4' }">checked="checked"</c:if> /><small>D：对本岗位业务知识及理论功底扎实，并具有一定的广度，工作中表现出高超的专业技能，完全胜任或超出岗位职务需求</small></td>
					</tr>
					<tr>
						<td rowspan="4">执<br/>行<br/>力</td>
						<td colspan="5" class="text-left"><input type="radio" data-sum="10" name="execute" class="i-checks contractExpirate" value="1" <c:if test="${contractExpirate.execute=='1' }">checked="checked"</c:if> /><small>A：经常无法正常贯彻指令，不能按照进程执行下达的工作任务，不能完成工作计划，执行中不讲求实效</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="12" name="execute" class="i-checks contractExpirate" value="2" <c:if test="${contractExpirate.execute=='2' }">checked="checked"</c:if> /><small>B：在监督下能够贯彻指令和执行下达的任务，能够完成大部分工作任务，执行中比较讲求实效，但缺乏反馈</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="15" name="execute" class="i-checks contractExpirate" value="3" <c:if test="${contractExpirate.execute=='3' }">checked="checked"</c:if> /><small>C：能够较好的贯彻指令，执行下达的任务，能较好的完成工作任务和工作计划，执行中务实、讲求实效，能身体力行的推进工作，并能够经常反馈</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="16" name="execute" class="i-checks contractExpirate" value="4" <c:if test="${contractExpirate.execute=='4' }">checked="checked"</c:if> /><small>D：能够贯彻指令和执行下达和各项任务，能出色的按照进程完成任务和工作计划，执行中极其务实、讲求实效，在困难情况下，仍能够调集资源并创造条件执行并保持及时反馈</small></td>
					</tr>
					<tr>
						<td rowspan="4">组织<br/>协调</td>
						<td colspan="5" class="text-left"><input type="radio" data-sum="10" name="organization" class="i-checks contractExpirate" value="1" <c:if test="${contractExpirate.organization=='1' }">checked="checked"</c:if> /><small>A：沟通能力欠缺，不能有效的调配资源，缺乏组织协调能力，无法达成目标</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="12" name="organization" class="i-checks contractExpirate" value="2" <c:if test="${contractExpirate.organization=='2' }">checked="checked"</c:if> /><small>B：具备基本的沟通能力但主动性欠缺，经过指导可以有效调配资源，具备一定的组织协调能力但方法欠佳，基本能够达成目标</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="15" name="organization" class="i-checks contractExpirate" value="3" <c:if test="${contractExpirate.organization=='3' }">checked="checked"</c:if> /><small>C：具备良好的沟通能力，能够有效调配资源，组织协调方式得当，顺利达成组织目标</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="16" name="organization" class="i-checks contractExpirate" value="4" <c:if test="${contractExpirate.organization=='4' }">checked="checked"</c:if> /><small>D：具备优秀沟通能力与技巧，善于调配各种资源，运用不同的方式组织协调工作，圆满达成工作目标</small></td>
					</tr>
					<tr>
						<td rowspan="4">工作<br/>态度</td>
						<td colspan="5" class="text-left"><input type="radio" data-sum="10" name="formulate" class="i-checks contractExpirate" value="1" <c:if test="${contractExpirate.formulate=='1' }">checked="checked"</c:if> /><small>A：不能严格遵守公司制度，职业操守较差，工作消极、被动，经督促仍不改进，工作缺乏热情，遇到困难及挫折退缩不前</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="12" name="formulate" class="i-checks contractExpirate" value="2" <c:if test="${contractExpirate.formulate=='2' }">checked="checked"</c:if> /><small>B：能够按照公司规定要求自己，具有一定的职业道德，工作中有基本的责任心，遇到困难及挫折经帮助能够克服</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="15" name="formulate" class="i-checks contractExpirate" value="3" <c:if test="${contractExpirate.formulate=='3' }">checked="checked"</c:if> /><small>C：能够按照公司规定要求自己，具有良好的职业道德，工作敬业，有良好的责任心且较为主动，遇到困难及挫折能积极调整自身并保持工作热情</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="16" name="formulate" class="i-checks contractExpirate" value="4" <c:if test="${contractExpirate.formulate=='4' }">checked="checked"</c:if> /><small>D：严于律己，品德端正，具有优秀的职业道德，具有很强的敬业精神和责任心，工作积极主动，遇到较大的困难时能保持饱满的工作热情</small></td>
					</tr>
					<tr>
						<td rowspan="4">学习<br/>能力</td>
						<td colspan="5" class="text-left"><input type="radio" data-sum="10" name="learn" class="i-checks contractExpirate" value="1" <c:if test="${contractExpirate.learn=='1' }">checked="checked"</c:if> /><small>A：学习能力较低或主观上基本不进行自我学习与提升，能力一直停滞于现状</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="12" name="learn" class="i-checks contractExpirate" value="2" <c:if test="${contractExpirate.learn=='2' }">checked="checked"</c:if> /><small>B：有一定的学习意愿及能力，并经常督促自我学习，通过学习自身能力稍有提高，但收效不明显</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="15" name="learn" class="i-checks contractExpirate" value="3" <c:if test="${contractExpirate.learn=='3' }">checked="checked"</c:if> /><small>C：重视自我学习，学习能力较强，面对新事务及新工作能较快适应并不断钻研以获取专业技能和知识的提升，自身工作能力有较大提高</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="16" name="learn" class="i-checks contractExpirate" value="4" <c:if test="${contractExpirate.learn=='4' }">checked="checked"</c:if> /><small>D：学习能力与意愿较强，能够快速适应新工作、掌握新事务的要点并做到举一反三，变通性强，工作中思路灵活，视野开阔，自身能力有显著提高</small></td>
					</tr>
					<tr>
						<td rowspan="4">创新<br/>能力</td>
						<td colspan="5" class="text-left"><input type="radio" data-sum="10" name="innovate" class="i-checks contractExpirate" value="1" <c:if test="${contractExpirate.innovate=='1' }">checked="checked"</c:if> /><small>A：不提倡创新</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="12" name="innovate" class="i-checks contractExpirate" value="2" <c:if test="${contractExpirate.innovate=='2' }">checked="checked"</c:if> /><small>B：知道创新的重要性，但没有开展的思路</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="15" name="innovate" class="i-checks contractExpirate" value="3" <c:if test="${contractExpirate.innovate=='3' }">checked="checked"</c:if> /><small>C：积极主动完成各项任务，主动思考，改进工作思路与方法，并不断提高工作效率与质量，取得成果</small></td>
					</tr>							                  
					<tr>							                  
						<td colspan="5" class="text-left"><input type="radio" data-sum="16" name="innovate" class="i-checks contractExpirate" value="4" <c:if test="${contractExpirate.innovate=='4' }">checked="checked"</c:if> /><small>D：运用专业知识与技能，不断提供新思想、新理论、新方法，突破固有思维，增强企业竞争力</small></td>
					</tr>
					<tr>
						<td width="16.6%"><font color="red">*</font><strong>合计</strong></td>
						<td width="16.6%"><form:input path="score" htmlEscape="false"  class="form-control required digits"/></td>
						<td colspan="4" width="66.6%">注：A：10分；B：12分；C：15分；D：16分；80分为合格分</td>
					</tr>
					<tr>
						<td width="16.6%">考核人对员工<br/>评价及改进要求</td>
						<td colspan="5"><textarea id="evaluate" name="evaluate" maxlength="200">${contractExpirate.evaluate}</textarea></td>
					</tr>
					<tr>
						<td width="16.6%">考核人建议</td>
						<td colspan="5" class="text-left">
							<div class="radio">
								<label>
									<input type="radio" name="recommend" id="optionsRadios1" class="i-checks" value="0" <c:if test="${contractExpirate.recommend=='0' }">checked="checked"</c:if> >
									续订合同
								</label>
							</div>
							<div class="radio">
								<label>
									<input type="radio" name="recommend" id="optionsRadios2" class="i-checks" value="1" <c:if test="${contractExpirate.recommend=='1' }">checked="checked"</c:if> >
									终止合同
								</label>
							</div>
							<div class="radio">
								<label>
									<input type="radio" name="recommend" id="optionsRadios3" class="i-checks" value="2" <c:if test="${contractExpirate.recommend=='2' }">checked="checked"</c:if> >
									其他 :
								</label>
								<input type="text" id="recommendother" name="recommendother" style="width:400px; text-align:left" maxlength="100" value="${contractExpirate.recommendother}" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="16.6%">分管领导建议</td>
						<td colspan="5"><textarea id="leaderrecommend" name="leaderrecommend" maxlength="200" <c:if test="${user.userType!='5'&&user.userType!='2' }">readonly="readonly"</c:if> >${contractExpirate.leaderrecommend}</textarea></td>
					</tr>
					<tr>
						<td width="16.6%">行政人资部建议</td>
						<td colspan="5"><textarea id="hrrecommend" name="hrrecommend" maxlength="200" <c:if test="${user.userType!='4' }">readonly="readonly"</c:if> >${contractExpirate.hrrecommend}</textarea></td>
					</tr>
					<tr>
						<td width="16.6%">总经理建议</td>
						<td colspan="5"><textarea id="ceorecommend" name="ceorecommend" maxlength="200" <c:if test="${user.userType!='2' }">readonly="readonly"</c:if> >${contractExpirate.ceorecommend}</textarea></td>
					</tr>
				</table>
	</form:form>
	</div>
</body>
</html>