<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		    $("#search").click(function(){
		        $("#searchForm").submit();
			});
		});

	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<%--<div class="ibox-title">
		<h5>信息列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
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
			</a>
		</div>
	</div>--%>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
		<form:form id="searchForm" modelAttribute="oaquestion" action="${ctx}/oaqa/oaquestion/list" method="post" class="form-inline">
			<div class="form-group">
				<label style="padding-right: 12px;"><h2><i style="top:10px; color:#1ab394;" class="glyphicon glyphicon-question-sign"></i></h2></label>
				<form:input path="var1" htmlEscape="false" maxlength="50" class=" form-control" style="border-color:#1ab394; width: 480px;" />
				<button type="button" id="search" class="btn btn-primary"><i class="glyphicon glyphicon-search"></i> 搜索</button>
				<shiro:hasPermission name="oaqa:oaquestion:add">
					<a href="javascript:" onclick="add()" class="btn btn-white"><i class="glyphicon glyphicon-plus"></i> 我要创建</a>
				</shiro:hasPermission>
			</div>

            <input id="hasanswer" name="hasanswer" type="hidden" value="${oaquestion.hasanswer}"/>
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/>
		</form:form>
        <c:if test="${oaquestion.hasanswer == null || oaquestion.hasanswer == ''}">
		<p style="padding-top: 12px;">以下共搜索出${page.count}条相关<b class="text-success">${oaquestion.var1}</b>的问答</p>
        </c:if>
        <c:if test="${oaquestion.hasanswer == 'yes'}">
			<p style="padding-top: 12px;">以下共搜索出${page.count}条相关<b class="text-success">${oaquestion.var1}</b>的<b class="text-info">已有答案的问题</b></p>
        </c:if>
        <c:if test="${oaquestion.hasanswer == 'no'}">
			<p style="padding-top: 12px;">以下共搜索出${page.count}条相关<b class="text-success">${oaquestion.var1}</b>的<b class="text-info">等待回答的问题</b></p>
        </c:if>
		<hr />

	
	<!-- 工具栏 -->
	<%--<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="oaqa:oaquestion:add">
				<table:addRow url="${ctx}/oaqa/oaquestion/form" title="信息"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			&lt;%&ndash;<shiro:hasPermission name="oaqa:oaquestion:edit">
			    <table:editRow url="${ctx}/oaqa/oaquestion/form" title="信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oaqa:oaquestion:del">
				<table:delRow url="${ctx}/oaqa/oaquestion/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oaqa:oaquestion:import">
				<table:importExcel url="${ctx}/oaqa/oaquestion/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oaqa:oaquestion:export">
	       		<table:exportExcel url="${ctx}/oaqa/oaquestion/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>&ndash;%&gt;
	       &lt;%&ndash;<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>&ndash;%&gt;
		
			</div>
		&lt;%&ndash;<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>&ndash;%&gt;
	</div>
	</div>--%>
	
	<!-- 表格 -->
		<div class="row">
			<div class="col-sm-12">
				<ul class="list-group">
					<c:forEach items="${page.list}" var="oaquestion">
						<li class="list-group-item">
							<a class="rel <shiro:hasPermission name='oaqa:oaquestion:del'>qa_name</shiro:hasPermission>"><shiro:hasPermission name="oaqa:oaquestion:del"><b class="qa_del_btn1" onclick="delThis(this,0,'${oaquestion.id}')">&times;</b></shiro:hasPermission><h3>${oaquestion.question}</h3></a>

							<c:forEach items="${oaquestion.oaanswerList}" var="answer" >
								<p class="rel <shiro:hasPermission name='oaqa:oaquestion:del'>qa_name</shiro:hasPermission>"><shiro:hasPermission name="oaqa:oaquestion:del"><b class="qa_del_btn1" onclick="delThis(this,1,'${answer.id}')">&times;</b></shiro:hasPermission>${fns:getUserById(answer.createBy.id).name}&nbsp;回答：<span id="${answer.id}">${answer.answer}</span> <a class="zan rel" href="javascript:" vl="0" vl2="${answer.id}"><b class="glyphicon glyphicon-thumbs-up"></b><i></i>&nbsp;<span>${answer.praise}</span></a></p>
							</c:forEach>
							<c:if test="${fn:length(oaquestion.oaanswerList) == 0}"><h6>暂无答案</h6></c:if>

							<shiro:hasPermission name="oaqa:oaquestion:edit">
								<%--<hr />--%>
							<div class="row">
								<div class="col-sm-1"><h4><a href="javascript:" vl="${oaquestion.id}" name="toAnswer" onclick="eidtAnswer(this)">我要回答</a></h4></div>
								<div class="col-sm-11 form-inline" style="display: none">
									<c:set var="exitId" value="0"></c:set>
									<c:forEach items="${oaquestion.oaanswerList}" var="answer" >
										<c:if test="${answer.createBy.id == loginUser.id}">
											<input type="text" name="answercontent" value="${answer.answer}" class="form-control" style="width: 300px;" />
											<input type="hidden" name="answerid" value="${answer.id}" />
											<input type="hidden" name="questionid" value="${oaquestion.id}" />
											<c:set var="exitId" value="1"></c:set>
										</c:if>
									</c:forEach>
									<c:if test="${exitId == '0'}">
										<input type="text" class="form-control" name="answercontent" style="width: 300px;" />
										<input type="hidden" name="answerid" value="" />
										<input type="hidden" name="questionid" value="${oaquestion.id}" />
									</c:if>
									<button type="button" class="btn btn-primary" onclick="submitAnswer(this)">提交</button>
									<button type="button" class="btn btn-white" onclick="hide(this)">我再想想</button>
								</div>
							</div>
							</shiro:hasPermission>


						</li>

					</c:forEach>
				</ul>
			</div>
		</div>
	<%--<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  class="">问</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="oaquestion">
			<tr>
				<td>
					${oaquestion.question}<br/>
					<c:forEach items="${oaquestion.oaanswerList}" var="answer" >
						${fns:getUserById(answer.createBy.id).name}:${answer.answer}
					</c:forEach>
				</td>
				<td>
					&lt;%&ndash;<shiro:hasPermission name="oaqa:oaquestion:view">
						<a href="#" onclick="openDialogView('查看信息', '${ctx}/oaqa/oaquestion/form?id=${oaquestion.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>&ndash;%&gt;
					&lt;%&ndash;<shiro:hasPermission name="oaqa:oaquestion:edit">
    					<a href="#" onclick="openDialog('修改信息', '${ctx}/oaqa/oaquestion/form?id=${oaquestion.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="oaqa:oaquestion:del">
						<a href="${ctx}/oaqa/oaquestion/delete?id=${oaquestion.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>&ndash;%&gt;
					<c:if test="${loginUser.id == oaquestion.createBy.id}">
						<a href="#" onclick="openDialog('修改信息', '${ctx}/oaqa/oaquestion/form?id=${oaquestion.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
						<a href="${ctx}/oaqa/oaquestion/delete?id=${oaquestion.id}" onclick="return confirmx('确认要删除吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>--%>
		<shiro:hasPermission name="oaqa:oaquestion:del">
			<input type="hidden" id="isHasDel" name="isHasDel" value="1" />
		</shiro:hasPermission>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>

