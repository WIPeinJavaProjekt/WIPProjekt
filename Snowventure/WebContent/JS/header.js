window.onresize= function(){scalediv();};
window.onload= function(){scalediv();};
$(document).ready(function() {scalediv();});

function scalediv(){
	//$('#brandimg').width($( window ).width()*3/5*0.25);
	//$('#innerbrandbox').css("padding-left","22%");
	//$('.contentwrapp').css("min-height",$( window ).height()-$('#brandimg').height()-100);
	$('#useroptions').css("min-height",$( window ).height()-872.5>350 ? $( window ).height()-872.5 : 350);
	$('#loginbox').css("min-height",$( window ).height()-$('#brandimg').height()-300 > 350? $( window ).height()-$('#brandimg').height()-300 : 350);
	console.log(window.innerWidth);
	console.log($(window).width());
	$('#articleresultcontainer').css("min-height",$( window ).height()-300);
	$('#articleadminbox').css("min-height",$( window ).height()-$('#brandimg').height()-300);
	$('content-container').css("min-height",$( window ).height()-800>450?$( window ).height()-800>450 :450);
	$('.startslider-btn').css("width", $('.startslider').width()<1250? 38 : ($('.startslider').width()-1250)/2);
	$('body').show();
	//var paddingarticle = (($( window ).width()-(parseInt($( window ).width()/360)-1)*4)%360)/2;
	//$('#articleresultcontainer').css("padding-left",paddingarticle < 1 ? 180 + $( window ).width()*0.2 : paddingarticle +$( window ).width()*0.2);
	
};