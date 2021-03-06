<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="cus" uri="/WEB-INF/raceResults.tld" %>
  
    		
<c:set var="language" value="${locale.getLanguage()}"></c:set>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.training.project4.messages"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Race Results</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="content">
		<a href="/" style="font-weight: 600"><fmt:message key="jsp.link.main"/></a>
		<h1><fmt:message key="jsp.raceResultsEditor.title"/></h1>
		 		 		
		<form accept-charset="UTF-8" action="/app/administrator/results" method="post" enctype="application/x-www-form-urlencoded">
			<table border="1" class="tableCenter">
				<tr>
					<th align="center"><fmt:message key="jsp.betParameters.place"/></td>
					<th align="center"><fmt:message key="jsp.raceResultsParameters.horse"/></td>
				</tr>

				<cus:raceResults results="${race.raceResults}" selection="${selection}" />
				
				<c:if test="${not empty errors.msg}">
					<tr>
						<td colspan="2">
							<c:out value="${errors.msg}"></c:out> 
						</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="2">
						<input type="submit" value="<fmt:message key="jsp.raceResultsEditor.submit"/>"/>
						<input type="hidden" name="raceID" value="${race.ID}"/> 
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>