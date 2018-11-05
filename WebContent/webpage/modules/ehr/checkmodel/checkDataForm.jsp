<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>绩效数据设定管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  //设置详情
			  if(!setCheckDataDetail()){
				  return false;
			  }
			  //验证权重
			  if(!validCheckData()){
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
			
			$("#searchUserList").click(function(){
				searchUserList();
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
						/* $.each(userList,function(i,n){
							htmlstr+="<li>";
							htmlstr+="姓名："+n.name+"&nbsp;&nbsp;";
							htmlstr+="工号："+n.no+"&nbsp;&nbsp;";
							htmlstr+="部门："+n.officename+"&nbsp;&nbsp;";
							htmlstr+="岗位："+n.stationname+"&nbsp;&nbsp;";
							htmlstr+='<input type="button" value="选择" onclick="selectUser(this)" vl="'+n.id+'" vl1="'+n.name+'" vl2="'+n.no+'" vl3="'+n.officeid+'" vl4="'+n.officename+'" vl5="'+n.stationid+'" vl6="'+n.stationname+'"  />';
							htmlstr+="</li>";
						}); */
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
		<form:form id="inputForm" modelAttribute="checkData" action="${ctx}/checkmodel/checkData/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group form-inline">
					<span>员工姓名/编码：</span>
					<input type="text" id="searchKey" htmlEscape="false" class="form-control input-sm" maxlength="200" />
					<button type="button" class="btn btn-primary btn-rounded btn-outline btn-sm " id="searchUserList" style="margin-left:20px;"><i class="fa fa-search"></i> 查询</button>
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
		<form:hidden path="stationid" htmlEscape="false" value="${stationid}"/>
		<form:hidden path="userid" htmlEscape="false" value="${userid}"/>
		<form:hidden path="officeid" htmlEscape="false" value="${officeid}"/>
		<form:hidden path="detailJson" htmlEscape="true" value=""/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>员工编码：</label></td>
					<td class="width-35">
						<form:input path="userno" htmlEscape="false"    class="form-control required" readonly="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>员工姓名：</label></td>
					<td class="width-35">
						<form:input path="username" htmlEscape="false"    class="form-control required" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门：</label></td>
					<td class="width-35">
						<form:input path="officename" htmlEscape="false"    class="form-control required" readonly="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>岗位：</label></td>
					<td class="width-35">
						<form:input path="stationname" htmlEscape="false"    class="form-control required" readonly="true"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		
		<div class="tabs-container" id="keyContent">
        	<ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">绩效考核指标</a>
                </li>
                <%--<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">关键绩效考核指标</a>
                </li>--%>
            </ul>
        	<div class="tab-content">
            	<div id="tab-1" class="tab-pane active">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-sm-12" id="div_basic">
                            	<div class="row">
                            		<div class="col-sm-12">
                            			<div class="pull-left">
                            				<button type="button" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="addLine()" title="添加"><i class="fa fa-plus"></i> 添加</button>
                            				
                            			</div>
                            		</div>
                            	</div>
                            	<table class="table table-bordered table-hover">
								  <tr>
								    <th>关键指标</th>
								    <th>绩效衡量标准</th>
								    <th>权重（%）</th>
								    <th>操作</th>
								  </tr>
								  <c:forEach items="${checkData.checkDataDetailList}" var="checkDataDetail" varStatus="s">
								  	<c:if test="${checkDataDetail.type=='0' }">
									  	<tr>
									  		<td><input type="text" class="form-control" name="td_kpi" value="${checkDataDetail.kpi }" maxlength="50"/></td>
									  		<td><input type="text" class="form-control" name="td_referencepoint" value="${checkDataDetail.referencepoint }" maxlength="50"/></td>
									  		<td><input type="text" class="form-control" name="td_weight" value="${checkDataDetail.weight }" /><input type="hidden" name="td_type" value="${checkDataDetail.type }" /></td>
									  		<td><button type="button" class="btn btn-white btn-sm" onclick="delLine(this)" data-toggle="tooltip" data-placement="top"><i class="fa fa-trash-o"> 删除</i></button></td>
									  	</tr>
									 </c:if>
								  </c:forEach>
								</table>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div id="tab-2" class="tab-pane">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-sm-12" id="div_key">
                            	<div class="row">
                            		<div class="col-sm-12">
                            			<div class="pull-left">
                            				<button type="button" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="addLine()" title="添加"><i class="fa fa-plus"></i> 添加</button>
                 
                            			</div>
                            		</div>
                            	</div>
                            	<table class="table table-bordered table-hover">
								  <tr>
								    <th>本期工作计划/指标</th>
								    <th>绩效衡量标准</th>
								    <th>权重（%）</th>
								    <th>操作</th>
								  </tr>
								  <c:forEach items="${checkData.checkDataDetailList}" var="checkDataDetail" varStatus="s">
								  	<c:if test="${checkDataDetail.type=='1' }">
									  	<tr>
									  		<td><input type="text" class="form-control" name="td_kpi" value="${checkDataDetail.kpi }" maxlength="50"/></td>
									  		<td><input type="text" class="form-control" name="td_referencepoint" value="${checkDataDetail.referencepoint }" maxlength="50"/></td>
									  		<td><input type="text" class="form-control" name="td_weight" value="${checkDataDetail.weight }" /><input type="hidden" name="td_type" value="${checkDataDetail.type }" /></td>
									  		<td><button type="button" class="btn btn-white btn-sm" onclick="delLine(this)" data-toggle="tooltip" data-placement="top"><i class="fa fa-trash-o"> 删除</i></button></td>
									  	</tr></c:if>
								  </c:forEach>
								</table>
                            </div>
                        </div>
                    </div>
                </div>
                
                
            </div>
        </div>
		
		
		<%-- <div id="keyContent">
			<ul>
				<li class="li_active" id="li_basic">基本绩效考核指标</li>
				<li id="li_key">关键绩效考核指标</li>
				<li><input type="button" id="input_add" value="增加" /><input type="button" id="input_del" value="删除" /></li>
			</ul>
			<div style="clear:both;"></div>
			<div id="div_basic">
				<table border="1">
				  <tr>
				    <th>关键指标</th>
				    <th>绩效衡量标准</th>
				    <th>权重（%）</th>
				  </tr>
				  <c:forEach items="${checkData.checkDataDetailList}" var="checkDataDetail" varStatus="s">
				  	<c:if test="${checkDataDetail.type=='0' }">
					  	<tr>
					  		<td><input type="text" name="td_kpi" value="${checkDataDetail.kpi }" /></td>
					  		<td><input type="text" name="td_referencepoint" value="${checkDataDetail.referencepoint }" /></td>
					  		<td><input type="text" name="td_weight" value="${checkDataDetail.weight }" /><input type="hidden" name="td_type" value="${checkDataDetail.type }" /></td>
					  	</tr>
					 </c:if>
				  </c:forEach>
				</table>
			</div>
			<div id="div_key" style="display:none;">
				<table>
				  <tr>
				    <th>关键指标</th>
				    <th>绩效衡量标准</th>
				    <th>权重（%）</th>
				  </tr>
				  <c:forEach items="${checkData.checkDataDetailList}" var="checkDataDetail" varStatus="s">
				  	<c:if test="${checkDataDetail.type=='1' }">
					  	<tr>
					  		<td><input type="text" name="td_kpi" value="${checkDataDetail.kpi }" /></td>
					  		<td><input type="text" name="td_referencepoint" value="${checkDataDetail.referencepoint }" /></td>
					  		<td><input type="text" name="td_weight" value="${checkDataDetail.weight }" /><input type="hidden" name="td_type" value="${checkDataDetail.type }" /></td>
					  	</tr></c:if>
				  </c:forEach>
				</table>
			</div>
		</div> --%>
	</form:form>
</div>
	
	<script>
		$(function(){
			/* $("#li_basic").click(function(){
				$(this).addClass("li_active");
				$("#li_key").removeClass("li_active");
				$("#div_basic").show();
				$("#div_key").hide();
			});
			
			$("#li_key").click(function(){
				$(this).addClass("li_active");
				$("#li_basic").removeClass("li_active");
				$("#div_key").show();
				$("#div_basic").hide();
			}); */
			
			/* $("#input_add").click(function(){
				addLine();
			});
			
			$("#input_del").click(function(){
				delLine();
			}); */
		})
		
		//加行
		function addLine(){
			if($("#tab-1").is(":visible")){
				$("#tab-1 table").find("tr:last").after('<tr><td><input type="text" class="form-control" name="td_kpi" maxlength="50"/></td><td><input type="text" class="form-control" name="td_referencepoint" maxlength="50"/></td><td><input type="text" class="form-control" name="td_weight" /><input type="hidden" name="td_type" value="0" /></td><td><button type="button" class="btn btn-white btn-sm" onclick="delLine(this)" data-toggle="tooltip" data-placement="top"><i class="fa fa-trash-o"> 删除</i></button></td></tr>');
			}
			if($("#tab-2").is(":visible")){
				$("#tab-2 table").find("tr:last").after('<tr><td><input type="text" class="form-control" name="td_kpi" maxlength="50"/></td><td><input type="text" class="form-control" name="td_referencepoint" maxlength="50"/></td><td><input type="text" class="form-control" name="td_weight" /><input type="hidden" name="td_type" value="1" /></td><td><button type="button" class="btn btn-white btn-sm" onclick="delLine(this)" data-toggle="tooltip" data-placement="top"><i class="fa fa-trash-o"> 删除</i></button></td></tr>');
			}
		}
		
		//减行
		function delLine(_this){
			$(_this).closest("tr").remove();
		}
		
		//设置详细列表
		function setCheckDataDetail(){
			$("#detailJson").val("");
			
			var jsonTemp="";
			var jsonArray=[];
			//基本
			var flag=true;
			var detailBasicList=$("#div_basic table").find("tr:gt(0)");
			$.each(detailBasicList,function(i,n){
				var kpi=$(this).find("input[name=td_kpi]:first").val();
				var referencepoint=$(this).find("input[name=td_referencepoint]:first").val();
				var weight=$(this).find("input[name=td_weight]:first").val();
				var type=$(this).find("input[name=td_type]:first").val();
				if(kpi==''&&referencepoint==''&&weight==''){
				}else{
					if(kpi==''){
						alert("绩效考核关键指标不能为空！");
						flag=false;
						return false;
					}
					if(referencepoint==''){
						alert("绩效考核绩效衡量标准不能为空！");
						flag=false;
						return false;
					}
					if(weight==''){
						alert("绩效考核权重不能为空！");
						flag=false;
						return false;
					}
					if(isNaN(weight)){
						alert("绩效考核权重不是数字！");
						flag=false;
						return false;
					}
					jsonTemp='{';
					jsonTemp+='"kpi":'+'"'+kpi+'",';
					jsonTemp+='"referencepoint":'+'"'+referencepoint+'",';
					jsonTemp+='"weight":'+'"'+weight+'",';
					jsonTemp+='"type":'+'"'+type+'"';
					jsonTemp+='}';
					jsonArray.push(jsonTemp);
				}
			});
			
			//关键
			var detailKeyList=$("#div_key table").find("tr:gt(0)");
			$.each(detailKeyList,function(i,n){
				var kpi=$(this).find("input[name=td_kpi]:first").val();
				var referencepoint=$(this).find("input[name=td_referencepoint]:first").val();
				var weight=$(this).find("input[name=td_weight]:first").val();
				var type=$(this).find("input[name=td_type]:first").val();
				if(kpi==''&&referencepoint==''&&weight==''){
				}else{
					if(kpi==''){
						alert("关键绩效考核关键指标不能为空！");
						flag=false;
						return false;
					}
					if(referencepoint==''){
						alert("关键绩效考核绩效衡量标准不能为空！");
						flag=false;
						return false;
					}
					if(weight==''){
						alert("关键绩效考核权重不能为空！");
						flag=false;
						return false;
					}
					if(isNaN(weight)){
						alert("关键绩效考核权重不是数字！");
						flag=false;
						return false;
					}
					jsonTemp='{';
					jsonTemp+='"kpi":'+'"'+kpi+'",';
					jsonTemp+='"referencepoint":'+'"'+referencepoint+'",';
					jsonTemp+='"weight":'+'"'+weight+'",';
					jsonTemp+='"type":'+'"'+type+'"';
					jsonTemp+='}';
					jsonArray.push(jsonTemp);
				}
			});
			
			if(jsonArray.length>0){
				var jsonStr=jsonArray.join(",");
				jsonStr="["+jsonStr+"]";
				console.log(jsonStr);
				$("#detailJson").val(jsonStr);
			}
			return flag;
		}
		
		//验证权重
		function validCheckData(){
			//基本
			var flag=true;
			var total=0;
			var detailBasicList=$("#div_basic table").find("tr:gt(0)");
			$.each(detailBasicList,function(i,n){
				var weight=$(this).find("input[name=td_weight]:first").val();
				total+=Number(weight);
			});
			
			//关键
			var detailKeyList=$("#div_key table").find("tr:gt(0)");
			$.each(detailKeyList,function(i,n){
				var weight=$(this).find("input[name=td_weight]:first").val();
				total+=Number(weight);
			});
			if(total!=100){
				alert("权重要为100");
				flag=false;
			}
			return flag;
		}
	</script>
</body>

</html>