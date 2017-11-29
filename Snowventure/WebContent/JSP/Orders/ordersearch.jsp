<!-- 
Beschreibung: Bestellsuche
Ansprechpartner: Jacob Markus
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="pure-g " id ="account-searchbox">
	<div class="pure-u-1-5 sizezero"></div>
	<div id="wrapper" class="pure-u-3-5 fullwidth">
		<div id="search-container" class="searchbox">
			<form class="pure-form" action="users" method="POST">
			    <fieldset>
			        <legend>
			        	<h3>Bestellungen</h3>
			        </legend>
					<div class="pure-g" id="searchbar">
						<div class="pure-u-1-5">
					        <select name="categorie" class="boxedinput">
					        	<option value="0">Bestellnummer</option>
					        	<option value="1">Status</option>
					        	<c:if test="${ currentUser.utid == '1' || currentUser.utid == '3' }">
					        		<option value="2">Nutzername</option>
					        	</c:if>
							</select>
						</div>
						<div class="pure-u-3-5">
					        	<input type="text" name="searchOrderPattern" class="boxedinput" placeholder="Suchinformation eingeben">
						</div>
						<div class="pure-u-1-5">
			        		<button type="submit" name="searchOrders" class="pure-button pure-button-primary boxedinput">Suchen</button>
			        	</div>
			        </div>
			    </fieldset>
			</form>
		</div>
		</div>
	<div class="pure-u-1-5 sizezero"></div>
</div>

<center>
	<div class="search-results">	
		<c:if test="${ not empty orders && currentUser.utid == 1 || not empty orders && currentUser.utid == 3}">
			<div class="pure-g">
			    <div class="pure-u-1-5 textbox"><p><b>Bestellnr.</b></p></div>
			    <div class="pure-u-1-5 textbox"><p><b>Name</b></p></div>
			    <div class="pure-u-1-5 textbox"><p><b>Status</b></p></div>
			    <div class="pure-u-1-5 textbox"><p><b>Datum</b></p></div>    
			</div>
		 	<c:forEach var="order" items="${orders}">
				<div>
					<hr>
					<div class="pure-g" >
					    <div class="pure-u-1-5 overflowhider"><p><b>${order.getOrid()}</b></p></div>
					    <div class="pure-u-1-5 overflowhider"><p>${order.getName() }  ${ order.getSurname()}</p></div>
					    <div class="pure-u-1-5 overflowhider"><p>${order.statuscycle.get(order.statuscycle.size()-1).description}</p></div>
					    <div class="pure-u-1-5 overflowhider"><p>${order.statuscycle.get(order.statuscycle.size()-1).statusdate}</p></div>
					    <div class="pure-u-1-5">
					    	<p>
					    		<i class="fa fa-pencil" style ="cursor: pointer;" onclick="location.href='./order?ID=${order.getOrid()}';"></i>
					    	</p>
					    </div>				  
					</div>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${ not empty orders && currentUser.utid == 2 }">
			<div class="pure-g">
			    <div class="pure-u-1-4 textbox"><p><b>Bestellnummer</b></p></div>
			    <div class="pure-u-1-4 textbox"><p><b>Status</b></p></div>
			    <div class="pure-u-1-4 textbox"><p><b>Bestelldatum</b></p></div>    
			</div>
		 	<c:forEach var="order" items="${orders}">
				<div>
					<hr>
					<div class="pure-g">
					    <div class="pure-u-1-4 overflowhider"><p><b>${order.getOrid()}</b></p></div>
					    <div class="pure-u-1-4 overflowhider"><p>${order.statuscycle.get(order.statuscycle.size()-1).description}</p></div>
					    <div class="pure-u-1-4 overflowhider"><p>${order.statuscycle.get(order.statuscycle.size()-1).statusdate}</p></div>
					    <div class="pure-u-1-4 overflowhider">
					    	<p>
					    		<i class="fa fa-search" style ="cursor: pointer;" onclick="location.href='./order?ID=${order.getOrid()}';"></i>
							</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${noOrderFound}">
			<form class="pure-form pure-form-aligned" action="user?page=ordersearch" method="get">
				<p class="error">Es wurden keine Suchergebnisse gefunden.</p>
			</form>	
		</c:if>	
	</div>
</center>