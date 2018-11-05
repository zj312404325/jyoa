<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>入职考试</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		var a = new Array("A", "C", "A", "A", "B", "C", "B", "C", "B", "B");
		$("#sub").click(function(){
			var flag = "";
			$("p[name=subject]").each(function(){
				var s = $(this).attr("vl");
			    var name = "subject"+s;
			    if($("input[name="+name+"]:checked").val() == null){
			    	flag = s;
					return false;
				}
			});
			if(flag != ""){
				layer.alert('第'+flag+'题没有选择!', {icon: 0, title:'警告'});
				return false;
			}else{
				var ispass = true;
				$("p[name=subject]").each(function(){
					var s = $(this).attr("vl");
				    var name = "subject"+s;
				    if($("input[name="+name+"]:checked").val() != a[s-1]){
				    	ispass = false;
						return false;
					}
				});
				if(!ispass){
					/* layer.alert('对不起，您未能全部答对，请重新学习并考试!', {icon: 0, title:'警告'},function(){
						alert(1);
					}); */
					layer.confirm('对不起，您未能全部答对，请重新学习并考试!', {
						  btn: ['去学习'] //按钮
						}, function(){
							var dataUrl = '${ctx}/ehr/entry/gotrain';
							addFrameItem(dataUrl,'entrytrain','入职培训');
							var closeTabUrl = '${ctx}/ehr/entry/train';
							closeTheTab(closeTabUrl);
							/* var flag = true;
							var dataUrl = '/a/ehr/entry/gotrain';
							$('.J_menuTab',parent.document).each(function () {
						        if ($(this).data('id') == dataUrl) {
						            if (!$(this).hasClass('active')) {
						                $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
						                scrollToTab(this);
						                // 显示tab对应的内容区
						                $('.J_mainContent .J_iframe',parent.document).each(function () {
						                	
						                    if ($(this).data('id') == dataUrl) {
						                        $(this).show().siblings('.J_iframe').hide();
						                        $(this).attr("src",dataUrl);
						                        return false;
						                    }
						                });
						            }
						            flag = false;
						            return false;
						        }
						    });
							// 选项卡菜单不存在
						    if (flag) {
						        var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '">入职培训<i class="fa fa-times-circle"></i></a>';
						        $('.J_menuTab',parent.document).removeClass('active');

						        // 添加选项卡对应的iframe
						        var str1 = '<iframe class="J_iframe" name="iframeentrytrain" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
						        $('.J_mainContent',parent.document).find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);

						        //显示loading提示
						       // var loading = layer.load();

						        $('.J_mainContent iframe:visible').load(function () {
						            //iframe加载完成后隐藏loading提示
						            layer.close(loading);
						        });
						        // 添加选项卡
						        $('.J_menuTabs .page-tabs-content',parent.document).append(str);
						        scrollToTab($('.J_menuTab.active'));
						    } */
						});
					return false;
				}else{
					loading('正在提交，请稍等...');
					$("#permitForm").submit();
				}
			}
		});
	});
	</script>
</head>
<body class="hideScroll gray-bg">
<sys:message content="${message}"/>

