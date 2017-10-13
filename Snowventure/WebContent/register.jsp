<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Ski-Shop24</title>
		<!-- <link type="text/css" rel="stylesheet" href="./css/Start.css" /> -->
		<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="CSS/default.css">
	</head>
	
	<body>
	
		<%@include file = "header.jsp" %> 
	
			<div class="pure-g">
			
				<div class="pure-u-2-5"></div>
	
				<div class="pure-u-2-5">
					<h1>Register</h1>
					
					<form class="pure-form pure-form-aligned" action="register" method="post">				
					    <fieldset>
					    	<div class="pure-control-group">
					            <label for="username">Benutzername</label>
					            <input id="username" type="text" placeholder="Benutzername">
					        </div>
					        <div class="pure-control-group">
					            <label for="name">Vorname</label>
					            <input id="name" type="text" placeholder="Vorname">
					        </div>
					        <div class="pure-control-group">
					            <label for="surname">Nachname</label>
					            <input id="surname" type="text" placeholder="Nachname">
					        </div>
					        
					        <div class="pure-control-group">
					            <label for="location">Wohnort</label>
					            <input id="location" type="text" placeholder="Wohnort">
					        </div>
					        <div class="pure-control-group">
					            <label for="street">Straße</label>
					            <input id="street" type="text" placeholder="Straße">
					        </div>
					        <div class="pure-control-group">
					            <label for="houseno">Hausnummer</label>
					            <input id="houseno" type="text" placeholder="Hausnummer">
					        </div>
					        <div class="pure-control-group">
					            <label for="postcode">Postleitzahl</label>
					            <input id="postcode" type="text" placeholder="Postleitzahl">
					        </div>
					        
					        <div class="pure-control-group">
					            <label for="email">E-Mail-Addresse</label>
					            <input id="email" type="email" placeholder="E-Mail-Addresse">
					        </div>
					
					        <div class="pure-control-group">
					            <label for="password">Passwort</label>
					            <input id="password" type="password" placeholder="Passwort">
					        </div>
					        <div class="pure-control-group">
					            <label for="passwordRepeat">Passwort wiederholen</label>
					            <input id="passwordRepeat" type="password" placeholder="Passwort wiederholen">
					        </div>			        
					
					        <div class="pure-control-group">
					            <label for="cb" class="pure-checkbox">
					                <input id="cb" type="checkbox"> I've read the terms and conditions
					            </label>
							</div>
							<div class="pure-control-group">
					            <button type="submit" name="submitRegister" class="pure-button pure-button-primary">Abschicken</button>
					            <button type="submit" name="back" class="pure-button pure-button-primary">Zurück</button>
					        </div>
					    </fieldset>				
					</form>
				</div>
				
				<div class="pure-u-1-5"></div>
			</div>
	</body>
	
</html>