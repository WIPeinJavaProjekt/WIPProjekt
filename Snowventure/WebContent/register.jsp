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
		<link rel="stylesheet" type="text/css" href="CSS/registration.css">
	</head>
	
	<body>
	
		<%@include file = "header.jsp" %> 
	
			<div class="pure-g">
			
				<div class="pure-u-1-5"></div>
	
				<div class="pure-u-3-5" align="center">
					<h1>Registrieren</h1>
					
					<form class="pure-form pure-form-aligned" action="register" method="post">				
					    <fieldset>
					    	<div class="pure-control-group">
					            <label for="username">Benutzername</label>
					            <input id="username" name="username" value="${username}" type="text" required placeholder="Benutzername">
					        </div>
					        <div class="pure-control-group">
					            <label for="name">Vorname</label>
					            <input id="name" name="name" value="${name}" type="text" required placeholder="Vorname">
					        </div>
					        <div class="pure-control-group">
					            <label for="surname">Nachname</label>
					            <input id="surname" name="surname" value="${surname}" type="text" required placeholder="Nachname">
					        </div>
					        
					        <div class="pure-control-group">
					            <label for="location">Wohnort</label>
					            <input id="location" name="location" value="${location}" type="text" required placeholder="Wohnort">
					        </div>
					        <div class="pure-control-group">
					            <label for="street">Straße</label>
					            <input id="street" name="street" value="${street}" type="text" required placeholder="Straße">
					        </div>
					        <div class="pure-control-group">
					            <label for="houseno">Hausnummer</label>
					            <input id="houseno" name="houseno" value="${houseno}" type="text" required placeholder="Hausnummer">
					        </div>
					        <div class="pure-control-group">
					            <label for="postcode">Postleitzahl</label>
					            <input id="postcode" name="postcode" value="${postcode}" type="text" required placeholder="Postleitzahl">
					        </div>
					        
					        <div class="pure-control-group">
					            <label for="email">E-Mail-Addresse</label>
					            <input id="email" name="email" value="${email}" type="email" required placeholder="E-Mail-Addresse">
					        </div>
					
					        <div class="pure-control-group">
					            <label for="password">Passwort</label>
					            <input id="password" name="password" type="password" required placeholder="Passwort">
					        </div>
					        <div class="pure-control-group">
					            <label for="passwordRepeat">Passwort wiederholen</label>
					            <input id="passwordRepeat" name="passwordRepeat" type="password" required placeholder="Passwort wiederholen">
					        </div>			        
					
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
					<form class="pure-form pure-form-aligned" action="register" method="get">
							<p class="error">${error}</p>
					</form>
				</div>	
				
				<div class="pure-u-1-5"></div>
			</div>
	</body>
	
</html>