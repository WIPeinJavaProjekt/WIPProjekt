<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty selectedUser}">
	<h1>${selectedUser.username}</h1>
</c:if>

<form class="pure-form" action="register" method="post">
    <div class="pure-g">
	    <div class ="pure-u-1-2 leftdiv">
		    <fieldset class="pure-group">								    
		    	<label>Name</label>							    	
		    	<div class="pure-control-group">
		            <input class="boxedinput" id="username"  name="username" value="${username}" type="text" required placeholder="Benutzername">
		        </div>					        
		        <div class="pure-control-group">
		            <input class="boxedinput" id="name" name="name" value="${name}" type="text" required placeholder="Vorname">
		        </div>
		        <div class="pure-control-group">
		            <input class="boxedinput" id="surname" name="surname" value="${surname}" type="text" required placeholder="Nachname">
		        </div>					        
		        <div class="pure-control-group">
		            <input class="boxedinput" id="email" name="email" value="${email}" type="email" required placeholder="E-Mail-Addresse">
		        </div>
	        </fieldset>
	            
       	</div> 
       	<div class ="pure-u-1-2"> 
	        <fieldset class="pure-group rightdiv">
	        	<label>Passwort</label>				
		        <div class="pure-control-group">
		            <input class="boxedinput" id="password" name="password" type="password" required placeholder="Passwort">
		        </div>
		        <div class="pure-control-group">
		            <input class="boxedinput" id="passwordRepeat" name="passwordRepeat" type="password" required placeholder="Passwort wiederholen">
		        </div>	    
	        </fieldset>
	   	</div>
	</div>
				
	<div class="pure-g">
	    <div class ="pure-u-1-2 leftdiv">
		   	<fieldset class="pure-group"> 
	       		<label>Anschrift</label>
		        <div class="pure-control-group">
		            <input class="boxedinput" id="location" name="location" value="${location}" type="text" required placeholder="Wohnort">
		        </div>
		        <div class="pure-control-group">
		            <input class="boxedinput" id="street" name="street" value="${street}" type="text" required placeholder="Straße">
		        </div>
		        <div class="pure-control-group">
		            <input class="boxedinput" id="houseno" name="houseno" value="${houseno}" type="text" required placeholder="Hausnummer">
		        </div>
		        <div class="pure-control-group">
		            <input class="boxedinput" id="postcode" name="postcode" value="${postcode}" type="text" required placeholder="Postleitzahl">
		        </div>
		    </fieldset>
		    
		    <fieldset class="pure-group">		            	        
		        <c:if test="${not empty currentUser && currentUser.utid == '1'}">
		        <div class="pure-control-group">
	                <label>Nutzertyp</label>
	                <select id="state" name="state" class="boxedinput" required>
	                    <option value="customer" <c:if test="${selectedUser.utid=='2'}"><c:out value="selected"/></c:if>>Kunde</option>
	                    <option value="employee" <c:if test="${selectedUser.utid=='3'}"><c:out value="selected"/></c:if>>Mitarbeiter</option>
	                    <option value="admin" <c:if test="${selectedUser.utid=='1'}"><c:out value="selected"/></c:if>>Admin</option>
	                </select>
	            </div>
	            </c:if>
	        </fieldset>
		    
	       	<fieldset>
				<div class="pure-control-group">
		            <button class="pure-button pure-button-primary boxedinput" type="submit" name="submitRegister" class="pure-button pure-button-primary">Abschicken</button>
		        </div>
	       	</fieldset>
		</div>
		<div class ="pure-u-1-2 rightdiv">
			<fieldset class="pure-group">
	        	<label>Sicherheitsfrage</label>			        
		        <div class="pure-control-group">
		            <select class="boxedinput" id="safetyQuestion" name="safetyQuestion" required>
		            	 <option value="">Bitte Sicherheitsfrage auswählen</option>
					    <c:forEach items="${squestions}" var="squestion">
					        <option value="${squestion.getId()}">${squestion.getQuestion()}</option>
					    </c:forEach>
					</select>
		        </div>
		        <div class="pure-control-group">
		            <input class="boxedinput" id="safetyAnswer" name="safetyAnswer" type="text" required placeholder="Antwort">
		        </div>
	        </fieldset>
		</div>
	</div>
</form>