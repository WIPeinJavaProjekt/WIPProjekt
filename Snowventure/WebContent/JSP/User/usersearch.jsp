<div class="pure-g" id ="searchbox">
	<div class="pure-u-1-5"></div>
	<div id="wrapper" class="pure-u-3-5">
		<div id="search-container" class="searchbox">
			<form class="pure-form" action="" method="GET">
			    <fieldset>
			        <legend>
			        <h1>Nutzersuche</h1>
			        </legend>
					<div class="pure-g">
				        <div class="pure-u-1-5">
					        <select name="categories" class="boxedinput">
					          <option value="all">Alle</option>
							  <option value="customer">Kunde</option>
							  <option value="employee">Mitarbeiter</option>
							  <option value="admin">Admin</option>
							</select>
						</div>
				        <div class="pure-u-3-5">
				        	<input type="text" class="boxedinput" placeholder="Nutzername eingeben">
						</div>
						<div class="pure-u-1-5">
			        		<button type="submit" name="search-user" class="pure-button pure-button-primary boxedinput">Suchen</button>
			        	</div>
			        </div>
			    </fieldset>
			</form>			
			</div>
				<div>
				<form class="add-new-user-form" action="" method="GET">
					<div class="pure-u-1-5" id="add-new-user">
				        <button type="submit" name="new-user" class="pure-button pure-button-primary boxedinput" style="width=20%">Nutzer anlegen</button>
				    </div>
				</form>		
			</div>			
		</div>
	<div class="pure-u-1-5"></div>
</div>

<div class="search-results">

</div>

<div class="user-info">

</div>