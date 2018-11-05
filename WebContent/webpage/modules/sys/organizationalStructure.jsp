<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>组织架构表</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/jOrgChart/css/jquery.jOrgChart.css"/>
	<script type="text/javascript" src="${ctxStatic}/jOrgChart/jquery.jOrgChart.js"></script>
	<style>
		a {
			text-decoration: none;
			color: #fff;
			font-size: 12px;
		}
		.jOrgChart .node {
			min-width: 120px;
			height: 50px;
			line-height: 50px;
			border-radius: 4px;
			margin: 0 8px;
		}
		.jOrgChart .node a:hover { color:#fff}
	</style>
	<script type="text/javascript">


	</script>
</head>
<body >
<!--显示组织架构图-->
<div id='jOrgChart' style="margin-top: 50px"></div>


<script type='text/javascript'>
    $(function(){
        //数据返回
        $.ajax({
            //url: "${ctxStatic}/jOrgChart/test.json",
			url:"${ctx}/sys/office/organizationalStructureJson",
            type: 'POST',
            dataType: 'JSON',
            data: {action: 'org_select'},
            success: function(result){
                var showlist = $("<ul id='org' style='display:none'></ul>");
                showall(result.data, showlist);
                $("#jOrgChart").append(showlist);
                $("#org").jOrgChart( {
                    chartElement : '#jOrgChart',//指定在某个dom生成jorgchart
                    dragAndDrop : false //设置是否可拖动
                });

            }
        });
    });

    function showall(menu_list, parent) {
        $.each(menu_list, function(index, val) {
            if(val.childrens.length > 0){

                var li = $("<li></li>");
                //li.append("<a href='javascript:void(0)' onclick=getOrgId("+val.id+");>"+val.name+"</a>").append("<ul></ul>").appendTo(parent);
                li.append("<a href='javascript:void(0)' onclick=getOrgId("+val.id+");>"+val.name+"</a>").append("<ul></ul>").appendTo(parent);
                //递归显示
                showall(val.childrens, $(li).children().eq(1));
            }else{
                //$("<li></li>").append("<a href='javascript:void(0)' onclick=getOrgId("+val.id+");>"+val.name+"</a>").appendTo(parent);
                $("<li></li>").append("<a href='javascript:void(0)' >"+val.name+"</a>").appendTo(parent);
            }
        });

    }

</script>
</body>
</html>