<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>提交流程申请管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrapValidator/bootstrapValidator.min.css">
	<style>
		.has-feedback label~.form-control-feedback{ top:0;}
		.checkbox input[type=checkbox], .checkbox-inline input[type=checkbox], .radio input[type=radio], .radio-inline input[type=radio]{ margin-top: 0;}
	</style>
    <script type="text/javascript" src="${ctxStatic}/bootstrapValidator/bootstrapValidator.min.js"></script>
	<script type="text/javascript">
		/* function checkLayDate(_this){
			var name = $(_this).attr('name');
			$('#commentForm').data('bootstrapValidator').updateStatus(name, 'NOT_VALIDATED').validateField(name);
		} */
        var flag = 1;
		var excuteReplaceFlag=false;
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {


			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			//验证
			$('#commentForm').bootstrapValidator({
				feedbackIcons: {
					valid: 'glyphicon glyphicon-ok',
					invalid: 'glyphicon glyphicon-remove',
					validating: 'glyphicon glyphicon-refresh'
				},
				fields: {
					//validfield
					${validfield}
				}
			});
			
			$('#commentForm').find('.layer-date').each(function(){
				if($(this).closest(".form-group").attr("vl") == "9"){
					$(this).datetimepicker({ 
						format: 'YYYY-MM-DD',  
				        locale: moment.locale('zh-cn'),
				        showClose: true
						}).on('dp.hide',function(e) { 
						$('#commentForm').data('bootstrapValidator').updateStatus($(this).attr("name"), 'NOT_VALIDATED',null).validateField($(this).attr("name")); 
						});
				}else{
					$(this).datetimepicker({ 
						format: 'YYYY-MM-DD HH:mm:ss',  
				        locale: moment.locale('zh-cn'),
				        showClose: true
						}).on('dp.hide',function(e) { 
						$('#commentForm').data('bootstrapValidator').updateStatus($(this).attr("name"), 'NOT_VALIDATED',null).validateField($(this).attr("name")); 
						});
				}
				
			});

            /*薪资调整*/
            $("#saveTempleteContentSalary").click(function(){
                var msg = "";
                var detailForms = new Array();
                detailForms =  $("#salaryTbl").find("tr[name='createTr']").toArray();
                if(detailForms.length > 0){
                    var detailJsonArray = [];
                    $.each(detailForms,function(i,item){
                        var detailJSON = '{';
                        var j = i + 1;
                        detailJSON = detailJSON + '"sortno":"'+ j +'",';
                        if($(this).find("input[name=var1]").val() == null || $(this).find("input[name=var1]").val() == ''){
                            detailJSON = detailJSON + '"var1":"",';
                            msg = "姓名不能为空！";
                            $(this).find("input[name=var1]").focus();
                            return false;
                        }else{
                            detailJSON = detailJSON + '"var1":"'+ $(this).find("input[name=var1]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var2]").val() == null || $(this).find("input[name=var2]").val() == ''){
                            detailJSON = detailJSON + '"var2":"",';
                        }else{
                            detailJSON = detailJSON + '"var2":"'+ $(this).find("input[name=var2]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var3]").val() == null || $(this).find("input[name=var3]").val() == ''){
                            detailJSON = detailJSON + '"var3":"",';
                        }else{
                            detailJSON = detailJSON + '"var3":"'+ $(this).find("input[name=var3]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var4]").val() == null || $(this).find("input[name=var4]").val() == ''){
                            detailJSON = detailJSON + '"var4":"",';
                        }else{
                            detailJSON = detailJSON + '"var4":"'+ $(this).find("input[name=var4]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var5]").val() == null || $(this).find("input[name=var5]").val() == ''){
                            detailJSON = detailJSON + '"var5":"",';
                        }else{
                            detailJSON = detailJSON + '"var5":"'+ $(this).find("input[name=var5]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var6]").val() == null || $(this).find("input[name=var6]").val() == ''){
                            detailJSON = detailJSON + '"var6":"",';
                        }else{
                            detailJSON = detailJSON + '"var6":"'+ $(this).find("input[name=var6]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var7]").val() == null || $(this).find("input[name=var7]").val() == ''){
                            detailJSON = detailJSON + '"var7":"",';
                        }else{
                            detailJSON = detailJSON + '"var7":"'+ $(this).find("input[name=var7]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var8]").val() == null || $(this).find("input[name=var8]").val() == ''){
                            detailJSON = detailJSON + '"var8":"",';
                        }else{
                            detailJSON = detailJSON + '"var8":"'+ $(this).find("input[name=var8]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var9]").val() == null || $(this).find("input[name=var9]").val() == ''){
                            detailJSON = detailJSON + '"var9":"",';
                        }else{
                            detailJSON = detailJSON + '"var9":"'+ $(this).find("input[name=var9]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var10]").val() == null || $(this).find("input[name=var10]").val() == ''){
                            detailJSON = detailJSON + '"var10":""}';
                        }else{
                            detailJSON = detailJSON + '"var10":"'+ $(this).find("input[name=var10]").val().replace(/\"/g,"“")+'"}';
                        }
                        detailJsonArray.push(detailJSON);
                    });
                    if(msg != ""){
                        layer.alert(msg, {icon: 0, title:'警告'});
                        return false;
                    }
                    //alert('['+detailJsonArray+']');

                    //判断是否执行时替换
                    checkExcuteReplace();
                    var userIds=$("#userIds").val();
                    if((userIds!=null&&userIds!='')&&!excuteReplaceFlag  || $("#isend").val() == '1') {//通过验证（非执行时替换或已选择替换执行的用户）
                        $.post("${ctx}/leipiflow/leipiFlowApply/saveSpecial",
                            {
                                "flowtemplateid":$("#flowtemplateid").val(),
                                "flowid":$("#flowid").val(),
                                "title":$("#title").val(),
                                "templatehtmlArea":$("#templatehtmlArea").html(),
                                "userIds":userIds,
                                "detailJsonArray":'['+detailJsonArray+']'
                            },function(data){
                                var jsonData = jQuery.parseJSON(data);
                                if(jsonData.status == 'y'){
                                    layer.msg(jsonData.info, {icon: 1});
                                    window.location.href="${ctx }/leipiflow/templateDetail/list?flowid="+$("#flowid").val();
                                }else{
                                    layer.msg(jsonData.info, {icon: 2});
                                }
                            });
                    }else{
                        //执行时替换用户选择
                        layer.alert("下一步骤【"+$("#nextprocessname").val()+"】需要指定执行人，请选取下一步需要执行的人员",{icon: 1},function(index){
                            $("#userIds").val("");
                            layer.close(index);
                            //跳出选择用户页面
                            var ids = [], names = [], nodes = [];
                            layer.open({
                                type: 2,
                                //area: ['300px', '420px'],
                                area: ['550px', '60%'],
                                title:"新增人员【"+$("#nextprocessname").val()+"】",
                                ajaxData:{selectIds: $("#addoaNotifyRecordId").val()},
                                //content: "${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3")+"&module=&checked=true&extId=&isAll=&selectIds="+$("#addoaNotifyRecordId").val() ,
                                content: "${ctx}/tag/treeselectHold?url="+encodeURIComponent("/sys/office/treeData?type=3"),
                                btn: ['确定', '关闭']
                                ,yes: function(index, layero){ //或者使用btn1
                                    var idsStr = layero.find("iframe")[0].contentWindow.$("#ids").val();
                                    if(idsStr==null||idsStr==''){
                                        layer.alert("请选择！");
                                    }else{
                                        $("#userIds").val(idsStr);
                                        layer.closeAll();
                                        //提交内容
                                        var userIds=$("#userIds").val();

                                        $.post("${ctx}/leipiflow/leipiFlowApply/saveSpecial",
                                            {
                                                "flowtemplateid":$("#flowtemplateid").val(),
                                                "flowid":$("#flowid").val(),
                                                "title":$("#title").val(),
                                                "templatehtmlArea":$("#templatehtmlArea").html(),
                                                "userIds":userIds,
                                                "detailJsonArray":'['+detailJsonArray+']'
                                            },function(data){
                                                var jsonData = jQuery.parseJSON(data);
                                                if(jsonData.status == 'y'){
                                                    layer.msg(jsonData.info, {icon: 1});
													window.location.href="${ctx }/leipiflow/templateDetail/list?flowid="+$("#flowid").val();
                                                }else{
                                                    layer.msg(jsonData.info, {icon: 2});
                                                }
                                            });
                                    }
                                },
                                cancel: function(index){ //或者使用btn2
                                    //按钮【按钮二】的回调
                                }
                            });
                        });
                    }
                }else{
                    layer.msg("请先添加申请明细", {icon: 2});
				}
            });

            /*奖惩调整*/
            $("#saveTempleteContentReward").click(function(){
                var msg = "";
                var detailForms = new Array();
                detailForms =  $("#salaryTbl").find("tr[name='createTr']").toArray();
                if(detailForms.length > 0){
                    var detailJsonArray = [];
                    $.each(detailForms,function(i,item){
                        var detailJSON = '{';
                        var j = i + 1;
                        detailJSON = detailJSON + '"sortno":"'+ j +'",';
                        if($(this).find("input[name=var1]").val() == null || $(this).find("input[name=var1]").val() == ''){
                            detailJSON = detailJSON + '"var1":"",';
                            msg = "姓名不能为空！";
                            $(this).find("input[name=var1]").focus();
                            return false;
                        }else{
                            detailJSON = detailJSON + '"var1":"'+ $(this).find("input[name=var1]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("select[name=var2]").val() == null || $(this).find("select[name=var2]").val() == ''){
                            detailJSON = detailJSON + '"var2":"",';
                        }else{
                            if($(this).find("select[name=var2]").val() == '0'){
                                detailJSON = detailJSON + '"var2":"惩",';
							}else{
                                detailJSON = detailJSON + '"var2":"奖",';
							}

                        }
                        if($(this).find("input[name=var3]").val() == null || $(this).find("input[name=var3]").val() == ''){
                            detailJSON = detailJSON + '"var3":"",';
                        }else{
                            detailJSON = detailJSON + '"var3":"'+ $(this).find("input[name=var3]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var4]").val() == null || $(this).find("input[name=var4]").val() == ''){
                            detailJSON = detailJSON + '"var4":"",';
                        }else{
                            detailJSON = detailJSON + '"var4":"'+ $(this).find("input[name=var4]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var5]").val() == null || $(this).find("input[name=var5]").val() == ''){
                            detailJSON = detailJSON + '"var5":"",';
                        }else{
                            detailJSON = detailJSON + '"var5":"'+ $(this).find("input[name=var5]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var6]").val() == null || $(this).find("input[name=var6]").val() == ''){
                            detailJSON = detailJSON + '"var6":"",';
                        }else{
                            detailJSON = detailJSON + '"var6":"'+ $(this).find("input[name=var6]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var7]").val() == null || $(this).find("input[name=var7]").val() == ''){
                            detailJSON = detailJSON + '"var7":"",';
                        }else{
                            detailJSON = detailJSON + '"var7":"'+ $(this).find("input[name=var7]").val().replace(/\"/g,"“")+'",';
                        }
                        if($(this).find("input[name=var10]").val() == null || $(this).find("input[name=var10]").val() == ''){
                            detailJSON = detailJSON + '"var10":""}';
                        }else{
                            detailJSON = detailJSON + '"var10":"'+ $(this).find("input[name=var10]").val().replace(/\"/g,"“")+'"}';
                        }
                        detailJsonArray.push(detailJSON);
                    });
                    if(msg != ""){
                        layer.alert(msg, {icon: 0, title:'警告'});
                        return false;
                    }
                    //alert('['+detailJsonArray+']');

                    //判断是否执行时替换
                    checkExcuteReplace();
                    var userIds=$("#userIds").val();
                    if((userIds!=null&&userIds!='')&&!excuteReplaceFlag  || $("#isend").val() == '1') {//通过验证（非执行时替换或已选择替换执行的用户）
                        $.post("${ctx}/leipiflow/leipiFlowApply/saveSpecial",
                            {
                                "flowtemplateid":$("#flowtemplateid").val(),
                                "flowid":$("#flowid").val(),
                                "title":$("#title").val(),
                                "templatehtmlArea":$("#templatehtmlArea").html(),
                                "userIds":userIds,
                                "detailJsonArray":'['+detailJsonArray+']'
                            },function(data){
                                var jsonData = jQuery.parseJSON(data);
                                if(jsonData.status == 'y'){
                                    layer.msg(jsonData.info, {icon: 1});
                                    window.location.href="${ctx }/leipiflow/templateDetail/list?flowid="+$("#flowid").val();
                                }else{
                                    layer.msg(jsonData.info, {icon: 2});
                                }
                            });
                    }else{
                        //执行时替换用户选择
                        layer.alert("下一步骤【"+$("#nextprocessname").val()+"】需要指定执行人，请选取下一步需要执行的人员",{icon: 1},function(index){
                            $("#userIds").val("");
                            layer.close(index);
                            //跳出选择用户页面
                            var ids = [], names = [], nodes = [];
                            layer.open({
                                type: 2,
                                //area: ['300px', '420px'],
                                area: ['550px', '60%'],
                                title:"新增人员【"+$("#nextprocessname").val()+"】",
                                ajaxData:{selectIds: $("#addoaNotifyRecordId").val()},
                                //content: "${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3")+"&module=&checked=true&extId=&isAll=&selectIds="+$("#addoaNotifyRecordId").val() ,
                                content: "${ctx}/tag/treeselectHold?url="+encodeURIComponent("/sys/office/treeData?type=3"),
                                btn: ['确定', '关闭']
                                ,yes: function(index, layero){ //或者使用btn1
                                    var idsStr = layero.find("iframe")[0].contentWindow.$("#ids").val();
                                    if(idsStr==null||idsStr==''){
                                        layer.alert("请选择！");
                                    }else{
                                        $("#userIds").val(idsStr);
                                        layer.closeAll();
                                        //提交内容
                                        var userIds=$("#userIds").val();

                                        $.post("${ctx}/leipiflow/leipiFlowApply/saveSpecial",
                                            {
                                                "flowtemplateid":$("#flowtemplateid").val(),
                                                "flowid":$("#flowid").val(),
                                                "title":$("#title").val(),
                                                "templatehtmlArea":$("#templatehtmlArea").html(),
                                                "userIds":userIds,
                                                "detailJsonArray":'['+detailJsonArray+']'
                                            },function(data){
                                                var jsonData = jQuery.parseJSON(data);
                                                if(jsonData.status == 'y'){
                                                    layer.msg(jsonData.info, {icon: 1});
                                                    window.location.href="${ctx }/leipiflow/templateDetail/list?flowid="+$("#flowid").val();
                                                }else{
                                                    layer.msg(jsonData.info, {icon: 2});
                                                }
                                            });
                                    }
                                },
                                cancel: function(index){ //或者使用btn2
                                    //按钮【按钮二】的回调
                                }
                            });
                        });
                    }
                }else{
                    layer.msg("请先添加申请明细", {icon: 2});
                }
            });

            /*通用流程申请*/
			$("#saveTempleteContent").click(function(){
				var bootstrapValidator = $("#commentForm").data('bootstrapValidator');
				bootstrapValidator.validate();
				if(bootstrapValidator.isValid()){
					//判断是否执行时替换
					checkExcuteReplace();
                    var userIds=$("#userIds").val();
					if((userIds!=null&&userIds!='')&&!excuteReplaceFlag  || $("#isend").val() == '1'){//通过验证（非执行时替换或已选择替换执行的用户）
						//验证通过
						var detailJsonArray = [];
						var controls='${controls}';
						var jsonData = jQuery.parseJSON(controls);
						//var validfield="";

                        /*固定标题列*/
                        var detailJSON = '{';
                        detailJSON = detailJSON + '"controlid":"title",';
                        detailJSON = detailJSON + '"columnname":"标题",';
                        detailJSON = detailJSON + '"columnvalue":"'+$("#title").val()+'",';
                        detailJSON = detailJSON + '"columntype":"0",';
                        detailJSON = detailJSON + '"columnlocate":"1",';
                        detailJSON = detailJSON + '"columnsort":"0"}';
                        detailJsonArray.push(detailJSON);

                        $.each(jsonData.lst, function(i, n) {
							var detailJSON = '{';
							detailJSON = detailJSON + '"controlid":"'+ n.id+'",';
							detailJSON = detailJSON + '"columnname":"'+ n.columnname+'",';
							if(n.columntype=='4'){//上传空件
								detailJSON = detailJSON + '"columnvalue":"'+$("#"+n.columnid).val()+'",';
							}else if(n.columntype=='6'){//单选
								//console.log("================================="+n.columnid);
								detailJSON = detailJSON + '"columnvalue":"'+$('input:radio[name='+n.columnid+']:checked').val()+'",';
							}else if(n.columntype=='7'){//多选
								var chk_value =[]; 
								//console.log("================================="+n.columnid);
								$('input[name='+n.columnid+']:checked').each(function(){ 
									chk_value.push($(this).val()); 
								}); 
								detailJSON = detailJSON + '"columnvalue":"'+chk_value.join(',')+'",';
							}else{
								detailJSON = detailJSON + '"columnvalue":"'+$("#"+n.columnid).val().replace(/\"/g,"“")+'",';
							}
							detailJSON = detailJSON + '"columntype":"'+n.columntype+'",';
							detailJSON = detailJSON + '"columnlocate":"'+n.columnlocate+'",';
							detailJSON = detailJSON + '"columnsort":"'+n.columnsort+'"}';
							//validfield+=n.columnid+':'+'{validators: {notEmpty: {message: "请填写'+n.columnname+'名称！"}}},';
							//console.log(detailJSON);
							detailJsonArray.push(detailJSON);
						});

						$.post("${ctx}/leipiflow/leipiFlowApply/save",
								{
							     "flowtemplateid":$("#flowtemplateid").val(),
							     "flowid":$("#flowid").val(),
								 "title":$("#title").val(),
							     "templatehtmlArea":$("#templatehtmlArea").html(),
							     "userIds":userIds,
							     "detailJsonArray":'['+detailJsonArray+']'
							    },function(data){
							var jsonData = jQuery.parseJSON(data);
							if(jsonData.status == 'y'){
								layer.msg(jsonData.info, {icon: 1});
								window.location.href="${ctx}/leipiflow/leipiFlowApply/myLeipiFlow/";
								/* $(".nav-tabs").find("li").removeClass("disabled");
								$("#flowtemplateid").val(jsonData.flowtemplateid);
								
								$('#processTable').attr("data-ajax","${ctx}/flow/flowtemplate/getTemplatecontrolList?id="+jsonData.flowtemplateid);
								$('#processTable').bootstrapTable(); */
							}else{
								layer.msg(jsonData.info, {icon: 2});
								//layer.alert('内容', {icon: 0});		
							}
						});
					}else{
						//执行时替换用户选择
						layer.alert("下一步骤【"+$("#nextprocessname").val()+"】需要指定执行人，请选取下一步需要执行的人员",{icon: 1},function(index){
							$("#userIds").val("");
							layer.close(index);
							//跳出选择用户页面
							var ids = [], names = [], nodes = [];
							layer.open({
							    type: 2, 
							    //area: ['300px', '420px'],
                                area: ['550px', '60%'],
							    title:"新增人员【"+$("#nextprocessname").val()+"】",
							    ajaxData:{selectIds: $("#addoaNotifyRecordId").val()},
							    //content: "${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3")+"&module=&checked=true&extId=&isAll=&selectIds="+$("#addoaNotifyRecordId").val() ,
                                content: "${ctx}/tag/treeselectHold?url="+encodeURIComponent("/sys/office/treeData?type=3"),
								btn: ['确定', '关闭']
					    	       ,yes: function(index, layero){ //或者使用btn1
											/*var tree = layero.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
											nodes = tree.getCheckedNodes(true);
											for(var i=0; i<nodes.length; i++) {
												if (nodes[i].isParent){
													continue; // 如果为复选框选择，则过滤掉父节点
												}
												if (nodes[i].level == 0){
													//top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
													top.layer.msg("不能选择根节点（"+nodes[i].name+"）请重新选择。", {icon: 0});
													return false;
												}
												if (nodes[i].isParent){
													//top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
													//layer.msg('有表情地提示');
													top.layer.msg("不能选择父节点（"+nodes[i].name+"）请重新选择。", {icon: 0});
													return false;
												}
												ids.push(nodes[i].id);
												names.push(nodes[i].name);
												//break;
											}
											var idsStr = ids.join(",").replace(/u_/ig,"");*/
                                            var idsStr = layero.find("iframe")[0].contentWindow.$("#ids").val();
											if(idsStr==null||idsStr==''){
												layer.alert("请选择！");
											}else{
												$("#userIds").val(idsStr);
												layer.closeAll();
												//提交内容


                                                var userIds=$("#userIds").val();
                                                var detailJsonArray = [];
                                                var controls='${controls}';
                                                var jsonData = jQuery.parseJSON(controls);
                                                //var validfield="";

                                                /*固定标题列*/
                                                var detailJSON = '{';
                                                detailJSON = detailJSON + '"controlid":"title",';
                                                detailJSON = detailJSON + '"columnname":"标题",';
                                                //detailJSON = detailJSON + '"columnvalue":"'+$("#title").val().replace(/\"/g,"“")+'",';
                                                detailJSON = detailJSON + '"columnvalue":"'+$("#title").val()+'",';
                                                detailJSON = detailJSON + '"columntype":"0",';
                                                detailJSON = detailJSON + '"columnlocate":"1",';
                                                detailJSON = detailJSON + '"columnsort":"0"}';
                                                detailJsonArray.push(detailJSON);
                                                $.each(jsonData.lst, function(i, n) {
                                                    var detailJSON = '{';
                                                    detailJSON = detailJSON + '"controlid":"'+ n.id+'",';
                                                    detailJSON = detailJSON + '"columnname":"'+ n.columnname+'",';
                                                    if(n.columntype=='4'){//上传空件
                                                        detailJSON = detailJSON + '"columnvalue":"'+$("#"+n.columnid).val()+'",';
                                                    }else if(n.columntype=='6'){//单选
                                                        //console.log("================================="+n.columnid);
                                                        detailJSON = detailJSON + '"columnvalue":"'+$('input:radio[name='+n.columnid+']:checked').val()+'",';
                                                    }else if(n.columntype=='7'){//多选
                                                        var chk_value =[];
                                                        //console.log("================================="+n.columnid);
                                                        $('input[name='+n.columnid+']:checked').each(function(){
                                                            chk_value.push($(this).val());
                                                        });
                                                        detailJSON = detailJSON + '"columnvalue":"'+chk_value.join(',')+'",';
                                                    }else{
                                                        detailJSON = detailJSON + '"columnvalue":"'+$("#"+n.columnid).val().replace(/\"/g,"“")+'",';
                                                    }
                                                    detailJSON = detailJSON + '"columntype":"'+n.columntype+'",';
                                                    detailJSON = detailJSON + '"columnlocate":"'+n.columnlocate+'",';
                                                    detailJSON = detailJSON + '"columnsort":"'+n.columnsort+'"}';
                                                    //validfield+=n.columnid+':'+'{validators: {notEmpty: {message: "请填写'+n.columnname+'名称！"}}},';
                                                    //console.log(detailJSON);
                                                    detailJsonArray.push(detailJSON);
                                                });


                                                $.post("${ctx}/leipiflow/leipiFlowApply/save",
                                                    {
                                                        "flowtemplateid":$("#flowtemplateid").val(),
                                                        "flowid":$("#flowid").val(),
                                                        "title":$("#title").val(),
                                                        "templatehtmlArea":$("#templatehtmlArea").html(),
                                                        "userIds":userIds,
                                                        "detailJsonArray":'['+detailJsonArray+']'
                                                    },function(data){
                                                        var jsonData = jQuery.parseJSON(data);
                                                        if(jsonData.status == 'y'){
                                                            layer.msg(jsonData.info, {icon: 1});
                                                            if($("#leipiflowtype").val() == '1'){//申请
                                                                window.location.href="${ctx}/leipiflow/leipiFlowApply/myLeipiFlowById?flowid="+$("#flowid").val();
															}else{//流程
                                                                window.location.href="${ctx}/leipiflow/leipiFlowApply/myLeipiFlow/";
															}

                                                            /* $(".nav-tabs").find("li").removeClass("disabled");
                                                            $("#flowtemplateid").val(jsonData.flowtemplateid);

                                                            $('#processTable').attr("data-ajax","${ctx}/flow/flowtemplate/getTemplatecontrolList?id="+jsonData.flowtemplateid);
								$('#processTable').bootstrapTable(); */
                                                        }else{
                                                            layer.msg(jsonData.info, {icon: 2});
                                                            //layer.alert('内容', {icon: 0});
                                                        }
                                                    });


											}
									},
					    	cancel: function(index){ //或者使用btn2
					    	           //按钮【按钮二】的回调
					    	       }
							}); 
						});
					}
				}
				
			});
			
			
			
			$("#resetsaveTemplete").click(function(){
				$('#commentForm').data('bootstrapValidator').resetForm(true);
			});
            $("a[name=furl]").live("click",function(){
                var id= $(this).closest("ol").attr("id");
                $(this).parent().remove();
                totalUrl(id);
            });
			
		});
		
		//判断此步骤是否是执行时替换
		function checkExcuteReplace(){
			excuteReplaceFlag=false;
            $("#userIds").val("");
            $("#nextprocessname").val("");
			$.ajax({   
				  url:"${ctx}/leipiflow/leipiFlowApply/checkAutoPerson",   
				  type:'post',   
				  data:{"flowId":'${flowid}'},   
				  async : false, //默认为true 异步   
				  dataType:'json',
				  error:function(){   
				    alert('error');   
				  },   
				  success:function(json){
						if(json.status=='y'){
							excuteReplaceFlag=true;
                            $("#nextprocessname").val(json.processname);
                            $("#isend").val("0");
						}else if(json.status=='n'){
							excuteReplaceFlag=false;
                            $("#userIds").val(json.ids);
                            $("#nextprocessname").val(json.processname);
                            $("#isend").val("0");
						} else{
                            excuteReplaceFlag=false;
                            $("#userIds").val("");
                            $("#isend").val("1");
                        }
				  }
			});
		}
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_del");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}

		function addLine() {
		    var htmlTr = '<tr name="createTr">'+
                '<td><input name="var1" type="text" class="form-control" /></td>'+
                '<td><input name="var2" type="text" class="form-control" /></td>'+
                '<td><input name="var3" type="text" class="form-control" /></td>'+
                '<td><input name="var4" type="text" class="form-control" /></td>'+
                '<td><input name="var5" type="text" class="form-control" /></td>'+
                '<td><input name="var6" type="text" class="form-control" /></td>'+
                '<td><input name="var7" type="text" class="form-control" /></td>'+
                '<td><input name="var8" type="text" class="laydate-icon form-control layer-date1" id="datePicker'+flag+'" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\',isShowClear:false});" /></td>'+
                '<td><input name="var9" type="text" class="form-control" /></td>'+
                /*'<td>'+
                '<button type="button" class="btn btn-primary btn-sm" onclick="commonFileUpload(\'var10'+ flag +'\',\'muti\')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>'+
            	'<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filearea'+ flag +'">'+
                '</ol>'+
                '<input id="var10'+ flag +'" name="var10" type="hidden" value="">'+
                '</td>'+*/
                '<td><a href="javascript:" onclick="delLine(this)" class="text-danger"><i class="glyphicon glyphicon-remove"></i></a></td>'+
            	'</tr>';
		    if($("#createTbody").find("tr").length > 0){
                $("#createTbody").find("tr:last").after(htmlTr);
			}else{
                $("#createTbody").append(htmlTr);
			}

            flag++;
        }

        function addLine2() {
            var htmlTr = '<tr name="createTr">'+
                '<td><input name="var1" type="text" class="form-control" /></td>'+
                '<td><select name="var2" class="form-control" style="min-width: 65px;"><option value="1">奖</option><option value="0">惩</option></select></td>'+
                '<td><input name="var3" type="text" class="form-control" /></td>'+
                '<td><input name="var4" type="text" class="form-control" /></td>'+
                '<td><input name="var5" type="text" class="form-control" /></td>'+
                '<td><input name="var6" type="text" class="form-control" /></td>'+
                '<td><input name="var7" type="text" class="form-control" /></td>'+
                /*'<td>'+
                '<button type="button" class="btn btn-primary btn-sm" onclick="commonFileUpload(\'var10'+ flag +'\',\'muti\')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>'+
                '<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filearea'+ flag +'">'+
                '</ol>'+
                '<input id="var10'+ flag +'" name="var10" type="hidden" value="">'+
                '</td>'+*/
                '<td><a href="javascript:" onclick="delLine(this)" class="text-danger"><i class="glyphicon glyphicon-remove"></i></a></td>'+
                '</tr>';
            if($("#createTbody2").find("tr").length > 0){
                $("#createTbody2").find("tr:last").after(htmlTr);
            }else{
                $("#createTbody2").append(htmlTr);
            }

            flag++;
        }


        function delLine(obj){
		    top.layer.confirm('确定要删除此行？',{
				btn:['确定','取消']
			},function () {
                $(obj).closest("tr").remove();
                top.layer.closeAll();
            });

		}

        function commonFileUploadCallBack(id,url,fname){
            if(url!=null){
                var strId = id.substring(5);
                $.each(url,function (i,n) {
                    $("#filearea"+strId).append('<li class="inline_box"><a class="inline_two" href="javascript:" vl="'+n.fileUrl+'" onclick="commonFileDownLoad(this)">'+n.fileName+'</a> &nbsp; <a href="javascript:" name="furl" vl="'+n.fileUrl+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
                })
            }
            totalUrl(id);
        }
        function totalUrl(id) {
            var strId = id.substring(5);
            var urls = "";
            $("#filearea"+strId).find("a[name=furl]").each(function(i,n){
                var obj = $(this);
                if(i==0){
                    urls+=obj.attr("vl");
                }else{
                    urls+=","+obj.attr("vl");
                }
            });
            $("#var10"+strId).val(urls);
        }


	</script>
</head>
<body class="hideScroll gray-bg  pace-done">
<div class="wrapper wrapper-content">
	<div class="ibox">
		<div class="ibox-title">
			<h5>${leipiFlow.flowname }</h5>
			<div class="ibox-tools">
				<a class="collapse-link">
					<i class="fa fa-chevron-up"></i>
				</a>
				<%--<a class="dropdown-toggle" data-toggle="dropdown" href="#">
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
				</a>--%>
			</div>
		</div>
		<div class="ibox-content">
		<form id="commentForm" action="${ctx}/flow/flowapply/save" method="post" class="form-horizontal">
		<input type="hidden" id="flowtemplateid" name="flowtemplateid" value="${flowtemplate.id }" />
		<input type="hidden" id="flowid" name="flowid" value="${flowid }" />
			<input type="hidden" id="leipiflowtype" name="leipiflowtype" value="${leipiFlow.leipiflowtype }" />
		<input type="hidden" id="userIds" name="userIds" />
			<input type="hidden" id="nextprocessname" name="nextprocessname" />
            <input type="hidden" id="isend" name="isend" value="0"/>

		<sys:message content="${message}"/>	
			<div class="row" id="templatehtmlArea">
				<!-- new html start -->
				<c:if test="${flowid == SALARY_FLOW_ID}">
				<div class="row">
					<div class="col-md-12">
						<div class="pull-right">
							<button class="btn btn-info btn-sm" onclick="addLine()" title="添加行"><i class="fa fa-plus"></i> 添加行</button>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<table class="table table-bordered table-hover dataTable create_table" id="salaryTbl">
							<thead>
								<tr>
									<th>姓名</th>
									<th>原部门</th>
									<th>现部门</th>
									<th>原岗位</th>
									<th>现岗位</th>
									<th>原薪资</th>
									<th>现薪资</th>
									<th>执行时间</th>
									<th>调整说明</th>
									<%--<th>附件</th>--%>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="createTbody">

							</tbody>
						</table>
					</div>
				</div>
				</c:if>

				<c:if test="${flowid == REWARD_FLOW_ID}">
					<div class="row">
						<div class="col-md-12">
							<div class="pull-right">
								<button class="btn btn-info btn-sm" onclick="addLine2()" title="添加行"><i class="fa fa-plus"></i> 添加行</button>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<table class="table table-bordered table-hover dataTable create_table" id="salaryTbl">
								<thead>
								<tr>
									<th>姓名</th>
									<th>奖惩</th>
									<th>部门</th>
									<th>岗位</th>
									<th>奖惩金额</th>
									<th>奖惩分数</th>
									<th>奖惩说明</th>
									<%--<th>附件</th>--%>
									<th>操作</th>
								</tr>
								</thead>
								<tbody id="createTbody2">

								</tbody>
							</table>
						</div>
					</div>
				</c:if>

				<c:if test="${flowid != SALARY_FLOW_ID && flowid != REWARD_FLOW_ID}">
					${flowtemplate.templatehtml }
				</c:if>

			</div>
			<div class="form-actions">

				<c:if test="${flowid == SALARY_FLOW_ID}">
					<input class="btn btn-primary" type="button" id="saveTempleteContentSalary" name="saveTempleteContentSalary" value="保 存">&nbsp;
				</c:if>
				<c:if test="${flowid == REWARD_FLOW_ID}">
					<input class="btn btn-primary" type="button" id="saveTempleteContentReward" name="saveTempleteContentReward" value="保 存">&nbsp;
				</c:if>
				<c:if test="${flowid != SALARY_FLOW_ID && flowid != REWARD_FLOW_ID}">
					<input class="btn btn-primary" type="button" id="saveTempleteContent" name="saveTempleteContent" value="保 存">&nbsp;
				</c:if>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)">
			</div>
			</form>
		</div>
	</div>
</div>

</body>
</html>