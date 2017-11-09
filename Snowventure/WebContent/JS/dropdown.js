

$(".dropdown div ").on('click',"a" , function() {
  console.log("test");
  console.log($(this).parent().parent());
  console.log($(this).parent().parent().children("dd div ul"));
  $(this).parent().parent().find("dd div ul").slideToggle('fast');
});

$(".dropdown div ul li a").on('click', function() {
  $(".dropdown div ul").hide();
});

function getSelectedValue(id) {
  return $("#" + id).find("div a span.value").html();
}

$(document).bind('click', function(e) {
  var $clicked = $(e.target);
  if (!$clicked.parents().hasClass("dropdown")) $(".dropdown div ul").hide();
});

$('.mutliSelect input[type="checkbox"]').on('click', function() {
  var  title = $(this).val() + ",";
  console.log("Title: "+title);
  
  if ($(this).is(':checked')) {
    var html = '<span title="' + title + '">' + title + '</span>';
    $(this).parent().parent().parent().parent().parent().parent().find('a .multiSel').append(html);
    
  } else {
    $('span[title="' + title + '"]').remove();
    

  }
});


(function($) {
    $.fn.closest_descendent = function(filter) {
        var $found = $(),
            $currentSet = this; // Current place
        while ($currentSet.length) {
            $found = $currentSet.filter(filter);
            if ($found.length) break;  // At least one match: break loop
            // Get all children of the current set
            $currentSet = $currentSet.children();
        }
        return $found.first(); // Return first match of the collection
    }    
})(jQuery);