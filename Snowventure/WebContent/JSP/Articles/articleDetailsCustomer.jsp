<!-- 
Beschreibung: Detailansicht eines Artikels
Ansprechpartner: Garrit Kniepkamp, Fabian Meise
 -->
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <title>Snowventure - ${article.getName()}</title>

        <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="./CSS/default.css">
        <link rel="stylesheet" type="text/css" href="./CSS/startpageslider.css">

        <script type="text/javascript" src="/Snowventure/JS/jquery-3.2.1.min.js"></script>
        <script type="text/javascript" src="./JS/header.js"></script>
        <script type="text/javascript" src="./JS/displayImage.js"></script>
        <script type="text/javascript" src="./JS/versions.js"></script>

        <link rel="shortcut icon" href="/favicon.ico">
        <link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
        <link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">

        <script src="./JS/jquery-3.2.1.min.js"></script>
        <script src="./JS/header.js"></script>
    </head>

    <body>
        <%@include file = "../Basic/header.jsp" %>
            <div class="content-container">
                <div class="pure-g articledescriptioncontainer">
                    <div class="pure-u-1-1 fullwidth">
						<c:if test="${not empty error}">
							<form class="pure-form pure-form-aligned" action=#>
								<p class="error">${error}</p>
							</form>
						</c:if>
                        <div class="w3-content w3-display-container w3slidmod articleslider" align="center">

                            <c:forEach items="${article.getAllVersions().get(article.getSelectedVersion()).getArticlesPictures()}" var="ap">
                                <div class="w3-display-container mySlides ">
                                    <div class="productCardimage detailsearchimage">
                                        <span class="articleimagehelper"></span><img src="${pageContext.request.contextPath}/images/${ap.getImageId()}" class="articledetailimage" />
                                    </div>
                                </div>
                            </c:forEach>
                            <c:if test="${article.getAllVersions().get(article.getSelectedVersion()).getArticlesPictures().size()>1}">
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
                                if (n > x.length) {
                                    slideIndex = 1
                                }
                                if (n < 1) {
                                    slideIndex = x.length
                                }
                                for (i = 0; i < x.length; i++) {
                                    x[i].style.display = "none";
                                }
                                x[slideIndex - 1].style.display = "block";
                            }
                        </script>
                    </div>
                    <div class="pure-u-1-1 fullwidth  textbox">
                        <div>
                            <form class="pure-form" action="articleshopping" method="post">
                                <h2>${article.getName()}</h2>
                                von <b>${article.getManufacturer()}</b> <br>

                                <div class="pure-control-group pure-form">
                                    <select required id="selectedSize" name="selectedSize">
							    	<option value="">Größe auswählen</option>
							        <c:forEach items="${article.getAllSizesFromArticle()}" var="size">
						        		<option value="${size}" <c:if test="${selectedSize==size}"><c:out value="selected"/></c:if>>
						        			<c:choose>
						        				<c:when test="${article.getStock(size)==0}">
						        					${size} (Nicht verfügbar)
						        				</c:when>
						        				<c:when test="${article.getStock(size)<11}">
						        					${size} (Noch ${article.getStock(size)} auf Lager)
						        				</c:when>						        				
						        				<c:when test="${article.getStock(size)>=11}">
						       						${size}
						      					</c:when>
						        			</c:choose>
						        		</option>
						    		</c:forEach>
								</select>
                                </div>
                                <br> Version wählen <br>
                                <c:forEach items="${article.getAllVersions()}" var="av">

                                    <div id="selectColor" name="selectColor" onclick="customerChangeVersion(${article.getId()}, 'articleshopping', ${article.getAllVersions().indexOf(article.getAvByVersionId(av.getAvId()))})" class="pure-button  <c:if test=" ${av.getAvId()==article.getAllVersions().get(article.getSelectedVersion()).getAvId()} ">
						    	 versionchoice 
						    	</c:if>" style="background-color:${av.getColors().get(0).getHexcode()};">
                                        <div class="pure-control-group">
                                            <p>
                                                <font color="${av.getColors().get(0).getBackgroundHexcode()}"><b>${av.getColorsAsString()}</b></font>
                                            </p>
                                        </div>
                                    </div>
                                </c:forEach>
                                <br>
                                <h3>Preis ${article.getPriceFormatted()} EUR</h3>
                                <div>
                                    <div class="pure-control-group">
                                        <input required class="boxedinput" id="amount" name="amount" value="1" type="number" step="1" placeholder="Menge" min="1">
                                    </div>
                                    <div class="pure-control-group">
                                        <button class="pure-button pure-button-primary boxedinput" type="submit" id="addToCart" name="addToCart">IN DEN WARENKORB</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div style="height:2px; width:100%; background-color:rgb(75,75,75); margin-top: 5px;"></div>
                <div class="articledescriptioncontainer  textbox">
                    <h2>Beschreibung</h2>
                    ${article.getDescription()}
                </div>
            </div>
            <%@include file = "../Basic/footer.jsp" %>
    </body>

    </html>