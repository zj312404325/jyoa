<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>离职调查问卷管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  setRadioValue();
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
			
			laydate({
	            elem: '#entrytime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
			
			$("#sub").click(function(){
				doSubmit();
			});
		});
		
		function setRadioValue(){
			var internalreasonArray=[];
			var internalreason;
			var externalreasonArray=[];
			var externalreason;
			var improveArray=[];
			var improve;
			var internalreasonCheck=$("input[type=checkbox][name=internalreason1]:checked");
			$.each(internalreasonCheck,function(i,n){
				internalreasonArray.push(n.value);
			});
			if(internalreasonArray.length>0){
				internalreason=','+internalreasonArray.join(',')+',';
				$("#internalreason").val(internalreason);
			}
			var externalreasonCheck=$("input[type=checkbox][name=externalreason1]:checked");
			$.each(externalreasonCheck,function(i,n){
				externalreasonArray.push(n.value);
			});
			if(externalreasonArray.length>0){
				externalreason=','+externalreasonArray.join(',')+',';
				$("#externalreason").val(externalreason);
			}
			var improveCheck=$("input[type=checkbox][name=improve1]:checked");
			$.each(improveCheck,function(i,n){
				improveArray.push(n.value);
			});
			if(improveArray.length>0){
				improve=','+improveArray.join(',')+',';
				$("#improve").val(improve);
			}
		}
	</script>
