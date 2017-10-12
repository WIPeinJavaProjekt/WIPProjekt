<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to SnowSki</title>
<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="CSS/default.css">
</head>
<body>

	<header>
	
		<div class="pure-g">
		
		    <div class="pure-u-7-8"><p></p>
		    </div>
		    <div class="pure-u-1-8">
		    	<p>Warenkorb</p>
		    	<p>Login</p>
		    </div>
		    
		</div>
	
	</header>


	<div class="pure-g">
		<div class="pure-u-1-5"></div>
		<div id="wrapper" class="pure-u-3-5">
			<div id="search-container" class="searchbox">
			
				<form class="pure-form">
				    <fieldset>
				        <legend>Welches Abenteuer begegnet dir als Nächstes?</legend>
				
				        <select name="categories">
						  <option value="clothes">Kleidung</option>
						  <option value="shoes">Skischuhe</option>
						  <option value="equipment">Ausrüstung</option>
						</select>
				        <input type="text" placeholder="Suchbegriff eingeben">
				
				        <button type="submit" class="pure-button pure-button-primary">SUCHEN</button>
				    </fieldset>
				</form>			
			</div>
			
		</div>
		<div class="pure-u-1-5"></div>
	</div>
	
	
	<footer>
	
		<a>Impressum</a>
	
	</footer>
	

</body>
</html>