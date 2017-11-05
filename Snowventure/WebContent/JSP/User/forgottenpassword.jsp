<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="./CSS/default.css">
	
	<link rel="shortcut icon" href="/favicon.ico">
	<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
	<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
	
	<script src="./JS/jquery-3.2.1.min.js"></script>
   	<script src="./JS/header.js"></script>	
	<title>Passwort zurücksetzen</title>
</head>
<body>
	<%@include file = "../Basic/header.jsp" %> 
		
	<div class="pure-g" id="loginbox">
		<div class="pure-u-1-5"></div>
			<div class="pure-u-3-5">
			
				<h1>Passwort zurücksetzen</h1>
		
					
						<form class="pure-form pure-form-aligned" method="post" action="forgottenPassword">
							<fieldset class="pure-group">
							    <div class="pure-control-group">
									<input class="boxedinput" id="username" name="username" type="text" required placeholder="Nutzername" value="${userpw.username}" <c:if test="${ not empty userpw }">readonly</c:if>>					
									<div class="pure-control-group">
								       <button class="pure-button pure-button-primary boxedinput" type="submit" name="confirmusername" class="pure-button pure-button-primary" <c:if test="${ not empty userpw }">style="display:none;"</c:if> >Bestätigen</button>
								    </div>
							    </div>
					        </fieldset>    
						</form>
						<c:if test="${ not empty userpw && correctAnswer eq 'true' }">
						<form class="pure-form pure-form-aligned" method="post" action="#">
							<fieldset class="pure-group">
						        <div class="pure-control-group">
						            <input class="boxedinput" id="newpassword" name="newpassword" type="password" required placeholder="Neues Passwort">
						            <input class="boxedinput" id="newpasswordrepeat" name="newpasswordrepeat" type="password" required placeholder="Neues Passwort wiederholen">
						            <button class="pure-button pure-button-primary boxedinput" type="submit" name="submitNewPassword" class="pure-button pure-button-primary">Speichern</button>
						        </div>
					       	</fieldset>
					    </form>
						</c:if>
						<c:if test="${ not empty userpw && empty correctAnswer}">
						<form class="pure-form pure-form-aligned" method="post" action="#">
							<fieldset class="pure-group">
						        <div class="pure-control-group">
						            <input class="boxedinput" id="safetyQuestion" name="safetyQuestion" type="text" readonly value="${ userpw.squestion.getQuestion() }">
						            <input class="boxedinput" id="safetyAnswer" name="safetyAnswer" type="text" required placeholder="Antwort eingeben">
						        	<div class="pure-control-group">
						       			<button class="pure-button pure-button-primary boxedinput" type="submit" name="confirmanswer" class="pure-button pure-button-primary">Bestätigen</button>
						    		</div>     
						        </div>
					        </fieldset>
				        </form>
						</c:if>											
					
					<form class="pure-form pure-form-aligned" action="forgottenPassword" method="post">
						<div class="pure-control-group">
							<button class="pure-button pure-button-primary boxedinput" name="back" class="pure-button pure-button-primary">Zurück</button>
						</div>
					</form>
				</div>						
			
		<div class="pure-u-1-5"></div>
	</div>

	<%@include file = "../Basic/footer.jsp" %>
</body>
</html>