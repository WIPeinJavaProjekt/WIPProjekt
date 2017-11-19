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
					<div class="pure-g">
						<div class="pure-u-4-5">
					        	<input type="text" name="searchOrderIDPattern" class="boxedinput" placeholder="Bestellnummer eingeben">
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
			    <div class="pure-u-1-5"><p><b>Bestellnummer</b></p></div>
			    <div class="pure-u-1-5"><p><b>Name</b></p></div>
			    <div class="pure-u-1-5"><p><b>Bestellstatus</b></p></div>
			    <div class="pure-u-1-5"><p><b>Datum</b></p></div>    
			</div>
		 	<c:forEach var="order" items="${orders}">
				<div>
					<hr>
					<div class="pure-g" >
					    <div class="pure-u-1-5"><p><b>${order.getOrid()}</b></p></div>
					    <div class="pure-u-1-5"><p>${order.getName() }  ${ order.getSurname()}</p></div>
					    <div class="pure-u-1-5"><p>${order.statuscycle.get(order.statuscycle.size()-1).description}</p></div>
					    <div class="pure-u-1-5"><p>${order.statuscycle.get(order.statuscycle.size()-1).statusdate}</p></div>
					    <div class="pure-u-1-5">
					    	<p>
					    		<i class="fa fa-pencil" style ="cursor: pointer;" onclick="location.href='./order?ID=${order.getOrid()}';"></i>
					    		<i class="fa fa-trash" aria-hidden="true"></i>
					    	</p>
					    </div>				  
					</div>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${ not empty orders && currentUser.utid == 2 }">
			<div class="pure-g">
			    <div class="pure-u-1-4"><p><b>Bestellnummer</b></p></div>
			    <div class="pure-u-1-4"><p><b>Bestellstatus</b></p></div>
			    <div class="pure-u-1-4"><p><b>Bestelldatum</b></p></div>    
			</div>
		 	<c:forEach var="order" items="${orders}">
				<div>
					<hr>
					<div class="pure-g" onclick="location.href='./order?ID=${order.getOrid()}';">
					    <div class="pure-u-1-4"><p><b>${order.getOrid()}</b></p></div>
					    <div class="pure-u-1-4"><p>${order.statuscycle.get(order.statuscycle.size()-1).description}</p></div>
					    <div class="pure-u-1-4"><p>${order.statuscycle.get(order.statuscycle.size()-1).statusdate}</p></div>
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