<!-- 
Beschreibung: Seite f�r Warenkorb
Ansprechpartner: Jacob Markus
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <title>Snowventure Einkaufswagen</title>

        <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="./CSS/default.css">
        <link rel="stylesheet" type="text/css" href="./CSS/w3.css">
        <link rel="stylesheet" type="text/css" href="./CSS/shoppingcart.css">

        <script src="/Snowventure/JS/jquery-3.2.1.min.js"></script>
        <script type="text/javascript" src="./JS/header.js"></script>

        <link rel="shortcut icon" href="/favicon.ico">
        <link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
        <link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
    </head>

    <body>

        <%@include file = "../Basic/header.jsp" %>

            <div class="pure-g content-container textbox shoppingcart">
                <form class=" textbox" action="cart" method="post" style="width:100%">

                    <div class="pure-u-1-1">

                        <c:choose>
                            <c:when test="${not empty currentCart && currentCart.getCartPositions().size() > 0}">
                                <c:if test="${not empty error}">
                                    <form class="pure-form pure-form-aligned" action="#">
                                        <p class="error">${error}</p>
                                    </form>
                                </c:if>
                                <c:forEach var="position" items="${ currentCart.getCartPositions() }">
                                    <div class="w3-card-4" id="scp-article-card">
                                        <div class="pure-u-1-5" id="sc-aimg-container">
                                            <img class="article-img" src="images/${ position.article.getAllVersions().get(position.article.getSelectedVersion()).getArticleHeadPicture().getImageId()}" alt="Artikelbild">
                                        </div>
                                        <div class="pure-u-1-4" id="scp-info-part">
                                            <h4 id="articlelink" onclick="location.href='./articleshopping?ID=${ position.article.getId() }&version=${ position.article.getSelectedVersion()}';"><b>${ position.article.getName() } von ${ position.article.getManufacturer()}</b></h4>
                                            <hr size="5">
                                            <p>Farbe: ${ position.getArticle().getAllVersions().get(position.getArticle().getSelectedVersion()).getColorsAsString() }</p>
                                            <p>Gr��e: ${ position.getSize() }</p>
                                        </div>
                                        <div class="pure-u-1-4" id="scp-info-part">
                                            <p><b>Preis</b></p>
                                            <p>EUR ${ position.article.getPrice() }</p>
                                        </div>
                                        <div class="pure-u-1-4" id="scp-info-part">
                                            <p><b>Menge</b></p>
                                            <!--<select id="amount${currentCart.getCartPositions().indexOf(position)}" onchange="location.href='./cart?scpid=<c:choose><c:when test='${ not empty currentUser}'>${currentUser.shoppingcart.getCartPositions().indexOf(position)}</c:when><c:otherwise>${ currentCart.getCartPositions().indexOf(position) }</c:otherwise></c:choose>&amount=' + jQuery('#amount${currentCart.getCartPositions().indexOf(position)} option:selected').val();">-->

                                            <c:choose>
                                                <c:when test="${ position.amount < 11}">
                                                    <select id="amount${currentCart.getCartPositions().indexOf(position)}" onchange="location.href='./cart?scpid=${currentCart.getCartPositions().indexOf(position)}&amount=' + jQuery('#amount${currentCart.getCartPositions().indexOf(position)} option:selected').val();">
												<c:forEach var="counter" begin="1" end="10">
													<option <c:if test="${ position.amount == counter }">selected</c:if> value="${counter}">${counter}</option>
												</c:forEach>
												<option value="11">10+</option>
											</select>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="pure-u-1-8" style="align: center; min-width: 50px;">
                                                        <input class="boxedinput" id="amount${currentCart.getCartPositions().indexOf(position)}" name="amount${currentCart.getCartPositions().indexOf(position)}" value="${ position.amount }" min="0" max="10000000" step="1" type="number" placeholder="Menge" oninput="this.value = this.value.replace(/[^0-9]/g, '');" onblur="location.href='./cart?scpid=${currentCart.getCartPositions().indexOf(position)}&amount=' + (this.value != '' ? jQuery('#amount${currentCart.getCartPositions().indexOf(position)}').val() : 'novalue');">
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>

                                            <!--<p><a href="cart?scpid=<c:choose><c:when test='${ not empty currentUser}'>${currentUser.shoppingcart.getCartPositions().indexOf(position)}</c:when><c:otherwise>${ currentCart.getCartPositions().indexOf(position) }</c:otherwise></c:choose>&option=delete">L�schen</a></p>-->
                                            <p><a href="cart?scpid=${ currentCart.getCartPositions().indexOf(position) }&option=delete"><i class="fa fa-trash fa-1g" aria-hidden="true"></i></a></p>
                                        </div>
                                        <hr>
                                    </div>
                                </c:forEach>

                                <div id="scp-article-card">

                                    <div class="pure-u-11-12" id="sc-conclusion">

                                        <!--
						    	<c:choose>
						    	<c:when test="${ not empty currentUser }">
							    	<h3><b>Summe (${currentUser.shoppingcart.getArticleCount()} Artikel):  EUR ${currentUser.shoppingcart.getShoppingCartPrice()}</b></h3>
					    		</c:when>
					    		<c:otherwise>
					    			<h3><b>Summe (${currentCart.getArticleCount()} Artikel):  EUR ${currentCart.getShoppingCartPrice()}</b></h3>
					    		</c:otherwise>
					    		</c:choose>
					    		-->

                                        <h3><b>Summe (${currentCart.getArticleCount()} Artikel):  EUR ${currentCart.getShoppingCartPrice()}</b></h3>
                                        <h4>
                                            <input id="scp-button-1" class="pure-button pure-button-primary" type="button" value="Weitershoppen" onclick="location.href='./articles'">
                                            <input id="scp-button-2" class="pure-button pure-button-primary" type="button" value="Jetzt Bestellen" onclick="location.href='./order?neworder=true'">
                                        </h4>
                                    </div>
                                    <div class="pure-u-1-12" style="height: 15%;"></div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div id="scp-article-card">
                                    <h2><b>Ihr Einkaufswagen ist leer.</b></h2>
                                    <h4>Ihr Einkaufswagen steht zu Ihrer Verf�gung. Nutzen Sie ihn und bef�llen Sie ihn mit Ski-Kleidung, Ausr�stung und mehr.
                                        <c:if test="${empty currentUser}"><br>Wenn Sie bereits ein Konto besitzen, tippen Sie auf <a href="./login">Anmelden</a>, um den vollen Funktionsumfang von Snowventure nutzen zu k�nnen.<br></c:if>
                                        Setzen Sie den Einkauf bei <a href="./articles?search">Snowventure</a> fort.</h4>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </form>
            </div>
            <%@include file = "../Basic/footer.jsp"%>
    </body>

    </html>