<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome to SnowSki</title>
<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="CSS/default.css">
<link rel="stylesheet" type="text/css" href="CSS/startpageslider.css">
<link rel="shortcut icon" href="/favicon.ico">
<link rel="icon" type="image/png" href="Images/favicon.png" sizes="32x32">
<link rel="icon" type="image/png" href="Images/favicon.png" sizes="96x96">
</head>
<body>

	<%@include file = "header.jsp" %>

	<div class="pure-g" id ="searchbox">
		<div class="pure-u-1-5"></div>
		<div id="wrapper" class="pure-u-3-5">
			<div id="search-container" class="searchbox">
				<form class="pure-form">
				    <fieldset>
				        <legend>
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
					        	<input type="text" class="boxedinput" placeholder="Wähle deine Ausrüstung">
							</div>
							<div class="pure-u-1-5">
				        		<button type="submit" class="pure-button pure-button-primary boxedinput">Suchen</button>
				        	</div>
				        </div>
				    </fieldset>
				</form>
			</div>
<div class="cssSlider" align="center">

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
		<a href="impressum">Impressum</a>
	</footer>

</body>

</html>
