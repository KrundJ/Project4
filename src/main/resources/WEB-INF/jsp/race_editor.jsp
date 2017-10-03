<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="cus" uri="/WEB-INF/horsesInRace.tld" %>
		
		<%--locale set in command, request parameter --%>
<c:set var="language" value="${locale.getLanguage()}"></c:set>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.training.project4.messages"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Race</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>

	<div class="content">
		
		<h1><fmt:message key="${titleKey}"/></h1>
		
		<form accept-charset="UTF-8" action="${commandURL}" method="post"
			enctype="application/x-www-form-urlencoded">
			<table border="1" class="tableCenter">
				<tr>
					<td align="center"><b>Distance:</b></td>
					<td align="center"><b>Date:</b></td>
					<td rowspan="2" align="center"><input type="submit"
						value="Create" /></td>
				</tr>
				<tr>
					<td align="center">
						<c:if test="${not empty race}">
							<c:set var="distance" value="${race.distance}"></c:set>
						</c:if> 
						<select name="distance">
							<c:if test="${distance eq 'RACE_ON_1600m'}">
								<c:set var="sel_1600" value="selected=\"selected\""></c:set>
							</c:if>
							<option value="RACE_ON_1600m" ${sel_1600}>1600</option>
							
							<c:if test="${distance eq 'RACE_ON_2400m'}">
								<c:set var="sel_2400" value="selected=\"selected\""></c:set>
							</c:if>		
							<option value="RACE_ON_2400m" ${sel_2400}>2400</option>
							
							<c:if test="${distance eq 'RACE_ON_3200m'}">
								<c:set var="sel_3200" value="selected=\"selected\""></c:set>
							</c:if>
							<option value="RACE_ON_3200m" ${sel_3200}>3200</option>
						</select> 
						<c:if test="${not empty errors['distance']}">
							<br>
							<span><c:out value="${errors['distance']}"></c:out></span>
						</c:if>
					</td>
					<td align="center">
						<c:if test="${not empty race}">
							<c:set var="date" value="${race.date}"></c:set>
						</c:if> 
						<input type="text" value="<fmt:formatDate value="${date}" dateStyle="MEDIUM"/>" name="date" placeholder="${dateFormat}" size="9" /> 
						<c:if test="${not empty errors['date']}">
							<br>
							<span><c:out value="${errors['date']}"></c:out></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center">
						<b>Horses:</b> 
						<c:if test="${not empty errors['horse']}">
							<br>
							<span><c:out value="${errors['horse']}"></c:out></span>
						</c:if>
					</td>
				</tr>

				<cus:horsesInRace allHorses="${horses}" raceToEdit="${race}" />

			</table>
		</form>
	</div>
</body>
</html>