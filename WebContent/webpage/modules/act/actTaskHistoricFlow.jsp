<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<table class="table table-striped table-bordered table-condensed">
	<tr><th>执行环节</th><th>执行人</th><th>开始时间</th><th>结束时间</th><th>提交意见</th><th>任务历时</th></tr>
	<%-- <c:forEach items="${histoicFlowList}" var="act">
		<tr>
			<td>${act.histIns.activityName}</td>
			<td>${act.assigneeName}</td>
			<td><fmt:formatDate value="${act.histIns.startTime}" type="both"/></td>
			<td><fmt:formatDate value="${act.histIns.endTime}" type="both"/></td>
			<td style="word-wrap:break-word;word-break:break-all;">${act.comment}</td>
			<td>${act.durationTime}</td>
		</tr>
	</c:forEach> --%>
	<c:forEach items="${histoicFlowList}" var="act">
		<tr>
			<c:choose> 
			  <c:when test="${act.AGENTCREATEBY!=null&&act.AGENTCREATEBY!='' }">   
			     <td>${act.ACT_NAME_}</td>
				 <td>${act.APPLYUSER}（由${act.AGENTCREATEBYUSER}指定代理）</td>
				 <td>${act.START_TIME_}</td>
				 <td>${act.END_TIME_}</td>
				 <td style="word-wrap:break-word;word-break:break-all;">${act.COMMENT_}</td>
				 <td>
				 <c:choose> 
				  <c:when test="${act.ACT_TYPE_!='startEvent'&&act.ACT_TYPE_!='endEvent'}">   
				    ${act.DURATION_}  
				  </c:when> 
				  <c:otherwise>   
				  </c:otherwise> 
				</c:choose></td>
			  </c:when> 
			  <c:when test="${act.ISAGENT==1 }">   
			     <td></td>
				 <td>${act.APPLYUSER}</td>
				 <td>${act.START_TIME_}</td>
				 <td>${act.END_TIME_}</td>
				 <td style="word-wrap:break-word;word-break:break-all;">${act.COMMENT_}</td>
				 <td>
				 <c:choose> 
				  <c:when test="${act.ACT_TYPE_!='startEvent'&&act.ACT_TYPE_!='endEvent'}">   
				    ${act.DURATION_}  
				  </c:when> 
				  <c:otherwise>   
				  </c:otherwise> 
				</c:choose></td>
			  </c:when> 
			  <c:otherwise>   
			     <td>${act.ACT_NAME_}</td>
				 <td><c:if test="${act.ACT_TYPE_=='startEvent'}">${act.APPLYUSERID}</c:if><c:if test="${act.ACT_TYPE_!='startEvent'}">${act.APPLYUSER}</c:if></td>
				 <td>${act.START_TIME_}</td>
				 <td>${act.END_TIME_}</td>
				 <td style="word-wrap:break-word;word-break:break-all;">${act.COMMENT_}</td>
				 <td>
				 <c:choose> 
				  <c:when test="${act.ACT_TYPE_!='startEvent'&&act.ACT_TYPE_!='endEvent'}">   
				    ${act.DURATION_}  
				  </c:when> 
				  <c:otherwise>   
				  </c:otherwise> 
				</c:choose></td>
			  </c:otherwise> 
			</c:choose>
			
		</tr>
	</c:forEach>
</table>