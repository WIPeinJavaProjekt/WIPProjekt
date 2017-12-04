/**
 * Beschreibung: Methoden für das Bedienen der Filter Dropdowns
 * @author Ansprechpartner Fabian Meise
 *
 */

$("#filterbtn-show").on('click', function() {
    $("#filtersfield").slideToggle('fast');
});



$(".dropdown div ").on('click', "a", function() {
    $(this).parent().parent().find("dd div ul").slideToggle('fast');
});

$("#search").on('click', function() {
    $(".dropdown div ul").hide();
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




(function($) {
    $.fn.closest_descendent = function(filter) {
        var $found = $(),
            $currentSet = this; // Current place
        while ($currentSet.length) {
            $found = $currentSet.filter(filter);
            if ($found.length) break; // At least one match: break loop
            // Get all children of the current set
            $currentSet = $currentSet.children();
        }
        return $found.first(); // Return first match of the collection
    }
})(jQuery);