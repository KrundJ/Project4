<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bet Successful</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="content">
		<h1>Your bet is accepted</h1>
		
		<h4>Bet ID: <c:out value="${bet.ID}"></c:out></h4>	
		<h4>Amount: <c:out value="${bet.amount}"></c:out></h4>
		<h4>Race ID: <c:out value="${bet.raceID}"></c:out></h4>
		<h4>Horse: <c:out value="${bet.horseName}"></c:out></h4>
		<h4>Bet type: <c:out value="${bet.betType.name().toLowerCase().replace('_', ' ')}"></c:out></h4>
	</div>
</body>
</html>