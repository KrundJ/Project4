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
<title>HorseRaces</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
<div class="container">
		<div class="topDiv">
			<div class="topBar left">
				<form action="/app/logout" method="post"
					enctype="application/x-www-form-urlencoded">
					<input type="submit" value="<fmt:message key="jsp.link.logout"/>"/>
				</form>
			</div>
			<div class="topBar right">
				<form action="/app/locale" method="post"
					enctype="application/x-www-form-urlencoded">
					<table style="float: right;">
						<tr>
							<td>
								<select name="new_lang">
									<option value="en">English(US)</option>
									<option value="ua">Украинский</option>
								</select>
							</td>
							<td><input type="submit" value="<fmt:message key="jsp.link.lang"/>"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div class="content">
			<h1><a href="/app/administrator"><fmt:message key="jsp.link.admin"/></a></h1>
			<h1><a href="/app/bookmaker"><fmt:message key="jsp.link.bookm"/></a></h1>
			<h1><a href="/app/races"><fmt:message key="jsp.link.user"/></a></h1>
		</div>
	</div>
</body>
</html>