<script>
    function add(){
        openDialog("创建问答","${ctx}/oaqa/oaquestion/form","60%", "30%","");
    }

    function eidtAnswer(obj){
		$(obj).parent().parent().next("div").show();
        $(obj).parent().parent().next("div").find("input[type=text]").focus();
	}
	function hide(obj) {
		$(obj).parent().hide();
    }
    function submitAnswer(obj) {
		var answer = $(obj).prev().prev().prev("input[name=answercontent]").val();
        var answerid = $(obj).prev().prev("input[name=answerid]").val();
        var questionid = $(obj).prev("input[name=questionid]").val();
		if(answer == ""){
		    layer.alert("请输入答案后提交！",{icon:2});
		}else{
			var len = $(obj).closest("li").find("p").length;

            $.post("${ctx}/oaqa/oaquestion/subAnswer",{'answercontent':answer,'answerid':answerid,'questionid':questionid},function(data){
                var jsonData = jQuery.parseJSON(data);
                if(jsonData.status == 'y'){

                    $(obj).prev().prev("input[name=answerid]").val(jsonData.answerid);
                    $(obj).prev().prev().prev("input[name=answercontent]").val(answer);

                    if(len == 0){
                        $(obj).closest("li").find("h6").remove();
                        if(answerid != ''){
							$("#"+answerid).html(answer);
                        }else{
                            if($("#isHasDel").val() == '1'){
                                $(obj).closest("li").find("a.rel").after('<p class="rel qa_name"><b class="qa_del_btn1" onclick="delThis(this,1,\''+jsonData.answerid+'\')">&times;</b>'+jsonData.name+'&nbsp;回答：<span id='+jsonData.answerid+'>'+answer+'</span> <a class="zan rel" href="javascript:" vl="0" vl2="'+jsonData.answerid+'"><b class="glyphicon glyphicon-thumbs-up"></b><i></i>&nbsp;<span>0</span></a></p>');
							}else{
                                $(obj).closest("li").find("a.rel").after('<p class="rel ">'+jsonData.name+'&nbsp;回答：<span id='+jsonData.answerid+'>'+answer+'</span> <a class="zan rel" href="javascript:" vl="0" vl2="'+jsonData.answerid+'"><b class="glyphicon glyphicon-thumbs-up"></b><i></i>&nbsp;<span>0</span></a></p>');
							}

                        }
                    }else{
                        if(answerid != ''){
                            $("#"+answerid).html(answer);
                        }else{

                            if($("#isHasDel").val() == '1'){
                                $(obj).closest("li").find("p:last").after('<p class="rel qa_name"><b class="qa_del_btn1" onclick="delThis(this,1,\''+jsonData.answerid+'\')">&times;</b>'+jsonData.name+'&nbsp;回答：<span id='+jsonData.answerid+'>'+answer+'</span> <a class="zan rel" href="javascript:" vl="0" vl2="'+jsonData.answerid+'"><b class="glyphicon glyphicon-thumbs-up"></b><i></i>&nbsp;<span>0</span></a></p>')
                            }else{
                                $(obj).closest("li").find("p:last").after('<p class="rel ">'+jsonData.name+'&nbsp;回答：<span id='+jsonData.answerid+'>'+answer+'</span> <a class="zan rel" href="javascript:" vl="0" vl2="'+jsonData.answerid+'"><b class="glyphicon glyphicon-thumbs-up"></b><i></i>&nbsp;<span>0</span></a></p>')
                            }
                        }

                    }


                }else{
                    layer.alert(jsonData.info,{icon:2});
                }
            });



		}
        //$(obj).prev("input[type=text]").val("");
        $(obj).parent().hide();
    }
    function delThis(obj,type,id) {
        $.post("${ctx}/oaqa/oaquestion/logicDelete",{'type':type,'pid':id},function(data){
            var jsonData = jQuery.parseJSON(data);
            if(jsonData.status == 'y'){
                if(type==0){
                    $(obj).closest("li").remove();
                }else{
                    var li = $(obj).closest("li");
                    $(obj).closest("p").remove();
                    if(li.find("p").length == 0){
                        li.find("a.qa_name").after('<h6>暂无答案</h6>');
                    }
                }

                li.find("input[name=answercontent]").val("");
                li.find("input[name=answerid]").val("");

            }else{
                layer.alert(jsonData.info,{icon:2});
            }
        });


    }

	$(function () {


        $('a.zan').live("click",function(){
            var i = $(this).index();
            var vl = $(this).attr("vl");
            var vl2 = $(this).attr("vl2");
            var obj=$(this);
            if(vl == 0){


                $.post("${ctx}/oaqa/oaquestion/doPraise",{'answerid':vl2},function(data){
                    var jsonData = jQuery.parseJSON(data);
                    if(jsonData.status == 'y'){
                        obj.attr("vl","1");

                        obj.append('<div id="zhan'+ i +'"><b>+1</b></div>');
                        $('#zhan'+i).css({'position':'absolute','z-index':'1', 'color':'#C30','left':'10px','top':'-15px'}).animate({top:'-20px',opacity:0},'slow',function(){
                            $(this).fadeIn('fast').remove();
                            var Num = parseInt(obj.find('span').text());
                            Num++;
                            obj.find('span').text(Num);
                        });
                    }else{
                        alert(jsonData.info);
                    }
                });
            }else{
                return false;
            }
        });

        /*$("a[name=toAnswer]").click(function(){
            var questionid = $(this).attr("vl");
            alert(questionid);
		});*/
    })

</script>
</body>
</html>