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
<title>Make Bet</title>
</head>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="content">
		<a href="/" style="font-weight: 600"><fmt:message key="jsp.link.main"/></a>
		<h1><fmt:message key="jsp.betEditor.title"/></h1>
		 		 		
		<form accept-charset="UTF-8" action="/app/bet" method="post" enctype="application/x-www-form-urlencoded">
			<table border="1" class="tableCenter">
				<tr>
					<td>
						<b><fmt:message key="jsp.betParameters.type"/></b>
					</td>
					<td>
						<b><fmt:message key="jsp.betParameters.horse"/></b>
					</td>
					<td>
						<select name="horseName">
							<c:forEach items="${race.raceResults.keySet()}" var="horse">
								<option value="${horse.name}">${horse.name}</option>
							</c:forEach>
						</select>
						<c:if test="${not empty errors['horseName']}">
							<br>
							<span><fmt:message key="${errors['horseName']}" /></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td rowspan="3" align="left">
						<input type="radio" name="betType" value="WIN_BET" checked="checked"><fmt:message key="jsp.betParameters.type.win"/><br>
						<input type="radio" name="betType" value="PLACE_BET"><fmt:message key="jsp.betParameters.type.place"/><br>
						<input type="radio" name="betType" value="SHOW_BET"><fmt:message key="jsp.betParameters.type.show"/>
						
						<c:if test="${not empty errors['betType']}">
							<br>
							<span><fmt:message key="${errors['betType']}" /></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<b><fmt:message key="jsp.betParameters.amount"/></b>
					</td>
					<td>
						<input type="text" name="amount" size="10"/>
						<c:if test="${not empty errors['amount']}">
							<br>
							<span><fmt:message key="${errors['amount']}" /></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<!-- EMPTY -->
					</td>
					<td>
						<input type="submit" value="<fmt:message key="jsp.betEditor.submit"/>"/>
						<input type="hidden" name="raceID" value="${race.ID}"/> 
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>