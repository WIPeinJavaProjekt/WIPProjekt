<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="pure-g" id ="account-searchbox">
	<div class="pure-u-1-5"></div>
	<div id="wrapper" class="pure-u-3-5">
		<div id="search-container" class="searchbox">
			<form class="pure-form" action="users" method="POST">
			    <fieldset>
			        <legend>
			        	<h3>Artikelsuche</h3>
			        </legend>
					<div class="pure-g">
				        <div class="pure-u-1-5">
					        <select name="categories" class="boxedinput">
					        	<option value="">Alle</option>
						        <c:forEach items="${categories}" var="categories">
					        		<option value="${categories.GetACID()}">${categories.GetName()}</option>
					    		</c:forEach>
							</select>
						</div>
				        <div class="pure-u-3-5">
				        	<input type="text" name="searchArticlePattern" class="boxedinput" placeholder="Artikelinformationen eingeben">
						</div>
						<div class="pure-u-1-5">
			        		<button type="submit" name="searchArticles" class="pure-button pure-button-primary boxedinput">Suchen</button>
			        	</div>
			        </div>
			    </fieldset>
			</form>
		</div>
		<div>
			<form class="pure-form" action="users" method="POST">
				<div class="pure-u-1-5">
	        		<button type="submit" name="addArticle" class="pure-button pure-button-primary boxedinput">Artikel hinzufügen</button>
	        	</div>
			</form>		
		</div>	
	</div>
	<div class="pure-u-1-5"></div>
</div>

<center>
	<div class="search-results">	
		<c:if test="${ not empty articles }">
		 	<c:forEach var="article" items="${articles}">
		 	
		 		<div class="pure-u-1-5 productCard" onclick="location.href='./article?ID=${article.GetId()}';">
			 		<div class="w3-card-4" style="width:100%">
					    <div class="w3-container w3-center">
							<p><b>${article.GetName()}</b></p>
							<hr size="5">
							<p>${article.GetDescription()}</p>
							<hr>
							<p>Preis: ${article.GetPrice()} &euro; <br>
							Größe: ${article.GetSize()} <br>
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
</center>

<div class="article-info">
	
</div>