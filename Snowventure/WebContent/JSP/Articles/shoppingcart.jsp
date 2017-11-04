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
	
	<style>
		
		#scp-info-part{
			height: 20%; 
			text-align: center;
		}
	
		#sc-conclusion{
			height: 15%; 
			text-align: right; 
			align: right;
		}
		
		.article-img{
			width: 80%; 
			height: 100%; 
			max-height: 140px;
		}
		
		#sc-aimg-container{
			float:left; 
			max-height: 140px;
		}
		
		#scp-article-card{
			width: 100%;
		}
	
	</style>
	
	
</head>
<body>

	<%@include file = "../Basic/header.jsp" %>
	<div class="contentwrapp">
		<div class="pure-g">
			<form action="cart" method="post" style="width:100%">
				<fieldset class="pure-group">
					<legend>
						<h1>Einkaufswagen</h1>
					</legend>
					<div class="pure-u-1-1">
					
					<c:choose>
					<c:when test="${not empty currentUser && currentUser.shoppingcart != null && currentUser.shoppingcart.cart != null && currentUser.shoppingcart.cart.size() > 1}">
						<c:forEach var="position" items="currentUser.shoppingcart.cart">
							<div class="w3-card-4" id="scp-article-card">
								<div class="pure-u-1-5" id="sc-aimg-container">
									<img class="article-img" src="./Images/Brille_schwarz.jpg" alt="pic">
								</div>
						    	<div class="pure-u-1-4" id="scp-info-part">
									<h4><b>${ position.article.GetName() }</b></h4>
									<hr size="5">
									<p>Variante: ${ position.article.selectedversion }</p>
									<p><a href="cart?scpid=${ position.article.GetSelectedVersion() }&option=delete">Löschen</a></p>
								</div>
								<div class="pure-u-1-4" id="scp-info-part">
									<p><b>Preis</b></p>
									<p>${ position.article.getPrice() }</p>
								</div>
								<div class="pure-u-1-4" id="scp-info-part">
									<p><b>Menge</b></p>
									<select>
										<c:forEach var="counter" begin="1" end="10">
											<option <c:if test="${ position.amount == counter }">selected</c:if> value="${counter}">${counter}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</c:forEach>
						
						<div class="w3-card-4" id="scp-article-card">
					    	<div class="pure-u-11-12" id="sc-conclusion">
						    	<h3><b>Summe (${currentUser.shoppingcart.cart.GetArticleCount()} Artikel):  EUR ${currentUser.shoppingcart.cart.GetShoppingCartPrice()}</b></h3>
				    			<h4><input class="pure-button pure-button-primary" type="submit" value="zur Kasse gehen"></h4>
							</div>
							<div class="pure-u-1-12" style="height: 15%;"></div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="w3-card-4" id="scp-article-card">
							<h2><b>Ihre Einkaufswagen ist leer.</b></h2>
							<h4>Ihr Einkaufswagen steht zu Ihrer Verfügung. Nutzen Sie ihn und befüllen Sie ihn mit Ski-Kleidung, Ausrüstung und mehr.
								<c:if test="${empty currentUser}"><br>Wenn Sie bereits ein Konto besitzen, tippen Sie auf <a href="/Snowventure/login">Anmelden</a>, um den vollen Funktionsumfang von Snowventure nutzen zu können.<br></c:if>  
								Setzen Sie den Einkauf bei <a href="/Snowventure/articles">Snowventure</a> fort.</h4>
						</div>
					</c:otherwise>
					</c:choose>					
					</div>
										
					
					<div class="w3-card-4" id="scp-article-card">
						<div class="pure-u-1-5" style="float:left; max-height: 140px">
							<img class="article-img" src="./Images/Brille_schwarz.jpg" style="width: 80%; height: 100%; max-height: 140px" alt="pic">
						</div>
				    	<div class="pure-u-1-4" id="scp-info-part">
							<h4><b>Dieser Artikel ist schön</b></h4>
							<hr size="5">
							<p>Variante: XL</p>
							<p><a href="cart?Aid=1&option=delete">Löschen</a></p>
						</div>
						<div class="pure-u-1-4" id="scp-info-part">
							<p><b>Preis</b></p>
							<p>19.95 EUR</p>
						</div>
						<div class="pure-u-1-4" id="scp-info-part">
							<p><b>Menge</b></p>
							<select>
								<c:forEach var="counter" begin="1" end="10">
									<option value="${counter}">${counter}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="w3-card-4" id="scp-article-card">
				    	<div class="pure-u-11-12" id="sc-conclusion">
				    	<h3><b>Summe (2 Artikel): EUR 50,34</b></h3>
				    	<h4><input class="pure-button pure-button-primary" type="submit" value="zur Kasse gehen"></h4>
						</div>
						<div class="pure-u-1-12" style="height: 15%;"></div>
					</div>
				</fieldset>
			</form>		
		</div>
		</div>
	<%@include file = "../Basic/footer.jsp"%>
</body>
</html>