<div id="user-account-menu" class="ure-menu pure-menu-horizontal pure-menu-scrollable">				
    <ul class="pure-menu-list">
        <li class="pure-menu-item">
        	<a onclick="changeVersion(${article.getId()}, 'article')" class="pure-menu-link">
        	<c:choose>
				<c:when test="${updateArticle}">
				Artikel bearbeiten
				</c:when>
				<c:otherwise>
				Artikel anlegen
				</c:otherwise>
			</c:choose>
        	</a>
       	</li>
        <li class="pure-menu-item">
	        <a onclick="changeToStock(${article.getId()}, 'article')" class="pure-menu-link">
	        Lagerbestand
	        </a>
        </li>
    </ul>
</div>