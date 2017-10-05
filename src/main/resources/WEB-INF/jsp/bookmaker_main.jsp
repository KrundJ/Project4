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
<title>Bookmaker</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="container">
		<div class="content">
			<h2><fmt:message key="jsp.bookmaker.title"/></h2>
			<table border="1" class="tableCenter">
				<tr>
					<th><fmt:message key="jsp.raceParameters.distance"/></th>
					<th><fmt:message key="jsp.raceParameters.horseJockey"/></th>
					<th><fmt:message key="jsp.raceParameters.coefficients"/></th>
					<th><fmt:message key="jsp.raceParameters.state"/></th>
					<th><fmt:message key="jsp.raceParameters.date"/></th>
					<th><fmt:message key="jsp.bookmaker.action"/></th>
				</tr>
				<c:forEach items="${races}" var="race">
					<tr>
						<td>
							<c:out value="${race.distance.getDistance()}"></c:out> 
						</td>
						<td>
							<c:forEach var="entry" items="${race.raceResults}">
								<c:out value="${entry.key.name} - ${entry.key.jockey}"></c:out>
								<br>
							</c:forEach>
						</td>
						<td>
							<c:forEach var="coef" items="${coefficients}">
								<c:if test="${coef.raceID eq race.ID}">
									<c:set var="currentCoef" value="${coef}"></c:set>
								</c:if>
							</c:forEach> 
							<c:forEach var="entry" items="${currentCoef.values}">
								<c:out value="${entry.value}"></c:out>
								<br>
							</c:forEach>
						</td>
						<td>
							<c:out value="${race.state}"></c:out> 
						</td>
						<td>
							<fmt:formatDate value="${race.date}"/> 
						</td>
						<td>
							<button form="editForm" type="submit" name="raceID" value="${race.ID}"><fmt:message var="jsp.bookmaker.setOrEdit"/></button>
						</td>
					</tr>
				</c:forEach>
				<form id="editForm" method="get" action="/app/bookmaker/edit"></form>
			</table>
		</div>
	</div>
</body>
</html>