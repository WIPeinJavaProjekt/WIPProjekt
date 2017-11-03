<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>Snowventure Einkaufswagen</title>
	
	<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="./CSS/default.css">
	<link rel="stylesheet" type="text/css" href="./CSS/w3.css">
	
	<script src="/Snowventure/JS/jquery-3.2.1.min.js"></script>		
	<script type="text/javascript" src="./JS/header.js"></script>
	
	<link rel="shortcut icon" href="/favicon.ico">
	<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
	<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
</head>
<body>

	<%@include file = "../Basic/header.jsp" %>
	
		<div class="pure-g">
			<form style="width:100%">
				<fieldset class="pure-group">
					<legend>
						<h1>Einkaufswagen</h1>
					</legend>
					<div class="pure-u-1-1">
					
					<c:choose>
					<c:when test="${not empty currentUser && currentUser.shoppingcart != null && currentUser.shoppingcart.cart != null && currentUser.shoppingcart.cart.size() > 1}">
					<c:forEach var="position" items="currentUser.shoppingcart.cart">
						<div class="w3-card-4" style="width:100%">
							<div class="pure-u-1-5" style="float:left">
								<img class="article-img" src="./Images/Brille_schwarz.jpg" style="max-width: 100%; max-height: 175px" alt="pic">
							</div>
					    	<div class="pure-u-1-4" style="height: 175px; text-align:center">
								<h4><b>${ position.article.GetName() }</b></h4>
								<hr size="5">
								<p>Variante: ${ position.article.selectedversion }</p>
							</div>
							<div class="pure-u-1-4" style="height: 175px; text-align:center">
								<p><b>Preis</b></p>
								<p>${ position.article.getPrice() }</p>
							</div>
							<div class="pure-u-1-4" style="height: 175px; text-align: center">
								<p><b>Menge</b></p>
								<select>
									<c:forEach var="counter" begin="1" end="10">
										<option <c:if test="${ position.amount == counter }">selected</c:if> value="${counter}">${counter}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="w3-card-4" style="width:100%; background-color: #FFF">
							<h2><b>Ihre Einkaufswagen ist leer.</b></h2>
							<h4>Ihr Einkaufswagen steht zu Ihrer Verfügung. Nutzen Sie ihn und befüllen Sie ihn mit Ski-Kleidung, Ausrüstung und mehr. 
								Setzen Sie den Einkauf bei <a href="/Snowventure/articles">Snowventure</a> fort.</h4>
						</div>
					</c:otherwise>
					</c:choose>					
					</div>
					
					
					<div class="w3-card-4" style="width:100%">
						<div class="pure-u-1-5" style="float:left">
							<img class="article-img" src="./Images/Brille_schwarz.jpg" style="max-width: 100%; max-height: 175px" alt="pic">
						</div>
				    	<div class="pure-u-1-4" style="height: 175px; text-align:center">
							<h4><b>Dieser Artikel ist schön</b></h4>
							<hr size="5">
							<p>Variante: XL</p>
						</div>
						<div class="pure-u-1-4" style="height: 175px; text-align:center">
							<p><b>Preis</b></p>
							<p>19.95 EUR</p>
						</div>
						<div class="pure-u-1-4" style="height: 175px; text-align: center">
							<p><b>Menge</b></p>
							<select>
								<c:forEach var="counter" begin="1" end="10">
									<option value="${counter}">${counter}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
				</fieldset>
				<c:if test="${not empty currentUser && currentUser.shoppingcart != null}">
				<input class="pure-button pure-button-primary" type="submit" value="Kauf abwickeln">
				<input class="pure-button pure-button-primary" type="submit" value="Warenkorb löschen">
				</c:if>
			</form>		
		</div>
	
	<%@include file = "../Basic/footer.jsp"%>
</body>
</html>