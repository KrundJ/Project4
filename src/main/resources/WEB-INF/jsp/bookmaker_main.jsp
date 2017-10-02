<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bookmaker</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="container">
		<div class="topDiv">
			<div class="topBar left"></div>

			<div class="topBar right">
				<form action="/app/administrator" method="get"
					enctype="application/x-www-form-urlencoded">
					<table style="border: solid; border-color: black; float: right;">
						<tr>
							<td>
								<c:if test="${lang eq 'en'}">
									<c:set value="selected=\"\"" var="selectedEn"></c:set>
								</c:if> <c:if test="${lang eq 'ru'}">
									<c:set value="selected=\"\"" var="selectedRu"></c:set>
								</c:if> <select name="new_lang">
									<option value="en" ${selectedEn}>English(US)</option>
									<option value="ru" ${selectedRu}>Russian</option>
							</select></td>
							<td><input type="submit" value="Change"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>

		<div class="content">
			<h2>Races:</h2>

			<table border="1" class="tableCenter">
				<tr>
					<th>Distance:</th>
					<th>Horses and Jockeys:</th>
					<th>Coefficients:</th>
					<th>State:</th>
					<th>Date:</th>
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
							${race.state}
						</td>
						<td>
							${race.date}
						</td>
						<td>
							<button form="editForm" type="submit" name="raceID" value="${race.ID}">Set or edit</button>
						</td>
					</tr>
				</c:forEach>
				<form id="editForm" method="get" action="/app/bookmaker/edit"></form>
			</table>
		</div>
	</div>
</body>
</html>