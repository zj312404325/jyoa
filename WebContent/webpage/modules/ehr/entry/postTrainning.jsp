<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位培训</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
	</script>
</head>
<body class="hideScroll"><sys:message content="${message}"/>
	<div class="wrapper wrapper-content animated fadeInRightBig">
		<div class="ibox">
			<div class="ibox-title">
				<h5>岗位培训列表 </h5>
			</div>
			<div class="tabs-container">
	        	<ul class="nav nav-tabs">
	                <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">我发出的</a>
	                </li>
	                <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">我收到的</a>
	                </li>
	            </ul>
	        	<div class="tab-content">
	        		<div id="tab-1" class="tab-pane active">
	        			<div class="panel-body">
		        			<div class="row">
								<div class="col-sm-12"></div>
							</div>
						</div>
	        		</div>
	        	</div>
	       	</div>
		</div>
		
	</div>
	
</body>
</html>