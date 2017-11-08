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
	
	<div class="contentwrapp">
		<div class="pure-u-1-8"></div>
		<div class="pure-u-3-4">
			<div class="pure-u-1-1">
				<h1>Bestelldetails</h1>
				<div class="pure-u-3-8">Bestellt am 26.03.2017</div>
				<div class="pure-u-3-8">Bestellnr. 21545342326</div>
			</div>
			<div class="pure-u-1-1" style="height: 20px;">
			</div>
			<div class="pure-u-1-1">
				<div class="pure-u-3-8">
						<div class="pure-u-1-1"><b>Versandadresse</b></div>
						<div class="pure-u-1-1">Jacob Markus</div>
						<div class="pure-u-1-1">Dorfstr. 3</div>
						<div class="pure-u-1-1">23923 Retelsdorf</div>
						<div class="pure-u-1-1">Deutschland</div>
				</div>
				<div class="pure-u-1-3">
						<div class="pure-u-1-1"><b>Zahlungsart</b></div>
						<div class="pure-u-1-1">Kreditkarte ****1267</div>
				</div>
				<div class="pure-u-1-4">
						<div class="pure-u-1-1"><b>Bestellübersicht</b></div>
						<div class="pure-u-2-5">Zwischensumme</div><div class="pure-u-3-5">EUR 10,00</div>
						<div class="pure-u-2-5">Gesamtsumme</div><div class="pure-u-3-5">EUR 10,00</div>
				</div>
			</div>
			<div class="pure-u-1-1">
			
			</div>			
		</div>
		<div class="pure-u-1-8"></div>
		
	</div>
	
	<%@include file = "../Basic/footer.jsp" %>
</body>
</html>