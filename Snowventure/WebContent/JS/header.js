window.onresize= function(){scalediv();};
window.onload= function(){scalediv();};
$(document).ready(function() {scalediv();});

function scalediv(){
	$('#brandimg').width($('#search-container').width()*0.6);
	$('#innerbrandbox').css("padding-left",$('#search-container').width()*0.2);
};