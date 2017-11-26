<!-- 
Beschreibung: 
Ansprechpartner:
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<c:choose>
			<c:when test="${currentUser.utid == '1'}">
				<title>Neuer Nutzer</title>
			</c:when>
			<c:otherwise>
				<title>Registrierung</title>
			</c:otherwise>
		</c:choose>
		
		<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="./CSS/default.css">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
		
		<script src="./JS/jquery-3.2.1.min.js"></script>
    	<script src="./JS/header.js"></script>		
	</head>
	
	<body>
	
		<%@include file = "../Basic/header.jsp" %>
	
		<div class="pure-g  textbox" id="loginbox">
		
			<div class="pure-u-1-5 sizezero"></div>
			<div class="pure-u-3-5 fullwidth">
				<c:choose>
					<c:when test="${currentUser.utid == '1'}">
						<h1>Neuen Nutzer anlegen</h1>
					</c:when>
					<c:otherwise>
						<h1>Registrieren</h1>
					</c:otherwise>
				</c:choose>
				
				<%@include file = "userinfo.jsp" %>
				
				<div class="pure-g">
					<div class ="pure-u-1-2 leftdiv fullwidth">
					<c:if test="${empty currentUser}">
						<form class="pure-form pure-form-aligned" action="register" method="post">
							<div class="pure-control-group">
								<button class="pure-button pure-button-primary boxedinput" name="back" class="pure-button pure-button-primary">Zurück</button>
							</div>
						</form>
					</c:if>
					<c:if test="${not empty currentUser && currentUser.utid == '1'}">
						<form class="pure-form pure-form-aligned" action="users?page=usersearch" method="post">
							<div class="pure-control-group">
								<button class="pure-button pure-button-primary boxedinput" name="back" class="pure-button pure-button-primary">Zurück</button>
							</div>
						</form>
					</c:if>		
					</div>
					<div class ="pure-u-1-2 fullwidth"></div>
				</div>
				<c:if test="${not empty error}">
					<form class="pure-form pure-form-aligned" action="register" method="get">
							<p class="error">${error}</p>
					</form>
				</c:if>
		   
			</div>
			<div class="pure-u-1-5 sizezero"></div>
		</div>
		
		<%@include file = "../Basic/footer.jsp" %>
	</body>
	
</html>