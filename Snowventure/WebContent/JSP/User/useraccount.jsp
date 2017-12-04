<!-- 
Beschreibung: Rahmen für Kontoübersichtsmenü, der entsprechende Content wird über andere JSPS geladen
Ansprechpartner: Jacob Markus
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <head>
            <title>Kontoübersicht</title>

            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
            <title>Welcome to SnowSki</title>

            <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
            <link rel="stylesheet" type="text/css" href="./CSS/default.css">

            <script src="/Snowventure/JS/jquery-3.2.1.min.js"></script>
            <script type="text/javascript" src="./JS/header.js"></script>
            <script type="text/javascript" src="./JS/popup.js"></script>

            <link rel="shortcut icon" href="/favicon.ico">
            <link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
            <link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
        </head>

        <body>
            <%@include file = "../Basic/header.jsp" %>
                <div id="useroptions">
                    <div id="user-account-menu" class="ure-menu pure-menu-horizontal pure-menu-scrollable">
                        <span class="pure-menu-heading">Kontoübersicht</span>

                        <ul class="pure-menu-list">
                            <li class="pure-menu-item"><a href="users?page=mydata" class="pure-menu-link">Meine Daten</a></li>
                            <c:if test="${currentUser.utid==1}">
                                <li class="pure-menu-item"><a href="users?page=usersearch" class="pure-menu-link">Nutzerübersicht</a></li>
                            </c:if>
                            <c:if test="${currentUser.utid==1||currentUser.utid==3}">
                                <li class="pure-menu-item"><a href="users?page=articlesearch" class="pure-menu-link">Artikelübersicht</a></li>
                            </c:if>
                            <li class="pure-menu-item"><a href="users?page=ordersearch" class="pure-menu-link">Bestellungen</a></li>
                        </ul>
                    </div>

                    <div id="content">
                        <script type="text/javascript" src="./JS/useraccount.js"></script>
                    </div>

                </div>

                <%@include file = "../Basic/footer.jsp"%>
        </body>

        </html>