</head>
<body class="hideScroll gray-bg">
<div class="wrapper wrapper-content">
		<form:form id="inputForm" modelAttribute="questionSurvey" action="${ctx}/ehr/questionSurvey/save?isadmin=${isadmin}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="internalreason"/>
		<form:hidden path="externalreason"/>
		<form:hidden path="improve"/>
		<sys:message content="${message}"/>	
		<div class="ibox">
			<div class="ibox-title">
				<h5>离职调查问卷</h5>
			</div>
			    
			<div class="ibox-content">
				<table class="table table-bordered assessment_table" style="text-align:center; width:820px">
					<tr>
						<td width="12.5%"><strong>离职人姓名</strong></td>
						<td width="12.5%"><form:input path="username" htmlEscape="false"    class="form-control required"/></td>
						<td width="12.5%"><strong>所在部门</strong></td>
						<td width="12.5%"><form:input path="officename" htmlEscape="false"    class="form-control required"/></td>
						<td width="12.5%"><strong>职务</strong></td>
						<td width="12.5%"><form:input path="post" htmlEscape="false"    class="form-control "/></td>
						<td width="12.5%"><strong>入职时间</strong></td>
						<td width="12.5%"><input id="entrytime" name="entrytime" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${questionSurvey.entrytime}" pattern="yyyy-MM-dd"/>"/></td>
					</tr>
					<tr>
						<td colspan="8" style="text-align:left;">感谢您为公司的付出与贡献，为您选择离开公司，我们表示遗憾。为更深入了解您离职的真实原因，从而改进我们的工作，我们设计了此问卷，期盼获得您的宝贵意见，谢谢配合！</td>
					</tr>
					<tr>
						<td rowspan="2">
							请指出您离职最主要的原因（在恰当处打“√”）
						</td>
						<td>内部原因</td>
						<td colspan="6" style="text-align:left;">
							<div class="row">
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="1" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',1,')}">checked="checked"</c:if> > 工资低
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="2" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',2,')}">checked="checked"</c:if> > 工作时间
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="3" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',3,')}">checked="checked"</c:if> > 无晋升机会
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="4" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',4,')}">checked="checked"</c:if> > 与领导关系不和谐
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="5" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',5,')}">checked="checked"</c:if> > 工作量大、压力大
									  	</label>				                                                                                                      
									</div>						                                                                                                      
									<div class="checkbox">				                                                                                                      
										<label>					                                                                                                      
									    	<input type="checkbox" class="i-checks" value="6" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',6,')}">checked="checked"</c:if> > 不满意公司的政策制度
									  	</label>				                                                                                                      
									</div>						                                                                                                      
									<div class="checkbox">				                                                                                                      
										<label>					                                                                                                      
									    	<input type="checkbox" class="i-checks" value="7" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',7,')}">checked="checked"</c:if> > 工作环境
									  	</label>				                                                                                                      
									</div>						                                                                                                      
									<div class="checkbox">				                                                                                                      
										<label>					                                                                                                      
									    	<input type="checkbox" class="i-checks" value="8" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',8,')}">checked="checked"</c:if> > 无发展空间
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="9"  name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',9,')}">checked="checked"</c:if> > 领导分工不公正
									  	</label>				                                                                                                                                        
									</div>						                                                                                                                                        
									<div class="checkbox">				                                                                                                                                        
										<label>					                                                                                                                                        
									    	<input type="checkbox" class="i-checks" value="10" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',10,')}">checked="checked"</c:if> > 与同事关系不融洽
									  	</label>				                                                                                                                                        
									</div>						                                                                                                                                        
									<div class="checkbox">				                                                                                                                                        
										<label>					                                                                                                                                        
									    	<input type="checkbox" class="i-checks" value="11" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',11,')}">checked="checked"</c:if> > 不满意公司的福利
									  	</label>				                                                                                                                                        
									</div>						                                                                                                                                        
									<div class="checkbox">				                                                                                                                                        
										<label>					                                                                                                                                        
									    	<input type="checkbox" class="i-checks" value="12" name="internalreason1" <c:if test="${fn:contains(questionSurvey.internalreason,',12,')}">checked="checked"</c:if> > 其他
									  	</label>
									</div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>外部原因</td>
						<td colspan="6" style="text-align:left;">
							<div class="row">
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="1" name="externalreason1" <c:if test="${fn:contains(questionSurvey.externalreason,',1,')}">checked="checked"</c:if>> 找到更好的企业
									  	</label>				                                                                                                                         
									</div>						                                                                                                                         
									<div class="checkbox">				                                                                                                                         
										<label>					                                                                                                                         
									    	<input type="checkbox" class="i-checks" value="2" name="externalreason1" <c:if test="${fn:contains(questionSurvey.externalreason,',2,')}">checked="checked"</c:if>> 家庭原因
									  	</label>				                                                                                                                         
									</div>						                                                                                                                         
									<div class="checkbox">				                                                                                                                         
										<label>					                                                                                                                         
									    	<input type="checkbox" class="i-checks" value="3" name="externalreason1" <c:if test="${fn:contains(questionSurvey.externalreason,',3,')}">checked="checked"</c:if>> 个人身体健康原因
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="4" name="externalreason1" <c:if test="${fn:contains(questionSurvey.externalreason,',4,')}">checked="checked"</c:if>> 自己创业
									  	</label>				                                                                                                                         
									</div>						                                                                                                                         
									<div class="checkbox">				                                                                                                                         
										<label>					                                                                                                                         
									    	<input type="checkbox" class="i-checks" value="5" name="externalreason1" <c:if test="${fn:contains(questionSurvey.externalreason,',5,')}">checked="checked"</c:if>> 转换行业
									  	</label>				                                                                                                                         
									</div>						                                                                                                                         
									<div class="checkbox">				                                                                                                                         
										<label>					                                                                                                                         
									    	<input type="checkbox" class="i-checks" value="6" name="externalreason1" <c:if test="${fn:contains(questionSurvey.externalreason,',6,')}">checked="checked"</c:if>> 交通不便
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									&nbsp;
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>你认为公司哪些方面需要改善（可选择多项）</td>
						<td colspan="7" style="text-align:left;">
							<div class="row">
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="1" name="improve1" <c:if test="${fn:contains(questionSurvey.improve,',1,')}">checked="checked"</c:if>> 领导管理能力
									  	</label>				                                                                                                                         
									</div>						                                                                                                                         
									<div class="checkbox">				                                                                                                                         
										<label>					                                                                                                                         
									    	<input type="checkbox" class="i-checks" value="2" name="improve1" <c:if test="${fn:contains(questionSurvey.improve,',2,')}">checked="checked"</c:if>> 工资及福利
									  	</label>				                                                                                                                         
									</div>						                                                                                                                         
									<div class="checkbox">				                                                                                                                         
										<label>					                                                                                                                         
									    	<input type="checkbox" class="i-checks" value="3" name="improve1" <c:if test="${fn:contains(questionSurvey.improve,',3,')}">checked="checked"</c:if>> 其他
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="4" name="improve1" <c:if test="${fn:contains(questionSurvey.improve,',4,')}">checked="checked"</c:if>> 部门之间的沟通
									  	</label>				                                                                                                                         
									</div>						                                                                                                                         
									<div class="checkbox">				                                                                                                                         
										<label>					                                                                                                                         
									    	<input type="checkbox" class="i-checks" value="5" name="improve1" <c:if test="${fn:contains(questionSurvey.improve,',5,')}">checked="checked"</c:if>> 团队合作
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value="6" name="improve1" <c:if test="${fn:contains(questionSurvey.improve,',6,')}">checked="checked"</c:if>> 员工发展机会
									  	</label>				                                                                                                                         
									</div>						                                                                                                                         
									<div class="checkbox">				                                                                                                                         
										<label>					                                                                                                                         
									    	<input type="checkbox" class="i-checks" value="7" name="improve1" <c:if test="${fn:contains(questionSurvey.improve,',7,')}">checked="checked"</c:if>> 工作环境及设施
									  	</label>
									</div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="8" style="text-align:left;">
							<p>您在公司感觉最满意的地方是什么？最不满意的是什么？</p>
							<form:textarea path="statisfaction" htmlEscape="false"  maxlength="500"    class="form-control "/>
						</td>
					</tr>
					<tr>
						<td colspan="8" style="text-align:left;">
							<p>对您的部门经理有何评价？</p>
							<form:textarea path="price" htmlEscape="false"  maxlength="500"    class="form-control "/>
						</td>
					</tr>
					<tr>
						<td colspan="8" style="text-align:left;">
							<p>如果公司哪一方面改善，您会愿意继续留在公司？</p>
							<form:textarea path="improvestay" htmlEscape="false" rows="4" maxlength="500"    class="form-control "/>
						</td>
					</tr>
				</table>
				<c:if test="${isadmin==null||isadmin==''}">
					<c:if test="${questionSurvey.managerview=='0' }">
						<div class="row" style="padding-top:20px; padding-left:390px;">
							<a href="javascript:" class="btn btn-primary" id="sub">提&nbsp;交</a>
						</div>
					</c:if>
				</c:if>
			</div>
		</div>
	</form:form>
	</div>
</body>
</html>