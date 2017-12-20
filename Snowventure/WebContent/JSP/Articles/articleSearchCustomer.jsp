<!-- 
Beschreibung: Artikelsuche f�r Kunden Filter + Ergebnis
Ansprechpartner: Garrit Kniepkamp
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link rel="stylesheet" type="text/css" href="./CSS/w3.css">

    <div class="pure-g" id="searchfilter">
        <div class="pure-u-1-5 sizezero"></div>
        <div id="wrapper" class="pure-u-3-5 fullwidth">
            <div id="search-container" class="searchbox">
                <form class="pure-form textbox" action="articles" method="post">
                    <fieldset>
                        <div class="pure-g" id="filters">
                            <div class="pure-u-1-5">
                                <select name="categorie" class="boxedinput">
							        <option value="-1">Alle</option>
							        <c:forEach items="${categories}" var="categories">
							        	<option value="${categories.getACID()}"<c:if test="${selectedCategory.equals(categories.getACID())}"><c:out value="selected"/></c:if>>${categories.getName()}</option>
						    		</c:forEach>
								</select>
                            </div>
                            <div class="pure-u-3-5">
                                <input type="text" class="boxedinput" name="searchArticlePattern" maxlength="255" placeholder="W�hle deine Ausr�stung">
                            </div>
                            <div class="pure-u-1-5">
                                <button type="submit" id="search" name="search" class="pure-button pure-button-primary boxedinput">Suchen</button>
                            </div>
                        </div>
                    </fieldset>

					<!-- Filter zur Verfeinerung der Artikelsuche -->
                    <a id="filterbtn-show" href="javascript:void(0)">Filter &#x25bc;</a>
                    <fieldset id="filtersfield" style="display:none;">

                        <%@include file = "../Basic/filterManufacturer.jsp" %>
                        <%@include file = "../Basic/filterColor.jsp" %>
                        <%@include file = "../Basic/filterSize.jsp" %>
                        <%@include file = "../Basic/filterGender.jsp" %>

                        <div style="clear:both;">
                            <br>
                            <u style="font-size: 16px;">Preis</u>

                            <span> von </span>
                            <c:if test="${minprice>0 }">
                                <input name="minprice" value="${minprice}" type="number" min="0" max="10000" step=".01">
                            </c:if>
                            <c:if test="${minprice<= 0 || minprice == null}">
                                <input name="minprice" type="number" min="0" max="10000" step=".01">
                            </c:if>
                            <span> bis </span>
                            <c:if test="${maxprice >0 }">
                                <input name="maxprice" value="${maxprice}" type="number" min="0" max="10000" step=".01">
                            </c:if>
                            <c:if test="${maxprice<= 0 || maxprice == null}">
                                <input name="maxprice" type="number" min="0" max="10000" step=".01">
                            </c:if>
                       	</div>
                    </fieldset>
                </form>
            </div>
        </div>
        <div class="pure-u-1-5 sizezero"></div>
    </div>

	<!-- Anzeige aller Artikel, die den vorher eingebenen Suchkriterien entsprechen -->
    <center>		
        <form name="cardForm" id="cardForm" action="articles" method="post">
            <div id="articleresultcontainer" class="search-results" align="left">
                <c:if test="${ not empty articles }">


                    <c:forEach var="article" items="${articles}">
                        <div style="display:none;" class="productCard pure-u-1-3" onclick="location.href='./articleshopping?ID=${article.getId()}&version=${article.getSelectedVersion()}';">
                            <div style="width:100%">
                                <div class="productCardimage">
                                    <span class="articleimagehelper"></span><img src="${pageContext.request.contextPath}/images/${article.getAllVersions().get(article.getSelectedVersion()).getArticleHeadPicture().getImageId()}" class="articlesearchimage">
                                </div>

                                <div class="w3-container w3-center">
                                    <p>
                                        <b>${article.getManufacturer()}</b> - ${article.getName()}</p>
                                    <div style="height:1px; width:100%; background-color:rgb(75,75,75);"></div>
                                    <p>${article.getPriceFormatted()} EUR <br></p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </c:if>

                <script>
                    $(".productCard").each(function(fadeInDiv) {
                        $(this).delay(350 * fadeInDiv).fadeIn(420);

                    });
                </script>

                <c:if test="${noArticleFound}">
                    <form class="pure-form pure-form-aligned" action="users?page=articlesearch" method="get">
                        <p class="error">Es wurde keine Suchergebnisse gefunden.</p>
                    </form>
                </c:if>
            </div>
        </form>
    </center>
    <script type="text/javascript" src="./JS/dropdown.js"></script>
    <div class="article-info">

    </div>