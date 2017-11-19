<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<title>Willkommen bei Snowventure</title>
		<link rel="stylesheet" type="text/css" href="./CSS/default.css">
		<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="./CSS/startpageslider.css">

		<link rel="shortcut icon" href="/favicon.ico">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
		
		<script src="./JS/jquery-3.2.1.min.js"></script>
   		<script src="./JS/header.js"></script>
   		
   		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	</head>
	<body>	
		
		<%@include file = "./Basic/header.jsp" %>		
			
		<div class="contentwrapp" id ="searchbox">
			<div class="pure-g" id ="banner">
			<div class="pure-u-1-5 sizezero"></div>
			<div id="wrapper" class="pure-u-3-5 fullwidth">
				<div id="search-container" class="searchbox">
					<form class="pure-form  textbox" action="start" method="post">
					    <fieldset>
					        <legend>
					        	<h1 id="titlemessage">Starte dein Abenteuer</h1>
					        </legend>
							<div class="pure-g" id="searchbar">
						        <div class="pure-u-1-5">
							        <select name="categorie" class="boxedinput">
							        	<option value="-1">Alle</option>
								        <c:forEach items="${categories}" var="categories">
							        		<option value="${categories.GetACID()}">${categories.GetName()}</option>
							    		</c:forEach>
									</select>
								</div>
						        <div class="pure-u-3-5">
						        	<input type="text" name="searchArticlePattern" class="boxedinput" placeholder="W�hle deine Ausr�stung">
								</div>
								<div class="pure-u-1-5">
					        		<button type="submit" id="search" name="search" class="pure-button pure-button-primary boxedinput">Suchen</button>
					        	</div>
					        </div>
					    </fieldset>
					</form>
				</div>
				
			</div>
			<div class="pure-u-1-5 sizezero"></div>
			</div>
		</div>
		<div id="slider">
			<%@include file = "./Basic/slider.jsp" %>
		</div>
		
		
		<div class="pure-g" id="advertisingstrip">
   			<div class="pure-u-1-3"><p align="center"><i class="fa fa-globe fa-2x" aria-hidden="true"></i><br>Umweltfreundliche Verpackung</p></div>
    		<div class="pure-u-1-3"><p align="center"><i class="fa fa-compass fa-2x" aria-hidden="true"></i><br>Europaweiter Versand</p></div>
    		<div class="pure-u-1-3"><p align="center"><i class="fa fa-clock-o fa-2x" aria-hidden="true"></i><br>24h Lieferung</p></div>
		</div>
		
		
		<div id="history">
		<div align="center" id="historyheader"><h2>�BER SNOWVENTURE</h2></div>
		
		<div class="pure-g historyblock">
	    <div class="pure-u-1-2 fullwidth"><img src="./Images/alpinehouse.jpg" alt="Webereibild" class="historypic"></div>
	    <div class="pure-u-1-2 fullwidth textbox">
	    <h3>Das Unternehmen</h3>
	    
	    <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   
		Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   
		Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse</p></div>
		</div>

		<div class="pure-g historyblock">
	    <div class="pure-u-1-2 fullwidth textbox">
	    <h3>Qualit�t zahlt sich aus</h3>
	    
	    <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   
		Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   
		Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse</p></div>
		<div class="pure-u-1-2 fullwidth"><img src="./Images/weaving_glasses.jpg" alt="Alpines Haus" class="historypic"></div>
		</div>
		
	    
		</div>
		
		
			
		<%@include file = "./Basic/footer.jsp" %>
	</body>

</html>
