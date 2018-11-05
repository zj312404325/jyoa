<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">

<div class="wrapper wrapper-content" style="height:100%">
	<div class="ibox" style="height:100%">
		<%--<div class="ibox-title">
			<h5>列表</h5>
		</div>--%>
		<div class="ibox-content" style="height:100%">
			<div class="tabs-container" id="keyContent" style="height:100%">
	        	<ul class="nav nav-tabs">
	                <li class="<c:if test="${type!=null&&type=='0' }">active</c:if>"><a data-toggle="tab" href="#tab-1" aria-expanded="true">我发出的</a>
	                </li>
	                <li class="<c:if test="${type!=null&&type=='1' }">active</c:if>"><a data-toggle="tab" href="#tab-2" aria-expanded="false">我收到的</a>
	                </li>
	            </ul>
	        	<div class="tab-content" style="height:100%">
	            	<div id="tab-1" class="tab-pane <c:if test="${type!=null&&type=='0' }">active</c:if>" style="height:100%">
	                    <div class="panel-body" style="height:100%">
	                    	<iframe id="contentIframe1" src="${ctx }/leipiflow/leipiFlowApply/myLeipiFlowById?flowid=${flowid}" style="width:100%;height:100%" frameborder="0"></iframe>
	                    </div>
	                </div>
	                <div id="tab-2" class="tab-pane <c:if test="${type!=null&&type=='1' }">active</c:if>" style="height:100%">
	                    <div class="panel-body" style="height:100%">
	                    	<iframe id="contentIframe2" src="${ctx }/leipiflow/leipiFlowApply/myLeipiTaskById?flowid=${flowid}" style="width:100%;height:100%" frameborder="0"></iframe>
	                    </div>
	                </div>
	           	</div>
	            	
	    	</div>
		</div>
	</div>
</div>

<%-- <div id="keyContent">
	<ul>
		<li  <c:if test="${type!=null&&type=='0' }">class="li_active"</c:if> id="li_basic"><span id="span_send">我发出的</span></li>
		<li id="li_key" <c:if test="${type!=null&&type=='1' }">class="li_active"</c:if>><span id="span_recieve">我收到的</span></li>
	</ul>
	<div style="clear:both;"></div>
	<div id="div_basic" style="height:100%">
		<c:if test="${type!=null&&type=='0' }"><iframe id="contentIframe" src="${ctx }/checkmodel/performanceCheck/list" style="width:100%;height:100%"></iframe></c:if>
		<c:if test="${type!=null&&type=='1' }"><iframe id="contentIframe" src="${ctx }/checkmodel/performanceCheck/list?category=recieve" style="width:100%;height:100%"></iframe></c:if>
	</div>
</div> --%>

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
	});
	
	$("#span_send").click(function(){
		$("#contentIframe").attr("src","${ctx }/checkmodel/performanceCheck/list");
	});
	
	$("#span_recieve").click(function(){
		$("#contentIframe").attr("src","${ctx }/checkmodel/performanceCheck/list?category=recieve");
	}); */
})

</script>
</body>
</html>