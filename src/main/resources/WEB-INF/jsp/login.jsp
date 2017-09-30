<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<center>
		<form action="/login" method="post">
		<table style="border:solid ;border-color: black; border-width: 4px;">
			<tr>
				<td colspan="2" align="center"><h4>Login</h4></td>
			</tr>
			<tr>
				<td align="center">login:</td>
				<td align="center"><input type="text" name="login"/></td>
				<td align="center">Error text</td>
			</tr>
			<tr>
				<td align="center">password:</td>
				<td align="center"><input type="password" name="login"/></td>
			</tr>
				<td rowspan="2" align="right"><input type="submit" value="Login"/></td>
			</tr>
		</table>
		</form>			
	</center>
</body>
</html>