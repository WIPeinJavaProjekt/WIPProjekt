<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Snowventure - ${article.GetName()}</title>
		
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
	
		<div class="pure-g articledescriptioncontainer">
		    <div class="pure-u-1-2">		    
    			<div class="w3-content w3-display-container w3slidmod articleslider" align="center">
    			
    				<c:forEach items="${article.getArticlesPictures()}" var="ap">
				   		<div class="w3-display-container mySlides ">
							<div class="productCardimage">
						   		<span class="articleimagehelper"></span><img src="${pageContext.request.contextPath}/images/${ap.GetImageId()}" class="articlesearchimage"/>
						   	</div>
						</div>
					</c:forEach>
					<c:if test="${article.getArticlesPictures().size()>1}"> 
						<button class="w3-button w3-display-left w3-black" onclick="plusDivs(-1)">&#10094;</button>
						<button class="w3-button w3-display-right w3-black" onclick="plusDivs(1)">&#10095;</button>
					</c:if>				
				</div>
				<script>
					var slideIndex = 1;
					showDivs(slideIndex);
					
					function plusDivs(n) {
					  showDivs(slideIndex += n);
					}
					
					function showDivs(n) {
					  var i;
					  var x = document.getElementsByClassName("mySlides");
					  if (n > x.length) {slideIndex = 1}    
					  if (n < 1) {slideIndex = x.length}
					  for (i = 0; i < x.length; i++) {
					     x[i].style.display = "none";  
					  }
					  x[slideIndex-1].style.display = "block";  
					}
				</script>		    
		    </div>
		    <div class="pure-u-1-2">
			    <div>
				    <h2>${article.GetName()}</h2>
				    von <b>${article.GetManufacturer()}</b> <br>
				    in der Farbe <i>${article.GetColor()}</i>
				    <br>
				    <h3>${article.GetPriceFormatted()} EUR</h3>
				    <div class="div-align-bottom">
					    <form>
							<select required name="selectedVersion" class="boxedinput">
								<c:forEach items="${article.GetAllVersions()}" var="version">
									<option value="${article.GetAllVersions().indexOf(version)}">${article.GetAllVersions().indexOf(version)}</option>
								</c:forEach>
							</select>
					    	<button class="pure-button pure-button-primary boxedinput" type="submit" name="updateArticle">IN DEN WARENKORB</button>
					    </form>
				    </div>
			    </div>    
		    </div>
		</div>
		<div style="height:2px; width:100%; background-color:rgb(75,75,75); margin-top: 5px;"></div>
		<div class="articledescriptioncontainer">
			<h2>Beschreibung</h2>
			${article.GetDescription()}		
		</div>
		
		<%@include file = "../Basic/footer.jsp" %>
	</body>
</html>