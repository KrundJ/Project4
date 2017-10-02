<%@page import="java.util.Locale"%>
<%@page import="ua.training.project4.model.entities.Race"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="mm" %>  
<%@ page session="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Administrator</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>	
	<div class="container">
		<div class="topDiv">
					
			<div class="topBar left">
			
			</div>
			<%--locale set in command, request parameter --%>
			<c:set var="language" value="${sessionScope.locale.getLanguage()}"></c:set>
			<fmt:setLocale value="${language}" />
			<fmt:setBundle basename="ua.training.project4.messages"/>
			
			<div class="topBar right">
				<form action="/app/locale" method="post"
					enctype="application/x-www-form-urlencoded">
					<table style="border: solid; border-color: black; float: right;">
						<tr>
							<td>
								<c:if test="${language eq 'en'}">
									<c:set value="selected=\"\"" var="selectedEn"></c:set>
								</c:if> 
								<c:if test="${language eq 'ru'}">
									<c:set value="selected=\"\"" var="selectedRu"></c:set>
								</c:if> 
								<select name="new_lang">
									<option value="en" ${selectedEn}>English(US)</option>
									<option value="ru" ${selectedRu}>Русский</option>
								</select>
							</td>
							<td><input type="submit" value="Change"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>

		<div class="content">
			<h2><fmt:message key="jsp.admin.main.title"/></h2>
			<h4>
				<a href="/app/administrator/new">Create a new race</a>
			</h4>
			<table border="1" class="tableCenter">
				<tr>
					<th>Distance:</th>
					<th>Results:</th>
					<th>Coefficients set:</th>
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
							<c:if test="${race.state == 'FINISHED'}">
								first<br>
							    second<br>
							    third<br>
							</c:if>
						</td>
						<td>
							false
						</td>
						<td>
							${race.state}
						</td>
						<td>
							${race.date}
						</td>
						<td>
							<script type="text/javascript">
								function action(value) {
									var form = document
											.getElementById("actionForm");
									var input = document.getElementById("raceID");
									var values = value.split(":");
									var urlPart = values[0];
									var raceid = values[1];
									/*GET for edit and set results*/
									if (urlPart == "/app/administrator/edit" || urlPart == "/app/administrator/results") {
										location.assign(urlPart + "?raceID=" + raceid);
										return;
									}
									form.action = urlPart;
									input.value = raceid;
									form.submit();
								}
							</script> 
							<select onchange="action(this.value);">
								<option value="">None</option>
								<option value="/app/administrator/start:${race.ID}">Start</option>
								<option value="/app/administrator/finish:${race.ID}">Finish</option>
								<option value="/app/administrator/edit:${race.ID}">Edit</option>
								<option value="/app/administrator/delete:${race.ID}">Delete</option>
								<option value="/app/administrator/results:${race.ID}">Set results</option>	
							</select>
						</td>
					</tr>
				</c:forEach>
			</table>
			<!-- Form to send race action -->
			<form action="" accept-charset="UTF-8" method="post" name="actionForm" id="actionForm">
				<input id="raceID" type="hidden" name="raceID" value="" />
			</form>
		</div>
	</div>
</body>
</html>