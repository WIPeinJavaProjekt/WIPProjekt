<form class="pure-form pure-form-stacked" id="mydata-form" action="users" method="post">
    <fieldset>
        <legend>
        	<h1>Personenangaben</h1>
        </legend>

        <div class="pure-g">
            <div class="pure-u-1 pure-u-md-1-3">
                <label for="first-name">Vorname</label>
                <input id="first-name" class="pure-u-23-24" type="text" required>
            </div>

            <div class="pure-u-1 pure-u-md-1-3">
                <label for="last-name">Nachname</label>
                <input id="last-name" class="pure-u-23-24" type="text" required>
            </div>

            <div class="pure-u-1 pure-u-md-1-3">
                <label for="email">E-Mail</label>
                <input id="email" class="pure-u-23-24" type="email" required>
            </div>
			
			<div class="pure-u-1 pure-u-md-1-3">
                <label for="street">Straße</label>
                <input id="street" class="pure-u-23-24" type="text" required>
            </div>

			<div class="pure-u-1 pure-u-md-1-3">
                <label for="houseno">Hausnummer</label>
                <input id="houseno" class="pure-u-23-24" type="text" required>
            </div>

            <div class="pure-u-1 pure-u-md-1-3">
                <label for="location">Stadt</label>
                <input id="location" class="pure-u-23-24" type="text" required>
            </div>
            
            <div class="pure-u-1 pure-u-md-1-3">
                <label for="postcode">PLZ</label>
                <input id="postcode" class="pure-u-23-24" type="text" required>
            </div>

            <div class="pure-u-1 pure-u-md-1-3">
                <label for="state">Nutzertyp</label>
                <select id="state" class="pure-input-1-2" required>
                    <option value="customer" >Kunde</option>
                    <option value="employee">Mitarbeiter</option>
                    <option value="admin">Admin</option>
                </select>
            </div>
        </div>

        <button type="submit" class="pure-button pure-button-primary">Änderung speichern</button>
    </fieldset>
    
    <fieldset>
    
    	<legend>
    		<h1>Passwort zurücksetzen</h1>
    	</legend>
    	
    	<div class="pure-class-g">
            
            <div class="pure-u-1 pure-u-md-1-3">
	            <label for="password">Aktuelles Passwort</label>
	            <input id="password" name="password" type="password">
	        </div>
	        
	        <div class="pure-u-1 pure-u-md-1-3">
	            <label for="new-password">Neues Passwort</label>
	            <input id="new-password" name="password" type="password">
	        </div>
	        
	        <div class="pure-u-1 pure-u-md-1-3">
	            <label for="new-passwordRepeat">Neues Passwort wiederholen</label>
	            <input id="new-passwordRepeat" type="password">
	        </div>
	        
	        <button type="submit" class="pure-button pure-button-primary">Änderung speichern</button>
    	</div>
    </fieldset>
    
    
</form>