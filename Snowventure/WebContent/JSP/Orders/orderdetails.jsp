<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Bestelldetails</title>	
	<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="./CSS/default.css">
	<link rel="stylesheet" type="text/css" href="./CSS/shoppingcart.css">
	<link rel="stylesheet" type="text/css" href="./CSS/startpageslider.css">
	
	<script type="text/javascript" src="/Snowventure/JS/jquery-3.2.1.min.js"></script>		
	<script type="text/javascript" src="./JS/header.js"></script>
	<script type="text/javascript" src="./JS/displayImage.js"></script>
	
	<link rel="shortcut icon" href="/favicon.ico">
	<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
	<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
	
	<script src="./JS/jquery-3.2.1.min.js"></script>
    <script src="./JS/header.js"></script>
    
    <style>
    
    #orderitem {
    	width:100%; 
    	border-style: outset; 
    	border-color: gainsboro;
    }
    
    #orderitems-div {
    	margin-bottom: 20px;
    }
    
    #orderdetails {
    	float: left;
    }
    
    #ordervalue {
    	text-align: right;
    }
    
    #orderstatus {
    	color: green;
    }
    
    </style>
    
</head>
<body>
	
	<%@include file = "../Basic/header.jsp" %> 
	
	<div class="contentwrapp">
		<div class="pure-u-1-8"></div>
		
		<c:if test="${ not empty currentUser && not empty selectedOrder }">	
			<div class="pure-u-3-4">
				<div class="pure-u-1-1">
					<div class="pure-u-1-1"><h1>Bestelldetails</h1></div>
					<div class="pure-u-3-4" id="orderdetails">
						<div class="pure-u-1-1">Bestellt am ${ selectedOrder.statuscycle.get(0).statusdate }</div>
						<div class="pure-u-1-1">Bestellnr. ${ selectedOrder.orid }</div>
					</div>
					<div class="pure-u-1-4" id="orderdetails">
						<center>
							<h4>
								<b>Status: 
								<c:choose>
									<c:when test="${ currentUser.utid == '2' }">
										<span id="orderstatus"> ${ selectedOrder.statuscycle.get(selectedOrder.statuscycle.size()-1).description }</span>
									</c:when>
									<c:otherwise>
										<select>
											<c:forEach var="status" items="statuslist">
												<option <c:if test ="${ selectedOrder.statuscycle.get(selectedOrder.statuscycle.size()-1).osid == status.osid }">selected</c:if>>${ status.description }</option>
											</c:forEach>
										</select>
									</c:otherwise>
								</c:choose>
								</b>
							</h4>
						</center></div>
				</div>
				<div class="pure-u-1-1" style="height: 20px;">
				</div>
				<div class="pure-u-1-1">
					<div class="pure-u-3-4" id="orderdetails">
							<div class="pure-u-1-1"><b>Versandadresse</b></div>
							<div class="pure-u-1-1">${ selectedOrder.name + ' ' + selectedOrder.surname }</div>
							<div class="pure-u-1-1">${ selectedOrder.adress.street + ' ' + selectedOrder.adress.houseno}</div>
							<div class="pure-u-1-1">${ selectedOrder.adress.postcode + ' ' + selectedOrder.adress.location}</div>
					</div>
					<div class="pure-u-1-4">
							<div class="pure-u-1-1"><b>Bestellübersicht</b></div>
							<div class="pure-u-2-5">Gesamtsumme</div><div class="pure-u-3-5" id="ordervalue">EUR ${ selectedOrder.shoppingCart.GetShoppingCartPrice() }</div>
					</div>
				</div>
				<div class="pure-u-1-1" style="height: 20px;"></div>
				<c:forEach items="${ selectedOrder.shoppingCart.cart }" var="position">			
				<div class="pure-g" id="orderitems-div">
					<form id="orderitem">
						 <div  id="scp-article-card">
							<div class="pure-u-1-5" id="sc-aimg-container">
								<img class="article-img" src="images/${ position.article.getArticleHeadPicture().getImageId()}" alt="Artikelbild">
							</div>
					    	<div class="pure-u-1-4" id="scp-info-part">
								<h4><b>${ position.article.GetName() }</b></h4>
								<hr size="5">
								<p>Variante: ${ position.article.versions.get(position.article.selectedversion).property }</p>
							</div>
							<div class="pure-u-1-4" id="scp-info-part">
								<p><b>Preis</b></p>
								<p>${ position.article.versions.get(position.article.selectedversion).price } EUR</p>
							</div>
							<div class="pure-u-1-4" id="scp-info-part">
								<p><b>Menge</b></p>
								<p>${ position.amount }</p>
							</div>
							<hr>
						</div>
					</form>
				</div>	
				</c:forEach>		
			</div>	
		</c:if>
		
		
		<c:if test="${ empty selectedOrder }">
			<div class="pure-u-3-4">
				<div class="pure-u-1-1">
					<div class="pure-u-1-1"><h1>Bestelldetails</h1></div>
					<div class="pure-u-3-4" style="float:left;">
						<div class="pure-u-1-1">Bestellt am 26.03.2017</div>
						<div class="pure-u-1-1">Bestellnr. 21545342326</div>
					</div>
					<div class="pure-u-1-4" style="float:left;"><center><h4><b>Status: <span style="color: green;">Abgeschlossen</span></b></h4></center></div>
				</div>
				<div class="pure-u-1-1" style="height: 20px;">
				</div>
				<div class="pure-u-1-1">
					<div class="pure-u-3-4" style="float:left;">
							<div class="pure-u-1-1"><b>Versandadresse</b></div>
							<div class="pure-u-1-1">Jacob Markus</div>
							<div class="pure-u-1-1">Dorfstr. 3</div>
							<div class="pure-u-1-1">23923 Retelsdorf</div>
					</div>
					<div class="pure-u-1-4">
							<div class="pure-u-1-1"><b>Bestellübersicht</b></div>
							<div class="pure-u-2-5">Gesamtsumme</div><div class="pure-u-3-5" style="text-align: right">EUR 19,95</div>
					</div>
				</div>
				<div class="pure-u-1-1" style="height: 20px;"></div>
				<div class="pure-g" style="margin-bottom: 20px;">
					<form style="width:100%; border-style: outset; border-color: gainsboro;">
						 <div  id="scp-article-card">
							<div class="pure-u-1-5" style="float:left; max-height: 140px">
								<img class="article-img" src="./Images/Brille_schwarz.jpg" alt="pic">
							</div>
					    	<div class="pure-u-1-4" id="scp-info-part">
								<h4><b>Dieser Artikel ist schön</b></h4>
								<hr size="5">
								<p>Variante: XL</p>
							</div>
							<div class="pure-u-1-4" id="scp-info-part">
								<p><b>Preis</b></p>
								<p>19,95 EUR</p>
							</div>
							<div class="pure-u-1-4" id="scp-info-part">
								<p><b>Menge</b></p>
								<p>1</p>
							</div>
							<hr>
						</div>
					</form>
				</div>			
			</div>
		</c:if>
		<div class="pure-u-1-8"></div>
	</div>
	
	<%@include file = "../Basic/footer.jsp" %>
</body>
</html>