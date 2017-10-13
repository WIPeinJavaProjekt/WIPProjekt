<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<<<<<<< HEAD
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome to SnowSki</title>
<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="CSS/default.css">
<link rel="stylesheet" type="text/css" href="CSS/startpageslider.css">
<script src="JS/startpageslider.js"></script>
</head>
<body>
	
	<div id ="brandbox" >
		<div>
			<img id ="brandimg" src="Images/brand.png">
		</div>
	</div>
	
	
	<header>
		<div class="pure-g">
			<div class="pure-u-4-5"></div>
				<div class="pure-u-1-5">
			    	<form class="pure-form" action="start" method="post">
				    	<button type="submit" name="cart" class="pure-button pure-button-primary"><i class="fa fa-shopping-cart" aria-hidden="true"></i></button>
				    	<button type="submit" name="login" class="pure-button pure-button-primary"><i class="fa fa-home"></i></button>
			    	</form>
		    	</div>
    	</div>
	</header>


	
	
	<div class="pure-g" id ="searchbox">
		<div class="pure-u-1-5"></div>
		<div id="wrapper" class="pure-u-3-5">
			<div id="search-container" class="searchbox">			
				<form class="pure-form">
				    <fieldset>
				        <legend>
				        <h1>Starte dein nächstes Abenteuer!</h1>				        
				        </legend>
						<div class="pure-g">
					        <div class="pure-u-1-5">
						        <select name="categories" class="boxedinput">
								  <option value="clothes">Kleidung</option>
								  <option value="shoes">Skischuhe</option>
								  <option value="equipment">Ausrüstung</option>
								</select>
							</div>
					        <div class="pure-u-3-5">
					        	<input type="text" class="boxedinput" placeholder="Suchbegriff eingeben">
							</div>
							<div class="pure-u-1-5">
				        		<button type="submit" class="pure-button pure-button-primary boxedinput">Suchen</button>
				        	</div>
				        </div>
				    </fieldset>
				</form>			
			</div>
<div class="cssSlider">
 
    <!-- die inputs um den Slider zu Steuern -->
    <input type="radio" name="slider" id="slide01" checked="checked">
    <input type="radio" name="slider" id="slide02">
    <input type="radio" name="slider" id="slide03">
    <input type="radio" name="slider" id="slide04">
 
    <!-- die einzelnen Slides, hier als Liste angelegt -->
    <ul class="sliderElements">
        <li>
            <figure>
                <img src="Images/mountain.jpg" alt="" height="400" width="1200">
                <figcaption>Hier könnte deine Beschreibung stehen.</figcaption>
            </figure>
        </li>
        <li>
            <figure>
                <img src="Images/mountain.jpg" alt="" height="400" width="1200">
                <figcaption>Eine tolle Beschreibung</figcaption>
            </figure>
        </li>
        <li>
            <figure>
                <img src="Images/skityp.jpg" alt="" height="400" width="1200">
                <figcaption>Die Beschreibung zum dritten Bild</figcaption>
            </figure>
        </li>
        <li>
            <figure>
                <img src="Images/skityp.jpg" alt="" height="400" width="1200">
                <figcaption>Und noch ein toller Untertitel</figcaption>
            </figure>
        </li>
    </ul>
 
    <!-- Eine Steuerung -->
    <ul class="sliderControls">
        <li><label for="slide01"></label></li>
        <li><label for="slide02"></label></li>
        <li><label for="slide03"></label></li>
        <li><label for="slide04"></label></li>
    </ul>
</div>
		</div>
		<div class="pure-u-1-5"></div>
	</div>
	
	
	
	
	
	<footer>	
		<a>Impressum</a>	
	</footer>	

</body>
=======
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Welcome to SnowSki</title>
		<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="CSS/default.css">
		<link rel="stylesheet" type="text/css" href="CSS/font-awesome.min.css">
	</head>
	<body>
	
		<header>		
			<div class="pure-g">
				    <div class="pure-u-7-8"><p></p>
				    </div>
				    <div class="pure-u-1-8">
				    	<form class="pure-form" action="start" method="post">
					    	<p>
						    	<button class="pure-button pure-button-primary"  type="submit" name="cart">
								    Warenkorb
							    </button>
						    </p>
					    	<p><button type="submit" name="login" class="pure-button pure-button-primary">Login</button></p>
				    	</form>
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
					
					        <button type="submit" class="pure-button pure-button-primary">Suchen</button>
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
>>>>>>> 89bee4a36446f24b8032f993df522c37269ba3c7
</html>