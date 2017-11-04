window.onresize= function(){scalediv();};
window.onload= function(){scalediv();};
$(document).ready(function() {scalediv();});

function scalediv(){
	$('#brandimg').width($( window ).width()*3/5*0.25);
	$('#innerbrandbox').css("padding-left","22%");
	//$('.contentwrapp').css("min-height",$( window ).height()-$('#brandimg').height()-100);
	$('#loginbox').css("min-height",$( window ).height()-$('#brandimg').height()-300);
	$('#articleadminbox').css("min-height",$( window ).height()-$('#brandimg').height()-300);
	$('body').show();
};