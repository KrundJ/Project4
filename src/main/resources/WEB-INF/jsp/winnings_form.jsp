<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Winnings</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
</head>
<body>
	<div class="content">
		<form accept-charset="UTF-8" action="/app/winnings" method="post" enctype="application/x-www-form-urlencoded">
			<table border="1" class="tableCenter">
				<tr>
					<th align="center">
						Please, enter bet ID
					</th>
				</tr>
				<tr>
					<td align="center">
						<input name="betID" type="text"/>
						<c:if test="${not empty errors['betID']}">
							<br>
							<span><c:out value="${errors['betID']}"></c:out></span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td align="center">
						<input type="submit" value="Submit"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>