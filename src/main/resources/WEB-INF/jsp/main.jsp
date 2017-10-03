<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Main</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="container">
		<div class="content">
			<h2>Current races:</h2>
			<table border="1" class="tableCenter">
				<tr>
					<th>Distance:</th>
					<th>Horses and Jockeys:</th>
					<th>Coefficients:</th>
					<th>Action:</th>
				</tr>
				<c:forEach items="${races}" var="race">		
					<tr>
						<td>
							${race.distance.getDistance()}
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
							<button form="actionForm" type="submit" name="raceID" value="${race.ID}">Make bet</button>
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