<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<c:set var="url">${pageContext.request.requestURL}</c:set>
	<base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
	<title>Messages Inbox</title>
	<link rel="stylesheet" href="resources/css/style.css">
	<script>var base = document.getElementsByTagName("base")[0].href;</script>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="page">
	<c:if test="${not empty unopenedRepliedMessageList}">
   	<p class="heading">SUPPORT HAS RESPONDED TO YOUR REQUEST:</p>
		<table class="message">
			<tr>
				<th><div></div></th>
				<th class="w200">From</th>
				<th class="w250">Subject</th>
				<th class="w150">Date</th>
				<th class="w80"></th>
				<th class="w80"></th>
			</tr>
			
		<c:forEach items="${unopenedRepliedMessageList}" var="uMessage">
		<tr>
			<td><div id="logoUnreadMail"></div></td>
			<td>${uMessage.user.name} ${uMessage.user.surname}</td>
			<td>${uMessage.title}</td>
			<td><fmt:formatDate type="both" timeStyle="short" dateStyle="short" value="${uMessage.creationDate}"/></td>
			<td>
				<form action="readMail">
					<input name="messageId" value="${uMessage.id}" type="hidden" />
					<input name="mailBoxSource" value="userInbox" type="hidden" />
					<input class="button submit" type="submit" value="Read" />
				</form>
			</td>
			<td>
				<form action="disableThread">
					<input name="messageId" value="${uMessage.id}" type="hidden" />
					<input name="mailBoxSource" value="userInbox" type="hidden" />
					<input class="button submit" type="submit" value="Delete" />

				</form>
			</td>
		</tr>
      	</c:forEach>
      	
	</table>
	</c:if>
	<c:if test="${not empty openedRepliedMessageList}">
	<div class="section">
   	<p class="heading">OLD MESSAGES</p>
		<table>
			<tr>
				<th><div></div></th>
				<th class="w200">From</th>
				<th class="w250">Subject</th>
				<th class="w150">Date</th>
				<th class="w80"></th>
				<th class="w80"></th>
			</tr>
			
		<c:forEach items="${openedRepliedMessageList}" var="oMessage">
		<tr>
			<td><div id="logoUnreadMail"></div></td>
			<td>${oMessage.title}</td>
			<td>${oMessage.user.name} ${oMessage.user.surname}</td>
			<td><fmt:formatDate type="both" timeStyle="short" dateStyle="short" value="${oMessage.creationDate}"/></td>
			<td>
				<form action="readMail">
					<input name="messageId" value="${oMessage.id}" type="hidden" />
					<input name="mailBoxSource" value="userInbox" type="hidden" />
					<input class="button submit" type="submit" value="Read" />
				</form>
			</td>
			<td>
				<form action="disableThread">
					<input name="messageId" value="${oMessage.id}" type="hidden" />
					<input name="mailBoxSource" value="userInbox" type="hidden" />
					<input class="button submit" type="submit" value="Delete" />
				</form>
			</td>
		</tr>
      	</c:forEach>
      	
	</table>
	</div>
	</c:if>
	<br/>
	<br/>
	<br/>
	<div>
		<a class="button contact" href="contact">Contact Us</a>
	</div>
	<div>
		<a class="button login" href="userOutbox">Outbox</a>
	</div>
</div>	
</body>
</html>