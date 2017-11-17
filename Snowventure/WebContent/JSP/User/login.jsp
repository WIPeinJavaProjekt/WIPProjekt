<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
		<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="./CSS/default.css">
        <link rel="stylesheet" type="text/css" href="./CSS/startpageslider.css">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
	</head>
	<body>
	<script src="./JS/jquery-3.2.1.min.js"></script>
    <script src="./JS/header.js"></script>	
		<%@include file = "../Basic/header.jsp" %>
		
		<div class="pure-g" id="loginbox">
			
			<div class="pure-u-1-5 sizezero"></div>

			<div class="pure-u-3-5 fullwidth">
				<h1>Anmelden</h1>

				<form class="pure-form" action ="login" method="post">
			       	<fieldset class="pure-group">
				        <div class="pure-control-group">
				            <input class="loginfield" name="userId" id="userId" type="text" placeholder="Benutzername" required>
				        </div>					
				        <div class="pure-control-group">
				            <input class="loginfield" name="password" id="password" type="password" placeholder="Passwort" required>
				        </div>
			        </fieldset>
					<fieldset class="pure-group">
				        <div class="pure-controls">
				            <button class="loginfield pure-button pure-button-primary" type="submit" name="login" class="pure-button pure-button-primary">Anmelden</button>
				        </div>
			        </fieldset>
				</form>
				
				<a name="passwordforgotten" href="forgottenPassword?param=login">Passwort vergessen?</a> 
					
				<form class="pure-form" action ="login" method="post">
					<fieldset>
			            <button class="loginfield-2 pure-button pure-button-primary" type="submit" name="register" class="pure-button pure-button-primary">Registrieren</button>
			            <button class="loginfield-2 pure-button pure-button-primary" type="submit" name="back" class="pure-button pure-button-primary">Zurück</button>
		            </fieldset>
				</form>
			</div>
			
			<div class="pure-u-1-5 sizezero"></div>
		</div>
		<%@include file = "../Basic/footer.jsp" %>
	</body>
</html>