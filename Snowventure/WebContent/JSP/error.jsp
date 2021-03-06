<!-- 
Beschreibung: Fehlerseite die aufgerufen wird, sofern ein Fehler auftritt
Ansprechpartner: Garrit Kniepkamp, Fabian Meise
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <title>Willkommen bei Snowventure</title>
        <link rel="stylesheet" type="text/css" href="./CSS/default.css">
        <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="./CSS/startpageslider.css">

        <link rel="shortcut icon" href="/favicon.ico">
        <link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
        <link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">

        <script src="./JS/jquery-3.2.1.min.js"></script>
        <script src="./JS/header.js"></script>

        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>

    <body>
		<!-- Einf�gen des Header -->
        <%@include file = "./Basic/header.jsp" %>

            <div>
                <div class="pure-g" id="banner">
                    <div class="pure-u-1-5"></div>
                    <div class="pure-u-3-5">
                        <center class="content-container">
                            <h1><i class="fa fa-exclamation-triangle fa-5x" aria-hidden="true"></i></h1>
                            <h1>Ein Fehler ist aufgetreten!</h1>
                        </center>
                    </div>
                    <div class="pure-u-1-5"></div>
                </div>
            </div>
            <!-- Einf�gen des Footer -->
            <%@include file = "./Basic/footer.jsp" %>
    </body>

    </html>