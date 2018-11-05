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
			
					$('#collapseOne').on('show.bs.collapse', function () {
						if($(this).prev(".panel-heading").find("i").hasClass("glyphicon-chevron-down")){
							$(this).prev(".panel-heading").find("i").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
						}
						
					});
					$('#collapseOne').on('hide.bs.collapse', function () {
						if($(this).prev(".panel-heading").find("i").hasClass("glyphicon-chevron-up")){
							$(this).prev(".panel-heading").find("i").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
						}
						
					});
					
					
				$("#fundnatureid").change(function(){
					$("#contractpartyid").val("");
					$("#contractparty").val("");
				});
				$("#selectcontractparty").click(function(){
					if($('#fundnatureid option:selected') .val() == ''){
						layer.alert('请先选择资金性质', {icon: 3});
						return false;
					}else if($('#fundnatureid option:selected') .val() == '1'){
						var url = "${ctx}/filemanagement/customer/selectlist?usertype=0";
						layer.open({
						    type: 2,  
						    area: ['800px', '450px'],
						    title: '选择合同对方',
					        maxmin: true, //开启最大化最小化按钮
						    content: url 
						}); 
						return false;
					}else if($('#fundnatureid option:selected') .val() == '2'){
						var url = "${ctx}/filemanagement/customer/selectlist?usertype=1";
						layer.open({
						    type: 2,  
						    area: ['800px', '450px'],
						    title: '选择合同对方',
					        maxmin: true, //开启最大化最小化按钮
						    content: url 
						}); 
						return false;
					}
					
				});
				$("#selectproject").click(function(){
					var url = "${ctx}/filemanagement/projectManage/selectlist";
					layer.open({
					    type: 2,  
					    area: ['800px', '450px'],
					    title: '选择所属项目',
				        maxmin: true, //开启最大化最小化按钮
					    content: url 
					}); 
					return false;
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
	<style type="text/css">
	.form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control {
	    background-color: #fbfbfb;
	    opacity: 1;
	}
	</style>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="contractManager" action="${ctx}/contractmanager/contractManager/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		
		<div class="panel-group" id="accordion">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="#accordion" 
				   href="#collapseOne">
					<i class="glyphicon glyphicon-chevron-up"></i> 合同基本信息
				</a>
			</h4>
		</div>
		<div id="collapseOne" class="panel-collapse collapse in">
			<div class="panel-body">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">合同名称：</label></td>
					<td colspan="16">
						${contractManager.contractname}
					</td>
					
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">合同编号：</label></td>
					<td class="width-35" colspan="6">
						${contractManager.contractno}
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">签订日期：</label></td>
					<td class="width-35" colspan="6">
						<fmt:formatDate value="${contractManager.signdate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td class="width-10 active" colspan="2"><label class="pull-right">合同类别：</label></td>
					<td colspan="3">
						${contractManager.contractclass}
					</td>
					<td class="active" colspan="2"><label class="pull-right">资金性质：</label></td>
					<td class="width-15" colspan="3">
						${contractManager.fundnature}
					</td>
					<td class="active" colspan="4"><label class="pull-right">生效日期：</label></td>
					<td colspan="6">
						<fmt:formatDate value="${contractManager.effectivedate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td class="active" colspan="2"><label class="pull-right">合同金额：</label></td>
					<td colspan="3">
						${contractManager.contractamount}
					</td>
					<td class="active" colspan="2"><label class="pull-right">币种：</label></td>
					<td colspan="3">
						${contractManager.currency}
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">计划完成日期：</label></td>
					<td class="width-35" colspan="6">
						<fmt:formatDate value="${contractManager.plancompletiondate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">印花税额：</label></td>
					<td class="width-35" colspan="6">
						${contractManager.stamptax}元（人民币）
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">所属机构：</label></td>
					<td class="width-35" colspan="6">
						${contractManager.affiliation}
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">备注1：</label></td>
					<td class="width-35" colspan="6">
						${contractManager.remark1}
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">备注2：</label></td>
					<td class="width-35" colspan="6">
						${contractManager.remark2}
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">合同对方：</label></td>
					<td colspan="16">
						${contractManager.contractparty}
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">所属项目：</label></td>
					<td colspan="16">
						${contractManager.project}
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">负责人：</label></td>
					<td colspan="16">
					    ${contractManager.responsibleperson}
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">合同状态：</label></td>
					<td class="width-35" colspan="6">
						${fns:getDictLabel(contractManager.status, 'contractmanager_contractstatus', '')}
					</td>
					<td class="width-15" colspan="10"></td>
				</tr>
		 	</tbody>
		</table>
		</div>
		</div>
		</div>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-5" aria-expanded="true">合同文本</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-4" aria-expanded="false">合同标的</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">计划资金</a>
                </li>
                <c:if test="${contractManager.status > 0}">
	                <li class=""><a data-toggle="tab" href="#tab-7" aria-expanded="false">实际资金</a>
	                </li>
                </c:if>
                <li class=""><a data-toggle="tab" href="#tab-6" aria-expanded="false">资金条款</a>
                </li>
                <c:if test="${contractManager.status > 0}">
	                <li class=""><a data-toggle="tab" href="#tab-8" aria-expanded="false">发票</a>
	                </li>
                </c:if>
                <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">合同备忘</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-1" aria-expanded="false">合同附件</a>
                </li>
                
                
                
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane ">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>附件名称</th>
						<th>附件类型</th>
						<th>上传日期</th>
						<th>附件说明</th>
						<th>上传人</th>
						<th>下载</th>
					</tr>
				</thead>
				<tbody id="contractFileList">
					<c:forEach items="${contractManager.contractFileList}" var="item" >
					<tr> 
				        <td>${item.filename}</td>
				        <td>${fns:getDictLabel(item.fileclassid, 'contractmanager_fileclass', '')}</td>
				        <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></td>
				        <td>${item.fileremark}</td>
				        <td>${fns:getUserById(item.createBy.id).name}</td>
				        <td><a href="${ctx}/sys/user/fileDownload?fileUrl=${item.fileurl}" >下载</a></td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			</div>
				<div id="tab-2" class="tab-pane">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>说明事项</th>
						<th>内容</th>
						<th>提交人</th>
						<th>提交时间</th>
					</tr>
				</thead>
				<tbody id="contractNoteList">
					<c:forEach items="${contractManager.contractNoteList}" var="item" >
					  <tr> 
				        <td>${item.note}</td>
				        <td>${item.content}</td>
				        <td>${fns:getUserById(item.createBy.id).name}</td>
				        <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			</div>
				<div id="tab-3" class="tab-pane">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>名称</th>
						<th>金额</th>
						<th>已完成</th>
						<th>余额</th>
						<th>完成比例</th>
						<th>结算方式</th>
						<th>计划完成日期</th>
						<th>逾期</th>
					</tr>
				</thead>
				<tbody id="contractPlanfundList">
					<c:forEach items="${contractManager.contractPlanfundList}" var="item" >
					<tr> 
				        <td>${item.planfundname}</td>
				        <td><fmt:formatNumber type="number" value="${item.money}" pattern="0.00" maxFractionDigits="2"/></td>
				        <td><fmt:formatNumber type="number" value="${contractActualfund}" pattern="0.00" maxFractionDigits="2"/></td>
				        <td><fmt:formatNumber type="number" value="${contractActualfundleft}" pattern="0.00" maxFractionDigits="2"/></td>
				        <td>${contractFundPercentage}</td>
				        <td>${fns:getDictLabel(item.settlementid, 'contractmanager_settlement', '')}</td>
				        <td><fmt:formatDate value="${item.plancompletiondate}" pattern="yyyy-MM-dd"/></td>
				        <td>${fns:getDaysBetween(item.plancompletiondate,'')}</td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			</div>
			<div id="tab-6" class="tab-pane">
			<form class="form-horizontal">
				<p>&nbsp;</p>
			 	<label class="col-sm-2 control-label">资金条款：</label>
			 	<div class="col-sm-9">${contractManager.fundclause}</div>
			</form>
			</div>
			
				<div id="tab-4" class="tab-pane">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>名称</th>
						<th>型号</th>
						<th>规格</th>
						<th>数量</th>
						<th>单位</th>
						<th>单价</th>
						<th>小计</th>
						<th>备注</th>
						<th>添加时间</th>
					</tr>
				</thead>
				<tbody id="contractSubjectList">
					<c:forEach items="${contractManager.contractSubjectList}" var="item" >
					<tr> 
				        <td>${item.subjectname}</td>
				        <td>${item.model}</td>
				        <td>${item.specification}</td>
				        <td>${item.quantity}</td>
				        <td>${item.unit}</td>
				        <td>${item.unitprice}</td>
				        <td>${item.subtotal}</td>
				        <td>${item.remark}</td>
				        <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			
			</div>
				<div id="tab-5" class="tab-pane active">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>文本名称</th>
						<th>文本类别</th>
						<th>创建人</th>
						<th>创建日期</th>
						<th>文本说明</th>
						<th>下载</th>
					</tr>
				</thead>
				<tbody id="contractTextList">
				    <c:forEach items="${contractManager.contractTextList}" var="item" >
				    <tr> 
				        <td>${item.textname}</td>
				        <td>${fns:getDictLabel(item.textclassid, 'contractmanager_textclass', '')}</td>
				        <td>${fns:getUserById(item.createBy.id).name}</td>
				        <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></td>
				        <td>${item.textremark}</td>
				        <td><a href="${ctx}/sys/user/fileDownload?fileUrl=${item.texturl}" >下载</a></td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			</div>
			
			<div id="tab-7" class="tab-pane">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>金额</th>
						<th>结算方式</th>
						<th>票据号码</th>
						<th>结算日期</th>
						<th>建立人</th>
						<th>建立日期</th>
						<th>备注1</th>
						<th>备注2</th>
					</tr>
				</thead>
				<tbody id="contractActualFundsList">
					<c:forEach items="${contractManager.contractActualFundsList}" var="item" >
					<tr> 
				        <td><fmt:formatNumber type="number" value="${item.money}" pattern="0.00" maxFractionDigits="2"/></td>
				        <td>${fns:getDictLabel(item.settlementid, 'contractmanager_settlement', '')}</td>
				        <td>${item.billno}</td>
				        <td><fmt:formatDate value="${item.settlementdate}" pattern="yyyy-MM-dd"/></td>
				        <td>${fns:getUserById(item.createBy.id).name}</td>
				        <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></td>
				        <td>${item.remark1}</td>
				        <td>${item.remark2}</td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			
			</div>
			
			<div id="tab-8" class="tab-pane">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>发票类型</th>
						<th>金额</th>
						<th>开票日期</th>
						<th>开票号</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody id="contractInvoiceList">
					<c:forEach items="${contractManager.contractInvoiceList}" var="item" >
					<tr> 
						<td>${fns:getDictLabel(item.invoicetypeid, 'contractmanager_invoicetype', '')}</td>
				        <td>${item.money}</td>
				        <td><fmt:formatDate value="${item.invoicedate}" pattern="yyyy-MM-dd"/></td>
				        <td>${item.invoiceno}</td>
				        <td><c:if test="${contractManager.fundnatureid == '2'}">已收到</c:if>
				        <c:if test="${contractManager.fundnatureid == '1'}">已开出</c:if></td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			
			</div>
			
		</div>
		</div>
	</form:form>
</body>
</html>