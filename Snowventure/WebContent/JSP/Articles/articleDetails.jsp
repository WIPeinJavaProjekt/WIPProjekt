<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		
		<c:choose>
			<c:when test="false">
				<h1>Artikel bearbeiten</h1>
			</c:when>
			<c:otherwise>
				<h1>Artikel anlegen</h1>
			</c:otherwise>
		</c:choose>
		
		<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="./CSS/default.css">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
		
		<script src="./JS/jquery-3.2.1.min.js"></script>
    	<script src="./JS/header.js"></script>
	</head>
	
	<body>
	
		<%@include file = "../Basic/header.jsp" %> 
	
		<div class="pure-g" id="articlebox">
		
			<div class="pure-u-1-5"></div>
			<div class="pure-u-3-5">			
				<c:choose>
					<c:when test="false">
						<h1>Artikel bearbeiten</h1>
					</c:when>
					<c:otherwise>
						<h1>Artikel anlegen</h1>
					</c:otherwise>
				</c:choose>
			
				<div class="pure-g">
				    <div class ="pure-u-1-2 leftdiv">
					    <fieldset class="pure-group">
					    	<label>Artikel</label>
					    	<div class="pure-control-group">
					            <input class="boxedinput" id="name"  name="name" value="${name}" type="text" required placeholder="Artikelbezeichnung">
					        </div>			        
					        <div class="pure-control-group">
					            <input class="boxedinput" id="description" name="description" value="${description}" type="text" required placeholder="Artikelbeschreibung">
					        </div>
					        <div class="pure-control-group">
					            <input class="boxedinput" type="image" placeholder="Bild">
					        </div>
					        <div class="pure-control-group">
					            <input class="boxedinput" type="file" placeholder="Bild 2">
					        </div>
				        </fieldset>				            
			       	</div> 
				</div>

				<c:if test="${not empty errorArticle}">
					<form class="pure-form pure-form-aligned" action="article" method="get">
							<p class="error">${errorArticle}</p>
					</form>
				</c:if>
		   
			</div>
			<div class="pure-u-1-5"></div>
		</div>
		
		<%@include file = "../Basic/footer.jsp" %>
	</body>
	
</html>