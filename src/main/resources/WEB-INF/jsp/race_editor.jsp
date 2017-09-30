<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="javax.servlet.jsp.JspWriter" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="ua.training.project4.model.entities.Horse" %>
<%@ page import="ua.training.project4.model.entities.Race" %>
		
		<%--locale set in command, request parameter --%>
<c:set var="language" value="${locale.getLanguage()}"></c:set>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.training.project4.messages"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Race</title>
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
		td {
		   align="center";
		}
		span {
			font-weight: 600;
			font-size: 12px;
			color: red; 
		}
	</style>
</head>
<body>

			<div class="content">	
				<fmt:message key="${titleKey}" var="title"/>
			
				<h1><c:out value="${ title }"></c:out></h1>
				<form accept-charset="UTF-8" action="${commandURL}" method="post" enctype="application/x-www-form-urlencoded">
					<table border="1" class="tableCenter">
						<tr>
							<td align="center"><b>Distance:</b></td>
							<td align="center"><b>Date:</b></td>
							<td rowspan="2" align="center"><input type="submit" value="Create"/></td>
						</tr>
						<tr>
							<td align="center"> 
								<c:if test="${not empty raceToEdit}">
									<c:set var="distance" value="${raceToEdit.distance}"></c:set>
								</c:if>
								<select name="distance">
		  							<option value="RACE_ON_1600m" <c:if test="${distance eq 'RACE_ON_1600m'}">selected="selected"</c:if> >1600</option>
		  							<option value="RACE_ON_2400m" <c:if test="${distance eq 'RACE_ON_2400m'}">selected="selected"</c:if> >2400</option>
		  							<option value="RACE_ON_3200m" <c:if test="${distance eq 'RACE_ON_3200m'}">selected="selected"</c:if> >3200</option>
								</select>
								
								<c:if test="${not empty errors['distance']}">
									<br>
									<span><c:out value="${errors['distance']}"></c:out></span>
								</c:if>
		  					</td>
							<td align="center">
								<c:if test="${not empty raceToEdit}">
									<c:set var="date" value="${raceToEdit.date}"></c:set>
								</c:if>
								<input type="text" value="<fmt:formatDate value="${date}" dateStyle="MEDIUM"/>" name="date" placeholder="${dateFormat}" size="9"/>
								<c:if test="${not empty errors['date']}">
									<br>
									<span><c:out value="${errors['date']}"></c:out></span>
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan="3" align="center">
								<b>Horses:</b>
								<c:if test="${not empty errors['horse']}">
									<br>
									<span><c:out value="${errors['horse']}"></c:out></span>
								</c:if>
							</td>
						</tr>
						<%!
							int itemsTotal = 5;
							int itemsInRow = 3;						
							private void showHorses(JspWriter out, Set<Horse> allHorses, Race raceToEdit) {  
							  try {
								  	Set<Horse> horsesInRace = null;
								  	if (raceToEdit != null) {
								  		horsesInRace = raceToEdit.getRaceResults().keySet();
								  	}
								  	int itemsPrinted = 0;
								  	boolean needMoreItems = true;
								  	
								  	while(needMoreItems) {
								  		out.println("<tr>");
								  		for (int j = 1; j <= itemsInRow; j++) {
								  			out.println("<td>");
									  			out.println("<select name=\"horse\">");
									  				boolean selectionSet = false;
													for (Horse h : allHorses) {
														if (horsesInRace != null && horsesInRace.contains(h) && ! selectionSet) {
															out.println(String.format("<option value=\"%s\" selected=\"selected\">%s</option>", h.getName(), h.getName()));
															horsesInRace.remove(h);
															selectionSet = true;
														} else {
															out.println(String.format("<option value=\"%s\">%s</option>", h.getName(), h.getName()));
														}
													}
									  			out.println("</select>");
								  			out.println("</td>");
								  			itemsPrinted++;
								  			if (itemsPrinted == itemsTotal) {
								  				needMoreItems = false;
								  				break;
								  			}
								  		}
								  		out.println("</tr>");
								  	}
								  									  
								} catch(Exception ex) { 
								  ex.printStackTrace();
							  	}
							}
						%>
						
						<%
							Set<Horse> horses = (Set<Horse>) request.getAttribute("horses");
							Race race = null;
							try {
								race = (Race) request.getAttribute("raceToEdit");
							} catch(NullPointerException ex) {
								
							}
							showHorses(out, horses, race);
						%>
		
					</table>
				</form>
			</div>
</body>
</html>