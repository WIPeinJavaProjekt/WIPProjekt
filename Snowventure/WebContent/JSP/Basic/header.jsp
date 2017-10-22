<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id ="brandbox" >
		<div id="innerbrandbox">
			<a href="start"><img id ="brandimg" src="./Images/brand.png"></a>
		</div>
</div>
	
	
<header>
	<div class="pure-g">
		<div class="pure-u-1-5"></div>
		<div class="pure-u-3-5" align="right" >
			<c:if test="${!userLoggedIn}">
				<div><img src="./Images/login.png"></div>
			</c:if>
			<c:if test="${userLoggedIn}">
			<div class ="headerwelcome"><b>	Herzlich Willkommen, <i>${currentUser.getName()}!</i>	</b></div>
			</c:if>
		</div>
			<div class="pure-u-1-5">
		    	<form class="pure-form" action="start" method="post">
			    	<c:if test="${!userLoggedIn}">
			    		<button type="submit" name="login" class="pure-button pure-button-primary"><i class="fa fa-sign-in"></i></button>
		    		</c:if>
		    		<c:if test="${userLoggedIn}">
		    			<button type="submit" name="settings" class="pure-button pure-button-primary"><i class="fa fa-cog"></i></button>
			    		<button type="submit" name="logout" class="pure-button pure-button-primary"><i class="fa fa-sign-out"></i></button>
		    		</c:if>
		    		<button type="submit" name="start" class="pure-button pure-button-primary"><i class="fa fa-home"></i></button>
			    	<button type="submit" name="cart" class="pure-button pure-button-primary"><i class="fa fa-shopping-cart"></i></button>
		    	</form>
	    	</div>
   	</div>
</header>