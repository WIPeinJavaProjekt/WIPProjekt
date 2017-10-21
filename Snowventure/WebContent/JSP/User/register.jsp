<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		
		<title>Registrierung</title>
		
		<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="./CSS/default.css">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
		
		<script src="./JS/jquery-3.2.1.min.js"></script>
    	<script src="./JS/header.js"></script>
		
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	</head>
	
	<body>
	
		<%@include file = "../Basic/header.jsp" %> 
	
		<div class="pure-g">
		
			<div class="pure-u-1-5"></div>

			<div class="pure-u-3-5" align="center">
				<h1>Registrieren</h1>
				
				<form class="pure-form" action="register" method="post">
				    <fieldset class="pure-group">								    
				    	<label>Name</label>							    	
				    	<div class="pure-control-group">
				            <input id="username" name="username" value="${username}" type="text" required placeholder="Benutzername">
				        </div>					        
				        <div class="pure-control-group">
				            <input id="name" name="name" value="${name}" type="text" required placeholder="Vorname">
				        </div>
				        <div class="pure-control-group">
				            <input id="surname" name="surname" value="${surname}" type="text" required placeholder="Nachname">
				        </div>					        
				        <div class="pure-control-group">
				            <input id="email" name="email" value="${email}" type="email" required placeholder="E-Mail-Addresse">
				        </div>
			        </fieldset>
			        <fieldset class="pure-group">
			        	<label>Passwort</label>				
				        <div class="pure-control-group">
				            <input id="password" name="password" type="password" required placeholder="Passwort">
				        </div>
				        <div class="pure-control-group">
				            <input id="passwordRepeat" name="passwordRepeat" type="password" required placeholder="Passwort wiederholen">
				        </div>
			        </fieldset>
		        	<fieldset class="pure-group"> 
			       		<label>Anschrift</label>
				        <div class="pure-control-group">
				            <input id="location" name="location" value="${location}" type="text" required placeholder="Wohnort">
				        </div>
				        <div class="pure-control-group">
				            <input id="street" name="street" value="${street}" type="text" required placeholder="Straße">
				        </div>
				        <div class="pure-control-group">
				            <input id="houseno" name="houseno" value="${houseno}" type="text" required placeholder="Hausnummer">
				        </div>
				        <div class="pure-control-group">
				            <input id="postcode" name="postcode" value="${postcode}" type="text" required placeholder="Postleitzahl">
				        </div>
				    </fieldset>
				    <fieldset class="pure-group">
			        	<label>Sicherheitsfrage</label>			        
				        <div class="pure-control-group">
				            <select id="safetyQuestion" name="safetyQuestion" required>
				            	 <option value="">Bitte Sicherheitsfrage auswählen</option>
							    <c:forEach items="${squestions}" var="squestion">
							        <option value="${squestion.getId()}">${squestion.getQuestion()}</option>
							    </c:forEach>
							</select>
				        </div>
				        <div class="pure-control-group">
				            <input id="safetyAnswer" name="safetyAnswer" type="text" required placeholder="Antwort">
				        </div>
			        </fieldset>
					<fieldset>
						<div class="pure-control-group">
				            <button type="submit" name="submitRegister" class="pure-button pure-button-primary">Abschicken</button>
				        </div>
			        </fieldset>
				    
				</form>
				<form class="pure-form pure-form-aligned" action="register" method="post">
					<div class="pure-control-group">
						<button name="back" class="pure-button pure-button-primary">Zurück</button>
					</div>
				</form>
				<c:if test="${not empty error}">
					<form class="pure-form pure-form-aligned" action="register" method="get">
							<p class="error">${error}</p>
					</form>
				</c:if>
			</div>	
			
			<div class="pure-u-1-5"></div>
		</div>
		
		<%@include file = "../Basic/footer.jsp" %>
	</body>
	
</html>