<form:form id="permitForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/passEntryTrain" method="post" class="form-inline">
<form:hidden path="id"/>
<input type="hidden" name="entrytrain" id="entrytrain" value="1" />
</form:form>
<c:if test="${userInfo.entrytrain == 1}">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>入职考试</h5>
			</div>
			<div class="ibox-content">
				<div class="row">
					<div class="col-md-12">&nbsp;</div>
				</div>
				<div class="row" style="padding:100px 0;">
					<h1 class="text-center text-info"><i class="glyphicon glyphicon-ok"></i> 恭喜您通过入职考试！</h1>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${userInfo.entrytrain != 1}">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>入职考试</h5>
			</div>
			<div class="ibox-content">
				<div class="row container" id="examSubject">
					<div class="col-md-6">
						<p name="subject" vl="1">1、下列哪一个不是平台网站：</p>
						<div class="radio">
							<label>
								<input type="radio" name="subject1" value="A" class="i-checks">
						   		A:www.jinying.com
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject1" value="B" class="i-checks">
						   		B:www.jinying365.com
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject1" value="C" class="i-checks">
						   		C:56.jinying365.com
						  	</label>
						</div>
						<p name="subject" vl="2">2、平台理念：</p>
						<div class="radio">
							<label>
								<input type="radio" name="subject2" value="A" class="i-checks">
						   		A:Better idea
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject2" value="B" class="i-checks">
						   		B:Better future
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject2" value="C" class="i-checks">
						   		C:Better idea,Better future
						  	</label>
						</div>
						<p name="subject" vl="3">3、平台核心价值观：</p>
						<div class="radio">
							<label>
								<input type="radio" name="subject3" value="A" class="i-checks">
						   		A:简单、快乐、真诚、高效、责任
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject3" value="B" class="i-checks">
						   		B:简单、快乐、真诚
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject3" value="C" class="i-checks">
						   		C:真诚、高效、责任
						  	</label>
						</div>
						<p name="subject" vl="4">4、下列哪项产品不属于平台自营产品：</p>
						<div class="radio">
							<label>
								<input type="radio" name="subject4" value="A" class="i-checks">
						   		A:电线电缆
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject4" value="B" class="i-checks">
						   		B:酸洗
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject4" value="C" class="i-checks">
						   		C:冷轧
						  	</label>
						</div>
						<p name="subject" vl="5">5、平台制度不包括下面哪一类：</p>
						<div class="radio">
							<label>
								<input type="radio" name="subject5" value="A" class="i-checks">
						   		A:行政人资类
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject5" value="B" class="i-checks">
						   		B:礼仪管理类
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject5" value="C" class="i-checks">
						   		C:运营管理类
						  	</label>
						</div>
					</div>
					<div class="col-md-6">
						<p name="subject" vl="6">6、“互联网+工业生态圈”服务平台正式运营年份：</p>
						<div class="radio">
							<label>
								<input type="radio" name="subject6" value="A" class="i-checks">
						   		A:2010
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject6" value="B" class="i-checks">
						   		B:2011
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject6" value="C" class="i-checks">
						   		C:2012
						  	</label>
						</div>
						<p name="subject" vl="7">7、互联网+工业生态圈服务平台办公系统不包括下面哪一个功能：</p>
						<div class="radio">
							<label>
								<input type="radio" name="subject7" value="A" class="i-checks">
						   		A:OA
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject7" value="B" class="i-checks">
						   		B:ERP
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject7" value="C" class="i-checks">
						   		C:EHR
						  	</label>
						</div>
						<p name="subject" vl="8">8、下列哪项不属于平台产品：</p>
						<div class="radio">
							<label>
								<input type="radio" name="subject8" value="A" class="i-checks">
						   		A:挖土机
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject8" value="B" class="i-checks">
						   		B:钢卷
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject8" value="C" class="i-checks">
						   		C:农业作物
						  	</label>
						</div>
						<p name="subject" vl="9">9、平台吉祥物是什么动物：</p>
						<div class="radio">
							<label>
								<input type="radio" name="subject9" value="A" class="i-checks">
						   		A:熊猫
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject9" value="B" class="i-checks">
						   		B:猴子
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject9" value="C" class="i-checks">
						   		C:松鼠
						  	</label>
						</div>
						<p name="subject" vl="10">10、金赢网网址是哪一个？：</p>
						<div class="radio">
							<label>
								<input type="radio" name="subject10" value="A" class="i-checks">
						   		A:www.jinyingwang.com
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject10" value="B" class="i-checks">
						   		B:www.jinying365.com
						  	</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="subject10" value="C" class="i-checks">
						   		C:www.jinying360.com
						  	</label>
						</div>
					</div>
					
				</div>
				<div class="row">
					<div class="col-md-1 text-right">
						<button type="button" id="sub" class="btn btn-primary">提交</button>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</c:if>	
<a href="javascript:" id="gob" class="frameItem" vl="${ctx}/ehr/entry/gotrain" vl2="入职培训" data-index="20"><button type="btn" class="btn btn-primary" disabled="disabled"></button></a>
</body>
</html>