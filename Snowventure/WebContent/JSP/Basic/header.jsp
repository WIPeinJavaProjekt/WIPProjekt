<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="./CSS/w3.css">

	
<header>
<form class="pure-form" action="start" method="post">
	<div class="pure-g">
		<div class="pure-u-1-5">
			
			<!--  
			<c:if test="${currentUser == null}">
				
				<div><img src="./Images/login.png"></div>
			</c:if>
			<c:if test="${currentUser != null}">
			<div class ="headerwelcome"><b>	<i>${currentUser.getName()}!</i>	</b></div>
			</c:if>-->
		
		</div>
	
			
			
		
				
				<div class="pure-u-3-5" align="left">
				<div class="pure-menu pure-menu-horizontal">
					
						<ul class="pure-menu-list">
								<li class="pure-menu-item">
								<div id ="brandbox" >
						<div id="innerbrandbox">
						<a href="start"><img id ="brandimg" src="./Images/brand.png" height= "105px" width="auto"></a>
						</div>
					</div>
								
								</li>
			    				<li class="pure-menu-item"><button type="submit" name="cart" class="headerbtn">ÜBER UNS</button></li>
								        <c:if test="${currentUser != null}">
								        <li class="pure-menu-item pure-menu-has-children pure-menu-allow-hover">
								        
								            <button type="submit" name="settings" class="headerbtn pure-menu-link">KONTO</button>
								            <ul class="pure-menu-children">
								                <li class="pure-menu-item"><button type="submit" name="logout" class="second-headerbtn pure-menu-link">(ABMELDEN)</button></li>
								            </ul>
								        </li>
								        </c:if>
								 <c:if test="${currentUser == null}"> <li class="pure-menu-item"><button type="submit" name="login" class="headerbtn firstheaderbtn">ANMELDEN</button></li> </c:if>     
								        <li class="pure-menu-item"><button type="submit" name="cart" class="headerbtn">WARENKORB <c:if test="${currentCart != null && currentCart.GetCountShoppingCartPosition() != 0 }">(${currentCart.GetCountShoppingCartPosition()})</c:if></button> </li>
							</ul>
			    		
			    		
				    	
			    		
		    		</div>
				</div>
				

		

	

	

		
	

   			<div class="pure-u-1-5">
		    	
	    	</div>
   	</div>
   		    	</form>		
		
   	<!-- <div style="height:2px; width:100%; background-color:rgba(75,75,75,0.1);"></div> -->
</header>