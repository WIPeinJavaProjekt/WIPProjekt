<!-- 
Beschreibung: Header jeder Seite
Ansprechpartner: Fabian Meise
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="./CSS/w3.css">
<link rel="stylesheet" type="text/css" href="./CSS/default_reps.css">

<header>
    <form class="pure-form" action="start" method="post">
        <div class="pure-g">
            <div class="pure-u-1-5 sizezero"></div>
            <div class="pure-u-3-5 fullwidth" align="left">

                <div align="right" id="responsiveheader" class="responsiveheaderimg">
                    <div style="cursor: pointer; height:45px; width: 250px; position: absolute;" onclick="window.location='start';"></div>
                    <a id="toghead" href="javascript:void(0)"><i class="fa fa-bars fa-3x" aria-hidden="true"></i></a>
                </div>

                <div class="pure-menu pure-menu-horizontal" id="headermenu">
                    <ul class="pure-menu-list">
                        <li class="pure-menu-item">
                            <div id="brandbox">
                                <div id="innerbrandbox">
                                    <a href="start"><img id="brandimg" src="./Images/brand.png" height="105px" width="auto"></a>
                                </div>
                            </div>
                        </li>
                        <li class="pure-menu-item">
                            <a href="start#history">
                                <div class="headerbtn" id="aboutlink">
                                    <span>ÜBER UNS</span>
                                </div>
                            </a>
                        </li>
                        <c:if test="${currentUser != null}">
                            <li class="pure-menu-item pure-menu-has-children pure-menu-allow-hover">
                                <button type="submit" name="settings" class="headerbtn pure-menu-link">KONTO</button>
                                <ul class="pure-menu-children">
                                    <li class="pure-menu-item">
                                        <button type="submit" name="logout" class="second-headerbtn pure-menu-link">ABMELDEN</button>
                                    </li>
                                </ul>
                            </li>
                        </c:if>
                        <c:if test="${currentUser == null}">
                            <li class="pure-menu-item">
                                <button type="submit" name="login" class="headerbtn firstheaderbtn">ANMELDEN</button>
                            </li>
                        </c:if>
                        <li class="pure-menu-item">
                            <button type="submit" name="cart" class="headerbtn">WARENKORB 
	        	<c:if test="${currentCart != null && currentCart.getCountShoppingCartPosition() != 0 }">(${currentCart.getCountShoppingCartPosition()})</c:if>
	        </button>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="pure-u-1-5 sizezero"></div>
        </div>
    </form>
</header>
<div id="headerdummy"></div>
<script type="text/javascript" src="./JS/responsive.js"></script>