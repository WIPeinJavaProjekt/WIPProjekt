<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="./CSS/w3.css">

<div class="contentwrapp" id ="searchbox">
	<div class="pure-g" id ="banner">
		<div class="pure-u-1-5"></div>
		<div id="wrapper" class="pure-u-3-5">
			<div id="search-container" class="searchbox">
				<form class="pure-form" action="start" method="post">
				    <fieldset>
				        <legend>
				        	<h1 id="titlemessage">Teste deine Grenzen</h1>
				        </legend>
						<div class="pure-g">
					        <div class="pure-u-1-5">
						        <select name="categories" class="boxedinput">
							        <c:forEach items="${categories}" var="categories">
						        		<option value="${categories.GetACID()}">${categories.GetName()}</option>
						    		</c:forEach>
								</select>
							</div>
					        <div class="pure-u-3-5">
					        	<input type="text" class="boxedinput" placeholder="W�hle deine Ausr�stung">
							</div>
							<div class="pure-u-1-5">
				        		<button type="submit" id="search" name="search" class="pure-button pure-button-primary boxedinput">Suchen</button>
				        	</div>
				        </div>
				    </fieldset>
				</form>
			</div>			
		</div>
		<div class="pure-u-1-5"></div>
	</div>
</div>

<center>
	<form name="cardForm" id="cardForm" action="articles" method="post">
		<div class="search-results">	
			<c:if test="${ not empty articles }">
			 	<c:forEach var="article" items="${articles}">
				 		<div class="pure-u-1-5 productCard" onclick="location.href='./article?ID=${article.GetId()}';">
					 		<div class="w3-card-4" style="width:100%">
							    <img src="./Images/Brille_schwarz.jpg" style="width:100%">
							    <div class="w3-container w3-center">
									<p><b>${article.GetName()}</b></p>
									<hr size="5">
									<p>${article.GetDescription()}</p>
									<hr>
									<p>Preis: ${article.GetPrice()} &euro; <br>
									Gr��e: ${article.GetSize()} <br>
									Farbe: ${article.GetColor()}</p>
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