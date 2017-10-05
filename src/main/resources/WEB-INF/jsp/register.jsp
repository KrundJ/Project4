<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="content">
	
		<a href="/" style="font-weight: 600">Main</a>
		
		<div class="formDiv">
			<form action="/app/register" method="post" enctype="application/x-www-form-urlencoded"
				accept-charset="UTF-8">
			<table style="border:solid; border-color: black; border-width: 4px;">
				<tr>
					<td colspan="2" align="center">
						<h4>Register</h4>
					</td>
				</tr>
				<tr>
					<td align="center">
						Login:
					</td>
					<td align="center">
						<input type="text" name="login"/>
						<c:if test="${not empty errors['login']}">
							<br>
							<span><c:out value="${errors['login']}"></c:out></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td align="center">
						Password:
					</td>
					<td align="center">
						<input type="password" name="password"/>
						<c:if test="${not empty errors['password']}">
							<br>
							<span><c:out value="${errors['password']}"></c:out></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td align="right">
						<input type="submit" value="Register"/>
					</td>
				</tr>
			</table>
			</form>			
		</div>
	</div>
</body>
</html>