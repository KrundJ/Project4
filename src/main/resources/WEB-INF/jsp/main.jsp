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
<title>Main</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="container">
		<a href="/" style="font-weight: 600"><fmt:message key="jsp.link.main"/></a>
		<div class="content">
			<h2><fmt:message key="jsp.main.title"/></h2>
			<h4>
				<a href="/app/winnings">
					<fmt:message key="jsp.main.winnings"/>
				</a>
			</h4>
			<table border="1" class="tableCenter">
				<tr>
					<th><fmt:message key="jsp.raceParameters.distance"/></th>
					<th><fmt:message key="jsp.raceParameters.horseJockey"/></th>
					<th><fmt:message key="jsp.raceParameters.coefficients"/></th>
					<th><fmt:message key="jsp.main.action"/></th>
				</tr>
				<c:forEach items="${races}" var="race">		
					<tr>
						<td>
							<c:out value="${race.distance.getDistance()}"/> 
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
							<button form="actionForm" type="submit" name="raceID" value="${race.ID}"><fmt:message key="jsp.betEditor.submit"/></button>
						</td>
					</tr>
				</c:forEach>
			</table>
			<!-- Action form -->
			<form id="actionForm" method="get" action="/app/bet"></form>
		</div>
	</div>
</body>
</html>