<!-- 
Beschreibung: Detailansicht einer Bestellung
Ansprechpartner: Jacob Markus
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Bestelldetails</title>
            <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
            <link rel="stylesheet" type="text/css" href="./CSS/default.css">
            <link rel="stylesheet" type="text/css" href="./CSS/shoppingcart.css">
            <link rel="stylesheet" type="text/css" href="./CSS/order.css">
            <link rel="stylesheet" type="text/css" href="./CSS/startpageslider.css">

            <script type="text/javascript" src="/Snowventure/JS/jquery-3.2.1.min.js"></script>
            <script type="text/javascript" src="./JS/header.js"></script>
            <script type="text/javascript" src="./JS/displayImage.js"></script>
            <script type="text/javascript" src="./JS/popup.js"></script>

            <link rel="shortcut icon" href="/favicon.ico">
            <link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
            <link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">

            <script src="./JS/jquery-3.2.1.min.js"></script>
            <script src="./JS/header.js"></script>
    </head>

    <body>

        <%@include file = "../Basic/header.jsp" %>

            <div class="pure-g content-container textbox">
                <div class="pure-u-1-8 sizezero"></div>

                <div id="popup-div">
                    <div id="popup-div-content">
                        <form action="order?neworder=true" id="popup-div-form" method="post" name="delivery-adr-form">
                            <i id="close" class="fa fa-close fa-1g" aria-hidden="true" onclick="div_hide()"></i>
                            <h4>Neue Versandadresse</h4>
                            <hr>
                            <input required id="name" name="name" placeholder="Vorname" type="text">
                            <input required id="surename" name="surename" placeholder="Nachname" type="text">
                            <input required id="street" name="street" placeholder="Straße" type="text">
                            <input required id="houseno" name="houseno" placeholder="Hausnummer" type="text">
                            <input required id="postcode" name="postcode" placeholder="PLZ" type="text">
                            <input required id="location" name="location" placeholder="Ort" type="text">
                            <input class="pure-button pure-button-primary" name="delivery-adr-submit" type="submit" id="popup-submit" value="Speichern">
                        </form>
                    </div>
                </div>

                <c:if test="${ not empty currentUser && not empty currentOrder }">
                    <div class="pure-u-3-4 fullwidth">
                        <div class="pure-u-1-1">
                            <div class="pure-u-1-1">
                                <h1>Bestelldetails
                                    <c:if test="${ currentOrder.statuscycle.size() < 1 }">prüfen</c:if>
                                </h1>
                            </div>
                            <c:if test="${not empty error}">
                                <form class="pure-form pure-form-aligned" action=#>
                                    <p class="error">${error}</p>
                                </form>
                            </c:if>
                            <c:if test="${ currentOrder.statuscycle.size() > 0 }">
                                <div class="pure-u-3-4" id="orderdetails">
                                    <div class="pure-u-1-1">Bestellt am ${ currentOrder.statuscycle.get(0).statusdate }</div>
                                    <div class="pure-u-1-1">Bestellnr. ${ currentOrder.orid }</div>
                                </div>
                                <div class="pure-u-1-4" id="orderdetails">
                                    <center>
                                        <h4>
                                            <b>Status: 
								<c:choose>
									<c:when test="${ currentUser.utid == '2' }">
										<span id="orderstatus"> ${ currentOrder.statuscycle.get(currentOrder.statuscycle.size()-1).description }</span>
									</c:when>
									<c:otherwise>
										<select id="chooseStatus" name="chooseStatus">
											<c:forEach var="status" items="${ statusList }">
												<option <c:if test ="${ currentOrder.statuscycle.get(currentOrder.statuscycle.size()-1).description eq status.description }">selected</c:if> value="${ status.description }">${ status.description }</option>
											</c:forEach>
										</select>
										<i id="savestatus" class="fa fa-save fa-1g" aria-hidden="true" onclick ="location.href='./order?saveorder=true&newstatus='+ jQuery('#chooseStatus option:selected').val();"></i>
									</c:otherwise>
								</c:choose>
								</b>
                                        </h4>
                                        <p>Letzte Änderung: ${ currentOrder.statuscycle.get(currentOrder.statuscycle.size()-1).statusdate }</p>
                                    </center>
                                </div>
                            </c:if>
                        </div>
                        <div class="pure-u-1-1" style="height: 20px;">
                        </div>
                        <div class="pure-u-1-1">
                            <div class="pure-u-1-4 fullwidth" id="orderdetails">
                                <div class="pure-u-1-1"><b>Versandadresse</b>
                                    <c:if test="${ currentOrder.statuscycle.size() == 0 }"> <a style="font-size: 12px;" href="#" onclick="div_show()">Ändern</a></c:if>
                                </div>
                                <div class="pure-u-1-1 orderdetailsvalue">${ currentOrder.name} ${ currentOrder.surname }</div>
                                <div class="pure-u-1-1 orderdetailsvalue">${ currentOrder.adress.street} ${ currentOrder.adress.houseno}</div>
                                <div class="pure-u-1-1 orderdetailsvalue">${ currentOrder.adress.postcode} ${ currentOrder.adress.location}</div>
                            </div>
                            <div class="pure-u-1-4 fullwidth" id="orderreceiptadr">
                                <div class="pure-u-1-1"><b>Rechnungsadresse</b></div>
                                <div class="pure-u-1-1 orderdetailsvalue">Identisch mit Lieferadresse</div>
                                <br>
                                <div class="pure-u-1-1"><b>Versandart</b></div>
                                <div class="pure-u-1-1 orderdetailsvalue">DHL Express</div>
                            </div>
                            <div class="pure-u-1-4 fullwidth" id="orderpaymentmethod">
                                <div class="pure-u-1-1"><b>Zahlungsart</b></div>
                                <div class="pure-u-1-1 orderdetailsvalue">Auf Rechnung</div>
                            </div>
                            <div class="pure-u-1-4 fullwidth">
                                <div class="pure-u-1-1"><b>Bestellübersicht</b></div>
                                <div class="pure-u-2-5 orderdetailsvalue">Artikel</div>
                                <div class="pure-u-3-5" style="text-align: right">EUR ${ currentOrder.shoppingCart.getShoppingCartPrice() }</div>
                                <div class="pure-u-2-5 orderdetailsvalue">Versand/Verpackung</div>
                                <div class="pure-u-3-5" style="text-align: right">EUR 0,00</div>
                                <div class="pure-u-2-5" id="ordervaluedescription">Gesamtsumme</div>
                                <div class="pure-u-3-5" id="ordervaluesum">EUR ${ currentOrder.shoppingCart.getShoppingCartPrice() }</div>
                            </div>
                        </div>
                        <div class="pure-u-1-1" style="height: 20px;"></div>
                        <c:forEach items="${ currentOrder.shoppingCart.cartPositions }" var="position">
                            <div class="pure-g" id="orderitems-div">
                                <form id="orderitem">
                                    <div id="scp-article-card" class="w3-card-4">
                                        <div class="pure-u-1-5" id="sc-aimg-container">
                                            <img class="article-img" src="images/${ position.article.getAllVersions().get(position.article.getSelectedVersion()).getArticleHeadPicture().getImageId()}" alt="Artikelbild">
                                        </div>
                                        <div class="pure-u-1-4" id="scp-info-part">
                                            <h4><b>${ position.article.getName() } von ${ position.article.getManufacturer()}</b></h4>
                                            <hr size="5">
                                            <p>Farbe: ${ position.getArticle().getAllVersions().get(position.getArticle().getSelectedVersion()).getColorsAsString() }</p>
                                            <p>Variante: ${ position.size }</p>
                                        </div>
                                        <div class="pure-u-1-4" id="scp-info-part">
                                            <p><b>Preis</b></p>
                                            <p>${ position.article.getAllVersions().get(position.article.getSelectedVersion()).price } EUR</p>
                                        </div>
                                        <div class="pure-u-1-4" id="scp-info-part">
                                            <p><b>Menge</b></p>
                                            <p>${ position.amount }</p>
                                        </div>
                                        <hr>
                                    </div>
                                </form>
                            </div>
                        </c:forEach>
                        <div class="pure-u-5-8 fullwidth" id="order-control-div">
                            <c:choose>
                                <c:when test="${ currentOrder.statuscycle.size() < 1 }">
                                    <input type="button" name="back" value="Zurück" class="pure-button pure-button-primary boxedinput order-button" onclick="location.href='./cart'">
                                </c:when>
                                <c:otherwise>
                                    <input type="button" name="back" value="Zurück" class="pure-button pure-button-primary boxedinput order-button" onclick="location.href='./users?page=ordersearch'">
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${ currentOrder.statuscycle.size() < 1 }"><input type="button" name="processOrder" value="Jetzt kaufen (verbindlich)" class="pure-button pure-button-primary boxedinput order-button" onclick="location.href='./order?processOrder=true'"></c:if>
                        </div>
                    </div>
                </c:if>
                <div class="pure-u-1-8 sizezero"></div>
            </div>
            <%@include file = "../Basic/footer.jsp" %>
    </body>

    </html>