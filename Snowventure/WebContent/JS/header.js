window.onresize= function(){scalediv();};
window.onload= function(){scalediv();};
$(document).ready(function() {scalediv();});

function scalediv(){
	$('#brandimg').width($( window ).width()*3/5*0.6);
	$('#innerbrandbox').css("padding-left",$( window ).width()*3/5*0.2);
	$('.contentwrapp').css("min-height",$( window ).height()-$('#brandimg').height()-100);
};