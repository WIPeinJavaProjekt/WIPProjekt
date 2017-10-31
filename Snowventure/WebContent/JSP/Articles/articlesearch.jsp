<div class="pure-g" id ="account-searchbox">
	<div class="pure-u-1-5"></div>
	<div id="wrapper" class="pure-u-3-5">
		<div id="search-container" class="searchbox">
			<form class="pure-form" action="users" method="POST">
			    <fieldset>
			        <legend>
			        <h1>Artikelsuche</h1>
			        </legend>
					<div class="pure-g">
				        <div class="pure-u-1-5">
					        <select name="categories" class="boxedinput">
					          <option value="all">Alle</option>
							  <option value="customer">Kleidung</option>
							  <option value="employee">Skischuhe</option>
							  <option value="admin">Ausrüstung</option>
							</select>
						</div>
				        <div class="pure-u-3-5">
				        	<input type="text" class="boxedinput" placeholder="Artikelinformationen eingeben">
						</div>
						<div class="pure-u-1-5">
			        		<button type="submit" class="pure-button pure-button-primary boxedinput">Suchen</button>
			        	</div>
			        	
			        	<div class="pure-u-1-5">
			        		<button type="submit" name="addArticle" class="pure-button pure-button-primary boxedinput">Artikel hinzufügen</button>
			        	</div>
			        </div>
			    </fieldset>
			</form>
		</div>
	</div>
	<div class="pure-u-1-5"></div>
</div>

<div class="search-results">

</div>

<div class="article-info">
	
</div>