<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="javax.servlet.jsp.JspWriter" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry"%>
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
<title>Race Results</title>
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
		
		<h1>Race Results</h1>
			
		<%!
			int totalHorses = 5;
			private void showResult(JspWriter out, Map<Horse, Integer> raceResults, Map<Integer, String> selection) throws Exception{
				for (int i = 1; i <= totalHorses; i++) {
	  				out.println("<tr>");
	  					out.println("<td align=\"center\">");
	  						out.println("<b>" + i + "</b>");
	  					out.println("</td>");
	  					out.println("<td>");
		  					out.println("<select name=\"" + i + "\">");
							for (Horse h : raceResults.keySet()) {
								if (selection != null && selection.values().contains(h.getName()) && h.getName().equals(selection.get(i))) {
									out.println(String.format("<option value=\"%s\" selected=\"selected\">%s</option>", h.getName(), h.getName()));
								} else {
									out.println(String.format("<option value=\"%s\">%s</option>", h.getName(), h.getName()));
								}
							}
		  					out.println("</select>");
	  					out.println("</td>");
	  				out.println("</tr>");
	  			}
			}
		%>
		
		 		 		
		<form accept-charset="UTF-8" action="/app/administrator/results" method="post" enctype="application/x-www-form-urlencoded">
			<table border="1" class="tableCenter">
				<tr>
					<th align="center">Place:</td>
					<th align="center">Horse:</td>
				</tr>
				<%
					Map<Horse, Integer> results = null;
					Map<Integer, String> selection = null;
					try {
						selection = (Map<Integer, String>) request.getAttribute("selection");
						Race r = (Race) request.getAttribute("race");
						results = r.getRaceResults();
					} catch(NullPointerException ex) {
						
					}
					showResult(out, results, selection);
				%>
				<c:if test="${not empty errors.msg}">
					<tr>
						<td colspan="2">
							<c:out value="${errors.msg}"></c:out> 
						</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="2">
						<input type="submit" value="Sumbit"/>
						<input type="hidden" name="raceID" value="${race.ID}"/> 
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>