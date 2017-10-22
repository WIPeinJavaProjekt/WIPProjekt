<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		
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
	</head>
	
	<body>
	
		<%@include file = "../Basic/header.jsp" %> 
	
		<div class="pure-g" id="loginbox">
		
			<div class="pure-u-1-5"></div>
<div class="pure-u-3-5">
				<h1>Registrieren</h1>
				
				<form class="pure-form" action="register" method="post">
				    <div class="pure-g">
				    <div class ="pure-u-1-2 leftdiv">
					    <fieldset class="pure-group">								    
					    	<label>Name</label>							    	
					    	<div class="pure-control-group">
					            <input class="boxedinput" id="username"  name="username" value="${username}" type="text" required placeholder="Benutzername">
					        </div>					        
					        <div class="pure-control-group">
					            <input class="boxedinput" id="name" name="name" value="${name}" type="text" required placeholder="Vorname">
					        </div>
					        <div class="pure-control-group">
					            <input class="boxedinput" id="surname" name="surname" value="${surname}" type="text" required placeholder="Nachname">
					        </div>					        
					        <div class="pure-control-group">
					            <input class="boxedinput" id="email" name="email" value="${email}" type="email" required placeholder="E-Mail-Addresse">
					        </div>
				        </fieldset>
				        

			        
			       </div> 
			       <div class ="pure-u-1-2"> 
				        <fieldset class="pure-group rightdiv">
				        	<label>Passwort</label>				
					        <div class="pure-control-group">
					            <input class="boxedinput" id="password" name="password" type="password" required placeholder="Passwort">
					        </div>
					        <div class="pure-control-group">
					            <input class="boxedinput" id="passwordRepeat" name="passwordRepeat" type="password" required placeholder="Passwort wiederholen">
					        </div>
				        </fieldset>
	

				    </div>
				
				</div>
				
				<div class="pure-g">
				    <div class ="pure-u-1-2 leftdiv">
					   	<fieldset class="pure-group"> 
				       		<label>Anschrift</label>
					        <div class="pure-control-group">
					            <input class="boxedinput" id="location" name="location" value="${location}" type="text" required placeholder="Wohnort">
					        </div>
					        <div class="pure-control-group">
					            <input class="boxedinput" id="street" name="street" value="${street}" type="text" required placeholder="Straße">
					        </div>
					        <div class="pure-control-group">
					            <input class="boxedinput" id="houseno" name="houseno" value="${houseno}" type="text" required placeholder="Hausnummer">
					        </div>
					        <div class="pure-control-group">
					            <input class="boxedinput" id="postcode" name="postcode" value="${postcode}" type="text" required placeholder="Postleitzahl">
					        </div>
					    </fieldset>
			        	<fieldset>
						<div class="pure-control-group">
				            <button class="pure-button pure-button-primary boxedinput" type="submit" name="submitRegister" class="pure-button pure-button-primary">Abschicken</button>
				        </div>
			        	</fieldset>
					</div>
					<div class ="pure-u-1-2 rightdiv">
						<fieldset class="pure-group">
				        	<label>Sicherheitsfrage</label>			        
					        <div class="pure-control-group">
					            <select class=" boxedinput" id="safetyQuestion" name="safetyQuestion" required>
					            	 <option value="">Bitte Sicherheitsfrage auswählen</option>
								    <c:forEach items="${squestions}" var="squestion">
								        <option value="${squestion.getId()}">${squestion.getQuestion()}</option>
								    </c:forEach>
								</select>
					        </div>
					        <div class="pure-control-group">
					            <input class="boxedinput" id="safetyAnswer" name="safetyAnswer" type="text" required placeholder="Antwort">
					        </div>
				        </fieldset>
					</div>
				</div>
				
				
				
				
				</form>
				
				<div class="pure-g">
					<div class ="pure-u-1-2 leftdiv">
					<form class="pure-form pure-form-aligned" action="register" method="post">
						<div class="pure-control-group">
							<button class="pure-button pure-button-primary boxedinput" name="back" class="pure-button pure-button-primary">Zurück</button>
						</div>
					</form>
					</div>
					<div class ="pure-u-1-2"></div>
				</div>
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