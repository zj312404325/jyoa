<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位培训</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("a[name=tab]").click(function(){
				$("a[name=tab]").parent("li").removeClass("active");
				var obj = $(this);
				obj.parent("li").addClass("active");
				var flag = obj.attr("vl");
				if(flag == '2'){
					$("#area2").show();
					$("#area1").hide();
				}else{
					$("#area1").show();
					$("#area2").hide();
				}
			});
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>离职申请管理 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<!-- <a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a> -->
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	<div class="tabs-container">
       	<ul class="nav nav-tabs">

            <li class="active"><a  href="javascript:" name="tab" vl="2" >我的申请</a>
            </li>
			<li class=""><a  href="javascript:" name="tab" vl="1" >收到的申请</a>
			</li>
        </ul>
    	<div class="tab-content">

	        <iframe id="area2"  width="100%" height="600" src="${ctx}/ehr/leaveSheet/toleaveSheet" frameborder="0" data-id="1" ></iframe>
			<iframe id="area1" style="display: none;" width="100%" height="600" src="${ctx}/ehr/entry/leavePermitlist?isadmin=0" frameborder="0" data-id="1" ></iframe>
	   </div>
	</div>
	<!--查询条件-->
	
	</div>
	</div>
</div>
</body>
</html>