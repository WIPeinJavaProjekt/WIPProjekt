<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<form class="pure-form pure-form-stacked" id="mydata-form" action="users" method="post">
    <fieldset>
        
        <legend>
        	<h1>Meine Personenangaben</h1>
        </legend>
       		
        <div class="pure-g">
            
	            <div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="first-name">Vorname</label>
	                <input id="first-name" name="first-name" class="pure-u-23-24" type="text" value="${currentUser.name}" required>
	            </div>
	
	            <div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="last-name">Nachname</label>
	                <input id="last-name" name="last-name" class="pure-u-23-24" type="text" value="${currentUser.surname}" required>
	            </div>
	
	            <div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="email">E-Mail</label>
	                <input id="email" name="email" class="pure-u-23-24" type="email" value="${currentUser.email}" required>
	            </div>			 
			
				<div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="street">Straße</label>
	                <input id="street" name="street" class="pure-u-23-24" type="text" value="${currentUser.adress.getStreet()}" required>
	            </div>
	
				<div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="houseno">Hausnummer</label>
	                <input id="houseno" name="houseno" class="pure-u-23-24" type="text" value="${currentUser.adress.getHouseno()}" required>
	            </div>
	
	            <div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="location">Stadt</label>
	                <input id="location" name="location" class="pure-u-23-24" type="text" value="${currentUser.adress.getLocation()}" required>
	            </div>
	            
	            <div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="postcode">PLZ</label>
	                <input id="postcode" name="postcode" class="pure-u-23-24" type="text" value="${currentUser.adress.postcode}" required>
	            </div>			
				<c:if test="${currentUser.utid == '1'}">
	            <div class="pure-u-1 pure-u-md-1-3">
	                <label for="state">Nutzertyp</label>
	                <select id="state" name="state" class="pure-input-1-2" required>
	                    <option value="customer" selected="${currentUser.utid=='2'?true:false}" >Kunde</option>
	                    <option value="employee" selected="${currentUser.utid=='3'?true:false}">Mitarbeiter</option>
	                    <option value="admin" selected="${currentUser.utid=='1'?true:false}">Admin</option>
	                </select>
	            </div>
	            </c:if>
        </div>

        <button type="submit" name="update-data" class="pure-button pure-button-primary">Änderung speichern</button>
    </fieldset>
</form>

<form class="pure-form pure-form-stacked" id="security-form" action="users" method="post">
<fieldset>
    
    	<legend>
    		<h1>Mein Passwort zurücksetzen</h1>
    	</legend>
    	
    	<div class="pure-class-g">
            
            <div class="pure-u-1 pure-u-md-1-3">
	            <label for="password">Aktuelles Passwort</label>
	            <input id="password" name="password" type="password">
	        </div>
	        
	        <div class="pure-u-1 pure-u-md-1-3">
	            <label for="new-password">Neues Passwort</label>
	            <input id="new-password" name="new-password" type="password">
	        </div>
	        
	        <div class="pure-u-1 pure-u-md-1-3">
	            <label for="new-passwordRepeat">Neues Passwort wiederholen</label>
	            <input id="new-passwordRepeat" name="new-passwordRepeat" type="password">
	        </div>
	        
	        <button type="submit" name="update-password" class="pure-button pure-button-primary">Änderung speichern</button>
    	</div>
    </fieldset>
</form>

<form class="pure-form pure-form-stacked" id="security-form" action="users" method="post">
	<fieldset>
    
    	<legend>
    		<h1>Meine Sicherheitsfrage</h1>
    	</legend>
    	
    	<div class="pure-class-g">         
            <div class="pure-control-group">
	            <select class="boxedinput" id="safetyQuestion" name="safetyQuestion" required>
	            	<option value="">Bitte Sicherheitsfrage auswählen</option>
				    <c:forEach items="${squestions}" var="squestion">
				        <option>${squestion.getQuestion()}</option>
				    </c:forEach>
				</select>
			</div>
 	        <div class="pure-control-group">
	            <input class="boxedinput" id="safetyAnswer" name="safetyAnswer" type="text" value="${currentUser.squestion.getAnswer()}" required placeholder="Antwort">
	        </div>
	        
	        <button type="submit" name="update-squestion" class="pure-button pure-button-primary">Änderung speichern</button>
    	</div>
    </fieldset>
</form>

<c:if test="${not empty passworderror}">
	<form class="pure-form pure-form-aligned" action="users?page=mydata" method="get">
			<p class="error">${passworderror}</p>
	</form>
</c:if>

    