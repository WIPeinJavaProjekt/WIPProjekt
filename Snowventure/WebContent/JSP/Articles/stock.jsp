<!-- 
Beschreibung: Detailansicht zum bearbeiten von Artikeln
Ansprechpartner: Garrit Kniepkamp, Fabian Meise
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

                <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
                <c:choose>
                    <c:when test="${updateArticle}">
                        <title>Artikel bearbeiten</title>
                    </c:when>
                    <c:otherwise>
                        <title>Artikel anlegen</title>
                    </c:otherwise>
                </c:choose>

                <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
                <link rel="stylesheet" type="text/css" href="./CSS/default.css">
                <link rel="stylesheet" type="text/css" href="./CSS/startpageslider.css">
                <link rel="stylesheet" type="text/css" href="./CSS/w3.css">

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

            <div class="pure-g" id="articleadminbox">

                <div class="pure-u-1-5 sizezero"></div>
                <div class="pure-u-3-5 fullwidth">

                    <%@include file = "./articleMenu.jsp" %>

                        <form id="articleadminboxform" class="pure-form  textbox" enctype="multipart/form-data" action="article" method="post">

                            <fieldset class="pure-group">
                                <div class="pure-g">
                                    <div class="pure-u-1-2 fullwidth">
                                        <div class="pure-control-group">
                                            <c:choose>
                                                <c:when test="${updateArticle}">
                                                    <div class="boxedinput">
                                                        <label> Ausgewählte Version:</label><br>
                                                        <select required id="selectedVersion" name="selectedVersion" class="boxedinput" onchange="changeToStock(${article.getId()}, 'article')">
													<c:forEach items="${article.getAllVersions()}" var="version">
										        		<option value="${article.getAllVersions().indexOf(version)}"
										        			<c:if test="${article.getSelectedVersion()==article.getAllVersions().indexOf(version)}"><c:out value="selected"/></c:if>>
									        				${article.getAllVersions().indexOf(version)}</option>
										    		</c:forEach>
												</select>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="pure-control-group">
                                                        <select required name="selectedVersion" class="boxedinput">
									        		<option value="0">0</option>
												</select>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                            <br>
                                        </div>
                                        <div class="pure-control-group">
                                            <div>Lagerbestand für Unigröße:
                                                <input class="" id="Unigröße" name="Unigröße" value="${article.getStock('Unigröße')}" type="number" step="1" placeholder="Lagerbestand">
                                            </div>
                                            <div>Lagerbestand für S:
                                                <input class="" id="S" name="S" value="${article.getStock('S')}" type="number" step="1" placeholder="Lagerbestand">
                                            </div>
                                            <div>Lagerbestand für M:
                                                <input class="" id="M" name="M" value="${article.getStock('M')}" type="number" step="1" placeholder="Lagerbestand">
                                            </div>
                                            <div>Lagerbestand für L:
                                                <input class="" id="L" name="L" value="${article.getStock('L')}" type="number" step="1" placeholder="Lagerbestand">
                                            </div>
                                            <div>Lagerbestand für XL:
                                                <input class="" id="XL" name="XL" value="${article.getStock('XL')}" type="number" step="1" placeholder="Lagerbestand">
                                            </div>
                                            <div>Lagerbestand für XXL:
                                                <input class="" id="XXL" name="XXL" value="${article.getStock('XXL')}" type="number" step="1" placeholder="Lagerbestand">
                                            </div>
                                            <div>Lagerbestand für XXXL:
                                                <input class="" id="XXXL" name="XXXL" value="${article.getStock('XXXL')}" type="number" step="1" placeholder="Lagerbestand">
                                            </div>
                                            <button class="pure-button pure-button-primary" type="submit" name="changeStock" onclick="changeVersion(${article.getId()}, 'article')">Lagerbestände ändern</button>
                                        </div>
                                    </div>
                                    <div class="pure-u-1-2 fullwidth">

                                    </div>
                                </div>
                            </fieldset>
                        </form>
                </div>
                <div class="pure-u-1-5 sizezero"></div>
            </div>

            <%@include file = "../Basic/footer.jsp" %>
    </body>

    </html>