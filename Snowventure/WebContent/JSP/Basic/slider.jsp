


<div class="w3-content w3-display-container w3slidmod startslider" style="max-width:100%;" align="center">

<div class="w3-display-container mySlides">
   <img  class="sliderimage" src="./Images/skityp.jpg"/>
</div>

<div class="w3-display-container mySlides">
   <img  class="sliderimage" src="./Images/mountain.jpg"/>
</div>

<button class="w3-display-left startslider-btn" onclick="plusDivs(-1)"><h1>&#10094;</h1></button>
<button class="w3-display-right startslider-btn" onclick="plusDivs(1)"><h1>&#10095;</h1></button>

</div>
<script>
var slideIndex = 1;
var clickcounter = 0;
showDivs(slideIndex);

function plusDivs(n) {
	if(clickcounter <1){
		  clickcounter++;
	showDivs(slideIndex += n);
	  setTimeout(function(){},2000);  
	clickcounter--;
	  }
}

function showDivs(n) {
   var i;
  
  var x = document.getElementsByClassName("mySlides");
  
  if (n > x.length) {slideIndex = 1}    
  if (n < 1) {slideIndex = x.length}
  for (i = 0; i < x.length; i++) {
	  
	  $(x[slideIndex-1]).fadeOut(500);
	  x[i].style.display = "none";
  }
  //x[slideIndex-1].style.display = "block";
  $(x[slideIndex-1]).fadeIn(1500);
}



</script>