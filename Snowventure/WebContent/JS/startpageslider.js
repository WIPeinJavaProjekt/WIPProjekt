window.onresize= function(){scalediv();};
window.onload= function(){scalediv();};
$(document).ready(function() {scalediv();});

function scalediv(){
$('#brandimg').width($('#search-container').width());
};