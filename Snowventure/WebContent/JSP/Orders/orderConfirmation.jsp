<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Bestätigung</title>	
	<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="./CSS/default.css">
	<link rel="stylesheet" type="text/css" href="./CSS/startpageslider.css">
	
	<script type="text/javascript" src="/Snowventure/JS/jquery-3.2.1.min.js"></script>		
	<script type="text/javascript" src="./JS/header.js"></script>
	<script type="text/javascript" src="./JS/displayImage.js"></script>
	
	<link rel="shortcut icon" href="/favicon.ico">
	<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
	<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
	
	<script src="./JS/jquery-3.2.1.min.js"></script>
    <script src="./JS/header.js"></script>
</head>
<body>
	
	<%@include file = "../Basic/header.jsp" %> 
	<div class="pure-g content-container">
	<div class="pure-u-1-1">
	
		<div class="pure-u-1-8 sizezero"></div>	
		<div class="pure-u-6-8 fullwidth">
			<center>
			<h1>
			<b>Vielen Dank für Ihre Bestellung bei  <img id="branding" src="./Images/brand.png" height="135px" width="auto"></b><br>Wir hoffen Sie bald wieder als Kunden in unserem <a href="./start">Onlineshop</a> begrüßen zu dürfen.<br>Bis dahin wünschen wir Ihnen ganz viele außergewöhnliche Ski-Erlebnisse.
			</h1>
			<h3>
			Ihre getätigten Bestellungen können Sie in Ihrem Kundenkonto unter <a href="./users?page=ordersearch">Bestellungen</a> einsehen. 
			</h3>
			</center>
		</div>
		<div class="pure-u-1-8 sizezero"></div>
	
	</div>
	</div>
	
	<%@include file = "../Basic/footer.jsp" %>

</body>
</html>