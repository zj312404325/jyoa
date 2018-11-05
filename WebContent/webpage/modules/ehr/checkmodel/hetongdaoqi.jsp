<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>离职调查问卷</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			/* laydate({
	            elem: '#startdate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
			laydate({
	            elem: '#enddate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        }); */
	        
	        
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">

	<sys:message content="${message}"/>
		<div class="ibox">
			<div class="ibox-title">
				<h5>离职调查问卷</h5>
			</div>
			    
			<div class="ibox-content">
				<table class="table table-bordered assessment_table" style="text-align:center; width:820px">
					<tr>
						<td width="12.5%"><strong>离职人姓名</strong></td>
						<td width="12.5%"><input type="text" /></td>
						<td width="12.5%"><strong>所在部门</strong></td>
						<td width="12.5%"><input type="text" /></td>
						<td width="12.5%"><strong>职务</strong></td>
						<td width="12.5%"><input type="text" /></td>
						<td width="12.5%"><strong>入职时间</strong></td>
						<td width="12.5%"><input type="text" /></td>
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
									    	<input type="checkbox" class="i-checks" value=""> 工资低
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 工作时间
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 无晋升机会
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 与领导关系不和谐
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 工作量大、压力大
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 不满意公司的政策制度
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 工作环境
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 无发展空间
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 领导分工不公正
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 与同事关系不融洽
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 不满意公司的福利
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 其他
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
									    	<input type="checkbox" class="i-checks" value=""> 找到更好的企业
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 家庭原因
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 个人身体健康原因
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 自己创业
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 转换行业
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 交通不便
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
									    	<input type="checkbox" class="i-checks" value=""> 领导管理能力
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 工资及福利
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 其他
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 部门之间的沟通
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 团队合作
									  	</label>
									</div>
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 员工发展机会
									  	</label>
									</div>
									<div class="checkbox">
										<label>
									    	<input type="checkbox" class="i-checks" value=""> 工作环境及设施
									  	</label>
									</div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="8" style="text-align:left;">
							<p>您在公司感觉最满意的地方是什么？最不满意的是什么？</p>
							<textarea></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="8" style="text-align:left;">
							<p>对您的部门经理有何评价？</p>
							<textarea></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="8" style="text-align:left;">
							<p>如果公司哪一方面改善，您会愿意继续留在公司？</p>
							<textarea></textarea>
						</td>
					</tr>
				</table>
				
				<div class="row" style="padding-top:20px; padding-left:390px;">
					<a href="javascript:" class="btn btn-primary" id="sub">提&nbsp;交</a>
				</div>
			</div>
		</div>
		
	</div>
</body>
</html>