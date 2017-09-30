<%@page import="ua.training.project4.model.entities.Race"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@ taglib uri="/WEB-INF/mytags.tld" prefix="mm" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Administrator</title>
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

	   ul {
			list-style-type: none;
		}
		a {
			color: black;
			text-decoration: none;
		}
		a:hover {
			color: white;
		    background-color: black;
		    cursor: pointer;
		}
	</style>
</head>
<body>
		
		<mm:today/>
		
		<div class="container">
			<div class="topDiv">
				<div class="topBar left"></div>					
				
				<div class="topBar right">
					<form action="/app/administrator" method="get" enctype="application/x-www-form-urlencoded">
						<table style="border: solid; border-color: black; float: right;">
							<tr>
								<td>
									<c:if test="${lang eq 'en'}">
										<c:set value="selected=\"\"" var="selectedEn"></c:set>
									</c:if>
									<c:if test="${lang eq 'ru'}">
										<c:set value="selected=\"\"" var="selectedRu"></c:set>
									</c:if>
									<select name="new_lang">
			  							<option value="en" ${selectedEn}>English(US)</option>
			  							<option value="ru" ${selectedRu}>Russian</option>
									</select> 
								</td>
								<td>
									<input type="submit" value="Change">
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
						
	<div class="content">
		<h2>Races:</h2>
		<h4><a href="/app/administrator/new">Create a new race</a></h4>
		<table border="1"  class="tableCenter">
			<tr>
				<th>Distance:</th>
				<th>Results:</th>
				<th>Coefficients set:</th>
				<th>State:</th>
				<th>Date:</th>
				<th>Action:</th>
			</tr>
			<c:forEach items="${races}" var="race">	
				<tr>
					<td>${race.distance.getDistance()}</td>
					<td> 
						<c:if test = "${race.state == 'FINISHED'}">
							first<br>
						    second<br>
						    third<br>
				      	</c:if>
				    </td>
				    <td>
				    	false
				    </td>
      				<td>${race.state}</td>
      				<td>${race.date}</td>
      				<td>
      					<script type="text/javascript">
							function action(value)
							{
								var form = document.getElementById("actionForm");
								var input = document.getElementById("raceID");
								var values = value.split(":");
								var urlPart = values[0];
								var raceid = values[1];
								/*GET for edit and set results*/
								if (urlPart == "/app/administrator/edit" || urlPart == "/app/administrator/results") {
									location.assign(urlPart + "?raceID=" + raceid);
									return;
								}
								form.action = urlPart; 
								input.value = raceid;
								form.submit();
							}
						</script>
						<select onchange="action(this.value);">
							<option value="">None</option>
						<%-- <c:if test="${race.state eq 'PLANNED'}"> --%>
								<option value="/app/administrator/start:${race.ID}">Start</option>
								<option value="/app/administrator/finish:${race.ID}">Finish</option>
								<option value="/app/administrator/edit:${race.ID}">Edit</option>
								<option value="/app/administrator/delete:${race.ID}">Delete</option>
								<option value="/app/administrator/results:${race.ID}">Set results</option>
						<%--</c:if> --%>
						</select>
      				</td>
				</tr>
			</c:forEach>
		</table>
		<!-- Form to send race action -->
		<form action=""  accept-charset="UTF-8" method="post" name="actionForm" id="actionForm">
			<input id="raceID" type="hidden" name="raceID" value=""/>
		</form>
	</div>
	</div>
</body>
</html>