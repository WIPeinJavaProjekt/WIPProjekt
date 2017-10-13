<form class="pure-form pure-form-stacked" id="mydata-form">
    <fieldset>
        <legend>Personenangaben</legend>

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
                <label for="phone">Telefon</label>
                <input id="phone" class="pure-u-23-24" type="text">
            </div>
			
			<div class="pure-u-1 pure-u-md-1-3">
                <label for="street">Straße</label>
                <input id="street" class="pure-u-23-24" type="text" required>
            </div>

            <div class="pure-u-1 pure-u-md-1-3">
                <label for="city">Stadt</label>
                <input id="city" class="pure-u-23-24" type="text" required>
            </div>
            
            <div class="pure-u-1 pure-u-md-1-3">
                <label for="postcode">PLZ</label>
                <input id="postcode" class="pure-u-23-24" type="text" required>
            </div>

            <div class="pure-u-1 pure-u-md-1-3">
                <label for="state">Nutzertyp</label>
                <select id="state" class="pure-input-1-2" required>
                    <option>Kunde</option>
                    <option>Mitarbeiter</option>
                    <option>Admin</option>
                </select>
            </div>
        </div>

        <button type="submit" class="pure-button pure-button-primary">Änderung speichern</button>
    </fieldset>
</form>