window.onresize= function(){scalediv();};
window.onload= function(){scalediv();};
$(document).ready(function() {scalediv();});

function scalediv(){
	//$('#brandimg').width($( window ).width()*3/5*0.25);
	//$('#innerbrandbox').css("padding-left","22%");
	//$('.contentwrapp').css("min-height",$( window ).height()-$('#brandimg').height()-100);
	$('#loginbox').css("min-height",$( window ).height()-$('#brandimg').height()-300 > 99? $( window ).height()-$('#brandimg').height()-300 : 100);
	$('#articleadminbox').css("min-height",$( window ).height()-$('#brandimg').height()-300);
	$('body').show();
	var paddingarticle = (($( window ).width()-(parseInt($( window ).width()/360)-1)*4)%360)/2;
	$('#articleresultcontainer').css("padding-left",paddingarticle < 1 ? 180 : paddingarticle);
	
};