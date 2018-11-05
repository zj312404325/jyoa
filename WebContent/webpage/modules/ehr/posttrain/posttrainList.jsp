<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位培训</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			/* $("a[name=tab]").click(function(){
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
			}); */
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>岗位培训 </h5>
		
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	<div class="tabs-container">
       	<ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true" >我发出的</a>
            </li>
            <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">我收到的</a>
            </li>
        </ul>
    	<div class="tab-content">
	       	<div id="tab-1" class="tab-pane active">
	       		<div class="panel-body">
	        		<iframe id="area1" width="100%" height="600" src="${ctx}/ehr/posttrain/mylist" frameborder="0" data-id="1" ></iframe>
	        	</div>
	       	</div>
	       	<div id="tab-2" class="tab-pane">
	       		<div class="panel-body">
	        		<iframe id="area2" width="100%" height="600" src="${ctx}/ehr/posttrain/thelist" frameborder="0" data-id="1" ></iframe>
	        	</div>
	        </div>
	   </div>
	</div>
	<!--查询条件-->
	
	</div>
	</div>
</div>
</body>
</html>