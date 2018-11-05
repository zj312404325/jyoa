<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>OA系统安卓版下载</title>
	<meta name="decorator" content="default"/>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<style>
		body,html,ul,li,dl,dd,h1,h2,h3,h4,h5,h6,input,p{padding: 0;margin: 0;font-size: 12px;}ul,li{list-style: none;}
		a{text-decoration: none;}.cl{clear: both;}.l{float: left;}.r{float: right;}
		body,html{font-family:\5FAE\8F6F\96C5\9ED1, "Lucida Grande", Lucida, Verdana, sans-serif;}
		a,img,div,span,h2{-webkit-touch-callout: none; /* prevent callout to copy image, etc when tap to hold */
			-webkit-text-size-adjust: none; /* prevent webkit from resizing text to fit */
			-webkit-tap-highlight-color: rgba(0,0,0,0); /* make transparent link selection, adjust last value opacity 0 to 1.0 */
			-webkit-user-select: none; /* prevent copy paste, to allow, change 'none' to 'text' */
		}

		a{-webkit-tap-highlight-color:rgba(0,0,0,0.3);}
		.clearfix:before, .clearfix:after {content:""; display:table;}
		.clearfix:after{clear:both; overflow:hidden;}
		.clearfix{zoom:1;}
		body{ background:#fff;}
		body,html{ width:100%; height:100%}
		.container{width:100%; height: 100%; background:url(../../../static/images/OA_index_img.jpg); background-size:100% 100%; margin:0 auto; position: relative;}
		.container a { position: absolute; top: 22.656%; left:30.833%;width:38.333%; height: 6.25%; display: block;}
		.btn_group{ width:100%; height:29%; text-align:center;}
		#weixin-tip{display:none;position:fixed;left:0;top:0;background:rgba(0,0,0,0.8);filter:alpha(opacity=80);width:100%;height:100%;z-index:100;}
		#weixin-tip p{text-align:center;margin-top:10%;padding:0 5%;position:relative;}
		#weixin-tip .close{color:#fff;padding:5px;font:bold 20px/24px simsun;text-shadow:0 1px 0 #ddd;position:absolute;top:0;left:5%;}
	</style>
	<script type="text/javascript">
    var is_weixin = (function(){
        try{
            return navigator.userAgent.toLowerCase().indexOf('micromessenger') !== -1
        }catch(Ex){
            return false;
        }
    })();
    window.onload = function() {
        var tip = document.getElementById('weixin-tip');
        var close = document.getElementById('close');
        if (is_weixin) {
            tip.style.display = 'block';
            close.onclick = function() {
                tip.style.display = 'none';
            }
        }
    }

	</script>
</head>
<body >

<c:if test="${!isOnline}"><%--测试版--%>
<div class="container"><a href="http://172.19.3.222:8090/download/oa.apk"></a></div>
</c:if>

<c:if test="${isOnline}"><%--正式版--%>
	<div class="container"><a href="http://172.19.203.8/download/oa.apk"></a></div>
</c:if>

<div id="weixin-tip"><p><img src="${ctxStatic}/images/live_weixin.png" alt="微信打开" width="100%" /><span id="close" title="关闭" class="close">×</span></p></div>

</body>
</html>