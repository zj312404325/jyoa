<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="decorator" content="default"/>
    <title>消息提醒</title>
<script language="Javascript">
$(function(){
	closeit();
});
function closeit()
{
setTimeout("self.close()",90000);
}
</script>
</head>

<body class="gray-bg" >
	<table cellpadding="0" cellspacing="0" style="width:100%;">
		<tr>
			<td style="padding-left:10px; padding-right:10px; font-size:16px; font-family:'MicroSoft Yahei'; vertical-align: middle; color:orangered">
				<c:forEach items="${lst}" var="m">
				  <i class="glyphicon glyphicon-bullhorn"></i>
				   <c:if test="${m.url == null || m.url == ''}">${m.title}</c:if>
    			   <c:if test="${m.url != null && m.url != ''}"><a style="color:orangered" href="${m.url}" target="_blank" >${m.title}</a></c:if>
				</c:forEach>
			</td>
		</tr>
	</table>
</body>

</html>