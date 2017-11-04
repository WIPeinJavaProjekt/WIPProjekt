


<div class="w3-content w3-display-container w3slidmod" align="center">

<div class="w3-display-container mySlides">
   <img  height="512px" width = "auto" src="./Images/mountain.jpg"/>
</div>

<div class="w3-display-container mySlides">
   <img  height="512px" width = "auto" src="./Images/skityp.jpg"/>
</div>

<button class="w3-button w3-display-left w3-black" onclick="plusDivs(-1)">&#10094;</button>
<button class="w3-button w3-display-right w3-black" onclick="plusDivs(1)">&#10095;</button>

</div>
<script>
var slideIndex = 1;
showDivs(slideIndex);

function plusDivs(n) {
  showDivs(slideIndex += n);
}

function showDivs(n) {
  var i;
  var x = document.getElementsByClassName("mySlides");
  if (n > x.length) {slideIndex = 1}    
  if (n < 1) {slideIndex = x.length}
  for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";  
  }
  x[slideIndex-1].style.display = "block";  
}
</script>