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
<title>Login</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="content">
	
		<a href="/" style="font-weight: 600"><fmt:message key="jsp.link.main"/></a>

		<div class="formDiv">	
			<form action="/app/login" method="post" enctype="application/x-www-form-urlencoded"
				accept-charset="UTF-8">
			<table style="border:solid; border-color: black; border-width: 4px;">
				<tr>
					<td colspan="2" align="center">
						<h4><fmt:message key="jsp.login.title"/></h4>
					</td>
				</tr>
				<tr>
					<td align="center">
						<fmt:message key="jsp.authParameters.login"/>
					</td>
					<td align="center">
						<input type="text" name="login"/>
					</td>
				</tr>
				<tr>
					<td align="center">
						<fmt:message key="jsp.authParameters.password"/>
					</td>
					<td align="center">
						<input type="password" name="password"/>
						<c:if test="${not empty errors}">
							<br>
							<span><fmt:message key="jsp.login.error"/></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td align="right">
						<input type="submit" value="<fmt:message key="jsp.login.title"/>"/>
					</td>
					<td align="right">
						<a href="/app/register" style="font-weight: 600"><fmt:message key="jsp.register.title"/></a>
					</td>
				</tr>
			</table>
			</form>			
		</div>
	</div>
</body>
</html>