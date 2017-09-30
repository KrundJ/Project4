<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="WEB-INF/mytags.tld" prefix="m" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp</title>
</head>
<body>
	<m:today/>  
	<%
		System.out.println("test jsp");

		double number = Math.random();
	%>

	<p>
		Math.random():
		<%=number%>
	</p>

</body>
</html>