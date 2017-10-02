<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Make Bet</title>
</head>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="content">
		
		<h1>Make Bet</h1>
		 		 		
		<form accept-charset="UTF-8" action="/app/bet" method="post" enctype="application/x-www-form-urlencoded">
			<table border="1" class="tableCenter">
				<tr>
					<td>
						<b>Type:</b>
					</td>
					<td>
						<b>Horse:</b>
					</td>
					<td>
						<select name="horse">
							<c:forEach items="${race.raceResults.keySet()}" var="horse">
								<option value="${horse.name}">${horse.name}</option>
							</c:forEach>
						</select>
						<c:if test="${not empty errors['horse']}">
							<br>
							<span><c:out value="${errors['horse']}"></c:out></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td rowspan="3" align="left">
						<input type="radio" name="betType" value="WIN_BET" checked="checked">Win bet<br>
						<input type="radio" name="betType" value="PLACE_BET">Place bet<br>
						<input type="radio" name="betType" value="SHOW_BET">Show bet
						
						<c:if test="${not empty errors['betType']}">
							<br>
							<span><c:out value="${errors['betType']}"></c:out></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<b>Amount:</b>
					</td>
					<td>
						<input type="text" name="amount" size="10"/>
						<c:if test="${not empty errors['amount']}">
							<br>
							<span><c:out value="${errors['amount']}"></c:out></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<!-- EMPTY -->
					</td>
					<td>
						<input type="submit" value="Make Bet"/>
						<input type="hidden" name="raceID" value="${race.ID}"/> 
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>