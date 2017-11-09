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
				<div id ="brandbox" >
						<div id="innerbrandbox">
						<a href="start"><img id ="brandimg" src="./Images/brand.png" height= "105px" width="auto"></a>
						</div>
					</div>
					<div>
						<c:if test="${currentUser == null}">
				    		<button type="submit" name="login" class="headerbtn firstheaderbtn">ANMELDEN</button>
			    		</c:if>
				    	<c:if test="${currentUser != null}">
			    			<button type="submit" name="settings" class="headerbtn">KONTO</button>
				    		<button type="submit" name="logout" class="headerbtn">(ABMELDEN)</button>
			    		</c:if>
				    	<button type="submit" name="cart" class="headerbtn">WARENKORB</button>
			    		<button type="submit" name="cart" class="headerbtn">ÜBER UNS</button>
		    		</div>
				</div>
				

		

	

	

		
	

   			<div class="pure-u-1-5">
		    	
	    	</div>
   	</div>
   		    	</form>		
		
   	<!-- <div style="height:2px; width:100%; background-color:rgba(75,75,75,0.1);"></div> -->
</header>