$("#toghead").on('click', function() {
	console.log("HERE");
	  $("#headermenu").slideToggle('fast');
	  
	  if($("#responsiveheader").hasClass('responsiveheaderimg'))
	  	$("#responsiveheader").removeClass('responsiveheaderimg');
	  else
		$("#responsiveheader").addClass('responsiveheaderimg');
});
