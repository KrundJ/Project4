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
				<c:when test="${not empty messageToFormat}">
					<fmt:message key="${messageToFormat}"/>	<c:out value="${message}"></c:out> <%=Currency.getInstance(loc).getSymbol()%>	
				</c:when>
				<c:otherwise>
					<fmt:message key="${message}"/>
				</c:otherwise>
			</c:choose>

	    </div>
    </div>
</body>
</html>