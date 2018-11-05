<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>入职考试</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
	function openDialogView(title,url,width,height){
		
		
		if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
			width='auto';
			height='auto';
		}else{//如果是PC端，根据用户设置的width和height显示。
		
		}
		top.layer.open({
		    type: 2,  
		    area: [width, height],
		    title: title,
	        maxmin: true, //开启最大化最小化按钮
		    content: url ,
		    btn: ['关闭'],
		    yes: function(index, layero){
		    	top.layer.close(index);
		    	location.reload();
		    },
		    cancel: function(index){ 
		    	location.reload();
		    }
		}); 
		
	}
	</script>
</head>
<body class="hideScroll gray-bg">
<sys:message content="${message}"/>
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>入职培训&nbsp;&nbsp;<small>(每个课程需学习15分钟以上才算学习完成，所有课程学习完成后才能参加考试，请认真学习)</small></h5>
			</div>
			<div class="ibox-content">
				<table class="table table-bordered table-striped table-hover">
					<tr class="warning">
						<th>培训内容</th>
						<th>操作</th>
						<th>状态</th>
					</tr>
					<tr>
						<td>1、视频播放认知</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain1 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain1 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('视频播放认知', '${ctx}/ehr/train/detail?trainno=1&fileurl=http://ieduren.com/viewMedia.htm','700px', '550px')" ><c:if test="${userInfo.entrytrain1 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain1 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain1 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain1 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>2、开启“互联网＋工业生态圈”服务平台之旅——平台介绍</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain2 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain2 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('平台介绍', '${ctx}/ehr/train/detail?trainno=2&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf1.pdf','1000px', '800px')" ><c:if test="${userInfo.entrytrain2 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain2 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain2 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain2 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>3、开启“互联网＋工业生态圈”服务平台之旅——企业介绍</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain3 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain3 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('集团介绍', '${ctx}/ehr/train/detail?trainno=3&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf2.pdf','1000px', '800px')"><c:if test="${userInfo.entrytrain3 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain3 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain3 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain3 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>4、开启“互联网＋工业生态圈”服务平台之旅——文化篇</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain4 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain4 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('文化篇', '${ctx}/ehr/train/detail?trainno=4&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf3.pdf','1000px', '800px')"><c:if test="${userInfo.entrytrain4 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain4 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain4 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain4 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>5、开启“互联网＋工业生态圈”服务平台之旅——励志篇</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain5 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain5 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('励志篇', '${ctx}/ehr/train/detail?trainno=5&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf4.pdf','1000px', '800px')"><c:if test="${userInfo.entrytrain5 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain5 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain5 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain5 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>6、开启“互联网＋工业生态圈”服务平台之旅——礼仪篇</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain6 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain6 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('礼仪篇', '${ctx}/ehr/train/detail?trainno=6&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf5.pdf','1000px', '800px')"><c:if test="${userInfo.entrytrain6 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain6 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain6 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain6 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>7、开启“互联网＋工业生态圈”服务平台之旅——安全管理知识篇</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain7 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain7 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('安全管理知识篇', '${ctx}/ehr/train/detail?trainno=7&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf6.pdf','1000px', '800px')"><c:if test="${userInfo.entrytrain7 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain7 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain7 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain7 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>8、开启“互联网＋工业生态圈”服务平台之旅——产品篇</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain8 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain8 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('产品篇', '${ctx}/ehr/train/detail?trainno=8&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf7.pdf','1000px', '800px')"><c:if test="${userInfo.entrytrain8 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain8 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain8 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain8 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>9、开启“互联网＋工业生态圈”服务平台之旅——制度篇</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain9 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain9 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('制度篇', '${ctx}/ehr/train/detail?trainno=9&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf8.pdf','1000px', '800px')"><c:if test="${userInfo.entrytrain9 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain9 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain9 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain9 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>10、开启“互联网＋工业生态圈”服务平台之旅——办公自动化篇</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain10 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain10 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('办公自动化篇', '${ctx}/ehr/train/detail?trainno=10&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf9.pdf','1000px', '800px')"><c:if test="${userInfo.entrytrain10 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain10 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain10 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain10 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>11、开启“互联网＋工业生态圈”服务平台之旅——管理篇</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain11 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain11 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('管理篇', '${ctx}/ehr/train/detail?trainno=11&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf10.pdf','1000px', '800px')"><c:if test="${userInfo.entrytrain11 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain11 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain11 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain11 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
					<tr>
						<td>12、开启“互联网＋工业生态圈”服务平台之旅——员工成长手册</td>
						<td><a href="javascript:" class="btn <c:if test="${userInfo.entrytrain12 == 1}">btn-success</c:if><c:if test="${userInfo.entrytrain12 == 0}">btn-warning</c:if> frameItem" onclick="openDialogView('员工成长手册', '${ctx}/ehr/train/detail?trainno=12&fileurl=/webpage/modules/ehr/viewPDF/web/viewer.html?file=pdf11.pdf','1000px', '800px')"><c:if test="${userInfo.entrytrain12 == 1}">去复习</c:if><c:if test="${userInfo.entrytrain12 == 0}">去学习</c:if></a></td>
						<td><c:if test="${userInfo.entrytrain12 == 1}"><span class="text-info">已学习</span></c:if><c:if test="${userInfo.entrytrain12 == 0}"><span class="text-danger">未学习</span></c:if></td>
					</tr>
				</table>
				<c:if test="${userInfo.entrytrain != 1}">
				<div class="row text-center">
				    <c:if test="${userInfo.entrytrain1 == 1 && userInfo.entrytrain2 == 1 && userInfo.entrytrain3 == 1 && userInfo.entrytrain4 == 1
				     && userInfo.entrytrain5 == 1 && userInfo.entrytrain6 == 1 && userInfo.entrytrain7 == 1 && userInfo.entrytrain8 == 1
				      && userInfo.entrytrain9 == 1 && userInfo.entrytrain10 == 1 && userInfo.entrytrain11 == 1 && userInfo.entrytrain12 == 1}">
				    <a href="javascript:" class="btn btn-primary frameItem" vl="${ctx}/ehr/entry/train" vl2="入职考试" data-index="entrytrain">去考试</a>
					</c:if>
					<c:if test="${userInfo.entrytrain1 == 0 || userInfo.entrytrain2 == 0 || userInfo.entrytrain3 == 0 || userInfo.entrytrain4 == 0
				     || userInfo.entrytrain5 == 0 || userInfo.entrytrain6 == 0 || userInfo.entrytrain7 == 0 || userInfo.entrytrain8 == 0
				      || userInfo.entrytrain9 == 0 || userInfo.entrytrain10 == 0 || userInfo.entrytrain11 == 0 || userInfo.entrytrain12 == 0}">
				    <button onclick="layer.alert('你尚未完成学习，请继续学习！', {icon: 0, title:'提示'});" type="btn" class="btn btn-primary">去考试</button>
					</c:if>
				</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>