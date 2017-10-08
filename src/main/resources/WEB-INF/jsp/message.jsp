<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import = "java.util.Locale"%>
<%@ page import = "java.util.Currency"%>
<%
	Locale loc = (Locale) request.getSession().getAttribute("locale");
%>

<c:set var="language" value="${locale.getLanguage()}"></c:set>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.training.project4.messages"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Message</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="content">
		<a href="/" style="font-weight: 600"><fmt:message key="jsp.link.main"/></a>
		<div class="messageDiv">
			
			<c:choose>
				<c:when test="${message.getClass().simpleName eq 'Winnings'}">
					 <fmt:message key="${message.message}"/>  <c:if test="${not empty message.amount}"> <c:out value="${message.amount}"></c:out> <%=Currency.getInstance(loc).getSymbol()%></c:if>  	
				</c:when>
				<c:otherwise>
					otherwise
					<fmt:message key="${message}"/>
				</c:otherwise>
			</c:choose>

	    </div>
    </div>
</body>
</html>