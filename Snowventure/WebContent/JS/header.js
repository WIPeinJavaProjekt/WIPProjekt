/**
 * Beschreibung: Diverse automatische Skalierungen
 * @author Ansprechpartner Fabian Meise
 *
 */
window.onresize = function() { scalediv(); };
window.onload = function() { scalediv(); };
$(document).ready(function() { scalediv(); });

function scalediv() {
    //$('#brandimg').width($( window ).width()*3/5*0.25);
    //$('#innerbrandbox').css("padding-left","22%");
    $('#useroptions').css("min-height", $(window).height() - 872.5 > 350 ? $(window).height() - 872.5 : 350);
    $('#loginbox').css("min-height", $(window).height() - $('#brandimg').height() - 300 > 350 ? $(window).height() - $('#brandimg').height() - 300 : 350);
    $('#articleresultcontainer').css("min-height", $(window).height() - 300);
    $('#articleadminbox').css("min-height", $(window).height() - $('#brandimg').height() - 300);
    $('.content-container').css("min-height", $(window).height() - $('header').height() - $('footer').height() - 80 > 0 ? $(window).height() - $('header').height() - $('footer').height() - 80 : 450);
    $('#headerdummy').css("height", $('header').height());
    $('body').show();
    //var paddingarticle = (($( window ).width()-(parseInt($( window ).width()/360)-1)*4)%360)/2;
    //$('#articleresultcontainer').css("padding-left",paddingarticle < 1 ? 180 + $( window ).width()*0.2 : paddingarticle +$( window ).width()*0.2);
    
    if($(window).width() >= 1340)
	{
    	$('#headermenu').css("display","block");
	}
};




var lastScrollTop = 0;
$(window).scroll(function(event) {
    var st = $(this).scrollTop();
    if (st < lastScrollTop) {

        if ($(window).scrollTop() >= 122.5) {
            $('header').addClass('fixed-header', 3000);
            $('#headerdummy').show();
        } else {
            $('header').removeClass('fixed-header', 3000);
            $('#headerdummy').hide();
        }
    } else {

        $('header').removeClass('fixed-header', 3000);
        $('#headerdummy').hide();

    }
    lastScrollTop = st;
});