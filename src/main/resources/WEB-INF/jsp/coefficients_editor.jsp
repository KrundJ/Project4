<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="ua.training.project4.model.entities.Coefficients" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Coefficients</title>
<style>
		.container{	
			overflow-y: hidden;
			width: 100%;
			
			border: solid;
			border-color: black;
		}

		.topDiv{	
			height:150px;
			padding: 0 0;
			margin: 0 0;
			z-index: 1;
			line-height: 30px;
			
			border: solid;
			border-color: yellow;
		}
		
		.topBar{
			width: 280px;
			height: 100%;
		}

		.topBar.left{
			text-align: left;
			float: left;  
			
			border: solid;
			border-color: green;
		}

		.topBar.right{
			text-align: right;
			float: right;
	
			border: solid;
			border-color: red;
		}
		
		.content {	
			float: top;
			margin: 0 auto;
			height: 100%;
			width: 90%;
			text-align: center;
			overflow: auto;
			
			border: solid;
			border-color: pink;
			
		}
		
		.tableCenter {
		 	display: inline-block;
		}
		
		span {
			font-weight: 600;
			font-size: 12px;
			color: red; 
		}
	</style>
</head>
<body>
	<%
		Coefficients c = (Coefficients) request.getAttribute("coefficients");
		System.out.println(c);
	%>
	<div class="content">	
		<h1><c:out value="Set"></c:out></h1>
		<form accept-charset="UTF-8" action="/app/bookmaker/edit" method="post" enctype="application/x-www-form-urlencoded">
			<table border="1" class="tableCenter">
				<tr>
					<th align="center">Horse:</th>
					<th align="center">Coefficient:</th>
				</tr>
				<c:forEach var="entry" items="${coefficients.values}">
					<tr>
						<td>
							<c:out value="${entry.key.name}"></c:out>
						</td>
						<td>
							<input type="text" name="${entry.key.name}" value="${entry.value}" />
						</td>
					</tr>
				</c:forEach>
				<c:if test="${not empty errors['coefficients']}">
					<tr>
						<td colspan="2">
							<c:out value="${errors['coefficients']}"></c:out>
						</td>
					</tr>
				</c:if>		
				<tr>
					<td colspan="2">
						<input type="submit" value="Submit"/>
						<input type="hidden" name="raceID" value="${raceID}"/> 
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>