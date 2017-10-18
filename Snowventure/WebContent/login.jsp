<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
		<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="CSS/default.css">
	</head>
	<body>	
		<%@include file = "header.jsp" %>
		
		<div class="pure-g">
			
				<div class="pure-u-1-5"></div>
	
				<div class="pure-u-3-5" align="center">
					<h1>Einloggen</h1>
	
					<form class="pure-form" action ="login" method="post">
				       	<fieldset class="pure-group">
					        <div class="pure-control-group">
					            <input name="userId" id="userId" type="text" placeholder="Benutzername" required>
					        </div>					
					        <div class="pure-control-group">
					            <input name="password" id="password" type="text" placeholder="Passwort" required>
					        </div>
				        </fieldset>
						<fieldset class="pure-group">
					        <div class="pure-controls">
					            <button type="submit" name="login" class="pure-button pure-button-primary">Einloggen</button>
					        </div>
				        </fieldset>
					</form>
					<form class="pure-form" action ="login" method="post">
				            <button type="submit" name="register" class="pure-button pure-button-primary">Registrieren</button>
				            <button type="submit" name="back" class="pure-button pure-button-primary">Zurück</button>
					</form>
				</div>
				
				<div class="pure-u-1-5"></div>
	</body>
</html>