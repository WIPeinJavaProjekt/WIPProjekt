<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="pure-g" id ="account-searchbox">
	<div class="pure-u-1-5"></div>
	<div id="wrapper" class="pure-u-3-5">
		<div id="search-container" class="searchbox">
			<form class="pure-form" action="users" method="POST">
			    <fieldset>
			        <legend>
			        	<h3>Bestellungen Suchen</h3>
			        </legend>
					<div class="pure-g">
						<div class="pure-u-4-5">
					        <div class="pure-u-1-1">
					        	<input type="text" name="searchOrderByUserPattern" class="boxedinput" placeholder="Nutzernamen eingeben">
							</div>
					        <div class="pure-u-1-1">
					        	<input type="text" name="searchOrderIDPattern" class="boxedinput" placeholder="Bestellnummer eingeben">
							</div>
						</div>
						<div class="pure-u-1-5">
			        		<button type="submit" name="searchOrders" class="pure-button pure-button-primary boxedinput">Suchen</button>
			        	</div>
			        </div>
			    </fieldset>
			</form>
		</div>
		</div>
	<div class="pure-u-1-5"></div>
</div>

<center>
	<div class="pure-g">
	    <div class="pure-u-1-5"><p><b>Bestellnummer</b></p></div>
	    <div class="pure-u-1-5"><p><b>Name</b></p></div>
	    <div class="pure-u-1-5"><p><b>Bestellstatus</b></p></div>
	    <div class="pure-u-1-5"><p><b>Datum</b></p></div>    
	</div>
	<div class="search-results">	
		<c:if test="${ not empty orders }">
		 	<c:forEach var="order" items="${orders}">
				<div>
					<hr>
					<div class="pure-g" >
					    <div class="pure-u-1-5"><p><b>${order.GetId()}</b></p></div>
					    <div class="pure-u-1-5"><p>${order.GetName() + ' ' + order.GetSurname()}</p></div>
					    <div class="pure-u-1-5"><p>${order.statuscycle.get(order.statuscycle.size()-1).description}</p></div>
					    <div class="pure-u-1-5"><p>${order.statuscycle.get(order.statuscycle.size()-1).statusdate}</p></div>
					    <div class="pure-u-1-5">
					    	<p>
					    		<i class="fa fa-pencil" style ="cursor: pointer;" onclick="location.href='./order?ID=${order.GetId()}';"></i>
					    		<i class="fa fa-trash" aria-hidden="true"></i>
					    	</p>
					    </div>				  
					</div>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${noOrderFound}">
			<form class="pure-form pure-form-aligned" action="user?page=ordersearch" method="get">
				<p class="error">Es wurde keine Suchergebnisse gefunden.</p>
			</form>	
		</c:if>	
	</div>
</center>