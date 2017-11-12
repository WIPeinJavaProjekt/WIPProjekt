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
	<link rel="stylesheet" type="text/css" href="./CSS/shoppingcart.css">
	
	<script src="/Snowventure/JS/jquery-3.2.1.min.js"></script>		
	<script type="text/javascript" src="./JS/header.js"></script>
	
	<link rel="shortcut icon" href="/favicon.ico">
	<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
	<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
</head>
<body>

	<%@include file = "../Basic/header.jsp" %>
	
		<div class="pure-g">
			<form action="cart" method="post" style="width:100%">
				
					<div class="pure-u-1-1">
					
					<c:choose>
					<c:when test="${not empty currentUser || not empty currentCart}">
						<c:forEach var="position" items="${ not empty currentUser ? currentUser.shoppingcart.cartPositions : currentCart.cartPositions}">
							<div class="w3-card-4" id="scp-article-card">
								<div class="pure-u-1-5" id="sc-aimg-container">
									<img class="article-img" src="images/${ position.article.getArticleHeadPicture().getImageId()}" alt="Artikelbild">
								</div>
						    	<div class="pure-u-1-4" id="scp-info-part">
									<h4><b>${ position.article.GetName() }</b></h4>
									<hr size="5">
									<p>Farbe: ${ position.color.GetColorName() }</p>
									<p>Variante: ${ position.size }</p>
								</div>
								<div class="pure-u-1-4" id="scp-info-part">
									<p><b>Preis</b></p>
									<p>${ position.article.getPrice() }</p>
								</div>
								<div class="pure-u-1-4" id="scp-info-part">
									<p><b>Menge</b></p>
									<select onchange="location.href='./cart?scpid=${currentUser.shoppingcart.cartPositions.indexOf(position)}&amount=' + jQuery('#amount option:selected').val();">
										<c:forEach var="counter" begin="1" end="10">
											<option <c:if test="${ position.amount == counter }">selected</c:if> value="${counter}">${counter}</option>
										</c:forEach>
									</select>
									<p><a href="cart?scpid=${ currentUser.shoppingcart.cartPositions.indexOf(position) }&option=delete">Löschen</a></p>
								</div>
								<hr>
							</div>
						</c:forEach>
						
						<div  id="scp-article-card">
						
					    	<div class="pure-u-11-12" id="sc-conclusion">
						    	<h3><b>Summe (${currentUser.shoppingcart.cartPositions.GetArticleCount()} Artikel):  EUR ${currentUser.shoppingcart.cartPositions.GetShoppingCartPrice()}</b></h3>
				    			<h4><input class="pure-button pure-button-primary" type="submit" value="Jetzt Bestellen"></h4>
							</div>
							<div class="pure-u-1-12" style="height: 15%;"></div>
						</div>
					</c:when>
					<c:otherwise>
						
						<div  id="scp-article-card">
							<h2><b>Ihre Einkaufswagen ist leer.</b></h2>
							<h4>Ihr Einkaufswagen steht zu Ihrer Verfügung. Nutzen Sie ihn und befüllen Sie ihn mit Ski-Kleidung, Ausrüstung und mehr.
								<c:if test="${empty currentUser}"><br>Wenn Sie bereits ein Konto besitzen, tippen Sie auf <a href="./login">Anmelden</a>, um den vollen Funktionsumfang von Snowventure nutzen zu können.<br></c:if>  
								Setzen Sie den Einkauf bei <a href="./articles">Snowventure</a> fort.</h4>
						</div>
					</c:otherwise>
					</c:choose>					
					</div>
										
					
					<div  id="scp-article-card">
						<div class="pure-u-1-5" style="float:left; max-height: 140px">
							<img class="article-img" src="./Images/Brille_schwarz.jpg" alt="pic">
						</div>
				    	<div class="pure-u-1-4" id="scp-info-part">
							<h4><b>Dieser Artikel ist schön</b></h4>
							<hr size="5">
							<p>Farbe: Rot</p>
							<p>Variante: XL</p>
						</div>
						<div class="pure-u-1-4" id="scp-info-part">
							<p><b>Preis</b></p>
							<p>19,95 EUR</p>
						</div>
						<div class="pure-u-1-4" id="scp-info-part">
							<p><b>Menge</b></p>
							<select id="amount" onchange="location.href='./cart?scpid=1&amount=' + jQuery('#amount option:selected').val();">
								<c:forEach var="counter" begin="1" end="10">
									<option value="${counter}">${counter}</option>
								</c:forEach>
							</select>
							<p><a href="cart?scpid=1&option=delete">Löschen</a></p>
						</div>
						<hr>
					</div>
					<div  id="scp-article-card">
				    	<div class="pure-u-11-12" id="sc-conclusion">
				    	<h3><b>Summe (1 Artikel): EUR 19,95</b></h3>
				    	<h4><input class="pure-button pure-button-primary" type="submit" value="Jetzt Bestellen"></h4>
						</div>
						<div class="pure-u-1-12" style="height: 15%;"></div>
					</div>
			</form>		
		</div>
	<%@include file = "../Basic/footer.jsp"%>
</body>
</html>