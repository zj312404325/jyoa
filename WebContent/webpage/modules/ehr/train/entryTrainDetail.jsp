<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>入职考试</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
	
	var trainno = "${trainno}";
	var intDiff = parseInt(15*60);//倒计时总秒数量
	if(trainno == '1'){
		intDiff = parseInt(100);
	}
	$(document).ready(function() {
		timer(intDiff);
	});
	
	function timer(intDiff){
        //console.log(intDiff);
		if(intDiff < 0){
			$.post("${ctx}/ehr/train/trainOver",{"trainno":"${trainno}"},function(data){
				/* var jsonData = jQuery.parseJSON(data);
				if(jsonData.status == 'y'){
				}else{
				} */
			});
	       	return false;
		}else{
			var day=0,
	        hour=0,
	        minute=0,
	        second=0;//时间默认值        
		    if(intDiff > 0){
		        day = Math.floor(intDiff / (60 * 60 * 24));
		        hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
		        minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
		        second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
		    }
		    if (minute <= 9) minute = '0' + minute;
		    if (second <= 9) second = '0' + second;
		    //$('#day_show').html(day+"天");
		   // $('#hour_show').html('<s id="h"></s>'+hour+'时');
		   // $('#minute_show').html('<s></s>'+minute);
		   // $('#second_show').html('<s></s>'+second);
			setTimeout(function() { 
				intDiff--;
				timer(intDiff);
			}, 1000);
		}
		
	}
	
	
	</script>
</head>
<body class="hideScroll gray-bg">
<c:if test="${trainno != 1}">
<iframe class="J_iframe" id="iframepage" width="100%" height="850" name="iframe0"  src="${fileurl}" frameborder="0" data-id="${ctx}/home" seamless></iframe>
</c:if>
<c:if test="${trainno == 1}">
<iframe class="J_iframe" id="iframepage" width="100%" height="450" name="iframe0"  src="${fileurl}" frameborder="0" data-id="${ctx}/home" seamless></iframe>
</c:if>
</body>
</html>