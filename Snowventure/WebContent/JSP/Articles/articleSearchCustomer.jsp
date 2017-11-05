<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="./CSS/w3.css">

	<div class="pure-g" id ="banner">
		<div class="pure-u-1-5"></div>
		<div id="wrapper" class="pure-u-3-5">
			<div id="search-container" class="searchbox">
				<form class="pure-form" action="start" method="post">
				    <fieldset>
						<div class="pure-g">
					        <div class="pure-u-1-5">
						        <select name="categories" class="boxedinput">
							        <c:forEach items="${categories}" var="categories">
						        		<option value="${categories.GetACID()}">${categories.GetName()}</option>
						    		</c:forEach>
								</select>
							</div>
					        <div class="pure-u-3-5">
					        	<input type="text" class="boxedinput" name ="searchArticlePattern" placeholder="Wähle deine Ausrüstung">
							</div>
							<div class="pure-u-1-5">
				        		<button type="submit" id="search" name="search" class="pure-button pure-button-primary boxedinput">Suchen</button>
				        	</div>
				        </div>
				    </fieldset>
				    <fieldset>
				    Baustelle
				    Ergebnisse filtern:
				    Preis: <input type="range" min="-10" max="10">
				    Farbe: 
				    <select multiple>
					  <option value="Schwarz">Schwarz</option>
					  <option value="Weiß">Weiß</option>
					  <option value="Rot">Rot</option>
					  <option value="Blau">Blau</option>
					  <option value="Gelb">Gelb</option>
					  <option value="Grün">Grün</option>
					  <option value="Braun">Braun</option>
					  <option value="Orange">Orange</option>
					</select>
					Filter Größe
					Hersteller
				    </fieldset>
				</form>
			</div>			
		</div>
		<div class="pure-u-1-5"></div>
	</div>

<center>
<div style="height:2px; width:100%; background-color:rgb(75,75,75);"></div>
	<form name="cardForm" id="cardForm" action="articles" method="post">
		<div id="articleresultcontainer" class="search-results" align="left">	
			<c:if test="${ not empty articles }">
			 	<c:forEach var="article" items="${articles}">
				 		<div class="productCard pure-u-1-3" onclick="location.href='./articleshopping?ID=${article.GetId()}';">
					 		<div  style="width:100%">
							    <div class="productCardimage">
							    	<span class="articleimagehelper"></span><img src="./Images/Brille_schwarz.jpg" class="articlesearchimage">
							    </div>
							    <div class="w3-container w3-center">
									<p>
									<b>${article.GetManufacturer()}</b> - ${article.GetName()}</p>
									<div style="height:1px; width:100%; background-color:rgb(75,75,75);"></div>
									<p>${article.GetPriceFormatted()} EUR <br></p>
						    	</div>
						  	</div>
						</div>
					
				</c:forEach>
			</c:if>
			<c:if test="${noArticleFound}">
				<form class="pure-form pure-form-aligned" action="users?page=articlesearch" method="get">
					<p class="error">Es wurde keine Suchergebnisse gefunden.</p>
				</form>	
			</c:if>	
		</div>
	</form>
</center>

<div class="article-info">
	
</div>