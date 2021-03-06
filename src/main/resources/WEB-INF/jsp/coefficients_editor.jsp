<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${locale.getLanguage()}"></c:set>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.training.project4.messages"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Coefficients</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="content">
		<a href="/" style="font-weight: 600"><fmt:message key="jsp.link.main"/></a>
		<h1><fmt:message key="jsp.coefficientsEditor.title"/></h1>
		
		<form accept-charset="UTF-8" action="/app/bookmaker/edit" method="post" enctype="application/x-www-form-urlencoded">
			<table border="1" class="tableCenter">
				<tr>
					<th align="center"><fmt:message key="jsp.betParameters.horse"/></th>
					<th align="center"><fmt:message key="jsp.betParameters.coefficient"/></th>
				</tr>
				<c:forEach var="entry" items="${coefficients.values}">
					<tr>
						<td>
							<c:out value="${entry.key.name}"></c:out>
						</td>
						<td>
							<input type="text" name="${entry.key.name}" value="${entry.value}" />
						</td>
					</tr>
				</c:forEach>
				<c:if test="${not empty errors['coefficients']}">
					<tr>
						<td colspan="2">
							<span><fmt:message key="${errors['coefficients']}"/></span>
						</td>
					</tr>
				</c:if>		
				<tr>
					<td colspan="2">
						<input type="submit" value="<fmt:message key="jsp.coefficientsEditor.submit"/>"/>
						<input type="hidden" name="raceID" value="${raceID}"/> 
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>